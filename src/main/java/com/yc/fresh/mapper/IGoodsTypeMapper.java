package com.yc.fresh.mapper;

import java.util.List;

import com.yc.fresh.entity.GoodsType;

public interface IGoodsTypeMapper {
	public int add(GoodsType gt);
	
	public List<GoodsType> finds();
}
