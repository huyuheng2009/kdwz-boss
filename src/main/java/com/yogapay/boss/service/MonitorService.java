package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.SinputInfo;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtil;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

@SuppressWarnings({"rawtypes", "unchecked"})
@Service
public class MonitorService {

    @Resource
    private BaseDao baseDao;

    public PageInfo<Map<String, Object>> iolist(Map<String, String> params, Page pageInfo) {
    	Map<String, String> pMap = new HashMap<String, String>() ;
    	 String orderNos = StringUtils.nullString(params.get("orderNo"));
        String[] ods = {};
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
            String o = "";
            orderNos = orderNos.replace("'", "");
            ods = orderNos.split("\r\n");
            for (int i = 0; i < ods.length; i++) {
                o = o + "'" + ods[i] + "',";
            }
            o = o.substring(0, o.length()-1) ;
            params.put("orderNos", o);
            pMap.put("orderNos", o);
            pMap.put("substationNo", params.get("substationNo")) ;
        }else {
        	pMap.putAll(params);
        	pMap.remove("orderNos") ;
		}
        PageInfo<Map<String, Object>> list = baseDao.getByPage("Monitor.iolist", pageInfo, pMap);
        return list;
    }
    public List<Map<String, Object>> getIoList(Map<String, String> params) {
     	Map<String, String> pMap = new HashMap<String, String>() ;
   	 String orderNos = StringUtils.nullString(params.get("orderNo"));
       String[] ods = {};
       if (!StringUtil.isEmptyWithTrim(orderNos)) {
           String o = "";
           orderNos = orderNos.replace("'", "");
           ods = orderNos.split("\r\n");
           for (int i = 0; i < ods.length; i++) {
               o = o + "'" + ods[i] + "',";
           }
           o = o.substring(0, o.length()-1) ;
           params.put("orderNos", o);
           pMap.put("orderNos", o);
           pMap.put("substationNo", params.get("substationNo")) ;
           pMap.put("curOrNext", params.get("curOrNext")) ;
       }else {
       	pMap.putAll(params);
       	pMap.remove("orderNos") ;
		}
    	List<Map<String, Object>> list = baseDao.getList("Monitor.iolist", pMap);
    	return list;
    }
    
   

    
}
