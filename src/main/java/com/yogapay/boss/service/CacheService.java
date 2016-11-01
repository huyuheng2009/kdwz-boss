package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.SysDict;
import com.yogapay.boss.utils.StringUtils;

@Service
public class CacheService {
	@Resource
	private BaseDao baseDao ;
	@Resource
	private ManagerDao managerDao ;
	private static List<SysDict> allDict = null;
	private static List<Map<String, Object>> allProStatus = null ;

	public List<SysDict> findByName(String dictName) throws SQLException {
		if (null == allDict) {
			loadAllDict();
		}
		List<SysDict> revList = new ArrayList<SysDict>();
		for (SysDict d : allDict) {
			if (d.getDictName().equals(dictName.toUpperCase())) {
				revList.add(d);
			}
		}
		return revList;
	}
	
	public List<SysDict> findByKey(String key) throws SQLException {
		if (null == allDict) {
			loadAllDict();
		}
		List<SysDict> revList = new ArrayList<SysDict>();
		for (SysDict d : allDict) {
			if (d.getDictKey().equals(key.toUpperCase())) {
				revList.add(d);
			}
		}
		return revList;
	}

	public void loadAllDict() throws SQLException {
		//allDict = dictDao.getSysDict(null);
		allDict = managerDao.getList("SysDict.getSysDict") ;
	}

	public SysDict findByNameKey(String dictName, String key)
			throws SQLException {
		SysDict reval = null;
		if (null == allDict) {
			loadAllDict();
		}
		for (SysDict d : allDict) {
			if (d.getDictName().equals(dictName.toUpperCase())) {
				if (d.getDictKey().equals(key.toUpperCase())) {
					reval = d;
					break;
				}
			}
		}
		return reval;
	}

	
	public String proOrderStatus(String key)
			throws SQLException {
		if (allProStatus==null) {
			allProStatus = baseDao.getList("ProOrderStatus.list");
		}
		String resultVal = "" ;
		for (Map<String,Object> map:allProStatus) {
			if (key.equals(map.get("id").toString())) {
				resultVal = map.get("content").toString() ;
				break ;
			}
		}
		return resultVal ;
	}
	
	public String proOrderReason(String key)
			throws SQLException {
		   String resultVal = "" ;
		  Map<String, Object> params = new HashMap<String, Object>() ;
		    params.put("id", StringUtils.nullString(key)) ;
			Map<String, Object> map = baseDao.getOne("ProOrderReason.getById", params);
			resultVal = map==null?key:map.get("context").toString() ;
		return resultVal ;
	}
	
	


	public void save(SysDict sysDict) throws SQLException {
		//dictDao.save(sysDict);
		managerDao.insert("SysDict.save",sysDict) ;
	}	
	
	public void delByNameKey(String dictName, String key) throws SQLException {
		//dictDao.delByNameKey(dictName,key);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dictName", dictName) ;
		params.put("key", key) ;
		managerDao.deleteByParams("SysDict.delByNameKey", params) ;
	}	
	
	
	
}
