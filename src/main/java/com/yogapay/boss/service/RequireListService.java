package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;

@Service
public class RequireListService {
	@Resource
	private BaseDao baseDao;
	
	
public List<Map<String, Object>> getByTid(Integer id,String	viewType) {
	Map<String, Object> pMap = new HashMap<String, Object>() ;
	pMap.put("id", id) ;
	pMap.put("viewType", viewType) ;
	return baseDao.getList("RequireList.getByTid", pMap) ;
}

public List<Map<String, Object>> getByCode(String code,String	viewType) {
	Map<String, Object> pMap = new HashMap<String, Object>() ;
	pMap.put("code", code) ;
	pMap.put("viewType", viewType) ;
	return baseDao.getList("RequireList.getByCode", pMap) ;
}

	public void updateRequireCheckBox(String ids,String tid){
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("required", "N") ;
		params.put("tid", tid) ;
		params.put("viewType", "CHECKBOX") ;
	    baseDao.update("RequireList.update", params);
	    Map<String, String> params1 = new HashMap<String, String>() ;
	    params1.put("required", "Y") ;
		params1.put("ids", ids) ;
	    baseDao.update("RequireList.update", params1);
	}
	
	public void updateRequireByName(String tid,String name,String value){
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("tid", tid) ;
		params.put("name", name) ;
		params.put("required", value) ;
	    baseDao.update("RequireList.update", params);
	}


}
