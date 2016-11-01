package com.yogapay.boss.service;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;

@Service
public class LgcWeightConfigService {
	@Resource
	private BaseDao baseDao;

	public int update(Map<String, String> params) {
		return baseDao.update("WeightConfig.update", params);
	}

	public int insert(Map<String, String> params) {
		return baseDao.update("WeightConfig.insert", params);
	}
	
	public Map<String,Object>  selectOne(Map<String, String> params) {
		return baseDao.getOne("WeightConfig.selectOne", params);
	}
}
