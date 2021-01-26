package com.gcu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.gcu.business.OrdersBusinessInterface;
import com.gcu.business.OrdersBusinessService;

/**
 * This class is used to define beans for dependency injection
 * @author Josh Van de Walle
 *
 */
@Configuration
public class ApplicationConfiguration 
{
	
	/**
	 * This method defines the OrderBusinessInterface implementation to inject into the OrderService
	 * @return the service to inject
	 */
	@Bean(name="ordersService")
	@Scope(value="prototype", proxyMode=ScopedProxyMode.TARGET_CLASS) 
	public OrdersBusinessInterface getOrdersService()
	{
		return new OrdersBusinessService();
	}

}
