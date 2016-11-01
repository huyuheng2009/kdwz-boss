package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.CodRateType;
import com.yogapay.boss.domain.MonthSettleType;

@Service
public class CodRateTypeService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("CodRateType.list", pageInfo, params);
		return  page;
	}
	
	public CodRateType getById(Integer id){
		CodRateType codRateType = baseDao.getById("CodRateType.getById", id);
		return codRateType;
	}
	
	
	public int saveCodRateType(CodRateType codRateType){
		return baseDao.insert("CodRateType.insert", codRateType);
	}
	
	public int updateCodRateType(CodRateType codRateType){
		return baseDao.update("CodRateType.update", codRateType);
	}


	public int delById(Integer id){
		return baseDao.delete("CodRateType.delById", id);
	}


}
