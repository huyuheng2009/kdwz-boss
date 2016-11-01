package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.ItemType;
import com.yogapay.boss.domain.OrderSubstation;
import com.yogapay.boss.utils.StringUtils;

@Service
public class OrderSubstationService {
	@Resource
	private BaseDao baseDao;
	
	
	public void save(OrderSubstation orderSubstation){
		baseDao.deleteByParams("OrderSubstation.delByOIdSno", orderSubstation) ;
		baseDao.insert("OrderSubstation.insert", orderSubstation);
	}
	
   public List<Map<String, Object>> getByOId(Integer id) {
	return baseDao.getList("OrderSubstation.getByOId", id) ;
  }

	
   public int delByOId(Integer id){
		return baseDao.delete("OrderSubstation.delByOId", id);
	}
   
   public int updateByOId(Integer id,Integer taked){
	   Map<String, Object> pMap = new HashMap<String, Object>() ;
	   pMap.put("id", id) ;
	   pMap.put("taked", taked) ;
 		return baseDao.update("OrderSubstation.updateByOId", pMap);
 	}

   public String getStationString(Integer id,String spit){
	   List<Map<String, Object>> map = baseDao.getList("OrderSubstation.getByOId", id) ;
	   StringBuffer rev = new StringBuffer();
	   for(Map<String, Object> m : map){
			if (m.get("substation_no")!=null&&!StringUtils.isEmptyWithTrim(m.get("substation_no").toString())) {
		    rev.append(spit+m.get("substation_no")+spit);
			rev.append(",");
		}
		}
		if (null != rev && rev.length() > 0) {
			rev.setLength(rev.length() - 1);
		}
		return rev.toString() ;
	}

}
