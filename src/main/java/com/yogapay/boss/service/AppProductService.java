package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.AppProduct;
import com.yogapay.boss.domain.User;

@Service
public class AppProductService {
	@Resource
	private BaseDao baseDao ;
	@Resource
	private ManagerDao managerDao ;

	public PageInfo<Map<String, Object>> list(Map<String, String> params,Page pageInfo) {
		PageInfo<Map<String, Object>> page = managerDao.getByPage("AppProduct.list", pageInfo, params);
		return page;
	}

    public int saveAppProduct(AppProduct appProduct) {
    	return managerDao.insert("AppProduct.insert", appProduct) ;
	}
	
    
    public int status(AppProduct appProduct) {
    	return managerDao.update("AppProduct.status", appProduct) ;
    }
    
    public int updateAppProduct(AppProduct appProduct) {
    	return managerDao.update("AppProduct.update", appProduct) ;
    }
    
    public int delById(Integer id){
		return managerDao.delete("AppProduct.delById", id);
	}

	public AppProduct getById(int id) {
		return managerDao.getById("AppProduct.getById", id);
	}
    
	public AppProduct getByCode(int appCode) {
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("appCode", appCode) ;
		return managerDao.getOne("AppProduct.getByCode", params);
	}
	
}
