package com.yogapay.boss.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.jsp.JspFactory;

import org.springframework.beans.factory.access.el.SimpleSpringBeanELResolver;
import org.springframework.web.context.ContextLoaderListener;

public class InitBean extends ContextLoaderListener{

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		JspFactory.getDefaultFactory().getJspApplicationContext(event.getServletContext()).addELResolver(new SimpleSpringBeanELResolver(getCurrentWebApplicationContext()));
	}

	
}
