package com.yogapay.boss.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yogapay.boss.domain.BossAuth;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.service.AuthService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.ConstantsLoader;
import com.yogapay.boss.utils.StringUtils;

@Component("authcCaptcha")
public class FormAuthenticationCaptchaFilter extends FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	
	private static final Logger log = LoggerFactory
			.getLogger(FormAuthenticationCaptchaFilter.class);

	@Resource
	private UserService userService;
	@Autowired
	private AuthService authService ;
	@Resource
	private SubstationService substationService ;
	
	public String getCaptchaParam() {

		return captchaParam;

	}

	protected String getCaptcha(ServletRequest request) {

		return WebUtils.getCleanParam(request, getCaptchaParam());

	}
    @Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {

		String username = getUsername(request);

		String password = getPassword(request);

		String captcha = getCaptcha(request);
          if (username==null) {
			username = "" ;
		}
       if (password==null) {
		  password = "" ;
	    }
       if (captcha==null) {
		captcha = "" ;
	    }
		boolean rememberMe = isRememberMe(request);

		String host = getHost(request);

		return new UsernamePasswordCaptchaToken(username,

		password.toCharArray(), rememberMe, host, captcha);

	}

/*	@Override
	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		UsernamePasswordCaptchaToken userToken = (UsernamePasswordCaptchaToken)createToken(request, response) ;
		  try {  
	           // 图形验证码验证  
	            //doCaptchaValidate((HttpServletRequest) request, userToken);  
	            Subject subject = getSubject(request, response);  
	            //subject.login(userToken);//正常验证  
	            log.info("User login: {}", userToken.getUsername());
	            return onLoginSuccess(userToken, subject, request, response);  
	        }catch (AuthenticationException e) {  
	        	log.info(userToken.getUsername()+"登录失败--"+e);  
	            return onLoginFailure(userToken, e, request, response);  
	        }
	}*/

/*	private void doCaptchaValidate(HttpServletRequest request,
			UsernamePasswordCaptchaToken userToken) {
		// TODO Auto-generated method stub
		
	}
	
	*/
	
	
   

	// 登录成功操作,这里设置了代理商常用信息
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		System.out.println("onLoginSuccess**************************");
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		userService.failTimes(bossUser.getUserName(), 0);
		String lgcNo = "0" ;
		List<BossAuth> bas = null ;
		List<BossAuth> suAuth = null ;
		boolean sub = false ;
		 HttpServletRequest req = (HttpServletRequest) request;
		 LgcConfig lgcConfig = (LgcConfig) req.getSession().getAttribute("lgcConfig") ;
		if ("root".equals(bossUser.getUserName())) {
			 sub = true ;
			 bas = authService.getAuthLists(null) ;
			 suAuth = authService.getSuAuthList() ;
			 bas.addAll(suAuth) ;
			 //lgcNo = userService.getUserLgcNo(Long.parseLong("-1"));
			 lgcNo = lgcConfig==null?"":lgcConfig.getLgcNo() ;
		}else {
			 
			 if ("admin".equals(bossUser.getUserName())) {
				 sub = true ;
				 bas = authService.getAuthLists("s") ;
				 suAuth = authService.getSuAuthList() ;
				 bas.addAll(suAuth) ;
				
				 lgcNo = lgcConfig==null?"":lgcConfig.getLgcNo() ;
				}else {
				 bas = authService.getAuthListByUserId(bossUser.getId());
				 suAuth = authService.getSuAuthListByUserId(bossUser.getId()) ;
				 bas.addAll(suAuth) ;
				 lgcNo = userService.getUserLgcNo(bossUser.getId());
		   }
		}
		
		if (StringUtils.isEmptyWithTrim(lgcNo)) {
			lgcNo = "0" ;
		}
		bossUser.setLgcNo(lgcNo);
		String substationNo = "0" ;
		
		for (BossAuth b : bas) {
			if ("LISTSUBSTATION".equals(b.getAuthCode())) {
				sub = true ;
				break ;
			}
		}
		if (!sub) {
		   substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
		}else {
			substationNo = userService.getUserSubstationByLgcNo(lgcNo);
		}
		/*if (StringUtils.isEmptyWithTrim(substationNo)) {
			substationNo = "0" ;
		}*/
		String sno = "" ;
		if (substationNo.split(",").length==2) {
			sno = substationNo.split(",")[1].replace("'", "") ;
		}
		HttpSession session = WebUtils.toHttp(request).getSession(true);
		session.setAttribute("warming", "0");
		Substation substation = substationService.getSubstationByNo(sno) ;
		if (substation==null||!"J".equals(substation.getSubstationType())) {
			session.setAttribute("jm", "0");
		}else { 
			String balance = substationService.queryBalance(sno,true) ;
			if (balance==null) {
				session.setAttribute("jm", "0");
			}else {
				session.setAttribute("jm", "1");
				session.setAttribute("balance", balance);
				if (substationService.queryShutBalance(sno)) {
					session.setAttribute("warming", "1");
				}
			}
		}
		session.setAttribute("sno", sno);
		bossUser.setSubstationNo(substationNo);
		bossUser.setSoid("0");
		
		session.setAttribute("user", bossUser);
		session.setAttribute("bas", bas);
	    System.out.println(bossUser.getUserName());
		Map<String, String> param = new HashMap<String, String>();
		SecurityUtils.getSubject().getSession().setTimeout(60000*120);   //120min
		WebUtils.issueRedirect(request, response, getSuccessUrl(), param, true);
		 //登陆日志
        {
            String ip = request.getRemoteAddr();
            userService.saveLog(ip,"登陆",bossUser);
        }
		return false;
	}
	
	

}
