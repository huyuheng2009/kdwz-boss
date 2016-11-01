package com.yogapay.boss.controller;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.service.BossMsgService;
import com.yogapay.boss.service.CodSettleUserService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.LgcService;
import com.yogapay.boss.service.MenuService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.ConstantsLoader;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.RandomValidateCode;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.jpos.transaction.participant.HasEntry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统登录，注销，首页等
 * 
 * @author dj
 * 
 */
@Controller
public class MainController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private LgcService lgcService ;
	@Resource
	private BossMsgService bossMsgService ;
	@Resource
	private CourierService courierService ;
	@Resource
	private MobileUserService mobileUserService ;
	@Resource
	private CodSettleUserService codSettleUserService ;
	@Resource
	private MenuService menuService ;
	@Resource
	private SubstationService substationService ;

	// 用于未通过系统验证输入URL的跳转页面
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
	
		return "login";
	}

	// 用户登录验证,用于提交form表单数据验证
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam Map<String, String> params,
			HttpServletRequest request, Model model) throws SQLException {

		BossUser user = null;
		try {
         System.out.println("*****************");
			user = userService.getUserByPwd((String) params.get("username"),
					Md5.md5Str((String) params.get("password")));

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (SecurityUtils.getSubject().isAuthenticated() && user != null
				&& Integer.parseInt(user.getStatus()) != 0) {
				return "redirect:/";
		} else {

		BossUser user1 = userService.getByUserName(params.get("username"));
			if (null != user1 && null != user1.getFailTimes()) {
				if (user1.getFailTimes() > 20) {
                   if(!user1.getUserName().equals("admin")&&!user1.getUserName().equals("root")){
                	   userService.status(params.get("username"), 0);
                   }
				}
			}
			userService.failTimes(params.get("username"));
		}
		if (user == null) {
			request.setAttribute(
					FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,
					"IncorrectCredentialsException");
		} else {
			if(!"1".equals(user.getStatus())){
				request.setAttribute(
						FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,
						"UserCloseException");
			}/*else if (!params.get("captcha").toUpperCase().equals(request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY))) {
				System.out.println("----------cc-------->"+params.get("captcha").toUpperCase());
				System.out.println("----------ss-------->"+request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY));
				request.setAttribute(
						FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,
						"RandomValidateCodeException");
			}*/else {
				request.setAttribute(
						FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,
						"UnknownAccountException");
			}
		}
		return "login";
	}

	// 修改密码
	@RequestMapping(value = "/changePwd")
	public String changePwd() {
		return "person/changePwd";
	}

	// 保存修改的密码
	@RequestMapping(value = "/savepwd")
	public void savepwd(@RequestParam Map<String, String> params,
			HttpServletResponse response) throws SQLException {
		String oldPwd = params.get("oldpwd");
		String pwd = params.get("pwd");
		BossUser bu = userService.getByUserName(Constants.getUser()
				.getUserName());
		if ("root".equals(bu.getUserName())) {
			outText("2", response);
			return;
		}
		if (!(bu.getPassword().equals(Md5.md5Str(oldPwd)))) {
			outText("3", response);
			return;
		}
		bu.setPassword(Md5.md5Str(pwd.trim()));
		bu.setUpdateTime(DateUtils.formatDate(new Date()));
		userService.updateUser(bu, null,null,null) ;
		outText("1", response);
	}

	// 换肤
		@RequestMapping(value = "/cstyle")
		public void cstyle(@RequestParam Map<String, String> params,
				HttpServletResponse response) throws SQLException {
			String layoutPage = params.get("layoutPage");
			if ("01".equals(layoutPage)||StringUtil.isEmptyWithTrim(layoutPage)) {
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		        bossUser.setLayoutPage(layoutPage);
				SecurityUtils.getSubject().getSession().setAttribute("user", bossUser);
			}
			outText("1", response);
		}

	
	
	// 用于生成验证码
			@RequestMapping(value = "/identifyImage")
			public void identifyImage(@RequestParam Map<String, String> params,
					HttpServletRequest request ,HttpServletResponse response) throws SQLException {
				response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
		        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
		        response.setHeader("Cache-Control", "no-cache");
		        response.setDateHeader("Expire", 0);
		        RandomValidateCode randomValidateCode = new RandomValidateCode();
		       // request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY);
		        try {
		            randomValidateCode.getRandcode(request, response);//输出图片方法
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
			
			
			
			
			@RequestMapping(value = "/bossMsg")
			public void bossMsg(@RequestParam Map<String, String> params,
					HttpServletResponse response) throws SQLException {
			    Map<String, Object> msg = bossMsgService.getFirst() ;
			    BossUser user = Constants.getUser() ;
			    if (msg!=null) {
			    	 msg.put("tips", user.getTips()) ;
				}
				outText(JsonUtil.toJson(msg), response);
			}	
			
			@RequestMapping(value = "/balance")
			public void balance(@RequestParam Map<String, String> params,
					HttpServletResponse response,HttpServletRequest request) throws SQLException {
				String r = "err" ;
				try {
			       String sno  =  StringUtils.nullString(request.getSession().getAttribute("sno")) ;
                       if (StringUtils.isEmptyWithTrim(sno)) {
				      r = "0" ;
			         }else {
					  r = substationService.queryBalance(sno,false) ;
				  }
				} catch (Exception e) {
					e.printStackTrace();
				}
				outText(r, response);
			}	
	
			@RequestMapping(value = "/msgRead")
			public void msgRead(@RequestParam Map<String, String> params,
					HttpServletResponse response) throws SQLException {
			   bossMsgService.readed(Integer.MAX_VALUE);
			}	
		
			@RequestMapping(value = "/closeMsg")
			public void closeMsg(@RequestParam Map<String, String> params,
					HttpServletResponse response,String tips) throws SQLException {
				BossUser user = Constants.getUser() ;
				if (!"Y".equals(tips)) {
					user.setTips("N");
				}else {
					user.setTips("Y");
				}
				SecurityUtils.getSubject().getSession().setAttribute("user", user);
			}		
					
		
			
			@RequestMapping(value = "/clistBySno")
			public void clistBySno(@RequestParam Map<String, String> params,
					HttpServletResponse response,String sno) throws SQLException {
				Map<String, String> pMap = new HashMap<String, String>() ;
				pMap.put("substationNo", sno) ;
				PageInfo<Map<String, Object>> courierList = courierService.list(pMap,getPageInfo(1,5000));
				Map<String, Object> retMap = new HashMap<String, Object>() ;
				retMap.put("clist",courierList.getList()) ;
				outJson(JsonUtil.toJson(retMap), response);
			}		
			
		
			@RequestMapping(value = "/updata")
			public void updata(@RequestParam Map<String, String> params,HttpServletResponse response,HttpServletRequest request) throws SQLException {
				   BossUser user = Constants.getUser() ;
				   System.out.println(request.getRealPath("/"));
				   Map<String, Object> ret = new HashMap<String, Object>() ;
				   Map<String, Object> ret2 = new HashMap<String, Object>() ;
				   LgcConfig lgcConfig = (LgcConfig) request.getSession().getAttribute("lgcConfig") ;
				   String rootDir = configInfo.getFile_root() ;
				   String key = "com" ;
				   if (lgcConfig!=null) {
					  key = lgcConfig.getKey() ;
			       }
				   FileWriter fw = null ;
				   FileWriter fw2 = null ;
				try {
					   File f = new File(rootDir+"/tmjs/"+key);
					   if (!f.exists()) {
						    f.mkdirs() ;
					    }
					   
					   File file = new File(rootDir+"/tmjs/"+key+"/"+user.getUserName()+"_01.js");
					   if (!file.exists()) {
						   file.createNewFile() ;
					    }
					   
					   File file2 = new File(rootDir+"/tmjs/"+key+"/"+user.getUserName()+"_02.js");
					   if (!file2.exists()) {
						   file2.createNewFile() ;
					    }
					   
					fw =  new FileWriter(file);  
					fw2 =  new FileWriter(file2);  
					   
					Map<String, String> pMap = new HashMap<String, String>() ;
					Map<String, String> pMap2 = new HashMap<String, String>() ;
					pMap = setSubstationNo(pMap,request) ;
					
					List<Map<String, Object>> substations = userService.getCurrentSubstation("1");
					List<Map<String, Object>> substations2 = userService.getCurrentSubstation("0");
					ret.put("slist", substations) ;
					ret2.put("slist", substations2) ;
					
					pMap.put("cstatus", "1") ;
					pMap2.put("cstatus", "1") ;
					List<Map<String, Object>> courierList = courierService.alist(pMap);
					List<Map<String, Object>> courierList2 = courierService.alist(pMap2);
	                ret.put("clist", courierList) ;
	                ret2.put("clist", courierList2) ;
	                
	            	List<Map<String, Object>> mList = mobileUserService.museraList(params) ;
	            	ret.put("mlist", mList) ;
	            	ret2.put("mlist", mList) ;
	            	 
					List<Map<String, Object>> codList = codSettleUserService.alist(params) ;
					ret.put("codlist", codList) ;
					ret2.put("codlist", codList) ;
	                ret.put("su", "1") ;
	                ret2.put("su", "1") ;
	                
	                fw.write("var tmjs = "+JsonUtil.toJson(ret));
	                fw2.write("var tmjs = "+JsonUtil.toJson(ret2));
	                fw.flush();
	                fw2.flush();
				} catch (Exception e) {
					e.printStackTrace();
					ret.put("slist", null) ;
					ret.put("clist", null) ;
					ret.put("mlist", null) ;
					ret.put("codlist", null) ;
					ret.put("su", "更新失败") ;
				}finally{
					if (fw!=null) {
						 try {
							fw.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				
				outText(JsonUtil.toJson(ret), response);
			}	
			
			@RequestMapping(value = "/other/page/{mid}")
			public String otherPage(@PathVariable String mid,@RequestParam Map<String, String> params,final ModelMap model) throws SQLException {
				Map<String, Object> menu =  menuService.getMenu2ById(mid) ;
				if (menu!=null) {
					params.put("url", menu.get("url").toString()) ;
				}
				model.put("params", params) ;
				return "other/page" ;
			}		
	
			@RequestMapping(value = "/getSnameBycno")
			public void getSnameBycno(@RequestParam Map<String, String> params,
					HttpServletResponse response) throws SQLException {
			    String cno = params.get("cno") ;
			    String ret = "" ;
		     Substation substation =  substationService.getSubstationByCourierNo(cno) ;
		       if (substation!=null) {
				  ret = substation.getSubstationName() ;
			  }
				outText(ret, response);
			}		
			
}
