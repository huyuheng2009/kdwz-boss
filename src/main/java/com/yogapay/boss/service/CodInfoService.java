package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jpos.transaction.participant.HasEntry;
import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.CodInfo;
import com.yogapay.boss.domain.LgcResource;

@Service
public class CodInfoService {
	@Resource
	private BaseDao baseDao ;
	
	
	public void save(CodInfo codInfo){
		baseDao.insert("CodInfo.insert", codInfo);
	}
	
	
	public void batchSave(List<CodInfo> codInfos){
		if (codInfos!=null&&codInfos.size()>0) {
			baseDao.insert("CodInfo.batchInsert", codInfos);
		}
	}
	
	public void nopay(Integer id){
		baseDao.update("CodInfo.nopay", id);
	}
	
	public void delete(String orderNos){
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("orderNos", orderNos) ;
		baseDao.deleteByParams("CodInfo.delete", pMap);
	}


	public void cpayRate(String orderNos, float r) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("nos", orderNos.split(",")) ;
		pMap.put("rate", r) ;
		baseDao.update("CodInfo.cpayRate", pMap);
	}
	
	
}
