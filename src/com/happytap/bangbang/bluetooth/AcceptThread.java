package com.happytap.bangbang.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import com.happytap.bangbang.BangBang;
import com.happytap.bangbang.R;

import java.io.IOException;
import java.util.UUID;

public class AcceptThread extends Thread {

    private final BluetoothServerSocket mmServerSocket;

    private final BluetoothAdapter mAdapter;

    private final BangBang mApplication;

    public AcceptThread(BangBang application) {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        mAdapter = application.getAdapter();
        mApplication = application;
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            UUID uuid = mApplication.getUuid();
            String sdp = mApplication.getResources().getString(R.string.bluetooth_sdp_name);
            tmp = mAdapter.listenUsingRfcommWithServiceRecord(
                       sdp,
                       uuid);
        } catch (IOException e) {
            Log.e("AcceptThread","listenUsingRfcommWithServiceRecord",e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                //manageConnectedSocket(socket);
                try {
                    socket.close();
                } catch (IOException e) {
                    Log.e(getClass().getSimpleName(), "close", e);
                }
                break;
            }
        }
    }

    /**
     * Will cancel the listening socket, and cause the thread to finish
     */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
        }
    }
}