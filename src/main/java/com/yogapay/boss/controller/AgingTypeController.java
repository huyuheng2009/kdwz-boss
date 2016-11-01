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
import com.yogapay.boss.domain.AgingType;
import com.yogapay.boss.service.AgingTypeService;
import com.yogapay.boss.utils.StringUtil;


@Controller
@RequestMapping(value = "/aging")
public class AgingTypeController extends BaseController {
	private final static Logger log = LoggerFactory.getLogger(AgingTypeController.class);
    @Resource
    private  AgingTypeService agingTypeService ;

		
		  // 用于物品类型列表
		@RequestMapping(value = { "/item_type" })
		public String item_type(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) throws IOException {
			PageInfo<Map<String, Object>> list = agingTypeService.list(params, getPageInfo(cpage)) ;
			model.put("list", list) ;
			model.put("params", params) ;
			return "agingType/item_type";
		}	
		
		
		
		
		//用于编辑物品类型
		@RequestMapping(value = { "/type_edit"})
		public String type_edit(final ModelMap model,
				@RequestParam Map<String, String> params){
			if(params.get("id") != null){
				Integer id = Integer.parseInt(params.get("id"));
				AgingType agingType = agingTypeService.getById(id) ;
				model.put("agingType", agingType);
			}
			return "agingType/type_edit";
		}

		//用于保存
				@RequestMapping(value = { "/type_save"})
				public void type_save(HttpServletResponse response, HttpServletRequest request,AgingType agingType) throws SQLException{
					int r = 0;
					try {
						if(agingType.getId()== null){
							agingTypeService.save(agingType);
							r = 1;
						}else{
							agingTypeService.update(agingType) ;
							r = 1;
						}
					} catch (Exception e) {
						e.printStackTrace();
						r = 0;
					}
					outText(r + "", response);
				}
				
				//用于删除
				@RequestMapping(value = {"/type_del"})
				public void type_del(@RequestParam Map<String, String> params,
						HttpServletResponse response) throws SQLException {
					if (StringUtil.isEmpty(params.get("id"))) {
						outText("删除失败", response);
					} else {
						agingTypeService.delById(Integer.parseInt(params.get("id")));
						outText("1", response);
					}
				}
				
	
			
	
}
