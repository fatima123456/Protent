package com.protent.protent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.util.Log;

public class PlaceDevice extends AppCompatActivity {

    private Integer images[] = {R.drawable.abcd, R.drawable.abcd};
    ImageView image;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_device);
        //basing on which tent has been clicked show correspondent image

        //get chosen tent name and model
        final String TentName = getIntent().getStringExtra("NameTent");
        final int TentModel = getIntent().getIntExtra("ModelTent",0);

        image = (ImageView)findViewById(R.id.imageView);
        image.setImageResource(images[TentModel]);

        //handle NEXT button
        next = (Button)findViewById(R.id.button6);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PlaceDevice.this,TurnOnDevice.class);
                intent.putExtra("TentName", TentName);
                intent.putExtra("TentModel", TentModel);
                startActivity(intent);
            }
        });
    }
}
