package com.yogapay.boss.exception;

/**
 * Created by zhanggc on 2014/5/19.
 */
public class BusinessException  extends  Exception{

    public BusinessException(){
        super();
    }

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(String msg,Throwable cause){
        super(msg,cause);
    }
}
