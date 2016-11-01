package com.yogapay.boss.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.xerces.impl.xpath.regex.REUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.CodRateType;
import com.yogapay.boss.domain.ConsumeHistory;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.CourierCost;
import com.yogapay.boss.domain.DisUser;
import com.yogapay.boss.domain.DisUserBalance;
import com.yogapay.boss.domain.DiscountType;
import com.yogapay.boss.domain.ForwardOrder;
import com.yogapay.boss.domain.FranchiseRule;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.LgcAddr;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.MonthSettleType;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.enums.PayStatus;
import com.yogapay.boss.enums.TrackStatus;
import com.yogapay.boss.service.CodRateTypeService;
import com.yogapay.boss.service.CodSettleUserService;
import com.yogapay.boss.service.CourierCostRecordService;
import com.yogapay.boss.service.CourierCostService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.ForOrderService;
import com.yogapay.boss.service.FranOrderService;
import com.yogapay.boss.service.FranchiseRuleService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.MonthSettleService;
import com.yogapay.boss.service.MonthSettleTypeService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.service.WagesService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;
import com.yogapay.boss.utils.WeiXinUtil;

@Controller
@RequestMapping(value = "/franOrder")
public class FranOrderController extends BaseController {
			
	@Resource
	private FranchiseRuleService franchiseRuleService ;
	@Resource
	private FranOrderService franOrderService ;

				 // 中心加盟对账
				@RequestMapping(value = { "/zlist" })
				public String zlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws Exception {
			
					
					String tno = params.get("takeNo");
					if (StringUtils.nullString(tno).contains("(")) {
						tno = tno.substring(0, tno.indexOf("("));
					}
					params.put("tno", tno);

					String sno = params.get("sendNo");
					if (StringUtils.nullString(sno).contains("(")) {
						sno = sno.substring(0, sno.indexOf("("));
					}
					params.put("sno", sno);
					
					
					String ctno = params.get("tcourierNo");
					if (StringUtils.nullString(ctno).contains("(")) {
						ctno = ctno.substring(0, ctno.indexOf("("));
					}
					params.put("ctno", ctno);
					params.put("moneyType", "Z") ;
					
					PageInfo<Map<String, Object>> list = franOrderService.list(params, getPageInfo(cpage,1000)) ;
					
					model.put("list", list) ;
					model.put("params", params) ;
					return "franOrder/zlist";
				}				
			

				
				 // 用于批带
				@RequestMapping(value = { "/pidai" })
				public void pidai(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					String r = "保存失败" ;
					String ids = StringUtils.nullString(params.get("ids")) ;
					String moneyType = StringUtils.nullString(params.get("moneyType")) ;
					try {
						if (StringUtils.isEmptyWithTrim(ids)) {
							outText("1", response);
							return ;
						}    
						String[] idList = ids.split(",")  ;
						
						boolean flag = false ;
                       for (int i = 0; i < idList.length; i++) {
                    	  int ret =  franOrderService.withTransactionPidai(Integer.valueOf(idList[i]),moneyType);
                    	  if (ret==2) {
							  r = "余额不足" ;
							  flag = true ;
							  break ;
						  }
					   }
                        if (!flag) {
                        	 r = "1" ;
						}   
					} catch (Exception e) {
						e.printStackTrace();
					}
					outText(r, response);
				}	
				
				 // 中心派件对账
				@RequestMapping(value = { "/slist" })
				public String slist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws Exception {
					
					String tno = params.get("takeNo");
					if (StringUtils.nullString(tno).contains("(")) {
						tno = tno.substring(0, tno.indexOf("("));
					}
					params.put("tno", tno);

					String sno = params.get("sendNo");
					if (StringUtils.nullString(sno).contains("(")) {
						sno = sno.substring(0, sno.indexOf("("));
					}
					params.put("sno", sno);
					
					
					String ctno = params.get("tcourierNo");
					if (StringUtils.nullString(ctno).contains("(")) {
						ctno = ctno.substring(0, ctno.indexOf("("));
					}
					params.put("csno", ctno);
					params.put("moneyType", "P") ;
					
					PageInfo<Map<String, Object>> list = franOrderService.list(params, getPageInfo(cpage,1000)) ;
					
					model.put("list", list) ;
					model.put("params", params) ;
					return "franOrder/slist";
				}				
			
				 // 用于批量修改页面
				@RequestMapping(value = { "/batchUpdate" })
				public String batchUpdate(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException, SQLException {
					model.put("params", params) ;
					return "franOrder/batchUpdate";
				}

				 // 用于批量修改保存
				@RequestMapping(value = { "/batchSave" })
				public void batchSave(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					String r = "保存失败" ;
					String ids = StringUtils.nullString(params.get("ids")) ;
					
					try {
						if (StringUtils.isEmptyWithTrim(ids)) {
							outText("1", response);
							return ;
						}    
						String tno = params.get("takeNo");
						if (StringUtils.nullString(tno).contains("(")) {
							tno = tno.substring(0, tno.indexOf("("));
						}

						String sno = params.get("sendNo");
						if (StringUtils.nullString(sno).contains("(")) {
							sno = sno.substring(0, sno.indexOf("("));
						}
						String[] idsStringArray = ids.split(",");
						Long[] idsLongArray = new Long[idsStringArray.length];
						for(int i=0;i<idsStringArray.length;i++){
							idsLongArray[i] = Long.parseLong(idsStringArray[i]);
						}
						List<Map<String, Object>> lgcOrderNoList = franOrderService.getListByIds(idsLongArray);
						StringBuffer sb = new StringBuffer();
						for(Map<String, Object> lgcOrderNoMap : lgcOrderNoList){
							sb.append(lgcOrderNoMap.get("lgc_order_no"));
							sb.append(",");
						}
						String lgcOrderNos = sb.toString().substring(0, sb.toString().length()-1);
						System.out.println("lgcOrderNos=========="+lgcOrderNos);
						StringBuffer update = new StringBuffer(" ,修改内容有：");
						
						Map<String, Object> pMap = new HashMap<String, Object>() ;
						pMap.put("ids", ids) ;
						
						boolean updateAble = true ;
						boolean u = false ;
						if (franOrderService.pidaiIds(idsLongArray)) {
							u = true ;
						}
						
						if ("Y".equals(params.get("tno_check"))) {
							pMap.put("tno", tno) ;
							update.append("寄件网点-"+tno+",");
							if (u) {
								updateAble = false ; //已批带的不可修改寄件网点
							}
							
						}
						if ("Y".equals(params.get("itype_check"))) {
							pMap.put("itype", params.get("itype")) ;
							update.append("物品类型-"+params.get("itype")+",");
						}
						if ("Y".equals(params.get("weight_check"))) {
							pMap.put("weight", params.get("weight")) ;
							update.append("重量-"+params.get("weight")+",");
						}
						if ("Y".equals(params.get("sno_check"))) {
							pMap.put("sno", sno) ;
							update.append("派件网点-"+sno+",");
							if (u) {
								updateAble = false ; //已批带的不可修改网点
							}
						}
						pMap.put("last_update_name", Constants.getUser().getRealName()) ;
						pMap.put("last_update_time",DateUtils.formatDate(new Date())) ;
						
						if (!updateAble) {
							r= "已经批带过的订单不允许修改寄派网点" ;
						}else {
							 franOrderService.batchUpdate(pMap) ;
		                        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		                        userService.saveLog("","加盟-中心对账-批量修改：运单号 "+lgcOrderNos+update.toString() ,bossUser);
		                        r = "1" ;
						}
                       
					} catch (Exception e) {
						e.printStackTrace();
					}
					outText(r, response);
				}	
				
				
				 // 网点账单查询
					@RequestMapping(value = { "/subzlist" })
					public String subzlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
							HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws Exception {
						
						BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
						String substationNo ;
						if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
							 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
						}else {
							substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
						}
						System.out.println("substationNo============="+substationNo);
						params.put("take_substation_no", substationNo);
						//params.put("send_substation_no", substationNo);
						
						String tno = params.get("takeNo");
						if (StringUtils.nullString(tno).contains("(")) {
							tno = tno.substring(0, tno.indexOf("("));
						}
						params.put("tno", tno);

						String sno = params.get("sendNo");
						if (StringUtils.nullString(sno).contains("(")) {
							sno = sno.substring(0, sno.indexOf("("));
						}
						params.put("sno", sno);
						
						
						String ctno = params.get("tcourierNo");
						if (StringUtils.nullString(ctno).contains("(")) {
							ctno = ctno.substring(0, ctno.indexOf("("));
						}
						params.put("ctno", ctno);
						params.put("moneyType", "Z") ;
						
						PageInfo<Map<String, Object>> list = franOrderService.list(params, getPageInfo(cpage,1000)) ;
						
						model.put("list", list) ;
						model.put("params", params) ;
						return "franOrder/subzlist";
					}				
				
					
				 // 网点派件费查询
				@RequestMapping(value = { "/subslist" })
				public String subslist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws Exception {
					
						BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
						String substationNo ;
						if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
							 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
						}else {
							substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
						}
						params.put("send_substation_no", substationNo);
					
					String tno = params.get("takeNo");
					if (StringUtils.nullString(tno).contains("(")) {
						tno = tno.substring(0, tno.indexOf("("));
					}
					params.put("tno", tno);

					String sno = params.get("sendNo");
					if (StringUtils.nullString(sno).contains("(")) {
						sno = sno.substring(0, sno.indexOf("("));
					}
					params.put("sno", sno);
					
					
					String ctno = params.get("tcourierNo");
					if (StringUtils.nullString(ctno).contains("(")) {
						ctno = ctno.substring(0, ctno.indexOf("("));
					}
					params.put("csno", ctno);
					params.put("moneyType", "P") ;
					
					PageInfo<Map<String, Object>> list = franOrderService.list(params, getPageInfo(cpage,1000)) ;
					
					model.put("list", list) ;
					model.put("params", params) ;
					return "franOrder/subslist";
				}						
	
				
				 // 用于确认
				@RequestMapping(value = { "/queren" })
				public void queren(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					String r = "保存失败" ;
					String ids = StringUtils.nullString(params.get("ids")) ;
					String moneyType = StringUtils.nullString(params.get("moneyType")) ;
					
					try {
						if (StringUtils.isEmptyWithTrim(ids)) {
							outText("1", response);
							return ;
						}  
						Map<String, Object> pMap = new HashMap<String, Object>() ;
						pMap.put("ids", ids) ;
						pMap.put("confirm_status", "SUCCESS") ;
						pMap.put("confirm_time",DateUtils.formatDate(new Date())) ;
						pMap.put("confirm_name", Constants.getUser().getUserName()) ;
						pMap.put("confirm_real_name", Constants.getUser().getRealName()) ;
						pMap.put("moneyType", moneyType) ;
						franOrderService.batchQueren(pMap) ;
						
                       	 r = "1" ;
					} catch (Exception e) {
						e.printStackTrace();
					}
					outText(r, response);
				}
				
}
