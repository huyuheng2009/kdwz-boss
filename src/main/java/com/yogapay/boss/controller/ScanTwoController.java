package com.yogapay.boss.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.ForwardOrder;
import com.yogapay.boss.domain.FranchiseOrder;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.enums.PayStatus;
import com.yogapay.boss.enums.TrackStatus;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.ForOrderService;
import com.yogapay.boss.service.FranOrderService;
import com.yogapay.boss.service.JoinSubsationService;
import com.yogapay.boss.service.LgcConfigService;
import com.yogapay.boss.service.MessageInfoService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.service.OrderTwoService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.SendMessage;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

@Controller
@RequestMapping(value = "/scant")
public class ScanTwoController extends BaseController {
	
	@Resource
	private OrderTrackService orderTrackService ;
	@Resource
	private SubstationService substationService ;
	@Resource
	private OrderService orderService ;
	@Resource
	private UserService userService ;
	@Resource
	private SequenceService sequenceService ;
	@Resource
	private ForOrderService forOrderService ;
	@Resource
	private CourierService courierService ;
	@Resource
	private OrderTwoService orderTwoService ;
	@Resource
	private FranOrderService franOrderService ;
	@Resource private JoinSubsationService joinSubsationService ;
	@Resource
	private  LgcConfigService lgcConfigService ;
	@Resource private MessageInfoService messageInfoService ;

	


				 // 用于派件扫描
				@RequestMapping(value = { "/sendscan" })
				public String revscan(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					setModelCour(model, "Y", courierService,request) ;
					return "scant/sendscan";
				}	


				
				//用于派件扫描操作保存
				@RequestMapping(value = {"/ssend"})
				public void ssend(@RequestParam Map<String, String> params,
						HttpServletResponse response,HttpServletRequest request) throws SQLException, IOException {
					Map<String, Object> retMap = new HashMap<String, Object> () ;
					
					String tcourierNo = params.get("sendCourierNo") ;
					String tno = tcourierNo ;
					if (StringUtils.nullString(tno).contains("(")) {
						tno = tno.substring(0, tno.indexOf("(")) ;
					}
					params.put("sendCourierNo", tno) ;
					Courier courier = courierService.getCourierByNo(tno) ;
					if (courier==null||courier.getStatus()==0) {
						retMap.put("ret", "操作失败，派件员错误或禁用") ;
						outJson(JsonUtil.toJson(retMap), response);
						return ;
					}
					   Substation substation = substationService.getSubstationByNo(courier.getSubstationNo()) ;
						Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcNo"), params.get("lgcOrderNo"), null) ;
						String orderNo = "" ;
						OrderTrack orderTrack = new OrderTrack() ;
						orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
						orderTrack.setCompleted("N");
						orderTrack.setIsLast("Y");
						orderTrack.setScanOno(Constants.getUser().getUserName());
						orderTrack.setScanOname(Constants.getUser().getRealName());
						orderTrack.setOrderStatus(TrackStatus.SEND.toString());
						String status = "8" ;
						boolean add = false ;
						if (orderMap==null) {
							OrderInfo orderInfo = new OrderInfo() ;
							 Date nowDate  = new Date() ;
			                 orderNo = sequenceService.getNextVal("order_no");
			                 orderInfo.setOrderNo(orderNo);
			                 orderInfo.setCod(0);
			                 orderInfo.setCreateTime(DateUtils.formatDate(nowDate));
			                 orderInfo.setTakeOrderTime(DateUtils.formatDate(nowDate));
			                 orderInfo.setLastUpdateTime(DateUtils.formatDate(nowDate));
			                 orderInfo.setStatus(8);                     //处理中
			                 orderInfo.setPayStatus(PayStatus.NOPAY.getValue());
			                 orderInfo.setKstatus("CALLED");
			                 orderInfo.setSource("BOSS_SCAN");
			                 LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig") ;
				     		  if (lgcConfig!=null) {
				     				orderInfo.setLgcNo(lgcConfig.getLgcNo());
				     			}
			                 
			                 orderInfo.setLgcOrderNo(params.get("lgcOrderNo"));
			                 orderInfo.setSubStationNo(params.get("substationNo"));
			                 
			                 if (!lgcConfigService.validateLgcOrderNo(orderInfo.getLgcOrderNo())) {
						    	    outText("录入失败,录入的运单号不符合规则", response);
		                		    return ;
							   }
			                 
			                 orderService.takesave(orderInfo) ;  
							add = true ;
							
						}else {
							orderNo = orderMap.get("order_no").toString() ;
							status = orderMap.get("status").toString() ;
						}
						orderTrack.setOrderNo(orderNo);
						OrderTrack lastOrderTrack =  null ;
						if (orderMap!=null) {
							lastOrderTrack = orderTrackService.getLastOrderTrack(orderNo) ;
						}
						if (lastOrderTrack==null) {	
							orderTrack.setCurNo(params.get("substationNo"));
							orderTrack.setCurType("C");
						}else {
							if (!"2".equals(status)&&!"8".equals(status)&&!"7".equals(status)) {
								retMap.put("ret", "操作失败，订单已不在流转状态") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}

							if ("SIGNED".equals(lastOrderTrack.getOrderStatus())) {
								retMap.put("ret", "操作失败，当前件已被签收") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}
							if ("FORD".equals(lastOrderTrack.getOrderStatus())) {
								retMap.put("ret", "操作失败，当前件已被转出") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}
							orderTrack.setPreNo(lastOrderTrack.getPreNo());
							orderTrack.setPreType(lastOrderTrack.getPreType());
							orderTrack.setCurNo(lastOrderTrack.getCurNo());
							orderTrack.setCurType(lastOrderTrack.getCurType());
							orderTrack.setNextNo(courier.getCourierNo());
							orderTrack.setNextType("C");
							
							orderTrack.setParentId(lastOrderTrack.getId());
						}
						orderTrack.setContext(substation.getSubstationName()+",快递员:"+courier.getRealName()+"将很快进行派送，请保持电话畅通："+courier.getPhone());
						orderTrack.setOpname(Constants.getUser().getRealName());
						orderTrackService.add(orderTrack);
						orderTrackService.unLastTrack(lastOrderTrack);
						orderTwoService.sendScanUpdate(tno,courier.getSubstationNo(),orderNo) ;
						//新增，更新派件时间
						OrderInfo info=new OrderInfo();
						info.setLgcOrderNo(params.get("lgcOrderNo"));
						info.setRealSendTime(DateUtils.formatDate(new Date()));
						orderService.updateRealSendTime(info);
						
						
						  Map<String, Object> lmap = lgcConfigService.getByType("MOBILE_CONFIG") ;
			 				String isMessage =  "0";
			 				if (lmap!=null&&lmap.get("TAKE_SEND_MSG")!=null&&"1".equals(lmap.get("SEND_SEND_MSG").toString())) {
			 					isMessage =  "1";
			 				}
			 				
			 				
						if ("1".equals(isMessage)) {
							if(!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("rev_phone")))&&StringUtils.nullString(orderMap.get("rev_phone")).length()==11){
								String content = courier.getRealName()+"快递员正在派送中，电话："+courier.getPhone()+"，请耐心等待。【快递王子】";
								if("kuaike".equals(dynamicDataSourceHolder.getKey())){
									content ="您好！欢迎您使用快客同城速配，现有您一件同城货物派送中，单号"+StringUtils.nullString(orderMap.get("lgc_order_no"))+".请您电话保持畅通，我们将及时为您配送。下单、查询、投诉建议请关注微信公众号“快刻同城速配”，服务热线：0592－7127770【快递王子】";
								}
								String channel = "szkyt" ;
								if("yx".equals(dynamicDataSourceHolder.getKey())){
									content ="【亿翔快递】尊敬的"+StringUtils.nullString(orderMap.get("rev_name"))+"先生/女士 ，您的快递："+StringUtils.nullString(orderMap.get("lgc_order_no"))+"已由"+courier.getRealName()+"快递员即将配送，电话："+courier.getPhone()+",请留意收件并保持电话畅通。如您家中有生活垃圾需要带出请告知快递员，感谢您使用亿翔快递，祝您生活愉快！";
								    channel = "yx" ;
								}
								 LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig") ;
								SendMessage.send(StringUtils.nullString(orderMap.get("rev_phone")), content, lgcConfig==null?"":lgcConfig.getLgcNo(),channel,messageInfoService,lgcConfigService) ;
							}	
						}
						
						
						retMap.put("ret", "1") ;
						retMap.put("track", orderTrack) ;
						retMap.put("courier", courier) ;
						retMap.put("substation", substation) ;
						params.put("sendCourierNo", tcourierNo) ;
						outJson(JsonUtil.toJson(retMap), response);
					
				}	

	

				 // 用于签收扫描
				@RequestMapping(value = { "/signscan" })
				public String signscan(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					setModelCour(model, "Y", courierService,request) ;
					return "scant/signscan";
				}	

			
				
				//用于派件扫描操作保存
				@RequestMapping(value = {"/ssign"})
				public void ssign(@RequestParam Map<String, String> params,
						HttpServletResponse response,HttpServletRequest request) throws SQLException, IOException {
					Map<String, Object> retMap = new HashMap<String, Object> () ;
					
					String tcourierNo = params.get("sendCourierNo") ;
					String tno = tcourierNo ;
					if (StringUtils.nullString(tno).contains("(")) {
						tno = tno.substring(0, tno.indexOf("(")) ;
					}
					params.put("sendCourierNo", tno) ;
					Courier courier = courierService.getCourierByNo(tno) ;
					if (courier==null||courier.getStatus()==0) {
						retMap.put("ret", "操作失败，派件员错误或禁用") ;
						outJson(JsonUtil.toJson(retMap), response);
						return ;
					}
					
					 boolean join = false ;
					 if (courier!=null) {
		                    join =  joinSubsationService.shutAcount(courier.getSubstationNo()) ;
		                 }
					 if (join) {
							retMap.put("ret", "录入失败,寄件网点余额不足") ;
							outJson(JsonUtil.toJson(retMap), response);
               		       return ;
					   }
					
					
					   Substation substation = substationService.getSubstationByNo(courier.getSubstationNo()) ;
						Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcNo"), params.get("lgcOrderNo"), null) ;
						String orderNo = "" ;
						OrderTrack orderTrack = new OrderTrack() ;
						orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
						orderTrack.setCompleted("N");
						orderTrack.setIsLast("Y");
						orderTrack.setScanOno(Constants.getUser().getUserName());
						orderTrack.setScanOname(Constants.getUser().getRealName());
						orderTrack.setOrderStatus("SIGNED");
						String status = "8" ;
						if (orderMap==null) {
							OrderInfo orderInfo = new OrderInfo() ;
							 Date nowDate  = new Date() ;
			                 orderNo = sequenceService.getNextVal("order_no");
			                 orderInfo.setOrderNo(orderNo);
			                 orderInfo.setCod(0);
			                 orderInfo.setCreateTime(DateUtils.formatDate(nowDate));
			                 orderInfo.setTakeOrderTime(DateUtils.formatDate(nowDate));
			                 orderInfo.setLastUpdateTime(DateUtils.formatDate(nowDate));
			                 orderInfo.setStatus(8);                     //处理中
			                 orderInfo.setPayStatus(PayStatus.NOPAY.getValue());
			                 orderInfo.setKstatus("CALLED");
			                 orderInfo.setSource("BOSS_SCAN");
			                 
			                 LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig") ;
				     		  if (lgcConfig!=null) {
				     				orderInfo.setLgcNo(lgcConfig.getLgcNo());
				     			}
			                 
			                 orderInfo.setLgcOrderNo(params.get("lgcOrderNo"));
			                 orderInfo.setSubStationNo(params.get("substationNo"));
			                 
			                 if (!lgcConfigService.validateLgcOrderNo(orderInfo.getLgcOrderNo())) {
						    	    outText("录入失败,录入的运单号不符合规则", response);
		                		    return ;
							   }
			                 
			                 orderService.takesave(orderInfo) ;  
			                	 //加盟网点增加加盟订单数据
							
						}else {
							orderNo = orderMap.get("order_no").toString() ;
							status = orderMap.get("status").toString() ;
						}
						orderTrack.setOrderNo(orderNo);
						OrderTrack lastOrderTrack =  null ;
						if (orderMap!=null) {
							lastOrderTrack = orderTrackService.getLastOrderTrack(orderNo) ;
						}
						if (lastOrderTrack==null) {	
							orderTrack.setCurNo(params.get("substationNo"));
							orderTrack.setCurType("C");
						}else {
							if (!"2".equals(status)&&!"8".equals(status)&&!"7".equals(status)) {
								retMap.put("ret", "操作失败，订单已不在流转状态") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}

							if ("SIGNED".equals(lastOrderTrack.getOrderStatus())) {
								retMap.put("ret", "操作失败，当前件已被签收") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}
							if ("FORD".equals(lastOrderTrack.getOrderStatus())) {
								retMap.put("ret", "操作失败，当前件已被转出") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}
							orderTrack.setPreNo(lastOrderTrack.getPreNo());
							orderTrack.setPreType(lastOrderTrack.getPreType());
							orderTrack.setCurNo(lastOrderTrack.getCurNo());
							orderTrack.setCurType(lastOrderTrack.getCurType());
							orderTrack.setNextNo(courier.getCourierNo());
							orderTrack.setNextType("C");
							orderTrack.setParentId(lastOrderTrack.getId());
						}
						orderTrack.setContext("派件签收，签收人："+params.get("signName"));
						orderTrack.setOpname(Constants.getUser().getRealName());
						orderTrackService.add(orderTrack);
						orderTrackService.unLastTrack(lastOrderTrack);
						orderTwoService.signScanUpdate(tno,courier.getSubstationNo(),orderNo) ;
						
						orderMap = orderService.findByOrderNo(orderNo) ;
						Substation s =  substationService.getSubstationByNo(courier.getSubstationNo()) ;
						 if (s!=null&&"J".equals(s.getSubstationType())) {
	                		 FranchiseOrder franchiseOrder = new FranchiseOrder() ;
	                		 franchiseOrder.setOrder_no(orderNo);
	                		 franchiseOrder.setLgc_order_no(StringUtils.stringNull(orderMap.get("lgc_order_no")));
	                		 franchiseOrder.setTake_substation_no(StringUtils.stringNull(orderMap.get("sub_station_no")));
	                		 franchiseOrder.setSend_substation_no(StringUtils.stringNull(courier.getSubstationNo()));
	                		 franchiseOrder.setItem_type(StringUtils.stringNull(orderMap.get("item_type")));
	                		 franchiseOrder.setItem_weight(StringUtils.nullString(orderMap.get("item_weight"),"0"));
	                		 franchiseOrder.setMoney_type("P");
	                		 franchiseOrder.setCreate_time(DateUtils.formatDate(new Date()));
						  	  franOrderService.saveOrder(franchiseOrder) ;
					   }	
						
						 Substation s1 =  substationService.getSubstationByNo(StringUtils.stringNull(orderMap.get("sub_station_no"))) ; 
						 if (s1!=null&&"J".equals(s1.getSubstationType())) {
                              //更新寄件加盟网点
						  	  franOrderService.updateSendSno(StringUtils.stringNull(orderMap.get("order_no")),StringUtils.stringNull(courier.getSubstationNo()),StringUtils.stringNull(orderMap.get("lgc_order_no"))) ;
					   }
						 
						 
						 Map<String, Object> lmap = lgcConfigService.getByType("MOBILE_CONFIG") ;
			 				String isMessage =  "0";
			 				if (lmap!=null&&lmap.get("TAKE_SEND_MSG")!=null&&"1".equals(lmap.get("TAKE_SEND_MSG").toString())) {
			 					isMessage =  "1";
			 				}
						 
						 if ("1".equals(isMessage)&&"1".equals(StringUtils.nullString(orderMap.get("message")))) {
								if(!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("send_phone")))&&StringUtils.nullString(orderMap.get("send_phone")).length()==11){
									String content = "运单号："+StringUtils.stringNull(orderMap.get("lgc_order_no"))+"，已签收完成，签收人："+params.get("signName")+"。【快递王子】";
									 LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig") ;
										String channel = "szkyt" ;
										if("yx".equals(dynamicDataSourceHolder.getKey())){
											//尊敬的XX先生/女士，您的快递：xxxxxxxxxx已由xx先生/女士签收，请核实签收情况。感谢您使用亿翔快递，祝您生活愉快！ 
											content ="【亿翔快递】尊敬的"+StringUtils.nullString(orderMap.get("send_name"))+"先生/女士 ，您的快递："+StringUtils.nullString(orderMap.get("lgc_order_no"))+"已由:"+params.get("signName")+"先生/女士签收，请核实签收情况。感谢您使用亿翔快递，祝您生活愉快！";
										    channel = "yx" ;
										} 
									SendMessage.send(StringUtils.nullString(orderMap.get("send_phone")), content, lgcConfig==null?"":lgcConfig.getLgcNo(),channel,messageInfoService,lgcConfigService) ;
								}	
							}
						
						retMap.put("ret", "1") ;
						retMap.put("track", orderTrack) ;
						retMap.put("courier", courier) ;
						retMap.put("substation", substation) ;
						params.put("sendCourierNo", tcourierNo) ;
						outJson(JsonUtil.toJson(retMap), response);
					
				}	
			
				
				 // 用于中心出仓扫描
				@RequestMapping(value = { "/center_out" })
				public String center_out(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					return "scant/centerout";
				}	
				
			
				
				
				//用于中心出仓下一站
				@RequestMapping(value = {"/center_out_save"})
				public void center_out(@RequestParam Map<String, String> params,
						HttpServletResponse response,HttpServletRequest req) throws SQLException {
					Map<String, Object> retMap = new HashMap<String, Object> () ;
					if (StringUtil.isEmpty(params.get("lgcOrderNo"))||StringUtil.isEmpty(params.get("nextNo"))) {
						retMap.put("ret", "操作失败") ;
						outJson(JsonUtil.toJson(retMap), response);
						return ;
					} else {
						
						String nextNo = params.get("nextNo");
						if (StringUtils.nullString(nextNo).contains("(")) {
							nextNo = nextNo.substring(0, nextNo.indexOf("("));
						}
						Substation next = substationService.getSubstationByNo(nextNo) ;
						if (next==null||"0".equals(next.getStatus())) {
							retMap.put("ret", "网点错误或禁用") ;
							outJson(JsonUtil.toJson(retMap), response);
							return ;
						}
						
						String sendNo = params.get("sendNo");
						if (StringUtils.nullString(sendNo).contains("(")) {
							sendNo = sendNo.substring(0, sendNo.indexOf("("));
						}
						String sendName = "" ;
						if (!"".equals(sendNo)) {
							Substation send = substationService.getSubstationByNo(sendNo) ;
							if (send==null) {
								retMap.put("ret", "派件网点错误") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}
							sendName = send.getSubstationName() ;
						}
						
						
						String curNo = userService.getByUserName(Constants.getUser().getUserName()).getSno() ;
						if (curNo==null||curNo.equals("")) {
							curNo = nextNo ;
						}
						Substation cur = substationService.getSubstationByNo(curNo) ;
						String curName = cur==null?"":cur.getSubstationName() ;
						
						Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcOrderNo")) ;
						String orderNo = "" ;
						if (orderMap==null) {
							
							OrderInfo orderInfo = new OrderInfo() ;
							 Date nowDate  = new Date() ;
			                 if (!StringUtils.isEmptyWithTrim(orderInfo.getTakeTime())) {
			                	 orderInfo.setTakeTimeBegin(DateUtils.formatDate(DateUtils.getBeginDate(orderInfo.getTakeTime())));
			                     orderInfo.setTakeTimeEnd(DateUtils.formatDate(DateUtils.getEndDate(orderInfo.getTakeTime())));
							 }else {
								 orderInfo.setTakeTimeBegin(DateUtils.formatDate(DateUtils.getBeginDate(null)));
			                     orderInfo.setTakeTimeEnd(DateUtils.formatDate(DateUtils.getEndDate(null)));
							}
			                 if (StringUtils.isEmptyWithTrim(orderInfo.getTakeAddr())) {
								orderInfo.setTakeAddr(orderInfo.getSendArea()+orderInfo.getSendAddr());
							 }
			                 orderNo = sequenceService.getNextVal("order_no");
			                 orderInfo.setOrderNo(orderNo);
			                 orderInfo.setCod(0);
			                 orderInfo.setCreateTime(DateUtils.formatDate(nowDate));
			                 orderInfo.setTakeOrderTime(DateUtils.formatDate(nowDate));
			                 orderInfo.setLastUpdateTime(DateUtils.formatDate(nowDate));
			                 orderInfo.setStatus(2);                     //处理中
			                 orderInfo.setPayStatus(PayStatus.NOPAY.getValue());
			                 orderInfo.setKstatus("CALLED");
			                 orderInfo.setSource("BOSS_SCAN");
			                 LgcConfig lgcConfig = (LgcConfig) req.getSession().getAttribute("lgcConfig") ;
			                 if (lgcConfig!=null) {
			                	 orderInfo.setLgcNo(lgcConfig.getLgcNo());
							}
			                 orderInfo.setLgcOrderNo(params.get("lgcOrderNo"));
			                 orderInfo.setSubStationNo(curNo);
			                 
			                 if (!lgcConfigService.validateLgcOrderNo(orderInfo.getLgcOrderNo())) {
						    	    retMap.put("ret", "操作失败，录入的运单号不符合规则") ;
									outJson(JsonUtil.toJson(retMap), response);
		                		    return ;
							   }
			                 
			                 orderService.takesave(orderInfo) ;  
			                 
			                 orderMap = orderService.findByOrderNo(orderNo) ;
							
					/*		retMap.put("ret", "操作失败,运单号不存在") ;
							outJson(JsonUtil.toJson(retMap), response);
							return ;*/
						}
						
						
						
						String status = orderMap.get("status").toString() ;
						if (!"2".equals(status)&&!"8".equals(status)&&!"7".equals(status)) {
							retMap.put("ret", "操作失败，订单已不在流转状态") ;
							outJson(JsonUtil.toJson(retMap), response);
							return ;
						}
						
						//orderService.updateStatus("7",orderMap.get("order_no").toString()) ;
						
						OrderTrack lastOrderTrack = orderTrackService.getLastOrderTrack(orderMap.get("order_no").toString()) ;

						if (lastOrderTrack==null) {
							lastOrderTrack = new OrderTrack() ;
						 }
							OrderTrack orderTrack = new OrderTrack() ;
							orderTrack.setOrderNo(orderMap.get("order_no").toString());
							orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
							orderTrack.setCompleted("N");
							orderTrack.setPreNo(lastOrderTrack.getPreNo());
							orderTrack.setPreType(lastOrderTrack.getPreType());
							orderTrack.setCurNo(curNo);
							orderTrack.setCurType("S");
							orderTrack.setNextNo(nextNo);
							orderTrack.setNextType("S");
							orderTrack.setOrderStatus(TrackStatus.SEND.toString());
							orderTrack.setParentId(lastOrderTrack.getId());
							orderTrack.setIsLast("Y");
							orderTrack.setScanIno(lastOrderTrack.getScanIno());
							orderTrack.setScanIname(lastOrderTrack.getScanIname());
							orderTrack.setScanOno(Constants.getUser().getUserName());
							orderTrack.setScanOname(Constants.getUser().getRealName());

							orderTrack.setContext(Constants.getUser().getRealName()+",已进行出仓扫描,当前快件在"+curName+",下一站点:"+next.getSubstationName());
							orderTrack.setOpname(Constants.getUser().getRealName());
							orderTrackService.add(orderTrack);
							orderTrackService.unLastTrack(lastOrderTrack);
							
							if (!"".equals(sendNo)) {
								franOrderService.updateSendSnoIfnull(orderMap.get("order_no").toString(), sendNo);
							}
							
							orderTrack.setCurType(curName);
							orderTrack.setNextType(next.getSubstationName());
							retMap.put("ret", "1") ;
							retMap.put("track_sendNo", sendNo) ;
							retMap.put("track_sendName", sendName) ;
							retMap.put("track", orderTrack) ;
							retMap.put("itemStatus", orderMap.get("item_Status")==null?"":orderMap.get("item_Status").toString()) ;
							outJson(JsonUtil.toJson(retMap), response);
						
					}
				}		
		
}
