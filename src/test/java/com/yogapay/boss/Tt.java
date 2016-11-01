package com.yogapay.boss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yogapay.boss.utils.JsonUtil;

public class Tt {

	public static void main(String[] args) throws Exception {
		

        List<Map<String, Object>> rList = new ArrayList<Map<String,Object>>() ;

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://61.141.232.98/yixiang_lgc", "root", "yogapay.com.0916");
		String sql = "select  *  from city where level=1 " ;
		String sq2 = "select  *  from city where level=2 and parent_id=" ;
		String sq3 = "select  *  from city where level=3 and parent_id= " ;
		
		
		
		
		
		
		
		
		// cityList
		
		
		Statement statement =  conn.createStatement() ;
		Statement statement2 =  conn.createStatement() ;
		Statement statement3 =  conn.createStatement() ;
		try {
			ResultSet resultSet = statement.executeQuery(sql) ;
		
			 List<Map<String, Object>> pList = new ArrayList<Map<String,Object>>() ;
          while (resultSet.next()) {
       	  Map<String, Object> pMap = new HashMap<String, Object>() ;
       	  pMap.put("pid", resultSet.getString(1)) ;
       	  pMap.put("provinceName", resultSet.getString(3)) ;
       	  ResultSet resultSet2 = statement2.executeQuery(sq2+resultSet.getString(1)) ;
       	  List<Map<String, Object>> cList = new ArrayList<Map<String,Object>>() ;
       	 while (resultSet2.next()) {
       		 
       		Map<String, Object> cMap = new HashMap<String, Object>() ;
       		cMap.put("cid", resultSet2.getString(1)) ;
       		cMap.put("cityName", resultSet2.getString(3)) ;
       		 ResultSet resultSet3 = statement3.executeQuery(sq3+resultSet2.getString(1)) ;
       		 List<Map<String, Object>> lList = new ArrayList<Map<String,Object>>() ; 
       		 while (resultSet3.next()) {
       			 Map<String, Object> lMap = new HashMap<String, Object>() ;
       			 lMap.put("countyName", resultSet3.getString(3)) ;
       			 lList.add(lMap) ;
				}
       		 cMap.put("couList", lList) ; 
				cList.add(cMap) ;
			}
       	 pMap.put("cityList", cList) ;
       	 pList.add(pMap) ;
		}
		System.out.println(JsonUtil.toJson(pList));
		
		
		} finally {
			conn.close();
		}
		
		
     
	}

}
