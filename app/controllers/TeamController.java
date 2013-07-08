package controllers;

import java.util.Map;
import models.OrgUnit;
import play.api.templates.Html;
import play.data.Form;
import play.mvc.Controller;
import static play.data.Form.*;
import play.mvc.Result;
import views.html.team.*;

/**
 * @author      osa
 * @since       09-06-2013
 * @version     $Id: TeamController.java -1 09-06-2013 21:37:57 osa $
 */
public class TeamController extends Controller {
    final static Form<models.OrgUnit> teamForm = form(models.OrgUnit.class);

    public static Result newTeam() {
        Map<String, String> clubOpts = OrgUnit.allClubsOptions();
        Html res = form.render("Nyt hold?!", teamForm, views.html.helper.options.apply(clubOpts));
        return ok(res);
    }

    public static Result createTeam() {
        Form<models.OrgUnit> filledForm = teamForm.bindFromRequest();

        if(filledForm.hasErrors()) {
            Map<String, String> clubOpts = OrgUnit.allClubsOptions();
            return badRequest(form.render("Nyt hold?!", filledForm, views.html.helper.options.apply(clubOpts)));
        } else {
            OrgUnit newTeam = filledForm.get();
            return ok(summary.render(newTeam));
        }
    }
}
