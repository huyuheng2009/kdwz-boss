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
import com.yogapay.boss.domain.CodRule;
import com.yogapay.boss.domain.ConsumeHistory;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.DisUser;
import com.yogapay.boss.domain.DisUserBalance;
import com.yogapay.boss.domain.DiscountType;
import com.yogapay.boss.domain.LgcAddr;
import com.yogapay.boss.domain.MonthSettleType;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.domain.TableFiledSort;
import com.yogapay.boss.domain.ValuationRule;
import com.yogapay.boss.enums.PayStatus;
import com.yogapay.boss.enums.TrackStatus;
import com.yogapay.boss.service.CodRateTypeService;
import com.yogapay.boss.service.CodRuleService;
import com.yogapay.boss.service.CodSettleUserService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.MonthSettleTypeService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.service.TableFiledSortService;
import com.yogapay.boss.service.ValuationRuleService;
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
@RequestMapping(value = "/order_ext")
public class OrderTwoController extends BaseController {
	
	     @Resource
	     private OrderService orderService ;
	     @Resource
	     private OrderTrackService orderTrackService ;
	     @Resource
	     private CourierService courierService ;
	     @Resource
	     private MobileUserService mobileUserService ;
	     @Resource
	     private CodSettleUserService codSettleUserService ;
	     @Resource
	     private MonthSettleTypeService monthSettleTypeService ;
	     @Resource
	     private CodRateTypeService codRateTypeService ;
	     @Resource
	     private TableFiledSortService tableFiledSortService;
	     @Resource
	     private CodRuleService codRuleService ;
	     @Resource
	     private ValuationRuleService valuationRuleService ;
	     /**
	      * 订单备注页面
	      * @param params
	      * @param model
	      * @param request
	      * @param response
	      * @return
	      */
			@RequestMapping(value = { "/note" })
			public String note(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response) {
				params.put("orderId", params.get("id")) ;
				 List<Map<String, Object>> noteList =  orderService.getNoteList(params) ;
				 model.put("noteList", noteList);
				model.put("params", params) ;
				return "order_ext/note";
			}	
			
			// 用于无效单
			@RequestMapping(value = { "/note_save" })
			public void note_save(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
								HttpServletResponse response, Integer id,String note) throws IOException {
							String r = "数据有误" ;
							try {
								BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
								params.put("operator", bossUser.getRealName()) ;
								params.put("orderId", id.toString()) ;
								params.put("note", note) ;
								params.put("opSrc", "备注") ;
								params.put("createTime", DateUtils.formatDate(new Date())) ;
								orderService.note(params);
								r = "1" ;
							} catch (Exception e) {
						}
					 outText(r, response);
		}
							
	
			 // 用于寄件单查询
			@RequestMapping(value = { "/sendlist" })
			public String sendlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
				System.out.println("**************order list begin***********************"+System.currentTimeMillis());
				Long begin = System.currentTimeMillis() ;
				String orderNos = params.get("orderNo") ;
			
				if (!StringUtil.isEmptyWithTrim(orderNos)) {
					String o = "'0'" ;
					String[] ods = orderNos.split("\r\n") ;
					for (int i = 0; i < ods.length; i++) {
						o=o+ ",'"+ods[i]+"'" ;
					}
					params.put("orderNos", o) ;
				}
				
				String monthSettleNo = params.get("monthSettleNo") ;
				if (!StringUtils.isEmptyWithTrim(monthSettleNo)) {
					if (monthSettleNo.contains(")")) {
						params.put("monthSettleNo", monthSettleNo.substring(0,monthSettleNo.indexOf("("))) ;
					}
				}
				Date nowDate = new Date() ;
				String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 0, 0),"yyyy-MM-dd") ;
				String endTime =  DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 0, 0),"yyyy-MM-dd") ;
				if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
					params.put("createTimeBegin", beginTime) ;
				}
				if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
					endTime = params.get("createTimeEnd") ;
				}
				params.put("createTimeEnd", endTime+" 23:59:59") ;
				if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					String substationNo ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo);
					if (SecurityUtils.getSubject().isPermitted("ORDER_ASSIGN_")) {
						params.put("orderAssign", "1") ;
					}
				}
				if ("NONE".equals(params.get("asign"))) {
					params.put("noneAssign", "1") ;
				}
				if ("SUBSTATION".equals(params.get("asign"))) {
					params.put("assignTos", "1") ;
				}
				if ("COURIER".equals(params.get("asign"))) {
					params.put("assignToc", "1") ;
				}
				
				
				params.put("el", "1") ;   //去除未取件订单
				params.put("zid", "1") ;   //去除子单
				
				Map<String, String> pMap = new HashMap<String, String>(params) ;
				pMap.put("takeTimeBegin", params.get("createTimeBegin")) ;
				pMap.put("takeTimeEnd", params.get("createTimeEnd")) ;
				pMap.remove("createTimeBegin") ;
				pMap.remove("createTimeEnd") ;
				
				//第一次默认为空
				PageInfo<Map<String, Object>> orderList = orderService.list(pMap,getPageInfo(cpage));
				List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
				
				params.put("userNo", ((BossUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
				params.put("tab", "order_sendlist");
				List<TableFiledSort> tableFieldSorts = tableFiledSortService.selectList(params);
				if(tableFieldSorts==null||tableFieldSorts.size()==0){
					params.put("userNo", "");
					tableFieldSorts = tableFiledSortService.selectList(params);
				}
				
				
				model.put("orderList", orderList) ;
				model.put("substations", substations);
				params.put("createTimeEnd", endTime) ;
				params.put("monthSettleNo", monthSettleNo) ;
				model.put("params", params) ;
				model.put("tableFieldSorts", tableFieldSorts);
				//setModelCour(model, "Y", courierService) ;
				System.out.println("**************order list end***********************"+System.currentTimeMillis());
				Long end = System.currentTimeMillis() ;
				System.out.println("**************order list 耗时***********************"+(end-begin)+"ms");
				return "order_ext/sendlist";
			}				
			

			 // 用于寄件单审核列表
			@RequestMapping(value = { "/selist" })
			public String selist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
				System.out.println("**************order list begin***********************"+System.currentTimeMillis());
				Long begin = System.currentTimeMillis() ;
				String orderNos = params.get("orderNo") ;
			
				if (!StringUtil.isEmptyWithTrim(orderNos)) {
					String o = "'0'" ;
					String[] ods = orderNos.split("\r\n") ;
					for (int i = 0; i < ods.length; i++) {
						o=o+ ",'"+ods[i]+"'" ;
					}
					params.put("orderNos", o) ;
				}
				
				String monthSettleNo = params.get("monthSettleNo") ;
				if (!StringUtils.isEmptyWithTrim(monthSettleNo)) {
					if (monthSettleNo.contains(")")) {
						params.put("monthSettleNo", monthSettleNo.substring(0,monthSettleNo.indexOf("("))) ;
					}
				}
				Date nowDate = new Date() ;
				String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
				String endTime =  DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
				if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
					if (StringUtils.isEmptyWithTrim(params.get("examineStatus"))) {
						params.put("examineStatus", "NONE") ;
					}
					params.put("createTimeBegin", beginTime) ;
				}
				if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
					endTime = params.get("createTimeEnd") ;
				}
				params.put("createTimeEnd", endTime+" 23:59:59") ;
				if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					String substationNo ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo);
					if (SecurityUtils.getSubject().isPermitted("ORDER_ASSIGN_")) {
						params.put("orderAssign", "1") ;
					}
				}
				if ("NONE".equals(params.get("asign"))) {
					params.put("noneAssign", "1") ;
				}
				if ("SUBSTATION".equals(params.get("asign"))) {
					params.put("assignTos", "1") ;
				}
				if ("COURIER".equals(params.get("asign"))) {
					params.put("assignToc", "1") ;
				}
				
				Map<String, String> map = new HashMap<String, String>() ;
				/*PageInfo<Map<String, Object>> mList = mobileUserService.muserList(map,getPageInfo(1,5000)) ;
				if (mList!=null) {
					model.put("mlist", JsonUtil.toJson(mList.getList())) ;
				}else {
					model.put("mlist",mList) ;
				}*/
				params.put("el", "1") ;   //去除未取件订单
				params.put("zid", "1") ;   //去除子单
				
				Map<String, String> pMap = new HashMap<String, String>(params) ;
				pMap.put("takeTimeBegin", params.get("createTimeBegin")) ;
				pMap.put("takeTimeEnd", params.get("createTimeEnd")) ;
				pMap.remove("createTimeBegin") ;
				pMap.remove("createTimeEnd") ;
				
				//第一次默认为空
				PageInfo<Map<String, Object>> orderList = new PageInfo<Map<String,Object>>(null) ;
				if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
					orderList = orderService.list(pMap,getPageInfo(cpage,20000));
				}else {
					params.put("ff", "1") ;
				}
				
				params.put("userNo", ((BossUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
				params.put("tab", "order_selist");
				List<TableFiledSort> tableFieldSorts = tableFiledSortService.selectList(params);
				if(tableFieldSorts==null||tableFieldSorts.size()==0){
					params.put("userNo", "");
					tableFieldSorts = tableFiledSortService.selectList(params);
				}
				
				List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
				model.put("orderList", orderList) ;
				model.put("substations", substations);
				params.put("createTimeEnd", endTime) ;
				params.put("monthSettleNo", monthSettleNo) ;
				model.put("params", params) ;
				model.put("tableFieldSorts",tableFieldSorts);
				//setModelCour(model, "Y", courierService) ;
				System.out.println("**************order list end***********************"+System.currentTimeMillis());
				Long end = System.currentTimeMillis() ;
				System.out.println("**************order list 耗时***********************"+(end-begin)+"ms");
				return "order_ext/selist";
			}
			
			

			 // 用于寄件单审核统计
			@RequestMapping(value = { "/sendEcount" })
			public String sendEcount(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response) {
				System.out.println("**************sendEcountount begin***********************"+System.currentTimeMillis());
				Long begin = System.currentTimeMillis() ;
				String orderNos = params.get("orderNo") ;
			
				if (!StringUtil.isEmptyWithTrim(orderNos)) {
					String o = "'0'" ;
					String[] ods = orderNos.split("\r\n") ;
					for (int i = 0; i < ods.length; i++) {
						o=o+ ",'"+ods[i]+"'" ;
					}
					params.put("orderNos", o) ;
				}
				
				String monthSettleNo = params.get("monthSettleNo") ;
				if (!StringUtils.isEmptyWithTrim(monthSettleNo)) {
					if (monthSettleNo.contains(")")) {
						params.put("monthSettleNo", monthSettleNo.substring(0,monthSettleNo.indexOf("("))) ;
					}
				}
				Date nowDate = new Date() ;
				String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
				String endTime =  DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
				if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
					if (StringUtils.isEmptyWithTrim(params.get("examineStatus"))) {
						params.put("examineStatus", "NONE") ;
					}
					params.put("createTimeBegin", beginTime) ;
				}
				if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
					endTime = params.get("createTimeEnd") ;
				}
				params.put("createTimeEnd", endTime+" 23:59:59") ;
				if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					String substationNo ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo);
					if (SecurityUtils.getSubject().isPermitted("ORDER_ASSIGN_")) {
						params.put("orderAssign", "1") ;
					}
				}
				if ("NONE".equals(params.get("asign"))) {
					params.put("noneAssign", "1") ;
				}
				if ("SUBSTATION".equals(params.get("asign"))) {
					params.put("assignTos", "1") ;
				}
				if ("COURIER".equals(params.get("asign"))) {
					params.put("assignToc", "1") ;
				}
				params.put("el", "1") ;   //去除未取件订单
				params.put("zid", "1") ;   //去除子单
				List<Map<String, Object>> orderList = orderService.sendEcount(params) ;
				model.put("orderList", orderList) ;
				
				Long end = System.currentTimeMillis() ;
				System.out.println("**************sendEcountount 耗时***********************"+(end-begin)+"ms");
				return "order_ext/sendEcount";
			}				
			
		
			
			// 物流详情
			@RequestMapping(value = "/edit_track")
			public String editTrack(@RequestParam Map<String, String> params,final ModelMap model) {
				
			   String orderNo = params.get("orderNo") ;
			   Map<String, Object> orderMap =  orderService.findByOrderNo(orderNo) ;
			   Map<String, Object> signMap =  orderService.findSignByOrderNo(orderNo) ;
			   
			   if ("PASS".equals(orderMap.get("examine_status"))) {
				 params.put("exam", "pass") ;
			   }else {
				   params.put("exam", "none") ;
			   }
			   if (StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("take_order_time")))) {
				   //params.put("tdate", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm")) ;
				   params.put("tdate",DateUtils.formatDate((Date) orderMap.get("create_time"),"yyyy-MM-dd HH:mm")) ;
			   }else {
				   params.put("tdate",DateUtils.formatDate((Date) orderMap.get("take_order_time"),"yyyy-MM-dd HH:mm")) ;
			   }
			   if (StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("send_order_time")))) {
				   params.put("sdate", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm")) ;
			   }else {
				   params.put("sdate",DateUtils.formatDate((Date) orderMap.get("send_order_time"),"yyyy-MM-dd HH:mm")) ;
			   }
			   params.put("orderId", orderMap.get("id").toString()) ;
			  // List<Map<String, Object>> noteList =  orderService.getNoteList(params) ;
			   String lgcOrderNo = StringUtils.nullString(orderMap.get("lgc_order_no")) ;
			   List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>() ;
			   String ischeck = "0" ;
				try {
						dataList = orderTrackService.getByOrderNo(orderNo, true) ;
						if (dataList==null||dataList.size()<1) {
							dataList = new ArrayList<Map<String,Object>>() ;
							Map<String, Object> map = new HashMap<String, Object>() ;
							map.put("time", "") ;	
							map.put("location", "") ;
							map.put("context", "无此物流单号信息！") ;
							map.put("ftime", "") ;
							dataList.add(map);
						}
						if ("3".equals(orderMap.get("status").toString())||"4".equals(orderMap.get("status").toString())||"5".equals(orderMap.get("status").toString())||"6".equals(orderMap.get("status").toString())) {
							ischeck = "1" ;
						}
				} catch (Exception e) {
					dataList.clear();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("time", "");
					map.put("location", "");
					map.put("context", "无此物流单号信息！");
					map.put("ftime", "");
					dataList.add(map);
					e.printStackTrace();
			}
				
				if (orderMap.get("pay_type")!=null&&!StringUtils.isEmptyWithTrim(orderMap.get("pay_type").toString())) {
					if ("CASH".equals(orderMap.get("pay_type").toString())) {
						orderMap.put("pay_type", "现金");
					}
					if ("MONTH".equals(orderMap.get("pay_type").toString())) {
						orderMap.put("pay_type", "月结");
					}
					if ("HUIYUAN".equals(orderMap.get("pay_type").toString())) {
						orderMap.put("pay_type", "会员卡");
					}
				}
				
				
				if (orderMap.get("req_rece")!=null&&!StringUtils.isEmptyWithTrim(orderMap.get("req_rece").toString())) {
					if ("Y".equals(orderMap.get("req_rece").toString())) {
						orderMap.put("req_rece", "1");
					}else {
						orderMap.put("req_rece", "0");
					}
				}
				
				
				if (signMap!=null) {
					if (signMap.get("si_pay_type")!=null&&!StringUtils.isEmptyWithTrim(signMap.get("si_pay_type").toString())) {
						if ("CASH".equals(signMap.get("si_pay_type").toString())) {
							signMap.put("pay_type", "现金");
						}
						if ("MONTH".equals(signMap.get("si_pay_type").toString())) {
							signMap.put("pay_type", "月结");
						}
						if ("HUIYUAN".equals(signMap.get("si_pay_type").toString())) {
							signMap.put("pay_type", "会员卡");
						}
					}
				}
				
			/*	PageInfo<Map<String, Object>> mList = mobileUserService.muserList(params,getPageInfo(1,5000)) ;
				if (mList!=null) {
					model.put("mlist", JsonUtil.toJson(mList.getList())) ;
				}else {
					model.put("mlist",mList) ;
				}
				PageInfo<Map<String, Object>> codList = codSettleUserService.list(params,getPageInfo(1,5000)) ;
				if (codList!=null) {
					model.put("codList", JsonUtil.toJson(codList.getList())) ;
				}else {
					model.put("codList",codList) ;
				}
				setModelCour(model, "Y", courierService) ;
				setModelSub(model, "Y") ;
				setModelSub(model, "N") ;	*/	
				
			model.put("lgcOrderNo", lgcOrderNo);
			model.put("orderMap", orderMap) ;
			model.put("signMap", signMap) ;
			model.put("ischeck", ischeck) ;
			model.put("take_plane", orderMap.get("take_plane")) ;
			model.put("send_plane", orderMap.get("send_plane")) ;
			Collections.reverse(dataList);
			model.put("trackList", dataList);
			model.put("params", params);
			
			if ("Y".equals(params.get("sign"))) {
				//签收单编辑
				return "order_ext/edit_track_sign";
			}else {
				return "order_ext/edit_track";
			}
				
			}		
		
			// 用于 录入保存
			@RequestMapping(value = { "/take_update" })
			public void take_update(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response,OrderInfo orderInfo) throws SQLException {
				try {
					Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcNo"), params.get("lgcOrderNo"), null) ;
					  if ("Y".equals(params.get("sign"))) {
						     orderInfo.setSendOrderTime(params.get("tdate")+":00");
					    	if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("take_order_time")))) {
					    		orderInfo.setTakeOrderTime(StringUtils.nullString(orderMap.get("take_order_time")));
					    		orderInfo.setCreateTime(StringUtils.nullString(orderMap.get("take_order_time")));
							}
					    }else {
					    	orderInfo.setCreateTime(params.get("tdate")+":00");
					    	orderInfo.setTakeOrderTime(params.get("tdate")+":00");
					    	if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("send_order_time")))) {
					    		orderInfo.setSendOrderTime(StringUtils.nullString(orderMap.get("send_order_time")));
							}
						}
					    
					    String orderNote = StringUtils.nullString(params.get("orderNote")) ;
					    if (StringUtils.isEmptyWithTrim(orderNote)) {
						  orderInfo.setOrderNote(StringUtils.nullString(orderMap.get("orderNote")));
					    }
					  
						boolean first = true ;  //是否已经收件
						orderInfo.setPayStatus(StringUtils.nullString(orderMap.get("pay_status")));
						orderInfo.setFpayStatus(StringUtils.nullString(orderMap.get("fpay_status")));
						orderInfo.setCpayStatus(StringUtils.nullString(orderMap.get("cpay_status")));
						
		                 orderInfo.setOrderNo(orderMap.get("order_no").toString());
		                 if(!StringUtils.isEmptyWithTrim(params.get("itemWeight_"))){
		                	 orderInfo.setItemWeight(Float.valueOf(params.get("itemWeight_"))); 
		                 }
		                 if(first&&!StringUtils.isEmptyWithTrim(params.get("freight_"))){
		                	 orderInfo.setFreight(Float.valueOf(params.get("freight_"))); 
		                 }else {
							orderInfo.setFreight(Float.valueOf(orderMap.get("freight").toString()));
						}
		                 if(first&&!StringUtils.isEmptyWithTrim(params.get("payAcount_"))){
		                	 orderInfo.setPayAcount(Float.valueOf(params.get("payAcount_"))); 
		                 }else {
								orderInfo.setPayAcount(Float.valueOf(orderMap.get("pay_acount").toString()));
							}
		                 
		                 if(first){
		                	 if (!StringUtils.isEmptyWithTrim(params.get("cpay_"))) {
		                		 orderInfo.setCpay(Float.valueOf(params.get("cpay_"))); 
							}else {
								orderInfo.setCpay(0); 
							}
		                 }else {
								orderInfo.setCpay(Float.valueOf(orderMap.get("cpay").toString()));
							}
		                 
		                 if(first){
		                	 if (!StringUtils.isEmptyWithTrim(params.get("vpay_"))) {
		                		 orderInfo.setVpay(Float.valueOf(params.get("vpay_"))); 
							}else {
								orderInfo.setVpay(0); 
							}
		                 }else {
								orderInfo.setVpay(Float.valueOf(orderMap.get("vpay").toString()));
							}
		                 
		                 if(first){
		                	 if (!StringUtils.isEmptyWithTrim(params.get("goodPrice_"))) {
		                		 orderInfo.setGoodPrice(Float.valueOf(params.get("goodPrice_"))); 
		                		 if (orderInfo.getGoodPrice()>0) {
		                			 orderInfo.setCod(1);
								}
							}else {
								 orderInfo.setGoodPrice(0); 
							}
		                 }else {
								orderInfo.setGoodPrice(Float.valueOf(orderMap.get("good_price").toString()));
							}
		                 
		                 if(first){
		                	 if (!StringUtils.isEmptyWithTrim(params.get("goodValuation_"))) {
		                		 orderInfo.setGoodValuation(Float.valueOf(params.get("goodValuation_"))); 
							}else {
								 orderInfo.setGoodValuation(0); 
							}
		                 }else {
								orderInfo.setGoodValuation(Float.valueOf(orderMap.get("good_valuation").toString()));
							}
		                 /////////////////////////////////////////////以下判断是否需要扣费
		                 if (orderMap.get("fpay_status")!=null) {
		                	  orderInfo.setFpayStatus(orderMap.get("fpay_status").toString());
						  }
		                 if (orderMap.get("cpay_status")!=null) {
		                	 orderInfo.setCpayStatus(orderMap.get("cpay_status").toString());
						  }
		                 
		                 String tnpay = params.get("tnpay") ;
		                 if (StringUtils.isEmptyWithTrim(tnpay)) {
							tnpay = "0" ;
						 }
		                 BigDecimal pay = new BigDecimal(tnpay) ;
		                 String snpay = "0" ;
		                 
		              
		                 float mpay = 0 ;
		                 if (first) {
		                	 String codName = params.get("codName") ;
	                		  if (!StringUtils.isEmptyWithTrim(codName)) {
	                			  Map<String, Object> codSettleUser =  codSettleUserService.getCuserByNo(codName) ;

	                			  if (codSettleUser==null) {
		                			  outText("录入失败,代收客户号不存在", response);
			                		  return ;
								  } 
		                		  if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(codSettleUser.get("cstype")))) {
		                			  CodRateType codRateType = codRateTypeService.getById(Integer.valueOf(codSettleUser.get("cstype").toString())) ;
					                  if (codRateType!=null) {
					                	  orderInfo.setCodRate(codRateType.getDiscount().toString());
									}
								 }  
							  } 

						 if ("MONTH".equals(params.get("payType"))) {
											 String monthSettleNo = params.get("monthSettleNo") ;
					                		  if (StringUtils.isEmptyWithTrim(monthSettleNo)) {
					                			  outText("录入失败,请输入月结号", response);
						                		  return ;
											  } 
					                		  Map<String, Object> monthSettleUser = mobileUserService.getMuserByNo(monthSettleNo) ;  
					                		  if (monthSettleUser==null) {
					                			  outText("录入失败,月结号不存在", response);
						                		  return ;
											  } 
					                		  if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(monthSettleUser.get("mstype")))) {
					                			  MonthSettleType monthSettleType = monthSettleTypeService.getById(Integer.valueOf(monthSettleUser.get("mstype").toString())) ;
								                  if (monthSettleType!=null) {
								                	  orderInfo.setMonthDiscount(monthSettleType.getDiscount().toString());
												}
											 }
					                		mpay = pay.floatValue() ;  
										 }
										
										if ("1".equals(orderInfo.getFreightType())) {
											orderInfo.setFpayStatus("SUCCESS");
										}else {
										   orderInfo.setFpayStatus(orderMap.get("fpay_status")==null?"":orderMap.get("fpay_status").toString());
						                }
								
                        }else {
							orderInfo.setPayType(null);
							orderInfo.setMonthSettleNo(null);
							//snpay = String.valueOf((orderInfo.getGoodPrice() + orderInfo.getFreight() + orderInfo.getVpay())) ;
							orderInfo.setFpayStatus(orderMap.get("fpay_status")==null?"":orderMap.get("fpay_status").toString());
						}
		                 if ("1".equals(orderInfo.getFreightType())) {
		                	 if (orderInfo.getGoodPrice()>0) {
									snpay = String.valueOf(orderInfo.getGoodPrice()) ;
							}
						}else {
							snpay = String.valueOf((orderInfo.getGoodPrice() + orderInfo.getFreight() + orderInfo.getVpay())) ;
						}
		                orderInfo.setSnpay(snpay);
		                if (StringUtils.isEmptyWithTrim(orderInfo.getReqRece())) {
							orderInfo.setReqRece("0");
						}
		                	 orderInfo.setMpay(mpay);
		                	 orderInfo.setTakeTime(StringUtils.nullString(orderMap.get("take_order_time")));
		                	// orderInfo.setCreateTime(params.get("tdate")+":00");
		                //修改签收单
		                	
		                if ("Y".equals(params.get("sign"))) {
							orderService.editSignUpdate(orderInfo) ;
							if ("2".equals(orderInfo.getFreightType())&&"2".equals(StringUtils.nullString(orderMap.get("freight_type")))) {
								
							}else {
								//orderInfo.setFreightType(StringUtils.nullString(orderMap.get("freight_type")));
							//	orderInfo.setPayType(StringUtils.nullString(orderMap.get("pay_type")));
							}
							//	orderInfo.setMonthSettleNo(StringUtils.nullString(orderMap.get("month_settle_no")));
							orderInfo.setTakeCourierNo(StringUtils.nullString(orderMap.get("take_courier_no")));
							orderInfo.setSubStationNo(StringUtils.nullString(orderMap.get("sub_station_no")));
							 Courier courier =courierService.getCourierByNo(orderInfo.getSendCourierNo()) ;
		                	 if (courier!=null) {
								orderInfo.setSendSubstationNo(courier.getSubstationNo());
							 }else {
								 orderInfo.setSendSubstationNo(StringUtils.nullString(orderMap.get("send_substation_no")));
							 }
						}else {
							orderInfo.setSendCourierNo(StringUtils.nullString(orderMap.get("send_courier_no")));
							orderInfo.setSendSubstationNo(StringUtils.nullString(orderMap.get("send_substation_no")));
							 Courier courier =courierService.getCourierByNo(orderInfo.getTakeCourierNo()) ;
		                	 if (courier!=null) {
								orderInfo.setSubStationNo(courier.getSubstationNo());
							 }else {
								 orderInfo.setSubStationNo(StringUtils.nullString(orderMap.get("sub_station_no")));
							 }
						}	
		                orderInfo.setZidanOrder(orderInfo.getZidanOrder().replace("，", ","));   //全角替换
		                int zidan = orderInfo.getZidanOrder().split(",").length ;
		                orderInfo.setZidanNumber(zidan);
		                
		                if (StringUtils.isEmptyWithTrim(orderInfo.getZidanOrder())) {
							orderInfo.setItemCount("1");
						}else {
							orderInfo.setItemCount((zidan+1)+"");
						}
		                
		                orderService.editUpdate(orderInfo) ;
		                orderService.editOrderRecord(orderInfo) ;
		                
		                //增加备注记录
		                 if (!StringUtils.isEmptyWithTrim(orderNote)) {
		                	 String id = StringUtils.nullString(orderMap.get("id")) ;
			                    Map<String, String> nMap = new HashMap<String, String>() ;
			                    nMap.put("operator", Constants.getUser().getRealName()) ;
			                    nMap.put("orderId", id.toString()) ;
			                    nMap.put("note", orderNote) ;
			                    nMap.put("opSrc", "备注") ;
			                    nMap.put("createTime", DateUtils.formatDate(new Date())) ;
								orderService.note(nMap);
						  }
		                
		                
					    outText("1", response);
				} catch (Exception e) {
                 e.printStackTrace();
                 outText("录入失败,数据有误", response);
				}
			}		
			
		
			
			 // 用于签收审核列表
			@RequestMapping(value = { "/relist" })
			public String relist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
				//String sno = params.get("substationNo") ;
				String sno = params.get("sendSno") ;
				Date nowDate = new Date() ;
				String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
				String endTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
				String monthSettleNo = params.get("monthSettleNo") ;
				if (!StringUtils.isEmptyWithTrim(monthSettleNo)) {
					if (monthSettleNo.contains(")")) {
						params.put("monthSettleNo", monthSettleNo.substring(0,monthSettleNo.indexOf("("))) ;
					}
				}
				
				if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
					if (StringUtils.isEmptyWithTrim(params.get("examineStatus"))) {
						params.put("examineStatus", "NONE") ;
					}
					params.put("createTimeBegin", beginTime) ;
					
				}
				if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
					endTime = params.get("createTimeEnd") ;
				}
				
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}
				
				if(StringUtils.isEmptyWithTrim(substationNo)){
					params.put("substationNo", "0000") ;
				}else{
					if(sno==null||"".equals(sno)){
						params.put("substationNo", substationNo) ;
					}else{
						if(substationNo.contains(sno)){
							params.put("substationNo", sno) ;
						}else{
							params.put("substationNo", "0000") ;
						}
					}
				}
//				if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
//					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
//					String substationNo ;
//					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
//						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
//					}else {
//						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
//					}
//					params.put("substationNo", substationNo);
//				}
				
				params.put("createTimeEnd", endTime+" 23:59:59") ;
				setModelSub(model, "N",request) ;
				
				params.put("zid", "1") ;   //去除子单
				//第一次默认为空
				PageInfo<Map<String, Object>> orderList = new PageInfo<Map<String,Object>>(null) ;
				if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
					orderList =  orderService.sendOrder(params,getPageInfo(cpage,20000)) ;
				}else {
					params.put("ff", "1") ;
				}
				
				params.put("userNo", ((BossUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
				params.put("tab", "order_relist");
				List<TableFiledSort> tableFieldSorts = tableFiledSortService.selectList(params);
				if(tableFieldSorts==null||tableFieldSorts.size()==0){
					params.put("userNo", "");
					tableFieldSorts = tableFiledSortService.selectList(params);
				}
				
				params.put("createTimeEnd", endTime) ;
				params.put("monthSettleNo", monthSettleNo) ;
				params.put("substationNo", sno) ;
				model.put("params", params);
				model.put("tableFieldSorts", tableFieldSorts);
				model.put("list", orderList);
				return "order_ext/relist";
			}		
		
			
			 // 用于签收审核列表统计
			@RequestMapping(value = { "/revEcount" })
			public String revEcount(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response) throws IOException {
				Date nowDate = new Date() ;
				String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
				String endTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
				String monthSettleNo = params.get("monthSettleNo") ;
				if (!StringUtils.isEmptyWithTrim(monthSettleNo)) {
					if (monthSettleNo.contains(")")) {
						params.put("monthSettleNo", monthSettleNo.substring(0,monthSettleNo.indexOf("("))) ;
					}
				}
				
				if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
					if (StringUtils.isEmptyWithTrim(params.get("examineStatus"))) {
						params.put("examineStatus", "NONE") ;
					}
					params.put("createTimeBegin", beginTime) ;
					
				}
				if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
					endTime = params.get("createTimeEnd") ;
				}
				if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					String substationNo ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo);
				}
				params.put("createTimeEnd", endTime+" 23:59:59") ;
				params.put("zid", "1") ;   //去除子单
				List<Map<String, Object>> orderList = orderService.revEcount(params) ;
				model.put("orderList", orderList) ;
				return "order_ext/sendEcount";
			}	
			
			
			
			// 用于签收单审核保存
			@RequestMapping(value = { "/examine_save" })
			public void examine_save(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response) throws SQLException {
				try {
					Map<String, String> pMap = new HashMap<String, String>() ;
					pMap.put("ids", params.get("ids")) ;
					 boolean same = true ;
					 if ("PASS".equals(params.get("examineStatus"))) {
						/* pageList = orderService.examineOrder(pMap, getPageInfo(1,50)) ;
						 for (Map<String, Object> map:pageList.getList()) {
								if (!map.get("freight_type").equals(map.get("si_freight_type"))) {
									same = false ;
									break ;
								 }
								if (!map.get("freight").equals(map.get("si_freight"))) {
									same = false ;
									break ;
								 }
								if (!map.get("good_price").equals(map.get("si_good_price"))) {
									same = false ;
									break ;
								 }
								if (!map.get("good_valuation").equals(map.get("si_good_valuation"))) {
									same = false ;
									break ;
								 }
								if (!map.get("vpay").equals(map.get("si_vpay"))) {
									same = false ;
									break ;
								 }
							 }*/
					}
					 BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					 if (same) {
						 params.put("examiner", bossUser.getUserName()) ;   // 审核人
						 params.put("etime", DateUtils.formatDate(new Date())) ;    //审核时间
						 orderService.signExamine(params) ;            //签收审核
						 params.put("ids", params.get("oids")) ;
						 orderService.signExamineOrder(params) ;
						 if (!StringUtils.isEmptyWithTrim(params.get("pids"))) {
							  pMap.put("examiner", bossUser.getUserName()) ;   // 审核人
							  pMap.put("etime", DateUtils.formatDate(new Date())) ;    //审核时间
							  pMap.put("ids", params.get("pids")) ;
							  pMap.put("examineStatus", "INIT") ;
							  orderService.signExamine(pMap) ;  
							  pMap.put("ids", params.get("poids")) ;
							  orderService.signExamineOrder(pMap) ;
						  }
						 
						 outText("1", response);
					 }else {
						 outText("当前审核包含有收件与签收不一致的数据，请修改", response);
					}
		            
				} catch (Exception e) {
                 e.printStackTrace();
                 outText("保存失败", response);
				}
			}	
			
			
	
			
			
			// 物流详情
			@RequestMapping(value = "/oedit")
			public String oedit(@RequestParam Map<String, String> params,final ModelMap model) {
				String lgcOrderNo = StringUtils.nullString(params.get("lgcOrderNo")) ;
				if (StringUtils.isEmptyWithTrim(lgcOrderNo)) {
					return "order_ext/order_edit";
				}
				
			   Map<String, Object> orderMap =  orderService.findByLgcOrderNo(lgcOrderNo) ;
			   if (orderMap==null) {
				   return "order_ext/order_edit";
		      }
			   
			   
			   if ("PASS".equals(orderMap.get("examine_status"))) {
				 params.put("exam", "pass") ;
			   }else {
				   params.put("exam", "none") ;
			   }
			   if (StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("create_time")))) {
				   //params.put("tdate", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm")) ;
				   params.put("tdate","") ;
			   }else {
				   params.put("tdate",DateUtils.formatDate((Date) orderMap.get("create_time"),"yyyy-MM-dd HH:mm")) ;
			   }
			   if (StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("send_order_time")))) {
				  // params.put("sdate", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm")) ;
				   params.put("sdate", "") ;
			   }else {
				   params.put("sdate",DateUtils.formatDate((Date) orderMap.get("send_order_time"),"yyyy-MM-dd HH:mm")) ;
			   }
			   params.put("orderId", orderMap.get("id").toString()) ;

				
				if (orderMap.get("pay_type")!=null&&!StringUtils.isEmptyWithTrim(orderMap.get("pay_type").toString())) {
					if ("CASH".equals(orderMap.get("pay_type").toString())) {
						orderMap.put("pay_type", "现金");
					}
					if ("MONTH".equals(orderMap.get("pay_type").toString())) {
						orderMap.put("pay_type", "月结");
					}
					if ("HUIYUAN".equals(orderMap.get("pay_type").toString())) {
						orderMap.put("pay_type", "会员卡");
					}
				}
				
				if (orderMap.get("req_rece")!=null&&!StringUtils.isEmptyWithTrim(orderMap.get("req_rece").toString())) {
					if ("Y".equals(orderMap.get("req_rece").toString())) {
						orderMap.put("req_rece", "1");
					}else {
						orderMap.put("req_rece", "0");
					}
				}
				
			model.put("lgcOrderNo", lgcOrderNo);
			model.put("orderMap", orderMap) ;
			model.put("params", params);
			
		   return "order_ext/order_edit";
				
			}			
	
			
			
			// 用于 录入保存
						@RequestMapping(value = { "/order_edit_save" })
						public void order_edit_save(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
								HttpServletResponse response,OrderInfo orderInfo) throws SQLException {
							try {
								Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcOrderNo")) ;
								  if (orderMap==null) {
									  outText("录入失败, 运单号不存在", response);
			                		  return ;
							      }
								
								
								if (!StringUtils.isEmptyWithTrim(params.get("tdate"))) {
									orderInfo.setCreateTime(params.get("tdate")+":00"); 
									orderInfo.setTakeOrderTime(params.get("tdate")+":00");
								}else {
									orderInfo.setCreateTime(StringUtils.nullString(orderMap.get("take_order_time")));
									orderInfo.setTakeOrderTime(StringUtils.nullString(orderMap.get("take_order_time")));
								}
								if (!StringUtils.isEmptyWithTrim(params.get("sdate"))) {
									orderInfo.setSendOrderTime(params.get("sdate")+":00");
								}else {
									orderInfo.setSendOrderTime(StringUtils.nullString(orderMap.get("send_order_time")));
								}
								if (StringUtils.isEmptyWithTrim(orderInfo.getCreateTime())) {
									orderInfo.setCreateTime(null);
								}
								if (StringUtils.isEmptyWithTrim(orderInfo.getTakeOrderTime())) {
									orderInfo.setTakeOrderTime(null);
								}
								if (StringUtils.isEmptyWithTrim(orderInfo.getSendOrderTime())) {
									orderInfo.setSendOrderTime(null);
								}
								
								String orderNote = StringUtils.nullString(params.get("orderNote")) ;
							    if (StringUtils.isEmptyWithTrim(orderNote)) {
								  orderInfo.setOrderNote(StringUtils.nullString(orderMap.get("orderNote")));
							    }
								
								if (!StringUtils.isEmptyWithTrim(orderInfo.getTakeCourierNo())) {
									 Courier courier =courierService.getCourierByNo(orderInfo.getTakeCourierNo()) ;
				                	 if (courier!=null) {
										orderInfo.setSubStationNo(courier.getSubstationNo());
									 }else {
										  outText("录入失败, 取件员不存在", response);
				                		  return ;
									 }
								}else {
									orderInfo.setTakeCourierNo(StringUtils.nullString(orderMap.get("take_courier_no")));
									orderInfo.setSubStationNo(StringUtils.nullString(orderMap.get("sub_station_no")));
								}
								
								
								if (!StringUtils.isEmptyWithTrim(orderInfo.getSendCourierNo())) {
									 Courier courier =courierService.getCourierByNo(orderInfo.getSendCourierNo()) ;
				                	 if (courier!=null) {
										orderInfo.setSendSubstationNo(courier.getSubstationNo());
									 }else {
										  outText("录入失败, 派件员不存在", response);
				                		  return ;
									 }
								}else {
									orderInfo.setSendCourierNo(StringUtils.nullString(orderMap.get("send_courier_no")));
									orderInfo.setSendSubstationNo(StringUtils.nullString(orderMap.get("send_substation_no")));
								}
								
							  orderInfo.setOrderNo(orderMap.get("order_no").toString());
					                
					                 if(!StringUtils.isEmptyWithTrim(params.get("itemWeight_"))){
					                	 orderInfo.setItemWeight(Float.valueOf(params.get("itemWeight_"))); 
					                 }
					                 if(!StringUtils.isEmptyWithTrim(params.get("freight_"))){
					                	 orderInfo.setFreight(Float.valueOf(params.get("freight_"))); 
					                 }else {
										orderInfo.setFreight(Float.valueOf(orderMap.get("freight").toString()));
									 }
					                 
					                 if(!StringUtils.isEmptyWithTrim(params.get("payAcount_"))){
					                	 orderInfo.setPayAcount(Float.valueOf(params.get("payAcount_"))); 
					                 }else {
											orderInfo.setPayAcount(Float.valueOf(orderMap.get("pay_acount").toString()));
									}
					                 

                                     if (!StringUtils.isEmptyWithTrim(params.get("vpay_"))) {
					                		 orderInfo.setVpay(Float.valueOf(params.get("vpay_"))); 
										}else {
											orderInfo.setVpay(Float.valueOf(orderMap.get("vpay").toString()));
										}
					               
					      
					                if (!StringUtils.isEmptyWithTrim(params.get("goodPrice_"))) {
					                		 orderInfo.setGoodPrice(Float.valueOf(params.get("goodPrice_"))); 
					                		 if (orderInfo.getGoodPrice()>0) {
					                			 orderInfo.setCod(1);
											}
										}else {
											orderInfo.setGoodPrice(Float.valueOf(orderMap.get("good_price").toString()));
								      }
					               
					                  if (!StringUtils.isEmptyWithTrim(params.get("goodValuation_"))) {
					                		 orderInfo.setGoodValuation(Float.valueOf(params.get("goodValuation_"))); 
										}else {
											orderInfo.setGoodValuation(Float.valueOf(orderMap.get("good_valuation").toString()));
										}

					        float tnpay = 0 ;
					        float snpay = orderInfo.getGoodPrice() ;
					        float mpay = 0 ;
					        
					       if ("1".equals(orderInfo.getFreightType())) {
							    tnpay = orderInfo.getFreight() + orderInfo.getVpay() ;
						    } else {
								snpay = orderInfo.getFreight() + orderInfo.getVpay() + orderInfo.getGoodPrice() ;
							}         
					        orderInfo.setTnpay(String.valueOf(tnpay));
					        orderInfo.setSnpay(String.valueOf(snpay));
					                  
                              String codName = params.get("codName") ;
				                		  if (!StringUtils.isEmptyWithTrim(codName)) {
				                			  Map<String, Object> codSettleUser =  codSettleUserService.getCuserByNo(codName) ;

				                			  if (codSettleUser==null) {
					                			  outText("录入失败,代收客户号不存在", response);
						                		  return ;
											  } 
					                		  if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(codSettleUser.get("cstype")))) {
					                			  CodRateType codRateType = codRateTypeService.getById(Integer.valueOf(codSettleUser.get("cstype").toString())) ;
								                  if (codRateType!=null) {
								                	  orderInfo.setCodRate(codRateType.getDiscount().toString());
												}
											 }  
										  } 

									 if ("MONTH".equals(params.get("payType"))) {
														 String monthSettleNo = params.get("monthSettleNo") ;
								                		  if (StringUtils.isEmptyWithTrim(monthSettleNo)) {
								                			  outText("录入失败,请输入月结号", response);
									                		  return ;
														  } 
								                		  Map<String, Object> monthSettleUser = mobileUserService.getMuserByNo(monthSettleNo) ;  
								                		  if (monthSettleUser==null) {
								                			  outText("录入失败,月结号不存在", response);
									                		  return ;
														  } 
								                		  if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(monthSettleUser.get("mstype")))) {
								                			  MonthSettleType monthSettleType = monthSettleTypeService.getById(Integer.valueOf(monthSettleUser.get("mstype").toString())) ;
											                  if (monthSettleType!=null) {
											                	  orderInfo.setMonthDiscount(monthSettleType.getDiscount().toString());
															}
														 }
								                		mpay = orderInfo.getFreight() ;  
								   }
													
							
					                orderInfo.setSnpay(String.valueOf(snpay));
					                if (StringUtils.isEmptyWithTrim(orderInfo.getReqRece())) {
										orderInfo.setReqRece("0");
									}
					              orderInfo.setMpay(mpay);
					                	
					                orderInfo.setZidanOrder(orderInfo.getZidanOrder().replace("，", ","));   //全角替换
					                int zidan = orderInfo.getZidanOrder().split(",").length ;
					                orderInfo.setZidanNumber(zidan);
					                
					                if (StringUtils.isEmptyWithTrim(orderInfo.getZidanOrder())) {
										orderInfo.setItemCount("1");
									}else {
										orderInfo.setItemCount((zidan+1)+"");
									}
					                
					                orderService.orderEditSave(orderInfo) ;
					                orderService.editOrderRecord(orderInfo) ;
					                
					                //增加备注记录
					                 if (!StringUtils.isEmptyWithTrim(orderNote)) {
					                	 String id = StringUtils.nullString(orderMap.get("id")) ;
						                    Map<String, String> nMap = new HashMap<String, String>() ;
						                    nMap.put("operator", Constants.getUser().getRealName()) ;
						                    nMap.put("orderId", id.toString()) ;
						                    nMap.put("note", orderNote) ;
						                    nMap.put("opSrc", "备注") ;
						                    nMap.put("createTime", DateUtils.formatDate(new Date())) ;
											orderService.note(nMap);
									  }
					                
								    outText("1", response);
							} catch (Exception e) {
			                 e.printStackTrace();
			                 outText("录入失败,数据有误", response);
							}
						}		
						
			
			
			
			
			
			
		@RequestMapping(value = { "/batchedit_order_init" })
		public String batchOrderEditInit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,OrderInfo orderInfo) throws SQLException {
			String ids = params.get("ids");
			PageInfo<Map<String, Object>> list = codRuleService.list(params,getPageInfo(100));
			
			for (Map<String, Object> m : list.getList()) {
				if(m.get("status").toString().equals("1")){
					model.put("codRule", m);
					break;
				}
			}
			
			PageInfo<Map<String, Object>> list1 = valuationRuleService.list(params,getPageInfo(100));

			for (Map<String, Object> m : list1.getList()) {
				if(m.get("status").toString().equals("1")){
					model.put("valRule", m);
					break;
				}
			}
			
			if(ids.length()>0 && ids.endsWith(",")){
				ids =ids.substring(0, ids.length()-1);
			}
			model.put("ids", ids);
			return "order_ext/batch_edit";
		}
		
		@RequestMapping(value = { "/batchedit_order" })
		public void batchOrderEdit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response) throws SQLException {
			String ids = params.get("ids");
			if(ids==null||"".equals(ids.trim())){
				outText("请至少选择一项进行修改", response);
				return;
			}
			
			if(params.get("good_price")!= null && !"".equals(params.get("good_price").toString().trim())){
				PageInfo<Map<String, Object>> list = codRuleService.list(params,getPageInfo(100));
				CodRule codRule = new CodRule();
				for (Map<String, Object> m : list.getList()) {
					if(m.get("status").toString().equals("1")){
						codRule.setMaxv(m.get("maxv").toString());
						codRule.setMinv(m.get("minv").toString());
						break;
					}
				}
				if(Double.parseDouble(params.get("good_price").toString())<Double.parseDouble(codRule.getMinv())){
					outText("代收款小于"+codRule.getMinv(), response);
					return;
				}
				if(Double.parseDouble(params.get("good_price").toString())>Double.parseDouble(codRule.getMaxv())){
					outText("代收款大于"+codRule.getMaxv(), response);
					return;
				}
			}
			if(params.get("good_valuation")!= null && !"".equals(params.get("good_valuation").toString().trim())){
				PageInfo<Map<String, Object>> list1 = valuationRuleService.list(params,getPageInfo(100));
				ValuationRule valRule =new ValuationRule();
				for (Map<String, Object> m : list1.getList()) {
					if(m.get("status").toString().equals("1")){
						valRule.setMaxv(m.get("maxv").toString());
						valRule.setMinv(m.get("minv").toString());
						valRule.setRate(m.get("rate").toString());
						break;
					}
				}
				if(Double.parseDouble(params.get("good_valuation").toString())<Double.parseDouble(valRule.getMinv())){
					outText("保价费小于"+valRule.getMinv(), response);
					return;
				}
				if(Double.parseDouble(params.get("good_valuation").toString())>Double.parseDouble(valRule.getMaxv())){
					outText("保价费大于"+valRule.getMaxv(), response);
					return;
				}
				params.put("vpay", new BigDecimal(params.get("good_valuation").toString()).multiply(new BigDecimal(valRule.getRate())).divide(new BigDecimal(1000)).toString());
			}
			
			if(params.get("freight_type")!=null && !"".equals(params.get("freight_type").toString().trim())){
				if((!"1".equals(params.get("freight_type").toString())&&!"2".equals(params.get("freight_type").toString()))){
					outText("付款人错误", response);
					return;
				}
			}
			
			if(params.get("pay_type")!=null && !"".equals(params.get("pay_type").toString().trim())){
				if(!"1".equals(params.get("pay_type").toString())&&!"2".equals(params.get("pay_type").toString())){
					outText("付款方式错误", response);
					return;
				}
				if("2".equals(params.get("pay_type").toString())&&params.get("month_settle_no")!=null){
					 Map<String, Object> monthSettleUser = mobileUserService.getMuserByNo(params.get("month_settle_no").toString()) ;  
	           		  if (monthSettleUser==null) {
	           			  outText("月结号不存在", response);
	               		  return ;
					  } 
				}
			}
			if("1".equals(params.get("pay_type").toString())){
				params.put("pay_type","CASH");
			}
			if("2".equals(params.get("pay_type").toString())){
				params.put("pay_type","MONTH");
			}
			if(params.get("take_courier_no")!=null && !"".equals(params.get("take_courier_no").toString().trim())){
				 Courier takecourier =courierService.getCourierByNo(params.get("take_courier_no").toString()) ;
		       	 if (takecourier!=null) {
						params.put("sub_station_no", takecourier.getSubstationNo());
					 }else {
						  outText("取件员不存在", response);
		       		  return ;
				 }
			}
			if(params.get("send_courier_no")!=null && !"".equals(params.get("send_courier_no").toString().trim())){
		       	Courier sendcourier =courierService.getCourierByNo(params.get("send_courier_no").toString()) ;
		       	 if (sendcourier!=null) {
						params.put("send_substation_no", sendcourier.getSubstationNo());
					 }else {
						  outText("派件员不存在", response);
		       		  return ;
				 }
			}
	       	orderService.updateBatchOrder(params);
			outText("修改成功", response);
		}
			
}
