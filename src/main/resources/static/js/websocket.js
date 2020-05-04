function openWebSocket(usid) {
	var socket;
	if (typeof(WebSocket) == undefined) {
		alert("对不起，您的浏览器不支持WebSocket通信...");
		return;
	}
	
	socket = new WebSocket("ws://127.0.0.1:8888/websocket/" + usid);
	
	socket.onopen = function() {
		console.info("Socket已连接...");
	}
	
	socket.onclose = function() {
		console.info("Socket已关闭...");
	}
	
	socket.onerror = function() {
		console.info("Socket服务器访问失败...");
	}
	
	socket.onmessage = function(msg) {
		if (msg.data == "101") { // 规定，如果通信数据是101，则说明是要挤退
			alert("对不起，您已经其它地方登陆，若非本人操作，请及时修改密码...");
			location.href="loginout";
		} else {
			console.info(msg);
		}
	}
}