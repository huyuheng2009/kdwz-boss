package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;

@Service
public class MonthSettleService {
	@Resource
	private BaseDao baseDao;
	
	//查询上月月结用户所有收派件信
	public  List<Map<String, Object>> monthSettleUserList(Map<String,Object> map ){
		List<Map<String, Object>> list = baseDao.getList("Muser.list",map);
		return list;
	}
	//保存月结用户数据
	public   void save(Map<String,Object> map ){
		  baseDao.insert("Muser.save",map);
		
	}
	//删除月结用户数据
	public   void delete(Map<String,Object> map ){
		baseDao.insert("Muser.delete",map);
		
	}
}
