package com.happytap.bangbang.activity;

import android.app.Activity;
import com.happytap.bangbang.BangBang;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 12/10/10
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class BangBangActivity extends Activity {

    public BangBang getBangBang() {
        return (BangBang)super.getApplication();
    }

}
