/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.classic.Logger;

import com.yogapay.boss.domain.BatchOrderAddr;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.City;
import com.yogapay.boss.domain.JSONResult;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.domain.RunnableUtils;
import com.yogapay.boss.init.SelectMap;
import com.yogapay.boss.service.BatchOrderService;
import com.yogapay.boss.service.CityService;
import com.yogapay.boss.service.ItemTypeService;
import com.yogapay.boss.service.LgcService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserAddrService;
import com.yogapay.boss.utils.CityUtil;
import com.yogapay.boss.utils.DateUtil;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.ExcelReader;
import com.yogapay.boss.utils.ExcelUtil;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtil;

import java.awt.Toolkit;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/batchOrder")
public class BatchOrderController extends BaseController {
	
	private static Logger log = (Logger) LoggerFactory.getLogger(BatchOrderController.class) ;
	@Resource
	private ItemTypeService itemTypeService;
	@Resource
	private CityService cityService;
	@Resource
	private BatchOrderService batchOrderService;
	@Resource
	private SequenceService sequenceService;
	@Resource
	private LgcService lgcService;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderTrackService orderTrackService;
	@Resource
	private UserAddrService userAddrService;
	@Resource
	private SubstationService substationService;
	@Value("#{config['lgcNo']}")
	private String gcNo;
	
	@RequestMapping(value = "addOrderMuchPage")
	public Object addOrderMuchPage(@RequestParam Map<String, String> params, ModelMap mmap) {
		try {
			List<City> list = this.cityService.queryListByParentId0();
			mmap.addAttribute("procity", CityUtil.getCityArea(list, "sprovince", "sendArea", "down_addrSend", "scity", "sarea", "addr_contSend"));
			Date date = DateUtil.getCurrentDate();
			String year = DateUtil.dateToString(date, "yyyy");
			mmap.addAttribute("jt", "今天（" + DateUtil.dateToString(date, "M月d日") + ")");
			mmap.addAttribute("jty", DateUtil.dateToString(date, "yyyy-MM-dd"));
			Date t1 = DateUtil.getAddDay(1);
			String t1year = DateUtil.dateToString(t1, "yyyy");
			mmap.addAttribute("mty", DateUtil.dateToString(t1, "yyyy-MM-dd"));
			if (year.equals(t1year)) {
				mmap.addAttribute("mt", "明天（" + DateUtil.dateToString(t1, "M月d日") + ")");
			} else {
				mmap.addAttribute("mt", "明年（" + DateUtil.dateToString(t1, "M月d日") + ")");
			}
			Date t2 = DateUtil.getAddDay(2);
			String t2year = DateUtil.dateToString(t2, "yyyy");
			mmap.addAttribute("hty", DateUtil.dateToString(t2, "yyyy-MM-dd"));
			if (year.equals(t2year)) {
				mmap.addAttribute("ht", "后天（" + DateUtil.dateToString(t2, "M月d日") + ")");
			} else {
				mmap.addAttribute("ht", "明年（" + DateUtil.dateToString(t2, "M月d日") + ")");
			}
			mmap.addAttribute("params", params);
		} catch (Exception ex) {
			log.error(null, ex);
		}
		return "batchOrder/orderMuchAdd";
	}
	
	@RequestMapping(value = "addOrderImportPage")
	public Object addOrderImportPage(@RequestParam Map<String, String> params, ModelMap mmap) {
		
		return "batchOrder/orderImport";
	}

	/**
	 *
	 *
	 * 添加批量下单
	 *
	 * @param params
	 * @param mmap
	 * @return
	 */
	@RequestMapping(value = "addOrderMuch")
	public Object addOrdeMuch(@RequestParam Map<String, String> params, ModelMap mmap, HttpServletRequest request) {
		try {
			
			Date nowDate = new Date();
			long batchNum = nowDate.getTime();
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			//Map<String, String> user = (Map<String, String>) request.getSession().getAttribute("user");
			Map<String, Object> mp = new HashMap<String, Object>();
			List<BatchOrderAddr> list = this.batchOrderService.getOrderAddrList(bossUser.getRealName(), "", "");
			if (StringUtil.isEmptyString(list) || list.size() <= 0) {
				mmap.addAttribute("msg", "请添加收件人信息");
				mmap.addAttribute("params", params);
				return "batchOrder/orderMuchAdd";
			}
			//mp.put("uid", params.get("uid"));
			//mp.put("lgcNo", params.get("lgcNo"));
			//mp.put("userName", bossUser.getRealName());

			Map<String, Object> mapOrder = new HashMap<String, Object>();
//            mapOrder.put("takeMap", params);
//            mapOrder.put("sendList", list);
			mapOrder.put("takeTimeBegin", params.get("takeTimeBegin"));
			if (!StringUtil.isEmptyString(params.get("takeTimeBegin"))) {
				String endTime = DateUtil.getHouserStr(DateUtil.stringToDate(params.get("takeTimeBegin")), 2);
				mapOrder.put("takeTimeEnd", endTime);
			} else {
				mapOrder.put("takeTimeEnd", "");
			}
			// mp.put("orderInfo", mapOrder);

//            if (mapOrder == null) {
//                mmap.addAttribute("msg", "订单信息不能为空");
//                mmap.addAttribute("params", params);
//                return "batchOrder/orderMuchAdd";
//            }


			//Map<String, Object> takeMap = (Map<String, Object>) mapOrder.get("takeMap");//取件地址信息
			if (params == null) {
				mmap.addAttribute("msg", "订单信息不能为空");
				mmap.addAttribute("params", params);
				return "batchOrder/orderMuchAdd";
			}

			//List<Map<String, Object>> sendList = (List<Map<String, Object>>) mapOrder.get("sendList");//派件地址信息
			if (list == null || list.isEmpty() || list.size() < 1) {
				mmap.addAttribute("msg", "订单信息不能为空");
				mmap.addAttribute("params", params);
				return "batchOrder/orderMuchAdd";
			}
			List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
			LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig");
			
			List<OrderInfo> orderList = new ArrayList<OrderInfo>();
			List<OrderTrack> orderTrackList = new ArrayList<OrderTrack>();
			StringBuffer lgcbuf = new StringBuffer();
			for (BatchOrderAddr sendmap : list) {
				
				OrderInfo orderInfo = new OrderInfo();
				String orderNo = sequenceService.getNextVal("order_no");
				orderInfo.setOrderNo(orderNo);//订单号
				orderInfo.setUserNo(bossUser.getRealName());
				orderInfo.setCreateTime(DateUtil.getStrCurrentDate("yyyy-MM-dd HH:mm:ss"));
				orderInfo.setLastUpdateTime(DateUtil.getStrCurrentDate("yyyy-MM-dd HH:mm:ss"));
				orderInfo.setStatus(1);    //处理中
				orderInfo.setSource("BATCH_ADD");
				orderInfo.setLgcOrderNo(sendmap.getLgcOrderNo());
				orderInfo.setLgcNo(bossUser.getLgcNo());
				orderInfo.setBatchNumber(String.valueOf(batchNum));
				if (!StringUtils.isEmpty(bossUser.getRealName()) && (lgcConfig.getKey().equals("gdt") || lgcConfig.getKey().equals("gangmeiwuliu"))) {
					String lgcOrderNo = sequenceService.getNextVal("lgc_order_no");//获取运单号
					String lgcOrderLast = sequenceService.getCurrVal("lgc_order_no_last");//获取运单号结尾
					if (Long.valueOf(lgcOrderNo) > Long.valueOf(lgcOrderLast)) {
						mmap.addAttribute("msg", "运单号已使用完结，请联系快递公司更新");
						mmap.addAttribute("params", params);
						return "batchOrder/orderMuchAdd";
					}
					orderInfo.setLgcOrderNo(lgcOrderNo);//运单号				
				}

				/**
				 * 发件信息
				 */
				orderInfo.setSendName(params.get("sendName"));
				orderInfo.setSendPhone(params.get("sendPhone"));
				orderInfo.setSendArea(params.get("sendArea"));
				orderInfo.setSendAddr(params.get("sendAddr"));
				/**
				 * 派件信息
				 */
				orderInfo.setRevName(sendmap.getRevName());
				orderInfo.setRevPhone(sendmap.getRevPhone());
				orderInfo.setRevArea(sendmap.getRevArea());
				orderInfo.setRevAddr(sendmap.getRevAddr());
				/**
				 * 其他物品信息及保价信息
				 */
				if (!StringUtil.isEmptyString(sendmap.getFreight())) {
					orderInfo.setFreight(Float.valueOf(String.valueOf(sendmap.getFreight())));
				} else {
					orderInfo.setFreight(0.00f);
				}
				if (!StringUtil.isEmptyString(sendmap.getItemStatus())) {
					System.out.println("itemStatus==========" + sendmap.getItemStatus());
					String itemStatus = sendmap.getItemStatus();
					if (itemTypeService.isExsit(itemStatus)) {
						orderInfo.setItemStatus(itemStatus);//物品类型
					} else {
						itemStatus = itemTypeService.firstItemStatus();
					}
					orderInfo.setItemStatus(itemStatus);
				} else {
					orderInfo.setItemStatus(itemTypeService.firstItemStatus());
				}
				if (!StringUtil.isEmptyString(sendmap.getItemWeight())) {
					System.out.println("itemWeight==========" + sendmap.getItemWeight());
					orderInfo.setItemWeight(Float.valueOf(sendmap.getItemWeight()));//物品类型
				}
				if (!StringUtil.isEmptyString(sendmap.getFreightType())) {
					System.out.println("freightType==========" + sendmap.getFreightType());
					orderInfo.setFreightType(sendmap.getFreightType());//邮费支付方
				} else {
					orderInfo.setFreightType("1");
				}
				
				if (!StringUtil.isEmptyString(sendmap.getPayType())) {
					System.out.println("payType==========" + sendmap.getPayType());
					orderInfo.setPayType(sendmap.getPayType());//支付方式
				}else{
				    orderInfo.setPayType("CASH");
				}
				
				if (!StringUtil.isEmptyString(sendmap.getGoodValuation())) {
					orderInfo.setGoodValuation(Float.valueOf(sendmap.getGoodValuation()));//保价金额
					/**
					 * 计算报价手续费
					 */
					Map<String, Object> vRate = lgcService.getLgcVrate();//货物保价费率
					float lvpay = getPayByRate(orderInfo.getGoodValuation(), vRate);//保价费
					orderInfo.setGoodValuationRate(String.valueOf(Float.valueOf((String) vRate.get("rate")) / 1000f));
					if (!StringUtil.isEmptyString(lvpay)) {
						//orderInfo.setVpay(String.valueOf(lvpay));
						orderInfo.setVpay(lvpay);
					}
				} else {
					orderInfo.setVpay(0.00f);
					orderInfo.setGoodValuation(0.00f);
				}
				if ("1".equals(orderInfo.getFreightType())) {					
					orderInfo.setTnpay(orderInfo.getFreight() + orderInfo.getVpay() + "");
					orderInfo.setSnpay("0");
				} else {	
					orderInfo.setTnpay("0");
					orderInfo.setSnpay(orderInfo.getFreight() + orderInfo.getVpay() + "");					
				}
				
				orderInfo.setPayAcount(orderInfo.getFreight() + orderInfo.getVpay());
				
				if (!StringUtil.isEmptyString(sendmap.getOrderNote())) {
					orderInfo.setOrderNote(sendmap.getOrderNote());
				}
				if (!StringUtil.isEmptyString(mapOrder.get("takeTimeBegin")) && !StringUtil.isEmptyString(mapOrder.get("takeTimeEnd"))) {
					orderInfo.setTakeTimeBegin(DateUtils.formatDate((String) mapOrder.get("takeTimeBegin"), "yyyy-MM-dd HH:mm:ss"));
					orderInfo.setTakeTimeEnd(DateUtils.formatDate((String) mapOrder.get("takeTimeBegin"), "yyyy-MM-dd HH:mm:ss"));
				}
				if (!StringUtil.isEmptyString(sendmap.getLgcOrderNo())) {
					String lgcOrderNo = String.valueOf(sendmap.getLgcOrderNo());
					orderInfo.setLgcOrderNo(lgcOrderNo);//userName
					Map<String, Object> order1 = orderService.getOrderInfoByLgcOrderNo(lgcOrderNo, lgcConfig.getLgcNo());
					if (order1 != null) {
						Map<String, Object> errorMap = new HashMap<String, Object>();
						errorMap.put("lgcOrderNo", lgcOrderNo);
						errorMap.put("context", "已被使用,请更换运单号");
						errorList.add(errorMap);
						continue;
//                        if (bossUser.getRealName().equals(order1.get("userNo"))) {
//                            orderService.updateOrderInfo(orderInfo);
//                            OrderTrack track = new OrderTrack();
//                            track.setOrderNo(orderInfo.getOrderNo());
//                            track.setContext("订单于" + DateUtils.formatDate(nowDate) + "被用户" + bossUser.getRealName() + "更改");
//                            track.setOrderTime(DateUtils.formatDate(nowDate));
//                            track.setCompleted("N");
//                            orderTrackService.addOrder(track);
//                            orderTrackList.add(track);
//                            //continue;
//                        } else {
//                            Map<String, Object> errorMap = new HashMap<String, Object>();
//                            errorMap.put("lgcOrderNo", lgcOrderNo);
//                            errorMap.put("context", "运单号已被使用,请更换运单号");
//                            errorList.add(errorMap);
//                            continue;
//                        }
					}
				}
				//lgc_order_no唯一约束 不允许为空字符串，可以为空值
				if (StringUtils.isEmpty(orderInfo.getLgcOrderNo())) {
					orderInfo.setLgcOrderNo(null);
				}
				orderList.add(orderInfo);

				/**
				 * 更新轨迹
				 */
				OrderTrack track = new OrderTrack();
				track.setOrderNo(orderInfo.getOrderNo());
				track.setContext("订单被创建");
				track.setOrderTime(DateUtils.formatDate(nowDate));
				track.setCompleted("N");
				orderTrackList.add(track);
			}
			RunnableUtils run = new RunnableUtils();
			run.batchOrderRunble(orderList, orderTrackList, userAddrService, bossUser.getRealName(), dynamicDataSourceHolder.getKey(), dynamicDataSourceHolder, String.valueOf(batchNum));
			
			if (!errorList.isEmpty() && errorList.size() > 0) {
				if (!errorList.isEmpty() && errorList.size() > 0) {
					StringBuffer buf = new StringBuffer();
					for (Map<String, Object> ap : errorList) {
						buf.append("运单号：").append(ap.get("lgcOrderNo")).append(ap.get("context")).append("\\n");
					}
					// request.setAttribute("errorMsg", buf.toString());
					mmap.addAttribute("errorMsg", buf.toString());
				}
			}
			this.batchOrderService.del(bossUser.getRealName());
			request.getSession().removeAttribute("fileNames");
			request.getSession().setAttribute("orderPath", "/batchOrder/addOrderMuchPage");
			mmap.addAttribute("params", params);
		} catch (Exception ex) {
			log.error(null, ex);
			ex.printStackTrace();
			mmap.addAttribute("params", params);
			mmap.addAttribute("msg", "服务器异常");
			return "batchOrder/orderMuchAdd";
		}
		return "redirect:/batchOrder/ordersucPage";
	}
	
	@RequestMapping(value = "ordersucPage")
	public Object ordersucPage(@RequestParam Map<String, String> params, ModelMap mmap) {
		return "batchOrder/ordersuc";
	}

	/**
	 *
	 *
	 * 历史数据批量下单
	 *
	 * @param params
	 * @param mmap
	 * @return
	 */
	@RequestMapping(value = "addOrdeImport")
	public Object addOrdeImport(@RequestParam Map<String, String> params, ModelMap mmap, HttpServletRequest request) {
		try {
			
			Date nowDate = new Date();
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			//Map<String, String> user = (Map<String, String>) request.getSession().getAttribute("user");
			Map<String, Object> mp = new HashMap<String, Object>();
			List<BatchOrderAddr> list = this.batchOrderService.queryAddrImport(bossUser.getRealName(), "", "");
			if (StringUtil.isEmptyString(list) || list.size() <= 0) {
				mmap.addAttribute("msg", "请添加收件人信息");
				mmap.addAttribute("params", params);
				return "batchOrder/orderImport";
			}
			//mp.put("uid", params.get("uid"));
			//mp.put("lgcNo", params.get("lgcNo"));
			//mp.put("userName", bossUser.getRealName());

			Map<String, Object> mapOrder = new HashMap<String, Object>();
//            mapOrder.put("takeMap", params);
//            mapOrder.put("sendList", list);
			mapOrder.put("takeTimeBegin", params.get("takeTimeBegin"));
			if (!StringUtil.isEmptyString(params.get("takeTimeBegin"))) {
				String endTime = DateUtil.getHouserStr(DateUtil.stringToDate(params.get("takeTimeBegin")), 2);
				mapOrder.put("takeTimeEnd", endTime);
			} else {
				mapOrder.put("takeTimeEnd", "");
			}
			// mp.put("orderInfo", mapOrder);

//            if (mapOrder == null) {
//                mmap.addAttribute("msg", "订单信息不能为空");
//                mmap.addAttribute("params", params);
//                return "batchOrder/orderMuchAdd";
//            }


			//Map<String, Object> takeMap = (Map<String, Object>) mapOrder.get("takeMap");//取件地址信息
			if (params == null) {
				mmap.addAttribute("msg", "订单信息不能为空");
				mmap.addAttribute("params", params);
				return "batchOrder/orderMuchAdd";
			}

			//List<Map<String, Object>> sendList = (List<Map<String, Object>>) mapOrder.get("sendList");//派件地址信息
			if (list == null || list.isEmpty() || list.size() < 1) {
				mmap.addAttribute("msg", "订单信息不能为空");
				mmap.addAttribute("params", params);
				return "batchOrder/orderMuchAdd";
			}
			List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
			Map<String, String> errorListx = new HashMap<String, String>();
			LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig");
			
			List<OrderInfo> orderList = new ArrayList<OrderInfo>();
			List<OrderTrack> orderTrackList = new ArrayList<OrderTrack>();
			List<Integer> ids = new ArrayList<Integer>();
			StringBuffer erbuf = new StringBuffer();
			for (BatchOrderAddr sendmap : list) {
				OrderInfo orderInfo = new OrderInfo();
				boolean flag = false;
				if (!StringUtil.isEmptyString(sendmap.getSendSubstation()) || !StringUtil.isEmptyString(sendmap.getSendCourier())) {
					Map<String, Object> station = substationService.queryIsExitsBySubstationName(sendmap.getSendSubstation(), sendmap.getSendCourier());
					if (station != null) {
						orderInfo.setSendSubstationNo(StringUtil.isEmptyString(station.get("substationNo")) ? "" : station.get("substationNo").toString());
						orderInfo.setSendCourierNo(StringUtil.isEmptyString(station.get("courierNo")) ? "" : station.get("courierNo").toString());//寄件
					} else {
						if (!errorListx.containsKey(sendmap.getSendSubstation()) || !errorListx.containsKey(sendmap.getSendCourier())) {
							errorListx.put(StringUtil.isEmptyString(sendmap.getSendSubstation()) ? sendmap.getSendCourier() : sendmap.getSendSubstation(), StringUtil.isEmptyString(sendmap.getSendSubstation()) ? "" : sendmap.getSendCourier());
							if (!StringUtil.isEmptyString(sendmap.getSendSubstation())) {
								erbuf.append("寄件网点:").append(sendmap.getSendSubstation()).append("  ");
							}
							if (!StringUtil.isEmptyString(sendmap.getSendCourier())) {
								erbuf.append("寄件快递员:").append(sendmap.getSendCourier()).append("  ");
							}
							erbuf.append("匹配不到").append("     ");
							flag = true;
						}
					}
				}
				if (!StringUtil.isEmptyString(sendmap.getRevSubstation()) || !StringUtil.isEmptyString(sendmap.getRevCourier())) {
					Map<String, Object> station = substationService.queryIsExitsBySubstationName(sendmap.getRevSubstation(), sendmap.getRevCourier());
					if (station != null) {
						orderInfo.setSubStationNo(StringUtil.isEmptyString(station.get("substationNo")) ? "" : station.get("substationNo").toString());
						orderInfo.setTakeCourierNo(StringUtil.isEmptyString(station.get("courierNo")) ? "" : station.get("courierNo").toString());//收件
					} else {
						if (!errorListx.containsKey(sendmap.getRevSubstation()) || !errorListx.containsKey(sendmap.getRevCourier())) {
							errorListx.put(StringUtil.isEmptyString(sendmap.getRevSubstation()) ? sendmap.getRevCourier() : sendmap.getRevSubstation(), StringUtil.isEmptyString(sendmap.getRevSubstation()) ? "" : sendmap.getRevCourier());
							if (!StringUtil.isEmptyString(sendmap.getSendSubstation())) {
								erbuf.append("收件网点:").append(sendmap.getSendSubstation()).append("  ");
							}
							if (!StringUtil.isEmptyString(sendmap.getSendCourier())) {
								erbuf.append("收件快递员:").append(sendmap.getSendCourier()).append("  ");
							}
							erbuf.append("匹配不到").append("\\n");
							flag = true;
						}
					}
				}
				
				if (flag) {
					ids.add(sendmap.getId());
					continue;
				}
				
				String orderNo = sequenceService.getNextVal("order_no");
				orderInfo.setOrderNo(orderNo);//订单号
				orderInfo.setUserNo(bossUser.getRealName());
				orderInfo.setCreateTime(DateUtil.getStrCurrentDate("yyyy-MM-dd HH:mm:ss"));
				orderInfo.setLastUpdateTime(DateUtil.getStrCurrentDate("yyyy-MM-dd HH:mm:ss"));
				orderInfo.setStatus(3);    //处理中
				orderInfo.setSource("WEB");
				orderInfo.setLgcOrderNo(sendmap.getLgcOrderNo());
				orderInfo.setLgcNo(bossUser.getLgcNo());
				orderInfo.setExamineStatus("PASS");

//                if (!StringUtils.isEmpty(bossUser.getRealName()) && (lgcConfig.getKey().equals("gdt") || lgcConfig.getKey().equals("gangmeiwuliu"))) {
//                    String lgcOrderNo = sequenceService.getNextVal("lgc_order_no");//获取运单号
//                    String lgcOrderLast = sequenceService.getCurrVal("lgc_order_no_last");//获取运单号结尾
//                    if (Long.valueOf(lgcOrderNo) > Long.valueOf(lgcOrderLast)) {
//                        mmap.addAttribute("msg", "运单号已使用完结，请联系快递公司更新");
//                        mmap.addAttribute("params", params);
//                        return "batchOrder/orderMuchAdd";
//                    }
//                    orderInfo.setLgcOrderNo(lgcOrderNo);//运单号				
//                }

				/**
				 * 发件信息
				 */
				orderInfo.setSendName(sendmap.getSendName());
				orderInfo.setSendPhone(sendmap.getSendPhone());
				orderInfo.setSendArea(sendmap.getSendArea());
				orderInfo.setSendAddr(sendmap.getSendAddr());
				orderInfo.setTakeOrderTime(sendmap.getSendTime());

				/**
				 * 派件信息
				 */
				orderInfo.setRevName(sendmap.getRevName());
				orderInfo.setRevPhone(sendmap.getRevPhone());
				orderInfo.setRevArea(sendmap.getRevArea());
				orderInfo.setRevAddr(sendmap.getRevAddr());
				/**
				 * 其他物品信息及保价信息
				 */
				if (!StringUtil.isEmptyString(sendmap.getFreight())) {
					orderInfo.setFreight(Float.valueOf(String.valueOf(sendmap.getFreight())));
				} else {
					orderInfo.setFreight(0.00f);
				}
				if (!StringUtil.isEmptyString(sendmap.getItemStatus())) {
					System.out.println("itemStatus==========" + sendmap.getItemStatus());
					String itemStatus = sendmap.getItemStatus();
					if (itemTypeService.isExsit(itemStatus)) {
						orderInfo.setItemStatus(itemStatus);//物品类型
					} else {
						itemStatus = itemTypeService.firstItemStatus();
					}
					orderInfo.setItemStatus(itemStatus);
				} else {
					orderInfo.setItemStatus(itemTypeService.firstItemStatus());
				}
				if (!StringUtil.isEmptyString(sendmap.getItemWeight())) {
					System.out.println("itemWeight==========" + sendmap.getItemWeight());
					orderInfo.setItemWeight(Float.valueOf(sendmap.getItemWeight()));//物品类型
				}
				if (!StringUtil.isEmptyString(sendmap.getFreightType())) {
					System.out.println("freightType==========" + sendmap.getFreightType());
					orderInfo.setFreightType(sendmap.getFreightType());//邮费支付方
				}
				if (!StringUtil.isEmptyString(sendmap.getPayType())) {
					System.out.println("payType==========" + sendmap.getPayType());
					orderInfo.setPayType(sendmap.getPayType());//支付方式
				}
				if (!StringUtil.isEmptyString(sendmap.getGoodValuation())) {
					orderInfo.setGoodValuation(Float.valueOf(sendmap.getGoodValuation()));//保价金额
					/**
					 * 计算报价手续费
					 */
					Map<String, Object> vRate = lgcService.getLgcVrate();//货物保价费率
					float lvpay = getPayByRate(orderInfo.getGoodValuation(), vRate);//保价费
					orderInfo.setGoodValuationRate(String.valueOf(Float.valueOf((String) vRate.get("rate")) / 1000f));
					if (!StringUtil.isEmptyString(lvpay)) {
						//orderInfo.setVpay(String.valueOf(lvpay));
						orderInfo.setVpay(lvpay);
					}
				}
				
				if (!StringUtil.isEmptyString(sendmap.getOrderNote())) {
					orderInfo.setOrderNote(sendmap.getOrderNote());
				}
				if (!StringUtil.isEmptyString(mapOrder.get("takeTimeBegin")) && !StringUtil.isEmptyString(mapOrder.get("takeTimeEnd"))) {
					orderInfo.setTakeTimeBegin(DateUtils.formatDate((String) mapOrder.get("takeTimeBegin"), "yyyy-MM-dd HH:mm:ss"));
					orderInfo.setTakeTimeEnd(DateUtils.formatDate((String) mapOrder.get("takeTimeBegin"), "yyyy-MM-dd HH:mm:ss"));
				}
				if (!StringUtil.isEmptyString(sendmap.getLgcOrderNo())) {
					String lgcOrderNo = String.valueOf(sendmap.getLgcOrderNo());
					orderInfo.setLgcOrderNo(lgcOrderNo);//userName
					Map<String, Object> order1 = orderService.getOrderInfoByLgcOrderNo(lgcOrderNo, lgcConfig.getLgcNo());
					if (order1 != null) {
						Map<String, Object> errorMap = new HashMap<String, Object>();
						errorMap.put("lgcOrderNo", lgcOrderNo);
						errorMap.put("context", "已存在");
						errorList.add(errorMap);
						continue;
//                        if (bossUser.getRealName().equals(order1.get("userNo"))) {
//                            orderService.updateOrderInfo(orderInfo);
//                            OrderTrack track = new OrderTrack();
//                            track.setOrderNo(orderInfo.getOrderNo());
//                            track.setContext("订单于" + DateUtils.formatDate(nowDate) + "被用户" + bossUser.getRealName() + "更改");
//                            track.setOrderTime(DateUtils.formatDate(nowDate));
//                            track.setCompleted("N");
//                            orderTrackService.addOrder(track);
//                            orderTrackList.add(track);
//                            //continue;
//                        } else {
//                            Map<String, Object> errorMap = new HashMap<String, Object>();
//                            errorMap.put("lgcOrderNo", lgcOrderNo);
//                            errorMap.put("context", "运单号已被使用,请更换运单号");
//                            errorList.add(errorMap);
//                            continue;
//                        }
					}
				}
				orderList.add(orderInfo);

				/**
				 * 更新轨迹
				 */
				OrderTrack track = new OrderTrack();
				track.setOrderNo(orderInfo.getOrderNo());
				track.setContext("订单被导入");
				track.setOrderTime(DateUtils.formatDate(nowDate));
				track.setCompleted("N");
				orderTrackList.add(track);
			}
			RunnableUtils run = new RunnableUtils();
			if (orderList != null && !orderList.isEmpty()) {
				run.BatchOrderImportThread(orderList, orderTrackList, userAddrService, bossUser.getRealName(), dynamicDataSourceHolder.getKey(), dynamicDataSourceHolder);
			}
			StringBuffer buf = new StringBuffer();
			if (!errorList.isEmpty() && errorList.size() > 0) {
				if (!errorList.isEmpty() && errorList.size() > 0) {
					
					for (Map<String, Object> ap : errorList) {
						buf.append("运单号：").append(ap.get("lgcOrderNo")).append(ap.get("context")).append("\\n");
					}
					// request.setAttribute("errorMsg", buf.toString());

				}
			}
			if (!StringUtil.isEmptyString(erbuf.toString()) || !StringUtil.isEmptyString(buf.toString())) {
				mmap.addAttribute("errorMsg", erbuf.toString() + buf.toString());
			}
			this.batchOrderService.delImport(bossUser.getRealName());
			request.getSession().removeAttribute("importfileNames");
			request.getSession().setAttribute("orderPath", "/batchOrder/addOrderImportPage");
			mmap.addAttribute("params", params);
		} catch (Exception ex) {
			log.error(null, ex);
			ex.printStackTrace();
			mmap.addAttribute("params", params);
			mmap.addAttribute("msg", "服务器异常");
			return "batchOrder/orderMuchAdd";
		}
		return "batchOrder/ordersuc";
	}
	
	@RequestMapping(value = "/selectOrderAddr")
	public Object queryOrderAddr(@RequestParam Map<String, String> params, ModelMap mmp, HttpServletRequest request) {
		try {
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			List<BatchOrderAddr> list = this.batchOrderService.getOrderAddrList(bossUser.getRealName(), "", params.get("commonName"));
			String addrStr = StringUtil.setParam(params);
			request.getSession().setAttribute("addrStr", addrStr);
			mmp.addAttribute("list", list);
			mmp.addAttribute("params", params);
			mmp.addAttribute("PAYP", SelectMap.freightMap);
			mmp.addAttribute("PAYTYPE", SelectMap.payMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(null, ex);
		}
		return "batchOrder/queryAddr";
	}

	/**
	 * 历史数据导入查询
	 *
	 * @param params
	 * @param mmp
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryOrderAddrImport")
	public Object queryOrderAddrImport(@RequestParam Map<String, String> params, ModelMap mmp, HttpServletRequest request) {
		try {
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			List<BatchOrderAddr> list = this.batchOrderService.queryAddrImport(bossUser.getRealName(), "", params.get("commonName"));
			String addrStr = StringUtil.setParam(params);
			request.getSession().setAttribute("addrStrh", addrStr);
			mmp.addAttribute("list", list);
			mmp.addAttribute("params", params);
			mmp.addAttribute("PAYP", SelectMap.freightMap);
			mmp.addAttribute("PAYTYPE", SelectMap.payMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(null, ex);
		}
		return "batchOrder/queryAddrImport";
	}
	
	@RequestMapping(value = "/editOrderAddr")
	public void addOrderAddr(BatchOrderAddr orderAddr, HttpServletRequest request, ModelMap mmap, HttpServletResponse rsp) {
		JSONResult result = new JSONResult();
		try {
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			orderAddr.setUserName(bossUser.getRealName());
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", orderAddr.getId());
			param.put("lgcOrderNo", orderAddr.getLgcOrderNo());
			param.put("userName", orderAddr.getUserName());
			BatchOrderAddr b = this.batchOrderService.queryExitsByNum(param);
			if (StringUtil.isEmptyString(b)) {
				if (orderAddr.getId() > 0) {
					this.batchOrderService.updateOrderAddr(orderAddr);
				} else {
					this.batchOrderService.addOrderAddr(orderAddr);
				}
			} else {
				result.setErrorCode(1);
				result.setMessage("运单号已经存在，不能新增");
			}
		} catch (Exception ex) {
			log.error(null, ex);
			result.setErrorCode(1);
			result.setMessage("服务器异常");
		}
		outJson(JsonUtil.toJson(result), rsp);
	}
	
	@RequestMapping(value = "/editOrderImportAddr")
	public void editOrderImportAddr(BatchOrderAddr orderAddr, HttpServletRequest request, ModelMap mmap, HttpServletResponse rsp) {
		JSONResult result = new JSONResult();
		try {
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			orderAddr.setUserName(bossUser.getRealName());
			if (orderAddr.getId() > 0) {
				this.batchOrderService.updateOrderImportAddr(orderAddr);
			} else {
				this.batchOrderService.addOrderImportAddr(orderAddr);
			}
		} catch (Exception ex) {
			log.error(null, ex);
			result.setErrorCode(1);
			result.setMessage("服务器异常");
		}
		outJson(JsonUtil.toJson(result), rsp);
	}
	
	@RequestMapping(value = "/editOrderAddrPage")
	public Object addOrderAddrPage(@RequestParam Map<String, String> params, @RequestParam(defaultValue = "0") int id, ModelMap mmp, HttpServletRequest request) {
		
		try {
//            String uid = (String) request.getAttribute("uid");
//            params.put("uid", uid);
//            String str = HttpUtil.post(interceUrl + "webOrder-addOrder/itemType", params);
//            Map<String, Object> ap = CommonConstant.findMapper().readValue(str, Map.class);
//            List<Map<String, Object>> mList = CommonConstant.findMapper().readValue(CommonConstant.findMapper().writeValueAsString(ap.get("itemTypeList")), List.class);
//            Map<String, Object> pMap = new HashMap<String, Object>();
//            for (Map<String, Object> map : mList) {
//                pMap.put((String) map.get("itemText"), map.get("itemText"));
//            }
			List<Map<String, Object>> itemType = userAddrService.getItmeType();
			Map<String, Object> wtype = new HashMap<String, Object>();
			for (Map<String, Object> t : itemType) {
				wtype.put(t.get("itemText") + "", t.get("itemText"));
			}
			mmp.addAttribute("WPTYPE", wtype);
			BatchOrderAddr order;
			if (id > 0) {
				order = this.batchOrderService.queryById(id);
			} else {
				order = new BatchOrderAddr();
			}
			List<City> list = this.cityService.queryListByParentId0();
			mmp.addAttribute("procityone", CityUtil.getCityArea(list, "sprovince", "revArea", "down_addrRev", "scityTwo", "sareaTwo", "addr_contRev"));
			mmp.addAttribute("orderAddr", order);
			mmp.addAttribute("params", params);
			mmp.addAttribute("PAYP", SelectMap.freightMap);
			mmp.addAttribute("PAYTYPE", SelectMap.payMap);
		} catch (Exception ex) {
			log.error(null, ex);
		}
		
		return "batchOrder/orderAddrAdd";
	}
	
	@RequestMapping(value = "/addOrderAddrImportPage")
	public Object addOrderAddrImportPage(@RequestParam Map<String, String> params, @RequestParam(defaultValue = "0") int id, ModelMap mmp, HttpServletRequest request) {
		
		try {
//            String uid = (String) request.getAttribute("uid");
//            params.put("uid", uid);
//            String str = HttpUtil.post(interceUrl + "webOrder-addOrder/itemType", params);
//            Map<String, Object> ap = CommonConstant.findMapper().readValue(str, Map.class);
//            List<Map<String, Object>> mList = CommonConstant.findMapper().readValue(CommonConstant.findMapper().writeValueAsString(ap.get("itemTypeList")), List.class);
//            Map<String, Object> pMap = new HashMap<String, Object>();
//            for (Map<String, Object> map : mList) {
//                pMap.put((String) map.get("itemText"), map.get("itemText"));
//            }
			List<Map<String, Object>> itemType = userAddrService.getItmeType();
			Map<String, Object> wtype = new HashMap<String, Object>();
			for (Map<String, Object> t : itemType) {
				wtype.put(t.get("itemText") + "", t.get("itemText"));
			}
			mmp.addAttribute("WPTYPE", wtype);
			BatchOrderAddr order;
			if (id > 0) {
				order = this.batchOrderService.queryImportById(id);
			} else {
				order = new BatchOrderAddr();
			}
			List<City> list = this.cityService.queryListByParentId0();
			mmp.addAttribute("procityone", CityUtil.getCityArea(list, "sprovince", "revArea", "down_addrRev", "scityTwo", "sareaTwo", "addr_contRev"));
			mmp.addAttribute("orderAddr", order);
			mmp.addAttribute("params", params);
			mmp.addAttribute("PAYP", SelectMap.freightMap);
			mmp.addAttribute("PAYTYPE", SelectMap.payMap);
		} catch (Exception ex) {
			log.error(null, ex);
		}
		
		return "batchOrder/orderAddrImportAdd";
	}

	/**
	 * 清空
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cleanOrderAddr")
	public Object cleanOrderAddr(@RequestParam Map<String, String> params, HttpServletRequest request) {
		try {
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			this.batchOrderService.del(bossUser.getRealName());
			request.getSession().removeAttribute("fileNames");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(null, ex);
		}
		return "redirect:selectOrderAddr?layout=no";
	}

	/**
	 * 历史数据导入清空
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cleanOrderAddrImport")
	public Object cleanOrderAddrImport(@RequestParam Map<String, String> params, HttpServletRequest request) {
		try {
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			this.batchOrderService.delImport(bossUser.getRealName());
			request.getSession().removeAttribute("importfileNames");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(null, ex);
		}
		return "redirect:queryOrderAddrImport?layout=no";
	}

	/**
	 * 下订单导出
	 *
	 * @param params
	 * @param mmp
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/orderAddExport")
	public void orderAddExport(@RequestParam Map<String, String> params, ModelMap mmp, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		try {
			int celSize = 0;
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			List<BatchOrderAddr> list = new ArrayList<BatchOrderAddr>();
			list = this.batchOrderService.getOrderAddrList(bossUser.getRealName(), params.get("ids"), "");
			LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig");
			ExcelUtil eu = new ExcelUtil();
			eu.createWorkBook();
			eu.createSheet("order");
			//代表亿翔
			/*if (gcNo.equals(lgcConfig.getLgcNo())) {
			 celSize = 13;
			 Object[][] objHead = {{"姓名", "联系电话", "省", "市", "区", "收件地址", "物品类别", "物品重量(单位kg)", "付款人", "付款方式", "保价金额", "运单号", "备注"}};
			 eu.putHead(objHead);
			 } else {
			 celSize = 13;
			 Object[][] objHead = {{"姓名", "联系电话", "省", "市", "区", "收件地址", "物品类别", "物品重量(单位kg)", "付款人", "付款方式", "保价金额", "运单号", "备注"}};
			 eu.putHead(objHead);
			 }*/
			
			celSize = 13;
			Object[][] objHead = {{"姓名", "联系电话", "省", "市", "区", "收件地址", "物品类别", "物品重量(单位kg)", "付款人", "付款方式", "保价金额", "运单号", "备注"}};
			eu.putHead(objHead);
			
			
			Object[][] objData = new Object[list.size()][celSize];
			for (int i = 0; i < list.size(); i++) {
				BatchOrderAddr oAddr = list.get(i);
				objData[i][0] = oAddr.getRevName();
				objData[i][1] = oAddr.getRevPhone();
				if (StringUtil.isEmptyString(oAddr.getRevArea())) {
					objData[i][2] = "";
					objData[i][3] = "";
					objData[i][4] = "";
				} else {
					String[] area = oAddr.getRevArea().split("-");
					objData[i][2] = area[0];
					if (area.length == 1) {
						objData[i][3] = "";
						objData[i][4] = "";
					}
					if (area.length > 1 && area.length < 3) {
						objData[i][3] = area[1];
						objData[i][4] = "";
					}
					if (area.length == 3) {
						objData[i][3] = area[1];
						objData[i][4] = area[2];
					}
				}

				//objData[i][2] = Toolkit.isEmptyString(oAddr.getRevArea()) ? "" + oAddr.getRevAddr() : oAddr.getRevArea() + oAddr.getRevAddr();
				objData[i][5] = oAddr.getRevAddr();
				objData[i][6] = oAddr.getItemStatus();
				objData[i][7] = StringUtil.isEmptyString(oAddr.getItemWeight()) ? "" : oAddr.getItemWeight();
				objData[i][8] = StringUtil.isEmptyString(oAddr.getFreightType()) ? "" : SelectMap.freightMap.get(oAddr.getFreightType());
				objData[i][9] = StringUtil.isEmptyString(oAddr.getPayType()) ? "" : SelectMap.payMap.get(oAddr.getPayType());
				objData[i][10] = StringUtil.isEmptyString(oAddr.getGoodValuation()) ? "0.00" : oAddr.getGoodValuation();
				/*if (gcNo.equals(request.getAttribute("lgcNo"))) {
				 objData[i][11] = StringUtil.isEmptyString(oAddr.getLgcOrderNo()) ? "" : oAddr.getLgcOrderNo();
				 objData[i][12] = StringUtil.isEmptyString(oAddr.getOrderNote()) ? "" : oAddr.getOrderNote();
				 } else {
				 objData[i][11] = StringUtil.isEmptyString(oAddr.getLgcOrderNo()) ? "" : oAddr.getLgcOrderNo();
				 objData[i][12] = StringUtil.isEmptyString(oAddr.getOrderNote()) ? "" : oAddr.getOrderNote();
				 }*/
				
				objData[i][11] = StringUtil.isEmptyString(oAddr.getLgcOrderNo()) ? "" : oAddr.getLgcOrderNo();
				objData[i][12] = StringUtil.isEmptyString(oAddr.getOrderNote()) ? "" : oAddr.getOrderNote();
			}
			eu.putData(objData);

			//eu.createExcel("card记录");
			//eu.putHead(objHead);
			//eu.putData(objData);

			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			//eu.putData(objData);
			String fileName = "订单地址(" + DateUtil.dateToString(new Date(), "yyyyMMddHHmmss") + ").xls";
			
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "iso8859-1"));
			OutputStream os = response.getOutputStream();
			eu.getFile(os);
			os.close();
		} catch (Exception ex) {
			log.error(null, ex);
		}
	}

	/**
	 * 导入
	 *
	 * @param params
	 * @param file
	 * @param mmp
	 * @return
	 */
	@RequestMapping(value = "/importOrderExcel")
	public void importOrder(@RequestParam Map<String, String> params, @RequestParam("file") MultipartFile file, ModelMap mmp, HttpServletRequest request, HttpServletResponse resp) {
		JSONResult result = new JSONResult();
		Map<String, String> sMap = null;
		try {
			int celSize = 0;
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			Object obj = request.getSession().getAttribute("fileNames");
			if (!StringUtil.isEmptyString(obj)) {
				sMap = (Map<String, String>) obj;
			} else {
				sMap = new HashMap<String, String>();
				//this.orderService.del(user.get("userName"));
			}
			String fileName = file.getOriginalFilename();
			if (sMap.containsKey(fileName)) {
				result.setErrorCode(1);
				result.setMessage("此文件导入过,不能重复导入");
			} else {
				sMap.put(fileName, fileName);
				String last = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
				InputStream is = file.getInputStream();
				List<BatchOrderAddr> list = new ArrayList<BatchOrderAddr>();
				StringBuilder buffer = new StringBuilder();
				if ("xls".equals(last)) {
					ExcelReader excelReader = new ExcelReader();
					String[] title = excelReader.readExcelTitle(is);
					LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig");
					/*	if (gcNo.equals(lgcConfig.getLgcNo())) {
					 celSize = 14;
					 if (!"姓名".equals(title[0].trim()) || !"联系电话".equals(title[1].trim())
					 || !"省".equals(title[2].trim()) || !"市".equals(title[3].trim()) || !"区".equals(title[4].trim())
					 || !"收件地址".equals(title[5].trim()) || !"物品类别".equals(title[6].trim()) || !"物品重量(单位kg)".equals(title[7].trim())
					 || !"付款人".equals(title[8].trim()) || !"付款方式".equals(title[9].trim()) || !"保价金额".equals(title[10].trim())
					 || !"运费".equals(title[11].trim())
					 || !"运单号".equals(title[12].trim())
					 || !"备注".equals(title[13].trim())) {
					 result.setErrorCode(1);
					 result.setMessage("请下载正确的模版，填写正确的数据");
					 outJson(JsonUtil.toJson(result), resp);
					 return;
					 }
					 } else {
					 celSize = 13;
					 if (!"姓名".equals(title[0].trim()) || !"联系电话".equals(title[1].trim())
					 || !"省".equals(title[2].trim()) || !"市".equals(title[3].trim()) || !"区".equals(title[4].trim())
					 || !"收件地址".equals(title[5].trim()) || !"物品类别".equals(title[6].trim()) || !"物品重量(单位kg)".equals(title[7].trim())
					 || !"付款人".equals(title[8].trim()) || !"付款方式".equals(title[9].trim()) || !"保价金额".equals(title[10].trim())
					 || !"运单号".equals(title[11].trim())
					 || !"备注".equals(title[12].trim())) {
					 result.setErrorCode(1);
					 result.setMessage("请下载正确的模版，填写正确的数据");
					 outJson(JsonUtil.toJson(result), resp);
					 return;
					 }
					 }*/
					
					
					celSize = 13;
					if (!"姓名".equals(title[0].trim()) || !"联系电话".equals(title[1].trim())
							|| !"省".equals(title[2].trim()) || !"市".equals(title[3].trim()) || !"区".equals(title[4].trim())
							|| !"收件地址".equals(title[5].trim()) || !"物品类别".equals(title[6].trim()) || !"物品重量(单位kg)".equals(title[7].trim())
							|| !"付款人".equals(title[8].trim()) || !"付款方式".equals(title[9].trim()) || !"保价金额".equals(title[10].trim())
							|| !"运单号".equals(title[11].trim())
							|| !"备注".equals(title[12].trim())) {
						result.setErrorCode(1);
						result.setMessage("请下载正确的模版，填写正确的数据");
						outJson(JsonUtil.toJson(result), resp);
						return;
					}
					
					
					InputStream is1 = file.getInputStream();
					String[][] array = excelReader.readExcelContent(is1);
					Map<String, Object> depM = new HashMap<String, Object>();
					if (array.length > 0) {
						if (array[0].length == celSize) {
							for (int i = 0; i < array.length; i++) {
								String[] t = array[i];
								if (StringUtil.isEmptyString(t[0].trim())
										&& StringUtil.isEmptyString(t[1].trim())
										&& StringUtil.isEmptyString(t[2].trim())) {
									continue;
								}
								
								BatchOrderAddr orderAddr = new BatchOrderAddr();
								orderAddr.setRevName(t[0].trim());
//								if (t[1].trim().length() != 11) {
//									buffer.append("第" + i + "行手机号码：" + t[1] + "错误").append("\n");
//								}
								orderAddr.setRevPhone(t[1].trim());
								StringBuffer buf = new StringBuffer();
								
								if (!StringUtil.isEmptyString(t[2].trim())) {
									buf.append(t[2].trim());
								} else {
									buf.append("无");
								}
								
								if (!StringUtil.isEmptyString(t[3].trim())) {
									buf.append("-").append(t[3].trim());
								} else {
									buf.append("-").append("无");
								}
								
								if (!StringUtil.isEmptyString(t[4].trim())) {
									buf.append("-").append(t[4].trim());
								} else {
									buf.append("-").append("无");
								}
								orderAddr.setRevArea(buf.toString());
								orderAddr.setRevAddr(t[5].trim());
								orderAddr.setItemStatus(t[6].trim());
								orderAddr.setItemWeight(t[7].trim());
								orderAddr.setFreightType(StringUtil.getSelectKeyByString(SelectMap.freightMap, t[8].trim()));
								orderAddr.setPayType(StringUtil.getSelectKeyByString(SelectMap.payMap, t[9].trim()));
								orderAddr.setGoodValuation(t[10].trim());
								/*		if (gcNo.equals(lgcConfig.getLgcNo())) {
								 if (!depM.containsKey(t[12].trim())) {
								 depM.put(t[12].trim(), t[12].trim());
								 } else {
								 buffer.append("导入文件中的运单号:").append(t[12].trim()).append("重复,不能导入");
								 break;
								 }
								 orderAddr.setFreight(t[11].trim());
								 orderAddr.setLgcOrderNo(t[12].trim());
								 orderAddr.setOrderNote(t[13].trim());
								 } else {
								 if (!depM.containsKey(t[11].trim())) {
								 depM.put(t[11].trim(), t[11].trim());
								 } else {
								 buffer.append("导入文件中的运单号:").append(t[11].trim()).append("重复,不能导入");
								 break;
								 }
								 orderAddr.setLgcOrderNo(t[11].trim());
								 orderAddr.setOrderNote(t[12].trim());
								 }*/
								
								
								if (!depM.containsKey(t[11].trim())) {
									depM.put(t[11].trim(), t[11].trim());
								} else {
									buffer.append("导入文件中的运单号:").append(t[11].trim()).append("重复,不能导入");
									break;
								}
								orderAddr.setLgcOrderNo(t[11].trim());
								orderAddr.setOrderNote(t[12].trim());


								//orderAddr.setOrderNote(t[11].trim());
								list.add(orderAddr);
							}
							if (StringUtil.isEmptyString(buffer.toString())) {
								this.batchOrderService.del(bossUser.getRealName());
								this.batchOrderService.addOrderMuchAddrImport(bossUser.getRealName(), list);
								request.getSession().setAttribute("fileNames", sMap);
							} else {
								result.setErrorCode(1);
								result.setMessage(buffer.toString());
							}
						} else {
							result.setErrorCode(1);
							result.setMessage("模版与导入数据不匹配,请下载正确的模版，填写正确的数据");
						}
					}
				} //                else if ("xlsx".equals(last)) {
				//                    ReadXLSXExcel excelReader = new ReadXLSXExcel();
				//                    String[][] array = excelReader.readExcelContent(is);
				//                    System.out.println("获得Excel表格的内容:");
				//                    if (array.length > 0) {
				//                        if (array[0].length == celSize) {
				//                            for (int i = 0; i < array.length; i++) {
				//                                String[] t = array[i];
				//                                if (StringUtil.isEmptyString(t[0].trim())
				//                                        && StringUtil.isEmptyString(t[1].trim())
				//                                        && StringUtil.isEmptyString(t[2].trim())) {
				//                                    continue;
				//                                }
				//                                BatchOrderAddr orderAddr = new BatchOrderAddr();
				//                                orderAddr.setRevName(t[0].trim());
				//
				//                                orderAddr.setRevPhone(t[1].trim());
				//                                StringBuffer buf = new StringBuffer();
				//                                if (!StringUtil.isEmptyString(t[2].trim())) {
				//                                    buf.append(t[2].trim());
				//                                } else {
				//                                    buf.append("无");
				//                                }
				//
				//                                if (!StringUtil.isEmptyString(t[3].trim())) {
				//                                    buf.append("-").append(t[3].trim());
				//                                } else {
				//                                    buf.append("-").append("无");
				//                                }
				//
				//                                if (!StringUtil.isEmptyString(t[4].trim())) {
				//                                    buf.append("-").append(t[4].trim());
				//                                } else {
				//                                    buf.append("-").append("无");
				//                                }
				//                                orderAddr.setRevArea(buf.toString());
				//                                orderAddr.setRevAddr(t[5].trim());
				//                                orderAddr.setItemStatus(t[6]);
				//                                orderAddr.setItemWeight(t[7].trim());
				//                                orderAddr.setFreightType(StringUtil.getSelectKeyByString(SelectMap.dictMap.get("PAYP"), t[8].trim()));
				//                                orderAddr.setPayType(StringUtil.getSelectKeyByString(SelectMap.dictMap.get("PAYTYPE"), t[9].trim()));
				//                                orderAddr.setGoodValuation(t[10].trim());
				//                                if (gcNo.equals(request.getAttribute("lgcNo"))) {
				//                                    orderAddr.setFreight(t[11].trim());
				//                                    orderAddr.setLgcOrderNo(t[12].trim());
				//                                    orderAddr.setOrderNote(t[13].trim());
				//                                } else {
				//                                    orderAddr.setOrderNote(t[11].trim());
				//                                }
				//                                list.add(orderAddr);
				//                            }
				//                            if (StringUtil.isEmptyString(buffer.toString())) {
				//                                this.orderService.addOrderMuchAddrImport(user.get("userName"), params.get("uid"), list);
				//                                request.getSession().setAttribute("fileNames", sMap);
				//                            } else {
				//                                result.setErrorCode(1);
				//                                result.setMessage("填写的手机号码格式不正确,请检查");
				//                            }
				//                        } else {
				//                            result.setErrorCode(1);
				//                            result.setMessage("请下载正确的模版，填写正确的数据");
				//                        }
				//                    }
				//                } 
				else {
					result.setErrorCode(1);
					result.setMessage("只能导入excel文件");
				}
			}
		} catch (Exception ex) {
			log.error(null, ex);
			ex.printStackTrace();
			result.setErrorCode(1);
			result.setMessage("数据异常,请下载正确的模版，填写正确的数据");
		}
		outJson(JsonUtil.toJson(result), resp);
	}

	/**
	 * 历史数据下订单导出
	 *
	 * @param params
	 * @param mmp
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/orderAddExportImport")
	public void orderAddExportImport(@RequestParam Map<String, String> params, ModelMap mmp, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		try {
			int celSize = 0;
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			List<BatchOrderAddr> list = new ArrayList<BatchOrderAddr>();
			list = this.batchOrderService.queryAddrImport(bossUser.getRealName(), params.get("ids"), "");
			LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig");
			ExcelUtil eu = new ExcelUtil();
			eu.createWorkBook();
			eu.createSheet("order");
			//代表亿翔
			//if (gcNo.equals(lgcConfig.getLgcNo())) {
			celSize = 19;
			Object[][] objHead = {{"运单号", "寄件人姓名", "寄件人手机号", "寄件人地址", "寄件时间", "收件人姓名", "收件人手机号", "收件人地址", "运费", "寄件网点", "寄件快递员", "收件网点", "收件快递员", "物品名称", "物品重量(单位kg)", "付款人", "付款方式", "保价金额", "备注"}};
			eu.putHead(objHead);
//			} else {
//				celSize = 13;
//				Object[][] objHead = {{"姓名", "联系电话", "省", "市", "区", "收件地址", "物品类别", "物品重量(单位kg)", "付款人", "付款方式", "保价金额", "运单号", "备注"}};
//				eu.putHead(objHead);
//			}

			
			Object[][] objData = new Object[list.size()][celSize];
			for (int i = 0; i < list.size(); i++) {
				BatchOrderAddr oAddr = list.get(i);
				objData[i][0] = oAddr.getLgcOrderNo();
				objData[i][1] = oAddr.getSendName();
				objData[i][2] = oAddr.getSendPhone();
				objData[i][3] = oAddr.getSendAddr();
				objData[i][4] = oAddr.getSendTime();
				objData[i][5] = oAddr.getRevName();
				objData[i][6] = oAddr.getRevPhone();
				objData[i][7] = oAddr.getRevAddr();
				objData[i][8] = oAddr.getFreight();
				objData[i][9] = oAddr.getSendSubstation();
				
				
				objData[i][10] = oAddr.getSendCourier();
				objData[i][11] = oAddr.getRevSubstation();
				objData[i][12] = oAddr.getRevCourier();
//				if (StringUtil.isEmptyString(oAddr.getRevArea())) {
//					objData[i][2] = "";
//					objData[i][3] = "";
//					objData[i][4] = "";
//				} else {
//					String[] area = oAddr.getRevArea().split("-");
//					objData[i][2] = area[0];
//					if (area.length == 1) {
//						objData[i][3] = "";
//						objData[i][4] = "";
//					}
//					if (area.length > 1 && area.length < 3) {
//						objData[i][3] = area[1];
//						objData[i][4] = "";
//					}
//					if (area.length == 3) {
//						objData[i][3] = area[1];
//						objData[i][4] = area[2];
//					}
				//}

				//objData[i][2] = Toolkit.isEmptyString(oAddr.getRevArea()) ? "" + oAddr.getRevAddr() : oAddr.getRevArea() + oAddr.getRevAddr();

				objData[i][13] = oAddr.getItemStatus();
				objData[i][14] = StringUtil.isEmptyString(oAddr.getItemWeight()) ? "" : oAddr.getItemWeight();
				objData[i][15] = StringUtil.isEmptyString(oAddr.getFreightType()) ? "" : SelectMap.freightMap.get(oAddr.getFreightType());
				objData[i][16] = StringUtil.isEmptyString(oAddr.getPayType()) ? "" : SelectMap.payMap.get(oAddr.getPayType());
				objData[i][17] = StringUtil.isEmptyString(oAddr.getGoodValuation()) ? "0.00" : oAddr.getGoodValuation();
//				if (gcNo.equals(request.getAttribute("lgcNo"))) {
//					objData[i][11] = StringUtil.isEmptyString(oAddr.getLgcOrderNo()) ? "" : oAddr.getLgcOrderNo();
//					objData[i][12] = StringUtil.isEmptyString(oAddr.getOrderNote()) ? "" : oAddr.getOrderNote();
//				} else {
//					objData[i][11] = StringUtil.isEmptyString(oAddr.getLgcOrderNo()) ? "" : oAddr.getLgcOrderNo();
//					objData[i][12] = StringUtil.isEmptyString(oAddr.getOrderNote()) ? "" : oAddr.getOrderNote();
//				}
				objData[i][18] = StringUtil.isEmptyString(oAddr.getOrderNote()) ? "" : oAddr.getOrderNote();
			}
			eu.putData(objData);

			//eu.createExcel("card记录");
			//eu.putHead(objHead);
			//eu.putData(objData);

			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			//eu.putData(objData);
			String fileName = "历史订单数据(" + DateUtil.dateToString(new Date(), "yyyyMMddHHmmss") + ").xls";
			
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "iso8859-1"));
			OutputStream os = response.getOutputStream();
			eu.getFile(os);
			os.close();
		} catch (Exception ex) {
			log.error(null, ex);
		}
	}

	/**
	 * 历史数据导入
	 *
	 * @param params
	 * @param file
	 * @param mmp
	 * @return
	 */
	@RequestMapping(value = "/importOrderExcelHistory")
	public void importOrderExcelHistory(@RequestParam Map<String, String> params, @RequestParam("file") MultipartFile file, ModelMap mmp, HttpServletRequest request, HttpServletResponse resp) {
		JSONResult result = new JSONResult();
		Map<String, String> sMap = null;
		try {
			int celSize = 0;
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			Object obj = request.getSession().getAttribute("importfileNames");
			if (!StringUtil.isEmptyString(obj)) {
				sMap = (Map<String, String>) obj;
			} else {
				sMap = new HashMap<String, String>();
				//this.orderService.del(user.get("userName"));
			}
			String fileName = file.getOriginalFilename();
			if (sMap.containsKey(fileName)) {
				result.setErrorCode(1);
				result.setMessage("此文件导入过,不能重复导入");
			} else {
				sMap.put(fileName, fileName);
				String last = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
				InputStream is = file.getInputStream();
				List<BatchOrderAddr> list = new ArrayList<BatchOrderAddr>();
				//StringBuilder buffer = new StringBuilder();
				if ("xls".equals(last)) {
					ExcelReader excelReader = new ExcelReader();
					String[] title = excelReader.readExcelTitle(is);
					//LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig");
					//if (gcNo.equals(lgcConfig.getLgcNo())) {
					celSize = 19;
					if (!"运单号".equals(title[0].trim()) || !"寄件人姓名".equals(title[1].trim()) || !"寄件人手机号".equals(title[2].trim())
							|| !"寄件人地址".equals(title[3].trim()) || !"寄件时间".equals(title[4].trim()) || !"收件人姓名".equals(title[5].trim())
							//|| !"省".equals(title[2].trim()) || !"市".equals(title[3].trim()) || !"区".equals(title[4].trim())
							|| !"收件人手机号".equals(title[6].trim()) || !"收件人地址".equals(title[7].trim()) || !"运费".equals(title[8].trim())
							|| !"寄件网点".equals(title[9].trim()) || !"寄件快递员".equals(title[10].trim()) || !"收件网点".equals(title[11].trim())
							|| !"收件快递员".equals(title[12].trim()) || !"物品名称".equals(title[13].trim()) || !"物品重量(单位kg)".equals(title[14].trim())
							|| !"付款人".equals(title[15].trim()) || !"付款方式".equals(title[16].trim()) || !"保价金额".equals(title[17].trim())
							|| !"备注".equals(title[18].trim())) {
						result.setErrorCode(1);
						result.setMessage("请下载正确的模版，填写正确的数据");
						outJson(JsonUtil.toJson(result), resp);
						return;
					}
//					} else {
//						celSize = 13;
//						if (!"姓名".equals(title[0].trim()) || !"联系电话".equals(title[1].trim())
//								|| !"省".equals(title[2].trim()) || !"市".equals(title[3].trim()) || !"区".equals(title[4].trim())
//								|| !"收件地址".equals(title[5].trim()) || !"物品类别".equals(title[6].trim()) || !"物品重量(单位kg)".equals(title[7].trim())
//								|| !"付款人".equals(title[8].trim()) || !"付款方式".equals(title[9].trim()) || !"保价金额".equals(title[10].trim())
//								|| !"运单号".equals(title[11].trim())
//								|| !"备注".equals(title[12].trim())) {
//							result.setErrorCode(1);
//							result.setMessage("请下载正确的模版，填写正确的数据");
//							outJson(JsonUtil.toJson(result), resp);
//							return;
//						}
//					}
					InputStream is1 = file.getInputStream();
					String[][] array = excelReader.readExcelContent(is1);
					if (array.length > 0) {
						if (array[0].length == celSize) {
							for (int i = 0; i < array.length; i++) {
								String[] t = array[i];
								if (StringUtil.isEmptyString(t[0].trim())
										&& StringUtil.isEmptyString(t[1].trim())
										&& StringUtil.isEmptyString(t[2].trim())) {
									continue;
								}
								BatchOrderAddr orderAddr = new BatchOrderAddr();
								orderAddr.setLgcOrderNo(t[0].trim());
								orderAddr.setSendName(t[1].trim());
								orderAddr.setSendPhone(t[2].trim());
								orderAddr.setSendArea("无-无-无");
								orderAddr.setSendAddr(t[3].trim());
								orderAddr.setSendTime(t[4].trim());
								orderAddr.setRevName(t[5].trim());
//								if (t[1].trim().length() != 11) {
//									buffer.append("第" + i + "行手机号码：" + t[1] + "错误").append("\n");
//								}
								orderAddr.setRevPhone(t[6].trim());
//								StringBuffer buf = new StringBuffer();
//
//								if (!StringUtil.isEmptyString(t[2].trim())) {
//									buf.append(t[2].trim());
//								} else {
//									buf.append("无");
//								}
//
//								if (!StringUtil.isEmptyString(t[3].trim())) {
//									buf.append("-").append(t[3].trim());
//								} else {
//									buf.append("-").append("无");
//								}
//
//								if (!StringUtil.isEmptyString(t[4].trim())) {
//									buf.append("-").append(t[4].trim());
//								} else {
//									buf.append("-").append("无");
//								}
								orderAddr.setRevArea("无-无-无");
								orderAddr.setRevAddr(t[7].trim());
								orderAddr.setFreight(t[8].trim());
								orderAddr.setSendSubstation(t[9].trim());
								orderAddr.setSendCourier(t[10].trim());
								orderAddr.setRevSubstation(t[11].trim());
								orderAddr.setRevCourier(t[12].trim());
								orderAddr.setItemStatus(t[13].trim());
								orderAddr.setItemWeight(t[14].trim());
								orderAddr.setFreightType(StringUtil.getSelectKeyByString(SelectMap.freightMap, t[15].trim()));
								orderAddr.setPayType(StringUtil.getSelectKeyByString(SelectMap.payMap, t[16].trim()));
								orderAddr.setGoodValuation(t[17].trim());
								orderAddr.setOrderNote(t[18].trim());
								//orderAddr.setOrderNote(t[11].trim());
								list.add(orderAddr);
							}
							this.batchOrderService.addOrderBatchImport(bossUser.getRealName(), list);
							request.getSession().setAttribute("importfileNames", sMap);
							
						} else {
							result.setErrorCode(1);
							result.setMessage("请下载正确的模版，填写正确的数据");
						}
					}
				} //                else if ("xlsx".equals(last)) {
				//                    ReadXLSXExcel excelReader = new ReadXLSXExcel();
				//                    String[][] array = excelReader.readExcelContent(is);
				//                    System.out.println("获得Excel表格的内容:");
				//                    if (array.length > 0) {
				//                        if (array[0].length == celSize) {
				//                            for (int i = 0; i < array.length; i++) {
				//                                String[] t = array[i];
				//                                if (StringUtil.isEmptyString(t[0].trim())
				//                                        && StringUtil.isEmptyString(t[1].trim())
				//                                        && StringUtil.isEmptyString(t[2].trim())) {
				//                                    continue;
				//                                }
				//                                BatchOrderAddr orderAddr = new BatchOrderAddr();
				//                                orderAddr.setRevName(t[0].trim());
				//
				//                                orderAddr.setRevPhone(t[1].trim());
				//                                StringBuffer buf = new StringBuffer();
				//                                if (!StringUtil.isEmptyString(t[2].trim())) {
				//                                    buf.append(t[2].trim());
				//                                } else {
				//                                    buf.append("无");
				//                                }
				//
				//                                if (!StringUtil.isEmptyString(t[3].trim())) {
				//                                    buf.append("-").append(t[3].trim());
				//                                } else {
				//                                    buf.append("-").append("无");
				//                                }
				//
				//                                if (!StringUtil.isEmptyString(t[4].trim())) {
				//                                    buf.append("-").append(t[4].trim());
				//                                } else {
				//                                    buf.append("-").append("无");
				//                                }
				//                                orderAddr.setRevArea(buf.toString());
				//                                orderAddr.setRevAddr(t[5].trim());
				//                                orderAddr.setItemStatus(t[6]);
				//                                orderAddr.setItemWeight(t[7].trim());
				//                                orderAddr.setFreightType(StringUtil.getSelectKeyByString(SelectMap.dictMap.get("PAYP"), t[8].trim()));
				//                                orderAddr.setPayType(StringUtil.getSelectKeyByString(SelectMap.dictMap.get("PAYTYPE"), t[9].trim()));
				//                                orderAddr.setGoodValuation(t[10].trim());
				//                                if (gcNo.equals(request.getAttribute("lgcNo"))) {
				//                                    orderAddr.setFreight(t[11].trim());
				//                                    orderAddr.setLgcOrderNo(t[12].trim());
				//                                    orderAddr.setOrderNote(t[13].trim());
				//                                } else {
				//                                    orderAddr.setOrderNote(t[11].trim());
				//                                }
				//                                list.add(orderAddr);
				//                            }
				//                            if (StringUtil.isEmptyString(buffer.toString())) {
				//                                this.orderService.addOrderMuchAddrImport(user.get("userName"), params.get("uid"), list);
				//                                request.getSession().setAttribute("fileNames", sMap);
				//                            } else {
				//                                result.setErrorCode(1);
				//                                result.setMessage("填写的手机号码格式不正确,请检查");
				//                            }
				//                        } else {
				//                            result.setErrorCode(1);
				//                            result.setMessage("请下载正确的模版，填写正确的数据");
				//                        }
				//                    }
				//                } 
				else {
					result.setErrorCode(1);
					result.setMessage("只能导入excel文件");
				}
			}
		} catch (Exception ex) {
			log.error(null, ex);
			ex.printStackTrace();
			result.setErrorCode(1);
			result.setMessage("请下载正确的模版，填写正确的数据");
		}
		outJson(JsonUtil.toJson(result), resp);
	}

	/**
	 * 删除订单地址
	 *
	 * @param params
	 * @param mmp
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delOrderAddr")
	public Object delOrderAddr(@RequestParam Map<String, String> params, ModelMap mmp, HttpServletRequest request) {
		String paramstr = "";
		try {
			Object obj = request.getSession().getAttribute("addrStr");
			mmp.addAttribute("params", params);
			if (!StringUtil.isEmptyString(obj)) {
				paramstr = (String) obj;
			}
			this.batchOrderService.delOrderAddr(params.get("ids"));
		} catch (Exception e) {
			log.error(null, e);
		}
		return "redirect:selectOrderAddr?layout=no&" + paramstr;
	}

	/**
	 * 删除历史数据订单
	 *
	 * @param params
	 * @param mmp
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delOrderAddrImport")
	public Object delOrderAddrImport(@RequestParam Map<String, String> params, ModelMap mmp, HttpServletRequest request) {
		String paramstr = "";
		try {
			Object obj = request.getSession().getAttribute("addrStrh");
			mmp.addAttribute("params", params);
			if (!StringUtil.isEmptyString(obj)) {
				paramstr = (String) obj;
			}
			this.batchOrderService.delOrderAddrImport(params.get("ids"));
		} catch (Exception e) {
			log.error(null, e);
		}
		return "redirect:queryOrderAddrImport?layout=no&" + paramstr;
	}
	
	private float getPayByRate(float value, Map<String, Object> rateMap) {
		String rateType = rateMap.get("rateType").toString();//费率类型
		float rate = Float.parseFloat(rateMap.get("rate").toString());// 费率
		float minv = Float.parseFloat(rateMap.get("minv").toString());//收取起点
		float maxv = Float.parseFloat(rateMap.get("maxv").toString());//封顶费用
		float ret = 0;
		if ("LR".equals(rateType)) {
			if (value >= minv) {
				ret = value * rate;
			}
		} else if ("LTR".equals(rateType)) {
			if (value >= minv) {
				ret = value * rate;
			}
			if (ret >= maxv) {
				ret = maxv;
			}
		}
		return ret / 1000f;
	}
}
