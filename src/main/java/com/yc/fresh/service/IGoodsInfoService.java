package com.yc.fresh.service;

import java.util.List;
import java.util.Map;

import com.yc.fresh.entity.GoodsInfo;

public interface IGoodsInfoService {
	/**
	 * 添加商品信息
	 * @param gf
	 * @return
	 */
	public int add(GoodsInfo gf);
	
	/**
	 * 首页显示的数据
	 * @return
	 */
	public Map<String, Object> finds();
	
	/**
	 * 根据商品类型分页查询
	 * @param tno
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<GoodsInfo> findByType(int tno, int page, int rows);
	
	/**
	 * 第一次根据商品类型分页查询
	 * @param tno
	 * @param page
	 * @param rows
	 * @return
	 */
	public Map<String, Object> findByFirst(int tno, int page, int rows);
	
	/**
	 * 根据商品编号，查询商品信息
	 * @param gno
	 * @return
	 */
	public GoodsInfo findByGno(int gno);
}
