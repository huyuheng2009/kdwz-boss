package com.yogapay.boss.controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.domain.SubstationAccount;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.SubstationAccountService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

@Controller
@RequestMapping(value = "/gather")
public class GatherController extends BaseController {
	private final static Logger log = LoggerFactory.getLogger(GatherController.class);
	
	@Resource
	private UserService userService ;
	@Resource
	private CourierService courierService ;
	@Resource
	private MobileUserService mobileUserService ;
	@Resource
	private SubstationService substationService ;
	@Resource
	private SubstationAccountService substationAccountService ;
	


	//用于网点录入
	@RequestMapping(value = { "/sinput"})
	public String sinput(final ModelMap model,@RequestParam Map<String, String> params,HttpServletRequest request) throws SQLException{
		params.put("lgcNo", Constants.getUser().getLgcNo()) ;
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			params.put("substationNo", substationNo);
		}
		/*PageInfo<Map<String, Object>> mList = mobileUserService.muserList(params,getPageInfo(1,5000)) ;
		if (mList!=null) {
			model.put("mlist", JsonUtil.toJson(mList.getList())) ;
		}else {
			model.put("mlist",mList) ;
		}*/
		/*PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(1,5000));
		List<Map<String, Object>> substations = userService.getCurrentSubstation();
		model.put("clist", JsonUtil.toJson(courierList.getList())) ;
		model.put("slist", JsonUtil.toJson(substations)) ;*/
		return "gather/sinput";
	}
	
	
	
	 // 用于录入保存
	@RequestMapping(value = { "/ssave" })
	public void ssave(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,SubstationAccount substationAccount) throws IOException {
		String r = "1" ;
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		Courier c = new Courier() ;
		c.setCourierNo(substationAccount.getCourierNo());
		Courier courier = courierService.getCourierByNo(c) ;
		if (courier==null) {
			r = "快递员输入错误" ;
		}
		Substation substation = substationService.getSubstationByNo(params.get("substationNo")) ;
		if (courier==null) {
			r = "分站输入错误" ;
		}
		Map<String, Object> map = null ;
		if (!StringUtils.isEmptyWithTrim(params.get("monthNo"))) {
			 map =  mobileUserService.getMuserByNo(params.get("monthNo")) ;
			if (map==null) {
				r = "月结号错误" ;
			}
		}
		
		if ("1".equals(r)) {
			if (courier.getSubstationNo().equals(substation.getSubstationNo())) {
				
				if (StringUtils.isEmptyWithTrim(substationAccount.getSettleTime())) {
					substationAccount.setSettleTime(null);
				}
				if (!StringUtils.isEmptyWithTrim(params.get("monthNo"))) {
					substationAccount.setMonthNo(map.get("month_no").toString());
				}else {
					substationAccount.setMcount(0);
					substationAccount.setMtype(null);
					substationAccount.setMsettleDate(null);
					substationAccount.setMcontact(null);
					substationAccount.setMphome(null);
					substationAccount.setMemail(null);
					substationAccount.setLiced(null);
				}
				float c1 = substationAccount.getFcount()+substationAccount.getCcount()+substationAccount.getOcount()+substationAccount.getHcount() ;
				float m1 = substationAccount.getMcount() ;
				if (c1>0&&m1>0) {
					substationAccount.setRtype("CM");
				}else {
					if (m1>0) {
						substationAccount.setRtype("M");
					}else {
						substationAccount.setRtype("C");
					}
				}
				substationAccount.setAcount(c1+m1);
				substationAccount.setCreateTime(DateUtils.formatDate(new Date()));
				substationAccount.setOperator(bossUser.getUserName());
				substationAccount.setExamineStatus(0);
				substationAccountService.saveSubstationAccount(substationAccount) ;
				r = "1" ;
			}else {
				r = "快递员不属于分站" ;
			}
		}
		 outText(r, response);
	}
	

	
    // 用于
	@RequestMapping(value = { "/list" })
	public String list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
		Date nowDate = new Date() ;
		String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -3, 0, 0),"yyyy-MM-dd") ;
		String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
		if (StringUtils.isEmptyWithTrim(params.get("settleTimeBegin"))) {
			params.put("settleTimeBegin", beginTime) ;
		}
		if (!StringUtils.isEmptyWithTrim(params.get("settleTimeEnd"))) {
			endTime = params.get("settleTimeEnd") ;
		}
		params.put("settleTimeEnd", endTime+" 23:59:59") ;
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		String substationNo1 = params.get("substationNo") ;
		String substationNo ;
		if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
			 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
		}else {
			substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
		}
		if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
			/*String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}*/
			params.put("substationNo", substationNo);
		}else {
			if (!substationNo.contains(params.get("substationNo"))) {
				params.put("substationNo", "0000") ;
			}
		}
		PageInfo<Map<String, Object>> list = substationAccountService.list(params, getPageInfo(cpage)) ;
		/*PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(1,5000));
		List<Map<String, Object>> substations = userService.getCurrentSubstation();
		model.put("clist", JsonUtil.toJson(courierList.getList())) ;
		model.put("slist", JsonUtil.toJson(substations)) ;*/
		model.put("list", list) ;
		params.put("substationNo", substationNo1) ;
		params.put("settleTimeEnd", endTime) ;
		model.put("params", params) ;
		return "gather/list";
	}	
	
	
	 // 用于网点登记
		@RequestMapping(value = { "/slist" })
		public String slist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
			/*Date nowDate = new Date() ;
			if (StringUtils.isEmptyWithTrim(params.get("settleTime"))) {
				String settleTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
			    params.put("settleTime", settleTime) ;
			}*/
			String examineStatus = "0" ;
			params.put("examineStatus", examineStatus) ;
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo1 = params.get("substationNo") ;
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				/*String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}*/
				params.put("substationNo", substationNo);
			}else {
				if (!substationNo.contains(params.get("substationNo"))) {
					params.put("substationNo", "0000") ;
				}
			}
			/*PageInfo<Map<String, Object>> mList = mobileUserService.muserList(params,getPageInfo(1,5000)) ;
			if (mList!=null) {
				model.put("mlist", JsonUtil.toJson(mList.getList())) ;
			}else {
				model.put("mlist",mList) ;
			}*/
			PageInfo<Map<String, Object>> list = substationAccountService.list(params, getPageInfo(cpage)) ;
		/*	PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(1,5000));
			List<Map<String, Object>> substations = userService.getCurrentSubstation();
			model.put("clist", JsonUtil.toJson(courierList.getList())) ;
			model.put("slist", JsonUtil.toJson(substations)) ;*/
			model.put("list", list) ;
			params.put("substationNo", substationNo1) ;
			model.put("params", params) ;
			return "gather/slist";
		}	
		
		
	//用于往上提交
	@RequestMapping(value = {"/commit"})
	public void commit(@RequestParam Map<String, String> params,
			HttpServletResponse response) throws SQLException {
		if (StringUtil.isEmpty(params.get("ids"))) {
			outText("提交失败", response);
		} else {
			if(!StringUtil.isEmpty(params.get("pass"))){
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				params.put("examineTime", DateUtils.formatDate(new Date())) ;
				params.put("examinePeople", bossUser.getRealName()) ;
			 }else {
				 params.put("examineTime", null) ;
					params.put("examinePeople", null) ;
			}
			substationAccountService.examine(params) ;
			outText("1", response);
		}
	}
		
		 // 用于
		@RequestMapping(value = { "/clist" })
		public String clist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
			/*Date nowDate = new Date() ;
			if (StringUtils.isEmptyWithTrim(params.get("settleTime"))) {
				String settleTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
			    params.put("settleTime", settleTime) ;
			}*/
			String examineStatus = "1" ;
			params.put("examineStatus", examineStatus) ;
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo1 = params.get("substationNo") ;
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				/*String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}*/
				params.put("substationNo", substationNo);
			}else {
				if (!substationNo.contains(params.get("substationNo"))) {
					params.put("substationNo", "0000") ;
				}
			}
		/*	PageInfo<Map<String, Object>> mList = mobileUserService.muserList(params,getPageInfo(1,5000)) ;
			if (mList!=null) {
				model.put("mlist", JsonUtil.toJson(mList.getList())) ;
			}else {
				model.put("mlist",mList) ;
			}*/
			PageInfo<Map<String, Object>> list = substationAccountService.list(params, getPageInfo(cpage)) ;
		/*	PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(1,5000));
			List<Map<String, Object>> substations = userService.getCurrentSubstation();
			model.put("clist", JsonUtil.toJson(courierList.getList())) ;
			model.put("slist", JsonUtil.toJson(substations)) ;*/
			model.put("list", list) ;
			params.put("substationNo", substationNo1) ;
			model.put("params", params) ;
			return "gather/clist";
		}	
		
		
		
		 // 用于快递员收款查询
		@RequestMapping(value = { "/cshow" })
		public String cshow(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
			Date nowDate = new Date() ;
			if (StringUtils.isEmptyWithTrim(params.get("settleTime"))) {
				String settleTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
			    params.put("settleTime", settleTime) ;
			}
			String examineStatus = "2" ;
			params.put("examineStatus", examineStatus) ;
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo1 = params.get("substationNo") ;
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				
				params.put("substationNo", substationNo);
			}else {
				if (!substationNo.contains(params.get("substationNo"))) {
					params.put("substationNo", "0000") ;
				}
			}
			PageInfo<Map<String, Object>> list = substationAccountService.list(params, getPageInfo(cpage)) ;
			PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(1,5000));
			List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
			model.put("clist", JsonUtil.toJson(courierList.getList())) ;
			model.put("slist", JsonUtil.toJson(substations)) ;
			model.put("list", list) ;
			params.put("substationNo", substationNo1) ;
			model.put("params", params) ;
			return "gather/cshow";
		}	
		
		
		
		 // 用于快递员收款审核
		@RequestMapping(value = { "/cexam" })
		public String cexam(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
			Date nowDate = new Date() ;
			String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -30, 0, 0),"yyyy-MM-dd") ;
			String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
			if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
				params.put("createTimeBegin", beginTime) ;
			}
			if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
				endTime = params.get("createTimeEnd") ;
			}
			params.put("createTimeEnd", endTime+" 23:59:59") ;
			String examineStatus = "2,3" ;
			String rtype = "'C','CM'" ;
			if (StringUtils.isEmptyWithTrim(params.get("estatus"))) {
				params.put("examineStatus", examineStatus) ;
			}else {
				params.put("examineStatus", params.get("estatus")) ;
			 }
			params.put("rtype", rtype) ;
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo1 = params.get("substationNo") ;
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
			
				params.put("substationNo", substationNo);
			}else {
				if (!substationNo.contains(params.get("substationNo"))) {
					params.put("substationNo", "0000") ;
				}
			}
			PageInfo<Map<String, Object>> list = substationAccountService.list(params, getPageInfo(cpage)) ;
		/*	PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(1,5000));
			List<Map<String, Object>> substations = userService.getCurrentSubstation();
			model.put("clist", JsonUtil.toJson(courierList.getList())) ;
			model.put("slist", JsonUtil.toJson(substations)) ;*/
			model.put("list", list) ;
			params.put("substationNo", substationNo1) ;
			params.put("createTimeEnd", endTime) ;
			model.put("params", params) ;
			return "gather/cexam";
		}	
		
		
		
		 // 用于
		@RequestMapping(value = { "/mshow" })
		public String mshow(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
			Date nowDate = new Date() ;
			if (StringUtils.isEmptyWithTrim(params.get("settleTime"))) {
				String settleTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -1, 0, 0),"yyyy-MM-dd") ;
			    params.put("settleTime", settleTime) ;
			}
			String examineStatus = "2" ;
			params.put("examineStatus", examineStatus) ;
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo1 = params.get("substationNo") ;
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
			
				params.put("substationNo", substationNo);
			}else {
				if (!substationNo.contains(params.get("substationNo"))) {
					params.put("substationNo", "0000") ;
				}
			}
			PageInfo<Map<String, Object>> list = substationAccountService.list(params, getPageInfo(cpage)) ;
			PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(1,5000));
			List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
			model.put("clist", JsonUtil.toJson(courierList.getList())) ;
			model.put("slist", JsonUtil.toJson(substations)) ;
			model.put("list", list) ;
			params.put("substationNo", substationNo1) ;
			model.put("params", params) ;
			return "gather/mshow";
		}	
		
		 // 用于月结审核列表
		@RequestMapping(value = { "/mexam" })
		public String mexam(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
	
			String examineStatus = "2,3" ;
			String rtype = "'M','CM'" ;
			if (StringUtils.isEmptyWithTrim(params.get("estatus"))) {
				params.put("examineStatus", examineStatus) ;
			}else {
				params.put("examineStatus", params.get("estatus")) ;
			 }
			params.put("rtype", rtype) ;
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo1 = params.get("substationNo") ;
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
			
				params.put("substationNo", substationNo);
			}else {
				if (!substationNo.contains(params.get("substationNo"))) {
					params.put("substationNo", "0000") ;
				}
			}
		/*	PageInfo<Map<String, Object>> mList = mobileUserService.muserList(params,getPageInfo(1,5000)) ;
			if (mList!=null) {
				model.put("mlist", JsonUtil.toJson(mList.getList())) ;
			}else {
				model.put("mlist",mList) ;
			}*/
			PageInfo<Map<String, Object>> list = substationAccountService.list(params, getPageInfo(cpage)) ;
		/*	PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(1,5000));
			List<Map<String, Object>> substations = userService.getCurrentSubstation();
			model.put("clist", JsonUtil.toJson(courierList.getList())) ;
			model.put("slist", JsonUtil.toJson(substations)) ;*/
			model.put("list", list) ;
			params.put("substationNo", substationNo1) ;
			model.put("params", params) ;
			return "gather/mexam";
		}	
		
		
		 // 用于编辑页面
		@RequestMapping(value = { "/edit" })
		public String edit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
			Map<String, Object> aMap = substationAccountService.getById(Integer.valueOf(params.get("id"))) ;
			Map<String, Object> mMap = null ;
			if (aMap.get("month_no")!=null) {
				mMap = mobileUserService.getMuserByMonthNo(aMap.get("month_no").toString()) ;
			}
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			params.put("substationNo", substationNo);
			PageInfo<Map<String, Object>> mList = mobileUserService.muserList(params,getPageInfo(1,5000)) ;
			if (mList!=null) {
				model.put("mlist", JsonUtil.toJson(mList.getList())) ;
			}else {
				model.put("mlist",mList) ;
			}
			model.put("aMap", aMap) ;
			model.put("mMap", mMap) ;
			model.put("params", params) ;
			return "gather/ashow";
		}	
		
		// 用于编辑保存
		@RequestMapping(value = { "/esave" })
		public void esave(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,SubstationAccount substationAccount) throws IOException {
			String r = "1" ;
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			Map<String, Object> map = null ;
			if (!StringUtils.isEmptyWithTrim(params.get("monthNo"))) {
				 map =  mobileUserService.getMuserByNo(params.get("monthNo")) ;
				if (map==null) {
					r = "月结号错误" ;
				}
			}
			
			if ("1".equals(r)) {
					if (StringUtils.isEmptyWithTrim(substationAccount.getSettleTime())) {
						substationAccount.setSettleTime(null);
					}
					if (!StringUtils.isEmptyWithTrim(params.get("monthNo"))) {
						substationAccount.setMonthNo(map.get("month_no").toString());
					}else {
						substationAccount.setMcount(0);
						substationAccount.setMtype(null);
						substationAccount.setMsettleDate(null);
						substationAccount.setMcontact(null);
						substationAccount.setMphome(null);
						substationAccount.setMemail(null);
						substationAccount.setLiced(null);
					}
					float c1 = substationAccount.getFcount()+substationAccount.getCcount()+substationAccount.getOcount()+substationAccount.getHcount() ;
					float m1 = substationAccount.getMcount() ;
					if (c1>0&&m1>0) {
						substationAccount.setRtype("CM");
					}else {
						if (m1>0) {
							substationAccount.setRtype("M");
						}else {
							substationAccount.setRtype("C");
						}
					}
					substationAccount.setAcount(c1+m1);
					//substationAccount.setOperator(bossUser.getUserName());
					//substationAccount.setExamineStatus(0);
					substationAccountService.updateSubstationAccount(substationAccount) ;
					r = "1" ;
			}
			 outText(r, response);
		}
		

		
		
		

}
