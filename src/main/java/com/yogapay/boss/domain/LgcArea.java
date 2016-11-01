package com.yogapay.boss.domain;

public class LgcArea {
	
	private Integer id ;
	
	private String addrArea ;
	
	private String baddr ;
	
	private String naddr ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddrArea() {
		return addrArea;
	}

	public void setAddrArea(String addrArea) {
		this.addrArea = addrArea;
	}

	public String getBaddr() {
		return baddr;
	}

	public void setBaddr(String baddr) {
		this.baddr = baddr;
	}

	public String getNaddr() {
		return naddr;
	}

	public void setNaddr(String naddr) {
		this.naddr = naddr;
	}
	
	public void fix(){
	this.baddr = this.baddr.replace("，", ",") ;
	this.naddr = this.naddr.replace("，", ",") ;
	this.baddr = this.baddr.replace(",,", ",").replace(",,,", ",").replace(",,,,", ",") ;
	this.naddr = this.naddr.replace(",,", ",").replace(",,,", ",").replace(",,,,", ",") ;
	}

}
