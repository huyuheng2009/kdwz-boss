package com.yogapay.boss.controller;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.AuthenticationFailedException;
import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.AuthList;
import com.yogapay.boss.domain.BossAuth;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.ItemType;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.domain.SystemPunish;
import com.yogapay.boss.service.AuthService;
import com.yogapay.boss.service.LgcService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.SystemPunishService;
import com.yogapay.boss.service.SystemService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.SendMail;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

/**
 * 系统登录，注销，首页等
 * 
 * @author dj
 * 
 */
@Controller
@RequestMapping(value = "/system")
public class SystemController extends BaseController {
    @Resource
    private UserService userService ;
    @Resource
    private AuthService authService ;
    @Resource
    private SystemService systemService ;
    @Resource
    private LgcService lgcService ;
    @Resource
    private SubstationService substationService;
    @Resource
    private SystemPunishService systemPunishService ;

    
	@RequestMapping(value = { "/user" })
	public String ulist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
		BossUser user = Constants.getUser() ;
        /* if (!authService.isUserGroup(user.getId().toString(), "admin")) {
        	params.put("createOperator", user.getUserName()) ; 
		}*/
		setSubstationNo(params, request) ;
		Map<String, String> pMap = new HashMap<String, String>(params) ;
		if ("root".equals(user.getUserName())) {
			pMap.put("root", "1") ;
		}
		
		if("root".equals(user.getUserName())||"admin".equals(user.getUserName())){
			pMap.put("substationNo", "") ;
		}
		pageList = userService.getUserlist(pMap, getPageInfo(cpage));
		model.put("params", params);
		model.put("list", pageList);
		return "system/user_list";
	}		
	
	  // 用于
		@RequestMapping(value = { "/ugroup" })
		public String glist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			pageList = authService.getGrouplist(params, getPageInfo(cpage));
			model.put("params", params);
			model.put("list", pageList);
			return "system/group_list";
		}		
		
		
		  // 用于
		@RequestMapping(value = { "/auth" })
		public String alist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			pageList = authService.getAtuchlist(params, getPageInfo(cpage));
			model.put("params", params);
			model.put("list", pageList);
			return "system/auth_list";
		}		
		
		  // 用于
		@RequestMapping(value = { "/log" })
		public String llist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			pageList = systemService.getLoginLog(params, getPageInfo(cpage));
			model.put("params", params);
			model.put("list", pageList);
			return "system/loginlog";
		}		
		

		
		@RequestMapping(value = "/ushow")
		public String ushow(final ModelMap model,@RequestParam Map<String, String> params,HttpServletRequest request)
				throws SQLException {
			BossUser user = Constants.getUser() ;
            boolean admin = true ;
            if ("admin".equals(user.getUserName())||"root".equals(user.getUserName())) {
				admin = true ;
			}
			Object userName = params.get("userName");
			if (null != userName) {
				Object id = params.get("id");
				BossUser bu = userService.getByUserName(userName.toString());
				model.put("params", bu);
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				if (admin) {
					list = authService.getUserGroupList(null,Long.parseLong(id.toString()),admin,true);
				}else {
					System.out.println(user.getId().toString()+"login_id***************");
					list = authService.getUserGroupList(user.getId().toString(),Long.parseLong(id.toString()),admin,true);
				}
				model.put("list", list);
			} else {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				if (admin) {
					list = authService.getUserGroupList();
				}else {
					list = authService.getUserGroupList(user.getId().toString(),user.getId(),false,false);
				}
				model.put("list", list);
			}
			Map<String, String> userParams = new HashMap<String, String>() ;
			
			if (!"root".equals(user.getUserName())) {
				userParams.put("lgcNo", user.getLgcNo()) ;
				if (!"admin".equals(user.getUserName())) {
					if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
					 	BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
						String substationNo ;
						substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
						userParams.put("substationNo", substationNo) ;
					}
				}
			}
			
			/*
			if (!admin) {
				
				//没有查看所有分站权限
				
				System.out.println("*********************");
			}*/
			PageInfo<Map<String, Object>> lgcList = lgcService.list(userParams,getPageInfo(1,500));
			model.put("lgcList", lgcList.getList()) ;
			
			PageInfo<Map<String, Object>> substationList = substationService.ulist(userParams,getPageInfo(1,500));
			model.put("substationList", substationList.getList()) ;
			
			return "system/user_show";
		}
		
		
		@RequestMapping(value = "/usave")
		public void usave(HttpServletResponse response, HttpServletRequest request,
				BossUser bu) throws SQLException{
			Substation sub=substationService.getSubstationByNo(bu.getSno());
			if(sub==null||"0".equals(sub.getStatus())){
				outText("3", response);
				return ;
			}
			
			int r = 0;
			String userGroup  = request.getParameter("userGroup");
			String[] values = request.getParameterValues("userGroup");
			values=userGroup.split(",");
			
			String lgcNos = request.getParameter("lgcNos");
			String[] lgcNoList = lgcNos.split(",");
			
			String substationNos = request.getParameter("substationNos");
			String[] substationNoList = substationNos.split(",");
			
			
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			if (StringUtil.isEmptyWithTrim(lgcNos)) {
				lgcNos = "0" ;
			}
			if (StringUtil.isEmptyWithTrim(substationNos)) {
				substationNos = "0" ;
			}
			if (bu.getId()==bossUser.getId()&&bu.getId()!=0) {
				bossUser.setLgcNo(lgcNos);
				bossUser.setSubstationNo(substationNos);
				SecurityUtils.getSubject().getSession().setAttribute("user", bossUser);
			}
			
			
			try {
				if (null != bu.getId()) {
	                r=1;
	                bu.setUpdateTime(DateUtils.formatDate(new Date()));
	                bu.setFailTimes(-1);
					userService.updateUser(bu, values,lgcNoList, substationNoList);
				} else {
					r = 1;
					String pwd = bu.getUserName();
					String uname = bu.getUserName();
					uname = uname.substring(0, uname.length() - 2);
					uname += "**";
//					new SendMail().send(bi.getEmail(), "用户创建成功", "您的用户创建成功，用户名："
//							+ uname + "，密码：" + pwd + ",请及时修改密码。");
					bu.setCreateTime(DateUtils.formatDate(new Date()));
					bu.setPassword(Md5.md5Str(pwd));
					bu.setCreateOperator(Constants.getUser().getUserName());
					if ("root".equals(bu.getUserName())||"admin".equals(bu.getUserName())) {
						r = 2 ;
					}else if (userService.isExist(bu)) {
					r = 2 ;
					}else {
						userService.saveUser(bu, values,lgcNoList, substationNoList);
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				r = 0;
			}
			outText(r + "", response);
		}
		
		
		// 禁用用户
		@RequestMapping(value = { "/ustatus" }, method = RequestMethod.GET)
		public void ustatus(HttpServletRequest request,HttpServletResponse response, Long id, String userName,
				String s) throws SQLException {
			BossUser bu = new BossUser();
			bu.setId(id);
			bu.setUserName(userName);
			bu.setStatus(s);
			bu.setUpdateTime(DateUtils.formatDate(new Date()));
			userService.updateUser(bu, null,null, null);
			outText("1", response);
		}
		
		// 用户删除
		@RequestMapping(value = "/udel")
		public void udel(final ModelMap model,
	                     HttpServletRequest request,
	                     HttpServletResponse response,
				@RequestParam Map<String, String> params) {
			long id = Long.parseLong(params.get("id").toString()) ;
			String userName = params.get("userName").toString();
			try {
				userService.delUserById(id, userName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				outText("删除失败！", response);
			}
			outText("1", response);
		}
		
		// 重置用户
				@RequestMapping(value ="/ureset")
				public void ureset(HttpServletRequest request,HttpServletResponse response, Long id, String userName,String email) throws SQLException {
					BossUser bu = new BossUser();
					bu.setId(id);
					bu.setUserName(userName);
					//String pw = StringUtil.getRandomString(6) ;
					String pw = "1234qwer" ;
					bu.setPassword(Md5.md5Str(pw));
					bu.setUpdateTime(DateUtils.formatDate(new Date()));
					bu.setStatus("1");
					//userService.updateUser(bu, null,null,null);
					userService.cpwd(bu);
					if(email!=null && !"".equals(email)){
						try {
							if (StringUtil.isEmptyWithTrim(email)) {
								throw new SendFailedException("邮箱为空") ;
							}
							String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
							//邮件内容
							String mailContent = "尊敬的用户：<br/>" +
							                     "&nbsp;&nbsp;&nbsp;&nbsp;您好！您于"+date+"重置密码,新密码为：<b style=\"color:red\">"+pw+"</b>,请尽快修改！<br/><br/><br/><hr>"+
							                     "<a href=http://www.yogapay.com><img src=\"cid:a00000001\"><br/><br/></a> "+
							                     "公 司：深圳支付界科技有限公司<br/>"+
							                     "客服热线：400-808-0202<br/>"+
							                     "网 址:www.yogapay.com<br/>"+
							                     "地 址：深圳市南山区西丽大学城学苑大道1068号西侧国家超算深圳中心（深圳云计算中心）13层";
							// 内嵌了多少张图片，如果没有，则new一个不带值的Map
							Map<String, String> image = new HashMap<String, String>();
							

							image.put("a00000001", request.getSession().getServletContext().getRealPath("/")+"/themes/default/images/mail.png");
						   new SendMail().send(email, "用户密码重置", mailContent,image);
							   outText("密码重置成功，新密码为："+pw, response);
						}catch(SendFailedException e){
							outText("密码重置成功，新密码为："+pw+",邮件发送失败", response);
							e.printStackTrace();
						}catch(AuthenticationFailedException e){
							outText("密码重置成功，新密码为："+pw+",邮件发送失败", response);
							e.printStackTrace();
						}catch (Exception e1) {
							outText("err", response);
							e1.printStackTrace();
						}
					}else{
						 outText("密码重置成功，新密码为："+pw, response);
					}
						
						
				}
		
				// 群组权限
				@RequestMapping(value = "/groupauth")
				public String groupauth(final ModelMap model, Long id) {
					try {
						if (id != null) {
							Map<String, Object> userGroupInfo = authService.queryUserGroupInfo(id);
							model.put("params", userGroupInfo);
							List<AuthList> list = new ArrayList<AuthList>();
							list = authService.getAuthList(id);
							model.put("list", list);
						} else {
							List<AuthList> list = new ArrayList<AuthList>();
							list = authService.getAuthList();
							model.put("list", list);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					return "system/group_auth";
				}	
				
				
				@RequestMapping(value = "/groupauthsave")
				public String groupauthsave(@RequestParam Map<String, String> params,
						HttpServletRequest request) throws SQLException {
					String[] values = request.getParameterValues("authGroup");
					String id = params.get("id");
					if (id == null || id.trim().length() == 0) {
						authService.userGroupSave(params, values);
					} else {
						authService.userGroupUpdate(params, values);
					}
					return "redirect:ugroup";
				}

	// 群组删除
	@RequestMapping(value = "/gdel")
	public void gdel(final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> params) throws SQLException {
		long id = Integer.valueOf(params.get("id").toString());
		if (authService.checkUserGroupId(id).size() != 0) {
			outText("请先移除群组内用户", response);
		} else {
			outText(authService.delGroup(id) + "", response);
		}
	}
	
	// 资源显示
		@RequestMapping(value = "/ashow")
		public String ashow(final ModelMap model,
				@RequestParam Map<String, String> params,
				@RequestParam(value = "p", defaultValue = "1") int cpage)
				throws SQLException {
			List<Map<String, Object>> authList = authService.getParentAuthListAll();
			model.put("authList", authList);
			return "system/auth_show";
		}
	
		
		@RequestMapping(value = "/asave")
		public void asave(HttpServletRequest request,HttpServletResponse response, BossAuth ba) throws SQLException{
			String r = "";
			ba.setCreateTime(DateUtils.formatDate(new Date()));
			try {
				Map<String, Object> a = authService.getAuthByNameCode(ba) ;
				if (a!=null) {
					r = "保存失败";
					if (a.get("auth_code").toString().equals(ba.getAuthCode())) {
						r = "权限代码重复，保存失败";
					}
					if (a.get("auth_name").toString().equals(ba.getAuthName())) {
						r = "权限名称重复，保存失败";
					}
				}else {
					authService.saveAuth(ba);
					r = "1";
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				r = "数据有误，保存失败";
			}
			outText(r + "", response);
		}
	
		// 权限删除
		@RequestMapping(value = "/adel")
		public void adel(final ModelMap model,
	                     HttpServletRequest request,
	                     HttpServletResponse response,
				@RequestParam Map<String, String> params) throws SQLException {
			long id = Integer.valueOf(params.get("id").toString());
			if (authService.findAuthByParentId(id).size() != 0) {
				outText("请先删除子权限", response);
			} else {
				outText(authService.delAuthById(id) + "", response);
			}
		}
			
		// 处罚条例
		@RequestMapping(value = "/punish")
		public String punish(final ModelMap model,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> params) throws SQLException {	
			List<Map<String, Object>> list = systemPunishService.list(params, getPageInfo(1,300)).getList() ;
			model.put("list", list) ;
			return "system/punish";
		}
		
		@RequestMapping(value = { "/punish_save"})
		public void punish_save(HttpServletResponse response, HttpServletRequest request,SystemPunish systemPunish) throws SQLException{
			int r = 0;
			try {
				if(systemPunish.getId()== null){
					systemPunishService.save(systemPunish);
					r = 1;
				}else{
					systemPunishService.update(systemPunish) ;
					r = 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
				r = 0;
			}
			outText(r + "", response);
		}
		
		
		//用于编辑物品类型
		@RequestMapping(value = { "/punish_edit"})
		public String punish_edit(final ModelMap model,
				@RequestParam Map<String, String> params){
			if(params.get("id") != null){
				Integer id = Integer.parseInt(params.get("id"));
				SystemPunish systemPunish = systemPunishService.getById(id) ;
				model.put("systemPunish", systemPunish);
			}
			return "system/punish_edit";
		}
		
		@RequestMapping(value = {"/punish_del"})
		public void punish_del(@RequestParam Map<String, String> params,
				HttpServletResponse response) throws SQLException {
			if (StringUtil.isEmpty(params.get("id"))) {
				outText("删除失败", response);
			} else {
				systemPunishService.delById(Integer.parseInt(params.get("id")));
				outText("1", response);
			}
		}
		
		
		
		
		@RequestMapping(value = "/sudo")
		public String sudo(final ModelMap model,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> params) throws SQLException {		
			List<BossAuth> suList = authService.getSuAuthList() ;
			model.put("params", params) ;
			model.put("suList", suList) ;
			return "system/sudo";
		}
			
		@RequestMapping(value = {"/sudo_save"})
		public void sudo_save(@RequestParam Map<String, String> params,
				HttpServletResponse response)  {
			try {
				 if (!StringUtil.isEmptyWithTrim(params.get("tid"))) {
					 String ids = null ;
					 if (!StringUtil.isEmptyWithTrim(params.get("ids"))) {
						 ids = params.get("ids") ;
					}
					 authService.updateSuAuth(params.get("tid"), ids);
				}
				outText("1", response);
			} catch (Exception e) {
				outText("保存失败", response);
			}
			
		}	
		
}
