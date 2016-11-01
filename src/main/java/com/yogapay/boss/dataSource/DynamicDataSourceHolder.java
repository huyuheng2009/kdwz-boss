package com.yogapay.boss.dataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.Assert;

import bsh.This;

import com.alibaba.druid.pool.DruidDataSource;
import com.yogapay.boss.service.ProjectDsService;
import com.yogapay.boss.utils.StringUtil;

public class DynamicDataSourceHolder implements ApplicationContextAware,ApplicationListener<ContextRefreshedEvent>{
	public static final ThreadLocal<DataSource> holder = new ThreadLocal<DataSource>();  
	public static final Lock lock = new ReentrantLock() ;
	private Map<Object, DataSource> targetDataSources;

	private DataSource defaultTargetDataSource;

	private static ApplicationContext ctx;  
    
    private Map<Object,DataSource> tdsTemp = new HashMap<Object,DataSource>();  //保存xml配置的数据源，用于刷新数据源
    
    public static Map<String, Map<String, String>> hostKey = new HashMap<String,  Map<String, String>>() ;
    private static List<String> keyList = new ArrayList<String>() ;
    public static final ThreadLocal<String> key = new ThreadLocal<String>();  
    
    public void setKey(String keyName) { 
    	key.set(keyName);
    }  
    
    public String getKey() { 
    	return key.get() ;
    }  
	  
    public void setDataSource(String name) { 
    	key.set(name);
        holder.set(determineTargetDataSource(name));  
    }  
  
    public void setDataSource(DataSource dataSource) { 
        holder.set(dataSource);  
    }  
    
    public  DataSource getDataSouce() {  
        return holder.get();  
    }
    public void remove() { 
        holder.remove();
    }  
    
	
	protected synchronized  DataSource determineTargetDataSource(String key) {
		Assert.notNull(this.targetDataSources, "DataSource router not initialized");
		DataSource dataSource = this.targetDataSources.get(key);
		
		if (dataSource == null) {
			dataSource =this.defaultTargetDataSource;
		}
		if (dataSource == null) {
			throw new IllegalStateException(key+" ------------->err");
		}
		return dataSource;
	}
    
	
	
	
	
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
                 keyList.add(mDataSource.getKey()) ;
                 hostKey.put(mDataSource.getHost(),map) ;
                 hostKey.put(mDataSource.getKey()+".pro-boss.yogapay.com",map) ;
                 hostKey.put(mDataSource.getKey()+".uat-boss.yogapay.com",map) ;
                 hostKey.put(mDataSource.getKey()+".upt-boss.yogapay.com",map) ;
                 if("yx".equals(mDataSource.getKey())){
                	 hostKey.put("dev.yogapay.com:8042",map) ;
                	 hostKey.put("debug_host",map) ;
                 }
                 if("yx".equals(mDataSource.getKey())){
                	 hostKey.put("127.0.0.1:9090",map) ;
                	 hostKey.put("debug_host",map) ;
                 }
                 if("yx".equals(mDataSource.getKey())){
                	 hostKey.put("192.168.1.115:9090",map) ;
                	 hostKey.put("debug_host",map) ;
                 }
                 if("yx".equals(mDataSource.getKey())){
                	 hostKey.put("yxboss.yogapay.com",map) ;
                	 hostKey.put("debug_host",map) ;
                 }
                 System.out.println("key-----init---------------"+mDataSource.getKey());
			}
        } 
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
	
    
    public synchronized boolean isExitHost(String host,ApplicationContext applicationContext) { 
    	boolean flag = false ;
         if (hostKey.containsKey(host)) {
			flag = true ;
		}else{
			refulsh("codtsi");
			if (hostKey.containsKey(host)) {
				flag = true ;
			}
		}
         return flag ;
         
      
    }

	public Map<String, Map<String, String>> getHostKey() {
		return hostKey;
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

	public synchronized  List<String> getKeyList() {
		return new ArrayList<String>(keyList) ;
	}

    
    
    
}
