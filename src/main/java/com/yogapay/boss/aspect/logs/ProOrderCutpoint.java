package com.yogapay.boss.aspect.logs;

import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.ProOrder;

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
public class ProOrderCutpoint extends  CommonCutpoint{
    private final String classz="com.yogapay.boss.service.ProOrderService";


    @Around("execution(* "+classz+".save(..))")
    public Object save(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        ProOrder proOrder = (ProOrder) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"新增问题件,运单号："+proOrder.getLgcOrderNo(),bossUser);
        return pjp.proceed();
    }

    

}
