package com.yogapay.boss.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.xerces.impl.xpath.regex.REUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.CodRateType;
import com.yogapay.boss.domain.ConsumeHistory;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.CourierCost;
import com.yogapay.boss.domain.CourierSalayPay;
import com.yogapay.boss.domain.DisUser;
import com.yogapay.boss.domain.DisUserBalance;
import com.yogapay.boss.domain.DiscountType;
import com.yogapay.boss.domain.ForwardOrder;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.LgcAddr;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.MonthSettleType;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.OrderTrack;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.enums.PayStatus;
import com.yogapay.boss.enums.TrackStatus;
import com.yogapay.boss.service.CodRateTypeService;
import com.yogapay.boss.service.CodSettleUserService;
import com.yogapay.boss.service.CourierCostRecordService;
import com.yogapay.boss.service.CourierCostService;
import com.yogapay.boss.service.CourierSalayPayService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.ForOrderService;
import com.yogapay.boss.service.LgcConfigService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.MonthSettleService;
import com.yogapay.boss.service.MonthSettleTypeService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.OrderTrackService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.service.WagesService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;
import com.yogapay.boss.utils.WeiXinUtil;

@Controller
@RequestMapping(value = "/wages")
public class WagesController extends BaseController {
			
	   @Resource
		private WagesService wagesService ;	
	   @Resource
	   private CourierService courierService ;
	   @Resource
	   private CourierCostService courierCostService ;
	   @Resource
	   private SequenceService sequenceService ;
	   @Resource
	   private CourierCostRecordService courierCostRecordService ;
	   @Resource
	   private CourierSalayPayService courierSalayPayService;
	   @Resource
	   private LgcConfigService lgcConfigService ;
				 // 用于工资页面
				@RequestMapping(value = { "/list" })
				public String list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					
					Date nowDate = new Date() ;
					if (StringUtils.isEmptyWithTrim(params.get("dateMonth"))) {
						params.put("dateMonth", DateUtils.formatDate(nowDate, "yyyy-MM")) ;
					}
					
					String sno = params.get("substationNo");
					if (StringUtils.nullString(sno).contains("(")) {
						sno = sno.substring(0, sno.indexOf("("));
					}
					params.put("sno", sno);
					
					String cno =  params.get("courierNo");
					if (StringUtils.nullString(cno).contains("(")) {
						cno = cno.substring(0, cno.indexOf("("));
					}
					params.put("cno", cno);
					
					PageInfo<Map<String, Object>> list = new PageInfo<Map<String,Object>>(null) ;
					Map<String, Object> total = new HashMap<String, Object>() ;
					
					if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
						list =  wagesService.list(params, getPageInfo(cpage)) ;
						total = wagesService.listTotal(params) ;
					}else {
						params.put("ff", "1") ;
						params.put("tcDay", "A") ;
					}
					List<Map<String, Object>> nameList =  wagesService.listCostName(new HashMap<String, String>()) ;
					model.put("list", list) ;
					model.put("total", total) ;
					model.put("nameList", nameList) ;
					model.put("params", params) ;
					return "wages/list";
				}				
			

				 // 用于费用录入页面
				@RequestMapping(value = { "/input" })
				public String input(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					Date nowDate = new Date() ;
					if (StringUtils.isEmptyWithTrim(params.get("dateMonth"))) {
						params.put("dateMonth", DateUtils.formatDate(nowDate, "yyyy-MM")) ;
					}
					params.put("costIo", "1") ;
					List<Map<String, Object>> inList =  wagesService.listCostName(params) ;
					params.put("costIo", "-1") ;
					List<Map<String, Object>> outList =  wagesService.listCostName(params) ;
					model.put("inList", inList) ;
					model.put("outList", outList) ;
					model.put("params", params) ;
					return "wages/input";
				}	
				
				

				 // 用于费用录入保存
				@RequestMapping(value = { "/inputSave" })
				public void inputSave(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					String r = "保存失败" ;
					try {
						String dataMonth = params.get("dateMonth") ;
						String courierNo = params.get("courierNo") ;
						
						CourierCost courierCost = new CourierCost() ;
						courierCost.setCost_month(dataMonth);
						courierCost.setCourier_no(courierNo);
						
						Courier courier =  courierService.getCourierByNo(courierNo) ;
						if (courier==null) {
							outText("快递员不存在", response);
							return ;
						}
						if (courierCostService.isExist(courierCost)) {
							outText("此员工本月费用已经提交，请勿重复操作", response);
							return ;
						}
						Date nowDate = new Date() ;
						String op = Constants.getUser().getRealName() ;
						String batchId = sequenceService.getNextVal("cost_batch_id") ;
						List<Map<String, Object>> nameList = wagesService.listCostName(new HashMap<String, String>()) ;
						List<CourierCost> clist = new ArrayList<CourierCost>() ;
						String n = "" ;
						for (Map<String, Object>map:nameList) {
							n = StringUtils.nullString(map.get("name")) ; 
							CourierCost cc = new CourierCost() ;
							cc.setCost_amount(Float.valueOf(StringUtils.nullString(params.get(n), "0")));
							
							if (cc.getCost_amount()<=0.009) {
								continue ;
							 }
							cc.setCost_month(dataMonth);
							cc.setCourier_no(courierNo);
							cc.setBatch_id(batchId);
							cc.setCreate_time(DateUtils.formatDate(nowDate));
							cc.setOperator(op);
							
							cc.setCost_name(n);
							
							clist.add(cc) ;
						}
					courierCostService.batchSave(clist);	
					
					r = "1" ;
					} catch (Exception e) {
						e.printStackTrace();
					}
					outText(r, response);
				}	
				
				

				 // 用于费用录入流水
				@RequestMapping(value = { "/ilist" })
				public String ilist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -3, 0, 0),"yyyy-MM-dd") ;
					String endTime =  DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
						params.put("createTimeBegin", beginTime) ;
					}
					if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
						endTime = params.get("createTimeEnd") ;
					}
					params.put("createTimeEnd", endTime+" 23:59:59") ;
					
					String sno = params.get("substationNo");
					if (StringUtils.nullString(sno).contains("(")) {
						sno = sno.substring(0, sno.indexOf("("));
					}
					params.put("sno", sno);
					
					String cno =  params.get("courierNo");
					if (StringUtils.nullString(cno).contains("(")) {
						cno = cno.substring(0, cno.indexOf("("));
					}
					params.put("cno", cno);
					
					
					PageInfo<Map<String, Object>> list =  courierCostService.list(params, getPageInfo(cpage)) ;
					List<Map<String, Object>> nameList =  wagesService.listCostName(new HashMap<String, String>()) ;
					model.put("list", list) ;
					model.put("nameList", nameList) ;
					params.put("createTimeEnd", endTime) ;
					model.put("params", params) ;
					return "wages/ilist";
				}				
			

				 // 用于费用管理
				@RequestMapping(value = { "/mlist" })
				public String mlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					
					Date nowDate = new Date() ;
					String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -3, 0, 0),"yyyy-MM-dd") ;
					String endTime =  DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
					if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
						params.put("createTimeBegin", beginTime) ;
					}
					if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
						endTime = params.get("createTimeEnd") ;
					}
					params.put("createTimeEnd", endTime+" 23:59:59") ;
					
					String sno = params.get("substationNo");
					if (StringUtils.nullString(sno).contains("(")) {
						sno = sno.substring(0, sno.indexOf("("));
					}
					params.put("sno", sno);
					
					String cno =  params.get("courierNo");
					if (StringUtils.nullString(cno).contains("(")) {
						cno = cno.substring(0, cno.indexOf("("));
					}
					params.put("cno", cno);
					
					
					PageInfo<Map<String, Object>> list =  courierCostService.list(params, getPageInfo(cpage)) ;
					List<Map<String, Object>> nameList =  wagesService.listCostName(new HashMap<String, String>()) ;
					model.put("list", list) ;
					model.put("nameList", nameList) ;
					params.put("createTimeEnd", endTime) ;
					model.put("params", params) ;
					return "wages/mlist";
				}				
	
				 // 用于费用编辑页面
				@RequestMapping(value = { "/edit" })
				public String edit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
			       Map<String, Object> courierCost =	courierCostService.getByBid(params.get("bid")) ;
			       params.put("costIo", "1") ;
					List<Map<String, Object>> inList =  wagesService.listCostName(params) ;
					params.put("costIo", "-1") ;
					List<Map<String, Object>> outList =  wagesService.listCostName(params) ;
					model.put("inList", inList) ;
					model.put("outList", outList) ;
			        model.put("courierCost", courierCost) ;
					return "wages/edit";
				}	
		
				


				 // 用于费用编辑保存
				@RequestMapping(value = { "/editSave" })
				public void editSave(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					String r = "保存失败" ;
					try {
						String dataMonth = params.get("dateMonth") ;
						String courierNo = params.get("courierNo") ;
						
						CourierCost courierCost = new CourierCost() ;
						courierCost.setCost_month(dataMonth);
						courierCost.setCourier_no(courierNo);
						
						Courier courier =  courierService.getCourierByNo(courierNo) ;
						if (courier==null) {
							outText("快递员不存在", response);
							return ;
						}
						
					  String batchId = params.get("batch_id") ;	
					  Map<String, Object> cmap =	courierCostService.getByBid(batchId) ;
						
						Date nowDate = new Date() ;
						String op = Constants.getUser().getRealName() ;
						
						List<Map<String, Object>> nameList = wagesService.listCostName(new HashMap<String, String>()) ;
						List<CourierCost> clist = new ArrayList<CourierCost>() ;
						String n = "" ;
						for (Map<String, Object>map:nameList) {
							n = StringUtils.nullString(map.get("name")) ; 
							CourierCost cc = new CourierCost() ;
							cc.setCost_amount(Float.valueOf(StringUtils.nullString(params.get(n), "0")));
							
							if (cc.getCost_amount()<=0.009) {
								continue ;
							 }
							cc.setCost_month(dataMonth);
							cc.setCourier_no(courierNo);
							cc.setBatch_id(batchId);
							cc.setCreate_time(StringUtils.nullString(cmap.get("create_time")));
							cc.setOperator(StringUtils.nullString(cmap.get("operator")));
							
							cc.setCost_name(n);
							
							clist.add(cc) ;
						}
						
				    courierCostService.deleteByBid(batchId);		
					courierCostService.batchSave(clist);	
					
					String batch_id = sequenceService.getNextVal("cost_batch_id") ;
					for (CourierCost cost:clist) {
						cost.setBatch_id(batch_id);
						cost.setCreate_time(DateUtils.formatDate(nowDate));
						cost.setOperator(op);
					}
					courierCostRecordService.batchSave(clist);
					
					r = "1" ;
					} catch (Exception e) {
						e.printStackTrace();
					}
					outText(r, response);
				}	
				
				 // 用于费用管理流水
					@RequestMapping(value = { "/rlist" })
					public String rlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
							HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
						
						Date nowDate = new Date() ;
						String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -3, 0, 0),"yyyy-MM-dd") ;
						String endTime =  DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
						if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
							params.put("createTimeBegin", beginTime) ;
						}
						if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
							endTime = params.get("createTimeEnd") ;
						}
						params.put("createTimeEnd", endTime+" 23:59:59") ;
						
						String sno = params.get("substationNo");
						if (StringUtils.nullString(sno).contains("(")) {
							sno = sno.substring(0, sno.indexOf("("));
						}
						params.put("sno", sno);
						
						String cno =  params.get("courierNo");
						if (StringUtils.nullString(cno).contains("(")) {
							cno = cno.substring(0, cno.indexOf("("));
						}
						params.put("cno", cno);
						
						
						PageInfo<Map<String, Object>> list =  courierCostRecordService.list(params, getPageInfo(cpage)) ;
						List<Map<String, Object>> nameList =  wagesService.listCostName(new HashMap<String, String>()) ;
						model.put("list", list) ;
						model.put("nameList", nameList) ;
						params.put("createTimeEnd", endTime) ;
						model.put("params", params) ;
						return "wages/rlist";
					}	
					
					@RequestMapping(value = { "/payOff" })
					public void payOff(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
							HttpServletResponse response){
						String dateMonth = params.get("dateMonth");
						String tcDay = params.get("tcDay");
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
						Date curDate =new Date();
						if(dateMonth==null||"".equals(dateMonth)){
							outText("日期错误", response);
							return;
						}
						if(Integer.parseInt(sdf.format(curDate).replace("-", ""))<=Integer.parseInt(dateMonth.replace("-", ""))){
							outText("不能提前发放当月或以后的工资", response);
							return;
						}
						
						String cno =  params.get("courierNo");
						if (cno==null||"".equals(cno)) {
							outText("快递员编号不能为空", response);
							return;
						}
						//检查是否已经发工资
						params.put("courierNo", cno);
						params.put("costMonth", dateMonth);
						List<CourierSalayPay> payList = courierSalayPayService.query(params);
						if(payList!=null&&payList.size()>0){
							outText("该快递员已经发过工资", response);
							return;
						}
						
						params.put("cno", cno);
						double salary = 0;
						
						PageInfo<Map<String, Object>> list =  wagesService.list(params, getPageInfo(1,20)) ;
						List<Map<String, Object>> olist = list.getList();
						if(olist!=null&&olist.size()!=0){//单个快递员提成
							Map<String, Object> m = olist.get(0);
							salary+=Double.parseDouble(StringUtils.nullString(m.get("take_sum")));
							salary+=Double.parseDouble(StringUtils.nullString(m.get("send_sum")));
							salary+=Double.parseDouble(StringUtils.nullString(m.get("for_sum")));
						}
						params.put("cost_month", dateMonth);
						params.put("courier_no", cno);
						//工资奖励
						List<Map<String,Object>> sList = courierSalayPayService.queryCountSalayPay(params);
						Map<String,Object> cost = sList.get(0);
						if(sList==null||sList.size()==0||cost==null){
							outText("请录入快递员费用", response);
							return;
						}
						String salaryStr = StringUtils.nullString(cost.get("salary"));
						if(salaryStr==null || "".equals(salaryStr)){
							outText("快递员费用错误，请检查是否录入该快递员该月费用", response);
							return;
						}
						salary+=Double.parseDouble(salaryStr);
						CourierSalayPay cpay = new CourierSalayPay();
						int flag=1;
						if(!"".equals(tcDay)&&tcDay!=null){
							flag=1;
						}else{
							flag=2;
						}
						cpay.setCostAmout(new BigDecimal(salary));
						cpay.setCostMonth(dateMonth);
						cpay.setCourierNo(cno);
						cpay.setCourierTcWay(flag);
						cpay.setCrateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(curDate));
						courierSalayPayService.insert(cpay);
						
						outText("操作成功", response);
						return;
						
					}
					
					
					@RequestMapping(value = { "/batchPayOff" })
					public void batchPayOff(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
							HttpServletResponse response){
						String dateMonth = params.get("dateMonth");
						String tcDay = params.get("tcDay");
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
						Date curDate =new Date();
						if(dateMonth==null||"".equals(dateMonth)){
							outText("日期错误", response);
							return;
						}
						if(Integer.parseInt(sdf.format(curDate).replace("-", ""))<=Integer.parseInt(dateMonth.replace("-", ""))){
							outText("不能提前发放当月或以后的工资", response);
							return;
						}
						
						String cno =  params.get("courierNo");
						if(cno.endsWith(",")){
							cno =cno.substring(0, cno.length()-1);
						}
						if (cno==null||"".equals(cno.trim())) {
							outText("请至少选择一个快递员", response);
							return;
						}
						String[] courierNo =  cno.split(",");
						String ids ="";
						List<String> courierNos = new ArrayList<String>();
						for (int i = 0; i < courierNo.length; i++) {
							ids+="'"+courierNo[i]+"'"+",";
							courierNos.add(courierNo[i]);
						}
						if(ids.endsWith(",")){
							ids =ids.substring(0, ids.length()-1);
						}
						
						//检查是否已经发工资,去除发过的人
						params.put("courierNo", ids);
						params.put("costMonth", dateMonth);
						List<CourierSalayPay> payList = courierSalayPayService.batchQuery(params);
						for (CourierSalayPay courierSalayPay : payList) {
							if(courierNos.contains(courierSalayPay.getCourierNo())){
								courierNos.remove(courierSalayPay.getCourierNo());
							}
						}
						
						double salary = 0;
						String errNo ="";
						for (String c : courierNos) {
							params.put("cno", c);
							PageInfo<Map<String, Object>> list =  wagesService.list(params, getPageInfo(1,20)) ;
							List<Map<String, Object>> olist = list.getList();
							if(olist!=null&&olist.size()!=0){//单个快递员提成
								Map<String, Object> m = olist.get(0);
								salary+=Double.parseDouble(StringUtils.nullString(m.get("take_sum")));
								salary+=Double.parseDouble(StringUtils.nullString(m.get("send_sum")));
								salary+=Double.parseDouble(StringUtils.nullString(m.get("for_sum")));
							}
							params.put("cost_month", dateMonth);
							params.put("courier_no", c);
							//工资奖励
							List<Map<String,Object>> sList = courierSalayPayService.queryCountSalayPay(params);
							Map<String,Object> cost = sList.get(0);
							if(sList==null||sList.size()==0||cost==null){
								errNo =errNo+ c+",";
								continue;
							}
							String salaryStr = StringUtils.nullString(cost.get("salary"));
							if(salaryStr==null || "".equals(salaryStr)){
								errNo =errNo+ c+",";
								continue;
							}
							salary+=Double.parseDouble(salaryStr);
							CourierSalayPay cpay = new CourierSalayPay();
							int flag=1;
							if(!"".equals(tcDay)&&tcDay!=null){
								flag=1;
							}else{
								flag=2;
							}
							cpay.setCostAmout(new BigDecimal(salary));
							cpay.setCostMonth(dateMonth);
							cpay.setCourierNo(c);
							cpay.setCourierTcWay(flag);
							cpay.setCrateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(curDate));
							courierSalayPayService.insert(cpay);
							salary=0;
						}
						if("".equals(errNo)){
							outText("操作成功", response);
						}else{
							outText("快递员"+errNo+"未成功发放工资，请录入花费", response);
						}					
					}
	
					
					
					
					
					  // 用于必填项列表
					@RequestMapping(value = { "/base" })
					public String base(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
							HttpServletResponse response,Integer id) throws IOException {
						   model.put("params", params) ;
							Map<String, Object> config = lgcConfigService.getByType("WAGES_BASE") ;
							model.put("configMap", config);
							return "wages/base";
					}							
					
					@RequestMapping(value = {"/wages_base_save"})
					public void wages_base_save(@RequestParam Map<String, String> params,
							HttpServletResponse response)  {
						try {
								lgcConfigService.updateByTypeName("WAGES_BASE", "FREIGHT", params.get("FREIGHT"));
								lgcConfigService.updateByTypeName("WAGES_BASE", "WEIGHT", params.get("WEIGHT"));
								lgcConfigService.updateByTypeName("WAGES_BASE", "COUNT", params.get("COUNT"));
								lgcConfigService.updateByTypeName("WAGES_BASE", "COURIER_STATUS", params.get("COURIER_STATUS"));
							outText("1", response);
						} catch (Exception e) {
							outText("保存失败", response);
						}
						
					}		
					
					
					
}
