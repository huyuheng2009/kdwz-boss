/**
 * 项目: posboss
 * 包名：com.yogapay.boss.utils.txt
 * 文件名: TxtReader
 * 创建时间: 2014/7/10 16:52
 * 支付界科技有限公司版权所有，保留所有权利
 */
package com.yogapay.boss.utils.txt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Todo:Txt 数据解析
 * @Author: Zhanggc
 */
public class TxtReader {
    private static String encode = "utf-8";
    /**
     * @Todo 读取行数据
     * @param in
     * @return
     * @throws Exception
     */
    public static List<String> read(InputStream in) throws Exception{
        List<String> lineCollection = new ArrayList<String>();
        InputStreamReader read = new InputStreamReader(
                in, getEncode());//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lineCollection.add(line);
        }
        bufferedReader.close();
        read.close();
        return lineCollection;
    }

    /**
     * @Todo 解析结算文件
     * @param lineCollection
     * @return
     */
    public static  List<List<Object>> readForSettle(List<String> lineCollection){
        List<List<Object>> dataCollection = new ArrayList<List<Object>>();
        if(null!=lineCollection&&lineCollection.size()>3){  //至少含有两行数据
            String line;
            List<Object> lineList = null;
            int length = lineCollection.size();
            for(int i = 0;i<length;i++){
                lineList = new ArrayList<Object>();
                line = lineCollection.get(i);
                if(0==i){
                    String[] parts = line.split(":");
                    parts[1] = parts[1].substring(0,parts[1].length()-4).trim();
                    lineList.add(parts[1]); //商户号；
                    lineList.add(parts[2].trim());  //商户名
                }else if(1==i||i+1==length){
                    //不处理
                }else{
                    String[] parts = line.split("\\|");
                    for(String part:parts){
                        lineList.add(part.trim());
                    }
                }
                dataCollection.add(lineList);
            }
        }
        return dataCollection;
    }

    /**
     * @Todo 判断是否为txt文件
     * @param contentType
     * @return
     */
    public static boolean isTxt(String contentType){
        if("text/plain".equals(contentType)) return true;
        return false;
    }

    public static String getEncode(){
        return encode;
    }

    public static void setEncode(String encode){
        TxtReader.encode = encode;
    }
}
