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
import models.User;

public class UserDaoImpl implements UserDao{
	
	public UserDaoImpl() {

	}

	// fetch a particular user
	public User getUser(String id) {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		User user = null;
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM users WHERE uid = '" + id + "'";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				user = new User(
						set.getString(1),
						set.getString(2),
						set.getString(3),
						set.getString(4));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return user;
	}
	
	// fetch all users
	public Map<Integer, User> getAll() {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		Map<Integer, User> users = new HashMap<Integer, User>();
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM users";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			int ctr = 1;
			
			while(set.next()) {
				User user = new User(
						set.getString(1),
						set.getString(2),
						set.getString(3),
						set.getString(4));
				users.put(ctr, user);
				ctr ++;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return users;
	}
	
	// fetch all users for a given role
	public Map<Integer, User> getAll(String role) {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		Map<Integer, User> users = new HashMap<Integer, User>();
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM users WHERE urole = '" + role + "'";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			int ctr = 1;
			
			while(set.next()) {
				User user = new User(
						set.getString(1),
						set.getString(2),
						set.getString(3),
						set.getString(4));
				users.put(ctr, user);
				ctr ++;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return users;
	}
	
	// login for the customer portal
	public User customerLogin(String id, String password) {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		User user = null;
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM users WHERE urole = 'customer' AND uid = '" + id + "' AND upassword = '" + password + "'";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				user = new User(
						set.getString(1),
						set.getString(2),
						set.getString(3),
						set.getString(4));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return user;
	}
	
	// login for the employee portal (should work for both employees and managers)
	public User employeeLogin(String id, String password) {
		// set up the resources we'll need
		Connection conn = null;
		Statement stmt = null;
		ResultSet set = null;
		User user = null;
		
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "SELECT * FROM users WHERE (urole = 'employee' OR urole = 'manager') AND uid = '" + id + "' AND upassword = '" + password + "'";
			stmt = conn.createStatement();
			
			set = stmt.executeQuery(query);
			
			while(set.next()) {
				user = new User(
						set.getString(1),
						set.getString(2),
						set.getString(3),
						set.getString(4));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		
		return user;
	} 
	
	// create a new user (should work for all roles)
	public void createUser(User user) {
		// set up the resources we'll need
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "INSERT INTO users VALUES(?, ?, ?, ?)";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, user.getId());
			stmt.setString(2, user.getRole());
			stmt.setString(3, user.getName());
			stmt.setString(4, user.getPassword());
			
			stmt.execute();

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
		}
		
	}
	
	// delete a user from the database
	public void deleteUser(String id) {
		// set up the resources we'll need
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			
			final String query = "DELETE FROM users WHERE uid = '" + id + "'";
			stmt = conn.prepareStatement(query);
			
			stmt.execute();

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// close all our resources
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
		}
	}
}
