package testng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import dao.UserDaoImpl;
import dbconfig.ConnectionUtil;
import dbconfig.ResourceClosers;
import models.User;

public class UserDaoTest {

	MockedStatic<ConnectionUtil> connectionUtil = Mockito.mockStatic(ConnectionUtil.class);
	MockedStatic<ResourceClosers> resourceClosers = Mockito.mockStatic(ResourceClosers.class);
	
	@InjectMocks
	public UserDaoImpl userDaoImpl;
	
	@BeforeTest
	public void beforeTestSetup() throws SQLException {
		// initialize our mocks
		MockitoAnnotations.openMocks(this);
		
		// set up our mock database connection
		Connection mockConnection = DriverManager.getConnection("jdbc:postgresql://localhost/", "postgres", "password");
		
		// return our mock connection whenever a method tries to access the database
		connectionUtil.when(() -> ConnectionUtil.getConnection()).thenReturn(mockConnection);
	}
	
	@AfterTest
	public void afterTestTeardown() {
		// close out mocks
		connectionUtil.close();
		resourceClosers.close();
	}
	
	@Test(groups = "before-new-user")
	public void testGetUser() {
		// use the method to fetch a user
		User result = userDaoImpl.getUser("testEmployee");
		
		// verify that the method returned the correct user
		assert result.getClass() == User.class;
		assert result.getId().equals("testEmployee");
		assert result.getRole().equals("employee");
		assert result.getName().equals("Test Employee");
		assert result.getPassword().equals("password1");
	}
	
	@Test(groups = "before-new-user")
	public void testGetAll() {
		// use the method to fetch all the users
		Map<Integer, User> result = userDaoImpl.getAll();
		
		// verify that the method returned all three users with the correct information
		assert result.size() == 3;
		result.forEach((key, user) -> {
			assert user.getClass() == User.class;
			if(user.getId().equals("testManager")) {
				assert user.getId().equals("testManager");
				assert user.getRole().equals("manager");
				assert user.getName().equals("Test Manager");
				assert user.getPassword().equals("password1");
			} else if(user.getId().equals("testEmployee")) {
				assert user.getId().equals("testEmployee");
				assert user.getRole().equals("employee");
				assert user.getName().equals("Test Employee");
				assert user.getPassword().equals("password1");
			}else {
				assert user.getId().equals("testCustomer");
				assert user.getRole().equals("customer");
				assert user.getName().equals("Test Customer");
				assert user.getPassword().equals("password1");
			}
		});
	}
	
	@Test(groups = "before-new-user")
	public void testGetAllForRole() {
		// use the method to fetch all users who are managers
		Map<Integer, User> result = userDaoImpl.getAll("manager");
		
		// verify the method only returned the correct user
		assert result.size() == 1;
		User user = result.get(1);
		assert user.getClass() == User.class;
		assert user.getId().equals("testManager");
		assert user.getRole().equals("manager");
		assert user.getName().equals("Test Manager");
		assert user.getPassword().equals("password1");
		
		// use the method to fetch all users who are employees
		result = userDaoImpl.getAll("employee");
		
		// verify the method only returned the correct user
		assert result.size() == 1;
		user = result.get(1);
		assert user.getClass() == User.class;
		assert user.getId().equals("testEmployee");
		assert user.getRole().equals("employee");
		assert user.getName().equals("Test Employee");
		assert user.getPassword().equals("password1");
		
		// use the method to fetch all users who are customers
		result = userDaoImpl.getAll("customer");
		
		// verify the method only returned the correct user
		assert result.size() == 1;
		user = result.get(1);
		assert user.getClass() == User.class;
		assert user.getId().equals("testCustomer");
		assert user.getRole().equals("customer");
		assert user.getName().equals("Test Customer");
		assert user.getPassword().equals("password1");
	}
	
	@Test(groups = "before-new-user")
	public void testCustomerLogin() {
		// call the login method with the correct credentials for a customer
		User result = userDaoImpl.customerLogin("testCustomer", "password1");
		
		// verify that all of the returned information is correct
		assert result.getId().equals("testCustomer");
		assert result.getRole().equals("customer");
		assert result.getName().equals("Test Customer");
		assert result.getPassword().equals("password1");
		
		// verify that the method returns a null value when called with incorrect information
		assert userDaoImpl.customerLogin("testCustomer", "wrong password") == null;
	}
	
	@Test(groups = "before-new-user")
	public void testEmployeeLogin() {
		// call the login method with the correct credentials for an employee
		User result = userDaoImpl.employeeLogin("testEmployee", "password1");
		
		// verify that all of the returned information is correct
		assert result.getId().equals("testEmployee");
		assert result.getRole().equals("employee");
		assert result.getName().equals("Test Employee");
		assert result.getPassword().equals("password1");
		
		// call the login method with the correct credentials for a manager
		result = userDaoImpl.employeeLogin("testManager", "password1");
		
		// verify that all of the returned information is correct
		assert result.getId().equals("testManager");
		assert result.getRole().equals("manager");
		assert result.getName().equals("Test Manager");
		assert result.getPassword().equals("password1");
		
		// verify that the method returns a null value when called with incorrect information
		assert userDaoImpl.customerLogin("testEmployee", "wrong password") == null;
	}
	
	@Test(dependsOnGroups = "before-new-user")
	public void testCreateUser() {
		// set up a new user
		User testUser = new User("testUser", "manager", "Test User", "password");
		
		// verify that the user doesn't already exist
		assert userDaoImpl.getUser(testUser.getId()) == null;
		
		// add the user to the test database
		userDaoImpl.createUser(testUser);
		
		// verify that the user now exists and has all the correct information
		User result = userDaoImpl.getUser(testUser.getId());
		assert result.getClass() == User.class;
		assert result.getId().equals(testUser.getId());
		assert result.getRole().equals(testUser.getRole());
		assert result.getName().equals(testUser.getName());
		assert result.getPassword().equals(testUser.getPassword());
		
	}
	
	@Test(dependsOnMethods = "testCreateUser")
	public void testDeleteUser() {
		// verify that the user currently exists
		assert userDaoImpl.getUser("testUser").getClass() == User.class;
		
		// delete the user from the test database
		userDaoImpl.deleteUser("testUser");
		
		// verify that the user no longer exists
		assert userDaoImpl.getUser("testUser") == null;
	}
}
