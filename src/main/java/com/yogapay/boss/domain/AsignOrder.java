package com.yogapay.boss.domain;

import java.math.BigDecimal;

public class AsignOrder {

	private Long id ;
		
	private String order_no ;
	
	private String order_time ;
	
	private String asign_time ;
	
	private String asing_date ;
	
	private String order_date ;
	
	private String asign_no ;
	
	private String asign_name ;
	
	private BigDecimal asign_duration ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public String getAsign_time() {
		return asign_time;
	}

	public void setAsign_time(String asign_time) {
		this.asign_time = asign_time;
	}

	public String getAsing_date() {
		return asing_date;
	}

	public void setAsing_date(String asing_date) {
		this.asing_date = asing_date;
	}

	public String getAsign_no() {
		return asign_no;
	}

	public void setAsign_no(String asign_no) {
		this.asign_no = asign_no;
	}

	public String getAsign_name() {
		return asign_name;
	}

	public void setAsign_name(String asign_name) {
		this.asign_name = asign_name;
	}

	public BigDecimal getAsign_duration() {
		return asign_duration;
	}

	public void setAsign_duration(BigDecimal asign_duration) {
		this.asign_duration = asign_duration;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	
	
	
}

