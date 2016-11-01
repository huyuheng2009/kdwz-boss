package com.yogapay.boss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.service.MobileUserService;
import com.yogapay.boss.service.SalaryService;
import com.yogapay.boss.service.SequenceService;
import com.yogapay.boss.service.WagesService;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtils;

@Controller
@RequestMapping(value = "/salary")
public class SalaryController extends BaseController {

	@Resource
	private WagesService wagesService ;	
	@Resource
	private SalaryService salaryService ;	
	@Resource
	private SequenceService sequenceService ;
	@Resource
	private MobileUserService mobileUserService;

	// 用于费用录入页面
	@RequestMapping(value = { "/clist" })
	public String input(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		params.put("costIo", "1") ;
		List<Map<String, Object>> inList =  wagesService.listCostName(params) ;
		params.put("costIo", "-1") ;
		List<Map<String, Object>> outList =  wagesService.listCostName(params) ;
		model.put("inList", inList) ;
		model.put("outList", outList) ;
		return "salary/clist";
	}	



	// 用于保存
	@RequestMapping(value = { "/inputSave" })
	public void inputSave(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String r = "保存失败" ;
		try {



			r = "1" ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		outText(r, response);
	}	
	@SuppressWarnings("static-method")
	@RequestMapping(value = { "/add1" })
	public String add1(){
		return "salary/add1";
	}
	@SuppressWarnings("static-method")
	@RequestMapping(value = { "/add2" })
	public String add2(){
		return "salary/add2";
	}
	@RequestMapping(value="/add_1")
	public void add_1(@RequestParam Map<String,String> params,HttpServletRequest 
			request,HttpServletResponse response,@RequestParam(value="message")String message) throws SQLException{
		Map<String,Object>	modleMap= new HashMap<String,Object>();
		String id = sequenceService.getNextVal("salary_name_id");		
		params.put("cnName", message);
		params.put("costIo", "1");
		Map<String,Object>	map = salaryService.getAddMap(params);
		if(map!=null){
			modleMap.put("code", "1");
			modleMap.put("message", "当前工资增加项已存在！");
			outJson(JsonUtil.toJson(modleMap), response);
			return ;		
		}
		params.put("id", "name"+id);
		salaryService.insertAdd1(params);		
		modleMap.put("code", "0");
		modleMap.put("message", "新增成功！");
		outJson(JsonUtil.toJson(modleMap), response);
		return ;		
	}
	@RequestMapping(value="/add_2")
	public void add_2(@RequestParam Map<String,String> params,HttpServletRequest 
			request,HttpServletResponse response,@RequestParam(value="message")String message) throws SQLException{
		Map<String,Object>	modleMap= new HashMap<String,Object>();
		String id = sequenceService.getNextVal("salary_name_id");		
		params.put("cnName", message);
		params.put("costIo", "-1");
		Map<String,Object>	map = salaryService.getAddMap(params);
		if(map!=null){
			modleMap.put("code", "1");
			modleMap.put("message", "当前工资扣除项已存在！");
			outJson(JsonUtil.toJson(modleMap), response);
			return ;		
		}
		params.put("id", "name"+id);
		salaryService.insertAdd1(params);		
		modleMap.put("code", "0");
		modleMap.put("message", "新增成功！");
		outJson(JsonUtil.toJson(modleMap), response);
		return ;		
	}
	@RequestMapping(value="/del")
	public void del(@RequestParam Map<String,String> params,HttpServletRequest 
			request,HttpServletResponse response,@RequestParam(value="id")String id) throws SQLException{
		Map<String,Object>	modleMap= new HashMap<String,Object>();								
		if(salaryService.checkId(id)){
			modleMap.put("code", "1");
			modleMap.put("message", "当前工资项不存在，请检查！");
			outJson(JsonUtil.toJson(modleMap), response);
			return ;		
		}				
		salaryService.deleteById(id);		
		modleMap.put("code", "0");
		modleMap.put("message", "删除成功！");
		outJson(JsonUtil.toJson(modleMap), response);
		return ;		
	}	
	@SuppressWarnings("static-method")
	@RequestMapping(value = { "/change" })
	public String change(final ModelMap model,@RequestParam(value="id")String id){
		Map<String,Object> map = salaryService.getSalaryMap(id);
		System.out.println(map);
		model.put("map", map);
		return "salary/change";
	}
	@RequestMapping(value="/changeInfo")
	public void changeInfo(@RequestParam Map<String,String> params,HttpServletRequest 
			request,HttpServletResponse response,@RequestParam(value="id")String id) throws SQLException{
		Map<String,Object>	modleMap= new HashMap<String,Object>();
		if(salaryService.checkId(id)){
			modleMap.put("code", "1");
			modleMap.put("message", "当前工资项不存在，请检查！");
			outJson(JsonUtil.toJson(modleMap), response);
			return ;		
		}					
		salaryService.updateSalaryInfo(params);		
		modleMap.put("code", "0");
		modleMap.put("message", "修改成功！");
		outJson(JsonUtil.toJson(modleMap), response);
		return ;		
	}	
	//快递员提成维护
	@RequestMapping(value = { "/cclist" })
	public String cclist(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException, ParseException {
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		System.out.println(params);

		//					{substationNo=1001003, courierNo=测试222, serviceName=}						

		PageInfo<Map<String, Object>> countList = salaryService.getccList (params, getPageInfo(cpage));

		List<Map<String, Object>> courierList  = mobileUserService.getAllCourierList(params);					
		List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));

		model.put("list", countList);
		model.put("params", params);
		model.put("courierList", JsonUtil.toJson(courierList));
		model.put("substations", substations);

		return "salary/courierlist";
	}
	@SuppressWarnings("static-method")
	@RequestMapping(value = { "/compile" })
	public String compile(final ModelMap model,@RequestParam(value="courierNo")String courierNo){
		Map<String,Object> map = salaryService.getCourierInfo(courierNo);
		model.put("map", map);
		return "salary/salary_pop";
	}
	@RequestMapping(value="/saveCompile")
	public void saveCompile(@RequestParam Map<String,String> params,HttpServletRequest 
			request,HttpServletResponse response,@RequestParam(value="courierNo")String courierNo) throws SQLException{
		Map<String,Object>	modleMap= new HashMap<String,Object>();
		BossUser bossUser = (BossUser)SecurityUtils.getSubject().getPrincipal();

		params.put("operator", bossUser.getRealName());
		if(salaryService.checkCourierNo(courierNo)){
			salaryService.insertCourierTc(params);
		}else{
			salaryService.updateCourierTc(params);
		}
		salaryService.insertTcRecord(params);
		modleMap.put("code", "0");
		modleMap.put("message", "修改成功！");
		outJson(JsonUtil.toJson(modleMap), response);
		return ;		
	}	
	//快递员提成维护
	@RequestMapping(value = { "/crlist" })
	public String crlist(@RequestParam Map<String, String> params, final ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException, ParseException {
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		System.out.println(params);

		//					{substationNo=1001003, courierNo=测试222, serviceName=}						

		PageInfo<Map<String, Object>> countList = salaryService.getCourierInfoRecord(params, getPageInfo(cpage));

		List<Map<String, Object>> courierList  = mobileUserService.getAllCourierList(params);					
		List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));

		model.put("list", countList);
		model.put("params", params);
		model.put("courierList", JsonUtil.toJson(courierList));
		model.put("substations", substations);

		return "salary/crlist";
	}
	// 快递员每日收件数量
	@RequestMapping(value = { "/export" })
	public void export(@RequestParam Map<String, String> params, HttpServletResponse response, HttpServletRequest request) throws SQLException {
		String serviceName = params.get("serviceName");
		String beginTime = params.get("beginTime");
		String endTime = params.get("endTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		OutputStream os = null;
		try {
			request.setCharacterEncoding("UTF-8");
			os = response.getOutputStream(); // 取得输出流
			response.reset(); // 清空输出流
			response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
			if (!StringUtils.isEmpty(params.get("serviceName"))) {
				if ("tc".equals(serviceName)) {
					String fileName = "快递员提成维护表-" + beginTime + "~" + endTime + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 1);
				}
				if ("tcRecord".equals(serviceName)) {
					String fileName = "快递员提成维护记录表-" + beginTime + "~" + endTime + ".xls";
					response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
					exportOrder(os, params, fileName, 2);
				}

			}
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

	/**
	 * mergeCells(a,b,c,d) 单元格合并函数
	 * a 单元格的列号
	 * b 单元格的行号
	 * c 从单元格[a,b]起，向下合并的列数
	 * d 从单元格[a,b]起，向下合并的行数	
	 * @param os
	 * @param params
	 * @param fileName
	 * @param num
	 * @throws Exception
	 */
	public void exportOrder(OutputStream os, Map<String, String> params, String fileName, int num) throws Exception {
		int row =2; // 从第三行开始写
		int col = 0; // 从第一列开始写

		if (num == 1) {	
			PageInfo<Map<String, Object>> countList = salaryService.getccList (params, getPageInfo(1,100000));
			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/salaryTc.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = countList.getList().iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row, StringUtils.nullString( map.get("substationName"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("innerNo"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("realName"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("phone"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("takeTcf"))));
				ws.addCell(new Label(col++, row,StringUtils.nullStringtoRound( map.get("takeTcw"))));
				ws.addCell(new Label(col++, row,StringUtils.nullStringtoRound( map.get("takeTcc"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("sendTcf"))));
				ws.addCell(new Label(col++, row,StringUtils.nullStringtoRound( map.get("sendTcw"))));
				ws.addCell(new Label(col++, row,StringUtils.nullStringtoRound( map.get("sendTcc"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("forTcf"))));
				ws.addCell(new Label(col++, row,StringUtils.nullStringtoRound( map.get("forTcw"))));
				ws.addCell(new Label(col++, row,StringUtils.nullStringtoRound( map.get("forTcc"))));			
				row++;
				col = 0;
			}

			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}
		if (num == 2) {
			PageInfo<Map<String, Object>> countList = salaryService.getCourierInfoRecord(params, getPageInfo(1,100000));
			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/salaryTcRecord.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = countList.getList().iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row, StringUtils.nullString( map.get("substationName"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("innerNo"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("realName"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("phone"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("take_tcf"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("take_tcw"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("take_tcc"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("send_tcf"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("send_tcw"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("send_tcc"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("for_tcf"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("for_tcw"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("for_tcc"))));			
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("operator"))));			
				ws.addCell(new Label(col++, row,StringUtils.nullString( map.get("create_time"))));			
				row++;
				col = 0;
			}

			wwb.write();
			wwb.close();
			wb.close();
			os.close();
		}			
	}
}
