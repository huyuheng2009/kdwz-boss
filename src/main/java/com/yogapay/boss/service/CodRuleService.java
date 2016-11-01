package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.CodRule;

@Service
public class CodRuleService {

	@Resource
	private BaseDao baseDao ;
	
    public long save(CodRule codRule) throws SQLException {
		return baseDao.insert("CodRule.insert", codRule) ;
	}
    
    public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("CodRule.list", pageInfo, params);
		return  page;
	}
    
    public  Map<String, Object> getById(Integer id){
		return baseDao.getById("CodRule.getById", id);
    }
	
    public void update(CodRule codRule) {
		baseDao.update("CodRule.update", codRule) ;
	}
    
    public  void delById(Integer id){
    	baseDao.delete("CodRule.delById", id) ;
    }
    
    
    public void status(CodRule codRule) {
		baseDao.update("CodRule.closeAll", codRule) ;
		baseDao.update("CodRule.status", codRule) ;
	}
    
}
