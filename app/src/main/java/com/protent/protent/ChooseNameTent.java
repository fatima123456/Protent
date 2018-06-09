package com.protent.protent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChooseNameTent extends AppCompatActivity {

    Button next, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_name_tent);

        next = (Button)findViewById(R.id.button4);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: check that the inserted name is not already used

                EditText mEdit = (EditText)findViewById(R.id.editText);
                String name = mEdit.getText().toString();
                Intent intent = new Intent(ChooseNameTent.this, SearchTent.class);
                //send that name to next activity where it should be inserted in the file MyTents
                intent.putExtra("NAME", name);
                startActivity(intent);
            }
        });

        back = (Button)findViewById(R.id.button7);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseNameTent.this, InstallProtect.class));
            }
        });
    }
}
