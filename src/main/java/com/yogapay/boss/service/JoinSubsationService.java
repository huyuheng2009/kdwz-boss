package com.yogapay.boss.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.JoinSubstationAcount;
import com.yogapay.boss.domain.Substation;


@Service
public class JoinSubsationService {
	@Resource
	private BaseDao baseDao ;
	//	查询所有加盟网点
	public List<Map<String,Object>> getJoinSubstationList(){
		List<Map<String,Object>> list  = baseDao.getList("Join.joinSubstationList");	
		return list;		
	}
	  public PageInfo<Map<String, Object>> joinList(Map<String, String> params, Page pageInfo) {
	        return baseDao.getByPage("Join.joinList", pageInfo, params);
	    }
	  //查询未开账的网点
	  public List<String> getNotOpenSubstation(){
		  return baseDao.getList("Join.getNotOpenSubstation");
	  }
	  //查询所有开过账的网点 包括未启用的
	  public List<Map<String, Object>> allJoinOpenSubstation(Map<String, String> params){
		  return baseDao.getList("Join.allJoinOpenSubstation",params);
	  }
	  //批量开账
	  public void batchOpenSubstation(List<Map<String,Object>> list){
		  baseDao.insert("Join.batchOpenSubstation", list);
	  }
	  //开账网点启用展厅
	  public void updateStatus(Map<String, String> params){
		  baseDao.update("Join.updateStatus", params) ;
	  }
	  //保存编辑
	  public void saveEdit(Map<String, String> params){
		  baseDao.update("Join.saveEdit", params) ;
	  }
	  //加盟网点开账信息查询
	  public JoinSubstationAcount   getInfo(String id){
		  return baseDao.getFrist("Join.getInfo", id);
	  }
	  //更新开账网点启用时间
	  public void updateTime(Object obj){
		  baseDao.update("Join.updateTime", obj);
	  }
	  //查询以开帐 并启用的网点
		public List<Map<String,Object>> getJoinOpenList(){
			List<Map<String,Object>> list  = baseDao.getList("Join.getJoinOpenList");	
			return list;		
		}
		//查询加盟网点是否已开账并启用
		public boolean checkAcount(Map<String, String> params){
			Map<String,Object> map = baseDao.getFrist("Join.checkAcount", params);
			if(map==null){
				return true;
			}
			return false;
		}
		//插入充值记录并更新开账表
		public void insertBill(Map<String, String> params){
			JoinSubstationAcount joinInfo = 	baseDao.getFrist("Join.selectJoinSubInfo", params.get("substationNo"));	
			String sendTime  =baseDao.getFrist("Join.getSendTime", params.get("lgcOrderNo"));
			BigDecimal user= new BigDecimal(params.get("money"));			
			params.put("sendTime", sendTime)	;
			params.put("beforeBalance", joinInfo.getCurBalance().toString());
			if("S".equals(params.get("type"))){
				params.put("afterBalance",  user.add( joinInfo.getCurBalance()).toString() )	;
			}
			if("Z".equals(params.get("type"))){
				params.put("afterBalance",   joinInfo.getCurBalance().subtract(user).toString() )	;
				params.put("money", "-"+params.get("money")) ;
			}
			params.put("source",  "USER")	;		
			baseDao.insert("Join.insertBill", params);
			
			baseDao.update("Join.updateCountBill", params);
		}
		//查询订单石头存在 并已经发件的发件时间
		public String getSendTime(String lgcOrderNo){
			String sendTime  = baseDao.getFrist("Join.getSendTime", lgcOrderNo);
			return sendTime;
		}
		//充值明细查询
		  public PageInfo<Map<String, Object>> detailList(Map<String, String> params, Page pageInfo) {
		        return baseDao.getByPage("Join.detailList", pageInfo, params);
		    }
		
		  //历史
		  public PageInfo<Map<String, Object>> acountList(Map<String, String> params, Page pageInfo) {
			  return baseDao.getByPage("Join.acountList", pageInfo, params);
		  }
		  public Map<String, Object> allAcountMap(Map<String, String> params){
			  return baseDao.getFrist("Join.allAcountMap", params); 
		  }
		 
		  /**
		   * 
		   * @param sno
		   * @return  非加盟或者加盟网点余额充足，或者加盟网点账户停用返回false，其余返回true
		   */
		  public boolean shutAcount (String sno){
			   Map<String, String> params = new HashMap<String, String>();
				params.put("sno", sno);
				Substation station = baseDao.getOne("Substation.getBySno", params);
				if (!"J".equals(station.getSubstationType())) {
					return false ;
				}
			   Map<String, Object>	map = baseDao.getOne("Join.selectShut", params) ;
			   if (map==null||"0".equals(map.get("status"))) {
				 return false ;
			  }
			   params.put("shut", "1");  
			   map = baseDao.getOne("Join.selectShut", params) ;
			   if (map!=null) {
					return false ;
				}
			   return true ;
		  }  
		  
}
