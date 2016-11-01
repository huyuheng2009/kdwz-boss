package com.yogapay.boss.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.PushMsg;
import com.yogapay.boss.domain.PushNotice;
import com.yogapay.boss.enums.MsgType;
import com.yogapay.boss.service.CourierService;
import com.yogapay.boss.service.MsgService;
import com.yogapay.boss.service.PushNoticeService;
import com.yogapay.boss.service.SubstationService;
import com.yogapay.boss.service.UserService;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.PushUtil;
import com.yogapay.boss.utils.StringUtils;

@Controller
@RequestMapping(value = "/notice")
public class PushNoticeController extends BaseController{
	@Resource
	private PushNoticeService pushNoticeService;	
	@Resource
    private UserService userService ;
	@Resource
	private SubstationService substationService;
	@Resource
	private CourierService courierService;
	@Resource
	private MsgService msgService;
	
	@RequestMapping("/init")
	public String init(HttpServletRequest request,HttpServletResponse response){
		return "notice/add";
	}
	
	@RequestMapping("/sadd")
	public void add(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,PushNotice pushNotice){
		String tip ="";
		String flag="edit";//编辑or新增
		String isSend = params.get("isSend").toString();//推送标志
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (StringUtils.isEmptyWithTrim(pushNotice.getContent())) {
			tip = "内容不能为空";
			outText(tip, response);
			return;
		}
		if (StringUtils.isEmptyWithTrim(pushNotice.getTitle())) {
			tip = "标题不能为空";
			outText(tip, response);
			return;
		}
		
		Date curDate = new Date();
		BossUser bossUser = Constants.getUser() ;
		
		if(params.get("id")==null||"".equals(params.get("id"))){
			pushNotice.setCreateTime(sdf.format(curDate));
			pushNotice.setUserNo(bossUser.getLgcNo());
			flag="add";
		}
		pushNotice.setEditName(bossUser.getRealName());
		pushNotice.setLastUpdateTime(sdf.format(curDate));
		
		if(pushNotice.getPushName()==null||"".equals(pushNotice.getPushName())){
			pushNotice.setPushName(bossUser.getRealName());
		}
		
		if(!isSend.equals("1")&&!isSend.equals("0")){
			isSend="0";
		}
		pushNotice.setIsSend(Integer.parseInt(isSend));
		if(flag.equals("add")){
			pushNoticeService.insert(pushNotice);
		}else if(flag.equals("edit")){
			PushNotice temp = pushNoticeService.findById(Long.parseLong(params.get("id")));
			if(temp==null){
				tip = "记录不存在";
				outText(tip, response);
				return;
			}else{
				if(temp.getIsSend()==1&&"1".equals(isSend)){
					tip = "该条消息已经推送过";
					outText(tip, response);
					return;
				}
				pushNoticeService.updateById(pushNotice);
			}
		}
		//消息推送
		if(isSend.equals("1")){
				
				
				List<Map<String, Object>> couriers= courierService.alist(null);
				List<PushMsg> msgs = new ArrayList<PushMsg>();
				for (Map<String, Object> map : couriers) {
					PushMsg msg = new PushMsg();
					msg.setCreateTime(sdf.format(curDate));
					msg.setExpireTime(sdf.format(DateUtils.addDate(new Date(), 0, 6, 0)));
					msg.setMsgCode(MsgType.NOTICE.getValue());
					msg.setMsgContent(pushNotice.getTitle()+"-"+pushNotice.getContent());
					msg.setMsgData("");
					msg.setReaded(false);
					msg.setUserNo(map.get("courier_no").toString());
					msg.setUserType(2);//向所有分站发
					try {
						msgService.save(msg);
						msgs.add(msg);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				PushUtil.pushByBatchMSG(msgs, dynamicDataSourceHolder.getKey()+"_courier");
			tip="推送成功";
		}else{
			tip="保存成功";
		}
		outText(tip, response);
	}
	
	@RequestMapping("/delete")
	public void delete(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		String id = params.get("id");
		pushNoticeService.deleteById(Long.parseLong(id));
		outText("删除成功", response);
		
	}
	@RequestMapping("/list")
	public String list(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "p", defaultValue = "1") int cpage){
		params.put("type", "0");
		PageInfo<Map<String, Object>> noticeList = pushNoticeService.getList(params, getPageInfo(cpage));
		params.put("type", "1");
		PageInfo<Map<String, Object>> noticeList1 = pushNoticeService.getList(params, getPageInfo(cpage));
		model.put("noticeList", noticeList);
		model.put("noticeList1", noticeList1);
		model.put("p", cpage);
		return "notice/list";
	}
	@RequestMapping("/editInit")
	public String editInit(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		String id = params.get("id");
		PushNotice notic = pushNoticeService.findById(Long.parseLong(id));
		model.put("notice", notic);
		return "notice/edit";
	}
	
	@RequestMapping("/detail")
	public String findById(@RequestParam Map<String, String> params,final ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		String id = params.get("id");
		PushNotice notic = pushNoticeService.findById(Long.parseLong(id));
		notic.setContent(notic.getContent().replace("\r\n", "<br/>").replace("\n", "<br/>").replace("\r", "<br/>").replace(" ", "&nbsp;"));
		model.put("notice", notic);
		return "notice/detail";
	}
}
