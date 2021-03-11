package com.gcu.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gcu.model.Order;
import com.gcu.service.OrderService;
import com.gcu.util.exception.DatabaseException;

/**
 * The OrdersDataService handles database CRUD operations for orders
 * @author Josh Van de Walle
 *
 */
public class OrdersDataService implements DataAccessInterface<Order> 
{
	// DataSource used for database operations
	private DataSource dataSource;
	
	// JDBC template used for database operations
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * This method is used to set the data source and create a jdbc template from it
	 * @param dataSource the DataSource from which to create the jdbc template
	 */
	public void setDataSource(DataSource dataSource) 
	{
		// set data source and jdbc template
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<Order> findAll() 
	{		
		// SQL to get all orders
		String sql = "SELECT * FROM orders";
		
		// initialize list of orders to return
		List<Order> result = new ArrayList<Order>();
		
		// use try/catch to handle database exceptions
		try 
		{
			// execute query and capture result
			SqlRowSet srs = namedParameterJdbcTemplate.queryForRowSet(sql, EmptySqlParameterSource.INSTANCE);
	
			// iterate over result set
			while (srs.next()) 
			{
				// add orders to list
				result.add(new Order(srs.getString("ORDER_NO"), srs.getString("PRODUCT_NAME"),
						srs.getFloat("PRICE"), srs.getInt("QUANTITY")));
			}
		
		}
		
		// handle database exceptions
		catch (DataAccessException e)
		{
			// print stack trace
			e.printStackTrace(); 
			// wrap exception in custom database exception for encapsulation
			throw new DatabaseException(e);
		}
		
		// return list
		return result;
	}
	
	
	@Override
	public boolean create(Order order) 
	{	
		// SQL to insert an order
		String sql = "INSERT INTO orders (ORDER_NO, PRODUCT_NAME, PRICE, QUANTITY) VALUES (:orderNo, :productName, :price, :quantity)";
		
		// use try/catch to handle database exceptions
		try 
		{
			// make the order passed in the parameter source for the query
			BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(order);
	
			// execute statement and IF successful
			if (namedParameterJdbcTemplate.update(sql, params) != 0)
			{	
				// inform the business layer the operation was successful
				return true;
			}
						
			// inform the business layer the operation failed
			return false;
		}
		
		// handle database exceptions
		catch (DataAccessException e)
		{
			// print stack trace
			e.printStackTrace();
					
			// wrap exception in custom database exception for encapsulation
			throw new DatabaseException(e);
		}

	}

	@Override
	public boolean update(Order order) 
	{
		// SQL to update an order
		String sql = "UPDATE orders SET ORDER_NO = :orderNo, PRODUCT_NAME = :productName, PRICE = :price, QUANTITY = :quantity WHERE ID = :id";

		// use try/catch to handle database exceptions
		try
		{
			// make the order passed in the parameter source for the query
			BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(order);
	
			// execute statement and IF successful
			if (namedParameterJdbcTemplate.update(sql, params) != 0)
			{	
				// inform the business layer the operation was successful
				return true;
			}
			
			// inform the business layer the operation failed
			return false;
		}
		
		// handle database exceptions
		catch (DataAccessException e)
		{
			// print stack trace
			e.printStackTrace();
			
			// wrap exception in custom database exception for encapsulation
			throw new DatabaseException(e);
		}

	}

	@Override
	public boolean delete(Order order) 
	{	
		// SQL to delete an order
		String sql = "DELETE FROM orders WHERE ID = :id";
		
		// use try/catch to handle database exception
		try
		{

			// make the order passed in the parameter source for the query
			BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(order);
	
			if (namedParameterJdbcTemplate.update(sql, params) != 0)
			{			
				// inform the business layer the operation was successful
				return true;
			}
					
			// inform the business layer the operation failed
			return false;
		}
		
		// handle database exceptions
		catch (DataAccessException e)
		{
			// print stack trace
			e.printStackTrace();
			
			// wrap exception in custom database exception for encapsulation
			throw new DatabaseException(e);
		}
	}
}
