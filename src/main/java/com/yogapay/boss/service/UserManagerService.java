package com.yogapay.boss.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;

@Service
public class UserManagerService {

	@Resource
	private BaseDao baseDao ;

	//停用分站
	public void sstop(String id){		
		baseDao.update("UserManager.sstop", id);		
	}
	//启用分站
	public void sstart(String id){		
		baseDao.update("UserManager.sstart", id);		
	}
	//停用月结用户
	public void mstop(String id){		
		baseDao.update("UserManager.mstop", id);		
	}
	//启用月结用户
	public void mstart(String id){		
		baseDao.update("UserManager.mstart", id);		
	}
	//停用快递员
	public void cstop(String id){		
		baseDao.update("UserManager.cstop", id);		
	}
	//启用快递员
	public void cstart(String id){		
		baseDao.update("UserManager.cstart", id);		
	}
	//重置快递员密码
	public void cpwd(String id){		
		baseDao.update("UserManager.cpwd", id);		
	}
	//删除分站
	public boolean deleteSubstation(String id){		
		String substationNo = 	baseDao.getFrist("UserManager.gSubByID", id);		
		Map<String,Object> map = baseDao.getFrist("UserManager.gOrderBySub", substationNo);
		if(map==null){
			return true;
		}
		return false;
	}
	//删除月结客户
	public boolean deleteMonthUser(String id){		
		String monthNo = 	baseDao.getFrist("UserManager.gMonthNo", id);		
		Map<String,Object> map = baseDao.getFrist("UserManager.gOrderByMonthNo", monthNo);
		if(map==null){
			return true;
		}
		return false;
	}
	
	//删除代收货款 用户
	public boolean deleteCodUser(String id){		
		String codNo = 	baseDao.getFrist("UserManager.gCodNo", id);		
		Map<String,Object> map = baseDao.getFrist("UserManager.gOrderByCodNo", codNo);
		if(map==null){
			return true;
		}
		return false;
	}




}
