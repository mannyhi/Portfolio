package dao;

import java.util.Map;

import models.Order;

public interface OrderDao {
	// fetch a particular order
	Order getOrder(Integer id);
	// fetch all orders
	Map<Integer, Order> getAll();
	// fetch all orders for a particular customer
	Map<Integer, Order> getAll(String customerId);
	// fetch all pending orders
	Map<Integer, Order> getPending();
	// fetch all pending orders for a particular customer
	Map<Integer, Order> getPending(String customerId);
	// fetch all completed orders
	Map<Integer, Order> getCompleted();
	// fetch all completed orders for a particular customer
	Map<Integer, Order> getCompleted(String customerId);
	// create a new order
	void createOrder(Order order);
	// update an existing order
	void updateOrder(Integer id, Order newOrder);
	// delete an order from the database
	void deleteOrder(Integer id);
	// this is a utility method used to get the id of a newly created order
	Integer getNewOrderId(String customerId);
	// fetch the key order statistics
	Map <String, String> getStats();
}