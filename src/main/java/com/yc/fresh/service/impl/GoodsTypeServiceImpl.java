package com.yc.fresh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.fresh.entity.GoodsType;
import com.yc.fresh.mapper.IGoodsTypeMapper;
import com.yc.fresh.service.IGoodsTypeService;
import com.yc.fresh.util.StringUtil;

@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService{
	@Autowired
	private IGoodsTypeMapper mapper;

	@Override
	public int add(GoodsType gt) {
		if (StringUtil.checkNull(gt.getTname(), gt.getPic())) {
			return -1;
		}
		return mapper.add(gt);
	}

	@Override
	public List<GoodsType> finds() {
		return mapper.finds();
	}
}
