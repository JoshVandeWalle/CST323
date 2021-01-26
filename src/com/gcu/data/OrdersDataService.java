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
import com.mysql.jdbc.Connection;

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
	
	// Logger used to monitor application usage
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

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
		// log method entry
		logger.info("Entering OrdersDataService.findAll()");
				
		// SQL to get all orders
		String sql = "SELECT * FROM Orders";
		
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
			
			// log method exit
			logger.error("Exiting OrdersDataService.findAll with exception");
			
			// wrap exception in custom database exception for encapsulation
			throw new DatabaseException(e);
		}

		// log method exit
		logger.info("Exiting OrdersDataService.findAll()");
		
		// return list
		return result;
	}
	
	
	@Override
	public boolean create(Order order) 
	{
		// log method entry
		logger.info("Entering OrdersDataService.create()");
		
		// SQL to insert an order
		String sql = "INSERT INTO Orders (ORDER_NO, PRODUCT_NAME, PRICE, QUANTITY) VALUES (:orderNo, :productName, :price, :quantity)";
		
		// use try/catch to handle database exceptions
		try 
		{
			// make the order passed in the parameter source for the query
			BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(order);
	
			// execute statement and IF successful
			if (namedParameterJdbcTemplate.update(sql, params) != 0)
			{
				// log method exit
				logger.info("Exiting OrdersDataService.create() with true");
				
				// inform the business layer the operation was successful
				return true;
			}
			
			// log method exit
			logger.info("Exiting OrdersDataService.create() with false");
						
			// inform the business layer the operation failed
			return false;
		}
		
		// handle database exceptions
		catch (DataAccessException e)
		{
			// print stack trace
			e.printStackTrace();
					
			// log method exit
			logger.error("Exiting OrdersDataService.findAll with exception");
					
			// wrap exception in custom database exception for encapsulation
			throw new DatabaseException(e);
		}

	}

	@Override
	public boolean update(Order order) 
	{
		// log method entry
		logger.info("Entering OrdersDataService.update()");
		
		// SQL to update an order
		String sql = "UPDATE Orders SET ORDER_NO = :orderNo, PRODUCT_NAME = :productName, PRICE = :price, QUANTITY = :quantity WHERE ID = :id";

		// use try/catch to handle database exceptions
		try
		{
			// make the order passed in the parameter source for the query
			BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(order);
	
			// execute statement and IF successful
			if (namedParameterJdbcTemplate.update(sql, params) != 0)
			{
				// log method exit
				logger.info("Exiting OrdersDataService.update() with true");
				
				// inform the business layer the operation was successful
				return true;
			}
			
			// log method exit
			logger.info("Exiting OrdersDataService.update() with false");
					
			// inform the business layer the operation failed
			return false;
		}
		
		// handle database exceptions
		catch (DataAccessException e)
		{
			// print stack trace
			e.printStackTrace();
			
			// log method exit
			logger.error("Exiting OrdersDataService.findAll with exception");
			
			// wrap exception in custom database exception for encapsulation
			throw new DatabaseException(e);
		}

	}

	@Override
	public boolean delete(Order order) 
	{
		// log method entry
		logger.info("Entering OrdersDataService.delete()");
		
		// SQL to delete an order
		String sql = "DELETE FROM Orders WHERE ID = :id";
		
		// use try/catch to handle database exception
		try
		{

			// make the order passed in the parameter source for the query
			BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(order);
	
			if (namedParameterJdbcTemplate.update(sql, params) != 0)
			{
				// log method exit
				logger.info("Exiting OrdersDataService.delete() with true");
				
				// inform the business layer the operation was successful
				return true;
			}
			
			// log method exit
			logger.info("Exiting OrdersDataService.delete() with false");
					
			// inform the business layer the operation failed
			return false;
		}
		
		// handle database exceptions
		catch (DataAccessException e)
		{
			// print stack trace
			e.printStackTrace();
			
			// log method exit
			logger.error("Exiting OrdersDataService.findAll with exception");
			
			// wrap exception in custom database exception for encapsulation
			throw new DatabaseException(e);
		}
	}
}
