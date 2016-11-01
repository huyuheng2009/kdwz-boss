package com.yogapay.boss.filter;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.exception.ParamsUncheckException;
import com.yogapay.boss.utils.StringUtil;

public class ParamsInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object o) throws Exception {
		Map paramsMap = request.getParameterMap();
		for (Iterator<Map.Entry> it = paramsMap.entrySet().iterator(); it
				.hasNext();) {
			Map.Entry entry = it.next();
			Object[] values = (Object[]) entry.getValue();
			for (Object obj : values) {
				String oldStr = obj.toString();
				String newStr = StringUtil.StringFilter(oldStr);
				if (!oldStr.equals(newStr)) {
					throw new ParamsUncheckException();
				}
			}
		}
	
		return super.preHandle(request, response, o);
	}

	public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1,
			Object o, ModelAndView mav) throws Exception {
	}

	public void afterCompletion(HttpServletRequest hsr,
			HttpServletResponse hsr1, Object o, Exception excptn)
			throws Exception {
	}
}