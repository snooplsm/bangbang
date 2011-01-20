package com.happytap.bangbang;

import android.bluetooth.BluetoothDevice;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 1/1/11
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BluetoothDeviceListener {

    void onFound(Map<String,BluetoothDevice> devices, BluetoothDevice device);

}
