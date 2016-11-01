package com.yogapay.boss.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.CourierDaySettle;
import com.yogapay.boss.utils.DateUtils;

@Service
public class CourierDaySettleService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>(params) ;
		if (map.containsKey("substationNo")) {
			String[] sno = map.get("substationNo").toString().replace("'", "").split(",") ;
			map.put("sno", sno) ;
		}
		PageInfo<Map<String, Object>> page = baseDao.getByPage("CourierDaySettle.list", pageInfo, map);
		return  page;
	}
	
	
	public List<Map<String, Object>> list(Map<String, String> params) {
		Map<String, Object> map = new HashMap<String, Object>(params) ;
		if (map.containsKey("substationNo")) {
			String[] sno = map.get("substationNo").toString().replace("'", "").split(",") ;
			map.put("sno", sno) ;
		}
		List<Map<String, Object>> page = baseDao.getList("CourierDaySettle.list", map);
		return  page;
	}
	
	
	public void save(CourierDaySettle courierDaySettle){
		baseDao.insert("CourierDaySettle.insert", courierDaySettle);
	}
	
	public void batchSave(List<CourierDaySettle> courierDaySettles){
		Map<String, Object> map =  new HashMap<String, Object>() ;
		map.put("list", courierDaySettles) ;
		baseDao.insert("CourierDaySettle.batchInsert", map);
	}
	
	
	public void batchpay(String ids,String settleType,String settleCount,String note){
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		Map<String, Object> map =  new HashMap<String, Object>() ;
        map.put("ids", ids) ;
        map.put("settleType", settleType) ;
        map.put("settleCount", settleCount) ;
        map.put("settleTime", DateUtils.formatDate(new Date())) ;
        map.put("settleName", bossUser.getRealName()) ;
        map.put("note", note) ;
		baseDao.update("CourierDaySettle.batchpay", map);
	}
	
	public void batchpayCount(String ids,String settleType,String note){
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		Map<String, Object> map =  new HashMap<String, Object>() ;
        map.put("ids", ids) ;
        map.put("settleType", settleType) ;
        map.put("settleTime", DateUtils.formatDate(new Date())) ;
        map.put("settleName", bossUser.getRealName()) ;
        map.put("note", note) ;
		baseDao.update("CourierDaySettle.batchpayCount", map);
	}
	
	public void nopay(String ids){
		Map<String, Object> map =  new HashMap<String, Object>() ;
        map.put("ids", ids) ;
		baseDao.update("CourierDaySettle.nopay", map);
	}
	
	public void batchExamine(String ids){
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		Map<String, Object> map =  new HashMap<String, Object>() ;
        map.put("ids", ids) ;
        map.put("examiner", bossUser.getRealName()) ;
        map.put("examineTime", DateUtils.formatDate(new Date())) ;
		baseDao.update("CourierDaySettle.batchExamine", map);
	}
	
	public void noExamine(String ids){
		Map<String, Object> map =  new HashMap<String, Object>() ;
        map.put("ids", ids) ;
		baseDao.update("CourierDaySettle.noExamine", map);
	}
	
	
	public int delByCtime(String ctime){
		Map<String, String> map =  new HashMap<String, String>() ;
		map.put("ctime", ctime) ;
		return baseDao.deleteByParams("CourierDaySettle.delByCtime", map);
	}


}
