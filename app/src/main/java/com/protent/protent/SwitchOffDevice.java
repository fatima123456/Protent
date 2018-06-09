package com.protent.protent;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Set;
import java.util.UUID;

public class SwitchOffDevice extends AppCompatActivity {

    Button switchOFF;
    private BluetoothAdapter BA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_off_device);

        //handle of button switchOFF
        switchOFF = (Button)findViewById(R.id.button13);
        switchOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: send the shut down signal to the device with bluetooth, SEARCH FOR IT
                BA = BluetoothAdapter.getDefaultAdapter();
                Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();

                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address
                        Log.d("TURN ON DEVICE", "name: "+deviceName);
                        if(deviceName.equals("raspberrypi")){
                            Log.d("SOCKET:","connessa a raspberrypi");
                            ParcelUuid[] uuids = device.getUuids();
                            BluetoothSocket socket;
                            final UUID sppUuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");
                            try{
                                socket = device.createRfcommSocketToServiceRecord(sppUuid);
                                //BluetoothSocket socket =(BluetoothSocket) device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] {int.class}).invoke(device,1);
                                for(int i=0; ;i++){
                                    try{
                                        socket.connect();
                                    }catch(IOException e){
                                        if(i<5){
                                            Log.d("SOCKET: ","Failed to connect, retrying "+e.toString());
                                            continue;
                                        }
                                        Log.d("SOCKET: ","Failed to connect "+e.toString());
                                        return;
                                    }
                                    break;
                                }
                                Log.d("SOCKET: ","Connected to bluetooth socket");
                                String command = "stop McKinley-KEA-3";
                                try {
                                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "ASCII"));
                                    writer.write(command);
                                    writer.flush();
                                } catch (IOException ex) {
                                    Log.d("SOCKET: ","Failed to write a command: " + ex.toString());
                                    return;
                                }
                                Log.d("SOCKET: ","Command is sent: " + command);

                            } catch(IOException e){
                                Log.d("SOCKET: ","failed to create RFComm socket "+e.toString());
                            }
                        }
                    }
                }
                Intent intent = new Intent(SwitchOffDevice.this,TurnOnDevice.class);
                startActivity(intent);
            }
        });
    }
}
