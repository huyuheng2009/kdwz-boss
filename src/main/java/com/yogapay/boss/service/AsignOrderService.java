package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.AsignOrder;

@Service
public class AsignOrderService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<AsignOrder> list(Map<String, String> params, Page pageInfo) {
		PageInfo<AsignOrder> page = baseDao.getPage("AsignOrder.list", pageInfo, params);
		return  page;
	}
	public List<AsignOrder> getList() {
		List<AsignOrder> list = baseDao.getList("AsignOrder.list");
		return  list;
	}
	
	public PageInfo<Map<String, Object>> monitorList(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getPage("AsignOrder.monitorList", pageInfo, params);
		return  page;
	}
	
	public List<Map<String, Object>> monitorList(Map<String, String> params) {
		List<Map<String, Object>> page = baseDao.getList("AsignOrder.monitorList", params);
		return  page;
	}
	
	public List<Map<String, Object>> monitorDetailList(Map<String, String> params) {
		List<Map<String, Object>> page = baseDao.getList("AsignOrder.monitorDetailList", params);
		return  page;
	}
	
	public List<Map<String, Object>> monitorTakeDetailList(Map<String, String> params) {
		List<Map<String, Object>> page = baseDao.getList("AsignOrder.monitorTakeDetailList", params);
		return  page;
	}
	
	public PageInfo<Map<String, Object>> takeMonitor(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getPage("AsignOrder.takeMonitor", pageInfo, params);
		return  page;
	}
	
	public List<Map<String, Object>> takeMonitor(Map<String, String> params) {
		List<Map<String, Object>> page = baseDao.getList("AsignOrder.takeMonitor", params);
		return  page;
	}
	
	
	public Map<String, Object> monitorTotal(Map<String, String> params) {
		Map<String, Object> total = baseDao.getOne("AsignOrder.monitorTotal", params);
		return  total;
	}
	
	
	public void save(AsignOrder asignOrder){
		baseDao.delete("AsignOrder.delByOrderNo", asignOrder.getOrder_no());
		baseDao.insert("AsignOrder.insert", asignOrder);
	}
	
public AsignOrder getById(Integer id) {
	return baseDao.getById("AsignOrder.getById", id) ;
}

	public int update(AsignOrder asignOrder){
		return baseDao.update("AsignOrder.update", asignOrder);
	}
	
	public int delById(Integer id){
		return baseDao.delete("AsignOrder.delById", id);
	}
	
	public int delByOrderNo(String orderNo){
		return baseDao.delete("AsignOrder.delByOrderNo", orderNo);
	}



}
