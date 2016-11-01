package com.yogapay.boss.controller;

import java.io.IOException;
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
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.domain.ProDealHistory;
import com.yogapay.boss.domain.ProOrder;
import com.yogapay.boss.domain.ProOrderReason;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.service.ProDealHistoryService;
import com.yogapay.boss.service.ProOrderReasonService;
import com.yogapay.boss.service.ProOrderService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.KuaiDiYiBaiUtil;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

@Controller
@RequestMapping(value = "/porder")
public class PorderController extends BaseController {
	
	        @Resource
	        private ProOrderService proOrderService ;
	        @Resource
	        private OrderService orderService ;
	        @Resource
	        private OrderTrackService orderTrackService ;
	        @Resource
	        private SubstationService substationService ;
	        @Resource
	        private UserService userService ;
	        @Resource
	        private CourierService courierService ;
	        @Resource
	        private ProOrderReasonService proOrderReasonService ;
	        @Resource
	        private ProDealHistoryService proDealHistoryService ;
	
	
	
		      // 用于问题件列表
				@RequestMapping(value = { "/list" })
				public String list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					String orderNos = params.get("lgcOrderNo") ;
					
					if (!StringUtil.isEmptyWithTrim(orderNos)) {
						String o = "'0'" ;
						String[] ods = orderNos.split("\r\n") ;
						for (int i = 0; i < ods.length; i++) {
							o=o+ ",'"+ods[i]+"'" ;
						}
						params.put("orderNos", o) ;
					}
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -30, 0, 0),"yyyy-MM-dd") ;
					String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
						params.put("createTimeBegin", beginTime) ;
					}
					if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
						endTime = params.get("createTimeEnd") ;
					}
					params.put("createTimeEnd", endTime+" 23:59:59") ;
					pageList = proOrderService.list(params,getPageInfo(cpage));
					List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
					model.put("substations", substations);
					params.put("createTimeEnd", endTime) ;
					model.put("params", params);
					model.put("list", pageList);
					return "porder/plist";
				}	
				
				
				
				 // 用于问题件
				@RequestMapping(value = { "/add" })
				public String add(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					model.put("params", params);
					return "porder/ashow";
				}
		
				//用于保存问题件
				@RequestMapping(value = { "/psave"})
				public void csave(HttpServletResponse response, HttpServletRequest request,ProOrder proOrder) throws SQLException{
					Map<String, Object> retMap = new HashMap<String, Object> () ;
					try {
						 if (StringUtils.isEmptyWithTrim(proOrder.getOrderNo())) {
							 if (StringUtils.isEmptyWithTrim(proOrder.getLgcOrderNo())) {
								 retMap.put("ret", "操作失败") ;
								outJson(JsonUtil.toJson(retMap), response);
								return ;
							}else {
								 Map<String, Object> oMap = orderService.findByLgcOrderNo("", proOrder.getLgcOrderNo(), "") ;
								 if (oMap==null) {
									   retMap.put("ret", "运单号不存在") ;
									 	outJson(JsonUtil.toJson(retMap), response);
										return ;
								}else {
									String status = oMap.get("status").toString() ;
									if (!"2".equals(status)&&!"8".equals(status)&&!"7".equals(status)) {
										retMap.put("ret", "操作失败，订单已不在流转状态") ;
										outJson(JsonUtil.toJson(retMap), response);
										return ;
									}
									proOrder.setOrderNo(oMap.get("order_no").toString());
								}
							}
						 }
						
						 OrderTrack lastOrderTrack = orderTrackService.getLastOrderTrack(proOrder.getOrderNo()) ;
						 OrderTrack orderTrack = new OrderTrack() ;
						    orderTrack.setOrderNo(lastOrderTrack.getOrderNo());
							orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
							orderTrack.setCompleted("N");
							orderTrack.setIsLast("Y");
                            orderTrack.setScanOno(Constants.getUser().getUserName());							
							orderTrack.setScanOname(Constants.getUser().getRealName());
							orderTrack.setOrderStatus("PRO");
							System.out.println("*************************");
							Substation cur = null ;
						 if (lastOrderTrack!=null) {
							 System.out.println(lastOrderTrack);
							    orderTrack.setScanIno(lastOrderTrack.getScanIno());
								orderTrack.setScanIname(lastOrderTrack.getScanIname());
								orderTrack.setCurNo(lastOrderTrack.getCurNo());
								orderTrack.setCurType(lastOrderTrack.getCurType());
								 cur = substationService.getSubstationByNo(orderTrack.getCurNo()) ;
								if (cur!=null) {
									orderTrack.setContext("快件进入问题件处理，当前站是:"+cur.getSubstationName());
								}else {
									orderTrack.setContext("快件进入问题件处理");
								}
								orderTrack.setParentId(lastOrderTrack.getId());
								orderTrackService.unLastTrack(lastOrderTrack);
						   }else {
							  orderTrack.setContext("快件进入问题件处理，当前站未知");
						 }
					orderTrack.setOpname(Constants.getUser().getRealName());
					 orderTrackService.add(orderTrack);     
				
					 Map<String, Object> reason =  proOrderReasonService.getById(Integer.valueOf(proOrder.getProReason())) ;
					 proOrder.setDealStatus("1");
					 if ("N".equals(reason.get("dealed").toString())) {
						 proOrder.setDealStatus("2");
					}
					proOrder.setCreateTime(DateUtils.formatDate(new Date()));
					proOrder.setCheckName(Constants.getUser().getRealName());
					if (cur!=null) {
						proOrder.setCurSubstationNo(cur.getSubstationNo());
					}else {
						proOrder.setCurSubstationNo("000");
					}
					
					ProOrder p = proOrderService.findByOrderNo(proOrder.getOrderNo()) ;
					if (p==null) {
						proOrderService.save(proOrder) ;	
						orderService.proOrder(proOrder.getOrderNo(),proOrder.getId());	//设置问题件
					}else {
						p.setCheckName(proOrder.getCheckName());
						p.setProReason(proOrder.getProReason());
						p.setDescb(proOrder.getDescb());
						proOrderService.update(p);
						orderService.proOrder(proOrder.getOrderNo(),p.getId());	//设置问题件
					}
					retMap.put("ret", "1") ;
                    retMap.put("scanName", Constants.getUser().getRealName()) ;
					retMap.put("scanTime", DateUtils.formatDate(new Date())) ;
					outJson(JsonUtil.toJson(retMap), response);
					} catch (Exception e) {
						e.printStackTrace();
						retMap.put("ret", "操作失败") ;
						outJson(JsonUtil.toJson(retMap), response);
					}
				}
				
				

				 // 用于问题件处理登记
				@RequestMapping(value = { "/pro_text" })
				public String pro_text(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					ProOrder pOrder = proOrderService.findByOrderNo(params.get("orderNo")) ;
					Map<String, Object> oMap = orderService.findByOrderNo(pOrder.getOrderNo()) ;
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					String substationNo ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo);
					LgcConfig lgcConfig = (LgcConfig)request.getSession().getAttribute("lgcConfig") ;	
					String lgcno = "1131" ;
					if (lgcConfig!=null) {
						lgcno = lgcConfig.getLgcNo() ;
					}
					params.put("lgcNo", lgcno) ;
					PageInfo<Map<String, Object>> substations = substationService.list(params, getPageInfo(1, 5000));
					PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(1,5000));
					//model.put("substations", substations);
					model.put("substations", substations!=null?substations.getList():null);
					model.put("clist", courierList.getList());
					model.put("pOrder", pOrder);
					model.put("params", params);
					model.put("ostatus", oMap.get("status")) ;
					model.put("oMap", oMap) ;
					
					params.put("proId",String.valueOf(pOrder.getId())) ;
					List<Map<String, Object>> dList =  proDealHistoryService.list(params, getPageInfo(1,50)).getList() ;
					model.put("dList", dList) ;
					/////////////////////////////////////////////////////////////
					
					String lgcOrderNo = "" ;
					   String lgcNo = "";
					   String lgcName = "";
					   String pingyin = "";
					   String orderNo = pOrder.getOrderNo() ;
					   Map<String, Object> lgcMap = orderService.getLgcInfo(pOrder.getOrderNo());
					   Map<String, Object> orderMap =  oMap;
					   if (lgcMap != null) {
					     	if (lgcMap.get("lgcNo") != null) {
							lgcNo = lgcMap.get("lgcNo").toString();
						  }
						   if (lgcMap.get("lgcName") != null) {
							lgcName = lgcMap.get("lgcName").toString();
					  	}
					   if (lgcMap.get("pingyin") != null) {
							pingyin = lgcMap.get("pingyin").toString();
					  	}
					   
					   if (lgcMap.get("lgcOrderNo") != null) {
						   lgcOrderNo = lgcMap.get("lgcOrderNo").toString();
					  	}
				    }
					   Map<String, Object> trackMap = new HashMap<String, Object>() ;
					   List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>() ;
					   String ischeck = "0" ;
					if (lgcMap==null||StringUtils.isEmptyWithTrim(lgcOrderNo)|| StringUtils.isEmptyWithTrim(lgcNo)) {
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
							System.out.println(lgcMap.get("track_src").toString().toUpperCase()+"**************************");
							if ("LOC".equals(lgcMap.get("track_src").toString().toUpperCase())) {
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
					Collections.reverse(dataList);
					model.put("trackList", dataList);

					
					
					return "porder/pro_text";
				}
				
				
				//用于保存问题件
				@RequestMapping(value = { "/pro_text_save"})
				public void pro_text_save(@RequestParam Map<String, String> params,HttpServletResponse response, HttpServletRequest request,ProOrder proOrder) throws SQLException{
					try {
					String sno = params.get("curSubstationNo") ;
					String cno = params.get("courierNo") ;
					ProOrder pOrder = proOrderService.findByOrderNo(proOrder.getOrderNo()) ;
					
					String preason = pOrder.getProReason() ;
					String descb = pOrder.getDescb() ;
					
					 OrderTrack lastOrderTrack = orderTrackService.getLastOrderTrack(proOrder.getOrderNo()) ;
					 OrderTrack orderTrack = new OrderTrack() ;
					    orderTrack.setOrderNo(lastOrderTrack.getOrderNo());
						orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
						orderTrack.setCompleted("N");
						orderTrack.setIsLast("Y");
						orderTrack.setOrderStatus("REV");
						orderTrack.setPreNo(lastOrderTrack.getCurNo());
						orderTrack.setPreType(lastOrderTrack.getCurType());
					    orderTrack.setScanIno(Constants.getUser().getUserName());
						orderTrack.setScanIname(Constants.getUser().getRealName());
					    orderTrack.setParentId(lastOrderTrack.getId());
					    
					    Map<String, Object> sMap = proOrderService.getStatusById(Integer.valueOf(params.get("dealStatus"))) ;
						
						if ("Y".equals(sMap.get("completed"))) {
							orderTrack.setCurNo(sno);
							orderTrack.setCurType("S");
							Substation  cur = substationService.getSubstationByNo(orderTrack.getCurNo()) ;
							orderTrack.setContext("问题件处理完成，完成网点:"+cur.getSubstationName());
							//orderService.completed(pOrder.getOrderNo()) ;
						}else {
							if (StringUtils.isEmptyWithTrim(cno)) {
								orderTrack.setCurNo(sno);
								orderTrack.setCurType("S");
								Substation  cur = substationService.getSubstationByNo(orderTrack.getCurNo()) ;
								orderTrack.setContext("来自问题件处理，当前站是:"+cur.getSubstationName());
							}else {
								orderTrack.setCurNo(cno);
								orderTrack.setCurType("C");
								orderTrack.setContext("来自问题件处理，当前已重新指派给快递员:"+courierService.getCourierByNo(cno).getRealName());
								if ("2".equals(params.get("ostatus"))) {
									orderService.proUpdate(pOrder.getOrderNo(),sno,cno);
								}
							}
						}
					pOrder.setProReason(proOrder.getProReason());
					pOrder.setDescb(proOrder.getDescb());
					pOrder.setCurSubstationNo(proOrder.getCurSubstationNo());
					pOrder.setDealStatus(params.get("dealStatus"));
					pOrder.setLastDesc(params.get("dealText"));
					pOrder.setComoperator(Constants.getUser().getRealName());
					proOrderService.update(pOrder) ;
					
					
					
					
					ProDealHistory proDealHistory = new ProDealHistory() ;
					proDealHistory.setProId(pOrder.getId());
					proDealHistory.setDealText(params.get("dealText"));
					proDealHistory.setSubstationNo(sno);
					proDealHistory.setCouierNo(cno);
					proDealHistory.setDealer(Constants.getUser().getRealName());
					proDealHistory.setCreateTime(DateUtils.formatDate(new Date()));
					proDealHistory.setDealStatus(params.get("dealStatus"));
					Map<String,Object> rMap = proOrderReasonService.getById(Integer.valueOf(preason))  ;
					proDealHistory.setReson(StringUtils.nullString(rMap.get("context")));
					proDealHistory.setResonText(descb);
					proDealHistoryService.save(proDealHistory);
					
					orderTrackService.unLastTrack(lastOrderTrack);
					orderTrack.setOpname(Constants.getUser().getRealName());
					orderTrackService.add(orderTrack);     
					outText("1", response);    
					} catch (Exception e) {
						e.printStackTrace();
						outText("操作失败", response);
					}
				}
				

				 // 用于问题件编辑
				@RequestMapping(value = { "/pro_edit" })
				public String pro_edit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					ProOrder pOrder = proOrderService.findByOrderNo(params.get("orderNo")) ;
					model.put("pOrder", pOrder);
					model.put("params", params);
					return "porder/pro_edit";
				}
				
				//用于编辑保存
				@RequestMapping(value = { "/pro_edit_save"})
				public void pro_edit_save(HttpServletResponse response, HttpServletRequest request,ProOrder proOrder) throws SQLException{/*
					try {
					ProOrder pOrder = proOrderService.findByOrderNo(proOrder.getOrderNo()) ;
					pOrder.setNotes(proOrder.getNotes());
					proOrderService.update(pOrder) ;	
					outText("1", response);    
					} catch (Exception e) {
						e.printStackTrace();
						outText("操作失败", response);
					}
				*/}
				
				
				 // 用于问题件完成
				@RequestMapping(value = { "/pro_deal" })
				public String pro_deal(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					//ProOrder pOrder = proOrderService.findByOrderNo(params.get("orderNo")) ;
					//model.put("pOrder", pOrder);
					model.put("params", params);
					return "porder/pro_deal";
				}
				
				 // 用于问题件完成保存
				@RequestMapping(value = { "/pro_deal_save" })
				public void pro_deal_save(HttpServletResponse response, HttpServletRequest request,ProOrder proOrder) throws SQLException{/*
					try {
					ProOrder pOrder = proOrderService.findByOrderNo(proOrder.getOrderNo()) ;
					pOrder.setLastDesc(proOrder.getLastDesc());
					pOrder.setComoperator(Constants.getUser().getRealName());
					pOrder.setStatus("DEALED");
					proOrderService.update(pOrder) ;	
					
					 OrderTrack lastOrderTrack = orderTrackService.getLastOrderTrack(proOrder.getOrderNo()) ;
					 OrderTrack orderTrack = new OrderTrack() ;
					    orderTrack.setOrderNo(lastOrderTrack.getOrderNo());
						orderTrack.setOrderTime(DateUtils.formatDate(new Date()));
						orderTrack.setCompleted("N");
						orderTrack.setIsLast("Y");
						orderTrack.setOrderStatus("REV");
						orderTrack.setPreNo(lastOrderTrack.getCurNo());
						orderTrack.setPreType(lastOrderTrack.getCurType());
					    orderTrack.setScanIno(Constants.getUser().getUserName());
						orderTrack.setScanIname(Constants.getUser().getRealName());
					    orderTrack.setParentId(lastOrderTrack.getId());
					    orderTrack.setCurNo(lastOrderTrack.getCurNo());
						orderTrack.setCurType(lastOrderTrack.getCurType());
						orderTrack.setContext("问题件已经处理完成，如有问题请与客服联系");
						orderTrackService.unLastTrack(lastOrderTrack);
						orderTrackService.add(orderTrack);
					outText("1", response);    
					} catch (Exception e) {
						e.printStackTrace();
						outText("操作失败", response);
					}
				*/}
				
				 // 用于问题件列表
				@RequestMapping(value = { "/rlist" })
				public String rlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					pageList = proOrderReasonService.list(params, getPageInfo(cpage)) ;
					model.put("params", params);
					model.put("list", pageList);
					return "porder/rlist";
				}	
							
				 // 用于问题件原因
				@RequestMapping(value = { "/redit" })
				public String redit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Map<String, Object> proOrderReason = null ;
					if (!StringUtils.isEmptyWithTrim(params.get("id"))) {
						proOrderReason = proOrderReasonService.getById(Integer.parseInt(params.get("id"))) ;
					}
					model.put("params", params);
					model.put("proOrderReason", proOrderReason);
					return "porder/redit";
				}			
				
				
				@RequestMapping(value = { "/rsave"})
				public void rsave(HttpServletResponse response, HttpServletRequest request,ProOrderReason reason) throws SQLException{
					String r = "";
					try {
						if(reason.getId() == null||reason.getId()<1){
						   proOrderReasonService.save(reason);
						   r = "1";
						}else{
							r = "1";
							proOrderReasonService.update(reason) ;
						}
					} catch (Exception e) {
						e.printStackTrace();
						r = "保存失败";
					}
					outText(r, response);
				}		
			@RequestMapping(value="/pstop")	
			public void proStop(HttpServletResponse response, HttpServletRequest request){
				if(StringUtil.isEmptyWithTrim(request.getParameter("id"))){
					outText("停用失败", response);
				}else{
					proOrderReasonService.pstop(request.getParameter("id"));				
					outText("1", response);
				}			
			}
			@RequestMapping(value="/pstart")	
			public void pstart(HttpServletResponse response, HttpServletRequest request){
				if(StringUtil.isEmptyWithTrim(request.getParameter("id"))){
					outText("停用失败", response);
				}else{
					proOrderReasonService.pstart(request.getParameter("id"));				
					outText("1", response);
				}			
			}
}
