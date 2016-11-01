package com.yogapay.boss.aspect.logs;

import java.util.Map;

import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Lgc;
import com.yogapay.boss.domain.OrderInfo;

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
public class OrderCutpoint extends  CommonCutpoint{
    private final String classz="com.yogapay.boss.service.OrderService";


    @Around("execution(* "+classz+".save(..))")
    public Object save(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        OrderInfo orderInfo = (OrderInfo) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        if ("BOSS".equals(orderInfo.getSource())) {
        	userService.saveLog(ip,"订单管理-在线下单",bossUser);
		}
        return pjp.proceed();
    }

    @Around("execution(* "+classz+".bing(..))")
    public Object bing(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Map<String, String> params = (Map<String, String>) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"订单管理-订单列表-录入绑定，运单号： "+params.get("lgcOrderNo"),bossUser);
        return pjp.proceed();
    }
    
    @Around("execution(* "+classz+".asign(..))")
    public Object asign(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Map<String, String> params = (Map<String, String>) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"订单管理-订单列表-分配给快递员，订单号： "+params.get("orderNo"),bossUser);
        return pjp.proceed();
    }
    
    @Around("execution(* "+classz+".asignTos(..))")
    public Object asignTos(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Map<String, String> params = (Map<String, String>) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"订单管理-订单列表-分配给分站，订单号： "+params.get("orderNo"),bossUser);
        return pjp.proceed();
    }
     
    
    @Around("execution(* "+classz+".takeUpdate(..))")
    public Object takeUpdate(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        OrderInfo orderInfo = (OrderInfo) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"订单管理-订单列表-订单录入，订单号： "+orderInfo.getOrderNo(),bossUser);
        return pjp.proceed();
    }
    
    @Around("execution(* "+classz+".signUpdate(..))")
    public Object signUpdate(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        OrderInfo orderInfo = (OrderInfo) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"订单管理-订单列表-签收录入，订单号： "+orderInfo.getOrderNo(),bossUser);
        return pjp.proceed();
    }
    
    @Around("execution(* "+classz+".examine(..))")
    public Object examine(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        Map<String, String> params = (Map<String, String>) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"订单管理-订单列表-订单审核保存",bossUser);
        return pjp.proceed();
    }
    
    @Around("execution(* "+classz+".editUpdate(..))")
    public Object editUpdate(ProceedingJoinPoint pjp) throws Throwable{
        Object[] objs = pjp.getArgs();
        OrderInfo orderInfo = (OrderInfo) objs[0];
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        String ip = "";
        userService.saveLog(ip,"订单审核编辑修改，订单号： "+orderInfo.getLgcOrderNo(),bossUser);
        return pjp.proceed();
    }
    
}
