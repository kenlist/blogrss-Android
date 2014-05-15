package com.kenlist.blogrss;

import android.app.Application;
import org.blogrsssdk.*;
import org.chromium.base.*;

public class BlogRSSApplication extends Application {
    private static final String PRIVATE_DATA_DIRECTORY_SUFFIX = "blogrss";
    
    @Override
    public void onCreate() {
        super.onCreate();
        PathUtils.setPrivateDataDirectorySuffix(PRIVATE_DATA_DIRECTORY_SUFFIX);
        BlogRSSSDK.getInstance().start(this.getApplicationContext());
    }
    
    @Override
    public void onTerminate() {
        super.onTerminate();
        BlogRSSSDK.getInstance().stop();
    }
}
