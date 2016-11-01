package com.yogapay.boss.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yogapay.boss.controller.ConfigInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dataSource.DynamicDataSourceHolder;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.service.LgcResourceService;
import com.yogapay.boss.service.LgcService;
import com.yogapay.boss.utils.ConstantsLoader;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

public class BlankSpaceFilter implements Filter{
	private static ApplicationContext applicationContext; // Spring应用上下文环境

	private String charset = "UTF-8";
	private FilterConfig config;

	public void destroy() {
		charset = null;
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);	
		
		
        
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response ;
		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
		String uri = req.getRequestURI() ;
		LgcConfig lgcConfig = (LgcConfig) req.getSession().getAttribute("lgcConfig") ;
		String host = req.getHeader("host") ;
		System.out.println("------->"+host);
		DynamicDataSourceHolder dynamicDataSourceHolder = applicationContext.getBean(DynamicDataSourceHolder.class) ;
		ConfigInfo configInfo = applicationContext.getBean(ConfigInfo.class) ;
		if ("true".equals(configInfo.getDebug())) {
			host = "debug_host" ;
			System.out.println("------->"+host);
		}
		
		if ("/login".equals(uri)||lgcConfig==null||!host.toLowerCase().equals(lgcConfig.getHost().toLowerCase())
				||uri.startsWith("/download/app/")) {
			
			if (StringUtil.isEmptyWithTrim(host)) {
				System.out.println("host null");
				return ;
			}
			
			if (!dynamicDataSourceHolder.isExitHost(host,applicationContext)) {
				System.out.println("host err");
				return ;
			}
			
			Map<String, String> map = dynamicDataSourceHolder.hostKey.get(host) ;
			String key = map.get("key") ;
			String lgcNo = map.get("lgcNo") ;
			
			dynamicDataSourceHolder.setDataSource(key);
			//dynamicDataSourceHolder.setKey(key);
			System.out.println("_______________________>"+dynamicDataSourceHolder);
			System.out.println("----------------------->"+key);
			lgcConfig = new LgcConfig() ;
			lgcConfig.setLgcNo(lgcNo);
			lgcConfig.setKey(key);
			lgcConfig.setHost(host);
			
			LgcService lgcService = applicationContext.getBean(LgcService.class); 
			LgcResourceService lgcResourceService = applicationContext.getBean(LgcResourceService.class);
			Lgc lgc = lgcService.getLgcByNo(lgcConfig.getLgcNo()) ;
			lgcConfig.setWebTitle(lgc.getWebTitle());
			lgcConfig.setUbname(lgc.getUbname());
			lgcConfig.setCbname(lgc.getCbname());
			lgcConfig.setDefaultCity(lgc.getDefaultCity());
			lgcConfig.setWebUrl(lgc.getWebsite());
			lgcConfig.setReportExam(lgc.getReportExam());
			Map<String, String>  resMap = new HashMap<String, String>() ;
			String name[] = {"logo_login","logo_old","logo_new","title"} ;
			for (int i = 0; i < name.length; i++) {
				Map<String, Object> lgcRes = lgcResourceService.getLgcResource(lgcNo, name[i], "0000") ;
				if (lgcRes!=null) {
					resMap.put(name[i], StringUtils.nullString(lgcRes.get("res_url"))) ;
				}else {
					resMap.put(name[i], "") ;
				}
			}
			lgcConfig.setRes(resMap);
			
			try {
				 SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
			} catch (Exception e) {
				// TODO: handle exception
			}
			 
			 req.getSession().setAttribute("lgcConfig", lgcConfig);
		}else {
			dynamicDataSourceHolder.setDataSource(lgcConfig.getKey());
			//dynamicDataSourceHolder.setKey(lgcConfig.getKey());
		}
	
		String furi[] = {"/identifyImage","/login","/download/app/android","/download/app/ios"} ;
		boolean f = false ;
		for (int i = 0; i < furi.length; i++) {
			if (uri.equals(furi[i])) {
				f = true ;
				break ;
			}
		}
		
		if (f||uri.startsWith("/themes/")||uri.startsWith("/scripts/")) {
			chain.doFilter(req, response);
		}else {
			System.out.println(req.getRequestURI());
			if (f) {
				chain.doFilter(req, response);
				return ;
			}
			
		    int b = 0 ;
			BossUser bossUser =   (BossUser)req.getSession().getAttribute("user");
			if (bossUser==null) {
				Object o = req.getSession().getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
				if (o instanceof BossUser) {
					bossUser = (BossUser) o ;
					b = 1 ;
				}
			}	
			BossUser bu = null ;
			if (bossUser!=null) {
				
				BaseDao baseDao = applicationContext.getBean(BaseDao.class); 
			    bu = baseDao.getFrist("BossUser.getByUserName", bossUser.getUserName()) ;
			    if (!StringUtil.isEmptyWithTrim(bossUser.getLayoutPage())) {
					request.setAttribute("layoutPage", bossUser.getLayoutPage());
				}else {
					request.setAttribute("layoutPage", "");
				}
		}
		
		if (bu==null) {
			    if(bossUser!=null){
			    	 SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
			    	 req.getSession().removeAttribute("user");
			    	 //req.getSession().removeAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
			    	 req.setAttribute("msg", "用户被删除，请重新登录");
			    }
			    
			    Cookie cookie1 = new Cookie("boss.lgcNo", null) ;
				Cookie cookie2 = new Cookie("boss.uid", null) ;
				Cookie cookie3 = new Cookie("boss.timestamp", null) ;
				Cookie cookie4 = new Cookie("boss.sign", null) ;
				cookie1.setMaxAge(0);
				cookie2.setMaxAge(0);
				cookie3.setMaxAge(0);
				cookie4.setMaxAge(0);
				rep.addCookie(cookie1);
				rep.addCookie(cookie2);
				rep.addCookie(cookie3);
				rep.addCookie(cookie4);
				
				
				rep.setHeader("Cache-Control", "no-store");  
				rep.setDateHeader("Expires", 0);  
				rep.setHeader("Prama", "no-cache");  
				rep.sendRedirect("/login");
		}else {
			if (bu.getFailTimes()<0) {
				 if(bossUser!=null){
					 SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
			    	 req.getSession().removeAttribute("user");
			    	 //req.getSession().removeAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
			    	 req.setAttribute("msg", "用户被编辑，请重新登录");
			    }
				 
				 Cookie cookie1 = new Cookie("boss.lgcNo", null) ;
					Cookie cookie2 = new Cookie("boss.uid", null) ;
					Cookie cookie3 = new Cookie("boss.timestamp", null) ;
					Cookie cookie4 = new Cookie("boss.sign", null) ;
					cookie1.setMaxAge(0);
					cookie2.setMaxAge(0);
					cookie3.setMaxAge(0);
					cookie4.setMaxAge(0);
					rep.addCookie(cookie1);
					rep.addCookie(cookie2);
					rep.addCookie(cookie3);
					rep.addCookie(cookie4); 
					
					
				rep.setHeader("Cache-Control", "no-store");  
				rep.setDateHeader("Expires", 0);  
				rep.setHeader("Prama", "no-cache");  
				rep.sendRedirect("/login");
			}else {
				if (req.getMethod().equalsIgnoreCase("get")) {
					req = new BlankSpaceRequestWrapper(req);
				}
				try{
					
					lgcConfig.setCurName(bu.getUserName());
					req.getSession().setAttribute("lgcConfig", lgcConfig);
					
					//写入cookie
					Cookie cookie1 = new Cookie("boss.lgcNo", lgcConfig.getLgcNo()) ;
					Cookie cookie2 = new Cookie("boss.uid", bu.getUserName()) ;
					String timestamp = String.valueOf(System.currentTimeMillis()) ;
					Cookie cookie3 = new Cookie("boss.timestamp", timestamp) ;
					String sign = Md5.md5Str(lgcConfig.getLgcNo()+bu.getUserName()+timestamp+"HzgSXlo8") ;
					Cookie cookie4 = new Cookie("boss.sign", sign) ;
					//cookie1.setDomain(".yogapay.com");
					cookie1.setPath("/");
					cookie1.setMaxAge(1800);
					
					//cookie2.setDomain(".yogapay.com");
					cookie2.setPath("/");
					cookie2.setMaxAge(1800);
					
					//cookie3.setDomain(".yogapay.com");
					cookie3.setPath("/");
					cookie3.setMaxAge(1800);
					
					//cookie4.setDomain(".yogapay.com");
					cookie4.setPath("/");
					cookie4.setMaxAge(1800);
					
					rep.addCookie(cookie1);
					rep.addCookie(cookie2);
					rep.addCookie(cookie3);
					rep.addCookie(cookie4);
					
				chain.doFilter(req, response);
				}finally{
					dynamicDataSourceHolder.remove();
				}
			}
			
			
		}
	  }
		
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		String charset = config.getServletContext().getInitParameter("charset");
		if (charset != null && charset.trim().length() != 0) {
			this.charset = charset;
		}
	}
	
}