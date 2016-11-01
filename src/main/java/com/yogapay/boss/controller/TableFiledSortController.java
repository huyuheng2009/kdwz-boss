package com.yogapay.boss.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.TableFiledSort;
import com.yogapay.boss.service.TableFiledSortService;
import com.yogapay.boss.utils.Constants;

@Controller
@RequestMapping("tabelFiled")
public class TableFiledSortController extends BaseController{
	
	@Resource
    private TableFiledSortService tableFiledSortService;
	
	@RequestMapping("/tableFiledSelect")
	public String tableFiledSelect(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		BossUser user = Constants.getUser();
		params.put("userNo", user.getUserName());
		List<TableFiledSort> tableFieldSorts = tableFiledSortService.selectList(params);
		if(tableFieldSorts==null||tableFieldSorts.size()==0){
			params.put("userNo", null);
			tableFieldSorts = tableFiledSortService.selectList(params);
		}
		model.put("tableFieldSorts", tableFieldSorts);
		model.put("tab", params.get("tab"));
		model.put("tabName",tableFieldSorts.get(0).getTabName());
		for (int i = 0; i < tableFieldSorts.size(); i++) {
			System.out.println(tableFieldSorts.get(i).toString());
		}
		
		return "tableFiled/input";
	}
	
	@RequestMapping("/delete")
	public  void delete(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		BossUser user = Constants.getUser();
		params.put("userNo", user.getUserName());
		List<TableFiledSort> tableFieldSorts = tableFiledSortService.selectList(params);
		if(tableFieldSorts==null||tableFieldSorts.size()==0){
			outText("你还未设置", response);
			return;
		}
		tableFiledSortService.deleteByUserNo(params);
		outText("恢复默认成功", response);
	}
	
	@RequestMapping("/addOrUpdate")
	public  void add(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		BossUser user = Constants.getUser();
		params.put("userNo", user.getUserName());
		List<TableFiledSort> tableFieldSorts = tableFiledSortService.selectList(params);
		String fields = params.get("filed");
		String[] arr = fields.split(";");
		List<TableFiledSort> list =new ArrayList<TableFiledSort>();
		for (int i = 0; i < arr.length; i++) {
			TableFiledSort s =new TableFiledSort();
			s.setCol(arr[i].split(",")[0]);
			s.setIsShow(Integer.parseInt(arr[i].split(",")[1]));
			s.setSort(Integer.parseInt(arr[i].split(",")[2]));
			s.setColName(arr[i].split(",")[3]);
			s.setTabName(params.get("tabName"));
			s.setTab(params.get("tab"));
			s.setUserNo(user.getUserName());
			list.add(s);
		}
		if(tableFieldSorts==null||tableFieldSorts.size()==0){
			//新增tab=aasasa&filed=id,1,0;a,0,3
			tableFiledSortService.insert(list);
		}else{
			//修改
			tableFiledSortService.updateByTabAndCol(list);
		}
		outText("设置成功", response);
	}
	

}
