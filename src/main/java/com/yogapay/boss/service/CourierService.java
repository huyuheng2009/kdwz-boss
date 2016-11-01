package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.Courier;

@Service
public class CourierService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("Courier.list", pageInfo, params);
		return  page;
	}
	
	public List<Map<String, Object>> alist(Map<String, String> params) {
		return  baseDao.getList("Courier.list", params) ;
	}
	
	
	public PageInfo<Map<String, Object>> asignList(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("Courier.asignList", pageInfo, params);
		return  page;
	}
	
	public PageInfo<Map<String, Object>> ccount(Map<String, String> params,
			Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("Courier.ccount", pageInfo, params);
		return page;
	}
	public PageInfo<Map<String, Object>> listBySubStation(String substationNo, Page pageInfo) {
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("substationNo", substationNo) ;
		PageInfo<Map<String, Object>> page = baseDao.getByPage("Courier.listBySubStation", pageInfo, params);
		return  page;
	}
	
	public Courier getCourierById(Integer id){
		Courier courier = baseDao.getById("Courier.getById", id);
		return courier;
	}
	
	public Courier getCourierByNo(Courier courier){
		Courier courier1 = baseDao.getOne("Courier.getByNo",courier.getCourierNo()) ;
		return courier1;
	}
	
	public Courier getCourierByNo(String courierNo){
		Courier courier1 = baseDao.getOne("Courier.getByNo",courierNo) ;
		return courier1;
	}
	
	public int save(Courier courier){
		return baseDao.insert("Courier.insert", courier);
	}
	
	public boolean isExist(Courier courier){
		Courier courierinfo = baseDao.getOne("Courier.getByUserName", courier.getUserName());
		if(courierinfo == null)
			return false;
		else
			return true;
	}
	
	public int updateSarea(Courier courier){
		return baseDao.update("Courier.updateSarea", courier);
	}
	
	public int update(Courier courier){
		/*Courier courier1 = baseDao.getById("Courier.getById", courier.getId());
		if (!courier.getSubstationNo().equals(courier1.getSubstationNo())) {
			if (isChangeSno(courier1.getCourierNo())) {
				baseDao.update("Courier.expireCourier", courier) ;
			}else {
				return -1 ;
			}
			//baseDao.update("Courier.cancelCourierOrder", courier) ;
		}*/
		return baseDao.update("Courier.update", courier);
	}
	
	public int creset(Courier courier){
		return baseDao.update("Courier.creset", courier);
	}
	
	public int delete(Integer id){
		Courier courier = baseDao.getById("Courier.getById", id);
		baseDao.update("Courier.expireCourier", courier) ;
		//baseDao.update("Courier.cancelCourierOrder", courier) ;
		return baseDao.delete("Courier.delete", id);
	}
	
	
	public boolean isChange(Integer id){
		Courier courier = baseDao.getById("Courier.getById", id);
		Map<String, Object> ret = baseDao.getFrist("Courier.isChange", courier);
		if(ret == null)
			return true;
		else
			return false;
	}

	public boolean isChangeSno(String courierNo){
		Map<String, Object> map = new HashMap<String, Object>() ;
		map.put("courierNo", courierNo) ;
		map.put("takeStatus", "1") ;
		map.put("sendStatus", "2,7,8") ;
		Map<String, Object> ret = baseDao.getFrist("Courier.isChangeSno", map);
		if(ret == null)
			return true;
		else
			return false;
	}
	
}
