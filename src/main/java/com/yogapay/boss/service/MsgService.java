package com.yogapay.boss.service;

import java.sql.SQLException;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.PushMsg;

@Service
public class MsgService {

	@Resource
	private BaseDao baseDao ;
	
	
    public long save(PushMsg msg) throws SQLException {
		return baseDao.update("PushMsg.save", msg) ;
	}
	
	
}
