package com.yogapay.boss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.LgcCustomer;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.enums.Check;
import com.yogapay.boss.service.AsignOrderService;
import com.yogapay.boss.service.CacheService;
import com.yogapay.boss.service.CourierCostRecordService;
import com.yogapay.boss.service.CourierCostService;
import com.yogapay.boss.service.CourierDaySettleService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.ForOrderService;
import com.yogapay.boss.service.FranOrderService;
import com.yogapay.boss.service.FranchiseRuleService;
import com.yogapay.boss.service.LgcCustomerService;
import com.yogapay.boss.service.ManagerService;
import com.yogapay.boss.service.MonitorService;
import com.yogapay.boss.service.MonthUserCountService;
import com.yogapay.boss.service.MonthUserSettleService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.service.ProOrderService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.WagesService;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

/**
 * 
 * @author 005
 *
 */
@Controller
@RequestMapping(value = "/export")
public class ExportController extends BaseController {
	
	     @Resource
	     private  MonthUserCountService monthUserCountService ;
	     @Resource
	     private CourierService courierService ;
	     @Resource
	     private SubstationService substationService ;    
	     @Resource
	     private OrderService orderService ;
	     @Resource
	     private OrderTrackService orderTrackService ;
	     @Resource
	     private ManagerService managerService ;
	     @Resource
	     private CourierDaySettleService courierDaySettleService ;
	     @Resource
	     private ForOrderService forOrderService ;
	     @Resource
	     private MonthUserSettleService monthUserSettleService ;
	     @Resource
			private WagesService wagesService ;	
	     @Resource
	     private CourierCostService courierCostService ;
	     @Resource
	     private CourierCostRecordService courierCostRecordService ;
	     @Resource
	     private FranchiseRuleService franchiseRuleService ;
	     @Resource
	     private FranOrderService franOrderService ;
	     @Resource
	     private CacheService cacheService ;
	     @Resource
	     private ProOrderService proOrderService ;
	     @Resource
	     private AsignOrderService asignOrderService ;
	     @Resource
	     private MonitorService monitorService ;
	     @Resource
	     private LgcCustomerService lgcCustomerService ;
	     
	     
			//导出方法
			@RequestMapping(value = { "/service" })
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
					if(!StringUtils. isEmpty(params.get("serviceName"))){
		                if("monthClist".equals(params.get("serviceName"))){
		                	 String fileName = "月结客户每月发货统计-" + sdf.format(new Date()) + ".xls";
		                     response.setHeader("Content-disposition", "attachment;filename="
		                             + new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                     monthClist(os, params, fileName);
		                }
		                
		                if("monthMmlist".equals(params.get("serviceName"))){
		                	 String fileName = "网点收派环比表-" + sdf.format(new Date()) + ".xls";
		                     response.setHeader("Content-disposition", "attachment;filename="
		                             + new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                     monthMmlist(os, params, fileName);
		                }
		                
		                
		                if("monthCmlist".equals(params.get("serviceName"))){
		                	 String fileName = "快递员收派环比表-" + sdf.format(new Date()) + ".xls";
		                     response.setHeader("Content-disposition", "attachment;filename="
		                             + new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                     monthCmlist(os, params, fileName);
		                }
		                if("signScan".equals(params.get("serviceName"))){
		                	 String fileName = "有派件未签收对比表-" + sdf.format(new Date()) + ".xls";
		                     response.setHeader("Content-disposition", "attachment;filename="
		                             + new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                     signScan(os, params, fileName);
		                }
		                
		                if("takeExport".equals(params.get("serviceName"))){
		                	String fileName = "寄件单列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	takeExport(os, params, fileName);
		                }
		                if("sendExport".equals(params.get("serviceName"))){
		                	String fileName = "签收单列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	sendExport(os, params, fileName);
		                }
		                
		                if("courierSettleExport".equals(params.get("serviceName"))){
		                	String fileName = "快递员账单列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	courierSettleExport(os, params, fileName);
		                }
		                
		                if("monthSettleExport".equals(params.get("serviceName"))){
		                	String fileName = "月结账单列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	monthSettleExport(os, params, fileName);
		                }
		                
		                if("forOrderExport".equals(params.get("serviceName"))){
		                	String fileName = "转件清单列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	forOrderExport(os, params, fileName);
		                }
		                
		                if("wagesList".equals(params.get("serviceName"))){
		                	String fileName = "快递员工资表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	wagesExport(os, params, fileName);
		                }
		                
		                if("wagesIlist".equals(params.get("serviceName"))){
		                	String fileName = "费用录入流水-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	wages_ilistExport(os, params, fileName);
		                }
		                
		                if("wagesMlist".equals(params.get("serviceName"))){
		                	String fileName = "费用管理-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	wages_mlistExport(os, params, fileName);
		                }
		                
		                if("wagesRlist".equals(params.get("serviceName"))){
		                	String fileName = "费用管理流水-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	wages_rlistExport(os, params, fileName);
		                }
		                if("jmrule".equals(params.get("serviceName"))){
		                	String fileName = "加盟报价规则-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	jmrule(os, params, fileName);
		                }
		                if("jmzlist".equals(params.get("serviceName"))){
		                	String fileName = "中心加盟对账-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	jmzlist(os, params, fileName);
		                }
		                
		                if("jmslist".equals(params.get("serviceName"))){
		                	String fileName = "中心派件对账-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	jmslist(os, params, fileName);
		                }
		                
		                if("jmsubzlist".equals(params.get("serviceName"))){
		                	String fileName = "网点中转费查询-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	jmsubzlist(os, params, fileName);
		                }
		                
		                if("jmsubslist".equals(params.get("serviceName"))){
		                	String fileName = "网点派件费查询-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	jmsubslist(os, params, fileName);
		                }
		                
		                if("porderlist".equals(params.get("serviceName"))){
		                	String fileName = "问题件列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	porderlist(os, params, fileName);
		                }
		                
		                if("monitor_asign".equals(params.get("serviceName"))){
		                	String fileName = "分配监控列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	monitor_asign(os, params, fileName);
		                }
		                
		                if("monitor_asign_detail".equals(params.get("serviceName"))){
		                	String fileName = "分配明细列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	 monitor_asign_detail(os, params, fileName);
		                }
		                if("monitor_take".equals(params.get("serviceName"))){
		                	String fileName = "收件监控列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	monitor_take(os, params, fileName);
		                }
		                if("monitor_take_detail".equals(params.get("serviceName"))){
		                	String fileName = "收件分配明细列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	monitor_take_detail(os, params, fileName);
		                }
		                if("monitor_inmonitor".equals(params.get("serviceName"))){
		                	String fileName = "入仓监控列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	monitor_inmonitor(os, params, fileName);
		                }
		                
		                if("monitor_outmonitor".equals(params.get("serviceName"))){
		                	String fileName = "出仓监控列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	monitor_outmonitor(os, params, fileName);
		                }
		                if("customer_list".equals(params.get("serviceName"))){
		                	String fileName = "客户列表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	customer_list(os, params, fileName);
		                }
		                if("customer_huifang".equals(params.get("serviceName"))){
		                	String fileName = "客户回访-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	customer_huifang(os, params, fileName);
		                }
		                if("customer_report".equals(params.get("serviceName"))){
		                	String fileName = "客户报表-" + sdf.format(new Date()) + ".xls";
		                	response.setHeader("Content-disposition", "attachment;filename="
		                			+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		                	customer_report(os, params, fileName);
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
	     
			public void monthClist(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
				params.put("limit", "20000");
				String substationNo = params.get("substationNo") ;
			    String monthNo = params.get("monthNo") ;
			    if (!StringUtils.isEmptyWithTrim(params.get("substationNo"))&&monthNo.contains(")")) {
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
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
						 substationNo1 = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo1 = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo1);
				}
				List<Map<String, Object>> orderList = monthUserCountService.list(params);
				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/monthClist.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = orderList.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
					Substation substation = substationService.getSubstationByNo(StringUtils.nullString(map.get("substation_no"))) ;
					if (substation!=null) {
						ws.addCell(new Label(col++, row,  StringUtils.nullString(substation.getSubstationName())));
					}else {
						ws.addCell(new Label(col++, row,""));
					}
					Courier courier = courierService.getCourierByNo(StringUtils.nullString(map.get("courier_no"))) ;
					if (courier!=null) {
						ws.addCell(new Label(col++, row,  StringUtils.nullString(courier.getRealName())));
					}else {
						ws.addCell(new Label(col++, row,""));
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("month_settle_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("month_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m1"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m2"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m3"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m4"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m5"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m6"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m7"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m8"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m9"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m10"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m11"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("m12"))));
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}
			
			public void monthMmlist(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
				params.put("limit", "20000");
				
				String substationNo = params.get("substationNo") ;
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				if(!StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					if (substationNo.contains(")")) {
						params.put("substationNo", substationNo.substring(0,substationNo.indexOf("("))) ;
					}
				}else {
					String substationNo1 ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
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
				
				List<Map<String, Object>> orderList = monthUserCountService.mmlist(params);
				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/monthMmlist.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = orderList.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("substation_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("substation_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("s1"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("s2"))));
					String s1_s2 = "100" ;
					double s1 = Integer.valueOf( StringUtils.nullString(map.get("s1"))) ;
					double s2 = Integer.valueOf( StringUtils.nullString(map.get("s2"))) ;
					if (s1>0) {
						 s1_s2 = String.valueOf(Math.round((s2-s1)/s1*10000)/100.0);
					}
					ws.addCell(new Label(col++, row,  s1_s2+"%"));
					
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("r1"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("r2"))));
					String r1_r2 = "100" ;
					double r1 = Integer.valueOf( StringUtils.nullString(map.get("r1"))) ;
					double r2 = Integer.valueOf( StringUtils.nullString(map.get("r2"))) ;
					if (r1>0) {
						 r1_r2 = String.valueOf(Math.round((r2-r1)/r1*10000)/100.0);
					}
					ws.addCell(new Label(col++, row,  r1_r2+"%"));
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}		
			
			
			 // 寄件单审核导出
			public void takeExport(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
				DisplayFormat displayFormat = NumberFormats.TEXT;
				WritableCellFormat format = new WritableCellFormat(wf,displayFormat);
				format.setAlignment(jxl.format.Alignment.LEFT);
				format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.NONE); 
				
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
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
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
				
				List<Map<String, Object>> orderList = orderService.getList(pMap);
				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/exaExport.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = orderList.iterator();
				while (it.hasNext()) {	
					Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row, "PASS".equals((StringUtils.nullString(map.get("examine_status"))))?"已审核":"未审核"));//审核
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgc_order_no"))));//运单编号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("monthSname"))));//寄件客户
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("month_settle_no"))));//月结帐号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("create_time"))));//寄件日期
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeSubstationName"))));//寄件网点
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendSubstationName"))));//目的地
				jxl.write.Number item_weight = new jxl.write.Number(col++, row, Double.parseDouble(StringUtils.nullString(map.get("item_weight"))),format);
				ws.addCell(item_weight);//重量
				jxl.write.Number good_price = new jxl.write.Number(col++, row, Double.parseDouble(StringUtils.nullString(map.get("good_price"))),format);
				ws.addCell(good_price);//货款
				jxl.write.Number good_valuation = new jxl.write.Number(col++, row, Double.parseDouble(StringUtils.nullString(map.get("good_valuation"))),format);
				ws.addCell(good_valuation);//保价费
				jxl.write.Number freight = new jxl.write.Number(col++, row, Double.parseDouble(StringUtils.nullString(map.get("freight"))),format);
				ws.addCell(freight);//运费
				jxl.write.Number vpay = new jxl.write.Number(col++, row, Double.parseDouble(StringUtils.nullString(map.get("vpay"))),format);
				ws.addCell(vpay);//附加费
				ws.addCell(new Label(col++, row, "1".equals(StringUtils.nullString(map.get("freight_type")))?"寄方付":"收方付"));//支付人
				ws.addCell(new Label(col++, row, Check.getIndex(StringUtils.nullString(map.get("pay_type")))));//支付方式+
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeCourierName"))));//取件员
				ws.addCell(new Label(col++, row, "0.00"));//取件提成
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_count"))));//件数
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_volume"))));//体积
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_card_no"))));//货款帐号				
				ws.addCell(new Label(col++, row, "0.00"));//货款退款			
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("order_note"))));//备注		
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_name"))));//寄件人	
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_phone"))));//寄件电话
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_area")).concat(StringUtils.nullString(map.get("send_addr")))));//寄件地址
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_name"))));//收件人	
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_name"))));//收件人	
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_phone"))));//收件人	电话
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_area")).concat(StringUtils.nullString(map.get("rev_addr")))));//收件人地址
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rece_no"))));//回单号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("for_no"))));//转出单号
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendSubstationName"))));//目的网点
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendCourierName"))));//派件员
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendSubstationName"))));//派件网点
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendSubstationName"))));//派件网点
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_order_time"))));//签收时间
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sign_name"))));//签收人
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeSubstationName"))));//录入网点
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("inputer"))));//录单员
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("input_time"))));//录单时间
				ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("examine_time"))));//审核时间
				ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("examiner"))));//审核人
				ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("takeSubstationName"))));//审核网点
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("descb"))));//问题说明
				ws.addCell(new Label(col++, row, ""));//短信
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("order_no"))));//订单号
			
				row++;
				col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}		
		
			// 签收单审核导出
			public void sendExport(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
				DisplayFormat displayFormat = NumberFormats.TEXT;
				WritableCellFormat format = new WritableCellFormat(wf,displayFormat);
				format.setAlignment(jxl.format.Alignment.LEFT);
				format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.NONE); 
				
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
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo);
				}
				
				params.put("createTimeEnd", endTime+" 23:59:59") ;
				params.put("zid", "1") ;   //去除子单
		
				List<Map<String, Object>> orderList = orderService.sendOrderList(params);
				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/exaExport.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = orderList.iterator();
				while (it.hasNext()) {	
					Map<String, Object> map = it.next();
					ws.addCell(new Label(col++, row, "PASS".equals((StringUtils.nullString(map.get("sign_examine_status"))))?"已审核":"未审核"));//审核
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgc_order_no"))));//运单编号
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("monthSname"))));//寄件客户
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("month_settle_no"))));//月结帐号
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("create_time"))));//寄件日期
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeSubstationName"))));//寄件网点
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendSubstationName"))));//目的地
					jxl.write.Number item_weight = new jxl.write.Number(col++, row, Double.parseDouble(StringUtils.nullString(map.get("item_weight"))),format);
					ws.addCell(item_weight);//重量
					jxl.write.Number good_price = new jxl.write.Number(col++, row, Double.parseDouble(StringUtils.nullString(map.get("good_price"))),format);
					ws.addCell(good_price);//货款
					jxl.write.Number good_valuation = new jxl.write.Number(col++, row, Double.parseDouble(StringUtils.nullString(map.get("good_valuation"))),format);
					ws.addCell(good_valuation);//保价费
					jxl.write.Number freight = new jxl.write.Number(col++, row, Double.parseDouble(StringUtils.nullString(map.get("freight"))),format);
					ws.addCell(freight);//运费
					jxl.write.Number vpay = new jxl.write.Number(col++, row, Double.parseDouble(StringUtils.nullString(map.get("vpay"))),format);
					ws.addCell(vpay);//附加费
					ws.addCell(new Label(col++, row, "1".equals(StringUtils.nullString(map.get("freight_type")))?"寄方付":"收方付"));//支付方式
					ws.addCell(new Label(col++, row, Check.getIndex(StringUtils.nullString(map.get("pay_type")))));//支付方式+
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeCourierName"))));//取件员
					ws.addCell(new Label(col++, row, "0.00"));//取件提成
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_count"))));//件数
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_volume"))));//体积
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_card_no"))));//货款帐号				
					ws.addCell(new Label(col++, row, "0.00"));//货款退款			
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("order_note"))));//备注		
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_name"))));//寄件人	
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_phone"))));//寄件电话
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_area")).concat(StringUtils.nullString(map.get("send_addr")))));//寄件地址
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_name"))));//收件人	
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_name"))));//收件人	
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_phone"))));//收件人	电话
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rev_area")).concat(StringUtils.nullString(map.get("rev_addr")))));//收件人地址
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rece_no"))));//回单号
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("for_no"))));//转出单号
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendSubstationName"))));//目的网点
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendCourierName"))));//目的网点
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendSubstationName"))));//派件网点
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendSubstationName"))));//派件网点
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_order_time"))));//签收时间
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sign_name"))));//签收人
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeSubstationName"))));//录入网点
					ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("sinputor"))));//录单员
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sinput_time"))));//单时间录
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("sign_examine_time"))));//审核时间
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("sign_examiner"))));//审核人
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("sendSubstationName"))));//审核网点
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("descb"))));//问题说明
					ws.addCell(new Label(col++, row, ""));//短信
					ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("order_no"))));//订单号
					
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}		

			

			 // 用于快递员收派环比表
			public void monthCmlist(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
				params.put("limit", "20000");
				
				
				String substationNo = params.get("substationNo") ;
				
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				if(!StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					if (substationNo.contains(")")) {
						params.put("substationNo", substationNo.substring(0,substationNo.indexOf("("))) ;
					}
				}else {
					String substationNo1 ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
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
					
				List<Map<String, Object>> orderList = monthUserCountService.cmlist(params);
				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/monthCmlist.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = orderList.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("substation_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("substation_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("real_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("s1"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("s2"))));
					String s1_s2 = "100" ;
					double s1 = Integer.valueOf( StringUtils.nullString(map.get("s1"))) ;
					double s2 = Integer.valueOf( StringUtils.nullString(map.get("s2"))) ;
					if (s1>0) {
						 s1_s2 = String.valueOf(Math.round((s2-s1)/s1*10000)/100.0);
					}
					ws.addCell(new Label(col++, row,  s1_s2+"%"));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("r1"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("r2"))));
					String r1_r2 = "100" ;
					double r1 = Integer.valueOf( StringUtils.nullString(map.get("r1"))) ;
					double r2 = Integer.valueOf( StringUtils.nullString(map.get("r2"))) ;
					if (r1>0) {
						 r1_r2 = String.valueOf(Math.round((r2-r1)/r1*10000)/100.0);
					}
					ws.addCell(new Label(col++, row,  r1_r2+"%"));
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}	
			
			 // 用于有派件未签收对比表
			public void signScan(OutputStream os, Map<String, String> params,String fileName) throws Exception {
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
				Date nowDate = new Date() ;
				String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
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
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo);
				}
				List<Map<String, Object>> orderList =null ;
				if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
					orderList = orderTrackService.getSignScan(params) ;
				}else {
					params.put("ff", "1") ;
				}
				if (orderList==null) {
					orderList = new ArrayList<Map<String,Object>>() ;
				}
				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/signScan.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = orderList.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("order_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lgc_order_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("substation_name"))));
					ws.addCell(new Label(col++, row,  "派件"));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("real_name"))));
					if ("3".equals(StringUtils.nullString(map.get("status")))) {
						ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_order_time"))));
					}else {
						ws.addCell(new Label(col++, row,  ""));
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("sign_name"))));
					
					if ("2".equals(StringUtils.nullString(map.get("freight_type")))) {
						ws.addCell(new Label(col++, row,  "到方付"));
					}else {
						ws.addCell(new Label(col++, row,  "收方付"));
					}
					Map<String, Object> payType = managerService.getPayType(StringUtils.nullString(map.get("pay_type"))) ;
					if (payType!=null) {
						ws.addCell(new Label(col++, row,  StringUtils.nullString(payType.get("pay_name"))));
					}else {
						ws.addCell(new Label(col++, row,  ""));
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("freight"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("good_price"))));
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}	
			
			//账单导出
			public void courierSettleExport(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
				Date nowDate = new Date() ;
				String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
				String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
				if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
					params.put("createTimeBegin", beginTime) ;
				}
				if (StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
					params.put("createTimeEnd", endTime) ;
				}
				
				String settleTimeEnd = params.get("settleTimeEnd") ;
				if (!StringUtils.isEmptyWithTrim(settleTimeEnd)) {
					params.put("settleTimeEnd", settleTimeEnd+" 23:59:59") ;
				}
				
				if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					String substationNo ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo);
				}
				List<Map<String, Object>> orderList = courierDaySettleService.list(params) ;
				

				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/settlelist.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = orderList.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
					
					if (StringUtils.nullString(map.get("examine_status")).equals("1")) {
						ws.addCell(new Label(col++, row, "已审核"));
					}else {
						ws.addCell(new Label(col++, row,  "未审核"));
					}
					if (StringUtils.nullString(map.get("settle_status")).equals("1")) {
						ws.addCell(new Label(col++, row, "已收款"));
					}else {
						ws.addCell(new Label(col++, row,  "未收款"));
					}
					
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_date"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("sno"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cno"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("month_count"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("good_count"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cash_count"))));
					
					BigDecimal c1 = new BigDecimal(StringUtils.nullString(map.get("cash_count"))) ;
					BigDecimal c2 = new BigDecimal(StringUtils.nullString(map.get("good_count"))) ;
					BigDecimal c3 = c1.add(c2) ;
					
					ws.addCell(new Label(col++, row,  StringUtils.nullString(c3)));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_count"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("note"))));
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}
			
			
			//月结账单导出
			public void monthSettleExport(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
				Date nowDate = new Date() ;
				
				String beginTime = params.get("createTimeBegin") ; 
				String endTime = params.get("createTimeEnd") ;
				if (StringUtils.isEmptyWithTrim(beginTime)) {
					beginTime = DateUtils.formatDate(nowDate,"yyyy-MM") ;
				}
				if (StringUtils.isEmptyWithTrim(endTime)) {
					endTime = DateUtils.formatDate(nowDate,"yyyy-MM") ;
				}
				params.put("createTimeBegin", beginTime+"-01") ;
				params.put("createTimeEnd", endTime+"-01") ;
				
				if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					String substationNo ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo);
				}
				List<Map<String, Object>>  orderList = monthUserSettleService.list(params) ;
				

				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/monthSettleList.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = orderList.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
					
					if (StringUtils.nullString(map.get("examine_status")).equals("1")) {
						ws.addCell(new Label(col++, row, "已审核"));
					}else {
						ws.addCell(new Label(col++, row,  "未审核"));
					}
					if (StringUtils.nullString(map.get("settle_status")).equals("1")) {
						ws.addCell(new Label(col++, row, "已收款"));
					}else {
						ws.addCell(new Label(col++, row,  "未收款"));
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settleMonth"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("month_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("month_sname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("sno"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cno"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("sum_count"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("month_count"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_count"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("note"))));
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}		
			
			
			
			//转件清单导出
			public void forOrderExport(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
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
				List<Map<String, Object>> orderList = forOrderService.list(params) ;

				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/forOrder.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = orderList.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
		               
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lgc_order_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("io_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("io_lgc_order_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("scan_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("forward_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("create_time"))));
					
					Substation substation =  substationService.getSubstationByNo(StringUtils.nullString(map.get("send_substation_no"))) ;
					if (substation!=null) {
						ws.addCell(new Label(col++, row,  StringUtils.nullString(substation.getSubstationName())));
					}else {
						ws.addCell(new Label(col++, row,  ""));
					}
					if (!"2".equals(StringUtils.nullString(map.get("freight_type")))) {
						ws.addCell(new Label(col++, row,  "收方付"));
					}else {
						ws.addCell(new Label(col++, row,  "寄方付"));
					}
					Map<String, Object> payType = managerService.getPayType(StringUtils.nullString(map.get("pay_type"))) ;
					if (payType!=null) {
						ws.addCell(new Label(col++, row,  StringUtils.nullString(payType.get("pay_name"))));
					}else {
						ws.addCell(new Label(col++, row,  ""));
					}
					
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("freight"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("vpay"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("good_price"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("item_count"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("item_weight"))));
					
					Courier courier = courierService.getCourierByNo(StringUtils.nullString(map.get("take_courier_no"))) ;
					if (courier!=null) {
						ws.addCell(new Label(col++, row,  StringUtils.nullString(courier.getRealName())));
					}else {
						ws.addCell(new Label(col++, row,  ""));
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("sign_name"))));
					
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}	
			
			
			//工资导出
			public void wagesExport(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
				Date nowDate = new Date() ;
				if (StringUtils.isEmptyWithTrim(params.get("dateMonth"))) {
					params.put("dateMonth", DateUtils.formatDate(nowDate, "yyyy-MM")) ;
				}
				
				String sno = params.get("substationNo");
				if (StringUtils.nullString(sno).contains("(")) {
					sno = sno.substring(0, sno.indexOf("("));
				}
				params.put("sno", sno);
				
				String cno =  params.get("courierNo");
				if (StringUtils.nullString(cno).contains("(")) {
					cno = cno.substring(0, cno.indexOf("("));
				}
				params.put("cno", cno);
				
				List<Map<String, Object>> list =  wagesService.list(params) ;
				List<Map<String, Object>> nameList =  wagesService.listCostName(new HashMap<String, String>()) ;
				

				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/wages.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				//垂直居中
				WritableCellFormat cellFormat=new WritableCellFormat();
		        cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		        cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				
				/*ws.addCell(new Label(col++, 0,  "账单时间"));
				ws.addCell(new Label(col++, 0,  "网点"));
				ws.addCell(new Label(col++, 0,  "快递员编号"));
				ws.addCell(new Label(col++, 0,  "快递员"));
				ws.addCell(new Label(col++, 0,  "名称"));
				ws.addCell(new Label(col++, 0,  "同城收件"));
				ws.addCell(new Label(col++, 0,  "同城派件"));
				ws.addCell(new Label(col++, 0,  "外件"));*/
		        
		        col=11 ;

				for (Map<String, Object>map:nameList) {
					ws.addCell(new Label(col++, 0,  StringUtils.nullString(map.get("cn_name"))));
				}
				ws.addCell(new Label(col++, 0,  "应发工资"));
				
				col=0 ;
				
				Iterator<Map<String, Object>> it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
		            
					Label label1 = new Label(col++, row,  StringUtils.nullString(map.get("ccmonth"))) ;
					label1.setCellFormat(cellFormat);
					ws.addCell(label1);
					ws.mergeCells(col-1,row, col-1, row+3);
					
					Label label2 = new Label(col++, row,  StringUtils.nullString(map.get("substation_name"))) ;
					label2.setCellFormat(cellFormat);
					ws.addCell(label2);
					ws.mergeCells(col-1,row, col-1, row+3);
					
					Label label3 = new Label(col++, row,  StringUtils.nullString(map.get("inner_no"))) ;
				    label3.setCellFormat(cellFormat);
					ws.addCell(label3);
					ws.mergeCells(col-1,row, col-1, row+3);
					
					Label label4 = new Label(col++, row,  StringUtils.nullString(map.get("real_name"))) ;
					label4.setCellFormat(cellFormat);
					ws.addCell(label4);
					ws.mergeCells(col-1,row, col-1, row+3);
					
					
					ws.addCell(new Label(col++, row, "总计"));
					
					Label label5 = new Label(col++, row,  StringUtils.nullString(map.get("take_sum"))) ;
					label5.setCellFormat(cellFormat);
					ws.addCell(label5);
					ws.addCell(new Label(col++, row, ""));
					ws.mergeCells(col-2,row, col-1, row);
					
					Label label6 = new Label(col++, row,  StringUtils.nullString(map.get("send_sum"))) ;
					label6.setCellFormat(cellFormat);
					ws.addCell(label6);
					ws.addCell(new Label(col++, row, ""));
					ws.mergeCells(col-2,row, col-1, row);
					
					Label label7 = new Label(col++, row,  StringUtils.nullString(map.get("for_sum"))) ;
					label7.setCellFormat(cellFormat);
					ws.addCell(label7);
					ws.addCell(new Label(col++, row, ""));
					ws.mergeCells(col-2,row, col-1, row);
					
					
					float wages = Float.valueOf(StringUtils.nullString(map.get("take_sum"),"0"))+
							      Float.valueOf(StringUtils.nullString(map.get("send_sum"),"0"))+
							      Float.valueOf(StringUtils.nullString(map.get("for_sum"),"0"));
					
					for (Map<String, Object>map1:nameList) {
						float v = Float.valueOf(StringUtils.nullString(map.get(map1.get("name")),"0")) ;
						Label label = null ;
						if (v<0) {
							label = new Label(col++, row,  StringUtils.nullString(v*-1)) ;
						}else {
							label = new Label(col++, row,  StringUtils.nullString(v)) ;
						}
						label.setCellFormat(cellFormat);
						ws.addCell(label);
						ws.mergeCells(col-1,row, col-1, row+3);
						wages  = wages + v ;
					}
				    wages =  (float)(Math.round(wages*100))/100 ;
				    
				    Label label8 = new Label(col++, row,  StringUtils.nullString(wages)) ;
				    label8.setCellFormat(cellFormat);
					ws.addCell(label8);
					ws.mergeCells(col-1,row, col-1, row+3);
					
					
					row++;
					col = 4 ;
					ws.addCell(new Label(col++, row, "金额"));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("take_freight"))+" x "+StringUtils.nullString(map.get("take_tcf"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("take_sum_freight"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_freight"))+" x "+StringUtils.nullString(map.get("send_tcf"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_sum_freight"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("for_freight"))+" x "+StringUtils.nullString(map.get("for_tcf"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("for_sum_freight"))));
					
					row++;
					col = 4 ;
					ws.addCell(new Label(col++, row, "件数"));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("take_count"))+" x "+StringUtils.nullString(map.get("take_tcc"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("take_sum_count"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_count"))+" x "+StringUtils.nullString(map.get("send_tcc"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_sum_count"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("for_count"))+" x "+StringUtils.nullString(map.get("for_tcc"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("for_sum_count"))));
					
					row++;
					col = 4 ;
					ws.addCell(new Label(col++, row, "重量"));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("take_weight"))+" x "+StringUtils.nullString(map.get("take_tcw"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("take_sum_weight"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_weight"))+" x "+StringUtils.nullString(map.get("send_tcw"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_sum_weight"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("for_weight"))+" x "+StringUtils.nullString(map.get("for_tcw"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("for_sum_weight"))));
					
					row++;
					col = 0;
				}
				
				row++;
				col = 0;
				
				Map<String, Object> total  = wagesService.listTotal(params) ;
				ws.addCell(new Label(col++, row, "总人数："+StringUtils.nullString(total.get("totalCount"))));
				ws.addCell(new Label(col++, row, "收件总票数："+StringUtils.nullString(total.get("total_take_count"))));
				ws.addCell(new Label(col++, row, "收件总重量："+StringUtils.nullString(total.get("total_take_weight"))));
				ws.addCell(new Label(col++, row, "收件总金额："+StringUtils.nullString(total.get("total_take_freight"))));
				ws.addCell(new Label(col++, row, "收件总提成："+StringUtils.nullString(total.get("total_take_sum"))));
				row++;row++;
				col = 0;
				
				double twages = Double.valueOf(StringUtils.nullString(total.get("total_take_sum"),"0")) +  
						        Double.valueOf(StringUtils.nullString(total.get("total_send_sum"),"0")) +
						        Double.valueOf(StringUtils.nullString(total.get("total_for_sum"),"0")) +
						        Double.valueOf(StringUtils.nullString(total.get("total_cost"),"0")) ;
				
				ws.addCell(new Label(col++, row, "应发工资："+StringUtils.nullString(twages)));
				ws.addCell(new Label(col++, row, "派件总票数："+StringUtils.nullString(total.get("total_send_count"))));
				ws.addCell(new Label(col++, row, "派件总重量："+StringUtils.nullString(total.get("total_send_weight"))));
				ws.addCell(new Label(col++, row, "派件总金额："+StringUtils.nullString(total.get("total_send_freight"))));
				ws.addCell(new Label(col++, row, "派件总提成："+StringUtils.nullString(total.get("total_send_sum"))));
				row++;row++;
				col = 0;
				
				ws.addCell(new Label(col++, row, ""));
				ws.addCell(new Label(col++, row, "外件总票数："+StringUtils.nullString(total.get("total_for_count"))));
				ws.addCell(new Label(col++, row, "外件总重量："+StringUtils.nullString(total.get("total_for_weight"))));
				ws.addCell(new Label(col++, row, "外件总金额："+StringUtils.nullString(total.get("total_for_freight"))));
				ws.addCell(new Label(col++, row, "外件总提成："+StringUtils.nullString(total.get("total_for_sum"))));
				
				
				
				row++;
				col = 0;
				
				
				
				
				
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}		
			
			//费用录入流水导出
			public void wages_ilistExport(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
				Date nowDate = new Date() ;
				String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -3, 0, 0),"yyyy-MM-dd") ;
				String endTime =  DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
				if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
					params.put("createTimeBegin", beginTime) ;
				}
				if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
					endTime = params.get("createTimeEnd") ;
				}
				params.put("createTimeEnd", endTime+" 23:59:59") ;
				
				String sno = params.get("substationNo");
				if (StringUtils.nullString(sno).contains("(")) {
					sno = sno.substring(0, sno.indexOf("("));
				}
				params.put("sno", sno);
				
				String cno =  params.get("courierNo");
				if (StringUtils.nullString(cno).contains("(")) {
					cno = cno.substring(0, cno.indexOf("("));
				}
				params.put("cno", cno);
				
				List<Map<String, Object>> list =  courierCostService.list(params) ;
				List<Map<String, Object>> nameList =  wagesService.listCostName(new HashMap<String, String>()) ;
				

				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				
				ws.addCell(new Label(col++, 0,  "账单时间"));
				ws.addCell(new Label(col++, 0,  "网点"));
				ws.addCell(new Label(col++, 0,  "快递员编号"));
				ws.addCell(new Label(col++, 0,  "快递员"));

				for (Map<String, Object>map:nameList) {
					ws.addCell(new Label(col++, 0,  StringUtils.nullString(map.get("cn_name"))));
				}
				
				ws.addCell(new Label(col++, 0,  "录入人"));
			    ws.addCell(new Label(col++, 0,  "录入时间"));
				
				col=0 ;
				
				Iterator<Map<String, Object>> it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
		               
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cost_month"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("substation_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("inner_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("real_name"))));
					for (Map<String, Object>map1:nameList) {
						float v = Float.valueOf(StringUtils.nullString(map.get(map1.get("name")),"0")) ;
						ws.addCell(new Label(col++, row, StringUtils.nullString(v)));
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("operator"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("create_time"))));
					
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}		
			
			//费用管理流水导出
			public void wages_mlistExport(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
				Date nowDate = new Date() ;
				String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -3, 0, 0),"yyyy-MM-dd") ;
				String endTime =  DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
				if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
					params.put("createTimeBegin", beginTime) ;
				}
				if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
					endTime = params.get("createTimeEnd") ;
				}
				params.put("createTimeEnd", endTime+" 23:59:59") ;
				
				String sno = params.get("substationNo");
				if (StringUtils.nullString(sno).contains("(")) {
					sno = sno.substring(0, sno.indexOf("("));
				}
				params.put("sno", sno);
				
				String cno =  params.get("courierNo");
				if (StringUtils.nullString(cno).contains("(")) {
					cno = cno.substring(0, cno.indexOf("("));
				}
				params.put("cno", cno);
				
				List<Map<String, Object>> list =  courierCostService.list(params) ;
				List<Map<String, Object>> nameList =  wagesService.listCostName(new HashMap<String, String>()) ;
				

				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				
				ws.addCell(new Label(col++, 0,  "账单时间"));
				ws.addCell(new Label(col++, 0,  "网点"));
				ws.addCell(new Label(col++, 0,  "快递员编号"));
				ws.addCell(new Label(col++, 0,  "快递员"));

				for (Map<String, Object>map:nameList) {
					ws.addCell(new Label(col++, 0,  StringUtils.nullString(map.get("cn_name"))));
				}
				
				//ws.addCell(new Label(col++, 0,  "录入人"));
			   // ws.addCell(new Label(col++, 0,  "录入时间"));
				
				col=0 ;
				
				Iterator<Map<String, Object>> it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
		               
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cost_month"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("substation_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("inner_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("real_name"))));
					for (Map<String, Object>map1:nameList) {
						float v = Float.valueOf(StringUtils.nullString(map.get(map1.get("name")),"0")) ;
						ws.addCell(new Label(col++, row, StringUtils.nullString(v)));
					}
					//ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("operator"))));
					//ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("create_time"))));
					
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}		
			
			//费用管理流水导出
			public void wages_rlistExport(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
				Date nowDate = new Date() ;
				String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -3, 0, 0),"yyyy-MM-dd") ;
				String endTime =  DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
				if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
					params.put("createTimeBegin", beginTime) ;
				}
				if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
					endTime = params.get("createTimeEnd") ;
				}
				params.put("createTimeEnd", endTime+" 23:59:59") ;
				
				String sno = params.get("substationNo");
				if (StringUtils.nullString(sno).contains("(")) {
					sno = sno.substring(0, sno.indexOf("("));
				}
				params.put("sno", sno);
				
				String cno =  params.get("courierNo");
				if (StringUtils.nullString(cno).contains("(")) {
					cno = cno.substring(0, cno.indexOf("("));
				}
				params.put("cno", cno);
				
				List<Map<String, Object>> list =  courierCostRecordService.list(params) ;
				List<Map<String, Object>> nameList =  wagesService.listCostName(new HashMap<String, String>()) ;
				

				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				
				ws.addCell(new Label(col++, 0,  "账单时间"));
				ws.addCell(new Label(col++, 0,  "网点"));
				ws.addCell(new Label(col++, 0,  "快递员编号"));
				ws.addCell(new Label(col++, 0,  "快递员"));

				for (Map<String, Object>map:nameList) {
					ws.addCell(new Label(col++, 0,  StringUtils.nullString(map.get("cn_name"))));
				}
				
				ws.addCell(new Label(col++, 0,  "编辑人"));
			    ws.addCell(new Label(col++, 0,  "编辑时间"));
				
				col=0 ;
				
				Iterator<Map<String, Object>> it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
		               
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cost_month"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("substation_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("inner_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("real_name"))));
					for (Map<String, Object>map1:nameList) {
						float v = Float.valueOf(StringUtils.nullString(map.get(map1.get("name")),"0")) ;
						ws.addCell(new Label(col++, row, StringUtils.nullString(v)));
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("operator"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("create_time"))));
					
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}	
			

			 // 用于
			public void jmrule(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
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
				
				List<Map<String, Object>> list = franchiseRuleService.getlistRule(params) ;
				
				
				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/jiamengrule.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tsname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("ssname"))));
					if ("Z".equals(StringUtils.nullString(map.get("money_type")))) {
						ws.addCell(new Label(col++, row,  "中转费"));
					}else {
						ws.addCell(new Label(col++, row,  "派件费"));
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("citype"))));
					if ("R".equals(StringUtils.nullString(map.get("weight_type")))) {
						ws.addCell(new Label(col++, row,  "实际重量"));
					}else {
						if ("W".equals(StringUtils.nullString(map.get("weight_type")))) {
							ws.addCell(new Label(col++, row,  "四舍五入"));
						}
						if ("L".equals(StringUtils.nullString(map.get("weight_type")))) {
							ws.addCell(new Label(col++, row,  "0.5进位"));
						}
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("wval1"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("wval2"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("vpay"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("zpay"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("begin_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("end_time"))));
					String wt = StringUtils.nullString(map.get("weight_text")) ;
					wt = wt.replaceAll("&nbsp;", "    ").replaceAll("</br>", "    \n").replaceAll("&lt;", "<").replaceAll("&gt;", ">") ;
					ws.addCell(new Label(col++, row,  wt));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("create_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("create_name"))));
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}
			

			 // 用于
			public void jmzlist(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
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
				
				List<Map<String, Object>> list = franOrderService.getlist(params) ;
				
				
				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/jmzlist.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
					
					if ("SUCCESS".equals(StringUtils.nullString(map.get("settle_status")))) {
						ws.addCell(new Label(col++, row,  "已批带"));
					}else {
						ws.addCell(new Label(col++, row,  "未批带"));
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lgc_order_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("take_order_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tsname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tcno"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tcname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("item_type"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("item_weight"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("ssname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_order_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("scno"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("scname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_money"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(cacheService.findByNameKey("ORDER_STATUS", StringUtils.nullString(map.get("status"))).getDictValue())));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("last_update_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("last_update_time"))));

					
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}

			 // 用于
			public void jmslist(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
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
				
				List<Map<String, Object>> list = franOrderService.getlist(params) ;
				
				
				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/jmslist.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
					
					if ("SUCCESS".equals(StringUtils.nullString(map.get("settle_status")))) {
						ws.addCell(new Label(col++, row,  "已批带"));
					}else {
						ws.addCell(new Label(col++, row,  "未批带"));
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lgc_order_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("take_order_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tsname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tcno"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tcname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("item_type"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("item_weight"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("ssname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_order_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("scno"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("scname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_money"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(cacheService.findByNameKey("ORDER_STATUS", StringUtils.nullString(map.get("status"))).getDictValue())));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("last_update_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("last_update_time"))));

					
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}
			
			 // 用于
			public void jmsubzlist(OutputStream os, Map<String, String> params,
					String fileName) throws Exception {
				int row = 1; // 从第三行开始写
				int col = 0; // 从第一列开始写
				
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}
				params.put("take_substation_no", substationNo);
				
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
				
				List<Map<String, Object>> list = franOrderService.getlist(params) ;
				
				
				Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/jmsubzlist.xls"));
				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
				WritableSheet ws = wwb.getSheet(0);
				Iterator<Map<String, Object>> it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
					
					if ("SUCCESS".equals(StringUtils.nullString(map.get("settle_status")))) {
						ws.addCell(new Label(col++, row,  "已批带"));
					}else {
						ws.addCell(new Label(col++, row,  "未批带"));
					}
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("confirm_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("confirm_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("confirm_real_name"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lgc_order_no"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("take_order_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tsname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tcno"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tcname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("item_type"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("item_weight"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("ssname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_order_time"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("scno"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("scname"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_money"))));
					ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_time"))));
					
					row++;
					col = 0;
				}
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			}		
			
			// 用于
						public void jmsubslist(OutputStream os, Map<String, String> params,
								String fileName) throws Exception {
							int row = 1; // 从第三行开始写
							int col = 0; // 从第一列开始写
							
							BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
							String substationNo ;
							if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
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
							
							List<Map<String, Object>> list = franOrderService.getlist(params) ;
							
							
							Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/jmsubslist.xls"));
							WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
							WritableSheet ws = wwb.getSheet(0);
							Iterator<Map<String, Object>> it = list.iterator();
							while (it.hasNext()) {
								Map<String, Object> map = it.next();
								
								if ("SUCCESS".equals(StringUtils.nullString(map.get("settle_status")))) {
									ws.addCell(new Label(col++, row,  "已批带"));
								}else {
									ws.addCell(new Label(col++, row,  "未批带"));
								}
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("confirm_time"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("confirm_name"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("confirm_real_name"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lgc_order_no"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("take_order_time"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tsname"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tcno"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("tcname"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("item_type"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("item_weight"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("ssname"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_order_time"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("scno"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("scname"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_money"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("settle_time"))));
								
								row++;
								col = 0;
							}
							wwb.write();
							wwb.close();
							wb.close();
							os.close();
						}		
				// 用于
		public void porderlist(OutputStream os, Map<String, String> params,
								String fileName) throws Exception {
							int row = 1; // 从第三行开始写
							int col = 0; // 从第一列开始写
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
							List<Map<String, Object>> list = proOrderService.list(params,getPageInfo(1,4000)).getList();
							
							Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/porderlist.xls"));
							WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
							WritableSheet ws = wwb.getSheet(0);
							Iterator<Map<String, Object>> it = list.iterator();
							while (it.hasNext()) {
								Map<String, Object> map = it.next();
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lgc_order_no"))));
								ws.addCell(new Label(col++, row,  cacheService.proOrderStatus(StringUtils.nullString(map.get("deal_status")))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("otime"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("send_phone"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("month_settle_no"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("descb"))));
								ws.addCell(new Label(col++, row,  cacheService.proOrderReason(StringUtils.nullString(map.get("pro_reason")))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("check_name"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("last_desc"))));
								row++;
								col = 0;
							}
							wwb.write();
							wwb.close();
							wb.close();
							os.close();
						}		
		
		
		// 用于
		public void monitor_asign_detail(OutputStream os, Map<String, String> params,
								String fileName) throws Exception {
							int row = 0; // 从第三行开始写
							int col = 0; // 从第一列开始写


							List<Map<String, Object>> list = asignOrderService.monitorDetailList(params) ;
									 
							
							Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
							WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
							WritableSheet ws = wwb.getSheet(0);
							
							ws.addCell(new Label(col++, row,  "运单编号"));
							ws.addCell(new Label(col++, row,  "运单号"));
							ws.addCell(new Label(col++, row,  "下单时间"));
							ws.addCell(new Label(col++, row,  "分配时间"));
							ws.addCell(new Label(col++, row,  "分配人编号"));
							ws.addCell(new Label(col++, row,  "分配人"));
							ws.addCell(new Label(col++, row,  "分配时长（分钟）"));
							row++;
							col = 0;
							
							Iterator<Map<String, Object>> it = list.iterator();
							while (it.hasNext()) {
								Map<String, Object> map = it.next();
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("order_no"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lgc_order_no"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("order_time"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("asign_time"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("asign_no"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("asign_name"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("asign_duration"))));
								row++;
								col = 0;
							}
							
							wwb.write();
							wwb.close();
							wb.close();
							os.close();
						}
		

		// 用于
		public void monitor_asign(OutputStream os, Map<String, String> params,
								String fileName) throws Exception {
							int row = 0; // 从第三行开始写
							int col = 0; // 从第一列开始写
							Date nowDate = new Date() ;
							String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
							String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
							if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
								params.put("createTimeBegin", beginTime) ;
							}
							if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
								endTime = params.get("createTimeEnd") ;
							}
							params.put("createTimeEnd", endTime+" 23:59:59") ;
							List<Map<String, Object>> list = asignOrderService.monitorList(params) ;
							Map<String, Object> total = asignOrderService.monitorTotal(params) ;
									 
							
							Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
							WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
							WritableSheet ws = wwb.getSheet(0);
							if ("1".equals(params.get("noType"))) {
								ws.addCell(new Label(col++, row,  "分配日期"));
							}else {
								ws.addCell(new Label(col++, row,  "下单日期"));
							}
							ws.addCell(new Label(col++, row,  "分配人编号"));
							ws.addCell(new Label(col++, row,  "分配人"));
							ws.addCell(new Label(col++, row,  "分配数量"));
							ws.addCell(new Label(col++, row,  "平均分配时长（分钟）"));
							row++;
							col = 0;
							
							Iterator<Map<String, Object>> it = list.iterator();
							while (it.hasNext()) {
								Map<String, Object> map = it.next();
								if ("1".equals(params.get("noType"))) {
									ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("asing_date"))));
								}else {
									ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("order_date"))));
								}
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("asign_no"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("asign_name"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("asignCount"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("asignDuration"))));
								row++;
								col = 0;
							}
							ws.addCell(new Label(col++, row,  ""));
							ws.addCell(new Label(col++, row,  ""));
							ws.addCell(new Label(col++, row,  ""));
							ws.addCell(new Label(col++, row,  StringUtils.nullString(total.get("asignCount"))));
							ws.addCell(new Label(col++, row,  StringUtils.nullString(total.get("asignDuration"))));
							row++;
							col = 0;
							
							wwb.write();
							wwb.close();
							wb.close();
							os.close();
						}		
		
		
		// 用于
		public void monitor_take(OutputStream os, Map<String, String> params,
								String fileName) throws Exception {
							int row = 0; // 从第三行开始写
							int col = 0; // 从第一列开始写

							BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
							String substationNo ;
							if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
								 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
							}else {
								substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
							}
							params.put("substationNo", substationNo);
							
							String sno = params.get("subNo");
							if (StringUtils.nullString(sno).contains("(")) {
								sno = sno.substring(0, sno.indexOf("("));
							}
							params.put("sno", sno);
							
							String cno =  params.get("courierNo");
							if (StringUtils.nullString(cno).contains("(")) {
								cno = cno.substring(0, cno.indexOf("("));
							}
							params.put("cno", cno);
							
							Date nowDate = new Date() ;
							String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
							String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
							if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
								params.put("createTimeBegin", beginTime) ;
							}
							if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
								endTime = params.get("createTimeEnd") ;
							}
							params.put("createTimeEnd", endTime+" 23:59:59") ;
							
							List<Map<String, Object>> list = asignOrderService.takeMonitor(params) ;
							
							Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
							WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
							WritableSheet ws = wwb.getSheet(0);
							
							ws.addCell(new Label(col++, row,  "收件日期"));
							ws.addCell(new Label(col++, row,  "网点编号"));
							ws.addCell(new Label(col++, row,  "网点名称"));
							ws.addCell(new Label(col++, row,  "快递员编号"));
							ws.addCell(new Label(col++, row,  "快递员"));
							ws.addCell(new Label(col++, row,  "收件票数"));
							ws.addCell(new Label(col++, row,  "分配票数"));
							ws.addCell(new Label(col++, row,  "平均收件时长（分钟）"));
							row++;
							col = 0;
							
							Iterator<Map<String, Object>> it = list.iterator();
							while (it.hasNext()) {
								Map<String, Object> map = it.next();
								float takeSum = Float.valueOf(StringUtils.nullString(map.get("takeSum"),"0")) ;
								float asignCount = Float.valueOf(StringUtils.nullString(map.get("asignCount"),"0")) ;
								int d = 0 ;
								if (asignCount>0.01) {
									d= Math.round(takeSum/asignCount) ;
								}
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("takeDate"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("sno"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("substation_name"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cno"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("real_name"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("takeCount"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("asignCount"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(d)));
								row++;
								col = 0;
							}
							
							wwb.write();
							wwb.close();
							wb.close();
							os.close();
						}
		
		
		
		// 用于
		public void monitor_take_detail(OutputStream os, Map<String, String> params,
								String fileName) throws Exception {
							int row = 0; // 从第三行开始写
							int col = 0; // 从第一列开始写

							List<Map<String, Object>> list = asignOrderService.monitorTakeDetailList(params) ;
							
							Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
							WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
							WritableSheet ws = wwb.getSheet(0);
							
							
							ws.addCell(new Label(col++, row,  "运单号"));
							ws.addCell(new Label(col++, row,  "分配时间"));
							ws.addCell(new Label(col++, row,  "收件时间	"));
							ws.addCell(new Label(col++, row,  "快递员编号"));
							ws.addCell(new Label(col++, row,  "快递员"));
							ws.addCell(new Label(col++, row,  "收件时长（分钟）"));
							row++;
							col = 0;
							
							Iterator<Map<String, Object>> it = list.iterator();
							while (it.hasNext()) {
								Map<String, Object> map = it.next();
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lgc_order_no"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("asign_time"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("take_order_time"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("inner_no"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("real_name"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("takeSum"))));
								row++;
								col = 0;
							}
							
							wwb.write();
							wwb.close();
							wb.close();
							os.close();
						}
		
		
		// 用于
		public void monitor_inmonitor(OutputStream os, Map<String, String> params,
								String fileName) throws Exception {
							int row = 0; // 从第三行开始写
							int col = 0; // 从第一列开始写

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
							
							BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
							String substationNo ;
							if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
								 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
							}else {
								substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
							}
							params.put("substationNo", substationNo);
							
							String sno = params.get("cur_no");
							if (StringUtils.nullString(sno).contains("(")) {
								sno = sno.substring(0, sno.indexOf("("));
							}
							params.put("nextNo", sno);
							params.put("curOrNext", "2");
							List<Map<String, Object>> list = monitorService.getIoList(params) ; 
							
							Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
							WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
							WritableSheet ws = wwb.getSheet(0);
							
							ws.addCell(new Label(col++, row,  "扫描时间"));
							ws.addCell(new Label(col++, row,  "运单号"));
							ws.addCell(new Label(col++, row,  "网点编号"));
							ws.addCell(new Label(col++, row,  "网点名称"));
							ws.addCell(new Label(col++, row,  "上一站编号"));
							ws.addCell(new Label(col++, row,  "上一站名称"));
							row++;
							col = 0;
							
							Iterator<Map<String, Object>> it = list.iterator();
							while (it.hasNext()) {
								Map<String, Object> map = it.next();
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("order_time"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lgc_order_no"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("nextNo"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("nextName"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("curNo"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("curName"))));
								row++;
								col = 0;
							}
							
							wwb.write();
							wwb.close();
							wb.close();
							os.close();
						}
		
		
		// 用于
		public void monitor_outmonitor(OutputStream os, Map<String, String> params,
								String fileName) throws Exception {
							int row = 0; // 从第三行开始写
							int col = 0; // 从第一列开始写

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
							
							BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
							String substationNo ;
							if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(params.get("sub_limit"))) {
								 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
							}else {
								substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
							}
							params.put("substationNo", substationNo);
							
							String sno = params.get("cur_no");
							if (StringUtils.nullString(sno).contains("(")) {
								sno = sno.substring(0, sno.indexOf("("));
							}
							params.put("curNo", sno);
							params.put("curOrNext", "1");
							List<Map<String, Object>> list = monitorService.getIoList(params) ; 
							
							Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
							WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
							WritableSheet ws = wwb.getSheet(0);
							
							
							ws.addCell(new Label(col++, row,  "扫描时间"));
							ws.addCell(new Label(col++, row,  "运单号"));
							ws.addCell(new Label(col++, row,  "网点编号"));
							ws.addCell(new Label(col++, row,  "网点名称"));
							ws.addCell(new Label(col++, row,  "操作人"));
							ws.addCell(new Label(col++, row,  "下一站编号"));
							ws.addCell(new Label(col++, row,  "下一站名称"));
							row++;
							col = 0;
							
							Iterator<Map<String, Object>> it = list.iterator();
							while (it.hasNext()) {
								Map<String, Object> map = it.next();
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("order_time"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lgc_order_no"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("curNo"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("curName"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("opname"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("nextNo"))));
								ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("nextName"))));
								row++;
								col = 0;
							}
							
							wwb.write();
							wwb.close();
							wb.close();
							os.close();
						}
		
		// 用于
				public void customer_list(OutputStream os, Map<String, String> params,
										String fileName) throws Exception {
									int row = 0; // 从第三行开始写
									int col = 0; // 从第一列开始写

									String endTime = params.get("createTimeEnd") ;
									if (!StringUtils.isEmptyWithTrim(endTime)) {
										params.put("createTimeEnd", endTime+" 23:59:59") ;
									}
									List<LgcCustomer> list = lgcCustomerService.getList(params) ;
									
									Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
									WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
									WritableSheet ws = wwb.getSheet(0);
									
									
									ws.addCell(new Label(col++, row,  "客户号"));
									ws.addCell(new Label(col++, row,  "公司简称"));
									ws.addCell(new Label(col++, row,  "联系人"));
									ws.addCell(new Label(col++, row,  "联系电话"));
									ws.addCell(new Label(col++, row,  "手机号"));
									ws.addCell(new Label(col++, row,  "客服负责人"));
									ws.addCell(new Label(col++, row,  "月结号"));
									ws.addCell(new Label(col++, row,  "来源"));
									ws.addCell(new Label(col++, row,  "注册时间"));
									row++;
									col = 0;
									
									Iterator<LgcCustomer> it = list.iterator();
									while (it.hasNext()) {
										LgcCustomer lgcCustomer = it.next();
										ws.addCell(new Label(col++, row,  StringUtils.nullString(lgcCustomer.getCustomer_no())));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(lgcCustomer.getCpn_sname())));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(lgcCustomer.getConcat_name())));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(lgcCustomer.getConcat_phone())));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(lgcCustomer.getCell_phone())));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(lgcCustomer.getKefu_name())));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(lgcCustomer.getMonth_no())));
										if ("BOSS".equals(lgcCustomer.getSource())) {
											ws.addCell(new Label(col++, row,  "后台下单"));
										}
										if ("WEIXIN".equals(lgcCustomer.getSource())) {
											ws.addCell(new Label(col++, row,  "微信"));
										}
										if ("COURIER".equals(lgcCustomer.getSource())) {
											ws.addCell(new Label(col++, row,  "快递端"));
										}
										if ("WEB".equals(lgcCustomer.getSource())) {
											ws.addCell(new Label(col++, row,  "网页下单"));
										}
										ws.addCell(new Label(col++, row,  StringUtils.nullString(lgcCustomer.getCreate_time())));
										row++;
										col = 0;
									}
									
									wwb.write();
									wwb.close();
									wb.close();
									os.close();
								}
				
				// 用于
				public void customer_huifang(OutputStream os, Map<String, String> params,
										String fileName) throws Exception {
									int row = 0; // 从第三行开始写
									int col = 0; // 从第一列开始写

									String endTime = params.get("createTimeEnd") ;
									if (!StringUtils.isEmptyWithTrim(endTime)) {
										params.put("createTimeEnd", endTime+" 23:59:59") ;
									}
									List<Map<String, Object>> list = lgcCustomerService.huifang(params) ;
									
									Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
									WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
									WritableSheet ws = wwb.getSheet(0);
									
									
									ws.addCell(new Label(col++, row,  "客户号"));
									ws.addCell(new Label(col++, row,  "公司简称"));
									ws.addCell(new Label(col++, row,  "联系人"));
									ws.addCell(new Label(col++, row,  "联系电话"));
									ws.addCell(new Label(col++, row,  "手机号"));
									ws.addCell(new Label(col++, row,  "客服负责人"));
									ws.addCell(new Label(col++, row,  "月结号"));
									ws.addCell(new Label(col++, row,  "客户回访"));
									row++;
									col = 0;
									
									Iterator<Map<String, Object>> it = list.iterator();
									while (it.hasNext()) {
										Map<String, Object> map = it.next();
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("customer_no"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cpn_sname"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("concat_name"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("concat_phone"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cell_phone"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("kefu_name"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("month_no"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("huifang")).replaceAll("</br>", "\n")));
										row++;
										col = 0;
									}
									
									wwb.write();
									wwb.close();
									wb.close();
									os.close();
								}
				
				// 用于
				public void customer_report(OutputStream os, Map<String, String> params,
										String fileName) throws Exception {
									int row = 0; // 从第三行开始写
									int col = 0; // 从第一列开始写

									Date nowDate = new Date() ;
									if (!params.containsKey("curDay")) {
										params.put("nowDate", DateUtils.formatDate(nowDate,"yyyy-MM-dd")) ;
									}else {
										params.put("curDayMonth", params.get("curDay").substring(0,7)) ;
									}
									if (!params.containsKey("curMonth")) {
										params.put("curMonth", DateUtils.formatDate(nowDate,"yyyy-MM")) ;
									}
									if (!params.containsKey("lastMonth")) {
										params.put("lastMonth", DateUtils.getLastMonth(nowDate)) ;
									}
									List<Map<String, Object>> list = lgcCustomerService.report(params) ;
									
									Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/common.xls"));
									WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
									WritableSheet ws = wwb.getSheet(0);
									
									ws.addCell(new Label(col++, row,  "客户号"));
									ws.addCell(new Label(col++, row,  "联系人"));
									ws.addCell(new Label(col++, row,  "联系电话"));
									ws.addCell(new Label(col++, row,  "公司简称"));
									ws.addCell(new Label(col++, row,  "月结号"));
									ws.addCell(new Label(col++, row,  "客服负责人"));
									ws.addCell(new Label(col++, row,  "当日发件量"));
									ws.addCell(new Label(col++, row,  "本月份发件量"));
									ws.addCell(new Label(col++, row,  "上月份发件"));
									ws.addCell(new Label(col++, row,  "趋势（%）"));
									row++;
									col = 0;
									
									Iterator<Map<String, Object>> it = list.iterator();
									while (it.hasNext()) {
										Map<String, Object> map = it.next();
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("customer_no"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("concat_name"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("concat_phone"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("cpn_sname"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("month_no"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("kefu_name"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("curDayCount"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("curMonthCount"))));
										ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("lastMonthCount"))));


											long curMonthCount = Long.valueOf(map.get("curMonthCount").toString()) ;
											long lastMonthCount = Long.valueOf(map.get("lastMonthCount").toString()) ;
											long x = curMonthCount - lastMonthCount ;
											long  a  = 0 ;
											if (curMonthCount==0&&lastMonthCount==0) {
												 map.put("qushi", "↑") ;
												 map.put("qushival", "0%") ;
											}else {
												if (x>0) {
												    map.put("qushi", "↑") ;
												}else {
													x = lastMonthCount - curMonthCount ;
													map.put("qushi", "↓") ;
												}
												if (curMonthCount==0||lastMonthCount==0) {
													map.put("qushival", "100%") ;
												}else {
													double x1 = x ;
													double x2 = lastMonthCount ;
													double r = 0 ;
													 r = x1/x2*100 ;	
													 a = (long) Math.ceil(r) ;
													 map.put("qushival", String.format("%.2f",r)+"%") ;
												}
											}
											ws.addCell(new Label(col++, row,  StringUtils.nullString(map.get("qushi"))+"   "+StringUtils.nullString(map.get("qushival"))));
										row++;
										col = 0;
									}
									
									wwb.write();
									wwb.close();
									wb.close();
									os.close();
								}
}
