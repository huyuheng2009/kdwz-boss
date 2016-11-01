package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.ProDealHistory;

@Service
public class ProDealHistoryService {
	@Resource
	private BaseDao baseDao;
	
	
	public void save(ProDealHistory proDealHistory) {
		baseDao.insert("ProDealHistory.insert", proDealHistory) ;
	}

	public PageInfo<Map<String, Object>> list(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ProDealHistory.list", pageInfo, params);
	}


	
}
