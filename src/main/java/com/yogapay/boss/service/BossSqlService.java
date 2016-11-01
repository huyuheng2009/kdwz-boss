package com.yogapay.boss.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.BossSql;
import com.yogapay.boss.utils.StringUtils;

@Service
public class BossSqlService {
	@Resource
	private ManagerDao managerDao ;
	@Value("#{config['master.url']}")
	private String dbUrl ;
	@Value("#{config['master.username']}")
	private String dbUser ;
	@Value("#{config['master.password']}")
	private String dbPw ;

   public List<Map<String, Object>> list() {
	return managerDao.getList("BossSql.list") ;
  }
	
   public void save(BossSql bossSql) {
	managerDao.insert("BossSql.insert",bossSql);
  }
 
   public List<Map<String, Object>> getByMaxId(Integer id) {
		return managerDao.getList("BossSql.getByMaxId") ;
 }
   
   /**
    * 通过jdbc建立库并执行sql
    * @param dbName  数据库名称
    * @param sqlList  sql集合
    * @param create  是否需要创建库
    *  @param testSql 测试sql
    * @throws Exception 
    */
   public boolean createDBAndExcute(String dbName,List<Map<String, Object>> sqlList,boolean create,String testSql) throws Exception {
		
      boolean r = true ;
	   Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
		Statement stmt = conn.createStatement();
		boolean test = true ;  //测试通过后执行sqllist
		try {
			try {
			stmt.executeUpdate("set names utf8");
			//stmt.executeUpdate("drop database if exists " + db_name);
			if (create) {
				stmt.executeUpdate("create database " + dbName);
			}
			//
			conn.setCatalog(dbName);
			stmt = conn.createStatement();
			if (!StringUtils.isEmptyWithTrim(testSql)) {
				stmt.executeQuery(testSql) ;
			}
			} catch (Exception e) {
				test = false ;
				r = false ;
				e.printStackTrace();
				System.out.println("create test error......................");
			}
			System.out.println("create database " + dbName );
			test = false ;
			if (test) {
				for (Map<String, Object> map:sqlList) {
					   try {
						    stmt.execute(map.get("sqlstring").toString()) ;
						    System.err.println("----------------success----------------------");
						    System.out.println(map.get("sqlstring").toString());
					     } catch (Exception e) {
					    	 System.err.println("----------------err----------------------");
						    System.err.println(map.get("sqlstring").toString());
						    System.err.println("-----------------err---------------------");
						   e.printStackTrace();
						   continue ;
					    }
					}
			}
			
		} finally {
			stmt.close();
			conn.close();
		}
	   
	   return r ;
	  }  
   
   /**
    * 通过jdbc建立库并执行sql
    * @param dbName  数据库名称
    * @param sqlList  sql集合
    * @param create  是否需要创建库
    *  @param testSql 测试sql
    * @throws Exception 
    */
   public boolean createDBAndExcute1(String dbName,List<String> sqlList,boolean create,String testSql) throws Exception {
		
      boolean r = true ;
	   Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
		Statement stmt = conn.createStatement();
		boolean test = true ;  //测试通过后执行sqllist
		try {
			try {
			stmt.executeUpdate("set names utf8");
			//stmt.executeUpdate("drop database if exists " + db_name);
			if (create) {
				stmt.executeUpdate("create database " + dbName);
			}
			//
			conn.setCatalog(dbName);
			stmt = conn.createStatement();
			if (!StringUtils.isEmptyWithTrim(testSql)) {
				stmt.executeQuery(testSql) ;
			}
			} catch (Exception e) {
				test = false ;
				r = false ;
				e.printStackTrace();
				System.out.println("create test error......................");
			}
			System.out.println("create database " + dbName );
			if (test) {
				for (String sql:sqlList) {
					   try {
						    stmt.execute(sql) ;
						    System.err.println("----------------success----------------------");
						    System.out.println(sql);
					     } catch (Exception e) {
					    	 System.err.println("----------------err----------------------");
						    System.err.println(sql);
						    System.err.println("-----------------err---------------------");
						   e.printStackTrace();
						   continue ;
					    }
					}
			}
			
		} finally {
			stmt.close();
			conn.close();
		}
	   
	   return r ;
	  }  
}
