import com.avaje.ebean.Ebean;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.PlayAuthenticate.Resolver;
import com.feth.play.module.pa.exceptions.AccessDeniedException;
import com.feth.play.module.pa.exceptions.AuthException;

import controllers.routes;
import java.util.List;
import java.util.Map;
import models.OrgUnit;
import models.User;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Yaml;
import play.mvc.Call;



/**
 * @author      osa
 * @since       02-06-2013
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        Logger.info("Starting");

		PlayAuthenticate.setResolver(new Resolver() {

			@Override
			public Call login() {
				// Your login page
				return routes.Application.login();
			}

			@Override
			public Call afterAuth() {
				// The user will be redirected to this page after authentication
				// if no original URL was saved
				return routes.Application.index();
			}

			@Override
			public Call afterLogout() {
				return routes.Application.index();
			}

			@Override
			public Call auth(final String provider) {
				// You can provide your own authentication implementation,
				// however the default should be sufficient for most cases
				return com.feth.play.module.pa.controllers.routes.Authenticate
						.authenticate(provider);
			}

			@Override
			public Call askMerge() {
				return routes.Account.askMerge();
			}

			@Override
			public Call askLink() {
				return routes.Account.askLink();
			}

			@Override
			public Call onException(final AuthException e) {
				if (e instanceof AccessDeniedException) {
					return routes.Signup
							.oAuthDenied(((AccessDeniedException) e)
									.getProviderKey());
				}

				// more custom problem handling here...
				return super.onException(e);
			}
		});

		initialData();
    }

    @Override
    public void onStop(Application app) {
        Logger.info("Stopped");
    }


	private void initialData() {
/*		if (SecurityRole.find.findRowCount() == 0) {
			for (final String roleName : Arrays
					.asList(controllers.Application.USER_ROLE)) {
				final SecurityRole role = new SecurityRole();
				role.roleName = roleName;
				role.save();
			}
		}
*/
        if (User.find.findRowCount() == 0) {
            Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data.yml");

            Ebean.save(all.get("securityRoles"));

            Ebean.save(all.get("users"));

            Ebean.save(all.get("clubs"));
            Ebean.save(all.get("teams"));

            Ebean.save(all.get("players"));

            Ebean.save(all.get("events"));

//            for (Object close: all.get("users")) {
//                Ebean.saveManyToManyAssociations(close, "friends");
        }

        for (OrgUnit u : OrgUnit.find.all()) {
            u.path = OrgUnit.getPath(u);
            u.save();
        }

    }
}
