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
@RequestMapping(value = "/franchise")
public class FranchiseController extends BaseController {
			
	@Resource
	private FranchiseRuleService franchiseRuleService ;

				 // 加盟报价维护
				@RequestMapping(value = { "/rule" })
				public String list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
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
					
					PageInfo<Map<String, Object>> list = franchiseRuleService.listRule(params, getPageInfo(cpage)) ;
					
					model.put("list", list) ;
					model.put("params", params) ;
					return "franchise/rule";
				}				
			

				 // 用于报价编辑
				@RequestMapping(value = { "/redit" })
				public String input(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException, SQLException {
					String id = StringUtils.nullString(params.get("id")) ;
				   List<Map<String, Object>> tlist = null ;
				   List<Map<String, Object>> slist = null ;
				   List<Map<String, Object>> ilist = null ;
				   Map<String, Object> rule = new HashMap<String, Object>() ;
				   Map<String, String> xweight = new HashMap<String, String>() ;
                   if (StringUtils.isEmptyWithTrim(id)) {
					 tlist = franchiseRuleService.getRuleTakeNo(false, "") ;
				     slist = franchiseRuleService.getRuleSendNo(false, "") ;
				     ilist = franchiseRuleService.getRuleItype(false, "") ;
				     Date nowDate = new Date() ;
				     rule.put("begin_time", DateUtils.formatDate(nowDate)) ;
				     rule.put("end_time", DateUtils.formatDate(DateUtils.addDate(nowDate, 800, 0, 0))) ;
                   }else {
                	   tlist = franchiseRuleService.getRuleTakeNo(true, id) ;
  				       slist = franchiseRuleService.getRuleSendNo(true, id) ;
  				       ilist = franchiseRuleService.getRuleItype(true, id) ;
  				       rule = franchiseRuleService.getById(params) ;
  				       String jsonString = StringUtils.nullString(rule.get("xweight")) ;
  				       xweight = JsonUtil.getMapFromJson(jsonString) ;
				  }
                     
					model.put("params", params) ;
					model.put("tlist", tlist) ;
					model.put("slist", slist) ;
					model.put("ilist", ilist) ;
					model.put("rule", rule) ;
					model.put("xweight", xweight) ;
					return "franchise/rule_edit";
				}	
				
				
				 // 用于规则保存
				@RequestMapping(value = { "/ruleSave" })
				public void ruleSave(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					String r = "保存失败" ;
					Date nowDate = new Date() ;
					try {
						  FranchiseRule rule = new FranchiseRule() ;
                          Map<String, String> rmap = new HashMap<String, String>() ;
                          rule.setBegin_time(StringUtils.nullString(params.get("createTimeBegin"))) ;
                          rule.setEnd_time(StringUtils.nullString(params.get("createTimeEnd"))) ;
                          rule.setMoney_type(StringUtils.nullString(params.get("moneyType"))) ;
                          rule.setWeight_type(StringUtils.nullString(params.get("weightType"))) ;
                          
                         
                           if ("L".equals(StringUtils.nullString(params.get("weightType")))) {
                        	   rule.setWval1(StringUtils.nullString(params.get("wval1"),"0")) ;
                        	   rule.setWval2(StringUtils.nullString(params.get("wval2"),"0")) ;
						   }
                           
                           rule.setVpay(StringUtils.nullString(params.get("vpay"),"0")) ;
                           rule.setZpay(StringUtils.nullString(params.get("zpay"),"0")) ;
                           rule.setFweight(StringUtils.nullString(params.get("fweight"),"0")) ;
                           rule.setFmoney(StringUtils.nullString(params.get("fmoney"),"0")) ;
                           rule.setCreate_name(Constants.getUser().getRealName()) ;
                           rule.setCreate_time(DateUtils.formatDate(nowDate)) ;
                           
                           Map<String, String> wmap = new HashMap<String, String>() ;    
                           wmap.put("w1", StringUtils.nullString(params.get("w1"))) ;
                           wmap.put("w2", StringUtils.nullString(params.get("w2"))) ;
                           wmap.put("w3", StringUtils.nullString(params.get("w3"))) ;
                           wmap.put("w4", StringUtils.nullString(params.get("w4"))) ;
                           wmap.put("p1", StringUtils.nullString(params.get("p1"))) ;
                           wmap.put("p2", StringUtils.nullString(params.get("p2"))) ;
                           wmap.put("p3", StringUtils.nullString(params.get("p3"))) ;
                           wmap.put("p4", StringUtils.nullString(params.get("p4"))) ;
                           String jsonString = JsonUtil.toJson(wmap) ;
                           
                           rule.setXweight(jsonString);
                           
                           String text = "首重"+ StringUtils.nullString(params.get("fweight")) + "&nbsp;&nbsp;" + StringUtils.nullString(params.get("fmoney")) +"元" ;
                           StringBuffer weight_text = new StringBuffer(text) ;
                           if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(params.get("w1")))) {
							  weight_text.append("</br>").append(StringUtils.nullString(params.get("fweight"))).append("&lt;w&lt;")
							    .append(StringUtils.nullString(params.get("w1"))).append("&nbsp;&nbsp;").append(StringUtils.nullString(params.get("fmoney")))
							    .append("+(w-").append( StringUtils.nullString(params.get("fweight"))).append(")*")
							    .append(StringUtils.nullString(params.get("p1"))) ;
						   }
                           if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(params.get("w2")))) {
 							  weight_text.append("</br>").append(StringUtils.nullString(params.get("w1"))).append("&lt;w&lt;")
 							    .append(StringUtils.nullString(params.get("w2"))).append("&nbsp;&nbsp;").append(StringUtils.nullString(params.get("fmoney")))
 							    .append("+(w-").append( StringUtils.nullString(params.get("fweight"))).append(")*")
 							    .append(StringUtils.nullString(params.get("p2"))) ;
 						   }
                           
                           if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(params.get("w3")))) {
 							  weight_text.append("</br>").append(StringUtils.nullString(params.get("w2"))).append("&lt;w&lt;")
 							    .append(StringUtils.nullString(params.get("w3"))).append("&nbsp;&nbsp;").append(StringUtils.nullString(params.get("fmoney")))
 							    .append("+(w-").append( StringUtils.nullString(params.get("fweight"))).append(")*")
 							    .append(StringUtils.nullString(params.get("p3"))) ;
 						   }
                           
                           if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(params.get("w4")))) {
 							  weight_text.append("</br>").append(StringUtils.nullString(params.get("w3"))).append("&lt;w&lt;")
 							    .append(StringUtils.nullString(params.get("w4"))).append("&nbsp;&nbsp;").append(StringUtils.nullString(params.get("fmoney")))
 							    .append("+(w-").append( StringUtils.nullString(params.get("fweight"))).append(")*")
 							    .append(StringUtils.nullString(params.get("p4"))) ;
 						   }
                           
                           rule.setWeight_text(weight_text.toString());
                           
                          franchiseRuleService.saveRule(rule); 
                          
                          int rid = rule.getId() ;
                          String[] tno = StringUtils.nullString(params.get("tno")).split(",") ;
                          String[] sno = StringUtils.nullString(params.get("sno")).split(",") ;
                          String[] itype = StringUtils.nullString(params.get("itype")).split(",") ;
                          
                          List<Map<String, Object>> tList = new ArrayList<Map<String,Object>>() ;
                          for (int i = 0; i < tno.length; i++) {
							   Map<String, Object> map = new HashMap<String, Object>() ;
							   map.put("rid", rid) ;
							   map.put("tno", tno[i]) ;
							   tList.add(map);
						  }
                          franchiseRuleService.batchTno(tList);
                          
                          List<Map<String, Object>> sList = new ArrayList<Map<String,Object>>() ;
                          for (int i = 0; i < sno.length; i++) {
							   Map<String, Object> map = new HashMap<String, Object>() ;
							   map.put("rid", rid) ;
							   map.put("sno", sno[i]) ;
							   sList.add(map);
						  }
                          franchiseRuleService.batchSno(sList);
                          
                          List<Map<String, Object>> iList = new ArrayList<Map<String,Object>>() ;
                          for (int i = 0; i < itype.length; i++) {
							   Map<String, Object> map = new HashMap<String, Object>() ;
							   map.put("rid", rid) ;
							   map.put("itype", itype[i]) ;
							   iList.add(map);
						  }
                         franchiseRuleService.batchItype(iList); 
                         
                         Integer id = Integer.valueOf(StringUtils.nullString(params.get("id"),"0")) ;
                         if (id!=0) {
							franchiseRuleService.delById(id) ;
						}
                           
					r = "1" ;
					} catch (Exception e) {
						e.printStackTrace();
					}
					outText(r, response);
				}	
				
				//用于删除rule
				@RequestMapping(value = {"/rdel"})
				public void rdel(@RequestParam Map<String, String> params,
						HttpServletResponse response) throws SQLException {
					Integer id = Integer.valueOf(StringUtils.nullString(params.get("id"),"0")) ;
                    if (id!=0) {
						franchiseRuleService.delById(id) ;
					}
                    outText("1", response);
				}
							


				 // 用于报价试算页面
				@RequestMapping(value = { "/rtrys" })
				public String rtrys(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException, SQLException {
					model.put("params", params) ;
					return "franchise/rtrys";
				}
				
				 // 用于报价试算
					@RequestMapping(value = { "/ruleTry" })
					public void ruleTry(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
							HttpServletResponse response) throws IOException {
						String r = "none" ;
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
						
						String moneyType = params.get("moneyType");
						String weightType = params.get("weightType");
						String weight = params.get("weight");
						String itemStatus = params.get("itemStatus"); 
						String settleTime = DateUtils.formatDate(new Date()) ;
						try {

						   Map<String, Object> ruleMap = franchiseRuleService.getFirstRule(tno, sno, itemStatus, moneyType, weightType, settleTime) ;
						   if (ruleMap==null) {
							  r = "none" ;
						  }else {
							  r = "8" ;
							 float f =  franchiseRuleService.getRuleSettle(ruleMap, weight) ;
							 r = f + "" ;
						  }
						} catch (Exception e) {
							e.printStackTrace();
						}
						outText(r, response);
					}			
}
