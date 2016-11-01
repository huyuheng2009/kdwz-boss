package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.FranchiseRule;
import com.yogapay.boss.domain.FreightRule;
import com.yogapay.boss.utils.StringUtils;

@Service
public class FreightRuleService {
	@Resource
	private BaseDao baseDao ;

	public PageInfo<Map<String, Object>> listRule(Map<String, String> params, Page pageInfo) throws SQLException {
		PageInfo<Map<String, Object>> page  = baseDao.getByPage("FreightRule.list", pageInfo,params) ;
		return page;
	}
	
	public List<Map<String, Object>> getlistRule(Map<String, String> params) throws SQLException {
		List<Map<String, Object>> list  = baseDao.getList("FreightRule.list",params) ;
		return list;
	}
	
	public Map<String, Object> getById(Map<String, String> params) throws SQLException {
		Map<String, Object> map  = baseDao.getOne("FreightRule.getById",params) ;
		return map;
	}
	
	public void saveRule(FreightRule rule) throws SQLException {
		baseDao.insert("FreightRule.saveRule", rule) ;
	}

	public void batchTtype(List<Map<String, Object>> ttype) throws SQLException {
		baseDao.insert("FreightRule.batchTtype", ttype) ;
	}
	
	
	public void batchItype(List<Map<String, Object>> itype) throws SQLException {
		baseDao.insert("FreightRule.batchItype", itype) ;
	}
	
	public List<Map<String, Object>> getRuleTtype(boolean select,String rid) throws SQLException {
		List<Map<String,Object>> list  = baseDao.getList("AgingType.list") ;
		if (select) {
			Map<String, String> map = new HashMap<String, String>() ;
			map.put("rid", rid) ;
			List<Map<String,Object>> list2 = baseDao.getList("FreightRule.getRuleTtype",map) ;
			for(Map<String,Object> mp:list2){
				Object time_type = mp.get("time_type");
				for(Map<String,Object> mmp:list){
					Object item_text =mmp.get("item_text");
					if(item_text.equals(time_type)){
						mmp.put("checked", "show");
					}else{
						if(!mmp.containsKey("checked")){
							mmp.put("checked", "hiden");
						}
					}
				}
			}
		}
		return list;
	}
	
	
	
	public List<Map<String, Object>> getRuleItype(boolean select,String rid) throws SQLException {
		List<Map<String,Object>> list  = baseDao.getList("ItemType.list") ;
		if (select) {
			Map<String, String> map = new HashMap<String, String>() ;
			map.put("rid", rid) ;
			List<Map<String,Object>> list2 = baseDao.getList("FreightRule.getRuleItype",map) ;
			for(Map<String,Object> mp:list2){
				Object itype = mp.get("itype");
				for(Map<String,Object> mmp:list){
					Object item_text =mmp.get("item_text");
					if(item_text.equals(itype)){
						mmp.put("checked", "show");
					}else{
						if(!mmp.containsKey("checked")){
							mmp.put("checked", "hiden");
						}
					}
				}
			}
		}
		return list;
	}

	public void delById(Integer id) {
		baseDao.delete("FreightRule.delById", id) ;
		baseDao.delete("FreightRule.delTtypeById", id) ;
		baseDao.delete("FreightRule.delItypeById", id) ;
	}
	
	
	public double freightCalculate(String time_type ,String item_type,double weight,double distance) {
		FreightRule rule = getFirstRule(time_type, item_type) ;
		return getRuleSettle(rule, weight, distance) ;
	}
	
	
	public FreightRule getFirstRule(String timeType,String itype) {
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("timeType", timeType) ;
		pMap.put("itype", itype) ;
		return baseDao.getFrist("FreightRule.getRuleByParams", pMap) ;
	}

	public double getRuleSettle(FreightRule rule,double weight,double distance) {
		double ret = 0 ;
		double w = 0 ;
	   if (rule==null||weight<0.01||distance<0.01) {
		return 0 ;
	   } 
	   ret = rule.getFmoney()+rule.getVpay() ;
	   
	   double rf_distance = rule.getFdistance() ;  
	   double rf_weight = rule.getFweight() ;
	   double by_distance = distance-rf_distance ;
	   double by_weight = weight-rf_weight ;
	   
	   double dmoney = 0 ;
	   double wmoney = 0 ;
	   if (by_distance>0.01) {
		  int step = (int) Math.ceil(by_distance/rule.getStep_distance()) ;
		  dmoney = step*rule.getStep_distance_money() ;
	   }
	   if (by_weight>0.01) {
			  int step = (int) Math.ceil(by_weight/rule.getStep_weight()) ;
			  wmoney = step*rule.getStep_weight_money() ;
	   }
	    ret = ret + dmoney + wmoney ;
		return ret ;
	}
	
	

}