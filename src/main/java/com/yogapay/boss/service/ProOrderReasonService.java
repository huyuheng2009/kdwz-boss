package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.ProOrderReason;

@Service
public class ProOrderReasonService {
	@Resource
	private BaseDao baseDao;
	
	
	public void save(ProOrderReason proOrderReason) {
		baseDao.insert("ProOrderReason.insert", proOrderReason) ;
	}

	public PageInfo<Map<String, Object>> list(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ProOrderReason.list", pageInfo, params);
	}

	public Map<String, Object> getById(int id) {
		// TODO Auto-generated method stub
		return baseDao.getById("ProOrderReason.getById", id);
	}
	
	public int update(ProOrderReason proOrderReason) {
		return baseDao.update("ProOrderReason.update", proOrderReason) ;
	}
	
	public int delById(long id) {
		// TODO Auto-generated method stub
		return baseDao.deleteByParams("ProOrderReason.delete", id) ;
	}
	
	public int pstop(String id) {
		return baseDao.deleteByParams("ProOrderReason.pstop", id) ;
	}
	public int pstart(String id) {	
		return baseDao.deleteByParams("ProOrderReason.pstart", id) ;
	}
	
}
