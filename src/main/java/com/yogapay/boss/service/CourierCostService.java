package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.CourierCost;

@Service
public class CourierCostService {
	@Resource
	private BaseDao baseDao;
	
	
	public void save(CourierCost courierCost){
		baseDao.insert("CourierCost.insert", courierCost);
	}
	
	
	public void batchSave(List<CourierCost> courierCosts){
		if (courierCosts!=null&&courierCosts.size()>0) {
			baseDao.insert("CourierCost.batchInsert", courierCosts);
		}
	}
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		String nameSql = getNameSql() ;
		params.put("nameSql", nameSql) ;
		PageInfo<Map<String, Object>> page = baseDao.getByPage("CourierCost.list", pageInfo, params);
		return  page;
	}
	
	public List<Map<String, Object>> list(Map<String, String> params) {
		String nameSql = getNameSql() ;
		params.put("nameSql", nameSql) ;
		List<Map<String, Object>> list = baseDao.getList("CourierCost.list", params);
		return  list;
	}
	
	public Map<String, Object> getByBid(String batch_id) {
		Map<String, String> params = new HashMap<String, String>() ;
		String nameSql = getNameSql() ;
		params.put("nameSql", nameSql) ;
		params.put("batch_id", batch_id) ;
		return  baseDao.getFrist("CourierCost.getByBid", params);
	}

	public boolean  isExist(CourierCost courierCost) {
		boolean flag = false ;
	  Map<String, Object>	map =  baseDao.getFrist("CourierCost.getByCm", courierCost);
	  if (map!=null) {
		 flag = true ;
	  }
	  return flag ;
	}
	
	
	public void deleteByBid(String batch_id){
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("batch_id", batch_id) ;
		baseDao.deleteByParams("CourierCost.deleteByBid", pMap);
	}

	
	public String getNameSql() {
		List<Map<String, Object>> list = baseDao.getList("Wages.listCostName");
		String sql = "" ;
		for (Map<String, Object>map:list) {
			if (Integer.valueOf(map.get("cost_io").toString())!=1) {
				sql +=",sum(IF(cc.cost_name='"+map.get("name")+"',cc.cost_amount,0)) as "+map.get("name") ;
			}else {
				sql +=",sum(IF(cc.cost_name='"+map.get("name")+"',cc.cost_amount,0)) as "+map.get("name") ;
			}
		}
		return  sql;
	}
	
	
}
