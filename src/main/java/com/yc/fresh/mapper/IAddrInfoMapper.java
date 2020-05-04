package com.yc.fresh.mapper;

import java.util.List;

import com.yc.fresh.entity.AddrInfo;

public interface IAddrInfoMapper {
	/**
	 * 添加收货地址信息
	 * @param af
	 * @return
	 */
	public int add(AddrInfo af);
	
	/**
	 * 修改默认地址
	 * @param anos
	 * @return
	 */
	public int updates(String[] anos);
	
	/**
	 * 根据收货地址编号设为默认地址
	 * @param ano
	 * @return
	 */
	public int update(String ano);
	
	/**
	 * 根据会员编号查询会员收货地址信息
	 * @param mno
	 * @return
	 */
	public List<AddrInfo> findByMno(int mno);
	
}
