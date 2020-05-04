package com.yc.fresh.mapper;

import com.yc.fresh.entity.AdminInfo;

public interface IAdminInfoMapper {
	/**
	 * 后台管理员登录
	 * @param af
	 * @return
	 */
	public AdminInfo login(AdminInfo af);
}
