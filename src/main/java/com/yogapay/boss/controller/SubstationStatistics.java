package com.yogapay.boss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.CodInfo;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.service.CodInfoService;
import com.yogapay.boss.service.CodMonthCountService;
import com.yogapay.boss.service.CodSettleUserService;
import com.yogapay.boss.service.CourierDayStaticService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.LgcService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.MonthUserStaticService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.service.ScanStaticsService;
import com.yogapay.boss.service.SubstationAccountService;
import com.yogapay.boss.service.SubstationDayStaticService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtil;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.SendMail;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

/**
 * 用于统计网点信息等
 * 
 */
@Controller
@RequestMapping(value = "/substatic")
public class SubstationStatistics extends BaseController {

	@Resource
	private SubstationService substationService;
	@Resource
	private CourierService courierService;
	@Resource
	private CourierDayStaticService courierDayStaticService;
	@Resource
	private MonthUserStaticService monthUserStaticService;
	@Resource
	private SubstationAccountService substationAccountService;
	@Resource
	private SubstationDayStaticService substationDayStaticService;
	@Resource
	private MobileUserService mobileUserService;
	@Resource
	private OrderService orderService;
	@Resource
	private CodSettleUserService codSettleUserService;
	@Resource
	private CodMonthCountService codMonthCountService;
	@Resource
	private ScanStaticsService scanStaticsService;
	@Resource
	private UserService userService;
    @Resource
    private OrderTrackService orderTrackService ;
    @Resource
    private CodInfoService codInfoService ;
    @Resource
    private LgcService lgcService;

	// 快递员每日收件数量
	@RequestMapping(value = { "/dayCount" })
	public String dayCount(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		String substationNo = params.get("substationNo");
		String courierNo = params.get("courierNo");
		if (!StringUtils.isEmptyWithTrim(substationNo)) {
			if (substationNo.contains("(")) {
				substationNo = substationNo.substring(0, substationNo.indexOf("("));
				params.put("substationNo", substationNo);
			}
		}
		if (!StringUtils.isEmptyWithTrim(courierNo)) {
			if (courierNo.contains("(")) {
				courierNo = courierNo.substring(0, courierNo.indexOf("("));
				params.put("courierNo", courierNo);
			}
		}
		System.out.println(beginTime + "," + endTime + "," + substationNo + "," + courierNo);
		Calendar cld = Calendar.getInstance();
		cld.add(Calendar.DAY_OF_YEAR, -1);
		Date nowDate = cld.getTime();
		if (StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
			params.put("endTime", DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
		}
		System.out.println("=============================" + params);
		PageInfo<Map<String, Object>> countList = courierDayStaticService.list(params, getPageInfo(cpage));
		int sumSendCount = 0;
		int sumTakeCount = 0;

		for (Map<String, Object> map : countList.getList()) {
			sumSendCount = sumSendCount + Integer.valueOf(map.get("sendCount").toString());
			sumTakeCount = sumTakeCount + Integer.valueOf(map.get("revCount").toString());
		}
		int sumCount = sumTakeCount + sumSendCount;
		PageInfo<Map<String, Object>> courierList = courierService.list(params, getPageInfo(1, 5000));
		List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
		model.put("courierList", JsonUtil.toJson(courierList.getList()));
		model.put("substationList", JsonUtil.toJson(substations));
		model.put("params", params);
		model.put("countList", countList);
		model.put("sumSendCount", sumSendCount);// 派件件数
		model.put("sumTakeCount", sumTakeCount);// 取件数
		model.put("sumCount", sumCount);// 总数
		return "substatic/dayCount";
	}

	/**
	 * 网点扫描数据汇总
	 * 
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/subScan" })
	public String subScan(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		Date dae = new Date();

		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		String sno = params.get("substationNo");


		if (StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtils.formatDate(dae, "yyyy-MM-dd"));
			params.put("endTime", DateUtils.formatDate(dae, "yyyy-MM-dd"));
		}

		setSubstationNo(params, request) ;
		
		PageInfo<Map<String, Object>> current = new PageInfo<Map<String,Object>>(new ArrayList<Map<String, Object>>()) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			current = scanStaticsService.selectScanInfo(params, getPageInfo(cpage));
		}else {
			params.put("ff", "1") ;
		}
		
		List<Map<String, Object>> packageList = new ArrayList<Map<String, Object>>();
		if(!StringUtils.isEmptyWithTrim(beginTime) && !StringUtils.isEmptyWithTrim(endTime)){
 
			if (current.getList()!=null) {
				for (Map<String, Object> map : current.getList()) {
					String date = (String) map.get("scanTime");
					String subtationNo = (String) map.get("subNo");
					map.put("scanTimeEnd", map.get("scanTime")+" 23:59:59") ;
					map.put("takeCount", scanStaticsService.takeCount(map));// 收件票数
					map.put("sendCount", scanStaticsService.sendCount(map));// 到站票数
					map.put("daozhanpiaoshu", scanStaticsService.daozhanpiaoshu(map));// 到站票数
					map.put("chuzhanpiaoshu", scanStaticsService.chuzhanpiaoshu(map));// 出站票数
					map.put("wentijian", scanStaticsService.wentijian(map));// 问题件
					map.put("qianshoupiaoshu", scanStaticsService.qianshoupiaoshu(map));// 签收票数
					map.put("inAndOut", scanStaticsService.inAndOut(map));// 有到站有出站
					map.put("inNotOut", scanStaticsService.inNotOut(map));// 有到站无出站
					// map.put("noInhaveOut",scanStaticsService.noInhaveOut(map));//无到站有出站
					map.put("noInhaveOutSend", scanStaticsService.noInhaveOutSend(map));// 无到站有派件
					// map.put("inNotSend", scanStaticsService.inNotSend(map));//有到站无派件
					packageList.add(map);
				}	
			}
		
			model.put("packageList", packageList);
			model.put("current", current);
		}
        
		params.put("substationNo", sno) ;
		model.put("params", params);
		return "substatic/subScan";
	}

	// //查询分站名
	// @RequestMapping(value = { "/subNoCheck" })
	// @ResponseBody
	// public void substationNo(@RequestParam Map<String, String> params,final
	// ModelMap model, HttpServletRequest request,
	// HttpServletResponse response) throws IOException {
	// String subNo = params.get("substationNo");
	// Substation substa = substationService.getSubstationByNo(subNo);
	// if(substa==null){
	// params.put("substaNam", "未知分站编号");
	// outJson(JsonUtil.toJson(params), response);
	// }else{
	// params.put("substaNam", substa.getSubstationName());
	// outJson(JsonUtil.toJson(params), response);
	// }
	// }
	// //查询快递员姓名
	// @RequestMapping(value = { "/courierNoCheck" })
	// public void courierNo(@RequestParam Map<String, String> params,final
	// ModelMap model, HttpServletRequest request,
	// HttpServletResponse response) throws IOException {
	// String subNo = params.get("courierNo");
	// Courier cr = new Courier();
	// cr.setCourierNo(subNo);
	// Courier cr1= courierService.getCourierByNo(cr);
	// if(cr1==null){
	// params.put("courierNam","未知快递员编号");
	// outJson(JsonUtil.toJson(params), response);
	// }else{
	// params.put("courierNam",cr1.getRealName() );
	// outJson(JsonUtil.toJson(params), response);
	// }
	// }

	// 快递员每日收件数量
	@RequestMapping(value = { "/export" })
	public void export(@RequestParam Map<String, String> params, HttpServletResponse response, HttpServletRequest request) throws SQLException {
		String serviceName = params.get("serviceName");
		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		OutputStream os = null;
		params.put("sub_limit", StringUtils.nullString(request.getAttribute("sub_limit"))) ;
		String host = "http://"+request.getHeader("host") ;
		params.put("host",host) ;
		try {
			request.setCharacterEncoding("UTF-8");
			os = response.getOutputStream(); // 取得输出流
			response.reset(); // 清空输出流
			response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
			if (!StringUtils.isEmpty(params.get("serviceName"))) {
				if ("dayCountExport".equals(serviceName)) {
					String fileName = "收派件记录统计-" + beginTime + "~" + endTime + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 1);
				}
				if ("pdayCountExport".equals(serviceName)) {
					String fileName = "应交款报表-" + beginTime + "~" + endTime + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 2);
				}
				if ("monthCountExport".equals(serviceName)) {
					String fileName = "月结用户收派明细-" + beginTime + "~" + endTime + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 3);
				}
				if ("monthUserCountExport".equals(serviceName)) {
					String fileName = "月结用户月报-" + beginTime + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 4);
				}
				if ("substationDayCountExport".equals(serviceName)) {
					String fileName = "网点每日收派件统计-" + beginTime + "~" + endTime + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 5);
				}
				if ("monthUserRevCountExport".equals(serviceName)) {
					String fileName = "月结客户每月发货统计-" + beginTime + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 6);
				}
				if ("codMonthList".equals(serviceName)) {
					String fileName = "代收客户订单明细-" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 7);
				}
				if ("codMonthCount".equals(serviceName)) {
					String fileName = "代收客户月报统计-" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 8);
				}
				if ("dayCompanyExport".equals(serviceName)) {
					String fileName = "公司每日收件派件统计-" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 9);
				}

				if ("daySubstationExport".equals(serviceName)) {
					String fileName = "网点每日收件派件统计-" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 10);
				}

				if ("dayCourierExport".equals(serviceName)) {
					String fileName = "快递员每日报表-" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 11);
				}
				if ("dayCourierExport2".equals(serviceName)) {
					String fileName = "快递员收派统计表-" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 12);
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

	/**
	 * mergeCells(a,b,c,d) 单元格合并函数
	* a 单元格的列号
	* b 单元格的行号
 	* c 从单元格[a,b]起，向下合并的列数
	* d 从单元格[a,b]起，向下合并的行数	
	 * @param os
	 * @param params
	 * @param fileName
	 * @param num
	 * @throws Exception
	 */
	public void exportOrder(OutputStream os, Map<String, String> params, String fileName, int num) throws Exception {
		int row = 1; // 从第二行开始写
		int col = 0; // 从第一列开始写
		
		if (num == 1) {
			String substationNo = params.get("substationNo");
			String courierNo = params.get("courierNo");
			if (!StringUtils.isEmptyWithTrim(substationNo)) {
				if (substationNo.contains("(")) {
					substationNo = substationNo.substring(0, substationNo.indexOf("("));
					params.put("substationNo", substationNo);
				}
			}
			if (!StringUtils.isEmptyWithTrim(courierNo)) {
				if (courierNo.contains("(")) {
					courierNo = courierNo.substring(0, courierNo.indexOf("("));
					params.put("courierNo", courierNo);
				}
			}
			int sumSendCount = 0;
			int sumTakeCount = 0;
			List<Map<String, Object>> orderList = courierDayStaticService.getList(params);
			for (Map<String, Object> map : orderList) {
				sumSendCount = sumSendCount + Integer.valueOf(map.get("sendCount").toString());
				sumTakeCount = sumTakeCount + Integer.valueOf(map.get("revCount").toString());
			}
			int sumCount = sumTakeCount + sumSendCount;
			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/statistics.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = orderList.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row, (String) map.get("substationNo")));
				ws.addCell(new Label(col++, row, (String) map.get("substationName")));
				ws.addCell(new Label(col++, row, String.valueOf(map.get("courierNo"))));
				ws.addCell(new Label(col++, row, String.valueOf(map.get("courierName"))));
				ws.addCell(new Label(col++, row, (String.valueOf(map.get("staticDate"))).substring(0, 10)));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("revCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendCount"))));
				row++;
				col = 0;
			}
			ws.addCell(new Label(5, row, "收件总计：" + sumTakeCount));
			ws.addCell(new Label(6, row, "派件总计：" + sumSendCount));
			row++;
			ws.addCell(new Label(6, row, "收派件总计：" + sumCount));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}
		if (num == 2) {
			int sumSendCount = 0;
			int sumTakeCount = 0;
			List<Map<String, Object>> orderList = courierDayStaticService.getList(params);
			BigDecimal pfcount = new BigDecimal("0.00");// 应收现金
			BigDecimal pmcount = new BigDecimal("0.00");// 应收月结
			BigDecimal pccount = new BigDecimal("0.00");// 应收代收款
			BigDecimal pacount = new BigDecimal("0.00");// 应收总额
			BigDecimal hmcount = new BigDecimal("0.00");// 应收会员
			BigDecimal cashAcount = new BigDecimal("0.00");// 应现金总额

			for (Map<String, Object> map : orderList) {
				sumSendCount = sumSendCount + Integer.valueOf(map.get("sendCount").toString());
				sumTakeCount = sumTakeCount + Integer.valueOf(map.get("revCount").toString());
				pfcount = pfcount.add((BigDecimal) map.get("fcount"));
				pmcount = pmcount.add((BigDecimal) map.get("mcount"));
				pccount = pccount.add((BigDecimal) map.get("ccount"));
				pacount = pacount.add((BigDecimal) map.get("acount"));
				hmcount = hmcount.add((BigDecimal) map.get("hcount"));
				cashAcount = cashAcount.add((BigDecimal) map.get("cashCount"));
			}
			int sumCount = sumTakeCount + sumSendCount;
			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/pstatistics.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = orderList.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row, (String) map.get("substationName")));
				ws.addCell(new Label(col++, row, String.valueOf(map.get("courierNo"))));
				ws.addCell(new Label(col++, row, String.valueOf(map.get("courierName"))));
				ws.addCell(new Label(col++, row, (String.valueOf(map.get("staticDate")))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("revCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("fcount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("mcount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("ccount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("hcount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("acount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cashCount"))));
				row++;
				col = 0;
			}
			ws.addCell(new Label(4, row, "收件总计：" + sumTakeCount));
			ws.addCell(new Label(5, row, "派件总计：" + sumSendCount));
			ws.addCell(new Label(6, row, "应收现金总计：" + pfcount));
			ws.addCell(new Label(7, row, "应收月结总计：" + pmcount));
			ws.addCell(new Label(8, row, "应收代收款总计：" + pccount));
			ws.addCell(new Label(9, row, "应收会员款总计：" + hmcount));
			ws.addCell(new Label(10, row, "应收总额总计：" + pacount));
			ws.addCell(new Label(11, row, "应收现金总额总计：" + cashAcount));
			row++;
			ws.addCell(new Label(5, row, "收派件总计：" + sumCount));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}
		//月结用户收派明细-
		if (num == 3) {
			 row = 2; // 从第三行开始写
			 col = 0; // 从第一列开始写
			String userNo = params.get("monthSettleNo");
			String revNo = params.get("revNo");
			String sendNo = params.get("sendNo");
			String beginTime =params.get("beginTime"); ;// 收件时间
			String endTime = params.get("endTime");
			String sendOrderBeginTime = params.get("sendOrderBeginTime");// 签收时间
			String sendOrderEndTime = params.get("sendOrderEndTime");
			if (!StringUtils.isEmptyWithTrim(userNo)) {
				if (userNo.contains("(")) {
					userNo = userNo.substring(0, userNo.indexOf("("));
					params.put("monthSettleNo", userNo);
				}
			}
			if (!StringUtils.isEmptyWithTrim(revNo)) {
				if (revNo.contains("(")) {
					revNo = revNo.substring(0, revNo.indexOf("("));
					params.put("revNo", revNo);
				}
			}
			if (!StringUtils.isEmptyWithTrim(sendNo)) {
				if (sendNo.contains("(")) {
					sendNo = sendNo.substring(0, sendNo.indexOf("("));
					params.put("sendNo", sendNo);
				}
			}
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(params.get("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}
				params.put("substationNo", substationNo);
			}
			String takeOrSend = params.get("takeOrSend");
			List<Map<String, Object>> orderList = new ArrayList<Map<String,Object>>	();
			if("T".equals(takeOrSend)){
				orderList = mobileUserService.getMonthListForTake(params, getPageInfo(1,100000)).getList();
			}
			if("S".equals(takeOrSend)){
				orderList = mobileUserService.getMonthListForSend(params, getPageInfo(1,100000)).getList();
			}			
			Map<String, Object> monthMap = mobileUserService.getMuserByNo(params.get("monthSettleNo"));

			
			
			BigDecimal allTake = new BigDecimal("0.00");//所有取件收钱
			BigDecimal allSend = new BigDecimal("0.00");//所有派件收钱
			BigDecimal allMpay = new BigDecimal("0.00");//所有应收
			BigDecimal allRemainderMoney = new BigDecimal("0.00");//所有折扣金额;
			BigDecimal allDiscountMoney = new BigDecimal("0.00");//所有实收金额
			if(orderList.size()>0){		
			for(Map<String,Object> map :orderList){
				allTake=allTake.add(new BigDecimal(String.valueOf(map.get("takeMoney"))))	;
				allSend=allSend.add(new BigDecimal(String.valueOf(map.get("sendMoney"))))	;
				allMpay=allMpay.add(new BigDecimal(String.valueOf(map.get("mpay"))))	;
				allRemainderMoney=allRemainderMoney.add(new BigDecimal(String.valueOf(map.get("remainderMoney"))))	;
				allDiscountMoney=allDiscountMoney.add(new BigDecimal(String.valueOf(map.get("discountMoney"))))	;		
			}
			}
			Workbook wb =null;
			if(Constants.getUser().getLgcNo().equals("1131")){
				wb=Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/monthCount.xls"));
			}else{
				wb=Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/monthCount1.xls"));
			}
			 
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = orderList.iterator();
			
			ws.addCell(new Label(2, row, userNo));	
			if("T".equals(takeOrSend)){
				ws.addCell(new Label(7, row, beginTime+"-"+endTime));
			}
			if("S".equals(takeOrSend)){
				ws.addCell(new Label(7, row, sendOrderBeginTime+"-"+sendOrderEndTime));
			}		
			row++;
			ws.addCell(new Label(2, row, StringUtils.nullString(monthMap.get("month_sname"))));	
			ws.addCell(new Label(7, row, allDiscountMoney.toString()));	
			row++;
			ws.addCell(new Label(2, row, StringUtils.nullString(monthMap.get("contact_name"))));	
			ws.addCell(new Label(7, row,StringUtils.nullString(monthMap.get("realName"))));	
			row++;
			row++;
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgcOrderNo"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeOrderTime"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendName"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("itemWeight"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("revArea"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("completeTime"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("revName"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeMoney"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendMoney"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("mpay"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("remainderMoney"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("discountMoney"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("realName1"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("realName2"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("monthNote"))));
				if (!StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("take_plane")))) {
					ws.addCell(new Label(col++, row, params.get("host")+StringUtils.nullString(map.get("take_plane"))));
				}else {
					if (!StringUtil.isEmptyWithTrim(StringUtils.nullString(map.get("send_plane")))) {
						ws.addCell(new Label(col++, row, params.get("host")+StringUtils.nullString(map.get("send_plane"))));
					}else {
						ws.addCell(new Label(col++, row, ""));
					}
					
				}
				row++;
				col = 0;
			}
			ws.addCell(new Label(0, row, "总计：" ));
			ws.addCell(new Label(7, row, "" + allTake));
			ws.addCell(new Label(8, row, "" + allSend));
			ws.addCell(new Label(9, row, "" + allMpay));
			ws.addCell(new Label(10, row, "" + allRemainderMoney));
			ws.addCell(new Label(11, row, "" + allDiscountMoney));

			row++;
			ws.addCell(new Label(10, row, "总条数：" + orderList.size()));
			if(Constants.getUser().getLgcNo().equals("1131")){
				row++;
				ws.mergeCells(0, row, 15, row);
				ws.addCell(new Label(0, row, "◆月结客户可订阅“电子账单服务，只要贵司提供准确的电子邮箱，即可自动查询和收阅账单。" ));
				row++;
				ws.mergeCells(0, row, 15, row);
				ws.addCell(new Label(0, row, "◆请保留好运单，以便核对账单。并请于每月协议承诺日前，支付上月运费及相关费用。" ));
				row++;
				ws.mergeCells(0, row, 15, row);
				ws.addCell(new Label(0, row, "◆为了您的资金安全，希望您在付款时优先选择支票、转账、等非现金支付方式结算运费。" ));
				row++;
				ws.mergeCells(0, row, 15, row);
				ws.addCell(new Label(0, row, "我公司相关资料如下：" ));	
				row++;
				ws.mergeCells(0, row, 3,row);
				ws.addCell(new Label(0, row, "公司账户：亿翔供应链（深圳）有限责任公司	" ));	
				ws.mergeCells(4, row, 10, row);
				ws.addCell(new Label(4, row, "个人帐户：何军" ));
				row++;
				ws.mergeCells(0, row, 3, row);
				ws.addCell(new Label(0, row, "开户银行：平安银行深圳新秀支行" ));	
				ws.mergeCells(4, row, 10, row);
				ws.addCell(new Label(4, row, "开户银行：招商银行深圳文锦渡支行" ));
				row++;
				ws.mergeCells(0, row, 3, row);
				ws.addCell(new Label(0, row, "银行账号：11014836018000（RMB）" ));	
				ws.mergeCells(4, row, 10, row);
				ws.addCell(new Label(4, row, "银行帐号：6214 8578 0668 9859" ));
				row++;
				row++;
				ws.mergeCells(0, row, 15, row);
				ws.addCell(new Label(0, row, "尊敬的客户，现将上个月对帐单送上，请即予核对。联系人：陈小姐，电话：0755-33033696，QQ:3425141662," ));	
				row++;
				ws.mergeCells(0, row, 15, row);
				ws.addCell(new Label(0, row, "如有不符，务必于3日内与我司联系，否则我公司将视本对帐单无误，谢谢！" ));	
			}
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}
		if (num == 4) {
			String beginTime = params.get("beginTime");
			String endTime = params.get("endTime");
			String beginTimeM = params.get("beginTimeM");//按月统计
//			Calendar cld = Calendar.getInstance();
//			cld.add(Calendar.MONTH, -1);
			Date nowDate =new Date();
			String dayOrMonth = params.get("dayOrMonth");//按天/月
			System.out.println("-----------------------"+dayOrMonth+"-----------------------");
			if(!StringUtils.isEmptyWithTrim(dayOrMonth)){
				if("D".equals(dayOrMonth)){
					if (StringUtils.isEmptyWithTrim(beginTime)) {
						params.put("beginTime", DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
					}	
					if (StringUtils.isEmptyWithTrim(endTime)) {
						params.put("endTime", DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
					}	
				}
				if("M".equals(dayOrMonth)){
					if (StringUtils.isEmptyWithTrim(beginTimeM)) {
						params.put("beginTimeM", DateUtils.formatDate(nowDate, "yyyy-MM"));
					}	
				}			
				String courierNo = params.get("courierNo");
				String monthSettleNo = params.get("monthSettleNo");
				String isEmail = params.get("isEmail");
				System.out.println(courierNo + "=====================courierNo");
				System.out.println(monthSettleNo + "=====================monthSettleNo");
				System.out.println(params.get("beginTime") + "=====================beginTime");
				System.out.println(params.get("endTime") + "=====================endTime");
				if (!StringUtils.isEmptyWithTrim(monthSettleNo)) {
					if (monthSettleNo.contains("(")) {
						monthSettleNo = monthSettleNo.substring(0, monthSettleNo.indexOf("("));
						params.put("monthSettleNo", monthSettleNo);
					}
				}
				if (!StringUtils.isEmptyWithTrim(courierNo)) {
					if (courierNo.contains("(")) {
						courierNo = courierNo.substring(0, courierNo.indexOf("("));
						params.put("courierNo", courierNo);
					}
				}
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					String substationNo ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(params.get("sub_limit")))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					params.put("substationNo", substationNo);
				}

			List<Map<String, Object>> list = monthUserStaticService.getMonthDayOrMonthList(params);		
				BigDecimal AllsumMoney = new BigDecimal("0.00");// 实收
				int count = 0;
				for (Map<String, Object> map : list) {
					AllsumMoney = AllsumMoney.add((BigDecimal) (map.get("sumMoney")));
				count = count+Integer.valueOf(String.valueOf(map.get("sumCount")));
				}
			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/monthUserCount.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = list.iterator();
			while (it.hasNext()) {

				Map<String, Object> map = it.next();
				if("M".equals(params.get("dayOrMonth"))){
					ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("beginTimeM"))));
					ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("beginTimeM"))));
				}else{
					ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("beginTime"))));
					ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("endTime"))));
				}	
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substationName"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("courierName"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("TmonthSettleNo"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("monthSname"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeCount"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("TALL"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendCount"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("SALL"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sumCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("TSALL"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("discountText"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sumMoney"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("settleType"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("note"))));
				row++;
				col = 0;
			}
			ws.addCell(new Label(7, row, "总数：" + count));
			ws.addCell(new Label(9 ,row, "实收款项总计：" + AllsumMoney));
			row++;
			ws.addCell(new Label(3, row, "出纳签名："));
			ws.addCell(new Label(7, row, "业务签名："));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
			}
		}
		if (num == 5) {
			int sumSendCount = 0;
			int sumTakeCount = 0;
			List<Map<String, Object>> orderList = substationDayStaticService.getListExport(params);
			BigDecimal pfcount = new BigDecimal("0.00");// 应收现金
			BigDecimal pmcount = new BigDecimal("0.00");// 应收月结
			BigDecimal pccount = new BigDecimal("0.00");// 应收代收款
			BigDecimal pacount = new BigDecimal("0.00");// 应收总额
			for (Map<String, Object> map : orderList) {
				sumSendCount = sumSendCount + Integer.valueOf(map.get("sendCount").toString());
				sumTakeCount = sumTakeCount + Integer.valueOf(map.get("revCount").toString());
				pfcount = pfcount.add((BigDecimal) map.get("fcount"));
				pmcount = pmcount.add((BigDecimal) map.get("mcount"));
				pccount = pccount.add((BigDecimal) map.get("ccount"));
				pacount = pacount.add((BigDecimal) map.get("acount"));
			}
			int sumCount = sumTakeCount + sumSendCount;
			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/substationDayCount.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = orderList.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row, (String) map.get("substationNo")));
				ws.addCell(new Label(col++, row, (String) map.get("substationName")));
				ws.addCell(new Label(col++, row, (String.valueOf(map.get("staticDate"))).substring(0, 10)));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("revCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("fcount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("mcount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("ccount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("acount"))));
				row++;
				col = 0;
			}
			ws.addCell(new Label(3, row, "收件总计：" + sumTakeCount));
			ws.addCell(new Label(4, row, "派件总计：" + sumSendCount));
			ws.addCell(new Label(5, row, "应收现金总计：" + pfcount));
			ws.addCell(new Label(6, row, "应收月结总计：" + pmcount));
			ws.addCell(new Label(7, row, "应收代收款总计：" + pccount));
			ws.addCell(new Label(8, row, "应收总额总计：" + pacount));
			row++;
			ws.addCell(new Label(3, row, "收派件总计：" + sumCount));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}
		if (num == 6) {
			List<Map<String, Object>> countList = monthUserStaticService.getMonthUserRevListExport(params);
			int count = 0;
			for (Map<String, Object> map : countList) {
				count = count + (Integer) map.get("count");
			}
			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/monthUserYearStatic.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = countList.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("monthSettleNo"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("monthName"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substationNo"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substationName"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("courierName"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("count"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("oneCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("twoCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("threeCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("fourCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("fiveCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sixCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sevenCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("eightCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("nineCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("tenCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("elevenCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("twelveCount"))));
				row++;
				col = 0;
			}
			ws.addCell(new Label(5, row, "收派件总计：" + count));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}

		if (num == 7) {
			Date nowDate = new Date();
			String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -3, 0, 0), "yyyy-MM-dd");
			String endTime = DateUtils.formatDate(nowDate, "yyyy-MM-dd");
			String tcourierNo = params.get("tcourierNo");
			String tno = tcourierNo;
			if (StringUtils.nullString(tno).contains("(")) {
				tno = tno.substring(0, tno.indexOf("("));
			}
			params.put("tcourierNo", tno);

			String scourierNo = params.get("scourierNo");
			String sno = scourierNo;
			if (StringUtils.nullString(sno).contains("(")) {
				sno = sno.substring(0, sno.indexOf("("));
			}
			params.put("scourierNo", sno);
			if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
				params.put("createTimeBegin", beginTime);
			}
			if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
				endTime = params.get("createTimeEnd");
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
			}
			params.put("createTimeEnd", endTime + " 23:59:59");
			PageInfo<Map<String, Object>> orderList = orderService.codList(params, getPageInfo(1, 20000));
			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/codOrderList.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = orderList.getList().iterator();
			BigDecimal sum_good_price = new BigDecimal("0.00");// 应收总额
			BigDecimal sum_cpay = new BigDecimal("0.00");// 应收总额

			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				sum_good_price = sum_good_price.add(new BigDecimal(StringUtils.nullString(map.get("good_price"))));
				sum_cpay = sum_cpay.add(new BigDecimal(StringUtils.nullString(map.get("cpay"),"0.0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_no"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_sname"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgc_order_no"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rece_no"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("for_no"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("create_time"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("send_order_time"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sign_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_Status"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("item_weight"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("good_price"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cpay"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeName"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendName"))));
	             if (StringUtils.nullString(map.get("pay_status")).equals("1")) {
	            	 ws.addCell(new Label(col++, row, "已付款"));
				   }else {
					ws.addCell(new Label(col++, row, "未付款"));
				 }
	            ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("pay_time"))));  
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("pay_type"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("pay_name"))));
				
			/*	Courier take_courier = courierService.getCourierByNo(StringUtils.nullString(map.get("take_courier_no")));
				Courier send_courier = courierService.getCourierByNo(StringUtils.nullString(map.get("send_courier_no")));
				if (take_courier != null) {
					ws.addCell(new Label(col++, row, StringUtils.nullString(take_courier.getRealName())));
				} else {
					ws.addCell(new Label(col++, row, ""));
				}
				if (send_courier != null) {
					ws.addCell(new Label(col++, row, StringUtils.nullString(send_courier.getRealName())));
				} else {
					ws.addCell(new Label(col++, row, ""));
				}*/
				row++;
				col = 0;
			}
			ws.addCell(new Label(11, row, "" + sum_good_price));
			ws.addCell(new Label(12, row, "" + sum_cpay));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}

		if (num == 8) {
			String courierNo = params.get("courierNo");
			String cno = courierNo;
			if (StringUtils.nullString(cno).contains("(")) {
				cno = cno.substring(0, cno.indexOf("("));
			}
			params.put("courierNo", cno);
			String codNo = params.get("codNo");
			String cod = codNo;
			if (StringUtils.nullString(cod).contains("(")) {
				cod = cod.substring(0, cod.indexOf("("));
			}
			params.put("codNo", cod);
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(params.get("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}
				params.put("substationNo", substationNo);
			}
			PageInfo<Map<String, Object>> orderList = codMonthCountService.list(params, getPageInfo(1, 20000));

			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/codMonthCount.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = orderList.getList().iterator();
			BigDecimal sum_cod_price = new BigDecimal("0.00");// 应收总额
			BigDecimal sum_return_price = new BigDecimal("0.00");// 应收总额
			while (it.hasNext()) {
				Map<String, Object> map = it.next();

				sum_cod_price = sum_cod_price.add(new BigDecimal(StringUtils.nullString(map.get("cod_price"))));
				sum_return_price = sum_return_price.add(new BigDecimal(StringUtils.nullString(map.get("return_price"))));

				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_no"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_sname"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("settle_date"))));
				Courier courier = courierService.getCourierByNo(StringUtils.nullString(map.get("courier_no")));
				if (courier != null) {
					ws.addCell(new Label(col++, row, StringUtils.nullString(courier.getRealName())));
				} else {
					ws.addCell(new Label(col++, row, ""));
				}
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("settle_time"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cod_price"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("discount")) + "‰(千分之)"));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("return_price"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("settle_type"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("settle_card_no"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("settle_bank"))));
				row++;
				col = 0;
			}
			ws.addCell(new Label(5, row, "代收款总计：" + sum_cod_price));
			ws.addCell(new Label(7, row, "返款总计：" + sum_return_price));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}

		if (num == 9) {
		
			PageInfo<Map<String, Object>> countList = orderService.queryDayCompanyInfo(params, getPageInfo(1, 20000));
			Map<String, Object> count = orderService.queryDayCompanyCount(params);

			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/dayCompanyCount.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = countList.getList().iterator();
			BigDecimal sum_cod_price = new BigDecimal("0.00");// 应收总额
			BigDecimal sum_return_price = new BigDecimal("0.00");// 应收总额
			row = row + 0;
			while (it.hasNext()) {
				Map<String, Object> map = it.next();

				// sum_cod_price = sum_cod_price.add(new
				// BigDecimal(StringUtils.nullString(map.get("cod_price"))));
				// sum_return_price = sum_return_price.add(new
				// BigDecimal(StringUtils.nullString(map.get("return_price"))));

				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("timejoin"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("jcount"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("jcashMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("jmonthMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("jmemberMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dcount"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dcashMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dmonthMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dmemberMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dgoodMoney"),"0")));
				
				BigDecimal b1 = new BigDecimal(StringUtils.nullString(map.get("jcashMoney"),"0")) ;
				BigDecimal b2 = new BigDecimal(StringUtils.nullString(map.get("dcashMoney"),"0")) ;
				BigDecimal dgoodMoney = new BigDecimal(StringUtils.nullString(map.get("dgoodMoney"),"0")) ;
				BigDecimal c1 = b1.add(b2).add(dgoodMoney) ;
				ws.addCell(new Label(col++, row, StringUtils.nullString(c1)));
				
				b1 = new BigDecimal(StringUtils.nullString(map.get("jmonthMoney"),"0")) ;
				b2 = new BigDecimal(StringUtils.nullString(map.get("dmonthMoney"),"0")) ;
				BigDecimal c2 = b1.add(b2) ;
				ws.addCell(new Label(col++, row, StringUtils.nullString(c2)));
				
				b1 = new BigDecimal(StringUtils.nullString(map.get("jmemberMoney"),"0")) ;
				b2 = new BigDecimal(StringUtils.nullString(map.get("dmemberMoney"),"0")) ;
				BigDecimal c3 = b1.add(b2) ;
				ws.addCell(new Label(col++, row, StringUtils.nullString(c3)));
				
				ws.addCell(new Label(col++, row, StringUtils.nullString(c1.add(c2).add(c3))));
				row++;
				col = 0;
			}
			ws.addCell(new Label(col++, row, "总计"));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("jcount"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("jcashMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("jmonthMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("jmemberMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("dcount"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("dcashMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("dmonthMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("dmemberMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("dgoodMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("xjtotal"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("yjtotal"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("hytotal"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("total"),"0")));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}

		if (num == 10) {
			
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(params.get("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}
				params.put("substationNo", substationNo);
			}
			PageInfo<Map<String, Object>> countList = orderService.queryDaySubstationInfo(params, getPageInfo(1, 20000));
			Map<String, Object> count = orderService.queryDaySubstationCount(params);

			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/daySubstationCount.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = countList.getList().iterator();
			BigDecimal sum_cod_price = new BigDecimal("0.00");// 应收总额
			BigDecimal sum_return_price = new BigDecimal("0.00");// 应收总额
			row = row + 0;
			while (it.hasNext()) {
				Map<String, Object> map = it.next();

				// sum_cod_price = sum_cod_price.add(new
				// BigDecimal(StringUtils.nullString(map.get("cod_price"))));
				// sum_return_price = sum_return_price.add(new
				// BigDecimal(StringUtils.nullString(map.get("return_price"))));

				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("timejoin"))));
				// if (!StringUtil.isEmptyString(map.get("jsubstationNo"))) {
				// ws.addCell(new Label(col++, row,
				// StringUtils.nullString(map.get("jsubstationNo"))));
				// ws.addCell(new Label(col++, row,
				// StringUtils.nullString(map.get("jsubstationName"))));
				// } else {
				// ws.addCell(new Label(col++, row,
				// StringUtils.nullString(map.get("dsubstationNo"))));
				// ws.addCell(new Label(col++, row,
				// StringUtils.nullString(map.get("dsubstationName"))));
				// }
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substationNo"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substationName"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("jcount"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("jcashMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("jmonthMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("jmemberMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dcount"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dcashMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dmonthMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dmemberMoney"),"0")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dgoodMoney"),"0")));
				
				BigDecimal b1 = new BigDecimal(StringUtils.nullString(map.get("jcashMoney"),"0")) ;
				BigDecimal b2 = new BigDecimal(StringUtils.nullString(map.get("dcashMoney"),"0")) ;
				BigDecimal dgoodMoney = new BigDecimal(StringUtils.nullString(map.get("dgoodMoney"),"0")) ;
				BigDecimal c1 = b1.add(b2).add(dgoodMoney) ;
				ws.addCell(new Label(col++, row, StringUtils.nullString(c1)));
				
				b1 = new BigDecimal(StringUtils.nullString(map.get("jmonthMoney"),"0")) ;
				b2 = new BigDecimal(StringUtils.nullString(map.get("dmonthMoney"),"0")) ;
				BigDecimal c2 = b1.add(b2) ;
				ws.addCell(new Label(col++, row, StringUtils.nullString(c2)));
				
				b1 = new BigDecimal(StringUtils.nullString(map.get("jmemberMoney"),"0")) ;
				b2 = new BigDecimal(StringUtils.nullString(map.get("dmemberMoney"),"0")) ;
				BigDecimal c3 = b1.add(b2) ;
				ws.addCell(new Label(col++, row, StringUtils.nullString(c3)));
				
				ws.addCell(new Label(col++, row, StringUtils.nullString(c1.add(c2).add(c3))));
				
				row++;
				col = 0;
			}
			ws.addCell(new Label(col++, row, "总计"));
			ws.addCell(new Label(col++, row, ""));
			ws.addCell(new Label(col++, row, ""));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("jcount"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("jcashMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("jmonthMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("jmemberMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("dcount"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("dcashMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("dmonthMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("dmemberMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("dgoodMoney"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("xjtotal"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("yjtotal"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("hytotal"),"0")));
			ws.addCell(new Label(col++, row, StringUtils.nullString(count.get("total"),"0")));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}

		if (num == 11) {
			
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(params.get("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}
				params.put("substationNo", substationNo);
			}
			PageInfo<Map<String, Object>> countList = orderService.queryDayCourierInfoByWang(params, getPageInfo(1, 20000));
		

			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/dayCourierCount.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = countList.getList().iterator();
			BigDecimal takeCount = new BigDecimal("0");
			BigDecimal takeCash = new BigDecimal("0.00");
			BigDecimal takeMonth= new BigDecimal("0.00");
			BigDecimal takeHuiyuan = new BigDecimal("0.00");
			BigDecimal forCount= new BigDecimal("0");
			BigDecimal forSum = new BigDecimal("0.00");
			BigDecimal sendCount = new BigDecimal("0");
			BigDecimal sendCash = new BigDecimal("0");
			BigDecimal sendMonth = new BigDecimal("0.00");
			BigDecimal sendHuyuan = new BigDecimal("0.00");
			BigDecimal sendGoodPrice = new BigDecimal("0.00");
			BigDecimal sumCash = new BigDecimal("0.00");
			BigDecimal sumMonth = new BigDecimal("0.00");
			BigDecimal sumHuiyuan = new BigDecimal("0.00");
			BigDecimal sumAll = new BigDecimal("0.00");
			for(Map<String,Object> map :countList.getList()){
				takeCount = takeCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeCount"))));
				takeCash = takeCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeCashCol"))));
				takeMonth = takeMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeMonthCol"))));
				takeHuiyuan = takeHuiyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeHuiyuanCol"))));
				forCount = forCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeForCount"))));
				forSum = forSum.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeForCol"))));
				sendCount = sendCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendCount"))));
				sendCash = sendCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendCashCol"))));
				sendMonth = sendMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendMonthCol"))));
				sendHuyuan = sendHuyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendHuiyuanCol"))));
				sendGoodPrice = sendGoodPrice.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendGoodPrice"))));
				sumCash = sumCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumCash"))));
				sumMonth = sumMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumMonth"))));
				sumHuiyuan = sumHuiyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumHuiyuan"))));
				sumAll = sumAll.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumAll"))));		
			}
	
			row = row + 0;
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("timeJoin1"))));			
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("subtationInnerNo"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substation_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("courierInnerNo"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("real_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeCashCol"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeMonthCol"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeHuiyuanCol"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeForCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeForCol"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sendCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sendCashCol"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sendMonthCol"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sendHuiyuanCol"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sendGoodPrice"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sumCash"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sumMonth"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sumHuiyuan"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sumAll"))));			
				row++;
				col = 0;
			}
			ws.addCell(new Label(col++, row, "总计"));
			ws.addCell(new Label(col++, row, ""));
			ws.addCell(new Label(col++, row, ""));
			ws.addCell(new Label(col++, row, ""));
			ws.addCell(new Label(col++, row, ""));
			ws.addCell(new Label(col++, row,takeCount.toString() ));
			ws.addCell(new Label(col++, row,takeCash.toString()));
			ws.addCell(new Label(col++, row,takeMonth.toString() ));
			ws.addCell(new Label(col++, row,takeHuiyuan.toString() ));
			ws.addCell(new Label(col++, row,forCount.toString() ));
			ws.addCell(new Label(col++, row,forSum.toString() ));
			ws.addCell(new Label(col++, row,sendCount.toString() ));
			ws.addCell(new Label(col++, row,sendCash.toString() ));
			ws.addCell(new Label(col++, row,sendMonth.toString() ));
			ws.addCell(new Label(col++, row,sendHuyuan.toString() ));
			ws.addCell(new Label(col++, row,sendGoodPrice.toString() ));
			ws.addCell(new Label(col++, row,sumCash.toString() ));
			ws.addCell(new Label(col++, row,sumMonth.toString() ));
			ws.addCell(new Label(col++, row,sumHuiyuan.toString() ));
			ws.addCell(new Label(col++, row,sumAll.toString() ));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}
	if (num == 12) {
			
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(params.get("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}
				params.put("substationNo", substationNo);
			}
			PageInfo<Map<String, Object>> countList = orderService.queryDayCourierInfoByWang(params, getPageInfo(1, 20000));
		

			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/dayCourierCount2.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = countList.getList().iterator();
			BigDecimal takeCount = new BigDecimal("0");
			BigDecimal takeCash = new BigDecimal("0.00");
			BigDecimal takeMonth= new BigDecimal("0.00");
			BigDecimal takeHuiyuan = new BigDecimal("0.00");
			BigDecimal takeWeight= new BigDecimal("0.00");
			BigDecimal forCount= new BigDecimal("0");
			BigDecimal forSum = new BigDecimal("0.00");
			BigDecimal takeForWeight = new BigDecimal("0.00");
			BigDecimal sendCount = new BigDecimal("0");
			BigDecimal sendCash = new BigDecimal("0");
			BigDecimal sendMonth = new BigDecimal("0.00");
			BigDecimal sendHuyuan = new BigDecimal("0.00");
			BigDecimal sendGoodPrice = new BigDecimal("0.00");
			BigDecimal sendWeight = new BigDecimal("0.00");
			BigDecimal sumCash = new BigDecimal("0.00");
			BigDecimal sumMonth = new BigDecimal("0.00");
			BigDecimal sumHuiyuan = new BigDecimal("0.00");
			BigDecimal sumAll = new BigDecimal("0.00");
			BigDecimal z同城寄件金额总计 = new BigDecimal("0.00");
			for (Map<String, Object> map : countList.getList()) {
				takeCount = takeCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeCount"))));
				takeCash = takeCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeCashCol"))));
				takeMonth = takeMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeMonthCol"))));
				takeHuiyuan = takeHuiyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeHuiyuanCol"))));
				takeWeight = takeWeight.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeWeight"))));
				forCount = forCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeForCount"))));
				forSum = forSum.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeForCol"))));
				takeForWeight = takeForWeight.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeForWeight"))));
				sendCount = sendCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendCount"))));
				sendCash = sendCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendCashCol"))));
				sendMonth = sendMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendMonthCol"))));
				sendHuyuan = sendHuyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendHuiyuanCol"))));
				sendGoodPrice = sendGoodPrice.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendGoodPrice"))));
				sendWeight = sendWeight.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendWeight"))));
				sumCash = sumCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumCash"))));
				sumMonth = sumMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumMonth"))));
				sumHuiyuan = sumHuiyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumHuiyuan"))));
				sumAll = sumAll.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumAll"))));
//				BigDecimal z同城寄件金额 = new BigDecimal("0.00");
//				for (String t : new String[]{"takeCashCol", "takeMonthCol", "takeHuiyuanCol", "sendCashCol", "sendMonthCol", "sendHuiyuanCol"}) {
//					z同城寄件金额 = z同城寄件金额.add(new BigDecimal(StringUtils.nullStringtoZero(map.get(t))));
//				}
//				map.put("z同城寄件金额", z同城寄件金额);
//				z同城寄件金额总计 = z同城寄件金额总计.add(z同城寄件金额);
				
				BigDecimal z同城寄件金额 = new BigDecimal("0.00");
				//for (String t : new String[]{"takeCashCol", "takeMonthCol", "takeHuiyuanCol", "sendCashCol", "sendMonthCol", "sendHuiyuanCol"}) {
				//	z同城寄件金额 = z同城寄件金额.add(new BigDecimal(StringUtils.nullStringtoZero(map.get(t))));
				//}
				z同城寄件金额 = z同城寄件金额.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("_sum"))));
				map.put("z同城寄件金额", z同城寄件金额);
				z同城寄件金额总计 = z同城寄件金额总计.add(z同城寄件金额);
			}
	
			row = row + 0;
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("timeJoin1"))));			
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("subtationInnerNo"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substation_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("courierInnerNo"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("real_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeWeight"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("z同城寄件金额"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeCashCol"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeMonthCol"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeHuiyuanCol"))));				
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeForCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeForWeight"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("takeForCol"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sendCount"))));
				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sendWeight"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sendCashCol"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sendMonthCol"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sendHuiyuanCol"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sendGoodPrice"))));			
//				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sumCash"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sumMonth"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sumHuiyuan"))));
//				ws.addCell(new Label(col++, row, StringUtils.nullStringtoZero(map.get("sumAll"))));			
				row++;
				col = 0;
			}
			ws.addCell(new Label(col++, row, "总计"));
			ws.addCell(new Label(col++, row, ""));
			ws.addCell(new Label(col++, row, ""));
			ws.addCell(new Label(col++, row, ""));
			ws.addCell(new Label(col++, row, ""));
			ws.addCell(new Label(col++, row,takeCount.toString() ));
			ws.addCell(new Label(col++, row,takeWeight.toString() ));
			ws.addCell(new Label(col++, row,z同城寄件金额总计.toString()));
//			ws.addCell(new Label(col++, row,takeCash.toString()));
//			ws.addCell(new Label(col++, row,takeMonth.toString() ));
//			ws.addCell(new Label(col++, row,takeHuiyuan.toString() ));
			ws.addCell(new Label(col++, row,forCount.toString() ));
			ws.addCell(new Label(col++, row,takeForWeight.toString() ));
			ws.addCell(new Label(col++, row,forSum.toString() ));
			ws.addCell(new Label(col++, row,sendCount.toString() ));
			ws.addCell(new Label(col++, row,sendWeight.toString() ));
//			ws.addCell(new Label(col++, row,sendCash.toString() ));
//			ws.addCell(new Label(col++, row,sendMonth.toString() ));
//			ws.addCell(new Label(col++, row,sendHuyuan.toString() ));
//			ws.addCell(new Label(col++, row,sendGoodPrice.toString() ));		
//			ws.addCell(new Label(col++, row,sumCash.toString() ));
//			ws.addCell(new Label(col++, row,sumMonth.toString() ));
//			ws.addCell(new Label(col++, row,sumHuiyuan.toString() ));
//			ws.addCell(new Label(col++, row,sumAll.toString() ));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}
	}

	// 应交款报表
	@RequestMapping(value = { "/pdayCount" })
	public String pdayCount(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		String substationNo = params.get("substationNo");
		String courierNo = params.get("courierNo");
		if (!StringUtils.isEmptyWithTrim(substationNo)) {
			if (substationNo.contains("(")) {
				substationNo = substationNo.substring(0, substationNo.indexOf("("));
				params.put("substationNo", substationNo);
			}
		}
		if (!StringUtils.isEmptyWithTrim(courierNo)) {
			if (courierNo.contains("(")) {
				courierNo = courierNo.substring(0, courierNo.indexOf("("));
				params.put("courierNo", courierNo);
			}
		}
		System.out.println(beginTime + "," + endTime + "," + substationNo + "," + courierNo);
		Calendar cld = Calendar.getInstance();
		cld.add(Calendar.DAY_OF_YEAR, -1);
		Date nowDate = cld.getTime();
		if (StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
			params.put("endTime", DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
		}
		PageInfo<Map<String, Object>> countList = courierDayStaticService.list(params, getPageInfo(cpage));
		int sumSendCount = 0;
		int sumTakeCount = 0;
		BigDecimal pfcount = new BigDecimal("0.00");// 应收现金
		BigDecimal pmcount = new BigDecimal("0.00");// 应收月结
		BigDecimal pccount = new BigDecimal("0.00");// 应收代收款
		BigDecimal pacount = new BigDecimal("0.00");// 应收总额
		BigDecimal hmcount = new BigDecimal("0.00");// 应收会员
		BigDecimal cashAcount = new BigDecimal("0.00");// 应现金总额

		for (Map<String, Object> map : countList.getList()) {
			sumSendCount = sumSendCount + Integer.valueOf(map.get("sendCount").toString());
			sumTakeCount = sumTakeCount + Integer.valueOf(map.get("revCount").toString());
			pfcount = pfcount.add((BigDecimal) map.get("fcount"));
			pmcount = pmcount.add((BigDecimal) map.get("mcount"));
			pccount = pccount.add((BigDecimal) map.get("ccount"));
			pacount = pacount.add((BigDecimal) map.get("acount"));
			hmcount = hmcount.add((BigDecimal) map.get("hcount"));
			cashAcount = cashAcount.add((BigDecimal) map.get("cashCount"));
		}
		System.out.println(pfcount.toString() + "," + pmcount.toString() + "," + pccount.toString() + "," + pacount.toString());
		int sumCount = sumTakeCount + sumSendCount;
		PageInfo<Map<String, Object>> courierList = courierService.list(params, getPageInfo(1, 5000));
		List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
		model.put("courierList", JsonUtil.toJson(courierList.getList()));
		model.put("substationList", JsonUtil.toJson(substations));
		model.put("params", params);
		model.put("countList", countList);
		model.put("sumSendCount", sumSendCount);// 派件件数
		model.put("sumTakeCount", sumTakeCount);// 取件数
		model.put("pfcount", pfcount.toString());// 应收现金
		model.put("pmcount", pmcount.toString());// 应收月结
		model.put("pccount", pccount.toString());// 应收代收款
		model.put("pacount", pacount.toString());// 应收总额
		model.put("hmcount", hmcount.toString());// 应收会员
		model.put("cashAcount", cashAcount.toString());// 应收现金总额
		model.put("sumCount", sumCount);// 总数
		return "substatic/pdayCount";
	}

	// 月结用户收派明细
	@RequestMapping(value = { "/monthCount" })
	public String monthCount(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
	
		//sendNo=, takeOrSend=T, sendOrderBeginTime=2016-05-31, ff=1, sendOrderEndTime=2016-05-31, revNo=,
		//beginTime=2016-05-31, orderLgc=, monthSettleNo=, endTime=2016-05-31, serviceName=}
		String takeOrSend = params.get("takeOrSend");

		String userNo = params.get("monthSettleNo");
		String revNo = params.get("revNo");
		String sendNo = params.get("sendNo");
		String orderLgc = params.get("orderLgc");
		String beginTime =params.get("beginTime"); ;// 收件时间
		String endTime = params.get("endTime");
		String sendOrderBeginTime = params.get("sendOrderBeginTime");// 签收时间
		String sendOrderEndTime = params.get("sendOrderEndTime");
		System.out.println(beginTime + "," + endTime + "," + userNo + "," + orderLgc + "," + revNo + "," + sendNo);
		if (!StringUtils.isEmptyWithTrim(userNo)) {
			if (userNo.contains("(")) {
				userNo = userNo.substring(0, userNo.indexOf("("));
				params.put("monthSettleNo", userNo);
			}
		}
		if (!StringUtils.isEmptyWithTrim(revNo)) {
			if (revNo.contains("(")) {
				revNo = revNo.substring(0, revNo.indexOf("("));
				params.put("revNo", revNo);
			}
		}
		if (!StringUtils.isEmptyWithTrim(sendNo)) {
			if (sendNo.contains("(")) {
				sendNo = sendNo.substring(0, sendNo.indexOf("("));
				params.put("sendNo", sendNo);
			}
		}

		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			params.put("substationNo", substationNo);
		}
		
		Date nowDate = new Date();
		if ("S".equals(takeOrSend)) {
			params.put("beginTime","");
			params.put("endTime", "");
		}
		if ("T".equals(takeOrSend)) {
			params.put("sendOrderBeginTime", "");
			params.put("sendOrderEndTime", "");
		}
		PageInfo<Map<String, Object>> monthList = new PageInfo<Map<String,Object>>(new ArrayList<Map<String, Object>>()) ;
		PageInfo<Map<String, Object>> allList = new PageInfo<Map<String,Object>>(new ArrayList<Map<String, Object>>()) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			if("T".equals(takeOrSend)){
				monthList = mobileUserService.getMonthListForTake(params, getPageInfo(cpage));
				allList=mobileUserService.getMonthListForTake(params, getPageInfo(1,100000));
			}
			if("S".equals(takeOrSend)){
				monthList = mobileUserService.getMonthListForSend(params, getPageInfo(cpage));
				allList=mobileUserService.getMonthListForSend(params, getPageInfo(1,100000));
			}			
		}else {
			params.put("ff", "1") ;
		}

		
		BigDecimal allTake = new BigDecimal("0.00");//所有取件收钱
		BigDecimal allSend = new BigDecimal("0.00");//所有派件收钱
		BigDecimal allMpay = new BigDecimal("0.00");//所有应收
		BigDecimal allRemainderMoney = new BigDecimal("0.00");//所有折扣金额;
		BigDecimal allDiscountMoney = new BigDecimal("0.00");//所有实收金额
		if(monthList.getTotal()>0){		
		for(Map<String,Object> map :allList.getList()){
			allTake=allTake.add(new BigDecimal(String.valueOf(map.get("takeMoney"))))	;
			allSend=allSend.add(new BigDecimal(String.valueOf(map.get("sendMoney"))))	;
			allMpay=allMpay.add(new BigDecimal(String.valueOf(map.get("mpay"))))	;
			allRemainderMoney=allRemainderMoney.add(new BigDecimal(String.valueOf(map.get("remainderMoney"))))	;
			allDiscountMoney=allDiscountMoney.add(new BigDecimal(String.valueOf(map.get("discountMoney"))))	;		
		}
		}
		model.put("listTotal", String.valueOf(monthList.getTotal()));
		model.put("params", params);
		model.put("monthList", monthList);

		model.put("allTake", allTake);
		model.put("allSend", allSend);
		model.put("allMpay", allMpay);
		model.put("allRemainderMoney", allRemainderMoney);
		model.put("allDiscountMoney", allDiscountMoney);

		return "substatic/monthCount";
	}

	// 月结客户号查询
	@RequestMapping(value = { "/checkMonthUser" })
	public void checkMonthUser(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String monthUserNo = params.get("monthSettleNo");// 月结客户号
		Map<String, Object> map = mobileUserService.getMuserByNo(monthUserNo);
		if (map == null) {
			params.put("company", "未知客户编号");
			params.put("name", "未知客户编号");
			params.put("phone", "未知客户编号");
			params.put("address", "未知客户编号");
			outJson(JsonUtil.toJson(params), response);
		} else {
			params.put("company", (String) map.get("substation_no"));
			params.put("name", (String) map.get("contact_name"));
			params.put("phone", (String) map.get("contact_phone"));
			params.put("address", (String) map.get("substation_no"));
			outJson(JsonUtil.toJson(params), response);
		}
	}

	// 月结用户月报表
	@RequestMapping(value = { "/monthUserCount" })
	public String monthUserCount(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		String beginTimeM = params.get("beginTimeM");//按月统计
//		Calendar cld = Calendar.getInstance();
//		cld.add(Calendar.MONTH, -1);
		Date nowDate =new Date();
		String dayOrMonth = params.get("dayOrMonth");//按天/月
		System.out.println("-----------------------"+dayOrMonth+"-----------------------");
		if(!StringUtils.isEmptyWithTrim(dayOrMonth)){
			if("D".equals(dayOrMonth)){
				params.put("beginTimeM", "");
				if (StringUtils.isEmptyWithTrim(beginTime)) {
					params.put("beginTime", DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
				}	
				if (StringUtils.isEmptyWithTrim(endTime)) {
					params.put("endTime", DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
				}	
			}
			if("M".equals(dayOrMonth)){
				params.put("beginTime", "");
				params.put("endTime", "");
				if (StringUtils.isEmptyWithTrim(beginTimeM)) {
					params.put("beginTimeM", DateUtils.formatDate(nowDate, "yyyy-MM"));
				}	
			}			
			String courierNo = params.get("courierNo");
			String monthSettleNo = params.get("monthSettleNo");
			String isEmail = params.get("isEmail");
			System.out.println(courierNo + "=====================courierNo");
			System.out.println(monthSettleNo + "=====================monthSettleNo");
			System.out.println(params.get("beginTime") + "=====================beginTime");
			System.out.println(params.get("endTime") + "=====================endTime");
			if (!StringUtils.isEmptyWithTrim(monthSettleNo)) {
				if (monthSettleNo.contains("(")) {
					monthSettleNo = monthSettleNo.substring(0, monthSettleNo.indexOf("("));
					params.put("monthSettleNo", monthSettleNo);
				}
			}
			if (!StringUtils.isEmptyWithTrim(courierNo)) {
				if (courierNo.contains("(")) {
					courierNo = courierNo.substring(0, courierNo.indexOf("("));
					params.put("courierNo", courierNo);
				}
			}
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}
				params.put("substationNo", substationNo);
			}

			PageInfo<Map<String, Object>> list = new PageInfo<Map<String,Object>>(null) ;
			if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
				list = monthUserStaticService.getMonthDayOrMonth(params, getPageInfo(cpage));	
			}else {
				params.put("ff", "1") ;
			}
			
			
			BigDecimal AllsumMoney = new BigDecimal("0.00");// 实收
			int count = 0;
			if (list.getList()!=null) {
				for (Map<String, Object> map : list.getList()) {
					AllsumMoney = AllsumMoney.add((BigDecimal) (map.get("sumMoney")));
				count = count+Integer.valueOf(String.valueOf(map.get("sumCount")));
				}
			}
			
			model.put("AllsumMoney", AllsumMoney.toString());// 实收
			model.put("list", list);
			model.put("count", count);
			//List<Map<String, Object>> montherUserList = mobileUserService.getAllMonthSetterUser(params);
			//List<Map<String, Object>> courierList = mobileUserService.getAllCourierList(params);
			//model.put("monthList", JsonUtil.toJson(montherUserList));
			//model.put("courierList", JsonUtil.toJson(courierList));		
		}	
		model.put("params", params);
		return "substatic/monthUserCount";
	}

	// 网点每日收派件统计

	@RequestMapping(value = { "/substationDayCount" })
	public String substationDayCount(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		String substationNo = params.get("substationNo");
		System.out.println(beginTime + "," + endTime + "," + substationNo);
		Calendar cld = Calendar.getInstance();
		cld.add(Calendar.DAY_OF_YEAR, -1);
		Date nowDate = cld.getTime();
		if (StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
			params.put("endTime", DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
		}
		PageInfo<Map<String, Object>> countList = substationDayStaticService.list(params, getPageInfo(cpage));
		int sumSendCount = 0;
		int sumTakeCount = 0;
		BigDecimal pfcount = new BigDecimal("0.00");// 应收现金
		BigDecimal pmcount = new BigDecimal("0.00");// 应收月结
		BigDecimal pccount = new BigDecimal("0.00");// 应收代收款
		BigDecimal pacount = new BigDecimal("0.00");// 应收总额
		for (Map<String, Object> map : countList.getList()) {
			sumSendCount = sumSendCount + Integer.valueOf(map.get("sendCount").toString());
			sumTakeCount = sumTakeCount + Integer.valueOf(map.get("revCount").toString());
			pfcount = pfcount.add((BigDecimal) map.get("fcount"));
			pmcount = pmcount.add((BigDecimal) map.get("mcount"));
			pccount = pccount.add((BigDecimal) map.get("ccount"));
			pacount = pacount.add((BigDecimal) map.get("acount"));
		}
		System.out.println(pfcount.toString() + "," + pmcount.toString() + "," + pccount.toString() + "," + pacount.toString());
		int sumCount = sumTakeCount + sumSendCount;
		model.put("params", params);
		model.put("countList", countList);
		model.put("sumSendCount", sumSendCount);// 派件件数
		model.put("sumTakeCount", sumTakeCount);// 取件数
		model.put("pfcount", pfcount.toString());// 应收现金
		model.put("pmcount", pmcount.toString());// 应收月结
		model.put("pccount", pccount.toString());// 应收代收款
		model.put("pacount", pacount.toString());// 应收总额
		model.put("sumCount", sumCount);// 总数
		return "substatic/substationDayCount";
	}

	// 月结客户每月发货统计

	@RequestMapping(value = { "/monthUserRevCount" })
	public String monthUserRevCount(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		String beginTime = params.get("beginTime");
		String substationNo = params.get("substationNo");
		String userNo = params.get("monthSettleNo");
		System.out.println(beginTime + "." + substationNo + "." + userNo);
		Date nowDate = new Date();
		if (StringUtils.isEmptyWithTrim(beginTime)) {
			params.put("beginTime", DateUtils.formatDate(nowDate, "yyyy"));
		}
		PageInfo<Map<String, Object>> countList = monthUserStaticService.getMonthUserRevList(params, getPageInfo(cpage));
		long count = 0;
		for (Map<String, Object> map : countList.getList()) {
			count = count + (Long) map.get("count");
		}
		model.put("count", count);
		model.put("countList", countList);
		model.put("params", params);
		return "substatic/monthUserRevCount";
	}

	
	 // 用于有派件未签收对比表
	@RequestMapping(value = { "/signScan" })
	public String signScan(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
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
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			params.put("substationNo", substationNo);
		}
		PageInfo<Map<String, Object>> orderList = new PageInfo<Map<String,Object>>(null) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			orderList = orderTrackService.getSignScan(params, getPageInfo(cpage)) ;
		}else {
			params.put("ff", "1") ;
		}
		model.put("orderList", orderList) ;
		params.put("createTimeEnd", endTime) ;
		model.put("params", params) ;
		return "substatic/signScan";
	}				
	
	
	
	// 用于
	@RequestMapping(value = { "/codList" })
	public String codList(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) {
		Date nowDate = new Date();
		String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -3, 0, 0), "yyyy-MM-dd");
		String endTime = DateUtils.formatDate(nowDate, "yyyy-MM-dd");
		String tcourierNo = params.get("tcourierNo");
		String tno = tcourierNo;
		if (StringUtils.nullString(tno).contains("(")) {
			tno = tno.substring(0, tno.indexOf("("));
		}
		params.put("tcourierNo", tno);

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
		String scourierNo = params.get("scourierNo");
		String sno = scourierNo;
		if (StringUtils.nullString(sno).contains("(")) {
			sno = sno.substring(0, sno.indexOf("("));
		}
		params.put("scourierNo", sno);
		if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
			params.put("createTimeBegin", beginTime);
		}
		if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
			endTime = params.get("createTimeEnd");
		}
		params.put("createTimeEnd", endTime + " 23:59:59");
		//setModelCour(model, "Y", courierService);
		if (StringUtils.isEmptyWithTrim(params.get("ttype"))) {
			params.put("ttype", "SSS") ;
		}
		
		
		PageInfo<Map<String, Object>> orderList = new PageInfo<Map<String,Object>>(null) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			orderList = orderService.codList(params, getPageInfo(cpage));
		}else {
			params.put("ff", "1") ;
		}
		
		
		model.put("orderList", orderList);
		params.put("createTimeEnd", endTime);
		params.put("tcourierNo", tcourierNo);
		params.put("scourierNo", scourierNo);
		model.put("params", params);
		return "substatic/codlist";
	}

	// 用于cod月报
	@RequestMapping(value = { "/codMonth" })
	public String codMonth(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) {
		String courierNo = params.get("courierNo");
		String cno = courierNo;
		if (StringUtils.nullString(cno).contains("(")) {
			cno = cno.substring(0, cno.indexOf("("));
		}
		params.put("courierNo", cno);

		String codNo = params.get("codNo");
		String cod = codNo;
		if (StringUtils.nullString(cod).contains("(")) {
			cod = cod.substring(0, cod.indexOf("("));
		}
		params.put("codNo", cod);

		//setModelCour(model, "Y", courierService);
		/*PageInfo<Map<String, Object>> codList = codSettleUserService.list(params, getPageInfo(1, 5000));
		if (codList != null) {
			model.put("codList", JsonUtil.toJson(codList.getList()));
		} else {
			model.put("codList", codList);
		}*/
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
		PageInfo<Map<String, Object>> orderList = new PageInfo<Map<String,Object>>(null) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			orderList = codMonthCountService.list(params, getPageInfo(cpage));
		}else {
			params.put("ff", "1") ;
		}
		model.put("orderList", orderList);
		params.put("courierNo", courierNo);
		params.put("codNo", codNo);
		model.put("params", params);
		return "substatic/codMonth";
	}

	// 用于保存
	@RequestMapping(value = { "/cod_settle" })
	public void cod_settle(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		try {
			codMonthCountService.settleUpdate(Integer.valueOf(params.get("id")));
			outText("1", response);
		} catch (Exception e) {
			e.printStackTrace();
			outText("保存失败", response);
		}
	}

	// 用于保存
	@RequestMapping(value = { "/cod_print" })
	public void cod_print(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		try {
			codMonthCountService.printUpdate(Integer.valueOf(params.get("id")));
			outText("1", response);
		} catch (Exception e) {
			e.printStackTrace();
			outText("保存失败", response);
		}
	}

	@RequestMapping(value = { "/queryDayCompanyInfo" })
	public String queryDayCompanyInfo(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException, ParseException {
		BossUser user = Constants.getUser() ;
		params.put("substationNo", user.getSubstationNo1());		
		System.out.println(user.getSubstationNo1());
		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		if (StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtil.dateToString(DateUtil.getAddDay(-1), "yyyy-MM-dd"));
			params.put("endTime", DateUtil.dateToString(DateUtil.getAddDay(-1), "yyyy-MM-dd"));
		} else if (StringUtils.isEmptyWithTrim(beginTime) && !StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtil.dateToString(DateUtil.getAddDay(endTime, -7), "yyyy-MM-dd"));
		} else if (!StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("endTime", DateUtil.dateToString(DateUtil.getAddDay(beginTime, 7), "yyyy-MM-dd"));
		}

		Map<String, Object> count = new HashMap<String, Object>() ;
		PageInfo<Map<String, Object>> countList = new PageInfo<Map<String,Object>>(null) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			countList = orderService.queryDayCompanyInfo(params, getPageInfo(cpage));
			count = orderService.queryDayCompanyCount(params);
		}else {
			params.put("ff", "1") ;
		}
		
		model.put("mapcount", count);
		model.put("list", countList);
		model.put("params", params);
		return "substatic/dayCompany";
	}

	@RequestMapping(value = { "/queryDaySubstationInfo" })
	public String queryDaySubstationInfo(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException, ParseException {		
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
	    String snoString = params.get("substationNo") ;
		if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			params.put("substationNo", substationNo);
		}
		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		if (StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtil.dateToString(DateUtil.getAddDay(-1), "yyyy-MM-dd"));
			params.put("endTime", DateUtil.dateToString(DateUtil.getAddDay(-1), "yyyy-MM-dd"));
		} else if (StringUtils.isEmptyWithTrim(beginTime) && !StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtil.dateToString(DateUtil.getAddDay(endTime, -7), "yyyy-MM-dd"));
		} else if (!StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("endTime", DateUtil.dateToString(DateUtil.getAddDay(beginTime, 7), "yyyy-MM-dd"));
		}

		
		Map<String, Object> count = new HashMap<String, Object>() ;
		PageInfo<Map<String, Object>> countList = new PageInfo<Map<String,Object>>(null) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			countList =  orderService.queryDaySubstationInfo(params, getPageInfo(cpage));
			count = orderService.queryDaySubstationCount(params);
		}else {
			params.put("ff", "1") ;
		}
		
		
		List<Map<String, Object>> substations  =new ArrayList<Map<String, Object>>();
		if("root".equals(bossUser.getRealName())	|| "admin".equals(bossUser.getRealName())){	
			substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
		}else{
			substations = userService.getCurrentSubstationByUser();

		}
        params.put("substationNo", StringUtils.nullString(snoString).trim()) ;
		model.put("mapcount", count);
		model.put("list", countList);
		model.put("params", params);
		model.put("substations", substations);
		return "substatic/daySubstation";
	}
   //
	@RequestMapping(value = { "/queryDayCourierInfo" })
	public String queryDayCourierInfo(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException, ParseException {
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		System.out.println(params);
	    String snoString = params.get("substationNo") ;
		if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			params.put("substationNo", substationNo);
		}
		
		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		if (StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtil.dateToString(DateUtil.getAddDay(-1), "yyyy-MM-dd"));
			params.put("endTime", DateUtil.dateToString(DateUtil.getAddDay(-1), "yyyy-MM-dd"));
		} else if (StringUtils.isEmptyWithTrim(beginTime) && !StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtil.dateToString(DateUtil.getAddDay(endTime, -7), "yyyy-MM-dd"));
		} else if (!StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("endTime", DateUtil.dateToString(DateUtil.getAddDay(beginTime, 7), "yyyy-MM-dd"));
		}

		
		
		PageInfo<Map<String, Object>> countList = new PageInfo<Map<String,Object>>(null) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			countList =  orderService.queryDayCourierInfoByWang(params, getPageInfo(cpage));
		}else {
			params.put("ff", "1") ;
		}
		
		BigDecimal takeCount = new BigDecimal("0");
		BigDecimal takeCash = new BigDecimal("0.00");
		BigDecimal takeMonth= new BigDecimal("0.00");
		BigDecimal takeHuiyuan = new BigDecimal("0.00");
		BigDecimal forCount= new BigDecimal("0");
		BigDecimal forSum = new BigDecimal("0.00");
		BigDecimal sendCount = new BigDecimal("0");
		BigDecimal sendCash = new BigDecimal("0");
		BigDecimal sendMonth = new BigDecimal("0.00");
		BigDecimal sendHuyuan = new BigDecimal("0.00");
		BigDecimal sendGoodPrice = new BigDecimal("0.00");
		BigDecimal sumCash = new BigDecimal("0.00");
		BigDecimal sumMonth = new BigDecimal("0.00");
		BigDecimal sumHuiyuan = new BigDecimal("0.00");
		BigDecimal sumAll = new BigDecimal("0.00");
		if (countList.getList()!=null) {
			for(Map<String,Object> map :countList.getList()){
				takeCount = takeCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeCount"))));
				takeCash = takeCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeCashCol"))));
				takeMonth = takeMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeMonthCol"))));
				takeHuiyuan = takeHuiyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeHuiyuanCol"))));
				forCount = forCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeForCount"))));
				forSum = forSum.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeForCol"))));
				sendCount = sendCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendCount"))));
				sendCash = sendCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendCashCol"))));
				sendMonth = sendMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendMonthCol"))));
				sendHuyuan = sendHuyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendHuiyuanCol"))));
				sendGoodPrice = sendGoodPrice.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendGoodPrice"))));
				sumCash = sumCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumCash"))));
				sumMonth = sumMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumMonth"))));
				sumHuiyuan = sumHuiyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumHuiyuan"))));
				sumAll = sumAll.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumAll"))));		
			}
		}
	
		
		List<Map<String, Object>> courierList  = new  ArrayList<Map<String, Object>>();
		if("root".equals(bossUser.getRealName())	|| "admin".equals(bossUser.getRealName())){	
			courierList = mobileUserService.getAllCourierList(params);
		}else{
			courierList = mobileUserService.getCourierListBySubStation(userService.getUserSubstationNoEx(bossUser.getId()));
		}
		
		List<Map<String, Object>> substations  =new ArrayList<Map<String, Object>>();
		if("root".equals(bossUser.getRealName())	|| "admin".equals(bossUser.getRealName())){	
			substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
		}else{
			substations = userService.getCurrentSubstationByUser();
		}	
		params.put("substationNo", StringUtils.nullString(snoString).trim()) ;	 
		model.put("list", countList);
		model.put("params", params);
		model.put("courierList", JsonUtil.toJson(courierList));
		model.put("substations", substations);
		
		model.put("takeCount", takeCount.toString());
		model.put("takeCash", takeCash.toString());
		model.put("takeMonth", takeMonth.toString());
		model.put("takeHuiyuan", takeHuiyuan.toString());
		model.put("forCount", forCount.toString());
		model.put("forSum", forSum.toString());
		model.put("sendCount",sendCount.toString());
		model.put("sendCash",sendCash.toString());
		model.put("sendMonth",sendMonth.toString());
		model.put("sendHuyuan",sendHuyuan.toString());
		model.put("sendGoodPrice",sendGoodPrice.toString());
		model.put("sumCash",sumCash.toString());
		model.put("sumMonth",sumMonth.toString());
		model.put("sumHuiyuan",sumHuiyuan.toString());
		model.put("sumAll",sumAll.toString());		
		return "substatic/dayCourier";
	}
	
	@RequestMapping(value = {"/queryDayCourierInfo2"})
	public String queryDayCourierInfo2(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException, ParseException {
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		System.out.println(params);
		String snoString = params.get("substationNo");
		if (StringUtils.isEmptyWithTrim(params.get("substationNo"))) {
			String substationNo;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			} else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			params.put("substationNo", substationNo);
		}

		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		if (StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtil.dateToString(DateUtil.getAddDay(-1), "yyyy-MM-dd"));
			params.put("endTime", DateUtil.dateToString(DateUtil.getAddDay(-1), "yyyy-MM-dd"));
		} else if (StringUtils.isEmptyWithTrim(beginTime) && !StringUtils.isEmptyWithTrim(endTime)) {
			params.put("beginTime", DateUtil.dateToString(DateUtil.getAddDay(endTime, -7), "yyyy-MM-dd"));
		} else if (!StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)) {
			params.put("endTime", DateUtil.dateToString(DateUtil.getAddDay(beginTime, 7), "yyyy-MM-dd"));
		}

		PageInfo<Map<String, Object>> countList = new PageInfo<Map<String,Object>>(null) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			params.put("unfor", "1") ;  //除去外件
			countList = orderService.queryDayCourierInfoByWang(params, getPageInfo(cpage));
		}else {
			params.put("ff", "1") ;
		}
		
		BigDecimal takeCount = new BigDecimal("0");
		BigDecimal takeCash = new BigDecimal("0.00");
		BigDecimal takeMonth = new BigDecimal("0.00");
		BigDecimal takeHuiyuan = new BigDecimal("0.00");
		BigDecimal takeWeight = new BigDecimal("0.00");
		BigDecimal forCount = new BigDecimal("0");
		BigDecimal forSum = new BigDecimal("0.00");
		BigDecimal takeForWeight = new BigDecimal("0.00");
		BigDecimal sendCount = new BigDecimal("0");
		BigDecimal sendCash = new BigDecimal("0");
		BigDecimal sendMonth = new BigDecimal("0.00");
		BigDecimal sendHuyuan = new BigDecimal("0.00");
		BigDecimal sendGoodPrice = new BigDecimal("0.00");
		BigDecimal sendWeight = new BigDecimal("0.00");
		BigDecimal sumCash = new BigDecimal("0.00");
		BigDecimal sumMonth = new BigDecimal("0.00");
		BigDecimal sumHuiyuan = new BigDecimal("0.00");
		BigDecimal sumAll = new BigDecimal("0.00");
		BigDecimal z同城寄件金额总计 = new BigDecimal("0.00");
		if (countList.getList()!=null) {
		for (Map<String, Object> map : countList.getList()) {
			takeCount = takeCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeCount"))));
			takeCash = takeCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeCashCol"))));
			takeMonth = takeMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeMonthCol"))));
			takeHuiyuan = takeHuiyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeHuiyuanCol"))));
			takeWeight = takeWeight.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeWeight"))));
			forCount = forCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeForCount"))));
			forSum = forSum.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeForCol"))));
			takeForWeight = takeForWeight.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("takeForWeight"))));
			sendCount = sendCount.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendCount"))));
			sendCash = sendCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendCashCol"))));
			sendMonth = sendMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendMonthCol"))));
			sendHuyuan = sendHuyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendHuiyuanCol"))));
			sendGoodPrice = sendGoodPrice.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendGoodPrice"))));
			sendWeight = sendWeight.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sendWeight"))));
			sumCash = sumCash.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumCash"))));
			sumMonth = sumMonth.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumMonth"))));
			sumHuiyuan = sumHuiyuan.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumHuiyuan"))));
			sumAll = sumAll.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("sumAll"))));
			BigDecimal z同城寄件金额 = new BigDecimal("0.00");
			//for (String t : new String[]{"takeCashCol", "takeMonthCol", "takeHuiyuanCol", "sendCashCol", "sendMonthCol", "sendHuiyuanCol"}) {
			//	z同城寄件金额 = z同城寄件金额.add(new BigDecimal(StringUtils.nullStringtoZero(map.get(t))));
			//}
			z同城寄件金额 = z同城寄件金额.add(new BigDecimal(StringUtils.nullStringtoZero(map.get("_sum"))));
			map.put("z同城寄件金额", z同城寄件金额);
			z同城寄件金额总计 = z同城寄件金额总计.add(z同城寄件金额);
		}
		}

		List<Map<String, Object>> courierList;
		if ("root".equals(bossUser.getRealName()) || "admin".equals(bossUser.getRealName())) {
			courierList = mobileUserService.getAllCourierList(params);
		} else {
			courierList = mobileUserService.getCourierListBySubStation(userService.getUserSubstationNoEx(bossUser.getId()));
		}

		List<Map<String, Object>> substations;
		if ("root".equals(bossUser.getRealName()) || "admin".equals(bossUser.getRealName())) {
			substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
		} else {
			substations = userService.getCurrentSubstationByUser();
		}
		params.put("substationNo", StringUtils.nullString(snoString).trim());
		model.put("list", countList);
		model.put("params", params);
		model.put("courierList", JsonUtil.toJson(courierList));
		model.put("substations", substations);

		model.put("takeCount", takeCount.toString());
		model.put("takeCash", takeCash.toString());
		model.put("takeMonth", takeMonth.toString());
		model.put("takeHuiyuan", takeHuiyuan.toString());
		model.put("takeWeight", takeWeight.toString());
		model.put("forCount", forCount.toString());
		model.put("forSum", forSum.toString());
		model.put("takeForWeight", takeForWeight.toString());
		model.put("sendCount", sendCount.toString());
		model.put("sendCash", sendCash.toString());
		model.put("sendMonth", sendMonth.toString());
		model.put("sendHuyuan", sendHuyuan.toString());
		model.put("sendGoodPrice", sendGoodPrice.toString());
		model.put("sendWeight", sendWeight.toString());
		model.put("sumCash", sumCash.toString());
		model.put("sumMonth", sumMonth.toString());
		model.put("sumHuiyuan", sumHuiyuan.toString());
		model.put("sumAll", sumAll.toString());
		model.put("sumAll", sumAll.toString());
		model.put("z同城寄件金额总计", z同城寄件金额总计);
		return "substatic/dayCourier2";
	}
	/**
	 * 新增备注
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@RequestMapping(value = "/monthNote")
	public String deleteOrder(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response) throws InterruptedException, IOException {
		System.out.println(params);

		model.put("params", params);
		return "substatic/monthNote";
	}

	/**
	 * 删除票段
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@RequestMapping(value = "/addNote")
	public void addNote(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response) throws InterruptedException, IOException {
		scanStaticsService.saveOrderNote(params);
		outText("成功添加备注", response);
	}

	/**
	 * 发送邮件
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception 
	 * 
	 * 	private String smtp = ""; // 邮件服务器主机名
	private String protocol = ""; // 邮件传输协议
	private String username = ""; // 登录用户名
	private String password = ""; // 登录密码
	private String from = ""; // 发件人地址
	private String to = ""; // 收件人地址
	private String subject = ""; // 邮件主题
	private String body = ""; // 邮件内容
	private String nick = ""; // 发件人昵称

	 * 
	 */
	@SuppressWarnings("static-method")
	@RequestMapping(value = "/sendEmail")
	public void sendEmail(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isYx =false;
		String host = "http://"+request.getHeader("host") ;
		if(request.getServerName().contains("yxboss.yogapay.com")||"1131".equals(Constants.getUser().getLgcNo())){
			isYx =true;
		}else{
			isYx =false;
		}
		String lgcOrderNoList = params.get("lgcOrderNoList");	
		System.out.println(params);
		
		if(!StringUtils.isEmptyWithTrim(lgcOrderNoList)){
			Map<String,String> sendMpa = new HashMap<String,String>();
			lgcOrderNoList = 	lgcOrderNoList.substring(0, lgcOrderNoList.length()-1);
		
			String lgcOrderNo[] = lgcOrderNoList.split(",");
			sendMpa.put("smtp", "smtp.exmail.qq.com");
			sendMpa.put("protocol", "smtp");
			sendMpa.put("username", "zentaopms@yogapay.com");
			sendMpa.put("password", "zentao0732");
			sendMpa.put("from", "zentaopms@yogapay.com");	
			Lgc lgc = lgcService.getLgcByNo(Constants.getUser().getLgcNo());
			if(isYx){
				sendMpa.put("nick", "亿翔集团");	
			}else{
				sendMpa.put("nick", lgc.getName());	
			}
			
			params.put("lgcOrderNo", lgcOrderNoList);
			List<String> monthNoList = scanStaticsService.getEmail(params);//查询所有单号的月结信息
			for(String montNo:monthNoList){	
				BigDecimal allMpay = new BigDecimal("0.00");
				BigDecimal allDiscountMoney = new BigDecimal("0.00");
				sendMpa.put("subject", "月结用户收派件明细");				
				params.put("monthNo", montNo);
				List<Map<String,Object>> listDetail = scanStaticsService.getMonthDetail(params);
				Map<String,Object> discountMap= scanStaticsService.getMonthInfo(params);
				if(StringUtil.isEmptyString(discountMap.get("email"))){
					continue;
				}
				System.out.println("--------------------折扣率--"+discountMap+"-------------------"+listDetail.size());
				String ths[]={"序号","运单编号","寄件日期","重量","目的地","签收时间","签收人","寄付","到付","应收","折扣","实收","收件员","派件员","寄件人","备注","图片链接"};
				String thStr="";
				for(String th :ths){
					thStr+="<th align=\"center\" style=\"border:1px solid #000; \">"+th+"</th>";
				}
				String lgcName = lgc.getName();
				String[] lgcNames= lgcName.split("");
				lgcName="";
				for (int i = 0; i < lgcNames.length; i++) {
					lgcName+=lgcNames[i]+"&nbsp;&nbsp;";
				}
				String body ="<div>尊敬的月结客户&nbsp;"+discountMap.get("contactName")+"&nbsp;您好：感谢你选择"+(isYx==true?"亿翔同城":lgc.getName())+"快递，这是您&nbsp;"+params.get("beginTime")+"&nbsp;到&nbsp;"+params.get("endTime")+"&nbsp;收派件明细：</div>"+
						"<div style=\"font-size:20px; text-align:center;\"><strong >"+(isYx==true?"亿&nbsp;&nbsp;翔&nbsp;&nbsp;":lgcName)+"快&nbsp;&nbsp;递&nbsp;&nbsp;客&nbsp;&nbsp;户&nbsp;&nbsp;月&nbsp;&nbsp;结&nbsp;&nbsp;对&nbsp;&nbsp;账&nbsp;&nbsp;单</strong></div></br>" 		
						+ "<table style=\" border:1px solid #000;width:100%;border-collapse: collapse;\">"
						+ "<tr>"+thStr+"</tr>";			
				
				for(Map<String,Object> emailInfoMap:listDetail){
					scanStaticsService.updateEmailStatus((String)emailInfoMap.get("lgcOrderNo"));
					BigDecimal mpay = new BigDecimal((String.valueOf(emailInfoMap.get("mpay"))));//获取单笔金额
					BigDecimal discount = new BigDecimal((String.valueOf(discountMap.get("discount"))));//获取折扣率					
					BigDecimal discountMoney = mpay.multiply(discount).divide(new BigDecimal("100"));//计算折扣后金额
					BigDecimal rest = mpay.subtract(discountMoney);//节省金额
					body = body+									
							"<tr><td align=\"center\" style=\"border:1px solid #000; \">"+emailInfoMap.get("rowno") +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("lgcOrderNo")) +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("takeOrderTime")) +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("itemWeight")) +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("revArea")) +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("sendOrderTime")) +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("signType")) +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("takeMoney")) +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("sendMoney")) +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("mpay"))+"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+rest.toString() +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+discountMoney.toString() +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("takeCourierName") )+"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("sendCourierName") )+"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("sendName")) +"</td>"+
							"<td align=\"center\" style=\"border:1px solid #000; \">"+StringUtils.nullString(emailInfoMap.get("monthNote") )+"</td>" ;
					
					if (!StringUtil.isEmptyWithTrim(StringUtils.nullString(emailInfoMap.get("take_plane")))) {
						body += "<td align=\"center\" style=\"border:1px solid #000; \">"+host+StringUtils.nullString(emailInfoMap.get("take_plane") )+"</td></tr>";		
					}else {
						if (!StringUtil.isEmptyWithTrim(StringUtils.nullString(emailInfoMap.get("send_plane")))) {
							body +="<td align=\"center\" style=\"border:1px solid #000; \">"+host+StringUtils.nullString(emailInfoMap.get("send_plane") )+"</td></tr>";	
						}else {
							body +="<td align=\"center\" style=\"border:1px solid #000; \"></td></tr>";	
						}
							
					}
					
					
					
					allMpay=allMpay.add(mpay);//计算总金额
					allDiscountMoney = allDiscountMoney.add(discountMoney);//计算总金额折扣后金额
				}
				String strTmp ="</table>"
						+ "<table style=\" border:0;width:100%\">"
						+ "<tr><td style=\"font-size:14px;\">客户帐号："+StringUtils.nullString(discountMap.get("monthSettleNo"))+"</td><td style=\"font-size:14px;\">结算期间："+params.get("beginTime")+"&nbsp;到&nbsp;"+params.get("endTime")+"<td>"
						+ "<tr><td style=\"font-size:14px;\">客户名称："+StringUtils.nullString(discountMap.get("monthSname"))+"</td><td style=\"font-size:14px;\">结算金额："+allMpay.toString()+"&nbsp;&nbsp;折扣金额："+allDiscountMoney.toString()+"<td>"
						+ "<tr><td style=\"font-size:14px;\">联系人："+StringUtils.nullString(discountMap.get("contactName"))+"</td><td style=\"font-size:14px;\">制表人："+"<td>"
						+ "<tr><td style=\"font-size:14px;\">联系地址："+StringUtils.nullString(discountMap.get("contactAddr"))+"</td><td style=\"font-size:14px;\">"
						+ "</table>";
				if(isYx){
					strTmp += 
							 "◆月结客户可订阅”电子账单服务”，只要贵公司提供准确的电子邮箱，即可自动查询和收阅账单。</br>"
							+ "◆请保留好运单，以便核对账单。并请于每月协议承诺日前，支付上月运费及相关费用。</br>"
							+ "◆为了您的资金安全，希望您在付款时优先选择支票、转账、等非现金支付方式结算运费。</br>"
							+ "◆我公司相关资料如下：</br>"
							+ "<table style=\" border:0;width:80%\">"
							+ "<tr><td style=\"font-size:14px;\">公司账户：亿翔供应链（深圳）有限责任公司	</td><td style=\"font-size:14px;\">个人帐户：何军 </td></tr>"
							+ "<tr><td style=\"font-size:14px;\">开户银行：平安银行深圳新秀支行	</td><td style=\"font-size:14px;\">	开户银行：招商银行深圳文锦渡支行</td></tr>"
							+ "<tr><td style=\"font-size:14px;\">银行账号：11014836018000（RMB）	</td><td style=\"font-size:14px;\">银行帐号：6214 8578 0668 9859 </td></tr>"
							+ "</table> "
							+ "◆尊敬的客户，现将上个月对帐单送上，请即予核对。联系人：陈小姐，电话：0755-33033696 ,QQ:3425141662	</br>"
							+ "◆如有不符，务必于3日内与我司联系，否则我公司将视本对帐单无误，谢谢！</br>";
				}
				body = body+strTmp
						+ "★本邮件请勿回复，谢谢合作！</br>";

				sendMpa.put("body",body);
				sendMpa.put("to", String.valueOf(discountMap.get("email")));		  		  
				//				 		sendMpa.put("to", "609708791@qq.com");		  		  
				new SendMail(sendMpa,null,null).send();			
			}
			outText("成功发送邮件", response);
		}else{
			outText("请选择需要发送邮件的订单", response);
		}	
	}
	
	@RequestMapping(value = "/codPay")
	public String codPay(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.put("params", params);
		return "substatic/cod_pay";
	}
	
	// 用于保存
		@RequestMapping(value = { "/cod_pay_save" })
		public void cod_pay_save(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response){
			try {
                String orderNos = params.get("orderNos") ;
                String payType = params.get("payType") ;
                String payName = Constants.getUser().getRealName() ;
                String payTime = DateUtils.formatDate(new Date()) ;
                
                List<CodInfo> codInfos = new ArrayList<CodInfo>() ;
                if (!StringUtils.isEmptyWithTrim(orderNos)) {
					codInfoService.delete(orderNos);
					String[] olist = orderNos.split(",") ;
					for (int i = 0; i < olist.length; i++) {
						if (!"0".equals(olist[i])) {
							CodInfo codInfo = new CodInfo() ;
							codInfo.setOrderNo(olist[i]);
							codInfo.setPayStatus(1);
							codInfo.setPayType(payType);
							codInfo.setPayName(payName);
							codInfo.setPayTime(payTime);
							codInfos.add(codInfo) ;
						}
					}
					codInfoService.batchSave(codInfos);
				}
				outText("1", response);
			} catch (Exception e) {
				e.printStackTrace();
				outText("保存失败", response);
			}
		}
	
				@RequestMapping(value = { "/cod_nopay" })
				public void cod_nopay(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response){
					try {
		                String id = params.get("id") ;
		                if (!StringUtils.isEmptyWithTrim(id)) {
		                	codInfoService.nopay(Integer.valueOf(id));
						}
						outText("1", response);
					} catch (Exception e) {
						e.printStackTrace();
						outText("保存失败", response);
					}
				}
	
				//
				@RequestMapping(value = { "/cpay_rate" })
				public void cpay_rate(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response){
					try {
						 String orderNos = StringUtils.nullString(params.get("ids")) ;
						 String rate = StringUtils.nullString(params.get("rate")) ;
						 if (rate.length()<1||rate.length()>8) {
							outText("数据有误", response);
							return ;
						 }
						float r = Float.valueOf(rate) ; 
						 
		                if (!StringUtils.isEmptyWithTrim(orderNos)) {
		                	codInfoService.cpayRate(orderNos, r);
						}
						outText("1", response);
					} catch (Exception e) {
						e.printStackTrace();
						outText("保存失败", response);
					}
				}
				@RequestMapping(value = { "/checkMonth" })
				public void checkMonth(HttpServletResponse response,@RequestParam(value="monthNo")String monthNo){
					Map<String, Object> map = mobileUserService.getMuserByNo(monthNo);
					Map<String,Object> map1 = new HashMap<String, Object>();
						if(map==null){
							map1.put("code", "1");
							map1.put("message", "月结用户不存在，请检查后导出！");
							outJson(JsonUtil.toJson(map1), response);
							return ;
						}						
						map1.put("code", "0");				
					outJson(JsonUtil.toJson(map1), response);
					return ;
				}
}
