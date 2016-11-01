package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.MatterType;

@Service
public class MatterTypeService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("MatterType.list", pageInfo, params);
		return  page;
	}
	
	public MatterType getById(Integer id){
		MatterType matterType = baseDao.getById("MatterType.getById", id);
		return matterType;
	}
	
	public int saveMatterPro(MatterType matterType){
		return baseDao.insert("MatterType.insert", matterType);
	}
	
	public int delById(Integer id){
		return baseDao.delete("MatterType.delById", id);
	}

	public MatterType getByName(Map<String, String> params) {
		MatterType matterType = baseDao.getOne("MatterType.getByName", params);
		return matterType;
	}


}
