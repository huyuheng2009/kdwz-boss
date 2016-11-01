package com.yogapay.boss.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Repository
@SuppressWarnings("unchecked")
public class BaseDao extends SqlSessionDaoSupport {
	@Resource
	 public void setSqlSessionTemplate(SqlSessionTemplate sqlSession) {
	       super.setSqlSessionTemplate(sqlSession);
    }
	
	/**
	 * 根据数据库ID获取唯一记录
	 * 
	 * @param key
	 *            mapper中sql语句对应的ID
	 * @param id
	 *            主键
	 * @return 唯一记录
	 */
	public <T> T getById(String key, Serializable id) {
		return (T) getSqlSession().selectOne(key, id);
	}
	
	/**
	 * 插入新记录到数据库
	 * 
	 * @param key
	 *            mapper中sql语句对应的ID
	 * @param params
	 *            数据库字段数据
	 */
	public int insert(String key, Object params) {
		return getSqlSession().insert(key, params);
	}
	
	/**
	 * 插入新记录到数据库
	 * 
	 * @param key
	 *            mapper中sql语句对应的ID
	 * @param params
	 *            数据库字段数据，生成的sql_map中有现成的更新语句可以自动判断为空的值，跟据主ID进行少量参数修改。这样可以少传参数提高开发效率和性能
	 */
	public int update(String key, Object params) {
		return getSqlSession().update(key, params);
	}

	/**
	 * 根据条件查询唯一记录，如根据ID查询记录详情等操作
	 * 
	 * @param key
	 *            mapper中sql语句对应的ID
	 * @param params
	 *            查询参数
	 * @return 唯一记录
	 */
	public <T> T getOne(String key, Object params) {
		return (T) getSqlSession().selectOne(key, params);
	}
	
	/**
	 * 获取多条数据
	 * 
	 * @param key
	 *            mapper中sql语句对应的ID
	 * @param params
	 *            查询参数
	 * @return 多条数据集
	 * @author dongjie.wang
	 * @date 2012-5-18 上午11:20:58
	 */
	public <T> List<T> getList(String key, Object params) {
		return getSqlSession().selectList(key, params);
	}

	/**
	 * 无参数获取多条数据
	 * 
	 * @param key
	 *            mapper中sql语句对应的ID
	 * @return 多条数据集
	 * @author dongjie.wang
	 * @date 2012-5-18 上午11:20:58
	 */
	public <T> List<T> getList(String key) {
		return getSqlSession().selectList(key);
	}

	/**
	 * 根据数据库主键ID删除数据库记录
	 * 
	 * @param key
	 *            mapper中sql语句对应的ID
	 * @param id
	 *            数据库主键
	 */
	public int delete(String key, Serializable id) {
		return getSqlSession().delete(key, id);
	}
	
	/**
	 * 根据参数删除数据库记录
	 * 
	 * @param key
	 *            mapper中sql语句对应的ID
	 * @param params
	 *            参数
	 */
	public int deleteByParams(String key,Object params){
		return getSqlSession().delete(key, params);
	}
	
	/**
	 * 分页查询
	 * 
	 * @param statementId Mapper id
	 * @param pageRequest 分页请求
	 * @param param 参数
	 * @return
	 */
	public <T> PageInfo<Map<String, Object>> getByPage(String statementId, Page pageInfo, Object param) {
		PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize()) ;
		PageInfo<Map<String, Object>> page =new PageInfo(getSqlSession().selectList(statementId, param));
		return page;
	}
	

	/**
	 * 分页查询
	 * 
	 * @param statementId Mapper id
	 * @param pageRequest 分页请求
	 * @param param 参数
	 * @return
	 */
	public <T> PageInfo<T> getPage(String statementId, Page pageInfo, Object param) {
		PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize()) ;
		PageInfo<T> page =new PageInfo(getSqlSession().selectList(statementId, param));
		return page;
	}
	
	/**
	 * 分页查询
	 * 
	 * @param statementId
	 * @param pageRequest
	 * @return
	 */
	public <T> PageInfo<Map<String, Object>> getByPage(String statementId, Page pageInfo) {
		return getByPage(statementId, pageInfo, null);
	}
	
	/**
	 * 分页查询
	 * 
	 * @param statementId Mapper id
	 * @param pageRequest 分页请求
	 * @param param 参数
	 * @return
	 */
	public <T> T getFrist(String statementId, Object param) {
		PageHelper.startPage(1,1) ;
		PageInfo<Map<String, Object>> page =new PageInfo(getSqlSession().selectList(statementId, param));
		if (page.getList()==null||page.getList().size()<1) {
			return null ;
		}
		return (T) page.getList().get(0);
	}
	
	
}
