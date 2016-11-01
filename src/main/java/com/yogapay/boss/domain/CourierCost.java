package com.yogapay.boss.domain;

public class CourierCost {
    
	private int  id  ;
	
	private String courier_no ;
	
	private String cost_month ;
	
	private String cost_name ;
	
	private float cost_amount ;
	
	private String batch_id ;
	
	private String create_time ;
	
	private String operator ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourier_no() {
		return courier_no;
	}

	public void setCourier_no(String courier_no) {
		this.courier_no = courier_no;
	}

	public String getCost_month() {
		return cost_month;
	}

	public void setCost_month(String cost_month) {
		this.cost_month = cost_month;
	}

	public String getCost_name() {
		return cost_name;
	}

	public void setCost_name(String cost_name) {
		this.cost_name = cost_name;
	}

	public float getCost_amount() {
		return cost_amount;
	}

	public void setCost_amount(float cost_amount) {
		this.cost_amount = cost_amount;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
	 
	
}
