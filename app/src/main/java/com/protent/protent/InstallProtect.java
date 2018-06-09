package com.protent.protent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstallProtect extends AppCompatActivity {

    Button install;
    Button protect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_protect);

        install = (Button)findViewById(R.id.button2);
        protect = (Button)findViewById(R.id.button3);

        install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstallProtect.this, ChooseNameTent.class));
            }
        });

        protect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstallProtect.this, ChooseTentToProtect.class));
            }
        });
    }
}
