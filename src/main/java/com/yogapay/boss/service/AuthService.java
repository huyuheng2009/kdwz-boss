package com.yogapay.boss.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yogapay.boss.dao.BaseDao;
import com.yogapay.boss.domain.AuthList;
import com.yogapay.boss.domain.BossAuth;
import com.yogapay.boss.domain.BossGroup;
import com.yogapay.boss.utils.Constants;
import com.yogapay.boss.utils.DateUtils;
import com.yogapay.boss.utils.StringUtils;

@Service
public class AuthService {
	@Resource
	private BaseDao baseDao ;

	public PageInfo<Map<String, Object>> getGrouplist(Map<String, String> params,Page pageInfo) {
		/*PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize()) ;
		List<Map<String, Object>> list = authDao.getGrouplist(params) ;
		PageInfo<Map<String, Object>> page =new PageInfo(list);*/
		return baseDao.getByPage("BossAuth.getGrouplist", pageInfo, params) ;
	}

	public List<BossAuth> getAuthListByUserId(Long id) throws SQLException {
		//List<BossAuth> list = authDao.getAuthListByUserId(id) ;
		List<BossAuth> list = baseDao.getList("BossAuth.getAuthListByUserId", id);
		//authDao.getAuthListByUserId(id) ;
		List<BossAuth> lst = new ArrayList<BossAuth>();
		for (BossAuth au : list) {
			if (!lst.contains(au)) {
				lst.add(au);
			}
		}
		return lst;
	}
	
	public List<BossAuth> getSuAuthListByUserId(Long id) throws SQLException {
		//List<BossAuth> list = authDao.getAuthListByUserId(id) ;
		String idstring = getSuIdString(id) ;
		Map<String, Object> map = new HashMap<String, Object>() ;
		map.put("idstring", idstring) ;
		List<BossAuth> list = baseDao.getList("BossAuth.getSuAuthListByUserId", map);
		//authDao.getAuthListByUserId(id) ;
		List<BossAuth> lst = new ArrayList<BossAuth>();
		for (BossAuth au : list) {
			if (!lst.contains(au)) {
				lst.add(au);
			}
		}
		return lst;
	}
	
	public String getSuIdString(Long id) throws SQLException {
		Map<String, Object> retMap = baseDao.getOne("BossAuth.getSuIdString", id);
		if (retMap==null||retMap.get("idstring")==null||retMap.get("idstring").toString().length()<1) {
			return "0" ; 
		}
		return retMap.get("idstring").toString();
	}
	
	
	public List<BossAuth> getSuAuthList() throws SQLException {
		List<BossAuth> list = baseDao.getList("BossAuth.getSuAuthList");
		List<BossAuth> lst = new ArrayList<BossAuth>();
		for (BossAuth au : list) {
			if (!lst.contains(au)) {
				lst.add(au);
			}
		}
		return lst;
	}
	
	public void updateSuAuth(String tid,String ids) {
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("ids", ids) ;
		pMap.put("tid", tid) ;
		baseDao.update("BossAuth.updateSuAuth", pMap) ;
	}
	
	public List<BossAuth> getAuthLists(String s) throws SQLException {
		//List<BossAuth> list = authDao.getAuthListByUserId(id) ;
		Map<String, Object> pMap = new HashMap<String, Object>() ;
		pMap.put("s", s) ;
		List<BossAuth> list = baseDao.getList("BossAuth.getAuthLists",pMap);
		//authDao.getAuthListByUserId(id) ;
		List<BossAuth> lst = new ArrayList<BossAuth>();
		for (BossAuth au : list) {
			if (!lst.contains(au)) {
				lst.add(au);
			}
		}
		return lst;
	}
	
	
	public PageInfo<Map<String, Object>> getAtuchlist(Map<String, String> params, Page pageInfo) {
		PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize()) ;
		/*List<Map<String, Object>> list = authDao.getAtuchlist(params) ;*/
		/*List<Map<String, Object>> list = baseDao.getList("BossAuth.getAtuchlist", params) ;
		PageInfo<Map<String, Object>> page =new PageInfo(list);*/
		return baseDao.getByPage("BossAuth.getAtuchlist", pageInfo, params) ;
	}
    
	/**
	 *  
	 *  @param puserId 
	 * @param userID
	 * @param all  是否列出全部群组，非admin用户请传false，只列出当前用户的群组
	 * @param select 是否需要检查选中
	 * @return
	 */
	public List<Map<String, Object>> getUserGroupList(String puserId,long userID,boolean all,boolean select) {
		/*List<Map<String,Object>> list=authDao.getUserGroupList();
		List<Map<String,Object>> list2=authDao.getUserGroupListById(userID);*/
		List<Map<String,Object>> list = null ;
		if (all) {
			list = baseDao.getList("BossAuth.getUserGroupList") ;
		}else {
			if (!StringUtils.isEmptyWithTrim(puserId)) {
				list = baseDao.getList("BossAuth.getUserGroupListById",puserId) ;
			}else {
				list = baseDao.getList("BossAuth.getUserGroupListById",userID) ;
			}
			
		}
		//List<Map<String,Object>> list = baseDao.getList("BossAuth.getUserGroupList") ;
		if (select) {
			List<Map<String,Object>> list2 = baseDao.getList("BossAuth.getUserGroupListById",userID) ;
			for(Map<String,Object> mp:list2){
				Object id=mp.get("id");
				for(Map<String,Object> mmp:list){
					Object mID=mmp.get("id");
					if(id.equals(mID)){
						mmp.put("checked", "show");
					}else{
						if(!mmp.containsKey("checked")){
							mmp.put("checked", "hiden");
						}
					}
				}
			}	
		}
		return list;
	
	}

	public Map<String, Object> getAuthByNameCode(BossAuth ba) {
		return  baseDao.getOne("BossAuth.getAuthByNameCode", ba);
	}

	
	public List<Map<String, Object>> getUserGroupList() {
		// TODO Auto-generated method stub
		return baseDao.getList("BossAuth.getUserGroupList");
	}

	public List<AuthList> getAuthList() {
	List<AuthList> authList = new ArrayList<AuthList>();
	//List<Map<String, Object>> list = authDao.getAuthList();
	List<Map<String, Object>> list = baseDao.getList("BossAuth.getAuthList") ;
	// 分组
	for (Map<String, Object> mp : list) {
		int parent_id =  Integer.valueOf(mp.get("parent_id").toString());
		AuthList auth = new AuthList();
		Object id = mp.get("id");
		if (parent_id == 0) {
			auth.setId((Integer.valueOf(mp.get("id").toString())));
			auth.setParent_code((String) mp.get("auth_code"));
			auth.setParent_name((String) mp.get("auth_name"));
			List<AuthList> nodeList = new ArrayList<AuthList>();
			// List<Map<String,Object>> nodeList=new
			// ArrayList<Map<String,Object>>();
			for (Map<String, Object> mmp : list) {
				Object node_parent_id = mmp.get("parent_id");
				if (id.toString().equals(node_parent_id.toString())) {
					nodeList.add(new AuthList((Integer.valueOf( mmp.get("id").toString())),
							(String) mmp.get("auth_name"), (String) mmp
									.get("checked"), (Integer) mmp
									.get("parent_id")));
				}
			}
			Collections.sort(nodeList);
			auth.setNodeList(nodeList);
		}
		if (auth != null && auth.getId() != null && auth.getId() > 0) {
			authList.add(auth);
		}
	}

	getAuthList(authList, list);
	return authList;
	}
	
	private List<AuthList> getAuthList(List<AuthList> authList,
			List<Map<String, Object>> list) {
		for (AuthList auth : authList) {
			for (AuthList nodeAuth : auth.getNodeList()) {
				List<AuthList> nodeList = new ArrayList<AuthList>();
				for (Map<String, Object> mmp : list) {
					Integer parent_id = Integer.valueOf(mmp.get("parent_id").toString());
					if (nodeAuth.getId().equals(parent_id)) {
						nodeList.add(new AuthList(Integer.valueOf(mmp.get("id").toString()) ,
								(String) mmp.get("auth_name"), (String) mmp
										.get("checked"), (Integer) mmp
										.get("parent_id")));
					}
				}
				if (nodeList.size() > 0) {
					Collections.sort(nodeList);
				}
				nodeAuth.setNodeList(nodeList);
			}
		}
		return authList;
	}
	
	
	public List<AuthList> getAuthList(long groupID) throws SQLException {
		//List<Map<String, Object>> list = authDao.getAuthList() ;
		List<Map<String, Object>> list = baseDao.getList("BossAuth.getAuthList") ;
		//String authSql = "select a.* from boss_auth a,boss_auth_group g where a.id=g.auth_id and g.group_id=?";
		//List<Map<String, Object>> list2 =	authDao.getGroupAuth(groupID);
		List<Map<String, Object>> list2 =	baseDao.getList("BossAuth.getGroupAuth",groupID) ;
		// checked
		for (Map<String, Object> mp : list2) {
			int id = Integer.valueOf(mp.get("id").toString());
			for (Map<String, Object> mmp : list) {
				int mID = Integer.valueOf(mmp.get("id").toString());
				if (id == mID) {
					mmp.put("checked", "show");
				} else {
					if (!mmp.containsKey("checked")) {
						mmp.put("checked", "hiden");
					}
				}

			}
		}

		// 分组
		List<AuthList> authList = new ArrayList<AuthList>();
		for (Map<String, Object> mp : list) {
			int parent_id = (Integer) mp.get("parent_id");
			AuthList auth = new AuthList();
			Object id = mp.get("id");
			if (parent_id == 0) {
				auth.setId(Integer.valueOf(mp.get("id").toString()));
				auth.setParent_code((String) mp.get("auth_code"));
				auth.setParent_name((String) mp.get("auth_name"));
				auth.setChecked((String) mp.get("checked"));
				List<AuthList> nodeList = new ArrayList<AuthList>();
				// List<Map<String,Object>> nodeList=new
				// ArrayList<Map<String,Object>>();
				for (Map<String, Object> mmp : list) {

					Object node_parent_id = mmp.get("parent_id");

					if (id.toString().equals(node_parent_id.toString())) {
						nodeList.add(new AuthList((Integer.valueOf(mmp
								.get("id").toString())), (String) mmp
								.get("auth_name"), (String) mmp.get("checked"),
								Integer.valueOf(mmp.get("parent_id").toString()) ));
					}
				}
				Collections.sort(nodeList);
				auth.setNodeList(nodeList);
			}
			if (auth != null && auth.getId() != null && auth.getId() > 0) {
				authList.add(auth);
			}
		}

		authList = getAuthList(authList, list);
		return authList;
	}
	
	public int userGroupUpdate(Map<String, String> params, String[] authVals) throws SQLException {
		String id=params.get("id");
		String group_name=params.get("group_name");
		String group_desc=params.get("group_desc");
		String clogin=params.get("clogin");
		BossGroup bossGroup = new BossGroup();
		bossGroup.setId(Integer.parseInt(id));
		bossGroup.setGroupName(group_name);
		bossGroup.setGroupDesc(group_desc);
		bossGroup.setClogin(clogin);
		/*authDao.updateGroup(bossGroup);
		authDao.delGroupAuth(bossGroup.getId());*/
		baseDao.deleteByParams("BossAuth.updateGroup", bossGroup);
		baseDao.deleteByParams("BossAuth.delGroupAuth", bossGroup.getId());
		
		List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
		for(String authID:authVals){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("groupId", bossGroup.getId()) ;
			map.put("authId", Long.parseLong(authID)) ;
			objList.add(map);
		}
		//authDao.saveGroupAuth(objList);
		baseDao.insert("BossAuth.saveGroupAuth", objList) ;
		return 1;		
	}

	public int userGroupSave(Map<String, String> params, String[] authVals) {
		String group_name=params.get("group_name");
		String group_desc=params.get("group_desc");
		String clogin=params.get("clogin");
		
		List<Object> paramList=new ArrayList<Object>();
		paramList.add(group_name);
		paramList.add(group_desc);
		paramList.add(new Date());
		BossGroup bossGroup = new BossGroup() ;
		bossGroup.setGroupName(group_name);
		bossGroup.setGroupDesc(group_desc);
		bossGroup.setCreateTime(DateUtils.formatDate(new Date()));
		bossGroup.setClogin(clogin);
		bossGroup.setCreator(Constants.getUser().getRealName());
		//authDao.saveGroup(bossGroup);
		baseDao.insert("BossAuth.saveGroup", bossGroup) ;
		List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
		for(String authID:authVals){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("groupId", bossGroup.getId()) ;
			map.put("authId", Long.parseLong(authID)) ;
			objList.add(map);
		}
		/*authDao.saveGroupAuth(objList);*/
		baseDao.insert("BossAuth.saveGroupAuth", objList) ;
		return bossGroup.getId();
	}

	public Map<String, Object> queryUserGroupInfo(long id) throws SQLException {
		//return authDao.queryUserGroupInfo(id);
		return baseDao.getOne("BossAuth.queryUserGroupInfo", id) ;
	}

	//查询群组里是否还有用户
	public List<Map<String, Object>> checkUserGroupId(Long id) throws SQLException {
		//return authDao.checkUserGroupId(id);
		return baseDao.getList("BossAuth.checkUserGroupId", id) ;
	}

	public int delGroup(long id) throws SQLException {
		//return authDao.delGroup(id);
		baseDao.deleteByParams("BossAuth.delGroup", id) ;
		return 1;
	}
	
	
	public List<Map<String, Object>> getParentAuthListAll() throws SQLException {
		//List<Map<String, Object>> list = authDao.getParentAuthListAll();
		List<Map<String, Object>> list = baseDao.getList("BossAuth.getParentAuthListAll");
		List<Map<String, Object>> valList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "0");
		map.put("auth_name", "无");
		valList.add(map);
		valList.addAll(list);
		return valList;
	}

	public void saveAuth(BossAuth ba) throws SQLException {
		//authDao.saveAuth(ba);
		baseDao.insert("BossAuth.saveAuth", ba) ;
	}

	public List<BossAuth> findAuthByParentId(Long id) throws SQLException {
		//return authDao.findAuthByParentId(id);
		return baseDao.getList("BossAuth.findAuthByParentId", id) ;
	}

	public int delAuthById(long id) throws SQLException {
		//return authDao.delAuthById(id);
		baseDao.deleteByParams("BossAuth.delAuthById", id) ;
		return 1 ;
	}
	
	/**
	 * 查询用户是否属于某个群组
	 * @param userId
	 * @param groupName
	 * @return
	 */
	public boolean isUserGroup(String userId,String groupName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("groupName", groupName);
		Map<String, Object> ret = baseDao.getOne("BossAuth.checkUserGroupName", map) ;
		if (ret!=null) {
			return true ;
		}
		return false ;
	}
	
}