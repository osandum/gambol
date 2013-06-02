
import play.Application;
import play.GlobalSettings;
import play.Logger;


/**
 * @author      osa
 * @since       02-06-2013
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        Logger.info("Starting");
    }

    @Override
    public void onStop(Application app) {
        Logger.info("Stopped");
    }

}
