package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.MonthSettleType;

@Service
public class MonthSettleTypeService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("MonthSettleType.list", pageInfo, params);
		return  page;
	}
	
	public MonthSettleType getById(Integer id){
		MonthSettleType monthSettleType = baseDao.getById("MonthSettleType.getById", id);
		return monthSettleType;
	}
	
	
	public int saveMonthSettleType(MonthSettleType monthSettleType){
		return baseDao.insert("MonthSettleType.insert", monthSettleType);
	}
	
	public int updateMonthSettleType(MonthSettleType monthSettleType){
		return baseDao.update("MonthSettleType.update", monthSettleType);
	}


	public int delById(Integer id){
		return baseDao.delete("MonthSettleType.delById", id);
	}


}
