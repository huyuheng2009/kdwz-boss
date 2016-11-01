package com.yogapay.boss.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.JSONResult;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.RunnableUtils;
import com.yogapay.boss.domain.TakeInputFile;
import com.yogapay.boss.service.CourierDayStaticService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.MonthUserStaticService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserAddrService;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.ExcelReader;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;
@Controller
@RequestMapping(value="/orderBatch")
public class OrderBatchController extends BaseController{
	@Resource
	private SubstationService substationService;
	@Resource
	private CourierService courierService;
	@Resource
	private CourierDayStaticService courierDayStaticService;
	@Resource
	private MonthUserStaticService monthUserStaticService;
	@Resource
	private MobileUserService mobileUserService;
	@Resource
	private OrderService orderService;
	@Resource
	private UserAddrService userAddrService;

	@RequestMapping(value="/batchOrder")
	public String batchTake(@RequestParam Map<String, String> params, ModelMap model,
			HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "p", defaultValue = "1") int cpage){
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		String courierNo = params.get("courierNo");
		String beginTime = params.get("beginTime");
		String monthSettleNo = params.get("monthSettleNo");
		String sendSubNo = params.get("sendSubNo");
		System.out.println(courierNo+"=====================courierNo");
		System.out.println(beginTime+"=====================beginTime");
		System.out.println(monthSettleNo+"=====================monthSettleNo");
		System.out.println(sendSubNo+"=====================sendSubNo");
		if (!StringUtils.isEmptyWithTrim(monthSettleNo)) {
			if (monthSettleNo.contains("(")) {
				monthSettleNo = monthSettleNo.substring(0, monthSettleNo.indexOf("("));
				params.put("monthSettleNo", monthSettleNo);
			}
		}
		if (!StringUtils.isEmptyWithTrim(courierNo)) {
			if (courierNo.contains("(")) {
				courierNo = courierNo.substring(0, courierNo.indexOf("("));
				params.put("courierNo", courierNo);
			}
		}
		if (!StringUtils.isEmptyWithTrim(sendSubNo)) {
			if (sendSubNo.contains("(")) {
				sendSubNo = sendSubNo.substring(0, sendSubNo.indexOf("("));
				params.put("sendSubNo", sendSubNo);
			}
		}
		PageInfo<Map<String, Object>> list = userAddrService.selectTakeFile(getPageInfo(cpage),bossUser.getRealName());			
		List<Map<String, Object>> montherUserList = mobileUserService.getAllMonthSetterUser(params);
		List<Map<String, Object>> courierList = mobileUserService.getAllCourierList(params);
		List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));

		model.put("substationList", JsonUtil.toJson(substations));
		model.put("list", list);
		model.put("monthList", JsonUtil.toJson(montherUserList));
		model.put("courierList", JsonUtil.toJson(courierList));
		model.put("params", params);			
		return "orderBatch/batchOrder";
	}

	/**
	 * 导入
	 *
	 * @param params
	 * @param file
	 * @param mmp
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/importOrderExcel")
	public void importOrder(@RequestParam Map<String, String> params, @RequestParam("file") MultipartFile file, ModelMap mmp,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		mmp.put("params", params);
		JSONResult result = new JSONResult();	
		Map<String, String> sMap = null;
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		params.put("userName", bossUser.getRealName());
		//	        String path = request.getSession().getServletContext().getRealPath("/upload"); 
		//	        System.out.println(path+"-----------------------------------------"); 
		//	        File saveFile = new File(path); 
		//	        if (!saveFile.exists()) { 
		//            saveFile.mkdirs(); 
		//
		//	        } 
		//	        File targetFile = new File(path + "\\" + file.getOriginalFilename()); 
		try { 
			//	                file.transferTo(targetFile); 
			//获取文件类型
			String doctype=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1,file.getOriginalFilename().length()); 
			//获取文件名称
			String docname=file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf(".")); 
			Object obj = request.getSession().getAttribute("fileName1");
			if(!StringUtil.isEmptyString(obj)){
				if(docname.equals(obj)){
					result.setErrorCode(1);
					result.setMessage("此文件导入过,不能重复导入");
					outJson(JsonUtil.toJson(result), response);
					return;
				}						
			}		
			InputStream is = file.getInputStream();

			String errorStr  = "";
			if ("xls".equals(doctype)) {  

				int celSize = 12;
				ExcelReader excelReader = new ExcelReader();
				String[] title = excelReader.readExcelTitle(is);
				if (!"运单号".equals(title[0].trim())
						||!"收件公司".equals(title[1].trim())
						||!"地址".equals(title[2].trim())
						|| !"姓名".equals(title[3].trim())
						|| !"联系手机".equals(title[4].trim()) 
						|| !"件数".equals(title[5].trim()) 
						|| !"重量".equals(title[6].trim()) 
						|| !"费用总计".equals(title[7].trim()) 
						|| !"货款".equals(title[8].trim()) 
						|| !"回单号码".equals(title[9].trim())
						|| !"综合备注".equals(title[10].trim()) 
						|| !"派件网点".equals(title[11].trim())) {
					result.setErrorCode(1);
					result.setMessage("请下载正确的模版，填写正确的数据");
					outJson(JsonUtil.toJson(result), response);
					return ;
				}	
				InputStream is1 = file.getInputStream();
				String[][] array = excelReader.readExcelContent(is1);

				List<TakeInputFile> takeInputFileList = new ArrayList<TakeInputFile>();
				if (array.length > 0) {
					if (array[0].length == celSize) {

						for (int i = 0; i < array.length; i++) {

							String[] t = array[i];
							TakeInputFile 	inputFile =new TakeInputFile();
							if(StringUtil.isEmptyString(t[0])&&StringUtil.isEmptyString(t[1])&&
									StringUtil.isEmptyString(t[2])&&StringUtil.isEmptyString(t[3])&&
									StringUtil.isEmptyString(t[4])&&StringUtil.isEmptyString(t[5])&&
									StringUtil.isEmptyString(t[6])&&StringUtil.isEmptyString(t[7])&&
									StringUtil.isEmptyString(t[8])&&StringUtil.isEmptyString(t[9])&&
									StringUtil.isEmptyString(t[10])&&StringUtil.isEmptyString(t[11])){
								continue;
							}			
							if(!StringUtils.isEmptyWithTrim(t[0])){
								Map<String,Object>   mapUser = new HashMap<String,Object>();
								mapUser.put("userNo", bossUser.getRealName());
								mapUser.put("orderNo",t[0]);
								if(userAddrService.takeNoCheck(mapUser)){
									result.setErrorCode(1);
									result.setMessage("请检查运单号"+t[0]+"是否重复");
									outJson(JsonUtil.toJson(result), response);
									return ;									
								}
							}else{
								result.setErrorCode(1);
								result.setMessage("运单号不能为空");
								outJson(JsonUtil.toJson(result), response);
								return ;	
							}
							inputFile.setOrderNo(t[0]);
							inputFile.setTakeCompany(t[1]);
							inputFile.setTakeAddr(t[2]);
							inputFile.setTakeName(t[3]);
							inputFile.setTakePhone(t[4]);
							inputFile.setTakeCount(StringUtils.isEmpty( t[5])?1: Integer.valueOf(t[5]));
							inputFile.setWeight(StringUtils.isEmpty( t[6])?1.00f: Float.valueOf(t[6]));
							inputFile.setPayAcount(StringUtils.isEmpty( t[7])?0.00f: Float.valueOf(t[7]));
							inputFile.setGoodPrice(StringUtils.isEmpty( t[8])?0.00f: Float.valueOf(t[8]));
							inputFile.setBackNumber(t[9]);
							inputFile.setOrderNote(t[10]);
							inputFile.setSendSubtation(t[11]);
							inputFile.setUserNo(bossUser.getRealName());
							takeInputFileList.add(inputFile);					
						}



						Map<String,Object> mao = new HashMap<String,Object>();
						mao.put("list", takeInputFileList);				
						userAddrService.addTakeFileInput(mao);
						result.setErrorCode(0);
						result.setMessage("导入成功");


						request.getSession().setAttribute("fileName1", docname);
						outJson(JsonUtil.toJson(result), response);
						return;
					} else {
						result.setErrorCode(1);
						result.setMessage("请下载正确的模版，填写正确的数据");
						outJson(JsonUtil.toJson(result), response);
						return;
					}
				}


			}else{
				result.setErrorCode(1);
				result.setMessage("只能导入xls文件");
				outJson(JsonUtil.toJson(result), response);
				return;
			}

		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	/**
	 * 导入
	 *
	 * @param params
	 * @param file
	 * @param mmp
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/deleteAll")
	public void deleteAll(@RequestParam Map<String, String> params,  ModelMap mmp,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		params.put("userName", bossUser.getRealName());			
		userAddrService.deleteAllTakeInfo(bossUser.getRealName());
		request.getSession().removeAttribute("fileName1");
		JSONResult result = new JSONResult();
		result.setErrorCode(0);
		result.setMessage("删除成功");
		outJson(JsonUtil.toJson(result), response);
		return;
	}
	/**
	 *
	 *
	 * 添加批量下单
	 *
	 * @param params
	 * @param mmap
	 * @return
	 */
	@RequestMapping(value = "/addOrderMuch")
	public String addOrdeMuch(@RequestParam Map<String, String> params, ModelMap mmap, HttpServletRequest request,HttpServletResponse resp) {		
		List<Map<String, Object>> montherUserList = mobileUserService.getAllMonthSetterUser(params);
		List<Map<String, Object>> courierList = mobileUserService.getAllCourierList(params);
		List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));	
	
		String courierNo = params.get("courierNo");
		String beginTime = params.get("beginTime");
		String monthSettleNo = params.get("monthSettleNo");
		String sendSubNo = params.get("sendSubNo");
		String monthUser = params.get("monthUser");//默认月结用户为寄件地址
		String monthPhone = params.get("monthPhone");
		String monthAddr = params.get("monthAddr");
		String company = params.get("company");
		System.out.println(monthUser+","+monthPhone+","+monthAddr+","+company);
		mmap.put("params", params);
		mmap.put("monthList", JsonUtil.toJson(montherUserList));
		mmap.put("courierList", JsonUtil.toJson(courierList));
		mmap.put("substationList", JsonUtil.toJson(substations));
		mmap.put("params", params);
		if(StringUtil.isEmptyString(courierNo)){
			mmap.put("msg", "取件快递员不能为空");					
			return "forward:batchOrder";
		}
		if(StringUtil.isEmptyString(beginTime)){
			mmap.put("msg", "寄件时间不能为空");
			return "forward:batchOrder";
		}
		if(StringUtil.isEmptyString(monthSettleNo)){
			mmap.put("msg", "月结客户编号不能为空");
			return "forward:batchOrder";
		}
		if(StringUtil.isEmptyString(sendSubNo)){
			mmap.put("msg", "收件分站不能为空");
			return "forward:batchOrder";
		}

		System.out.println(courierNo+"=====================courierNo");
		System.out.println(beginTime+"=====================beginTime");
		System.out.println(monthSettleNo+"=====================monthSettleNo");
		System.out.println(sendSubNo+"=====================sendSubNo");
		if (monthSettleNo.contains("(")) {
			monthSettleNo = monthSettleNo.substring(0, monthSettleNo.indexOf("("));
			params.put("monthSettleNo", monthSettleNo);
		}	
		if (courierNo.contains("(")) {
			courierNo = courierNo.substring(0, courierNo.indexOf("("));
			params.put("courierNo", courierNo);
		}	
		if (sendSubNo.contains("(")) {
			sendSubNo = sendSubNo.substring(0, sendSubNo.indexOf("("));
			params.put("sendSubNo", sendSubNo);	
		}
		Map<String, Object> monthMap = mobileUserService.getMuserByNo(monthSettleNo);
		if(monthMap==null){
			mmap.put("msg", "月结客户不存在,请检查");
			return "forward:batchOrder";
		}
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		Date nowDate= new Date();
		List<TakeInputFile> batchInfo = userAddrService.getAllTakeUserOrderList(bossUser.getRealName());
		if (batchInfo.size() <= 0) {
			mmap.put("msg", "请添加收件人信息");
			return "forward:batchOrder";
		}

		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		
		for(TakeInputFile tf : batchInfo){
			OrderInfo order = new OrderInfo();
			order.setOrderNo(userAddrService.orderSequence());
			order.setUserNo(bossUser.getRealName());
			order.setLgcNo(bossUser.getLgcNo());
			order.setLgcOrderNo(tf.getOrderNo());
			if(!userAddrService.selectOrderLgcOrderNo(order)){
				mmap.put("msg", "提交失败，运单号"+order.getLgcOrderNo()+"当前系统已存在");	
				return "forward:batchOrder";
			}		
			order.setSendName(monthUser);
			order.setSendPhone(monthPhone);
			order.setSendAddr(monthAddr);
			order.setSendKehu(company);
			order.setInputTime(DateUtils.formatDate(nowDate, "yyyy-MM-dd HH:mm:ss"));
			order.setRevKehu(tf.getTakeCompany());
			order.setRevAddr(tf.getTakeAddr());		
			order.setRevName(tf.getTakeName());		
			order.setRevPhone(tf.getTakePhone());		
			order.setItemCount(String.valueOf(tf.getTakeCount()));		
			order.setItemWeight(tf.getWeight());		
			order.setFreight(tf.getPayAcount());
			order.setFreightType("1");
			order.setStatus(2);
			order.setGoodPrice(tf.getGoodPrice());
			order.setTnpay(String.valueOf(tf.getPayAcount()));			
			order.setSnpay(String.valueOf(tf.getGoodPrice()));
			order.setMpay(tf.getPayAcount());
			order.setPayAcount(tf.getPayAcount()+tf.getGoodPrice());
			order.setReceNo(tf.getBackNumber());//回单号码
			order.setOrderNote(tf.getOrderNote());
			order.setSubStationNo(sendSubNo);//取件分站
			order.setTakeOrderTime(beginTime);//取件时间
			order.setCreateTime(beginTime);
			order.setLastUpdateTime(beginTime);
			order.setTakeCourierNo(courierNo);//取件快递
			order.setPayType("MONTH");
			order.setMonthSettleNo(monthSettleNo);
			order.setSource("BATCH");
			order.setFpayStatus("SUCCESS");
			orderInfoList.add(order);
		}
			Map<String,Object> map  =new HashMap<String,Object>();
			map.put("list", orderInfoList);

		LgcConfig lgcConfig = (LgcConfig)request.getSession().getAttribute("lgcConfig") ;	
		String key = "yx" ;
		if (lgcConfig!=null) {
			key = lgcConfig.getKey() ;
		}
		RunnableUtils run  = new RunnableUtils();
		run.TakeBatchOrder(map, userAddrService, bossUser.getRealName(), dynamicDataSourceHolder, key);

		return 	"orderBatch/success1";
	}
	//月结客户号查询
		@RequestMapping(value = {"/checkMonthUser"})
		public void checkMonthUser(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			String monthUserNo = params.get("monthSettleNo");//月结客户号
			if (monthUserNo.contains("(")) {
				monthUserNo = monthUserNo.substring(0, monthUserNo.indexOf("("));
			}
			Map<String, Object> map = mobileUserService.getMuserByNo(monthUserNo);
			if (map == null) {
				params.put("company", "未知客户编号");
				params.put("name", "未知客户编号");
				params.put("phone", "未知客户编号");
				params.put("address", "未知客户编号");
				outJson(JsonUtil.toJson(params), response);
			} else {
				System.out.println(map);
				params.put("company", (String) map.get("month_sname"));
				params.put("name", (String) map.get("contact_name"));
				params.put("phone", (String) map.get("contact_phone"));
				params.put("address", (String) map.get("contact_addr"));
				outJson(JsonUtil.toJson(params), response);
			}
		}

}
