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
public class UserAddrCutpoint extends  CommonCutpoint{
    private final String classz="com.yogapay.boss.service.UserAddrService";


    @Around("execution(* "+classz+".addBatchOrder(..))")
    public Object addBatchOrder(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"订单管理-批量下单 ",bossUser);
        return pjp.proceed();
    }

    
    
    


}
