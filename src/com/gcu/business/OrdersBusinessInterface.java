package com.gcu.business;

import java.util.List;

import com.gcu.model.Order;

/**
 * This interface defines what methods are needed to enfore business logic and rules for orders
 * @author Josh Van de Walle
 *
 */
public interface OrdersBusinessInterface 
{
	
	/**
	 * This method fetches all orders
	 * @return a list of all orders
	 */
	public List<Order> retrieveAll();
	
	/**
	 * This method places an order
	 * @param order the order being placed
	 * @return boolean indicating whether the order was placed successfully
	 */
	public boolean place(Order order);
	
	/**
	 * This method edits an order
	 * @param order the changed order
	 * @return boolean indicating whether the order was edited successfully
	 */
	public boolean edit(Order order);
	
	/**
	 * This method cancels an order
	 * @param order the order being cancelled
	 * @return boolean indicating whether the order was edited successfully
	 */
	public boolean cancel(Order order);
}
