package com.yogapay.boss.controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.yogapay.boss.domain.ForCpn;
import com.yogapay.boss.service.ForCpnService;
import com.yogapay.boss.utils.StringUtil;


@Controller
@RequestMapping(value = "/forcpn")
public class ForCpnController extends BaseController {
	private final static Logger log = LoggerFactory.getLogger(ForCpnController.class);
    @Resource
    private ForCpnService forCpnService ;


		
		  // 用于物品类型列表
		@RequestMapping(value = { "/list" })
		public String item_type(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			PageInfo<Map<String, Object>> list = forCpnService.list(params, getPageInfo(cpage)) ;
			model.put("list", list) ;
			model.put("params", params) ;
			return "forcpn/list";
		}	
		
		
		
		
		//用于编辑物品类型
		@RequestMapping(value = { "/kdyblist"})
		public String kdyblist(final ModelMap model,
				@RequestParam Map<String, String> params,@RequestParam(value = "p", defaultValue = "1") int cpage){
			PageInfo<Map<String, Object>> list = forCpnService.kdyblist(params, getPageInfo(cpage,2000)) ;
			model.put("list", list) ;
			model.put("params", params) ;
			return "forcpn/kdyblist";
		}
		
		//用于
		@RequestMapping(value = { "/edit"})
		public String type_edit(final ModelMap model,
				@RequestParam Map<String, String> params){
			if(params.get("id") != null){
				Integer id = Integer.parseInt(params.get("id"));
				ForCpn forCpn = forCpnService.getById(id) ;
				model.put("forCpn", forCpn);
			}
			return "forcpn/edit";
		}

		//用于保存
				@RequestMapping(value = { "/save"})
				public void type_save(HttpServletResponse response, HttpServletRequest request,ForCpn forCpn) throws SQLException{
					int r = 0;
					try {
						if(forCpn.getId()== null){
							forCpnService.save(forCpn);
							r = 1;
						}else{
							forCpnService.update(forCpn) ;
							r = 1;
						}
					} catch (Exception e) {
						e.printStackTrace();
						r = 0;
					}
					outText(r + "", response);
				}
				
				//用于删除
				@RequestMapping(value = {"/del"})
				public void type_del(@RequestParam Map<String, String> params,
						HttpServletResponse response) throws SQLException {
					if (StringUtil.isEmpty(params.get("id"))) {
						outText("删除失败", response);
					} else {
						forCpnService.delById(Integer.parseInt(params.get("id")));
						outText("1", response);
					}
				}
				
	

			
	
}
