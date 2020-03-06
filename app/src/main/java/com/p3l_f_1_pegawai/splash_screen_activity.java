package com.p3l_f_1_pegawai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash_screen_activity extends Activity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                Intent intent=new Intent(splash_screen_activity.this, login_activity.class);
                startActivity(intent);
                finish();
            }
        }
        ,2000);
    }
}
