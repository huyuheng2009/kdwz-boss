package com.yogapay.boss.aspect.logs;

import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Lgc;
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
public class LgcCutpoint extends  CommonCutpoint{
    private final String classz="com.yogapay.boss.service.LgcService";


    @Around("execution(* "+classz+".saveLgc(..))")
    public Object saveLgc(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Lgc lgc = (Lgc) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"快递公司-新增： 公司名称 "+lgc.getName(),bossUser);
        return pjp.proceed();
    }

    @Around("execution(* "+classz+".updateLgc(..))")
    public Object updateLgc(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Lgc lgc = (Lgc) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"快递公司-编辑： 公司名称 "+lgc.getName(),bossUser);
        return pjp.proceed();
    }
    
    @Around("execution(* "+classz+".delById(..))")
    public Object delById(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Integer id = (Integer) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"快递公司-删除： 公司id: "+id,bossUser);
        return pjp.proceed();
    }
    
    
    
    
    


}
