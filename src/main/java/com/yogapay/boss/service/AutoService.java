package com.yogapay.boss.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogapay.boss.controller.ConfigInfo;
import com.yogapay.boss.dataSource.DynamicDataSourceHolder;
import com.yogapay.boss.domain.CodMonthCount;
import com.yogapay.boss.utils.ConstantsLoader;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.StringUtils;


@Service
public class AutoService {
	private final static Logger log = LoggerFactory.getLogger(AutoService.class);
	@Resource
	private OrderService orderService ;
	@Resource
	private UserService userService ;
	@Resource
	private SubstationService substationService ;
	@Resource
	private LgcService lgcService ;
	@Resource
	private CourierService courierService ;
	@Resource
	private MsgService msgService ;
	@Resource
	private SequenceService sequenceService ;
	@Resource
	private OrderTrackService orderTrackService ;
	@Resource
	private CacheService cacheService;
	@Resource
	private CourierDayStaticService courierDayStaticService ;
	@Resource
	private MonthUserStaticService monthUserStaticService ;	
	@Resource
	private SubstationAccountService substationAccountService ;	
	@Resource
	private SubstationDayStaticService substationDayStaticService ;	
	@Resource
	private MobileUserService mobileUserService;
	@Resource
	private DynamicDataSourceHolder dynamicDataSourceHolder;

	@Resource
	private MonthSettleService monthSettelService;
	@Resource
	private CodMonthCountService codMonthCountService ;
	@Autowired
	private ConfigInfo configInfo ;



	@SuppressWarnings("unused")
	public  void courierDayStatic(){	
		System.out.println("快递员每日收派表定时任务启动");
		Date nowDate = new Date();
		try{
			List<String> keyList = dynamicDataSourceHolder.getKeyList();
			for(String key : keyList){
				System.out.println("key======"+key);
				dynamicDataSourceHolder.setDataSource(key);
				dynamicDataSourceHolder.refulsh(key);
				Map<String,Object> map =new HashMap<String,Object>();	
				map.put("nowTime", DateUtils.formatDate(nowDate,"yyyy-MM-dd"));
//				map.put("nowTime", DateUtils.formatDate(DateUtils.addDate(nowDate,-4,0,0),"yyyy-MM-dd"));
				List<Map<String,Object>> list = courierDayStaticService.acount(map);
				courierDayStaticService.delete(map);
				for(Map<String,Object> mapL: list ){
					mapL.put("staticDate", DateUtils.formatDate(nowDate));
					mapL.put("createTime", DateUtils.formatDate(nowDate));	
//					mapL.put("staticDate", DateUtils.formatDate(DateUtils.addDate(nowDate,-4,0,0)));
//					mapL.put("createTime", DateUtils.formatDate(DateUtils.addDate(nowDate,-4,0,0)));	
					courierDayStaticService.save(mapL);
				}
			}
			log.info("===========快递员每日收派表end============耗时："+(new Date().getTime()-nowDate.getTime())/1000+"秒");
		}catch(Exception e){
			e.printStackTrace();
		}
	}	


	//月结客户统计 每月1号定时1点跑批统计上个月的月结客户收派件信
	@SuppressWarnings("unused")
	public void monthSettleMonthReport(){
		try {
			Date beginTime = new Date();
			log.info("============月结客户统计begin============");
			//1.切换数据源
			List<String> keyList = dynamicDataSourceHolder.getKeyList();
			for(String key : keyList){
				System.out.println("key======"+key);
				dynamicDataSourceHolder.setDataSource(key);
				dynamicDataSourceHolder.refulsh(key);
//				String lastMonth = DateUtils.getLastMonth();	
				String lastMonth = DateUtils.formatDate(beginTime, "yyyy-MM");
				
				//2.先查询所有月结账户
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("nowDate", lastMonth);
				monthSettelService.delete(params);
				List<Map<String, Object>> monthSettleInfoList = monthSettelService.monthSettleUserList(params);		
				for(Map<String, Object> map: monthSettleInfoList){
					Date newDate = new Date();
					BigDecimal money = new BigDecimal((Integer)map.get("discount")).
							multiply((BigDecimal)map.get("sumMoney")).divide(new BigDecimal("100"));//实际金额
					map.put("discountBefore", money.toString());
					map.put("newDate", newDate);
					map.put("nowDate", lastMonth);
					monthSettelService.save(map);
				}			
				log.info("============月结客户统计end============耗时："+(new Date().getTime()-beginTime.getTime())/1000+"秒");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//代收货款客户月报数据统计
	@SuppressWarnings("unused")
	public void codMonthReport(){
		try {
			Date beginTime = new Date();
			String lastMonth = DateUtils.getLastMonth() ;
			String curMonth = DateUtils.formatDate(beginTime,"yyyy-MM") ;
			
			if ("true".equals(configInfo.getDebug())) {
				lastMonth = curMonth ;
				curMonth =  "2019-01-01 00:00:00" ;
			}

			Map<String, String> params = new HashMap<String, String>() ;
				params.put("createTimeBegin", lastMonth+"-01 00:00:00");
				params.put("createTimeEnd", curMonth+"-01 00:00:00");
			log.info(lastMonth+"============代收货款客户月报统计begin============");
			//1.切换数据源
			List<String> keyList = dynamicDataSourceHolder.getKeyList();
			List<CodMonthCount> monthCountList = new ArrayList<CodMonthCount>() ;
			for(String key : keyList){
				System.out.println("key======"+key);
				monthCountList.clear();
				dynamicDataSourceHolder.setDataSource(key);
				List<Map<String, Object>> list = orderService.codMonthList(params) ;
				for (Map<String, Object> map:list) {
					CodMonthCount codMonthCount = new CodMonthCount() ;
					codMonthCount.setCodNo(StringUtils.nullString(map.get("codNo")));
					codMonthCount.setCodPrice(StringUtils.nullString(map.get("codPrice")));
					codMonthCount.setCreateTime(beginTime);
					codMonthCount.setPrinted("N");
					codMonthCount.setSettled("N");
					codMonthCount.setSettleDate(lastMonth);
					codMonthCount.setDiscount(StringUtils.nullString(map.get("discount")));
					if (!StringUtils.isEmptyWithTrim(StringUtils.nullString(map.get("discount")))) {
						double p = Double.valueOf(map.get("codPrice").toString()) ;
						double d = Double.valueOf(map.get("discount").toString())/1000.0 ;
						double r = p-(p*d) ;
						codMonthCount.setReturnPrice(String.valueOf(r));
					}else {
						codMonthCount.setReturnPrice(map.get("codPrice").toString());
					}
					monthCountList.add(codMonthCount) ;
				}
				codMonthCountService.delByMonth(lastMonth) ;
				if (monthCountList.size()>0) {
					codMonthCountService.batchSaveCodMonthCount(monthCountList) ;
				}
			}
			log.info(lastMonth+"============代收货款客户月报统计end============耗时："+(new Date().getTime()-beginTime.getTime())/1000+"秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
