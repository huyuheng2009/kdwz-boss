package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;

@Service
public class SequenceService {

	@Resource private BaseDao baseDao ;
	@Resource private ManagerDao managerDao ;
	
	public String getNextVal(String seqName) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seqName", seqName) ;
		Map<String, Object> map = baseDao.getOne("Sequence.getNextVal", params) ;
		String val = "" ;
		if (map!=null&&map.get("t")!=null) {
			val = map.get("t").toString() ;
		}
      return val ;
	}
	
	public String getCurrVal(String seqName) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seqName", seqName) ;
		Map<String, Object> map = baseDao.getOne("Sequence.getCurrVal", params) ;
		String val = "" ;
		if (map!=null&&map.get("t")!=null) {
			val = map.get("t").toString() ;
		}
      return val ;
	}
	

	public String getNextVal(String seqName,boolean manager) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seqName", seqName) ;
		Map<String, Object> map = managerDao.getOne("Sequence.getNextVal", params) ;
		String val = "" ;
		if (map!=null&&map.get("t")!=null) {
			val = map.get("t").toString() ;
		}
      return val ;
	}
	
	public String getCurrVal(String seqName,boolean manager) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seqName", seqName) ;
		Map<String, Object> map = managerDao.getOne("Sequence.getCurrVal", params) ;
		String val = "" ;
		if (map!=null&&map.get("t")!=null) {
			val = map.get("t").toString() ;
		}
      return val ;
	}
	
}
