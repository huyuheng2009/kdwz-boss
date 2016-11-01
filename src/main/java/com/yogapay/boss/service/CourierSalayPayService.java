package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.CourierSalayPay;

@Service
public class CourierSalayPayService {
	@Resource
	private BaseDao baseDao ;
	
	public int insert(CourierSalayPay params){
		return baseDao.insert("CourierSalayPay.insert", params);
	}
	
	public List<CourierSalayPay> query(Map<String, String> params){
		return baseDao.getList("CourierSalayPay.query", params);
	}
	
	public List<Map<String,Object>> queryCountSalayPay(Map<String, String> params){
		return baseDao.getList("CourierSalayPay.countSalary", params);
	}
	public List<CourierSalayPay> batchQuery(Map<String, String> params){
		return baseDao.getList("CourierSalayPay.batchQuery", params);
	}
}
