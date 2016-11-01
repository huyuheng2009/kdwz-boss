package com.yogapay.boss.service;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.DisUserBalance;

@Service
public class DisUserBalanceService {
	@Resource
	private BaseDao baseDao;
	
	public DisUserBalance getById(Integer id){
		DisUserBalance disUserBalance = baseDao.getById("DisUserBalance.getById", id);
		return disUserBalance;
	}
	
	public DisUserBalance getByUid(Integer uid){
		DisUserBalance disUserBalance = baseDao.getById("DisUserBalance.getByUid", uid);
		return disUserBalance;
	}
	
	public int save(DisUserBalance disUserBalance){
		return baseDao.insert("DisUserBalance.insert", disUserBalance);
	}
	
	   //充值
	   public int update(DisUserBalance disUserBalance){
		return baseDao.update("DisUserBalance.update", disUserBalance);
	   }
	
	    //扣费
		public int sub(DisUserBalance disUserBalance){
			return baseDao.update("DisUserBalance.sub", disUserBalance);
		}

		 //消费,1扣费成功，0余额不足，-1扣费失败
		public int consume(DisUserBalance disUserBalance){
			int r = -1 ;
			DisUserBalance curBalance =getByUid(disUserBalance.getUid()) ;
			BigDecimal cur = new BigDecimal(curBalance.getBalance()) ;   //当前余额
			BigDecimal consumeBalance =  new BigDecimal(disUserBalance.getBalance()) ;  //消费金额
			if (cur.compareTo(consumeBalance)==-1) {
				r = 0 ;
			}else {
				sub(disUserBalance);  //执行扣费
				r = 1 ;
			}
			return r ;
		}	
		

	public int delById(Integer id){
		return baseDao.delete("DisUserBalance.delById", id);
	}


}
