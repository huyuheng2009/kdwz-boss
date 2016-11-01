package com.yogapay.boss.domain;

import java.util.List;

/**
 * 
 * @author hdb
 * 
 */
public class AuthList implements Comparable<AuthList> {

	private Integer id;
	private String parent_name;
	private List<AuthList> nodeList;
	private String parent_code;
	private String checked;
	private Integer parent_id;

	public AuthList() {
		super();
	}

	public AuthList(Integer id, String parent_name, String checked,
			Integer parent_id) {
		super();
		this.id = id;
		this.parent_name = parent_name;
		this.checked = checked;
		this.parent_id = parent_id;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public List<AuthList> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<AuthList> nodeList) {
		this.nodeList = nodeList;
	}

	public String getParent_code() {
		return parent_code;
	}

	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}

	public int compareTo(AuthList o) {
		if (this.getParent_id() > o.getParent_id()) {
			return 1;
		}
		return -1;
	}

}
