package com.yogapay.boss.service;

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
public class WagesService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		String nameSql = getNameSql() ;
		params.put("nameSql", nameSql) ;
		String nameSumSql = getNameSumSql() ;
		params.put("nameSumSql", nameSumSql) ;
		LgcConfig lgcConfig = Constants.getLgcConfig() ;
		params.put("reportExam", lgcConfig.getReportExam()) ;
		Map<String, String> iMap = getWagesBase() ;
		params.putAll(iMap);
		PageInfo<Map<String, Object>> page = baseDao.getByPage("Wages.list", pageInfo, params);
		return  page;
	}
	
	public List<Map<String, Object>> list(Map<String, String> params) {
		String nameSql = getNameSql() ;
		params.put("nameSql", nameSql) ;
		String nameSumSql = getNameSumSql() ;
		params.put("nameSumSql", nameSumSql) ;
		LgcConfig lgcConfig = Constants.getLgcConfig() ;
		params.put("reportExam", lgcConfig.getReportExam()) ;
		Map<String, String> iMap = getWagesBase() ;
		params.putAll(iMap);
		List<Map<String, Object>> list = baseDao.getList("Wages.list", params);
		return  list;
	}
	
	public Map<String, Object> listTotal(Map<String, String> params) {
		String nameSql = getNameSql() ;
		params.put("nameSql", nameSql) ;
		String nameSumSql = getNameSumSql() ;
		params.put("nameSumSql", nameSumSql) ;
		LgcConfig lgcConfig = Constants.getLgcConfig() ;
		params.put("reportExam", lgcConfig.getReportExam()) ;
		Map<String, String> iMap = getWagesBase() ;
		params.putAll(iMap);
		return  baseDao.getOne("Wages.listTotal", params);
	}
	

	public List<Map<String, Object>> listCostName(Map<String, String> params) {
		List<Map<String, Object>> list = baseDao.getList("Wages.listCostName",params);
		return  list;
	}
	
	public String getNameSql() {
		List<Map<String, Object>> list = baseDao.getList("Wages.listCostName");
		String sql = "" ;
		for (Map<String, Object>map:list) {
			if (Integer.valueOf(map.get("cost_io").toString())!=1) {
				sql +=",sum(IF(cc.cost_name='"+map.get("name")+"',cc.cost_amount,0))*-1 as "+map.get("name") ;
			}else {
				sql +=",sum(IF(cc.cost_name='"+map.get("name")+"',cc.cost_amount,0)) as "+map.get("name") ;
			}
		}
		sql +=",sum(cc.cost_amount) as cost_sum_amount" ;
		return  sql;
	}
	
	public String getNameSumSql() {
		List<Map<String, Object>> list = baseDao.getList("Wages.listCostName");
		String sql = "0" ;
		for (Map<String, Object>map:list) {
              sql+= "+"+map.get("name");
		}
		return  sql;
	}
	
	
	public Map<String, String> getWagesBase() {
        Map<String, String> pMap = new HashMap<String, String>() ;
        pMap.put("configType", "WAGES_BASE") ;
		List<Map<String, Object>> list =  baseDao.getList("LgcConfig.getByType",pMap);  
		Map<String, String> retMap = new HashMap<String, String>() ;
		for (Map<String, Object> map:list) {
			retMap.put("WAGES_BASE_"+map.get("config_name").toString(), map.get("config_value").toString()) ;
		}
		if ("NONE".equals(retMap.get("WAGES_BASE_FREIGHT"))||retMap.get("WAGES_BASE_FREIGHT")==null) {
			retMap.put("WAGES_BASE_FREIGHT", "0") ;
		}
		if ("NONE".equals(retMap.get("WAGES_BASE_WEIGHT"))||retMap.get("WAGES_BASE_WEIGHT")==null) {
			retMap.put("WAGES_BASE_WEIGHT", "0") ;
		}
		if ("NONE".equals(retMap.get("WAGES_BASE_COUNT"))||retMap.get("WAGES_BASE_COUNT")==null) {
			retMap.put("WAGES_BASE_COUNT", "0") ;
		}
		if (retMap.get("WAGES_BASE_COURIER_STATUS")==null) {
			retMap.put("WAGES_BASE_COURIER_STATUS", "1") ;
		}
		return  retMap;
	
	}
	

}
