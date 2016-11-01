package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;

@Service
public class SubstationDayStaticService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params,Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("SubstationDayStatic.list", pageInfo, params);
		return page;
	}
	public List<Map<String, Object>> getListExport(Map<String, String> params) {
		List<Map<String, Object>> list = baseDao.getList("SubstationDayStatic.list", params);
		return list;
	}
	public List<Map<String, Object>> substationDayAcount(Map<String, Object> params) {
		List<Map<String, Object>> list = baseDao.getList("SubstationDayStatic.substationDayAcount", params);
		return list;
	}
	public void save(Map<String,Object> map) {
		baseDao.insert("SubstationDayStatic.save", map) ;
	}
	
}
