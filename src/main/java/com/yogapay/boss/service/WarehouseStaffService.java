package com.yogapay.boss.service;


import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.WarehouseStaff;
import com.yogapay.boss.utils.Md5;

@Service
public class WarehouseStaffService {
	
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String,Object>> selectWStaff(Map<String, String> params,Page pageInfo){
		return baseDao.getByPage("WarehouseStaff.select", pageInfo, params);
	}
	
	public int insertWStaff(Map<String, String> params){
		int ret =0;
		synchronized (WarehouseStaffService.class) {
			String no ="";
			Map<String,Object> m = baseDao.getOne("WarehouseStaff.selectMaxWareStaffNo", params.get("substationNo"));
			if(m!=null && !m.isEmpty()){
				no = m.get("prno").toString();
				params.put("warehouseNo", (Integer.parseInt(no)+1)+"");
			}else{
				params.put("warehouseNo", params.get("substationNo")+"001");
			}
			ret= baseDao.insert("WarehouseStaff.insert", params);
		}
		
		return ret;
	}
	
	public int updateByUserId(Map<String, String> params){
		return baseDao.update("WarehouseStaff.update", params);
	}
	
	public int updateByUserStatus(Map<String, String> params){
		return baseDao.update("WarehouseStaff.updateStatus", params);
	}
	
	public int updateByUserPwd(Map<String, String> params){
		params.put("passWord", Md5.md5Str( params.get("passWord").toString()));
		return baseDao.update("WarehouseStaff.updatePwd", params);
	}
	
	public int deleteById(Map<String, String> params){
		return baseDao.update("WarehouseStaff.deleteById", params);
	}
	
	public Map<String,Object> queryById(String id){
		return baseDao.getById("WarehouseStaff.selectById", id);
	}
	
}
