package com.protent.protent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.util.Log;
import android.bluetooth.BluetoothDevice;
import android.widget.TextView;
import android.widget.Toast;
import android.os.ParcelUuid;
import android.bluetooth.BluetoothSocket;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.lang.NullPointerException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class TurnOnDevice extends AppCompatActivity {

    private BluetoothAdapter BA;
    Button protect;
    private final static int REQUEST_ENABLE_BT = 1;
    private OutputStream outputStream;
    private InputStream inputStream;
    BluetoothSocket socket;
    boolean isTurnedOn;
    boolean openedSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_on_device);
        BA = BluetoothAdapter.getDefaultAdapter();
        isTurnedOn = false;
        openedSocket = false;
        //handle protect button
        protect = (Button)findViewById(R.id.button12);
        protect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: send the turn on signal to the device

                //Log.d("TURN ON DEVICE: ", "clicked on button");
                /*if (mBluetoothAdapter == null) {
                    //TODO: Device doesn't support Bluetooth

                    Log.d("TURN ON DEVICE: ","bluetooth not supported");
                }*/
                if (!BA.isEnabled()) {
                    //turn on the bluetooth, obv it will ask permission to the user
                    Log.d("TURN ON DEVICE: ", "before lines");
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 0);
                    //Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
                    Log.d("TURN ON DEVICE: ","requested enabling.......BLA BLA BLA.......");
                }
                else{
                    Log.d("SOCKET: ","bluetooth already enabled");
                    /*if(!socket.isConnected()){
                        openSocket();
                    }*/
                    //TODO:depends if the device is turned on or shut down DO SMTH
                    //turnOnDevice();
                    if(isTurnedOn){
                        Log.d("SOCKET: ","sono entrato in isturnedOn");
                        //device is turned on change then it must shutdown and change text
                        TextView testo = (TextView)findViewById(R.id.textView5);
                        testo.setText("Make sure your device is turned and in the right place! Get out of the tent!");
                        protect.setText("PROTECT THE TENT");
                        isTurnedOn = false;
                        openSocket();

                        sendCommand("stop McKinley-KEA-3");
                        try{
                            socket.close();
                        }catch(Exception e){
                            Log.d("SOCKET: ","failed to close socket");
                        }
                        startActivity(new Intent(TurnOnDevice.this,InstallProtect.class));
                    }
                    else{
                        Log.d("SOCKET: ","sono nel branch falso di isturnedOn");
                        //device needs to be shut down
                        TextView testo = (TextView)findViewById(R.id.textView5);
                        testo.setText("Your tent is now protected!");
                        protect.setText("SWITCH OFF");
                        isTurnedOn = true;
                        //connect socket if it is not connected
                        openSocket();
                        sendCommand("start McKinley-KEA-3");
                        try{
                            socket.close();
                        }catch(Exception e){
                            Log.d("SOCKET: ","failed to close socket");
                        }
                    }
                }
                //startActivity(new Intent(TurnOnDevice.this,SwitchOffDevice.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            // bluetooth enabled
            //turnOnDevice();
            TextView testo = (TextView)findViewById(R.id.textView5);
            testo.setText("Your tent is now protected!");
            protect.setText("SWITCH OFF");
            isTurnedOn = true;
            openSocket();
            sendCommand("start McKinley-KEA-3");
            try{
                socket.close();
            }catch(Exception e){
                Log.d("SOCKET: ","failed to close socket");
            }
        }else{
            // show error
        }
    }

    private void openSocket(){
        //Toast.makeText(getApplicationContext(), "YAAy",Toast.LENGTH_LONG).show();
        Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
        //TODO: still need to accoppiare il device all'inizio dell'installazione
        //TODO: per ora facciamo finta che è già accoppiato
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.d("TURN ON DEVICE", "name: "+deviceName);
                if(deviceName.equals("raspberrypi")){
                    Log.d("SOCKET:","connessa a raspberrypi");
                    ParcelUuid[] uuids = device.getUuids();

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
                        //String command = "start McKinley-KEA-3";
                    } catch(IOException e){
                        Log.d("SOCKET: ","failed to create RFComm socket "+e.toString());
                    }
                    //startActivity(new Intent(TurnOnDevice.this,SwitchOffDevice.class));
                }
            }
        }
    }

    private void sendCommand(String command){
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "ASCII"));
            writer.write(command);
            writer.flush();
        } catch (IOException ex) {
            Log.d("SOCKET: ","Failed to write a command: " + ex.toString());
            return;
        }
        Log.d("SOCKET: ","Command is sent: " + command);
    }

}
