package com.happytap.bangbang;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import com.happytap.bangbang.bluetooth.AcceptThread;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 12/9/10
 * Time: 11:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class BangBang extends Application {

    public UUID getUuid() {
        return uuid;
    }

    private UUID uuid;

    public BluetoothAdapter getAdapter() {
        return adapter;
    }

    private BluetoothAdapter adapter;

    private AcceptThread acceptThread;

    @Override
    public void onCreate() {
        super.onCreate();
        adapter = BluetoothAdapter.getDefaultAdapter();
        uuid = UUID.fromString(getString(R.string.bluetooth_uuid));
    }



    public void start() {
        acceptThread = new AcceptThread(this);
        acceptThread.start();
    }
}
