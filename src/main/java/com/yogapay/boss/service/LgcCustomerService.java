package com.yogapay.boss.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.LgcCustomer;
import com.yogapay.boss.utils.DateUtils;

@Service
public class LgcCustomerService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<LgcCustomer> list(Map<String, String> params, Page pageInfo) {
		PageInfo<LgcCustomer> page = baseDao.getPage("LgcCustomer.list", pageInfo, params);
		return  page;
	}
	public List<LgcCustomer> getList(Map<String, String> params) {
		List<LgcCustomer> getList = baseDao.getList("LgcCustomer.list",params);
		return  getList;
	}
	
	public PageInfo<Map<String, Object>> huifang(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getPage("LgcCustomer.huifang", pageInfo, params);
		return  page;
	}
	
	public List<Map<String, Object>> huifang(Map<String, String> params) {
		List<Map<String, Object>> getList = baseDao.getList("LgcCustomer.huifang",params);
		return  getList;
	}
	
	public PageInfo<Map<String, Object>> report(Map<String, String> params, Page pageInfo) {
		Date nowDate = new Date() ;
		if (!params.containsKey("curDay")) {
			params.put("curDay", DateUtils.formatDate(nowDate,"yyyy-MM-dd")) ;
			params.put("curDayMonth", DateUtils.formatDate(nowDate,"yyyy-MM")) ;
		}else {
			params.put("curDayMonth", params.get("curDay").substring(0,7)) ;
		}
		if (!params.containsKey("curMonth")) {
			params.put("curMonth", DateUtils.formatDate(nowDate,"yyyy-MM")) ;
		}
		if (!params.containsKey("lastMonth")) {
			params.put("lastMonth", DateUtils.getLastMonth(nowDate)) ;
		}
		PageInfo<Map<String, Object>> page = baseDao.getPage("LgcCustomer.report", pageInfo, params);
		return  page;
	}
	
	public List<Map<String, Object>> report(Map<String, String> params) {
		Date nowDate = new Date() ;
		if (!params.containsKey("curDay")) {
			params.put("nowDate", DateUtils.formatDate(nowDate,"yyyy-MM-dd")) ;
		}
		if (!params.containsKey("curMonth")) {
			params.put("curMonth", DateUtils.formatDate(nowDate,"yyyy-MM")) ;
		}
		if (!params.containsKey("lastMonth")) {
			params.put("lastMonth", DateUtils.getLastMonth(nowDate)) ;
		}
		List<Map<String, Object>> getList = baseDao.getList("LgcCustomer.report",params);
		return  getList;
	}
	
	public void save(LgcCustomer lgcCustomer){
		baseDao.insert("LgcCustomer.insert", lgcCustomer);
	}
	
	public void saveIfNotExsit(LgcCustomer lgcCustomer){
		if (!isExsit(lgcCustomer.getConcat_phone())) {
			baseDao.insert("LgcCustomer.insert", lgcCustomer);
		}
	}
	
public LgcCustomer getById(Integer id) {
	return baseDao.getById("LgcCustomer.getById", id) ;
}

public LgcCustomer getByCancatPhone(String  concatPhone) {
	Map<String, String> pMap = new HashMap<String, String>() ;
	pMap.put("concatPhone", concatPhone) ;
	return baseDao.getOne("LgcCustomer.getByCancatPhone", pMap) ;
}

	public int update(LgcCustomer lgcCustomer){
		return baseDao.update("LgcCustomer.update", lgcCustomer);
	}
	
	public int delById(Integer id){
		return baseDao.delete("LgcCustomer.delById", id);
	}

	  public boolean isExsit(String concatPhone) {
		  LgcCustomer map = getByCancatPhone(concatPhone) ;
	        boolean flag = true;
	        if (map == null) {
	            flag = false;
	        }
	        return flag;
	    }
	public void hfsave(int id, String huifang_text) {
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("cid", id) ;
		pMap.put("huifang_text", huifang_text) ;
		pMap.put("create_time", DateUtils.formatDate(new Date())) ;
		baseDao.insert("LgcCustomer.hfsave", pMap);
	}

}
