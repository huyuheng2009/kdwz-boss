package com.yogapay.boss.controller;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yogapay.boss.service.CodSettleUserService;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserManagerService;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.StringUtil;


@Controller
@RequestMapping(value="/userManager")
public class UserManagerController extends BaseController {
@Resource
private UserManagerService userManagerService;	
@Resource
private SubstationService substationService;
@Resource
private MobileUserService mobileUserService ;
@Resource
private CodSettleUserService codSettleUserService ;
	//快递员停用
	@RequestMapping(value="/cstop")
	public void cstopStatus(@RequestParam Map<String,String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtil.isEmptyWithTrim(params.get("id"))){
			outText("停用失败", response);
		}else{
			userManagerService.cstop(params.get("id"));
			outText("1", response);			
		}
	}
	
	
	//快递员启用
	@RequestMapping(value="/cstart")
	public void cstart(@RequestParam Map<String,String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtil.isEmptyWithTrim(params.get("id"))){
			outText("启用失败", response);
		}else{
			userManagerService.cstart(params.get("id"));
			outText("1", response);			
		}
	}
	
	//分站停用
	@RequestMapping(value="/sstop")
	public void sstop(@RequestParam Map<String,String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtil.isEmptyWithTrim(params.get("id"))){
			outText("停用失败", response);
		}else{
			userManagerService.sstop(params.get("id"));
			outText("1", response);			
		}
	}
	
	
	//分站启用
	@RequestMapping(value="/sstart")
	public void sstart(@RequestParam Map<String,String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtil.isEmptyWithTrim(params.get("id"))){
			outText("启用失败", response);
		}else{
			userManagerService.sstart(params.get("id"));
			outText("1", response);			
		}
	}
	//分站停用
	@RequestMapping(value="/mstop")
	public void mstop(@RequestParam Map<String,String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtil.isEmptyWithTrim(params.get("id"))){
			outText("停用失败", response);
		}else{
			userManagerService.mstop(params.get("id"));
			outText("1", response);			
		}
	}
	
	
	//分站启用
	@RequestMapping(value="/mstart")
	public void mstart(@RequestParam Map<String,String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtil.isEmptyWithTrim(params.get("id"))){
			outText("启用失败", response);
		}else{
			userManagerService.mstart(params.get("id"));
			outText("1", response);			
		}
	}
	
	//快递员密码重置
	@RequestMapping(value="/cpwd")
	public void cpwd(@RequestParam Map<String,String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtil.isEmptyWithTrim(params.get("id"))){
			outText("重置失败", response);
		}else{
			userManagerService.cpwd(params.get("id"));
			outText("1", response);			
		}
	}
	
	//用于删除分站
	@RequestMapping(value = {"/sdel"})
	public void sdel(@RequestParam Map<String, String> params,
			HttpServletResponse response) throws SQLException {
		if (StringUtil.isEmpty(params.get("id"))) {
			outText("删除失败", response);
		} else {
			if(userManagerService.deleteSubstation(params.get("id"))){
				substationService.delete(Integer.parseInt(params.get("id")));
				outText("1", response);
			}else{
				outText("删除失败,当前分站已有收派件记录,删除可能会影响报表系统。", response);
			}
		
		}
	}
	
	
	//用于删除月结客户
	@RequestMapping(value = { "/del_muser"})
	public void del_muser(HttpServletResponse response, HttpServletRequest request) throws SQLException{
		try {
			if (!StringUtil.isEmptyWithTrim(request.getParameter("id"))) {
				if(userManagerService.deleteMonthUser(request.getParameter("id"))){
					mobileUserService.delMuserById(Integer.valueOf(request.getParameter("id")));
					outText("1", response);
				}else{
					outText("无法删除，当前月结用户已有收派历史，删除将影响报表系统的准确性。", response);	
				}			 	
			}			
		} catch (Exception e) {
			e.printStackTrace();
			outText("删除失败", response);
		}
	}	
	
	//用于删除daishou客户
	@RequestMapping(value = { "/del_cuser"})
	public void del_cuser(HttpServletResponse response, HttpServletRequest request) throws SQLException{
		try {
			if (!StringUtil.isEmptyWithTrim(request.getParameter("id"))) {
				if(userManagerService.deleteCodUser(request.getParameter("id"))){
					codSettleUserService.delCuserById(Integer.valueOf(request.getParameter("id")));
					outText("1", response);
				}else{
					outText("无法删除，当前代收货款用户已有收派历史，删除将影响报表系统的准确性。", response);	
				}			
			}	
		} catch (Exception e) {
			e.printStackTrace();
			outText("删除失败", response);
		}
	}		
	
	
	
	
	
	
	public static void main(String[] args) {
		System.out.println(Md5.md5Str("123456"));
	}
	
}
