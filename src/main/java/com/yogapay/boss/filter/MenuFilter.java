package com.yogapay.boss.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dataSource.DynamicDataSourceHolder;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.service.LgcService;
import com.yogapay.boss.service.MenuService;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

public class MenuFilter implements Filter{
	private static ApplicationContext applicationContext; // Spring应用上下文环境


	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {	
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response ;
		String uri = req.getRequestURI() ;
		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
		MenuService menuService ;
		String furi[] = {"/identifyImage"} ;
		boolean f = false ;
		for (int i = 0; i < furi.length; i++) {
			if (uri.equals(furi[i])) {
				f = true ;
				break ;
			}
		}
		if (f||uri.startsWith("/themes/")||uri.startsWith("/scripts/")||uri.startsWith("/download/app")) {
			
		}else {
			
			menuService = applicationContext.getBean(MenuService.class) ;
			Map<String, Object> menu =  menuService.getMenuByUri(uri) ;
			  
			String b2 =  req.getParameter("b2") ;
			String mid = req.getParameter("mid") ;
			Map<String, Object> menu2 = null ;
			if ("1".equals(b2)&&!StringUtils.isEmptyWithTrim(mid)) {
				menu2 =  menuService.getMenu2ById(mid) ;
			}
			if (menu!=null) {
				menu.put("b2_url", "") ;
				if (menu2!=null) {
					menu.put("b2_url", menu2.get("url")) ;
					menu.put("menu_uri", menu2.get("cmenu_url")) ;
					menu.put("position_text", menu2.get("menu_text")) ;
				}
				if ("1".equals(menu.get("sub_limit").toString())) {
					req.getSession().setAttribute("cmenu_sub_limit", "01");
				}else {
					req.getSession().setAttribute("cmenu_sub_limit", "02");
				}
				req.getSession().setAttribute("cmenu", menu);
				request.setAttribute("sub_limit", menu.get("sub_limit"));
			}
		}
		chain.doFilter(request, response);
	}


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
}