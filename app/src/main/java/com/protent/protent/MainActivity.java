package com.protent.protent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.List;
import java.util.ArrayList;
import android.content.Intent;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button next;
    EditText enteredCode;
    List codeDevices = new ArrayList();
    boolean firstTime=true;
    //TODO: make sure this actually works :)
    SharedPreferences pref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create the file in which the tents will be saved
        //File MyTents = new File(getFilesDir(), "MyTents");
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Editor editor = pref.edit();
        //this is just if I want the welcome page...
        //TODO: not to have on the final code :)
        //seditor.clear().commit();
        boolean loggato = pref.getBoolean("loggato",false);
        Log.d("MAIN ACTIVITY: ","loggato: "+loggato);

        if(loggato){
            //Log.d("loggatooooo", "loggato è truueeeeeee");
            startActivity(new Intent(MainActivity.this, InstallProtect.class));
        }
        //fill the list of devices, assume that code has 5 alphanumeric letters
        codeDevices.add("12345");
        codeDevices.add("23456");

        next = (Button)findViewById(R.id.button);
        enteredCode = (EditText)findViewById(R.id.editText4);

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //startActivity(new Intent(MainActivity.this, InstallProtect.class));
                if(!codeDevices.contains(enteredCode.getText().toString())){
                    //the entered code is wrong, tell that to user
                    Toast.makeText(MainActivity.this,"There's no device with such code!",Toast.LENGTH_LONG).show();
                }
                else{
                    //the entered code is correct than continue
                    //the user is now "logged"
                    pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    Editor editor = pref.edit();
                    editor.putBoolean("loggato", true);
                    //put code of the device
                    editor.putString("CodeDevice",enteredCode.getText().toString());
                    editor.commit();
                    startActivity(new Intent(MainActivity.this, InstallProtect.class));
                }
            }
        });
    }
}
