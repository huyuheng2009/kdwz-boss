package com.yogapay.boss.domain;

public class TempFile {
	
	private byte[] bs ;
	
	private String originalFilename ;

	public byte[] getBs() {
		return bs;
	}

	public void setBs(byte[] bs) {
		this.bs = bs;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	
}
