package com.yc.fresh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.fresh.entity.MemberInfo;
import com.yc.fresh.mapper.IMemberInfoMapper;
import com.yc.fresh.service.IMemberInfoService;
import com.yc.fresh.util.StringUtil;

@Service
public class MemberInfoServiceImpl implements IMemberInfoService{
	@Autowired
	private IMemberInfoMapper mapper;

	@Override
	public MemberInfo login(MemberInfo mf) {
		if (StringUtil.checkNull(mf.getNickName(), mf.getPwd())) {
			return null;
		}
		return mapper.login(mf);
	}

	@Override
	public int add(MemberInfo mf) {
		if (StringUtil.checkNull(mf.getNickName(), mf.getEmail(), mf.getPwd())) {
			return -1;
		}
		return mapper.add(mf);
	}
}
