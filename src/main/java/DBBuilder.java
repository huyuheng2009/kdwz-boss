
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBBuilder {

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.11/manager_lgc", "admin", "72877450");
		try {
			String db_name = "yj_lgc";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("set names utf8");
			//
			ResultSet rs = stmt.executeQuery("select count(0) from _hugo_create_db where db_name='" + db_name + "' and `lock`=0");
			rs.next();
			int c = rs.getInt(1);
			if (c == 0) {
				throw new RuntimeException();
			}
			//
			stmt.executeUpdate("drop database if exists " + db_name);
			//
			stmt.executeUpdate("create database " + db_name);
			//
			conn.setCatalog(db_name);
			stmt.executeUpdate("source /home/deploy/project/sql/2015-11-02/codboss.sql");
			stmt.executeUpdate("source /home/deploy/project/sql/2015-11-02/LianHangHao.sql");
			stmt.executeUpdate(String.format("INSERT INTO sequence (name, current_value, increment) "
					+ "SELECT 'lgc_no', lgc_no, 1 FROM manager_lgc._hugo_create_db WHERE db_name='%s'", db_name));
			stmt.executeUpdate(String.format("INSERT INTO lgc "
					+ "SELECT 1, lgc_no, company_name, NULL, NULL, '1111', '', NULL, NULL, NULL, CONCAT(lgc_no,'001'), '2015-10-23 15:35:47', NULL, NULL, 'LOC', NULL FROM manager_lgc._hugo_create_db WHERE db_name='%s';", db_name));
		} finally {
			conn.close();
		}
	}
}
