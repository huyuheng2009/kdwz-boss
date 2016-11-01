package com.yogapay.boss.utils.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * 页面常用标签，如截取长度
 * 
 * 
 */
@SuppressWarnings("serial")
public class Substring extends BodyTagSupport {

	/**
	 * 需要截取的字符串长度
	 */
	private int length;

	private String content;


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
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotEmpty(content)) {
			if (content.length() > length) {
				sb.append("<bb title=" + content + ">");
				content = content.substring(0, length) + "...";
				sb.append(content);
				sb.append("</bb>");
				content = sb.toString();
			}

			try {
				out.write(content);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doEndTag();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
