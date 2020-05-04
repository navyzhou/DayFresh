package com.yc.fresh.mapper;

import com.yc.fresh.entity.OrderInfo;

public interface IOrderInfoMapper {
	/**
	 * 添加订单
	 * @param of
	 * @return
	 */
	public int add(OrderInfo of);
}
