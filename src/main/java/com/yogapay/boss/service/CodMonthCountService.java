package com.yogapay.boss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.CodMonthCount;
import com.yogapay.boss.domain.CodRateType;
import com.yogapay.boss.domain.MonthSettleType;

@Service
public class CodMonthCountService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("CodMonthCount.list", pageInfo, params);
		return  page;
	}
	
	public int saveCodMonthCount(CodMonthCount codMonthCount){
		return baseDao.insert("CodMonthCount.insert", codMonthCount);
	}
	
	public int batchSaveCodMonthCount(List<CodMonthCount> monthCountList){
		return baseDao.insert("CodMonthCount.insertBatch", monthCountList);
	}
	
	public int settleUpdate(Integer id){
		return baseDao.update("CodMonthCount.settleUpdate", id);
	}

	public int printUpdate(Integer id){
		return baseDao.update("CodMonthCount.printUpdate", id);
	}

	public int delById(Integer id){
		return baseDao.delete("CodMonthCount.delById", id);
	}

	public int delByMonth(String settleDate){
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("settleDate", settleDate) ;
		return baseDao.deleteByParams("CodMonthCount.delByMonth", params);
	}

}
