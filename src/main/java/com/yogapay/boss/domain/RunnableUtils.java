package com.yogapay.boss.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yogapay.boss.dataSource.DynamicDataSourceHolder;
import com.yogapay.boss.service.UserAddrService;

/**
 * *
 * 请求高德数据
 *
 * @author Administrator
 *
 */
public class RunnableUtils {

	public static final Logger log = LoggerFactory.getLogger(RunnableUtils.class);

	public void BatchRunble(List<OrderInfo> list, UserAddrService userAddrService, String realName, DynamicDataSourceHolder dynamicDataSourceHolder, String uid) {
		BatchThread th = new BatchThread(list, userAddrService, realName, dynamicDataSourceHolder, uid);
		Thread tg = new Thread(th);
		tg.start();
	}

	public void TakeBatchOrder(Map<String, Object> map, UserAddrService userAddrService, String realName, DynamicDataSourceHolder dynamicDataSourceHolder, String uid) {
		TakeBatchThread th = new TakeBatchThread(map, userAddrService, realName, dynamicDataSourceHolder, uid);
		Thread tg2 = new Thread(th);
		tg2.start();
	}

	public void SendBatchOrder(Map<String, Object> map, UserAddrService userAddrService, String realName, DynamicDataSourceHolder dynamicDataSourceHolder, String uid) {
		SendBatchThread th = new SendBatchThread(map, userAddrService, realName, dynamicDataSourceHolder, uid);
		Thread tg3 = new Thread(th);
		tg3.start();
	}

	public void batchOrderRunble(List<OrderInfo> orderlist, List<OrderTrack> trackList, UserAddrService userAddrService, String realName, String uid, DynamicDataSourceHolder dynamicDataSourceHolder, String batchNumber) {
		BatchOrderThread th = new BatchOrderThread(orderlist, trackList, userAddrService, realName, dynamicDataSourceHolder, uid, batchNumber);
		Thread t = new Thread(th);
		t.start();
	}

	public void BatchOrderImportThread(List<OrderInfo> orderlist, List<OrderTrack> trackList, UserAddrService userAddrService, String realName, String uid, DynamicDataSourceHolder dynamicDataSourceHolder) {
		BatchOrderImportThread th = new BatchOrderImportThread(orderlist, trackList, userAddrService, realName, dynamicDataSourceHolder, uid);
		Thread t = new Thread(th);
		t.start();
	}

	/**
	 * 用去批量导入
	 *
	 * @author Administrator
	 *
	 */
	class BatchThread implements Runnable {

		List<OrderInfo> orderInfoList;
		UserAddrService userAddrService;
		String realName;
		DynamicDataSourceHolder dynamicDataSourceHolder;
		String uid;

		public BatchThread(List<OrderInfo> list, UserAddrService user, String realName, DynamicDataSourceHolder dynamicDataSourceHolder, String uid) {
			this.orderInfoList = list;
			this.userAddrService = user;
			this.realName = realName;
			this.dynamicDataSourceHolder = dynamicDataSourceHolder;
			this.uid = uid;
		}

		@Override
		public void run() {
			Date date = new Date();
			log.info("-------------------------------------------批量导入订单线程开始--------------------------------");
			dynamicDataSourceHolder.setDataSource(uid);
			dynamicDataSourceHolder.refulsh(uid);
			log.info(uid);
			userAddrService.batchOrderInfo(orderInfoList);
			userAddrService.batchSignInput(orderInfoList);
			userAddrService.delByUserName(realName);
			log.info("-------------------------------------------批量导入订单线程结束--------------------------------用时" + (new Date().getTime() - date.getTime()) / 1000 + "秒;");

		}
	}

	/**
	 * 取件批量导入线程
	 *
	 * @author Administrator
	 *
	 */
	class TakeBatchThread implements Runnable {

		Map<String, Object> map;
		UserAddrService userAddrService;
		String realName;
		DynamicDataSourceHolder dynamicDataSourceHolder;
		String uid;

		public TakeBatchThread(Map<String, Object> map, UserAddrService user, String realName, DynamicDataSourceHolder dynamicDataSourceHolder, String uid) {
			this.map = map;
			this.userAddrService = user;
			this.realName = realName;
			this.dynamicDataSourceHolder = dynamicDataSourceHolder;
			this.uid = uid;
		}

		@Override
		public void run() {
			Date date = new Date();
			log.info("-------------------------------------------批量导入订单线程开始--------------------------------");
			dynamicDataSourceHolder.setDataSource(uid);
			dynamicDataSourceHolder.refulsh(uid);
			log.info(uid);
			userAddrService.TakeBatchOrderInfo(map);
			userAddrService.insertBatchTrack(map);
			userAddrService.deleteAllTakeInfo(realName);
			log.info("-------------------------------------------批量导入订单线程结束--------------------------------用时" + (new Date().getTime() - date.getTime()) / 1000 + "秒;");

		}
	}

	/**
	 * 取件批量导入线程
	 *
	 * @author Administrator
	 *
	 */
	class SendBatchThread implements Runnable {

		Map<String, Object> map;
		UserAddrService userAddrService;
		String realName;
		DynamicDataSourceHolder dynamicDataSourceHolder;
		String uid;

		public SendBatchThread(Map<String, Object> map, UserAddrService user, String realName, DynamicDataSourceHolder dynamicDataSourceHolder, String uid) {
			this.map = map;
			this.userAddrService = user;
			this.realName = realName;
			this.dynamicDataSourceHolder = dynamicDataSourceHolder;
			this.uid = uid;
		}

		@Override
		public void run() {
			Date date = new Date();
			log.info("-------------------------------------------批量导入订单线程开始--------------------------------");
			dynamicDataSourceHolder.setDataSource(uid);
			dynamicDataSourceHolder.refulsh(uid);
			log.info(uid);
			userAddrService.updateOrderInfo(map);
			userAddrService.insertInputOrder(map);
			userAddrService.deleteAllSendInfo(realName);
			log.info("-------------------------------------------批量导入订单线程结束--------------------------------用时" + (new Date().getTime() - date.getTime()) / 1000 + "秒;");

		}
	}

	/**
	 * 用去批量导入
	 *
	 * @author Administrator
	 *
	 */
	class BatchOrderThread implements Runnable {

		List<OrderInfo> orderList;
		List<OrderTrack> trackList;
		UserAddrService userAddrService;
		String realName;
		DynamicDataSourceHolder dynamicDataSourceHolder;
		String uid;
		String batchNumber;

		public BatchOrderThread(List<OrderInfo> orderlist, List<OrderTrack> trackList, UserAddrService user, String realName, DynamicDataSourceHolder dynamicDataSourceHolder, String uid, String batchNumber) {

			this.orderList = orderlist;
			this.trackList = trackList;
			this.userAddrService = user;
			this.realName = realName;
			this.dynamicDataSourceHolder = dynamicDataSourceHolder;
			this.uid = uid;
			this.batchNumber = batchNumber;
		}

		@Override
		public void run() {
			try {
				Date date = new Date();
				log.info("-------------------------------------------批量导入订单线程开始--------------------------------");
				dynamicDataSourceHolder.setDataSource(uid);
				//dynamicDataSourceHolder.refulsh(uid);
				log.info(uid);
				userAddrService.addBatchOrder(orderList);
				userAddrService.delByUserName(realName);
				userAddrService.updateOrderNote(batchNumber);
				if (trackList != null || !trackList.isEmpty()) {
					userAddrService.addBatchTrackList(trackList);
				}
				log.info("-------------------------------------------批量导入订单线程结束--------------------------------用时" + (new Date().getTime() - date.getTime()) / 1000 + "秒;");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	class BatchOrderImportThread implements Runnable {

		List<OrderInfo> orderList;
		List<OrderTrack> trackList;
		UserAddrService userAddrService;
		String realName;
		DynamicDataSourceHolder dynamicDataSourceHolder;
		String uid;

		public BatchOrderImportThread(List<OrderInfo> orderlist, List<OrderTrack> trackList, UserAddrService user, String realName, DynamicDataSourceHolder dynamicDataSourceHolder, String uid) {

			this.orderList = orderlist;
			this.trackList = trackList;
			this.userAddrService = user;
			this.realName = realName;
			this.dynamicDataSourceHolder = dynamicDataSourceHolder;
			this.uid = uid;
		}

		@Override
		public void run() {
			Date date = new Date();
			log.info("-------------------------------------------批量导入订单线程开始--------------------------------");
			dynamicDataSourceHolder.setDataSource(uid);
			dynamicDataSourceHolder.refulsh(uid);
			log.info(uid);
			userAddrService.addBatchTrackList(trackList);
			userAddrService.addOrderImport(orderList);
			//userAddrService.delImportByUserName(realName,null);
			log.info("-------------------------------------------批量导入订单线程结束--------------------------------用时" + (new Date().getTime() - date.getTime()) / 1000 + "秒;");
		}
	}
}
