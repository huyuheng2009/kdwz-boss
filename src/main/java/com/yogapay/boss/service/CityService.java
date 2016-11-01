package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.City;

@Service
public class CityService {
	@Resource
	private BaseDao baseDao;
	@Resource
	private ManagerDao managerDao ;
	
	
	public void save(City city) {
		managerDao.insert("City.insert", city) ;
	}

	public PageInfo<Map<String, Object>> list(Map<String, String> params,Page pageInfo) {
		return managerDao.getByPage("City.list", pageInfo, params);
	}

	public City getById(int id) {
		// TODO Auto-generated method stub
		return managerDao.getById("City.getById", id);
	}
	public List<City> getByIdList(int id) {
		return managerDao.getList("City.getByParaId", id);
	}
	public int update(City city) {
		return managerDao.update("City.update", city) ;
	}
	
	public int delById(long id) {
		// TODO Auto-generated method stub
		return managerDao.deleteByParams("City.delete", id) ;
	}
	
    public List<City> queryListByParentId0() {
		return this.managerDao.getList("City.queryByParentId0", null);
	}

	public List<City> queryListByParentId(int parentId){
		return this.managerDao.getList("City.queryByParentId", parentId);
	}
	
}
