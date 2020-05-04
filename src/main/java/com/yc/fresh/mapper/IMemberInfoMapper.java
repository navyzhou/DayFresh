package com.yc.fresh.mapper;

import com.yc.fresh.entity.MemberInfo;

public interface IMemberInfoMapper {
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
