package com.yogapay.boss.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.shiro.SecurityUtils;
import org.aspectj.weaver.ast.Var;
import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.noneDSA;
import org.jpos.transaction.participant.HasEntry;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dataSource.DynamicDataSourceHolder;
import com.yogapay.boss.domain.AsignOrder;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.CodRateType;
import com.yogapay.boss.domain.ConsumeHistory;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.DisUser;
import com.yogapay.boss.domain.DisUserBalance;
import com.yogapay.boss.domain.DiscountType;
import com.yogapay.boss.domain.FranchiseOrder;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.LgcAddr;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.LgcCustomer;
import com.yogapay.boss.domain.MonthSettleType;
import com.yogapay.boss.domain.MonthSettleUser;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.OrderSubstation;
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.domain.PushMsg;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.domain.TableFiledSort;
import com.yogapay.boss.enums.MsgType;
import com.yogapay.boss.enums.PayStatus;
import com.yogapay.boss.enums.PushUserType;
import com.yogapay.boss.enums.TrackStatus;
import com.yogapay.boss.service.AsignOrderService;
import com.yogapay.boss.service.CacheService;
import com.yogapay.boss.service.CodRateTypeService;
import com.yogapay.boss.service.CodSettleUserService;
import com.yogapay.boss.service.ConsumeHistoryService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.DisUserBalanceService;
import com.yogapay.boss.service.DisUserService;
import com.yogapay.boss.service.DiscountTypeService;
import com.yogapay.boss.service.ForCpnService;
import com.yogapay.boss.service.ForOrderService;
import com.yogapay.boss.service.FranOrderService;
import com.yogapay.boss.service.ItemTypeService;
import com.yogapay.boss.service.JoinSubsationService;
import com.yogapay.boss.service.LgcAddrService;
import com.yogapay.boss.service.LgcConfigService;
import com.yogapay.boss.service.LgcCustomerService;
import com.yogapay.boss.service.LgcModeService;
import com.yogapay.boss.service.LgcService;
import com.yogapay.boss.service.MessageInfoService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.MonthSettleTypeService;
import com.yogapay.boss.service.MsgService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderSubstationService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.service.RequireListService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.TableFiledSortService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.ConstantsLoader;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.KuaiDiYiBaiUtil;
import com.yogapay.boss.utils.LocationUtil;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.PushUtil;
import com.yogapay.boss.utils.SendMessage;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;
import com.yogapay.boss.utils.WeiXinUtil;

@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {
	
	     @Resource
	     private OrderService orderService ;
	     @Resource
	     private UserService userService ;
	     @Resource
	     private SubstationService substationService ;
	     @Resource
	     private LgcService lgcService ;
	     @Resource
	     private CourierService courierService ;
	     @Resource
	     private MsgService msgService ;
	     @Resource
	     private SequenceService sequenceService ;
	     @Resource
	     private OrderTrackService orderTrackService ;
	     @Resource
	     private CacheService cacheService;
	     @Resource
	     private LgcAddrService lgcAddrService ;
	     @Resource
	     private MobileUserService mobileUserService ;
	     @Resource
	     private DisUserService disUserService ;
	     @Resource
	     private DisUserBalanceService disUserBalanceService ;
	     @Resource 
	     private DiscountTypeService discountTypeService ;
	     @Resource
	     private ConsumeHistoryService consumeHistoryService ;
	     @Resource
	     private CodSettleUserService codSettleUserService ;
	     @Resource
	     private MonthSettleTypeService monthSettleTypeService ;
	     @Resource
	     private CodRateTypeService codRateTypeService ;
	     @Resource 
	     private OrderSubstationService orderSubstationService ;
	     @Resource private LgcModeService lgcModeService;
	     @Resource
	 	private FranOrderService franOrderService ;
	     @Resource
	     private JoinSubsationService joinSubsationService ;
	     @Resource
	     private ForOrderService forOrderService ;
	     @Resource
	     private ForCpnService forCpnService ;
	     @Resource
	     private TableFiledSortService tableFiledSortService;
	     @Resource
	     private AsignOrderService asignOrderService ;
	     @Resource
	     private RequireListService requireListService ;
	     @Resource
	     private LgcConfigService lgcConfigService ;
	     @Resource
	     private LgcCustomerService lgcCustomerService ;
	     @Resource
	     private ItemTypeService itemTypeService;
	     @Resource private MessageInfoService messageInfoService ;
	     
	     // 用于
			@RequestMapping(value = { "/add" })
			public String add(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response) {
				String  defaultCity = "" ;
				List<Map<String, Object>> lgcs = userService.getCurrentLgc();
				
				Map<String, Object> map = lgcConfigService.getByType("MOBILE_CONFIG") ;
				String isMessage =  "0";
				if (map!=null&&map.get("TAKE_SEND_MSG")!=null&&"1".equals(map.get("TAKE_SEND_MSG").toString())) {
					isMessage =  "1";
				}
				model.put("defaultCity", defaultCity) ;
				model.put("lgcs", lgcs) ;
				model.put("isMessage", isMessage) ;
				return "order/oadd";
			}	
			
			 // 用于
			@RequestMapping(value = { "/bing" })
			public String bind(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response) {
				model.put("params", params) ;
				return "order/bind";
			}	
			
			
			 // 用于绑定
			@RequestMapping(value = { "/bind_save" })
			public void bind_save(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
				Map<String, Object> orderMap = null ;
				String r = "数据有误" ;
				if(!StringUtils.isEmptyWithTrim(params.get("lgcOrderNo"))) {
					orderMap = orderService.findByLgcOrderNo(params.get("lgcNo"), params.get("lgcOrderNo"), null) ;
					if (orderMap!=null) {
						r= "运单号已存在" ;
					}else{
						orderMap = orderService.findByOrderNo(params.get("orderNo")) ;
						System.out.println("111111");
						System.out.println(orderMap.get("status"));
					/*	if ("1".equals(orderMap.get("status").toString())||"2".equals(orderMap.get("status").toString())
							||"7".equals(orderMap.get("status").toString())||"8".equals(orderMap.get("status").toString())) {*/
				       if(!"PASS".equals(StringUtils.nullString(orderMap.get("examine_status")))){
						    orderService.bing(params) ;
                               r = "1" ;
						}else {
							r = "当前状态不允许修改订单号" ;
						}
					}
				}
				 outText(r, response);
			}
			
			// 用于无效单
			@RequestMapping(value = { "/deled" })
			public void deled(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
								HttpServletResponse response, Integer id,String note) throws IOException {
							String r = "数据有误" ;
							try {
								BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
								params.put("operator", bossUser.getUserName()) ;
								params.put("orderId", id.toString()) ;
								params.put("note", note) ;
								params.put("opSrc", "无效单备注") ;
								params.put("createTime", DateUtils.formatDate(new Date())) ;
								orderService.deled(id) ;
								orderService.note(params);
								r = "1" ;
							} catch (Exception e) {
						}
							 outText(r, response);
						}
							
			
			 
		     // 用于
				@RequestMapping(value = { "/save" })
				public void save(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,OrderInfo orderInfo) throws SQLException {
					try {
					  Date nowDate  = new Date() ;
		                 if (!StringUtils.isEmptyWithTrim(orderInfo.getTakeTime())) {
		                	 orderInfo.setTakeTimeBegin(DateUtils.formatDate(DateUtils.getBeginDate(orderInfo.getTakeTime())));
		                     orderInfo.setTakeTimeEnd(DateUtils.formatDate(DateUtils.getEndDate(orderInfo.getTakeTime())));
						 }else {
							 orderInfo.setTakeTimeBegin(DateUtils.formatDate(DateUtils.getBeginDate(null)));
		                     orderInfo.setTakeTimeEnd(DateUtils.formatDate(DateUtils.getEndDate(null)));
						}
		                
		                 if (!StringUtils.isEmptyWithTrim(orderInfo.getTakeCourierNo())) {
								Courier courier = courierService.getCourierByNo(orderInfo.getTakeCourierNo()) ;
								if (courier!=null) {
									orderInfo.setSubStationNo(courier.getSubstationNo());
								}
							} 
		                 
		                /* if (StringUtils.isEmptyWithTrim(orderInfo.getTakeAddr())) {
							orderInfo.setTakeAddr(orderInfo.getSendArea()+orderInfo.getSendAddr());
						 }*/
		                 String orderNo = sequenceService.getNextVal("order_no");
		                 orderInfo.setOrderNo(orderNo);
		                 orderInfo.setCod(0);
		                 orderInfo.setCreateTime(DateUtils.formatDate(nowDate));
		                 orderInfo.setLastUpdateTime(DateUtils.formatDate(nowDate));
		                 orderInfo.setStatus(1);                     //处理中
		                 orderInfo.setPayStatus(PayStatus.NOPAY.getValue());
		                 orderInfo.setKstatus("CALLED");
		                 orderInfo.setSource("BOSS");
		                 
		                 
		                 Map<String, Object> lmap = lgcConfigService.getByType("MOBILE_CONFIG") ;
		 				String isMessage =  "0";
		 				if (lmap!=null&&lmap.get("TAKE_SEND_MSG")!=null&&"1".equals(lmap.get("TAKE_SEND_MSG").toString())) {
		 					isMessage =  "1";
		 				}
		 				
							String message = "0" ;
							if ("1".equals(isMessage)) {
								if ("1".equals(params.get("message"))) {
									message = "1" ;
								}
							}
							orderInfo.setMessage(message);
		                 
		                 orderService.save(orderInfo) ;  
		                 
		                 //增加备注记录
		                 if (!StringUtils.isEmptyWithTrim(orderInfo.getOrderNote())) {
		                	 String id = StringUtils.nullString(orderInfo.getId()) ;
			                    Map<String, String> nMap = new HashMap<String, String>() ;
			                    nMap.put("operator", Constants.getUser().getRealName()) ;
			                    nMap.put("orderId", id.toString()) ;
			                    nMap.put("note", orderInfo.getOrderNote()) ;
			                    nMap.put("opSrc", "备注") ;
			                    nMap.put("createTime", DateUtils.formatDate(new Date())) ;
								orderService.note(nMap);
						  }
		                 
		                 
		                 String addrString  = "" ;
		                 if (!StringUtils.isEmptyWithTrim(orderInfo.getSendArea())) {
		                	 addrString = orderInfo.getSendArea() ;
		                	   if (!StringUtils.isEmptyWithTrim(orderInfo.getSendAddr())) {
		                		   addrString += orderInfo.getSendAddr() ;
							}
		                   //LocationUtil.Location(addrString, "S", orderService, orderNo,dynamicDataSourceHolder.getKey(),dynamicDataSourceHolder);
						 }
		                 
		                 if (!StringUtils.isEmptyWithTrim(orderInfo.getRevArea())) {
		                	 addrString = orderInfo.getRevArea() ;
		                	   if (!StringUtils.isEmptyWithTrim(orderInfo.getRevAddr())) {
		                		   addrString += orderInfo.getRevAddr() ;
							}
		                  // LocationUtil.Location(addrString, "R", orderService, orderNo,dynamicDataSourceHolder.getKey(),dynamicDataSourceHolder);
						 }
		                 
		                 LgcAddr lgcAddr = new LgcAddr() ;
		                 lgcAddr.setLgcNo(orderInfo.getLgcNo());
		                 lgcAddr.setAddrSrc("ORDER_ADD");  //在线下单
		                 if(!StringUtil.isEmptyWithTrim(orderInfo.getSendPhone())&&!StringUtil.isEmptyWithTrim(orderInfo.getSendName())&&
		                		 !StringUtil.isEmptyWithTrim(orderInfo.getSendArea())&&!StringUtil.isEmptyWithTrim(orderInfo.getSendAddr())){
		                	 lgcAddr.setPhone(orderInfo.getSendPhone());
		                	 lgcAddr.setName(orderInfo.getSendName());
		                	 lgcAddr.setAreaAddr(orderInfo.getSendArea().replaceAll("-", ""));
		                	 lgcAddr.setDetailAddr(orderInfo.getSendAddr());
		                	 lgcAddr.setAddrType("SEND");
		                	 lgcAddr.setIncomingPhone(params.get("incomingPhone"));
		                	 Map<String, Object> addr = lgcAddrService.isExistA(lgcAddr) ;
		                	 Object addrId = 0 ;
		                	 if (addr==null) {
								lgcAddrService.saveLgcAddr(lgcAddr) ;
								addrId = lgcAddr.getId() ;
								
							}else {
								addrId = addr.get("id") ;
							}

		                	 Map<String, String> map = new HashMap<String, String>() ;
								map.put("addrId", StringUtils.nullString(addrId)) ;
								map.put("orderId", StringUtils.nullString(orderInfo.getId())) ;
								orderService.updateAddrId(map);
		                 }
		                 
		                 if(!StringUtil.isEmptyWithTrim(orderInfo.getSendPhone())){
                            String customer_no = sequenceService.getNextVal("customer_no") ;
                            if (!"0".equals(customer_no)) {
								LgcCustomer lgcCustomer = new LgcCustomer() ;
								lgcCustomer.setCustomer_no(Integer.valueOf(customer_no));
								lgcCustomer.setConcat_phone(orderInfo.getSendPhone());
								lgcCustomer.setConcat_name(orderInfo.getSendName());
								lgcCustomer.setConcat_addr(orderInfo.getSendArea()+orderInfo.getSendAddr());
								lgcCustomer.setSource("BOSS");
								lgcCustomer.setCreate_time(DateUtils.formatDate(nowDate));
								lgcCustomerService.saveIfNotExsit(lgcCustomer);
							}
		                 }
		                 
		                 
		                 if(!StringUtil.isEmptyWithTrim(orderInfo.getRevPhone())&&!StringUtil.isEmptyWithTrim(orderInfo.getRevName())&&
		                		 !StringUtil.isEmptyWithTrim(orderInfo.getRevArea())&&!StringUtil.isEmptyWithTrim(orderInfo.getRevAddr())){
		                	 lgcAddr.setPhone(orderInfo.getRevPhone());
		                	 lgcAddr.setName(orderInfo.getRevName());
		                	 lgcAddr.setAreaAddr(orderInfo.getRevArea().replaceAll("-", ""));
		                	 lgcAddr.setDetailAddr(orderInfo.getRevAddr());
		                	 lgcAddr.setAddrType("REV");
		                	 lgcAddr.setIncomingPhone("");
		                	 if (!lgcAddrService.isExist(lgcAddr)) {
								lgcAddrService.saveLgcAddr(lgcAddr) ;
							}
		                 }
		                 
		                 
		                 Map<String, String> track = new HashMap<String, String>() ;
		                 track.put("orderNo", orderNo) ;
		                 track.put("context","订单被创建");
		                 track.put("orderTime",DateUtils.formatDate(nowDate));
		                 track.put("check","N");
		                 orderTrackService.add(track);
		                 Map<String, String> m = new HashMap<String, String>() ;
		                 m.put("substationNo", orderInfo.getSubStationNo()) ;
		                 m.put("courierNo", orderInfo.getTakeCourierNo()) ;
		                 m.put("orderNo", orderNo) ;
		                 try {
		                		if (!StringUtils.isEmptyWithTrim(orderInfo.getTakeCourierNo())) {
		                			
		                			
		                			Date createDate = DateUtils.parseDate(orderInfo.getCreateTime()) ;
									AsignOrder asignOrder = new AsignOrder() ;
									asignOrder.setOrder_no(orderInfo.getOrderNo());
									asignOrder.setOrder_time(orderInfo.getCreateTime());
									asignOrder.setAsign_time(DateUtils.formatDate(nowDate));
									asignOrder.setAsing_date(DateUtils.formatDate(nowDate,"yyyy-MM-dd"));
									asignOrder.setOrder_date(DateUtils.formatDate(createDate,"yyyy-MM-dd"));
									asignOrder.setAsign_no(Constants.getUser().getUserName());
									asignOrder.setAsign_name(Constants.getUser().getRealName());
									
									double a = nowDate.getTime()-createDate.getTime() ;
									double  asign_duration = a/(1000*60) ;
									asignOrder.setAsign_duration(new BigDecimal(Math.ceil(asign_duration)));
									asignOrderService.save(asignOrder);
		                			
		                			
		                			m.put("asignStatus", "C") ;
		                			 PushMsg msg = new PushMsg() ;
						  				msg.setUserNo(orderInfo.getTakeCourierNo());
						  				msg.setUserType(2);
						  				msg.setMsgCode(MsgType.COME.getValue());
						  				msg.setMsgContent(DateUtils.formatDate(nowDate, "MM月dd HH:mm")+"在"+orderInfo.getSendAddr()+"有一个新的快件待取！\r\n联系方式"+orderInfo.getSendPhone()+"请尽快取件！");
						  				msg.setMsgData(orderNo);
						  				msg.setCreateTime(DateUtils.formatDate(nowDate));
						  				msg.setExpireTime(DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 6, 0)));
						  			    long msgId = msgService.save(msg) ;
						  			    PushUtil.pushById(String.valueOf(msg.getId()),2,dynamicDataSourceHolder.getKey()+"_courier");
						  			  orderService.asign(m) ;
								}else {
									Map<String, Object> mode = lgcModeService.getCurMode() ;
									if ("AUTO_ALL".equals(mode.get("code"))) {   //群发
										m.put("asignStatus", "S") ;
										 PushMsg msg = new PushMsg() ;
							  				msg.setUserNo(orderInfo.getLgcNo());
							  				msg.setUserType(5);
							  				msg.setMsgCode(MsgType.ASIGNTS.getValue());
							  				msg.setMsgContent("新的可接单");
							  				msg.setMsgData(params.get("orderNo"));
							  				msg.setCreateTime(DateUtils.formatDate(nowDate));
							  				msg.setExpireTime(DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 6, 0)));
							  			    //long msgId = msgService.save(msg) ;
							  			    PushUtil.pushByMSG(msg,dynamicDataSourceHolder.getKey()+"_courier");
							  			  orderService.asign(m) ;
									}else {                     //向分站发送
										   if ("AUTO_AREA".equals(mode.get("code"))) {
											   String stations = substationService.getStationStringByArea(orderInfo.getSendArea(),"") ;
								  			    String sno[] = stations.split(",") ;
								  			    if (!StringUtils.isEmptyWithTrim(stations)) {
								  			    	 m.put("asignStatus", "S") ;
												}else {
													stations = substationService.getStationStringByLgc(orderInfo.getLgcNo(),"") ; 
													sno = stations.split(",") ;
													if (!StringUtils.isEmptyWithTrim(stations)) {
														 m.put("asignStatus", "S") ;
													}
												}
								  			    for (int i = 0; i < sno.length; i++) {
								  			    	if (!StringUtils.isEmptyWithTrim(sno[i])) {
								  			    		OrderSubstation orderSubstation = new OrderSubstation() ;
														orderSubstation.setOrderId(orderInfo.getId());
														orderSubstation.setSubstationNo(sno[i]);
														orderSubstationService.save(orderSubstation);
													}
								  			    	
												}
								  			  if (!StringUtils.isEmptyWithTrim(stations)) {
								  				 PushMsg msg = new PushMsg() ;
									  				msg.setUserNo(stations);
									  				msg.setUserType(PushUserType.SUBSTATION.getValue());
									  				msg.setMsgCode(MsgType.ASIGNTS.getValue());
									  				msg.setMsgContent("新的可接单");
									  				msg.setMsgData(params.get("orderNo"));
									  				msg.setCreateTime(DateUtils.formatDate(nowDate));
									  				msg.setExpireTime(DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 6, 0)));
									  			    //long msgId = msgService.save(msg) ;
									  			    PushUtil.pushByMSG(msg,dynamicDataSourceHolder.getKey()+"_courier");	
									  			  orderService.asign(m) ;
												}
										}
									}
								}
							
							
						   } catch (Exception e) {
                               e.printStackTrace();
						 }
		                 
					  outText(orderInfo.getId()+"", response);
					} catch (Exception e) {
                     e.printStackTrace();
                     outText("0", response);
					}
				}
				// 用于 录入保存
				@RequestMapping(value = { "/take_update" })
				public void take_update(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,OrderInfo orderInfo) throws SQLException {
					try {
						boolean add = false ;
						 Courier courier =courierService.getCourierByNo(orderInfo.getTakeCourierNo()) ;
						Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcNo"), params.get("lgcOrderNo"), null) ;
						boolean join = false ;
						 if (orderMap==null) {
							   
							       if (!lgcConfigService.validateLgcOrderNo(orderInfo.getLgcOrderNo())) {
							    	    outText("录入失败,录入的运单号不符合规则", response);
			                		    return ;
								   }
							     
							     Date nowDate = new Date() ;
						         String orderNo = sequenceService.getNextVal("order_no");
				                 orderInfo.setOrderNo(orderNo);
				                 orderInfo.setCod(0);
				                 if (!StringUtils.isEmptyWithTrim(params.get("tdate"))) {
				                	 orderInfo.setCreateTime(params.get("tdate")+":00");
								}else {
									 orderInfo.setCreateTime(DateUtils.formatDate(nowDate));
								}
				                 LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig") ;
				     			if (lgcConfig!=null) {
				     				orderInfo.setLgcNo(lgcConfig.getLgcNo());
				     			}
				     			
				                 orderInfo.setLastUpdateTime(DateUtils.formatDate(nowDate));
				                 orderInfo.setStatus(1);                     //处理中
				                 orderInfo.setPayStatus(PayStatus.NOPAY.getValue());
				                 orderInfo.setKstatus("CALLED");
				                 orderInfo.setSource("BOSS");
				                 //orderService.save(orderInfo) ;  
				                 add = true ;
				                 //orderMap =  orderService.findByOrderNo(orderNo) ;
				                 orderMap = new HashMap<String, Object>() ;
				                 
				                 orderMap.put("order_no", orderNo) ;
				                 orderMap.put("cod", 0) ;
				                 orderMap.put("status", 1) ;
				                 orderMap.put("source", "BOSS") ;
				                 orderMap.put("pay_status", "000000") ;
				                 
				                 orderMap.put("freight", 0.0) ;
				                 orderMap.put("cpay", 0.0) ;
				                 orderMap.put("vpay", 0.0) ;
				                 orderMap.put("good_price", 0.0) ;
				                 orderMap.put("good_valuation", 0.0) ;
				                 orderMap.put("pay_acount", 0.0) ;
				                 orderMap.put("fpay_status", "INIT") ;
				                 if (courier!=null) {
				                    join =  joinSubsationService.shutAcount(courier.getSubstationNo()) ;
				                 }
				                 
				                 
					     }
						/*	if (Integer.parseInt(orderMap.get("status").toString())<2) {
								  outText("录入失败,运单状态错误，当前为未取件", response);
		                		  return ;
							}*/
						   if (join) {
							    outText("录入失败,寄件网点余额不足", response);
	                		    return ;
						   }
				
						    orderInfo.setTakeOrderTime(params.get("tdate")+":00");
							if (Integer.valueOf(orderMap.get("status").toString())<2) {
								orderInfo.setStatus(2);
							}else {
								orderInfo.setStatus(Integer.valueOf(orderMap.get("status").toString()));
							}
							
							if (add) {
								orderInfo.setStatus(2);
							}else {
								//orderInfo.setStatus(Integer.valueOf(orderMap.get("status").toString()));
								/*if (StringUtils.isEmptyWithTrim(params.get("sendCourierNo"))) {
									outText("录入失败,当前为新增,请输入派件快递员", response);
			                		  return ;
								}*/
							}
							
							String orderNote = StringUtils.nullString(params.get("orderNote")) ;
						    if (StringUtils.isEmptyWithTrim(orderNote)) {
							  orderInfo.setOrderNote(StringUtils.nullString(orderMap.get("orderNote")));
						    }
						    
							if (!StringUtils.isEmptyWithTrim(orderInfo.getTakeCourierNo())) {
								//Courier courier =courierService.getCourierByNo(orderInfo.getTakeCourierNo()) ;
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
						    
							   boolean first = false ;  //是否已经收件
				                 if (Integer.valueOf(orderMap.get("status").toString())<2) {
									first = true ;
								}
							boolean fp = false ;boolean cp = false ;
							if(orderMap.get("fpay_status")!=null&&"SUCCESS".equals(orderMap.get("fpay_status").toString())){fp=true;}
							if(orderMap.get("cpay_status")!=null&&"SUCCESS".equals(orderMap.get("cpay_status").toString())){cp=true;}
							
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
			
			                 boolean z = false ;
			                 String ez = "" ;
			                 String[] zid = {} ;
			                 if (!StringUtils.isEmptyWithTrim(orderInfo.getZidanOrder())) {
									 zid = orderInfo.getZidanOrder().split(",") ;
									for (int i = 0; i < zid.length; i++) {
										Map<String, Object> map = orderService.findByLgcOrderNo(zid[i]) ;
										if (map!=null) {
											z = true ;
											ez = zid[i] ;
										}
									}
							}	
			              if (z) {
			            	  outText("录入失败,子单号已存在："+ez, response);
	                		  return ;
						  }   
			                 
			              
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
		                		
			                	 
			                	 if (!fp) {
										//支付邮费
					                	  if ("HUIYUAN".equals(params.get("payType"))&&pay.compareTo(new BigDecimal("0"))==1) {
												//会员扣费
					                		  
					                		  String disUserNo = params.get("disUserNo") ;
					                		  String pwd = params.get("pwd") ;
					                		  if (StringUtils.isEmptyWithTrim(disUserNo)) {
					                			  outText("录入失败,请输入会员号", response);
						                		  return ;
											  }
					                		  if (StringUtils.isEmptyWithTrim(pwd)) {
					                			  outText("录入失败,请输入会员密码", response);
						                		  return ;
											  }
					                		DisUser disUser = disUserService.getByNo(disUserNo) ;
					                		if (disUser==null) {
					                			outText("录入失败,不存在的会员", response);
						                		  return ;
											}
					                		if (!Md5.md5Str(pwd).equals(disUser.getPwd())) {
					                			  outText("录入失败,会员密码错误", response);
						                		  return ;
											}
					                		
					                		Date nowDate = new Date() ;
					                		
					                		DiscountType discountType = discountTypeService.getById(disUser.getDisType()) ;
					                		BigDecimal d = new BigDecimal( (discountType.getDiscount()/100.0)) ;
					                		
					                		pay = new BigDecimal(orderInfo.getFreight())  ;
					                		BigDecimal rmoney = pay.multiply(d) ;  //实际扣费金额  
					                		rmoney = rmoney.add(new BigDecimal(orderInfo.getVpay())) ;
					                		orderInfo.setTnpay(rmoney.toString());
					                		
					                		DisUserBalance disUserBalance = new DisUserBalance() ;
					                		disUserBalance.setUid(disUser.getId());
					                		disUserBalance.setBalance(rmoney.toString());
					                		disUserBalance.setLastUpdateTime(DateUtils.formatDate(nowDate));
					                		
					                		int r = disUserBalanceService.consume(disUserBalance) ;
					                		
					                		if (r==0) {
					                			  outText("录入失败,会员余额不足", response);
						                		  return ;
											}
					                		
					                		if (r==-1) {
					                			  outText("录入失败,扣费失败", response);
						                		  return ;
											}
					                		
					                		DisUserBalance cur  = disUserBalanceService.getByUid(disUser.getId()) ;
					                		
					                		String afBalance = cur.getBalance();
					                		
					                		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					                		
					                		ConsumeHistory consumeHistory = new ConsumeHistory() ;
					                		consumeHistory.setDisUserNo(disUserNo);
					                		consumeHistory.setRmoney(rmoney.toString());
					                		consumeHistory.setOmoney(pay.toString());
					                		consumeHistory.setAfBalance(afBalance);
					                		consumeHistory.setStatus("SUCCESS");
					                		consumeHistory.setOrderNo(orderInfo.getOrderNo());
					                		consumeHistory.setDiscountText(discountType.getDiscountText());
					                		consumeHistory.setLied("N");
					                		consumeHistory.setCourierNo(orderInfo.getTakeCourierNo());
					                		consumeHistory.setOperator(bossUser.getUserName());
					                		consumeHistory.setCreateTime(DateUtils.formatDate(nowDate));
					                		consumeHistory.setLastUpdateTime(DateUtils.formatDate(nowDate));
					                		consumeHistory.setSource("BOSS");
					                		consumeHistoryService.save(consumeHistory) ;
					                		orderInfo.setFpayStatus("SUCCESS");	
					                		orderInfo.setDisUserNo(disUserNo);
					                		 
										}else {
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
											
										}
					                }else {
					                	orderInfo.setFpayStatus("SUCCESS");
									}
			                	 
			                   /* if (orderInfo.getGoodPrice()>0) {
									snpay = String.valueOf(orderInfo.getGoodPrice()) ;
								}	*/ 
							}else {
								//orderInfo.setPayType(null);
								//orderInfo.setMonthSettleNo(null);
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
			                 
			                 LgcAddr lgcAddr = new LgcAddr() ;
			                 lgcAddr.setLgcNo(orderInfo.getLgcNo());
			                 lgcAddr.setAddrSrc("ORDER_INPUT");  //订单录入
			                 if(!StringUtil.isEmptyWithTrim(orderInfo.getSendPhone())&&!StringUtil.isEmptyWithTrim(orderInfo.getSendName())&&
			                		 !StringUtil.isEmptyWithTrim(orderInfo.getSendArea())&&!StringUtil.isEmptyWithTrim(orderInfo.getSendAddr())){
			                	 lgcAddr.setPhone(orderInfo.getSendPhone());
			                	 lgcAddr.setName(orderInfo.getSendName());
			                	 lgcAddr.setAreaAddr(orderInfo.getSendArea().replaceAll("-", ""));
			                	 lgcAddr.setDetailAddr(orderInfo.getSendAddr());
			                	 lgcAddr.setAddrType("SEND");
			                	 lgcAddr.setCourierNo(orderInfo.getTakeCourierNo());
			                	 if (!lgcAddrService.isExist(lgcAddr)) {
									lgcAddrService.saveLgcAddr(lgcAddr) ;
								}
			                 }
			                 if(!StringUtil.isEmptyWithTrim(orderInfo.getRevPhone())&&!StringUtil.isEmptyWithTrim(orderInfo.getRevName())&&
			                		 !StringUtil.isEmptyWithTrim(orderInfo.getRevArea())&&!StringUtil.isEmptyWithTrim(orderInfo.getRevAddr())){
			                	 lgcAddr.setPhone(orderInfo.getRevPhone());
			                	 lgcAddr.setName(orderInfo.getRevName());
			                	 lgcAddr.setAreaAddr(orderInfo.getRevArea().replaceAll("-", ""));
			                	 lgcAddr.setDetailAddr(orderInfo.getRevAddr());
			                	 lgcAddr.setAddrType("REV");
			                	 lgcAddr.setCourierNo(orderInfo.getTakeCourierNo());
			                	 if (!lgcAddrService.isExist(lgcAddr)) {
									lgcAddrService.saveLgcAddr(lgcAddr) ;
								}
			                 }
			             	OrderTrack orderTrack = new OrderTrack() ;
							OrderTrack  lastOrderTrack = orderTrackService.getLastOrderTrack(orderMap.get("order_no").toString()) ;
							orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
							orderTrack.setCompleted("N");
							//orderTrack.setIsLast("Y");
							orderTrack.setScanIno(Constants.getUser().getUserName());
							orderTrack.setScanIname(Constants.getUser().getRealName());
							orderTrack.setOrderNo(orderMap.get("order_no").toString());
							if (lastOrderTrack==null) {	
								//orderTrack.setPreNo(params.get("substationNo"));
								//orderTrack.setPreType("S");
							}else {
								orderTrack.setPreNo(lastOrderTrack.getPreNo());
								orderTrack.setPreType(lastOrderTrack.getPreType());
								orderTrack.setOrderStatus(TrackStatus.SEND.toString());
								orderTrack.setParentId(lastOrderTrack.getId());
								orderTrack.setIsLast("Y");
								orderTrack.setScanIno(lastOrderTrack.getScanIno());
								orderTrack.setScanIname(lastOrderTrack.getScanIname());
								orderTrack.setScanOno(Constants.getUser().getUserName());
								orderTrack.setScanOname(Constants.getUser().getRealName());
							}
			                 orderTrack.setContext("订单收件录入,快递员已收件");
			                 if (!StringUtils.isEmptyWithTrim(orderInfo.getTakeCourierNo())) {
			                	 Courier c = new Courier() ;
			                	 c.setCourierNo(orderInfo.getTakeCourierNo());
			                	 //Courier courier =  courierService.getCourierByNo(c) ;
			                	 orderInfo.setSubStationNo(courier.getSubstationNo());
			                	 
			                	 /////////////
			                	if (Integer.valueOf(orderMap.get("status").toString())<2) {
			                		 orderTrack.setCurNo(courier.getCourierNo());
										orderTrack.setCurType("C");
										orderTrack.setNextNo(courier.getSubstationNo());
										orderTrack.setNextType("S");
										String innerPhone = "" ;
										if (!StringUtils.isEmptyWithTrim(courier.getInnerPhone())) {
											innerPhone = "("+courier.getInnerPhone()+")" ;
										}
										orderTrack.setContext("订单收件录入,快递员:"+courier.getRealName()+"已收件，联系号码："+courier.getPhone()+innerPhone);
										 if (!StringUtils.isEmptyWithTrim(orderInfo.getSendCourierNo())) {
											// orderTrack.setIsLast("N");
										 }
										 orderTrack.setOpname(Constants.getUser().getRealName());
										 if (first) {
												orderTrackService.add(orderTrack);
												if (lastOrderTrack!=null) {
													orderTrackService.unLastTrack(lastOrderTrack);
												}
											    if (orderMap.get("source").toString().equals("WEIXIN")&&StringUtils.isEmptyWithTrim(orderInfo.getSendCourierNo())) {
													if (orderMap.get("wx_openid")!=null&&!StringUtils.isEmptyWithTrim(orderMap.get("wx_openid").toString())) {
														Map<String, String> wxMap = new HashMap<String, String>() ;
														wxMap.put("touser",orderMap.get("wx_openid").toString());
														wxMap.put("template_id","b0tc3IWLvQGiLj3ReDbaOvJZI4sD_9lzPP3F1Aq5jrc") ;
														wxMap.put("templateType","3");
														wxMap.put("number", params.get("lgcOrderNo"));
														wxMap.put("company","我们");
														wxMap.put("remark","收件员:"+courier.getRealName());
														WeiXinUtil.push(wxMap);
													}
												}
										}
									
								}
			                	int pid = orderTrack.getId() ;
			                	 if (!StringUtils.isEmptyWithTrim(orderInfo.getSendCourierNo())) {
				                	 c.setCourierNo(orderInfo.getSendCourierNo());
				                	 Courier courier1 =  courierService.getCourierByNo(c) ;
				                	 orderInfo.setSendSubstationNo(courier1.getSubstationNo());
				                		OrderTrack orderTrack1 = new OrderTrack() ;
				                		orderTrack1.setOrderTime(DateUtils.formatDate(new Date()));
										orderTrack1.setCompleted("N");
										orderTrack1.setIsLast("Y");
										orderTrack1.setScanIno(Constants.getUser().getUserName());
										orderTrack1.setScanIname(Constants.getUser().getRealName());
										orderTrack1.setOrderNo(orderMap.get("order_no").toString());
										orderTrack1.setPreNo(orderTrack.getCurNo());
										orderTrack1.setPreType(orderTrack.getCurType());
										orderTrack1.setOrderStatus(TrackStatus.REV.toString());
										orderTrack1.setParentId(pid);
										orderTrack1.setContext("快递员:"+courier1.getRealName()+"正在派件，联系电话："+courier1.getUserName());
										//orderTrackService.add(orderTrack1);


								 }else {
									 if (orderMap.get("send_substation_no")!=null) {
										 orderInfo.setSendSubstationNo(orderMap.get("send_substation_no").toString());
									}
								}
							 }else {
								 if (orderMap.get("sub_station_no")!=null) {
									 orderInfo.setSubStationNo(orderMap.get("sub_station_no").toString());
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
			                 
				            orderInfo.setSendSubstationNo(null);    
			                OrderInfo oInfo = (OrderInfo) orderInfo.clone() ;
			                oInfo.setItemCount("1");
			                oInfo.setCpay(0);
			                oInfo.setVpay(0);
			                oInfo.setMpay(0);
			                oInfo.setGoodValuation(0);
			                oInfo.setGoodPrice(0);
			                oInfo.setFreight(0);
			                if(orderInfo.getItemStatus()==null||"".equals(orderInfo.getItemStatus())){
			                	String itemType = itemTypeService.firstItemStatus();
			                	orderInfo.setItemStatus(itemType);
			                }
			                
			                List<String> orderNos = new ArrayList<String>() ;
				              if (add) {
			                	 orderService.save(orderInfo) ;  
			                	 
			                     if(!StringUtil.isEmptyWithTrim(orderInfo.getSendPhone())){
			                            String customer_no = sequenceService.getNextVal("customer_no") ;
			                            if (!"0".equals(customer_no)) {
											LgcCustomer lgcCustomer = new LgcCustomer() ;
											lgcCustomer.setCustomer_no(Integer.valueOf(customer_no));
											lgcCustomer.setConcat_phone(orderInfo.getSendPhone());
											lgcCustomer.setConcat_name(orderInfo.getSendName());
											lgcCustomer.setConcat_addr(orderInfo.getSendArea()+orderInfo.getSendAddr());
											lgcCustomer.setSource("BOSS");
											lgcCustomer.setCreate_time(DateUtils.formatDate(new Date()));
											lgcCustomerService.saveIfNotExsit(lgcCustomer);
										}
					                 }
			                	 
			                	 orderMap.put("id", orderInfo.getId()) ;
			                	for (int i = 0; i < zid.length; i++) {
			                		String ono = sequenceService.getNextVal("order_no") ;
			                		orderNos.add(ono) ;
									oInfo.setLgcOrderNo(zid[i]);	
									oInfo.setOrderNo(ono);
									orderService.saveZid(oInfo);
									
									orderTrack.setOrderNo(ono);
									orderTrack.setOpname(Constants.getUser().getRealName());
									orderTrackService.add(orderTrack);
								}
			                if (!StringUtils.isEmptyWithTrim(orderInfo.getSubStationNo())) {
			                	 Substation substation =  substationService.getSubstationByNo(orderInfo.getSubStationNo()) ;
			                	 //加盟网点增加加盟订单数据
			                	 if (substation!=null&&"J".equals(substation.getSubstationType())) {
			                		 FranchiseOrder franchiseOrder = new FranchiseOrder() ;
			                		 franchiseOrder.setOrder_no(orderInfo.getOrderNo());
			                		 franchiseOrder.setLgc_order_no(orderInfo.getLgcOrderNo());
			                		 franchiseOrder.setTake_substation_no(orderInfo.getSubStationNo());
			                		 franchiseOrder.setSend_substation_no(null);
			                		 franchiseOrder.setItem_type(orderInfo.getItemType());
			                		 franchiseOrder.setItem_weight(StringUtils.nullString(orderInfo.getItemWeight(),"0"));
			                		 franchiseOrder.setMoney_type("Z");
			                		 franchiseOrder.setCreate_time(orderInfo.getCreateTime());
								  	franOrderService.saveOrder(franchiseOrder) ;
								}
							}	
			             
			                	
							}
				              
			                 
			                if (StringUtils.isEmptyWithTrim(orderInfo.getReqRece())) {
								orderInfo.setReqRece("0");
							}
			                BossUser bossUser = Constants.getUser() ;
				               bossUser.setInputTimes(1);
				               request.getSession().setAttribute("user", bossUser);
			                orderInfo.setInputer(bossUser.getRealName());
			               
			                 if (first) {
			                	 orderInfo.setMpay(mpay);
			                	 orderInfo.setTakeTime(DateUtils.formatDate(new Date()));
			                	 //Courier courier =courierService.getCourierByNo(orderInfo.getTakeCourierNo()) ;
			                	 if (courier!=null) {
									orderInfo.setSubStationNo(courier.getSubstationNo());
								}
			                	 orderService.takeUpdateFirst(orderInfo) ; 
			                	 
			                		for (String ono:orderNos) {
										oInfo.setOrderNo(ono);
										orderService.takeUpdateFirst(oInfo) ; 
									}
			                	 
							}else {
								orderInfo.setTakeTime(StringUtils.nullString(orderMap.get("take_order_time")));
								orderService.takeUpdate(orderInfo) ;  
								for (String ono:orderNos) {
									oInfo.setOrderNo(ono);
									orderService.takeUpdate(oInfo) ; 
								}

							}
			              
			                 //增加备注记录
			                 if (!StringUtils.isEmptyWithTrim(orderNote)) {
			                	 String id = StringUtils.nullString(orderMap.get("id")) ;
				                    Map<String, String> nMap = new HashMap<String, String>() ;
				                    nMap.put("operator", bossUser.getRealName()) ;
				                    nMap.put("orderId", id.toString()) ;
				                    nMap.put("note", orderNote) ;
				                    nMap.put("opSrc", "备注") ;
				                    nMap.put("createTime", DateUtils.formatDate(new Date())) ;
									orderService.note(nMap);
							  }
			                 
			                 String addrString  = "" ;
			                 if (!StringUtils.isEmptyWithTrim(orderInfo.getSendArea())) {
			                	 addrString = orderInfo.getSendArea() ;
			                	   if (!StringUtils.isEmptyWithTrim(orderInfo.getSendAddr())) {
			                		   addrString += orderInfo.getSendAddr() ;
								}
			                  // LocationUtil.Location(addrString, "S", orderService, orderInfo.getOrderNo(),dynamicDataSourceHolder.getKey(),dynamicDataSourceHolder);
							 }
			                 
			                 if (!StringUtils.isEmptyWithTrim(orderInfo.getRevArea())) {
			                	 addrString = orderInfo.getRevArea() ;
			                	   if (!StringUtils.isEmptyWithTrim(orderInfo.getRevAddr())) {
			                		   addrString += orderInfo.getRevAddr() ;
								}
			                  // LocationUtil.Location(addrString, "R", orderService, orderInfo.getOrderNo(),dynamicDataSourceHolder.getKey(),dynamicDataSourceHolder);
							 }
			                 
			                 
						  outText("1", response);
						
					  
					} catch (Exception e) {
                     e.printStackTrace();
                     outText("录入失败,数据有误", response);
					}
				}		
	     
	    // 用于
		@RequestMapping(value = { "/list" })
		public String list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
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
			
			if (StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
				System.out.println("============="+Constants.getUser().getLgcNo());
				params.put("lgcNo", Constants.getUser().getLgcNo()) ;
				
			}
			Date nowDate = new Date() ;
			String beginTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
			String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
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
			//默认为分配
			if (StringUtils.isEmptyWithTrim(params.get("ff"))) {
				params.put("noneAssign", "1") ;
				params.put("ff", "1") ;
				params.put("asign", "NONE") ;
			}else {
				if ("NONE".equals(params.get("asign"))) {
					params.put("noneAssign", "1") ;
				}
				if ("SUBSTATION".equals(params.get("asign"))) {
					params.put("assignTos", "1") ;
				}
				if ("COURIER".equals(params.get("asign"))) {
					params.put("assignToc", "1") ;
				}
			}
			params.put("nel", "11") ; //只保留未取件
			PageInfo<Map<String, Object>> orderList = orderService.list(params,getPageInfo(cpage));
			List<Map<String, Object>> lgcs = userService.getCurrentLgc();
			List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
			model.put("orderList", orderList) ;
			model.put("lgcs", lgcs) ;
			model.put("substations", substations);
			params.put("createTimeEnd", endTime) ;
			model.put("params", params) ;
			
			params.put("userNo", ((BossUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
			params.put("tab", "order_olist");
			List<TableFiledSort> tableFieldSorts = tableFiledSortService.selectList(params);
			if(tableFieldSorts==null||tableFieldSorts.size()==0){
				params.put("userNo", "");
				tableFieldSorts = tableFiledSortService.selectList(params);
			}
			
			model.put("tableFieldSorts", tableFieldSorts) ;
			
			System.out.println("**************order list end***********************"+System.currentTimeMillis());
			Long end = System.currentTimeMillis() ;
			System.out.println("**************order list 耗时***********************"+(end-begin)+"ms");
			return "order/olist";
		}		
	
		// 交易详情
		@RequestMapping(value = "/detail")
		public String orderDetail(final ModelMap model, int id, String orderNo)  {
			Map<String, Object> orderInfo = orderService.findByOrderNo(id,orderNo);
			Map<String, Object> monthMap = null ;
			Map<String, Object> codMap = null ;
			if(!StringUtil.isEmptyWithTrim(StringUtils.nullString(orderInfo.get("month_settle_no")))){
				monthMap = mobileUserService.getMuserByNo(orderInfo.get("month_settle_no").toString()) ;
			}
			if(!StringUtil.isEmptyWithTrim(StringUtils.nullString(orderInfo.get("cod_name")))){
				codMap = codSettleUserService.getCuserByNo(orderInfo.get("cod_name").toString()) ;
			}
			model.put("orderInfo", orderInfo);
			model.put("monthMap", monthMap);
			model.put("codMap", codMap);
			return "order/detail";
		}
	
		// 物流详情
		@RequestMapping(value = "/track")
		public String orderTrack(@RequestParam Map<String, String> params,final ModelMap model) {
			String lgcOrderNo = "" ;
		   String lgcNo = "";
		   String lgcName = "";
		   String pingyin = "";
		   String orderNo = params.get("orderNo") ;
		   //Map<String, Object> lgcMap = orderService.getLgcInfo(params.get("orderNo"));
		   Map<String, Object> orderMap =  orderService.findByOrderNo(orderNo) ;
		   params.put("orderId", orderMap.get("id").toString()) ;
		   List<Map<String, Object>> noteList =  orderService.getNoteList(params) ;

           lgcOrderNo = StringUtils.nullString(orderMap.get("lgc_order_no"));
		   Map<String, Object> trackMap = new HashMap<String, Object>() ;
		   List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>() ;
		   String ischeck = "0" ;
		if (StringUtils.isEmptyWithTrim(lgcOrderNo)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("time", "");
			map.put("location", "");
			map.put("context", "无此物流单号信息！");
			map.put("ftime", "");
			dataList.add(map);
		} else {
			// 默认申通查询
			// LgcUtil lgcUtil = new StoLgcUtil() ;
			// strackList = lgcUtil.strack(lgcOrderNo) ; //爬申通官网抓取结果
			try {
				if (true) {
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
				}else {
				trackMap = KuaiDiYiBaiUtil.track(pingyin, lgcOrderNo);
				dataList = (List<Map<String, Object>>) trackMap.get("data") ;
				ischeck = trackMap.get("ischeck").toString() ;
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

		}
		model.put("lgcName", lgcName);
		model.put("lgcOrderNo", lgcOrderNo);
		model.put("ischeck", ischeck) ;
		model.put("take_plane", orderMap.get("take_plane")) ;
		model.put("send_plane", orderMap.get("send_plane")) ;
		Collections.reverse(dataList);
		model.put("noteList", noteList);
		model.put("trackList", dataList);
			return "order/track";
		}
		
		// 交易详情
		@RequestMapping(value = "/imagelist")
		public String imagelist(final ModelMap model, String orderNo)  {
			List<Map<String, Object>> images = orderService.getOrderImages(orderNo);
			if (images==null) {
				images = new ArrayList<Map<String,Object>>() ;
			}
			model.put("images", images) ;
			return "order/image_view";
		}
		
		
		
		@RequestMapping(value = "/image")
		public void  getImage(final ModelMap model,
				@RequestParam Map<String, String> params,HttpServletRequest request,HttpServletResponse response){
			 String filePath = params.get("filePath") ;
			 String suffix = filePath.substring(filePath.lastIndexOf(".")+1) ;
		   String mime = "image/jpeg" ;
		   if ("png".equals(suffix)) {
			mime = "image/png" ;
		   }
		   if ("gif".equals(suffix)) {
				mime = "image/gif" ;
			}
			response.setContentType(mime);//设置相应类型,告诉浏览器输出的内容为图片
	        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expire", 0);
	        FileInputStream hFile = null ;
	        OutputStream toClient = null ;
	        try{ 
	    		 hFile = new FileInputStream(configInfo.getImage_root().concat(filePath)); // 以byte流的方式打开文件 d:\1.gif 
							int i=hFile.available(); //得到文件大小 
							byte data[]=new byte[i]; 
							hFile.read(data); //读数据 
							hFile.close(); 
							response.setContentType(mime); //设置返回的文件类型
							response.setContentLength(i);
						    toClient=response.getOutputStream(); //得到向客户端输出二进制数据的对象 
							toClient.write(data); //输出数据 
							toClient.close(); 
			} 
			catch(FileNotFoundException e) //错误处理 
			{   e.printStackTrace();
	        }catch (IOException e) {
				
			} finally{
	        	try {
	        		hFile.close(); 
	            	toClient.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
	        	 
	        }
	        
		  }
		
		
	    // 用于分配
		@RequestMapping(value = { "/asign_s" })
		public String asign_s(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws Exception {
			String orderNo = params.get("orderNo") ;
			Map<String, Object> orderMap = orderService.findByOrderNo(orderNo) ; 
			Map<String, String> paramsMap = new HashMap<String, String>() ;
			PageInfo<Map<String, Object>> lgcs  = null ;
			String sno = "" ;
			String cno = "" ;
			if (orderMap.get("lgc_no")!=null) {
				paramsMap.put("lgcNo", orderMap.get("lgc_no").toString()) ;
				params.put("lgcNo", orderMap.get("lgc_no").toString()) ;
				 lgcs = lgcService.list(paramsMap, getPageInfo(1, 500));
				 sno = orderMap.get("sub_station_no")!=null?orderMap.get("sub_station_no").toString():"" ;
				 cno = orderMap.get("take_courier_no")!=null?orderMap.get("take_courier_no").toString():"" ;
				 
			}else {
				if (!StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
					paramsMap.put("lgcNo", params.get("lgcNo")) ;
				}
				 lgcs = lgcService.list(null, getPageInfo(1, 500));
			}
			
			if (!StringUtils.isEmptyWithTrim(params.get("substationName"))) {
				paramsMap.put("substationName",params.get("substationName"));
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
			PageInfo<Map<String, Object>> sList = substationService.alist(params,getPageInfo(cpage));
			String sname = "" ;
			String cname = "" ;
			Date nowDate = new Date() ;
			if (!StringUtils.isEmptyWithTrim(sno)) {
				paramsMap.clear();
				paramsMap.put("substationNo", sno) ;
				sname = substationService.list(paramsMap, getPageInfo(1, 1)).getList().get(0).get("substation_name").toString() ;
				if (!StringUtils.isEmptyWithTrim(cno)) {
					Courier c = new Courier() ;
					c.setCourierNo(cno);
					Courier courier = courierService.getCourierByNo(c) ;
					cname = courier.getRealName()+courier.getPhone() ;
				}
			}
			model.put("sub_station_name",sname) ;
			model.put("take_courier_name",cname) ;
			model.put("sub_station_no",sno) ;
			model.put("take_courier_no",cno) ;
			model.put("lgcs", lgcs) ;
			model.put("sList", sList) ;
			model.put("params", params) ;
			return "order/slist";
		}	
		
		
	    // 用于分配到快递员
			@RequestMapping(value = { "/asign_ss" })
			public String asign_ss(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws Exception {
				String orderNo = params.get("orderNo") ;
				//Map<String, Object> orderMap = orderService.findByOrderNo(orderNo) ; 
				List<Map<String, Object>> oList =  orderService.findByIds(params.get("ids")) ;
				String stations = "" ;
				String  cno = "" ;
				if (!params.get("ids").contains(",")) {
					stations = orderSubstationService.getStationString(Integer.valueOf(params.get("ids")),"S") ;
					cno = oList.get(0).get("take_courier_no")!=null?oList.get(0).get("take_courier_no").toString():"" ;
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
				Map<String, Object> mode = lgcModeService.getCurMode() ;
				if ("AUTO_ALL".equals(mode.get("code"))||"MANUAL_PERSON".equals(mode.get("code"))) {
					params.put("mode", "2") ;
					params.put("AUTO_ALL", "1") ;
				}
				PageInfo<Map<String, Object>> cList = courierService.asignList(params, getPageInfo(cpage)) ;
				model.put("stations",stations+",C"+cno) ;
				model.put("take_courier_no",cno) ;
				model.put("cList", cList) ;
				model.put("oList", oList) ;
				model.put("params", params) ;
				params.put("p", cpage+"") ;
				return "order/clist1";
			}	
		
		
		   // 用于分配到快递员
				@RequestMapping(value = { "/asign_c" })
				public String asign_c(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
					String orderNo = params.get("orderNo") ;
					Map<String, Object> orderMap = orderService.findByOrderNo(orderNo) ; 
					Map<String, String> paramsMap = new HashMap<String, String>() ;
					PageInfo<Map<String, Object>> lgcs  = null ;
					if (orderMap.get("lgc_no")!=null) {
						paramsMap.put("lgcNo", orderMap.get("lgc_no").toString()) ;
					}
					lgcs = lgcService.list(paramsMap, getPageInfo(1, 500));
					if (!StringUtils.isEmptyWithTrim(params.get("substationName"))) {
						paramsMap.put("substationName",params.get("substationName"));
					}
					PageInfo<Map<String, Object>> cList = courierService.listBySubStation(params.get("substationNo"), getPageInfo(cpage)) ;
					
					String  sno = orderMap.get("sub_station_no")!=null?orderMap.get("sub_station_no").toString():"" ;
					String  cno = orderMap.get("take_courier_no")!=null?orderMap.get("take_courier_no").toString():"" ;
					String sname = "" ;
					String cname = "" ;
					if (!StringUtils.isEmptyWithTrim(sno)) {
						paramsMap.clear();
						paramsMap.put("substationNo", sno) ;
						sname = substationService.list(paramsMap, getPageInfo(1, 1)).getList().get(0).get("substation_name").toString() ;
						if (!StringUtils.isEmptyWithTrim(cno)) {
							Courier c = new Courier() ;
							c.setCourierNo(cno);
							Courier courier = courierService.getCourierByNo(c) ;
							cname = courier.getRealName()+courier.getPhone() ;
						}
					}
					model.put("sub_station_name",sname) ;
					model.put("take_courier_name",cname) ;
					model.put("sub_station_no",sno) ;
					model.put("take_courier_no",cno) ;
					model.put("lgcs", lgcs) ;
					model.put("cList", cList) ;
					model.put("params", params) ;
					return "order/clist";
				}	
				
		
				//用于
				@RequestMapping(value = { "/asign_save"})
				public void asign_save(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, String> params) throws SQLException{
					String r = "0";
					try {
						//Map<String, Object>  orderMap = orderService.findByOrderNo(params.get("orderNo")) ; 
						List<Map<String, Object>> oList =  orderService.findByIds(params.get("ids")) ;
						String substationNo = params.get("substationNo") ;
						String orderIds = params.get("ids") ;
						String sno = params.get("sno").replace("S", "").replace("C", "") ;
						String id[] = orderIds.split(",") ;
						
						Substation substation = null ;
						Courier courier = null ;
						if ("1".equals(params.get("t"))) {  //分配到分站
							substation = substationService.getSubstationByNo(substationNo) ;
						}else {
							courier = courierService.getCourierByNo(sno) ;
						}
						boolean flag = false ;
						for (Map<String, Object>  orderMap:oList) {
							params.put("orderNo", orderMap.get("order_no").toString()) ;
							
							OrderTrack orderTrack = new OrderTrack() ;
							orderTrack.setOrderNo((String)orderMap.get("orderNo"));
							orderTrackService.delByOrderNo(orderTrack);
							orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
							orderTrack.setCompleted("N");
							orderTrack.setOrderStatus(TrackStatus.SEND.toString());
							
							if ("1".equals(params.get("t"))) {  //分配到分站
								
								//String stations = orderSubstationService.getStationString(Integer.valueOf(orderMap.get("id").toString()),"[") ;
								if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("take_courier_no")))) {  //在这之前如果是分配到快递员
									orderSubstationService.delByOId(Integer.valueOf(orderMap.get("id").toString())) ;
								}

								OrderSubstation orderSubstation = new OrderSubstation() ;
								orderSubstation.setOrderId(Integer.valueOf(orderMap.get("id").toString()));
								orderSubstation.setSubstationNo(substationNo);
								orderSubstationService.save(orderSubstation);
								
								
								params.put("substationNo", null);
								params.put("courierNo", null);
								params.put("asignStatus", "S") ;
								
								
								orderTrack.setContext("快件已分配到分站："+substation.getSubstationName());
								orderTrack.setNextNo(substationNo);
								orderTrack.setNextType("S");
								
							}else{  //分配到快递员
								
								
								params.put("substationNo", substationNo) ;
								params.put("courierNo", sno.replace("[", "")) ;
								params.put("asignStatus", "C") ;
								orderSubstationService.updateByOId(Integer.valueOf(orderMap.get("id").toString()), 0) ;
								if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("addr_id")))) {
									Map<String, Object> map = new HashMap<String, Object>() ;
									map.put("courierNo", params.get("courierNo")) ;
									map.put("addrId", orderMap.get("addr_id")) ;
									lgcAddrService.updateCourier(map) ;
								}else {
									  LgcAddr lgcAddr = new LgcAddr() ;
					                 lgcAddr.setLgcNo(StringUtils.nullString(orderMap.get("lgc_no")));
					                 lgcAddr.setAddrSrc("ORDER_ASIGN");  
								 	 lgcAddr.setPhone(StringUtils.nullString(orderMap.get("send_phone")));
				                	 lgcAddr.setName(StringUtils.nullString(orderMap.get("send_name")));
				                	 lgcAddr.setAreaAddr(StringUtils.nullString(orderMap.get("send_area")).replaceAll("-", ""));
				                	 lgcAddr.setDetailAddr(StringUtils.nullString(orderMap.get("send_addr")));
				                	 lgcAddr.setAddrType("SEND");
				                	 lgcAddr.setCourierNo(params.get("courierNo"));
				                	 Map<String, Object> addr = lgcAddrService.isExistA(lgcAddr) ;
				                	 Object addrId = 0 ;
				                	 if (addr==null) {
										lgcAddrService.saveLgcAddr(lgcAddr) ;
										addrId = lgcAddr.getId() ;
									}else {
										addrId = addr.get("id") ;
										Map<String, Object> map = new HashMap<String, Object>() ;
										map.put("courierNo", params.get("courierNo")) ;
										map.put("addrId", addrId) ;
										lgcAddrService.updateCourier(map) ;
									}
								}
								//orderSubstationService.delByOId(Integer.valueOf(orderId)) ;
								
								orderTrack.setContext("快件已分配给快递员："+courier.getRealName()+",联系电话："+courier.getPhone());
								orderTrack.setNextNo(params.get("courierNo"));
								orderTrack.setNextType("C");
							}
							orderTrack.setOpname(Constants.getUser().getRealName());
							orderService.asign(params);
							orderTrackService.add(orderTrack);
							
							Date createDate = (Date) orderMap.get("create_time") ;
							Date nowDate = new Date() ;
							AsignOrder asignOrder = new AsignOrder() ;
							asignOrder.setOrder_no(orderMap.get("order_no").toString());
							asignOrder.setOrder_time(DateUtils.formatDate(createDate));
							asignOrder.setAsign_time(DateUtils.formatDate(nowDate));
							asignOrder.setAsing_date(DateUtils.formatDate(nowDate,"yyyy-MM-dd"));
							asignOrder.setOrder_date(DateUtils.formatDate(createDate,"yyyy-MM-dd"));
							asignOrder.setAsign_no(Constants.getUser().getUserName());
							asignOrder.setAsign_name(Constants.getUser().getRealName());
							double a = nowDate.getTime()-createDate.getTime() ;
							double  asign_duration = a/(1000*60) ;
							asignOrder.setAsign_duration(new BigDecimal(Math.ceil(asign_duration)));
							asignOrderService.save(asignOrder);
							
							{	
								
								
								if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("take_courier_no")))) {
									 PushMsg msg1 = new PushMsg() ;
						  			  msg1.setUserNo(StringUtils.nullString(orderMap.get("take_courier_no")));
						  			  msg1.setUserType(PushUserType.COURIER.getValue());
						  			  msg1.setMsgCode(MsgType.ACANCEL.getValue());
						  			  msg1.setMsgContent("您的快件已被后台取消分配！");
						  			   msg1.setMsgData((String)orderMap.get("order_no"));
						  			   msg1.setCreateTime(DateUtils.formatDate(nowDate));
						  			    msg1.setExpireTime(DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 6, 0)));
						   			    long msgId1 = msgService.save(msg1) ;
						   			    System.out.println("______________________________>"+dynamicDataSourceHolder);
						   			    System.out.println("______________________________>"+dynamicDataSourceHolder.getKey());
						   			    PushUtil.pushById(String.valueOf(msg1.getId()),PushUserType.COURIER.getValue(),dynamicDataSourceHolder.getKey()+"_courier");
								}
								
								if ("1".equals(params.get("t"))) {  //分站
						  			    if (!flag) {
						  			    	 PushMsg msg = new PushMsg() ;
								  				msg.setUserNo(substationNo);
								  				msg.setUserType(PushUserType.SUBSTATION.getValue());
								  				msg.setMsgCode(MsgType.ASIGNTS.getValue());
								  				msg.setMsgContent("新的可接单");
								  				msg.setMsgData((String)orderMap.get("order_no"));
								  				msg.setCreateTime(DateUtils.formatDate(nowDate));
								  				msg.setExpireTime(DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 6, 0)));
								  			    //long msgId = msgService.save(msg) ;
								  			    PushUtil.pushByMSG(msg,dynamicDataSourceHolder.getKey()+"_courier");
								  			    flag = true ;
										}
								}else {
									Map<String, Object> mode = lgcModeService.getCurMode() ;
									if ("AUTO_ALL".equals(mode.get("code"))) {   //群发
										
										if (!flag) {
											PushMsg msg = new PushMsg() ;
							  				msg.setUserNo(orderMap.get("lgc_no").toString());
							  				msg.setUserType(5);
							  				msg.setMsgCode(MsgType.ASIGNTS.getValue());
							  				msg.setMsgContent("订单已被后台重新分配！请注意刷新");
							  				msg.setMsgData((String)orderMap.get("order_no"));
							  				msg.setCreateTime(DateUtils.formatDate(nowDate));
							  				msg.setExpireTime(DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 6, 0)));
							  			    //long msgId = msgService.save(msg) ;
							  			    PushUtil.pushByMSG(msg,dynamicDataSourceHolder.getKey()+"_courier");
							  			   flag = true ;
										}
									}else {                     //向分站发送
										 String stations = orderSubstationService.getStationString(Integer.valueOf(orderMap.get("id").toString()),"") ;
										   PushMsg msg = new PushMsg() ;
							  				msg.setUserNo(stations);
							  				msg.setUserType(PushUserType.SUBSTATION.getValue());
							  				msg.setMsgCode(MsgType.ASIGNTS.getValue());
							  				msg.setMsgContent("订单已被后台重新分配！请注意刷新");
							  				msg.setMsgData((String)orderMap.get("order_no"));
							  				msg.setCreateTime(DateUtils.formatDate(nowDate));
							  				msg.setExpireTime(DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 6, 0)));
							  			    //long msgId = msgService.save(msg) ;
							  			    PushUtil.pushByMSG(msg,dynamicDataSourceHolder.getKey()+"_courier");
									}
									
						////////////////
									  String take_addr =  orderMap.get("take_addr") +"";
									  if (take_addr.length()<=0||orderMap.get("take_addr")==null||StringUtils.isEmptyWithTrim(orderMap.get("take_addr").toString())) {
										take_addr = orderMap.get("send_addr").toString() ;
									  }
							         PushMsg msg = new PushMsg() ;
						  				msg.setUserNo(params.get("courierNo"));
						  				msg.setUserType(2);
						  				msg.setMsgCode(MsgType.COME.getValue());
						  				msg.setMsgContent(DateUtils.formatDate((Date)orderMap.get("take_time_begin"), "MM月dd HH:mm")+"在"+take_addr+"有一个新的快件待取！\r\n联系方式"+orderMap.get("send_phone")+"请尽快取件！");
						  				msg.setMsgData(params.get("orderNo"));
						  				msg.setCreateTime(DateUtils.formatDate(nowDate));
						  				msg.setExpireTime(DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 6, 0)));
						  			    long msgId = msgService.save(msg) ;
						  			    PushUtil.pushById(String.valueOf(msg.getId()),2,dynamicDataSourceHolder.getKey()+"_courier");
						  			    
									}
								LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig") ;
								//微信推送
								  if (orderMap.get("source").toString().equals("WEIXIN")) {
										if (orderMap.get("wx_openid")!=null&&!StringUtils.isEmptyWithTrim(orderMap.get("wx_openid").toString())) {
											Map<String, String> wxMap = new HashMap<String, String>() ;
											wxMap.put("url","http://gdt.pro-wxxd.yogapay.com/page/queryOrderList?lgcNo="+lgcConfig.getLgcNo()+"&uid="+lgcConfig.getKey()+"&orderNo=");
											wxMap.put("dskey",dynamicDataSourceHolder.getKey());
											wxMap.put("t","1");
											wxMap.put("touser",orderMap.get("wx_openid").toString());
											wxMap.put("sendName",StringUtils.nullString(orderMap.get("rev_name")));
											wxMap.put("sendPhone",StringUtils.nullString(orderMap.get("rev_phone")));
											wxMap.put("lgcName","");
											wxMap.put("sendAddr",StringUtils.nullString(orderMap.get("rev_addr")));
											wxMap.put("remark","您发的快件已分配给快递员,快递员来的途中,请耐心等待！");
											wxMap.put("frist","尊敬的客户，您好,您的订单已受理");
											WeiXinUtil.push(wxMap);
										}
									}
						  }
								
							
					}
						
			  			    
			  			/*    if (orderMap.get("source").toString().equals("WEIXIN")) {
								if (orderMap.get("wx_openid")!=null&&!StringUtils.isEmptyWithTrim(orderMap.get("wx_openid").toString())) {
									Map<String, String> wxMap = new HashMap<String, String>() ;
									wxMap.put("touser",orderMap.get("wx_openid").toString());
									wxMap.put("template_id","muU2ULymy1iaOxHj2S0cw5MfG2cXMKQv_cGX7y8PfdE") ;
									wxMap.put("templateType","1");
									wxMap.put("number","");
									wxMap.put("statusName","您发的快件已分配给快递员："+params.get("cName")+",联系电话："+params.get("cPhone")+",快递员来的途中,请耐心等待！");
									wxMap.put("remark",DateUtils.formatDate(nowDate));
									wxMap.put("frist","尊敬的客户，您好");
									WeiXinUtil.push(wxMap);
								}
							}else {
								 if (orderMap.get("user_no")!=null) {
					  				 PushMsg msg1 = new PushMsg() ;
						  			   msg1.setUserNo(orderMap.get("user_no").toString());
						  			   msg1.setUserType(1);
						  			   msg1.setMsgCode(MsgType.COME.getValue());
						  			   msg1.setMsgContent("您发的快件已分配给快递员："+params.get("cName")+",联系电话："+params.get("cPhone")+",快递员来的途中,请耐心等待！");
						  			   msg1.setMsgData(params.get("orderNo"));
						  			   msg1.setCreateTime(nowDate);
						  			    msg1.setExpireTime(DateUtils.addDate(nowDate, 0, 6, 0));
						   			    long msgId1 = msgService.save(msg1) ;
						   			    PushUtil.pushById(String.valueOf(msg1.getId()),1,dynamicDataSourceHolder.getKey()+"_courier");
								} 
							}*/
			  			  
						r=params.get("cid") ;
					} catch (Exception e) {
						e.printStackTrace();
						r = "0";
					}
					outText(r, response);
				}
				
				//用于分配给分站
				@RequestMapping(value = { "/asign_tos"})
				public void asign_tos(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, String> params) throws SQLException{
					String r = "";
					try {
						Map<String, Object>  orderMap = orderService.findByOrderNo(params.get("orderNo")) ; 
						
						/*if (StringUtils.isEmptyWithTrim(orderMap.get("take_courier_no"))) {
							
						}*/
						if (Integer.parseInt(orderMap.get("status").toString())!=1) {
							System.out.println(orderMap.get("status").toString()+"订单当前状态不允许分配操作");
							r = "订单当前状态不允许分配操作！" ;
						}else {
							Date nowDate = new Date() ;
							String sno = orderMap.get("sub_station_no")!=null?orderMap.get("sub_station_no").toString():"" ;
							String cno = orderMap.get("take_courier_no")!=null?orderMap.get("take_courier_no").toString():"" ;
							if (!StringUtils.isEmptyWithTrim(sno)) {
								if (!StringUtils.isEmptyWithTrim(cno)) {
									 PushMsg msg1 = new PushMsg() ;
						  			  msg1.setUserNo(cno);
						  			  msg1.setUserType(PushUserType.COURIER.getValue());
						  			  msg1.setMsgCode(MsgType.ACANCEL.getValue());
						  			  msg1.setMsgContent("您的快件已被后台取消分配！");
						  			   msg1.setMsgData(params.get("orderNo"));
						  			   msg1.setCreateTime(DateUtils.formatDate(nowDate));
						  			    msg1.setExpireTime(DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 6, 0)));
						   			    long msgId1 = msgService.save(msg1) ;
						   			    PushUtil.pushById(String.valueOf(msg1.getId()),PushUserType.COURIER.getValue(),dynamicDataSourceHolder.getKey()+"_courier");
								}else {
									 PushMsg msg = new PushMsg() ;
						  				msg.setUserNo(sno);
						  				msg.setUserType(PushUserType.SUBSTATION.getValue());
						  				msg.setMsgCode(MsgType.ACANCEL.getValue());
						  				msg.setMsgContent("订单已被后台重新分配！请注意刷新");
						  				msg.setMsgData(params.get("orderNo"));
						  				msg.setCreateTime(DateUtils.formatDate(nowDate));
						  				msg.setExpireTime(DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 6, 0)));
						  			    //long msgId = msgService.save(msg) ;
						  			    PushUtil.pushByMSG(msg,dynamicDataSourceHolder.getKey()+"_courier");
								}
							}
							orderService.asignTos(params);
							
							OrderTrack orderTrack = new OrderTrack() ;
							orderTrack.setOrderNo(params.get("orderNo"));
							orderTrackService.delByOrderNo(orderTrack);
							
							////////////////
					         PushMsg msg = new PushMsg() ;
				  				msg.setUserNo(params.get("substationNo"));
				  				msg.setUserType(PushUserType.SUBSTATION.getValue());
				  				msg.setMsgCode(MsgType.ASIGNTS.getValue());
				  				msg.setMsgContent(DateUtils.formatDate((Date)orderMap.get("take_time_begin"), "MM/dd HH:mm")+"在"+orderMap.get("take_addr")+"有一个新的快件！联系方式"+orderMap.get("send_phone"));
				  				msg.setMsgData(params.get("orderNo"));
				  				msg.setCreateTime(DateUtils.formatDate(nowDate));
				  				msg.setExpireTime(DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 6, 0)));
				  			    //long msgId = msgService.save(msg) ;
				  			    PushUtil.pushByMSG(msg,dynamicDataSourceHolder.getKey()+"_courier");
							r="1" ;
						}
					
					} catch (Exception e) {
						e.printStackTrace();
						r = "分配失败";
					}
					outText(r, response);
				}
				
				//导出运单列表
		@RequestMapping(value = { "/export" })
		public void export(@RequestParam Map<String, String> params,
				HttpServletResponse response, HttpServletRequest request)
				throws SQLException {
			params.put("sub_limit", StringUtils.nullString(request.getAttribute("sub_limit"))) ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			OutputStream os = null;
			try {
				request.setCharacterEncoding("UTF-8");
				os = response.getOutputStream(); // 取得输出流
				response.reset(); // 清空输出流
				response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
				if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					if (SecurityUtils.getSubject().isPermitted("ORDER_ASSIGN")) {
						params.put("orderAssign", "1") ;
					}
				}
				if(!StringUtils. isEmpty(params.get("serviceName"))){
	                if("exportOrder".equals(params.get("serviceName"))){
	                	 String fileName = "分配列表明细-" + sdf.format(new Date()) + ".xls";
	                     response.setHeader("Content-disposition", "attachment;filename="
	                             + new String(fileName.getBytes("GBK"), "ISO8859-1"));
	                     exportOrder(os, params, fileName);
	                }
	                
	                if("exportOrderList".equals(params.get("serviceName"))){
	                	 String fileName = "运单列表明细-" + sdf.format(new Date()) + ".xls";
	                     response.setHeader("Content-disposition", "attachment;filename="
	                             + new String(fileName.getBytes("GBK"), "ISO8859-1"));
	                     exportOrderList(os, params, fileName);
	                }
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		public void exportOrder(OutputStream os, Map<String, String> params,
				String fileName) throws Exception {
			int row = 1; // 从第三行开始写
			int col = 0; // 从第一列开始写
			String orderNos = params.get("orderNo") ;
			
			if (!StringUtil.isEmptyWithTrim(orderNos)) {
				String o = "'0'" ;
				String[] ods = orderNos.split("\r\n") ;
				for (int i = 0; i < ods.length; i++) {
					o=o+ ",'"+ods[i]+"'" ;
				}
				params.put("orderNos", o) ;
			}
			
			if (StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
				System.out.println("============="+Constants.getUser().getLgcNo());
				params.put("lgcNo", Constants.getUser().getLgcNo()) ;
				
			}
			Date nowDate = new Date() ;
			String beginTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
			String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
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
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(params.get("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}
				params.put("substationNo", substationNo);
				if (SecurityUtils.getSubject().isPermitted("ORDER_ASSIGN_")) {
					params.put("orderAssign", "1") ;
				}
			}
			//默认为分配
			if (StringUtils.isEmptyWithTrim(params.get("ff"))) {
				params.put("noneAssign", "1") ;
				params.put("ff", "1") ;
				params.put("asign", "NONE") ;
			}else {
				if ("NONE".equals(params.get("asign"))) {
					params.put("noneAssign", "1") ;
				}
				if ("SUBSTATION".equals(params.get("asign"))) {
					params.put("assignTos", "1") ;
				}
				if ("COURIER".equals(params.get("asign"))) {
					params.put("assignToc", "1") ;
				}
			}
			params.put("nel", "11") ; //只保留未取件
			List<Map<String, Object>> orderList = orderService.list(params);
			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream(
					"/template/order.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);

			Iterator<Map<String, Object>> it = orderList.iterator();
			while (it.hasNext()) {

				Map<String, Object> map = it.next();

				ws.addCell(new Label(col++, row,  (String)map.get("order_no")));
				ws.addCell(new Label(col++, row, (String)map.get("lgc_order_no")));
				
				String status = String.valueOf(map.get("status"));
				status = cacheService.findByNameKey("ORDER_STATUS", status).getDictValue();
				ws.addCell(new Label(col++, row, status));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map
						.get("create_time"))));

				ws.addCell(new Label(col++, row, StringUtils.nullString(map
						.get("send_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map
						.get("send_phone"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_area"))+StringUtils.nullString(map.get("send_addr"))));

				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_phone"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_area"))+StringUtils.nullString(map.get("rev_addr"))));
				
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_name"))));
				String itemType = String.valueOf(map.get("item_type"));
				if("1".equals(itemType) || "2".equals(itemType)){
					itemType = "大物件";
				}else {
					itemType = "小物件";
				}
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_Status"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_weight"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("name"))));
				
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("freight"))));//支付邮费
				String freightType = StringUtils.nullString(map.get("freight_type"));
				if(StringUtil.isEmpty(freightType)||"0".equals(freightType)){
					freightType = "寄方付";
				}else if("1".equals(freightType)){
					freightType = "寄方付";
				}else if("2".equals(freightType)){
					freightType = "收方付";
				}else{
					freightType = "月结";
				}
				ws.addCell(new Label(col++, row, freightType));	//邮费类型
				if(StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("sub_station_no")))){
					ws.addCell(new Label(col++, row, "未分配"));
				}else if((!StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("sub_station_no")))) 
						&& StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("take_courier_no")))){
					ws.addCell(new Label(col++, row, "已分配"));
				}else if((!StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("sub_station_no")))) 
						&& (!StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("take_courier_no"))))){
					ws.addCell(new Label(col++, row, "已分配"));
				}
				ws.addCell(new Label(col++, row,
						StringUtils.nullString(map.get("order_note"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("take_time_begin"))));//取件时间
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("real_name"))));//取件快递员
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("take_addr"))));//取件地址
				
				if (map.get("send_courier_no")!=null&&!StringUtils.isEmptyWithTrim(map.get("send_courier_no").toString())) {
					Courier courier = new Courier() ;
					courier.setCourierNo(map.get("send_courier_no").toString());
					courier = courierService.getCourierByNo(courier) ;
					if (courier!=null) {
						ws.addCell(new Label(col++, row, courier.getRealName()));//派件快递员
					}else {
						ws.addCell(new Label(col++, row, ""));//派件快递员
					}
					
				}else {
					ws.addCell(new Label(col++, row, ""));//派件快递员
				}
				
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("month_settle_name"))));//月结账号名
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("month_settle_no"))));//月结账号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("month_settle_card"))));//月结卡号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("mpay"))));//月结金额
				if(!StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("cod")))&&"1".equals(StringUtils.nullString(map.get("cod")))){
					ws.addCell(new Label(col++, row, "是"));//是否代收货款
				}else{
					ws.addCell(new Label(col++, row, "否"));//是否代收货款
				}
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_card_no"))));//代收卡号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_name"))));//代收卡姓名
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("good_price"))));//物品价格
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_card_cnaps_no"))));//银行卡联行号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_bank"))));//银行卡名称
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("good_valuation"))));//保价金额
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("good_valuation_rate"))));//保价费率

				row++;
				col = 0;
			}

			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}
		
		public void exportOrderList(OutputStream os, Map<String, String> params,
				String fileName) throws Exception {
			int row = 1; // 从第三行开始写
			int col = 0; // 从第一列开始写
			String orderNos = params.get("orderNo") ;
			
			if (!StringUtil.isEmptyWithTrim(orderNos)) {
				String o = "'0'" ;
				String[] ods = orderNos.split("\r\n") ;
				for (int i = 0; i < ods.length; i++) {
					o=o+ ",'"+ods[i]+"'" ;
				}
				params.put("orderNos", o) ;
			}
			
			if (StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
				System.out.println("============="+Constants.getUser().getLgcNo());
				params.put("lgcNo", Constants.getUser().getLgcNo()) ;
				
			}
			Date nowDate = new Date() ;
			String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -3, 0, 0),"yyyy-MM-dd") ;
			String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
			if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
				params.put("createTimeBegin", beginTime) ;
			}
			if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
				endTime = params.get("createTimeEnd") ;
			}
			params.put("createTimeEnd", endTime+" 23:59:59") ;
			
			String sendTime = params.get("sendTimeEnd") ;
			if (!StringUtils.isEmptyWithTrim(sendTime)) {
				params.put("sendTimeEnd", sendTime+" 23:59:59") ;
			}
			
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(params.get("sub_limit")))) {
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
			Map<String, String> pMap = new HashMap<String, String>(params) ;
			pMap.put("take_or_send_no", pMap.get("substationNo")) ;
			pMap.remove("substationNo") ;
			List<Map<String, Object>> orderList = orderService.list(pMap);
			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream(
					"/template/order.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);

			Iterator<Map<String, Object>> it = orderList.iterator();
			while (it.hasNext()) {

				Map<String, Object> map = it.next();

				ws.addCell(new Label(col++, row,  (String)map.get("order_no")));
				ws.addCell(new Label(col++, row, (String)map.get("lgc_order_no")));
				
				String status = String.valueOf(map.get("status"));
				status = cacheService.findByNameKey("ORDER_STATUS", status).getDictValue();
				ws.addCell(new Label(col++, row, status));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map
						.get("create_time"))));

				ws.addCell(new Label(col++, row, StringUtils.nullString(map
						.get("send_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map
						.get("send_phone"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_area"))+StringUtils.nullString(map.get("send_addr"))));

				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_phone"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_area"))+StringUtils.nullString(map.get("rev_addr"))));
				
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_name"))));
				String itemType = String.valueOf(map.get("item_type"));
				if("1".equals(itemType) || "2".equals(itemType)){
					itemType = "大物件";
				}else {
					itemType = "小物件";
				}
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_Status"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_weight"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("name"))));
				
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("freight"))));//支付邮费
				String freightType = StringUtils.nullString(map.get("freight_type"));
				if(StringUtil.isEmpty(freightType)||"0".equals(freightType)){
					freightType = "寄方付";
				}else if("1".equals(freightType)){
					freightType = "寄方付";
				}else if("2".equals(freightType)){
					freightType = "收方付";
				}else{
					freightType = "月结";
				}
				ws.addCell(new Label(col++, row, freightType));	//邮费类型
				if(StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("sub_station_no")))){
					ws.addCell(new Label(col++, row, "未分配"));
				}else if((!StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("sub_station_no")))) 
						&& StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("take_courier_no")))){
					ws.addCell(new Label(col++, row, "已分配"));
				}else if((!StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("sub_station_no")))) 
						&& (!StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("take_courier_no"))))){
					ws.addCell(new Label(col++, row, "已分配"));
				}
				ws.addCell(new Label(col++, row,
						StringUtils.nullString(map.get("order_note"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("take_order_time"))));//取件时间
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("take_courier_name"))));//取件快递员
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("take_addr"))));//取件地址
				
				if (map.get("send_courier_no")!=null&&!StringUtils.isEmptyWithTrim(map.get("send_courier_no").toString())) {
					Courier courier = new Courier() ;
					courier.setCourierNo(map.get("send_courier_no").toString());
					courier = courierService.getCourierByNo(courier) ;
					if (courier!=null) {
						ws.addCell(new Label(col++, row, courier.getRealName()));//派件快递员
					}else {
						ws.addCell(new Label(col++, row, ""));//派件快递员
					}
					
				}else {
					ws.addCell(new Label(col++, row, ""));//派件快递员
				}
				
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("month_settle_name"))));//月结账号名
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("month_settle_no"))));//月结账号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("month_settle_card"))));//月结卡号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("mpay"))));//月结金额
				if(!StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("cod")))&&"1".equals(StringUtils.nullString(map.get("cod")))){
					ws.addCell(new Label(col++, row, "是"));//是否代收货款
				}else{
					ws.addCell(new Label(col++, row, "否"));//是否代收货款
				}
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_card_no"))));//代收卡号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_name"))));//代收卡姓名
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("good_price"))));//物品价格
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_card_cnaps_no"))));//银行卡联行号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_bank"))));//银行卡名称
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("good_valuation"))));//保价金额
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("good_valuation_rate"))));//保价费率

				row++;
				col = 0;
			}

			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}
		
		
		      // 用于面单上传
				@RequestMapping(value = { "/plane" })
				public String plane(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					pageList = null ;
					model.put("params", params);
					model.put("list", pageList);
					return "order/plane";
				}		
		
				 // 用于订单录入
				@RequestMapping(value = { "/input" })
				public String input(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Map<String, Object> orderMap = null ;
					if(!StringUtils.isEmptyWithTrim(params.get("lgcOrderNo"))) {
						orderMap = orderService.findByLgcOrderNo(params.get("lgcNo"), params.get("noFix")+params.get("lgcOrderNo"), null) ;
						params.put("first", "Y") ;
						if (orderMap==null) {
							params.put("msg", "运单号不存在,当前为新增订单") ;
						}else {
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
							if (Integer.valueOf(orderMap.get("status").toString())>1) {
								params.put("msg", "当前状态为已收件") ;
								params.put("first", "N") ;
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
					setModelSub(model, "N") ;*/
                      
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					if (bossUser.getInputTimes()!=null&&bossUser.getInputTimes()>0) {
						params.put("fi", "N") ;
					}else {
						params.put("fi", "Y") ;
					}
					String  defaultCity = "" ;
					model.put("defaultCity",defaultCity) ;
					List<Map<String, Object>> lgcs = userService.getCurrentLgc();
					if (StringUtils.isEmptyWithTrim(params.get("tdate"))) {
						params.put("tdate", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm")) ;
					}
					List<Map<String, Object>> reqList = requireListService.getByCode("ORDER_INPUT", "") ;
					Map<String, String> mapRequire = new HashMap<String, String>();
					for (Map<String, Object> mp : reqList) {
						mapRequire.put(mp.get("name").toString(),mp.get("required").toString());
					}
					model.put("reqMap", mapRequire) ;
					model.put("lgcs", lgcs) ;
					model.put("orderMap", orderMap) ;
					model.put("params", params);
					return "order/oinput";
				}	
				
				 // 用于订单录入
				@RequestMapping(value = { "/sinput" })
				public String sinput(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Map<String, Object> orderMap = null ;
					Map<String, Object> mMap = null ;
					Map<String, Object> codMap = null ;
					if(!StringUtils.isEmptyWithTrim(params.get("lgcOrderNo"))) {
						orderMap = orderService.findByLgcOrderNo(params.get("lgcNo"), params.get("noFix")+params.get("lgcOrderNo"), null) ;
						if (orderMap==null) {
							params.put("msg", "运单号不存在") ;
						}else{
							if (!orderMap.get("status").toString().equals("2")&&!orderMap.get("status").toString().equals("7")
									&&!orderMap.get("status").toString().equals("8")) {
								params.put("msg", "当前状态不允许签收录入,必须为未签收状态") ;
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
								if ("MONTH".equals(orderMap.get("pay_type"))) {
									if (!StringUtil.isEmptyWithTrim(orderMap.get("month_settle_no").toString())) {
										mMap = mobileUserService.getMuserByNo(orderMap.get("month_settle_no").toString()) ;
									}
								}
								if (orderMap.get("cod_name")!=null&&StringUtil.isEmptyWithTrim(orderMap.get("cod_name").toString())) {
									codMap = codSettleUserService.getCuserByNo(orderMap.get("cod_name").toString()) ;
								}
						}
						
						
					}
					/*List<Map<String, Object>> lgcs = userService.getCurrentLgc();
					PageInfo<Map<String, Object>> mList = mobileUserService.muserList(new HashMap<String, String>(),getPageInfo(1,5000)) ;
					if (mList!=null) {
						model.put("mlist", JsonUtil.toJson(mList.getList())) ;
					}else {
						model.put("mlist",mList) ;
					}
			        PageInfo<Map<String, Object>> codList = codSettleUserService.list(new HashMap<String, String>(),getPageInfo(1,5000)) ;
					if (codList!=null) {
						model.put("codList", JsonUtil.toJson(codList.getList())) ;
					}else {
						model.put("codList",codList) ;
					}*/
					if (StringUtils.isEmptyWithTrim(params.get("tdate"))) {
						params.put("tdate", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm")) ;
					}
					//setModelCour(model, "Y", courierService) ;
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					List<Map<String, Object>> signList = orderService.findSignByIds(bossUser.getSoid()) ; 
					//model.put("lgcs", lgcs) ;
					model.put("orderMap", orderMap) ;
					model.put("signList", signList) ;
					model.put("mMap", mMap) ;
					model.put("codMap", codMap) ;
					model.put("params", params);
					return "order/sinput";
				}
				
				

				// 用于签收录入保存
				@RequestMapping(value = { "/sign_update" })
				public void sign_update(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,OrderInfo orderInfo) throws SQLException {
					try {
						BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
						boolean flag = false ;
							Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcNo"), params.get("lgcOrderNo"), null) ;
							if (orderMap==null) {
								outText("录入失败，运单号不存在", response);
							}else {
								orderInfo.setLgcOrderNo(params.get("lgcOrderNo"));
								if (!orderMap.get("status").toString().equals("2")&&!orderMap.get("status").toString().equals("7")
										&&!orderMap.get("status").toString().equals("8")) {
									 outText("当前状态不允许签收录入", response);
								}else {
							OrderTrack orderTrack = new OrderTrack() ;
							OrderTrack  lastOrderTrack = orderTrackService.getLastOrderTrack(orderMap.get("order_no").toString()) ;
							orderInfo.setStatus(Integer.valueOf(orderMap.get("status").toString()));
							if (Integer.valueOf(orderMap.get("status").toString())<3||Integer.valueOf(orderMap.get("status").toString())==8
									||Integer.valueOf(orderMap.get("status").toString())==7) {
								orderInfo.setStatus(3);
								orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
								orderTrack.setCompleted("N");
								orderTrack.setIsLast("Y");
								orderTrack.setScanIno(Constants.getUser().getUserName());
								orderTrack.setScanIname(Constants.getUser().getRealName());
								orderTrack.setOrderStatus(TrackStatus.SEND.toString());
								orderTrack.setOrderNo(orderMap.get("order_no").toString());
								if (lastOrderTrack==null) {	
									//orderTrack.setPreNo(params.get("substationNo"));
									//orderTrack.setPreType("S");
								}else {
									orderTrack.setPreNo(lastOrderTrack.getPreNo());
									orderTrack.setPreType(lastOrderTrack.getPreType());
									orderTrack.setOrderStatus(TrackStatus.SEND.toString());
									orderTrack.setParentId(lastOrderTrack.getId());
									orderTrack.setIsLast("Y");
									orderTrack.setScanIno(lastOrderTrack.getScanIno());
									orderTrack.setScanIname(lastOrderTrack.getScanIname());
									orderTrack.setScanOno(Constants.getUser().getUserName());
									orderTrack.setScanOname(Constants.getUser().getRealName());
								}
								
							}else {
								orderInfo.setStatus(Integer.valueOf(orderMap.get("status").toString()));
							}
							 if (!StringUtils.isEmptyWithTrim(orderInfo.getSendCourierNo())) {
			                	 Courier c = new Courier() ;
			                	 c.setCourierNo(orderInfo.getSendCourierNo());
			                	 Courier courier =  courierService.getCourierByNo(c) ;
			                	 orderInfo.setSendSubstationNo(courier.getSubstationNo());
			                	 /////////////
				                	if (Integer.valueOf(orderMap.get("status").toString())<3||Integer.valueOf(orderMap.get("status").toString())==8
				                			||Integer.valueOf(orderMap.get("status").toString())==7) {
				                		 orderTrack.setCurNo(courier.getCourierNo());
											orderTrack.setCurType("C");
											String innerPhone = courier.getPhone() ;
											if (!StringUtils.isEmptyWithTrim(courier.getInnerPhone())) {
												innerPhone += "("+courier.getInnerPhone()+")" ;
											}
											orderTrack.setContext("订单签收录入,快递员:"+courier.getRealName()+",联系方式："+innerPhone+",已派件,签收人："+params.get("signName"));
											orderTrack.setOpname(Constants.getUser().getRealName());
											orderTrackService.add(orderTrack);
											if (lastOrderTrack!=null) {
											orderTrackService.unLastTrack(lastOrderTrack);
											}
										  /*  if (orderMap.get("source").toString().equals("WEIXIN")) {
												if (orderMap.get("wx_openid")!=null&&!StringUtils.isEmptyWithTrim(orderMap.get("wx_openid").toString())) {
												  flag = true ;
												}
											}*/
											
											
									   }
							 }else {
								 if (orderMap.get("send_substation_no")!=null) {
									 orderInfo.setSendSubstationNo(orderMap.get("send_substation_no").toString());
								}
							}

							 
							 boolean join = false ;
				                    join =  joinSubsationService.shutAcount(orderInfo.getSendSubstationNo()) ;
							 if (join) {
								 outText("录入失败,派件网点余额不足，系统关闭", response);
		               		       return ;
							   }
							 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							 
							 params.put("freightType", StringUtils.nullString(orderMap.get("freight_type"))) ;
							  //判断签收录入与收件录入是否付款人一致
							  boolean same = true ;
							  boolean same1 = true ;
							  if (orderMap.get("freight_type")!=null&&!orderMap.get("freight_type").toString().equals(params.get("freightType"))) {
								  same = false ;
								  same1 = false ;
							  }
							  
							  //判断签收录入与收件录入是否在数据上的区别
							 
							 
							  orderInfo.setFpayStatus(orderMap.get("fpay_status")==null?"":orderMap.get("fpay_status").toString());
							  boolean fp = false ;
							  if(!fp&&!StringUtils.isEmptyWithTrim(params.get("freight_"))){
				                	 orderInfo.setFreight(Float.valueOf(params.get("freight_"))); 
				                	 if (orderInfo.getFreight()!=Float.valueOf(orderMap.get("freight").toString())) {
										  same1 = false ;
									  }
				                 }else {
									orderInfo.setFreight(Float.valueOf(orderMap.get("freight").toString()));
								}
				                 if(!fp&&!StringUtils.isEmptyWithTrim(params.get("payAcount_"))){
				                	 orderInfo.setPayAcount(Float.valueOf(params.get("payAcount_"))); 
				                	 if (orderInfo.getPayAcount()!=Float.valueOf(orderMap.get("pay_acount").toString())) {
										  same1 = false ;
									  }
				                 }else {
										orderInfo.setPayAcount(Float.valueOf(orderMap.get("pay_acount").toString()));
									}
				                 
				                 if(!fp&&!StringUtils.isEmptyWithTrim(params.get("cpay_"))){
				                	 orderInfo.setCpay(Float.valueOf(params.get("cpay_"))); 
				                 }else {
										orderInfo.setCpay(Float.valueOf(orderMap.get("cpay").toString()));
									}
				                 
				                 if(!fp&&!StringUtils.isEmptyWithTrim(params.get("vpay_"))){
				                	 orderInfo.setVpay(Float.valueOf(params.get("vpay_"))); 
				                	 if (orderInfo.getVpay()!=Float.valueOf(orderMap.get("vpay").toString())) {
										  same1 = false ;
									  }
				                 }else {
										orderInfo.setVpay(Float.valueOf(orderMap.get("vpay").toString()));
									}
				                 
				                 if(!fp&&!StringUtils.isEmptyWithTrim(params.get("goodPrice_"))){
				                	  orderInfo.setGoodPrice(Float.valueOf(params.get("goodPrice_"))); 
				                	  if (orderInfo.getGoodPrice()!=Float.valueOf(orderMap.get("good_price").toString())) {
										  same1 = false ;
									  }
				                	  
				                 }else {
										orderInfo.setGoodPrice(Float.valueOf(orderMap.get("good_price").toString()));
									}
				               
				                 if (orderInfo.getGoodPrice()>0) {
										orderInfo.setCod(1);
									   }
				                 
				                 if(!fp&&!StringUtils.isEmptyWithTrim(params.get("goodValuation_"))){
				                	 orderInfo.setGoodValuation(Float.valueOf(params.get("goodValuation_"))); 
				                	 if (orderInfo.getGoodValuation()!=Float.valueOf(orderMap.get("good_valuation").toString())) {
										  same1 = false ;
									  }
				                 }else {
										orderInfo.setGoodValuation(Float.valueOf(orderMap.get("good_valuation").toString()));
								}
							  
				              if (same1) {
								orderInfo.setErred("N");
							 }else {
								orderInfo.setErred("Y");
							}
							  
							  
							  if (!StringUtil.isEmptyWithTrim(StringUtils.nullString(orderMap.get("month_settle_no")))) {
								  orderInfo.setMonthSettleNo(StringUtils.nullString(orderMap.get("month_settle_no")));
							  }
							  orderInfo.setDisUserNo(StringUtils.nullString(orderMap.get("dis_user_no")));
							  String tnpay = params.get("tnpay") ;
				                 if (StringUtils.isEmptyWithTrim(tnpay)) {
									tnpay = "0" ;
								 }
				                 orderInfo.setSnpay(tnpay);

				                 
				                 
				                 String m = "0" ;
				                 if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("mpay")))) {
									m = StringUtils.nullString(orderMap.get("mpay")) ;
								}
				                 float mpay = Float.valueOf(m)  ;
							   if ("2".equals(params.get("freightType"))) {
								   
							      if (orderMap.get("fpay_status")==null||!"SUCCESS".equals(StringUtils.nullString(orderMap.get("fpay_status")))) {
							         
						                 BigDecimal pay = new BigDecimal(orderInfo.getFreight()).add(new BigDecimal(orderInfo.getVpay()))  ;
													//支付邮费
								                	  if ("HUIYUAN".equals(params.get("payType"))&&pay.compareTo(new BigDecimal("0"))==1) {
														//会员扣费
								                		  
								                		  String disUserNo = params.get("disUserNo") ;
								                		  String pwd = params.get("pwd") ;
								                		  if (StringUtils.isEmptyWithTrim(disUserNo)) {
								                			  outText("录入失败,请输入会员号", response);
									                		  return ;
														  }
								                		  if (StringUtils.isEmptyWithTrim(pwd)) {
								                			  outText("录入失败,请输入会员密码", response);
									                		  return ;
														  }
								                		DisUser disUser = disUserService.getByNo(disUserNo) ;
								                		if (disUser==null) {
								                			outText("录入失败,不存在的会员", response);
									                		  return ;
														}
								                		if (!Md5.md5Str(pwd).equals(disUser.getPwd())) {
								                			  outText("录入失败,会员密码错误", response);
									                		  return ;
														}
								                		
								                		Date nowDate = new Date() ;
								                		
								                		DiscountType discountType = discountTypeService.getById(disUser.getDisType()) ;
								                		BigDecimal d = new BigDecimal( (discountType.getDiscount()/100.0)) ;
								                		
								                		pay = new BigDecimal(orderInfo.getFreight()) ;
								                		
								                		BigDecimal rmoney = pay.multiply(d) ;  //实际扣费金额
								                		rmoney = rmoney.add(new BigDecimal(orderInfo.getVpay())) ;
								                		/*if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("vpay")))) {
								                			rmoney = rmoney.add(new BigDecimal(orderMap.get("vpay").toString())) ;
														}*/
								                		orderInfo.setSnpay(String.valueOf(Float.valueOf(rmoney.toString())+orderInfo.getGoodPrice()));
								                		
								                		DisUserBalance disUserBalance = new DisUserBalance() ;
								                		disUserBalance.setUid(disUser.getId());
								                		disUserBalance.setBalance(rmoney.toString());
								                		disUserBalance.setLastUpdateTime(DateUtils.formatDate(nowDate));
								                		
								                		int r = disUserBalanceService.consume(disUserBalance) ;
								                		
								                		if (r==0) {
								                			  outText("录入失败,会员余额不足", response);
									                		  return ;
														}
								                		
								                		if (r==-1) {
								                			  outText("录入失败,扣费失败", response);
									                		  return ;
														}
								                		
								                		DisUserBalance cur  = disUserBalanceService.getByUid(disUser.getId()) ;
								                		
								                		String afBalance = cur.getBalance();
								                		
								                		
								                		
								                		ConsumeHistory consumeHistory = new ConsumeHistory() ;
								                		consumeHistory.setDisUserNo(disUserNo);
								                		consumeHistory.setRmoney(rmoney.toString());
								                		consumeHistory.setOmoney(pay.toString());
								                		consumeHistory.setAfBalance(afBalance);
								                		consumeHistory.setStatus("SUCCESS");
								                		consumeHistory.setOrderNo(orderInfo.getOrderNo());
								                		consumeHistory.setDiscountText(discountType.getDiscountText());
								                		consumeHistory.setLied("N");
								                		consumeHistory.setCourierNo(orderInfo.getTakeCourierNo());
								                		consumeHistory.setOperator(bossUser.getUserName());
								                		consumeHistory.setCreateTime(DateUtils.formatDate(nowDate));
								                		consumeHistory.setLastUpdateTime(DateUtils.formatDate(nowDate));
								                		consumeHistory.setSource("BOSS");
								                		consumeHistoryService.save(consumeHistory) ;
								                		orderInfo.setFpayStatus("SUCCESS");	
								                		if (same) {
															orderInfo.setDisUserNo(disUserNo);
														} 
													}else {
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
														orderInfo.setFpayStatus("SUCCESS");
													}
										}else {
											orderInfo.setPayType(StringUtils.nullString(orderMap.get("pay_type")));
										   // orderInfo.setMonthSettleNo(StringUtils.nullString(orderMap.get("month_settle_no")));
											orderInfo.setFpayStatus(StringUtils.nullString(orderMap.get("fpay_status")));
										}
						              
						  }else {
							  orderInfo.setFpayStatus(StringUtils.nullString(orderMap.get("fpay_status")));
						}
							   String snpay = "0" ;
							   if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("good_price")))) {
		                		     orderInfo.setGoodPrice(Float.valueOf(StringUtils.nullString(orderMap.get("good_price"))));
							   }
							   if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("freight")))) {
		                		     orderInfo.setFreight(Float.valueOf(StringUtils.nullString(orderMap.get("freight"))));
							   }
							   if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(orderMap.get("vpay")))) {
		                		     orderInfo.setVpay(Float.valueOf(StringUtils.nullString(orderMap.get("vpay"))));
							   }
							   
							if ("1".equals(orderInfo.getFreightType())) {
								    snpay = StringUtils.nullString(orderMap.get("good_price")) ; 	 
							}else {
									snpay = String.valueOf((orderInfo.getGoodPrice() + orderInfo.getFreight() + orderInfo.getVpay())) ;
							}
				             orderInfo.setSnpay(snpay);  
							   
							 orderInfo.setMpay(mpay);
			                 orderInfo.setOrderNo(orderMap.get("order_no").toString());
			                 orderInfo.setSendOrderTime(params.get("tdate")+":00");
			                 //orderInfo.setCompleteTime(DateUtils.formatDate(new Date()));
			                 BigDecimal spay = new BigDecimal(tnpay) ;
			                 spay.add(new BigDecimal(orderInfo.getGoodPrice())) ;
			                 bossUser.setSoid(bossUser.getSoid()+","+orderMap.get("id"));
			                 SecurityUtils.getSubject().getSession().setAttribute("user", bossUser);
			                
			                 //orderService.sinputInfoDel(orderInfo.getOrderNo());
			                 
			                 
			                 orderService.sinputInfo(orderInfo,"SIGN");
			                 //收件与签收一致为到付，更新payType
			                 if ("2".equals(StringUtils.nullString(orderMap.get("freight_type")))&&"2".equals(params.get("freightType"))) {
								orderInfo.setPayType(params.get("payType"));
							 }else {
								orderInfo.setPayType(StringUtils.nullString(orderMap.get("pay_type")));
							}
			                 orderInfo.setInputer(Constants.getUser().getRealName());
			                 orderService.signUpdate(orderInfo) ;  
			                 
			         		Substation s =  substationService.getSubstationByNo(orderInfo.getSendSubstationNo()) ;
							 if (s!=null&&"J".equals(s.getSubstationType())) {
		                		 FranchiseOrder franchiseOrder = new FranchiseOrder() ;
		                		 franchiseOrder.setOrder_no(orderInfo.getOrderNo());
		                		 franchiseOrder.setLgc_order_no(StringUtils.stringNull(orderMap.get("lgc_order_no")));
		                		 franchiseOrder.setTake_substation_no(StringUtils.stringNull(orderMap.get("sub_station_no")));
		                		 franchiseOrder.setSend_substation_no(orderInfo.getSendSubstationNo());
		                		 franchiseOrder.setItem_type(StringUtils.stringNull(orderMap.get("item_type")));
		                		 franchiseOrder.setItem_weight(StringUtils.nullString(orderMap.get("item_weight"),"0"));
		                		 franchiseOrder.setMoney_type("P");
		                		 franchiseOrder.setCreate_time(DateUtils.formatDate(new Date()));
		                		 
							  	  franOrderService.saveOrder(franchiseOrder) ;
						   }	
							 
							 Substation s1 =  substationService.getSubstationByNo(StringUtils.stringNull(orderMap.get("sub_station_no"))) ; 
							 if (s1!=null&&"J".equals(s1.getSubstationType())) {
                                  //更新寄件加盟网点
							  	  franOrderService.updateSendSno(StringUtils.stringNull(orderMap.get("order_no")),orderInfo.getSendSubstationNo(),StringUtils.stringNull(orderMap.get("lgc_order_no"))) ;
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
												content ="【快递王子】尊敬的"+StringUtils.nullString(orderMap.get("send_name"))+"先生/女士 ，您的快递："+StringUtils.nullString(orderMap.get("lgc_order_no"))+"已由"+params.get("signName")+"先生/女士签收，请核实签收情况。感谢您使用亿翔快递，祝您生活愉快！";
											   channel = "yx" ;
										 }
										SendMessage.send(StringUtils.nullString(orderMap.get("send_phone")), content, lgcConfig==null?"":lgcConfig.getLgcNo(),channel,messageInfoService,lgcConfigService) ;
									}	
								} 
			                 
			                 if (flag) {
			                		Map<String, String> wxMap = new HashMap<String, String>() ;
									wxMap.put("touser",orderMap.get("wx_openid").toString());
									wxMap.put("template_id","KZIR04DnPKWVK7fl8eHgm_Ms0g-PHZocVzVFNu5GY9I") ;
									wxMap.put("templateType","6");
									wxMap.put("number",params.get("lgcOrderNo"));
									wxMap.put("time",DateUtils.formatDate(new Date()));
									wxMap.put("remark","");
									wxMap.put("frist","尊敬的客户,你有一个件已被签收");
									WeiXinUtil.push(wxMap);
							}
			                 
						  outText("1", response);
						}
					 } 
					} catch (Exception e) {
                     e.printStackTrace();
                     outText("录入失败,数据有误", response);
					}
				}		
		
				 // 用于订单审核列表
				@RequestMapping(value = { "/examine" })
				public String examine(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -30, 0, 0),"yyyy-MM-dd") ;
					String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
						params.put("createTimeBegin", beginTime) ;
					}
					if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
						endTime = params.get("createTimeEnd") ;
					}
					String sendTime = params.get("sendTimeEnd") ;
					if (!StringUtils.isEmptyWithTrim(sendTime)) {
						params.put("sendTimeEnd", sendTime+" 23:59:59") ;
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
					params.put("erred", "1") ;
					params.put("createTimeEnd", endTime+" 23:59:59") ;
					pageList = orderService.examineOrder(params,getPageInfo(cpage)) ;
					params.put("createTimeEnd", endTime) ;
					params.put("sendTimeEnd", sendTime) ;
					model.put("params", params);
					model.put("list", pageList);
					return "order/elist";
				}
				
				
				 // 用于签收单列表
				@RequestMapping(value = { "/revlist" })
				public String revlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					//String sno = params.get("substationNo") ;
					String sno = params.get("sendSno") ;
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 0, 0),"yyyy-MM-dd") ;
					String endTime = DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 0, 0),"yyyy-MM-dd") ;
					String monthSettleNo = params.get("monthSettleNo") ;
					if (!StringUtils.isEmptyWithTrim(monthSettleNo)) {
						if (monthSettleNo.contains(")")) {
							params.put("monthSettleNo", monthSettleNo.substring(0,monthSettleNo.indexOf("("))) ;
						}
					}
					
					if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
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
					
//					if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
//						BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
//						String substationNo ;
//						if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
//							 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
//						}else {
//							substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
//						}
//						params.put("substationNo", substationNo);
//					}
					
					params.put("createTimeEnd", endTime+" 23:59:59") ;
					setModelSub(model, "N",request) ;
					params.put("zid", "1") ;   //去除子单
					
					params.put("userNo", ((BossUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
					params.put("tab", "order_revlist");
					List<TableFiledSort> tableFieldSorts = tableFiledSortService.selectList(params);
					if(tableFieldSorts==null||tableFieldSorts.size()==0){
						params.put("userNo", "");
						tableFieldSorts = tableFiledSortService.selectList(params);
					}
					
					pageList = orderService.sendOrder(params,getPageInfo(cpage)) ;
					params.put("createTimeEnd", endTime) ;
					params.put("monthSettleNo", monthSettleNo) ;
					params.put("substationNo", sno) ;
					model.put("params", params);
					model.put("list", pageList);
					model.put("tableFieldSorts", tableFieldSorts);
					return "order/revlist";
				}
				
				 // 用于订单审核列表
				@RequestMapping(value = { "/mamager" })
				public String mamager(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -30, 0, 0),"yyyy-MM-dd") ;
					String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
						params.put("createTimeBegin", beginTime) ;
					}
					if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
						endTime = params.get("createTimeEnd") ;
					}
					String sendTime = params.get("sendTimeEnd") ;
					if (!StringUtils.isEmptyWithTrim(sendTime)) {
						params.put("sendTimeEnd", sendTime+" 23:59:59") ;
					}
					params.put("createTimeEnd", endTime+" 23:59:59") ;
					pageList = orderService.examinePassOrder(params,getPageInfo(cpage)) ;
					params.put("createTimeEnd", endTime) ;
					params.put("sendTimeEnd", sendTime) ;
					model.put("params", params);
					model.put("list", pageList);
					setModelCour(model, "Y", courierService,request) ;
					return "order/emlist";
				}
				
				
				
				 // 用于订单审核编辑
				@RequestMapping(value = { "/examine_edit" })
				public String examine_edit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					Map<String, Object> oMap = orderService.findByOrderNo(params.get("orderNo")) ;
					Map<String, Object> sMap = new HashMap<String, Object>() ;
					
					Map<String, Object> mMap = null ;
					Map<String, Object> codMap = null ;
					Map<String, Object> si_mMap = null ;
					Map<String, Object> si_codMap = null ;
					if (!StringUtil.isEmptyWithTrim(params.get("sid"))) {
						sMap = orderService.getSinputInfoById(Integer.valueOf(params.get("sid"))) ;
					}
					
					
					
					if ("MONTH".equals(oMap.get("pay_type"))) {
						if (!StringUtil.isEmptyWithTrim(oMap.get("month_settle_no").toString())) {
							mMap = mobileUserService.getMuserByNo(oMap.get("month_settle_no").toString()) ;
						}
					}
					if (oMap.get("pay_type")!=null&&!StringUtils.isEmptyWithTrim(oMap.get("pay_type").toString())) {
						if ("CASH".equals(oMap.get("pay_type").toString())) {
							oMap.put("pay_type", "现金");
						}
						if ("MONTH".equals(oMap.get("pay_type").toString())) {
							oMap.put("pay_type", "月结");
						}
						if ("HUIYUAN".equals(oMap.get("pay_type").toString())) {
							oMap.put("pay_type", "会员卡");
						}
					}
					
					if (oMap.get("cod_name")!=null&&StringUtil.isEmptyWithTrim(oMap.get("cod_name").toString())) {
						codMap = codSettleUserService.getCuserByNo(oMap.get("cod_name").toString()) ;
					}
					///////smap
					if (sMap.get("si_pay_type")!=null&&!StringUtils.isEmptyWithTrim(sMap.get("si_pay_type").toString())) {
					
					if ("MONTH".equals(sMap.get("si_pay_type"))) {
						if (!StringUtil.isEmptyWithTrim(StringUtils.nullString(sMap.get("si_month_settle_no")))) {
							si_mMap = mobileUserService.getMuserByNo(sMap.get("si_month_settle_no").toString()) ;
						}
					}
					
					if ("CASH".equals(sMap.get("si_pay_type").toString())) {
						sMap.put("si_pay_type", "现金");
					}
					if ("MONTH".equals(sMap.get("si_pay_type").toString())) {
						sMap.put("si_pay_type", "月结");
					}
					if ("HUIYUAN".equals(sMap.get("si_pay_type").toString())) {
						sMap.put("si_pay_type", "会员卡");
					}
				}
					
					if (sMap.get("si_cod_name")!=null&&StringUtil.isEmptyWithTrim(sMap.get("si_cod_name").toString())) {
						si_codMap = codSettleUserService.getCuserByNo(sMap.get("si_cod_name").toString()) ;
					}
					/////
					PageInfo<Map<String, Object>> mList = mobileUserService.muserList(new HashMap<String, String>(),getPageInfo(1,5000)) ;
					if (mList!=null) {
						model.put("mlist", JsonUtil.toJson(mList.getList())) ;
					}else {
						model.put("mlist",mList) ;
					}
			        PageInfo<Map<String, Object>> codList = codSettleUserService.list(new HashMap<String, String>(),getPageInfo(1,5000)) ;
					if (codList!=null) {
						model.put("codList", JsonUtil.toJson(codList.getList())) ;
					}else {
						model.put("codList",codList) ;
					}
					setModelCour(model, "Y", courierService,request) ;
					
					params.put("lgcOrderNo", oMap.get("lgc_order_no").toString()) ;
					model.put("params", params);
					model.put("oMap", oMap) ;
					model.put("sMap", sMap) ;
					model.put("mMap", mMap) ;
					model.put("codMap", codMap) ;
					model.put("si_mMap", si_mMap) ;
					model.put("si_codMap", si_codMap) ;
					return "order/eedit";
				}
				
				
				// 用于保存
				@RequestMapping(value = { "/examine_update" })
				public void examine_update(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws SQLException {
					try {
 		          
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
				                	  params.put("monthDiscount", monthSettleType.getDiscount().toString()) ;
								}
							 }
	                		  
						 }
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
			                	  params.put("codRate", codRateType.getDiscount().toString()) ;
							}
						 }  
					  } 
						
 		            boolean same = true ;
 		            if (!params.get("freight_type").equals(params.get("si_freight_type"))) {
						same = false ;
					}
 		            
 		             float good_price = 0 ;
 		             if (!StringUtil.isEmptyWithTrim(params.get("good_price"))) {
						good_price = Float.valueOf(params.get("good_price")) ;
						params.put("cod", "1") ;
					 }else {
						 params.put("good_price", "0") ;
						 params.put("cod", "0") ;
					 }
 		            float vpay = 0 ;
		             if (!StringUtil.isEmptyWithTrim(params.get("vpay"))) {
		            	 vpay = Float.valueOf(params.get("vpay")) ;
					 }
 		             
 		            float si_good_price = 0 ;
		             if (!StringUtil.isEmptyWithTrim(params.get("si_good_price"))) {
		            	 si_good_price = Float.valueOf(params.get("si_good_price")) ;
					 }else {
						 params.put("si_good_price", "0") ;
					 }
 		           if (good_price!=si_good_price) {
						same = false ;
					}
 		           
 		            float freight = 0 ;
		             if (!StringUtil.isEmptyWithTrim(params.get("freight"))) {
		            	 freight = Float.valueOf(params.get("freight")) ;
					 }else {
						 params.put("freight", "0") ;
					 }
		             float si_freight = 0 ;
		             if (!StringUtil.isEmptyWithTrim(params.get("si_freight"))) {
		            	 si_freight = Float.valueOf(params.get("si_freight")) ;
					 }else {
						 params.put("si_freight", "0") ;
					 }
		             if (freight!=si_freight) {
							same = false ;
				    }
		             
		             //////////////////
	 		           if (StringUtil.isEmptyWithTrim(params.get("si_goodValuation"))) {
	 		        	  params.put("si_goodValuation", "0") ;
					   } 
		             
	 		          //////////////////
	 		           if (StringUtil.isEmptyWithTrim(params.get("goodValuation"))) {
	 		        	  params.put("goodValuation", "0") ;
					   }
		             
		               if (!params.get("goodValuation").equals(params.get("si_goodValuation"))) {
		            		same = false ;
					  }
		             
		             
		             float payAcount = 0 ;
		             if (!StringUtil.isEmptyWithTrim(params.get("payAcount"))) {
		            	 payAcount = Float.valueOf(params.get("payAcount")) ;
					 }
		             float si_payAcount = 0 ;
		             if (!StringUtil.isEmptyWithTrim(params.get("si_payAcount"))) {
		            	 si_payAcount = Float.valueOf(params.get("si_payAcount")) ;
					 }else {
						 params.put("si_payAcount", "0") ;
					 }
		             if (payAcount!=si_payAcount) {
							same = false ;
				    }
		           
		             if (StringUtil.isEmptyWithTrim(params.get("vpay"))) {
			        	  params.put("vpay", "0") ;
					 }
		             
		             if (StringUtil.isEmptyWithTrim(params.get("si_vpay"))) {
			        	  params.put("si_vpay", "0") ;
					 } 
		             if (!params.get("vpay").equals(params.get("si_vpay"))) {
		            		same = false ;
					  }
		             
 		           if (same) {
 		        	  params.put("erred", "N") ;
				     }else {
					   params.put("erred", "Y") ;
				   }
 		           
 		       
 		           if (StringUtil.isEmptyWithTrim(params.get("si_cpay"))) {
 		        	  params.put("si_cpay", "0") ;
				   }
 		           if (StringUtil.isEmptyWithTrim(params.get("si_snpay"))) {
		        	  params.put("si_snpay", "0") ;
				   } 
 		           
 		           String snpay = "0" ;
 		          if ("1".equals(params.get("freight_type"))) {
	                	 if (good_price>0) {
								snpay = String.valueOf(good_price) ;
						}
					}else {
						snpay = String.valueOf((good_price + freight + vpay)) ;
					}
 		            params.put("snpay", snpay) ;
 		            orderService.examineUpdate(params) ;
 		            orderService.sinputInfoSend(params) ;
 		            orderService.sinputInfoSign(params) ;
					 outText("1", response);
					} catch (Exception e) {
                     e.printStackTrace();
                     outText("保存失败", response);
					}
				}	
				
				
				 // 用于订单审核页面
				@RequestMapping(value = { "/examine_page" })
				public String examine_page(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					model.put("params", params);
					return "order/examine_page";
				}
				
				 // 用于订单审核页面
				@RequestMapping(value = { "/ehistory" })
				public String ehistory(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					PageInfo<Map<String, Object>> list = orderService.ehistory(params, getPageInfo(cpage));
					model.put("list", list) ;
					model.put("params", params);
					return "order/ehistory";
				}
				// 用于保存
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
							 orderService.examine(params) ;  
							 if (!StringUtils.isEmptyWithTrim(params.get("pids"))) {
								 pMap.put("examiner", bossUser.getUserName()) ;   // 审核人
								 pMap.put("etime", DateUtils.formatDate(new Date())) ;    //审核时间
								  pMap.put("ids", params.get("pids")) ;
								  pMap.put("examineStatus", "INIT") ;
								 orderService.examine(pMap) ;  
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
				
				// 用于保存
				@RequestMapping(value = { "/batch_examine" })
				public void batch_examine(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws SQLException {
					try {
						Map<String, String> pMap = new HashMap<String, String>() ;
						pMap.put("ids", params.get("ids")) ;
						 boolean same = true ;
						 if ("PASS".equals(params.get("examineStatus"))) {
							 pageList = orderService.examineOrder(pMap, getPageInfo(1,50)) ;
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
						 }
					 } 
						 if (same) {
							 orderService.examine(params) ;  
							 outText("1", response);
						 }else {
							 outText("当前审核包含有收件与签收不一致的数据，请修改", response);
						}
					} catch (Exception e) {
                     e.printStackTrace();
                     outText("保存失败", response);
					}
				}	
				
				
				 // 用于返回地址簿
				@RequestMapping(value = { "/lgc_addr" })
				public String lgc_addr(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					PageInfo<Map<String, Object>> list = lgcAddrService.list(params, getPageInfo(cpage)) ;
					model.put("list", list);
					model.put("params", params);
					return "order/lgc_addr";
				}
				
				 // 用于返回地址簿
				@RequestMapping(value = { "/lgc_addr1" })
				public String lgc_addr1(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					PageInfo<Map<String, Object>> list = lgcAddrService.list(params, getPageInfo(cpage)) ;
					model.put("list", list);
					model.put("params", params);
					return "order/lgc_addr1";
				}
				
				
				 // 用于返回选择地址页面
				@RequestMapping(value = { "/sarea" })
				public String sarea(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					//ProOrder pOrder = proOrderService.findByOrderNo(params.get("orderNo")) ;
					//model.put("pOrder", pOrder);
					model.put("params", params);
					return "order/sarea";
				}
				
				
				 // 用于订单追踪--查询
				@RequestMapping(value = { "/tracks" })
				public String tracks(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws Exception {
					Map<String, Object> orderMap = new HashMap<String, Object>() ;
					List<Map<String, Object>> orderTrackList = new ArrayList<Map<String, Object>>() ;
					Map<String, String> map = new HashMap<String, String>() ;
					 if (StringUtils.isEmptyWithTrim(params.get("noType"))) {
						params.put("noType", "1") ;
					}
					pageList = null ;
					if(!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo"))||!StringUtil.isEmptyWithTrim(params.get("phone"))
						||!StringUtil.isEmptyWithTrim(params.get("createTimeBegin"))||!StringUtil.isEmptyWithTrim(params.get("createTimeEnd"))
	                    ||!StringUtil.isEmptyWithTrim(params.get("sendTimeBegin"))||!StringUtil.isEmptyWithTrim(params.get("sendTimeEnd"))){
						
						String sendTime = params.get("sendTimeEnd") ;
						if (!StringUtils.isEmptyWithTrim(sendTime)) {
							params.put("sendTimeEnd", sendTime+" 23:59:59") ;
						}
						pageList = orderService.findTrack(params,getPageInfo(cpage)) ;
						params.put("sendTimeEnd", sendTime) ;
						
						for (int i = 0; i < pageList.getList().size(); i++) {
							orderTrackList = new ArrayList<Map<String, Object>>() ;
							orderMap = pageList.getList().get(i) ;
							orderTrackList = orderTrackService.getByOrderNo(StringUtils.nullString(orderMap.get("order_no")), false) ;
							pageList.getList().get(i).put("trackList", orderTrackList) ;
							if ("3".equals(orderMap.get("status").toString())||"4".equals(orderMap.get("status").toString())||"5".equals(orderMap.get("status").toString())||"6".equals(orderMap.get("status").toString())) {
								pageList.getList().get(i).put("ischeck", "1") ;
							}else {
								pageList.getList().get(i).put("ischeck", "0") ;
							}
						 map.put("orderId", orderMap.get("id").toString()) ;
					     List<Map<String, Object>> noteList =  orderService.getNoteList(map) ;
					     pageList.getList().get(i).put("noteList", noteList) ;
					     
					     //转件查询
					      if (StringUtils.nullString(orderMap.get("for_no")).length()>2) {
					    	 Map<String, Object> fMap = forOrderService.getByOrderNo(orderMap.get("order_no").toString()) ; 
					    	 if (fMap!=null) {
					    		 //有转件信息
					    		 pageList.getList().get(i).put("iffor", "1") ;
								 String name = StringUtils.nullString(fMap.get("io_name")) ;
								 pageList.getList().get(i).put("forname", name) ;
								 Map<String, Object> kMap = forCpnService.kdybName(name) ;  //快递100信息
								 if (kMap!=null) {
									Map<String, Object> kdybMap = KuaiDiYiBaiUtil.track(StringUtils.nullString(kMap.get("pinyin")), StringUtils.nullString(fMap.get("io_lgc_order_no")));
									pageList.getList().get(i).put("kdyb", "1") ;
									pageList.getList().get(i).put("kdybMap", kdybMap) ;
									
								 }else {
									 pageList.getList().get(i).put("kdyb", "0") ;
								}
							}else {
								pageList.getList().get(i).put("iffor", "0") ;
							}
						  }
					     
						}
				  }	
					model.put("params", params);
					model.put("list", pageList) ;
					return "order/tracks";
				}
				
				
				 // 用于返回选择地址页面
				@RequestMapping(value = { "/print" })
				public String print(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					String lgcNo = Constants.getUser().getLgcNo() ;
					Lgc lgc = lgcService.getLgcByNo(lgcNo) ;
					List<Map<String, String>> list = orderService.getPrintOrder(params);
					model.put("params", params);
					model.put("list", list);
					model.put("lgc1", lgc) ;
					LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig") ;
					/*if (lgcConfig!=null) {
						if ("gdt".equals(lgcConfig.getKey())) {
							return "order/print";
						}
					}*/
					String key = "ssh" ;
					if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(configInfo.getDev_key()))) {
						key = StringUtils.nullString(configInfo.getDev_key()) ;
					}
					if (lgcConfig.getKey().equals(key)) {
						//return "order/print1";
						return "order/print2";
					}else {
						//return "order/print";
						//return "order/print1";
						return "order/print2";
					}
				}
		
				
				 // 用于返回无效单页面
				@RequestMapping(value = { "/deled_page" })
				public String deled_page(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					model.put("params", params);
					return "order/deled";
				}
				
				
				 // 用于
				@RequestMapping(value = { "/olist" })
				public String olist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
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
						
						if (StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
							System.out.println("============="+Constants.getUser().getLgcNo());
							params.put("lgcNo", Constants.getUser().getLgcNo()) ;
							
						}
						Date nowDate = new Date() ;
						String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -3, 0, 0),"yyyy-MM-dd") ;
						String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
						if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
							params.put("createTimeBegin", beginTime) ;
						}
						if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
							endTime = params.get("createTimeEnd") ;
						}
						
						if (!StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))||!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
							params.put("createTimeEnd", endTime+" 23:59:59") ;
						}
						
						
						
						String sendTime = params.get("sendTimeEnd") ;
						if (!StringUtils.isEmptyWithTrim(sendTime)) {
							params.put("sendTimeEnd", sendTime+" 23:59:59") ;
						}
						if (!StringUtils.isEmptyWithTrim(params.get("sendTimeBegin"))) {
							if (StringUtils.isEmptyWithTrim(sendTime)) {
								sendTime=endTime;
								params.put("sendTimeEnd", endTime+" 23:59:59") ;
							}
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
						Map<String, String> pMap = new HashMap<String, String>(params) ;
						pMap.put("take_or_send_no", pMap.get("substationNo")) ;
						pMap.remove("substationNo") ;
						PageInfo<Map<String, Object>> orderList = orderService.list(pMap,getPageInfo(cpage));
						List<Map<String, Object>> lgcs = userService.getCurrentLgc();
						List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
						Map<String, String> map =  new HashMap<String, String>() ;
					/*	PageInfo<Map<String, Object>> mList = mobileUserService.muserList(map,getPageInfo(1,5000)) ;
						if (mList!=null) {
							model.put("mlist", JsonUtil.toJson(mList.getList())) ;
						}else {
							model.put("mlist",mList) ;
						}*/
						
						model.put("orderList", orderList) ;
						model.put("lgcs", lgcs) ;
						model.put("substations", substations);
						if (!StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))||!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
							params.put("createTimeEnd", endTime) ;
						}
						
						params.put("sendTimeEnd", sendTime) ;
						model.put("params", params) ;
						model.put("today", DateUtils.formatDate(nowDate,"yyyy-MM-dd")) ;
						
						System.out.println("**************order list end***********************"+System.currentTimeMillis());
						Long end = System.currentTimeMillis() ;
						System.out.println("**************order list 耗时***********************"+(end-begin)+"ms");
						return "order/orderlist";
				}				
				
				
				
				
}
