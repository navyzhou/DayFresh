package com.yc.fresh.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yc.fresh.entity.OrderInfo;
import com.yc.fresh.mapper.IGoodsInfoMapper;
import com.yc.fresh.mapper.IOrderInfoMapper;
import com.yc.fresh.mapper.IOrderItemInfoMapper;
import com.yc.fresh.service.ICartInfoService;
import com.yc.fresh.service.IOrderInfoService;
import com.yc.fresh.util.StringUtil;

@Service
public class OrderInfoServiceImpl implements IOrderInfoService{
	@Autowired
	private ICartInfoService cartService;
	
	@Autowired
	private IGoodsInfoMapper goodsMapper;
	
	@Autowired
	private IOrderInfoMapper orderMapper;
	
	@Autowired
	private IOrderItemInfoMapper itemMapper;
	
	
	@Transactional
	@Override
	public int add(OrderInfo of, String[] gnos, String mno) {
		if (StringUtil.checkNull(of.getOno(), of.getAno())) {
			return -2;
		}
		
		String cnos = of.getOno(); // 获取要下单的购物车编号
		String ono = new Date().getTime() + "" + new Random().nextInt(10000); // 生成订单
		of.setOno(ono);
		
		int result = -1;
		result = orderMapper.add(of); // 添加订单
		
		// 添加订单详细
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ono", ono);
		map.put("cnos", cnos.split(","));
		
		result = itemMapper.add(map);
		
		// 修改库存
		result = goodsMapper.updateStore(cnos.split(","));
		
		// 删除购物车
		result = cartService.delete(cnos.split(","), gnos, mno);
		
		return result;
	}

}
