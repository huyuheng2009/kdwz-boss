package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.TableFiledSort;

@Service
public class TableFiledSortService {
	@Resource
	private BaseDao baseDao;
	
	public List<TableFiledSort> selectList(Map<String, String> params){
		return baseDao.getList("TableFiledSort.queryByTab", params);
	}
	
	public int updateByTabAndCol(List<TableFiledSort> list){
		int r =0;
		for (TableFiledSort tableFiledSort : list) {
			r+=baseDao.update("TableFiledSort.updateByTabAndCol", tableFiledSort);
		}
		return r;
	}
	
	public int deleteByUserNo(Map<String, String> params){
		return baseDao.deleteByParams("TableFiledSort.delete", params);
	}
	
	public int insert(List<TableFiledSort> list){
		return baseDao.insert("TableFiledSort.insert", list);
	}
	
	
}
