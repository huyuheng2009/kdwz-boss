package com.yogapay.boss.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigInfo {
 
	  @Value("#{config['master.url']}")
	  private  String master_url ;
	  
	  @Value("#{config['master.driverClassName']}")
	  private  String master_driverClassName ;
	  
	  @Value("#{config['master.username']}")
	  private  String master_username ;
	  
	  @Value("#{config['master.password']}")
	  private  String master_password ;    //可选择去掉
	  
	  @Value("#{config['file_root']}")
	  private  String file_root ;
	  
	  @Value("#{config['image_root']}")
	  private  String image_root ;
	  
	  @Value("#{config['notice_file']}")
	  private  String notice_file ;
	  
	  @Value("#{config['banner_file']}")
	  private  String banner_file ;
	  
	  @Value("#{config['template_file']}")
	  private  String template_file ;
	  
	  @Value("#{config['push_host']}")
	  private  String push_host ;
	  
	  @Value("#{config['wxpush_host']}")
	  private  String wxpush_host ;
	  
	  @Value("#{config['mapabc_ws_key']}")
	  private  String mapabc_ws_key ;
	  
	  @Value("#{config['mapabc_js_key']}")
	  private  String mapabc_js_key ;
	  
	  @Value("#{config['dev']}")
	  private  String dev ;
	  
	  @Value("#{config['plane_dir']}")
	  private  String plane_dir ;
	  
	  @Value("#{config['dir_root']}")
	  private  String dir_root ;
	  
	  @Value("#{config['EMAIL_USERNSME']}")
	  private  String EMAIL_USERNSME ;
	  
	  @Value("#{config['EMAIL_PASSWORD']}")
	  private  String EMAIL_PASSWORD ;
	  
	  @Value("#{config['dev_key']}")
	  private  String dev_key ;
	  
	  @Value("#{config['debug']}")
	  private  String debug ;
	  
	  @Value("#{config['message_host']}")
	  private  String message_host ;
	  

	public String getMaster_url() {
		return master_url;
	}

	public void setMaster_url(String master_url) {
		this.master_url = master_url;
	}

	public String getMaster_driverClassName() {
		return master_driverClassName;
	}

	public void setMaster_driverClassName(String master_driverClassName) {
		this.master_driverClassName = master_driverClassName;
	}

	public String getMaster_username() {
		return master_username;
	}

	public void setMaster_username(String master_username) {
		this.master_username = master_username;
	}

	public String getMaster_password() {
		return master_password;
	}

	public void setMaster_password(String master_password) {
		this.master_password = master_password;
	}

	public String getFile_root() {
		return file_root;
	}

	public void setFile_root(String file_root) {
		this.file_root = file_root;
	}

	public String getImage_root() {
		return image_root;
	}

	public void setImage_root(String image_root) {
		this.image_root = image_root;
	}

	public String getNotice_file() {
		return notice_file;
	}

	public void setNotice_file(String notice_file) {
		this.notice_file = notice_file;
	}

	public String getBanner_file() {
		return banner_file;
	}

	public void setBanner_file(String banner_file) {
		this.banner_file = banner_file;
	}

	public String getTemplate_file() {
		return template_file;
	}

	public void setTemplate_file(String template_file) {
		this.template_file = template_file;
	}

	public String getPush_host() {
		return push_host;
	}

	public void setPush_host(String push_host) {
		this.push_host = push_host;
	}

	public String getWxpush_host() {
		return wxpush_host;
	}

	public void setWxpush_host(String wxpush_host) {
		this.wxpush_host = wxpush_host;
	}

	public String getMapabc_ws_key() {
		return mapabc_ws_key;
	}

	public void setMapabc_ws_key(String mapabc_ws_key) {
		this.mapabc_ws_key = mapabc_ws_key;
	}

	public String getMapabc_js_key() {
		return mapabc_js_key;
	}

	public void setMapabc_js_key(String mapabc_js_key) {
		this.mapabc_js_key = mapabc_js_key;
	}

	public String getDev() {
		return dev;
	}

	public void setDev(String dev) {
		this.dev = dev;
	}

	public String getPlane_dir() {
		return plane_dir;
	}

	public void setPlane_dir(String plane_dir) {
		this.plane_dir = plane_dir;
	}

	public String getDir_root() {
		return dir_root;
	}

	public void setDir_root(String dir_root) {
		this.dir_root = dir_root;
	}

	public String getEMAIL_USERNSME() {
		return EMAIL_USERNSME;
	}

	public void setEMAIL_USERNSME(String eMAIL_USERNSME) {
		EMAIL_USERNSME = eMAIL_USERNSME;
	}

	public String getEMAIL_PASSWORD() {
		return EMAIL_PASSWORD;
	}

	public void setEMAIL_PASSWORD(String eMAIL_PASSWORD) {
		EMAIL_PASSWORD = eMAIL_PASSWORD;
	}

	public String getDev_key() {
		return dev_key;
	}

	public void setDev_key(String dev_key) {
		this.dev_key = dev_key;
	}

	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}

	public String getMessage_host() {
		return message_host;
	}

	public void setMessage_host(String message_host) {
		this.message_host = message_host;
	}
	
	  
	  
}
