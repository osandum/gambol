
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.ok;
import views.html.caldemo;

/**
 * @author      osa
 * @since       06-07-2013
 * @version     $Id: CalDemo.java -1 06-07-2013 17:00:08 osa $
 */
public class CalDemo extends Controller {

	public static Result index() {
		return ok(caldemo.render());
	}

}
