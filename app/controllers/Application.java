package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
	public static final String FLASH_MESSAGE_KEY = "message";
	public static final String FLASH_ERROR_KEY = "error";
	public static final String USER_ROLE = "user";

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}
  
}
