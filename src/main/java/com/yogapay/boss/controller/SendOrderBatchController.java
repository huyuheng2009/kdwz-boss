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

import org.apache.commons.httpclient.util.DateUtil;
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
import com.yogapay.boss.domain.SendFileInput;
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
@RequestMapping(value="/sendBatch")
public class SendOrderBatchController extends BaseController{
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
		PageInfo<Map<String, Object>> list = userAddrService.selectSendFile(getPageInfo(cpage),bossUser.getRealName());			
		model.put("list", list);			
		return "orderBatch/sendBatchOrder";
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
			Object obj = request.getSession().getAttribute("fileName2");
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

				int celSize = 5;
				ExcelReader excelReader = new ExcelReader();
				String[] title = excelReader.readExcelTitle(is);
				if (!"运单编号".equals(title[0].trim())
						||!"签收时间".equals(title[1].trim())
						||!"签收人".equals(title[2].trim())
						|| !"派件员内部编号".equals(title[3].trim())
						|| !"派件网点".equals(title[4].trim()) 
					) {
					result.setErrorCode(1);
					result.setMessage("请下载正确的模版，填写正确的数据");
					outJson(JsonUtil.toJson(result), response);
					return ;
				}	
				InputStream is1 = file.getInputStream();
				String[][] array = excelReader.readExcelContent(is1);
				System.out.println(array.length);

				List<SendFileInput> SendInputFileList = new ArrayList<SendFileInput>();
				if (array.length > 0) {
					if (array[0].length == celSize) {

						for (int i = 0; i < array.length; i++) {

							String[] t = array[i];
							SendFileInput 	inputFile =new SendFileInput();
							if(StringUtil.isEmptyString(t[0])&&StringUtil.isEmptyString(t[1])&&
									StringUtil.isEmptyString(t[2])&&StringUtil.isEmptyString(t[3])&&
									StringUtil.isEmptyString(t[4])){
								continue;
							}			
							if(!StringUtils.isEmptyWithTrim(t[0])){
								Map<String,Object>   mapUser = new HashMap<String,Object>();
								mapUser.put("userNo", bossUser.getRealName());
								mapUser.put("orderNo",t[0]);
								if(userAddrService.sendNoCheck(mapUser)){
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
							if(StringUtils.isEmptyWithTrim(t[1])){
								result.setErrorCode(1);
								result.setMessage("派件日期不能为空");
								outJson(JsonUtil.toJson(result), response);
								return ;		
								
							}
							System.out.println("================"+t[1]);
							inputFile.setSendTime(DateUtils.formatDate(t[1],"yyyy-MM-dd"));
							System.out.println(inputFile.getSendTime()+"11111111111111111111");
							inputFile.setSendName(t[2]);
							if(StringUtils.isEmptyWithTrim(t[3])){
								result.setErrorCode(1);
								result.setMessage("派件员内部编号不能为空");
								outJson(JsonUtil.toJson(result), response);
								return ;		
								
							}
							inputFile.setSendCourierNo(t[3]);
							if(StringUtils.isEmptyWithTrim(t[4])){
								result.setErrorCode(1);
								result.setMessage("派件分站不能为空");
								outJson(JsonUtil.toJson(result), response);
								return ;								
							}
							String subNo = userAddrService.findSubNo(t[4]);
							if(StringUtils.isEmptyWithTrim(subNo)){
								result.setErrorCode(1);
								result.setMessage("派件分站"+t[4]+"不存在,请检查");
								outJson(JsonUtil.toJson(result), response);
								return ;		
							}						
							inputFile.setSendSubNo(t[4]);		
							inputFile.setUserNo(bossUser.getRealName());
							SendInputFileList.add(inputFile);					
						}



						Map<String,Object> mao = new HashMap<String,Object>();
						mao.put("list", SendInputFileList);				
						userAddrService.addSendFileInput(mao);
						result.setErrorCode(0);
						result.setMessage("导入成功,请稍后刷新页面");
						request.getSession().setAttribute("fileName2", docname);
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
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setMessage("请检查导入模版是否正确，时间格式如2015-12-12或2015/12/12");
			outJson(JsonUtil.toJson(result), response);
			e.printStackTrace();
			return;
			
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
		userAddrService.deleteAllSendInfo(bossUser.getRealName());
		request.getSession().removeAttribute("fileName2");
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
	System.out.println("===========sendBatch/addOrderMuch===============");
		mmap.put("params", params);
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		Date nowDate= new Date();
		List<SendFileInput> batchInfo = userAddrService.getAllSendUserOrderList(bossUser.getRealName());
		if (batchInfo.size() <= 0) {
			mmap.put("msg", "请添加签收件信息");
			return "forward:batchOrder";
		}
		List<OrderInfo> batchList = new ArrayList<OrderInfo>();
		for(SendFileInput sfi:batchInfo){
			OrderInfo order    = userAddrService.getBeanOrderInfo(sfi);
			if(order==null){
				mmap.put("msg", "运单"+sfi.getOrderNo()+"不存在,请检查！");
				return "forward:batchOrder";
			}
			order.setLgcOrderNo(sfi.getOrderNo());
			order.setSignName(sfi.getSendName());
			order.setUserNo(bossUser.getRealName());
			order.setSign("已签收");
			order.setSendOrderTime(sfi.getSendTime());
			order.setLastUpdateTime(sfi.getSendTime());
			String courierNo = userAddrService.findCourierNoByInnerNo(sfi.getSendCourierNo());
			if(StringUtil.isEmptyString(courierNo)){
				mmap.put("msg", "快递员内部编号"+sfi.getSendCourierNo()+"不存在,请检查！");
				return "forward:batchOrder";
			}		
			order.setSendCourierNo(courierNo);
			
			String subNo = userAddrService.findSubNo(sfi.getSendSubNo());
			if(StringUtil.isEmptyString(courierNo)){
				mmap.put("msg", "分站"+sfi.getSendSubNo()+"不存在,请检查！");
				return "forward:batchOrder";
			}		
			order.setSendSubstationNo(subNo);
			order.setStatus(3);
			order.setInputTime(DateUtil.formatDate(nowDate,"yyyy-MM-dd HH:mm:ss"));
			batchList.add(order);			
		}
			Map<String,Object> map  =new HashMap<String,Object>();
			map.put("list", batchList);
			LgcConfig lgcConfig = (LgcConfig)request.getSession().getAttribute("lgcConfig") ;	
			String key = "yx" ;
			if (lgcConfig!=null) {
				key = lgcConfig.getKey() ;
			}
		RunnableUtils run  = new RunnableUtils();
		run.SendBatchOrder(map, userAddrService, bossUser.getRealName(), dynamicDataSourceHolder, key);

		return 	"orderBatch/success2";
	}


}
