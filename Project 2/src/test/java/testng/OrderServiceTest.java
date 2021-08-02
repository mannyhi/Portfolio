package testng;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import models.Order;
import dao.OrderDaoImpl;
import service.OrderServiceImpl;

public class OrderServiceTest {
	
	@Mock
	public OrderDaoImpl orderDaoImpl;
	
	@InjectMocks
	public OrderServiceImpl orderServiceImpl;
	
	@SuppressWarnings("deprecation")
	@BeforeTest
	public void initMocks() {
	    MockitoAnnotations.initMocks(this);
	}
	
	// initialize our mock return values
	public static Order dummyOrder1;
	public static Order dummyOrder2;
	public static Order dummyOrder3;
	public static Order dummyOrder4;
	public static Order dummyOrder5;
	public static Map<Integer, Order> dummyOrders;
	public static Map<Integer, Order> dummyOrdersForCustomer;
	public static Map<Integer, Order> dummyPendingOrders;
	public static Map<Integer, Order> dummyPendingOrdersForCustomer;
	public static Map<Integer, Order> dummyCompletedOrders;
	public static Map<Integer, Order> dummyCompletedOrdersForCustomer;
	public static Map<String, String> dummyStats;
	
	@BeforeClass
	public void beforeClassSetup() {
		MockitoAnnotations.openMocks(OrderServiceTest.class);
		
		// set the values of variables to be returned
		dummyOrder1 = new Order(1, "testCustomer", "order placed", "items", new BigDecimal(34.29), 
								new Timestamp(System.currentTimeMillis()), null, null, null);
		dummyOrder2 = new Order(2, "testCustomer", "ready for pickup", "items", new BigDecimal(20), 
					new Timestamp(System.currentTimeMillis()), null, null, null);
		dummyOrder3 = new Order(3, "testCustomer", "completed", "items", new BigDecimal(19.99), 
					new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "testEmployee", null);
		dummyOrder4 = new Order(4, "testCustomer2", "ready for pickup", "items", new BigDecimal(42.53), 
					new Timestamp(System.currentTimeMillis()), null, null, null);
		dummyOrder5 = new Order(5, "testCustomer2", "completed", "items", new BigDecimal(12.52), 
					new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "testEmployee", null);
		dummyOrders = new HashMap<>();
			dummyOrders.put(1, dummyOrder1);
			dummyOrders.put(2, dummyOrder2);
			dummyOrders.put(3, dummyOrder3);
			dummyOrders.put(4, dummyOrder4);
			dummyOrders.put(5, dummyOrder5);
		dummyOrdersForCustomer = new HashMap<>();
			dummyOrdersForCustomer.put(1, dummyOrder1);
			dummyOrdersForCustomer.put(2, dummyOrder2);
			dummyOrdersForCustomer.put(3, dummyOrder3);
		dummyPendingOrders = new HashMap<>();
			dummyPendingOrders.put(1, dummyOrder1);
			dummyPendingOrders.put(2, dummyOrder2);
			dummyPendingOrders.put(4, dummyOrder4);
		dummyPendingOrdersForCustomer = new HashMap<>();
			dummyPendingOrdersForCustomer.put(1, dummyOrder1);
			dummyPendingOrdersForCustomer.put(2, dummyOrder2);
		dummyCompletedOrders = new HashMap<>();
			dummyCompletedOrders.put(3, dummyOrder3);
			dummyCompletedOrders.put(5, dummyOrder5);
		dummyCompletedOrdersForCustomer = new HashMap<>();
			dummyCompletedOrdersForCustomer.put(3, dummyOrder3);
		
		// set the return values for our mock dao methods
		Mockito.when(orderDaoImpl.getOrder(1)).thenReturn(dummyOrder1);
		Mockito.when(orderDaoImpl.getOrder(2)).thenReturn(null);
		Mockito.when(orderDaoImpl.getOrder(3)).thenReturn(dummyOrder3);
		Mockito.when(orderDaoImpl.getOrder(4)).thenReturn(dummyOrder4);
		Mockito.when(orderDaoImpl.getAll()).thenReturn(dummyOrders);
		Mockito.when(orderDaoImpl.getAll("testCustomer")).thenReturn(dummyOrdersForCustomer);
		Mockito.when(orderDaoImpl.getPending()).thenReturn(dummyPendingOrders);
		Mockito.when(orderDaoImpl.getPending("testCustomer")).thenReturn(dummyPendingOrdersForCustomer);
		Mockito.when(orderDaoImpl.getCompleted()).thenReturn(dummyCompletedOrders);
		Mockito.when(orderDaoImpl.getCompleted("testCustomer")).thenReturn(dummyCompletedOrdersForCustomer);
		Mockito.when(orderDaoImpl.getStats()).thenReturn(dummyStats);
		Mockito.when(orderDaoImpl.getNewOrderId("testCustomer")).thenReturn(2);
	}
	
	@Test(groups = "before-new-order")
	public void testGetOrder() {
		// execute the method with the wrong order ID
		Order result = orderServiceImpl.getOrder(2);
		
		// verify that it returned a null value
		assert result == null;
		
		// execute the method with the correct order ID
		result = orderServiceImpl.getOrder(1);
		
		// verify that the method returned the correct information
		assert result.getClass() == Order.class;
		assert result.getId() == dummyOrder1.getId();
		assert result.getCustomerId().equals(dummyOrder1.getCustomerId());
		assert result.getStatus().equals(dummyOrder1.getStatus());
		assert result.getItems().equals(dummyOrder1.getItems());
		assert result.getTotal().equals(dummyOrder1.getTotal());
		assert result.getTimePlaced().equals(dummyOrder1.getTimePlaced());
		assert result.getTimeCompleted() == dummyOrder1.getTimeCompleted();
		assert result.getEmployeeId() == dummyOrder1.getEmployeeId();
		assert result.getNotes() == dummyOrder1.getNotes();
	}
	
	@Test(groups = "before-new-order")
	public void testGetAll() {
		// execute the method
		Map<Integer, Order> results = orderServiceImpl.getAll();
		
		// verify that the method returned all five orders with the correct information
		assert results.size() == 5;
		results.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			assert order.getId() == dummyOrders.get(key).getId();
			assert order.getCustomerId().equals(dummyOrders.get(key).getCustomerId());
			assert order.getStatus().equals(dummyOrders.get(key).getStatus());
			assert order.getItems().equals(dummyOrders.get(key).getItems());
			assert order.getTotal().equals(dummyOrders.get(key).getTotal());
			assert order.getTimePlaced().equals(dummyOrders.get(key).getTimePlaced());
			if(order.getTimeCompleted() == null) {
				assert dummyOrders.get(key).getTimeCompleted() == null;
			} else {
				assert order.getTimeCompleted().equals(dummyOrders.get(key).getTimeCompleted());
			}
			if(order.getEmployeeId() == null) {
				assert dummyOrders.get(key).getEmployeeId() == null;
			} else {
				assert order.getEmployeeId().equals(dummyOrders.get(key).getEmployeeId());
			}
			if(order.getNotes() == null) {
				assert dummyOrders.get(key).getNotes() == null;
			} else {
				assert order.getNotes().equals(dummyOrders.get(key).getNotes());
			}
		});
	}
	
	@Test(groups = "before-new-order")
	public void testGetAllForCustomer() {
		// execute the method
		Map<Integer, Order> results = orderServiceImpl.getAll("testCustomer");
		
		// verify that the method returned the three orders with the correct information
		assert results.size() == 3;
		results.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			assert order.getId() == dummyOrders.get(key).getId();
			assert order.getCustomerId().equals(dummyOrders.get(key).getCustomerId());
			assert order.getStatus().equals(dummyOrders.get(key).getStatus());
			assert order.getItems().equals(dummyOrders.get(key).getItems());
			assert order.getTotal().equals(dummyOrders.get(key).getTotal());
			assert order.getTimePlaced().equals(dummyOrders.get(key).getTimePlaced());
			if(order.getTimeCompleted() == null) {
				assert dummyOrders.get(key).getTimeCompleted() == null;
			} else {
				assert order.getTimeCompleted().equals(dummyOrders.get(key).getTimeCompleted());
			}
			if(order.getEmployeeId() == null) {
				assert dummyOrders.get(key).getEmployeeId() == null;
			} else {
				assert order.getEmployeeId().equals(dummyOrders.get(key).getEmployeeId());
			}
			if(order.getNotes() == null) {
				assert dummyOrders.get(key).getNotes() == null;
			} else {
				assert order.getNotes().equals(dummyOrders.get(key).getNotes());
			}
		});
	}
	
	@Test(groups = "before-new-order")
	public void testGetPending() {
		// execute the method
		Map<Integer, Order> results = orderServiceImpl.getPending();
		
		// verify that the method returned the three orders with the correct information
		assert results.size() == 3;
		results.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			assert order.getId() == dummyOrders.get(key).getId();
			assert order.getCustomerId().equals(dummyOrders.get(key).getCustomerId());
			assert order.getStatus().equals(dummyOrders.get(key).getStatus());
			assert order.getItems().equals(dummyOrders.get(key).getItems());
			assert order.getTotal().equals(dummyOrders.get(key).getTotal());
			assert order.getTimePlaced().equals(dummyOrders.get(key).getTimePlaced());
			if(order.getTimeCompleted() == null) {
				assert dummyOrders.get(key).getTimeCompleted() == null;
			} else {
				assert order.getTimeCompleted().equals(dummyOrders.get(key).getTimeCompleted());
			}
			if(order.getEmployeeId() == null) {
				assert dummyOrders.get(key).getEmployeeId() == null;
			} else {
				assert order.getEmployeeId().equals(dummyOrders.get(key).getEmployeeId());
			}
			if(order.getNotes() == null) {
				assert dummyOrders.get(key).getNotes() == null;
			} else {
				assert order.getNotes().equals(dummyOrders.get(key).getNotes());
			}
		});
	}
	
	@Test(groups = "before-new-order")
	public void testGetPendingForCustomer() {
		// execute the method
		Map<Integer, Order> results = orderServiceImpl.getPending("testCustomer");
		
		// verify that the method returned the two orders with the correct information
		assert results.size() == 2;
		results.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			assert order.getId() == dummyOrders.get(key).getId();
			assert order.getCustomerId().equals(dummyOrders.get(key).getCustomerId());
			assert order.getStatus().equals(dummyOrders.get(key).getStatus());
			assert order.getItems().equals(dummyOrders.get(key).getItems());
			assert order.getTotal().equals(dummyOrders.get(key).getTotal());
			assert order.getTimePlaced().equals(dummyOrders.get(key).getTimePlaced());
			if(order.getTimeCompleted() == null) {
				assert dummyOrders.get(key).getTimeCompleted() == null;
			} else {
				assert order.getTimeCompleted().equals(dummyOrders.get(key).getTimeCompleted());
			}
			if(order.getEmployeeId() == null) {
				assert dummyOrders.get(key).getEmployeeId() == null;
			} else {
				assert order.getEmployeeId().equals(dummyOrders.get(key).getEmployeeId());
			}
			if(order.getNotes() == null) {
				assert dummyOrders.get(key).getNotes() == null;
			} else {
				assert order.getNotes().equals(dummyOrders.get(key).getNotes());
			}
		});
	}
	
	@Test(groups = "before-new-order")
	public void testGetCompleted() {
		// execute the method
		Map<Integer, Order> results = orderServiceImpl.getCompleted();
		
		// verify that the method returned the two orders with the correct information
		assert results.size() == 2;
		results.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			assert order.getId() == dummyOrders.get(key).getId();
			assert order.getCustomerId().equals(dummyOrders.get(key).getCustomerId());
			assert order.getStatus().equals(dummyOrders.get(key).getStatus());
			assert order.getItems().equals(dummyOrders.get(key).getItems());
			assert order.getTotal().equals(dummyOrders.get(key).getTotal());
			assert order.getTimePlaced().equals(dummyOrders.get(key).getTimePlaced());
			if(order.getTimeCompleted() == null) {
				assert dummyOrders.get(key).getTimeCompleted() == null;
			} else {
				assert order.getTimeCompleted().equals(dummyOrders.get(key).getTimeCompleted());
			}
			if(order.getEmployeeId() == null) {
				assert dummyOrders.get(key).getEmployeeId() == null;
			} else {
				assert order.getEmployeeId().equals(dummyOrders.get(key).getEmployeeId());
			}
			if(order.getNotes() == null) {
				assert dummyOrders.get(key).getNotes() == null;
			} else {
				assert order.getNotes().equals(dummyOrders.get(key).getNotes());
			}
		});
	}
	
	@Test(groups = "before-new-order")
	public void testGetCompletedForCustomer() {
		// execute the method
		Map<Integer, Order> results = orderServiceImpl.getCompleted("testCustomer");
		
		// verify that the method returned the order with the correct information
		assert results.size() == 1;
		results.forEach((key, order) -> {
			assert order.getClass() == Order.class;
			assert order.getId() == dummyOrders.get(key).getId();
			assert order.getCustomerId().equals(dummyOrders.get(key).getCustomerId());
			assert order.getStatus().equals(dummyOrders.get(key).getStatus());
			assert order.getItems().equals(dummyOrders.get(key).getItems());
			assert order.getTotal().equals(dummyOrders.get(key).getTotal());
			assert order.getTimePlaced().equals(dummyOrders.get(key).getTimePlaced());
			if(order.getTimeCompleted() == null) {
				assert dummyOrders.get(key).getTimeCompleted() == null;
			} else {
				assert order.getTimeCompleted().equals(dummyOrders.get(key).getTimeCompleted());
			}
			if(order.getEmployeeId() == null) {
				assert dummyOrders.get(key).getEmployeeId() == null;
			} else {
				assert order.getEmployeeId().equals(dummyOrders.get(key).getEmployeeId());
			}
			if(order.getNotes() == null) {
				assert dummyOrders.get(key).getNotes() == null;
			} else {
				assert order.getNotes().equals(dummyOrders.get(key).getNotes());
			}
		});
	}
	
	@Test
	public void testCreateOrder() {
		// call the method
		int result = orderServiceImpl.createOrder(dummyOrder2);
		
		// verify that the dao method was called with the correct information and returned the correct order ID
		Mockito.verify(orderDaoImpl).createOrder(dummyOrder2);
		assert result == 2;
	}
	
	@Test
	public void testUpdateOrder() {
		// set up some argument captors we'll need for verification
		final ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(int.class);
		final ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
		
		// call the method
		orderServiceImpl.updateOrder(dummyOrder1.getId(), dummyOrder1.getCustomerId(), dummyOrder1.getStatus(), dummyOrder1.getItems(), dummyOrder1.getTotal(), 
									dummyOrder1.getTimePlaced(), dummyOrder1.getTimeCompleted(), dummyOrder1.getEmployeeId(), dummyOrder1.getNotes());
		
		// verify that the dao method was called with the correct information
		Mockito.verify(orderDaoImpl, atLeastOnce()).updateOrder(intCaptor.capture(), orderCaptor.capture());
		assert intCaptor.getValue().equals(dummyOrder1.getId());
		Order argOrder = orderCaptor.getValue();
		assert argOrder.getClass() == Order.class;
		assert argOrder.getId() == dummyOrder1.getId();
		assert argOrder.getCustomerId().equals(dummyOrder1.getCustomerId());
		assert argOrder.getStatus().equals(dummyOrder1.getStatus());
		assert argOrder.getItems().equals(dummyOrder1.getItems());
		assert argOrder.getTotal().equals(dummyOrder1.getTotal());
		assert argOrder.getTimePlaced().equals(dummyOrder1.getTimePlaced());
		assert argOrder.getTimeCompleted() == dummyOrder1.getTimeCompleted();
		assert argOrder.getEmployeeId() == dummyOrder1.getEmployeeId();
		assert argOrder.getNotes() == dummyOrder1.getNotes();
	}
	
	@Test
	public void testSetOrderStatus() {
		// call the method with incorrect information
		orderServiceImpl.setOrderStatus(2, "testEmployee", "Test Status");
		
		// verify that the dao method was not called
		Mockito.verify(orderDaoImpl, never()).updateOrder(2, dummyOrder2);
		
		// call the method with the correct information
		orderServiceImpl.setOrderStatus(3, "testEmployee", "Test Status");
		
		// verify that the dao method was called with the correct information
		Mockito.verify(orderDaoImpl).updateOrder(3, dummyOrder3);
	}
	
	@Test
	public void testcancelOrder() {
		// call the method with incorrect information
		orderServiceImpl.cancelOrder(2, "testEmployee", "Test Notes");
		
		// verify that the dao method was not called
		Mockito.verify(orderDaoImpl, never()).updateOrder(2, dummyOrder2);
		
		// call the method with the correct information
		orderServiceImpl.cancelOrder(4, "testEmployee", "Test Notes");
		
		// verify that the dao method was called with the correct information
		Mockito.verify(orderDaoImpl).updateOrder(4, dummyOrder4);
	}
	
	@Test
	public void testDeleteOrder() {
		// call the method with incorrect information
		orderServiceImpl.deleteOrder(2);
		
		// verify that the dao method was not called
		Mockito.verify(orderDaoImpl, never()).deleteOrder(2);
		
		// call the method with the correct information
		orderServiceImpl.deleteOrder(1);
		
		// verify that the dao method was called with the correct information
		Mockito.verify(orderDaoImpl).deleteOrder(1);
	}
	
	@Test(groups = "before-new-order")
	public void testGetStats() {
		
	}
}
