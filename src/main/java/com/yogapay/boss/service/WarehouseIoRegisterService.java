package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.ValuationRule;

@Service
public class WarehouseIoRegisterService {

	@Resource
	private BaseDao baseDao ;
	
    public long save(Map<String, String> pMap) throws SQLException {
    	return baseDao.insert("WarehouseIoRegister.insert", pMap) ;
	}
    


    
}
