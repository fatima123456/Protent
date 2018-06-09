package com.protent.protent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ArrayAdapter;import android.widget.ListView;
import android.util.Log;
import android.widget.AdapterView.OnItemClickListener;
import java.io.FileOutputStream;
import android.widget.Toast;
import java.io.File;
import android.content.Context;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SearchTent extends AppCompatActivity {

    String[] ModelsList = {"McKinley-KEA-3"};
    Button next, back;
    String SelectedModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tent);

        //get chosen name from precedent activity
        final String ChosenName = getIntent().getStringExtra("NAME");

        //handle the list, put it in the listview
        final ListView simpleList = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView11, ModelsList);
        simpleList.setAdapter(arrayAdapter);

        //TODO: filter the list when user inputs

        //get the selected model
        simpleList.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Log.d("CLICCAATOOO  ", "  "+ChosenName);
                SelectedModel = ModelsList[position];
            }
        });

        //handle of NEXT button
        next = (Button)findViewById(R.id.button5);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the user selected a model
                //Log.d("SELECTEDMODEL", "   "+SelectedModel);
                if(SelectedModel != null){
                    //save info in file and finish.. ChosenName and SelectedModel
                    //File tents = new File(context.getFilesDir(), "MyTents");
                    //File MyTents = new File(getFilesDir(), "MyTents");
                    FileOutputStream outputStream;
                    try{
                        String TentInfo = ChosenName+" "+SelectedModel+" ";
                        outputStream = openFileOutput("MyTents.txt", MODE_APPEND);
                        outputStream.write(TentInfo.getBytes());
                        outputStream.close();
                        startActivity(new Intent(SearchTent.this, FinishInstall.class));
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "You need to select a model!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        //handle of BACK button
        back = (Button)findViewById(R.id.button8);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchTent.this, ChooseNameTent.class));
            }
        });
    }
}
