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
import com.yogapay.boss.domain.MonthSettleUser;
import com.yogapay.boss.domain.User;
import com.yogapay.boss.utils.Constants;

@Service
public class MobileUserService {

	@Resource
	private BaseDao baseDao;

	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("MobileUser.list", pageInfo, params);
		return page;
	}

	public int updateUser(User user) {
		baseDao.update("MobileUser.updateUser", user);
		return 1;
	}

	public int status(User user) {
		baseDao.update("MobileUser.status", user);
		return 1;
	}

	public PageInfo<Map<String, Object>> muserList(Map<String, String> params, Page pageInfo) {
		return baseDao.getByPage("MobileUser.muserList", pageInfo, params);
	}
	
	
	public List<Map<String, Object>> museraList(Map<String, String> params) {
		return baseDao.getList("MobileUser.muserList", params);
	}
	
	//月结用户收排明细

	public PageInfo<Map<String, Object>> getMonthList(Map<String, String> params, Page pageInfo) {
		LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
		return baseDao.getByPage("MobileUser.getMonthList", pageInfo, params);
	}
	//月结用户收排明细 收件

		public PageInfo<Map<String, Object>> getMonthListForTake(Map<String, String> params, Page pageInfo) {
			LgcConfig lgcConfig = Constants.getLgcConfig() ;
	    	params.put("reportExam", lgcConfig.getReportExam()) ;
			return baseDao.getByPage("MobileUser.getMonthListForTake", pageInfo, params);
		}
		//月结用户收排明细 派件
		
		public PageInfo<Map<String, Object>> getMonthListForSend(Map<String, String> params, Page pageInfo) {
			LgcConfig lgcConfig = Constants.getLgcConfig() ;
	    	params.put("reportExam", lgcConfig.getReportExam()) ;
			return baseDao.getByPage("MobileUser.getMonthListForSend", pageInfo, params);
		}
	//月结用户收排明细 用于导出

	public List<Map<String, Object>> getMonthList(Map<String, String> params) {
		LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
		return baseDao.getList("MobileUser.getMonthList", params);
	}

	public List<Map<String, Object>> getAllMonthSetterUser(Map<String, String> params) {
		return baseDao.getList("MobileUser.getMonthUserList", params);
	}

	public List<Map<String, Object>> getAllCourierList(Map<String, String> params) {
		return baseDao.getList("MobileUser.getCourierList", params);
	}
	
	public List<Map<String, Object>> getCourierListBySubStation(String substatioNo) {
		return baseDao.getList("MobileUser.getCourierListBySubStation", substatioNo);
	}
	//月结客户对账单——加回单

	public List<Map<String, Object>> getMonthListExport(Map<String, String> params) {
		return baseDao.getList("MobileUser.getMonthList", params);
	}

	public Map<String, Object> getMuserById(Integer id) {
		return baseDao.getById("MobileUser.getMuserById", id);
	}

	public Map<String, Object> getMuserByNo(String monthSettleNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("monthSettleNo", monthSettleNo);
		return baseDao.getOne("MobileUser.getMuserByNo", monthSettleNo);
	}

	public Map<String, Object> getMuserByMonthNo(String monthNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("monthNo", monthNo);
		return baseDao.getOne("MobileUser.getMuserByMonthNo", params);
	}

	public void saveMuser(MonthSettleUser mUser) {
		baseDao.insert("MobileUser.saveMuser", mUser);
	}

	public void updateMuser(MonthSettleUser mUser) {
		baseDao.update("MobileUser.uMuser", mUser);
	}

	public void resetMuser(MonthSettleUser mUser) {
		baseDao.update("MobileUser.resetMuser", mUser);
	}

	public void delMuserById(Integer id) {
		baseDao.delete("MobileUser.delMuserById", id);
	}
}
