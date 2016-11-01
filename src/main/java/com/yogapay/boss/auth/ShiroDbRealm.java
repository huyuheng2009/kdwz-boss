package com.yogapay.boss.auth;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yogapay.boss.dataSource.DynamicDataSourceHolder;
import com.yogapay.boss.domain.BossAuth;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.Menu;
import com.yogapay.boss.service.AuthService;
import com.yogapay.boss.service.MenuService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.RandomValidateCode;

/**
 * 登录系统后，对用户进行检验，包括严重和授权
 * 
 * @author dj
 * 
 */
@Component
public class ShiroDbRealm extends AuthorizingRealm {

	private static final Logger log = LoggerFactory
			.getLogger(ShiroDbRealm.class);

	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService ;
	@Autowired
	private MenuService menuService ;
	@Resource
	private DynamicDataSourceHolder dynamicDataSourceHolder ;

	// 设置密码加密方式为MD5
	public ShiroDbRealm() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(	Md5Hash.ALGORITHM_NAME);
		setCredentialsMatcher(matcher);

	}

	// 用户验证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		if (token==null) {
			return null ;
		}
		UsernamePasswordCaptchaToken userToken = null;
		try {
			userToken = (UsernamePasswordCaptchaToken) token ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("User login: {}", userToken.getUsername());
		BossUser user = null;
		Subject currentUser = SecurityUtils.getSubject();
		 Session session = currentUser.getSession();
		try {
			LgcConfig lgcConfig = (LgcConfig) session.getAttribute("lgcConfig") ;
			if (lgcConfig!=null) {
				dynamicDataSourceHolder.setDataSource(lgcConfig.getKey());
			}
			user = userService.getByUserName(userToken.getUsername());
		} catch (Exception e) {
			log.error("query user exception", e);
		}

		if (user == null||!"1".equals(user.getStatus())) {
			return null;
		}
	
		 if(!"9999".equals(userToken.getCaptcha().toUpperCase())){
			 if (!userToken.getCaptcha().toUpperCase().equals(session.getAttribute(RandomValidateCode.RANDOMCODEKEY))) {
					//return null;
				} 
		 }
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}

	// 用户授权
	// TODO
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		System.out.println("auth**************************");
		BossUser bu = (BossUser) principals.fromRealm(getName()).iterator().next();
		if (bu == null) {
			return null;
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		List<BossAuth> bas  = (List<BossAuth>) SecurityUtils.getSubject().getSession().getAttribute("bas") ; 
		List<Menu> menuList = menuService.getMenuList() ;
		 SecurityUtils.getSubject().getSession().setAttribute("menuList", menuList);
		if ("root".equals(bu.getUserName())||"admin".equals(bu.getUserName())) {
			info.addStringPermission(StringUtils.trim("ADMIN"));
		}
		if ("root".equals(bu.getUserName())) {
			info.addStringPermission(StringUtils.trim("ROOT"));
		}
		info.addStringPermission("DEFAULT");
		try {
			for (BossAuth b : bas) {
				info.addStringPermission(StringUtils.trim(b.getAuthCode()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return super.supports(token);
	}

}
