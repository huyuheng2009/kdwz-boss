package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.CodSettleUser;
import com.yogapay.boss.domain.MonthSettleUser;

@Service
public class CodSettleUserService {
	@Resource
	private BaseDao baseDao ;


	public PageInfo<Map<String, Object>> list(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("CodUser.list", pageInfo, params);
	}
	public List<Map<String, Object>> alist(Map<String, String> params) {
		return baseDao.getList("CodUser.list",params);
	}
	
	public Map<String, Object> getCuserById(Integer id) {
		return  baseDao.getById("CodUser.getCuserById", id);
	}
	
	public Map<String, Object> getCuserByNo(String  codNo) {
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("codNo", codNo) ;
		return  baseDao.getOne("CodUser.getCuserByNo", params);
	}
	
	
	public void saveCuser(CodSettleUser cUser) {
		baseDao.insert("CodUser.saveCuser", cUser) ;
	}

	public void updateCuser(CodSettleUser cUser) {
		baseDao.update("CodUser.uCuser", cUser) ;
	}
	
	public void delCuserById(Integer id) {
		baseDao.delete("CodUser.delCuserById", id) ;
	}


	public void status(CodSettleUser cUser) {
		baseDao.update("CodUser.status", cUser) ;
	}

	
}
