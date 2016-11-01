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
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.utils.StringUtil;

@Service
public class OrderTrackService {
	
    @Resource
    private BaseDao baseDao ;
	
	public void add(Map<String, String> track) throws SQLException {
        baseDao.insert("OrderTrack.save", track) ;
	}
	
	public void add(OrderTrack orderTrack) throws SQLException {
        baseDao.insert("OrderTrack.save1", orderTrack) ;
	}

	public void delByOrderNo(OrderTrack orderTrack) throws SQLException {
        baseDao.deleteByParams("OrderTrack.delByOrderNo", orderTrack) ;
	}
	
	
	public PageInfo<Map<String, Object>> scanList(Map<String, String> params, Page pageInfo) {
		if (!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo"))||!StringUtil.isEmptyWithTrim(params.get("orderNo"))) {
			params.remove("createTimeBegin") ;
			params.remove("createTimeEnd") ;
		}
		return baseDao.getByPage("OrderTrack.scanList", pageInfo, params) ;
	}
	
	public PageInfo<Map<String, Object>> getSignScan(Map<String, String> params, Page pageInfo) {
		return baseDao.getByPage("OrderTrack.getSignScan", pageInfo, params) ;
	}
	
	public List<Map<String, Object>> getSignScan(Map<String, String> params) {
		return baseDao.getList("OrderTrack.getSignScan", params) ;
	}
	
	public OrderTrack getLastOrderTrack(String orderNo) {
		Map<String, Object> pMap = new HashMap<String, Object>() ;
        pMap.put("orderNo", orderNo) ;
		return baseDao.getOne("OrderTrack.getLastOrderTrack",pMap) ;
	}
	
	
	//用户端
	public List<Map<String, Object>> getByOrderNo(String orderNo,boolean orderby) throws SQLException {
        Map<String, Object> pMap = new HashMap<String, Object>() ;
        pMap.put("orderNo", orderNo) ;
        if (orderby) {
          pMap.put("orderby", "1") ;
		}
		return baseDao.getList("OrderTrack.getByOrderNo", pMap) ;
	}

	public void unLastTrack(OrderTrack lastOrderTrack) {
	   baseDao.update("OrderTrack.unLastTrack", lastOrderTrack) ;
	}

    
}
