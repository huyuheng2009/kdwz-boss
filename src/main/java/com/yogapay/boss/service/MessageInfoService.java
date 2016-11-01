/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class MessageInfoService {

    @Resource
    private BaseDao baseDao;

    public PageInfo<Map<String, Object>> queryMessgeRecharge(Map<String, String> params, Page pageInfo) {
        PageInfo<Map<String, Object>> page = baseDao.getByPage("Message.queryMessgeRecharge", pageInfo, params);
        return page;
    }
    
    public PageInfo<Map<String, Object>> queryMessageInfo(Map<String, String> params, Page pageInfo) {
        PageInfo<Map<String, Object>> page = baseDao.getByPage("Message.queryMessageInfo", pageInfo, params);
        return page;
    }
    
    public Map<String, Object> queryMessageCount(Map<String, String> params) {        
        return  baseDao.getOne("Message.queryMessageCount", params);
    }
    
    public int msgCount(String lgcNo) {        
    	Map<String, Object> params = new HashMap<String, Object>() ;
    	params.put("lgcNo", lgcNo) ;
        Map<String, Object> retMap = baseDao.getOne("Message.msgCount", params);
        int c = 0 ;
        if (retMap!=null&&retMap.get("messagecount")!=null) {
			c=Integer.valueOf(retMap.get("messagecount").toString()) ;
		}
        return c ;
    }
    
    
    
}
