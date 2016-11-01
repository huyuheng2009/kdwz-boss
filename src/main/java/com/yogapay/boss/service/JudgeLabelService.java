package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.JudgeLabel;

@Service
public class JudgeLabelService {
	
	@Resource
	private BaseDao baseDao;
	public int insert(Map<String, String> params){
		return baseDao.insert("JudgeLabel.insert", params);
	}
	
	public PageInfo<Map<String, Object>> listByPage(Map<String, String> params, Page pageInfo){
		return baseDao.getByPage("JudgeLabel.list", pageInfo, params);
	}
	
	public int deleteById(long id){
		return baseDao.delete("JudgeLabel.delete", id);
	}
	
	public int updateById(Map<String, String> params){
		return baseDao.update("JudgeLabel.update", params);
	}
	
	public int updateStatusById(Map<String, String> params){
		return baseDao.update("JudgeLabel.updateStatus", params);
	}
	
	public JudgeLabel findById(Map<String, String> params){
		return baseDao.getOne("JudgeLabel.selectById", params);
	}
	
	public PageInfo<Map<String, Object>> listSummary(Map<String, String> params,Page pageInfo){
		return baseDao.getByPage("JudgeLabel.selectSummary", pageInfo, params);
	}
	
	public PageInfo<Map<String, Object>> listDetail(Map<String, String> params,Page pageInfo){
		return baseDao.getByPage("JudgeLabel.listDetail", pageInfo, params);
	}
}
