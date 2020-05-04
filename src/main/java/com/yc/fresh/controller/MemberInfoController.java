package com.yc.fresh.controller;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yc.fresh.entity.MemberInfo;
import com.yc.fresh.service.IMemberInfoService;
import com.yc.fresh.util.SendMailUtil;

@RestController
@RequestMapping("/member")
public class MemberInfoController {
	@Autowired
	private SendMailUtil sendMailUtil; // 邮件发送的工具类

	@Autowired
	private IMemberInfoService service;

	@RequestMapping("/code")
	public int code(String receive, String name, HttpSession session) {
		String code = "";
		Random rd = new Random();
		while( code.length() < 6) {
			code += rd.nextInt(10);
		}

		if (sendMailUtil.sendHtmlMail(receive, name, code)) { // 如果发送成功
			session.setAttribute("code", code);

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					session.removeAttribute("code"); // 清空验证码
				}
			};

			Timer timer = new Timer(); // 实例化一个定时器
			timer.schedule(task, 180000); // 3分钟后执行任务task一次
			return 1;
		}
		return 0;
	}

	/**
	 * 会员注册 
	 * @param session
	 * @param mf 我们将验证码暂存到 realName属性中
	 * @return
	 */
	@RequestMapping("/reg")
	public int reg(HttpSession session, MemberInfo mf) {
		Object obj = session.getAttribute("code");
		if (obj == null) { // 说明验证码过期了
			return -2;
		}

		String code = (String) obj;
		if (!code.equals(mf.getRealName())) { // 验证码错误
			return -1;
		}

		return service.add(mf);
	}

	/**-
	 * 会员登录
	 * @param session
	 * @param mf
	 * @return
	 */
	@RequestMapping("/login")
	public int login(HttpSession session, MemberInfo mf) {
		String code = mf.getRealName();
		String vcode = (String) session.getAttribute("validateCode");
		
		if (!code.equalsIgnoreCase(vcode)) {
			return -1;
		}
		MemberInfo memberInfo = service.login(mf);
		
		if (memberInfo == null) {
			return 0;
		}
		session.setAttribute("loginMember", memberInfo);
		return 1;
	}
	
	/**
	 * 校验用户有没有登录
	 * @param session
	 * @return
	 */
	@RequestMapping("/check")
	public MemberInfo checkLogin(HttpSession session) {
		Object obj = session.getAttribute("loginMember");
		if (obj == null) {
			return new MemberInfo();
		}
		return (MemberInfo) obj;
	}
}
