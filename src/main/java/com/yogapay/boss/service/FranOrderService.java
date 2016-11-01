package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.record.common.FeatFormulaErr2;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.FranchiseOrder;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

@Service
public class FranOrderService {
	@Resource
	private BaseDao baseDao ;
	@Resource
	private FranchiseRuleService franchiseRuleService ;
	

	
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) throws SQLException {
		String orderNos = StringUtils.nullString(params.get("lgcOrderNo"));
        String[] ods = {};
        Map<String, String> pMap = new HashMap<String, String>() ;
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
        	  String o = "";
              orderNos = orderNos.replace("'", "");
              ods = orderNos.split("\r\n");
              for (int i = 0; i < ods.length; i++) {
                  o = o + "'" + ods[i] + "',";
              }
              o = o.substring(0, o.length()-1) ;
            pMap.put("lgcOrderNos", o);
            pMap.put("take_substation_no", params.get("take_substation_no")) ;
            pMap.put("send_substation_no", params.get("send_substation_no")) ;
            pMap.put("moneyType", params.get("moneyType")) ;
        }else {
        	pMap.putAll(params);
		}
		PageInfo<Map<String, Object>> page  = baseDao.getByPage("FranchiseOrder.list", pageInfo,pMap) ;
		return page;
	}
	
	public List<Map<String, Object>> getlist(Map<String, String> params) throws SQLException {
		String orderNos = StringUtils.nullString(params.get("lgcOrderNo"));
        String[] ods = {};
        Map<String, String> pMap = new HashMap<String, String>() ;
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
        	  String o = "";
              orderNos = orderNos.replace("'", "");
              ods = orderNos.split("\r\n");
              for (int i = 0; i < ods.length; i++) {
                  o = o + "'" + ods[i] + "',";
              }
              o = o.substring(0, o.length()-1) ;
            pMap.put("lgcOrderNos", o);
            pMap.put("take_substation_no", params.get("take_substation_no")) ;
            pMap.put("send_substation_no", params.get("send_substation_no")) ;
            pMap.put("moneyType", params.get("moneyType")) ;
        }else {
        	pMap.putAll(params);
		}
		List<Map<String, Object>> list  = baseDao.getList("FranchiseOrder.list",pMap) ;
		return list;
	}
	
	public List<Map<String, Object>> getListByIds(Long[] ids){
		List<Map<String, Object>> list  = baseDao.getList("FranchiseOrder.getListByIds",ids) ;
		return list;
	}

	
	public Map<String, Object> getById(Integer id) {
		return baseDao.getById("FranchiseOrder.getById", id) ;
	}
	
	public boolean pidaiIds(Long[] ids) {
		List<Map<String, Object>> list  = baseDao.getList("FranchiseOrder.pidaiIds",ids) ;
		if (list!=null&&list.size()>0) {
			return true ;
		}
		return false ;
	}
   
	/**
	 * 批带事物方法
	 * @param id
	 * @param moneyType
	 * @return
	 */
	public int withTransactionPidai(Integer id,String moneyType) {
		Date nowDate = new Date() ;
		BossUser user = Constants.getUser() ;
		Map<String, Object> settleMap = new HashMap<String, Object>() ;
		
		settleMap.put("settleTime", DateUtils.formatDate(nowDate)) ;
		settleMap.put("settleName", user.getRealName()) ;
		settleMap.put("id", String.valueOf(id)) ;
		Map<String, Object> oMap = baseDao.getOne("FranchiseOrder.getById", id) ;
		//没有派件网点或者确认的订单不允许批带
		 if (oMap==null||!moneyType.equals(oMap.get("money_type"))||StringUtils.isEmptyWithTrim(StringUtils.nullString(oMap.get("send_substation_no")))
				 ||"SUCCESS".equals(oMap.get("confirm_status"))) {
			return 1 ;
		 }
		 
		 String sno =  StringUtils.nullString(oMap.get("take_substation_no")) ;
		 if ("P".equals(moneyType)) {
			 sno =  StringUtils.nullString(oMap.get("send_substation_no")) ;
		 }
		Map<String, Object> ruleMap = franchiseRuleService.getFirstRule(StringUtils.nullString(oMap.get("take_substation_no")), StringUtils.nullString(oMap.get("send_substation_no")), StringUtils.nullString(oMap.get("item_type")), 
				 moneyType, "", StringUtils.nullString(oMap.get("create_time"))) ;
		/*  if (ruleMap==null) {
			  settleMap.put("settleMoney", oMap.get("settle_money")) ;
			  baseDao.update("FranchiseOrder.settleStatus", settleMap) ;
			  return 1 ;
		  }*/
		
		 float balance = 0 ;
		if (ruleMap!=null) {
			  balance = franchiseRuleService.getRuleSettle(ruleMap, StringUtils.nullString(oMap.get("item_weight"),"0")) ;
		}
		
		  Map<String, Object> pMap  = new HashMap<String, Object>() ;
		  pMap.put("substationNo", sno) ;
		  Map<String, Object> curMap = baseDao.getOne("FranchiseOrder.curBalance", pMap) ;
		  
		  //未开账
		  if (curMap==null) {
			  settleMap.put("settleMoney", balance) ;
			  baseDao.update("FranchiseOrder.settleStatus", settleMap) ;
			  return 1 ;
		  }
		  
		  float curBalance = 0 ;
		  if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(curMap.get("cur_balance")))) {
			  curBalance = Float.valueOf(StringUtils.nullString(curMap.get("cur_balance"))) ;
		  }
		 
		  
		  
		
		  
		  float obalance = Float.valueOf(StringUtils.nullString(oMap.get("settle_money"),"0")) ;
		  float realMoney = 0 ;
		  float afBalance = curBalance ;
		  
		  boolean add = true ; //是否增加余额
		  realMoney = Math.abs(balance - obalance) ;
		  if (("Z".equals(moneyType)&&FranchiseRuleService.biger(balance, obalance, false))||
			   "P".equals(moneyType)&&FranchiseRuleService.biger(obalance,balance, false)) {
			add = false ; //扣款
		  }
		  
		  if (!add) {
			  //扣款
			  afBalance = curBalance - realMoney ;
			  
			  if (FranchiseRuleService.biger(0, realMoney, true)) {
				  settleMap.put("settleMoney", balance) ;
				  baseDao.update("FranchiseOrder.settleStatus", settleMap) ;
				  return 1 ;
			  }
			  
			/*  if (franchiseRuleService.biger(curBalance, realMoney, false)) {*/
				  if (true) {
				  pMap.put("balance", realMoney) ;
				  baseDao.update("FranchiseOrder.subBalance", pMap) ;
				  
				  settleMap.put("settleMoney", balance) ;
				  baseDao.update("FranchiseOrder.settleStatus", settleMap) ;
				  
				  Map<String, Object> bMap = new HashMap<String, Object>() ;
				  bMap.put("substation_no", sno) ;
				  bMap.put("create_time", DateUtils.formatDate(nowDate));
				  bMap.put("lgc_order_no", StringUtils.nullString(oMap.get("lgc_order_no"))) ;
				  if ("P".equals(moneyType)) {
					  bMap.put("type", "PJ") ;
				 }else {
					 bMap.put("type", "ZZ") ;
				 }
				  bMap.put("before_balance", curBalance) ;
				  bMap.put("use_balance", -realMoney) ; //扣款金额为负数
				  bMap.put("after_balance", afBalance) ;
				  bMap.put("source", "USER") ;
				  bMap.put("operater", user.getRealName()) ;
				  baseDao.insert("FranchiseOrder.saveRecord", bMap) ;
				  
			   }else {
				return 2 ;
			  }
		  }else {
			 //加余额
			  //realMoney = obalance - balance ;
			  afBalance = curBalance + realMoney ;
			  
			  if (FranchiseRuleService.biger(0, realMoney, true)) {
				  settleMap.put("settleMoney", balance) ;
				  baseDao.update("FranchiseOrder.settleStatus", settleMap) ;
				  return 1 ;
			  }
			  
			  pMap.put("balance", realMoney) ;
			  baseDao.update("FranchiseOrder.addBalance", pMap) ;
			  
			  settleMap.put("settleMoney", String.valueOf(balance)) ;
			  baseDao.update("FranchiseOrder.settleStatus", settleMap) ;
			  
			  Map<String, Object> bMap = new HashMap<String, Object>() ;
			  bMap.put("substation_no", sno) ;
			  bMap.put("create_time", DateUtils.formatDate(nowDate));
			  bMap.put("lgc_order_no", StringUtils.nullString(oMap.get("lgc_order_no"))) ;
			  if ("P".equals(moneyType)) {
				  bMap.put("type", "PJ") ;
			 }else {
				 bMap.put("type", "ZZ") ;
			 }
			  bMap.put("before_balance", curBalance) ;
			  bMap.put("use_balance", realMoney) ;
			  bMap.put("after_balance", afBalance) ;
			  bMap.put("source", "USER") ;
			  bMap.put("operater", user.getRealName()) ;
			  baseDao.insert("FranchiseOrder.saveRecord", bMap) ;
			  
		  }
		return 1 ;
	}

	public void batchUpdate(Map<String, Object> pMap) {
		baseDao.update("FranchiseOrder.batchUpdate", pMap) ;
		
	}
	
	public void updateById(Map<String, Object> pMap){
		baseDao.update("FranchiseOrder.updateById", pMap) ;
	}

	public void batchQueren(Map<String, Object> pMap) {
		baseDao.update("FranchiseOrder.batchQueren", pMap) ;
	}

	public void saveOrder(FranchiseOrder franchiseOrder) {
		baseDao.insert("FranchiseOrder.saveOrder", franchiseOrder) ;
	}

	public void updateSendSno(String orderNo, String sendSno,String lgcOrderNo) {
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("orderNo", orderNo) ;
		pMap.put("sendSno", sendSno) ;
		pMap.put("lgcOrderNo", lgcOrderNo) ;
		baseDao.update("FranchiseOrder.updateSendSno", pMap) ;
	}
	
	public void updateSendSnoIfnull(String orderNo, String sendSno) {
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("orderNo", orderNo) ;
		pMap.put("sendSno", sendSno) ;
		baseDao.update("FranchiseOrder.updateSendSnoIfnull", pMap) ;
	}
	

}