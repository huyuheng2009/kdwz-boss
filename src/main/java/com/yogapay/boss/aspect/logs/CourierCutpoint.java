package com.yogapay.boss.aspect.logs;

import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Courier;
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
public class CourierCutpoint extends  CommonCutpoint{
    private final String classz="com.yogapay.boss.service.CourierService";


    @Around("execution(* "+classz+".save(..))")
    public Object save(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Courier courier = (Courier) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"快递公司-快递员-新增： 用户名： "+courier.getUserName(),bossUser);
        return pjp.proceed();
    }

    @Around("execution(* "+classz+".update(..))")
    public Object update(ProceedingJoinPoint pjp) throws Throwable{
    	 Object[] objs = pjp.getArgs();
         Courier courier = (Courier) objs[0];
         BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
         String ip = "";
         userService.saveLog(ip,"快递公司-快递员-编辑： 用户名： "+courier.getUserName(),bossUser);
         return pjp.proceed();
    }
    
    @Around("execution(* "+classz+".delete(..))")
    public Object delete(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Integer id = (Integer) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"快递公司-快递员-删除： 分站id: "+id,bossUser);
        return pjp.proceed();
    }
    
    
    
    
    


}
