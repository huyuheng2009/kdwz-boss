package com.yogapay.boss.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.CodRateType;
import com.yogapay.boss.domain.ConsumeHistory;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.DisUser;
import com.yogapay.boss.domain.DisUserBalance;
import com.yogapay.boss.domain.DiscountType;
import com.yogapay.boss.domain.LgcAddr;
import com.yogapay.boss.domain.MonthSettleType;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.enums.PayStatus;
import com.yogapay.boss.enums.TrackStatus;
import com.yogapay.boss.service.CodRateTypeService;
import com.yogapay.boss.service.CodSettleUserService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.MonthSettleTypeService;
import com.yogapay.boss.service.MonthUserCountService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.ConstantsLoader;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.KuaiDiYiBaiUtil;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;
import com.yogapay.boss.utils.WeiXinUtil;

/**
 * 
 * @author 005
 *
 */
@Controller
@RequestMapping(value = "/monthCount")
public class MonthUserCountController extends BaseController {
	
	     @Resource
	     private  MonthUserCountService monthUserCountService ;
	     
			
	
			 // 用于月结客户每月发货统计
			@RequestMapping(value = { "/list" })
			public String list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
				
				String substationNo = params.get("substationNo") ;
			    String monthNo = params.get("monthNo") ;
			    if (!StringUtils.isEmptyWithTrim(params.get("monthNo"))&&monthNo.contains(")")) {
					params.put("monthNo", monthNo.substring(0,monthNo.indexOf("("))) ;
				}
				
				Date nowDate = new Date() ;
				String cyear = DateUtils.formatDate(nowDate,"yyyy") ;
				if (StringUtils.isEmptyWithTrim(params.get("cyear"))) {
					params.put("cyear", cyear) ;
				}
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				if(!StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					if (substationNo.contains(")")) {
						params.put("substationNo", substationNo.substring(0,substationNo.indexOf("("))) ;
					}
				}else {
					String substationNo1 ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
						 substationNo1 = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo1 = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo1);
				}
				
				//第一次默认为空
				PageInfo<Map<String, Object>> list = new PageInfo<Map<String,Object>>(null) ;
				if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
					list = monthUserCountService.list(params,getPageInfo(cpage));
				}else {
					params.put("ff", "1") ;
				}
				
				params.put("substationNo", substationNo) ;
				params.put("monthNo", monthNo) ;
				model.put("list", list) ;
				model.put("params", params) ;
				return "monthUser/list";
			}				
			
			 // 用于网点收派环比表
			@RequestMapping(value = { "/mmlist" })
			public String mmlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
				
				String substationNo = params.get("substationNo") ;
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				if(!StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					if (substationNo.contains(")")) {
						params.put("substationNo", substationNo.substring(0,substationNo.indexOf("("))) ;
					}
				}else {
					String substationNo1 ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
						 substationNo1 = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo1 = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo1);
				}
				
                Date nowDate = new Date() ;
				String date1 = params.get("date1") ;
				String date2 = params.get("date2") ;
				String date3 = params.get("date3") ;
				String date4 = params.get("date4") ;
				
				//第一次默认为空
				PageInfo<Map<String, Object>> list = new PageInfo<Map<String,Object>>(null) ;
				if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
					
					if (StringUtils.isEmptyWithTrim(date1)) {
						date1 = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					 }
					if (StringUtils.isEmptyWithTrim(date2)) {
						date2 = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					 }
					if (StringUtils.isEmptyWithTrim(date3)) {
						date3 = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					 }
					if (StringUtils.isEmptyWithTrim(date4)) {
						date4 = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					 }
					params.put("date1",date1) ;
					params.put("date2",date2+" 23:59:59") ;
					params.put("date3",date3) ;
					params.put("date4",date4+" 23:59:59") ;
					
					String timeBegin = date1 ;
					if (DateUtils.parseDate(date1, "yyyy-MM-dd").getTime()-DateUtils.parseDate(date3, "yyyy-MM-dd").getTime()>0) {
						timeBegin = date3 ;
					}
					String timeEnd = date4 ;
					if (DateUtils.parseDate(date2, "yyyy-MM-dd").getTime()-DateUtils.parseDate(date4, "yyyy-MM-dd").getTime()>0) {
						timeEnd = date2 ;
					}
					params.put("createTimeBegin", timeBegin) ;
					params.put("createTimeEnd", timeEnd+" 23:59:59") ;
					
					list = monthUserCountService.mmlist(params,getPageInfo(cpage));
				}else {
					params.put("ff", "1") ;
				}
				
				params.put("substationNo", substationNo) ;
				model.put("list", list) ;
				params.put("date2", date2) ;
				params.put("date4", date4) ;
				model.put("params", params) ;
				return "monthUser/mmlist";
			}				


			 // 用于快递员收派环比表
			@RequestMapping(value = { "/cmlist" })
			public String cmlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
				
				String substationNo = params.get("substationNo") ;
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				if(!StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					if (substationNo.contains(")")) {
						params.put("substationNo", substationNo.substring(0,substationNo.indexOf("("))) ;
					}
				}else {
					String substationNo1 ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
						 substationNo1 = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo1 = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo1);
				}
				
				String courierNo = params.get("courierNo") ;
				if(!StringUtils.isEmptyWithTrim(params.get("courierNo"))){
					if (courierNo.contains(")")) {
						params.put("courierNo", courierNo.substring(0,courierNo.indexOf("("))) ;
					}
				}
				
               Date nowDate = new Date() ;
				String date1 = params.get("date1") ;
				String date2 = params.get("date2") ;
				String date3 = params.get("date3") ;
				String date4 = params.get("date4") ;
				
				//第一次默认为空
				PageInfo<Map<String, Object>> list = new PageInfo<Map<String,Object>>(null) ;
				if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
					
					if (StringUtils.isEmptyWithTrim(date1)) {
						date1 = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					 }
					if (StringUtils.isEmptyWithTrim(date2)) {
						date2 = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					 }
					if (StringUtils.isEmptyWithTrim(date3)) {
						date3 = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					 }
					if (StringUtils.isEmptyWithTrim(date4)) {
						date4 = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					 }
					params.put("date1",date1) ;
					params.put("date2",date2+" 23:59:59") ;
					params.put("date3",date3) ;
					params.put("date4",date4+" 23:59:59") ;
					
					String timeBegin = date1 ;
					if (DateUtils.parseDate(date1, "yyyy-MM-dd").getTime()-DateUtils.parseDate(date3, "yyyy-MM-dd").getTime()>0) {
						timeBegin = date3 ;
					}
					String timeEnd = date4 ;
					if (DateUtils.parseDate(date2, "yyyy-MM-dd").getTime()-DateUtils.parseDate(date4, "yyyy-MM-dd").getTime()>0) {
						timeEnd = date2 ;
					}
					params.put("createTimeBegin", timeBegin) ;
					params.put("createTimeEnd", timeEnd+" 23:59:59") ;
					
					list = monthUserCountService.cmlist(params,getPageInfo(cpage));
				}else {
					params.put("ff", "1") ;
				}
				
				params.put("substationNo", substationNo) ;
				params.put("courierNo", courierNo) ;
				model.put("list", list) ;
				params.put("date2", date2) ;
				params.put("date4", date4) ;
				model.put("params", params) ;
				return "monthUser/cmlist";
			}						
			
			
}
