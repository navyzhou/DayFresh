package com.yc.fresh.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.yc.fresh.util.StringUtil;

@Component
@ServerEndpoint("/websocket/{id}")
public class WebServerSocket {
	private static int onlineCount = 0; // 在线用户人数
	
	// 用来存放每个客户端对应的WebSocket对象
	private static CopyOnWriteArraySet<WebServerSocket> webSocketSet = new CopyOnWriteArraySet<WebServerSocket>();
	
	private Session session; 
	
	private String usid;
	
	/**
	 * 建立连接时
	 * @param session
	 * @param usid
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("id") String usid) {
		this.session = session;
		this.usid = usid;
		
		webSocketSet.add(this); // 将当前登录连接对象存到集合
		
		addOnlineCount(); // 在线人数+1
		
		sendMessage("连接WebSocket服务器成功....");
		System.out.println("有新的用户 " + usid + " 上线了, 当前在线用户人数为 " + onlineCount);
	}
	
	/**
	 * 当用户下线时
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从在线用户列表中移除当前对象
		subOnlineCount(); // 在线人数 -1
		System.out.println("有用户 " + usid + " 下线了, 当前在线用户人数为 " + onlineCount);
	}
	
	/**
	 * 接收客户端发过来的信息
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("收到来至 " + usid + " 的信息 " + message);
		
		// 群发
		for (WebServerSocket wss : webSocketSet) {
			wss.sendMessage(message);
		}
	}
	
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}
	
	/**
	 * 定义单聊
	 * @param message
	 * @param sid
	 */
	public static void sendInfo(String message, @PathParam("id")String sid) {
		if (StringUtil.checkNull(sid)) {
			return;
		}
		
		for (WebServerSocket wss : webSocketSet) {
			if (sid.equals(wss.usid)) {
				wss.sendMessage(message);
				return;
			}
		}
	}
	
	/**
	 * 发送信息给客户端的方法
	 * @param msg
	 */
	public void sendMessage(String msg) {
		try {
			this.session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static synchronized void addOnlineCount() {
		WebServerSocket.onlineCount ++;
	}
	
	public static int getOnlineCount() {
		return WebServerSocket.onlineCount;
	}
	
	private static synchronized void subOnlineCount() {
		WebServerSocket.onlineCount --;
	}
	
	/**
	 * 根据登录的用户ID获取对应的WebSocket通信对象
	 * @param usid
	 * @return
	 */
	public static WebServerSocket getWebSocket(String usid) {
		if (webSocketSet.isEmpty()) {
			return null;
		}
		
		for (WebServerSocket wss : webSocketSet) {
			if (usid.equals(wss.usid)) {
				return wss;
			}
		}
		
		return null;
	}
}
