package com.yogapay.boss.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yogapay.boss.dao.ManagerDao;
import com.yogapay.boss.domain.Menu;

@Service
public class MenuService {

	@Resource
	private ManagerDao managerDao ;
	
	
	public List<Menu> getMenuList() {
	List<Menu> menuList = new ArrayList<Menu>();
	//List<Map<String, Object>> list = authDao.getAuthList();
	List<Map<String, Object>> list = managerDao.getList("Menu.getMenuList") ;
	// 分组
	for (Map<String, Object> mp : list) {
		int level = (Integer) mp.get("level");
	    Menu menu = new Menu() ;
		Object id = mp.get("id");
		if (level == 1) {
			menu.setId((Integer.valueOf(mp.get("id").toString())));
			menu.setMenuText(mp.get("menu_text").toString());
			menu.setMenuUri(mp.get("menu_uri")==null?"":mp.get("menu_uri").toString());
            menu.setMenuImg(mp.get("menu_img")==null?"":mp.get("menu_img").toString());
            menu.setPermisionCode(mp.get("permision_code")==null?"":mp.get("permision_code").toString());
			List<Menu> nodeList = new ArrayList<Menu>();
			for (Map<String, Object> mmp : list) {
				Object node_parent_id = mmp.get("pid");
				if (id.toString().equals(node_parent_id.toString())) {
					  Menu nMenu = new Menu() ;
					  nMenu.setId((Integer.valueOf(mmp.get("id").toString())));
					  nMenu.setMenuText(mmp.get("menu_text").toString());
					  nMenu.setMenuUri(mmp.get("menu_uri")==null?"":mmp.get("menu_uri").toString());
					  nMenu.setMenuImg(mmp.get("menu_img")==null?"":mmp.get("menu_img").toString());
					  nMenu.setPermisionCode(mmp.get("permision_code")==null?"":mmp.get("permision_code").toString());
					  nodeList.add(nMenu) ;
				}
			}
			menu.setNodeList(nodeList);
		}
		if (menu != null  && menu.getId() > 0) {
			menuList.add(menu);
		}
	}
	return menuList;
	}
	
	
	public Map<String, Object> getMenuByUri(String uri) {
		Map<String, Object> menu = managerDao.getOne("Menu.getMenuByUri",uri) ;
	    return menu ;
	}
	
	public Map<String, Object> getMenu2ById(String id) {
		Map<String, Object> menu = managerDao.getOne("Menu.getMenu2ById",id) ;
	    return menu ;
	}
	
}
