package com.yc.fresh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.fresh.entity.GoodsInfo;
import com.yc.fresh.entity.GoodsType;
import com.yc.fresh.mapper.IGoodsInfoMapper;
import com.yc.fresh.mapper.IGoodsTypeMapper;
import com.yc.fresh.service.IGoodsInfoService;
import com.yc.fresh.util.StringUtil;

@Service
public class GoodsInfoServiceImpl implements IGoodsInfoService{
	@Autowired
	private IGoodsInfoMapper mapper;
	
	@Autowired
	private IGoodsTypeMapper typeMapper;

	@Override
	public int add(GoodsInfo gf) {
		if (StringUtil.checkNull(gf.getGname(), gf.getDescr())) {
			return -1;
		}
		return mapper.add(gf);
	}

	@Override
	public Map<String, Object> finds() {
		List<GoodsInfo> goods = mapper.finds();
		
		// 所有商品类型
		List<GoodsType> types = typeMapper.finds();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goods", goods);
		map.put("types", types);
		
		return map;
	}

	@Override
	public List<GoodsInfo> findByType(int tno, int page, int rows) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("tno", tno);
		map.put("page", (page - 1) * rows);
		map.put("rows", rows);
		
		return mapper.findByType(map);
	}

	@Override
	public Map<String, Object> findByFirst(int tno, int page, int rows) {
		int total = mapper.getTotal(tno);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("goods", this.findByType(tno, page, rows));
		
		return result;
	}

	@Override
	public GoodsInfo findByGno(int gno) {
		return mapper.findByGno(gno);
	}
}
