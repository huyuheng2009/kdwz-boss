package com.yogapay.boss.controller;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.CodRule;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.ItemType;
import com.yogapay.boss.domain.JSONResult;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.LgcArea;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.LgcMode;
import com.yogapay.boss.domain.LgcPayType;
import com.yogapay.boss.domain.LgcResource;
import com.yogapay.boss.domain.LgcResourceTemp;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.domain.TempFile;
import com.yogapay.boss.domain.ValuationRule;
import com.yogapay.boss.domain.WeightConfig;
import com.yogapay.boss.service.CodRuleService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.ItemTypeService;
import com.yogapay.boss.service.LgcAreaService;
import com.yogapay.boss.service.LgcConfigService;
import com.yogapay.boss.service.LgcModeService;
import com.yogapay.boss.service.LgcPayTypeService;
import com.yogapay.boss.service.LgcResourceService;
import com.yogapay.boss.service.LgcService;
import com.yogapay.boss.service.LgcWeightConfigService;
import com.yogapay.boss.service.ManagerLgcConfigService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.RequireListService;
import com.yogapay.boss.service.RequireTypeService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.service.ValuationRuleService;
import com.yogapay.boss.utils.AmapUtil;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.ConstantsLoader;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;


@Controller
@RequestMapping(value = "/lgc")
public class LgcController extends BaseController {
	private final static Logger log = LoggerFactory.getLogger(LgcController.class);
    @Resource
    private OrderService orderService ;
    @Resource
    private LgcService lgcService;
    @Resource
    private SubstationService substationService;
    @Resource
    private CourierService courierService;
    @Resource
    private SequenceService sequenceService ;
    @Resource
    private UserService userService ;
    @Resource
    private ItemTypeService itemTypeService ;
    @Resource
    private CodRuleService codRuleService ;
    @Resource
    private ValuationRuleService valuationRuleService ;
    @Resource
    private RequireTypeService requireTypeService ;
    @Resource
    private RequireListService requireListService ;
    @Resource
    private LgcModeService lgcModeService ;
    @Resource
    private LgcAreaService lgcAreaService ;
    @Resource
    private LgcPayTypeService lgcPayTypeService ;
    @Resource
    private LgcResourceService lgcResourceService ;
    @Resource
    private LgcWeightConfigService lgcWeightConfigService;
    @Resource
    private LgcConfigService lgcConfigService ;
    @Resource
    private ManagerLgcConfigService managerLgcConfigService ;
    
   // 用于快递公司列表
	@RequestMapping(value = { "/lgclist" })
	public String lgclist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		Map<String, String> param1 = new HashMap<String, String>() ;
		param1.putAll(params);
		String lgcNoString  =  Constants.getUser().getLgcNo() ;
		if (StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
			param1.put("lgcNo", Constants.getUser().getLgcNo()) ;
		}else {
			if (!lgcNoString.contains(params.get("lgcNo"))) {
				param1.put("lgcNo", "0000") ;
			}
		}
		PageInfo<Map<String, Object>> lgcList = lgcService.list(param1,getPageInfo(cpage));
		model.put("lgcList", lgcList) ;
		model.put("params", params) ;
		
		return "lgc/lgc_list";
	}	
	
	//用于查看快递公司
	@RequestMapping(value = { "/lgcshow"})
	public String lgcshow(final ModelMap model,
			@RequestParam Map<String, String> params) throws SQLException{
		Integer id = Integer.parseInt(params.get("id"));
		Lgc lgc = lgcService.getLgcById(id);
		model.put("lgc1", lgc);
		return "lgc/lgc_show";
	}
	
		//用于编辑快递
		@RequestMapping(value = { "/lgcedit"})
		public String lgcedit(final ModelMap model,HttpServletRequest req,
				@RequestParam Map<String, String> params){
			if(params.get("id") != null){
				Integer id = Integer.parseInt(params.get("id"));
				Lgc lgc = lgcService.getLgcById(id);
				model.put("lgc1", lgc);
			}else {
				String lgcNo = "0000" ;
				LgcConfig lgcConfig = (LgcConfig) req.getSession().getAttribute("lgcConfig") ;
				if (lgcConfig!=null) {
					lgcNo = lgcConfig.getLgcNo() ;
				}
				Lgc lgc = lgcService.getLgcByNo(lgcNo) ;
				model.put("lgc1", lgc);
			}
			return "lgc/lgc_edit";
		}
		
		//用于保存快递公司
		@RequestMapping(value = { "/lgcsave"})
		public void lgcsave(HttpServletResponse response, HttpServletRequest request,
				Lgc lgc) throws SQLException{
			int r = 0;
			try {
				if(lgc.getId() == null){
					r = 1;
					if(lgcService.isExist(lgc)){
						r = 2;
						lgcService.updateLgc(lgc);
					}else{
						r = 1 ;
						lgc.setLgcNo(sequenceService.getNextVal("lgc_no"));
						lgc.setNextSno(lgc.getLgcNo()+"001");   //快递公司下一个分站编号
						lgc.setTrackSrc("LOC");
						lgcService.saveLgc(lgc);
					}
				}else{
				/*	if(lgcService.isExist(lgc)){
						r = 1;
						lgcService.updateLgc(lgc);
					}else{
						r = 2 ;
					}*/
					r = 1;
					lgcService.updateLgc(lgc);
				}
			} catch (Exception e) {
				e.printStackTrace();
				r = 0;
			}
			outText(r + "", response);
		}
		
	//用于删除快递公司
	@RequestMapping(value = {"/lgcdel"})
	public void lgcdel(@RequestParam Map<String, String> params,
			HttpServletResponse response) throws SQLException {
		if (StringUtil.isEmpty(params.get("id"))) {
			outText("删除失败", response);
		} else {
			lgcService.delById(Integer.parseInt(params.get("id")));
			outText("1", response);
		}
	}
	
	//接受快递公司logo
	@RequestMapping(value ={"/lgclogo"})
	public String lgclogo(HttpServletRequest request, HttpServletResponse response
			){
		System.out.println("11111111111");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();  
	      MultipartFile file = fileMap.get("logo");
		int r = 1;
		try {
			file.transferTo(new File("D:/lgc.jpg"));
			
		} catch (IllegalStateException e) {
			r = 0;
			e.printStackTrace();
		} catch (IOException e) {
			r = 0;
			e.printStackTrace();
		}  
//		outText(r + "", response);
		return "1";
	}
	
	  // 用于快递公司分站列表
		@RequestMapping(value = { "/slist" })
		public String slist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			Map<String, String> param1 = new HashMap<String, String>() ;
			param1.putAll(params);
			if (StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
				param1.put("lgcNo", Constants.getUser().getLgcNo()) ;
			}
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				
				param1.put("substationNo", substationNo);
			}else {
				if (!substationNo.contains(params.get("substationNo"))) {
					param1.put("substationNo", "0000") ;
				}
			}
			PageInfo<Map<String, Object>> substationList = substationService.tlist(param1,getPageInfo(cpage));
			model.put("substationList", substationList) ;
			
			//params.remove("substationNo") ;
			model.put("params", params) ;
			
			return "lgc/slist";
		}	
		
		  // 用于快递公司分站列表json数据返回
				@RequestMapping(value = { "/salllist" })
				public void salllist(@RequestParam Map<String, String> params, HttpServletRequest request,HttpServletResponse response) throws IOException {
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
					PageInfo<Map<String, Object>> substationList = substationService.list(params,getPageInfo(1,5000));
					outJson(JSONObject.toJSONString(substationList.getList()), response);
				}
				
				  // 用于快递公司分站列表json数据返回
				@RequestMapping(value = { "/calllist" })
				public void calllist(@RequestParam Map<String, String> params, HttpServletRequest request,HttpServletResponse response) throws IOException {
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
					PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(1,5000));
					outJson(JSONObject.toJSONString(courierList.getList()), response);
				}
		
		//用于查看快递分站
		@RequestMapping(value = { "/sshow"})
		public String sshow(final ModelMap model,
				@RequestParam Map<String, String> params) throws SQLException{
			Integer id = Integer.parseInt(params.get("id"));
			Substation substation = substationService.getSubstationById(id);
			model.put("substation", substation);
			log.info("substation="+substation.toString());
			return "lgc/sshow";
		}
		
		
		//用于编辑快递分站
		@RequestMapping(value = { "/sedit"})
		public String sedit(final ModelMap model,
				@RequestParam Map<String, String> params){
			if(params.get("id") != null){
				Integer id = Integer.parseInt(params.get("id"));
				Substation substation = substationService.getSubstationById(id);
				model.put("substation", substation);
			}
			
			Map<String, String> p = new HashMap<String, String>() ;
			p.put("lgcNo", Constants.getUser().getLgcNo()) ;
			PageInfo<Map<String, Object>> lgcList = lgcService.list(p,getPageInfo(1,1000));
			model.put("lgcList", lgcList.getList()) ;
			return "lgc/sedit";
		}
				
			//用于保存分站编号
			@RequestMapping(value = { "/ssave"})
			public void ssave(HttpServletResponse response, HttpServletRequest request,
					Substation substation) throws SQLException{
				int r = 0;
				try {
					if (StringUtils.isEmptyWithTrim(substation.getLocation())) {
						if (!StringUtils.isEmptyWithTrim(substation.getSubstationAddr())) {
							substation.setLocation(AmapUtil.addressToGPString(substation.getSubstationAddr()));
						}
					}
					if(substation.getId() == null){
						r = 1;
						if(substationService.isExist(substation)){
							r = 2;
							substationService.update(substation);
						}else{
							//Lgc lgc = lgcService.getLgcByNo(substation.getLgcNo()) ;
							substation.setSubstationNo(sequenceService.getNextVal("substation_no"));
							//substation.setSubstationNo(lgc.getNextSno());
							substation.setExchange("S"+substation.getSubstationNo());
							substation.setNextCno(substation.getSubstationNo()+"001");
							//lgcService.setNextSno(lgc);
							substationService.save(substation);
						}
					}else{
					/*	if(!substationService.isExist(substation)){
							r = 1;*/
							substationService.update(substation);
							r = 1;
						/*}else{
							r = 2 ;
						}*/
					}
				} catch (Exception e) {
					e.printStackTrace();
					r = 0;
				}
				outText(r + "", response);
			}
				
		//用于删除快递公司
		@RequestMapping(value = {"/sdel"})
		public void sdel(@RequestParam Map<String, String> params,
				HttpServletResponse response) throws SQLException {
			if (StringUtil.isEmpty(params.get("id"))) {
				outText("删除失败", response);
			} else {
				substationService.delete(Integer.parseInt(params.get("id")));
				outText("1", response);
			}
		}
		
		  // 用于快递员列表
		@RequestMapping(value = { "/clist" })
		public String clist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			/*if (StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
				params.put("lgcNo", Constants.getUser().getLgcNo()) ;
			}*/
			
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
			PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(cpage));
			List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
			model.put("substations", substations);
			model.put("courierList", courierList) ;
			model.put("params", params) ;
			
			return "lgc/clist";
		}	
		
		 // 用于快递员收派范围
		@RequestMapping(value = { "/csarea" })
		public String csarea(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			/*if (StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
				params.put("lgcNo", Constants.getUser().getLgcNo()) ;
			}*/
			
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
			PageInfo<Map<String, Object>> courierList = courierService.list(params,getPageInfo(cpage));
			List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
			model.put("substations", substations);
			model.put("courierList", courierList) ;
			model.put("params", params) ;
			
			return "lgc/csarea";
		}	
		
		
		
		  // 用于快递员统计列表
			@RequestMapping(value = { "/ccount" })
			public String ccount(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
				/*if (StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
					params.put("lgcNo", Constants.getUser().getLgcNo()) ;
				}*/
				
				BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
				String substationNo ;
				if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
					 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
				}else {
					substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
				}
				
				if (StringUtils.isEmptyWithTrim(params.get("substationNo"))) {
					params.put("substationNo", substationNo) ;
				}
				
				if (params.get("createTimeBegin") == null
						&& params.get("createTimeEnd") == null) {
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String createTime = sdf.format(date);
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DATE, -1);

					String createTimeBegin = sdf.format(calendar.getTime());
					String createTimeEnd = createTime;
					params.put("createTimeBegin", createTimeBegin);
					params.put("createTimeEnd", createTimeEnd);
				}
				
				PageInfo<Map<String, Object>> courierList = courierService.ccount(params,getPageInfo(cpage));
				List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
				model.put("substations", substations);
				model.put("courierList", courierList) ;
				model.put("params", params) ;
				
				return "lgc/ccount";
			}
		
		
		//用于查看快递员
		@RequestMapping(value = { "/cshow"})
		public String cshow(final ModelMap model,
				@RequestParam Map<String, String> params) throws SQLException{
			Integer id = Integer.parseInt(params.get("id"));
			Courier courier = courierService.getCourierById(id);
			model.put("courier", courier);
			log.info("courier="+courier.toString());
			return "lgc/cshow";
		}
		
		//用于编辑快递分站
		@RequestMapping(value = { "/cedit"})
		public String cedit(final ModelMap model,
				@RequestParam Map<String, String> params){
			if(params.get("id") != null){
				Integer id = Integer.parseInt(params.get("id"));
				Courier courier = courierService.getCourierById(id);
				model.put("courier", courier);
			}
			return "lgc/cedit";
		}
		
		//用于编辑快递分站
		@RequestMapping(value = { "/csedit"})
		public String csedit(final ModelMap model,
				@RequestParam Map<String, String> params){
			if(params.get("id") != null){
				Integer id = Integer.parseInt(params.get("id"));
				Courier courier = courierService.getCourierById(id);
				model.put("courier", courier);
			}
			return "lgc/csedit";
		}
		
		//用于保存快递员
		@RequestMapping(value = { "/csave"})
		public void csave(HttpServletResponse response, HttpServletRequest request,
				Courier courier,@RequestParam Map<String, String> params) throws SQLException{
			int r = 0;
			String sno = params.get("substationNo") ;
			setSubstationNo(params, request) ;
			System.out.println(params.get("substationNo"));
			if (params.get("substationNo").contains(sno)) {
				params.put("substationNo", sno) ;
			}else {
				outText("4", response);
				return ;
			}
			try {
				if(courier.getId() == null){
					r = 1;
					if(courierService.isExist(courier)){
						r = 3;
						//courierService.update(courier);
					}else{
						if (substationService.isExist(courier.getSubstationNo())) {
							//Substation substation = substationService.getSubstationByNo(courier.getSubstationNo()) ;
							courier.setCourierNo(sequenceService.getNextVal("courier_no"));
							//courier.setCourierNo(substation.getNextCno());
							courier.setQueueName("C"+courier.getCourierNo());
							courier.setPassWord(Md5.md5Str(courier.getUserName()));
							courier.setCreateOperator(Constants.getUser().getUserName());
							//substationService.nextCno(substation);
							courierService.save(courier);
						}else {
							r = 4 ;
						}
					
					}
				}else{
					if(courierService.isExist(courier)){
						r = 1;
						Courier courier1 =  courierService.getCourierById(courier.getId()) ;
						if (!courier.getSubstationNo().equals(courier1.getSubstationNo())) {
						 	if (!courierService.isChangeSno(courier.getCourierNo())) {
						 		r = 5 ;
							}else {
								courierService.update(courier);	
							}
					      }else {
					    	  courierService.update(courier);	
						}
					}else{
						r = 2 ;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				r = 0;
			}
			outText(r + "", response);
		}
		
		//用于保存快递员
		@RequestMapping(value = { "/csarea_save"})
		public void csarea_save(HttpServletResponse response, HttpServletRequest request,
				Courier courier) throws SQLException{
			int r = 0;
			try {
				courierService.updateSarea(courier) ;
                r=1 ;
			} catch (Exception e) {
				e.printStackTrace();
				r = 0;
			}
			outText(r + "", response);
		}
		
		//用于删除快递公司
		@RequestMapping(value = {"/cdel"})
		public void cdel(@RequestParam Map<String, String> params,
				HttpServletResponse response) throws SQLException {
			if (StringUtil.isEmpty(params.get("id"))) {
				outText("删除失败", response);
			} else {
				if (courierService.isChange(Integer.parseInt(params.get("id")))) {
					courierService.delete(Integer.parseInt(params.get("id")));
					outText("1", response);
				}else {
					outText("无法删除！当前快递员已有收派历史！删除将会影响报表系统的准确性！", response);
				}
				
			}
		}
		
		  // 用于代收款规则列表
		@RequestMapping(value = { "/cod_rule" })
		public String cod_rule(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			PageInfo<Map<String, Object>> list = codRuleService.list(params,getPageInfo(cpage));
			model.put("list", list) ;
			model.put("params", params) ;
			return "lgc/cod_list";
		}	
		
		  // 用于代收款规则
			@RequestMapping(value = { "/cod_rule_edit" })
			public String cod_rule_edit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
					HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
				Map<String, Object> codRule = null ;
				if (!StringUtil.isEmptyWithTrim(params.get("id"))) {
					codRule =codRuleService.getById(Integer.parseInt(params.get("id"))) ;
				}
				model.put("codRule", codRule) ;
				model.put("params", params) ;
				return "lgc/cod_edit";
			}	
		
		     //用于保存代收款规则
				@RequestMapping(value = { "/cod_rule_save"})
				public void csarea_save(HttpServletResponse response, HttpServletRequest request,
						CodRule codRule) throws SQLException{
					String r = "";
					try {
						if (codRule.getId()==null) {
							codRule.setLgcNo(null);
							codRule.setLatter("300");
							codRule.setRate("0.005");
							codRule.setRateType("LR");
							codRule.setStatus(0);
							codRule.setTop("0");
							codRuleService.save(codRule) ;
							r = "1" ;
						}else {
							codRuleService.update(codRule);
							r = "1" ;
						}

					} catch (Exception e) {
						e.printStackTrace();
						r = "数据有误，保存失败";
					}
					outText(r, response);
				}
		
				  //用于保存代收款规则
				@RequestMapping(value = { "/cod_rule_status"})
				public void cod_rule_status(HttpServletResponse response, HttpServletRequest request,
						CodRule codRule) throws SQLException{
					String r = "";
					try {
						if (codRule.getId()!=null) {
							codRule.setStatus(1);
							codRuleService.status(codRule);
							r = "1" ;
						}else {
							r = "数据有误，保存失败";
						}
						} catch (Exception e) {
						e.printStackTrace();
						r = "数据有误，保存失败";
					}
					outText(r, response);
				}
		
		  // 用于
		@RequestMapping(value = { "/valuation_rule" })
		public String vrule(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			PageInfo<Map<String, Object>> list = valuationRuleService.list(params,getPageInfo(cpage));
			model.put("list", list) ;
			model.put("params", params) ;
			return "lgc/valuation_list";
		}	
		
		// 用于代收款规则
					@RequestMapping(value = { "/valuation_edit" })
					public String valuation_edit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
							HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
						Map<String, Object> valuationRule = null ;
						if (!StringUtil.isEmptyWithTrim(params.get("id"))) {
							valuationRule = valuationRuleService.getById(Integer.parseInt(params.get("id"))) ;
						}
						model.put("valuationRule", valuationRule) ;
						model.put("params", params) ;
						return "lgc/valuation_edit";
					}	
				
				     //用于保存代收款规则
						@RequestMapping(value = { "/valuation_save"})
						public void valuation_save(HttpServletResponse response, HttpServletRequest request,
								ValuationRule valuationRule) throws SQLException{
							String r = "";
							if(Double.parseDouble(valuationRule.getMaxv())<Double.parseDouble(valuationRule.getMinv())){
								outText("最大保价值不能小于最低保价值", response);
								return;
							}
							try {
								if (valuationRule.getId()==null) {
									valuationRule.setLgcNo(null);
									valuationRule.setLatter("300");
									//valuationRule.setRate(String.valueOf(Float.valueOf(valuationRule.getRate())/1000.0));
									valuationRule.setRateType("LR");
									valuationRule.setStatus(0);
									valuationRule.setTop("0");
									valuationRuleService.save(valuationRule) ;
									r = "1" ;
								}else {
									//valuationRule.setRate(String.valueOf(Float.valueOf(valuationRule.getRate())/1000.0));
									valuationRuleService.update(valuationRule);
									r = "1" ;
								}

							} catch (Exception e) {
								e.printStackTrace();
								r = "数据有误，保存失败";
							}
							outText(r, response);
						}
				
						  //用于保存代收款规则
						@RequestMapping(value = { "/valuation_status"})
						public void valuation_status(HttpServletResponse response, HttpServletRequest request,
								ValuationRule valuationRule) throws SQLException{
							String r = "";
							try {
								if (valuationRule.getId()!=null) {
									valuationRule.setStatus(1);
									valuationRuleService.status(valuationRule);
									r = "1" ;
								}else {
									r = "数据有误，保存失败";
								}
								} catch (Exception e) {
								e.printStackTrace();
								r = "数据有误，保存失败";
							}
							outText(r, response);
						}
		
		

		
		//导出列表
		@RequestMapping(value = { "/export" })
		public void export(@RequestParam Map<String, String> params,
				HttpServletResponse response, HttpServletRequest request)
				throws SQLException {
			params.put("sub_limit", StringUtils.nullString(request.getAttribute("sub_limit"))) ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			OutputStream os = null;
			try {
				request.setCharacterEncoding("UTF-8");
				os = response.getOutputStream(); // 取得输出流
				response.reset(); // 清空输出流
				response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
				if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
					if (SecurityUtils.getSubject().isPermitted("ORDER_ASSIGN")) {
						params.put("orderAssign", "1") ;
					}
				}
				if(!StringUtils. isEmpty(params.get("serviceName"))){
	                if("exportCourier".equals(params.get("serviceName"))){
	                	 String fileName = "快递员统计明细-" + sdf.format(new Date()) + ".xls";
	                     response.setHeader("Content-disposition", "attachment;filename="
	                             + new String(fileName.getBytes("GBK"), "ISO8859-1"));
	                     exportCourier(os, params, fileName);
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
		
		
		public void exportCourier(OutputStream os, Map<String, String> params,
				String fileName) throws Exception {
			int row = 1; // 从第三行开始写
			int col = 0; // 从第一列开始写
			if (StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
				System.out.println("============="+Constants.getUser().getLgcNo());
				params.put("lgcNo", Constants.getUser().getLgcNo()) ;
			}
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(params.get("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			
			if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
				params.put("substationNo", substationNo);
			}
			
			String createTimeBegin = params.get("createTimeBegin") ;
			String createTimeEnd = params.get("createTimeEnd") ;
			if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))&&StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String createTime = sdf.format(date);
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -1);

				createTimeBegin = sdf.format(calendar.getTime());
			    createTimeEnd = createTime;
				params.put("createTimeBegin", createTimeBegin);
				params.put("createTimeEnd", createTimeEnd);
			}
			PageInfo<Map<String, Object>> courierList = courierService.ccount(params,getPageInfo(1,20000));
			List<Map<String, Object>> clist = new ArrayList<Map<String,Object>>() ;
			if (courierList.getList()!=null) {
				clist = courierList.getList() ;
			}
			
			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream(
					"/template/courier.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);

			Iterator<Map<String, Object>> it = clist.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row, String.valueOf(map.get("substation_name")))); //分站
				ws.addCell(new Label(col++, row, String.valueOf(map.get("user_name"))));//用户名
				ws.addCell(new Label(col++, row, String.valueOf(map.get("real_name"))));//姓名
				ws.addCell(new Label(col++, row, String.valueOf(map.get("takeCount"))));//收件数
				ws.addCell(new Label(col++, row, String.valueOf(map.get("tnpay"))));//收件金额
				ws.addCell(new Label(col++, row, String.valueOf(map.get("sendCount"))));//派件数
				ws.addCell(new Label(col++, row, String.valueOf(map.get("snpay"))));//派件金额
				ws.addCell(new Label(col++, row, String.valueOf(createTimeBegin+"到"+createTimeEnd)));//时间段
				ws.addCell(new Label(col++, row, String.valueOf("无")));// 备注
				row++;
				col = 0;
			}

			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}
		
		
		  // 用于物品类型列表
		@RequestMapping(value = { "/item_type" })
		public String item_type(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			PageInfo<Map<String, Object>> list = itemTypeService.list(params, getPageInfo(cpage)) ;
			model.put("list", list) ;
			model.put("params", params) ;
			return "lgc/item_type";
		}	
		
		
		
		
		//用于编辑物品类型
		@RequestMapping(value = { "/type_edit"})
		public String type_edit(final ModelMap model,
				@RequestParam Map<String, String> params){
			if(params.get("id") != null){
				Integer id = Integer.parseInt(params.get("id"));
				ItemType itemType = itemTypeService.getById(id) ;
				model.put("itemType", itemType);
			}
			return "lgc/type_edit";
		}

		//用于保存
				@RequestMapping(value = { "/type_save"})
				public void type_save(HttpServletResponse response, HttpServletRequest request,ItemType itemType) throws SQLException{
					int r = 0;
					try {
						if(itemType.getId()== null){
							itemTypeService.save(itemType);
							r = 1;
						}else{
							itemTypeService.update(itemType) ;
							r = 1;
						}
					} catch (Exception e) {
						e.printStackTrace();
						r = 0;
					}
					outText(r + "", response);
				}
				
				//用于删除
				@RequestMapping(value = {"/type_del"})
				public void type_del(@RequestParam Map<String, String> params,
						HttpServletResponse response) throws SQLException {
					if (StringUtil.isEmpty(params.get("id"))) {
						outText("删除失败", response);
					} else {
						itemTypeService.delById(Integer.parseInt(params.get("id")));
						outText("1", response);
					}
				}
				
				
				//用于defaultItem
				@RequestMapping(value = {"/defaultItem"})
				public void defaultItem(@RequestParam Map<String, String> params,
						HttpServletResponse response) throws SQLException {
					if (StringUtil.isEmpty(params.get("id"))) {
						outText("保存失败", response);
					} else {
						itemTypeService.defaultItem(Integer.parseInt(params.get("id")));
						outText("1", response);
					}
				}
	
				  // 用于必填项列表
				@RequestMapping(value = { "/require_type" })
				public String require_type(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					PageInfo<Map<String, Object>> list = requireTypeService.list(params, getPageInfo(cpage)) ;
					model.put("list", list) ;
					model.put("params", params) ;
					return "lgc/require_type";
				}	
				
				  // 用于必填项列表
				@RequestMapping(value = { "/require_list" })
				public String require_list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,Integer id) throws IOException {
					model.put("params", params) ;
					
					if ("ORDER_TAKE".equals(params.get("code"))) {
						List<Map<String, Object>> list = requireListService.getByTid(id,"CHECKBOX") ;
						List<Map<String, Object>> rlist = requireListService.getByTid(id,"RADIO") ;
						model.put("list", list) ;
						model.put("rlist", rlist) ;
						return "lgc/require_list";
					}
					
					if ("ORDER_INPUT".equals(params.get("code"))) {
						List<Map<String, Object>> list = requireListService.getByTid(id,"CHECKBOX") ;
						model.put("list", list) ;
						return "lgc/input_require_list";
					}
					
					if ("LGC_MODEL".equals(params.get("code"))) {
						PageInfo<Map<String, Object>> list = lgcModeService.list(params,getPageInfo(1));
						model.put("list", list);
						return "lgc/lgc_mode";
					}
					
					if ("LGC_ORDER_NO".equals(params.get("code"))) {
						Map<String, Object> config = lgcConfigService.getByType("LGC_ORDER_NO") ;
						model.put("configMap", config);
						return "lgc/lgc_no_config";
					}

					if ("MONTH_NO".equals(params.get("code"))) {
						Map<String, Object> config = lgcConfigService.getByType("MONTH_NO") ;
						model.put("configMap", config);
						return "lgc/month_no_config";
					}
					
					if ("CODE_RULE".equals(params.get("code"))) {
						Map<String, Object> codRule=codRuleService.getById(1) ;
						model.put("codRule", codRule) ;
						model.put("params", params) ;
						return "lgc/cod_edit";
						
					}
					
					if ("VALUATION_RULE".equals(params.get("code"))) {
						Map<String, Object> valuationRule = valuationRuleService.getById(1) ;
						model.put("valuationRule", valuationRule) ;
						model.put("params", params) ;
						return "lgc/valuation_edit";
					}
					
					if ("WEIGHTCONF".equals(params.get("code"))) {
						Map<String,Object> map= lgcWeightConfigService.selectOne(params);
						model.put("weightConfig", map);
						return "/lgc/weight_config";
					}
					
					if ("MOBILEMSGCONFIG".equals(params.get("code"))) {
						
						Map<String, Object> map=lgcConfigService.getByType("MOBILE_CONFIG");
						
						model.put("map", map);
						return "/lgc/mobile_msg_config";
						
					}
					
					return "" ;
				}				
			
				

				//用于保存必填项修改
				@RequestMapping(value = {"/require_list_save"})
				public void require_list_save(@RequestParam Map<String, String> params,
						HttpServletResponse response)  {
					try {
						
						if ("ORDER_TAKE".equals(params.get("code"))) {
						    requireListService.updateRequireCheckBox(params.get("ids"), params.get("tid"));
						   requireListService.updateRequireByName(params.get("tid"), "freight_type", params.get("freightType"));
						   requireListService.updateRequireByName(params.get("tid"), "weight_type", params.get("weightType"));
						}
						if ("ORDER_INPUT".equals(params.get("code"))) {
							requireListService.updateRequireCheckBox(params.get("ids"), params.get("tid"));
						}
						if ("LGC_ORDER_NO".equals(params.get("code"))) {
							lgcConfigService.updateByTypeName("LGC_ORDER_NO", "MIN_LENGTH", params.get("MIN_LENGTH"));
							lgcConfigService.updateByTypeName("LGC_ORDER_NO", "MAX_LENGTH", params.get("MAX_LENGTH"));
							lgcConfigService.updateByTypeName("LGC_ORDER_NO", "CONSTATUTE", params.get("CONSTATUTE"));
						}

						if ("MOBILEMSGCONFIG".equals(params.get("code"))) {
							lgcConfigService.updateByTypeName("MOBILE_CONFIG", "SEND_SEND_MSG", params.get("SEND_SEND_MSG"));
							lgcConfigService.updateByTypeName("MOBILE_CONFIG", "TAKE_SEND_MSG", params.get("TAKE_SEND_MSG"));
							
						}
						outText("1", response);
					} catch (Exception e) {
						outText("保存失败", response);
					}
					
				}
				
				
				
				
	// 用于分配模式
	@RequestMapping(value = { "/lgc_mode" })
	public String lgc_mode(@RequestParam Map<String, String> params,
			final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "p", defaultValue = "1") int cpage)
			throws IOException {
		PageInfo<Map<String, Object>> list = lgcModeService.list(params,getPageInfo(cpage));
		model.put("list", list);
		model.put("params", params);
		return "lgc/lgc_mode";
	}

	// 用于保存分配模式
	@RequestMapping(value = { "/lgc_mode_status" })
	public void lgc_mode_status(HttpServletResponse response,
			HttpServletRequest request, LgcMode lgcMode) throws SQLException {
		String r = "";
		try {
			if (lgcMode.getId() != null) {
				lgcMode.setStatus(1);
				lgcModeService.status(lgcMode);
				r = "1";
			} else {
				r = "数据有误，保存失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			r = "数据有误，保存失败";
		}
		outText(r, response);
	}
				
				

	  // 用于公司收派范围列表
	@RequestMapping(value = { "/lgc_area" })
	public String lgc_area(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		PageInfo<Map<String, Object>> list = lgcAreaService.list(params, getPageInfo(cpage)) ;
		model.put("list", list) ;
		model.put("params", params) ;
		return "lgc/lgc_area";
	}				
				
				
	//用于编辑物品类型
	@RequestMapping(value = { "/lgc_area_edit"})
	public String lgc_area_edit(final ModelMap model,
			@RequestParam Map<String, String> params){
		if(params.get("id") != null){
			Integer id = Integer.parseInt(params.get("id"));
			LgcArea lgcArea = lgcAreaService.getById(id) ;
			model.put("lgcArea", lgcArea);
		}
		return "lgc/lgc_area_edit";
	}			
				
	//用于保存
	@RequestMapping(value = { "/lgc_area_save"})
	public void lgc_area_save(HttpServletResponse response, HttpServletRequest request,LgcArea lgcArea) throws SQLException{
		int r = 0;
		try {
			lgcArea.fix();
			if(lgcArea.getId()== null){
				LgcArea lArea = lgcAreaService.getByArea(lgcArea) ;
				if (lArea!=null&&lArea.getId()!=null) {
					lgcArea.setId(lArea.getId());
					lgcAreaService.update(lArea) ;
				}else {
					lgcAreaService.save(lgcArea);
				}
				r = 1;
			}else{
				lgcAreaService.update(lgcArea) ;
				r = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r = 0;
		}
		outText(r + "", response);
	}				
				
	//用于删除
	@RequestMapping(value = {"/lgc_area_del"})
	public void lgc_area_del(@RequestParam Map<String, String> params,
			HttpServletResponse response) throws SQLException {
		if (StringUtil.isEmpty(params.get("id"))) {
			outText("删除失败", response);
		} else {
			lgcAreaService.delById(Integer.parseInt(params.get("id")));
			outText("1", response);
		}
	}
	
	
	@RequestMapping(value = "/paytype")
	public String paytype(final ModelMap model,@RequestParam Map<String, String> params)
			throws SQLException {
		  String lgcNo = params.get("lgcNo");
		  LgcPayType lgcPayType = new LgcPayType() ;
		  lgcPayType.setLgcNo(lgcNo);
           List<Map<String, Object>> payTypeList = lgcPayTypeService.getLgcPayType(lgcPayType) ;
           model.put("payTypeList", payTypeList);
	     	model.put("params", params) ;
		return "lgc/payshow";
	}
		
	@RequestMapping(value = "/lp_save")
	public void lp_save(final ModelMap model,@RequestParam Map<String, String> params,HttpServletResponse response){
		try {
		String lgcNo = params.get("lgcNo");   //版本id
		String ids = params.get("ids");
		String [] idList = {} ;
		if (!StringUtil.isEmptyWithTrim(ids)) {
			idList = ids.split(",") ;
		}
		List<LgcPayType> lgcPayTypes = new ArrayList<LgcPayType>() ;
		LgcPayType lgcPayType = null ;
		for (int i = 0; i < idList.length; i++) {
			lgcPayType = new LgcPayType() ;
			lgcPayType.setLgcNo(lgcNo);
			lgcPayType.setPayId(Integer.valueOf(idList[i]));
			lgcPayTypes.add(lgcPayType) ;
			
		}
		lgcPayType = new LgcPayType() ;
		lgcPayType.setLgcNo(lgcNo);
		lgcPayTypeService.delByLgcNo(lgcPayType);
		lgcPayTypeService.batchSave(lgcPayTypes);
		outText("1", response);
		} catch (Exception e) {
		    e.printStackTrace();
			outText("设置失败！", response);
		}
	}
	
	
	
	// 重置快递员密码
			@RequestMapping(value ="/creset")
			public void creset(HttpServletRequest request,HttpServletResponse response, Long id) throws SQLException {
			   Courier courier = new Courier() ;
			   courier.setId(Integer.valueOf(id.toString()));
				String pw = "1234" ;
				courier.setPassWord(Md5.md5Str(pw));
					try {
				          courierService.creset(courier) ;
				          outText("重置成功，密码:"+pw, response);
					}catch (Exception e1) {
						outText("err", response);
						e1.printStackTrace();
					}
					
			}
	
			//用于资源上传页面
			@RequestMapping(value = { "/resource"})
			public String resource(final ModelMap model,@RequestParam Map<String, String> params,HttpServletRequest req){
				 LgcConfig lgcConfig = (LgcConfig) req.getSession().getAttribute("lgcConfig") ;
				 Map<String, Object> cMap = null ;
				 if (lgcConfig!=null) {
					cMap = managerLgcConfigService.getByLgcNo(lgcConfig.getLgcNo()) ;
				 }
				model.put("cMap", cMap) ;
				return "lgc/resource";
			}	
			

			@RequestMapping(value = {"/resource_upload"})
			public void resource_upload(@RequestParam Map<String, String> params, @RequestParam("file") MultipartFile file,String rtype,String rtext,HttpServletResponse response) {
				JSONResult result = new JSONResult();
				try {
					TempFile tempFile = new TempFile() ;
					tempFile.setBs(file.getBytes());
					tempFile.setOriginalFilename(file.getOriginalFilename());
					LgcResourceTemp.add(tempFile,rtype);
					result.setErrorCode(0);
					result.setMessage("上传成功！资源类型："+rtext);
				} catch (Exception e) {
					result.setErrorCode(1);
					result.setMessage("上传失败！");
					e.printStackTrace();
				}
				outJson(JsonUtil.toJson(result), response);
				
			}
			
			
			
			@RequestMapping(value = {"/resource_save"})
			public void resource_save(@RequestParam Map<String, String> params,HttpServletResponse response,HttpServletRequest req) {
				try {
				 LgcConfig lgcConfig = (LgcConfig) req.getSession().getAttribute("lgcConfig") ;
				 if (lgcConfig!=null) {
					  Map<String, TempFile> rlist = LgcResourceTemp.get() ;
					  if (rlist!=null) {
						  String contextPath = "/resource/";
						  File dir = new File(configInfo.getFile_root().concat(contextPath));
							if(!dir.isDirectory()){
								dir.mkdirs();
							}
							String fileName = "_"+System.currentTimeMillis()+"_";
							LgcResource lgcResource = new LgcResource() ;
							lgcResource.setLgcNo(lgcConfig.getLgcNo());
							lgcResource.setResDesc("");
							 for (String key : rlist.keySet()) {
								   FileCopyUtils.copy(rlist.get(key).getBs(),
						 new File(configInfo.getFile_root().concat(contextPath).concat(key+fileName+rlist.get(key).getOriginalFilename())));
								 String url = "/codfile/resource/" + key+fileName+rlist.get(key).getOriginalFilename() ;
								 lgcResource.setResName(key);
								 lgcResource.setResUrl(url);
								 lgcResource.setResPath(configInfo.getFile_root().concat(contextPath).concat(key+fileName+rlist.get(key).getOriginalFilename()));
								 lgcResourceService.saveOrUpdate(lgcResource);
					      }
					   }
						 LgcResourceTemp.clear();
						 managerLgcConfigService.updateKeyAddr(lgcConfig.getLgcNo(), params.get("lgc_key") , params.get("web_xd_addr")) ;
				   }
				 outText("1", response);
				} catch (Exception e) {
					e.printStackTrace();
					outText("保存失败", response);
				}
				

			}		
			
			
			@RequestMapping("/queryByCourierNo")
			public void queryByCourierNo(@RequestParam Map<String, String> params,HttpServletResponse response,HttpServletRequest req){
				Courier courier = courierService.getCourierByNo(params.get("courierNo"));
				JSONObject json =new JSONObject();
				if(courier==null){
					json.put("status", "1");
					outJson(json.toJSONString(), response);
					return;
				}
				json.put("courierNo", courier.getCourierNo());
				json.put("substationNo", courier.getSubstationNo());
				json.put("innerNo", courier.getInnerNo());
				json.put("realName", courier.getRealName());
				json.put("status", "0");
				outJson(json.toJSONString(), response);
			}
			
			@RequestMapping("/weightConf")
			public String weightConfig(@RequestParam Map<String, String> params,HttpServletResponse response,HttpServletRequest req,final ModelMap model){
				Map<String,Object> map= lgcWeightConfigService.selectOne(params);
				model.put("weightConfig", map);
				return "/lgc/weight_config";
			}
	
			
			@RequestMapping("/weightConfEdit")
			public void weightConfigEdit(@RequestParam Map<String, String> params,HttpServletResponse response,HttpServletRequest req,final ModelMap model){
				if(params.get("rev_minv")==null||"".equals(params.get("rev_minv").toString())){
					params.put("rev_minv", "0.00");
				}
				if(params.get("warehouse_minv")==null||"".equals(params.get("warehouse_minv").toString())){
					params.put("warehouse_minv", "0.00");
				}
				Map<String,Object> map=lgcWeightConfigService.selectOne(params);
				if(map==null){
					lgcWeightConfigService.insert(params);
				}else{
					lgcWeightConfigService.update(params);
				}
				
				outText("1", response);
			}
}
