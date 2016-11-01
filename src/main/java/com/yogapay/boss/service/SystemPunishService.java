package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.SystemPunish;

@Service
public class SystemPunishService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("SystemPunish.list", pageInfo, params);
		return  page;
	}
	public List<Map<String, Object>> getList() {
		List<Map<String, Object>> getList = baseDao.getList("SystemPunish.list");
		return  getList;
	}
	
	public void save(SystemPunish systemPunish ){
		baseDao.insert("SystemPunish.insert",systemPunish );
	}
	
	public SystemPunish getById(Integer id) {
		return baseDao.getById("SystemPunish.getById", id) ;
	}
	
	public int update(SystemPunish systemPunish ){
		return baseDao.update("SystemPunish.update", systemPunish);
	}
	
	public int delById(Integer id){
		return baseDao.delete("SystemPunish.delById", id);
	}


}
