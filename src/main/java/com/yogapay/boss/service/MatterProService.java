package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.MatterPro;

@Service
public class MatterProService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("MatterPro.list", pageInfo, params);
		return  page;
	}
	
	public MatterPro getById(Integer id){
		MatterPro matterPro = baseDao.getById("MatterPro.getById", id);
		return matterPro;
	}
	
	public MatterPro getByNo(String no,String lgcNo){
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("no", no) ;
		pMap.put("lgcNo", lgcNo) ;
		MatterPro matterPro = baseDao.getOne("MatterPro.getByNo", pMap);
		return matterPro;
	}
	
	public MatterPro getByName(String name,String lgcNo){
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("name", name) ;
		pMap.put("lgcNo", lgcNo) ;
		MatterPro matterPro = baseDao.getOne("MatterPro.getByName", pMap);
		return matterPro;
	}
	
	
	public int saveMatterPro(MatterPro matterPro){
		return baseDao.insert("MatterPro.insert", matterPro);
	}
	
	
	
	
	
	public int updateMatterPro(MatterPro matterPro){
		return baseDao.update("MatterPro.update", matterPro);
	}
	
	public int delById(Integer id){
		return baseDao.delete("MatterPro.delById", id);
	}


}
