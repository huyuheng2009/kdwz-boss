package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.Lgc;

@Service
public class LgcService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("Lgc.list", pageInfo, params);
		return  page;
	}
	
	public Lgc getLgcById(Integer id){
		Lgc lgc = baseDao.getById("Lgc.getById", id);
		return lgc;
	}
	
	public int saveLgc(Lgc lgc){
		return baseDao.insert("Lgc.insert", lgc);
	}
	
	public boolean isExist(Lgc lgc){
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", lgc.getName());
		//Lgc lgcInfo = baseDao.getOne("Lgc.getByName", params);
		List<Lgc> lgcs = baseDao.getList("Lgc.getByName", params) ;
		if(lgcs == null||lgcs.size()<1)
			return false;
		else
			return true;
	}
	
	public int updateLgc(Lgc lgc){
		return baseDao.update("Lgc.update", lgc);
	}
	
	public int delById(Integer id){
		return baseDao.delete("Lgc.delById", id);
	}

	public Lgc getLgcByNo(String lgcNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("lgcNo", lgcNo);
		Lgc lgc = baseDao.getOne("Lgc.getByLgcNo", params);
		return lgc;
	}

	public void setNextSno(Lgc lgc) {
		baseDao.update("Lgc.nextSno", lgc);
	}
	
	  public Map<String, Object> getLgcVrate() {

	        Map<String, Object> ret = baseDao.getOne("Lgc.getLgcVrate", null);
	        return ret;
	    }
	
}
