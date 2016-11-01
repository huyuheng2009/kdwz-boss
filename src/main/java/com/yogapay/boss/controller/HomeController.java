package com.yogapay.boss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yogapay.boss.domain.PushNotice;
import com.yogapay.boss.service.PushNoticeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * 系统首页等
 * 
 * @author dj
 * 
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController extends BaseController {
	
	@Resource
	private PushNoticeService pushNoticeService ;
	
	// 用于
	@RequestMapping(value = { "/notice" }, method = RequestMethod.GET)
	public String notice(final ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return "notice";
	}			
	// 用于
		@RequestMapping(value = { "/index" })
		public String index(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			boolean noticPage = false ;
			PushNotice notic = pushNoticeService.getLast() ;
			if (notic!=null&&notic.getContent()!=null) {
				if(notic.getContent().contains("-")&&notic.getType()==1){
					notic.setContent(notic.getContent().replace(notic.getTitle()+"-", ""));
				}
				notic.setContent(notic.getContent().replace("\r\n", "<br/>").replace("\n", "<br/>").replace("\r", "<br/>"));
				noticPage = true ;
				model.put("notice", notic);
			}
			Map<String, Object> picMap = pushNoticeService.queryIndexPic();
			String picPath="";
			if(picMap!=null){
				if(picMap.get("v")!=null){
					picPath=picMap.get("v").toString();
				}
			}
			model.put("picPath", picPath);
			if (noticPage) {
				return "home/main";
			}
			return "home/index";
		}		
				
}
