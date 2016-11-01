package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.OrderInfo;

@Service
public class CourierDayStaticService  {
	@Resource
	private BaseDao baseDao;
	 /**
	  获取收派件记录表
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("CourierDayStatic.list", pageInfo, params);
		return  page;
	}
	
	/**
	 * 获取收派件记录表导出专用
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public List<Map<String, Object>> getList(Map<String, String> params) {
		List<Map<String, Object>> list = baseDao.getList("CourierDayStatic.list", params);
		return  list;
	}

	public void save(Map<String,Object> map) {
		System.out.println("开始插入数据");
		baseDao.insert("CourierDayStatic.save", map) ;
	}
	/**
	 * 每天定时统计数据
	 * @param map
	 */
	public  List<Map<String, Object>>  acount(Map<String,Object> map) {
		System.out.println("开始查询数据");
		List<Map<String, Object>> list = baseDao.getList("CourierDayStatic.acount", map) ;
		return list;
	}
	/**
	 * 删除定时任务前的数据
	 * @param map
	 */
	public  List<Map<String, Object>>  delete(Map<String,Object> map) {
		System.out.println("开始删除数据");
		List<Map<String, Object>> list = baseDao.getList("CourierDayStatic.delete", map) ;
		return list;
	}


}
