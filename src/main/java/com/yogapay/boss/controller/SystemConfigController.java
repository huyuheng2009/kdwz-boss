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
import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jpos.iso.IF_CHAR;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dataSource.MDataSource;
import com.yogapay.boss.domain.AuthList;
import com.yogapay.boss.domain.BossAuth;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.service.AuthService;
import com.yogapay.boss.service.BossSqlService;
import com.yogapay.boss.service.LgcService;
import com.yogapay.boss.service.ManagerService;
import com.yogapay.boss.service.ProjectDsService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.SystemService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.SendMail;
import com.yogapay.boss.utils.SimpleLock;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

/**
 * 系统登录，注销，首页等
 * 
 * @author dj
 * 
 */
@Controller
@RequestMapping(value = "/systemconfig")
public class SystemConfigController extends BaseController {
    @Resource
    private BossSqlService bossSqlService ;
    @Resource
    private ProjectDsService projectDsService ;
    @Resource
    private ManagerService managerService ;
    @Resource
    private SequenceService sequenceService ;
	@Value("#{config['master.url']}")
	private String dbUrl ;
	@Value("#{config['master.username']}")
	private String dbUser ;
	@Value("#{config['master.password']}")
	private String dbPw ;

    
	@RequestMapping(value = { "/init" })
	public String init(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return "systemconfig/init";
	}		
	
	@RequestMapping(value = { "/save" })
	public void save(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		boolean lock = false ;
		 try {
		
			 //简单锁
	     if (!SimpleLock.lock()) {
				 outText("当前正在操作，请稍后再试", response);
		          return ;
		  }else {
			lock = true ;
		}
			 
		BossUser user = Constants.getUser() ;
		if (!"root".equals(user.getUserName())) {
			outText("当前用户无法接入", response);
            return ;
		}
		
		String name = StringUtils.nullString(params.get("lgcName")).replace(",", "").replace("，", "") ;
		String key = StringUtils.nullString(params.get("key")) ;
		String login = StringUtils.nullString(params.get("login")) ;
		if (StringUtils.isEmptyWithTrim(name)||StringUtils.isEmptyWithTrim(key)||StringUtils.isEmptyWithTrim(login)) {
			outText("当前无法接入,请完整信息", response);
            return ;
		}
		MDataSource ds =   projectDsService.getByKey(key) ;
		if (ds!=null) {
			outText("新建失败，英文域名已经存在", response);
            return ;
		}
		Map<String, Object> lgcConfig = managerService.getLgcConfig(login) ;
		if (lgcConfig!=null) {
			outText("新建失败，验证码已经存在", response);
            return ;
		}
		String lgcNo = sequenceService.getNextVal("lgc_no", true) ;
		String dbName = key+ "_lgc" ;
		String url = "jdbc:mysql://127.0.0.1:3306/"+dbName+"?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull" ;
		String host = key+"-boss.yogapay.com" ;
		String dsSql = "INSERT INTO `manager_lgc`.`project_ds` (`key`, `lgc_no`, `descb`, `host`, `pro_key`, `db_url`, `db_username`, `db_password`, `min_idle`, `max_active`, `initial_size`, `max_wait`, `time_between_eviction_runs_millis`, `min_evictableIdle_time_millis`, `max_pool_prepared_statement_per_connection_size`, `status`, `create_time`)"
				   + " VALUES ('"+key+"', '"+lgcNo+"', '"+name+"', '"+host+"', 'codtsi1', '"+url+"', '"+dbUser+"', '"+dbPw+"', '1', '50', '1', '10000', '60000', '1800000', '50', '数据源', '"+DateUtils.formatDate(new Date())+"')";
		String configSql = "INSERT INTO `manager_lgc`.`lgc_config` (`lgc_no`, `lgc_key`, `status`, `descr`) VALUES ('"+lgcNo+"', '"+login+"', 1, '"+name+"')" ;
		
		
		
		
		 List<Map<String, Object>>  sqlList = bossSqlService.list() ;
		  String lgcUpdate = "update lgc set lgc_no='"+lgcNo+"',name='"+name+"'" ;
			Map<String, Object> map = new HashMap<String, Object>() ;
			map.put("sqlstring", lgcUpdate) ;
			sqlList.add(map) ;
			boolean r = bossSqlService.createDBAndExcute(dbName, sqlList, true,"");
		  //执行manager库的sql
		  List<String>  mList  = new ArrayList<String>() ;
		  mList.add(dsSql) ;
		  mList.add(configSql) ;
		   if (r) {
			r = bossSqlService.createDBAndExcute1("manager_lgc", mList, false, "") ;
		  }
		  if (r) {
			  lock = true ;
			  outText("1", response);
		   }else {
			   outText("新建失败，后台正在收集错误", response);
		  }
		} catch (Exception e) {
			e.printStackTrace();
			outText("新建失败，后台正在收集错误", response);
		}finally{
			if (lock) {
				SimpleLock.unlock();
			}
			
		}
		
		
	}

}
