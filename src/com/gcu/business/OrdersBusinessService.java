package com.gcu.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gcu.data.DataAccessInterface;
import com.gcu.model.Order;
import com.gcu.service.OrderService;

/**
 * The OrdersBusinessService oversees application business rules and logic for orders
 * @author Josh Van de Walle
 *
 */
public class OrdersBusinessService implements OrdersBusinessInterface {
	
	// The data service that handles database CRUD operations for orders
	DataAccessInterface<Order> service;
	
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
		// pass control to data service to find all orders
		return service.findAll();
	}

	@Override
	public boolean place(Order order) 
	{			
		// pass control to data service to create the new order
		return service.create(order);
	}

	@Override
	public boolean edit(Order order) 
	{
		// pass control to data service to update the order
		return service.update(order);
	}

	@Override
	public boolean cancel(Order order) 
	{
		// pass control to data service to delete the order
		return service.delete(order);
	}
}
