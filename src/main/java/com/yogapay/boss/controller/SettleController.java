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
import com.yogapay.boss.domain.CourierDaySettle;
import com.yogapay.boss.domain.MonthUserSettle;
import com.yogapay.boss.service.CodInfoService;
import com.yogapay.boss.service.CodMonthCountService;
import com.yogapay.boss.service.CodSettleUserService;
import com.yogapay.boss.service.CourierDaySettleService;
import com.yogapay.boss.service.CourierDayStaticService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.MonthUserSettleService;
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
@RequestMapping(value = "/settle")
public class SettleController extends BaseController {

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
    private CourierDaySettleService courierDaySettleService ;
    @Resource
    private MonthUserSettleService monthUserSettleService ;

	
	
	// 月结用户月报表账单生产
	@RequestMapping(value = { "/monthCount" })
	public String monthUserCount(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		String beginTimeM = params.get("beginTimeM");//按月统计
		Date nowDate =new Date();
				params.put("beginTime", "");
				params.put("endTime", "");
			if (StringUtils.isEmptyWithTrim(beginTimeM)) {
				params.put("beginTimeM", DateUtils.formatDate(nowDate, "yyyy-MM"));
			}	
			String courierNo = params.get("courierNo");
			String monthSettleNo = params.get("monthSettleNo");
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

			PageInfo<Map<String, Object>> list = new PageInfo<Map<String,Object>>(new ArrayList<Map<String, Object>>()) ;
			if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
				list = monthUserStaticService.getMonthDayOrMonth(params, getPageInfo(cpage,100));	
			}else {
				params.put("ff", "1") ;
			}
			
			BigDecimal AllsumMoney = new BigDecimal("0.00");// 实收
			int count = 0;
			if (list.getList()!=null) {
				for (Map<String, Object> map : list.getList()) {
					if (map!=null) {
						AllsumMoney = AllsumMoney.add(new BigDecimal(StringUtils.nullString(map.get("sumMoney"),"0")));
					}
					
				count = count+Integer.valueOf(String.valueOf(map.get("sumCount")));
				}
			}
			
			model.put("AllsumMoney", AllsumMoney.toString());// 实收
			model.put("list", list);
			model.put("count", count);
		        model.put("params", params);
		return "settle/monthCount";
	}


	
	@RequestMapping(value = { "/queryDayCourierInfo" })
	public String queryDayCourierInfo(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException, ParseException {
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
		
		PageInfo<Map<String, Object>> countList = new PageInfo<Map<String,Object>>(null) ;
		Map<String, Object> count = new HashMap<String, Object>() ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			countList = orderService.queryDayCourierInfo(params, getPageInfo(cpage));
			count = orderService.queryDayCourierCount(params);
		}else {
			params.put("ff", "1") ;
		}
		
	/*	List<Map<String, Object>> courierList  = new  ArrayList<Map<String, Object>>();
		if("root".equals(bossUser.getRealName())	|| "admin".equals(bossUser.getRealName())){	
			courierList = mobileUserService.getAllCourierList(params);
		}else{
			courierList = mobileUserService.getCourierListBySubStation(userService.getUserSubstationNoEx(bossUser.getId()));
		}*/
		
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
		//model.put("courierList", JsonUtil.toJson(courierList));
		model.put("substations", substations);
		return "settle/dayCourier";
	}

	
	
	@RequestMapping(value = { "/settleCreate" })
	public void settleCreate(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response)  {
		try {
			
		Date nowDate = new Date() ;
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
		List<Map<String, Object>> countList = orderService.queryDayCourierInfo(params);
		List<CourierDaySettle> settleList = new ArrayList<CourierDaySettle>() ;
		
		for (Map<String, Object> map:countList) {
			CourierDaySettle courierDaySettle = new CourierDaySettle();
			courierDaySettle.setCourierNo(StringUtils.nullString(map.get("courierNo"),"0"));
			courierDaySettle.setCtime(StringUtils.nullString(map.get("ctime"),"0"));
			courierDaySettle.setSettleDate(StringUtils.nullString(map.get("timejoin"),"0"));
			BigDecimal b1 = new BigDecimal(StringUtils.nullString(map.get("jcashMoney"),"0")) ;
			BigDecimal b2 = new BigDecimal(StringUtils.nullString(map.get("dcashMoney"),"0")) ;
			BigDecimal c1 = b1.add(b2) ;
			courierDaySettle.setCashCount(c1);
			
			b1 = new BigDecimal(StringUtils.nullString(map.get("jmonthMoney"),"0")) ;
			b2 = new BigDecimal(StringUtils.nullString(map.get("dmonthMoney"),"0")) ;
			BigDecimal c2 = b1.add(b2) ;
			courierDaySettle.setMonthCount(c2);
			
			courierDaySettle.setGoodCount(new BigDecimal(StringUtils.nullString(map.get("dgoodMoney"),"0")));
			courierDaySettle.setCreater(bossUser.getRealName());
			courierDaySettle.setCreateTime(DateUtils.formatDate(nowDate));
		
			settleList.add(courierDaySettle) ;
		}
		 if (settleList.size()>0) {
		      courierDaySettleService.delByCtime(params.get("ctime")) ;
		      courierDaySettleService.batchSave(settleList);
		 }
		outText("1", response);
		} catch (Exception e) {
			e.printStackTrace();
			outText("生成失败，稍后再试", response);
		}

	}
	
	

	// 快递员现金收款登记
	@RequestMapping(value = { "/clist" })
	public String clist(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
        
		String sno = params.get("substationNo") ;
		Date nowDate = new Date() ;
		String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
		String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
		if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
			params.put("createTimeBegin", beginTime) ;
		}
		if (StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
			params.put("createTimeEnd", endTime) ;
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
		PageInfo<Map<String, Object>> orderList = new PageInfo<Map<String,Object>>(null) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			orderList = courierDaySettleService.list(params, getPageInfo(cpage,100)) ;
		}else {
			params.put("ff", "1") ;
		}
		model.put("orderList", orderList) ;
		params.put("substationNo", sno) ;
		model.put("params", params) ;
		return "settle/clist";
	}

	

	// 
	@RequestMapping(value = { "/payPage" })
	public String payPage(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		model.put("params", params) ;
		return "settle/payPage";
	}
	
	// 
		@RequestMapping(value = { "/mpayPage" })
		public String mpayPage(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
			model.put("params", params) ;
			if (!"1".endsWith(params.get("len"))) {
				if (!StringUtils.isEmptyWithTrim(params.get("id"))) {
				  Map<String, Object> monthSettle =	monthUserSettleService.getById(Integer.valueOf(params.get("id"))) ;
				  model.put("monthSettle", monthSettle) ;
				}else {
				   model.put("monthSettle", "") ;
				}
				return "settle/mpayPage";
			}else {
				return "settle/mbpayPage";
			}
		}
	
	
	
	@RequestMapping(value = { "/batchPay" })
	public void batchPay(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response)  {
		try {
             String ids = params.get("ids") ;
             String len = params.get("len") ;
             String settleType = params.get("settleType") ;
             String settleCount = params.get("settleCount") ;
             String note = params.get("note") ;
             if (!StringUtils.isEmptyWithTrim(ids)&&!StringUtils.isEmptyWithTrim(len)&&!StringUtils.isEmptyWithTrim(settleType)) {
				if ("0".equals(len)) {
					courierDaySettleService.batchpay(ids, settleType, settleCount, note);
				}else {
					courierDaySettleService.batchpayCount(ids, settleType, note);
				}
			}
		outText("1", response);
		} catch (Exception e) {
			e.printStackTrace();
			outText("保存失败，稍后再试", response);
		}

	}
	
	//快递员收款批量取消
	@RequestMapping(value = { "/batchCancel" })
	public void batchCancel(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response)  {
		try {
             String ids = params.get("ids") ;
             if (!StringUtils.isEmptyWithTrim(ids)) {
				courierDaySettleService.nopay(ids);
			}
		outText("1", response);
		} catch (Exception e) {
			e.printStackTrace();
			outText("保存失败，稍后再试", response);
		}

	}
	
	//月结收款批量取消
	@RequestMapping(value = { "/mbatchCancel" })
	public void mbatchCancel(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response)  {
		try {
             String ids = params.get("ids") ;
             if (!StringUtils.isEmptyWithTrim(ids)) {
				monthUserSettleService.nopay(ids);
			}
		outText("1", response);
		} catch (Exception e) {
			e.printStackTrace();
			outText("保存失败，稍后再试", response);
		}

	}
	
	// 快递员现金收款审核
		@RequestMapping(value = { "/elist" })
		public String elist(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
	        
			String sno = params.get("substationNo") ;
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
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}
				params.put("substationNo", substationNo);
			}
			params.put("settleStatus", "1") ;
			PageInfo<Map<String, Object>> orderList = new PageInfo<Map<String,Object>>(null) ;
			if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
				orderList = courierDaySettleService.list(params, getPageInfo(cpage,100)) ;
			}else {
				params.put("ff", "1") ;
			}
			model.put("orderList", orderList) ;
			params.put("substationNo", sno) ;
			params.put("settleTimeEnd", settleTimeEnd) ;
			model.put("params", params) ;
			return "settle/elist";
		}
	
	
		@RequestMapping(value = { "/examine_save" })
		public void examine_save(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response)  {
			try {
	             String ids = params.get("ids") ;
	             String pids = params.get("pids") ;
	             if (!StringUtils.isEmptyWithTrim(ids)) {
					courierDaySettleService.batchExamine(ids);
				 }
	             if (!StringUtils.isEmptyWithTrim(pids)) {
						courierDaySettleService.noExamine(pids);
			       }
	             
			outText("1", response);
			} catch (Exception e) {
				e.printStackTrace();
				outText("保存失败，稍后再试", response);
			}

		}
		
		@RequestMapping(value = { "/mexamine_save" })
		public void mexamine_save(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response)  {
			try {
	             String ids = params.get("ids") ;
	             String pids = params.get("pids") ;
	             if (!StringUtils.isEmptyWithTrim(ids)) {
					monthUserSettleService.batchExamine(ids);
				 }
	             if (!StringUtils.isEmptyWithTrim(pids)) {
	            	 monthUserSettleService.noExamine(pids);
			       }
	             
			outText("1", response);
			} catch (Exception e) {
				e.printStackTrace();
				outText("保存失败，稍后再试", response);
			}

		}
	
		@RequestMapping(value = { "/monthSettleCreate" })
		public void monthSettleCreate(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response)  {
			try {
				
				String beginTimeM = params.get("beginTimeM");//按月统计
				Date nowDate =new Date();
						params.put("beginTime", "");
						params.put("endTime", "");
					if (StringUtils.isEmptyWithTrim(beginTimeM)) {
						params.put("beginTimeM", DateUtils.formatDate(nowDate, "yyyy-MM"));
					}	
					String courierNo = params.get("courierNo");
					String monthSettleNo = params.get("monthSettleNo");
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
			
			
			List<Map<String, Object>> list = monthUserStaticService.getMonthDayOrMonthList(params);		
			List<MonthUserSettle> settleList = new ArrayList<MonthUserSettle>() ;
			
			for (Map<String, Object> map:list) {
				MonthUserSettle monthUserSettle = new MonthUserSettle() ;
				monthUserSettle.setCtime(StringUtils.nullString(map.get("ctime")));
                monthUserSettle.setMonthNo(StringUtils.nullString(map.get("TmonthSettleNo")));
                monthUserSettle.setSettleMonth(StringUtils.nullString(map.get("settleMonth"))+"-10");
                monthUserSettle.setMonthCount(new BigDecimal(StringUtils.nullString(map.get("TSALL"),"0")));
                monthUserSettle.setSumCount(Integer.valueOf(StringUtils.nullString(map.get("sumCount"),"0")));
                monthUserSettle.setCreateTime(DateUtils.formatDate(nowDate));
                monthUserSettle.setCreater(bossUser.getRealName());
               settleList.add(monthUserSettle) ;
			}
			 if (settleList.size()>0) {
			      monthUserSettleService.delByCtime(params.get("ctime")) ;
			      monthUserSettleService.batchSave(settleList);
			 }
			outText("1", response);
			} catch (Exception e) {
				e.printStackTrace();
				outText("生成失败，稍后再试", response);
			}

		}	
		
		
		// 月结用户收款登记
		@RequestMapping(value = { "/mlist" })
		public String mlist(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			String sno = params.get("substationNo") ;
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
			params.put("createTimeEnd", endTime+"-25") ;
			
			
			
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
				orderList = monthUserSettleService.list(params, getPageInfo(cpage,100)) ;
			}else {
				params.put("ff", "1") ;
			}
			model.put("orderList", orderList) ;
			params.put("substationNo", sno) ;
			params.put("createTimeBegin", beginTime) ;
			params.put("createTimeEnd", endTime) ;
			model.put("params", params) ;
			return "settle/mlist";
		}
      
		
		// 月结用户收款审核列表
				@RequestMapping(value = { "/melist" })
				public String melist(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					String sno = params.get("substationNo") ;
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
					params.put("createTimeEnd", endTime+"-25") ;
					
					String settleTimeEnd = params.get("settleTimeEnd") ;
					if (!StringUtils.isEmptyWithTrim(settleTimeEnd)) {
						params.put("settleTimeEnd", settleTimeEnd+" 23:59:59") ;
					}
					
					params.put("settleStatus", "1") ;
					
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
						orderList = monthUserSettleService.list(params, getPageInfo(cpage,100)) ;
					}else {
						params.put("ff", "1") ;
					}
					model.put("orderList", orderList) ;
					params.put("substationNo", sno) ;
					params.put("createTimeBegin", beginTime) ;
					params.put("createTimeEnd", endTime) ;
					params.put("settleTimeEnd", settleTimeEnd) ;
					model.put("params", params) ;
					return "settle/melist";
				}
		
		//月结批量收款
		@RequestMapping(value = { "/mbbatchPay" })
		public void mbbatchPay(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response)  {
			try {
	             String ids = params.get("ids") ;
	             String settleType = params.get("settleType") ;
	             String note = params.get("note") ;
	             if (!StringUtils.isEmptyWithTrim(ids)&&!StringUtils.isEmptyWithTrim(settleType)) {
	            	 monthUserSettleService.batchpayCount(ids, settleType, note);
				}
			outText("1", response);
			} catch (Exception e) {
				e.printStackTrace();
				outText("保存失败，稍后再试", response);
			}

		}
		
		     //月结单个收款
				@RequestMapping(value = { "/mbatchPay" })
				public void mbatchPay(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response)  {
					try {
			             String ids = params.get("ids") ;
			             String settleType = params.get("settleType") ;
			             String settleCount = params.get("settleCount") ;
			             String note = params.get("note") ;
			             if (!StringUtils.isEmptyWithTrim(ids)&&!StringUtils.isEmptyWithTrim(settleType)&&!StringUtils.isEmptyWithTrim(settleCount)) {
			            	 monthUserSettleService.batchpay(ids, settleType,settleCount, note);
						}
					outText("1", response);
					} catch (Exception e) {
						e.printStackTrace();
						outText("保存失败，稍后再试", response);
					}

				}

}
