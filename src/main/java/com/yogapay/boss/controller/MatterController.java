package com.yogapay.boss.controller;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
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
import com.yogapay.boss.domain.MatterPro;
import com.yogapay.boss.domain.MatterType;
import com.yogapay.boss.domain.MatterWarehouseEnter;
import com.yogapay.boss.service.MatterProService;
import com.yogapay.boss.service.MatterTypeService;
import com.yogapay.boss.service.MatterWarehouseEnterService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.StringUtils;

@Controller
@RequestMapping(value = "/matter")
public class MatterController extends BaseController {
	private final static Logger log = LoggerFactory.getLogger(MatterController.class);
	
    @Resource
    private MatterProService matterProService ;
    @Resource
    private UserService userService ;
    @Resource
    private MatterTypeService matterTypeService ;
    @Resource
    private MatterWarehouseEnterService matterWarehouseEnterService ;
	
	//用于编辑、新增
	@RequestMapping(value = { "/pro_edit"})
	public String pro_edit(final ModelMap model,@RequestParam Map<String, String> params){
		if(params.get("id") != null){
			Integer id = Integer.parseInt(params.get("id"));
			MatterPro matterPro =  matterProService.getById(id) ;
			model.put("matterPro", matterPro);
		}
		List<Map<String, Object>> lgcs = userService.getCurrentLgc();
		model.put("lgcs", lgcs) ;
		return "matter/pro_edit";
	}
	
	
	
	//用于保存
	@RequestMapping(value = { "/pro_save"})
	public void prosave(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, String> params,MatterPro matterPro) throws SQLException{
		String r = "1";
		try {
			
			String mt = params.get("matterType_") ;
			params.put("name", mt) ;
			MatterType matterType = matterTypeService.getByName(params) ;
			if (matterType==null) {
				matterType = new MatterType() ;
				matterType.setLgcNo(matterPro.getLgcNo());
				matterType.setTypeName(mt);
				matterTypeService.saveMatterPro(matterType) ;
			}
			 matterPro.setMatterType(matterType.getId());
			 
			if(matterPro.getId() == null){
				 MatterPro mPro = matterProService.getByNo(matterPro.getMatterNo(), matterPro.getLgcNo()) ;
				 if (mPro!=null) {
					r = "物料编号已经存在" ;
				}else {
					mPro = matterProService.getByName(matterPro.getMatterName(), matterPro.getLgcNo()) ;
					if (mPro!=null) {
						r = "物料品名已经存在" ;
					}else {
						  matterProService.saveMatterPro(matterPro) ;
					}
				}
			}else{
				  matterProService.updateMatterPro(matterPro) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r = "数据有误";
		}
		outText(r, response);
	}
	
	//用于保存快递公司
	@RequestMapping(value = { "/prodel"})
	public void prodel(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, String> params) throws SQLException{
		String r = "1";
		try {
			if(!StringUtils.isEmptyWithTrim(params.get("id"))){
                  matterProService.delById(Integer.parseInt(params.get("id"))) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r = "数据有误";
		}
		outText(r, response);
	}
	
    // 用于
	@RequestMapping(value = { "/matter_pro" })
	public String list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
		
		String lgcNoString  =  Constants.getUser().getLgcNo() ;
		if (StringUtils.isEmptyWithTrim(params.get("lgcNo"))) {
			params.put("lgcNo", Constants.getUser().getLgcNo()) ;
		}else {
			if (!lgcNoString.contains(params.get("lgcNo"))) {
				params.put("lgcNo", "0000") ;
			}
		}
	    PageInfo<Map<String, Object>> list = matterProService.list(params,getPageInfo(cpage));
		model.put("list", list) ;
		model.put("params", params) ;
		return "matter/plist";
	}	
	
    // 用于
	@RequestMapping(value = { "/enter" })
	public String enter(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage) {
        params = setSubstationNo(params,request) ;
		Date nowDate = new Date() ;
		String beginTime = DateUtils.formatDate(DateUtils.addDate(nowDate, -30, 0, 0),"yyyy-MM-dd") ;
		String endTime = DateUtils.formatDate(nowDate,"yyyy-MM-dd") ;
		if (StringUtils.isEmptyWithTrim(params.get("createTimeBegin"))) {
			params.put("createTimeBegin", beginTime) ;
		}
		if (!StringUtils.isEmptyWithTrim(params.get("createTimeEnd"))) {
			endTime = params.get("createTimeEnd") ;
		}
		params.put("createTimeEnd", endTime+" 23:59:59") ;
		
        
	    PageInfo<Map<String, Object>> list = matterWarehouseEnterService.list(params,getPageInfo(cpage));
		model.put("list", list) ;
		params.put("createTimeEnd", endTime) ;
		model.put("params", params) ;
		return "matter/elist";
	}	
	

	//用于新增物料
	@RequestMapping(value = { "/enter_add"})
	public String enter_add(final ModelMap model,@RequestParam Map<String, String> params,HttpServletRequest request){
		List<Map<String, Object>> substations = userService.getCurrentSubstation(StringUtils.nullString(request.getAttribute("sub_limit")));
		model.put("substations", substations);
		return "matter/enter_add";
	}
	
	//用于物料入库保存
	@RequestMapping(value = { "/enter_add_save"})
	public void enter_add_save(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, String> params,
			 MatterWarehouseEnter matterWarehouseEnter) throws SQLException{
		String r = "1";
		try {
			matterWarehouseEnterService.saveEnter(matterWarehouseEnter) ;
		} catch (Exception e) {
			e.printStackTrace();
			r = "数据有误";
		}
		outText(r, response);
	}
		

}
