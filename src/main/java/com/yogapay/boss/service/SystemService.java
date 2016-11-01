package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;

@Service
public class SystemService {
	@Resource
	private BaseDao baseDao ;
	//private SystemDao systemDao ;

	public PageInfo<Map<String, Object>> getLoginLog(Map<String, String> params, Page pageInfo) {
	/*	PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize()) ;
		List<Map<String, Object>> list = systemDao.getLoginLog(params) ;
		PageInfo<Map<String, Object>> page =new PageInfo(list);
		return page ;*/
		return baseDao.getByPage("System.getLoginLog", pageInfo, params) ;
	}





}
