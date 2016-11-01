/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.shiro.SecurityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.classic.Logger;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.City;
import com.yogapay.boss.domain.JSONResult;
import com.yogapay.boss.domain.OrderAddr;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.RunnableUtils;
import com.yogapay.boss.enums.Check;
import com.yogapay.boss.service.CityService;
import com.yogapay.boss.service.ItemTypeService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.UserAddrService;
import com.yogapay.boss.utils.CityUtil;
import com.yogapay.boss.utils.DateUtil;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.ExcelReader;
import com.yogapay.boss.utils.ExcelUtil;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;





/**
 *
 * @author wang
 */
@Controller
@RequestMapping(value = "/batch")
@SuppressWarnings("static-method")
public class OrderFrontController extends BaseController {
	private final Logger log = (Logger) LoggerFactory.getLogger(ExcelUtil.class) ;
	@Resource
	private 	CityService cityService;
	@Resource
	private 	OrderService orderService;
	@Resource
	private 	UserAddrService userAddrService;
	@Resource
	private 	ItemTypeService itemTypeService;

	@RequestMapping(value = "addOrderMuchPage")
	public Object addOrderMuchPage(@RequestParam Map<String, String> params, ModelMap mmap,HttpServletResponse response, HttpServletRequest request) {
		try{

			List<City> list = cityService.getByIdList(0);
			System.out.println("---------------------------------"+list.size());
			mmap.addAttribute("procity", CityUtil.getCityArea(list, "sprovince", "sendArea", "down_addrSend", "scity", "sarea", "addr_contSend"));
			Date date = DateUtil.getCurrentDate();
			String year = DateUtil.dateToString(date, "yyyy");
			mmap.addAttribute("jt", "今天（" + DateUtil.dateToString(date, "M月d日") + ")");
			mmap.addAttribute("jty", DateUtil.dateToString(date, "yyyy-MM-dd"));
			Date t1 = DateUtil.getAddDay(1);
			String t1year = DateUtil.dateToString(t1, "yyyy");
			mmap.addAttribute("mty", DateUtil.dateToString(t1, "yyyy-MM-dd"));
			if (year.equals(t1year)) {
				mmap.addAttribute("mt", "明天（" + DateUtil.dateToString(t1, "M月d日") + ")");
			} else {
				mmap.addAttribute("mt", "明年（" + DateUtil.dateToString(t1, "M月d日") + ")");
			}
			Date t2 = DateUtil.getAddDay(2);
			String t2year = DateUtil.dateToString(t2, "yyyy");
			mmap.addAttribute("hty", DateUtil.dateToString(t2, "yyyy-MM-dd"));
			if (year.equals(t2year)) {
				mmap.addAttribute("ht", "后天（" + DateUtil.dateToString(t2, "M月d日") + ")");
			} else {
				mmap.addAttribute("ht", "明年（" + DateUtil.dateToString(t2, "M月d日") + ")");
			}
			mmap.addAttribute("params", params);
		} catch (Exception ex) {
			log.error(null, ex);
		}
		return "batch/orderMuchAdd";
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
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();

		Date nowDate= new Date();

		List<OrderAddr> batchInfo = userAddrService.	selectALLUserOrder(bossUser.getRealName());

		if (batchInfo.size() <= 0) {
			mmap.put("msg", "请添加收件人信息");
			mmap.put("params", params);
			return "batch/orderMuchAdd";
		}

		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		if("A".equals(params.get("check"))){				
			for(OrderAddr order:batchInfo){
				order.setLgcNo(bossUser.getLgcNo());
				OrderInfo info  = new OrderInfo();											
				if(userAddrService.selectOrderLgcOrderNo(order)!=null){
					mmap.put("msg", "提交失败，运单号"+order.getLgcOrderNo()+"重复");
					mmap.put("params", params);							
					return "batch/orderMuchAdd";
				}
				info.setLgcNo(order.getLgcNo());
				info.setLgcOrderNo(order.getLgcOrderNo());//
				info.setUserNo(bossUser.getRealName());
				info.setCreateTime(order.getSendOrderTime());
				info.setLastUpdateTime(order.getSendOrderTime());
				info.setOrderNo(userAddrService.orderSequence());//获取订单编号
				info.setTakeOrderTime(order.getSendOrderTime());//发件时间
				info.setSendOrderTime(order.getSendOrderTime());//发件时间
				info.setStatus(3);
				String goodPrice = order.getGoodPrice();
				if(!StringUtil.isEmptyString(goodPrice)){
					info.setCod(1);
					info.setGoodPrice(Float.valueOf(goodPrice));
				}else{
					info.setCod(0);
					info.setGoodPrice(0.00f);	
				}
				info.setSendCourierNo(userAddrService. findCourierNo(order.getSendCourierNo()));
				info.setTakeCourierNo(userAddrService. findCourierNo(order.getTakeCourierNo()));
				float vpay = 0.00f;		
				float goodValuation=0.00f;
				if(!StringUtil.isEmptyString(order.getGoodValuation())){				
					Map<String,Object> cpRate=userAddrService. findCpayRate();
					float rate =Float.valueOf(String.valueOf(cpRate.get("rate")));
					goodValuation =Float.valueOf(order.getGoodValuation());
					vpay =  rate*goodValuation/1000f;
					info.setGoodValuation(goodValuation);
					info.setVpay(vpay);
					info.setGoodValuationRate(String.valueOf(cpRate.get("rate")));
				}else{
					info.setGoodValuation(goodValuation);
					info.setVpay(vpay);
					info.setGoodValuationRate("");
				}
				info.setSendPhone(order.getSendPhone());
				info.setSendAddr(order.getSendAddr());
				info.setSendName(order.getSendName());
				info.setRevPhone(order.getRevPhone());
				info.setRevAddr(order.getRevAddr());
				info.setRevName(order.getRevName());
				float freight = 0.00f;
				if(!StringUtil.isEmptyString(order.getMonthNo())){
					freight = StringUtil.isEmptyString(order.getFreight())?0.00f:Float.valueOf(order.getFreight());
					info.setMpay(vpay+freight);	
					info.setMonthSettleNo(order.getMonthNo());										
				}
				info.setFreight(freight);
				info.setFreightType(Check.getType(order.getFreightType()));
				info.setPayType("MONTH");
				info.setOrderNote(order.getOrderNote());
				info.setSource("BATCH");//批量下单
				info.setItemStatus(order.getItemStatus());
				info.setItemWeight(StringUtil.isEmptyString(order.getItemWeight())?0.00f:Float.valueOf(order.getItemWeight()));
				if("1".equals(info.getFreightType())){
					info.setTnpay(StringUtil.isEmptyString(freight+info.getVpay())?"0":String.valueOf(freight+info.getVpay()));
					if(info.getCod()==1){
						info.setSnpay(StringUtil.isEmptyString(goodPrice)?"0":String.valueOf(goodPrice));
					}else{
						info.setSnpay("0");
					}			
				}else{
					info.setTnpay("0");
					if(info.getCod()==1){
						info.setSnpay(StringUtil.isEmptyString(freight+info.getVpay()+Float.valueOf(goodPrice))?"0":String.valueOf(freight+info.getVpay()+Float.valueOf(goodPrice)));
					}else{
						info.setSnpay(StringUtil.isEmptyString(freight+info.getVpay())?"0":String.valueOf(freight+info.getVpay()));
					}			
				}
				info.setPayAcount(freight+info.getVpay()+Float.valueOf(info.getGoodPrice()));
				info.setExamineStatus("PASS");
				orderInfoList.add(info)	;						
			}			
			RunnableUtils run  = new RunnableUtils();
			run.BatchRunble(orderInfoList, userAddrService, bossUser.getRealName(), dynamicDataSourceHolder,dynamicDataSourceHolder.getKey());
		
		}
		//		{sendPhone=, check=A, sendName=, ids=, sendAddr=, sendArea=, serviceName=batchExport}
		if("B".equals(params.get("check"))){				
			for(OrderAddr order:batchInfo){
				order.setLgcNo(bossUser.getLgcNo());
				OrderInfo info  = new OrderInfo();											
				if(userAddrService.selectOrderLgcOrderNo(order)!=null){
					mmap.put("msg", "提交失败，当前系统已存在运单号："+order.getLgcOrderNo());
					mmap.put("params", params);							
					return "batch/orderMuchAdd";
				}
		
				info.setLgcNo(order.getLgcNo());
				info.setLgcOrderNo(order.getLgcOrderNo());//
				info.setUserNo(bossUser.getRealName());
				info.setCreateTime(order.getSendOrderTime());
				info.setLastUpdateTime(order.getSendOrderTime());
				info.setOrderNo(userAddrService.orderSequence());//获取订单编号
				info.setTakeOrderTime(order.getSendOrderTime());//发件时间
				info.setSendOrderTime(order.getSendOrderTime());//发件时间
				info.setStatus(3);
				String goodPrice = order.getGoodPrice();
				if(!StringUtil.isEmptyString(goodPrice)){
					info.setCod(1);
					info.setGoodPrice(Float.valueOf(goodPrice));
				}else{
					info.setCod(0);
					info.setGoodPrice(0.00f);	
				}
				info.setSendCourierNo(userAddrService. findCourierNo(order.getSendCourierNo()));
				info.setTakeCourierNo(userAddrService. findCourierNo(order.getTakeCourierNo()));
				float vpay = 0.00f;		
				float goodValuation=0.00f;
				if(!StringUtil.isEmptyString(order.getGoodValuation())){				
					Map<String,Object> cpRate=userAddrService. findCpayRate();
					float rate =Float.valueOf(String.valueOf(cpRate.get("rate")));
					goodValuation =Float.valueOf(order.getGoodValuation());
					vpay =  rate*goodValuation/1000f;
					info.setGoodValuation(goodValuation);
					info.setVpay(vpay);
					info.setGoodValuationRate(String.valueOf(cpRate.get("rate")));
				}else{
					info.setGoodValuation(goodValuation);
					info.setVpay(vpay);
					info.setGoodValuationRate("");
				}
				info.setSendPhone(params.get("sendPhone"));
				info.setSendAddr(params.get("sendArea")+params.get("sendAddr"));
				info.setSendArea(params.get("sendArea"));
				info.setSendName(params.get("sendName"));
				info.setRevPhone(order.getRevPhone());
				info.setRevAddr(order.getRevAddr());
				info.setRevName(order.getRevName());
				float freight = 0.00f;
				if(!StringUtil.isEmptyString(order.getMonthNo())){
					freight = StringUtil.isEmptyString(order.getFreight())?0.00f:Float.valueOf(order.getFreight());
					info.setMpay(vpay+freight);	
					info.setMonthSettleNo(order.getMonthNo());										
				}
				info.setFreight(freight);
				info.setFreightType(Check.getType(order.getFreightType()));
				info.setPayType("MONTH");
				info.setOrderNote(order.getOrderNote());
				info.setSource("BATCH");//批量下单
				info.setItemStatus(order.getItemStatus());
				info.setItemWeight(StringUtil.isEmptyString(order.getItemWeight())?0.00f:Float.valueOf(order.getItemWeight()));
				if("1".equals(info.getFreightType())){
					info.setTnpay(String.valueOf(freight+info.getVpay()));
					if(info.getCod()==1){
						info.setSnpay(StringUtil.isEmptyString(goodPrice)?"0":String.valueOf(goodPrice));
					}else{
						info.setSnpay("0");
					}			
				}else{
					info.setTnpay("0");
					if(info.getCod()==1){
						info.setSnpay(StringUtil.isEmptyString(freight+info.getVpay()+Float.valueOf(goodPrice))?"0":String.valueOf(freight+info.getVpay()+Float.valueOf(goodPrice)));
					}else{
						info.setSnpay(StringUtil.isEmptyString(freight+info.getVpay())?"0":String.valueOf(freight+info.getVpay()));
					}			
				}
				info.setPayAcount(freight+info.getVpay()+Float.valueOf(info.getGoodPrice()));
				info.setExamineStatus("PASS");
				orderInfoList.add(info)	;						
			}			
			RunnableUtils run  = new RunnableUtils();
			run.BatchRunble(orderInfoList, userAddrService, bossUser.getRealName(), dynamicDataSourceHolder,dynamicDataSourceHolder.getKey());		
		}
		mmap.put("params", params);

		return 	"batch/ordersuc";
	}

	/**
	 * 删除订单地址
	 *
	 * @param params
	 * @param mmp
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delOrderAddr")
	public Object delOrderAddr(@RequestParam Map<String, String> params, ModelMap mmp, HttpServletRequest request) {
		String paramstr = "";
		try {
			mmp.addAttribute("params", params);
			if (!StringUtil.isEmptyString(request.getSession().getAttribute("addrStr"))) {
				paramstr = (String) request.getSession().getAttribute("addrStr");
			}
			userAddrService.deleteById(params);
		} catch (Exception e) {
			log.error(null, e);
		}
		return "redirect:selectOrderAddr?layout=no&" + paramstr;
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
			Object obj = request.getSession().getAttribute("fileName");
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

				int celSize = 20;
				ExcelReader excelReader = new ExcelReader();
				String[] title = excelReader.readExcelTitle(is);
				System.out.println(title[0].trim()+title[19].trim());
				if (!"寄件时间(年/月/日)".equals(title[0].trim())
						||!"运单号".equals(title[1].trim())
						||!"月结号".equals(title[2].trim())
						|| !"收件员".equals(title[3].trim())
						|| !"派件员".equals(title[4].trim()) 
						|| !"寄件人".equals(title[5].trim()) 
						|| !"寄件电话".equals(title[6].trim()) 
						|| !"寄件地址".equals(title[7].trim()) 
						|| !"收件人".equals(title[8].trim()) 
						|| !"收件电话".equals(title[9].trim())
						|| !"收件地址".equals(title[10].trim()) 
						|| !"物品类型".equals(title[11].trim())
						|| !"来源".equals(title[12].trim())
						|| !"物品重量".equals(title[13].trim())
						|| !"付款人".equals(title[14].trim()) 
						|| !"付款方式".equals(title[15].trim())
						|| !"运费".equals(title[16].trim())
						|| !"代收款金额".equals(title[17].trim())
						|| !"保价金额".equals(title[18].trim())
						|| !"备注".equals(title[19].trim())) {
					result.setErrorCode(1);
					result.setMessage("请下载正确的模版，填写正确的数据");
					outJson(JsonUtil.toJson(result), response);
					return ;
				}	



				InputStream is1 = file.getInputStream();
				String[][] array = excelReader.readExcelContent(is1);

				List<OrderAddr> orderAddrList = new ArrayList<OrderAddr>();
				if (array.length > 0) {
					if (array[0].length == celSize) {
						
						for (int i = 0; i < array.length; i++) {
					
							String[] t = array[i];
							OrderAddr 	addr =new OrderAddr();
							if(StringUtil.isEmptyString(t[0])&&StringUtil.isEmptyString(t[1])&&
									StringUtil.isEmptyString(t[2])&&StringUtil.isEmptyString(t[3])&&
									StringUtil.isEmptyString(t[4])&&StringUtil.isEmptyString(t[5])&&
									StringUtil.isEmptyString(t[6])&&StringUtil.isEmptyString(t[7])&&
									StringUtil.isEmptyString(t[8])&&StringUtil.isEmptyString(t[9])&&
									StringUtil.isEmptyString(t[10])&&StringUtil.isEmptyString(t[11])&&
									StringUtil.isEmptyString(t[12])&&StringUtil.isEmptyString(t[13])&&
									StringUtil.isEmptyString(t[14])&&StringUtil.isEmptyString(t[15])&&
									StringUtil.isEmptyString(t[16])&&StringUtil.isEmptyString(t[17])&&
									StringUtil.isEmptyString(t[18])&&StringUtil.isEmptyString(t[19])){
								continue;
							}			
							System.out.println("第"+i+"条寄件时间为=================="+i+","+t[0]);
							if(StringUtil.isEmptyString(t[0])){
								result.setErrorCode(1);
								result.setMessage("寄件时间为必填选项");
								outJson(JsonUtil.toJson(result), response);
								return;
							}
							addr.setSendOrderTime(t[0]);
							if(!StringUtil.isEmptyString(t[1])){
								params.put("lgcOrderNo", t[1]);
								Map<String,Object> mao = userAddrService.selectLgcOrder(params);
								if(mao!=null){
									result.setErrorCode(1);
									result.setMessage("当前系统已存在运单号"+ t[1]);
									outJson(JsonUtil.toJson(result), response);
									return;
								}
								addr.setLgcOrderNo(t[1]);
							}

							addr.setMonthNo(t[2]);									    	
							addr.setTakeCourierNo(t[3]);								    	
							addr.setSendCourierNo(t[4]);						
							addr.setSendName(t[5]);					
							addr.setSendPhone(t[6]);
							addr.setSendAddr(t[7]);
							addr.setRevName(t[8]);
							addr.setRevPhone(t[9]);
							addr.setRevAddr(t[10]);									
							addr.setItemStatus(t[11]);
							addr.setSource(t[12]);
							addr.setItemWeight(t[13]);
							if(StringUtil.isEmptyString(t[14])){
								result.setErrorCode(1);
								result.setMessage("付款人为必填项");
								outJson(JsonUtil.toJson(result), response);
								return;
							}
							if(!"寄方付".equals(t[14]) && !"收方付".equals(t[14])){
								result.setErrorCode(1);
								result.setMessage("请选择正确的付款人");
								outJson(JsonUtil.toJson(result), response);
								return;
							}
							addr.setFreightType(t[14]);
							addr.setPayType( t[15]);			
							if(StringUtil.isEmptyString(t[16])){
								result.setErrorCode(1);
								result.setMessage("运费为必填项");
								outJson(JsonUtil.toJson(result), response);
								return;
							}
							addr.setFreight(t[16]);
							addr.setGoodPrice(t[17]);							
							addr.setGoodValuation( t[18]);						
							addr.setOrderNote( t[19]);
							addr.setUserName(bossUser.getRealName());

							orderAddrList.add(addr);
						}
						userAddrService.addOrder(orderAddrList);
						result.setErrorCode(0);
						result.setMessage("导入成功");


						request.getSession().setAttribute("fileName", docname);
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


		}


	}


	//快递员每日收件数量
	@RequestMapping(value = {"/export"})
	public void export(@RequestParam Map<String, String> params, HttpServletResponse response, HttpServletRequest request)
			throws SQLException {
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		String serviceName = params.get("serviceName");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		OutputStream os = null;
		try {
			request.setCharacterEncoding("UTF-8");
			os = response.getOutputStream(); // 取得输出流
			response.reset(); // 清空输出流
			response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
			if (!StringUtils.isEmpty(params.get("serviceName"))) {
				if ("batchExport".equals(serviceName)) {
					String fileName = "批量订单导出-"+System.currentTimeMillis()+".xls";
					response.setHeader("Content-disposition", "attachment;filename="
							+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, bossUser);
				}					
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//写EXCEL
	public void exportOrder(OutputStream os, Map<String, String> params, String fileName,BossUser bossUser) throws Exception {
		int row = 1; // 从第三行开始写
		int col = 0; // 从第一列开始写

		List<Map<String,Object>> batchInfo = userAddrService.selectALLUserOrderList(bossUser.getRealName());

		Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream(
				"/template/batchOrder.xls"));
		WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
		WritableSheet ws = wwb.getSheet(0);
		Iterator<Map<String, Object>> it = batchInfo.iterator();
		while (it.hasNext()) {
			Map<String, Object> map = it.next();
			//					ws.addCell(new Label(col++, row, (String) map.get("monthNo")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("sendOrderTime"))?"":DateUtils.formatDate((Date)(map.get("sendOrderTime")),"yyyy-MM-dd")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("lgcOrderNo"))?"":(String)map.get("lgcOrderNo")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("monthNo"))?"":(String)map.get("monthNo")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("takeCourierNo"))?"":(String)map.get("takeCourierNo")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("sendCourierNo"))?"":(String)map.get("sendCourierNo")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("sendName"))?"":(String)map.get("sendName")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("sendPhone"))?"":(String)map.get("sendPhone")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("sendAddr"))?"":(String)map.get("sendAddr")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("revName"))?"":(String)map.get("revName")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("revPhone"))?"":(String)map.get("revPhone")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("revAddr"))?"":(String)map.get("revAddr")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("itemStatus"))?"":(String)map.get("itemStatus")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("source"))?"":(String)map.get("source")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("itemWeight"))?"":(String)map.get("itemWeight")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("freightType"))?"":(String)map.get("freightType")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("payType"))?"":(String)map.get("payType")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("freight"))?"":(String)map.get("freight")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("goodPrice"))?"":(String)map.get("goodPrice")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("goodValuation"))?"":(String)map.get("goodValuation")));
			ws.addCell(new Label(col++, row,StringUtil.isEmptyString(map.get("orderNote"))?"":(String)map.get("orderNote")));		
			row++;
			col = 0;
		}			
		wwb.write();
		wwb.close();
		wb.close();
		os.close();
	}		


	/**
	 * 清空
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cleanOrderAddr")
	public Object cleanOrderAddr(@RequestParam Map<String, String> params, HttpServletRequest request) {

		try {

			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();				
			userAddrService.delByUserName(bossUser.getRealName());
			request.getSession().removeAttribute("fileName");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(null, ex);
		}
		return "redirect:selectOrderAddr?layout=no";
	}
	//
	@RequestMapping(value = "/selectOrderAddr")
	public Object queryOrderAddr(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) {
		try {
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();		
			params.put("userName", bossUser.getRealName());
			PageInfo<Map<String,Object>> pageList =userAddrService.selectOrder(params, getPageInfo(cpage));
			model.put("data", pageList);
			model.put("params", params);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(null, ex);
		}
		return "batch/queryAddr";
	}





	@RequestMapping(value = "/queryRadiaoAddr")
	public  String queryRadiaoAddr(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) {

		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();

		params.put("userNo", bossUser.getUserName());
		params.put("addrType","1" );
		PageInfo<Map<String,Object>> pageList =userAddrService.getAllAddr(params, getPageInfo(cpage));

		model.put("data", pageList);
		model.put("params", params);
		return "batch/query";
	}
	@RequestMapping(value = "/queryByParentId")
	public void findCity(int id, String clickTag, String areaTag, String cid, String cityFlag, String areaFlag, String selectFlag,HttpServletResponse rs) {
		JSONResult result = new JSONResult();
		try {
			List<City> list = cityService.getByIdList(id);
			System.out.println(list.size()+"=========================");
			result.setValue(CityUtil.getCityArea(list, clickTag, areaTag, cid, cityFlag, areaFlag, selectFlag));
			String resStr = JsonUtil.toJson(result);
			outJson(resStr, rs);
		} catch (Exception ex) {

		}

	}
	@RequestMapping(value = "/saveAddr")
	public String saveAddr(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();

		Map<String,Object> addrMap= new HashMap<String, Object>();
		addrMap.put("userNo", bossUser.getRealName());
		addrMap.put("addrType", 1);
		addrMap.put("area", params.get("sendArea"));
		addrMap.put("addr", params.get("sendAddr"));
		addrMap.put("name", params.get("sendName"));
		addrMap.put("phone", params.get("sendPhone"));
		System.out.println(addrMap);
		userAddrService.saveSendAddr(addrMap);

		model.put("params", params);				
		return "redirect:addOrderMuchPage";

	}
	@RequestMapping(value = "/editOrderAddrPage")
	public Object addOrderAddrPage(@RequestParam Map<String, String> params, @RequestParam(defaultValue = "0") int id, ModelMap mmp) {

		try {
			OrderAddr order  =userAddrService.getOrderBean(id);	
			if(!StringUtil.isEmptyString(order.getSendOrderTime())){
				order.setSendOrderTime(order.getSendOrderTime().substring(0, 10));
			}		
			List<Map<String,Object>> itemType = userAddrService.getItmeType();
			mmp.addAttribute("orderAddr", order);
			mmp.addAttribute("itemType", itemType);
			mmp.addAttribute("params", params);
		} catch (Exception ex) {
			log.error(null, ex);
		}
		return "batch/orderAddrAdd";
	}
	/**
	 * 修改信息
	 * @param orderAddr
	 * @param request
	 * @param mmap
	 * @return
	 */
	@RequestMapping(value = "/editOrderAddr")
	public void addOrderAddr(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse rsp,ModelMap mmap) {
		JSONResult result = new JSONResult();
		try {
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			OrderAddr order = new OrderAddr();
			order.setSendOrderTime(params.get("sendOrderTime"));
			order.setMonthNo(params.get("monthNo"));
			order.setTakeCourierNo(params.get("takeCourierNo"));
			order.setSendCourierNo(params.get("sendCourierNo"));
			order.setSendPhone(params.get("sendPhone"));
			order.setSendName(params.get("sendName"));
			order.setSendAddr(params.get("sendAddr"));
			order.setRevPhone(params.get("revPhone"));		
			order.setRevName(params.get("revName"));
			order.setRevAddr(params.get("revAddr"));
			order.setItemStatus(params.get("itemStatus"));
			order.setFreightType(params.get("freightType"));
			order.setFreight(params.get("freight"));
			order.setGoodValuation(params.get("goodValuation"));
			order.setGoodPrice(params.get("goodPrice"));
			order.setPayType(params.get("payType"));
			order.setOrderNote(params.get("orderNote"));
			order.setItemWeight(params.get("itemWeight"));
			order.setLgcOrderNo(params.get("lgcOrderNo"));
			order.setUserName(bossUser.getRealName());
			order.setId(Integer.valueOf(params.get("id")));		
			userAddrService.upOrderInfo(order);	
		} catch (Exception ex) {
			log.error(null, ex);
			result.setErrorCode(1);
			result.setMessage("服务器异常");
		}
		outJson(JsonUtil.toJson(result), rsp);
	}
}

