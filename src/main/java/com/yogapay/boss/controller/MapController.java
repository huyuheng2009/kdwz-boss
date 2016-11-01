package com.yogapay.boss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yogapay.boss.domain.AppVersion;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.service.AppVersionService;
import com.yogapay.boss.service.MapService;
import com.yogapay.boss.utils.ConstantsLoader;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author dj
 * 
 */
@Controller
@RequestMapping(value = "/map")
public class MapController extends BaseController {
	
	@Resource
	private MapService mapService ;
	
	
	// 用于地图首页
	@RequestMapping(value = { "/index" })
	public String index(final ModelMap model, HttpServletRequest request,@RequestParam Map<String, String> params,
			HttpServletResponse response) throws IOException {
		params.remove("online") ;
		String realName = params.get("realName") ;
		List<Map<String, Object>> list = mapService.listGroup(params);
		params.put("online", "Y") ;
		params.remove("realName") ;
		List<Map<String, Object>> onlineList = mapService.list(params);
		model.put("list", list) ;
		model.put("slist", JsonUtil.toJson(list)) ;
		model.put("onlineList", JsonUtil.toJson(onlineList)) ;
		params.put("realName", realName) ;
		model.put("params", params) ;
		return "/map/index";
	}			
	

	
	
	// 用于获取所有15分钟内在线快递员
		@RequestMapping(value = { "/onlineList" })
		public void onlineList(final ModelMap model, HttpServletRequest request,@RequestParam Map<String, String> params,
				HttpServletResponse response) throws IOException {
			params.put("online", "Y") ;
			List<Map<String, Object>> onlineList = mapService.list(params);
			outText(JsonUtil.toJson(onlineList), response);
		}	
	
		
		// 用于地图快递员路径
		@RequestMapping(value = { "/polyline" })
		public String polyline(final ModelMap model, HttpServletRequest request,@RequestParam Map<String, String> params,
				HttpServletResponse response) throws IOException {
			params.remove("online") ;
			List<Map<String, Object>> list = mapService.listGroup(params);
			Date nowDate = new Date() ;
			params.put("createTimeBegin", DateUtils.formatDate(nowDate,"yyyy-MM-dd"+" 00:00:00")) ;
			List<Map<String, Object>> pointList = mapService.pointList(params);
			model.put("list", list) ;
			model.put("cno", params.get("cno")) ;
			model.put("pointList", JsonUtil.toJson(pointList)) ;
			model.put("params", params) ;
			return "/map/polyline";
		}			
			
	
		// 用于获取快递员路径坐标
		@RequestMapping(value = { "/lineUpdate" })
		public void lineUpdate(final ModelMap model, HttpServletRequest request,@RequestParam Map<String, String> params,
				HttpServletResponse response) throws IOException {
			Date nowDate = new Date() ;
			params.put("createTimeBegin", DateUtils.formatDate(nowDate,"yyyy-MM-dd"+" 00:00:00")) ;
			List<Map<String, Object>> pointList = mapService.pointList(params);
			outText(JsonUtil.toJson(pointList), response);
		}	
				
}
