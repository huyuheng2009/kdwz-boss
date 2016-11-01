package com.yogapay.boss.utils.tag;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.utils.Dao;
import com.yogapay.boss.utils.StringUtil;

/**
 * 逻辑判断，IF。。。。ELSE 之类的
 * 
 * 
 */
@SuppressWarnings("serial")
public class Logic extends BodyTagSupport implements ApplicationContextAware {

	private static ApplicationContext applicationContext; // Spring应用上下文环境
	private BaseDao baseDao ;
	private String name;

	private String value1;
	private String value2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		baseDao = applicationContext.getBean(BaseDao.class);
		return super.doStartTag();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {

		JspWriter out = this.pageContext.getOut();
		if (StringUtils.isNotEmpty(name)) {
			try {
				// 用户维护时候选择代理商时候的复选框
				if (name.equals("lgcUserChecked")) {
					if (!StringUtils.isEmpty(value1)) {
						boolean isChecked = isLgcUser(Long.parseLong(value1),value2);
						if (isChecked) {
							out.write("checked");
						}
					}
				}
				
				if (name.equals("substationUserChecked")) {
					if (!StringUtils.isEmpty(value1)) {
						boolean isChecked = isSubstationUser(Long.parseLong(value1),value2);
						if (isChecked) {
							out.write("checked");
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doEndTag();
	}

	// 查询用户是否属于代理商
	public boolean isLgcUser(Long userId, String lgcNo) {
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("userId", userId) ;
		params.put("lgcNo", lgcNo);
		List<Map<String, Object>> list = baseDao.getList("BossUser.getUserLgc", params);
		if (null != list && list.size() > 0) {
			return true;
		}
		 return false;
	}
	
	// 查询用户是否属于代理商
		public boolean isSubstationUser(Long userId, String substationNo) {
			Map<String, Object> params = new HashMap<String, Object>() ;
			params.put("userId", userId) ;
			params.put("substationNo", substationNo);
			List<Map<String, Object>> list = baseDao.getList("BossUser.getUserSubstation", params);
			if (null != list && list.size() > 0) {
				return true;
			}
			 return false;
		}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;

	}
}
