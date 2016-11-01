package com.yogapay.boss.aspect.logs;

import com.yogapay.boss.service.UserService;

import javax.annotation.Resource;

/**
 * Created by zfj on 2014/6/17.
 */
public class CommonCutpoint {
    @Resource
    protected UserService userService;
}
