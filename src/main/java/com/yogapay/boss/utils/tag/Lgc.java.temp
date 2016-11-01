package com.yogapay.boss.utils.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class Lgc extends BodyTagSupport implements ApplicationContextAware {
	private static ApplicationContext applicationContext; // Spring应用上下文环境
	/**
	 * 字典名称 dict_name
	 */
	private String lgcNo;

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
		BaseDao baseDao = applicationContext.getBean(BaseDao.class); 
		String resultVal = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("lgcNo", lgcNo);
			com.yogapay.boss.domain.Lgc lgcInfo = baseDao.getOne("Lgc.getByLgcNo", params);
			resultVal = lgcInfo!=null?lgcInfo.getName():"无";
			out.write(resultVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	public String getLgcNo() {
		return lgcNo;
	}

	public void setLgcNo(String lgcNo) {
		this.lgcNo = lgcNo;
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;

	}

}
