package com.yogapay.boss.utils.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.SysDict;
import com.yogapay.boss.service.CityService;

/**
 * 字典项查询
 * 
 * 
 */
@SuppressWarnings("serial")
public class City extends BodyTagSupport implements ApplicationContextAware {
	private static ApplicationContext applicationContext; // Spring应用上下文环境
	/**
	 * 字典名称 dict_name
	 */
	private int cityId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
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
		CityService cityService = applicationContext.getBean(CityService.class); 
		String resultVal = "";
		try {
			com.yogapay.boss.domain.City city = cityService.getById(cityId) ;
			resultVal = city!=null?city.getName():"无";
			out.write(resultVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	
	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;

	}

}
