package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.ForCpn;

@Service
public class ForCpnService {
	@Resource
	private BaseDao baseDao;
	@Resource
	private ManagerDao managerDao ;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("ForCpn.list", pageInfo, params);
		return  page;
	}
	
	public PageInfo<Map<String, Object>> kdyblist(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = managerDao.getByPage("ForCpn.kdyblist", pageInfo, params);
		return  page;
	}
	
	public Map<String, Object> kdybName(String name) {
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("name", name) ;
		Map<String, Object> map = managerDao.getFrist("ForCpn.kdybName", params);
		return  map;
	}
	
	public List<Map<String, Object>> getList() {
		List<Map<String, Object>> getList = baseDao.getList("ForCpn.list");
		return  getList;
	}
	
	public void save(ForCpn forCpn){
		baseDao.insert("ForCpn.insert", forCpn);
	}
	
public ForCpn getById(Integer id) {
	return baseDao.getById("ForCpn.getById", id) ;
}

	public int update(ForCpn forCpn){
		return baseDao.update("ForCpn.update", forCpn);
	}
	
	public int delById(Integer id){
		return baseDao.delete("ForCpn.delById", id);
	}



}
