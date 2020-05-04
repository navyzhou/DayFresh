package com.yc.fresh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.fresh.entity.AddrInfo;
import com.yc.fresh.mapper.IAddrInfoMapper;
import com.yc.fresh.service.IAddrInfoService;
import com.yc.fresh.util.StringUtil;

@Service
public class AddrInfoServiceImpl implements IAddrInfoService{
	@Autowired
	private IAddrInfoMapper mapper;
		
	@Override
	public int add(AddrInfo af) {
		if (StringUtil.checkNull(af.getAno(), af.getName(), af.getTel(), af.getProvince(), af.getCity(), af.getArea())) {
			return -1;
		}
		return mapper.add(af);
	}

	@Override
	public int update(String anos) { // 一个是原默认，一个是先在默认
		String[] temp = anos.split(";");
		if (temp.length == 1) { // 说明是第一次添加地址
			return mapper.update(temp[0]);
		} else { // 修改默认地址
			return mapper.updates(temp);
		}
	}

	@Override
	public List<AddrInfo> findByMno(int mno) {
		return mapper.findByMno(mno);
	}

}
