package com.yogapay.boss.controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.ForCpn;
import com.yogapay.boss.domain.LgcCustomer;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.ForCpnService;
import com.yogapay.boss.service.LgcCustomerService;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;


@Controller
@RequestMapping(value = "/customer")
public class LgcCustomerController extends BaseController {
	private final static Logger log = LoggerFactory.getLogger(LgcCustomerController.class);

         @Resource
         private LgcCustomerService lgcCustomerService ;
         @Resource
         private CourierService courierService ;
		
		@RequestMapping(value = { "/list" })
		public String list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			String endTime = params.get("createTimeEnd") ;
			if (!StringUtils.isEmptyWithTrim(endTime)) {
				params.put("createTimeEnd", endTime+" 23:59:59") ;
			}
			PageInfo<LgcCustomer> list = lgcCustomerService.list(params, getPageInfo(cpage)) ;
			model.put("list", list) ;
			params.put("createTimeEnd", endTime) ;
			model.put("params", params) ;
			return "customer/list";
		}	
		
		
		//用于
		@RequestMapping(value = { "/edit"})
		public String edit(final ModelMap model,
				@RequestParam Map<String, String> params){
			if(params.get("id") != null){
				Integer id = Integer.parseInt(params.get("id"));
				LgcCustomer lgcCustomer = lgcCustomerService.getById(id) ;
				model.put("lgcCustomer", lgcCustomer);
			}
			return "customer/edit";
		}
		
		//用于保存
		@RequestMapping(value = { "/update"})
		public void update(HttpServletResponse response, HttpServletRequest request,LgcCustomer lgcCustomer) throws SQLException{
			int r = 0;
			try {
				if (!StringUtil.isEmptyWithTrim(lgcCustomer.getSubstation_no())) {
					if (lgcCustomer.getSubstation_no().contains("(")) {
						lgcCustomer.setSubstation_no(lgcCustomer.getSubstation_no().substring(0, lgcCustomer.getSubstation_no().indexOf("(")));
					}
				}
				if (!StringUtil.isEmptyWithTrim(lgcCustomer.getCourier_no())) {
					if (lgcCustomer.getCourier_no().contains("(")) {
						lgcCustomer.setCourier_no(lgcCustomer.getCourier_no().substring(0, lgcCustomer.getCourier_no().indexOf("(")));
					}
				   Courier courier = courierService.getCourierByNo(lgcCustomer.getCourier_no()) ;
				    if (courier==null) {
					   outText("快递员编号错误", response);
					   return ;
				    }
				    if (StringUtil.isEmptyWithTrim(lgcCustomer.getSubstation_no())) {
						lgcCustomer.setSubstation_no(courier.getSubstationNo());
					}else {
						if (!lgcCustomer.getSubstation_no().equals(courier.getSubstationNo())) {
							   outText("快递员与所属网点对应不上", response);
							   return ;
						}
					}
				}
				LgcCustomer customer =  lgcCustomerService.getById(lgcCustomer.getId()) ;
				if (customer==null) {
					  outText("信息错误", response);
					   return ;
				}
				if (!customer.getConcat_phone().equals(lgcCustomer.getConcat_phone())) {
					if (lgcCustomerService.isExsit(lgcCustomer.getConcat_phone())) {
						   outText("已存在"+lgcCustomer.getConcat_phone()+"这个客户了哦", response);
						   return ;
					}
				}
				lgcCustomerService.update(lgcCustomer) ;
				r = 1 ;
			} catch (Exception e) {
				e.printStackTrace();
				r = 0;
			}
			outText(r + "", response);
		}
				
				//用于删除
				@RequestMapping(value = {"/del"})
				public void del(@RequestParam Map<String, String> params,
						HttpServletResponse response) throws SQLException {
					if (StringUtil.isEmpty(params.get("id"))) {
						outText("删除失败", response);
					} else {
						lgcCustomerService.delById(Integer.parseInt(params.get("id")));
						outText("1", response);
					}
				}
				
	
				@RequestMapping(value = { "/huifang" })
				public String huifang(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					String endTime = params.get("createTimeEnd") ;
					if (!StringUtils.isEmptyWithTrim(endTime)) {
						params.put("createTimeEnd", endTime+" 23:59:59") ;
					}
					PageInfo<Map<String, Object>> list = lgcCustomerService.huifang(params, getPageInfo(cpage)) ;
					model.put("list", list) ;
					params.put("createTimeEnd", endTime) ;
					model.put("params", params) ;
					return "customer/huifang";
				}	
			
				@RequestMapping(value = { "/hfedit" })
				public String hfedit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					model.put("params", params) ;
					return "customer/hfedit";
				}	
		
				

				//用于保存
				@RequestMapping(value = { "/hfsave"})
				public void hfsave(HttpServletResponse response, HttpServletRequest request,int id,String huifang_text) throws SQLException{
					int r = 0;
					try {
						lgcCustomerService.hfsave(id,huifang_text) ;
						r = 1 ;
					} catch (Exception e) {
						e.printStackTrace();
						r = 0;
					}
					outText(r + "", response);
				}
				
				@RequestMapping(value = { "/report" })
				public String report(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Date nowDate = new Date() ;
					if (!params.containsKey("curDay")) {
						params.put("nowDate", DateUtils.formatDate(nowDate,"yyyy-MM-dd")) ;
					}else {
						params.put("curDayMonth", params.get("curDay").substring(0,7)) ;
					}
					if (!params.containsKey("curMonth")) {
						params.put("curMonth", DateUtils.formatDate(nowDate,"yyyy-MM")) ;
					}
					if (!params.containsKey("lastMonth")) {
						params.put("lastMonth", DateUtils.getLastMonth(nowDate)) ;
					}
					//第一次默认为空
					PageInfo<Map<String, Object>> list = new PageInfo<Map<String,Object>>(null) ;
					if (!StringUtils.isEmptyWithTrim(params.get("ff"))) {
						list = lgcCustomerService.report(params, getPageInfo(cpage)) ;
						for (Map<String, Object> map:list.getList()) {
							long curMonthCount = Long.valueOf(map.get("curMonthCount").toString()) ;
							long lastMonthCount = Long.valueOf(map.get("lastMonthCount").toString()) ;
							long x = curMonthCount - lastMonthCount ;
							long  a  = 0 ;
							if (curMonthCount==0&&lastMonthCount==0) {
								 map.put("qushi", "up") ;
								 map.put("qushival", "0%") ;
							}else {
								if (x>0) {
								    map.put("qushi", "up") ;
								}else {
									x = lastMonthCount - curMonthCount ;
									map.put("qushi", "down") ;
								}
								if (curMonthCount==0||lastMonthCount==0) {
									map.put("qushival", "100%") ;
								}else {
									double x1 = x ;
									double x2 = lastMonthCount ;
									double r = 0 ;
									 r = x1/x2*100 ;	
									 a = (long) Math.ceil(r) ;
									 map.put("qushival", String.format("%.2f",r)+"%") ;
								}
							}
						}
					}else {
						params.put("ff", "1") ;
					}
					model.put("list", list) ;
					model.put("params", params) ;
					return "customer/report";
				}
				
}
