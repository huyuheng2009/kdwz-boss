package com.yogapay.boss;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.ExcelReader;

public class DateInsert {

	public static void main(String[] args) throws Exception {
		String db_name = "yixiang_lgc" ;
		
	    String tableSql = "CREATE TABLE `timejoin` ("
		+"`id` INT(11) NOT NULL AUTO_INCREMENT,"
		+"`time_join` DATE NOT NULL,"
		+"PRIMARY KEY (`id`),"
		+"UNIQUE INDEX `time_join` (`time_join`)) COLLATE='utf8_general_ci' ENGINE=MyISAM AUTO_INCREMENT=1" ;
	           
	            Class.forName("com.mysql.jdbc.Driver");
	      		Connection conn =  DriverManager.getConnection("jdbc:mysql://61.141.232.98/"+db_name, "root", "yogapay.com.0916");     
	      		Statement stmt = conn.createStatement();  
	      		
	      		stmt.executeUpdate("DROP TABLE IF EXISTS `"+"timejoin"+"`") ;
	      		stmt.executeUpdate(tableSql.toString()) ;
	         
	      		String insertfix  = "INSERT INTO `timejoin` (`time_join`) " ;     		
	      		
	      		String dateBegin = "2015-01-01" ;
	      		
	            Date bDate =  DateUtils.parseDate(dateBegin, "yyyy-MM-dd") ;
					StringBuffer inSql = new StringBuffer() ;
					 inSql.append(insertfix).append(" VALUES ") ;
						for (int i = 1; i < 8000; i++) {
							String v = DateUtils.formatDate(DateUtils.addDate(bDate, i, 0, 0), "yyyy-MM-dd");
						if (i==7999) {
							inSql.append("('").append(v).append("')") ;
						}else {
							inSql.append("('").append(v).append("'),") ;
						}
					}
					System.out.println(inSql.toString());
					stmt.executeUpdate(inSql.toString()) ;
	      		
	      		
	      		
		}

}
