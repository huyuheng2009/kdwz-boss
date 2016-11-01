package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;

@Service
public class LgcConfigService {
	@Resource
	private BaseDao baseDao;
	
	public Map<String, Object> getByType(String configType) {
        Map<String, String> pMap = new HashMap<String, String>() ;
        pMap.put("configType", configType) ;
		List<Map<String, Object>> list =  baseDao.getList("LgcConfig.getByType",pMap);  
		Map<String, Object> retMap = new HashMap<String, Object>() ;
		for (Map<String, Object> map:list) {
			retMap.put(map.get("config_name").toString(), map.get("config_value")) ;
		}
		return  retMap;
	}
	public void updateByTypeName(String	type,String name,String value) {
		 Map<String, String> pMap = new HashMap<String, String>() ;
	        pMap.put("configType", type) ;
	        pMap.put("configName", name) ;
	        pMap.put("configValue", value) ;
		baseDao.update("LgcConfig.updateByTypeName", pMap) ;
	}
	

 public boolean validateLgcOrderNo(String lgcOrderNo) {
	Map<String, Object> configMap = getByType("LGC_ORDER_NO") ;
	int MIN_LENGTH = Integer.valueOf(configMap.get("MIN_LENGTH").toString()) ;
	int MAX_LENGTH = Integer.valueOf(configMap.get("MAX_LENGTH").toString()) ;
	String CONSTATUTE = configMap.get("CONSTATUTE").toString() ;
	if (lgcOrderNo.length()<MIN_LENGTH||lgcOrderNo.length()>MAX_LENGTH) {
		return false ;
	}
	if ("NO_ONLY".equals(CONSTATUTE)) {
		String reg = "^[0-9]+$" ;
		if (!Pattern.compile(reg).matcher(lgcOrderNo).find()) {
			return false ;
		}
	}
	
	return true ;
}	
	
	


}
