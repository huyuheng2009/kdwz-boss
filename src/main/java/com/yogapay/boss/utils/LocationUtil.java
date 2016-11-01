package com.yogapay.boss.utils;

import java.util.Map;

import javax.sql.DataSource;

import com.yogapay.boss.dataSource.DynamicDataSourceHolder;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.utils.http.HttpUtils;


/**
 * 
 * @author 005
 *
 */

public class LocationUtil {
	 
	 public static final String WEIXINPUSH = ConstantsLoader.getProperty("wxpush_host")+"/sendMessage/orderNotice";// 
	 
	/**
	 * 更新订单的坐标
	 * @param addr 详细地址
	 * @param type S收件 R派件
	 * @param orderService  
	 * @throws Exception
	 */
	public static void Location(String addr,String type,OrderService orderService,String orderNo,DataSource dataSource,DynamicDataSourceHolder dynamicDataSourceHolder) throws Exception {
		LocationThread pThread = new LocationThread(addr,type,orderService,orderNo,dataSource,dynamicDataSourceHolder) ;
		Thread t = new Thread(pThread) ;
		t.start();
	}
}

  class LocationThread implements Runnable{
	String addr ;
	String type ;
	OrderService orderService ;
	String orderNo ;
	DataSource dataSource ;
	DynamicDataSourceHolder dynamicDataSourceHolder ;
	public LocationThread(String addr,String type,OrderService orderService,String orderNo,DataSource dataSource,DynamicDataSourceHolder dynamicDataSourceHolder) {
  this.addr = addr ;
  this.type = type ;
  this.orderService = orderService ;
  this.orderNo = orderNo ;
  this.dataSource = dataSource ;
  this.dynamicDataSourceHolder = dynamicDataSourceHolder ;
	}

	@Override
	public void run() {
		 try {
			Map<String, String> rMap = AmapUtil.addressToGPS(addr) ;
			System.out.println(rMap);
			rMap.put("orderNo", orderNo) ;
			dynamicDataSourceHolder.setDataSource(dataSource);
			if ("S".equals(type)) {
				orderService.updateSendLocation(rMap) ;
			}else {
				orderService.updateRevLocation(rMap) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


  
