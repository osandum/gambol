package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.*;

import play.*;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.Response;
import play.mvc.Http.Session;
import play.libs.Json;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;

import views.html.*;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.util.ISO8601DateFormat;
import static play.mvc.Controller.request;

public class Application extends Controller {

	public static final String FLASH_MESSAGE_KEY = "message";
	public static final String FLASH_ERROR_KEY = "error";
	public static final String USER_ROLE = "user";

	public static Result index() {
		return ok(index.render("Your new application is ready.", TeamController.teamForm));
	}

	public static User getLocalUser(final Session session) {
		final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
		final User localUser = User.findByAuthUserIdentity(currentAuthUser);
		return localUser;
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result restricted() {
		final User localUser = getLocalUser(session());
		return ok(restricted.render(localUser));
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result profile() {
		final User localUser = getLocalUser(session());
		return ok(profile.render(localUser));
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result events() throws IOException {
        long t1 = Long.valueOf(request().getQueryString("start")) * 1000L;
        long t2 = Long.valueOf(request().getQueryString("end")) * 1000L;

        Logger.info("events from " + new Date(t1) + " to " + new Date(t2));

		final User localUser = getLocalUser(session());
        Set<Event> res = new HashSet<Event>();
        for (TeamPlayer tp : localUser.teams)
            res.addAll(Event.forParty(tp.party, new Date(t1), new Date(t2)));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.setDateFormat( new ISO8601DateFormat() );
 		return ok(mapper.writeValueAsString(res.toArray())).as("application/json; charset=utf-8");

	//	return ok(Json.toJson(res.toArray()));
    }

	public static Result login() {
		return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));
	}

	public static Result doLogin() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<MyLogin> filledForm = MyUsernamePasswordAuthProvider.LOGIN_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			return badRequest(login.render(filledForm));
		} else {
			// Everything was filled
			return UsernamePasswordAuthProvider.handleLogin(ctx());
		}
	}

	public static Result signup() {
		return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM));
	}

	public static Result jsRoutes() {
		return ok(
				Routes.javascriptRouter("jsRoutes",
						controllers.routes.javascript.Signup.forgotPassword()))
				.as("text/javascript");
	}

	public static Result doSignup() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<MySignup> filledForm = MyUsernamePasswordAuthProvider.SIGNUP_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			return badRequest(signup.render(filledForm));
		} else {
			// Everything was filled
			// do something with your part of the form before handling the user
			// signup
			return UsernamePasswordAuthProvider.handleSignup(ctx());
		}
	}

	public static String formatTimestamp(final long t) {
		return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(new Date(t));
	}

    public static Result clubIndex(String clubSlug) {
        OrgUnit c = OrgUnit.findBySlug(clubSlug);
        if (c == null)
            return notFound(clubSlug);
        return ok(views.html.club.summary.render(c));
    }

    public static Result teamIndex(String clubSlug, String teamSlug) {
        OrgUnit c = OrgUnit.findBySlug(clubSlug);
        if (c == null)
            return notFound(clubSlug);
        final OrgUnit t = OrgUnit.findBySlug(c, teamSlug);
        if (t == null)
            return notFound(teamSlug);

        return ok(views.html.team.summary.render(t));
        /*
         F.Promise<play.mvc.Result> promise =
         WS.url(String.valueOf(t.rssFeed)).get().map(
         new Function<WS.Response, Result>() {
         public Result apply(WS.Response response) {
         return ok(team.render(t, response.asJson()));
         }
         }
         );

         return async(promise);
         */
    }

    public static Result teamSearch() {
        return notFound("Not yet implemented");
    }

	@Restrict(@Group(Application.USER_ROLE))
    public static Result myTeamIndex() {
		final User localUser = getLocalUser(session());

        for (TeamPlayer tp : localUser.teams)
            return ok(views.html.team.summary.render(tp.party));

        return notFound();
    }
}