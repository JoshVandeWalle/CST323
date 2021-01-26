package com.gcu.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcu.business.OrdersBusinessInterface;
import com.gcu.model.Order;
import com.gcu.util.RestDto;

/**
 * The Order Service is an API facade over application business logic
 * @author Josh Van de Walle
 *
 */
@RestController()
@RequestMapping("/service")
public class OrderService 
{
	// The business service that handles business logic for orders
	OrdersBusinessInterface service;
	
	// Logger used to monitor application usage
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	/**
	 * This method is used for setter injection of the business service
	 * @param service the implementation of the OrdersBusinessInterface to leverage
	 */
	@Autowired
	public void setOrdersService(OrdersBusinessInterface service)
	{
		this.service = service;
	}
	
	/**
	 * This method handles retrieving all orders
	 * @return RestDto a DTO that includes the response data, response, code, and response message
	 */
	@GetMapping("/getOrders")
	public RestDto<Order> handleGet()
	{
		// use try/catch to handle exceptions
		try 
		{
			// log method entry
			logger.info("Entering OrderService.handleGet");
			
			// instantiate a List of Orders
			List<Order> orders = new ArrayList<Order>();
			
			// pass control to business layer to retrieve orders
			orders = service.retrieveAll();
			
			// log outcome
			logger.info("Exiting OrderService.handleGet with code 200");
		
			// return DTO containing the orders
			return new RestDto<Order>(orders, 200, "OK");
		}
		
		// handle exceptions here
		catch (Exception e)
		{
			// log outcome
			logger.error("Exiting OrderService.handleGet with code 500");
						
			// return DTO informing the client of the error
			return new RestDto<Order>(null, 500, e.getMessage());
		}
	}
	
	/**
	 * This method handles the placement of an order
	 * @param order the Order being placed
	 * @return RestDto a DTO that includes the response data, response, code, and response message
	 */
	@PostMapping("/createOrder")
	public RestDto<Order> handlePlace(@Valid @RequestBody Order order, BindingResult bindingResult)
	{
		// use try/catch to handle exceptions
		try 
		{
			// log method entry
			logger.info("Entering OrderService.handlePlace");
						
			// validate parameter
			if (bindingResult.hasErrors())
			{
				// return DTO informing the user of the failure
				return new RestDto<Order>(null, 400, "Bad Request");
			}
			
			// if the order was successfully placed
			if (service.place(order))
			{
				// instantiate an ArrayList of Orders to return as response data
				ArrayList<Order> data= new ArrayList<Order>();
				// add the order that was placed to the response data
				data.add(order);
				
				// log outcome
				logger.info("Exiting OrderService.handlePlace with code 200"); 
				
				// return DTO indicating the request succeeded 
				return new RestDto<Order>(data, 200, "OK");
			}
			
			// if the request failed
			else 
			{
				// log outcome
				logger.error("Exiting OrderService.handlePlace with code 500"); 
				
				// return DTO informing the user of the failure
				return new RestDto<Order>(null, 500, "Internal error");
			}
		}
		
		// handle exceptions here
		catch (Exception e)
		{
			// log outcome
			logger.error("Exiting OrderService.handlePlace with code 500"); 
			
			// return DTO informing the user of the failure
			return new RestDto<Order>(null, 500, "Internal error");
		}
		
	}
	
	/**
	 * This method handles changes to an order
	 * @param order the updated order
	 * @return RestDto a DTO that includes the response data, response, code, and response message
	 */
	@PostMapping("/updateOrder")
	public RestDto<Order> handleEdit(@Valid @RequestBody Order order, BindingResult bindingResult)
	{
		// use try/catch to handle exceptions
		try 
		{
			// log method entry
			logger.info("Entering OrderService.handleEdit");
			
			// validate parameter
			if (bindingResult.hasErrors())
			{
				// return DTO informing the user of the failure
				return new RestDto<Order>(null, 400, "Bad Request");
			}
						
			// pass control to business layer to update order
			if (service.edit(order))
			{
				// instantiate response data
				ArrayList<Order> data= new ArrayList<Order>();
				// add updated order to response data
				data.add(order);
				
				// log outcome
				logger.info("Exiting OrderService.handleEdit with code 200"); 
				
				// return DTO indicating request succeed
				return new RestDto<Order>(data, 200, "OK");
			}
			
			// if the update failed
			else 
			{
				// log outcome
				logger.info("Exiting OrderService.handleEdit with code 404"); 
				
				// return DTO indicating the order wasn't found
				return new RestDto<Order>(null, 404, "Not found");
			}
		}
		
		// handle exceptions here
		catch (Exception e)
		{
			// log outcome
			logger.error("Exiting OrderService.handleEdit with code 500"); 
			
			// return DTO indicating there was an internal error
			return new RestDto<Order>(null, 500, "Internal error");
		}
		
	}
	
	/**
	 * This method handles the cancellation of an order
	 * @param id the unique ID of the order to being cancelled 
	 * @return RestDto a DTO that includes the response data, response, code, and response message
	 */
	@GetMapping("/deleteOrder/{id}")
	public RestDto<Order> handleCancel(@PathVariable("id") int id)
	{
		try
		{
			// log method entry
			logger.info("Entering OrderService.handleCancel");
						
			// instantiate order from passed ID
			Order order = new Order(id, null, null, 0, 0);
			// pass control to business layer to delete order
			if (service.cancel(order))
			{
				// initialize response data
				ArrayList<Order> data= new ArrayList<Order>();
				// add the cancelled order to the response data
				data.add(order);
				
				// log outcome
				logger.info("Exiting OrderService.handleCancel with code 200"); 
				
				// return a DTO indicating the request succeeded
				return new RestDto<Order>(data, 200, "OK");
			}
			
			// if cancellation fails
			else 
			{
				// log outcome
				logger.error("Exiting OrderService.handleCancel with code 500"); 
				
				// return a DTO indicating the order wasn't found
				return new RestDto<Order>(null, 500, "Internal error");
			}
		}
		
		// handle exceptions here
		catch (Exception e)
		{
			// log outcome
			logger.error("Exiting OrderService.handlePlace with code 500"); 
						
			// return a DTO indicating the order wasn't found
			return new RestDto<Order>(null, 500, "Internal error");
		}
		
	}

}
