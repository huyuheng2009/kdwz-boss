package com.yogapay.boss.domain;

import java.math.BigDecimal;
import java.util.Date;

public class JoinSubstationAcount {
		private long id;
		private String substationNo;
		private BigDecimal curBalance;
		private BigDecimal warningBalance;
		private BigDecimal shutBalance;
		private Date startTime;
		private Date createTime;
		private int status;
		private String note;
		private String operater;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getSubstationNo() {
			return substationNo;
		}
		public void setSubstationNo(String substationNo) {
			this.substationNo = substationNo;
		}
		public BigDecimal getCurBalance() {
			return curBalance;
		}
		public void setCurBalance(BigDecimal curBalance) {
			this.curBalance = curBalance;
		}
		public BigDecimal getWarningBalance() {
			return warningBalance;
		}
		public void setWarningBalance(BigDecimal warningBalance) {
			this.warningBalance = warningBalance;
		}
		public BigDecimal getShutBalance() {
			return shutBalance;
		}
		public void setShutBalance(BigDecimal shutBalance) {
			this.shutBalance = shutBalance;
		}
		public Date getStartTime() {
			return startTime;
		}
		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		public String getOperater() {
			return operater;
		}
		public void setOperater(String operater) {
			this.operater = operater;
		}
		
}
