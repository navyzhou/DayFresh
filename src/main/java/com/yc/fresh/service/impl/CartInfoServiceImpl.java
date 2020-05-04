package com.yc.fresh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.fresh.entity.CartInfo;
import com.yc.fresh.mapper.ICartInfoMapper;
import com.yc.fresh.redis.dao.IRedisDao;
import com.yc.fresh.service.ICartInfoService;

@Service
public class CartInfoServiceImpl implements ICartInfoService{
	@Autowired
	private ICartInfoMapper mapper;

	@Autowired
	private IRedisDao redisDao;

	@Override
	public int add(CartInfo cf) {
		// 添加到redis缓存中
		redisDao.hashPut(String.valueOf(cf.getMno()), "G" + cf.getGno(), 1);
		return mapper.add(cf);
	}

	@Override
	public Map<Object, Object> findByMno(int mno) {
		// 先访问redis缓存
		if(!redisDao.hashHasKey(String.valueOf(mno), "*")) { // 说明没有命中缓存
			// 查数据库
			List<CartInfo> cfs = mapper.findByMno(mno);
			if (cfs != null && !cfs.isEmpty()) { // 缓存到Object中
				Map<Object, Object> map = new HashMap<Object, Object>();
				for (CartInfo cf : cfs) {
					map.put("G" + cf.getGno(), cf.getNum());
				}
				redisDao.hashPutAll(String.valueOf(mno), map);
			}
		}
		return redisDao.hashEntries(String.valueOf(mno));
	}

	@Override
	public List<CartInfo> finds(int mno) {
		return mapper.finds(mno);
	}

	@Override
	public int delete(String[] cnos, String[] gnos, String mno) {
		// 删除redis
		for (String gno : gnos) {
			redisDao.hashDelete(mno, "G"+gno);
		}
		return mapper.delete(cnos);
	}

	@Override
	public int deleteByCno(String cno) {
		return mapper.deleteByCno(cno);
	}

	@Override
	public int update(CartInfo cf) {
		// 修改redis中的值
		redisDao.hashIncrement(String.valueOf(cf.getMno()), "G"+cf.getGno(), cf.getNum());
		return mapper.updates(cf);
	}

	@Override
	public List<CartInfo> findByCnos(String[] cnos) {
		return mapper.findByCnos(cnos);
	}
}
