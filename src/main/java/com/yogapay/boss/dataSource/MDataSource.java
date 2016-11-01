package com.yogapay.boss.dataSource;

public class MDataSource {
 
	private Integer id ;
	
	private String key ;
	
	private String lgcNo ; 
	
	private String host ;
	
	private String proKey ;
	
	private String dbUrl ;
	
	private String dbUsername ;
	
	private String dbPassword ;
	
	private String minIdle ;
	
	private String maxActive ;
	
	private String initialSize ;
	
	private String maxWait ;
	
	private String timeBetweenEvictionRunsMillis;
	
	private String minEvictableIdleTimeMillis;
	
	private String maxPoolPreparedStatementPerConnectionSize ;
	
	private String note ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	
	public String getLgcNo() {
		return lgcNo;
	}

	public void setLgcNo(String lgcNo) {
		this.lgcNo = lgcNo;
	}

	public String getProKey() {
		return proKey;
	}

	public void setProKey(String proKey) {
		this.proKey = proKey;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(String minIdle) {
		this.minIdle = minIdle;
	}

	public String getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}

	public String getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(String initialSize) {
		this.initialSize = initialSize;
	}

	public String getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(String maxWait) {
		this.maxWait = maxWait;
	}

	public String getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(
			String timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public String getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(
			String maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	
	
}
