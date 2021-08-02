package frontcontroller;

import controllers.UserController;
import controllers.OrderController;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class Dispatcher {
	public Dispatcher(Javalin app) {
		
		// User Endpoints
		// fetch a particular user /users/:id GET
		app.get("/users/id/:id", UserController::getUser);
		
		// fetch all users /users GET
		app.get("/users", UserController::getAll);
		
		// fetch all users for a given role /users/:role GET
		app.get("/users/role/:role", UserController::getAllByRole);
		
		// login for the customer portal /login/customer POST
		app.post("/login/customer", UserController::customerLogin);
		
		// login for the employee portal /login/employee POST
		app.post("/login/employee", UserController::employeeLogin);
		
		// logout (should work for customers and employees) /logout POST
		app.post("/logout", UserController::logout);
		
		//create new user /users POST
		app.post("/users", UserController::createUser);
		
		//delete user /users/:id DELETE
		app.delete("/users/:id", UserController::deleteUser);
		
		
		// Order Endpoints
		// fetch a particular order /orders/:id GET
		app.get("/orders/id/:id", OrderController::getOrder);
		
		// fetch all orders /orders GET
		app.get("/orders", OrderController::getAll);
		
		// fetch all orders for a particular customer /orders/customer/:id GET
		app.get("/orders/customer/:id", OrderController::getAllForCustomer);
		
		// fetch all pending orders /orders/pending GET
		app.get("/orders/pending", OrderController::getPending);
		
		// fetch all pending orders for a particular customer /orders/pending/:id GET
		app.get("/orders/pending/:id", OrderController::getPendingForCustomer);
		
		// fetch all completed orders /orders/completed GET
		app.get("/orders/completed", OrderController::getCompleted);
		
		// fetch all completed orders for a particular customer /orders/completed/:id GET
		app.get("/orders/completed/:id", OrderController::getCompletedForCustomer);
		
		// create a new order /orders POST
		app.post("/orders", OrderController::createOrder);
		
		// update an existing order /orders/:id POST
		app.post("/orders/:id", OrderController::updateOrder);
		
		// set the status of an order /orders/status/:id PATCH
		app.patch("/orders/status/:id", OrderController::setOrderStatus);
		
		// cancel order /orders/cancel/:id PATCH
		app.patch("/orders/cancel/:id", OrderController::cancelOrder);
		
		// delete order /orders/:id DELETE
		app.delete("/orders/:id", OrderController::deleteOrder);
		
		// fetch the key order statistics /orders/stats GET
		app.get("/orders/stats", OrderController::getStats);
		
		
		// Endpoint that returns all session variables /session GET
		app.get("/session", (Context context) -> {
			context.json(context.sessionAttributeMap());
		});
	}
}