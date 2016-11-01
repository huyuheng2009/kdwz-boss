package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.SysLoginLog;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.IPParser;
import com.yogapay.boss.utils.IPParser.IPLocation;
import com.yogapay.boss.utils.StringUtils;

@Service
public class UserService {
	@Resource
	private BaseDao baseDao ;

	public BossUser getByUserName(String userName) throws SQLException {
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("userName", userName);
		return baseDao.getOne("BossUser.getByUserName", params) ;
	}


	public void failTimes(String userName) {
		//userDao.failTimes(userName) ;
		baseDao.update("BossUser.failTimes", userName);
	}
	public void failTimes(String userName,int t) {
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("userName", userName);
		params.put("t", t) ;
		baseDao.update("BossUser.setfailTimes", params);
	}
	
	public void inputTimes(String userName,int t) {
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("userName", userName);
		params.put("t", t) ;
		baseDao.update("BossUser.setInputTimes", params);
	}

	public BossUser getUserByPwd(String userName, String pwd) {
		//return userDao.getUserByPwd(userName, pwd);
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("userName", userName);
		params.put("pwd", pwd) ;
		return baseDao.getOne("BossUser.getUserByPwd", params) ;
	}


	public void status(String userName, int status) {
		//userDao.status(userName,status) ;
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("userName", userName);
		params.put("status", status) ;
		baseDao.update("BossUser.status", params);
	}


	public void saveLog(String ip, String operation, BossUser bossUser) {
		String location = "";
		try {
			IPLocation ipp = IPParser.parse(ip);
			location = ipp.country + ipp.country;
		} catch (Exception e) {
			e.printStackTrace();
		}
		SysLoginLog sysLoginLog = new SysLoginLog() ;
		sysLoginLog.setSysName("BOSS");
		sysLoginLog.setLoginIp(ip);
		sysLoginLog.setLocation(location);
		sysLoginLog.setOperation(operation);
		sysLoginLog.setUserId(bossUser.getId());
		sysLoginLog.setUserName(bossUser.getRealName());
		sysLoginLog.setLastLoginTime(DateUtils.formatDate(new Date()));
		baseDao.insert("SysLog.saveLog", sysLoginLog) ;
       //userDao.saveLog(ip,location , operation, bossUser, new Date());
	}


	public PageInfo<Map<String, Object>> getUserlist(Map<String, String> params, Page pageInfo) {
		//PageInfo<Map<String, Object>> page =new PageInfo(userDao.getUserlist(params));
		return baseDao.getByPage("BossUser.getUserlist", pageInfo,params);
	}


	
	
	public void updateUser(BossUser bu, String[] values, String[] lgcNoList, String[] substationNoList) {
		//userDao.updateUser(bu);
		baseDao.update("BossUser.updateUser", bu) ;
		if (values != null && values.length > 0) {
			baseDao.deleteByParams("BossUser.delUserGroup", bu.getId()) ;
			//userDao.delUserGroup(bu.getId());      // 删除原来的组
				List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
				for (String val : values) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", bu.getId()) ;
					map.put("groupId", Long.parseLong(val)) ;
					objList.add(map);
				}
			  // userDao.saveUserGroup(objList);
		    baseDao.insert("BossUser.saveUserGroup",objList) ;	
		}
		
		
		if (lgcNoList != null && lgcNoList.length > 0) {
			baseDao.deleteByParams("BossUser.delLgcUser", bu.getId()) ;// 删除原来的映射
			List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
			for (String val : lgcNoList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", bu.getId()) ;
				map.put("lgcNo", val) ;
				objList.add(map);
			}
	      baseDao.insert("BossUser.saveLgcUser",objList) ;	
		}
		
	
		System.out.println("substationNoList==========="+substationNoList);
		if(substationNoList != null && substationNoList.length > 0){
			baseDao.deleteByParams("BossUser.delSubstationUser", bu.getId()) ;// 删除原来的映射
			List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
			for (String val : substationNoList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", bu.getId()) ;
				map.put("substationNo", val) ;
				objList.add(map);
			}
	       baseDao.insert("BossUser.saveSubstationUser",objList) ;
		}
		
		
	}


	public boolean isExist(BossUser bu) {
		//Map<String, Object> ret = userDao.findByUserNameOrEmail(bu);
		Map<String, Object> ret = baseDao.getOne("BossUser.findByUserNameOrEmail", bu);
		if (ret!=null) {
			return true ;
		}
		return false;
	}


	public void saveUser(BossUser bu, String[] values,String[] lgcNoList, String[] substationNoList) {
		   //userDao.saveUser(bu);
		   baseDao.insert("BossUser.saveUser", bu) ;
			if (values != null && values.length > 0) {
				List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
				for (String val : values) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", bu.getId()) ;
					map.put("groupId", Long.parseLong(val)) ;
					objList.add(map);
				}
				  baseDao.insert("BossUser.saveUserGroup",objList) ;	
			}
			if (lgcNoList != null && lgcNoList.length > 0) {
				List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
				for (String val : lgcNoList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", bu.getId()) ;
					map.put("lgcNo", val) ;
					objList.add(map);
				}
		      baseDao.insert("BossUser.saveLgcUser",objList) ;	
			}
			System.out.println("substationNoList==========="+substationNoList);
			if(substationNoList != null && substationNoList.length > 0){
				List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
				for (String val : substationNoList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", bu.getId()) ;
					map.put("substationNo", val) ;
					objList.add(map);
				}
		       baseDao.insert("BossUser.saveSubstationUser",objList) ;
			}
	}


	public void delUserById(long id, String userName) {
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("userName", userName);
		params.put("id", id) ;
		baseDao.deleteByParams("BossUser.delUserById", params) ;
		baseDao.deleteByParams("BossUser.delUserGroup", id) ;
		//userDao.delUserById(id,userName);
	    //userDao.delUserGroup(id);
	}

	//获取快递公司编号
	public String getUserLgcNo(Long id) {
		StringBuffer rev = new StringBuffer();
		List<Map<String, Object>> maps = null ;
		if (id==-1) {
			maps  = baseDao.getList("Lgc.getLgcNos") ;
		}else {
			maps  = baseDao.getList("BossUser.getUserLgcNo", id) ;
		}
		for (Map<String, Object> m : maps) {
			if (m.get("lgc_no")!=null&&!StringUtils.isEmptyWithTrim(m.get("lgc_no").toString())) {
				rev.append(m.get("lgc_no"));
				rev.append(",");
			}
		}
		if (null != rev && rev.length() > 0) {
			rev.setLength(rev.length() - 1);
		}
		return rev.toString();
	}
	
	
	
	//获取快递分站编号
	public String getUserSubstationNo(Long id){
		StringBuffer rev = new StringBuffer();
		List<Map<String, Object>> maps = baseDao.getList("BossUser.getUserSubstationNo", id);
		rev.append("'000',") ;
		for(Map<String, Object> m : maps){
			if (m.get("substation_no")!=null&&!StringUtils.isEmptyWithTrim(m.get("substation_no").toString())) {
		    System.out.println(m.get("substation_no"));
			rev.append("'"+m.get("substation_no")+"'");
			rev.append(",");
		}
		}
		if (null != rev && rev.length() > 0) {
			rev.setLength(rev.length() - 1);
		}
		return rev.toString();
	}
	
	
	//获取快递分站编号
	public String getUserSubstationNo(Long id,String lgcNo){
		if (id==1||id==2) {
		   return getUserSubstationByLgcNo(lgcNo);
		}
		StringBuffer rev = new StringBuffer();
		List<Map<String, Object>> maps = baseDao.getList("BossUser.getUserSubstationNo", id);
		rev.append("'000',") ;
		for(Map<String, Object> m : maps){
			if (m.get("substation_no")!=null&&!StringUtils.isEmptyWithTrim(m.get("substation_no").toString())) {
		    System.out.println(m.get("substation_no"));
			rev.append("'"+m.get("substation_no")+"'");
			rev.append(",");
		}
		}
		if (null != rev && rev.length() > 0) {
			rev.setLength(rev.length() - 1);
		}
		return rev.toString();
	}
	
	
	//获取当前用户分站
	public String getUserSubstationNoEx(Long id){
		StringBuffer rev = new StringBuffer();
		String substationNo ="";
		List<Map<String, Object>>list  = baseDao.getList("BossUser.getUserSubstationNo", id);
		if(list.size()>0){
			for(Map<String, Object> map:list){
				rev.append(map.get("substation_no")+",")	;			
			}
		substationNo= rev.substring(0,rev.length()-1);
		}	
		return substationNo;
	}
	
	//获取快递分站编号
	public String getUserSubstationByLgcNo(String lgcNo){
		StringBuffer rev = new StringBuffer();
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("lgcNo", lgcNo) ;
		rev.append("'000',") ;
		List<Map<String, Object>> maps = baseDao.getList("BossUser.getUserSubstationByLgcNo", pMap);
		for(Map<String, Object> m : maps){
			if (m.get("substation_no")!=null&&!StringUtils.isEmptyWithTrim(m.get("substation_no").toString())) {
		    System.out.println(m.get("substation_no"));
		    rev.append("'"+m.get("substation_no")+"'");
			rev.append(",");
		}
		}
		if (null != rev && rev.length() > 0) {
			rev.setLength(rev.length() - 1);
		}
		return rev.toString();
	}
	

    //获取用户下的快递公司
	public List<Map<String, Object>> getCurrentLgc() {
		String lgcNo = Constants.getUser().getLgcNo() ;
		Map<String, Object> params = new HashMap<String, Object>() ;
		if (!StringUtils.isEmptyWithTrim(lgcNo)) {
			params.put("lgcNo", lgcNo);
		}
		return baseDao.getList("BossUser.getCurrentLgc", params);
	}

   //获取用户下的快递分站
	public List<Map<String, Object>> getCurrentSubstation(String sub_limit){
		String substationNo = "";
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		
		if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(sub_limit)) {
			 substationNo = getUserSubstationNo(bossUser.getId());
		}else {
			substationNo = getUserSubstationByLgcNo(bossUser.getLgcNo());
		}
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("substationNo", substationNo);
		if (bossUser.getId()==1||bossUser.getId()==2) {
			params.put("root", "1");
		}
		return baseDao.getList("BossUser.getCurrentSubstation", params);
	}
	
	//获取用户下的快递分站
	public List<Map<String, Object>> getCurrentSubstationByUser(){
		String substationNo = "";
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		substationNo = getUserSubstationNo(bossUser.getId());
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("substationNo", substationNo);
		return baseDao.getList("BossUser.getCurrentSubstation", params);
	}


	public void cpwd(BossUser bu) {
		baseDao.update("BossUser.updateUser", bu) ;
	}

}
