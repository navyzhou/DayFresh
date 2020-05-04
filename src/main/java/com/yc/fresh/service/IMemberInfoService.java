package com.yc.fresh.service;

import com.yc.fresh.entity.MemberInfo;

public interface IMemberInfoService {
	/**
	 * 会员登录
	 * @param mf
	 * @return
	 */
	public MemberInfo login(MemberInfo mf);
	
	/**
	 * 会员注册
	 * @param mf
	 * @return
	 */
	public int add(MemberInfo mf);
}
