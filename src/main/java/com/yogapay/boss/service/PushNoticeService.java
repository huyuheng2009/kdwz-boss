package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.PushNotice;

@Service
public class PushNoticeService {
	@Resource
	private BaseDao baseDao;
	@Resource
	private ManagerDao managerDao;
	
	public int insert(PushNotice pushNotice){
		return baseDao.insert("PushNotice.insert", pushNotice);
	}
	
	public int updateById(PushNotice pushNotice){
		return baseDao.update("PushNotice.updateById", pushNotice);
	}
	
	public int deleteById(long id){
		return baseDao.deleteByParams("PushNotice.deleteById", id);
	}
	
	public PushNotice findById(long id){
		return baseDao.getOne("PushNotice.findById", id);
	}
	
	public PushNotice getLast(){
		return baseDao.getOne("PushNotice.getLast", null) ;
	}
	
	public PageInfo<Map<String, Object>> getList( Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> list = baseDao.getByPage("PushNotice.getList", pageInfo, params);
		return list;
	}
	
	public int queryCount(PushNotice pushNotice){
		return baseDao.getOne("PushNotice.totalCount", pushNotice);
	}
	
	public Map<String, Object> queryIndexPic(){
		return managerDao.getOne("Manager.getIndexPic", null);
	}
}
