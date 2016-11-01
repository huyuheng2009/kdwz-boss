/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.init;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class SelectMap {
    
    public static Map<String,Object> payMap = new HashMap<String,Object>();
    public static Map<String,Object> freightMap = new HashMap<String,Object>();
    public static Map<String,Object> messgeMap = new HashMap<String,Object>();
    static {
        freightMap.put("1", "寄方付");
        freightMap.put("2", "到方付");
        payMap.put("CASH", "现金");
        payMap.put("MONTH", "月结账户");
//        payMap.put("HUIYUAN", "会员");
        messgeMap.put("00","成功");
        messgeMap.put("01","失败");
        messgeMap.put("02","超时");
                
    }
}
