package com.yogapay.boss.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.DiscountType;

@Service
public class DiscountTypeService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("DiscountType.list", pageInfo, params);
		return  page;
	}
	
	public DiscountType getById(Integer id){
		DiscountType discountType = baseDao.getById("DiscountType.getById", id);
		return discountType;
	}
	
	public DiscountType getByMoney(BigDecimal money){
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("money", money) ;
		DiscountType discountType = baseDao.getOne("DiscountType.getByMoney", pMap);
		return discountType;
	}
	
	public int saveDiscountType(DiscountType discountType){
		return baseDao.insert("DiscountType.insert", discountType);
	}
	
	public int updateDiscountType(DiscountType discountType){
		return baseDao.update("DiscountType.update", discountType);
	}


	public int delById(Integer id){
		return baseDao.delete("DiscountType.delById", id);
	}


}
