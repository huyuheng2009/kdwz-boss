package com.yogapay.boss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.service.ScanExService;
import com.yogapay.boss.utils.StringUtils;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@Controller
@RequestMapping("/scanDetail")
public class SubScanDetailExportController  extends BaseController{
	
	@Resource
	private ScanExService scanExService;
	
	@RequestMapping("/export")
	public void export(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response){
		String serviceName = params.get("serviceName");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		OutputStream os = null;
		Date curDate =new Date();
		try {
			request.setCharacterEncoding("UTF-8");
			os = response.getOutputStream(); // 取得输出流
			response.reset(); // 清空输出流
			response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
			if("takeDetail".equals(serviceName)){
				String fileName= "收件明细-"+sdf.format(curDate)+".xls";
				response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
				exportData(os, 1 ,params );
			}
			if("sendDetail".equals(serviceName)){
				String fileName= "派件明细-"+sdf.format(curDate)+".xls";
				response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
				exportData(os, 2 ,params );
			}
			if("inCount".equals(serviceName)){
				String fileName= "到站明细-"+sdf.format(curDate)+".xls";
				response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
				exportData(os, 3 ,params );
			}
			if("outCount".equals(serviceName)){
				String fileName= "出站明细-"+sdf.format(curDate)+".xls";
				response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
				exportData(os, 4 ,params );
			}
			if("errorCount".equals(serviceName)){
				String fileName= "问题件明细-"+sdf.format(curDate)+".xls";
				response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
				exportData(os, 5 ,params );
			}
			if("complateOrder".equals(serviceName)){
				String fileName= "签收票明细-"+sdf.format(curDate)+".xls";
				response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
				exportData(os, 6 ,params );
			}
			if("inAndOut".equals(serviceName)){
				String fileName= "有到站有出站明细-"+sdf.format(curDate)+".xls";
				response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
				exportData(os, 7 ,params );
			}
			if("inNotOut".equals(serviceName)){
				String fileName= "有到站无出站明细-"+sdf.format(curDate)+".xls";
				response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
				exportData(os, 8 ,params );
			}
			if("noInhaveOutSend".equals(serviceName)){
				String fileName= "无到站有派件明细-"+sdf.format(curDate)+".xls";
				response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
				exportData(os, 9 ,params );
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void exportData(OutputStream os,int num,Map<String, String> params) {
		int col =0;
		int row =1;
		if(num==1 || num==2){
			String courierNo = params.get("courierNo");
			 if (!StringUtils.isEmptyWithTrim(courierNo)) {
		            if (courierNo.contains("(")) {
		                courierNo = courierNo.substring(0, courierNo.indexOf("("));
		                params.put("courierNo", courierNo);
		            }
		        }
		     PageInfo<Map<String, Object>> current = null;
		     if(num==1){
		    	 current=scanExService.getTakeDetailList(params, getPageInfo(1,20000));
		     }
		     if(num==2){
		    	 current = scanExService.getSendDetailList(params, getPageInfo(1,20000));
		     }
		     
		     Iterator<Map<String, Object>> it =  current.getList().iterator();
		     Map<String, Object> map =null;
		     
		     Workbook wb =null;
		     WritableWorkbook wwb =null;
		     WritableSheet ws =null;
			try {
				if(num==1){
					wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/takeDetail.xls"));
				}
				if(num==2){
					wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/sendDetail.xls"));
				}
				wwb = Workbook.createWorkbook(os, wb);
				ws = wwb.getSheet(0);
				while(it.hasNext()){
			    	 map= it.next();
			    	 ws.addCell(new Label(col++, row, row+""));
					 ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeOrderTime")) ));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgcOrderNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("subSationNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substationName"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("takeCourierNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("realName"))));
						row++;
						col = 0;
			     }
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		
		if(num==3 || num==4){
			String courierNo = params.get("courierNo");
			if (!StringUtils.isEmptyWithTrim(courierNo)) {
				if (courierNo.contains("(")) {
					courierNo = courierNo.substring(0, courierNo.indexOf("("));
					params.put("courierNo", courierNo);
				}
			}
			 String substationNo = params.get("substationNo");
		        if (!StringUtils.isEmptyWithTrim(substationNo)) {
		            if (substationNo.contains("(")) {
		                substationNo = substationNo.substring(0, substationNo.indexOf("("));
		                params.put("substationNo", substationNo);
		            }
		        }
			PageInfo<Map<String, Object>> current = null;
			if(num==3){
				current=scanExService.getInCount(params, getPageInfo(1,20000));
			}
			if(num==4){
				current = scanExService.getOutCount(params, getPageInfo(1,20000));
			}
		     Iterator<Map<String, Object>> it =  current.getList().iterator();
		     Map<String, Object> map =null;
		     
		     Workbook wb =null;
		     WritableWorkbook wwb =null;
		     WritableSheet ws =null;
			try {
				if(num==3){
					wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/inCount.xls"));
				}
				if(num==4){
					wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/outCount.xls"));
				}
				wwb = Workbook.createWorkbook(os, wb);
				ws = wwb.getSheet(0);
				while(it.hasNext()){
			    	 map= it.next();
			    	 ws.addCell(new Label(col++, row, row+""));
					 ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("orderTime")) ));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgcOrderNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subName"))));
						if(num==3){
							ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("preNo"))));
						}
						if(num==4){
							ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("nextNo"))));
						}
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substationName"))));
						if(num==3){
							ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("scanIno"))));
							ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("scanIname"))));
						}
						if(num==4){
							ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("scanono"))));
							ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("scanOname"))));
						}
						row++;
						col = 0;
			     }
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		
		
		if(num==5){
			String courierNo = params.get("courierNo");//扫描员
			if (!StringUtils.isEmptyWithTrim(courierNo)) {
				if (courierNo.contains("(")) {
					courierNo = courierNo.substring(0, courierNo.indexOf("("));
					params.put("courierNo", courierNo);
				}
			}
			String statusNo = params.get("statusNo");//处理状态
			String reasonNo = params.get("reasonNo");//问题原因
			PageInfo<Map<String, Object>> current = scanExService.getErrorCount(params, getPageInfo(1,20000));
		     Iterator<Map<String, Object>> it =  current.getList().iterator();
		     Map<String, Object> map =null;
		     
		     Workbook wb =null;
		     WritableWorkbook wwb =null;
		     WritableSheet ws =null;
			try {
				wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/errorCount.xls"));
				wwb = Workbook.createWorkbook(os, wb);
				ws = wwb.getSheet(0);
				while(it.hasNext()){
			    	 map= it.next();
			    	 ws.addCell(new Label(col++, row, row+""));
					 ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("orderTime")) ));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgcOrderNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("createTime"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subName"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("checkName"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("realName"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("monthNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("content"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("context"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("descb"))));
						row++;
						col = 0;
			     }
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		
		
		if(num==6){
			String courierNo = params.get("courierNo");
			if (!StringUtils.isEmptyWithTrim(courierNo)) {
				if (courierNo.contains("(")) {
					courierNo = courierNo.substring(0, courierNo.indexOf("("));
					params.put("courierNo", courierNo);
				}
			}
			PageInfo<Map<String, Object>> current = scanExService.getComplateOrderCount(params, getPageInfo(1,20000));
		     Iterator<Map<String, Object>> it =  current.getList().iterator();
		     Map<String, Object> map =null;
		     Workbook wb =null;
		     WritableWorkbook wwb =null;
		     WritableSheet ws =null;
			try {
				wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/complateOrder.xls"));
				wwb = Workbook.createWorkbook(os, wb);
				ws = wwb.getSheet(0);
				while(it.hasNext()){
			    	 map= it.next();
			    	 ws.addCell(new Label(col++, row, row+""));
					 ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendOrderTime")) ));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgcOrderNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subName"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("innerNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("realName"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sinputor"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sinputorEx"))));
						row++;
						col = 0;
			     }
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		
		if(num==7){
			String inCourierNo = params.get("inCourierNo");
			if (!StringUtils.isEmptyWithTrim(inCourierNo)) {
				if (inCourierNo.contains("(")) {
					inCourierNo = inCourierNo.substring(0, inCourierNo.indexOf("("));
					params.put("inCourierNo", inCourierNo);
				}
			}
			 String outCourierNo = params.get("outCourierNo");
		        if (!StringUtils.isEmptyWithTrim(outCourierNo)) {
		            if (outCourierNo.contains("(")) {
		            	outCourierNo = outCourierNo.substring(0, outCourierNo.indexOf("("));
		                params.put("substationNo", outCourierNo);
		            }
		        }
			PageInfo<Map<String, Object>> current = scanExService.getInAndOutCount(params, getPageInfo(1,20000));
		     Iterator<Map<String, Object>> it =  current.getList().iterator();
		     Map<String, Object> map =null;
		     Workbook wb =null;
		     WritableWorkbook wwb =null;
		     WritableSheet ws =null;
			try {
				wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/inAndOut.xls"));
				wwb = Workbook.createWorkbook(os, wb);
				ws = wwb.getSheet(0);
				while(it.hasNext()){
			    	 map= it.next();
			    	 ws.addCell(new Label(col++, row, row+""));
					 ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendOrderTime")) ));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgcOrderNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subName"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("preNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("preSubstationName"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("scanIname"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("outTime"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("nextNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substationName"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("scanOname"))));
						row++;
						col = 0;
			     }
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		
		if(num==8){
			String inCourierNo = params.get("inCourierNo");
			if (!StringUtils.isEmptyWithTrim(inCourierNo)) {
				if (inCourierNo.contains("(")) {
					inCourierNo = inCourierNo.substring(0, inCourierNo.indexOf("("));
					params.put("inCourierNo", inCourierNo);
				}
			}
			PageInfo<Map<String, Object>> current = scanExService.getInNotOutCount(params, getPageInfo(1,20000));
		     Iterator<Map<String, Object>> it =  current.getList().iterator();
		     Map<String, Object> map =null;
		     Workbook wb =null;
		     WritableWorkbook wwb =null;
		     WritableSheet ws =null;
			try {
				wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/inNotOut.xls"));
				wwb = Workbook.createWorkbook(os, wb);
				ws = wwb.getSheet(0);
				while(it.hasNext()){
			    	 map= it.next();
			    	 ws.addCell(new Label(col++, row, row+""));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgcOrderNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("inTime"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subName"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("scanIno"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("scanIname"))));
						ws.addCell(new Label(col++, row, "/"));
						ws.addCell(new Label(col++, row, "/"));
						ws.addCell(new Label(col++, row, "/"));
						row++;
						col = 0;
			     }
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		
		if(num==9){
			String courierNo = params.get("courierNo");
			if (!StringUtils.isEmptyWithTrim(courierNo)) {
				if (courierNo.contains("(")) {
					courierNo = courierNo.substring(0, courierNo.indexOf("("));
					params.put("courierNo", courierNo);
				}
			}		
			PageInfo<Map<String, Object>> current = scanExService.noInhaveOutSend(params, getPageInfo(1,20000));
		     Iterator<Map<String, Object>> it =  current.getList().iterator();
		     Map<String, Object> map =null;
		     Workbook wb =null;
		     WritableWorkbook wwb =null;
		     WritableSheet ws =null;
			try {
				wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/noInhaveOutSend.xls"));
				wwb = Workbook.createWorkbook(os, wb);
				ws = wwb.getSheet(0);
				while(it.hasNext()){
			    	 map= it.next();
			    	 ws.addCell(new Label(col++, row, row+""));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgcOrderNo"))));
						ws.addCell(new Label(col++, row, "/"));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendOrderTime"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(params.get("subName"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("sendCourierNo"))));
						ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("realName"))));
						row++;
						col = 0;
			     }
				wwb.write();
				wwb.close();
				wb.close();
				os.close();
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		
	}
}
