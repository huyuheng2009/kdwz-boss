package com.yogapay.boss.controller;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.CodRateType;
import com.yogapay.boss.domain.CodSettleUser;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.MonthSettleType;
import com.yogapay.boss.domain.MonthSettleUser;
import com.yogapay.boss.domain.User;
import com.yogapay.boss.service.AppProductService;
import com.yogapay.boss.service.AppVersionService;
import com.yogapay.boss.service.CodRateTypeService;
import com.yogapay.boss.service.CodSettleUserService;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.LgcConfigService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.MonthSettleTypeService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.RequestFile;
import com.yogapay.boss.utils.SHA;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;
import com.yogapay.boss.utils.http.CommonHttpConnection;
import com.yogapay.boss.utils.http.HttpConnectionParameters;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统登录，注销，首页等
 * 
 * @author dj
 * 
 */
@Controller
@RequestMapping(value = "/mobile")
public class MobileController extends BaseController {
    @Resource
    private MobileUserService mobileUserService ;
    @Resource
    private AppProductService appProductService ;
    @Resource
    private SequenceService sequenceService ;
    @Resource
    private AppVersionService appVersionService ;
    @Resource
    private UserService userService ;
    @Resource
    private CourierService courierService ;
    @Resource
    private CodSettleUserService codSettleUserService ;
    @Resource
    private MonthSettleTypeService monthSettleTypeService ;
    @Resource
    private CodRateTypeService codRateTypeService ;
    @Resource
    private LgcConfigService lgcConfigService ;

    
 // 用于
 	@RequestMapping(value = { "/getbyno" })
 	public void getbyno(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
 			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
 		Map<String, Object> mMap  = mobileUserService.getMuserByNo(params.get("monthSettleNo")) ;
 		outJson(JSONObject.toJSONString(mMap), response);
 	}
    
   // 用于
	@RequestMapping(value = { "/userlist" })
	public String ulist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		PageInfo<Map<String, Object>> userList = mobileUserService.list(params,getPageInfo(cpage));
		model.put("userList", userList) ;
		model.put("params", params) ;
		return "mobile/ulist";
	}	
	
	
	// 禁用用户
	@RequestMapping(value = { "/ustatus" }, method = RequestMethod.GET)
	public void ustatus(HttpServletRequest request,HttpServletResponse response, Long id,int s) throws SQLException {
		User user = new User();
		user.setId(id);
		user.setStatus(s);
		mobileUserService.status(user);
		outText("1", response);
	}
	
	
	// 重置用户
	@RequestMapping(value ="/ureset")
	public void ureset(HttpServletRequest request,HttpServletResponse response, Long id,String phone) throws SQLException {
		User user = new User() ;
		user.setId(id);
		String pw = StringUtil.getRandomString(6) ;
		user.setPassWord(Md5.md5Str(pw));
		mobileUserService.updateUser(user);
		String content = "尊敬的用户，您的密码已经重置，新密码为："+pw+",请勿向他人转发此条信息！【支付界快递】" ;
                Map<String, String> values = new HashMap<String, String>();
                Map<String, String> requestProperty = new HashMap<String, String>();
                values.put("operation", "S");
                values.put("target", phone);
                values.put("note.businessCode", "YYPT");
                values.put("content", content);
                values.put("channel", "Y");//选择通道
                values.put("check", SHA.SHA1Encode(values.get("channel")+values.get("target") + "yogapayHFT" + values.get("content") + values.get("note.businessCode")).toUpperCase());
                try {
					CommonHttpConnection.proccess(new HttpConnectionParameters(configInfo.getMessage_host(), "POST", 22000, true, true, true, requestProperty), values);
					   outText("密码重置成功，新密码为："+pw, response);
                } catch (IOException e) {
					outText("err", response);
					e.printStackTrace();
				}
				
		}
			
		
			
	
				 // 用于月结客户
				@RequestMapping(value = { "/muser_list" })
				public String muser_list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					String substationNo1 = params.get("substationNo") ;
					String substationNo ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					
					if(StringUtils.isEmptyWithTrim(substationNo)){
						params.put("substationNo", "0000") ;
					}else{
						if(substationNo1==null||"".equals(substationNo1)){
							params.put("substationNo", substationNo) ;
						}else{
							if(substationNo.contains(substationNo1)){
								params.put("substationNo", substationNo1) ;
							}else{
								params.put("substationNo", "0000") ;
							}
						}
					}
					
//					params.put("substationNo", substationNo);
//					if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
//						
//					}else {
//						if (!substationNo.contains(params.get("substationNo"))) {
//							params.put("substationNo", "0000") ;
//						}
//					}
					pageList = mobileUserService.muserList(params,getPageInfo(cpage)) ;
					List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
					model.put("substations", substations);
					params.put("substationNo", substationNo1) ;
					model.put("params", params);
					model.put("list", pageList);
					return "mobile/muser_list";
				}
				
				
				 // 用于月结客户
				@RequestMapping(value = { "/muser_show" })
				public String muser_show(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Map<String, Object> muserMap = null ;
					if (!StringUtils.isEmptyWithTrim(params.get("id"))) {
						 muserMap = mobileUserService.getMuserById(Integer.valueOf(params.get("id"))) ;
					}
					
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
					List<Map<String, Object>> couriers = courierService.list(params,new Page(1, 5000)).getList();
					List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
					model.put("substations", substations);
					model.put("couriers", couriers);
					model.put("params", params);
					model.put("muserMap", muserMap);
					return "mobile/muser_show";
				}
				
				 // 用于月结客户
				@RequestMapping(value = { "/muser_edit" })
				public String muser_edit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Map<String, Object> muserMap = null ;
					if (!StringUtils.isEmptyWithTrim(params.get("id"))) {
						 muserMap = mobileUserService.getMuserById(Integer.valueOf(params.get("id"))) ;
					}
					
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
				/*	List<Map<String, Object>> couriers = courierService.list(params,new Page(1, 5000)).getList();
					List<Map<String, Object>> substations = userService.getCurrentSubstation();*/
					
				   PageInfo<Map<String, Object>> mslist = monthSettleTypeService.list(null,getPageInfo(1,5000));
				   
				    Map<String, Object> config = lgcConfigService.getByType("MONTH_NO") ;
					model.put("configMap", config);
				    model.put("mslist", mslist.getList());
			/*		model.put("substations", substations);
					model.put("couriers", couriers);*/
					model.put("params", params);
					model.put("muserMap", muserMap);
					return "mobile/muser_edit";
				}
				
				 // 用于月结客户
				@RequestMapping(value = { "/muser_files" })
				public String muser_files(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Map<String, Object> muserMap = null ;
					if (!StringUtils.isEmptyWithTrim(params.get("id"))) {
						 muserMap = mobileUserService.getMuserById(Integer.valueOf(params.get("id"))) ;
					}
					model.put("muserMap", muserMap);
					return "mobile/muser_files";
				}
				
				//用于保存月结客户
				@RequestMapping(value = { "/muser_save"})
				public void muser_save(HttpServletResponse response, HttpServletRequest request,MonthSettleUser mUser) throws Exception{
					 PrintWriter out = response.getWriter();
					try {
						
						Date nowDate = new Date() ;
						List<String> mime = new ArrayList<String>();
						mime.add("image/jpeg");
						mime.add("image/pjpeg");
						mime.add("image/gif");
						mime.add("image/png");
						List<RequestFile> files1 = getFile(request, "file11",
								configInfo.getFile_root(), "/order/"+ DateUtils.formatDate(nowDate, "yyyyMMdd"),mime);
						List<RequestFile> files2 = getFile(request, "file22",
								configInfo.getFile_root(), "/order/"+ DateUtils.formatDate(nowDate, "yyyyMMdd"),mime);
						if (files1.size()>0) {
							mUser.setFile1("/codfile"+files1.get(0).getFilePath());
						}else {
							mUser.setFile1(null);
						}
						if (files2.size()>0) {
							mUser.setFile2("/codfile"+files2.get(0).getFilePath());
						}else {
							mUser.setFile2(null);
						}
						
						if (StringUtils.isEmptyWithTrim(mUser.getHtDateBegin())) {
							mUser.setHtDateBegin(null);
						}
						if (StringUtils.isEmptyWithTrim(mUser.getHtDateEnd())) {
							mUser.setHtDateEnd(null);
						}
						
						 if (!StringUtils.isEmptyWithTrim(mUser.getCourierNo())) {
								if (StringUtils.nullString(mUser.getCourierNo()).contains("(")) {
									mUser.setCourierNo(mUser.getCourierNo().substring(0, mUser.getCourierNo().indexOf("(")));
								}
							   Courier c = courierService.getCourierByNo(mUser.getCourierNo()) ;
							    if (c==null) {
							    	// outText("快递员编号错误", response);
							    	   out.println("<script type=\"text/javascript\">");    
									    out.println(" alert('快递员编号错误！！'); history.back();");    
									    out.println("</script>");
							    	 return ;
								}
							    
							    mUser.setSubstationNo(c.getSubstationNo());
							   /* if (StringUtils.isEmptyWithTrim(mUser.getSubstationNo())) {
									mUser.setSubstationNo(c.getSubstationNo());
								}else {
									if (!c.getSubstationNo().equals(mUser.getSubstationNo())) {
										 outText("快递员与网点对应不上", response);
								    	 return ;
									}
								}*/
						   } 
						if(StringUtil.isEmptyWithTrim(request.getParameter("mid"))){
							   if(mobileUserService.getMuserByNo(mUser.getMonthSettleNo())!=null){
								  // outText("", response);
								   out.println("<script type=\"text/javascript\">");    
								    out.println(" alert('月结号已存在！！'); history.back();");    
								    out.println("</script>");
							   }else {
								    mUser.setMonthNo("b"+sequenceService.getNextVal("month_no"));
								    mUser.setPwd(Md5.md5Str(mUser.getPwd()));
									mUser.setCreateTime(DateUtils.formatDate(new Date()));
									mobileUserService.saveMuser(mUser) ;
							}
							 	
						}else{
							  mUser.setId(Integer.valueOf(request.getParameter("mid")));
							   mobileUserService.updateMuser(mUser) ;
						}
						//outText("1", response);
						out.println("<script type=\"text/javascript\">");    
					    out.println(" alert('保存成功！！'); var api = frameElement.api;api.reload();api.close();");    
					    out.println("</script>");
					} catch (Exception e) {
						e.printStackTrace();
						//outText("数据有误，保存失败", response);
						out.println("<script type=\"text/javascript\">");    
					    out.println(" alert('数据有误，保存失败！！'); history.back();");    
					    out.println("</script>");
					}
				}		
				
				//用于重置月结客户
				@RequestMapping(value = { "/reset_muser"})
				public void reset_muser(HttpServletResponse response, HttpServletRequest request) throws SQLException{
					try {
						if (!StringUtil.isEmptyWithTrim(request.getParameter("id"))) {
							MonthSettleUser mUser = new MonthSettleUser() ;
							mUser.setId(Integer.valueOf(request.getParameter("id")));
							mUser.setPwd(Md5.md5Str("123456"));
							 mobileUserService.resetMuser(mUser);
						}
						outText("1", response);
					} catch (Exception e) {
						e.printStackTrace();
						outText("重置失败", response);
					}
				}		
				
				//用于删除月结客户
				@RequestMapping(value = { "/del_muser"})
				public void del_muser(HttpServletResponse response, HttpServletRequest request) throws SQLException{
					try {
						if (!StringUtil.isEmptyWithTrim(request.getParameter("id"))) {
							 mobileUserService.delMuserById(Integer.valueOf(request.getParameter("id")));	
						}
						outText("1", response);
					} catch (Exception e) {
						e.printStackTrace();
						outText("删除失败", response);
					}
				}
				
				
				 // 用于代收客户列表
				@RequestMapping(value = { "/cuser_list" })
				public String cuser_list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
					String substationNo1 = params.get("substationNo") ;
					String substationNo ;
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
						 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
					}else {
						substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
					}
					
					if(StringUtils.isEmptyWithTrim(substationNo)){
						params.put("substationNo", "0000") ;
					}else{
						if(substationNo1==null||"".equals(substationNo1)){
							params.put("substationNo", substationNo) ;
						}else{
							if(substationNo.contains(substationNo1)){
								params.put("substationNo", substationNo1) ;
							}else{
								params.put("substationNo", "0000") ;
							}
						}
					}
//					params.put("substationNo", substationNo);
//					if(StringUtils.isEmptyWithTrim(params.get("substationNo"))){
//						
//					}else {
//						if (!substationNo.contains(params.get("substationNo"))) {
//							params.put("substationNo", "0000") ;
//						}
//					}
					pageList = codSettleUserService.list(params,getPageInfo(cpage)) ;
					List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
					model.put("substations", substations);
					params.put("substationNo", substationNo1) ;
					model.put("params", params);
					model.put("list", pageList);
					return "mobile/cuser_list";
				}
				
				
				
				 // 用于代收货款客户新增
				@RequestMapping(value = { "/cuser_edit" })
				public String cuser_edit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Map<String, Object> cuserMap = null ;
					if (!StringUtils.isEmptyWithTrim(params.get("id"))) {
						 cuserMap = codSettleUserService.getCuserById(Integer.valueOf(params.get("id"))) ;
					}
					
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
					List<Map<String, Object>> couriers = courierService.list(params,new Page(1, 5000)).getList();
					List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
				   PageInfo<Map<String, Object>> cslist = codRateTypeService.list(null,getPageInfo(1,5000));
				    model.put("cslist", cslist.getList());
					model.put("substations", substations);
					model.put("couriers", couriers);
					model.put("params", params);
					model.put("cuserMap", cuserMap);
					return "mobile/cuser_edit";
				}
				
				
				
				//用于保存 daisho客户
				@RequestMapping(value = { "/cuser_save"})
				public void cuser_save(HttpServletResponse response, HttpServletRequest request,CodSettleUser cUser) throws SQLException{
					try {
						 if (!StringUtils.isEmptyWithTrim(cUser.getCourierNo())) {
							   Courier c = courierService.getCourierByNo(cUser.getCourierNo()) ;
							    if (c==null) {
							    	 outText("快递员编号错误", response);
							    	 return ;
								}
							    
							    if (StringUtils.isEmptyWithTrim(cUser.getSubstationNo())) {
							    	cUser.setSubstationNo(c.getSubstationNo());
								}else {
									if (!c.getSubstationNo().equals(cUser.getSubstationNo())) {
										 outText("快递员与网点对应不上", response);
								    	 return ;
									}
								}
						   } 
						if(StringUtil.isEmptyWithTrim(request.getParameter("mid"))){
							   if(codSettleUserService.getCuserByNo(cUser.getCodNo())!=null){
								   outText("代收货款用户号已存在", response);
							   }else {
								   cUser.setCreateTime(DateUtils.formatDate(new Date()));
								   cUser.setStatus(1);
									codSettleUserService.saveCuser(cUser);
							}
							 	
						}else{
							   cUser.setId(Integer.valueOf(request.getParameter("mid")));
							   codSettleUserService.updateCuser(cUser);
						}
						outText("1", response);
					} catch (Exception e) {
						e.printStackTrace();
						outText("数据有误，保存失败", response);
					}
				}		
				
				//用于删除daishou客户
				@RequestMapping(value = { "/del_cuser"})
				public void del_cuser(HttpServletResponse response, HttpServletRequest request) throws SQLException{
					try {
						if (!StringUtil.isEmptyWithTrim(request.getParameter("id"))) {
							codSettleUserService.delCuserById(Integer.valueOf(request.getParameter("id")));	
						}
						outText("1", response);
					} catch (Exception e) {
						e.printStackTrace();
						outText("删除失败", response);
					}
				}		
				
				//用于代收客户启用状态
				@RequestMapping(value = { "/status_cuser"})
				public void status_cuser(HttpServletResponse response, HttpServletRequest request,String s) throws SQLException{
					try {
						if (!StringUtil.isEmptyWithTrim(request.getParameter("id"))) {
							
						   CodSettleUser cUser = new CodSettleUser() ;
						   cUser.setId(Integer.valueOf(request.getParameter("id")));
							if ("1".equals(s)) {
								cUser.setStatus(1);
							}else {
								cUser.setStatus(0);
							}
							 codSettleUserService.status(cUser) ;
						}
						outText("1", response);
					} catch (Exception e) {
						e.printStackTrace();
						outText("修改失败", response);
					}
				}	
				
				
				 // 用于daishou客户
				@RequestMapping(value = { "/cuser_show" })
				public String cuser_show(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
					Map<String, Object> cuserMap = null ;
					if (!StringUtils.isEmptyWithTrim(params.get("id"))) {
						 cuserMap = codSettleUserService.getCuserById(Integer.valueOf(params.get("id"))) ;
					}
					model.put("cuserMap", cuserMap);
					return "mobile/cuser_show";
				}
				
				
				  // 用于月结优惠
				@RequestMapping(value = { "/mstype" })
				public String mstype(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
				    PageInfo<Map<String, Object>> list =monthSettleTypeService.list(params,getPageInfo(cpage));
					model.put("list", list) ;
					model.put("params", params) ;
					return "mobile/mstype";
				}		
			
				   // 用于月结优惠新增
				@RequestMapping(value = { "/msedit" })
				public String msedit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) {
					MonthSettleType monthSettleType= null ;
				  if (!StringUtils.isEmptyWithTrim(params.get("id"))) {
					  monthSettleType = monthSettleTypeService.getById(Integer.valueOf(params.get("id"))) ;
 				   }
				    model.put("monthSettleType", monthSettleType) ;
					model.put("params", params) ;
					return "mobile/msedit";
				}
				   // 用于月结优惠保存
				@RequestMapping(value = { "/mssave"})
				public void mssave(HttpServletResponse response, HttpServletRequest request,MonthSettleType monthSettleType) throws SQLException{
					String r = "1";
					monthSettleType.setDiscountText(monthSettleType.getDiscount()/10.0+"折");
					try {
						if(monthSettleType.getId() == null){
						  monthSettleTypeService.saveMonthSettleType(monthSettleType) ;
						}else{
							monthSettleTypeService.updateMonthSettleType(monthSettleType) ;
						}
					} catch (Exception e) {
						e.printStackTrace();
						r = "保存失败";
					}
					outText(r , response);
				}	
				
				
				  // 用于代收优惠
				@RequestMapping(value = { "/cstype" })
				public String cstype(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
				    PageInfo<Map<String, Object>> list = codRateTypeService.list(params,getPageInfo(cpage));
					model.put("list", list) ;
					model.put("params", params) ;
					return "mobile/cstype";
				}	
				
				   // 用于代收优惠新增
				@RequestMapping(value = { "/csedit" })
				public String csedit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) {
					CodRateType codRateType = null ;
				  if (!StringUtils.isEmptyWithTrim(params.get("id"))) {
					  codRateType = codRateTypeService.getById(Integer.valueOf(params.get("id"))) ;
 				   }
				    model.put("codRateType", codRateType) ;
					model.put("params", params) ;
					return "mobile/csedit";
				}
				
				  // 用于代收优惠保存
				@RequestMapping(value = { "/cssave"})
				public void cssave(HttpServletResponse response, HttpServletRequest request,CodRateType codRateType) throws SQLException{
					String r = "1";
					codRateType.setDiscountText("千分之"+codRateType.getDiscountText());
					try {
						if(codRateType.getId() == null){
							codRateTypeService.saveCodRateType(codRateType) ;
						}else{
							codRateTypeService.updateCodRateType(codRateType) ;
						}
					} catch (Exception e) {
						e.printStackTrace();
						r = "保存失败";
					}
					outText(r , response);
				}	
						
				
				
}
