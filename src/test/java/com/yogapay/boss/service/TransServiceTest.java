package com.yogapay.boss.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.junit.AbstractTest;


public class TransServiceTest extends AbstractTest{
	    @Resource
	    private CityService dictService ;
	    @Resource
	    private SequenceService sequenceService ;
	    @Resource
	    private LgcService lgcService ;
	    @Resource
	    private AutoService autoService ;

 
	    @Test
	    public void testAcq() throws Exception{
	    	
	    /*	
	    	
	    	String fullFileName = "E:/a.json";
	        
	        File file = new File(fullFileName);
	        Scanner scanner = null;
	        StringBuilder buffer = new StringBuilder();
	        try {
	            scanner = new Scanner(file, "utf-8");
	            while (scanner.hasNextLine()) {
	                buffer.append(scanner.nextLine());
	            }
	 
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block  
	 
	        } finally {
	            if (scanner != null) {
	                scanner.close();
	            }
	        }
	        Object [] listObjects =   JsonUtil.getObjectArrayFromJson(buffer.toString()); 
	     	Map<String, Object> params = new HashMap<String, Object>();
		    params.put("","") ;
		    int cid = 0 ;
	       for (int i = 0; i < listObjects.length; i++) {
	    	  Map<String, Object> proMap = (Map<String, Object>) listObjects[i] ;
	    	  
              List<Map<String, Object>> clist = (List<Map<String, Object>>) proMap.get("cityList") ;
              System.out.print(proMap.get("provinceName"));
              
              
              
	    	  for (int j = 0; j < clist.size(); j++) {
	    		  List<Map<String, Object>> couList = (List<Map<String, Object>>) clist.get(j).get("couList") ;
	    		  System.out.print(clist.get(j).get("cityName"));
	    		 for (int k = 0; k < couList.size(); k++) {
	    			 cid = cid+1 ;
	    			  System.out.println(couList.get(k).get("countyName"));
	    			   params.clear();
			    	   params.put("level","3") ;
			    	   params.put("name",couList.get(k).get("countyName")) ;
			    	   params.put("parentId",cid) ;
			    	   System.out.println(cid);
			    	  // dictService.savep(params);
				}

	    		 
	    		 
			}
	    	  
	    	  
	    	  
	    	
	    	   params.clear();
	    	   params.put("level","1") ;
	    	   params.put("name",proMap.get("provinceName")) ;
	    	   params.put("parentId","0") ;
	    	   dictService.savep(params);
	    	   
		    }
	       
	       */
	        

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

	     	Lgc lgc = new Lgc() ;
	     	lgc.setContact("888888");
	     	lgc.setWebsite("www.sf.com");
	     	lgc.setAccessTime(DateUtils.formatDate(new Date()));
	        for (int i = 0; i < nameList.size(); i++) {
	        	lgc.setName(nameList.get(i).getText());
	        	lgc.setPingyin(codeList.get(i).getText());
	        	lgc.setLogo("/codfile/lgclogo/"+imageList.get(i).getText()+".png");
	        	lgc.setLgcNo(sequenceService.getNextVal("lgc_no"));
				lgc.setNextSno(lgc.getLgcNo()+"001");   //快递公司下一个分站编号
				lgcService.saveLgc(lgc);
				System.out.println("image:"+imageList.get(i).getText()+"------code:"+codeList.get(i).getText()+"----name:"+nameList.get(i).getText());
		  	}
		


		}
	    
	    @Test
	   public void autoTest() {
		autoService.courierDayStatic();
	} 	
	    	
		
	    	
 }
	    
	    

