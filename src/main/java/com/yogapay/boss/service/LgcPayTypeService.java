package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.LgcPayType;

@Service
public class LgcPayTypeService {
	@Resource
	private BaseDao baseDao;
	@Resource
	private ManagerDao managerDao ;
	
	public List<Map<String, Object>> getLgcPayType(LgcPayType lgcPayType) {
		List<Map<String, Object>> page = managerDao.getList("LgcPayType.getLgcPayType", lgcPayType);
		return  page;
	}
	
	public void save(LgcPayType lgcPayType){
		managerDao.insert("LgcPayType.insert", lgcPayType);
	}
	
	public void batchSave(List<LgcPayType> lgcPayTypes){
		managerDao.insert("LgcPayType.batchInsert", lgcPayTypes);
	}
	
	public int delByLgcNo(LgcPayType lgcPayType){
		return managerDao.deleteByParams("LgcPayType.delByLgcNo", lgcPayType);
	}


}
