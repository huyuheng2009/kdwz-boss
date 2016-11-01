package com.yogapay.boss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateSql {

	public static void main(String[] args)  {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection conn = null ;
		Statement stmt = null ;
  try {
	 String ip = "183.62.232.128" ;
	 // String ip = "61.141.232.98" ;
		String u = "root" ;
		String r =  "A3sdfDX32ds8" ;
		
		//String r =  "yogapay.com.0916" ;
		
	
		 conn = DriverManager.getConnection("jdbc:mysql://"+ip+"/manager_lgc", u, r);
		 stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT `key`,lgc_no,db_url,db_username,db_password FROM project_ds where pro_key='codtsi' ");
		while (rs.next()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", rs.getString("key"));
			map.put("lgc_no", rs.getString("lgc_no"));
			map.put("db_url", rs.getString("db_url").replace("127.0.0.1", ip));
			map.put("db_username", rs.getString("db_username"));
			map.put("db_password", rs.getString("db_password"));
			list.add(map);
		}
		rs.close();
		conn.close();
		
  } catch (Exception e) {
		e.printStackTrace();
	}	
		String sql = "ALTER TABLE `order_info` ADD COLUMN `sign_input_time` TIMESTAMP NULL DEFAULT NULL COMMENT '签收录入时间' AFTER `zid`, ADD COLUMN `sign_inputer` VARCHAR(255) NULL DEFAULT '' COMMENT '签收录入人' AFTER `sign_input_time`" ;
		for (Map<String, String> t : list) {
			 try {
			conn = DriverManager.getConnection(t.get("db_url"), t.get("db_username"), t.get("db_password"));
			stmt = conn.createStatement();
			stmt.executeUpdate(sql) ;
			conn.close();
			System.out.println(t.get("key")+ "----->success");
			 } catch (Exception e) {
				 System.out.println(t.get("key")+ "----->err");
                 e.printStackTrace();
                 continue ;
		      }	
		}
        
	}

}
