package controllers;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import static controllers.Application.getLocalUser;
import static controllers.TeamController.teamForm;
import java.util.Date;
import java.util.Map;
import models.OrgUnit;
import models.User;
import play.Logger;
import play.api.templates.Html;
import play.data.Form;
import play.mvc.Controller;
import static play.data.Form.*;
import play.mvc.Result;
import views.html.club.*;

/**
 * @author      osa
 * @since       16-06-2013
 * @version     $Id: ClubController.java -1 16-06-2013 12:06:21 osa $
 */
public class ClubController extends Controller {

        final static Form<models.OrgUnit> clubForm = form(models.OrgUnit.class);

	@Restrict(@Group(Application.USER_ROLE))
    public static Result newClub() {
        Html res = form.render("Ny klub?!", clubForm);
        return ok(res);
    }

	@Restrict(@Group(Application.USER_ROLE))
    public static Result createClub() {
        Form<models.OrgUnit> filledForm = clubForm.bindFromRequest();

        if (filledForm.hasErrors())
            return badRequest(form.render("Ny klub?!", filledForm));

        OrgUnit newClub = filledForm.get();

        newClub.created = new Date();
		newClub.createdBy = getLocalUser(session());

        newClub.save();

        Logger.info("new club: " + newClub);

        return ok(summary.render(newClub));
    }

    public static Result newMember(String clubSlug) {
        Html res = form.render("Ny klub?!", clubForm);
        return ok(res);
    }

    public static Result createMember(String clubSlug) {
        Html res = form.render("Ny klub?!", clubForm);
        return ok(res);
    }


}
