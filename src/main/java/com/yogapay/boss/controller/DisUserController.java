package com.yogapay.boss.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.DisUser;
import com.yogapay.boss.domain.DisUserBalance;
import com.yogapay.boss.domain.DiscountType;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.MatterPro;
import com.yogapay.boss.domain.MatterType;
import com.yogapay.boss.domain.RechargeHistory;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.service.ConsumeHistoryService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.DisUserBalanceService;
import com.yogapay.boss.service.DisUserService;
import com.yogapay.boss.service.DiscountTypeService;
import com.yogapay.boss.service.MatterProService;
import com.yogapay.boss.service.MatterTypeService;
import com.yogapay.boss.service.MatterWarehouseEnterService;
import com.yogapay.boss.service.RechargeHistoryService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.ExcelUtil;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.StringUtils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping(value = "/disuser")
public class DisUserController extends BaseController {
	private final static Logger log = LoggerFactory.getLogger(DisUserController.class);
	
  
	@Resource
	private DisUserService disUserService ;
	@Resource
	private DiscountTypeService discountTypeService ;
	@Resource
	private CourierService courierService ;
	
	@Resource
	private DisUserBalanceService disUserBalanceService ;
	@Resource
	private RechargeHistoryService rechargeHistoryService ;
	@Resource
	private ConsumeHistoryService consumeHistoryService ;
	@Resource
	private SubstationService substationService;

	
	//用于编辑、新增
	@RequestMapping(value = { "/pro_edit"})
	public String pro_edit(final ModelMap model,@RequestParam Map<String, String> params){
		if(params.get("id") != null){
			Integer id = Integer.parseInt(params.get("id"));
			//MatterPro matterPro =  matterProService.getById(id) ;
			//model.put("matterPro", matterPro);
		}
		List<Map<String, Object>> lgcs = userService.getCurrentLgc();
		model.put("lgcs", lgcs) ;
		return "matter/pro_edit";
	}
	
	
	
	//用于保存
	@RequestMapping(value = { "/pro_save"})
	public void prosave(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, String> params,MatterPro matterPro) throws SQLException{
		String r = "1";
		try {/*
			
			String mt = params.get("matterType_") ;
			params.put("name", mt) ;
			MatterType matterType = matterTypeService.getByName(params) ;
			if (matterType==null) {
				matterType = new MatterType() ;
				matterType.setLgcNo(matterPro.getLgcNo());
				matterType.setTypeName(mt);
				matterTypeService.saveMatterPro(matterType) ;
			}
			 matterPro.setMatterType(matterType.getId());
			 
			if(matterPro.getId() == null){
				 MatterPro mPro = matterProService.getByNo(matterPro.getMatterNo(), matterPro.getLgcNo()) ;
				 if (mPro!=null) {
					r = "物料编号已经存在" ;
				}else {
					mPro = matterProService.getByName(matterPro.getMatterName(), matterPro.getLgcNo()) ;
					if (mPro!=null) {
						r = "物料品名已经存在" ;
					}else {
						  matterProService.saveMatterPro(matterPro) ;
					}
				}
			}else{
				  matterProService.updateMatterPro(matterPro) ;
			}
		*/} catch (Exception e) {
			e.printStackTrace();
			r = "数据有误";
		}
		outText(r, response);
	}

    // 用于
	@RequestMapping(value = { "/list" })
	public String list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
		String sno = params.get("substationNo") ;
        params = setSubstationNo(params,request) ;
		String endTime = params.get("createTimeEnd");
		if (!StringUtils.isEmptyWithTrim(endTime)) {
			params.put("createTimeEnd", endTime+" 23:59:59") ;
		}
	    PageInfo<Map<String, Object>> list =disUserService.list(params,getPageInfo(cpage));
	    params.put("substationNo", sno) ;
		model.put("list", list) ;
		params.put("createTimeEnd", endTime) ;
		//setModelSub(model,"Y") ;
		model.put("params", params) ;
		return "disuser/list";
	}	
	

	
	// 禁启用户
	@RequestMapping(value = { "/ustatus" })
	public void ustatus(HttpServletRequest request,HttpServletResponse response, Integer id,Integer s) {
		DisUser disUser = new DisUser() ;
		disUser.setId(id);
		disUser.setStatus(s);
		disUserService.statusUpdate(disUser) ;
		outText("1", response);
	}
	
	// 重置密码
	@RequestMapping(value = { "/ureset" })
	public void ureset(HttpServletRequest request,HttpServletResponse response, Integer id) throws SQLException {
		DisUser disUser = new DisUser() ;
		disUser.setId(id);
		disUser.setPwd(Md5.md5Str("1234"));
		disUserService.cpw(disUser) ;
		outText("1", response);
	}

	
	//用于新增会员
		@RequestMapping(value = { "/uadd"})
		public String uadd(final ModelMap model,@RequestParam Map<String, String> params,DisUser disUser,HttpServletRequest request){
			if (!StringUtils.isEmptyWithTrim(disUser.getDisUserNo())) {
				Date nowDate = new Date() ;
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				BigDecimal rmoney =  new BigDecimal(params.get("rmoney")) ;
				DiscountType discountType = discountTypeService.getByMoney(rmoney) ;
				DisUser dUser = disUserService.getByNo(disUser.getDisUserNo()) ;
				
				if (dUser!=null) {
					if (discountType==null) {
						model.put("msg", "充值失败，找不到适配的优惠类型") ;
						return "disuser/uadd";
					}
					
				 }else {
					 if (discountType==null) {
							model.put("msg", "新增失败，找不到适配的优惠类型") ;
							return "disuser/uadd";
						}
				}
				
				BigDecimal d = new BigDecimal( (discountType.getDiscount()/100.0))  ;
				BigDecimal payDecimal = rmoney.multiply(d) ;
				
				BigDecimal omoney = new BigDecimal("0") ;
				if (!StringUtils.isEmptyWithTrim(params.get("omoney"))) {
					 omoney = new BigDecimal(params.get("omoney")) ;
				}
				String balance = rmoney.add(omoney).toString() ;
				DisUserBalance disUserBalance = new DisUserBalance() ;
				
				disUserBalance.setBalance(balance);
				disUserBalance.setLastUpdateTime(DateUtils.formatDate(nowDate));
				
				boolean add = false ;
				if (dUser!=null) {
					
					if (dUser.getDisType()!=discountType.getId()) {
						dUser.setDisType(discountType.getId());
						disUserService.updateDisUser(dUser) ;
					}
					disUserBalance.setUid(dUser.getId());
					disUserBalanceService.update(disUserBalance) ;   //充值操作
				 }else {
					    add = true ;
						
						disUser.setPwd(Md5.md5Str("1234"));
						disUser.setDisType(discountType.getId());
						disUser.setOperator(bossUser.getRealName());
						disUser.setCreateTime(DateUtils.formatDate(new Date()));
						disUser.setStatus(1);
						disUserService.saveDisUser(disUser) ;
						
						dUser = disUserService.getByNo(disUser.getDisUserNo()) ;
						
						disUserBalance.setUid(dUser.getId());
						disUserBalanceService.save(disUserBalance) ;
				}
				
				RechargeHistory rechargeHistory = new RechargeHistory() ;
				rechargeHistory.setDisUserNo(disUser.getDisUserNo());
				rechargeHistory.setRmoney(params.get("rmoney"));
				rechargeHistory.setOmoney(omoney.toString());
				rechargeHistory.setPayMoney(payDecimal.toString());
				rechargeHistory.setAfBalance(balance);
				rechargeHistory.setStatus("SUCCESS");
				rechargeHistory.setDiscountText(discountType.getName()+"   "+discountType.getDiscountText());
				rechargeHistory.setLied(params.get("lied"));
				if (!StringUtils.isEmptyWithTrim(params.get("gatherNo"))) {
					String gno = params.get("gatherNo") ;
					if (gno.contains(")")) {
						gno = gno.substring(0,gno.indexOf("(")) ;
					}
					rechargeHistory.setGatherNo(gno);
					rechargeHistory.setGatherNoType("C");
				}else {
					rechargeHistory.setGatherNo(bossUser.getUserName());
					rechargeHistory.setGatherNoType("B");
				}
				rechargeHistory.setOperator(bossUser.getRealName());
				
				rechargeHistory.setCreateTime(DateUtils.formatDate(nowDate));
				rechargeHistory.setLastUpdateTime(DateUtils.formatDate(nowDate));
				rechargeHistory.setNote(params.get("note"));
				rechargeHistory.setSource("BOSS");
				rechargeHistoryService.save(rechargeHistory) ;
				
				if (add) {
					model.put("msg", "新增会员成功") ;
				}else {
					model.put("msg", "会员充值成功") ;
				}
			}
			  /* PageInfo<Map<String, Object>> tlist =discountTypeService.list(params,getPageInfo(1,5000));
			   model.put("tlist", tlist.getList());*/
			   setModelSub(model,"N",request) ;
			  // setModelCour(model,"Y",courierService) ;
			return "disuser/uadd";
		}
		
		//用于会员充值
		@RequestMapping(value = { "/urecharge"})
		public String urecharge(final ModelMap model,@RequestParam Map<String, String> params,DisUser disUser){
			if (!StringUtils.isEmptyWithTrim(disUser.getDisUserNo())) {
				disUser = disUserService.getByNo(disUser.getDisUserNo()) ;
				if (disUser==null) {
					model.put("msg", "充值失败，会员不存在") ;
				 }else {
						BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
                        DiscountType discountType1 = discountTypeService.getById(disUser.getDisType()) ;
                        DiscountType discountType2 = discountTypeService.getById(Integer.valueOf(params.get("disType"))) ;
                        if (discountType1.getDiscount()>discountType2.getDiscount()) {   //如果当前充值优惠优于之前，更新
							disUser.setDisType(discountType2.getId());
							disUserService.updateDisUser(disUser) ;
						}
                        Date nowDate = new Date() ;
                        DisUserBalance disUserBalance = new DisUserBalance() ;
						
						BigDecimal rmoney =  new BigDecimal(params.get("rmoney")) ;
						BigDecimal omoney = new BigDecimal("0") ;
						if (!StringUtils.isEmptyWithTrim(params.get("omoney"))) {
							 omoney = new BigDecimal(params.get("omoney")) ;
						}
						
						BigDecimal balance = rmoney.add(omoney) ;  //充值金额
						
						disUserBalance.setUid(disUser.getId());
						disUserBalance.setBalance(balance.toString());
						disUserBalance.setLastUpdateTime(DateUtils.formatDate(nowDate));
						disUserBalanceService.update(disUserBalance) ;   //充值操作
						
						DisUserBalance cur = disUserBalanceService.getByUid(disUser.getId()) ;
						String afBalance = cur.getBalance() ;
						
						RechargeHistory rechargeHistory = new RechargeHistory() ;
						rechargeHistory.setDisUserNo(disUser.getDisUserNo());
						rechargeHistory.setRmoney(params.get("rmoney"));
						rechargeHistory.setOmoney(omoney.toString());
						rechargeHistory.setAfBalance(afBalance);
						rechargeHistory.setStatus("SUCCESS");
						rechargeHistory.setDiscountText(discountType2.getDiscountText());
						rechargeHistory.setLied(params.get("lied"));
						if (!StringUtils.isEmptyWithTrim(params.get("gatherNo"))) {
							String gno = params.get("gatherNo") ;
							if (gno.contains(")")) {
								gno = gno.substring(0,gno.indexOf("(")) ;
							}
							rechargeHistory.setGatherNo(gno);
							rechargeHistory.setGatherNoType("C");
						}else {
							rechargeHistory.setGatherNo(bossUser.getUserName());
							rechargeHistory.setGatherNoType("B");
						}
						rechargeHistory.setOperator(bossUser.getRealName());
						rechargeHistory.setCreateTime(DateUtils.formatDate(nowDate));
						rechargeHistory.setLastUpdateTime(DateUtils.formatDate(nowDate));
						rechargeHistory.setNote(params.get("note"));
						rechargeHistory.setSource("BOSS");
						rechargeHistoryService.save(rechargeHistory) ;
						
				model.put("msg", "充值成功") ;
				}
			}
			 DisUser duser  = null ;
			  if (!StringUtils.isEmptyWithTrim(params.get("userNo"))) {
				  duser  = disUserService.getByNo(params.get("userNo")) ;
				}
			   model.put("duser", duser) ;
			   PageInfo<Map<String, Object>> tlist =discountTypeService.list(params,getPageInfo(1,5000));
			   model.put("tlist", tlist.getList());
			  // setModelCour(model,"Y",courierService) ;
			   model.put("params", params) ;
			return "disuser/urecharge";
		}
		
	    // 用于
		@RequestMapping(value = { "/tlist" })
		public String tlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
		    PageInfo<Map<String, Object>> list =discountTypeService.list(params,getPageInfo(cpage));
			model.put("list", list) ;
			model.put("params", params) ;
			return "disuser/tlist";
		}	
		
		
		@RequestMapping(value = { "/tsave"})
		public void tsave(HttpServletResponse response, HttpServletRequest request,DiscountType discountType) throws SQLException{
			String r = "1";
			discountType.setDiscountText(discountType.getDiscount()/10.0+"折");
			try {
				if(discountType.getId() == null){
				  discountTypeService.saveDiscountType(discountType) ;
				}else{
					discountTypeService.updateDiscountType(discountType) ;
				}
			} catch (Exception e) {
				e.printStackTrace();
				r = "保存失败";
			}
			outText(r , response);
		}
		
		
		   // 用于
				@RequestMapping(value = { "/tedit" })
				public String tadd(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
					DiscountType discountType = null ;
				  if (!StringUtils.isEmptyWithTrim(params.get("id"))) {
					   discountType = discountTypeService.getById(Integer.valueOf(params.get("id"))) ;
 				   }
				    model.put("discountType", discountType) ;
					model.put("params", params) ;
					return "disuser/tedit";
				}
		
		// 用于充值流水
		@RequestMapping(value = { "/rlist" })
		public String rlist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
					String sno = params.get("substationNo") ;
			        params = setSubstationNo(params,request) ;
				    PageInfo<Map<String, Object>> list =rechargeHistoryService.list(params,getPageInfo(cpage));
					model.put("list", list) ;
					params.put("substationNo", sno) ;
					//setModelSub(model,"Y") ;
					//setModelCour(model,"Y",courierService) ;
					model.put("params", params) ;
					return "disuser/rlist";
		}	
		
		  // 用于扣款流水
		@RequestMapping(value = { "/clist" })
		public String clist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
								HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
							String sno = params.get("substationNo") ;
							
							 String cno = params.get("courierNo") ;
							 String s=cno ;
							 if (StringUtils.nullString(s).contains("(")) {
									s = s.substring(0, s.indexOf("("));
									params.put("courierNo", s) ;
							 }
					        params = setSubstationNo(params,request) ;
						    PageInfo<Map<String, Object>> list =consumeHistoryService.list(params,getPageInfo(cpage));
							model.put("list", list) ;
							params.put("substationNo", sno) ;
							params.put("courierNo", cno) ;
							//setModelSub(model,"Y") ;
							//setModelCour(model,"Y",courierService) ;
							model.put("params", params) ;
							return "disuser/clist";
			}	
		
		
		  // 用于充值异步校验会员
		@RequestMapping(value = { "/info" })
		public void calllist(@RequestParam Map<String, String> params, HttpServletRequest request,HttpServletResponse response) throws IOException {
			DisUser disUser = null ;
		     if (!StringUtils.isEmptyWithTrim(params.get("disUserNo"))) {
				disUser = disUserService.getByNo(params.get("disUserNo")) ;
			 }
			outJson(JSONObject.toJSONString(disUser), response);
		}
		
		  // 
			@RequestMapping(value = { "/dis_type" })
			public void dis_type(@RequestParam Map<String, String> params, HttpServletRequest request,HttpServletResponse response) throws IOException {
				Map<String, Object> map = null ;
			     if (!StringUtils.isEmptyWithTrim(params.get("rmoney"))) {
			    	 try {
						BigDecimal money = new BigDecimal(params.get("rmoney")) ;
						DiscountType discountType = discountTypeService.getByMoney(money) ;
						if (discountType!=null) {
							BigDecimal d = new BigDecimal( (discountType.getDiscount()/100.0))  ;
							BigDecimal payDecimal = money.multiply(d) ;
							map = new HashMap<String, Object>() ;
							map.put("disText", discountType.getName()+"   "+discountType.getDiscountText()) ;
							map.put("payMoney", String.format("%.2f",payDecimal)) ;
						}
						outJson(JSONObject.toJSONString(map), response); 
					} catch (Exception e) {
						e.printStackTrace();
						outJson(JSONObject.toJSONString(null), response); 
					}
				 }
				
			}


		
    //用于编辑会员
 	@RequestMapping(value = { "/uedit"})
	public String uedit(final ModelMap model,@RequestParam Map<String, String> params,DisUser disUser){
				   disUser = disUserService.getById(disUser.getId()) ;
				   PageInfo<Map<String, Object>> tlist =discountTypeService.list(params,getPageInfo(1,5000));
				   model.put("tlist", tlist.getList());
                    model.put("disUser", disUser) ;
					return "disuser/uedit";
				}
 	
 	@RequestMapping(value = { "/udetail"})
	public String udetail(final ModelMap model,@RequestParam Map<String, String> params,Integer id){
				   Map<String , Object> disUser =   disUserService.getDetailById(id) ;
                    model.put("disUser", disUser) ;
					return "disuser/udetail";
				}
 	
 	 	@RequestMapping(value = { "/usave"})
 		public void usave(final ModelMap model,HttpServletResponse response, @RequestParam Map<String, String> params,DisUser disUser){
 	 		    try {
					
				
 	 		      String s = params.get("substationNo") ;
			       if (StringUtils.nullString(s).contains("(")) {
					s = s.substring(0, s.indexOf("("));
			     }
			        if (substationService.getSubstationByNo(s)==null) {
			    	 outText("分站编号错误", response);
			    	 return ;
				    }
			        disUser.setSubstationNo(s);
 	 		          DisUser dUser = new DisUser() ;
 	 		           dUser = disUserService.getById(disUser.getId()) ;
                       dUser.setDisUserName(disUser.getDisUserName());
                       dUser.setContactName(disUser.getContactName());
                       dUser.setContactPhone(disUser.getContactPhone());
                       dUser.setEmail(disUser.getEmail());
                       dUser.setNote(disUser.getNote());
                       dUser.setSubstationNo(disUser.getSubstationNo());
 					   disUserService.updateDisUser(dUser) ;
 					   disUserService.note(disUser.getId(), disUser.getNote());
                       outText("1", response);
 	 		  } catch (Exception e) {
					e.printStackTrace();
					  outText("保存失败", response);
				}  	   
 		}
 	 	
 	 	/**
 	 	 * 充值流水
 	 	 * @param params
 	 	 * @param response
 	 	 * @param request
 	 	 * @throws SQLException
 	 	 */
 	 	@RequestMapping(value = { "/export1" })
		public void export1(@RequestParam Map<String, String> params,
				HttpServletResponse response, HttpServletRequest request)
				throws SQLException {
 	 		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	        params = setSubstationNo(params,request) ;
	        PageInfo<Map<String, Object>> list =rechargeHistoryService.list(params,getPageInfo(1,50000));
	        List<Map<String, Object>> consumeHistoryList=  list.getList();
	        try {
				request.setCharacterEncoding("UTF-8");
				 OutputStream os = response.getOutputStream();
					response.reset(); // 清空输出流
					response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
					String fileName = "充值流水-" + sdf.format(new Date()) + ".xls";
		            response.setHeader("Content-disposition", "attachment;filename="
		                    + new String(fileName.getBytes("GBK"), "ISO8859-1"));
		            exportLog(consumeHistoryList,fileName,os,1);
			} catch (Exception e) {
				e.printStackTrace();
			}
 	 	}
 	

 	 	/**
 	 	 * 扣款流水
 	 	 * @param params
 	 	 * @param response
 	 	 * @param request
 	 	 * @throws SQLException
 	 	 */
 	 	@RequestMapping(value = { "/export2" })
		public void export2(@RequestParam Map<String, String> params,
				HttpServletResponse response, HttpServletRequest request)
				throws SQLException {
 	 		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	        params = setSubstationNo(params,request) ;
	        String cno = params.get("courierNo") ;
			 String s=cno ;
			 if (StringUtils.nullString(s).contains("(")) {
					s = s.substring(0, s.indexOf("("));
					params.put("courierNo", s) ;
			 }
	        PageInfo<Map<String, Object>> list =consumeHistoryService.list(params,getPageInfo(1,50000));
	        List<Map<String, Object>> consumeHistoryList=  list.getList();
	        try {
				request.setCharacterEncoding("UTF-8");
				 OutputStream os = response.getOutputStream();
					response.reset(); // 清空输出流
					response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
					String fileName = "扣款流水-" + sdf.format(new Date()) + ".xls";
		            response.setHeader("Content-disposition", "attachment;filename="
		                    + new String(fileName.getBytes("GBK"), "ISO8859-1"));
		            exportLog(consumeHistoryList,fileName,os,2);
			} catch (Exception e) {
				e.printStackTrace();
			}
 	 	}
 	
		public void exportLog(List<Map<String, Object>> list,String fileName,OutputStream os,int flag){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					 Workbook wb =null;
					if(flag==1){
						wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/rlog.xls"));
					}else{
						wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/clog.xls"));
					}
	                WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
	    			WritableSheet ws = wwb.getSheet(0);
	    			int row = 1;
	    			int col = 0;
	    			if(flag==1){
	    				Substation  substation  =null;
		    			for (Map<String, Object> map : list) {
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dis_user_no"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dis_user_name"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rmoney"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("discount_text"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("pay_money"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("omoney"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lied")).equals("Y")==true?"是":"否"));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("gather_no_type")).equals("C")==true?map.get("real_name").toString():map.get("gather_no").toString()));
		    				/*substation =substationService.getSubstationByNo(StringUtils.nullString(map.get("substation_no")));
		    				ws.addCell(new Label(col++, row, substation==null?"":substation.getSubstationName()));*/
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("operator"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("create_time"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("note"))));
		    				row++;
		    				col = 0;
		    				substation =null;
						}
	    			}else{
	    				Substation  substation  =null;
	    				for (Map<String, Object> map : list) {
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgc_order_no"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dis_user_no"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("dis_user_name"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("rmoney"))));
		/*    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("omoney"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("discount_text"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lied")).equals("Y")==true?"是":"否"));*/
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("real_name"))));
		    				/*substation =substationService.getSubstationByNo(StringUtils.nullString(map.get("substation_no")));
		    				ws.addCell(new Label(col++, row, substation==null?"":substation.getSubstationName()));*/
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("operator"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("create_time"))));
		    				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("note"))));
		    				row++;
		    				col = 0;
						}
	    			}
	    			wwb.write();
					wwb.close();
					wb.close();
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
}
