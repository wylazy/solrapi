package com.ipjmc.solr.dao;

import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.ipjmc.solr.utils.HibernateUtils;
import com.ipjmc.solr.utils.TextHelper;

public abstract class AbstractDAO<T> {

	private static final Logger logger = Logger.getLogger(AbstractDAO.class);

	public abstract Class<T> getEntityClass();
	
	/**
	 * 类名
	 * @return
	 */
	public String getEntityName() {
		return getEntityClass().getName();
	}
	
	/**
	 * 查询全部
	 * @return
	 */
	public List<T> findAll() {
		return findAll(null);
	}
	
	/**
	 * 查询全部
	 * @param orderBy
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(String orderBy) {
		
		List<T> list = null;
		try {
			Session session = HibernateUtils.currentSession();
			Transaction tx = session.beginTransaction();
			String sql = "FROM " + getEntityName();
			if (!TextHelper.isEmpty(orderBy)) {
				sql += " ORDER BY " + orderBy;
			}
			
			list = session.createQuery(sql).list();
			tx.commit();
		} catch (RuntimeException e) {
			logger.debug("find all " + getEntityName() + " error " + e.getMessage());
		}
		
		return list;
	}
	
	/**
	 * 通过主键返回结果
	 * @param id 主键
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findById(Serializable id) {

		T entity = null;
		try {
			Session session = HibernateUtils.currentSession();
			Transaction tx = session.beginTransaction();
			entity = (T) session.get(getEntityClass(), id);
			tx.commit();
		} catch (RuntimeException e) {
			logger.debug("find  " + getEntityName() +" with id = " + id + " error " + e.getMessage());
		}
		
		return entity;
	}
	
	/**
	 * 通过属性查询
	 * @param property 属性名
	 * @param value 属性值
	 * @return
	 */
	public List<T> findByProperty(String property, Object value) {
		return findByProperty(property, value, null);
	}
	
	/**
	 * 通过属性查询
	 * @param property 属性名
	 * @param value 属性值
	 * @param orderBy 排序依据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String property, Object value, String orderBy) {

		List<T> list = null;
		try {
			Session session = HibernateUtils.currentSession();
			Transaction tx = session.beginTransaction();
			
			String sql = "FROM " + getEntityName() + " WHERE "
						+ property + " = :propertyValue";
			if (!TextHelper.isEmpty(orderBy)) {
				sql += " ORDER BY " + orderBy;
			}
			
			//通过QBC检索
//			Criteria criteria = session.createCriteria(getEntityClass());
//			criteria.add(Restrictions.ilike(property, value.toString(), MatchMode.ANYWHERE));
//			if (!TextHelper.isEmpty(orderBy)) {
//				criteria.addOrder(Order.desc(orderBy));
//			}
//			criteria.list();
			
			Query query = session.createQuery(sql);
			query.setParameter("propertyValue", value);
			list = query.list();
			
			tx.commit();
			//配置延迟加载
			//lazy = "true"
			//Hibernate.initialize(studen.getTeam());
			
		} catch (RuntimeException e) {
			logger.debug("find " + getEntityName() + " with error " + e.getMessage());
		}
		
		return list;
	}
	
	public T findUnique(String property, Object value) {
		List<T> list = findByProperty(property, value, null);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * 将瞬态实例持久化
	 * @param entity
	 * @return 返回持久化的实例
	 */
	public T persist(T entity) {
		try {
			Session session = HibernateUtils.currentSession();
			Transaction tx = session.beginTransaction();
			session.save(entity);
			tx.commit();
			return entity;
		} catch (RuntimeException e) {
			logger.debug("persist " + getEntityName() +" error " + e.getMessage());
		}
		return null;
	}
	
	public void saveOrUpdate(T entity) {
		try {
			Session session = HibernateUtils.currentSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(entity);
			tx.commit();
		} catch (RuntimeException e) {
			logger.debug("persist " + getEntityName() +" error " + e.getMessage());
		}

	}
	
	
	public void update(T entity) {
		
		try {
			Session session = HibernateUtils.currentSession();

			//更新实例时，必须要使用事务
			Transaction tx = session.beginTransaction();
			session.update(getEntityName(), entity);
			tx.commit();
		} catch (RuntimeException e) {
			logger.debug("update " + getEntityName() + " error " + e.getMessage());
		}
	}
	
	public void delete(T entity) {
		try {
			Session session = HibernateUtils.currentSession();
			
			Transaction tx = session.beginTransaction();
			session.delete(getEntityName(), entity);
			tx.commit();
		} catch (RuntimeException e) {
			logger.debug("delete " + getEntityName() + " error " + e.getMessage());
		}
	}
}
