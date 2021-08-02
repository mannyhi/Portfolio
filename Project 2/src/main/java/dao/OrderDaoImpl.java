package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import dbconfig.ConnectionUtil;
import dbconfig.ResourceClosers;
import models.Order;

public class OrderDaoImpl implements OrderDao{

	// fetch a particular order
	public Order getOrder(Integer id) {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		Order order = null;
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM orders WHERE oid = '" + id + "'";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				order = new Order(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getBigDecimal(5),
						set.getTimestamp(6),
						set.getTimestamp(7),
						set.getString(8),
						set.getString(9));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return order;
	}

	// fetch all orders
	public Map<Integer, Order> getAll() {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		Map<Integer, Order> orders = new HashMap<Integer, Order>();
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM orders";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				Order order = new Order(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getBigDecimal(5),
						set.getTimestamp(6),
						set.getTimestamp(7),
						set.getString(8),
						set.getString(9));
				orders.put(set.getInt(1), order);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return orders;
	}

	//fetch all orders for a particular customer
	public Map<Integer, Order> getAll(String customerId) {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		Map<Integer, Order> orders = new HashMap<Integer, Order>();
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM orders WHERE uid = '" + customerId + "'";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				Order order = new Order(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getBigDecimal(5),
						set.getTimestamp(6),
						set.getTimestamp(7),
						set.getString(8),
						set.getString(9));
				orders.put(set.getInt(1), order);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return orders;
	}

	// fetch all pending orders
	public Map<Integer, Order> getPending() {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		Map<Integer, Order> orders = new HashMap<Integer, Order>();
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM orders WHERE ostatus <> 'completed' AND ostatus <> 'canceled'";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				Order order = new Order(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getBigDecimal(5),
						set.getTimestamp(6),
						set.getTimestamp(7),
						set.getString(8),
						set.getString(9));
				orders.put(set.getInt(1), order);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return orders;
	}

	// fetch all pending orders for a particular customer
	public Map<Integer, Order> getPending(String customerId) {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		Map<Integer, Order> orders = new HashMap<Integer, Order>();
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM orders WHERE ostatus <> 'completed' AND ostatus <> 'canceled' AND uid = '" + customerId + "'";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				Order order = new Order(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getBigDecimal(5),
						set.getTimestamp(6),
						set.getTimestamp(7),
						set.getString(8),
						set.getString(9));
				orders.put(set.getInt(1), order);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return orders;
	}

	// fetch all completed orders
	public Map<Integer, Order> getCompleted() {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		Map<Integer, Order> orders = new HashMap<Integer, Order>();
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM orders WHERE ostatus = 'completed'";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				Order order = new Order(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getBigDecimal(5),
						set.getTimestamp(6),
						set.getTimestamp(7),
						set.getString(8),
						set.getString(9));
				orders.put(set.getInt(1), order);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return orders;
	}

	// fetch all completed orders for a particular customer
	public Map<Integer, Order> getCompleted(String customerId) {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		Map<Integer, Order> orders = new HashMap<Integer, Order>();
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM orders WHERE ostatus = 'completed' AND uid = '" + customerId + "'";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				Order order = new Order(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getBigDecimal(5),
						set.getTimestamp(6),
						set.getTimestamp(7),
						set.getString(8),
						set.getString(9));
				orders.put(set.getInt(1), order);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return orders;
	}

	// create a new order
	public void createOrder(Order order) {
		// set up the resources we'll need
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "INSERT INTO orders VALUES(default, ?, 'order placed', ?, ?, now(), ?, ?, ?)";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, order.getCustomerId());
			stmt.setString(2, order.getItems());
			stmt.setBigDecimal(3, order.getTotal());
			stmt.setTimestamp(4, order.getTimeCompleted());
			stmt.setString(5, order.getEmployeeId());
			stmt.setString(6, order.getNotes());
			
			stmt.execute();

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
		}
	}

	// update an existing order
	public void updateOrder(Integer id, Order newOrder) {
		// set up the resources we'll need
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "UPDATE orders SET uid = ?, ostatus = ?, oitems = ?, ototal = ?, " +
					 			  "otime = ?, otc = ?, ocb = ?, onote = ? WHERE oid = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, newOrder.getCustomerId());
			stmt.setString(2, newOrder.getStatus());
			stmt.setString(3, newOrder.getItems());
			stmt.setBigDecimal(4, newOrder.getTotal());
			stmt.setTimestamp(5, newOrder.getTimePlaced());
			stmt.setTimestamp(6, newOrder.getTimeCompleted());
			stmt.setString(7, newOrder.getEmployeeId());
			stmt.setString(8, newOrder.getNotes());
			stmt.setInt(9, newOrder.getId());
			
			stmt.execute();

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
		}
	}

	// delete an order from the database
	public void deleteOrder(Integer id) {
		// set up the resources we'll need
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "DELETE FROM orders WHERE oid = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			
			stmt.execute();

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
		}
	}

	// this is a utility method used to get the id of a newly created order
	public Integer getNewOrderId(String customerId) {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		Integer result = null;
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM orders WHERE uid = '" + customerId + "'" +
								  " ORDER BY otime DESC LIMIT 1";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				result = set.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return result;
	}
	
	// fetch the key order statistics
	public Map<String, String> getStats(){
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		Map<String, String> result = new HashMap<String, String>();
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "select money(sum(case when ostatus <> 'canceled' and otime between '2021-01-01 00:00:00' and '2021-03-31  23:59:59' then (ototal::numeric) else 0 end)) as q1," +
								 " money(sum(case when ostatus <> 'canceled' and otime between '2021-04-01 00:00:00' and '2021-06-30  23:59:59' then (ototal::numeric) else 0 end)) as q2," +
								 " money(sum(case when ostatus <> 'canceled' and otime between '2021-07-01 00:00:00' and '2021-09-30  23:59:59' then (ototal::numeric) else 0 end)) as q3," +
								 " money(sum(case when ostatus <> 'canceled' and otime between '2021-10-01 00:00:00' and '2021-12-31  23:59:59' then (ototal::numeric) else 0 end)) as q4," +
								 " count(case when ostatus <> 'canceled' then oid else null end) as tno," +
								 " money(sum(case when ostatus <> 'canceled' then (ototal::numeric) else 0 end)) as tr from orders;";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				result.put("Q1 Revenue", "$" +set.getBigDecimal(1));
				result.put("Q2 Revenue", "$" +set.getBigDecimal(2));
				result.put("Q3 Revenue", "$" +set.getBigDecimal(3));
				result.put("Q4 Revenue", "$" +set.getBigDecimal(4));
				result.put("Number of Orders", String.valueOf(set.getInt(5)));
				result.put("Total Revenue", "$" +set.getBigDecimal(6));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return result;
	}

}
