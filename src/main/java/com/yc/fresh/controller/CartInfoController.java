package com.yc.fresh.controller;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yc.fresh.entity.CartInfo;
import com.yc.fresh.entity.MemberInfo;
import com.yc.fresh.service.ICartInfoService;

@RestController
@RequestMapping("/cart")
public class CartInfoController {
	@Autowired
	private ICartInfoService service;

	private MemberInfo getMemberInfo(HttpSession session) {
		Object obj = session.getAttribute("loginMember");
		if (obj == null) { // 说明没有登录
			return null;
		}
		return (MemberInfo) obj;
	}
	
	@RequestMapping("/getInfo")
	public Map<Object, Object> getInfo(HttpSession session) {
		MemberInfo mf = this.getMemberInfo(session);
		if (mf == null) { // 说明没有登录
			return null;
		}
		Map<Object, Object> map = service.findByMno(mf.getMno());
		System.out.println(map);
		return map; // 根据会员编号，查询购物车信息
	}
	
	@RequestMapping("/update")
	public int update(HttpSession session, CartInfo cf) {  //gno  num  mno
		MemberInfo mf = this.getMemberInfo(session);
		if (mf == null) { // 说明没有登录
			return -1;
		}
		
		cf.setMno(mf.getMno());
		return service.update(cf);
	}
	
	@RequestMapping("/updates")
	public int updates(CartInfo cf) {
		return service.update(cf);
	}
	
	@RequestMapping("/add")
	public int add(HttpSession session, CartInfo cf) {
		MemberInfo mf = this.getMemberInfo(session);
		if (mf == null) { // 说明没有登录
			return -1;
		}
		cf.setMno(mf.getMno());
		cf.setCno(new Date().getTime() + "" + new Random().nextInt(1000));
		cf.setNum(1);
		return service.add(cf); 
	}
	
	@RequestMapping("/finds")
	public List<CartInfo> finds(HttpSession session) {
		Object obj = session.getAttribute("loginMember");
		if (obj == null) { // 说明没有登录
			return null;
		}
		
		MemberInfo mf = (MemberInfo) obj;
		return service.finds(mf.getMno());
	}
	
	@RequestMapping("/del")
	public int del(String cno) {
		return service.deleteByCno(cno);
	}
	
	@RequestMapping("/findByCnos")
	public List<CartInfo> findByCnos(String cnos) {
		
		return service.findByCnos(cnos.split(","));
	}
}
