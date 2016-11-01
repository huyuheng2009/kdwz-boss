package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.dataSource.DynamicDataSourceHolder;
import com.yogapay.boss.dataSource.MDataSource;

@Service
public class ProjectDsService {
	
	@Resource
	private ManagerDao managerDao ;
	
	public List<MDataSource> getByProKey(String proKey) throws SQLException {
		return managerDao.getList("ProjectDs.getByProKey", proKey) ;
	}
    
	public MDataSource getByKey(String key) throws SQLException {
		return managerDao.getOne("ProjectDs.getByKey", key) ;
	}
	
}
