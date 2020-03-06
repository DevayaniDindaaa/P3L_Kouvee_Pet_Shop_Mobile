package com.p3l_f_1_pegawai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class login_activity extends AppCompatActivity {
    private EditText username, password;
    private Button  masuk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        masuk = (Button) findViewById(R.id.masuk);

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("owner")){
                    Intent i = new Intent(getApplicationContext(), drawer_activity_owner.class);
                    startActivity(i);
                }
                else if(username.getText().toString().equals("cs")){
                    Intent i = new Intent(getApplicationContext(), drawer_activity_cs.class);
                    startActivity(i);
                }
            }
        });
    }
}
