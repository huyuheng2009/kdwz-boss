package com.yogapay.boss.aspect.logs;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossAuth;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.service.AuthService;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zfj on 2014/6/18.
 */
@Component
@Aspect
public class SystemCutpoint extends  CommonCutpoint{
    private final String aclassz="com.yogapay.boss.service.AuthService";
    private final String uclassz="com.yogapay.boss.service.UserService";

    @Resource
    private AuthService authService;

    @Around("execution(* "+aclassz+".userGroupSave(..))")
    public Object userGroupSave(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Map<String,String> params = (HashMap<String,String>)objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject()
                .getPrincipal();
        String ip = "";
        userService.saveLog(ip,"系统管理-用户群组-新增： 群组名称 "+params.get("group_name"),bossUser);
        return pjp.proceed();
    }

    @Around("execution(* "+aclassz+".userGroupUpdate(..))")
    public Object userGroupUpdate(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Map<String,String> params = (HashMap<String,String>)objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject()
                .getPrincipal();
        String ip = "";
        userService.saveLog(ip,"系统管理-用户群组-修改： 群组名称 "+params.get("group_name"),bossUser);
        return pjp.proceed();
    }

    @Around("execution(* "+aclassz+".delGroup(..))")
    public Object delGroup(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Map<String,String> params = new HashMap<String, String>();
        params.put("id",objs[0]+"");
        PageInfo<Map<String, Object>> mapPage = authService.getGrouplist(params,new Page(1, 1));
        List<Map<String,Object>> list = mapPage.getList();
        if(list.size()>0) {
            BossUser bossUser = (BossUser) SecurityUtils.getSubject()
                    .getPrincipal();
            String ip = "";
            userService.saveLog(ip, "系统管理-用户群组-删除： 群组名称 " + list.get(0).get("group_name"), bossUser);
        }
        return pjp.proceed();
    }

    public Object pstatus(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        BossUser bossUser = (BossUser) SecurityUtils.getSubject()
                .getPrincipal();
        String ip = "";
        userService.saveLog(ip, "系统管理-用户群组-状态： ", bossUser);
        return pjp.proceed();
    }

    @Around("execution(* "+uclassz+".updateUser(..))")
    public Object updateUser(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        BossUser _bossUser = (BossUser)objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"系统管理-系统用户-编辑： 用户名称 "+_bossUser.getUserName(),bossUser);
        return pjp.proceed();
    }

    @Around("execution(* "+uclassz+".saveUser(..))")
    public Object saveUser(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        BossUser _bossUser = (BossUser)objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"系统管理-系统用户-新增： 用户名称 "+_bossUser.getUserName(),bossUser);
        return pjp.proceed();
    }
    
    @Around("execution(* "+uclassz+".cpwd(..))")
    public Object cpwd(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        BossUser _bossUser = (BossUser)objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"系统管理-系统用户-修改/重置密码： 用户名称 "+_bossUser.getUserName(),bossUser);
        return pjp.proceed();
    }
    
    
    

    @Around("execution(* "+uclassz+".delUserById(..))")
    public Object delUserById(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"系统管理-系统用户-删除： 用户名称 "+objs[1],bossUser);
        return pjp.proceed();
    }

    @Around("execution(* "+aclassz+".saveAuth(..))")
    public Object saveAuth(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        BossAuth bossAuth = (BossAuth)objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject()
                .getPrincipal();
        String ip = "";
        userService.saveLog(ip,"系统管理-权限管理-新增： 权限名称 "+bossAuth.getAuthName(),bossUser);
        return pjp.proceed();
    }

    @Around("execution(* "+aclassz+".delAuthById(..))")
    public Object delAuthById(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Map<String,String> params = new HashMap<String, String>();
        params.put("id",objs[0]+"");
        PageInfo<Map<String, Object>> mapPage = authService.getAtuchlist(params,new Page(1, 1));
        List<Map<String,Object>> list = mapPage.getList();
        if(list.size()>0){
            BossUser bossUser = (BossUser) SecurityUtils.getSubject()
                    .getPrincipal();
            String ip = "";
            userService.saveLog(ip,"系统管理-权限管理-删除： 权限名称 "+list.get(0).get("auth_name"),bossUser);
        }
        return pjp.proceed();
    }

}
