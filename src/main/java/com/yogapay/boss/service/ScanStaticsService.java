package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;

@Service
public class ScanStaticsService {
	@Resource
	private BaseDao baseDao ;
	/**
	 * 获取所有分站编号和姓名
	 * @return
	 */
	public List<Map<String,Object>> allSubstation(){	
		return baseDao.getList("ScanStatics.allSubstation");	
	}
	
	public void insertTimeInfo(Map<String, Object>  map){
		baseDao.insert("ScanStatics.insertTimeInfo", map);
		
	}
	/**
	 * 根据条件查询所有分站
	 */
	public  PageInfo<Map<String, Object>> selectScanInfo(Map<String, String>  map,Page pageInfo){
	return 	baseDao.getByPage("ScanStatics.selectScanInfo",pageInfo, map);
	}
	/**
	 * 查询分站当前收件
	 * @param map
	 * @return
	 */
	public String takeCount(Map<String, Object>  map){
		Map<String, Object>  map1 = 	baseDao.getFrist("ScanStatics.takeCount", map);
		if(map1!=null){
			return String.valueOf(map1.get("acount"));
		}
		return "0";
	}
	/**
	 * 查询分站当前派件
	 * @param map
	 * @return
	 */
	public String sendCount(Map<String, Object>  map){
		Map<String, Object>  map1 = 	baseDao.getFrist("ScanStatics.sendCount", map);
		if(map1!=null){
			return String.valueOf(map1.get("acount"));
		}
		return "0";
	}
	/**
	 * 查询分站到站
	 * @param map
	 * @return
	 */
	public String daozhanpiaoshu(Map<String, Object>  map){
		Map<String, Object>  map1 = 	baseDao.getFrist("ScanStatics.daozhanpiaoshu", map);
		if(map1!=null){
			return String.valueOf(map1.get("acount"));
		}
		return "0";
	}
	/**
	 * 查询分站出站
	 * @param map
	 * @return
	 */
	public String chuzhanpiaoshu(Map<String, Object>  map){
		Map<String, Object>  map1 = 	baseDao.getFrist("ScanStatics.chuzhanpiaoshu", map);
		if(map1!=null){
			return String.valueOf(map1.get("acount"));
		}
		return "0";
	}
	
	/**
	 * 查询问题件
	 * @param map
	 * @return
	 */
	public String wentijian(Map<String, Object>  map){
		Map<String, Object>  map1 = 	baseDao.getFrist("ScanStatics.wentijian", map);
		if(map1!=null){
			return String.valueOf(map1.get("acount"));
		}
		return "0";
	}
	/**
	 * 查询签收票数
	 * @param map
	 * @return
	 */
	public String qianshoupiaoshu(Map<String, Object>  map){
		Map<String, Object>  map1 = 	baseDao.getFrist("ScanStatics.qianshoupiaoshu", map);
		if(map1!=null){
			return String.valueOf(map1.get("acount"));
		}
		return "0";
	}
	
	/**
	 * 查询有到站有出站
	 * @param map
	 * @return
	 */
	public String inAndOut(Map<String, Object>  map){
		Map<String, Object>  map1 = 	baseDao.getFrist("ScanStatics.inAndOut", map);
		if(map1!=null){
			return String.valueOf(map1.get("acount"));
		}
		return "0";
	}
	/**
	 * 查询有到站无出站
	 * @param map
	 * @return
	 */
	public String inNotOut(Map<String, Object>  map){
		Map<String, Object>  map1 = 	baseDao.getFrist("ScanStatics.inNotOut", map);
		if(map1!=null){
			return String.valueOf(map1.get("acount"));
		}
		return "0";
	}
	
	/**
	 * 无到站有出站
	 * @param map
	 * @return
	 */
	public String noInhaveOut(Map<String, Object>  map){
		Map<String, Object>  map1 = 	baseDao.getFrist("ScanStatics.noInhaveOut", map);
		if(map1!=null){
			return String.valueOf(map1.get("acount"));
		}
		return "0";
	}
	/**
	 * 无到站有派件
	 * @param map
	 * @return
	 */
	public String noInhaveOutSend(Map<String, Object>  map){
		Map<String, Object>  map1 = 	baseDao.getFrist("ScanStatics.noInhaveOutSend", map);
		if(map1!=null){
			return String.valueOf(map1.get("acount"));
		}
		return "0";
	}
	
	/**
	 * 有到站无派件的所有订单 
	 * @param map
	 * @return
	 */
	public String  inNotSend(Map<String, Object>  map){
		
		Map<String, Object>  map1 = 	baseDao.getFrist("ScanStatics.inNotSend", map);
		if(map1!=null){
			return String.valueOf(map1.get("acount"));
		}
		return "0";
	}
	
	
	/**
	 * 查询订单信息
	 * @param map
	 * @return
	 */
	public String checkOrder(Map<String, Object>  map){
		Map<String,Object> checkOrder=  	baseDao.getFrist("ScanStatics.checkOrder", map);
		if(checkOrder!=null){
			return String.valueOf(checkOrder.get("sendSubstationNo"));
		}
		return "";
	}
	
	   /**
	    * 保存月结用户备注
	    * @param map
	    */
	   public void saveOrderNote(Map<String,String> map ){	   
		   baseDao.update("ScanStatics.saveOrderNote", map) ; 
		   
		
	   }
	   /**
	    * 查询当前单号的所有的邮箱
	    * @param map
	    */
	   public List<String> getEmail(Map<String, String> lgcOrderNo){	   
		   List<String> list  = baseDao.getList("ScanEx.getEmail", lgcOrderNo) ; 
		   return list;
		   
		   
	   }
	   /**
	    * 查询选择的每个月结用户的详情
	    * @param map
	    */
	   public   List<Map<String,Object>> getMonthDetail(Map<String, String> lgcOrderNo){	   
		   List<Map<String,Object>> list  = baseDao.getList("ScanEx.getMonthDetail", lgcOrderNo) ; 
		   return list;
		   
		   
	   }
	   /**
	    * 查询月结用户折扣信息
	    * @param map
	    */
	   public   Map<String,Object> getMonthInfo(Map<String, String> params){	   
		  return baseDao.getFrist("ScanEx.getMonthInfo", params) ; 
	   }
	   /**
	    * 更新状态已发送邮件
	    */
	   public  void updateEmailStatus(String str){	   
		   baseDao.update("ScanEx.updateEmailStatus", str) ; 
	   }
}
