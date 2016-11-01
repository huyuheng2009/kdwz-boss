package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hp.hpl.sparta.xpath.TrueExpr;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.utils.StringUtils;

@Service
public class SubstationService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("Substation.list", pageInfo, params);
		return  page;
	}
	
	public PageInfo<Map<String, Object>> ulist(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("Substation.ulist", pageInfo, params);
		return  page;
	}
	
	public PageInfo<Map<String, Object>> tlist(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("Substation.tlist", pageInfo, params);
		return  page;
	}
	
	
	public PageInfo<Map<String, Object>> alist(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("Substation.alist", pageInfo, params);
		return  page;
	}
	
	public Substation getSubstationById(Integer id){
		Substation station = baseDao.getById("Substation.getById", id);
		return station;
	}
	
	public Substation getSubstationByNo(String sno){
		Map<String, String> params = new HashMap<String, String>();
		params.put("sno", sno);
		Substation station = baseDao.getOne("Substation.getBySno", params);
		return station;
	}
	
	public Substation getSubstationByCourierNo(String cno){
		Map<String, String> params = new HashMap<String, String>();
		params.put("cno", cno);
		Substation station = baseDao.getOne("Substation.getSubstationByCourierNo", params);
		return station;
	}
	
	public int save(Substation station){
		return baseDao.insert("Substation.insert", station);
	}
	
	public boolean isExist(Substation station){
		Map<String, String> params = new HashMap<String, String>();
		params.put("substationName", station.getSubstationName());
		params.put("lgcNo", station.getLgcNo());
		Substation stationinfo = baseDao.getOne("Substation.getBySubstationName", params);
		if(stationinfo == null)
			return false;
		else
			return true;
	}
	
	public boolean isExist(String sno){
		Map<String, String> params = new HashMap<String, String>();
		params.put("sno", sno);
		Substation stationinfo = baseDao.getOne("Substation.getBySno", params);
		if(stationinfo == null)
			return false;
		else
			return true;
	}
	
	
	public int update(Substation station){
		return baseDao.update("Substation.update", station);
	}
	
	public int delete(Integer id){
		return baseDao.delete("Substation.delete", id);
	}

	public void nextCno(Substation substation) {
		baseDao.update("Substation.nextCno", substation);
	}
	
	   public String getStationStringByArea(String area,String spit){
		   Map<String, String> params = new HashMap<String, String>();
			params.put("sarea", area);
		   List<Substation> substations = baseDao.getList("Substation.getStationByLgcArea", params) ;
		   StringBuffer rev = new StringBuffer();
		   for(Substation s : substations){
			    rev.append(spit+s.getSubstationNo()+spit);
				rev.append(",");
			}
			if (null != rev && rev.length() > 0) {
				rev.setLength(rev.length() - 1);
			}
			return rev.toString() ;
		}
	
	   
	   public String getStationStringByLgc(String lgcNo,String spit){
		   Map<String, String> params = new HashMap<String, String>();
			params.put("lgcNo", lgcNo);
		   List<Substation> substations = baseDao.getList("Substation.getStationByLgcArea", params) ;
		   StringBuffer rev = new StringBuffer();
		   for(Substation s : substations){
			    rev.append(spit+s.getSubstationNo()+spit);
				rev.append(",");
			}
			if (null != rev && rev.length() > 0) {
				rev.setLength(rev.length() - 1);
			}
			return rev.toString() ;
		}
	   
	   public Map<String,Object> queryIsExitsBySubstationName(String substation,String courier) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("substation", substation);	
			params.put("courier", courier);
			Map<String,Object> station = baseDao.getOne("Substation.getByName", params);
			return station;
		}

	   /**
	    *  查询加盟网点余额
	    * @param sno
	    * @return
	    */
	   public String queryBalance(String sno,boolean rnull) {
		   Map<String, String> params = new HashMap<String, String>();
			params.put("sno", sno);	
			Map<String,Object> station = baseDao.getOne("Substation.queryBalance", params);
			if (station==null) {
				if (rnull) {
					return null ;
				}else {
					return "0" ;
				}
				
			}else {
				return StringUtils.nullString(station.get("cur_balance")) ;
			}
	}
	   
	   /**
	    *  查询加盟网点是否警报金额
	    * @param sno
	    * @return true为达到警报金额
	    */
	   public boolean queryShutBalance(String sno) {
		   Map<String, String> params = new HashMap<String, String>();
			params.put("sno", sno);	
			Map<String,Object> station = baseDao.getOne("Substation.queryShutBalance", params);
			if (station==null) {
				return true ;
			}
           return false ;
	}
	   
}
