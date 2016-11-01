package com.yogapay.boss.controller;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.City;
import com.yogapay.boss.service.CityService;
import com.yogapay.boss.service.OrderService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.Md5;
import com.yogapay.boss.utils.RandomValidateCode;
import com.yogapay.boss.utils.StringUtils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统登录，注销，首页等
 * 
 * @author dj
 * 
 */
@Controller
@RequestMapping(value = "/dict")
public class DictController extends BaseController {
    @Resource
    private CityService cityService ;

   // 用于
	@RequestMapping(value = { "/area" })
	public String alist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage)  {
		int ppId = 0 ;
		if (StringUtils.isEmptyWithTrim(params.get("parentId"))) {
			params.put("parentId", "0") ;
		}else {
			City city = cityService.getById(Integer.parseInt(params.get("parentId")));
			ppId = city!=null?city.getParentId():0 ;
		}
		 
		PageInfo<Map<String, Object>> areaList = cityService.list(params,getPageInfo(cpage));
		model.put("areaList", areaList) ;
		model.put("ppId", ppId) ;
		model.put("params", params) ;
		return "dict/alist";
	}		
	
	@RequestMapping(value = "/ashow")
	public String ashow(final ModelMap model,@RequestParam Map<String, String> params)
			throws SQLException {
		model.put("params", params) ;
		return "dict/ashow";
	}
	
	// 用户删除
	@RequestMapping(value = "/adel")
	public void adel(final ModelMap model,
                     HttpServletRequest request,
                     HttpServletResponse response,
			@RequestParam Map<String, String> params) {
		long id = Long.parseLong(params.get("id").toString()) ;
		String r = "1" ;
		try {
			Map<String, String> param = new HashMap<String, String>() ;
			param.put("parentId", params.get("id")) ;
			PageInfo<Map<String, Object>> areaList = cityService.list(param,getPageInfo(1,1));
			if (areaList.getTotal()>0) {
				r = "请先删除下级区域！" ;
			}else {
				cityService.delById(id);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			outText("删除失败！", response);
		}
		outText(r, response);
	}
	
	@RequestMapping(value = "/asave")
	public void asave(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, String> params){
		City city = new City() ;
		int r = 0;
		try {
			if (!StringUtils.isEmptyWithTrim(params.get("id"))) {
				city.setId(Integer.parseInt(params.get("id")));
				city.setName(params.get("name"));
                r=1;
    			cityService.update(city);
			} else {
				r = 1;
				if (!StringUtils.isEmptyWithTrim(params.get("level"))) {
					city.setLevel(Integer.parseInt(params.get("level")));
				}else {
					city.setLevel(1);
				}
				if (!StringUtils.isEmptyWithTrim(params.get("parentId"))) {
					city.setParentId(Integer.parseInt(params.get("parentId")));
				}else {
					city.setParentId(0);
				}
				city.setName(params.get("name"));
				cityService.save(city);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			r = 0;
		}
		outText(r + "", response);
	}
	
	   // 用于
		@RequestMapping(value = { "/company" })
		public String clist(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			/*PageInfo<Map<String, Object>> orderList = orderService.list(params,getPageInfo(cpage));
			model.put("orderList", orderList) ;
			model.put("params", params) ;
			*/
			return "order/olist";
		}		
	
}
