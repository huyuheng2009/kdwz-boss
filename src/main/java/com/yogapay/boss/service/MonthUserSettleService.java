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
import com.yogapay.boss.domain.MonthUserSettle;
import com.yogapay.boss.utils.DateUtils;

@Service
public class MonthUserSettleService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		Map<String, Object> map = new HashMap<String, Object>(params) ;
		if (map.containsKey("substationNo")) {
			String[] sno = map.get("substationNo").toString().replace("'", "").split(",") ;
			map.put("sno", sno) ;
		}
		PageInfo<Map<String, Object>> page = baseDao.getByPage("MonthUserSettle.list", pageInfo, map);
		return  page;
	}
	
	
	public List<Map<String, Object>> list(Map<String, String> params) {
		Map<String, Object> map = new HashMap<String, Object>(params) ;
		if (map.containsKey("substationNo")) {
			String[] sno = map.get("substationNo").toString().replace("'", "").split(",") ;
			map.put("sno", sno) ;
		}
		List<Map<String, Object>> page = baseDao.getList("MonthUserSettle.list", map);
		return  page;
	}
	
	public Map<String, Object> getById(Integer id) {
		return  baseDao.getById("MonthUserSettle.getById", id) ;
	}
	
	
	public void save(MonthUserSettle monthUserSettle){
		baseDao.insert("MonthUserSettle.insert", monthUserSettle);
	}
	
	public void batchSave(List<MonthUserSettle> monthUserSettles){
		Map<String, Object> map =  new HashMap<String, Object>() ;
		map.put("list", monthUserSettles) ;
		baseDao.insert("MonthUserSettle.batchInsert", map);
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
		baseDao.update("MonthUserSettle.batchpay", map);
	}
	
	public void batchpayCount(String ids,String settleType,String note){
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		Map<String, Object> map =  new HashMap<String, Object>() ;
        map.put("ids", ids) ;
        map.put("settleType", settleType) ;
        map.put("settleTime", DateUtils.formatDate(new Date())) ;
        map.put("settleName", bossUser.getRealName()) ;
        map.put("note", note) ;
		baseDao.update("MonthUserSettle.batchpayCount", map);
	}
	
	public void nopay(String ids){
		Map<String, Object> map =  new HashMap<String, Object>() ;
        map.put("ids", ids) ;
		baseDao.update("MonthUserSettle.nopay", map);
	}
	
	public void batchExamine(String ids){
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		Map<String, Object> map =  new HashMap<String, Object>() ;
        map.put("ids", ids) ;
        map.put("examiner", bossUser.getRealName()) ;
        map.put("examineTime", DateUtils.formatDate(new Date())) ;
		baseDao.update("MonthUserSettle.batchExamine", map);
	}
	
	public void noExamine(String ids){
		Map<String, Object> map =  new HashMap<String, Object>() ;
        map.put("ids", ids) ;
		baseDao.update("MonthUserSettle.noExamine", map);
	}
	
	
	public int delByCtime(String ctime){
		Map<String, Object> map =  new HashMap<String, Object>() ;
		String[] ctimes = ctime.split(",") ;
		map.put("ctime", ctimes) ;
		return baseDao.deleteByParams("MonthUserSettle.delByCtime", map);
	}


}
