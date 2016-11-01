package com.yogapay.boss.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.DisUser;
import com.yogapay.boss.domain.MatterPro;
import com.yogapay.boss.utils.DateUtils;

@Service
public class DisUserService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("DisUser.list", pageInfo, params);
		return  page;
	}
	
	public DisUser getById(Integer id){
		DisUser disUser = baseDao.getById("DisUser.getById", id);
		return disUser;
	}
	
	public Map<String, Object> getDetailById(Integer id){
		Map<String, Object> map = baseDao.getById("DisUser.getDetailById", id);
		return map;
	}
	
	
	
	public DisUser getByNo(String disUserNo){
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("disUserNo", disUserNo) ;
		DisUser disUser = baseDao.getOne("DisUser.getByNo", pMap);
		return disUser;
	}
	
	public void note(Integer uid,String note){
		if (note==null||"".equals(note)) {
			return ;
		}
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("uid", uid) ;
		pMap.put("note", note) ;
		pMap.put("create_time", DateUtils.formatDate(new Date())) ;
		baseDao.update("DisUser.updateNote", pMap);
		baseDao.update("DisUser.addNote", pMap);
	}
	
	public int statusUpdate(DisUser disUser){
		return baseDao.update("DisUser.statusUpdate", disUser);
	}
	
	public int saveDisUser(DisUser disUser){
		return baseDao.insert("DisUser.insert", disUser);
	}
	
	public int updateDisUser(DisUser disUser){
		return baseDao.update("DisUser.update", disUser);
	}
	
	public int cpw(DisUser disUser){
		return baseDao.update("DisUser.cpw", disUser);
	}
	
	public int delById(Integer id){
		return baseDao.delete("DisUser.delById", id);
	}


}
