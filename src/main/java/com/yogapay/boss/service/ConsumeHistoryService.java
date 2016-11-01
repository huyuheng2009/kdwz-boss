package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.ConsumeHistory;
import com.yogapay.boss.domain.RechargeHistory;

@Service
public class ConsumeHistoryService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("ConsumeHistory.list", pageInfo, params);
		return  page;
	}
	

	public int save(ConsumeHistory consumeHistory){
		return baseDao.insert("ConsumeHistory.insert", consumeHistory);
	}
	

}
