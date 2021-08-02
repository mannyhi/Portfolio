package service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import models.Order;

public interface OrderService {
	// fetch a particular order
	Order getOrder(int id);
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
	// create a new order (returns id of new order)
	Integer createOrder(Order order);
	// update an existing order
	String updateOrder(int id, String customerId, String status, String items, BigDecimal total, Timestamp timePlaced, Timestamp timeCompleted, String employeeId, String notes);
	// change the status of an order
	String setOrderStatus(int id, String employeeId, String status);
	// cancel an order
	String cancelOrder(int id, String employeeId, String notes);
	// delete an order from the database
	String deleteOrder(int id);
	// fetch the key order statistics
	Map<String, String> getStats();
}
