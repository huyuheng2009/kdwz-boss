package com.yogapay.boss.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jpos.util.Log;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.FranchiseRule;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtils;

@Service
public class FranchiseRuleService {
	@Resource
	private BaseDao baseDao ;

	
	
	public PageInfo<Map<String, Object>> listRule(Map<String, String> params, Page pageInfo) throws SQLException {
		PageInfo<Map<String, Object>> page  = baseDao.getByPage("FranchiseRule.list", pageInfo,params) ;
		return page;
	}
	
	public List<Map<String, Object>> getlistRule(Map<String, String> params) throws SQLException {
		List<Map<String, Object>> list  = baseDao.getList("FranchiseRule.list",params) ;
		return list;
	}
	
	public Map<String, Object> getById(Map<String, String> params) throws SQLException {
		Map<String, Object> map  = baseDao.getOne("FranchiseRule.getById",params) ;
		return map;
	}
	
	public void saveRule(FranchiseRule rule) throws SQLException {
		baseDao.insert("FranchiseRule.saveRule", rule) ;
	}
	
	public void batchTno(List<Map<String, Object>> tno) throws SQLException {
		baseDao.insert("FranchiseRule.batchTno", tno) ;
	}
	
	public void batchSno(List<Map<String, Object>> sno) throws SQLException {
		baseDao.insert("FranchiseRule.batchSno", sno) ;
	}
	
	
	public void batchItype(List<Map<String, Object>> itype) throws SQLException {
		baseDao.insert("FranchiseRule.batchItype", itype) ;
	}
	
	
	public List<Map<String, Object>> getRuleTakeNo(boolean select,String rid) throws SQLException {
		List<Map<String,Object>> list  = baseDao.getList("Substation.getSname") ;
		if (select) {
			Map<String, String> map = new HashMap<String, String>() ;
			map.put("rid", rid) ;
			List<Map<String,Object>> list2 = baseDao.getList("FranchiseRule.getRuleTakeNo",map) ;
			for(Map<String,Object> mp:list2){
				Object tno = mp.get("tno");
				for(Map<String,Object> mmp:list){
					Object substation_no =mmp.get("substation_no");
					if(substation_no.equals(tno)){
						mmp.put("checked", "show");
					}else{
						if(!mmp.containsKey("checked")){
							mmp.put("checked", "hiden");
						}
					}
				}
			}
		}
		return list;
	}
	
	public List<Map<String, Object>> getRuleSendNo(boolean select,String rid) throws SQLException {
		List<Map<String,Object>> list  = baseDao.getList("Substation.getSname") ;
		if (select) {
			Map<String, String> map = new HashMap<String, String>() ;
			map.put("rid", rid) ;
			List<Map<String,Object>> list2 = baseDao.getList("FranchiseRule.getRuleSendNo",map) ;
			for(Map<String,Object> mp:list2){
				Object sno = mp.get("sno");
				for(Map<String,Object> mmp:list){
					Object substation_no =mmp.get("substation_no");
					if(substation_no.equals(sno)){
						mmp.put("checked", "show");
					}else{
						if(!mmp.containsKey("checked")){
							mmp.put("checked", "hiden");
						}
					}
				}
			}
		}
		return list;
	}
	
	
	
	public List<Map<String, Object>> getRuleItype(boolean select,String rid) throws SQLException {
		List<Map<String,Object>> list  = baseDao.getList("ItemType.list") ;
		if (select) {
			Map<String, String> map = new HashMap<String, String>() ;
			map.put("rid", rid) ;
			List<Map<String,Object>> list2 = baseDao.getList("FranchiseRule.getRuleItype",map) ;
			for(Map<String,Object> mp:list2){
				Object itype = mp.get("itype");
				for(Map<String,Object> mmp:list){
					Object item_text =mmp.get("item_text");
					if(item_text.equals(itype)){
						mmp.put("checked", "show");
					}else{
						if(!mmp.containsKey("checked")){
							mmp.put("checked", "hiden");
						}
					}
				}
			}
		}
		return list;
	}

	public void delById(Integer id) {
		baseDao.delete("FranchiseRule.delById", id) ;
		baseDao.delete("FranchiseRule.delTnoById", id) ;
		baseDao.delete("FranchiseRule.delSnoById", id) ;
		baseDao.delete("FranchiseRule.delItypeById", id) ;
	}
	
	

    /**
     * 获取最新一条符合条件的报价规则
     * @param tno  寄件网点编号
     * @param sno 派件网点编号
     * @param itype  物品类型
     * @param moneyType  费用类型，Z：中转费，P：派件费
     * @param weightType 算重模式,R实际重量，W四舍五入，L进位0.5
     * @param settleTime  规则时间
     * @return
     */
	public Map<String, Object> getFirstRule(String tno,String sno,String itype,String moneyType,String weightType,String settleTime) {
		Map<String, String> pMap = new HashMap<String, String>() ;
		if (!moneyType.equals("P")) {
			pMap.put("tno", tno) ;
		}
		pMap.put("sno", sno) ;
		pMap.put("itype", itype) ;
		pMap.put("moneyType", moneyType) ;
		if (!StringUtils.isEmptyWithTrim(weightType)) {
			pMap.put("weightType", weightType) ;
		}
		if (!StringUtils.isEmptyWithTrim(settleTime)) {
			pMap.put("settleTime", settleTime) ;
		}
		
		return baseDao.getFrist("FranchiseRule.getRuleByParams", pMap) ;
	}
	
	
	/**
	 * 
	 * @param ruleMap  规则map
	 * @param weight   计算重量
	 * @return
	 */
	public float getRuleSettle(Map<String, Object> ruleMap,String weight) {
		float ret = 0 ;
	    float w = 0 ;
	   try {
		   w= Float.valueOf(weight) ;
	    } catch (Exception e) {
		 e.printStackTrace();
		 w = 0; 
	   }
	   if (w<0.01) {
		return 0 ;
	  }   
	   
	  //以下根据规则计算实际重量 ,R实际重量，W四舍五入，L进位0.5
	   if ("W".equals(StringUtils.nullString(ruleMap.get("weight_type")))) {
		w = Math.round(w) ;
	   }
	   if ("L".equals(StringUtils.nullString(ruleMap.get("weight_type")))) {
		   float wval1 = Float.valueOf(StringUtils.nullString(ruleMap.get("wval1"),"0"))   ;
		   float wval2 = Float.valueOf(StringUtils.nullString(ruleMap.get("wval2"),"0"))   ;
		   int lw = (int) w ;
		   float v = w - lw ;
		   if(wval1 == 0 && wval2 ==0){
			   if(letter(v, Float.valueOf("0.5"), false)){System.out.println("无参数小于0.5");w=lw+Float.valueOf("0.5");}
			   else{System.out.println("无参数大于0.5");w=(float)(lw+1.0);}
		   }else{
			   if(letter(v, wval1, false)){w=lw;}
			   if(biger(v, wval1,true)&&letter(v, wval2,true)){w=lw;w=(float) (w+0.5) ;}
			   if(biger(v, wval2, false)){w=lw;w=(float) (w+1.0) ;}
		   }
		   
	  }
	   
	  float fweight = Float.valueOf(StringUtils.nullString(ruleMap.get("fweight")))   ;
	  float fmoney =  Float.valueOf(StringUtils.nullString(ruleMap.get("fmoney")))   ;
	  float vpay =  Float.valueOf(StringUtils.nullString(ruleMap.get("vpay"),"0"))   ;
	  float zpay =  Float.valueOf(StringUtils.nullString(ruleMap.get("zpay"),"0"))   ;
	  if (w<=fweight) {
		  ret = fmoney +vpay ;
		  //小于最低费用
		  if (biger(zpay, ret, false)) {
			ret = zpay ;
		 }
		  return ret ; 
	  }
	  ret = fmoney ;
	  String jsonString = StringUtils.nullString(ruleMap.get("xweight")) ;
	  Map<String, Object> xweight = JsonUtil.getMapFromJson(jsonString) ;
	  float w1 = Float.valueOf(StringUtils.nullString(xweight.get("w1"),"0"))   ;
	  float w2 = Float.valueOf(StringUtils.nullString(xweight.get("w2"),"0"))   ;
	  float w3 = Float.valueOf(StringUtils.nullString(xweight.get("w3"),"0"))   ;
	  float w4 = Float.valueOf(StringUtils.nullString(xweight.get("w4"),"0"))   ;
	  float p1 = Float.valueOf(StringUtils.nullString(xweight.get("p1"),"0"))   ;
	  float p2 = Float.valueOf(StringUtils.nullString(xweight.get("p2"),"0"))   ;
	  float p3 = Float.valueOf(StringUtils.nullString(xweight.get("p3"),"0"))   ;
	  float p4 = Float.valueOf(StringUtils.nullString(xweight.get("p4"),"0"))   ;
	  System.out.println("w="+w+",w1="+w1+",w2="+w2+",w3="+w3);
	  if (w>fweight&&w<=w1) {
		  ret = fmoney + (w-fweight)*p1 ;
	  }else if (w>w1&&w<=w2) {
		  ret = fmoney + (w-fweight)*p2 ;
	  }else if (w>w2&&w<=w3) {
		  ret = fmoney + (w-fweight)*p3 ;
	  }else if (w>w3&&w<=w4) {
		  ret = fmoney + (w-fweight)*p4 ;
	  }else{
		  return 0;
	  }
	  ret = ret +vpay ;
	  //小于最低费用
	  if (biger(zpay, ret, false)) {
		ret = zpay ;
	 }
		return ret ;
	}
	
	
	public static boolean biger(float f1,float f2,boolean eq) {
		if ((f1-f2)>0.001) {
			return true ;
		}
          if (eq) {
        	  if (Math.abs((f1-f2))<0.001) {
        		  return true ;
  			 }
          }
		return false ;
	}
	
	
	public static boolean letter(float f1,float f2,boolean eq) {
		if ((f2-f1)>0.001) {
			return true ;
		}
		  if (eq) {
        	  if (Math.abs((f1-f2))<0.001) {
        		  return true ;
  			 }
          }
		return false ;
	}
	

}