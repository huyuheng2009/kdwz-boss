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
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.AppProduct;
import com.yogapay.boss.domain.AppVersion;
import com.yogapay.boss.domain.User;

@Service
public class AppVersionService {
	@Resource
	private BaseDao baseDao ;
	@Resource
	private ManagerDao managerDao ;

	public PageInfo<Map<String, Object>> list(Map<String, String> params,Page pageInfo) {
		PageInfo<Map<String, Object>> page = managerDao.getByPage("AppVersion.list", pageInfo, params);
		return page;
	}

    public int saveAppVersion(AppVersion appVersion) {
    	return managerDao.insert("AppVersion.insert", appVersion) ;
	}
	
    
    public int status(AppVersion appVersion) {
    	return managerDao.update("AppVersion.status", appVersion) ;
    }
    
    public int updateAppVersion(AppVersion appVersion) {
    	return managerDao.update("AppVersion.update", appVersion) ;
    }
    
    public int delById(Integer id){
		return managerDao.delete("AppVersion.delById", id);
	}

	public AppVersion getById(int id) {
		return managerDao.getById("AppVersion.getById", id);
	}
	
	public List<AppVersion> getLastVersion(String bname) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bname", bname) ;
		return managerDao.getList("AppVersion.getLastVersion", map);
	}
	
	public AppVersion getLastPlatVersion(String bname,String plat) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bname", bname) ;
		map.put("plat", plat) ;
		return managerDao.getFrist("AppVersion.getLastPlatVersion", map);
	}
    
	public List<Map<String, Object>> listPayType(int id) {
		return managerDao.getList("AppVersion.listPayType", id);
	}

	public void payType(String vid, String[] idList) {
		managerDao.delete("AppVersion.delPayTypeById", vid);
		List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
		for(String pid:idList){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vid", vid) ;
			map.put("pid", pid) ;
			objList.add(map);
		}
		if (objList.size()>0) {
			managerDao.insert("AppVersion.savePayType", objList) ;
		}
	}

	public void updateDownloadAddress(AppVersion appVersion) {
		 managerDao.update("AppVersion.updateDownloadAddress", appVersion) ;
	}
	
}
