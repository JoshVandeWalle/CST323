package com.gcu.data;

import java.util.List;

/**
 * This interface is used to define the methods of data services
 * @author Josh Van de Walle
 *
 * @param <T> the object model this service handles CRUD operations for
 */
public interface DataAccessInterface<T> 
{
	/**
	 * This method finds all records
	 * @return a list of all records
	 */
	public List<T> findAll();
	
	/**
	 * This method creates a new record
	 * @param t the record to be created
	 * @return boolean success flag
	 */
	public boolean create(T t);
	
	/**
	 * This method updates a record
	 * @param t the record to be updated
	 * @return boolean success flag
	 */
	public boolean update(T t);
	
	/**
	 * This method deletes a record
	 * @param t the record to be deleted
	 * @return boolean success flag
	 */
	public boolean delete(T t);


}
