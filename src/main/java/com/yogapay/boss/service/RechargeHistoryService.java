package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.RechargeHistory;

@Service
public class RechargeHistoryService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("RechargeHistory.list", pageInfo, params);
		return  page;
	}
	

	public int save(RechargeHistory disUser){
		return baseDao.insert("RechargeHistory.insert", disUser);
	}
	
	public int delById(Integer id){
		return baseDao.delete("RechargeHistory.delById", id);
	}


}
