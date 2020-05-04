package com.yc.fresh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.fresh.entity.AdminInfo;
import com.yc.fresh.mapper.IAdminInfoMapper;
import com.yc.fresh.service.IAdminInfoService;
import com.yc.fresh.util.StringUtil;

@Service
public class AdminInfoServiceImpl implements IAdminInfoService{
	@Autowired
	private IAdminInfoMapper mapper;
	
	@Override
	public AdminInfo login(AdminInfo af) {
		if (StringUtil.checkNull(af.getAname(), af.getPwd())) {
			return null;
		}
		return mapper.login(af);
	}
}
