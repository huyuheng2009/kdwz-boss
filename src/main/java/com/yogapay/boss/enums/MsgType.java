package com.yogapay.boss.enums;

public enum MsgType {
    
	NOTICE(499),//通知公告
    LGC(500),      //快件消息
    OTHER(501),   //其他消息
    COME(502),  //上门取件
    TAKE(503),  //快件取走
    SEND(504),  //快件派送
    SIGN(505),  //快件签收
    REFUSE(506),  //快件拒签
    CANCEL(510),  //快件取消
    ASIGNTS(513),    //快件新单，可接单
    ACANCEL(514);    //取消或被取消当前分配
	
	private  int value ;
    //构造器默认也只能是private, 从而保证构造函数只能在内部使用
	MsgType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
	
}
