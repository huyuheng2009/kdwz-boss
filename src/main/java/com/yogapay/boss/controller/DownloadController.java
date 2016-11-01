package com.yogapay.boss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yogapay.boss.domain.AppVersion;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.service.AppVersionService;
import com.yogapay.boss.service.ManagerLgcConfigService;
import com.yogapay.boss.utils.ConstantsLoader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author dj
 * 
 */
@Controller
@RequestMapping(value = "/download")
public class DownloadController extends BaseController {
	
	@Resource
	private AppVersionService appVersionService ;
	@Resource
	private ManagerLgcConfigService managerLgcConfigService ;
	
	// 用于
	@RequestMapping(value = { "/client" })
	public String client(final ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig") ;
		 Map<String, Object> cMap = null ;
		 if (lgcConfig!=null) {
			cMap = managerLgcConfigService.getByLgcNo(lgcConfig.getLgcNo()) ;
		 }
		model.put("cMap", cMap) ;
		String cbname = lgcConfig.getCbname() ;  //快递员端产品key
		String ubname = lgcConfig.getUbname()  ;  //用户端产品key
		List<AppVersion> cappVersions = appVersionService.getLastVersion(cbname) ;
		List<AppVersion> uappVersions = appVersionService.getLastVersion(ubname) ;
		String host = request.getHeader("host") ;
		model.put("host", host) ;
		model.put("cappVersions", cappVersions) ;
		model.put("uappVersions", uappVersions) ;
		return "download/client";
	}			
	
	// 用于
	@RequestMapping(value = { "app/android" })
	public String android(final ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String string  = request.getHeader("ss") ;
		System.out.println(string);
		request.setAttribute("layout", "no");
		String cbname = ((LgcConfig)request.getSession().getAttribute("lgcConfig")).getCbname() ;  //快递员端产品key
		AppVersion app = appVersionService.getLastPlatVersion(cbname,"android") ;
		model.put("app", app) ;
		return "download/android";
	}	
	
	// 用于
	@RequestMapping(value = { "app/ios" })
	public String ios(final ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("layout", "no");
		String cbname = ((LgcConfig)request.getSession().getAttribute("lgcConfig")).getCbname() ;  //快递员端产品key
		AppVersion app = appVersionService.getLastPlatVersion(cbname,"ios") ;
		model.put("app", app) ;
		return "download/ios";
	}	
	
				
}
