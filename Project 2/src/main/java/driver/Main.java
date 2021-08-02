package driver;

import frontcontroller.FrontController;
import io.javalin.Javalin;

public class Main {
	public static void main(String[] args) {
		Javalin app = Javalin.create(config -> {
			config.addStaticFiles("/static/");
		}).start(9001);
		
		
		new FrontController(app);
	}
}
