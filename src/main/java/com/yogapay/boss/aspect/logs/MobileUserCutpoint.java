package com.yogapay.boss.aspect.logs;

import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.MonthSettleUser;
import com.yogapay.boss.domain.User;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by zfj on 2014/6/18.
 */
@Component
@Aspect
public class MobileUserCutpoint extends  CommonCutpoint{
    private final String classz="com.yogapay.boss.service.MobileUserService";


    @Around("execution(* "+classz+".updateUser(..))")
    public Object updateUser(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        User user = (User) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"客户管理-普通用户-重置：用户： "+user.getUserName(),bossUser);
        return pjp.proceed();
    }

    @Around("execution(* "+classz+".status(..))")
    public Object status(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        User user = (User) objs[0];
        String t = "启用" ; 
        if (user.getStatus()!=1) {
        	t = "关闭" ; 
		}
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"客户管理-普通用户-"+t+"：用户： "+user.getUserName(),bossUser);
        return pjp.proceed();
    }
    
    @Around("execution(* "+classz+".saveMuser(..))")
    public Object saveMuser(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        MonthSettleUser monthSettleUser = (MonthSettleUser) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"客户管理-月结用户-新增： 月结账号: "+monthSettleUser.getMonthSettleNo(),bossUser);
        return pjp.proceed();
    }
    
    @Around("execution(* "+classz+".updateMuser(..))")
    public Object updateMuser(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        MonthSettleUser monthSettleUser = (MonthSettleUser) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"客户管理-月结用户-编辑： 月结账号: "+monthSettleUser.getMonthSettleNo(),bossUser);
        return pjp.proceed();
    }
    
    @Around("execution(* "+classz+".delMuserById(..))")
    public Object delMuserById(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Integer id = (Integer) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"客户管理-月结用户-删除： 月结id: "+id,bossUser);
        return pjp.proceed();
    }
    
    
    


}
