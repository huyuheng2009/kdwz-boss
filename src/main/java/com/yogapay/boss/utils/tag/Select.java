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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.SysDict;
import com.yogapay.boss.service.CacheService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.StringUtil;

/**
 * Select列表框
 * 
 * @author donjek
 * 
 */
public class Select extends BodyTagSupport implements ApplicationContextAware {
	private static ApplicationContext applicationContext; // Spring应用上下文环境
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// select的name
	private String sname;
	// select数据来源，agent
	private String stype;
	// 默认选中值
	private String value;

	private String style;

	private String id;

	private String disabled;

	private String def;
	
	
	private String classv ;

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
		try {
			if (StringUtils.isEmpty(style)) {
				style = "padding:2px;width:140px";
			}
            Map<String, Object> params = new HashMap<String, Object>() ;
			CacheService cacheService = applicationContext.getBean(CacheService.class);
			StringBuffer sb = new StringBuffer();
			if ("MATTER_NAME".equals(stype)) {   //物料品名
					params.put("lgcNo", Constants.getUser().getLgcNo()) ;
					List<Map<String, Object>> list =  baseDao.getByPage("MatterPro.list", new Page(1, 500), params).getList() ;
					for (Map<String, Object> m : list) {
						if (m.get("id").toString().equals(getValue())) {
							sb.append("<option  value=\"" + m.get("id") + "\""+ "selected>" + m.get("matter_name") + "</option>");
						} else {
							sb.append("<option  value=\"" + m.get("id") + "\" >"+ m.get("matter_name") + "</option>");
						}
					}
				
			}else if ("MATTER_TYPE".equals(stype)) {   //物料类型
				   //物料品名
				params.put("lgcNo", Constants.getUser().getLgcNo()) ;
				List<Map<String, Object>> list =  baseDao.getByPage("MatterType.list", new Page(1, 500), params).getList() ;
				for (Map<String, Object> m : list) {
					if (m.get("id").toString().equals(getValue())) {
						sb.append("<option  value=\"" + m.get("id") + "\""+ "selected>" + m.get("type_name") + "</option>");
					} else {
						sb.append("<option  value=\"" + m.get("id") + "\" >"+ m.get("type_name") + "</option>");
					}
				}
			}else if ("PRO_REASON".equals(stype)) {   //问题件原因
				List<Map<String, Object>> list =  baseDao.getByPage("ProOrderReason.list", new Page(1, 500), params).getList() ;
				for (Map<String, Object> m : list) {
					if (m.get("id").toString().equals(getValue())) {
						sb.append("<option  value=\"" + m.get("id") + "\""+ "selected>" + m.get("context") + "</option>");
					} else {
						sb.append("<option  value=\"" + m.get("id") + "\" >"+ m.get("context") + "</option>");
					}
				}
				
			}else if ("DEAL_STATUS".equals(stype)) {   //问题件状态
				List<Map<String, Object>> list =  baseDao.getByPage("ProOrderStatus.list", new Page(1, 500), params).getList() ;
				for (Map<String, Object> m : list) {
					if (m.get("id").toString().equals(getValue())) {
						sb.append("<option  value=\"" + m.get("id") + "\""+ "selected>" + m.get("content") + "</option>");
					} else {
						sb.append("<option  value=\"" + m.get("id") + "\" >"+ m.get("content") + "</option>");
					}
				}
				
			}else if ("ITEM_TYPE".equals(stype)) {   //物品类型
				List<Map<String, Object>> list =  baseDao.getByPage("ItemType.list", new Page(1, 500), params).getList() ;
				for (Map<String, Object> m : list) {
					if (m.get("item_text").toString().equals(getValue())) {
						sb.append("<option  value=\"" + m.get("item_text") + "\""+ "selected>" + m.get("item_text") + "</option>");
					} else {
						sb.append("<option  value=\"" + m.get("item_text") + "\" >"+ m.get("item_text") + "</option>");
					}
				}
				
			}else if ("AGING_TYPE".equals(stype)) {   //时效类型
				List<Map<String, Object>> list =  baseDao.getByPage("AgingType.list", new Page(1, 500), params).getList() ;
				for (Map<String, Object> m : list) {
					if (m.get("item_text").toString().equals(getValue())) {
						sb.append("<option  value=\"" + m.get("item_text") + "\""+ "selected>" + m.get("item_text") + "</option>");
					} else {
						sb.append("<option  value=\"" + m.get("item_text") + "\" >"+ m.get("item_text") + "</option>");
					}
				}
				
			}else{
				List<SysDict> dicts = null;
				try {
					dicts = cacheService.findByName(stype);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (SysDict m : dicts) {
					if (m.getDictKey().equals(getValue())) {
						sb.append("<option  value=\"" + m.getDictKey() + "\""
								+ "selected>" + m.getDictValue() + "</option>");
					} else {
						sb.append("<option  value=\"" + m.getDictKey() + "\" >"
								+ m.getDictValue() + "</option>");
					}
				}
			}
			
			String allOption = "";
			if (StringUtils.isNotEmpty(def)) {
				if (!"none".equals(def)) {
					allOption = "<option value=''>--" + def + "--</option>";
				}
			} else {
				allOption = "<option value=''>--请选择--</option>";
			}
            
			if (StringUtil.isEmptyString(classv)) {
				classv = "lll" ;
			}
			if (StringUtils.isEmpty(disabled)) {
				out.append("<select id='" + id + "' class='" + classv + "'   style=\"" + style + "\" "
						+ value + " name=\"" + sname + "\">" + allOption
						+ sb.toString() + "</select>");

			} else {
				out.append("<select id='" + id + "'  class='" + classv + "' disabled  style=\"" + style
						+ "\" " + value + " name=\"" + sname +  "\"> "
						+ allOption + sb.toString() + "</select>");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisabled() {
		return disabled;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		// TODO Auto-generated method stub
		applicationContext = ctx;
	}

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	public String getClassv() {
		return classv;
	}

	public void setClassv(String classv) {
		this.classv = classv;
	}

}
