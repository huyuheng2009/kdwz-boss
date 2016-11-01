package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.City;

@Service
public class ManagerLgcConfigService {
	@Resource
	private BaseDao baseDao;
	@Resource
	private ManagerDao managerDao ;
	
	public Map<String, Object> getByLgcNo(String lgcNo) {
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("lgcNo", lgcNo) ;
		return managerDao.getOne("ManagerLgcConfig.getByLgcNo", pMap);
	}
	
	public int updateKeyAddr(String lgcNo,String lgc_key,String web_xd_addr) {
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("lgcNo", lgcNo) ;
		pMap.put("lgc_key", lgc_key) ;
		pMap.put("web_xd_addr", web_xd_addr) ;
		return managerDao.update("ManagerLgcConfig.updateKeyAddr", pMap) ;
	}
	
}
