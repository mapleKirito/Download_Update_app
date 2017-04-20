package co.huiqu.webapp;

import android.app.Application;

import co.huiqu.webapp.config.SystemParams;

/**
 * Created by Song on 2016/11/2.
 */
public class A extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SystemParams.init(this);
    }
}
