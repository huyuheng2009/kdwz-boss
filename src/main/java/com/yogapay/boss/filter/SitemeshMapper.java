package com.yogapay.boss.filter;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.PrintableDecoratorMapper;
import com.yogapay.boss.utils.StringUtil;

public class SitemeshMapper extends PrintableDecoratorMapper{
	


    public SitemeshMapper()
    {
    }

    public void init(Config config, Properties properties, DecoratorMapper parent)
        throws InstantiationException
    {
        super.init(config, properties, parent);
        decorator = properties.getProperty("decorator");
        paramName = properties.getProperty("parameter.name", "printable");
        paramValue = properties.getProperty("parameter.value", "true");
    }

    @Override
    public Decorator getDecorator(HttpServletRequest request, Page page)
    {
        Decorator result = null;
        String val = request.getParameter(paramName) ;
        if (StringUtil.isEmpty(val)) {
			val = request.getAttribute(paramName)==null?"":request.getAttribute(paramName).toString() ;
		}
        if(decorator != null && paramValue.equalsIgnoreCase(val))
            result = getNamedDecorator(request, decorator);
        return result != null ? result : super.getDecorator(request, page);
    }

    private String decorator;
    private String paramName;
    private String paramValue;


}
