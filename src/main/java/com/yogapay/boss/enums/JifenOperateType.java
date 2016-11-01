package com.yogapay.boss.enums;

/**
 * @Todo 积分操作类型
 * Created by zfj on 2014/10/9.
 */
public enum JifenOperateType {
    IN("in"),//加积分
    OUT("out"),//减积分
    ADJUSTIN("adjust_in"),//调分进
    ADJUSTOUT("adjust_out");//调分出

    private String name;
    JifenOperateType(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
