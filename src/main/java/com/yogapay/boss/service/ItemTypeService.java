package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.ItemType;

@Service
public class ItemTypeService {
	@Resource
	private BaseDao baseDao;
	
	public PageInfo<Map<String, Object>> list(Map<String, String> params, Page pageInfo) {
		PageInfo<Map<String, Object>> page = baseDao.getByPage("ItemType.list", pageInfo, params);
		return  page;
	}
	public List<Map<String, Object>> getList() {
		List<Map<String, Object>> getList = baseDao.getList("ItemType.list");
		return  getList;
	}
	
	public void save(ItemType itemType){
		baseDao.insert("ItemType.insert", itemType);
	}
	
public ItemType getById(Integer id) {
	return baseDao.getById("ItemType.getById", id) ;
}

	public int update(ItemType itemType){
		return baseDao.update("ItemType.update", itemType);
	}
	
	public int defaultItem(Integer id){
		ItemType itemType = new ItemType() ;
		itemType.setId(id);
		baseDao.update("ItemType.notDefaultItem", itemType);
		return baseDao.update("ItemType.defaultItem", itemType);
	}
	
	public int delById(Integer id){
		return baseDao.delete("ItemType.delById", id);
	}

	  public boolean isExsit(String itemStatus) {
	        Map<String, Object> map = this.baseDao.getOne("ItemType.getByItemText", itemStatus);
	        boolean flag = true;
	        if (map == null) {
	            flag = false;
	        }
	        return flag;
	    }

	    /**
	     * 查询所有物品类型的第一个
	     */
	    public String firstItemStatus() throws SQLException {

	        Map<String, Object> map = this.baseDao.getOne("ItemType.getByItemStatus", null);
	        String item = "其他";
	        if (map != null) {
	            item = (String) map.get("itemText");
	        }
	        return item;
	    }

}
