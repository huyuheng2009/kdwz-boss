package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.ValuationRule;
import com.yogapay.boss.utils.DateUtils;

@Service
public class BossMsgService {

	@Resource
	private BaseDao baseDao ;
	
    public  Map<String, Object> getFirst(){
    	Map<String, Object> map = new HashMap<String, Object>() ;
    	Date nowDate = new Date() ;
    	map.put("createTime", DateUtils.formatDate(DateUtils.addDate(nowDate, 0, 0, -30))) ;
		return baseDao.getOne("BossMsg.getFirst",map);
    }
	
    public void readed(Integer id) {
		baseDao.update("BossMsg.readed", id) ;
	}
    
    
}
