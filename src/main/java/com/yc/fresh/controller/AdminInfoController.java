package com.yc.fresh.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.fresh.entity.AdminInfo;
import com.yc.fresh.service.IAdminInfoService;
import com.yc.fresh.websocket.WebServerSocket;


@Controller
@RequestMapping("/back")
public class AdminInfoController {
	@Autowired
	private IAdminInfoService adminInfoService;

	@RequestMapping("/login")
	@ResponseBody
	public int login(HttpSession session, AdminInfo af) {
		AdminInfo adminInfo = adminInfoService.login(af);
		if (adminInfo != null) {
			session.setAttribute("currentLoginAdmin", adminInfo);
			// 判断当前登陆用户有没有在其他地方登陆
			WebServerSocket wss = WebServerSocket.getWebSocket(String.valueOf(adminInfo.getAid()));
			if (wss != null) { // 说明已经登录，则发送挤退信息
				wss.sendMessage("101");
			}
			
			return 1;
		}
		return 0;
	}
	
	@RequestMapping("/check")
	public String checkLogin(HttpSession session) {
		Object obj = session.getAttribute("currentLoginAdmin");
		if (obj == null) {
			return "redirect:/back/index.html";
		} else {
			return "page/index.html";
		}
	}
	
	
	@RequestMapping("/get")
	@ResponseBody
	public Object getLoginInfo(HttpSession session) {
		return session.getAttribute("currentLoginAdmin");
	}
}
