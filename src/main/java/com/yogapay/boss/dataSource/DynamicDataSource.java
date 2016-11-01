package com.yogapay.boss.dataSource;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.alibaba.druid.pool.DruidDataSource;
import com.yogapay.boss.service.ProjectDsService;
import com.yogapay.boss.utils.StringUtil;

public class DynamicDataSource implements ApplicationContextAware,ApplicationListener<ContextRefreshedEvent>{
	
	private Map<Object, DataSource> targetDataSources;

	private DataSource defaultTargetDataSource;

	private static ApplicationContext ctx;  
    
    private Map<Object,DataSource> tdsTemp = new HashMap<Object,DataSource>();  //保存xml配置的数据源，用于刷新数据源
    
    private Map<String, Map<String, String>> hostKey = new HashMap<String,  Map<String, String>>() ;
	
	
	
	
	private synchronized void initailizeDataSource(String proKey) throws Exception {  
        DefaultListableBeanFactory dlbf  = (DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory();  
         ProjectDsService projectDsService =   ctx.getBean(ProjectDsService.class) ;
         List<MDataSource> ds = projectDsService.getByProKey(proKey) ;
         
        // 获取配置的数据源  
        for(MDataSource mDataSource: ds){  
        	if (!dlbf.containsSingleton(mDataSource.getKey())) {
        		 DataSource dataSource = newDruidDataSource(mDataSource) ;
                 // 将数据源交给spring管理  
                 dlbf.registerSingleton(mDataSource.getKey(), dataSource);  
                 targetDataSources.put(mDataSource.getKey(), dataSource);  
                 Map<String, String> map = new HashMap<String, String>() ;
                 map.put("key", mDataSource.getKey()) ;
                 map.put("lgcNo", mDataSource.getLgcNo()) ;
                 hostKey.put(mDataSource.getHost(),map) ;
			}
        } 
        //DynamicDataSourceHolder.setKeyList(tds);
       // DynamicDataSourceHolder.setHostKey(hostKey);
    }

	@Override
	public void setApplicationContext(ApplicationContext ctx)throws BeansException {
		// TODO Auto-generated method stub
		this.ctx = ctx ;
	}  

	
	public DataSource newDruidDataSource(MDataSource mdataSource) throws SQLException{
		DruidDataSource dataSource = new DruidDataSource() ;
		dataSource.setUrl(mdataSource.getDbUrl());
		dataSource.setUsername(mdataSource.getDbUsername());
		dataSource.setPassword(mdataSource.getDbPassword());
		dataSource.setMinIdle(StringUtil.isEmptyWithTrim(mdataSource.getMinIdle())?1:Integer.valueOf(mdataSource.getMinIdle()));
		dataSource.setMaxActive(StringUtil.isEmptyWithTrim(mdataSource.getMaxActive())?50:Integer.valueOf(mdataSource.getMaxActive()));
		dataSource.setInitialSize(StringUtil.isEmptyWithTrim(mdataSource.getInitialSize())?1:Integer.valueOf(mdataSource.getInitialSize()));
		dataSource.setMaxWait(StringUtil.isEmptyWithTrim(mdataSource.getMaxWait())?10000:Long.valueOf(mdataSource.getMaxWait()));
		dataSource.setTimeBetweenEvictionRunsMillis(StringUtil.isEmptyWithTrim(mdataSource.getTimeBetweenEvictionRunsMillis())?60000:Long.valueOf(mdataSource.getTimeBetweenEvictionRunsMillis()));
		dataSource.setMinEvictableIdleTimeMillis(StringUtil.isEmptyWithTrim(mdataSource.getMinEvictableIdleTimeMillis())?1800000:Long.valueOf(mdataSource.getMinEvictableIdleTimeMillis()));
	    dataSource.setMaxPoolPreparedStatementPerConnectionSize(StringUtil.isEmptyWithTrim(mdataSource.getMaxPoolPreparedStatementPerConnectionSize())?50:Integer.valueOf(mdataSource.getMaxPoolPreparedStatementPerConnectionSize()));
	    dataSource.setValidationQuery("SELECT 1");
	    dataSource.setTestOnBorrow(false);
	    dataSource.setTestOnReturn(false);
	    dataSource.setTestWhileIdle(true);
	    dataSource.init();
	    return dataSource ;
	}
	
	public void refulsh(String proKey)  {
		try {
			initailizeDataSource(proKey) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
       
		if (contextRefreshedEvent.getApplicationContext().getParent()==null) {
			  System.out.println("**************onApplicationEvent***********begin*********");
		       try {
				initailizeDataSource("codtsi") ;
			} catch (Exception e) {
				 System.out.println("**************initailizeDataSource***********err*********");
				e.printStackTrace();
			}
		       System.out.println("**************onApplicationEvent***********end*********");
		}

	}


	public Map<Object, DataSource> getTargetDataSources() {
		return targetDataSources;
	}


	public void setTargetDataSources(Map<Object, DataSource> targetDataSources) {
		this.targetDataSources = targetDataSources;
	}


	public DataSource getDefaultTargetDataSource() {
		return defaultTargetDataSource;
	}


	public void setDefaultTargetDataSource(DataSource defaultTargetDataSource) {
		this.defaultTargetDataSource = defaultTargetDataSource;
	}


	public Map<String, Map<String, String>> getHostKey() {
		return hostKey;
	}


	public void setHostKey(Map<String, Map<String, String>> hostKey) {
		this.hostKey = hostKey;
	}
	
	
	
	
	
	
	
}
