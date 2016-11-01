package com.yogapay.boss.controller;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.AppProduct;
import com.yogapay.boss.domain.AppVersion;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.User;
import com.yogapay.boss.service.AppProductService;
import com.yogapay.boss.service.AppVersionService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.ConstantsLoader;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.RandomValidateCode;
import com.yogapay.boss.utils.SHA;
import com.yogapay.boss.utils.SendMail;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;
import com.yogapay.boss.utils.http.CommonHttpConnection;
import com.yogapay.boss.utils.http.HttpConnectionParameters;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 系统登录，注销，首页等
 * 
 * @author dj
 * 
 */
@Controller
@RequestMapping(value = "/client")
public class ClientController extends BaseController {
    @Resource
    private MobileUserService mobileUserService ;
    @Resource
    private AppProductService appProductService ;
    @Resource
    private SequenceService sequenceService ;
    @Resource
    private AppVersionService appVersionService ;

			
	   // 用于
		@RequestMapping(value = { "/product" })
		public String plist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			PageInfo<Map<String, Object>> appProductList = appProductService.list(params,getPageInfo(cpage));
			model.put("appProductList", appProductList) ;
			model.put("params", params) ;
			return "client/plist";
		}	
		
		
		// 
		@RequestMapping(value = { "/pstatus" }, method = RequestMethod.GET)
		public void pstatus(HttpServletRequest request,HttpServletResponse response, int id,int s) throws SQLException {
			AppProduct appProduct = new AppProduct() ;
			appProduct.setId(id);
			appProduct.setStatus(s);
			appProductService.status(appProduct) ;
			outText("1", response);
		}
		
		
		@RequestMapping(value = "/pshow")
		public String pshow(final ModelMap model,@RequestParam Map<String, String> params)
				throws SQLException {
			String id = params.get("id");
			if (id != null) {
                AppProduct appProduct = appProductService.getById(Integer.parseInt(id));				
				model.put("appProduct", appProduct);
			}
			return "client/pshow";
		}
		
		
		@RequestMapping(value = "/psave")
		public void psave(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, String> params) throws SQLException{
			int r = 0;
			AppProduct appProduct = new AppProduct() ;
			appProduct.setAppName(params.get("appName"));
			appProduct.setStatus(Integer.parseInt(params.get("status")));
			appProduct.setBname(params.get("bname"));
			try {
				if (!StringUtil.isEmptyWithTrim(params.get("id"))) {
					appProduct.setId(Integer.parseInt(params.get("id")));
					//appProduct.setAppCode(Integer.parseInt(params.get("appCode")));
	                r=1;
	               appProductService.updateAppProduct(appProduct) ;
				} else {
					r = 1;
					appProduct.setAppCode(Integer.parseInt(sequenceService.getNextVal("app_code")));
					appProductService.saveAppProduct(appProduct);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				r = 0;
			}
			outText(r + "", response);
		}
		
		
		@RequestMapping(value = "/pdel")
		public void pdel(final ModelMap model,
	                     HttpServletRequest request,
	                     HttpServletResponse response,
				@RequestParam Map<String, String> params) {
			int id = Integer.parseInt(params.get("id")) ;
			String r = "1" ;
			try {
				Map<String, String> param = new HashMap<String, String>();
				param.put("appCode", params.get("appCode")) ;
				PageInfo<Map<String, Object>> appVersionList = appVersionService.list(param,getPageInfo(1,1));
				if (appVersionList.getTotal()>0) {
					outText("请先删除产品所有版本", response);
				}else {
					appProductService.delById(id);
					outText(r, response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				outText("删除失败！", response);
			}
			
		}
		
		
		  // 用于
				@RequestMapping(value = { "/appversion" })
				public String vlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					PageInfo<Map<String, Object>> appVersionList = appVersionService.list(params,getPageInfo(cpage));
					model.put("appVersionList", appVersionList) ;
					model.put("params", params) ;
					return "client/vlist";
				}
	
				
				// 
				@RequestMapping(value = { "/vstatus" }, method = RequestMethod.GET)
				public void vstatus(HttpServletRequest request,HttpServletResponse response, int id,int s) throws SQLException {
					AppVersion appVersion = new AppVersion() ;
					appVersion.setId(id);
					appVersion.setStatus(s);
					appVersionService.status(appVersion);
					outText("1", response);
				}
				
				@RequestMapping(value = "/vshow")
				public String vshow(final ModelMap model,@RequestParam Map<String, String> params)
						throws SQLException {
					String id = params.get("id");
					if (id != null) {
		               AppVersion appVersion = appVersionService.getById(Integer.parseInt(id)) ;
		               model.put("appVersion", appVersion);
					}
					PageInfo<Map<String, Object>> appProductList = appProductService.list(null,getPageInfo(1,200));
					model.put("appProductList", appProductList.getList()) ;
					return "client/vshow";
				}
				
				@RequestMapping(value = "/vplist")
				public String plist(final ModelMap model,@RequestParam Map<String, String> params)
						throws SQLException {
					model.put("params", params) ;
					return "client/vplist";
				}
				
				@RequestMapping(value = "/vpay")
				public String vpay(final ModelMap model,@RequestParam Map<String, String> params)
						throws SQLException {
					String id = params.get("id");
					if (id != null) {
		               List<Map<String, Object>> payTypeList = appVersionService.listPayType(Integer.parseInt(id)) ;
		               model.put("payTypeList", payTypeList);
					}
					model.put("params", params) ;
					return "client/payshow";
				}
				
				@RequestMapping(value = "/vp_save")
				public void vp_save(final ModelMap model,@RequestParam Map<String, String> params,HttpServletResponse response){
					try {
					String vid = params.get("vid");   //版本id
					String ids = params.get("ids");
					String [] idList = {} ;
					if (!StringUtil.isEmptyWithTrim(ids)) {
						idList = ids.split(",") ;
					}
					appVersionService.payType(vid,idList);
					outText("1", response);
					} catch (Exception e) {
					    e.printStackTrace();
						outText("设置失败！", response);
					}
				}
				
		
			
				@RequestMapping(value = "/vsave")
				public void vsave(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, String> params) throws IOException{
					 PrintWriter out = response.getWriter();
					AppVersion appVersion = new AppVersion() ;
					appVersion.setAppCode(Integer.parseInt(params.get("appCode")));
					appVersion.setPlatform(params.get("platform"));
					appVersion.setStatus(Integer.parseInt(params.get("status")));
					appVersion.setAddress(params.get("address"));
					appVersion.setDownloadAddress(params.get("downloadAddress"));
					appVersion.setVersion(params.get("version"));
					AppProduct appProduct = appProductService.getByCode(appVersion.getAppCode()) ;
					String contextPath = "/version/".concat(appProduct.getBname()+"/").concat(appVersion.getPlatform()+"/").concat(appVersion.getVersion()+"/");
					if (StringUtil.isEmptyWithTrim(params.get("mupdate"))) {
						appVersion.setMupdate(0);
					}else {
						appVersion.setMupdate(Integer.parseInt(params.get("mupdate")));
					}
					
					int r = 0;
					try {
				  
					if(!"web".equals(params.get("download_type"))){
					 	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    			       Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
	    			       String fileName  = "" ;
	    			       for(String businessType:fileMap.keySet()){
	    			    	   String prefix = fileMap.get(businessType).getOriginalFilename().substring(fileMap.get(businessType).getOriginalFilename().indexOf("."));
	    			    	fileName = fileMap.get(businessType).getOriginalFilename() ;
	    			    	File dir = new File(configInfo.getFile_root().concat(contextPath));
	    					if(!dir.isDirectory()){
	    						dir.mkdirs();
	    					}
	    				/*	File dir1 = new File(ConstantsLoader.getProperty("file_root").concat("/lastversion/"));
	    					if(!dir1.isDirectory()){
	    						dir1.mkdirs();
	    					}*/
	    					//String cbname = ((LgcConfig)request.getSession().getAttribute("lgcConfig")).getCbname() ;
	    					FileCopyUtils.copy(fileMap.get(businessType).getBytes(),new File(configInfo.getFile_root().concat(contextPath).concat(fileName)));
	    					//FileCopyUtils.copy(fileMap.get(businessType).getBytes(),new File(ConstantsLoader.getProperty("file_root").concat("/lastversion/")+cbname+appVersion.getPlatform()+prefix));
	    					
	    			       }
	    			    appVersion.setAddress(params.get("uri_fit")+"/codfile"+contextPath+fileName);  
	    			    appVersion.setIpaAddress(params.get("uri_fit")+"/codfile"+contextPath+fileName);  
	    			    appVersion.setDownloadAddress(params.get("uri_fit")+"/codfile"+contextPath+fileName);  
			    	}	
										
					   AppVersion version = 	appVersionService.getLastPlatVersion(appProduct.getBname(), "ios") ;
					   if (appVersion.getPlatform().equals("ios")) {
						    appVersion.setAddress(version.getAddress());
					    }
						if (!StringUtil.isEmptyWithTrim(params.get("id"))) {
							appVersion.setId(Integer.parseInt(params.get("id")));
			                r=1;
			               appVersionService.updateAppVersion(appVersion) ;
						} else {
							r = 1;
						  appVersion.setPublishTime(DateUtils.formatDate(new Date()));
						  appVersionService.saveAppVersion(appVersion);
						}
						   out.println("<script type=\"text/javascript\">");    
						    out.println(" alert('保存成功！！'); var api = frameElement.api;api.reload();api.close();");    
						    out.println("</script>");	

					} catch (Exception e) {
						// TODO Auto-generated catch block
						  e.printStackTrace();
						 out.println("<script type=\"text/javascript\">");    
						  out.println(" alert('添加失败！！'); var api = frameElement.api;api.close();");    
						  out.println("</script>");
					}
					  
				}
	
		

				@RequestMapping(value = "/plistsave")
				public void plistsave(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, String> params) throws IOException{
					 PrintWriter out = response.getWriter();
					AppVersion appVersion = appVersionService.getById(Integer.parseInt(params.get("id"))) ;
					AppProduct appProduct = appProductService.getByCode(appVersion.getAppCode()) ;
					String contextPath = "/version/".concat(appProduct.getBname()+"/").concat(appVersion.getPlatform()+"/").concat(appVersion.getVersion()+"/");
					
					int r = 0;
					try {
				  
					 	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    			       Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
	    			       String fileName  = "" ;
	    			       for(String businessType:fileMap.keySet()){
	    			    	   String prefix = fileMap.get(businessType).getOriginalFilename().substring(fileMap.get(businessType).getOriginalFilename().indexOf("."));
	    			    	fileName = fileMap.get(businessType).getOriginalFilename() ;
	    			    	File dir = new File(configInfo.getFile_root().concat(contextPath));
	    					if(!dir.isDirectory()){
	    						dir.mkdirs();
	    					}
	    					FileCopyUtils.copy(fileMap.get(businessType).getBytes(),new File(configInfo.getFile_root().concat(contextPath).concat(fileName)));
	    			      }
						 appVersion.setDownloadAddress(params.get("uri_fit")+"/codfile"+contextPath+fileName);  				
					     appVersionService.updateDownloadAddress(appVersion);

						   out.println("<script type=\"text/javascript\">");    
						    out.println(" alert('保存成功！！'); var api = frameElement.api;api.reload();api.close();");    
						    out.println("</script>");	

					} catch (Exception e) {
						// TODO Auto-generated catch block
						  e.printStackTrace();
						 out.println("<script type=\"text/javascript\">");    
						  out.println(" alert('添加失败！！'); var api = frameElement.api;api.close();");    
						  out.println("</script>");
					}
					  
				}			
				
				@RequestMapping(value = "/vdel")
				public void vdel(final ModelMap model,
			                     HttpServletRequest request,
			                     HttpServletResponse response,
						@RequestParam Map<String, String> params) {
					int id = Integer.parseInt(params.get("id")) ;
					String r = "1" ;
					try {
						appVersionService.delById(id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						outText("删除失败！", response);
					}
					outText(r, response);
				}
				
				@RequestMapping(value = "/fileadd")
				public String fileadd(final ModelMap model,@RequestParam Map<String, String> params)
						throws SQLException {
					model.put("params", params) ;
					return "client/fileadd";
				}
	

				@RequestMapping(value = "/fileaddsave")
				public void fileaddsave(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, String> params) throws IOException{
					 PrintWriter out = response.getWriter();
					String contextPath = "/uploadfile/";
					
					int r = 0;
					try {
				  
					 	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    			       Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
	    			       String fileName  = UUID.randomUUID().toString().replace("-", "") ;
	    			       String prefix = "" ;
	    			       for(String businessType:fileMap.keySet()){
	    			    	 prefix = fileMap.get(businessType).getOriginalFilename().substring(fileMap.get(businessType).getOriginalFilename().indexOf("."));
	    			    	//fileName = fileMap.get(businessType).getOriginalFilename() ;
	    			    	File dir = new File(configInfo.getFile_root().concat(contextPath));
	    					if(!dir.isDirectory()){
	    						dir.mkdirs();
	    					}
	    					FileCopyUtils.copy(fileMap.get(businessType).getBytes(),new File(configInfo.getFile_root().concat(contextPath).concat(fileName+prefix)));
	    			      }
						 String url = params.get("uri_fit")+"/codfile"+contextPath+fileName+prefix;  												
						   out.println("<script type=\"text/javascript\">");    
						    out.println("var api = frameElement.api;api.lock();");    
						    out.println("</script>");	
						    out.println("上传成功，下载地址为："+url);    

					} catch (Exception e) {
						// TODO Auto-generated catch block
						  e.printStackTrace();
						 out.println("<script type=\"text/javascript\">");    
						  out.println(" alert('添加失败！！'); var api = frameElement.api;api.close();");    
						  out.println("</script>");
					}
					  
				}						
				
				
}
