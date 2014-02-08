package main;

import play.Application;
import play.GlobalSettings;

/**
 * Created by Alexandru Grigoroi on 08/02/14.
 */
public class Global extends GlobalSettings {
    public static Mongo mongo;

    @Override
    public void onStart(Application application) {
        try {
            mongo = new Mongo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart(application);
    }
}
