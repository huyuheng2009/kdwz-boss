package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;

@Service
public class SalaryService {
	@Resource
	private BaseDao baseDao;

	public String maxId(){
		return	baseDao.getOne("Salary.maxId",null);	
	}
	public Map<String,Object> getAddMap(Map<String,String> map){		
		return baseDao.getFrist("Salary.getAddMap", map);
	}
	public void insertAdd1(Map<String,String> map){
		baseDao.insert("Salary.insertAdd1", map);		
	}
	public boolean checkId(String id){
		Map<String,Object> mao = baseDao.getFrist("Salary.checkId", id);
		if(mao ==null){
			return	true;
		}
		return false;
	}
	public void deleteById(String id){
		baseDao.delete("Salary.deleteById", id);	
	}
	public Map<String,Object> getSalaryMap(String id){
	return	baseDao.getFrist("Salary.checkId", id);
	}
	public Map<String,Object> getCourierInfo(String id){
		return	baseDao.getFrist("Salary.getCourierInfo", id);
	}
	
	public void updateSalaryInfo(Map<String,String> map){
		baseDao.update("Salary.updateSalaryInfo", map);
	}
	
	public PageInfo<Map<String, Object>> getccList(Map<String, String> params, Page pageInfo) {
        return baseDao.getByPage("Salary.getccList", pageInfo, params);
    }
	public PageInfo<Map<String, Object>> getCourierInfoRecord(Map<String, String> params, Page pageInfo) {
		return baseDao.getByPage("Salary.getCourierInfoRecord", pageInfo, params);
	}
	public boolean checkCourierNo(String courierNo){
	Map<String,Object> map =	baseDao.getFrist("Salary.checkCourierNo", courierNo);
		if(map==null){
			return true;
		}
		return false;
	}
	//提成维护
	public void insertCourierTc(Map<String, String> params){
		baseDao.insert("Salary.insertCourierTc",params);
	}
	//提成记录
	public void insertTcRecord(Map<String, String> params){
		baseDao.insert("Salary.insertTcRecord",params);
	}
	public void updateCourierTc(Map<String, String> params){
		baseDao.update("Salary.updateCourierTc", params);
	}
}
