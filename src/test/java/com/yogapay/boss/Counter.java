package com.yogapay.boss;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.sun.mail.handlers.text_html;

public class Counter {
	 
	 public   static AtomicInteger count = new AtomicInteger(0);
	 public static Lock lock = new ReentrantLock();// 锁  
    public static void inc()  {
 
        //这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
       //lock.lock();
       System.out.println(count.addAndGet(1));
    // lock.unlock();
    }
 
    public static void main(String[] args) throws InterruptedException {
 
        //同时启动1000个线程，去进行i++计算，看看实际结果
 
        for (int i = 0; i < 1000; i++) {
          Thread t =  new Thread(new Runnable() {
                @Override
                public void run() {
                    Counter.inc();
                }
            });
          t.start() ;
        }
        
        Thread.sleep(4000);
        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + Counter.count);
    }
}