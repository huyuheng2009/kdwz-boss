package com.yogapay.boss.enums;

public enum PushUserType {
	USER(1),
	COURIER(2),
	MANAGER(3),
	SUBSTATION(4),
	ALLCOURIER(5),
	ALLUSER(6);
	
	private  int value ;
    //构造器默认也只能是private, 从而保证构造函数只能在内部使用
	PushUserType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
