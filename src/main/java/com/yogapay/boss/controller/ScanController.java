package com.yogapay.boss.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import com.yogapay.boss.domain.ForwardOrder;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.enums.PayStatus;
import com.yogapay.boss.enums.TrackStatus;
import com.yogapay.boss.service.ForCpnService;
import com.yogapay.boss.service.ForOrderService;
import com.yogapay.boss.service.LgcConfigService;
import com.yogapay.boss.service.LgcWeightConfigService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.service.WarehouseIoRegisterService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

@Controller
@RequestMapping(value = "/scan")
public class ScanController extends BaseController {
	
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
	private WarehouseIoRegisterService warehouseIoRegisterService ;
	@Resource
	private ForCpnService forCpnService ;
	@Resource
	private LgcWeightConfigService lgcWeightConfigService;
	@Resource
	private  LgcConfigService lgcConfigService ;
	
		      // 用于快递员收件
				@RequestMapping(value = { "/clist" })
				public String clist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
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
					pageList = orderService.getRevOrder(params,getPageInfo(cpage)) ;
					params.put("createTimeEnd", endTime) ;
					model.put("params", params);
					model.put("list", pageList);
					return "scan/clist";
				}		
		
				 // 用于快递员派件
				@RequestMapping(value = { "/colist" })
				public String colist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					//Date nowDate = new Date() ;
//					String beginTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
//					String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
//					if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
//						params.put("createTimeBegin", beginTime) ;
//					}
//					if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
//						endTime = params.get("createTimeEnd") ;
//					}
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
					//params.put("createTimeEnd", endTime+" 23:59:59") ;
					pageList = orderService.getSendOrder(params,getPageInfo(cpage)) ;
					//params.put("createTimeEnd", endTime) ;
					params.put("sendTimeEnd", sendTime) ;
					model.put("params", params);
					model.put("list", pageList);
					return "scan/colist";
				}		
		
				 // 用于分站到站扫描
				@RequestMapping(value = { "/slist" })
				public String slist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -20, 0, 0),"yyyy-MM-dd") ;
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
					}
					if (StringUtils.isEmptyWithTrim(params.get("orderStatus"))) {
						params.put("orderStatus", "REV") ;
					}
					params.put("orderStatus", "INIT") ;
					pageList = orderTrackService.scanList(params, getPageInfo(cpage)) ;
					List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
					model.put("substations", substations);
					params.put("createTimeEnd", endTime) ;
					model.put("params", params);
					model.put("list", pageList);
					return "scan/slist";
				}
				
				 // 用于分站到站扫描添加
				@RequestMapping(value = { "/revscan" })
				public String revscan(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					List<Map<String, Object>> lgcs = userService.getCurrentLgc();
					model.put("lgcs", lgcs) ;
					List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
					model.put("substations", substations);
					return "scan/revscan";
				}	
				
				// 用于中心仓库到站扫描添加
				@RequestMapping(value = { "/revCenterScan" })
				public String revCenterScan(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					//List<Map<String, Object>> lgcs = userService.getCurrentLgc();
					//model.put("lgcs", lgcs) ;
					//List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
					Map<String,Object> weightConfig = lgcWeightConfigService.selectOne(params);
					//model.put("substations", substations);
					model.put("weightConfig", weightConfig);
					return "scan/revCenterScan";
				}	
				
				 // 用于分站出站扫描添加
				@RequestMapping(value = { "/sendscan" })
				public String sendscan(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
				/*	List<Map<String, Object>> lgcs = userService.getCurrentLgc();
					model.put("lgcs", lgcs) ;
					Map<String, String> params1 = new HashMap<String, String>() ;
					params1.put("lgcNo",  Constants.getUser().getLgcNo()) ;
					PageInfo<Map<String, Object>> substations = substationService.list(params1, getPageInfo(1, 5000));
					model.put("substations", substations!=null?substations.getList():null);*/
					return "scan/sendscan";
				}		
				
				
				
				
				 // 用于分站出站扫描
				@RequestMapping(value = { "/solist" })
				public String solist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -20, 0, 0),"yyyy-MM-dd") ;
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
					}
					if (StringUtils.isEmptyWithTrim(params.get("orderStatus"))) {
						params.put("orderStatus", "REV") ;
					}
					pageList = orderTrackService.scanList(params, getPageInfo(cpage)) ;
					List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
					model.put("substations", substations);
					params.put("createTimeEnd", endTime) ;
					model.put("params", params);
					model.put("list", pageList);
					return "scan/solist";
				}
		
				 // 用于转件清单
				@RequestMapping(value = { "/exchange" })
				public String exchange(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
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
					pageList = forOrderService.list(params,getPageInfo(cpage)) ;
					params.put("createTimeEnd", endTime) ;
					model.put("params", params);
					model.put("list", pageList);
					return "scan/forder";
				}
				
				 // 用于转入扫描
				@RequestMapping(value = { "/fiscan" })
				public String fiscan(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					List<Map<String, Object>> lgcs = userService.getCurrentLgc();
					model.put("lgcs", lgcs) ;
					List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
					model.put("substations", substations);
					return "scan/fiscan";
				}	
				

				//用于
				@RequestMapping(value = { "/fiscan_save"})
				public void fiscan_save(@RequestParam Map<String, String> params,HttpServletResponse response, HttpServletRequest request,ForwardOrder forwardOrder) throws SQLException{
					try {
					Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcNo"), params.get("curLgcOrderNo"), null) ;
					if (orderMap!=null) {
						 outText("该单号已经在系统使用", response);  
                         return ;
					}else {
						OrderInfo orderInfo = new OrderInfo() ;
						 Date nowDate  = new Date() ;
		                 if (!StringUtils.isEmptyWithTrim(orderInfo.getTakeTime())) {
		                	 orderInfo.setTakeTimeBegin(DateUtils.formatDate(DateUtils.getBeginDate(orderInfo.getTakeTime())));
		                     orderInfo.setTakeTimeEnd(DateUtils.formatDate(DateUtils.getEndDate(orderInfo.getTakeTime())));
						 }else {
							 orderInfo.setTakeTimeBegin(DateUtils.formatDate(DateUtils.getBeginDate(null)));
		                     orderInfo.setTakeTimeEnd(DateUtils.formatDate(DateUtils.getEndDate(null)));
						}
		                /* if (StringUtils.isEmptyWithTrim(orderInfo.getTakeAddr())) {
							orderInfo.setTakeAddr(orderInfo.getSendArea()+orderInfo.getSendAddr());
						 }*/
		                 String orderNo = sequenceService.getNextVal("order_no");
		                 orderInfo.setOrderNo(orderNo);
		                 orderInfo.setCod(0);
		                 orderInfo.setCreateTime(DateUtils.formatDate(nowDate));
		                 orderInfo.setTakeOrderTime(DateUtils.formatDate(nowDate));
		                 orderInfo.setLastUpdateTime(DateUtils.formatDate(nowDate));
		                 orderInfo.setStatus(2);                     //处理中
		                 orderInfo.setPayStatus(PayStatus.NOPAY.getValue());
		                 orderInfo.setKstatus("CALLED");
		                 orderInfo.setSource("BOSS_SCAN");
		                 orderInfo.setLgcNo(params.get("lgcNo"));
		                 orderInfo.setLgcOrderNo(params.get("curLgcOrderNo"));
		                 orderInfo.setSubStationNo(params.get("substationNo"));
		                 orderService.takesave(orderInfo) ;  
		                 
		                    OrderTrack orderTrack = new OrderTrack() ;
		                    orderTrack.setOrderNo(orderNo);
							orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
							orderTrack.setCompleted("N");
							orderTrack.setIsLast("Y");
							orderTrack.setScanIno(Constants.getUser().getUserName());
							orderTrack.setScanIname(Constants.getUser().getRealName());
							orderTrack.setOrderStatus(TrackStatus.REV.toString());
							orderTrack.setCurNo(params.get("substationNo"));
							orderTrack.setCurType("S");
							String pre = forwardOrder.getIoName() ;
							Substation cur = substationService.getSubstationByNo(orderTrack.getCurNo()) ;
							orderTrack.setContext("快件转入分站："+cur.getSubstationName()+",上一站是:"+pre);
							orderTrack.setOpname(Constants.getUser().getRealName());
							orderTrackService.add(orderTrack);
							
							forwardOrder.setForwardType("IN");
							forwardOrder.setCurNo(params.get("substationNo"));		
							forwardOrder.setOrderNo(orderNo);
							forwardOrder.setForwardTime(DateUtils.formatDate(nowDate));
							forwardOrder.setScanNo(Constants.getUser().getUserName());
							forwardOrder.setScanName(Constants.getUser().getRealName());
							forOrderService.save(forwardOrder) ;
							outText("1", response);
					 }	
				} catch (Exception e) {
						e.printStackTrace();
						outText("数据有误，保存失败", response);
				}
				
		}


				
				 // 用于转出扫描
				@RequestMapping(value = { "/foscan" })
				public String foscan(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					List<Map<String, Object>> lgcs = userService.getCurrentLgc();
					model.put("lgcs", lgcs) ;
					return "scan/foscan";
				}	
				
				
				//用于
				@RequestMapping(value = { "/foscan_save"})
				public void foscan_save(@RequestParam Map<String, String> params,HttpServletResponse response, HttpServletRequest request,ForwardOrder forwardOrder) throws SQLException{
					try {
					Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcNo"), params.get("curLgcOrderNo"), null) ;
					if (orderMap==null) {
						 outText("找不到该运单号，无法转出", response);  
                         return ;
					}else {
      
						    OrderTrack lastOrderTrack = orderTrackService.getLastOrderTrack(orderMap.get("order_no").toString()) ;
						    if (lastOrderTrack!=null) {
						    	BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
								String substationNo ;
								if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
									 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
								}else {
									substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
								}
						    	 if (lastOrderTrack!=null&&!substationNo.contains(lastOrderTrack.getCurNo())) {
							    	 outText("操作失败，不是所在分站", response);
							    	 return ;
								}
							}
						    
		                    OrderTrack orderTrack = new OrderTrack() ;
		                    orderTrack.setOrderNo(lastOrderTrack.getOrderNo());
							orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
							orderTrack.setCompleted("N");
							orderTrack.setIsLast("Y");
							orderTrack.setScanIno(lastOrderTrack.getScanIno());
							orderTrack.setScanIname(lastOrderTrack.getScanIname());
                            orderTrack.setScanOno(Constants.getUser().getUserName());							
							orderTrack.setScanOname(Constants.getUser().getRealName());
							orderTrack.setOrderStatus(TrackStatus.FORD.toString());
							orderTrack.setCurNo(lastOrderTrack.getCurNo());
							orderTrack.setCurType(lastOrderTrack.getCurType());
							Substation cur = substationService.getSubstationByNo(orderTrack.getCurNo()) ;
							orderTrack.setContext("快件转出："+params.get("ioName")+",上一站是:"+cur.getSubstationName());
							orderTrack.setParentId(lastOrderTrack.getId());
							orderTrack.setOpname(Constants.getUser().getRealName());
							orderTrackService.add(orderTrack);
							orderTrackService.unLastTrack(lastOrderTrack);
							
							forwardOrder.setForwardType("OUT");
							forwardOrder.setCurNo(lastOrderTrack.getCurNo());	
							forwardOrder.setOrderNo(lastOrderTrack.getOrderNo());
							forwardOrder.setForwardTime(DateUtils.formatDate(new Date()));
							forwardOrder.setScanNo(Constants.getUser().getUserName());
							forwardOrder.setScanName(Constants.getUser().getRealName());
							forOrderService.save(forwardOrder) ;
							
							outText("1", response);
					 }	
				} catch (Exception e) {
						e.printStackTrace();
						outText("数据有误，保存失败", response);
				}
				
		}
				
				
				
				//用于分站到件
				@RequestMapping(value = {"/srev"})
				public void srev(@RequestParam Map<String, String> params,
						HttpServletResponse response,HttpServletRequest req) throws SQLException {
					Map<String, Object> retMap = new HashMap<String, Object> () ;
					if (StringUtil.isEmpty(params.get("lgcNo"))) {
						retMap.put("ret", "操作失败") ;
						outJson(JsonUtil.toJson(retMap), response);
					} else {
						Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcNo"), params.get("lgcOrderNo"), null) ;
						String orderNo = "" ;
						
						OrderTrack orderTrack = new OrderTrack() ;
						orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
						orderTrack.setCompleted("N");
						orderTrack.setIsLast("Y");
						orderTrack.setScanIno(Constants.getUser().getUserName());
						orderTrack.setScanIname(Constants.getUser().getRealName());
						orderTrack.setOrderStatus(TrackStatus.REV.toString());
						String status = "2" ;
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
			                 orderInfo.setSubStationNo(params.get("substationNo"));
			                 
			                 if (!lgcConfigService.validateLgcOrderNo(orderInfo.getLgcOrderNo())) {
						    	    outText("录入失败,录入的运单号不符合规则", response);
		                		    return ;
							   }
			                 
			                 orderService.takesave(orderInfo) ;  
			                 
			                 orderMap = orderService.findByOrderNo(orderNo) ;
							//retMap.put("ret", "操作失败，运单号不存在") ;
							//outJson(JsonUtil.toJson(retMap), response);
							//return ;
							
						}else {
							orderNo = orderMap.get("order_no").toString() ;
							status = "7" ;
						}
						
						orderTrack.setOrderNo(orderNo);
						OrderTrack lastOrderTrack =  null ;
						if (orderMap!=null) {
							lastOrderTrack = orderTrackService.getLastOrderTrack(orderNo) ;
						}
						/*if (lastOrderTrack==null||!"SEND".equals(lastOrderTrack.getOrderStatus())) {*/
						if (lastOrderTrack==null) {	
							//orderTrack.setPreNo(params.get("substationNo"));
							//orderTrack.setPreType("S");
							orderTrack.setCurNo(params.get("substationNo"));
							orderTrack.setCurType("S");
						}else {
							if (!"2".equals(status)&&!"8".equals(status)&&!"7".equals(status)) {
								retMap.put("ret", "操作失败，订单已不在流转状态") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}
							/*if ("SIGNING".equals(lastOrderTrack.getOrderStatus())) {
								retMap.put("ret", "操作失败，当前正在派件") ;
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
							}*/
							
							
							/*if ("PRO".equals(lastOrderTrack.getOrderStatus())) {
								retMap.put("ret", "操作失败，当前件为问题件") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}*/
							/*if (!params.get("substationNo").equals(lastOrderTrack.getNextNo())&&lastOrderTrack.getNextNo()!=null) {
								retMap.put("ret", "操作失败，不是当前发往站点") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}*/
							
							
							orderTrack.setPreNo(lastOrderTrack.getCurNo());
							orderTrack.setPreType(lastOrderTrack.getCurType());
							orderTrack.setCurNo(params.get("substationNo"));
							orderTrack.setCurType("S");
							orderTrack.setParentId(lastOrderTrack.getId());
						}
						
						orderService.updateStatus("7",orderNo) ;
						
						String pre = "快递员" ;
						if ("S".equals(orderTrack.getPreType())) {
						   pre = substationService.getSubstationByNo(orderTrack.getPreNo()).getSubstationName() ;
						}
						Substation cur = substationService.getSubstationByNo(orderTrack.getCurNo()) ;
						orderTrack.setContext("快件到达分站："+cur.getSubstationName()+",上一站是:"+pre);
						orderTrack.setOpname(Constants.getUser().getRealName());
						orderTrackService.add(orderTrack);
						orderTrackService.unLastTrack(lastOrderTrack);
						
						Map<String, String> pMap = new HashMap<String, String>() ;
						pMap.put("manager_no", orderTrack.getScanIno()) ;
						pMap.put("lgc_order_no", params.get("lgcOrderNo")) ;
						pMap.put("create_time", orderTrack.getOrderTime()) ;
						pMap.put("input_or_output", "I") ;
						pMap.put("out_sub_or_courier", "SUBSTATION") ;
						pMap.put("out_number_no", orderTrack.getCurNo()) ;
						pMap.put("signal", "Y") ;
						warehouseIoRegisterService.save(pMap) ;
						
						orderTrack.setPreType(pre);
						retMap.put("ret", "1") ;
						retMap.put("track", orderTrack) ;
						/*if (params.get("substationNo").equals(lastOrderTrack.getCurNo())) {
							retMap.put("ret", "2") ;  //跳过当前扫描
							//outJson(JsonUtil.toJson(retMap), response);
							//return ;
						}*/
						outJson(JsonUtil.toJson(retMap), response);
						
					}
				}	
			
				@RequestMapping("/centerWareHouseRev")
				public void centerWareHouseRev(@RequestParam Map<String, String> params,
						HttpServletResponse response,HttpServletRequest req) throws SQLException {
					Map<String, Object> retMap = new HashMap<String, Object> () ;
					String sno = params.get("substationNo");
					if (StringUtils.nullString(sno).contains("(")) {
						sno = sno.substring(0, sno.indexOf("("));
					}
					Substation pre = substationService.getSubstationByNo(sno) ;
					if (pre==null||"0".equals(pre.getStatus())) {
						retMap.put("ret", "网点错误或禁用") ;
						outJson(JsonUtil.toJson(retMap), response);
						return ;
					}
					
					String curNo = userService.getByUserName(Constants.getUser().getUserName()).getSno() ;
					if (curNo==null||curNo.equals("")) {
						curNo = sno ;
					}
					Substation cur = substationService.getSubstationByNo(curNo) ;
					String curName = cur==null?"":cur.getSubstationName() ;
					
						Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("lgcOrderNo")) ;
						String orderNo = "" ;
						
						OrderTrack orderTrack = new OrderTrack() ;
						orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
						orderTrack.setCompleted("N");
						orderTrack.setIsLast("Y");
						orderTrack.setScanIno(Constants.getUser().getUserName());
						orderTrack.setScanIname(Constants.getUser().getRealName());
						orderTrack.setOrderStatus(TrackStatus.REV.toString());
						orderTrack.setPreNo(sno);
						orderTrack.setPreType("S");
						orderTrack.setCurNo(curNo);
						orderTrack.setCurType("S");
						String status = "2" ;
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
			                 orderService.takesave(orderInfo) ;  
			                 
			                 orderMap = orderService.findByOrderNo(orderNo) ;
						
						}else {
							orderNo = orderMap.get("order_no").toString() ;
							status = orderMap.get("status").toString() ;
						}
						
						orderTrack.setOrderNo(orderNo);
						OrderTrack lastOrderTrack =  null ;
						if (orderMap!=null) {
							lastOrderTrack = orderTrackService.getLastOrderTrack(orderNo) ;
						}
						/*if (lastOrderTrack==null||!"SEND".equals(lastOrderTrack.getOrderStatus())) {*/
						if (lastOrderTrack==null) {	
							//orderTrack.setPreNo(params.get("substationNo"));
							//orderTrack.setPreType("S");
							
							orderTrack.setCurNo(curNo);
							orderTrack.setCurType("S");
						}else {
							if (!"2".equals(status)&&!"8".equals(status)&&!"7".equals(status)) {
								retMap.put("ret", "操作失败，订单已不在流转状态") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}
						
							orderTrack.setParentId(lastOrderTrack.getId());
						}
						orderTrack.setContext(Constants.getUser().getRealName()+",已进行入仓扫描,当前快件在"+curName+"，上一站点："+pre.getSubstationName());
						orderTrack.setOpname(Constants.getUser().getRealName());
						orderTrackService.add(orderTrack);
						orderTrackService.unLastTrack(lastOrderTrack);
						
						Map<String, String> pMap = new HashMap<String, String>() ;
						pMap.put("manager_no", orderTrack.getScanIno()) ;
						pMap.put("lgc_order_no", params.get("lgcOrderNo")) ;
						pMap.put("create_time", orderTrack.getOrderTime()) ;
						pMap.put("input_or_output", "I") ;
						pMap.put("out_sub_or_courier", "SUBSTATION") ;
						pMap.put("out_number_no", orderTrack.getCurNo()) ;
						pMap.put("signal", "Y") ;
						warehouseIoRegisterService.save(pMap) ;
						
						orderTrack.setPreType(pre.getSubstationName());
						orderTrack.setCurType(curName);
						retMap.put("ret", "1") ;
						retMap.put("track", orderTrack) ;
						retMap.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						/*if (params.get("substationNo").equals(lastOrderTrack.getCurNo())) {
							retMap.put("ret", "2") ;  //跳过当前扫描
							//outJson(JsonUtil.toJson(retMap), response);
							//return ;
						}*/
						try {
							String stationNo = orderMap.get("sub_station_no").toString();
							Substation substation  =substationService.getSubstationByNo(stationNo);
							params.put("substation_type", substation.getSubstationType());
							Map<String, Object> lgcWeightCf = lgcWeightConfigService.selectOne(params);
							params.put("select_v", lgcWeightCf.get("select_v").toString());
							params.put("warehouse_minv", lgcWeightCf.get("warehouse_minv").toString());
							params.put("item_weight", orderMap.get("item_weight").toString());
							Map m = orderService.updateWeight(params);
							retMap.put("center_warehouse_weight", m.get("center_warehouse_weight")) ;
							retMap.put("itemStatus", orderMap.get("item_Status")==null?"":orderMap.get("item_Status"));
							outJson(JsonUtil.toJson(retMap), response);
						} catch (Exception e) {
							retMap.put("ret", "操作失败，电子秤未配置") ;
							outJson(JsonUtil.toJson(retMap), response);
						}
				
				}	
				
				//用于分站下一站显示页面
				@RequestMapping(value = {"/ssend_page"})
				public String ssend_page(@RequestParam Map<String, String> params,final ModelMap model,
						HttpServletResponse response) throws SQLException {
					String orderNo = params.get("orderNo") ;
					String lgcNo = orderService.findByOrderNo(orderNo).get("lgc_no").toString();
					params.put("lgcNo", lgcNo) ;
					PageInfo<Map<String, Object>> substationList = substationService.list(params,getPageInfo(1,500));
					model.put("substationList", substationList.getList()) ;
					model.put("params", params);
					return "scan/ssend_page";
				}		
				
				//用于分站下一站
				@RequestMapping(value = {"/ssend"})
				public void ssend(@RequestParam Map<String, String> params,
						HttpServletResponse response,HttpServletRequest req) throws SQLException {
					Map<String, Object> retMap = new HashMap<String, Object> () ;
					
					String tno = params.get("substationNo") ;
					if (StringUtils.nullString(tno).contains("(")) {
						tno = tno.substring(0, tno.indexOf("(")) ;
					}
					Substation substation = substationService.getSubstationByNo(tno) ;
					if (substation==null||"0".equals(substation.getStatus())) {
						retMap.put("ret", "下一站错误或禁用") ;
						outJson(JsonUtil.toJson(retMap), response);
						return ;
					}
					params.put("substationNo", tno) ;
					
					if (StringUtil.isEmpty(params.get("lgcOrderNo"))||StringUtil.isEmpty(params.get("substationNo"))) {
						retMap.put("ret", "操作失败") ;
						outJson(JsonUtil.toJson(retMap), response);
						return ;
					} else {
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
			                 orderInfo.setSubStationNo(params.get("substationNo"));
			                 
			                 if (!lgcConfigService.validateLgcOrderNo(orderInfo.getLgcOrderNo())) {
						    	    outText("录入失败,录入的运单号不符合规则", response);
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
						
						orderService.updateStatus("7",orderMap.get("order_no").toString()) ;
						
						String curNo = userService.getByUserName(Constants.getUser().getUserName()).getSno() ;
						if (curNo==null||curNo.equals("")) {
							curNo = tno ;
						}
						Substation cur = substationService.getSubstationByNo(curNo) ;
						String curName = cur==null?"":cur.getSubstationName() ;
						
						OrderTrack lastOrderTrack = orderTrackService.getLastOrderTrack(orderMap.get("order_no").toString()) ;
						
						/*if (lastOrderTrack==null||!"REV".equals(lastOrderTrack.getOrderStatus())) {
							retMap.put("ret", "2") ;
							retMap.put("msg", "已扫描,或请先做到站扫描才能出站") ;
							outJson(JsonUtil.toJson(retMap), response);
							return ;*/
						
						
						 if (lastOrderTrack==null) {
							lastOrderTrack = new OrderTrack() ;
						 }
 
							/*	if ("SIGNING".equals(lastOrderTrack.getOrderStatus())) {
									retMap.put("ret", "操作失败，当前正在派件") ;
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
								}*/
							 
							OrderTrack orderTrack = new OrderTrack() ;
							orderTrack.setOrderNo(orderMap.get("order_no").toString());
							orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
							orderTrack.setCompleted("N");
							orderTrack.setPreNo(lastOrderTrack.getPreNo());
							orderTrack.setPreType(lastOrderTrack.getPreType());
							orderTrack.setCurNo(curNo);
							orderTrack.setCurType("S");
							orderTrack.setNextNo(params.get("substationNo"));
							orderTrack.setNextType("S");
							orderTrack.setOrderStatus(TrackStatus.SEND.toString());
							orderTrack.setParentId(lastOrderTrack.getId());
							orderTrack.setIsLast("Y");
							orderTrack.setScanIno(lastOrderTrack.getScanIno());
							orderTrack.setScanIname(lastOrderTrack.getScanIname());
							orderTrack.setScanOno(Constants.getUser().getUserName());
							orderTrack.setScanOname(Constants.getUser().getRealName());
							String next = "快递员" ;
							if ("S".equals(orderTrack.getNextType())) {
								next = substationService.getSubstationByNo(orderTrack.getNextNo()).getSubstationName() ;
							}
							orderTrack.setContext("快件离开分站："+curName+",下一站是:"+next);
							orderTrack.setOpname(Constants.getUser().getRealName());
							orderTrackService.add(orderTrack);
							orderTrackService.unLastTrack(lastOrderTrack);
							orderTrack.setCurType(cur.getSubstationName());
							orderTrack.setNextType(next);
							retMap.put("ret", "1") ;
							retMap.put("track", orderTrack) ;
							outJson(JsonUtil.toJson(retMap), response);

					}
				}		
		
				
				
				 // 用于收件明细打印
				@RequestMapping(value = { "/revPrint" })
				public String revPrint(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					params.put("orderStatus", "2") ;
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					if (StringUtils.isEmptyWithTrim(params.get("takeTimeBegin"))) {
						params.put("takeTimeBegin", beginTime) ;
					}
					if (!StringUtils.isEmptyWithTrim(params.get("takeTimeEnd"))) {
						endTime = params.get("takeTimeEnd") ;
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
					params.put("takeTimeEnd", endTime+" 23:59:59") ;
					pageList = orderService.getRevOrder(params,getPageInfo(cpage)) ;
					
					List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
					model.put("substations", substations);
					params.put("takeTimeEnd", endTime) ;
					model.put("params", params);
					model.put("list", pageList);
					return "scan/revPrint";
				}				
				
		
				 // 用于收件明细打印预览页面
				@RequestMapping(value = { "/revPrintPage" })
				public String revPrintPage(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					params.put("orderStatus", "2") ;
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					if (StringUtils.isEmptyWithTrim(params.get("takeTimeBegin"))) {
						params.put("takeTimeBegin", beginTime) ;
					}
					if (!StringUtils.isEmptyWithTrim(params.get("takeTimeEnd"))) {
						endTime = params.get("takeTimeEnd") ;
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
					params.put("takeTimeEnd", endTime+" 23:59:59") ;
					pageList = orderService.getRevOrder(params,getPageInfo(1,1000)) ;
					model.put("list", pageList);
					return "scan/revPrintPage";
				}						
				
				
				
				 // 用于问题件扫描
				@RequestMapping(value = { "/proscan" })
				public String proscan(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					return "scan/proscan";
				}				
				
				 // 用于转出扫描页面
				@RequestMapping(value = { "/froscanPage" })
				public String froscanPage(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					PageInfo<Map<String, Object>> list = forCpnService.list(params, getPageInfo(cpage)) ;
					model.put("list", list) ;
					return "scan/froscanPage";
				}				
			
				

				//用于转出扫描保存
				@RequestMapping(value = { "/foscanSave"})
				public void foscanSave(@RequestParam Map<String, String> params,HttpServletResponse response, HttpServletRequest request,ForwardOrder forwardOrder) throws SQLException{
					Map<String, Object> retMap = new HashMap<String, Object> () ;
					try {
					Map<String, Object> orderMap = orderService.findByLgcOrderNo(params.get("curLgcOrderNo")) ;
					if (orderMap==null) {
						 retMap.put("ret", "找不到该运单号，无法转出") ;
							 outJson(JsonUtil.toJson(retMap), response);
					    	 return ;
					}else {
						
						if (forOrderService.getByOrderNo(orderMap.get("order_no").toString())!=null) {
							 retMap.put("ret", "此单号已经扫描过了") ;
								 outJson(JsonUtil.toJson(retMap), response);
						    	 return ;
						 }   
						
						    OrderTrack lastOrderTrack = orderTrackService.getLastOrderTrack(orderMap.get("order_no").toString()) ;
						    OrderTrack orderTrack = new OrderTrack() ;
						    if (lastOrderTrack!=null) {
						    	BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
								String substationNo ;
								if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
									 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
								}else {
									substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
								}
						    	 /*if (lastOrderTrack!=null&&!substationNo.contains(lastOrderTrack.getCurNo())) {
						    	   retMap.put("ret", "操作失败，不是所在分站") ;
									outJson(JsonUtil.toJson(retMap), response);
							    	 return ;
								}*/
								orderTrack.setScanIno(lastOrderTrack.getScanIno());
								orderTrack.setScanIname(lastOrderTrack.getScanIname());
								orderTrack.setCurNo(lastOrderTrack.getCurNo());
								orderTrack.setCurType(lastOrderTrack.getCurType());
								orderTrack.setParentId(lastOrderTrack.getId());
								forwardOrder.setCurNo(lastOrderTrack.getCurNo());	
							}
		                   
		                    orderTrack.setOrderNo(orderMap.get("order_no").toString());
							orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
							orderTrack.setCompleted("N");
							orderTrack.setIsLast("Y");
						
                            orderTrack.setScanOno(Constants.getUser().getUserName());							
							orderTrack.setScanOname(Constants.getUser().getRealName());
							orderTrack.setOrderStatus(TrackStatus.FORD.toString());
						
							Substation cur = substationService.getSubstationByNo(orderTrack.getCurNo()) ;
							String sname = "" ;
							if (cur!=null) {
								sname = cur.getSubstationName() ;
							}
							orderTrack.setContext("快件转出："+params.get("ioName")+",上一站是:"+sname);
							
							
							
							orderService.forNo(orderMap.get("order_no").toString(), forwardOrder.getIoLgcOrderNo());
							orderTrack.setOpname(Constants.getUser().getRealName());
							orderTrackService.add(orderTrack);
							if (lastOrderTrack!=null) {
								orderTrackService.unLastTrack(lastOrderTrack);
							}
							forwardOrder.setOrderNo(orderMap.get("order_no").toString());
							forwardOrder.setForwardType("OUT");
							forwardOrder.setForwardTime(DateUtils.formatDate(new Date()));
							forwardOrder.setScanNo(Constants.getUser().getUserName());
							forwardOrder.setScanName(Constants.getUser().getRealName());
							forOrderService.save(forwardOrder) ;
							
							if (!StringUtils.isEmptyWithTrim(params.get("note"))) {
								BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
								params.put("operator", bossUser.getRealName()) ;
								params.put("orderId", orderMap.get("id").toString()) ;
								params.put("note", params.get("note")) ;
								params.put("opSrc", "转件扫描") ;
								params.put("createTime", DateUtils.formatDate(new Date())) ;
								orderService.note(params);
							}
							 
							retMap.put("ret", "1") ;
							retMap.put("scanName", Constants.getUser().getRealName()) ;
							retMap.put("scanTime", forwardOrder.getForwardTime()) ;
							outJson(JsonUtil.toJson(retMap), response);
					 }	
				} catch (Exception e) {
						e.printStackTrace();
						retMap.put("ret", "数据有误，保存失败") ;
						outJson(JsonUtil.toJson(retMap), response);
				}
				
		}		
		
				// 用于分站出站打印页面
				@RequestMapping(value = { "/sendPrint" })
				public String sendPrint(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					if (StringUtils.isEmptyWithTrim(params.get("scanTimeBegin"))) {
						params.put("scanTimeBegin", beginTime) ;
					}
					if (!StringUtils.isEmptyWithTrim(params.get("scanTimeEnd"))) {
						endTime = params.get("scanTimeEnd") ;
					}
					params.put("scanTimeEnd", endTime+" 23:59:59") ;
					if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
						BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
						String substationNo ;
						if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
							 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
						}else {
							substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
						}
						String sno[] =  substationNo.split(",") ;
						if (sno.length>1) {
							substationNo = substationNo.split(",")[1].replace("'", "") ;
						}else {
							substationNo = "000" ;
						}
						params.put("substationNo", substationNo);
					}
					params.put("orderStatus", "SEND") ;
					pageList = orderTrackService.scanList(params, getPageInfo(cpage)) ;
					List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
					model.put("substations", substations);
					Map<String, String> params1 = new HashMap<String, String>() ;
					PageInfo<Map<String, Object>> nextNo = substationService.list(params1, getPageInfo(1, 5000));
					model.put("nextNo", nextNo!=null?nextNo.getList():null);
					params.put("scanTimeEnd", endTime) ;
					model.put("params", params);
					model.put("list", pageList);
					return "scan/sendPrint";
				}			
				
				
				
				// 用于分站出站打印预览页面
				@RequestMapping(value = { "/sendPrintPage" })
				public String sendPrintPage(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					if (StringUtils.isEmptyWithTrim(params.get("scanTimeBegin"))) {
						params.put("scanTimeBegin", beginTime) ;
					}
					if (!StringUtils.isEmptyWithTrim(params.get("scanTimeEnd"))) {
						endTime = params.get("scanTimeEnd") ;
					}
					params.put("scanTimeEnd", endTime+" 23:59:59") ;
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
						String substationNo ;
						if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
							 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
						}else {
							substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
						}
						String sno[] =  substationNo.split(",") ;
						if (sno.length>1) {
							substationNo = substationNo.split(",")[1].replace("'", "") ;
						}else {
							substationNo = "000" ;
						}
						params.put("substationNo", substationNo);
					}
					params.put("orderStatus", "SEND") ;
					pageList = orderTrackService.scanList(params, getPageInfo(cpage,3000)) ;
					params.put("printTime", DateUtils.formatDate(nowDate)) ;
					params.put("userName", bossUser.getRealName()) ;
					params.put("scanTimeEnd", endTime) ;
					model.put("params", params);
					model.put("list", pageList);
					return "scan/sendPrintPage";
				}					
				
				
}
