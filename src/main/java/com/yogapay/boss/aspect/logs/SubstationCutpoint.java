package com.yogapay.boss.aspect.logs;

import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.Substation;

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
public class SubstationCutpoint extends  CommonCutpoint{
    private final String classz="com.yogapay.boss.service.SubstationService";


    @Around("execution(* "+classz+".save(..))")
    public Object saveLgc(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Substation substation = (Substation) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"快递公司-分站-新增： 分站名称 "+substation.getSubstationName(),bossUser);
        return pjp.proceed();
    }

    @Around("execution(* "+classz+".update(..))")
    public Object updateLgc(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Substation substation = (Substation) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"快递公司-分站-编辑： 分站名称 "+substation.getSubstationName(),bossUser);
        return pjp.proceed();
    }
    
    @Around("execution(* "+classz+".delete(..))")
    public Object delById(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Integer id = (Integer) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"快递公司-分站-删除： 分站id: "+id,bossUser);
        return pjp.proceed();
    }
    
    
    
    
    


}
