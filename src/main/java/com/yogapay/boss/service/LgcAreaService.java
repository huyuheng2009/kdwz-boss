package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.LgcArea;

@Service
public class LgcAreaService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("LgcArea.list", pageInfo, params);
		return  page;
	}
	
	public void save(LgcArea lgcArea){
		baseDao.insert("LgcArea.insert", lgcArea);
	}
	
public LgcArea getById(Integer id) {
	return baseDao.getById("LgcArea.getById", id) ;
}

public LgcArea getByArea(LgcArea lgcArea) {
	return baseDao.getOne("LgcArea.getByArea", lgcArea) ;
}

	public int update(LgcArea lgcArea){
		return baseDao.update("LgcArea.update", lgcArea);
	}
	
	public int delById(Integer id){
		return baseDao.delete("LgcArea.delById", id);
	}



}
