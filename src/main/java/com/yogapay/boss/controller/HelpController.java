package com.yogapay.boss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yogapay.boss.domain.AppVersion;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.service.AppVersionService;
import com.yogapay.boss.service.LgcResourceService;
import com.yogapay.boss.utils.ConstantsLoader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
@RequestMapping(value = "/help")
public class HelpController extends BaseController {
	
	@Resource
	private AppVersionService appVersionService ;
	@Resource
	private LgcResourceService lgcResourceService ;
	
	// 用于
	@RequestMapping(value = { "/sysc" })
	public void sysc(final ModelMap model, HttpServletRequest request,HttpServletResponse response) throws IOException{
	/*	String r = String.valueOf(System.currentTimeMillis()) ;
		model.put("derictUrl", "/html/help/dingdan/yundanliebiao.html?"+r) ;
		return "derict";*/
		OutputStream os = response.getOutputStream() ;  // 取得输出流
		try {
		 LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig") ;
		 String lgcNo = "1149" ;
		 if (lgcConfig!=null) {
			lgcNo = lgcConfig.getLgcNo() ;
		}
		Map<String, Object> lgcResource = lgcResourceService.getLgcResource(lgcNo, "help_word", "1149") ;
		
		
		request.setCharacterEncoding("UTF-8");
		
		response.reset(); // 清空输出流
		response.setContentType("application/msword;charset=UTF-8");// 定义输出类型
            	 String fileName = "后台使用说明书.doc";
                 response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
           if (lgcResource==null) {
        	   os.flush();
		   }  else {
			File file = new File(lgcResource.get("res_path").toString()) ;
			FileInputStream inputStream = new FileInputStream(file) ;
			int i=inputStream.available(); //得到文件大小 
			byte data[]=new byte[i]; 
			response.setContentLength(i);
			inputStream.read(data); //读数据 
			inputStream.close(); 
			os.write(data);
			os.flush();
	    }
           
		} catch (Exception e) {
			
		}finally{
			
			os.close();
		}
	}			

				
}
