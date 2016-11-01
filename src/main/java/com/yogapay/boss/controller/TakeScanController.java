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
import com.yogapay.boss.domain.ForwardOrder;
import com.yogapay.boss.domain.FranchiseOrder;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.LgcAddr;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.MonthSettleType;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.domain.WeightConfig;
import com.yogapay.boss.enums.PayStatus;
import com.yogapay.boss.enums.TrackStatus;
import com.yogapay.boss.service.CodRateTypeService;
import com.yogapay.boss.service.CodSettleUserService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.ForOrderService;
import com.yogapay.boss.service.FranOrderService;
import com.yogapay.boss.service.JoinSubsationService;
import com.yogapay.boss.service.LgcConfigService;
import com.yogapay.boss.service.LgcWeightConfigService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.MonthSettleService;
import com.yogapay.boss.service.MonthSettleTypeService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.service.ValuationRuleService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;
import com.yogapay.boss.utils.WeiXinUtil;

@Controller
@RequestMapping(value = "/takeScan")
public class TakeScanController extends BaseController {
	
	@Resource
      private SequenceService sequenceService ;
	@Resource
      private OrderService orderService ;
	@Resource
	private OrderTrackService orderTrackService ;
   @Resource
   private CourierService courierService ;
   @Resource
   private CodRateTypeService codRateTypeService ;
   @Resource
   private CodSettleUserService codSettleUserService ;
   @Resource
   private MobileUserService mobileUserService ;
   @Resource 
   private MonthSettleService monthSettleService ;
   @Resource
   private MonthSettleTypeService monthSettleTypeService ;
   @Resource
   private SubstationService substationService ;
   @Resource private FranOrderService franOrderService ;
   @Resource private JoinSubsationService joinSubsationService ;
   @Resource
   private ValuationRuleService valuationRuleService ;
   @Resource
   private LgcWeightConfigService lgcWeightConfigService;
   @Resource
	private  LgcConfigService lgcConfigService ;
			
				
				 // 用于收件扫描页面
				@RequestMapping(value = { "/page" })
				public String page(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					PageInfo<Map<String, Object>> list = valuationRuleService.list(params,getPageInfo(100));
					List<Map<String, Object>> m = list.getList();
					for (Map<String, Object> map : m) {
						if(map.get("status").toString().equals("1")){
							model.put("valuationRule", map);
							break;
						}
					}
					Map<String,Object> WeightConfig =lgcWeightConfigService.selectOne(params);
					model.put("WeightConfig", WeightConfig);
					return "takeScan/page";
				}				
			
				

				//用于收件扫描保存
				@RequestMapping(value = { "/tsave"})
				public void tsave(@RequestParam Map<String, String> params,HttpServletResponse response, HttpServletRequest request,OrderInfo orderInfo) throws SQLException{
					Map<String, Object> retMap = new HashMap<String, Object> () ;
					  Map<String, Object> data = new HashMap<String, Object>() ;
					try {
						boolean add = false ;
						 Date nowDate = new Date() ;
						 Courier courier =courierService.getCourierByNo(orderInfo.getTakeCourierNo()) ;
						 if (courier==null||courier.getStatus()==0) {
							   retMap.put("ret", "录入失败,快递员不存在或禁用") ;
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
					Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcOrderNo")) ;
					 if (orderMap==null) {
					    
						 if (!lgcConfigService.validateLgcOrderNo(orderInfo.getLgcOrderNo())) {
					    	    outText("录入失败,录入的运单号不符合规则", response);
	                		    return ;
						   }
						 
				         String orderNo = sequenceService.getNextVal("order_no");
		                 orderInfo.setOrderNo(orderNo);
		                 orderInfo.setCod(0);
						 orderInfo.setCreateTime(DateUtils.formatDate(nowDate));
		                 LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig") ;
		     			if (lgcConfig!=null) {
		     				orderInfo.setLgcNo(lgcConfig.getLgcNo());
		     			}
		                 orderInfo.setLastUpdateTime(DateUtils.formatDate(nowDate));
		                 orderInfo.setStatus(1);                     //处理中
		                 orderInfo.setPayStatus(PayStatus.NOPAY.getValue());
		                 orderInfo.setKstatus("CALLED");
		                 if(!"BOSS_REV".equals(params.get("source").toString())){
		                	 orderInfo.setSource("BOSS");
		                 }else{
		                	 orderInfo.setSource("BOSS_REV");
		                 }
		                 
		                 orderService.save(orderInfo) ;  
		                 
		                 add = true ;
		                 orderMap =  orderService.findByOrderNo(orderNo) ;
		                 orderMap = new HashMap<String, Object>() ;
		                 orderMap.put("order_no", orderNo) ;
		                 orderMap.put("cod", 0) ;
		                 orderMap.put("status", 1) ;
		                 if(!"BOSS_REV".equals(params.get("source").toString())){
		                	 orderMap.put("source", "BOSS") ;
		                 }else{
		                	 orderMap.put("source","BOSS_REV") ;
		                 }
		                 orderMap.put("pay_status", "000000") ;
		                 
		                 orderMap.put("freight", 0.0) ;
		                 orderMap.put("cpay", 0.0) ;
		                 orderMap.put("vpay", 0.0) ;
		                 orderMap.put("good_price", 0.0) ;
		                 orderMap.put("good_valuation", 0.0) ;
		                 orderMap.put("pay_acount", 0.0) ;
		                 orderMap.put("fpay_status", "INIT") ;
			     }

					    if (Integer.parseInt(orderMap.get("status").toString())>1) {
								  retMap.put("ret", "运单状态错误，当前为已收件") ;
									outJson(JsonUtil.toJson(retMap), response);
		                		  return ;
						}
						
						    orderInfo.setTakeOrderTime(DateUtils.formatDate(nowDate));
							if (Integer.valueOf(orderMap.get("status").toString())<2) {
								orderInfo.setStatus(2);
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
			                 
			                 
			                 if (!StringUtils.isEmptyWithTrim(params.get("cpay_"))) {
			                		 orderInfo.setCpay(Float.valueOf(params.get("cpay_"))); 
								}else {
									orderInfo.setCpay(0); 
								}
			               
			               if (!StringUtils.isEmptyWithTrim(params.get("vpay_"))) {
			                		 orderInfo.setVpay(Float.valueOf(params.get("vpay_"))); 
								}else {
									orderInfo.setVpay(0); 
								}
			     
			 
			      if (!StringUtils.isEmptyWithTrim(params.get("goodPrice_"))) {
			                		 orderInfo.setGoodPrice(Float.valueOf(params.get("goodPrice_"))); 
			                		 if (orderInfo.getGoodPrice()>0) {
			                			 orderInfo.setCod(1);
									}
								}else {
									 orderInfo.setGoodPrice(0); 
								}
			              
			       if (!StringUtils.isEmptyWithTrim(params.get("goodValuation_"))) {
			                		 orderInfo.setGoodValuation(Float.valueOf(params.get("goodValuation_"))); 
								}else {
									 orderInfo.setGoodValuation(0); 
								}
			       
			       
	                /* if(!StringUtils.isEmptyWithTrim(params.get("payAcount_"))){
	                	 orderInfo.setPayAcount(Float.valueOf(params.get("payAcount_"))); 
	                 }else {
							orderInfo.setPayAcount(Float.valueOf(orderMap.get("pay_acount").toString()));
					}   */
			       
			       
			       orderInfo.setPayAcount(orderInfo.getFreight()+orderInfo.getVpay()+orderInfo.getGoodPrice());
			       
			       
			            String  tnpay = "0" ;
		    	          if ("1".equals(params.get("freightType"))) {
		    		          orderInfo.setFpayStatus("SUCCESS");
		    		          float t = orderInfo.getFreight()+ orderInfo.getVpay();
		    		           tnpay = String.valueOf(t) ;
			              }else {
			            	  params.put("payType", "CASH") ;
						}
			          orderInfo.setTnpay(tnpay);        	 
			                 
			                 BigDecimal pay = new BigDecimal(tnpay) ;
			                 String snpay = "0" ;
			
			                 float mpay = 0 ;
			          
			                	 String codName = params.get("codName") ;
		                		  if (!StringUtils.isEmptyWithTrim(codName)) {
		                			  Map<String, Object> codSettleUser =  codSettleUserService.getCuserByNo(codName) ;

		                			  if (codSettleUser==null) {
		                				    retMap.put("ret", "代收客户号不存在") ;
											outJson(JsonUtil.toJson(retMap), response);
				                		  return ;
									  } 
		                			  data.put("cname", codSettleUser.get("cod_name")) ;
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
						                			  retMap.put("ret", "请输入月结号") ;
														outJson(JsonUtil.toJson(retMap), response);
							                		  return ;
												  } 
						                		  Map<String, Object> monthSettleUser = mobileUserService.getMuserByNo(monthSettleNo) ;  
						                		  if (monthSettleUser==null) {
						                			   retMap.put("ret", "月结号不存在") ;
														outJson(JsonUtil.toJson(retMap), response);
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
			                	 if (orderInfo.getGoodPrice()>0) {
										snpay = String.valueOf(orderInfo.getGoodPrice()) ;
								}
							}else {
								snpay = String.valueOf((orderInfo.getGoodPrice() + orderInfo.getFreight() + orderInfo.getVpay())) ;
							}
			                 orderInfo.setSnpay(snpay);
			                 
			         
			             	OrderTrack orderTrack = new OrderTrack() ;
							OrderTrack  lastOrderTrack = orderTrackService.getLastOrderTrack(orderMap.get("order_no").toString()) ;
							orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
							orderTrack.setCompleted("N");
							orderTrack.setScanIno(Constants.getUser().getUserName());
							orderTrack.setScanIname(Constants.getUser().getRealName());
							orderTrack.setOrderNo(orderMap.get("order_no").toString());
							orderTrack.setOrderStatus("TAKEING");
							if (lastOrderTrack==null) {	
								//orderTrack.setPreNo(params.get("substationNo"));
								//orderTrack.setPreType("S");
							}else {
								orderTrack.setPreNo(lastOrderTrack.getPreNo());
								orderTrack.setPreType(lastOrderTrack.getPreType());
								
								orderTrack.setParentId(lastOrderTrack.getId());
								orderTrack.setIsLast("Y");
								orderTrack.setScanIno(lastOrderTrack.getScanIno());
								orderTrack.setScanIname(lastOrderTrack.getScanIname());
								orderTrack.setScanOno(Constants.getUser().getUserName());
								orderTrack.setScanOname(Constants.getUser().getRealName());
							}
			              orderTrack.setContext("订单收件扫描,快递员已收件");
			                 if (!StringUtils.isEmptyWithTrim(orderInfo.getTakeCourierNo())) {
			                	 Courier c = new Courier() ;
			                	 c.setCourierNo(orderInfo.getTakeCourierNo());
			                	 //Courier courier =  courierService.getCourierByNo(c) ;
			                	 orderInfo.setSubStationNo(courier.getSubstationNo());
			                	 data.put("tname",courier.getRealName()) ;
			                	 orderTrack.setContext("订单收件扫描,快递员"+courier.getRealName()+"已收件");
							 }else {
								 if (orderMap.get("sub_station_no")!=null) {
									 orderInfo.setSubStationNo(orderMap.get("sub_station_no").toString());
								}
					  }
			                 orderInfo.setItemCount("1");
			                 orderInfo.setReqRece("0");
				             orderInfo.setInputer(Constants.getUser().getRealName());
			                 orderInfo.setMpay(mpay);
			                 orderInfo.setTakeTime(DateUtils.formatDate(new Date()));
			                 orderTrack.setOpname(Constants.getUser().getRealName());
			            orderTrackService.add(orderTrack);
			        	if (lastOrderTrack!=null) {	
							orderTrackService.unLastTrack(lastOrderTrack);
						}
			            orderService.takeScanUpdate(orderInfo) ; 
			            
			            if (add) {
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
   
					 
					   data.put("takeTime", DateUtils.formatDate(nowDate)) ;
					   data.put("scanName", Constants.getUser().getRealName()) ;
					   data.put("itemStatus", orderMap.get("item_Status")==null?"":orderMap.get("item_Status"));
					    retMap.put("ret", "1") ;
					    retMap.put("data", data) ;
						outText(JsonUtil.toJson(retMap), response);
			       } catch (Exception e) {
						e.printStackTrace();
						retMap.put("ret", "数据有误，保存失败") ;
						outJson(JsonUtil.toJson(retMap), response);
				}
				
		}		
				
}
