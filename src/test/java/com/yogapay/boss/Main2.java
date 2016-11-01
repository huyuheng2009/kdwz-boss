package com.yogapay.boss ;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2 {

	public static void main(String[] args) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection conn = DriverManager.getConnection("jdbc:mysql://183.62.232.128/manager_lgc", "root", "A3sdfDX32ds8");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from city c where c.parent_id=29");
		while (rs.next()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", rs.getString("id"));
			map.put("name", rs.getString("name"));
			list.add(map);
		}
		rs.close();
		for (Map<String, String> t : list) {
			String update = "delete t from city t  left join  (select * from _city_t where city_name='"+t.get("name")+"') c on t.name=c.county_name where t.parent_id="+t.get("id")+" and c.id is null;" ;
			System.out.println(update);
			System.out.println(stmt.executeUpdate(update));
		}
		conn.close();
	}
}
