package com.yogapay.boss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.yogapay.boss.domain.JudgeLabel;
import com.yogapay.boss.service.JudgeLabelService;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping("/judge")
public class JudgeController extends BaseController{
	
	@Resource
	private JudgeLabelService judgeLabelService;
	
	@RequestMapping("/label")
	public String JudgeLabelList(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		PageInfo<Map<String, Object>> pageInfo = judgeLabelService.listByPage(params, getPageInfo(cpage,50));
		model.put("pageInfo", pageInfo);
		model.put("params", params);
		return "/judge/label_list";
	}
	
	@RequestMapping("/labelUpdate")
	public void labelUpdate(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		judgeLabelService.updateStatusById(params);
		outText("修改成功", response);
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		judgeLabelService.deleteById(Long.parseLong(params.get("id")));
		return "redirect:/judge/label";
	}
	
	@RequestMapping("/init")
	public String init(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		if(params.get("id")!=null){
			JudgeLabel judgeLabel =judgeLabelService.findById(params);
			model.put("judgeLabel", judgeLabel);
		}
		
		model.put("params", params);
		return "/judge/input";
	}
	
	@RequestMapping("/sadd")
	public void sadd(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		int f = 1;//1新增2修改
		if(params.get("id")!=null){
			if(!"".equals(params.get("id").toString().trim())){
				f=2;
			}
		}
		String msg="";
		if(f==1){
			judgeLabelService.insert(params);
			msg="添加成功";
		}else{
			judgeLabelService.updateById(params);
			msg="修改成功";
		}
		
		outText(msg, response);
	}

	@RequestMapping("/all")
	public String listSumary(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		PageInfo<Map<String, Object>> pageInfo = judgeLabelService.listSummary(params, getPageInfo(cpage));
		List<Map<String, Object>> list = pageInfo.getList();
		//去除重复的标签内容
		Set set= new HashSet();
		String label_txt="";
		for (Map<String, Object> map : list) {
			if(map.get("label_txt")!=null ){
				if(!"".equals(map.get("label_txt").toString().trim())){
					label_txt = map.get("label_txt").toString();
					map.put("label_txt",delRepeat(set,label_txt));
				}
			}
			map.put("avg", Double.parseDouble(map.get("total").toString())/Double.parseDouble(map.get("service_time").toString()));
		}
		model.put("list", list);
		model.put("total", pageInfo.getPages());
		model.put("current", pageInfo.getPageNum());
		model.put("sum", pageInfo.getTotal());
		model.put("params", params);
		return "judge/sumary_list";
	}
	
	@RequestMapping("/detailList")
	public String detailList(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		PageInfo<Map<String, Object>> pageInfo = judgeLabelService.listDetail(params, getPageInfo(cpage));
		List<Map<String, Object>> list = pageInfo.getList();
		model.put("list", list);
		model.put("total", pageInfo.getPages());
		model.put("current", pageInfo.getPageNum());
		model.put("sum", pageInfo.getTotal());
		model.put("params", params);
		return "judge/detail_list";
	}
	
	@RequestMapping("/exportData")
	public void export(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		OutputStream os = null;
		try {
			request.setCharacterEncoding("UTF-8");
			os = response.getOutputStream(); // 取得输出流
			response.reset(); // 清空输出流
			response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
			
			if(!StringUtils. isEmpty(params.get("serviceName"))){
                if("sumaryList".equals(params.get("serviceName"))){
                	 String fileName = "快递员评价汇总-" + sdf.format(new Date()) + ".xls";
                	 response.setHeader("Content-disposition", "attachment;filename="
 		                    + new String(fileName.getBytes("GBK"), "ISO8859-1"));
                     exportData(os, params,1);
                     
                }
                if("detailList".equals(params.get("serviceName"))){
               	 String fileName = "每单评价记录-" + sdf.format(new Date()) + ".xls";
               	response.setHeader("Content-disposition", "attachment;filename="
	                    + new String(fileName.getBytes("GBK"), "ISO8859-1"));
                    exportData(os, params,2);
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
	
	private void exportData(OutputStream os, Map<String, String> params,int type) throws Exception{
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Set set = new HashSet();
		int row = 1; // 从第三行开始写
		int col = 0; // 从第一列开始写
		Workbook wb =null;
		if(type==1){
			wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/judgeSumary.xls"));
		}else{
			wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/judgeDetail.xls"));
		}
		WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
		WritableSheet ws = wwb.getSheet(0);
		if(type==1){
			PageInfo<Map<String, Object>> pageInfo = judgeLabelService.listSummary(params, getPageInfo(0, 20000));
			List<Map<String,Object>> lis = pageInfo.getList();
			for (Map<String, Object> map : lis) {
				ws.addCell(new Label(col++, row,  row+""));
				ws.addCell(new Label(col++, row, (String)map.get("courier_no")));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("real_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substation_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("service_time"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("five"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("four"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("three"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("two"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("one"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(df.format(Double.parseDouble(map.get("total").toString())/Integer.parseInt(map.get("service_time").toString())))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(delRepeat(set, map.get("label_txt").toString()))));
				row++;
				col = 0;
			}
		}else{
			PageInfo<Map<String, Object>> pageInfo = judgeLabelService.listDetail(params, getPageInfo(0, 20000));
			List<Map<String,Object>> lis = pageInfo.getList();
			for (Map<String, Object> map : lis) {
				ws.addCell(new Label(col++, row,  row+""));
				ws.addCell(new Label(col++, row, map.get("take_order_time")==null?"":sdf.format((Date)map.get("take_order_time"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("order_no"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("lgc_order_no"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("courier_no"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("real_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substation_name"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("star")).equals("5")?"√":""));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("star")).equals("4")?"√":""));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("star")).equals("3")?"√":""));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("star")).equals("2")?"√":""));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("star")).equals("1")?"√":""));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("label_txt"))));
				ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("comments"))));
				row++;
				col = 0;
			}
		}
		wwb.write();
		wwb.close();
		wb.close();
	}
	
	private String delRepeat(Set set,String str){
		if(set==null){
			set= new HashSet();
		}
		set.clear();
		String[] arr = str.split(",");
		for (String s : arr) {
			set.add(s);
		}
		return set.toString().replace("[", "").replace("]", "");
	}
	
}
