/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.controller;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.init.SelectMap;
import com.yogapay.boss.service.MessageInfoService;
import java.io.IOException;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "messageInfo")
public class MessageInfoController extends BaseController {

    @Resource
    private MessageInfoService messageInfoService;

    @RequestMapping(value = {"/queryInfo"})
    public String queryInfo(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
            @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        params.put("lgcNo", bossUser.getLgcNo());
        PageInfo<Map<String, Object>> list = messageInfoService.queryMessgeRecharge(params, getPageInfo(cpage));
        model.put("list", list);
        model.put("params", params);
        return "message/queryInfo";
    }

    @RequestMapping(value = {"/queryMessage"})
    public String queryMessage(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
            @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        params.put("lgcNo", bossUser.getLgcNo());
        Map<String, Object> map = messageInfoService.queryMessageCount(params);
        PageInfo<Map<String, Object>> list = messageInfoService.queryMessageInfo(params, getPageInfo(cpage));
        model.put("list", list);
        model.put("countMap", map);      
        model.put("params", params);
         model.put("messageMap", SelectMap.messgeMap);
        return "message/queryMessageInfo";
    }

    @RequestMapping(value = {"/queryContacts"})
    public String queryContacts(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request,
            @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
       
        return "message/queryContacts";
    }
}
