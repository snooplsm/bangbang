package com.happytap.bangbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.happytap.bangbang.BangBang;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 12/10/10
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class BangBangActivity extends Activity {

    private GoogleAnalyticsTracker tracker;

    // DETERMINE BY ORDER THE PAGE VIEWS
    private static final int VIEW_COUNT = 1;


    public BangBang getBangBang() {
        return (BangBang)super.getApplication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        tracker = GoogleAnalyticsTracker.getInstance();
//        try {
//            PackageInfo info = getApplication().getPackageManager().getPackageInfo(getPackageName(),0);
//            tracker.setProductVersion(info.packageName, "" + info.versionCode);
//            tracker.start(getString(R.string.google_ua),this);
//            tracker.setDispatchPeriod(60);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        trackPage(getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //If there was an exception during creation tracker could be null
        if(tracker!=null) {
            tracker.stop();;
        }
    }

    protected void trackPage(String page) {
        tracker.trackPageView(page);
        tracker.trackEvent("page","view", (page+"/").replaceAll("//","/")+Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), VIEW_COUNT);
    }

    protected void trackEvent(String category, String action, String label, int value) {
        tracker.trackEvent(category,action,label,value);
    }
}
