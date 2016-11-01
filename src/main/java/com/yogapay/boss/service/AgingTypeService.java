package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.AgingType;
import com.yogapay.boss.domain.ItemType;

@Service
public class AgingTypeService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("AgingType.list", pageInfo, params);
		return  page;
	}
	public List<Map<String, Object>> getList() {
		List<Map<String, Object>> getList = baseDao.getList("AgingType.list");
		return  getList;
	}
	
	public void save(AgingType agingType){
		baseDao.insert("AgingType.insert", agingType);
	}
	
public AgingType getById(Integer id) {
	return baseDao.getById("AgingType.getById", id) ;
}

	public int update(AgingType agingType){
		return baseDao.update("AgingType.update", agingType);
	}
	
	public int delById(Integer id){
		return baseDao.delete("AgingType.delById", id);
	}



}
