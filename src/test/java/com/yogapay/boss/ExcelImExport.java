package com.yogapay.boss;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.ExcelReader;

public class ExcelImExport {

	public static void main(String[] args) throws Exception {
		String fileName = "F:\\yxdata\\sign\\sendBatch(21).xls" ;
		String tableName = "xiadan1" ;
		String db_name = "yixiang_lgc" ;
		File file = new File(fileName) ;
		ExcelReader excelReader = new ExcelReader();
		FileInputStream is = new FileInputStream(file) ;
		FileInputStream is1 = new FileInputStream(file) ;
		String colom[] = excelReader.readExcelTitle(is) ;
		
    StringBuffer tableSql = new StringBuffer() ;
           
            tableSql.append("CREATE TABLE `"+tableName+"` (") ;
            
       	   StringBuffer tableInsert = new StringBuffer() ;
       		 tableInsert.append(" INSERT INTO `"+db_name+"`.`"+tableName+"` (") ;
       		 int aa = 0 ;
            for (int i = 0; i < colom.length; i++) {
            	if (colom[i]==null||colom[i].trim().length()<1) {
					continue ;
				}
            	aa++ ;
            	tableSql.append("`")
			          .append(colom[i])
				      .append("` TEXT NULL DEFAULT NULL,") ;
            	if (i==colom.length-1) {
            		tableInsert.append("`").append(colom[i]).append("`") ;
				}else {
					tableInsert.append("`").append(colom[i]).append("`,") ;
				}
			}
            
                tableSql.append("`id` INT(11) NOT NULL AUTO_INCREMENT,")
                        .append("PRIMARY KEY (`id`))")
                        .append("COLLATE='utf8_general_ci' ")
                        .append("ENGINE=MyISAM ")
                        .append("AUTO_INCREMENT=1 ") ;
              System.out.println(tableSql.toString());
              
              tableInsert.append(")") ;
              
            /*  
             `手机号`, `客户名称`, `电话号码`, `更新时间`, `下单员`, `下单地址`, `状态`, `业务员号码`, `添加人`, `添加时间`) VALUES ('123', 

'12312', '123', '123', '123', '123', '123', '123', '123', '123');
    
              
              */
              
            Class.forName("com.mysql.jdbc.Driver");
      		Connection conn =  DriverManager.getConnection("jdbc:mysql://61.141.232.98/"+db_name, "root", "yogapay.com.0916");     
      		Statement stmt = conn.createStatement();  
      		//stmt.executeUpdate("DROP TABLE IF EXISTS `"+tableName+"`") ;
      		//stmt.executeUpdate(tableSql.toString()) ;
         
      		String[][] array = excelReader.readExcelContent(is1);     
      		
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
				System.out.println(i+"----"+array.length+"------"+inSql.toString());
				stmt.executeUpdate(inSql.toString()) ;
			}
      		
      		
      		
	}

}
