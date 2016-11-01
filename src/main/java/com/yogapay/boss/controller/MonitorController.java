package com.yogapay.boss.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.AppVersion;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.service.AppVersionService;
import com.yogapay.boss.service.AsignOrderService;
import com.yogapay.boss.service.MapService;
import com.yogapay.boss.service.MonitorService;
import com.yogapay.boss.utils.ConstantsLoader;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author dj
 * 
 */
@Controller
@RequestMapping(value = "/monitor")
public class MonitorController extends BaseController {
	
	@Resource
	private AsignOrderService asignOrderService ;
	@Resource
	private MonitorService monitorService ;
	
	
	@RequestMapping(value = { "/asign" })
	public String asign(final ModelMap model, HttpServletRequest request,@RequestParam Map<String, String> params,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		
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
		
		//第一次默认为空
		PageInfo<Map<String, Object>> orderList = new PageInfo<Map<String,Object>>(null) ;
		Map<String, Object> total = new HashMap<String, Object>() ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			orderList =  asignOrderService.monitorList(params, getPageInfo(cpage)) ;
			total = asignOrderService.monitorTotal(params) ;
		}else {
			params.put("ff", "1") ;
			params.put("noType", "1") ;
		}
		params.put("createTimeEnd", endTime) ;
		model.put("list", orderList) ;
		model.put("total", total) ;
		model.put("params", params) ;
		return "/monitor/asign";
	}			
	

	@RequestMapping(value = { "/asign_detail" })
	public String asign_detail(final ModelMap model, HttpServletRequest request,@RequestParam Map<String, String> params,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		List<Map<String, Object>> orderList = asignOrderService.monitorDetailList(params) ;
		model.put("list", orderList) ;
		model.put("params", params) ;
		return "/monitor/asign_detail";
	}			
	
	

	@RequestMapping(value = { "/take" })
	public String take(final ModelMap model, HttpServletRequest request,@RequestParam Map<String, String> params,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		String substationNo ;
		if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
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
		
		//第一次默认为空
		PageInfo<Map<String, Object>> orderList = new PageInfo<Map<String,Object>>(null) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			orderList =  asignOrderService.takeMonitor(params, getPageInfo(cpage)) ;
		}else {
			params.put("ff", "1") ;
		}
		params.put("createTimeEnd", endTime) ;
		model.put("list", orderList) ;
		model.put("params", params) ;
		return "/monitor/take";
	}			
	
	@RequestMapping(value = { "/take_detail" })
	public String take_detail(final ModelMap model, HttpServletRequest request,@RequestParam Map<String, String> params,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		List<Map<String, Object>> orderList = asignOrderService.monitorTakeDetailList(params) ;
		model.put("list", orderList) ;
		model.put("params", params) ;
		return "/monitor/take_detail";
	}
	
	//入仓监控
	@RequestMapping(value = { "/inmonitor" })
	public String inmonitor(final ModelMap model, HttpServletRequest request,@RequestParam Map<String, String> params,
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
		
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		String substationNo ;
		if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
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
		
		//第一次默认为空
		PageInfo<Map<String, Object>> orderList = new PageInfo<Map<String,Object>>(null) ;
		if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
			orderList =  monitorService.iolist(params, getPageInfo(cpage)) ;
		}else {
			params.put("ff", "1") ;
		}
		params.put("createTimeEnd", endTime) ;
		model.put("list", orderList) ;
		model.put("params", params) ;
		return "/monitor/inmonitor";
	}	
		
		
		
	//出仓监控
		@RequestMapping(value = { "/outmonitor" })
		public String outmonitor(final ModelMap model, HttpServletRequest request,@RequestParam Map<String, String> params,
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
			
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
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
			
			//第一次默认为空
			PageInfo<Map<String, Object>> orderList = new PageInfo<Map<String,Object>>(null) ;
			if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
				orderList =  monitorService.iolist(params, getPageInfo(cpage)) ;
			}else {
				params.put("ff", "1") ;
			}
			params.put("createTimeEnd", endTime) ;
			model.put("list", orderList) ;
			model.put("params", params) ;
			return "/monitor/outmonitor";
		}		
		
		
}
