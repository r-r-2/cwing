package info.androidhive.slidingmenu.model;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(getApplicationContext(), "emZ0b7JfFnd2Dt1jHvGkqtcS9XBcnqfs5UAszsE3", "lxbVihNI9Z6Ded2Odm3f2lMTeC57u5mwvNzz1GnY");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
