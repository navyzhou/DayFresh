package com.yc.fresh.service;

import com.yc.fresh.entity.OrderInfo;

public interface IOrderInfoService {
	public int add(OrderInfo of, String[] gnos, String mno);
}
