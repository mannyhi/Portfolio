package service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import dao.OrderDao;
import dao.OrderDaoImpl;
import models.Order;

public class OrderServiceImpl implements OrderService{
	
	private OrderDao orderDao;
	
	public OrderServiceImpl(){
		this.orderDao = new OrderDaoImpl();
	}
	
	// fetch a particular order
	public Order getOrder(int id) {
		return this.orderDao.getOrder(id);
	}

	// fetch all orders
	public Map<Integer, Order> getAll() {
		return this.orderDao.getAll();
	}

	// fetch all orders for a particular customer
	public Map<Integer, Order> getAll(String customerId) {
		return this.orderDao.getAll(customerId);
	}

	// fetch all pending orders
	public Map<Integer, Order> getPending() {
		return this.orderDao.getPending();
	}

	// fetch all pending orders for a particular customer
	public Map<Integer, Order> getPending(String customerId) {
		return this.orderDao.getPending(customerId);
	}

	// fetch all completed orders
	public Map<Integer, Order> getCompleted() {
		return this.orderDao.getCompleted();
	}

	// fetch all completed orders for a particular customer
	public Map<Integer, Order> getCompleted(String customerId) {
		return this.orderDao.getCompleted(customerId);
	}

	// create a new customer (returns id of new order)
	public Integer createOrder(Order order) {
		
		// add it to the database
		this.orderDao.createOrder(order);
		
		// return the id of the new order we created
		return this.orderDao.getNewOrderId(order.getCustomerId());
	}

	// update an existing order
	public String updateOrder(int id, String customerId, String status, String items, BigDecimal total,
							  Timestamp timePlaced, Timestamp timeCompleted, String employeeId, String notes) {
		// check to see if the order id exists in the database
		if(this.orderDao.getOrder(id) == null) {
			return "No order exists with that ID";
		}
		
		// create a new order with the updated information
		Order order = new Order(id, customerId, status, items, total, timePlaced, timeCompleted, 
								employeeId, notes);
		
		// send the updated information to the database
		this.orderDao.updateOrder(id, order);
		
		// return success message
		return "Order updated successfully";
	}

	// update the status of an order
	public String setOrderStatus(int id, String employeeId, String status) {
		// get the desired order from the database
		Order order = this.getOrder(id);
		
		// check to see if the order id exists in the database
		if(order == null) {
			return "No order exists with that ID";
		}
		
		// change the order status
		order.setStatus(status);
		
		if(status.equals("completed")) {
			// set the employeeId
			order.setEmployeeId(employeeId);
			
			// set the time completed
			order.setTimeCompleted(new Timestamp(System.currentTimeMillis()));
		}
		
		
		// update the order in the database
		this.orderDao.updateOrder(id, order);
		
		// return success message
		return "Order updated successfully";
	}

	// cancel an order
	public String cancelOrder(int id, String employeeId, String notes) {
		// get the desired order from the database
		Order order = this.getOrder(id);
		
		// check to see if the order id exists in the database
		if(order == null) {
			return "No order exists with that ID";
		}
		
		// change the order status to cancelled
		order.setStatus("canceled");
		
		// update the employeeId
		order.setEmployeeId(employeeId);
		
		// add the notes (required when canceling an order)
		order.setNotes(notes);
		
		// update the order in the database
		this.orderDao.updateOrder(id, order);
		
		// return success message
		return "Order updated successfully";
	}

	// delete an order from the database
	public String deleteOrder(int id) {
		// check to see if the order id exists in the database
		if(this.orderDao.getOrder(id) == null) {
			return "No order exists with that ID";
		}
		
		// delete the order if it exists in the database
		this.orderDao.deleteOrder(id);
		
		// return success message
		return "Order deleted successfully";
	}
	
	
	// fetch the key order statistics
	public Map<String, String> getStats(){
		return this.orderDao.getStats();
	}
}
