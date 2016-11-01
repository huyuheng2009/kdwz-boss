package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.ManagerDao;

@Service
public class ManagerService {
	
	@Resource
	private ManagerDao managerDao ;
	
	public Map<String, Object> getPayType(String key) throws SQLException {
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("payCode", key) ;
		Map<String, Object> payTypeMap = managerDao.getOne("PayType.getByCode", params);
		return payTypeMap ;
	}
	
	public Map<String, Object> getLgcConfig(String key) throws SQLException {
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("lgcKey", key) ;
		Map<String, Object> lgcConfig = managerDao.getOne("Manager.getLgcConfig", params);
		return lgcConfig ;
	}
    
}
