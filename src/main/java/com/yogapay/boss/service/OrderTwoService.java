package com.yogapay.boss.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.utils.DateUtils;

@Service
public class OrderTwoService {

    @Resource
    private BaseDao baseDao;
    //private OrderDao orderDao ;


    //  派件扫描更新
	public void sendScanUpdate(String sno, String substationNo, String orderNo) {
		  Map<String, Object> pMap = new HashMap<String, Object>() ;
	      pMap.put("sno", sno) ;
	      pMap.put("substationNo", substationNo) ;
	      pMap.put("orderNo", orderNo) ;
	      pMap.put("stime", DateUtils.formatDate(new Date())) ;
		baseDao.update("OrderInfoTwo.sendScanUpdate", pMap);
	}

	 //  签收扫描更新
		public void signScanUpdate(String sno, String substationNo, String orderNo) {
			  Map<String, Object> pMap = new HashMap<String, Object>() ;
		      pMap.put("sno", sno) ;
		      pMap.put("substationNo", substationNo) ;
		      pMap.put("orderNo", orderNo) ;
		      pMap.put("stime", DateUtils.formatDate(new Date())) ;
			baseDao.update("OrderInfoTwo.signScanUpdate", pMap);
		}
	
   
}
