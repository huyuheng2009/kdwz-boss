package com.yogapay.boss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.service.WarehouseStaffService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@Controller
@RequestMapping("/warehouseStaff")
public class WarehouseStaffController extends BaseController{
	@Resource
	private WarehouseStaffService warehouseStaffService;
	
	@RequestMapping("queryList")
	public String queryList(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		PageInfo<Map<String,Object>> list = warehouseStaffService.selectWStaff(params, getPageInfo(cpage));
		List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
		model.put("substations", substations);
		model.put("params", params);
		model.put("list", list);
		return"lgc/warestaff";
	}
	
	@RequestMapping("restPwd")
	public void restPwd(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		String pw = "1234" ;
		params.put("passWord", pw);
		try {
			warehouseStaffService.updateByUserPwd(params);
			outText("重置成功，密码为1234", response);
		}catch (Exception e1) {
			outText("err", response);
			e1.printStackTrace();
		}
		
	}
	
	@RequestMapping("changeStatus")
	public void changeStatus(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		try {
			warehouseStaffService.updateByUserStatus(params);
			outText("修改成功", response);
		}catch (Exception e1) {
			outText("err", response);
			e1.printStackTrace();
		}
	}
	
	@RequestMapping("deleteById")
	public void deleteById(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		try {
			warehouseStaffService.deleteById(params);
			outText("删除成功", response);
		}catch (Exception e1) {
			outText("err", response);
			e1.printStackTrace();
		}
	}
	
	@RequestMapping("sedit")
	public void sedit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		if(params.get("id")!=null&&!"".equals(params.get("id").toString())){
			warehouseStaffService.updateByUserId(params);
			outText("修改成功", response);
			return;
		}else{
			if(params.get("userName")==null||"".equals(params.get("userName").toString().trim())){
				outText("账号不能为空", response);
				return;
			}
			params.put("passWord", Md5.md5Str(params.get("userName").toString()));
			params.put("status", "1");
			params.put("createOperator", Constants.getUser().getUserName());
			warehouseStaffService.insertWStaff(params);
			outText("新增成功", response);
			return;
		}
		
	}
	
	@RequestMapping("seditInit")
	public String seditInit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		if(params.get("id")!=null){
			Map<String,Object> m=warehouseStaffService.queryById(params.get("id"));
			model.put("wareStaff", m);
		}
		return "/lgc/warestaff_edit";
		
	}
	@RequestMapping("detail")
	public String detail(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		if(params.get("id")!=null){
			Map<String,Object> m=warehouseStaffService.queryById(params.get("id"));
			model.put("wareStaff", m);
		}
		return "/lgc/warestaff_show";
		
	}
	
	
	@RequestMapping("export")
	public void export(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		PageInfo<Map<String,Object>> list = warehouseStaffService.selectWStaff(params, getPageInfo(cpage,10000));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		OutputStream os = null;
		try {
			request.setCharacterEncoding("UTF-8");
			os = response.getOutputStream(); // 取得输出流
			response.reset(); // 清空输出流
			response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
            String fileName = "仓管员表-" + sdf.format(new Date()) + ".xls";
                     response.setHeader("Content-disposition", "attachment;filename="
                             + new String(fileName.getBytes("GBK"), "ISO8859-1"));
            exportData(os, params, fileName,list.getList());
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	public void exportData(OutputStream os, Map<String, String> params,String fileName,List<Map<String, Object>>  list) throws Exception, WriteException{

		int row = 1; // 从第三行开始写
		int col = 0; // 从第一列开始写
		Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/warehouse_staff.xls"));
		WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
		WritableSheet ws = wwb.getSheet(0);

		Iterator<Map<String, Object>> it = list.iterator();
		while (it.hasNext()) {

			Map<String, Object> map = it.next();

			ws.addCell(new Label(col++, row,  row+""));
			ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substation_name"))));
			ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("user_name"))));
			ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("real_name"))));
			ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("inner_no"))));
			ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("inner_phone"))));
			ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("phone"))));
			if(map.get("regist_time")!=null){
				
				 ws.addCell(new Label(col++, row, StringUtils.nullString( DateUtils.formatDate((Date)map.get("regist_time"),"yyyy-MM-dd HH:mm:ss"))));
			}else{
				ws.addCell(new Label(col++, row,""));
			}
			ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("create_operator"))));
			row++;
			col = 0;
		}

		wwb.write();
		wwb.close();
		wb.close();
		os.close();
	}
	
}
