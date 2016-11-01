package com.yogapay.boss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.OrderAddr;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.domain.SendFileInput;
import com.yogapay.boss.domain.TakeInputFile;

@Service
public class UserAddrService {
	@Resource
	private BaseDao baseDao;
	@Resource
	private ManagerDao managerDao;

	/**
	 * 用户地址查询
	 * 
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String, Object>> getAllAddr(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("UserAddr.queryUserAddr", pageInfo, params);
		return page;
	}

	/**
	 * 获取支付方式
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getPayType(Map<String, String> params) {
		List<Map<String, Object>> page = managerDao.getList("UserAddr.queryPayType", params);
		return page;
	}

	public void addOrderAddr(Map<String, String> params) {
		baseDao.insert("UserAddr.addOrderAddr", params);
	}

	/**
	 * 根据用户删除临时表数据
	 * 
	 * @param params
	 */
	public void delByUserName(String str) {
		baseDao.deleteByParams("UserAddr.delByUserName", str);
	}

	public void delAll() {
		baseDao.deleteByParams("UserAddr.delAll", null);
	}

	public String findCourierNo(String couriderName) {
		Map<String, Object> map = baseDao.getOne("UserAddr.findCourierNo", couriderName);
		if (map != null) {
			return String.valueOf(map.get("courierNo"));
		}
		return "";
	}

	public String findCourierNoByInnerNo(String innerNo) {
		Map<String, Object> map = baseDao.getOne("UserAddr.findCourierNoByInnerNo", innerNo);
		if (map != null) {
			return String.valueOf(map.get("courierNo"));
		}
		return "";
	}

	/**
	 * 查询保价费率
	 * 
	 * @return
	 */
	public Map<String, Object> findCpayRate() {
		return baseDao.getOne("UserAddr.findCpayRate", null);

	}

	/**
	 * 查询临时表运单是否存在
	 * 
	 * @return
	 */
	public Map<String, Object> selectLgcOrder(Map<String, String> params) {
		return baseDao.getFrist("UserAddr.selectLgcOrder", params);
	}

	/**
	 * 查询info运单是否存在
	 * 
	 * @return
	 */
	public Map<String, Object> selectOrderLgcOrderNo(OrderAddr orderAddr) {
		return baseDao.getFrist("UserAddr.selectOrderLgcOrderNo", orderAddr);
	}

	/**
	 * 查询info运单是否存在
	 * 
	 * @return
	 */
	public boolean selectOrderLgcOrderNo(OrderInfo orderInfo) {
		Map<String, Object> map = baseDao.getFrist("UserAddr.selectOrderLgcOrderNo2", orderInfo);
		boolean flat = false;
		if (map == null) {
			flat = true;
			;
		}
		return flat;
	}

	/**
	 * 临时表插入数据
	 * 
	 * @param addr
	 */
	public void addOrder(List<OrderAddr> addrList) {
		for (OrderAddr addr : addrList) {
			baseDao.insert("UserAddr.addOrder", addr);
		}
	}

	/**
	 * 查询临时表数据
	 * 
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> selectOrder(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("UserAddr.selectOrder", pageInfo, params);
		return page;
	}

	/**
	 * 查询临时表数据
	 * 
	 * @param params
	 * @param pageInfo
	 * @return
	 */
	public void deleteById(Map<String, String> map) {
		System.out.println("===============================" + map);
		baseDao.deleteByParams("UserAddr.deleteById", map);

	}

	public List<OrderAddr> selectALLUserOrder(String userName) {
		return baseDao.getList("UserAddr.selectALLUserOrder", userName);
	}

	/**
	 * 查询所有当前用户的批量导入
	 * 
	 * @param userName
	 * @return
	 */
	public List<Map<String, Object>> selectALLUserOrderList(String userName) {
		return baseDao.getList("UserAddr.selectALLUserOrderList", userName);
	}

	/**
	 * 保存发件地址
	 * 
	 * @param userName
	 */

	public void saveSendAddr(Map<String, Object> map) {
		baseDao.insert("UserAddr.savaSendAddr", map);
	}

	/**
	 * 新增order_info 信息
	 * 
	 * @param userName
	 */

	public void batchOrderInfo(List<OrderInfo> list) {
		for (OrderInfo OrderInfo : list) {
			baseDao.insert("UserAddr.batchOrderInfo", OrderInfo);
		}
	}

	/**
	 * 新增签收录入 信息
	 * 
	 * @param userName
	 */

	public void batchSignInput(List<OrderInfo> list) {
		for (OrderInfo OrderInfo : list) {
			baseDao.insert("UserAddr.batchSignInput", OrderInfo);
		}
	}

	/**
	 * 获取最新的订单序列
	 * 
	 * @param userName
	 */

	public String orderSequence() {
		Map<String, Object> map = baseDao.getOne("UserAddr.getSequence", null);
		if (map != null) {
			return String.valueOf(map.get("t"));
		}
		return "";
	}

	/**
	 * 修改导入查询
	 * 
	 * @param ID
	 * @return
	 */
	public OrderAddr getOrderBean(int ID) {
		return baseDao.getOne("UserAddr.getOrderBean", String.valueOf(ID));
	}

	/**
	 * 获取物品类型
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getItmeType() {
		return baseDao.getList("UserAddr.getItmeType");
	}

	/**
	 * 更新修改
	 * 
	 * @return
	 */
	public void upOrderInfo(OrderAddr order) {
		baseDao.update("UserAddr.upOrderInfo", order);
	}

	/**
	 * 查询所有取件导入
	 * 
	 * @return
	 */
	public PageInfo<Map<String, Object>> selectTakeFile(Page pageInfo, String str) {
		return baseDao.getByPage("UserAddr.selectTakeFile", pageInfo, str);
	}

	/**
	 * 查询所有派件件导入
	 * 
	 * @return
	 */
	public PageInfo<Map<String, Object>> selectSendFile(Page pageInfo, String str) {
		return baseDao.getByPage("UserAddr.selectSendFile", pageInfo, str);
	}

	/**
	 * 临时表插入数据
	 * 
	 * @param addr
	 */
	public void addTakeFileInput(Map<String, Object> mao) {
		baseDao.insert("UserAddr.addTakeFileInput", mao);
	}

	/**
	 * 签收导入更新
	 * 
	 * @param addr
	 */
	public void addSendFileInput(Map<String, Object> mao) {
		baseDao.insert("UserAddr.addSendFileInput", mao);
	}

	/**
	 * 删除取件导入临时表
	 * 
	 * @param addr
	 */
	public void deleteAllTakeInfo(String userNo) {
		baseDao.update("UserAddr.deleteAllTakeInfo", userNo);
	}

	/**
	 * 删除派件导入临时表
	 * 
	 * @param addr
	 */
	public void deleteAllSendInfo(String userNo) {
		baseDao.update("UserAddr.deleteAllSendInfo", userNo);
	}

	/**
	 * 查询取件导入列表订单
	 * 
	 * @param addr
	 */
	public boolean takeNoCheck(Map<String, Object> mao) {
		Map<String, Object> map = baseDao.getFrist("UserAddr.takeNoCheck", mao);
		boolean flat = true;
		if (map == null) {
			flat = false;
		}
		return flat;
	}

	/**
	 * 查询签收导入列表订单
	 * 
	 * @param addr
	 */
	public boolean sendNoCheck(Map<String, Object> mao) {
		Map<String, Object> map = baseDao.getFrist("UserAddr.sendNoCheck", mao);
		boolean flat = true;
		if (map == null) {
			flat = false;
		}
		return flat;
	}

	/**
	 * 查选所有取件导入
	 * 
	 * @param userName
	 * @return
	 */
	public List<TakeInputFile> getAllTakeUserOrderList(String userName) {
		return baseDao.getList("UserAddr.getAllTakeUserOrderList", userName);
	}

	/**
	 * 查选所有签收导入
	 * 
	 * @param userName
	 * @return
	 */
	public List<SendFileInput> getAllSendUserOrderList(String userName) {
		return baseDao.getList("UserAddr.getAllSendUserOrderList", userName);
	}

	/**
	 * 新增 取件order_info 信息
	 * 
	 * @param userName
	 */

	public void TakeBatchOrderInfo(Map<String, Object> map) {
		baseDao.insert("UserAddr.takeBatchOrderInfo", map);

	}

	/**
	 * 查询order_info信息
	 * 
	 * @param map
	 */
	public OrderInfo getBeanOrderInfo(SendFileInput s) {
		return baseDao.getFrist("UserAddr.getBeanOrderInfo", s);

	}

	/**
	 * 查询分站编号
	 * 
	 * @param map
	 */
	public String findSubNo(String s) {
		Map<String, Object> mao = baseDao.getFrist("UserAddr.findSubNo", s);
		if (mao == null) {
			return "";
		}
		return (String) mao.get("substation_no");
	}

	/**
	 * 签收录入更新OrderInfo
	 * 
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	public void updateOrderInfo(Map<String, Object> map) {

		List<OrderInfo> list = (List<OrderInfo>) map.get("list");
		for (OrderInfo o : list) {
			baseDao.update("UserAddr.updateOrderInfo", o);
		}
	}

	/**
	 * 插入签收录入
	 * 
	 * @param map
	 */
	public void insertInputOrder(Map<String, Object> map) {
		baseDao.insert("UserAddr.insertInputOrder", map);

	}

	/**
	 * 插入批量收件导入订单轨迹
	 * 
	 * @param map
	 */
	public void insertBatchTrack(Map<String, Object> map) {
		baseDao.insert("UserAddr.insertBatchTrack", map);

	}
	
	public void addBatchTrackList(List<OrderTrack> list) {
		baseDao.insert("UserAddr.insertTrackOrder", list);
	}
	
	public void addBatchOrder(List<OrderInfo> list) {
		baseDao.insert("UserAddr.insertOrder", list);
	}
	
	public void updateOrderNote(String batchNumber) {
		List<Map<String,Object>> list = 	baseDao.getList("UserAddr.getBatchOrderByNumer", batchNumber);
		if(list.size()<1){
			return;
		}
		baseDao.insert("UserAddr.insertBatchNote", list);		
	}
	
	public void addOrderImport(List<OrderInfo> list) {
		baseDao.insert("UserAddr.insertOrderImport", list);
	}

}
