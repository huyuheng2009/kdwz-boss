package com.yogapay.boss;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class FileUtil {

	public static void main(String[] args) throws Exception {


              
            Class.forName("com.mysql.jdbc.Driver");
      		Connection conn =  DriverManager.getConnection("jdbc:mysql://192.168.1.30/plane_file", "root", "yogapay.com.0916");     
      		Statement stmt = conn.createStatement();  
      		//stmt.executeUpdate("DROP TABLE IF EXISTS `"+tableName+"`") ;
      		//stmt.executeUpdate(tableSql.toString()) ;
          
      		String insertfix  = "INSERT INTO `pfile` (`lgc_order_no`,`file_real_path`,`file_url`) VALUES (?,?,?)" ;   
      		String insertsql = insertfix ;
      		
      		PreparedStatement preStmt = 	conn.prepareStatement(insertfix) ;
      		conn.setAutoCommit(false);
      		
      		String s = "/home/fajian" ;
      		//String s = "F:\\yxdata" ;
      		File dir = new File(s) ;  ///home/fajian
      		File[] dirList=dir.listFiles();
      		for (File f:dirList) {
      			if(f.isDirectory())
      			{
      				File[] files = f.listFiles(); 
      				int a = 0 ;
      		
      				for (File plane:files) {
      					if (plane.isFile()) {
							System.out.println(plane.getAbsolutePath().replace(s, "/codfile/plane"));
							System.out.println(plane.getName().substring(0,plane.getName().lastIndexOf(".")));
							System.out.println("--------------------------------"+a++);
							/*insertsql = insertfix ;
                            insertsql +="'"+plane.getName().substring(0,plane.getName().lastIndexOf("."))+"'," ;
                            insertsql +="'"+plane.getAbsolutePath()+"'," ;
                            insertsql +="'"+plane.getAbsolutePath().replace(s, "/codfile/plane")+"')" ;
                            stmt.executeUpdate(insertsql) ;*/
							
							preStmt.setString(1, plane.getName().substring(0,plane.getName().lastIndexOf(".")));
							preStmt.setString(2, plane.getAbsolutePath());
							preStmt.setString(3, plane.getAbsolutePath().replace(s, "/codfile/plane"));
							 preStmt.addBatch(); 
						}
      				}
      				    preStmt.executeBatch();
      				    conn.commit();
      				    preStmt.clearBatch();
      			}else
                {
                   continue ;
                }
			}
      		
      		/*
      		for (int i = 0; i < array.length; i++) {
				String data[] = array[i] ;
				StringBuffer inSql = new StringBuffer() ;
				 inSql.append(tableInsert).append(" VALUES (") ;
				for (int j = 0; j < data.length; j++) {
					if (j==data.length-1) {
						inSql.append("'").append(data[j]).append("'") ;
					}else {
						inSql.append("'").append(data[j]).append("',") ;
					}
				}
				inSql.append(")");
				stmt.executeUpdate(inSql.toString()) ;
			}
      		*/
      		
      		
	}

}
