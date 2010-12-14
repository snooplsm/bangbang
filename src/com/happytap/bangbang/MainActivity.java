package com.happytap.bangbang;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import com.happytap.bangbang.activity.BangBangActivity;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: Dec 7, 2010
 * Time: 5:52:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends BangBangActivity {
    /**
     * Called when the activity is first created.
     */

    private static final int REQUEST_ENABLE_BT = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if(!getBangBang().getAdapter().isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
        case REQUEST_ENABLE_BT:
            if (resultCode == Activity.RESULT_OK) {
                getBangBang().start();
            } else {
                // User did not enable Bluetooth or an error occured
                //Log.d(TAG, "BT not enabled");
                //Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    }
}
