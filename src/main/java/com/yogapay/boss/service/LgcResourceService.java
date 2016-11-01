package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.LgcResource;

@Service
public class LgcResourceService {
	@Resource
	private ManagerDao managerDao ;
	
	public Map<String, Object> getLgcResource(String lgcNo,String resName) {
		LgcResource lgcResource = new LgcResource() ;
		lgcResource.setLgcNo(lgcNo);
		lgcResource.setResName(resName);
		return managerDao.getOne("LgcResource.getLgcResource", lgcResource) ;
	}
	
	/**
	 * 
	 * @param lgcResource
	 * @param def 
	 * @return
	 */
	public Map<String, Object> getLgcResource(String lgcNo,String resName,String def) {
		LgcResource lgcResource = new LgcResource() ;
		lgcResource.setLgcNo(lgcNo);
		lgcResource.setResName(resName);
		Map<String, Object> map = managerDao.getOne("LgcResource.getLgcResource", lgcResource) ;
		if (map==null) {
			lgcResource.setLgcNo(def);
			map = managerDao.getOne("LgcResource.getLgcResource", lgcResource) ;
		}
		return  map;
	}
	
	public void save(LgcResource lgcResource){
		managerDao.insert("LgcResource.insert", lgcResource);
	}
	
	public void updateUrl(LgcResource lgcResource){
		managerDao.update("LgcResource.updateUrl", lgcResource);
	}
	
	public void saveOrUpdate(LgcResource lgcResource){
		Map<String, Object> map = managerDao.getOne("LgcResource.getLgcResource", lgcResource) ;
		if (map!=null) {
			lgcResource.setId(Integer.valueOf(map.get("id").toString()));
			managerDao.update("LgcResource.updateUrl", lgcResource);
		}else {
			managerDao.insert("LgcResource.insert", lgcResource);
		}
	}
	
	
}
