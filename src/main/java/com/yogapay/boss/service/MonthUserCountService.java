package com.yogapay.boss.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.utils.Constants;

@Service
public class MonthUserCountService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
		PageInfo<Map<String, Object>> page = baseDao.getByPage("MonthUserCount.list", pageInfo, params);
		return  page;
	}
	
	public List<Map<String, Object>> list(Map<String, String> params) {
		LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
		List<Map<String, Object>> page = baseDao.getList("MonthUserCount.list", params);
		return  page;
	}
	
	public PageInfo<Map<String, Object>> mmlist(Map<String, String> params, Page pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>(params) ;
		if (map.containsKey("substationNo")) {
			String[] sno = map.get("substationNo").toString().replace("'", "").split(",") ;
			map.put("sno", sno) ;
		}
		PageInfo<Map<String, Object>> page = baseDao.getByPage("MonthUserCount.mmlist", pageInfo, map);
		return  page;
	}
	
	public List<Map<String, Object>> mmlist(Map<String, String> params) {
		Map<String, Object> map = new HashMap<String, Object>(params) ;
		if (map.containsKey("substationNo")) {
			String[] sno = map.get("substationNo").toString().replace("'", "").split(",") ;
			map.put("sno", sno) ;
		}
		List<Map<String, Object>> page = baseDao.getList("MonthUserCount.mmlist", map);
		return  page;
	}
	
	public PageInfo<Map<String, Object>> cmlist(Map<String, String> params, Page pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>(params) ;
		if (map.containsKey("substationNo")) {
			String[] sno = map.get("substationNo").toString().replace("'", "").split(",") ;
			map.put("sno", sno) ;
		}
		PageInfo<Map<String, Object>> page = baseDao.getByPage("MonthUserCount.cmlist", pageInfo, map);
		return  page;
	}
	
	
	public List<Map<String, Object>> cmlist(Map<String, String> params) {
		Map<String, Object> map = new HashMap<String, Object>(params) ;
		if (map.containsKey("substationNo")) {
			String[] sno = map.get("substationNo").toString().replace("'", "").split(",") ;
			map.put("sno", sno) ;
		}
		List<Map<String, Object>> page = baseDao.getList("MonthUserCount.cmlist", map);
		return  page;
	}
}
