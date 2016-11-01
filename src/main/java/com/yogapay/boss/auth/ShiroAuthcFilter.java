package com.yogapay.boss.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * 表单认证过滤器，可以在这里记录登录成功后的日志记录等操作
 * 
 * @author dj
 * 
 */
//@Component("authc")
public class ShiroAuthcFilter extends FormAuthenticationFilter {

	// 登录成功操作,这里设置了代理商常用信息
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {/*
		BossUser bossUser = (BossUser) SecurityUtils.getSubject()
				.getPrincipal();
		HttpSession session = WebUtils.toHttp(request).getSession(true);
		String agentNo = userService.agentCode(bossUser.getId());
		if (StringUtils.isEmpty(agentNo)) {
			agentNo = "0000";
		}
		bossUser.setAgentNo(agentNo);
		session.setAttribute("user", bossUser);
		String appNames = userService.appName(bossUser.getId());
		if (StringUtils.isNotEmpty(appNames)) {
			session.setAttribute("appNames", appNames);
		}
		Map<String, String> param = new HashMap<String, String>();
		WebUtils.issueRedirect(request, response, getSuccessUrl(), param, true);
        //设置用户创建代理商权限
        {
            boolean check = false;
            List<Map<String,Object>> groups = authService.getGrouplistByUserid(bossUser.getId());
            if(null!=groups){
                for(Map<String,Object> group:groups){
                    if("manager".equals(group.get("group_name"))||"admin".equals(group.get("group_name"))){
                        check = true;
                        break;
                    }
                }
            }
            if(check){
                bossUser.setPermissiveLevel(AgentLevel.FIRST);
            }else{
                List<Map<String,Object>> agentList = agentService.getHighestLevel(bossUser.getId());
                if(null!=agentList&&agentList.size()>0&&null!=agentList.get(0).get("level")){
                    if(3==(Integer)agentList.get(0).get("level")){
                        bossUser.setPermissiveLevel(null);
                    }else{
                        bossUser.setPermissiveLevel(AgentLevel.create(((Integer)agentList.get(0).get("level"))+1));
                    }
                }else{
                    bossUser.setPermissiveLevel(null);
                }
            }
        }
        //登陆日志
        {
            String ip = request.getRemoteAddr();
            userService.saveLog(ip,"登陆",bossUser);
        }
		
	*/return false;}
}
