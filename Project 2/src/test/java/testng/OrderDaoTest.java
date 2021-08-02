package testng;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import dao.OrderDaoImpl;
import models.Order;

public class OrderDaoTest {
	
	@InjectMocks
	public OrderDaoImpl orderDaoImpl;
	
	@SuppressWarnings("deprecation")
	@BeforeTest
	public void initMocks() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = "before-new-order")
	public void testGetOrder() {
		// use the method to fetch an order
		Order result = orderDaoImpl.getOrder(1);
		
		// verify that the method returned the correct order
		assert result.getClass() == Order.class;
		assert result.getId() == 1;
		assert result.getCustomerId().equals("testCustomer");
		assert result.getStatus().equals("completed");
		assert result.getItems().equals("items");
		assert result.getTotal().equals(new BigDecimal(25.36).setScale(2, RoundingMode.HALF_UP));
		assert result.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
		assert result.getTimeCompleted() == null;
		assert result.getEmployeeId() == null;
		assert result.getNotes() == null;
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = "before-new-order")
	public void testGetAll() {
		// use the method to fetch all the orders
		Map<Integer, Order> result = orderDaoImpl.getAll();
		
		// verify that the method returned all six orders with the correct information
		assert result.size() == 6;
		result.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			switch(order.getId()) {
				case 1:
					assert order.getCustomerId().equals("testCustomer");
					assert order.getStatus().equals("completed");
					assert order.getTotal().equals(new BigDecimal(25.36).setScale(2, RoundingMode.HALF_UP));
					assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
					break;
				case 2:
					assert order.getCustomerId().equals("testEmployee");
					assert order.getStatus().equals("order placed");
					assert order.getTotal().equals(new BigDecimal(5.69).setScale(2, RoundingMode.HALF_UP));
					assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
					break;
				case 3:
					assert order.getCustomerId().equals("testCustomer");
					assert order.getStatus().equals("canceled");
					assert order.getTotal().equals(new BigDecimal(7.98).setScale(2, RoundingMode.HALF_UP));
					assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
					break;
				case 4:
					assert order.getCustomerId().equals("testCustomer");
					assert order.getStatus().equals("ready for pickup");
					assert order.getTotal().equals(new BigDecimal(21.05).setScale(2, RoundingMode.HALF_UP));
					assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
					break;
				case 5:
					assert order.getCustomerId().equals("testCustomer");
					assert order.getStatus().equals("order placed");
					assert order.getTotal().equals(new BigDecimal(22.84).setScale(2, RoundingMode.HALF_UP));
					assert order.getTimePlaced().equals(new Timestamp(121, 6, 20, 0, 0, 0, 0));
					break;
				default:
					assert order.getId() == 6;
					assert order.getCustomerId().equals("testEmployee");
					assert order.getStatus().equals("completed");
					assert order.getTotal().equals(new BigDecimal(5.69).setScale(2, RoundingMode.HALF_UP));
					assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
					break;
			}
			assert order.getItems().equals("items");
			assert order.getTimeCompleted() == null;
			assert order.getEmployeeId() == null;
			assert order.getNotes() == null;
		});
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = "before-new-order")
	public void testGetAllForCustomer() {
		// use the method to fetch all the orders for the customer
		Map<Integer, Order> result = orderDaoImpl.getAll("testCustomer");
		
		// verify that the method returned all four orders with the correct information
		assert result.size() == 4;
		result.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			switch(order.getId()) {
				case 1:
					assert order.getStatus().equals("completed");
					assert order.getTotal().equals(new BigDecimal(25.36).setScale(2, RoundingMode.HALF_UP));
					assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
					break;
				case 3:
					assert order.getStatus().equals("canceled");
					assert order.getTotal().equals(new BigDecimal(7.98).setScale(2, RoundingMode.HALF_UP));
					assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
					break;
				case 4:
					assert order.getStatus().equals("ready for pickup");
					assert order.getTotal().equals(new BigDecimal(21.05).setScale(2, RoundingMode.HALF_UP));
					assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
					break;
				default:
					assert order.getId() == 5;
					assert order.getStatus().equals("order placed");
					assert order.getTotal().equals(new BigDecimal(22.84).setScale(2, RoundingMode.HALF_UP));
					assert order.getTimePlaced().equals(new Timestamp(121, 6, 20, 0, 0, 0, 0));
					break;
			}
			assert order.getCustomerId().equals("testCustomer");
			assert order.getItems().equals("items");
			assert order.getTimeCompleted() == null;
			assert order.getEmployeeId() == null;
			assert order.getNotes() == null;
		});
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = "before-new-order")
	public void testGetPending() {
		// use the method to fetch all the pending orders
		Map<Integer, Order> result = orderDaoImpl.getPending();
		
		// verify that the method returned the three orders with the correct information
		assert result.size() == 3;
		result.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			if(order.getId() == 2) {
				assert order.getCustomerId().equals("testEmployee");
				assert order.getStatus().equals("order placed");
				assert order.getTotal().equals(new BigDecimal(5.69).setScale(2, RoundingMode.HALF_UP));
				assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
			} else if(order.getId() == 4) {
				assert order.getCustomerId().equals("testCustomer");
				assert order.getStatus().equals("ready for pickup");
				assert order.getTotal().equals(new BigDecimal(21.05).setScale(2, RoundingMode.HALF_UP));
				assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
			} else {
				assert order.getId() == 5;
				assert order.getCustomerId().equals("testCustomer");
				assert order.getStatus().equals("order placed");
				assert order.getTotal().equals(new BigDecimal(22.84).setScale(2, RoundingMode.HALF_UP));
				assert order.getTimePlaced().equals(new Timestamp(121, 6, 20, 0, 0, 0, 0));
			}
			assert order.getItems().equals("items");
			assert order.getTimeCompleted() == null;
			assert order.getEmployeeId() == null;
			assert order.getNotes() == null;
		});
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = "before-new-order")
	public void testGetPendingForCustomer() {
		// use the method to fetch all the pending orders for the customer
		Map<Integer, Order> result = orderDaoImpl.getPending("testCustomer");
		
		// verify that the method returned the two orders with the correct information
		assert result.size() == 2;
		result.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			if(order.getId() == 4) {
				assert order.getStatus().equals("ready for pickup");
				assert order.getTotal().equals(new BigDecimal(21.05).setScale(2, RoundingMode.HALF_UP));
				assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
			} else {
				assert order.getId() == 5;
				assert order.getStatus().equals("order placed");
				assert order.getTotal().equals(new BigDecimal(22.84).setScale(2, RoundingMode.HALF_UP));
				assert order.getTimePlaced().equals(new Timestamp(121, 6, 20, 0, 0, 0, 0));
			}
			assert order.getCustomerId().equals("testCustomer");
			assert order.getItems().equals("items");
			assert order.getTimeCompleted() == null;
			assert order.getEmployeeId() == null;
			assert order.getNotes() == null;
		});
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = "before-new-order")
	public void testGetCompleted() {
		// use the method to fetch all the completed orders
		Map<Integer, Order> result = orderDaoImpl.getCompleted();
		
		// verify that the method returned the two orders with the correct information
		assert result.size() == 2;
		result.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			if(order.getId() == 1) {
				assert order.getCustomerId().equals("testCustomer");
				assert order.getStatus().equals("completed");
				assert order.getTotal().equals(new BigDecimal(25.36).setScale(2, RoundingMode.HALF_UP));
			} else {
				assert order.getId() == 6;
				assert order.getCustomerId().equals("testEmployee");
				assert order.getStatus().equals("completed");
				assert order.getTotal().equals(new BigDecimal(5.69).setScale(2, RoundingMode.HALF_UP));
			}
			assert order.getItems().equals("items");
			assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
			assert order.getTimeCompleted() == null;
			assert order.getEmployeeId() == null;
			assert order.getNotes() == null;
		});
	}
	
	@SuppressWarnings("deprecation")
	@Test(groups = "before-new-order")
	public void testGetCompletedForCustomer() {
		// use the method to fetch all the completed orders for the customer
		Map<Integer, Order> result = orderDaoImpl.getCompleted("testCustomer");
		
		// verify that the method returned the only completed order with the correct information
		assert result.size() == 1;
		result.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			assert order.getId() == 1;
			assert order.getCustomerId().equals("testCustomer");
			assert order.getStatus().equals("completed");
			assert order.getItems().equals("items");
			assert order.getTotal().equals(new BigDecimal(25.36).setScale(2, RoundingMode.HALF_UP));
			assert order.getTimePlaced().equals(new Timestamp(121, 6, 19, 0, 0, 0, 0));
			assert order.getTimeCompleted() == null;
			assert order.getEmployeeId() == null;
			assert order.getNotes() == null;
		});
	}
	
	@Test(dependsOnGroups = "before-new-order")
	public void testCreateOrder() {
		// set up a new order
		Order testOrder = new Order("testCustomer", "items", new BigDecimal(11.11).setScale(2, RoundingMode.HALF_UP), new Timestamp(System.currentTimeMillis()));
		
		// get the id of the most recent order for the customer
		int orderId = orderDaoImpl.getNewOrderId("testCustomer");
		
		// add the order to the test database
		orderDaoImpl.createOrder(testOrder);
		
		// get the id of the new order
		int newOrderId = orderDaoImpl.getNewOrderId("testCustomer");
		
		// verify that the order didn't already exist
		assert newOrderId > orderId;
		
		// verify that the order now exists and has all the correct information
		Order result = orderDaoImpl.getOrder(newOrderId);
		assert result.getClass() == Order.class;
		assert result.getId() == newOrderId;
		assert result.getCustomerId().equals("testCustomer");
		assert result.getStatus().equals("order placed");
		assert result.getItems().equals("items");
		assert result.getTotal().equals(new BigDecimal(11.11).setScale(2, RoundingMode.HALF_UP));
		assert result.getTimeCompleted() == null;
		assert result.getEmployeeId() == null;
		assert result.getNotes() == null;
	}
	
	@Test(dependsOnMethods = "testCreateOrder")
	public void testUpdateOrder() {
		// get the id of the most recent order for the customer
		int orderId = orderDaoImpl.getNewOrderId("testCustomer");
		
		// set up a new order with some different info
		Timestamp time = new Timestamp(System.currentTimeMillis());
		Order newOrder = new Order(orderId, "testCustomer", "completed", "new items", new BigDecimal(22.22).setScale(2, RoundingMode.HALF_UP), time, time, "testManager", "test order");
		
		// update the order info
		orderDaoImpl.updateOrder(orderId, newOrder);
		
		// fetch the order from the test database
		Order order = orderDaoImpl.getOrder(orderId);
		
		// verify that the order was updated with the correct info
		assert order.getClass() == Order.class;
		assert order.getId() == newOrder.getId();
		assert order.getCustomerId().equals(newOrder.getCustomerId());
		assert order.getItems().equals(newOrder.getItems());
		assert order.getTotal().equals(newOrder.getTotal());
		assert order.getTimePlaced().equals(newOrder.getTimePlaced());
		assert order.getTimeCompleted().equals(newOrder.getTimeCompleted());
		assert order.getEmployeeId().equals(newOrder.getEmployeeId());
		assert order.getNotes().equals(newOrder.getNotes());
	}
	
	@Test(dependsOnMethods = "testUpdateOrder")
	public void testDeleteOrder() {
		// get the id of the most recent order for the customer
		int orderId = orderDaoImpl.getNewOrderId("testCustomer");
		
		// verify that the order currently exists
		assert orderDaoImpl.getOrder(orderId).getClass() == Order.class;
		
		// delete the order from the test database
		orderDaoImpl.deleteOrder(orderId);
		
		// verify that the order no longer exists
		assert orderDaoImpl.getOrder(orderId) == null;
	}
	
	@Test(groups = "before-new-order")
	public void testGetNewOrderId() {
		// verify that the method returns the id of the most recent order for the customer
		assert orderDaoImpl.getNewOrderId("testCustomer") == 5;
	}
	
	@Test(groups = "before-new-order")
	public void testGetStats() {
		// use the method to fetch the stats
		Map <String, String> results = orderDaoImpl.getStats();
		
		// verify that all the information is correct
		results.forEach((key, stat) -> {
			switch(key) {
				case "Q1 Revenue":
					assert stat.equals("$0.00");
					break;
				case "Q2 Revenue":
					assert stat.equals("$0.00");
					break;
				case "Q3 Revenue":
					assert stat.equals("$80.63");
					break;
				case "Q4 Revenue":
					assert stat.equals("$0.00");
					break;
				case "Total Revenue":
					assert stat.equals("$80.63");
					break;
				default:
					assert key.equals("Number of Orders");
					assert stat.equals("5");
					break;
			}
		});
	}
}
