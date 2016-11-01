package com.yogapay.boss.controller;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.JoinSubstationAcount;
import com.yogapay.boss.enums.BillTypeMnums;
import com.yogapay.boss.service.JoinSubsationService;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

@Controller
@RequestMapping(value="/join")
public class JoinSubstationAcountController extends BaseController {
	private Log log = LogFactory.getLog(JoinSubstationAcountController.class);
	private static final  String reg = "^[0-9]+(.[0-9]{1,2})?$";
	@Resource
	private JoinSubsationService joinSubstation;

	//用于开张
	@RequestMapping(value="/openList")
	public String openList(@RequestParam Map<String, String> params, final ModelMap model, 
			HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){

		List<Map<String, Object>> substations  =joinSubstation.getJoinSubstationList();
		PageInfo<Map<String, Object>> list  = joinSubstation.joinList(params, getPageInfo(cpage));

		model.put("substationList", substations);	
		model.put("list", list);	
		model.put("params", params);	
		return "join/openlist";	
	}

	@RequestMapping(value="/open")
	public void open(@RequestParam Map<String,String>params,HttpServletRequest request,HttpServletResponse resp){
		BossUser boss = 	(BossUser)SecurityUtils.getSubject().getPrincipal();
		List<String> subList  = joinSubstation.getNotOpenSubstation();
		if(subList.size()<1){
			outText("当前无未开账的加盟网点",resp);
			return;
		}
		try{
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Iterator li  = subList.iterator();
			while(li.hasNext()){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("substationNo", li.next());
				map.put("operater",boss.getRealName());		
				list.add(map);		
			}			
			joinSubstation.batchOpenSubstation(list);

			outText("开账成功，共有"+subList.size()+"个加盟网点，开账！",resp);	

		}catch(Exception ex){
			outText("开账失败请稍候重试",resp);
			ex.printStackTrace();
		}	
	}
	@RequestMapping(value="/startOrStop")
	public void startOrStop(@RequestParam Map<String,String>params,HttpServletRequest request,HttpServletResponse resp){
		joinSubstation.updateStatus(params);	
		outText("操作成功",resp);	
	}	
	@RequestMapping(value="/saveEdit")
	public void saveEdit(@RequestParam Map<String,String>params,HttpServletRequest request,HttpServletResponse resp){
		Map<String,Object> map = new HashMap<String,Object>();
		if(!StringUtil.match(reg, params.get("warning"))){
			map.put("code", "1");
			map.put("message", "请输入正常的警戒金额,纯数字可保留两位小数");
			outJson(JsonUtil.toJson(map), resp);
			return ;
		}			
		if(!StringUtil.match(reg, params.get("shut"))){
			map.put("code", "1");
			map.put("message", "请输入正常的停止金额,纯数字可保留两位小数");
			outJson(JsonUtil.toJson(map), resp);
			return ;
		}				
		joinSubstation.saveEdit(params);	
		map.put("code", "0");
		map.put("message", "保存成功");
		outJson(JsonUtil.toJson(map), resp);
		return ;	
	}	
	@RequestMapping(value="/up")
	public String up(@RequestParam(value="id")String id,final ModelMap model,HttpServletResponse resp){
		JoinSubstationAcount info = 	joinSubstation.getInfo(id);
		model.put("id", info.getId());
		model.put("startTime", DateUtils.formatDate(info.getStartTime()));
		return "join/up";
	}
	@RequestMapping(value="/upTime")
	public void upTime(@RequestParam Map<String,Object> map,HttpServletResponse resp){
		joinSubstation.updateTime(map);
		outText("操作成功",resp);			
	}
	//用于开张
	@RequestMapping(value="/recharge")
	public String recharge(@RequestParam Map<String, String> params, final ModelMap model, 
			HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		Date date = new Date();
		List<Map<String, Object>> substations  =joinSubstation.getJoinOpenList();			
		model.put("substationList", substations);				
		model.put("nowDate", DateUtils.formatDate(date));	
		return "join/recharge";	
	}

	@RequestMapping(value="/upMoney")
	public void upMoney(@RequestParam Map<String,String>params,HttpServletRequest request,HttpServletResponse resp){
		Map<String,Object> map = new HashMap<String,Object>();

		if(joinSubstation.checkAcount(params)){		
			map.put("code", "1");
			map.put("message", "当前分站不存在，或未启用");
			outJson(JsonUtil.toJson(map), resp);
			return ;	
		}
		if(StringUtils.isEmptyWithTrim(params.get("money"))){
			map.put("code", "1");
			map.put("message", "必须输入金额！");
			outJson(JsonUtil.toJson(map), resp);
			return ;	
		}
		if(!StringUtil.match(reg, params.get("money"))){
			map.put("code", "1");
			map.put("message", "请输入正常的充值金额,纯数字可保留一位小数！");
			outJson(JsonUtil.toJson(map), resp);
			return ;
		}		
		BossUser boss = 	(BossUser)SecurityUtils.getSubject().getPrincipal();
		params.put("operater", boss.getRealName());

		if (!StringUtils.isEmptyWithTrim(params.get("lgcOrderNo"))) {
			if(StringUtils.isEmptyWithTrim(joinSubstation.getSendTime(params.get("lgcOrderNo")))){
				map.put("code", "1");
				map.put("message", "当前运单号不存在或未取件状态，无法充值或冲账！");
				outJson(JsonUtil.toJson(map), resp);
				return ;					
			}
		}

		joinSubstation.insertBill(params);	

		map.put("code", "0");
		if("S".equals(params.get("type"))){
			map.put("message", "充值成功");
		}else{
			map.put("message", "冲账成功");
		}		
		outJson(JsonUtil.toJson(map), resp);
		return ;	
	}	






	//用于开张
	@RequestMapping(value="/detail")
	public String detail(@RequestParam Map<String, String> params, final ModelMap model, 
			HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		//			List<Map<String, Object>> substations  =joinSubstation.getJoinOpenList();	
		
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		String substationNo ;
		if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
			 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
		}else {
			substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
		}
		params.put("sno", substationNo);
		
		List<Map<String, Object>> substations  =joinSubstation.allJoinOpenSubstation(params);//所有开账过网店 包括停用的			

		//			{sendOrderBeginTime=, sendOrderEndTime=, operater=, substationNo=
		//			, beginTime=, lgcOrderNo= , type=, endTime=, timeType=S, serviceName=, source=, ff=, payType=}
		String lgcOrderNo = params.get("lgcOrderNo");
		if(!StringUtils.isEmptyWithTrim(lgcOrderNo)){
			String[] lgc = lgcOrderNo.split("\r\n");
			StringBuffer buf = new StringBuffer();		
			for(String str:lgc){
				buf.append("'"+str+"',");				
			}
			buf.append("'0'");
			params.put("lgcOrderNo1", buf.toString().replaceAll(" ", ""));
		}	
		if ("S".equals(params.get("timeType"))) {
			params.put("beginTime","");
			params.put("endTime", "");
		}
		if ("C".equals(params.get("timeType"))) {
			params.put("sendOrderBeginTime", "");
			params.put("sendOrderEndTime", "");
		}

	

		PageInfo<Map<String, Object>> list  = joinSubstation.detailList(params, getPageInfo(cpage));

		model.put("substationList", substations);	
		model.put("list", list);	
		model.put("params", params);	
		return "join/detail";	
	}



	@RequestMapping(value="/export1")
	public void export(@RequestParam Map<String,String> params,HttpServletRequest request,HttpServletResponse response){
		Date  now = new Date();
		int col = 0;
		int row =1;

		OutputStream os = null;
		try {
			request.setCharacterEncoding("UTF-8");
			os = response.getOutputStream(); // 取得输出流
			response.reset(); // 清空输出流
			response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
			String fileName = "收支明细-" +  now.getTime() + ".xls";			
			response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));	

			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			params.put("sno", substationNo);
			
			String lgcOrderNo = params.get("lgcOrderNo");
			if(!StringUtils.isEmptyWithTrim(lgcOrderNo)){
				String[] lgc = lgcOrderNo.split("\r\n");
				StringBuffer buf = new StringBuffer();		
				for(String str:lgc){
					buf.append("'"+str+"',");				
				}
				buf.append("'0'");
				params.put("lgcOrderNo1", buf.toString().replaceAll(" ", ""));
			}	
			if ("S".equals(params.get("timeType"))) {
				params.put("beginTime","");
				params.put("endTime", "");
			}
			if ("C".equals(params.get("timeType"))) {
				params.put("sendOrderBeginTime", "");
				params.put("sendOrderEndTime", "");
			}


			PageInfo<Map<String, Object>> list  = joinSubstation.detailList(params, getPageInfo(1,100000));	


			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/detailAcount.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = list.getList().iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("createTime"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("sendTime"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("innerNo"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("substationName"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("lgcOrderNo"))));
				ws.addCell(new Label(col++, row,BillTypeMnums.getText(StringUtils.nullString(map.get("type")))));
				ws.addCell(new Label(col++, row,BillTypeMnums.getText(StringUtils.nullString(map.get("payType")))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("beforeBalance"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("useBalance"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("afterBalance"))));
				ws.addCell(new Label(col++, row,BillTypeMnums.getText(StringUtils.nullString(map.get("source")))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("operater"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("note"))));
				row++;
				col = 0;
			}			
			wwb.write();
			wwb.close();
			wb.close();
			os.close();	
		}catch(Exception e){
			e.printStackTrace();
		}    	
	}

	//用于开张
	@RequestMapping(value="/acount")
	public String acount(@RequestParam Map<String, String> params, final ModelMap model, 
			HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){
		//	  			List<Map<String, Object>> substations  =joinSubstation.getJoinOpenList();	
		BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
		String substationNo ;
		if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
			 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
		}else {
			substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
		}
		params.put("sno", substationNo);
		List<Map<String, Object>> substations  =joinSubstation.allJoinOpenSubstation(params);//所有开账过网店 包括停用的					
		PageInfo<Map<String, Object>> list  = joinSubstation.acountList(params, getPageInfo(cpage));	  		
		Map<String,Object> map = joinSubstation.allAcountMap(params);
		model.put("substationList", substations);	
		model.put("list", list);	
		model.put("map",map);	
		model.put("params", params);	
		return "join/acount";	
	}
	@RequestMapping(value="/export2")
	public void export2(@RequestParam Map<String,String> params,HttpServletRequest request,HttpServletResponse response){
		Date  now = new Date();
		int col = 0;
		int row =1;

		OutputStream os = null;
		try {
			request.setCharacterEncoding("UTF-8");
			os = response.getOutputStream(); // 取得输出流
			response.reset(); // 清空输出流
			response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
			String fileName = "网点对账导出-" +  now.getTime() + ".xls";			
			response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));	
			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			params.put("sno", substationNo);
			
			PageInfo<Map<String, Object>> list  = joinSubstation.acountList(params, getPageInfo(1,100000));	
			Map<String,Object> map1 = joinSubstation.allAcountMap(params);

			Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream("/template/joinAcount.xls"));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = wwb.getSheet(0);
			Iterator<Map<String, Object>> it = list.getList().iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("createTime"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("innerNo"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("substationName"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("chongzhi"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("chongzhang"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("zhongzhuan"))));
				ws.addCell(new Label(col++, row,StringUtils.nullString(map.get("paijian"))));				
				row++;
				col = 0;
			}			
			ws.addCell(new Label(0, row,"总计"));
			ws.addCell(new Label(3, row,StringUtils.nullString(map1.get("allchongzhi"))));
			ws.addCell(new Label(4, row,StringUtils.nullString(map1.get("allchongzhang"))));
			ws.addCell(new Label(5, row,StringUtils.nullString(map1.get("allzhongzhuan"))));
			ws.addCell(new Label(6, row,StringUtils.nullString(map1.get("allpaijian"))));
			wwb.write();
			wwb.close();
			wb.close();
			os.close();	
		}catch(Exception e){
			e.printStackTrace();
		}    	
	}
	//用于开张
		@RequestMapping(value="/balance")
		public String balance(@RequestParam Map<String, String> params, final ModelMap model, 
				HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "p", defaultValue = "1") int cpage){

			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			String substationNo ;
			if (!SecurityUtils.getSubject().isPermitted("LISTSUBSTATION")||"1".equals(StringUtils.nullString(request.getAttribute("sub_limit")))) {
				 substationNo = userService.getUserSubstationNo(bossUser.getId(),bossUser.getLgcNo());
			}else {
				substationNo = userService.getUserSubstationByLgcNo(bossUser.getLgcNo());
			}
			params.put("sno", substationNo);
			
			List<Map<String, Object>> substations  =joinSubstation.getJoinSubstationList();
			PageInfo<Map<String, Object>> list  = joinSubstation.joinList(params, getPageInfo(cpage));

			model.put("substationList", substations);	
			model.put("list", list);	
			model.put("params", params);	
			return "join/balance";	
		}
}
