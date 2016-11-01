package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.LgcMode;

@Service
public class LgcModeService {

	@Resource
	private BaseDao baseDao ;
	
    public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("LgcMode.list", pageInfo, params);
		return  page;
	}
    
    public void status(LgcMode lgcMode) {
		baseDao.update("LgcMode.closeAll", lgcMode) ;
		baseDao.update("LgcMode.status", lgcMode) ;
	}
    
    public Map<String, Object> getCurMode() {
		Map<String, Object> mode = baseDao.getOne("LgcMode.getCurMode", null);
		return  mode;
	}
    
}
