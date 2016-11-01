package com.yogapay.boss.utils;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleLock {

	public static AtomicBoolean lock = new AtomicBoolean(true) ;
	
	public static boolean lock() {
		if (lock.get()) {
			lock.getAndSet(false) ;
			return true ;
		}
		return false;
	}
	
	public static void unlock() {
			lock.getAndSet(true) ;
	}
	
	
}
