package br.com.reymond.lawrence.oqrola.activity;

import android.app.Activity;
import android.app.Application;

/**
 * Created by ian on 03-Nov-16.
 */

public class BoradcastReceiverApplication extends Application {
    private Activity currentActivity = null;

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity act) {
        currentActivity = act;
    }
}