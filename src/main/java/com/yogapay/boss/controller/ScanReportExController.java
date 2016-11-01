package com.yogapay.boss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
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
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.http.impl.cookie.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.enums.CheckTrackStatus;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.ScanExService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

@Controller
@RequestMapping(value="/scanEx")
public class ScanReportExController   extends BaseController{

	@Resource
	private ScanExService scanExService;
	@Resource
	private SubstationService substationService ;
	@Resource
	private CourierService courierService ;
	

	
	/**
	 * 收件明细
	 * @param params
	 * @param model 
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/takeDetail")
	public String takeDetail(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
            HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>>  currentCourierList = scanExService.allCourierBySub(params);
		String courierNo = params.get("courierNo");
		 if (!StringUtils.isEmptyWithTrim(courierNo)) {
	            if (courierNo.contains("(")) {
	                courierNo = courierNo.substring(0, courierNo.indexOf("("));
	                params.put("courierNo", courierNo);
	            }
	        }
	     PageInfo<Map<String, Object>> current = scanExService.getTakeDetailList(params, getPageInfo(cpage));
		
	
		model.put("courierList", JsonUtil.toJson(currentCourierList));
		model.put("list", current);	
		model.put("params", params);	
		return "substaticEx/takeDetail";
	}
	
	/**
	 * 派件明细
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/sendDetail")
	public String sendDetail(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>>  currentCourierList = scanExService.allCourierBySub(params);
		String courierNo = params.get("courierNo");
		if (!StringUtils.isEmptyWithTrim(courierNo)) {
			if (courierNo.contains("(")) {
				courierNo = courierNo.substring(0, courierNo.indexOf("("));
				params.put("courierNo", courierNo);
			}
		}
		PageInfo<Map<String, Object>> current = scanExService.getSendDetailList(params, getPageInfo(cpage));
		
		
		model.put("courierList", JsonUtil.toJson(currentCourierList));
		model.put("list", current);	
		model.put("params", params);	
		return "substaticEx/sendDetail";
	}
	/**
	 * 到站详情
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/inCount")
	public String inCount(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>>  currentCourierList = scanExService.allManager(params);//查询所有扫描员
		List<Map<String, Object>> substations = scanExService.allSubNo();
		
		
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
		PageInfo<Map<String, Object>> current = scanExService.getInCount(params, getPageInfo(cpage));
		model.put("courierList", JsonUtil.toJson(currentCourierList));
		model.put("substationList", JsonUtil.toJson(substations));
		model.put("list", current);	
		model.put("params", params);	
		return "substaticEx/inCount";
	}
	/**
	 * 出站详情
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/outCount")
	public String outCount(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>>  currentCourierList = scanExService.allManager(params);//查询所有扫描员
		List<Map<String, Object>> substations = scanExService.allSubNo();		
		
		String courierNo = params.get("courierNo");//扫描员
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
		PageInfo<Map<String, Object>> current = scanExService.getOutCount(params, getPageInfo(cpage));
		model.put("courierList", JsonUtil.toJson(currentCourierList));
		model.put("substationList", JsonUtil.toJson(substations));
		model.put("list", current);	
		model.put("params", params);	
		return "substaticEx/outCount";
	}
	/**
	 *  问题件
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/errorCount")
	public String errorCount(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>>  currentCourierList = scanExService.allManager(params);//查询所有扫描员
		List<Map<String, Object>> proReasonList = scanExService.allErrorReason();		//所有问题原因
		List<Map<String, Object>> proStatusList = scanExService.allErrorProStatus();		//所有问题原因	
		String courierNo = params.get("courierNo");//扫描员
		if (!StringUtils.isEmptyWithTrim(courierNo)) {
			if (courierNo.contains("(")) {
				courierNo = courierNo.substring(0, courierNo.indexOf("("));
				params.put("courierNo", courierNo);
			}
		}
		String statusNo = params.get("statusNo");//处理状态
		String reasonNo = params.get("reasonNo");//问题原因
		
		
		PageInfo<Map<String, Object>> current = scanExService.getErrorCount(params, getPageInfo(cpage));
		System.out.println(params);
		model.put("courierList", JsonUtil.toJson(currentCourierList));
		model.put("proReasonList", proReasonList);
		model.put("proStatusList", proStatusList);
		model.put("list", current);	
		model.put("params", params);	
		return "substaticEx/errorCount";
	}
	/**
	 * 签收票数
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/complateOrder")
	public String complateOrder(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>>  currentCourierList = scanExService.allCourierBySub(params);//查询所有快递员
		
		String courierNo = params.get("courierNo");
		if (!StringUtils.isEmptyWithTrim(courierNo)) {
			if (courierNo.contains("(")) {
				courierNo = courierNo.substring(0, courierNo.indexOf("("));
				params.put("courierNo", courierNo);
			}
		}
		
		PageInfo<Map<String, Object>> current = scanExService.getComplateOrderCount(params, getPageInfo(cpage));
		model.put("courierList", JsonUtil.toJson(currentCourierList));
	
		model.put("list", current);	
		model.put("params", params);	
		return "substaticEx/complateOrder";
	}
	/**
	 * 有到站有出站
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/inAndOut")
	public String inAndOut(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>>  currentCourierList = scanExService.allManager(params);//查询所有扫描员
	
		
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
		PageInfo<Map<String, Object>> current = scanExService.getInAndOutCount(params, getPageInfo(cpage));
		model.put("courierList", JsonUtil.toJson(currentCourierList));
		model.put("list", current);	
		model.put("params", params);	
		return "substaticEx/inAndOut";
	}
	/**
	 * 有到站无出站
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/inNotOut")
	public String inNotOut(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>>  currentCourierList = scanExService.allManager(params);//查询所有扫描员
		
		
		String inCourierNo = params.get("inCourierNo");
		if (!StringUtils.isEmptyWithTrim(inCourierNo)) {
			if (inCourierNo.contains("(")) {
				inCourierNo = inCourierNo.substring(0, inCourierNo.indexOf("("));
				params.put("inCourierNo", inCourierNo);
			}
		}
		PageInfo<Map<String, Object>> current = scanExService.getInNotOutCount(params, getPageInfo(cpage));
		model.put("courierList", JsonUtil.toJson(currentCourierList));
		model.put("list", current);	
		model.put("params", params);	
		return "substaticEx/inNotOut";
	}
	/**
	 * 无到站有出站
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/noInhaveOut")
	public String noInhaveOut(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>>  currentCourierList = scanExService.allManager(params);//查询所有扫描员
		List<Map<String, Object>> substations = scanExService.allSubNo();		
		
		String outCourierNo = params.get("outCourierNo");
		if (!StringUtils.isEmptyWithTrim(outCourierNo)) {
			if (outCourierNo.contains("(")) {
				outCourierNo = outCourierNo.substring(0, outCourierNo.indexOf("("));
				params.put("outCourierNo", outCourierNo);
			}
		}
		String substationNo = params.get("substationNo");
		if (!StringUtils.isEmptyWithTrim(substationNo)) {
			if (substationNo.contains("(")) {
				substationNo = substationNo.substring(0, substationNo.indexOf("("));
				params.put("substationNo", substationNo);
			}
		}
		PageInfo<Map<String, Object>> current = null;//scanExService.getInNotOutCount(params, getPageInfo(cpage));
		model.put("courierList", JsonUtil.toJson(currentCourierList));
		model.put("substationList", JsonUtil.toJson(substations));
		model.put("list", current);	
		model.put("params", params);	
		return "substaticEx/noInhaveOut";
	}
	/**
	 *无到站有派件
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/noInhaveOutSend")
	public String noInhaveOutSend(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>>  currentCourierList = scanExService.allCourierBySub(params);
		String courierNo = params.get("courierNo");
		if (!StringUtils.isEmptyWithTrim(courierNo)) {
			if (courierNo.contains("(")) {
				courierNo = courierNo.substring(0, courierNo.indexOf("("));
				params.put("courierNo", courierNo);
			}
		}		
		PageInfo<Map<String, Object>> current = scanExService.noInhaveOutSend(params, getPageInfo(cpage));
		model.put("courierList", JsonUtil.toJson(currentCourierList));
		model.put("list", current);	
		model.put("params", params);	
		return "substaticEx/noInhaveOutSend";
	}
	/**
	 * 有到站无派件
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/inNotSend")
	public String inNotSend(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>>  currentCourierList = scanExService.allManager(params);//查询所有扫描员
		String inCourierNo = params.get("inCourierNo");
		if (!StringUtils.isEmptyWithTrim(inCourierNo)) {
			if (inCourierNo.contains("(")) {
				inCourierNo = inCourierNo.substring(0, inCourierNo.indexOf("("));
				params.put("inCourierNo", inCourierNo);
			}
		}		
		PageInfo<Map<String, Object>> current = null;//scanExService.getInNotOutCount(params, getPageInfo(cpage));
		model.put("courierList", JsonUtil.toJson(currentCourierList));
		model.put("list", current);	
		model.put("params", params);	
		return "substaticEx/inNotSend";
	}
	
	/**
	 * 运单扫描记录查询
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @param cpage
	 */
	@RequestMapping(value="/orderScan")
	public String orderScan(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		System.out.println(params);
		Map<String,Object> map = new HashMap<String, Object>();
//		List<Map<String,Object>>  currentManagerList = scanExService.allManager(params);//查询所有扫描员
//		List<Map<String,Object>>  currentCourierList = scanExService.allCourierBySub(params);//所有快递员
//		List<Map<String, Object>> substationList= scanExService.allSubNo();	//所有分站
		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		Date date  = new Date();
		if(StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)){
			params.put("beginTime",DateUtils.formatDate(date, "yyyy-MM-dd"));
			params.put("endTime",  DateUtils.formatDate(date, "yyyy-MM-dd"));
		}
		String statusNo = params.get("statusNo");//状态
		if(!StringUtil.isEmptyWithTrim(statusNo)){
			params.put("status", CheckTrackStatus.getType(statusNo));				
		}	
		String orderNo = params.get("orderNo");
		
		if (!StringUtil.isEmptyWithTrim(orderNo)) {
			String o = "'0'" ;
			String[] ods = orderNo.split("\r\n") ;
			for (int i = 0; i < ods.length; i++) {
				o=o+ ",'"+ods[i]+"'" ;
			}
			params.put("orderNo", o) ;
		}
	
	
		String curSubstationNo = params.get("curSubstationNo");
		if (!StringUtils.isEmptyWithTrim(curSubstationNo)) {
			if (curSubstationNo.contains("(")) {
				curSubstationNo = curSubstationNo.substring(0, curSubstationNo.indexOf("("));
				params.put("curSubstationNo", curSubstationNo);
			}
		}		
		String nextSubstationNo = params.get("nextSubstationNo");
		if (!StringUtils.isEmptyWithTrim(nextSubstationNo)) {
			if (nextSubstationNo.contains("(")) {
				nextSubstationNo = nextSubstationNo.substring(0, nextSubstationNo.indexOf("("));
				params.put("nextSubstationNo", nextSubstationNo);
			}
		}		
		String preSubstationNo = params.get("preSubstationNo");
		if (!StringUtils.isEmptyWithTrim(preSubstationNo)) {
			if (preSubstationNo.contains("(")) {
				preSubstationNo = preSubstationNo.substring(0, preSubstationNo.indexOf("("));
				params.put("preSubstationNo", preSubstationNo);
			}
		}		
		System.out.println("params==="+params);


		//第一次默认为空
		PageInfo<Map<String, Object>> current = new PageInfo<Map<String,Object>>(new ArrayList<Map<String, Object>>()) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			current =scanExService.getOrderScan(params, getPageInfo(cpage));
		}else {
			params.put("ff", "1") ;
		}
		List<Map<String, Object>> mapList = current.getList()==null?new ArrayList<Map<String, Object>>():current.getList();
		List<Map<String, Object>> newList  = new ArrayList<Map<String, Object>>();
		for(Map<String,Object> tarck : mapList){
			if(!StringUtil.isEmptyString(tarck.get("curNo"))){
				String curNo = 	(String)tarck.get("curNo");
				String curType = (String)tarck.get("curType");
			if("S".equals(curType)){
				tarck.put("curName", scanExService.getSubName(curNo))	;	
				Substation substation= substationService.getSubstationByNo(curNo) ;
				if (substation!=null) {
					tarck.put("curNo",substation.getInnerNo());
				}else {
					tarck.put("curNo","");
				}
			}
			if("C".equals(curType)){
				tarck.put("curName", scanExService.getCourierName(curNo))	;	
				Courier courier =  courierService.getCourierByNo(curNo) ;
				if (courier!=null) {
					tarck.put("curNo",courier.getInnerNo());
				}else {
					tarck.put("curNo","");
				}
			}				
			}
		
			if(!StringUtil.isEmptyString(tarck.get("preNo"))){//上一站信息
				String preNo = 	(String)tarck.get("preNo");
				String preType = (String)tarck.get("preType");
				if("S".equals(preType)){
					tarck.put("preName", scanExService.getSubName(preNo))	;		
					Substation substation= substationService.getSubstationByNo(preNo) ;
					if (substation!=null) {
						tarck.put("preNo",substation.getInnerNo());
					}else {
						tarck.put("preNo","");
					}
				}
				if("C".equals(preType)){
					tarck.put("preName", scanExService.getCourierName(preNo))	;	
					Courier courier =  courierService.getCourierByNo(preNo) ;
					if (courier!=null) {
						tarck.put("preNo",courier.getInnerNo());
					}else {
						tarck.put("preNo","");
					}
				}				
			}
			
			if(!StringUtil.isEmptyString(tarck.get("nextNo"))){//下一站信息
				String nextNo = 	(String)tarck.get("nextNo");
				String nextType = (String)tarck.get("nextType");
				if("S".equals(nextType)){
					tarck.put("nextName", scanExService.getSubName(nextNo))	;	
					Substation substation= substationService.getSubstationByNo(nextNo) ;
					if (substation!=null) {
						tarck.put("nextNo",substation.getInnerNo());
					}else {
						tarck.put("nextNo","");
					}
				}
				if("C".equals(nextType)){
					tarck.put("nextName", scanExService.getCourierName(nextNo))	;
					Courier courier =  courierService.getCourierByNo(nextNo) ;
					if (courier!=null) {
						tarck.put("nextNo",courier.getInnerNo());
					}else {
						tarck.put("nextNo","");
					}
				}				
			}
			if(!StringUtil.isEmptyString(tarck.get("orderStatus"))){
				tarck.put("orderStatus", CheckTrackStatus.getIndex((String)tarck.get("orderStatus")));			
			}
			
			newList.add(tarck);		
		}	
		model.put("orderNo", orderNo);
		//model.put("courierList", JsonUtil.toJson(currentCourierList));
		//model.put("substationList", JsonUtil.toJson(substationList));
		model.put("list", current);	
		model.put("newList", newList);	
		model.put("params", params);	
		return "substaticEx/orderScan";
	}
	/**
	 * 报表生成Excel
	 * @param params
	 * @param response
	 * @param request
	 * @throws SQLException
	 */
	@RequestMapping(value = {"/export"})
    public void export(@RequestParam Map<String, String> params, HttpServletResponse response, HttpServletRequest request)
            throws SQLException {
        String serviceName = params.get("serviceName");
        String beginTime = params.get("beginTime");
        String endTime = params.get("endTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        OutputStream os = null;
        try {
            request.setCharacterEncoding("UTF-8");
            os = response.getOutputStream(); // 取得输出流
            response.reset(); // 清空输出流
            response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
            if (!StringUtils.isEmpty(params.get("serviceName"))) {
                if ("scanOrder".equals(serviceName)) {
                    String fileName = "扫描记录报表-" + beginTime + "~" + endTime + ".xls";
                    response.setHeader("Content-disposition", "attachment;filename="
                            + new String(fileName.getBytes("GBK"), "ISO8859-1"));
                    exportOrder(os, params, fileName, 1);
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
     * 写EXCEL
     *
     * @param os
     * @param params
     * @param fileName
     * @param num
     * @throws Exception
     */
    public void exportOrder(OutputStream os, Map<String, String> params, String fileName, int num) throws Exception {
        int row = 1; // 从第三行开始写
        int col = 0; // 从第一列开始写

        if (num == 1) {
            String substationNo = params.get("substationNo");
        	String beginTime = params.get("beginTime");
    		String endTime = params.get("endTime");
    		Date date  = new Date();
    		if(StringUtils.isEmptyWithTrim(beginTime) && StringUtils.isEmptyWithTrim(endTime)){
    			params.put("beginTime",DateUtils.formatDate(date, "yyyy-MM-dd"));
    			params.put("endTime",  DateUtils.formatDate(date, "yyyy-MM-dd"));
    		}
    		String statusNo = params.get("statusNo");//状态
    		if(!StringUtil.isEmptyWithTrim(statusNo)){
    			params.put("status", CheckTrackStatus.getType(statusNo));				
    		}	
    		String orderNo = params.get("orderNo");
    		
    		if (!StringUtil.isEmptyWithTrim(orderNo)) {
    			String o = "'0'" ;
    			String[] ods = orderNo.split("\r\n") ;
    			for (int i = 0; i < ods.length; i++) {
    				o=o+ ",'"+ods[i]+"'" ;
    			}
    			params.put("orderNo", o) ;
    		}
    	
    	
    		String curSubstationNo = params.get("curSubstationNo");
    		if (!StringUtils.isEmptyWithTrim(curSubstationNo)) {
    			if (curSubstationNo.contains("(")) {
    				curSubstationNo = curSubstationNo.substring(0, curSubstationNo.indexOf("("));
    				params.put("curSubstationNo", curSubstationNo);
    			}
    		}		
    		String nextSubstationNo = params.get("nextSubstationNo");
    		if (!StringUtils.isEmptyWithTrim(nextSubstationNo)) {
    			if (nextSubstationNo.contains("(")) {
    				nextSubstationNo = nextSubstationNo.substring(0, nextSubstationNo.indexOf("("));
    				params.put("nextSubstationNo", nextSubstationNo);
    			}
    		}		
    		String preSubstationNo = params.get("preSubstationNo");
    		if (!StringUtils.isEmptyWithTrim(preSubstationNo)) {
    			if (preSubstationNo.contains("(")) {
    				preSubstationNo = preSubstationNo.substring(0, preSubstationNo.indexOf("("));
    				params.put("preSubstationNo", preSubstationNo);
    			}
    		}		
    		System.out.println("params==="+params);
    		
    		
    		
    		
            List<Map<String, Object>> orderList = scanExService.getOrderScanList(params);
        
            
            List<Map<String, Object>> newList  = new ArrayList<Map<String, Object>>();
    		for(Map<String,Object> tarck : orderList){
    			if(!StringUtil.isEmptyString(tarck.get("curNo"))){
    				String curNo = 	(String)tarck.get("curNo");
    				String curType = (String)tarck.get("curType");
    			if("S".equals(curType)){
    				tarck.put("curName", scanExService.getSubName(curNo))	;	
    				Substation substation= substationService.getSubstationByNo(curNo) ;
    				if (substation!=null) {
    					tarck.put("curNo",substation.getInnerNo());
    				}else {
    					tarck.put("curNo","");
    				}
    			}
    			if("C".equals(curType)){
    				tarck.put("curName", scanExService.getCourierName(curNo))	;	
    				Courier courier =  courierService.getCourierByNo(curNo) ;
    				if (courier!=null) {
    					tarck.put("curNo",courier.getInnerNo());
    				}else {
    					tarck.put("curNo","");
    				}
    			  }				
    			}
    		
    			if(!StringUtil.isEmptyString(tarck.get("preNo"))){//上一站信息
    				String preNo = 	(String)tarck.get("preNo");
    				String preType = (String)tarck.get("preType");
    				if("S".equals(preType)){
    					tarck.put("preName", scanExService.getSubName(preNo))	;
    					Substation substation= substationService.getSubstationByNo(preNo) ;
    					if (substation!=null) {
    						tarck.put("preNo",substation.getInnerNo());
    					}else {
    						tarck.put("preNo","");
    					}
    				}
    				if("C".equals(preType)){
    					tarck.put("preName", scanExService.getCourierName(preNo))	;	
    					Courier courier =  courierService.getCourierByNo(preNo) ;
    					if (courier!=null) {
    						tarck.put("preNo",courier.getInnerNo());
    					}else {
    						tarck.put("preNo","");
    					}
    				}				
    			}
    			
    			if(!StringUtil.isEmptyString(tarck.get("nextNo"))){//下一站信息
    				String nextNo = 	(String)tarck.get("nextNo");
    				String nextType = (String)tarck.get("nextType");
    				if("S".equals(nextType)){
    					tarck.put("nextName", scanExService.getSubName(nextNo))	;		
    					Substation substation= substationService.getSubstationByNo(nextNo) ;
    					if (substation!=null) {
    						tarck.put("nextNo",substation.getInnerNo());
    					}else {
    						tarck.put("nextNo","");
    					}
    				}
    				if("C".equals(nextType)){
    					tarck.put("nextName", scanExService.getCourierName(nextNo))	;	
    					Courier courier =  courierService.getCourierByNo(nextNo) ;
    					if (courier!=null) {
    						tarck.put("nextNo",courier.getInnerNo());
    					}else {
    						tarck.put("nextNo","");
    					}
    				}				
    			}
    			if(!StringUtil.isEmptyString(tarck.get("orderStatus"))){
    				tarck.put("orderStatus", CheckTrackStatus.getIndex((String)tarck.get("orderStatus")));			
    			}		
    			newList.add(tarck);		
    		}	
               
            Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream(
                    "/template/scanOrder.xls"));
            WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
            WritableSheet ws = wwb.getSheet(0);
            Iterator<Map<String, Object>> it = newList.iterator();
            while (it.hasNext()) {
                Map<String, Object> map = it.next();
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("orderTime"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgcOrderNo"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("orderStatus"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("opname"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("preName"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("nextName"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("context"))));
                row++;
                col = 0;
            }         
            wwb.write();
            wwb.close();
            wb.close();
            os.close();
        }
     

    }	
	
}
