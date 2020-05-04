package com.yc.fresh.mapper;

import java.util.List;

import com.yc.fresh.entity.CartInfo;

public interface ICartInfoMapper {
	/**
	 * 添加购物车
	 * @param cf
	 * @return
	 */
	public int add(CartInfo cf);
	
	/**
	 * 根据会员编号查询购物车信息
	 * @param mno
	 * @return
	 */
	public List<CartInfo> findByMno(int mno);
	
	/**
	 * 根据会员编号查询购物车详细信息
	 * @param mno
	 * @return
	 */
	public List<CartInfo> finds(int mno);
	
	/**
	 * 同时删除多条购物车信息
	 * @param cnos
	 * @return
	 */
	public int delete(String[] cnos);
	
	/**
	 * 根据购物车编号删除购物车信息
	 * @param cno
	 * @return
	 */
	public int deleteByCno(String cno);
	
	/**
	 * 修改购物车信息，主要是修改数量
	 * @param cf
	 * @return
	 */
	public int update(CartInfo cf);
	
	public int updates(CartInfo cf);
	
	/**
	 * 根据会员编号和商品编号修改
	 * @param cf
	 * @return
	 */
	public int updateByMnoAndGno(CartInfo cf);
	
	/**
	 * 根据购物车编号查询购物车信息
	 * @param cnos
	 * @return
	 */
	public List<CartInfo> findByCnos(String[] cnos);
}
