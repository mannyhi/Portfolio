package testng;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import models.User;
import dao.UserDaoImpl;
import service.UserServiceImpl;

public class UserServiceTest {
	
	@Mock
	public UserDaoImpl userDaoImpl;
	
	@InjectMocks
	public UserServiceImpl userServiceImpl;
	
	@SuppressWarnings("deprecation")
	@BeforeTest
	public void initMocks() {
	    MockitoAnnotations.initMocks(this);
	}
	
	// initialize our mock return values
	public static User dummyUser;
	public static Map<Integer, User> dummyUsers;
	public static Map<Integer, User> dummyCustomers;
	public static User dummyCustomer;
	public static User dummyEmployee;
	public static User dummyManager;
	
	@BeforeClass
	public void beforeClassSetup() {
		MockitoAnnotations.openMocks(UserServiceTest.class);
		
		// set the values of variables to be returned
		dummyUser = new User("testUser", "customer", "Test User", "password1");
		dummyCustomer = new User("testCustomer", "customer", "Test Customer", "password1");
		dummyEmployee = new User("testEmployee", "employee", "Test Employee", "password1");
		dummyManager = new User("testManager", "manager", "Test Manager", "password1");
		dummyUsers = new HashMap<>();
			dummyUsers.put(1, dummyUser);
			dummyUsers.put(2, dummyCustomer);
			dummyUsers.put(3, dummyEmployee);
			dummyUsers.put(4, dummyManager);
		dummyCustomers = new HashMap<>();
			dummyCustomers.put(1, dummyUser);
			dummyCustomers.put(2, dummyCustomer);
		
		// set the return values for our mock dao methods
		Mockito.when(userDaoImpl.getUser("testUser")).thenReturn(dummyUser);
		Mockito.when(userDaoImpl.getUser("testCustomer")).thenReturn(null);
		Mockito.when(userDaoImpl.getAll()).thenReturn(dummyUsers);
		Mockito.when(userDaoImpl.getAll("customer")).thenReturn(dummyCustomers);
		Mockito.when(userDaoImpl.customerLogin("testCustomer", "password1")).thenReturn(dummyCustomer);
		Mockito.when(userDaoImpl.customerLogin("testCustomer", "wrong password")).thenReturn(null);
		Mockito.when(userDaoImpl.customerLogin("wrong userId", "password1")).thenReturn(null);
		Mockito.when(userDaoImpl.employeeLogin("testEmployee", "password1")).thenReturn(dummyEmployee);
		Mockito.when(userDaoImpl.employeeLogin("testManager", "password1")).thenReturn(dummyManager);
		Mockito.when(userDaoImpl.employeeLogin("testEmployee", "wrong password")).thenReturn(null);
		Mockito.when(userDaoImpl.employeeLogin("wrong userId", "password1")).thenReturn(null);
	}
	
	@Test
	public void testGetUser() {
		// execute the method
		User result = userServiceImpl.getUser("testUser");
		
		// verify that it returned all the correct information
		assert result.getClass() == User.class;
		assert result.getId().equals(dummyUser.getId());
		assert result.getRole().equals(dummyUser.getRole());
		assert result.getName().equals(dummyUser.getName());
		assert result.getPassword().equals(dummyUser.getPassword());
	}
	
	@Test
	public void testGetAll() {
		// execute the method
		Map<Integer, User> results = userServiceImpl.getAll();
		
		// verify that it returned the four users with all the correct information
		assert results.size() == 4;
		results.forEach((key, user) -> {
			assert user.getClass() == User.class;
			assert user.getPassword().equals("password1");
			switch(key) {
				case 1: 
					assert user.getId().equals(dummyUser.getId());
					assert user.getName().equals(dummyUser.getName());
					assert user.getRole().equals(dummyUser.getRole());
					break;
				case 2: 
					assert user.getId().equals(dummyCustomer.getId());
					assert user.getName().equals(dummyCustomer.getName());
					assert user.getRole().equals(dummyCustomer.getRole());
					break;
				case 3: 
					assert user.getId().equals(dummyEmployee.getId());
					assert user.getName().equals(dummyEmployee.getName());
					assert user.getRole().equals(dummyEmployee.getRole());
					break;
				case 4: 
					assert user.getId().equals(dummyManager.getId());
					assert user.getName().equals(dummyManager.getName());
					assert user.getRole().equals(dummyManager.getRole());
					break;
			}
		});
	}
	
	@Test
	public void testGetAllForRole() {
		// execute the method
		Map<Integer, User> results = userServiceImpl.getAll("customer");
		
		// verify that it returned the two customers with all the correct information
		assert results.size() == 2;
		results.forEach((key, user) -> {
			assert user.getClass() == User.class;
			assert user.getPassword().equals("password1");
			switch(key) {
				case 1: 
					assert user.getId().equals(dummyUser.getId());
					assert user.getName().equals(dummyUser.getName());
					assert user.getRole().equals(dummyUser.getRole());
					break;
				case 2: 
					assert user.getId().equals(dummyCustomer.getId());
					assert user.getName().equals(dummyCustomer.getName());
					assert user.getRole().equals(dummyCustomer.getRole());
					break;
			}
		});
	}
	
	@Test
	public void testCustomerLogin() {
		// verify that the method returns the customer when called with the correct information
		assert userServiceImpl.customerLogin("testCustomer", "password1").getId().equals(dummyCustomer.getId());
		
		// verify that the method returns null when called with the wrong password
		assert userServiceImpl.customerLogin("testCustomer", "wrong password") == null;
		
		// verify that the method returns null when called with the wrong userId
		assert userServiceImpl.customerLogin("wrong userId", "password1") == null;
	}
	
	@Test
	public void testEmployeeLogin() {
		// verify that the method returns the employee when called with the correct information
		assert userServiceImpl.employeeLogin("testEmployee", "password1").getId().equals(dummyEmployee.getId());
		
		// verify that the method returns the manager when called with the correct information
		assert userServiceImpl.employeeLogin("testManager", "password1").getId().equals(dummyManager.getId());
		
		// verify that the method returns null when called with the wrong password
		assert userServiceImpl.employeeLogin("testEmployee", "wrong password") == null;
		
		// verify that the method returns null when called with the wrong userId
		assert userServiceImpl.employeeLogin("wrong userId", "password1") == null;
	}
	
	@Test
	public void testCreateUser() {
		// call the method
		userServiceImpl.createUser(dummyCustomer);
		
		// verify that the dao method was called with the correct information
		Mockito.verify(userDaoImpl).createUser(dummyCustomer);
	}
	
	@Test
	public void deleteUser() {
		// call the method
		userServiceImpl.deleteUser(dummyUser.getId());
		
		// verify that the dao method was called with the correct information
		Mockito.verify(userDaoImpl).deleteUser(dummyUser.getId());
	}
}
