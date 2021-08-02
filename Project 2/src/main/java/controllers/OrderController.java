package controllers;

import io.javalin.http.Context;
import models.Order;
import service.OrderService;
import service.OrderServiceImpl;

public class OrderController {
	
	static OrderService orderService = new OrderServiceImpl();
	
	// fetch a particular order
	public static void getOrder(Context context) {
		Integer id = Integer.parseInt(context.pathParam("id"));
		Order result = orderService.getOrder(id);
		
		if(result == null) {
			context.result("No order exists with that ID");
		} else {
			context.json(result);
		}
		
	}

	// fetch all orders
	public static void getAll(Context context) {
		context.json(orderService.getAll());
	}
	
	// fetch all orders for a particular customer
	public static void getAllForCustomer(Context context) {
		String customerId = context.pathParam("id");
		context.json(orderService.getAll(customerId));
	}
	
	// fetch all pending orders
	public static void getPending(Context context) {
		context.json(orderService.getPending());
	}
	
	// fetch all pending orders for a particular customer
	public static void getPendingForCustomer(Context context) {
		String customerId = context.pathParam("id");
		context.json(orderService.getPending(customerId));
	}
	
	// fetch all completed orders
	public static void getCompleted(Context context) {
		context.json(orderService.getCompleted());
	}
	
	// fetch all completed orders for a particular customer
	public static void getCompletedForCustomer(Context context) {
		String customerId = context.pathParam("id");
		context.json(orderService.getCompleted(customerId));
	}
	
	// create a new order
	public static void createOrder(Context context) {
		Order order = context.bodyAsClass(Order.class);
		Integer result = orderService.createOrder(order);
		context.result("Order placed successfully. Your order number is " + result);
	}
	
	// update an existing order
	public static void updateOrder(Context context) {
		Integer id = Integer.parseInt(context.pathParam("id"));
		Order order = context.bodyAsClass(Order.class);
		String result = orderService.updateOrder(id, order.getCustomerId(), order.getStatus(), order.getItems(), 
				order.getTotal(), order.getTimePlaced(), order.getTimeCompleted(), order.getEmployeeId(), order.getNotes());
		context.result(result);
	}
	
	// change the status of an order
	public static void setOrderStatus(Context context) {
		Integer id = Integer.parseInt(context.pathParam("id"));
		Order order = context.bodyAsClass(Order.class);
		String result = orderService.setOrderStatus(id, order.getEmployeeId(), order.getStatus());
		context.result(result);
	}
	
	// cancel an order
	public static void cancelOrder(Context context) {
		Integer id = Integer.parseInt(context.pathParam("id"));
		Order order = context.bodyAsClass(Order.class);
		String result = orderService.cancelOrder(id, order.getEmployeeId(), order.getNotes());
		context.result(result);
	}
	
	// delete an order from the database
	public static void deleteOrder(Context context) {
		Integer id = Integer.parseInt(context.pathParam("id"));
		String result = orderService.deleteOrder(id);
		context.result(result);
	}
	
	// fetch the key order statistics
	public static void getStats(Context context) {
		context.json(orderService.getStats());
	}
}
