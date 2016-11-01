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
import com.yogapay.boss.domain.ForwardOrder;
import com.yogapay.boss.domain.ProOrder;
import com.yogapay.boss.domain.PushMsg;
import com.yogapay.boss.utils.StringUtil;

@Service
public class ForOrderService {

	@Resource
	private BaseDao baseDao ;
	
	
    public long save(ForwardOrder forwardOrder) throws SQLException {
    	delByOrderNo(forwardOrder.getOrderNo());
		return baseDao.update("ForOrder.save", forwardOrder) ;
	}


	public PageInfo<Map<String, Object>> list(Map<String, String> params,Page pageInfo) {
		if (!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo"))||!StringUtil.isEmptyWithTrim(params.get("orderNo"))) {
			params.remove("createTimeBegin") ;
			params.remove("createTimeEnd") ;
		}
		return baseDao.getByPage("ForOrder.list", pageInfo,params) ;
	}
	
	public List<Map<String, Object>> list(Map<String, String> params) {
		if (!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo"))||!StringUtil.isEmptyWithTrim(params.get("orderNo"))) {
			params.remove("createTimeBegin") ;
			params.remove("createTimeEnd") ;
		}
		return baseDao.getList("ForOrder.list",params) ;
	}
	
    
	 public void delByOrderNo(String orderNo) throws SQLException {
	    	Map<String, Object> params =  new HashMap<String, Object>() ;
	    	params.put("orderNo", orderNo) ;
			baseDao.deleteByParams("ForOrder.delByOrderNo", params) ;
		}
	 
	 public Map<String, Object> getByOrderNo(String orderNo) throws SQLException {
	    	Map<String, Object> params =  new HashMap<String, Object>() ;
	    	params.put("orderNo", orderNo) ;
			return baseDao.getOne("ForOrder.getByOrderNo", params) ;
		}
	
}
