package com.yogapay.boss.utils.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.AppProduct;
import com.yogapay.boss.domain.CodRateType;
import com.yogapay.boss.domain.Courier;
import com.yogapay.boss.domain.MatterType;
import com.yogapay.boss.domain.MonthSettleType;
import com.yogapay.boss.domain.Substation;
import com.yogapay.boss.domain.SysDict;
import com.yogapay.boss.utils.StringUtils;

/**
 * 字典项查询
 * 
 * 
 */
@SuppressWarnings("serial")
public class Dict extends BodyTagSupport implements ApplicationContextAware {
	private static ApplicationContext applicationContext; // Spring应用上下文环境
	/**
	 * 字典名称 dict_name
	 */
	private String name;

	private static List<SysDict> allDict = null;
	private static List<Map<String, Object>> allProStatus = null ;
	private static List<Map<String, Object>> payType = null ;
	private static ManagerDao managerDao = null ;
	/**
	 * 字典key
	 */
	private String key;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		BaseDao baseDao = applicationContext.getBean(BaseDao.class); 
		managerDao = applicationContext.getBean(ManagerDao.class) ;
		String resultVal = key;
		try {
			Map<String, String> params = new HashMap<String, String>() ;
			if ("PRODUCT".equals(name)) {
				params.put("appCode", key) ;
				AppProduct appProduct = managerDao.getOne("AppProduct.getByAppCode", params) ;
				resultVal = appProduct.getAppName() ;
			}else if("S".equals(name)){
				params.put("sno", key) ;
				Substation substation = baseDao.getFrist("Substation.getBySno", params) ;
				resultVal = substation==null?key:substation.getSubstationName() ;
			}else if("C".equals(name)){
				params.put("courierNo", key) ;
				Courier courier = baseDao.getOne("Courier.getByNo", params) ;
				resultVal = courier==null?key:courier.getRealName() ;
			}else if("COD".equals(name)){
				params.put("codNo", key) ;
				Map<String, Object> codUser = baseDao.getOne("CodUser.getCuserByNo", params);
				resultVal = codUser==null?key:StringUtils.nullString(codUser.get("cod_name"));
			}else if("MONTHUSER".equals(name)){
				params.put("monthSettleNo", key);
				Map<String, Object> monthUser =baseDao.getOne("MobileUser.getMuserByNo", params);
				resultVal = monthUser==null?key:StringUtils.nullString(monthUser.get("month_name"));
			}else if("MATTER_TYPE".equals(name)){
				MatterType matterType = baseDao.getById("MatterType.getById", key);
				resultVal = matterType==null?key:matterType.getTypeName() ;
			}else if("PAY_TYPE".equals(name)){
				//params.put("payCode", key) ;
				//Map<String, Object> payTypeMap = managerDao.getOne("PayType.getByCode", params);
				//resultVal = payTypeMap==null?key:payTypeMap.get("pay_name").toString() ;
				resultVal = getpayType(key) ;
			}else if("PRO_REASON".equals(name)){
				params.put("id", key) ;
				Map<String, Object> map = baseDao.getOne("ProOrderReason.getById", params);
				resultVal = map==null?key:map.get("context").toString() ;
			}else if("ORDER_NOTE".equals(name)){
				params.put("orderId", key) ;
				List<Map<String, Object>> ret= baseDao.getList("OrderInfo.getNoteList", params);
				resultVal = "" ;
				for (Map<String, Object> map:ret) {
					resultVal += map==null?"":StringUtils.nullString(map.get("note"))+"   " ;
				}
			}else if("DEAL_STATUS".equals(name)){
				if (allProStatus==null) {
					allProStatus = baseDao.getList("ProOrderStatus.list");
				}
				resultVal = "" ;
				for (Map<String,Object> map:allProStatus) {
					if (key.equals(map.get("id").toString())) {
						resultVal = map.get("content").toString() ;
						break ;
					}
				}
			}else if("MSTYPE".equals(name)){    //月结优惠类型
				MonthSettleType monthSettleType = baseDao.getById("MonthSettleType.getById", key);
				resultVal = monthSettleType==null?key:monthSettleType.getName() ;
			}else if("CSTYPE".equals(name)){    //月结优惠类型
				CodRateType codRateType = baseDao.getById("CodRateType.getById", key);
				resultVal = codRateType==null?key:codRateType.getName() ;
			}else if("DEAL_STATUS_COLOR".equals(name)){
				if (allProStatus==null) {
					allProStatus = baseDao.getList("ProOrderStatus.list");
				}
				resultVal = "" ;
				for (Map<String,Object> map:allProStatus) {
					if (key.equals(map.get("id").toString())) {
						resultVal = map.get("color").toString() ;
						break ;
					}
				}
			}else if("DEAL_STATUS_BACKGROUND_COLOR".equals(name)){
				if (allProStatus==null) {
					allProStatus = baseDao.getList("ProOrderStatus.list");
				}
				resultVal = "" ;
				for (Map<String,Object> map:allProStatus) {
					if (key.equals(map.get("id").toString())) {
						resultVal = map.get("background_color").toString() ;
						break ;
					}
				}
			}
			else{
				if (null == allDict) {
					//allDict = baseDao.getList("SysDict.getSysDict") ;
					allDict = managerDao.getList("SysDict.getSysDict") ;
				}
				boolean h = false ;
				for (SysDict d : allDict) {
					if (d.getDictName().equals(name.toUpperCase())) {
						if (d.getDictKey().equals(key.toUpperCase())) {
							resultVal = d.getDictValue();
							h = true ;
							break;
						}
					}
				}
				//找不到重新加载
				if (!h) {
					allDict = managerDao.getList("SysDict.getSysDict") ;
					for (SysDict d : allDict) {
						if (d.getDictName().equals(name.toUpperCase())) {
							if (d.getDictKey().equals(key.toUpperCase())) {
								resultVal = d.getDictValue();
								h = true ;
								break;
							}
						}
					}
				}
			}
				
			out.write(resultVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	
	public String getpayType(String payCode) {
		String r = payCode ;
		 if (payType==null) {
			payType = managerDao.getList("PayType.list") ;
		 }
		for (Map<String, Object> p:payType) {
			if(payCode.equals(p.get("pay_code"))){
				r = StringUtils.nullString(p.get("pay_name")) ;
			}
		}
		return r;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;

	}

}
