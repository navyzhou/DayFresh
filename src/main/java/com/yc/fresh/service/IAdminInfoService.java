package com.yc.fresh.service;

import com.yc.fresh.entity.AdminInfo;

public interface IAdminInfoService {
	/**
	 * 后台管理员登录
	 * @param af
	 * @return
	 */
	public AdminInfo login(AdminInfo af);
}
