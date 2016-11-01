package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.RequireType;

@Service
public class RequireTypeService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("RequireType.list", pageInfo, params);
		return  page;
	}
	
	public void save(RequireType requireType){
		baseDao.insert("RequireType.insert", requireType);
	}
	
public RequireType getById(Integer id) {
	return baseDao.getById("RequireType.getById", id) ;
}

	public int update(RequireType requireType){
		return baseDao.update("RequireType.update", requireType);
	}
	
	public int delById(Integer id){
		return baseDao.delete("RequireType.delById", id);
	}



}
