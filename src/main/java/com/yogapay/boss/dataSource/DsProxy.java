package com.yogapay.boss.dataSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;




import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;

public class DsProxy implements InvocationHandler {
	
	private  DynamicDataSourceHolder dynamicDataSourceHolder ;

	  public Object newInstance() {  
		  return Proxy.newProxyInstance(Log4jdbcProxyDataSource.class.getClassLoader(), Log4jdbcProxyDataSource.class.getInterfaces(), this);
	  }
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result=null;  
		DataSource ds =  dynamicDataSourceHolder.getDataSouce() ; 
            result=method.invoke(ds, args);  
       
        return result;  
	}

	public DynamicDataSourceHolder getDynamicDataSourceHolder() {
		return dynamicDataSourceHolder;
	}

	public void setDynamicDataSourceHolder(
			DynamicDataSourceHolder dynamicDataSourceHolder) {
		this.dynamicDataSourceHolder = dynamicDataSourceHolder;
	}

	

}
