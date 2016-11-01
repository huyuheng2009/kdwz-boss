package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.LgcAddr;

@Service
public class LgcAddrService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("LgcAddr.list", pageInfo, params);
		return  page;
	}
	
	public int saveLgcAddr(LgcAddr lgcAddr){
		return baseDao.insert("LgcAddr.insert", lgcAddr);
	}
	
	public boolean isExist(LgcAddr lgcAddr){
		Map<String, String> params = new  HashMap<String, String>() ;
		params.put("lgc_no", lgcAddr.getLgcNo()) ;
		params.put("name", lgcAddr.getName()) ;
		params.put("areaAddr", lgcAddr.getAreaAddr()) ;
		params.put("detailAddr", lgcAddr.getDetailAddr()) ;
		params.put("phone", lgcAddr.getPhone()) ;
		List<Map<String, Object>> page = baseDao.getList("LgcAddr.list",  params);
		if(page != null&&page.size()>0)
			return true;
		else
			return false;
	    }

	public Map<String, Object> isExistA(LgcAddr lgcAddr){
		Map<String, String> params = new  HashMap<String, String>() ;
		params.put("lgc_no", lgcAddr.getLgcNo()) ;
		params.put("name", lgcAddr.getName()) ;
		params.put("areaAddr", lgcAddr.getAreaAddr()) ;
		params.put("detailAddr", lgcAddr.getDetailAddr()) ;
		params.put("phone", lgcAddr.getPhone()) ;
		params.put("incomingPhone", lgcAddr.getIncomingPhone()) ;
		List<Map<String, Object>> page = baseDao.getList("LgcAddr.list",  params);
		if(page != null&&page.size()>0)
			return page.get(0);
		else
			return null;
	    }
	
    public int updateCourier(Map<String, Object> params) {
        return baseDao.update("LgcAddr.updateCourier", params);
    }
	
}
