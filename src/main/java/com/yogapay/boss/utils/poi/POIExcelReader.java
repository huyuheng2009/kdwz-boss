/**
 * 项目: tselling
 * 包名: com.camels.tselling
 * 文件名: POIExcelReader.java
 * 创建时间: 2014-5-7 上午11:21:40
 * 2014 骆驼工作室版权所有,保留所有权利;
 */
package com.yogapay.boss.utils.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @Todo: POI 框架 对Excel 解析数据
 * @Author: zhanggc
 */
public class POIExcelReader {

    //版本
    public enum Version{
        VERSION2007,
        VERSION2003
    }

    //类型
    public enum Type{
        ONE("application/vnd.ms-excel",Version.VERSION2003),
        TWO("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",Version.VERSION2007);
       String name;
       Version version;
        Type(String name,Version version){
            this.name = name;
            this.version = version;
        }

        public String getName(){
            return this.name;
        }

        public Version getVersion(){
            return this.version;
        }
    }

    public static List<List<List<Object>>> read(String fileName, boolean flag) throws Exception {
        Workbook wb = null;
        if (flag) {//2003
            File f = new File(fileName);

            FileInputStream is = new FileInputStream(f);
            POIFSFileSystem fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
            is.close();
        } else {//2007
            wb = new XSSFWorkbook(fileName);
        }

        return read(wb);
    }

    public static List<List<List<Object>>> read(InputStream is, Version version) throws Exception {
        Workbook wb = null;

        switch (version){
            case VERSION2003:{
                wb = new HSSFWorkbook(is);
                break;
            }
            case VERSION2007:{
                wb = new XSSFWorkbook(is);
                break;
            }
            default:{
                wb = new HSSFWorkbook(is);
                break;
            }

        }

        return read(wb);
    }

    public static List<List<List<Object>>> read(Workbook wb) throws Exception {
        List<List<List<Object>>> dataList = new ArrayList<List<List<Object>>>();
        for (int k = 0; k < wb.getNumberOfSheets(); k++) {
            List<List<Object>> sheetList = new ArrayList<List<Object>>();
            //sheet
            Sheet sheet = wb.getSheetAt(k);
            int rows = sheet.getPhysicalNumberOfRows();

            for (int r = 0; r < rows; r++) {
                // 定义 row
                Row row = sheet.getRow(r);
                if (row != null) {
                    int cells = row.getPhysicalNumberOfCells();
                    List<Object> rowList = new ArrayList<Object>();
                    for (short c = 0; c < cells; c++) {
                        Cell cell = row.getCell(c);
                        if (cell != null) {
                            Object value = null;

                            switch (cell.getCellType()) {

                                case Cell.CELL_TYPE_FORMULA:
                                    //公式类型
                                    value = cell.getCellFormula();
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                        //时间类型
                                        value = cell.getDateCellValue();
                                    } else {
                                        //数字类型
                                        value = String.valueOf(cell.getNumericCellValue());
                                        Pattern pattern = Pattern.compile("E");
                                        Matcher matcher = pattern.matcher(value.toString());
                                        if (matcher.find()) {
                                            int mulriple = Integer.parseInt(value.toString().substring(matcher.end(), value.toString().length()));
                                            value = value.toString().substring(0, matcher.start());
                                            String[] values = value.toString().split("\\.");
                                            if (values != null && values.length > 1) {
                                                String benginStr = values[0];
                                                values[1] = StringUtils.rightPad(values[1],mulriple,"0");
                                                String middStr = values[1].substring(0, mulriple);
                                                String endStr = values[1].substring(mulriple);
                                                String pointStr = "";
                                                if (endStr != null && !"".equals(endStr.trim())) {
                                                    pointStr = ".";
                                                }
                                                value = benginStr + middStr + pointStr + endStr;
                                            }
                                        } else {
                                            value = "" + cell.getNumericCellValue();
                                        }
                                    }

                                    break;

                                case Cell.CELL_TYPE_STRING:
                                    //字符类型
                                    value = cell.getStringCellValue();
                                    break;

                                case Cell.CELL_TYPE_BOOLEAN:
                                    //BOOLEAN类型
                                    value = "" + cell.getBooleanCellValue();


                                    cell.getDateCellValue();

                                    break;

                                default:
                            }

                            System.out.println(value);
                            rowList.add(value);
                        }
                    }
                    sheetList.add(rowList);
                }
            }
            dataList.add(sheetList);
        }
        return dataList;
    }

    /**
     * @param contentType
     * @return
     * @Todo 判断是否为Excel文件
     */
    public static boolean isExcel(String contentType) {
        for(Type type:Type.values()){
            if(type.getName().equals(contentType)){
                return true;
            }
        }
        return false;
    }

    //获取版本
    public static Version getVersion(String contentType){
        for(Type type:Type.values()){
            if(type.getName().equals(contentType)){
                return type.getVersion();
            }
        }
        return Version.VERSION2003;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String filePath = "E:\\_channelInfo.xls";
        File f = new File(filePath);
        String suffix = filePath.substring(filePath.lastIndexOf("."));
        FileInputStream is = new FileInputStream(f);

        System.out.println(f.getName());
    }
}
