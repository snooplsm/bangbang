package com.happytap.bangbang;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.happytap.bangbang.activity.BangBangActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: Dec 7, 2010
 * Time: 5:52:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends BangBangActivity implements BluetoothDeviceListener {
    /**
     * Called when the activity is first created.
     */

    private static final int REQUEST_ENABLE_BT = 2;

    private TextView deviceName;

    private ListView devices;

    private Button scanDevices;

    private ArrayAdapter<BluetoothDevice> devicesAdapter;

    private static final int REQUEST_DURATION = 300;

    private void start() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

        registerReceiver(getBangBang().getReceiver(), filter);
        getBangBang().addDeviceListener(this);
        getBangBang().start();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        deviceName = (TextView)findViewById(R.id.phone_number);
        scanDevices = (Button)findViewById(R.id.scan_devices);
        scanDevices.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                enableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            }
        });
        deviceName.setText("Your device's name is: " + getBangBang().getUsername() + " and your mac address is " + getBangBang().getAdapter().getAddress());

        devices = (ListView)findViewById(R.id.devices);

        devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getBangBang().getAdapter().cancelDiscovery();
                BluetoothDevice device = devicesAdapter.getItem(i);
                try {
                    //BluetoothSocket socket = device.createRfcommSocketToServiceRecord(getBangBang().getUuid());
                    getBangBang().connect(device);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
        case REQUEST_ENABLE_BT:
            if (resultCode == REQUEST_DURATION) {
                String username = getBangBang().getUsername();
                getBangBang().getAdapter().setName(username);
                start();
            } else {
                Toast.makeText(this, "You need to enble bluetooth.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onFound(Map<String, BluetoothDevice> devices, BluetoothDevice device) {
        this.devices.setAdapter(devicesAdapter =new ArrayAdapter<BluetoothDevice>(this,android.R.layout.simple_list_item_1,new ArrayList<BluetoothDevice>(devices.values())) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView)super.getView(position, convertView, parent);
                BluetoothDevice device = getItem(position);
                String address = device.getAddress();
                String name = device.getName();
                if(name==null) {
                    name = "Unknown... : " + address;
                }
                view.setText(name + " : " + address);
                return view;

            }
        });
    }
}
