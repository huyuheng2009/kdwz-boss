package com.yogapay.boss.utils.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.yogapay.boss.utils.StringUtil;

/**
 * 卡号隐藏
 * 
 * 
 */
@SuppressWarnings("serial")
public class TextHidden extends BodyTagSupport {

	private String text;

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
		if (StringUtils.isNotEmpty(text)) {
			try {
				out.write(StringUtil.TextHidden(text));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doEndTag();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	

}
