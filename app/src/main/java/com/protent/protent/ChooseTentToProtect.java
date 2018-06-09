package com.protent.protent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.util.Log;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ChooseTentToProtect extends AppCompatActivity {

    Button next, back;
    String SelectedTent;
    int SelectedTentModel;
    static final int READ_BLOCK_SIZE = 100;
    String [] TentsNamesFinal;
    String [] TentsModelsFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_tent_to_protect);
        //show list of tents, when it's clicked proceed
        //first get the info from the file put them in an array
        //save the info into those arrays
        //TODO: here the user can have just 100 tents SHITTY IMPLEMENTATION, TO IMPROVE!!!!!!
        String [] TentsNames = new String[100];
        final String [] TentsModels = new String[100];
        int k=0,l=0;
        try{
            FileInputStream read = openFileInput("MyTents.txt");
            InputStreamReader InputRead = new InputStreamReader(read);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            Log.d("EHILE FILE:   ", s);

            //save info in the arrays
            int i=0, j=0;
            while(i<s.length()){
                //Log.d("CHAAAAR "+i,s.charAt(i)+"");
                if(s.charAt(i)==' '){
                    //it means that I already added the name, should add the model
                    if(k>l){
                        TentsModels[l]=s.substring(j,i);
                        l++;
                    }else{
                        TentsNames[k]=s.substring(j,i);
                        k++;
                    }
                    j=i+1;
                }
                i++;
            }
            /*i=0;
            while(i<k){
                Log.d("tent "+i, TentsNames[i]);
                i++;
            }*/

        }catch(Exception e){
            e.printStackTrace();
        }

        //put it in a resized array
        TentsNamesFinal = new String[k];
        TentsModelsFinal = new String[k];
        int i=0;
        while(i<k){
            TentsNamesFinal[i]=TentsNames[i];
            TentsModelsFinal[i]=TentsModels[i];
            i++;
        }
        //put that info in the listview
        final android.widget.ListView simpleList = (android.widget.ListView)findViewById(R.id.listView2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView11, TentsNamesFinal);
        simpleList.setAdapter(arrayAdapter);

        //handle the listview to update the SelectedTent
        //get the selected model
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Log.d("CLICCAATOOO  ", "  "+ChosenName);
                SelectedTent = TentsNamesFinal[position];
                SelectedTentModel = position;
            }
        });

        //handle NEXT button
        next = (Button)findViewById(R.id.button11);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if a tent is selected, otherwise tell the user to select
                if(SelectedTent!=null){
                    Intent intent = new Intent(ChooseTentToProtect.this, PlaceDevice.class);
                    //send that name to next activity where it should show the mapping of that tent
                    intent.putExtra("NameTent", SelectedTent);
                    intent.putExtra("ModelTent", SelectedTentModel);
                    startActivity(intent);
                }else{
                    Toast.makeText(getBaseContext(), "You need to select a tent!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //handle BACK button
        back = (Button)findViewById(R.id.button10);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseTentToProtect.this, InstallProtect.class));
            }
        });
    }
}
