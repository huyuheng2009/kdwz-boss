package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.SubstationAccount;

/**
 * 月结客户service
 * @author 
 *
 */
@Service
public class SubstationAccountService {
	
	@Resource
	private BaseDao baseDao;


	public int saveSubstationAccount(SubstationAccount substationAccount){
		return baseDao.insert("SubstationAccount.insert", substationAccount);
	}
	
	public int updateSubstationAccount(SubstationAccount substationAccount){
		return baseDao.update("SubstationAccount.update", substationAccount);
	}
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("SubstationAccount.list", pageInfo, params);
		return  page;
	}

	public void examine(Map<String, String> params) {
	baseDao.update("SubstationAccount.examine", params) ;
	}
	
	public Map<String, Object> getById(Integer id) {
		return baseDao.getById("SubstationAccount.getById", id) ;
	}
	
	

}
