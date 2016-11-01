package com.yogapay.boss.utils.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import javax.servlet.jsp.tagext.TagSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class OptionTag extends TagSupport {

	private static final long serialVersionUID = 20131106L;
	private Map map;
	private Object value;
	private boolean addBlank;
	private boolean pdNull = true;
	private String type;
	private String name;
	private String attribute;
	private String onclick;
	private String persionName;
	private String tagNameShow;
	/**
	 * 按位与算 *
	 */
	public boolean bit = false;

	@Override
	public int doStartTag() throws JspException {
		JspWriter w = pageContext.getOut();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		List<String> persionList = (List<String>) request.getSession().getAttribute("userRight");
		try {
			if (type == null || type.equals("option")) {
				if (addBlank) {
					if (pdNull) {
						if (tagNameShow == null || tagNameShow.equals("")) {
							w.println("<option value=\"\">　</option>");
						} else {
							w.println("<option value=\"\">" + tagNameShow + "</option>");
						}
					} else {
						w.println("<option value=\"0\">　</option>");
					}
				}
				for (Entry t : (Set<Entry>) map.entrySet()) {
					w.print("<option value=\"");
					w.print(t.getKey());
					w.print("\"");
					if (value != null && t.getKey().toString().equals(value.toString())) {
						w.print(" selected");
					}
					w.print(">");
					w.print(t.getValue());
					w.print("</option>\n");
				}
			} else if ("checkbox".equals(type) || "radio".equals(type)) {
				int i = 0;
				for (Entry t : (Set<Entry>) map.entrySet()) {
					w.print("<input value=\"");
					w.print(t.getKey());
					w.print("\" name=\"" + name + "\" id=\"" + (name + "_" + i) + "\" type=\"" + type + "\"");

					if (value != null && bit ? ((Integer.parseInt(t.getKey().toString()) & Integer.parseInt(value.toString()))
							== Integer.parseInt(t.getKey().toString())) : (t.getKey().toString().equals(value.toString()))) {
						w.print(" checked='checked' ");
					}
					w.print(" " + (attribute == null ? "" : attribute) + " />   <label for='" + (name + "_" + i) + "'>" + t.getValue() + "</label> &nbsp;&nbsp;&nbsp;");
					w.print("\n");
					i++;
				}
			} else if ("text".equals(type)) {
				for (Entry t : (Set<Entry>) map.entrySet()) {
					if (value != null && t.getKey().toString().equals(value.toString())) {
						w.print(t.getValue());
					}
				}
			} else if ("persion".equals(type)) {
				if (persionList.contains(name)) {
					return EVAL_BODY_INCLUDE;

				}
			}
		} catch (IOException ex) {
			throw new JspException(ex);
		}
		return SKIP_BODY;
	}

	@Override
	public void release() {
		addBlank = false;
		type = null;
		value = null;
		map = null;
		super.release();
	}

	public String getName() {
		return name;
	}

	public void setBit(boolean bit) {
		this.bit = bit;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isAddBlank() {
		return addBlank;
	}

	public void setAddBlank(boolean addBlank) {
		this.addBlank = addBlank;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getPersionName() {
		return persionName;
	}

	public void setPersionName(String persionName) {
		this.persionName = persionName;
	}

	public boolean isPdNull() {
		return pdNull;
	}

	public void setPdNull(boolean pdNull) {
		this.pdNull = pdNull;
	}

	public String getTagNameShow() {
		return tagNameShow;
	}

	public void setTagNameShow(String tagNameShow) {
		this.tagNameShow = tagNameShow;
	}
}
