package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;


@Service
public class OrderAllotService {
	@Resource
	private BaseDao baseDao;
	/**
	 * 查询分配信息
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> getOrderList(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("OrderAllot.getOrderList", pageInfo, params);
	}
	public List<Map<String, Object>> getOrderList(Map<String, String> params) {
		return baseDao.getList("OrderAllot.getOrderList", params);
	}


	public boolean checkOrderPage(Map<String, String> params){
		Map<String,Object> map = baseDao.getFrist("OrderAllot.checkOrderPage", params);
		if(map==null){
			return false;
		}
		return true;
	}	
	public void saveOrderPage(Map<String, String> params){
		baseDao.update("OrderAllot.saveOrderPage", params);
	}


	public PageInfo<Map<String, Object>> getAllotDetailList(
			Map<String, String> params, Page pageInfo) {
		return baseDao.getByPage("OrderAllot.getAllotDetailList", pageInfo, params);
	}	
	/**
	 * 停用票段
	 * @param params
	 */
	public void stopAllotOrder(String id) {
		 baseDao.update("OrderAllot.stopAllotOrder", id);
	}	
	/**
	 * 启用票段
	 * @param params
	 */
	public void startAllotOrder(String id) {
		baseDao.update("OrderAllot.startAllotOrder", id);
	}


	public void deleteOrder(String id) {
		baseDao.update("OrderAllot.deleteOrder", id);
		
	}	
}
