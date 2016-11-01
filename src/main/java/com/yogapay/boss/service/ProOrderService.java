package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.ProOrder;
import com.yogapay.boss.domain.PushMsg;
import com.yogapay.boss.domain.Substation;

@Service
public class ProOrderService {

	@Resource
	private BaseDao baseDao ;
	
	
    public long save(ProOrder proOrder) throws SQLException {
		return baseDao.insert("ProOrder.save", proOrder) ;
	}


	public PageInfo<Map<String, Object>> list(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ProOrder.list", pageInfo,params) ;
	}


	public ProOrder findByOrderNo(String orderNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderNo", orderNo);
		return baseDao.getOne("ProOrder.getByOrderNo", params);
	}


	public void update(ProOrder pOrder) {
		baseDao.update("ProOrder.update", pOrder) ;
	}
	
	public Map<String, Object> getStatusById(int id) {
		// TODO Auto-generated method stub
		return baseDao.getById("ProOrderStatus.getById", id);
	}
	
	
}
