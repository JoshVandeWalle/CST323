package com.gcu.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gcu.data.DataAccessInterface;
import com.gcu.model.Order;
import com.gcu.service.OrderService;

/**
 * The OrdersBusinessService oversees business rules and logic for orders
 * @author Josh Van de Walle
 *
 */
public class OrdersBusinessService implements OrdersBusinessInterface {
	
	// The data service that handles database CRUD operations for orders
	DataAccessInterface<Order> service;
	
	// Logger used to monitor application usage
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	/**
	 * This method handles setter injection of the data service
	 * @param service
	 */
	@Autowired
	public void setService(DataAccessInterface<Order> service)
	{
		this.service = service;
	}

	
	@Override
	public List<Order> retrieveAll() 
	{
		// log method entry
		logger.info("Entering OrdersBusinessService.retrieveAll()");
		
		// pass control to data service to find all orders
		return service.findAll();
	}

	@Override
	public boolean place(Order order) 
	{
		// log method entry
		logger.info("Entering OrdersBusinessService.place()");
				
		// pass control to data service to create the new order
		return service.create(order);
	}

	@Override
	public boolean edit(Order order) 
	{
		// log method entry
		logger.info("Entering OrdersBusinessService.edit()");
		
		// pass control to data service to update the order
		return service.update(order);
	}

	@Override
	public boolean cancel(Order order) 
	{
		// log method entry
		logger.info("Entering OrdersBusinessService.cancel()");
		
		// pass control to data service to delete the order
		return service.delete(order);
	}
}
