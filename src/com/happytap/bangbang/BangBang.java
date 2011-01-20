package com.happytap.bangbang;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.happytap.bangbang.bluetooth.AcceptThread;

import java.io.IOException;
import java.util.*;

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

    private Map<String,BluetoothDevice> deviceMap = new HashMap<String,BluetoothDevice>();

    private boolean isServer;

    private BangBangClient client;

    private BangBangServer server;

    private Set<BluetoothDeviceListener> deviceListeners = new LinkedHashSet<BluetoothDeviceListener>(1);

    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket;

    public void connect(BluetoothDevice device) throws IOException {
        try {
            bluetoothDevice = device;
            bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            client = new BangBangClient(bluetoothSocket.getInputStream(),bluetoothSocket.getOutputStream(),this);
        } catch (IOException ex) {
          if(bluetoothSocket!=null) {
                try {
                    bluetoothSocket.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(),"Could not close bluetoothSocket",e);
                }
            }
        }
    }

    public void receiveConnection(BluetoothSocket socket) throws IOException {
        this.bluetoothSocket = socket;
        this.bluetoothDevice = socket.getRemoteDevice();
        server = new BangBangServer(bluetoothSocket.getInputStream(), bluetoothSocket.getOutputStream(), this);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(getClass().getSimpleName(), "mReceiver_action:"+ action);
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceMap.put(device.getAddress(),device);
                onDeviceFound(device);

            }
        }
    };

    public BroadcastReceiver getReceiver() {
        return mReceiver;
    }

    public void addDeviceListener(BluetoothDeviceListener listener) {
        deviceListeners.add(listener);
    }

    public void removeDeviceListener(BluetoothDeviceListener listener) {
        deviceListeners.remove(listener);
    }

    private void onDeviceFound(BluetoothDevice device) {
        for(BluetoothDeviceListener listener : deviceListeners) {
            listener.onFound(deviceMap,device);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        adapter = BluetoothAdapter.getDefaultAdapter();
        uuid = UUID.fromString(getString(R.string.bluetooth_uuid));
    }


    private static final String USERNAME = "username";

    private static final String PREFERENCES = "preferences";

    public void setUsername(String username) {
        setProperty(USERNAME,username);
    }

    public String getUsername() {
        return getProperty(USERNAME);
    }

    private void setProperty(String property, String value) {
        getEditor().putString(property,value).commit();
    }

    private String getProperty(String property) {
        return getSharedPreferences().getString(property,null);
    }

    private SharedPreferences.Editor getEditor() {
        return getSharedPreferences(PREFERENCES, Activity.MODE_PRIVATE).edit();
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences(PREFERENCES,Activity.MODE_PRIVATE);
    }



    public void start() {
        if (acceptThread==null) {
            acceptThread = new AcceptThread(this);
            acceptThread.start();
        }
        getAdapter().enable();
        getAdapter().startDiscovery();
        for(BluetoothDevice device : getAdapter().getBondedDevices()) {
            deviceMap.put(device.getAddress(),device);
            onDeviceFound(device);
        }
    }
}
