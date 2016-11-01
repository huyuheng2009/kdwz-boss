package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.ValuationRule;

@Service
public class ValuationRuleService {

	@Resource
	private BaseDao baseDao ;
	
    public long save(ValuationRule valuationRule) throws SQLException {
		return baseDao.insert("ValuationRule.insert", valuationRule) ;
	}
    
    public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("ValuationRule.list", pageInfo, params);
		return  page;
	}
    
    public  Map<String, Object> getById(Integer id){
		return baseDao.getById("ValuationRule.getById", id);
    }
	
    public void update(ValuationRule valuationRule) {
		baseDao.update("ValuationRule.update", valuationRule) ;
	}
    
    public  void delById(Integer id){
    	baseDao.delete("ValuationRule.delById", id) ;
    }
    
    
    public void status(ValuationRule valuationRule) {
		baseDao.update("ValuationRule.closeAll", valuationRule) ;
		baseDao.update("ValuationRule.status", valuationRule) ;
	}
    
}
