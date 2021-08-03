package frontcontroller;

import controllers.UserController;
import io.javalin.Javalin;

public class FrontController {
	Javalin app;
	Dispatcher dispatcher;

	public FrontController(Javalin app) {
		this.app = app;

		// login for the employee/manager portal
		app.post("/logins", ctx -> {
			if (UserController.employeeLogin(ctx).equals("employee")) {
				ctx.redirect("http://localhost:9000/fcce.html");
			} else if (UserController.employeeLogin(ctx).equals("manager")) {
				ctx.redirect("http://localhost:9000/fccm.html");
			} else {
				ctx.redirect("http://localhost:9000/fcc.html");
			}
		});

		// log off for the employee/manager portal
		app.post("/logoff", ctx -> {
			UserController.logout(ctx);
			ctx.redirect("http://localhost:9000/fcc.html");
		});

		// check for invalid login
		app.get("/invalid", ctx -> {
			if (ctx.sessionAttribute("currentUser") != null) {
				String login_status = ctx.sessionAttribute("currentUser");
				ctx.sessionAttribute("currentUser", null);
				ctx.json(login_status);
			} else {
				ctx.json("reset");
			}
		});
		this.dispatcher = new Dispatcher(app);

		// login verification for manager page
		app.before("/fccm.html", ctx -> {
			if (ctx.sessionAttribute("userRole") == null || !ctx.sessionAttribute("userRole").equals("manager")) {
				// redirect the user to the login page if they're not logged in as a manager
				ctx.sessionAttribute("currentUser", "not manager");
				ctx.redirect("http://localhost:9000/fcc.html");
			}
		});

		// login verification for employee page
		app.before("/fcce.html", ctx -> {
			if (ctx.sessionAttribute("userRole") == null || !ctx.sessionAttribute("userRole").equals("employee")) {
				// redirect the user to the login page if they're not logged in as a manager
				ctx.sessionAttribute("currentUser", "not employee");
				ctx.redirect("http://localhost:9000/fcc.html");
			}
		});
	}
}
