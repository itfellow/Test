package com.omni.component.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DataBaseUtility {

	
	private Session getSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}
	
	public boolean checkId(final Class<?> classToGet, String key){
		
		boolean isFound = false;
		
		Object tablePojo = getObject(classToGet,key);
		
		if(tablePojo!=null){
			
			isFound = true;
			
		}
		
		return isFound;
	
	}
	
	public Object getObject(final Class<?> classToGet, final Object key) {
		
		Object tablePojo = null;
		
		Session sessionForObjectToUpdate = null;
		
		Transaction trForObjectToUpdate = null;
		
		try{
			
			sessionForObjectToUpdate = getSession();
			
			trForObjectToUpdate = sessionForObjectToUpdate.beginTransaction();
			
			tablePojo = (Object)sessionForObjectToUpdate.get(classToGet, (Serializable) key);

			trForObjectToUpdate.commit();
			
		}catch(HibernateException e) {
			e.printStackTrace();
			
			if(trForObjectToUpdate!=null) {
				trForObjectToUpdate.rollback();
			}
		}
		finally{
			if(sessionForObjectToUpdate!=null && sessionForObjectToUpdate.isOpen()) {
				sessionForObjectToUpdate.close();
			}
		}
		return tablePojo;
	}
	
	/**
	 * Simple update of pojoTable
	 */
	public void updateWithTransaction(final Object tablepojo) {
		
		Session sessionForUpdate = null;
		Transaction trForUpdate = null;
	
		try{
			
			sessionForUpdate = getSession();
			trForUpdate = sessionForUpdate.beginTransaction();
			sessionForUpdate.update(tablepojo);
			trForUpdate.commit();
			
		}catch(HibernateException  e) {
			e.printStackTrace();
			if(trForUpdate!=null){
				trForUpdate.rollback();
			}
		}
		finally{
			if(sessionForUpdate!=null && sessionForUpdate.isOpen()) {
				sessionForUpdate.close();
			}
		}
	}
	

	/**
	 * simple insert of pojoTable
	 * 
	 * @param tablePojo
	 */
	public void insertRecord(final Object tablePojo){
		
		Session sessionForSavePojo = null;
		Transaction trForSavePojo = null;
		
		try{
			sessionForSavePojo = getSession();
			trForSavePojo = sessionForSavePojo.beginTransaction();
			sessionForSavePojo.save(tablePojo);
			trForSavePojo.commit();
		}
		catch(HibernateException  e){
			
			e.printStackTrace();
			
			if(trForSavePojo!=null){
				trForSavePojo.rollback();
			}
		   
		}
		finally{
			
			if(sessionForSavePojo!=null && sessionForSavePojo.isOpen()){
				sessionForSavePojo.close();
			}
			
		}
	}

	
	/**
	 * Performs batch Insert of multiple pojoTables
	 * @param tablepojo
	 */
	public void insertRecord(final Object[] tablepojo) {
		
		Session sessionForInsertPojo = null;
		Transaction trForInsertPojo = null;
		
		try {
			sessionForInsertPojo = getSession();
			trForInsertPojo = sessionForInsertPojo.beginTransaction();
			
			for (Object object : tablepojo) {
				sessionForInsertPojo.save(object);
				System.out.println(object.toString() + " saved");
			}
			
			trForInsertPojo.commit();
			
		} catch (HibernateException e) {
			
			e.printStackTrace();
			
			if(trForInsertPojo!=null){
				trForInsertPojo.rollback();
			}
		
		}
		finally {
			
			if(sessionForInsertPojo!=null && sessionForInsertPojo.isOpen()){
				sessionForInsertPojo.close();
			}
		}
	}
	
	/**
	 * Optimized batch insert for insert more than 10 Tables at time
	 * @param tablePojo
	 */
	@SuppressWarnings("unchecked")
	public void batchInsert(final Object[] tablePojo) {
		
		Session  sessionForBatchInsert = null;
		Transaction trForBatchInsert = null;
		
		try {
			
			sessionForBatchInsert = getSession();
			trForBatchInsert = sessionForBatchInsert.beginTransaction();
			
			for (int i = 0; i < tablePojo.length; i++) {
				ArrayList<Object> list1 = (ArrayList<Object>) tablePojo[i];
				int count = 0;
				for (Object object : list1) {
					sessionForBatchInsert.save(object);
					System.out.println("Saved objects = " + count);
					count++;
					if (count % 50 == 0) {
						sessionForBatchInsert.flush();
						sessionForBatchInsert.clear();
					}
				}

			}
			
			trForBatchInsert.commit();
			
			System.out.println("Objects saved successfully");
			
		} catch (HibernateException e) {
			
			e.printStackTrace();
			
			if (trForBatchInsert != null){
				trForBatchInsert.rollback();
			}
			
		} finally {
			if (sessionForBatchInsert!=null && sessionForBatchInsert.isOpen()) {
				sessionForBatchInsert.close();
			}
		}

	}
	


	
	/**
	 * This method execute Query's like "SELECT * FROM TablePojo"
	 * and return TablePojo Objects in a List
	 * 
	 * @param hql {@link String}
	 * @return {@link List}
	 */
	@SuppressWarnings("unchecked")
	public  List<Object> selectRecord(final String HQL_QUERY) {
		List<Object> results = null;
		
		Session sessionSelectRecord = null;
		Transaction trForSelectRecord = null;
		try {
			
			sessionSelectRecord =getSession();
			
			trForSelectRecord = sessionSelectRecord.beginTransaction();
			
			if(sessionSelectRecord.isConnected()){
				Query query = sessionSelectRecord.createQuery(HQL_QUERY).setReadOnly(true);
				results = (List<Object>)query.list();
			}
			else {
				System.out.println("session not connected");
			}
			
		  trForSelectRecord.commit();
		
		} catch(HibernateException e) {
			e.printStackTrace();
			
			if(trForSelectRecord!=null)
			{
				trForSelectRecord.rollback();
			}
		}
		finally
		{
			if(sessionSelectRecord!=null && sessionSelectRecord.isOpen()) {
			
				sessionSelectRecord.close();
			}
				
		}
		
		return results;
	}
	
	
	
	
	/**
	 * This method is Overloaded method of selectRecord(HQL_QUERY). 
	 * It execute Query's like "SELECT * FROM TablePojo"
	 * and return Fixed number of TablePojo Objects in a List
	 * 
	 * @param HQL_QUERY   	{@link String}
	 * @param selectLimit 	{@link Integer}
	 * @return {@link List}
	 */
	@SuppressWarnings("unchecked")
	public List<Object> selectRecord(final String HQL_QUERY, final int selectLimit) {
		
		Session sessionForExecuteQueryWithLimit = null;
		Transaction trForExecuteQueryWithLimit = null;
		List<Object> results = null;
		
		try {
			sessionForExecuteQueryWithLimit = getSession();
			trForExecuteQueryWithLimit = sessionForExecuteQueryWithLimit.beginTransaction();
			
			Query query = sessionForExecuteQueryWithLimit.createQuery(HQL_QUERY);
			query.setMaxResults(selectLimit);
			results = (List<Object>)query.list();
			
			trForExecuteQueryWithLimit.commit();
		} catch(HibernateException e) {
			e.printStackTrace();
			
			if(trForExecuteQueryWithLimit!=null){
				trForExecuteQueryWithLimit.rollback();
			}
		}
		finally {
			if(sessionForExecuteQueryWithLimit!=null && sessionForExecuteQueryWithLimit.isOpen()) {
				sessionForExecuteQueryWithLimit.close();
			}
		}
		
		return results;
	}
	

	public void updateRecord(final String hql){
		
		Session sessionForUpdate = null;
		Transaction trForUpdate = null;
		
		try{
			sessionForUpdate = getSession();
			trForUpdate = sessionForUpdate.beginTransaction();
			Query query = sessionForUpdate.createQuery(hql);
			int result = query.executeUpdate();
			System.out.println("UPDATE: Rows affected: " + result);
			
			trForUpdate.commit();
		}catch(HibernateException e){
			e.printStackTrace();
			
			if(trForUpdate!=null){
				trForUpdate.rollback();
			}
		}
		finally{
			if(sessionForUpdate!=null && sessionForUpdate.isOpen()){
				sessionForUpdate.close();
			}
		}
	}
	
	public void updateRecord(final Object[] tablepojo) {
		
		Session sessionForUpdatePojo = null;
		Transaction trForUpdatePojo = null;
		
		try {
			
			sessionForUpdatePojo = getSession();
			trForUpdatePojo = sessionForUpdatePojo.beginTransaction();
			
			for (Object object : tablepojo) {
				sessionForUpdatePojo.update(object);
				System.out.println(object + " updated");
			}
			
			trForUpdatePojo.commit();
			
		} catch (HibernateException e) {
			
			e.printStackTrace();
			
			if (trForUpdatePojo != null){
				trForUpdatePojo.rollback();
			}
			
		} finally {
			if (sessionForUpdatePojo!=null && sessionForUpdatePojo.isOpen()){
				sessionForUpdatePojo.close();
			}
		}
	}
	

	
	/**
	 * This method saves if the record is not present in table else it update the record
	 */
	public void saveUpdate(final Object tableObject){
		
		Session sessionForSaveOrUpdate = null;
		Transaction trForSaveOrUpdate = null;
		
		try{
			sessionForSaveOrUpdate = getSession();
			
			trForSaveOrUpdate = sessionForSaveOrUpdate.beginTransaction();
			
			sessionForSaveOrUpdate.saveOrUpdate(tableObject);
			
			trForSaveOrUpdate.commit();
			
		}catch(HibernateException   e){
			e.printStackTrace();
			if(trForSaveOrUpdate!=null){
				trForSaveOrUpdate.rollback();
			}
		   
		}
		finally{
			if(sessionForSaveOrUpdate!=null && sessionForSaveOrUpdate.isOpen()){
				sessionForSaveOrUpdate.close();
			}
		}
		
	}
	
	

	public void updateOrDeleteRecord(final String HQL_QUERY){
		
		Session sessionForUpdateOrDel = null;
		Transaction trForUpdateOrDel = null;
		
		try{
			
			sessionForUpdateOrDel = getSession();
			
			trForUpdateOrDel = sessionForUpdateOrDel.beginTransaction();
			
			Query query = sessionForUpdateOrDel.createQuery(HQL_QUERY);
			
			int result = query.executeUpdate();
			System.out.println("Rows affected: " + result);
			
			trForUpdateOrDel.commit();
			
		}catch(HibernateException e){
			e.printStackTrace();
			
			if(trForUpdateOrDel!=null){
				trForUpdateOrDel.rollback();
			}
		}
		finally{
			if(sessionForUpdateOrDel!=null && sessionForUpdateOrDel.isOpen()){
				sessionForUpdateOrDel.close();
			}
		}
		
	}
	
	

	public Long getCount(final String HQL_QUERY){

		Long count= 0L;
		
		Session sessionForGetCount = null;
		Transaction trForGetCount = null;
		
		try {
			sessionForGetCount = getSession();
			trForGetCount = sessionForGetCount.beginTransaction();
			
			Query query = sessionForGetCount.createQuery(HQL_QUERY).setReadOnly(true);
			
			if(query.list() == null || query.list().get(0)==null || (Long)query.list().get(0)<1L){
				count=0L;
			}else{
				count=(Long)query.list().get(0);
			}
			
			trForGetCount.commit();
		}   
		catch(HibernateException  e){
			e.printStackTrace();
			
			if(trForGetCount!=null){
				trForGetCount.rollback();
			}
		}
		finally{
			if(sessionForGetCount!=null && sessionForGetCount.isOpen()){
				sessionForGetCount.close();
			}
		}
		return count;
	}
	
	

	
	
	public void executeProcWithNoParam(final String procCall){
		
		Session sessionForProcWithNoParam = null;
		Transaction trForProcWithNoParam = null;
		
		try{
			sessionForProcWithNoParam = getSession();
			trForProcWithNoParam = sessionForProcWithNoParam.beginTransaction();
			Query query = sessionForProcWithNoParam.createQuery(procCall);
			int result = query.executeUpdate();
			
			System.out.println("row affected="+result);
			
			trForProcWithNoParam.commit();
			
		}catch(HibernateException e){
			e.printStackTrace();
			
			if(trForProcWithNoParam!=null){
				trForProcWithNoParam.rollback();
			}
		}
		finally{
			if(sessionForProcWithNoParam!=null && sessionForProcWithNoParam.isOpen()){
				sessionForProcWithNoParam.close();
			}
		}

	}
	
	/**
	 * Set them table name and name of the PrimaryKey column return new primary key Id for that
	 * table as long
	 * @param tableName
	 * @param idPKColumnName
	 * @return
	 */
	public long generateNewId(String tableName,String idPKColumnName){
		List<Object> id = new ArrayList<Object>();
		long newId = 1;
		try{
			id =  selectRecord("SELECT "+idPKColumnName+" FROM "+tableName );
			
			String idString = id.get(id.size()-1).toString();
			newId = Integer.parseInt(idString);
			newId++;
		}
		catch(Exception e){
			System.out.println("no id created yet");
		}
		
		return newId;
	}

	
}
