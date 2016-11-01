package com.yogapay.boss.aspect;

import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by zfj on 2014/6/17.
 */
@Component
@Aspect
public class RollingCacheService {

    @Resource
    private UserService userService;

    /**
     * 刷新当前用户关联代理商
     * @throws Throwable
     */
    @After("execution(* com.yogapay.boss.service.UserService.saveUser(..))")
    public void saveUser() throws Throwable{/*
        BossUser bossUser = (BossUser) SecurityUtils.getSubject()
                .getPrincipal();
        String agentNo = userService.agentCode(bossUser.getId());
        if (StringUtils.isEmpty(agentNo)) {
            agentNo = "0000";
        }
        //bossUser.setAgentNo(agentNo);
    */}
}
