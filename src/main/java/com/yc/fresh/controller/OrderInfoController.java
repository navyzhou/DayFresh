package com.yc.fresh.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yc.fresh.entity.MemberInfo;
import com.yc.fresh.entity.OrderInfo;
import com.yc.fresh.service.IOrderInfoService;

@RestController
@RequestMapping("/order")
public class OrderInfoController {
	@Autowired
	private IOrderInfoService service;
	
	@RequestMapping("/add")
	public int add(OrderInfo of, String gnos, HttpSession session) {
		Object obj = session.getAttribute("loginMember");
		if (obj == null) { // 说明没有登录
			return -1;
		}
		MemberInfo mf = (MemberInfo) obj;
		return service.add(of, gnos.split(","), String.valueOf(mf.getMno()));
	}
}
