package com.yogapay.boss.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

@Service
public class MapService {
	@Resource
	private BaseDao baseDao;
	
	public List<Map<String, Object>> list(Map<String, String> params) {
		List<Map<String, Object>> list = baseDao.getList("Map.list", params);
		return  list;
	}
	
	/**
	 *  获取快递员以分站分组
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> listGroup(Map<String, String> params) {
		List<Map<String, Object>> ret =  new ArrayList<Map<String,Object>>() ;
		List<Map<String, Object>> list = baseDao.getList("Map.list", params);
		List<String> sno  = new ArrayList<String>() ;
		String cursno = "" ; //当前分站的编号
		for (Map<String, Object> mp : list) {
			Map<String, Object> sMap = new HashMap<String, Object>() ;
			cursno = StringUtils.nullString(mp.get("substation_no"));
			if (sno.contains(cursno)||StringUtil.isEmptyWithTrim(cursno)) {
				continue ;
			}
			sMap.put("substation_no", cursno) ;
			sMap.put("substation_name", StringUtils.nullString(mp.get("substation_name"))) ;
			sMap.put("location", StringUtils.nullString(mp.get("location"))) ;
			sno.add(cursno) ;
			List<Map<String, Object>> mlist = new ArrayList<Map<String,Object>>() ;
			for (Map<String, Object> mpp : list) {
				if (cursno.equals(mpp.get("substation_no"))) {
					mlist.add(mpp) ;
				}
			}
			sMap.put("clist", mlist) ;   //分站快递员
			ret.add(sMap) ;
		}
		return  ret;
	}
	
	public List<Map<String, Object>> pointList(Map<String, String> params) {
		List<Map<String, Object>> list = baseDao.getList("Map.pointList", params);
		return  list;
	}
}
