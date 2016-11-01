package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.LgcConfig;
import com.yogapay.boss.domain.OrderInfo;
import com.yogapay.boss.domain.SinputInfo;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtil;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.StringUtil;
import com.yogapay.boss.utils.StringUtils;

@SuppressWarnings({"rawtypes", "unchecked"})
@Service
public class OrderService {

    @Resource
    private BaseDao baseDao;
    //private OrderDao orderDao ;

    public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
        //PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize()) ;
        //PageInfo<Map<String, Object>> page =new PageInfo(orderDao.list(params));
        //return  page;
    	Map<String, String> pMap = new HashMap<String, String>() ;
    	 String orderNos = StringUtils.nullString(params.get("orderNo"));
        String[] ods = {};
        boolean orderby = false;
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
            String o = "";
            orderNos = orderNos.replace("'", "");
            ods = orderNos.split("\r\n");
            for (int i = 0; i < ods.length; i++) {
                o = o + "'" + ods[i] + "',";
            }
            o = o.substring(0, o.length()-1) ;
            params.put("orderNos", o);
            pMap.put("orderNos", o);
            pMap.put("substationNo", params.get("substationNo")) ;
            pMap.put("take_or_send_no", params.get("take_or_send_no")) ;
            pMap.put("zid", params.get("zid")) ;
            pMap.put("orderAssign", params.get("orderAssign")) ;
            orderby = true;
        }else {
        	pMap.putAll(params);
        	pMap.remove("orderNos") ;
		}
        PageInfo<Map<String, Object>> list = baseDao.getByPage("OrderInfo.list", pageInfo, pMap);
        if (orderby) {
            Collections.sort(list.getList(), new OrderComparator(ods));
        }
        return list;
    }
    public List<Map<String, Object>> getList(Map<String, String> params) {
    	//PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize()) ;
    	//PageInfo<Map<String, Object>> page =new PageInfo(orderDao.list(params));
    	//return  page;
    	Map<String, String> pMap = new HashMap<String, String>() ;
    	 String orderNos = StringUtils.nullString(params.get("orderNo"));
    	String[] ods = {};
    	boolean orderby = false;
    	if (!StringUtil.isEmptyWithTrim(orderNos)) {
    		  String o = "";
              orderNos = orderNos.replace("'", "");
              ods = orderNos.split("\r\n");
              for (int i = 0; i < ods.length; i++) {
                  o = o + "'" + ods[i] + "',";
              }
              o = o.substring(0, o.length()-1) ;
    		params.put("orderNos", o);
    		   pMap.put("orderNos", o);
    		   pMap.put("substationNo", params.get("substationNo")) ;
    		   pMap.put("take_or_send_no", params.get("take_or_send_no")) ;
    		   pMap.put("zid", params.get("zid")) ;
    		   pMap.put("orderAssign", params.get("orderAssign")) ;
               orderby = true;
           }else {
           	pMap.putAll(params);
           	pMap.remove("orderNos") ;
   		}
    	List<Map<String, Object>> list = baseDao.getList("OrderInfo.getList", pMap);
    	if (orderby) {
    		Collections.sort(list, new OrderComparator(ods));
    	}
    	return list;
    }
    
    
    public List<Map<String, Object>> sendEcount(Map<String, String> params) {
    	 String orderNos = StringUtils.nullString(params.get("orderNo"));
        String[] ods = {};
        Map<String, String> pMap = new HashMap<String, String>() ;
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
        	  String o = "";
              orderNos = orderNos.replace("'", "");
              ods = orderNos.split("\r\n");
              for (int i = 0; i < ods.length; i++) {
                  o = o + "'" + ods[i] + "',";
              }
              o = o.substring(0, o.length()-1) ;
            params.put("orderNos", o);
            pMap.put("orderNos", o);
            pMap.put("substationNo", params.get("substationNo")) ;
            pMap.put("zid", params.get("zid")) ;
        }else {
        	pMap.putAll(params);
        	pMap.remove("orderNos") ;
		}
        List<Map<String, Object>> list = baseDao.getList("OrderInfo.sendEcount", pMap);
        return list;
    }
    
    
    public List<Map<String, Object>> revEcount(Map<String, String> params) {
    	 String orderNos = StringUtils.nullString(params.get("orderNo"));
        String[] ods = {};
        Map<String, String> pMap = new HashMap<String, String>() ;
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
        	  String o = "";
              orderNos = orderNos.replace("'", "");
              ods = orderNos.split("\r\n");
              for (int i = 0; i < ods.length; i++) {
                  o = o + "'" + ods[i] + "',";
              }
              o = o.substring(0, o.length()-1) ;
            params.put("orderNos", o);
            pMap.put("orderNos", o);
            pMap.put("substationNo", params.get("substationNo")) ;
            pMap.put("zid", params.get("zid")) ;
        }else {
        	pMap.putAll(params);
        	pMap.remove("orderNos") ;
		}
        List<Map<String, Object>> list = baseDao.getList("OrderInfo.revEcount", pMap);
        return list;
    }

    public PageInfo<Map<String, Object>> codList(Map<String, String> params, Page pageInfo) {
     	Map<String, String> pMap = new HashMap<String, String>() ;
     	 String orderNos = StringUtils.nullString(params.get("orderNo"));
    	String[] ods = {};
    	boolean orderby = false;
    	if (!StringUtil.isEmptyWithTrim(orderNos)) {
    		  String o = "";
              orderNos = orderNos.replace("'", "");
              ods = orderNos.split("\r\n");
              for (int i = 0; i < ods.length; i++) {
                  o = o + "'" + ods[i] + "',";
              }
              o = o.substring(0, o.length()-1) ;
    		params.put("orderNos", o);
    		   pMap.put("orderNos", o);
    		   pMap.put("substationNo", params.get("substationNo")) ;
    		   pMap.put("zid", params.get("zid")) ;
               orderby = true;
           }else {
           	pMap.putAll(params);
           	pMap.remove("orderNos") ;
   		}
    	LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	pMap.put("reportExam", lgcConfig.getReportExam()) ;
        return baseDao.getByPage("OrderInfo.codList", pageInfo, pMap);
    }

    public List<Map<String, Object>> list(Map<String, String> params) {
    	Map<String, String> pMap = new HashMap<String, String>() ;
    	 String orderNos = StringUtils.nullString(params.get("orderNo"));
    	String[] ods = {};
    	boolean orderby = false;
    	if (!StringUtil.isEmptyWithTrim(orderNos)) {
    		  String o = "";
              orderNos = orderNos.replace("'", "");
              ods = orderNos.split("\r\n");
              for (int i = 0; i < ods.length; i++) {
                  o = o + "'" + ods[i] + "',";
              }
              o = o.substring(0, o.length()-1) ;
    		params.put("orderNos", o);
    		   pMap.put("orderNos", o);
    		   pMap.put("substationNo", params.get("substationNo")) ;
    		   pMap.put("take_or_send_no", params.get("take_or_send_no")) ;
    		   pMap.put("zid", params.get("zid")) ;
               orderby = true;
           }else {
           	pMap.putAll(params);
           	pMap.remove("orderNos") ;
   		}
        return baseDao.getList("OrderInfo.list", pMap);
    }

    public List<Map<String, Object>> findByIds(String ids) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ids", ids);
        return baseDao.getList("OrderInfo.findByIds", params);
    }
    
    public List<Map<String, Object>> findSignByIds(String ids) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ids", ids);
        return baseDao.getList("OrderInfo.findSignByIds", params);
    }

    public Map<String, Object> findByOrderNo(int id, String orderNo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("orderNo", orderNo);
        return baseDao.getOne("OrderInfo.findByOrderNoId", params);
    }

    public Map<String, Object> findByOrderNo(String orderNo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderNo", orderNo);
        return baseDao.getOne("OrderInfo.findByOrderNo", params);
    }
    
    //获取签收订单
    public Map<String, Object> findSignByOrderNo(String orderNo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderNo", orderNo);
        return baseDao.getOne("OrderInfo.findSignByOrderNo", params);
    }

    public Map<String, Object> findByLgcOrderNo(String lgcNo, String lgcOrderNo, String phone) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("lgcNo", lgcNo);
        params.put("lgcOrderNo", lgcOrderNo);
        params.put("phone", phone);
        String orderNos  = StringUtils.nullString(params.get("lgcOrderNo"));
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
            String o = "";
            orderNos = orderNos.replace("'", "");
            String[] ods = orderNos.split("\r\n");
            for (int i = 0; i < ods.length; i++) {
                o = o + "'" + ods[i] + "',";
            }
            o = o.substring(0, o.length()-1) ;
            params.put("lgcOrderNos", o);
        }else {
        	params.remove("lgcOrderNos") ;
		}
        List<Map<String, Object>> list = baseDao.getByPage("OrderInfo.findByLgcOrderNo", new Page(1, 10), params).getList();
        if (list == null || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }
    
    public Map<String, Object> findByLgcOrderNo( String lgcOrderNo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("lgcOrderNo", lgcOrderNo);
        String orderNos  = StringUtils.nullString(params.get("lgcOrderNo"));
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
            String o = "";
            orderNos = orderNos.replace("'", "");
            String[] ods = orderNos.split("\r\n");
            for (int i = 0; i < ods.length; i++) {
                o = o + "'" + ods[i] + "',";
            }
            o = o.substring(0, o.length()-1) ;
            params.put("lgcOrderNos", o);
        } else {
        	params.remove("lgcOrderNos") ;
		}
        List<Map<String, Object>> list = baseDao.getList("OrderInfo.findByLgcOrderNo",  params);
        if (list == null || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    public PageInfo<Map<String, Object>> findByLgcOrderNo1(Map<String, String> params, Page pageInfo) {
        Map<String, String> p = new HashMap<String, String>(params);
        if (!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo")) || !StringUtil.isEmptyWithTrim(params.get("orderNo"))) {
            p.remove("createTimeBegin");
            p.remove("createTimeEnd");
        }
        String orderNos  = StringUtils.nullString(params.get("lgcOrderNo"));
        String[] ods = {};
        boolean orderby = false;
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
            String o = "";
            orderNos = orderNos.replace("'", "");
            ods = orderNos.split("\r\n");
            for (int i = 0; i < ods.length; i++) {
                o = o + "'" + ods[i] + "',";
            }
            o = o.substring(0, o.length()-1) ;
            p.put("lgcOrderNos", o);
            orderby = true;
        }else {
        	p.remove("lgcOrderNos") ;
		}
        PageInfo<Map<String, Object>> list = baseDao.getByPage("OrderInfo.findByLgcOrderNo", pageInfo, p);
        if (orderby) {
            Collections.sort(list.getList(), new OrderComparator(ods));
        }
        return list;
    }
    
    public PageInfo<Map<String, Object>> findTrack(Map<String, String> params, Page pageInfo) {
        Map<String, String> p = new HashMap<String, String>(params);
        if (!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo")) || !StringUtil.isEmptyWithTrim(params.get("orderNo"))) {
            p.remove("createTimeBegin");
            p.remove("createTimeEnd");
        }
        String orderNos  = StringUtils.nullString(params.get("lgcOrderNo"));
        String[] ods = {};
        boolean orderby = false;
        Map<String, String> pMap = new HashMap<String, String>() ;
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
            String o = "";
            orderNos = orderNos.replace("'", "");
            ods = orderNos.split("\r\n");
            for (int i = 0; i < ods.length; i++) {
                o = o + "'" + ods[i] + "',";
            }
            o = o.substring(0, o.length()-1) ;
            p.put("lgcOrderNos", o);
            orderby = true;
            pMap.put("lgcOrderNos", o);
            pMap.put("noType", params.get("noType"));
            pMap.put("substationNo", p.get("substationNo")) ;
        }else {
        	pMap.putAll(p);
        	pMap.remove("lgcOrderNos") ;
		}
        PageInfo<Map<String, Object>> list = baseDao.getByPage("OrderInfo.findTrack", pageInfo, pMap);
        if (orderby) {
            Collections.sort(list.getList(), new OrderComparator(ods));
        }
        return list;
    }
    
    

    //根据订单号获取物流公司的订单信息
    public Map<String, Object> getLgcInfo(String orderNo) {
        //return orderDao.getLgcInfo(orderNo) ;
        return baseDao.getOne("OrderInfo.getLgcInfo", orderNo);
    }

    public PageInfo<Map<String, Object>> ehistory(Map<String, String> params, Page pageInfo) {
        String orderNos  = StringUtils.nullString(params.get("orderNo"));
        boolean orderby = false;
        String[] ods = {};
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
            String o = "";
            orderNos = orderNos.replace("'", "");
            ods = orderNos.split("\r\n");
            for (int i = 0; i < ods.length; i++) {
                o = o + "'" + ods[i] + "',";
            }
            o = o.substring(0, o.length()-1) ;
            params.put("orderNos", o);
            orderby = true;
        }else {
        	params.remove("orderNos") ;
		}
        PageInfo<Map<String, Object>> list = baseDao.getByPage("OrderInfo.ehistory", pageInfo, params);
        if (orderby) {
            Collections.sort(list.getList(), new OrderComparator(ods));
        }
        return list;
    }

    public List<Map<String, Object>> getOrderImages(String orderNo) {
        return baseDao.getList("OrderInfo.getOrderImages", orderNo);
    }

    public int asign(Map<String, String> params) {
    	params.put("asignName", Constants.getUser().getRealName()) ;
        return baseDao.update("OrderInfo.asign", params);
    }
    
    public int updateAddrId(Map<String, String> params) {
        return baseDao.update("OrderInfo.updateAddrId", params);
    }
    
    public int updateStatus(String status,String orderNo) {
    	Map<String, String> params = new HashMap<String, String>() ;
    	params.put("s", status) ;
    	params.put("orderNo", orderNo) ;
        return baseDao.update("OrderInfo.updateStatus", params);
    }
    

    public void save(OrderInfo orderInfo) {
        baseDao.insert("OrderInfo.save", orderInfo);
    }
    
    public void takesave(OrderInfo orderInfo) {
        baseDao.insert("OrderInfo.takesave", orderInfo);
    }
    
    //保存子单
    public void saveZid(OrderInfo orderInfo) {
        baseDao.insert("OrderInfo.saveZid", orderInfo);
    }

    public Map<String, Object> getSinputInfoById(Integer id) {
        return baseDao.getOne("OrderInfo.getSinputInfoById", id);
    }

    public void sinputInfo(OrderInfo orderInfo, String type) {
        SinputInfo sinputInfo = new SinputInfo();
        sinputInfo.setOrderNo(orderInfo.getOrderNo());
        sinputInfo.setLgcOrderNo(orderInfo.getLgcOrderNo());
        sinputInfo.setSiFreightType(Integer.valueOf(orderInfo.getFreightType()));
        sinputInfo.setSiFreight(orderInfo.getFreight());
        sinputInfo.setSiPayType(orderInfo.getPayType());
        sinputInfo.setSiMonthSettleNo(orderInfo.getMonthSettleNo());
        sinputInfo.setSiDisUserNo(orderInfo.getDisUserNo());
        sinputInfo.setSiCodName(orderInfo.getCodName());
        sinputInfo.setSiGoodPrice(orderInfo.getGoodPrice());
        sinputInfo.setSiGoodValuation(orderInfo.getGoodValuation());
        sinputInfo.setSiCpay(orderInfo.getCpay());
        sinputInfo.setSiVpay(orderInfo.getVpay());
        sinputInfo.setSiSnpay(Float.valueOf(orderInfo.getSnpay()));
        sinputInfo.setSiPayAcount(orderInfo.getPayAcount());
        sinputInfo.setCreateTime(DateUtils.formatDate(new Date()));
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        sinputInfo.setOperator(bossUser.getUserName());
        sinputInfo.setOpType(type);
        sinputInfo.setErred(orderInfo.getErred());
        sinputInfoDel(orderInfo.getOrderNo()) ;
        baseDao.insert("OrderInfo.sinputInfo", sinputInfo);
    }

    public void sinputInfoSend(Map<String, String> params) {
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        params.put("operator", bossUser.getUserName());
        sinputInfoDel(params.get("orderNo")) ;
        baseDao.insert("OrderInfo.sinputInfoSend", params);
    }
    
    public void editOrderRecord(OrderInfo orderInfo) {
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        orderInfo.setInputer( bossUser.getUserName());
        baseDao.insert("OrderInfo.editOrderRecord", orderInfo);
    }

    public void sinputInfoSign(Map<String, String> params) {
        BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
        params.put("operator", bossUser.getUserName());
        sinputInfoDel(params.get("orderNo")) ;
        baseDao.insert("OrderInfo.sinputInfoSign", params);
        baseDao.insert("OrderInfo.editRecord", params);
    }

    public void sinputInfoDel(String orderNo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderNo", orderNo);
        baseDao.deleteByParams("OrderInfo.sinputInfoDel", params);
    }

    public int asignTos(Map<String, String> params) {
        return baseDao.update("OrderInfo.asignTos", params);
    }

    public PageInfo<Map<String, Object>> getSendOrder(Map<String, String> params, Page pageInfo) {
        Map<String, String> p = new HashMap<String, String>(params);
        if (!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo")) || !StringUtil.isEmptyWithTrim(params.get("orderNo"))) {
            p.remove("createTimeBegin");
            p.remove("createTimeEnd");
        }
        return baseDao.getByPage("OrderInfo.getSendOrder", pageInfo, p);
    }

    public PageInfo<Map<String, Object>> getRevOrder(Map<String, String> params, Page pageInfo) {
        Map<String, String> p = new HashMap<String, String>(params);
        if (!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo")) || !StringUtil.isEmptyWithTrim(params.get("orderNo"))) {
            p.remove("createTimeBegin");
            p.remove("createTimeEnd");
        }
        return baseDao.getByPage("OrderInfo.getRevOrder", pageInfo, p);
    }

    public void proOrder(String orderNo, int proId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderNo", orderNo);
        params.put("proId", proId);
        baseDao.update("OrderInfo.proOrder", params);
    }
  //编辑更新寄件单记录
    public int editUpdate(OrderInfo orderInfo) {
        return baseDao.update("OrderInfo.editUpdate", orderInfo);
    }
    
    //运单修改
    public int orderEditSave(OrderInfo orderInfo) {
        return baseDao.update("OrderInfo.orderEditSave", orderInfo);
    }
    
    
    //编辑更新签收单记录
    public int editSignUpdate(OrderInfo orderInfo) {
        return baseDao.update("OrderInfo.editSignUpdate", orderInfo);
    }

    public int takeUpdate(OrderInfo orderInfo) {
        return baseDao.update("OrderInfo.takeUpdate", orderInfo);
    }

    public int takeUpdateFirst(OrderInfo orderInfo) {
        return baseDao.update("OrderInfo.takeUpdateFirst", orderInfo);
    }
    
    public int takeScanUpdate(OrderInfo orderInfo) {
        return baseDao.update("OrderInfo.takeScanUpdate", orderInfo);
    }

    public int signUpdate(OrderInfo orderInfo) {
        return baseDao.update("OrderInfo.signUpdate", orderInfo);
    }

    public PageInfo<Map<String, Object>> examineOrder(Map<String, String> params, Page pageInfo) {
        Map<String, String> p = new HashMap<String, String>(params);
        if (!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo")) || !StringUtil.isEmptyWithTrim(params.get("orderNo"))) {
            p.remove("createTimeBegin");
            p.remove("createTimeEnd");
        }
        
        String orderNos = StringUtils.nullString(params.get("orderNo"));
        String[] ods = {};
        boolean orderby = false;
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
            String o = "";
            orderNos = orderNos.replace("'", "");
            ods = orderNos.split("\r\n");
            for (int i = 0; i < ods.length; i++) {
                o = o + "'" + ods[i] + "',";
            }
            o = o.substring(0, o.length()-1) ;
            p.put("orderNos", o);
            orderby = true;
        }else {
			p.remove("orderNos") ;
		}
        PageInfo<Map<String, Object>> list = baseDao.getByPage("OrderInfo.examineOrder", pageInfo, p);
        if (orderby) {
            Collections.sort(list.getList(), new OrderComparator(ods));
        }
        return list;
    }

    public PageInfo<Map<String, Object>> sendOrder(Map<String, String> params, Page pageInfo) {
        Map<String, String> p = new HashMap<String, String>(params);
        if (!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo")) || !StringUtil.isEmptyWithTrim(params.get("orderNo"))) {
            p.remove("createTimeBegin");
            p.remove("createTimeEnd");
        }
        String orderNos  = StringUtils.nullString(params.get("orderNo"));
        String[] ods = {};
        boolean orderby = false;
        Map<String, String> pMap = new HashMap<String, String>() ;
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
            String o = "";
            orderNos = orderNos.replace("'", "");
            ods = orderNos.split("\r\n");
            for (int i = 0; i < ods.length; i++) {
                o = o + "'" + ods[i] + "',";
            }
            o = o.substring(0, o.length()-1) ;
            p.put("orderNos", o);
            orderby = true;
            pMap.put("orderNos", o);
            pMap.put("substationNo", p.get("substationNo")) ;
            pMap.put("zid", p.get("zid")) ;
        }else {
        	pMap.putAll(p);
        	pMap.remove("orderNos") ;
		}
        PageInfo<Map<String, Object>> list = baseDao.getByPage("OrderInfo.sendOrder", pageInfo, pMap);
        if (orderby) {
            Collections.sort(list.getList(), new OrderComparator(ods));
        }
        return list;
    }
    public List<Map<String, Object>> sendOrderList(Map<String, String> params) {
    	Map<String, String> p = new HashMap<String, String>(params);
    	if (!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo")) || !StringUtil.isEmptyWithTrim(params.get("orderNo"))) {
    		p.remove("createTimeBegin");
    		p.remove("createTimeEnd");
    	}
    	 String orderNos  = StringUtils.nullString(params.get("orderNo"));
    	String[] ods = {};
    	boolean orderby = false;
    	if (!StringUtil.isEmptyWithTrim(orderNos)) {
    		String o = "";
    		orderNos = orderNos.replace("'", "");
    		ods = orderNos.split("\r\n");
    	    for (int i = 0; i < ods.length; i++) {
                o = o + "'" + ods[i] + "',";
            }
            o = o.substring(0, o.length()-1) ;
    		p.put("orderNos", o);
    		p.put("substationNo", p.get("substationNo")) ;
            p.put("zid", p.get("zid")) ;
    		orderby = true;
    	}else {
			p.remove("orderNos") ;
		}
    	List<Map<String, Object>> list = baseDao.getList("OrderInfo.sendOrderList", p);
    	if (orderby) {
    		Collections.sort(list, new OrderComparator(ods));
    	}
    	return list;
    }

    public PageInfo<Map<String, Object>> examinePassOrder(Map<String, String> params, Page pageInfo) {
        Map<String, String> p = new HashMap<String, String>(params);
        if (!StringUtil.isEmptyWithTrim(params.get("lgcOrderNo")) || !StringUtil.isEmptyWithTrim(params.get("orderNo"))) {
            p.remove("createTimeBegin");
            p.remove("createTimeEnd");
        }
        String orderNos  = StringUtils.nullString(params.get("orderNo"));
        String[] ods = {};
        boolean orderby = false;
        Map<String, String> pMap = new HashMap<String, String>() ;
        if (!StringUtil.isEmptyWithTrim(orderNos)) {
            String o = "";
            orderNos = orderNos.replace("'", "");
            ods = orderNos.split("\r\n");
            for (int i = 0; i < ods.length; i++) {
                o = o + "'" + ods[i] + "',";
            }
            o = o.substring(0, o.length()-1) ;
            p.put("orderNos", o);
            orderby = true;
            pMap.put("orderNos", o);
            pMap.put("substationNo", p.get("substationNo")) ;
            pMap.put("zid", p.get("zid")) ;
        }else {
        	pMap.putAll(p);
        	pMap.remove("orderNos") ;
		}
        PageInfo<Map<String, Object>> list = baseDao.getByPage("OrderInfo.examinePassOrder", pageInfo, pMap);
        if (orderby) {
            Collections.sort(list.getList(), new OrderComparator(ods));
        }
        return list;
    }

    public int examine(Map<String, String> params) {
        return baseDao.update("OrderInfo.examine", params);
    }

    public void forNo(String orderNo,String forNo) {
    	 Map<String, String> params = new HashMap<String, String>();
         params.put("orderNo", orderNo);
         params.put("forNo", forNo);
        baseDao.update("OrderInfo.forNo", params);
    }
    
    public void bing(Map<String, String> params) {
        baseDao.update("OrderInfo.bind", params);
    }

    public List<Map<String, String>> listSatistics(Map<String, String> params) {
        System.out.println("开始统计数据");
        return baseDao.getList("OrderInfo.Satistics", params);
    }

    public int signExamine(Map<String, String> params) {
        return baseDao.update("OrderInfo.signExamine", params);
    }
//签收审核修改order_info

    public int signExamineOrder(Map<String, String> params) {
        return baseDao.update("OrderInfo.signExamineOrder", params);
    }

    public void sendUpdate(String orderNo, String sno, String cno) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("sno", sno);
        params.put("cno", cno);
        params.put("orderNo", orderNo);
        baseDao.update("OrderInfo.sendUpdate", params);
    }
    
    public void proUpdate(String orderNo, String sno, String cno) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("sno", sno);
        params.put("cno", cno);
        params.put("orderNo", orderNo);
        baseDao.update("OrderInfo.proUpdate", params);
    }

    public void completed(String orderNo) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderNo", orderNo);
        baseDao.update("OrderInfo.completed", params);
    }

    public void updateSendLocation(Map<String, String> params) {
        baseDao.update("OrderInfo.updateSendLocation", params);
    }

    public void updateRevLocation(Map<String, String> params) {
        baseDao.update("OrderInfo.updateRevLocation", params);
    }

    public List<Map<String, String>> getPrintOrder(Map<String, String> params) {
        return baseDao.getList("OrderInfo.getPrintOrder", params);
    }

    public void deled(Integer id) {
        baseDao.update("OrderInfo.deled", id);
    }

    public String getOrderIdBySid(Map<String, String> params) {
    	String r = "0" ;
        List<Map<String, Object>> list =  baseDao.getList("OrderInfo.getOrderIdBySid", params);
        for (Map<String, Object> map:list) {
			r +=","+map.get("id").toString() ;
		}
        return r ;
    }
    
    public void examineUpdate(Map<String, String> params) {
        baseDao.update("OrderInfo.examineUpdate", params);
    }

    public List<Map<String, Object>> codMonthList(Map<String, String> params) {
        return baseDao.getList("OrderInfo.codMonthList", params);
    }

    public void note(Map<String, String> params) {
        baseDao.insert("OrderInfo.note", params);
    }

    public List<Map<String, Object>> getNoteList(Map<String, String> params) {
        return baseDao.getList("OrderInfo.getNoteList", params);
    }

    public List<OrderInfo> getOrderInfoList(String userNo) {
        return baseDao.getList("OrderInfo.getOrderInoList", userNo);
    }

    public Map<String, Object> selectOrderExit(String lgcOrderNo) {
        return baseDao.getOne("OrderInfo.selectOrder", lgcOrderNo);
    }

    public void updateImage(List<Map<String, String>> list, String type) {
        if ("1".equals(type)) {
            baseDao.update("OrderInfo.updateSendImgUrl", list);
        } else if ("2".equals(type)) {
            baseDao.update("OrderInfo.updateTakeImgUrl", list);
        } else {
        }
    }

    public PageInfo<Map<String, Object>> queryDayCompanyInfo(Map<String, String> params, Page pageInfo) {
    	LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
        return baseDao.getByPage("OrderInfo.dayCompanyInfo", pageInfo, params);
    }

    public Map<String, Object> queryDayCompanyCount(Map<String, String> params) {
    	LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
        return (Map<String, Object>) baseDao.getOne("OrderInfo.dayCompanyCountSum", params);
    }

    public PageInfo<Map<String, Object>> queryDaySubstationInfo(Map<String, String> params, Page pageInfo) {
    	LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
        return baseDao.getByPage("OrderInfo.daysubstationInfo", pageInfo, params);
    }

    public Map<String, Object> queryDaySubstationCount(Map<String, String> params) {
    	LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
        return (Map<String, Object>) baseDao.getOne("OrderInfo.daysubstationCountSum", params);
    }

    public PageInfo<Map<String, Object>> queryDayCourierInfo(Map<String, String> params, Page pageInfo) {
    	LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
        return baseDao.getByPage("OrderInfo.daycourierInfo", pageInfo, params);
    }
    
    public PageInfo<Map<String, Object>> queryDayCourierInfoByWang(Map<String, String> params, Page pageInfo) {
    	LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
        return baseDao.getByPage("OrderInfo.queryDayCourierInfoByWang", pageInfo, params);
    }
    public List<Map<String, Object>> queryDayCourierInfo(Map<String, String> params) {
    	LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
        return baseDao.getList("OrderInfo.daycourierInfo", params);
    }

    public Map<String, Object> queryDayCourierCount(Map<String, String> params) {
    	LgcConfig lgcConfig = Constants.getLgcConfig() ;
    	params.put("reportExam", lgcConfig.getReportExam()) ;
        return (Map<String, Object>) baseDao.getOne("OrderInfo.daycourierCountSum", params);
    }

    class OrderComparator implements Comparator<Map<String, Object>> {

        private String[] ods = {};

        public OrderComparator(String[] ods) {
            super();
            this.ods = ods;
        }

        @Override
        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
            Integer o1_index = 98;
            Integer o2_index = 99;
            int i = 0;
            for (String no : ods) {
                if (no.equals(o1.get("lgc_order_no"))) {
                    o1_index = i;
                } else {
                    if (no.equals(o2.get("lgc_order_no"))) {
                        o2_index = i;
                    }
                }
                i++;
            }
            return o1_index.compareTo(o2_index);
        }
    }
    
    public Map<String,Object> getOrderInfoByLgcOrderNo(String lgcOrderNo, String lgcNo) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("lgcOrderNo", lgcOrderNo);
        map.put("lgcNo", lgcNo);
        return baseDao.getOne("OrderInfo.getOrderInfoByLgcOrderNo", map);
    }
    
    
    public int updateBatchOrder(Map<String, String> params){
    	 baseDao.update("OrderInfo.batchUpdateByIds", params);
    	 baseDao.update("OrderInfo.batchUpdatePayAcount", params);
    	 return insertBatchOrderRecord(params);
    }
    
    public int insertBatchOrderRecord(Map<String, String> params){
    	List<Map<String,Object>> list = baseDao.getList("OrderInfo.selectByIds", params);
    	OrderInfo orderInfo =null;
    	BossUser bossUser = (BossUser) SecurityUtils.getSubject().getPrincipal();
    	int ret =0;
    	for (Map<String, Object> map : list) {
    		orderInfo = new OrderInfo();
    		orderInfo.setInputer( bossUser.getUserName());
    		if(params.get("good_price")==null||"".equals(params.get("good_price").toString())){
    			orderInfo.setGoodPrice(Float.parseFloat("0.00"));
    		}else{
    			orderInfo.setGoodPrice(Float.parseFloat(params.get("good_price").toString()));
    		}
    		orderInfo.setOrderNo(map.get("order_no").toString());
    		if(orderInfo.getFreightType()==null||"".equals(orderInfo.getFreightType())){
    			orderInfo.setFreightType("0");
    		}
    		
    		if(params.get("freight")==null||"".equals(params.get("freight").toString())){
    			orderInfo.setFreight(Float.parseFloat("0.00"));
    		}else{
    			orderInfo.setFreight(Float.parseFloat(params.get("freight").toString()));
    		}
    		orderInfo.setPayType(params.get("pay_type").toString());
    		orderInfo.setMonthSettleNo(params.get("month_settle_no").toString());
    		orderInfo.setTakeCourierNo(params.get("take_courier_no").toString());
    		orderInfo.setSendCourierNo(params.get("send_courier_no").toString());
    		if(params.get("take_order_time")==null||"".equals(params.get("take_order_time").toString())){
    			orderInfo.setTakeOrderTime(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
    		}
    		if(params.get("send_order_time")==null||"".equals(params.get("send_order_time").toString())){
    			orderInfo.setSendOrderTime(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
    		}
    		orderInfo.setCodName("");
    		if(params.get("good_valuation")==null||"".equals(params.get("good_valuation").toString())){
    			orderInfo.setGoodValuation(Float.parseFloat("0.00"));
    		}else{
    			orderInfo.setGoodValuation(Float.parseFloat(params.get("good_valuation").toString()));
    		}
    		
    		if(params.get("vpay")==null||"".equals(params.get("vpay").toString())){
    			orderInfo.setVpay(Float.parseFloat("0.00"));
    		}else{
    			orderInfo.setVpay(Float.parseFloat(params.get("vpay").toString()));
    		}
    		orderInfo.setSnpay("0.00");
    		orderInfo.setPayAcount(orderInfo.getFreight()+orderInfo.getVpay()+orderInfo.getGoodPrice()+orderInfo.getGoodValuation());
    		orderInfo.setLgcNo(bossUser.getLgcNo());
    		baseDao.insert("OrderInfo.editOrderRecord", orderInfo);
    		ret++;
		}
    	return ret;
    }
    
    public Map updateWeight(Map<String,String> params){
    	String substion_type =params.get("substation_type");
    	String select_v = params.get("select_v");
    	String item_weight = params.get("item_weight");
    	if(Float.parseFloat( params.get("center_warehouse_weight").toString())<Float.parseFloat( params.get("warehouse_minv").toString())){
			params.put("center_warehouse_weight",params.get("warehouse_minv"));
		}
    	if("J".equals(substion_type)){
    		if("0".equals(select_v)){
    			params.put("item_weight", item_weight);
    		}else if("1".equals(select_v)){
    			params.put("item_weight", params.get("center_warehouse_weight"));
    		}else if("2".equals(select_v)){
    			if(Float.parseFloat(item_weight)<Float.parseFloat(params.get("center_warehouse_weight"))){
    				params.put("item_weight", params.get("center_warehouse_weight"));
    			}else{
    				params.put("item_weight", item_weight);
    			}
    		}
    		baseDao.update("FranchiseOrder.updateJiaMengWeight", params);	
    	}
    	Map m =new HashMap<String,String>();
    	int ret =  baseDao.update("OrderInfo.updateCenterWarehouseWeight", params);
    	m.put("ret", ret+"");
    	m.put("center_warehouse_weight", params.get("center_warehouse_weight"));
    	return m;
    }
    
    public int updateRealSendTime(OrderInfo info){
    	return baseDao.update("OrderInfo.updateRealSendTime", info);
    }
    
}
