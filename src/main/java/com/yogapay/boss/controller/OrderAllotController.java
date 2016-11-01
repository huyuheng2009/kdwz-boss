package com.yogapay.boss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
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

import org.apache.http.client.utils.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.service.OrderAllotService;
import com.yogapay.boss.service.ScanExService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtils;

@Controller
@RequestMapping(value = "/orderAllot")
public class OrderAllotController extends BaseController {
	@Resource
	private ScanExService scanExService;
	@Resource
	private OrderAllotService orderAllotService;
	@Resource
	private SubstationService substationService;
	@SuppressWarnings("static-method")
	@RequestMapping(value = "/list")
	public String list(@RequestParam Map<String, String> params,
			final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "p", defaultValue = "1") int cpage) {
		System.out.println("===============/orderAllot/list================");
		List<Map<String, Object>> currentCourierList = scanExService
				.allManager(params);// 查询所有扫描员
		List<Map<String, Object>> substations = scanExService.allSubNo();

		String courierNo = params.get("courierNo");// 扫描员
		if (!StringUtils.isEmptyWithTrim(courierNo)) {
			if (courierNo.contains("(")) {
				courierNo = courierNo.substring(0, courierNo.indexOf("("));
				params.put("courierNo", courierNo);
			}
		}
		String substationNo = params.get("substationNo");
		if (!StringUtils.isEmptyWithTrim(substationNo)) {
			if (substationNo.contains("(")) {
				substationNo = substationNo.substring(0,
						substationNo.indexOf("("));
				params.put("substationNo", substationNo);
			}
		}
		PageInfo<Map<String, Object>> current = orderAllotService.getOrderList(params, getPageInfo(cpage));
		System.out.println(current.getList().size());
		model.put("courierList", JsonUtil.toJson(currentCourierList));
		model.put("substationList", JsonUtil.toJson(substations));
		model.put("list", current);
		model.put("params", params);
		return "orderAllot/mlist";
	}

	@SuppressWarnings("static-method")
	@RequestMapping(value = "/allotPage")
	public String allotPage(@RequestParam Map<String, String> params,
			final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "p", defaultValue = "1") int cpage) {
		List<Map<String, Object>> substations = scanExService.allSubNo();
		String substationNo = params.get("substationNo");
		if (!StringUtils.isEmptyWithTrim(substationNo)) {
			if (substationNo.contains("(")) {
				substationNo = substationNo.substring(0,	substationNo.indexOf("("));
				params.put("substationNo", substationNo);
			}
		}
		model.put("substations", JsonUtil.toJson(substations));
		model.put("params", params);
		return "orderAllot/allotPage";
	}

	@SuppressWarnings("static-method")
	@RequestMapping(value = "/allotOrder")
	public void allotOrder(@RequestParam Map<String, String> params,
			HttpServletRequest request,HttpServletResponse response) throws InterruptedException, IOException {
		try{
			Map<String,Object> map =  new HashMap<String,Object>();
			Date nowDate  = new Date();
			System.out.println(params);
			String   beginOrder = params.get("beginOrder");
			String   endOrder = params.get("endOrder");
			String   unitCost = params.get("unitCost");
			String   cost = params.get("cost");
			String   number = params.get("number");

			if(orderAllotService.checkOrderPage(params)){
				map =  new HashMap<String,Object>();
				map.put("msgNo", "1");
				map.put("msg", "票段已经被使用，请检查后提交");				
				outJson(JSONUtils.toJSONString(map), response);
				return;
			}
			String substationNo = params.get("substationNo");
			String sub= params.get("substationNo");
			if (!StringUtils.isEmptyWithTrim(substationNo)) {
				if (substationNo.contains("(")) {
					substationNo = substationNo.substring(0,
							substationNo.indexOf("("));
					params.put("substationNo", substationNo);
				}
			}

			Substation substa = substationService.getSubstationByNo(substationNo);
			if(substa==null){
				map = new HashMap<String,Object>();
				map.put("msgNo", "2");
				map.put("msg", "分站不存在,请注意检查");
				outJson(JSONUtils.toJSONString(map), response);
				return;
			}

			BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
			params.put("managerInfo", "票段分配");
			params.put("registerTime",DateUtils.formatDate(nowDate,"yyyy-MM-dd"));
			params.put("registerName",bossUser.getRealName());
			params.put("unitCost",StringUtils.isEmptyWithTrim(unitCost)?"0":unitCost);
			params.put("substationNo", substationNo);
			params.put("substationName", substa.getSubstationName());

			orderAllotService.saveOrderPage(params);	
			map = new HashMap<String,Object>();
			map.put("msgNo", "0");
			map.put("msg", "分配成功");
			outJson(JSONUtils.toJSONString(map), response);
			return;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 分配明细
	 * @param params
	 * @param request
	 * @param response
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@RequestMapping(value = "/allotDetail")
	public String allotDetail(@RequestParam Map<String, String> params,
			HttpServletRequest request,HttpServletResponse response,final ModelMap model,
			@RequestParam(value = "p", defaultValue = "1") int cpage) throws InterruptedException, IOException {

		Map<String,Object> map =  new HashMap<String,Object>();
		Date nowDate  = new Date();
		List<Map<String,Object>>  currentCourierList = scanExService.allCourierBySub(params);
		List<Map<String, Object>> substations = scanExService.allSubNo();		
		String courierNo = params.get("courierNo");
		 if (!StringUtils.isEmptyWithTrim(courierNo)) {
	            if (courierNo.contains("(")) {
	                courierNo = courierNo.substring(0, courierNo.indexOf("("));
	                params.put("courierNo", courierNo);
	            }
	        }		
			
		String substationNo = params.get("substationNo");
			if (!StringUtils.isEmptyWithTrim(substationNo)) {
				if (substationNo.contains("(")) {
					substationNo = substationNo.substring(0, substationNo.indexOf("("));
					params.put("substationNo", substationNo);
				}
			}
			PageInfo<Map<String, Object>> current = orderAllotService.getAllotDetailList(params, getPageInfo(cpage));
			model.put("list", current);
			model.put("params", params);
			model.put("courierList", JsonUtil.toJson(currentCourierList));
			model.put("substationList", JsonUtil.toJson(substations));

		return "orderAllot/allotDetail";
	}
	/**
	 * 停用票段
	 * @param params
	 * @param request
	 * @param response
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@RequestMapping(value = "/stopUser")
	public void stopUser(@RequestParam Map<String, String> params,
			HttpServletRequest request,HttpServletResponse response) throws InterruptedException, IOException {
		
		orderAllotService.stopAllotOrder(params.get("id"));		
		outText("成功停用", response);
	}
	/**
	 *启用票段
	 * @param params
	 * @param request
	 * @param response
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@RequestMapping(value = "/startUser")
	public void startUser(@RequestParam Map<String, String> params,
			HttpServletRequest request,HttpServletResponse response) throws InterruptedException, IOException {
		
		orderAllotService.startAllotOrder(params.get("id"));	
		outText("成功启用", response);
	}
	/**
	 *删除票段
	 * @param params
	 * @param request
	 * @param response
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@RequestMapping(value = "/deleteOrder")
	public void deleteOrder(@RequestParam Map<String, String> params,
			HttpServletRequest request,HttpServletResponse response) throws InterruptedException, IOException {
		
		orderAllotService.deleteOrder(params.get("id"));	
		outText("成功删除", response);
	}
	/**
	 * 报表生成Excel
	 * @param params
	 * @param response
	 * @param request
	 * @throws SQLException
	 */
	@RequestMapping(value = {"/export"})
    public void export(@RequestParam Map<String, String> params, HttpServletResponse response, HttpServletRequest request)
            throws SQLException {
        String serviceName = params.get("serviceName");
        Date date = new Date();
        OutputStream os = null;
        try {
            request.setCharacterEncoding("UTF-8");
            os = response.getOutputStream(); // 取得输出流
            response.reset(); // 清空输出流
            response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
            if (!StringUtils.isEmpty(params.get("serviceName"))) {
                if ("allotExport".equals(serviceName)) {
                    String fileName = "订单分配-" +date.getTime()  + ".xls";
                    response.setHeader("Content-disposition", "attachment;filename="
                            + new String(fileName.getBytes("GBK"), "ISO8859-1"));
                    exportOrder(os, params, fileName);
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
     * 写EXCEL
     *
     * @param os
     * @param params
     * @param fileName
     * @param num
     * @throws Exception
     */
    public void exportOrder(OutputStream os, Map<String, String> params, String fileName) throws Exception {
        int row = 1; // 从第三行开始写
        int col = 0; // 从第一列开始写

  
		String courierNo = params.get("courierNo");// 扫描员
		if (!StringUtils.isEmptyWithTrim(courierNo)) {
			if (courierNo.contains("(")) {
				courierNo = courierNo.substring(0, courierNo.indexOf("("));
				params.put("courierNo", courierNo);
			}
		}
		String substationNo = params.get("substationNo");
		if (!StringUtils.isEmptyWithTrim(substationNo)) {
			if (substationNo.contains("(")) {
				substationNo = substationNo.substring(0,
						substationNo.indexOf("("));
				params.put("substationNo", substationNo);
			}
		}
        
            List<Map<String, Object>> orderList =orderAllotService.getOrderList(params);
          
            Workbook wb = Workbook.getWorkbook(this.getClass().getResourceAsStream(
                    "/template/allotPage.xls"));
            WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
            WritableSheet ws = wwb.getSheet(0);
            Iterator<Map<String, Object>> it = orderList.iterator();
            while (it.hasNext()) {
                Map<String, Object> map = it.next();
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("managerInfo"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("registerTime"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("substationName"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("beginOrder"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("endOrder"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("number"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("userdCount"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("residueCount"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("unitCost"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("cost"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("registerName"))));
                ws.addCell(new Label(col++, row, StringUtils.nullString(map.get("note"))));           
                ws.addCell(new Label(col++, row, "Y".equals(map.get("status"))?"已启用":"已停用"));           
                row++;
                col = 0;
            }         
            wwb.write();
            wwb.close();
            wb.close();
            os.close();
        }
     

   
	
}