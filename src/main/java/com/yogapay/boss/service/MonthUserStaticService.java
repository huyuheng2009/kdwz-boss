package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.StringUtils;

@Service
public class MonthUserStaticService {

	@Resource
	private BaseDao baseDao;
	
	/**
	 * 根据商户号、时间查询月结商户月统计表
	 */
	public List<Map<String, Object>> getMonthUserStaticList(Map<String, Object> params){
		List<Map<String, Object>> list = baseDao.getList("MonthUserStatic.getMonthUserStaticList", params);
		return list;
	}
	
	//查询月结客户每月收件数
	
	
	//月结用户对账单查询
	public PageInfo<Map<String, Object>> list(Map<String, String> params,Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("MonthUserStatic.list", pageInfo, params);
		return page;
	}
	//月结用户对账单查询 按天或者月统计
	public PageInfo<Map<String, Object>> getMonthDayOrMonth(Map<String, String> params,Page pageInfo) {
		Map<String, Object> pMap = new HashMap<String, Object>(params) ;
		String ctime = StringUtils.nullString(params.get("ctime")) ;
		if (!StringUtils.isEmptyWithTrim(ctime)) {
			pMap.put("ctime", ctime.split(",")) ;
		}
		LgcConfig lgcConfig = Constants.getLgcConfig() ;
		pMap.put("reportExam", lgcConfig.getReportExam()) ;
		PageInfo<Map<String, Object>> page = baseDao.getByPage("MonthUserStatic.getMonthDayOrMonth", pageInfo, pMap);
		return page;
	}
	//月结用户对账单查询 按天或者月统计 导出
	public List<Map<String, Object>> getMonthDayOrMonthList(Map<String, String> params) {
		Map<String, Object> pMap = new HashMap<String, Object>(params) ;
		String ctime = StringUtils.nullString(params.get("ctime")) ;
		if (!StringUtils.isEmptyWithTrim(ctime)) {
			pMap.put("ctime", ctime.split(",")) ;
		}
		LgcConfig lgcConfig = Constants.getLgcConfig() ;
		pMap.put("reportExam", lgcConfig.getReportExam()) ;
		List<Map<String, Object>> list = baseDao.getList("MonthUserStatic.getMonthDayOrMonth", pMap);
		return list;
	}
	//月结用户对账单导出
	public List<Map<String, Object>> getUserListExport(Map<String, String> params) {
		List<Map<String, Object>> list = baseDao.getList("MonthUserStatic.list", params);
		return list;
	}


	//月结客户每月发货统计
	public PageInfo<Map<String, Object>> getMonthUserRevList(Map<String, String> params,Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("MonthUserStatic.getMonthUserRevList", pageInfo, params);
		return page;
	}
	//月结客户每月发货统计导出
	public List<Map<String, Object>>getMonthUserRevListExport(Map<String, String> params) {
		List<Map<String, Object>> list = baseDao.getList("MonthUserStatic.getMonthUserRevList", params);
		return list;
	}
	//"月结客户明细汇总表定时时任务
	public List<Map<String, String>>monthUserStatic(Map<String, String> params) {
		List<Map<String, String>> list = baseDao.getList("MonthUserStatic.monthUserStatic", params);
		return list;
	}
	//月结客户明细汇总表定时时任务
	public void save(Map<String,String> map) {
		baseDao.insert("MonthUserStatic.save", map) ;
	}
	//月结客户年统计
	public List<Map<String, Object>>monthUserYearStatic(Map<String, Object> a) {
		List<Map<String, Object>> list = baseDao.getList("MonthUserStatic.monthUserYearStatic" ,a);
		return list;
	}	
	public Map<String, Object>checkMonthUserYearStatic(Map<String,Object> params) {
		Map<String, Object> map = baseDao.getOne("MonthUserStatic.checkMonthUserYearStatic",params);
		return map;
	}	
	public void saveMonthUserYearStatics(Map<String,Object> map,int i) {
		if(i==1){
			baseDao.insert("MonthUserStatic.saveOneMonthUserYearStatics", map) ;				
		}
		if(i==2){
			baseDao.insert("MonthUserStatic.saveTwoMonthUserYearStatics", map) ;
		}
		if(i==3){
			baseDao.insert("MonthUserStatic.saveThreeMonthUserYearStatics", map) ;
		}
		if(i==4){
			baseDao.insert("MonthUserStatic.saveFourMonthUserYearStatics", map) ;
		}
		if(i==5){
			baseDao.insert("MonthUserStatic.saveFiveMonthUserYearStatics", map) ;
		}
		if(i==6){
			baseDao.insert("MonthUserStatic.saveSixMonthUserYearStatics", map) ;
		}
		if(i==7){
			baseDao.insert("MonthUserStatic.saveSevenMonthUserYearStatics", map) ;
		}
		if(i==8){			
			baseDao.insert("MonthUserStatic.saveEightMonthUserYearStatics", map) ;
		}
		if(i==9){
			baseDao.insert("MonthUserStatic.saveNineMonthUserYearStatics", map) ;
		}
		if(i==10){
			baseDao.insert("MonthUserStatic.saveTenMonthUserYearStatics", map) ;
		}
		if(i==11){
			baseDao.insert("MonthUserStatic.saveElevenMonthUserYearStatics", map) ;
		}
		if(i==12){
			baseDao.insert("MonthUserStatic.saveTwelveMonthUserYearStatics", map) ;
		}	
	}
	public void updateMonthUserYearStatics(Map<String, Object> params,int i) {
		if(i==1){
			 baseDao.update("MonthUserStatic.updateOneMonthUserYearStatics", params) ;			
		}
		if(i==2){
			 baseDao.update("MonthUserStatic.updateTwoMonthUserYearStatics", params) ;			
		}
		if(i==3){
			 baseDao.update("MonthUserStatic.updateThreeMonthUserYearStatics", params) ;			
		}
		if(i==4){
			 baseDao.update("MonthUserStatic.updateFourMonthUserYearStatics", params) ;			
		}
		if(i==5){
			 baseDao.update("MonthUserStatic.updateFiveMonthUserYearStatics", params) ;			
		}
		if(i==6){
			 baseDao.update("MonthUserStatic.updateSixMonthUserYearStatics", params) ;			
		}
		if(i==7){
			 baseDao.update("MonthUserStatic.updateSevenMonthUserYearStatics", params) ;			
		}
		if(i==8){			
			 baseDao.update("MonthUserStatic.updateEightMonthUserYearStatics", params) ;			
		}
		if(i==9){
			 baseDao.update("MonthUserStatic.updateNineMonthUserYearStatics", params) ;			
		}
		if(i==10){
			 baseDao.update("MonthUserStatic.updateTenMonthUserYearStatics", params) ;			
		}
		if(i==11){
			 baseDao.update("MonthUserStatic.updateElevenMonthUserYearStatics", params) ;			
		}
		if(i==12){
			 baseDao.update("MonthUserStatic.updateTwelveMonthUserYearStatics", params) ;			
		}	

	}	
	
	

}
