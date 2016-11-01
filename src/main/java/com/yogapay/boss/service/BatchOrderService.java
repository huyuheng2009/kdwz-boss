/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.service;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.BatchOrderAddr;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class BatchOrderService {

	@Resource
	private BaseDao baseDao;

	public List<BatchOrderAddr> getOrderAddrList(String userName, String ids, String comomonName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userName", userName);
		param.put("ids", ids);
		param.put("commonName", comomonName);
		return this.baseDao.getList("BatchOrder.queryAddr", param);
	}
	

	public List<BatchOrderAddr> queryAddrImport(String userName, String ids, String comomonName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userName", userName);
		param.put("ids", ids);
		param.put("commonName", comomonName);
		return this.baseDao.getList("BatchOrder.queryAddrImport", param);
	}

	public BatchOrderAddr queryById(int id) {
		return (BatchOrderAddr) this.baseDao.getOne("BatchOrder.queryone", id);
	}
	
	public BatchOrderAddr queryImportById(int id) {
		return (BatchOrderAddr) this.baseDao.getOne("BatchOrder.queryImportone", id);
	}
	
	public BatchOrderAddr queryExitsByNum(Map<String,Object> map) {
		return (BatchOrderAddr) this.baseDao.getOne("BatchOrder.queryExitsByNum", map);
	}

	public void addOrderAddr(BatchOrderAddr addr) {		
		this.baseDao.insert("BatchOrder.addOrderAddr", addr);
	}
	
	public void addOrderImportAddr(BatchOrderAddr addr) {
		this.baseDao.insert("BatchOrder.addOrderImportAddr", addr);
	}

	public void addOrderMuchAddr(String userName, String uid, List<Map<String, Object>> list) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userName", userName);
		param.put("uid", uid);
		param.put("list", list);
		this.baseDao.insert("BatchOrder.addOrderMuchAddr", param);
	}

	public void addOrderMuchAddrImport(String userName, List<BatchOrderAddr> list) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userName", userName);
		param.put("list", list);
		this.baseDao.insert("BatchOrder.addOrderMuchAddrImport", param);
	}
	
	public void addOrderBatchImport(String userName, List<BatchOrderAddr> list) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userName", userName);
		param.put("list", list);
		this.baseDao.insert("BatchOrder.addOrderImport", param);
	}

	public void delOrderAddr(String ids) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		this.baseDao.deleteByParams("BatchOrder.delOrderAddrById", param);
	}
	
	public void delOrderAddrImport(String ids) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		this.baseDao.deleteByParams("BatchOrder.delOrderAddrImportById", param);
	}

	public void updateOrderAddr(BatchOrderAddr orderAddr) {
		this.baseDao.update("BatchOrder.updateOrderAddr", orderAddr);
	}
	
	public void updateOrderImportAddr(BatchOrderAddr orderAddr) {
		this.baseDao.update("BatchOrder.updateOrderAddrImport", orderAddr);
	}

	public void del(String userName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userName", userName);
		this.baseDao.deleteByParams("BatchOrder.del", param);
	}
	
	public void delImport(String userName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userName", userName);
		this.baseDao.deleteByParams("BatchOrder.delImport", param);
	}

	public void delAll() {
		this.baseDao.deleteByParams("BatchOrder.delAll", "");
	}
}
