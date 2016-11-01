package com.yogapay.boss.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.FreightRule;
import com.yogapay.boss.service.FreightRuleService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.StringUtils;

@Controller
@RequestMapping(value = "/freightRule")
public class FreightRuleController extends BaseController {
			
	@Resource
	private FreightRuleService freightRuleService ;

				 // 邮费规则维护
				@RequestMapping(value = { "/rule" })
				public String list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws Exception {
					PageInfo<Map<String, Object>> list = freightRuleService.listRule(params, getPageInfo(cpage)) ;
					model.put("list", list) ;
					model.put("params", params) ;
					return "freightRule/rule";
				}				
			

				 // 用于邮费规则编辑
				@RequestMapping(value = { "/redit" })
				public String input(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException, SQLException {
					String id = StringUtils.nullString(params.get("id")) ;
				   List<Map<String, Object>> tlist = null ;
				   List<Map<String, Object>> ilist = null ;
				   Map<String, Object> rule = new HashMap<String, Object>() ;
                   if (StringUtils.isEmptyWithTrim(id)) {
                	   tlist = freightRuleService.getRuleTtype(false, "") ;
				     ilist = freightRuleService.getRuleItype(false, "") ;
                   }else {
                	   tlist = freightRuleService.getRuleTtype(true, id) ;
  				       ilist = freightRuleService.getRuleItype(true, id) ;
  				       rule = freightRuleService.getById(params) ;
				  }
					model.put("params", params) ;
					model.put("tlist", tlist) ;
					model.put("ilist", ilist) ;
					model.put("rule", rule) ;
					return "freightRule/rule_edit";
				}	
				
				
				 // 用于规则保存
				@RequestMapping(value = { "/ruleSave" })
				public void ruleSave(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException {
					String r = "保存失败" ;
					Date nowDate = new Date() ;
					try {
						  FreightRule rule = new FreightRule() ;
						  rule.setVpay(Double.valueOf(StringUtils.nullString(params.get("vpay"),"0")));
                           rule.setFmoney(Double.valueOf(StringUtils.nullString(params.get("fmoney"),"0"))) ;
                           rule.setFdistance(Double.valueOf(StringUtils.nullString(params.get("fdistance"),"0")));
                           rule.setStep_distance(Double.valueOf(StringUtils.nullString(params.get("step_distance"),"0")));
                           rule.setStep_distance_money(Double.valueOf(StringUtils.nullString(params.get("step_distance_money"),"0")));
                           rule.setFweight(Double.valueOf(StringUtils.nullString(params.get("fweight"),"0")));
                           rule.setStep_weight(Double.valueOf(StringUtils.nullString(params.get("step_weight"),"0")));
                           rule.setStep_weight_money(Double.valueOf(StringUtils.nullString(params.get("step_weight_money"),"0")));
                           
                           rule.setCreate_name(Constants.getUser().getRealName()) ;
                           rule.setCreate_time(DateUtils.formatDate(nowDate)) ;
                           
                          // 10+（首距离10km，每增加5km，增加2元）+（首重1kg，每增加2kg，增加1元）
                           
                           String text = StringUtils.nullString(params.get("fmoney")) + "+&nbsp;&nbsp;"  ;
                           StringBuffer weight_text = new StringBuffer(text) ;
							  weight_text.append("(首距离").append(rule.getFdistance()).append("km，每增加")
							    .append(rule.getStep_distance()).append("km，增加").append(rule.getStep_distance_money()).append("元）</br>+（首重")
                                .append(rule.getFweight()).append("kg，每增加").append(rule.getStep_weight()).append("kg，增加")
                                .append(rule.getStep_weight_money()).append("元）") ;
                           rule.setFreight_text(weight_text.toString());
                           freightRuleService.saveRule(rule); 
                          
                          int rid = rule.getId() ;
                          String[] ttype = StringUtils.nullString(params.get("ttype")).split(",") ;
                          String[] itype = StringUtils.nullString(params.get("itype")).split(",") ;
                          
                          List<Map<String, Object>> tList = new ArrayList<Map<String,Object>>() ;
                          for (int i = 0; i < ttype.length; i++) {
							   Map<String, Object> map = new HashMap<String, Object>() ;
							   map.put("rid", rid) ;
							   map.put("ttype", ttype[i]) ;
							   tList.add(map);
						  }
                          freightRuleService.batchTtype(tList);

                          List<Map<String, Object>> iList = new ArrayList<Map<String,Object>>() ;
                          for (int i = 0; i < itype.length; i++) {
							   Map<String, Object> map = new HashMap<String, Object>() ;
							   map.put("rid", rid) ;
							   map.put("itype", itype[i]) ;
							   iList.add(map);
						  }
                          freightRuleService.batchItype(iList); 
                         
                         Integer id = Integer.valueOf(StringUtils.nullString(params.get("id"),"0")) ;
                         if (id!=0) {
                        	 freightRuleService.delById(id) ;
						}
                           
					r = "1" ;
					} catch (Exception e) {
						e.printStackTrace();
					}
					outText(r, response);
				}	
				
				//用于删除rule
				@RequestMapping(value = {"/rdel"})
				public void rdel(@RequestParam Map<String, String> params,
						HttpServletResponse response) throws SQLException {
					Integer id = Integer.valueOf(StringUtils.nullString(params.get("id"),"0")) ;
                    if (id!=0) {
                    	freightRuleService.delById(id) ;
					}
                    outText("1", response);
				}
							


				 // 用于报价试算页面
				@RequestMapping(value = { "/rtrys" })
				public String rtrys(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
						HttpServletResponse response) throws IOException, SQLException {
					model.put("params", params) ;
					return "freightRule/rtrys";
				}
				
				 // 用于报价试算
					@RequestMapping(value = { "/ruleTry" })
					public void ruleTry(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
							HttpServletResponse response) throws IOException {
						String r = "none" ;
						try {
							double weight = Double.valueOf(StringUtils.nullString(params.get("weight"),"0")) ;
							double distance = Double.valueOf(StringUtils.nullString(params.get("distance"),"0")) ;
						  double freight = freightRuleService.freightCalculate(params.get("ttype"), params.get("itemStatus"), weight, distance) ;
                         r = freight + "" ;
						} catch (Exception e) {
							e.printStackTrace();
						}
						outText(r, response);
					}			
}
