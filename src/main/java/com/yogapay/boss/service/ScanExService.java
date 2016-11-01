package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
@Service
public class ScanExService {
	@Resource
	private BaseDao baseDao;
/**
 * 查询所有快递员
 * @param map
 * @return
 */
	public List<Map<String,Object>> allCourierBySub(Map<String,String> map){
		return 	baseDao.getList("ScanEx.allCourierBySub",map);
	}
	/**
	 * 查询所有仓管员
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> allManager(Map<String,String> map){
		return 	baseDao.getList("ScanEx.allManager",map);
	}
	/**
	 * 查询所有分站
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> allSubNo(){
		return 	baseDao.getList("ScanEx.allSubNo");
	}
	/**
	 * 查询问题件原因
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> allErrorReason(){
		return 	baseDao.getList("ScanEx.allErrorReason");
	}
	/**
	 * 查询所有问题件处理状态
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> allErrorProStatus(){
		return 	baseDao.getList("ScanEx.allErrorProStatus");
	}
	public PageInfo<Map<String, Object>> getTakeDetailList(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ScanEx.getTakeDetailList", pageInfo, params);
	}
	public PageInfo<Map<String, Object>> getSendDetailList(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ScanEx.getSendDetailList", pageInfo, params);
	}
	/**
	 * 查询到站
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> getInCount(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ScanEx.getInCount", pageInfo, params);
	}
	/**
	 * 查询出站
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> getOutCount(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ScanEx.getOutCount", pageInfo, params);
	}
	/**
	 * 查询问题件
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> getErrorCount(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ScanEx.getErrorCount", pageInfo, params);
	}
	/**
	 * 查询签收单
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> getComplateOrderCount(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ScanEx.getComplateOrderCount", pageInfo, params);
	}
	/**
	 *有到站有出战
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> getInAndOutCount(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ScanEx.getInAndOutCount", pageInfo, params);
	}
	/**
	 *有到站无出战
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> getInNotOutCount(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ScanEx.getInNotOutCount", pageInfo, params);
	}
	/**
	 *无到站有派件
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> noInhaveOutSend(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ScanEx.noInhaveOutSend", pageInfo, params);
	}
	public String getStatusNoContent(String str) {
		return baseDao.getOne("ScanEx.getStatusNoContent", str);
	}
	public String getReasonNoContent(String str) {
		return baseDao.getOne("ScanEx.getReasonNoContent", str);
	}
	/**
	 *扫描记录查询
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> getOrderScan(Map<String, String> params,Page pageInfo) {
		return baseDao.getByPage("ScanEx.orderScan", pageInfo, params);
	}
	/**
	 *扫描记录查询 用于生成报表
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public List<Map<String, Object>> getOrderScanList(Map<String, String> params) {
		return baseDao.getList("ScanEx.orderScan", params);
	}
	/**
	 *查询分站名
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public String getSubName(String str) {
		return baseDao.getOne("ScanEx.getSubName", str);
	}
	/**
	 *查询快递员名
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public String getCourierName(String str) {
		return baseDao.getOne("ScanEx.getCourierName", str);
	}
	
}
