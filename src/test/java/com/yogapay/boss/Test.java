/**
 * 项目: posboss
 * 包名：com.yogapay.boss
 * 文件名: Test
 * 创建时间: 2014/12/23 11:33
 * 支付界科技有限公司版权所有，保留所有权利
 */
package com.yogapay.boss;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yogapay.boss.enums.PayType;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.WeiXinUtil;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @Todo: 测试
 * @Author: Zhanggc
 */
public class Test {
    public static void main(String[] args) throws Exception{

    /*	Map<String, String> wxMap = new HashMap<String, String>() ;
		wxMap.put("touser","o6MOIt8wZbVJGDN29C5fQuUAth5E");
		wxMap.put("template_id","KZIR04DnPKWVK7fl8eHgm_Ms0g-PHZocVzVFNu5GY9I") ;
		wxMap.put("templateType","6");
		wxMap.put("number","111111");
		wxMap.put("time",DateUtils.formatDate(new Date()));
		wxMap.put("remark","");
		wxMap.put("frist","尊敬的客户,你有一个件已被签收");
		WeiXinUtil.push(wxMap);*/
    	
    	SAXReader reader = new SAXReader();
    	  File file = new File("E:\\expressList.plist");
    	  System.out.println(file.canRead());
    	  Document document = reader.read(file);
    	  Element root = document.getRootElement();
    	  List<Element> childElements = root.elements();
    	  List<Element> imageList =null;
    	  List<Element> codeList =null;
    	  List<Element> nameList =null;
          String name = "" ;
    	  for (Element child : childElements) {
    		  List<Element> elementList = child.elements();
    	   if ("image".equals(child.getText())) {
    		   name = "image" ;
			imageList = elementList ;
		   }
    	   if ("code".equals(child.getText())) {
    		   name = "code" ;
   			codeList = elementList ;
   		   }
    	   if ("name".equals(child.getText())) {
    		   name = "name" ;
   			
   		   }
    	   
    	   if (!"image".equals(child.getText())&&!"code".equals(child.getText())&&!"name".equals(child.getText())) {
    		   if ("image".equals(name)) {
    			imageList = elementList ;
    		   }
        	   if ("code".equals(name)) {
       			codeList = elementList ;
       		   }
        	   if ("name".equals(name)) {
        		   nameList = elementList ;
       		   }
		  }
    	 }

          for (int i = 0; i < nameList.size(); i++) {
			System.out.println("image:"+imageList.get(i).getText()+"------code:"+codeList.get(i).getText()+"----name:"+nameList.get(i).getText());
	  	}
    }
    
    public void readI() throws Exception {
    	SAXReader reader = new SAXReader();
  	  File file = new File("E:\\expressList.plist");
  	  System.out.println(file.canRead());
  	  Document document = reader.read(file);
  	  Element root = document.getRootElement();
  	  List<Element> childElements = root.elements();
  	  List<Element> imageList =null;
  	  List<Element> codeList =null;
  	  List<Element> nameList =null;
        String name = "" ;
  	  for (Element child : childElements) {
  		  List<Element> elementList = child.elements();
  	   if ("image".equals(child.getText())) {
  		   name = "image" ;
			imageList = elementList ;
		   }
  	   if ("code".equals(child.getText())) {
  		   name = "code" ;
 			codeList = elementList ;
 		   }
  	   if ("name".equals(child.getText())) {
  		   name = "name" ;
 			
 		   }
  	   
  	   if (!"image".equals(child.getText())&&!"code".equals(child.getText())&&!"name".equals(child.getText())) {
  		   if ("image".equals(name)) {
  			imageList = elementList ;
  		   }
      	   if ("code".equals(name)) {
     			codeList = elementList ;
     		   }
      	   if ("name".equals(name)) {
      		   nameList = elementList ;
     		   }
		  }
  	 }

        for (int i = 0; i < nameList.size(); i++) {
			System.out.println("image:"+imageList.get(i).getText()+"------code:"+codeList.get(i).getText()+"----name:"+nameList.get(i).getText());
	  	}
	}
    
}
