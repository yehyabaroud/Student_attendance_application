package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class splach extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        Thread splach = new Thread(){
            @Override
            public void run() {
                try {

                    sleep(5000);
                    SharedPreferences sharedPreferences = getSharedPreferences("myPreferences" , Context.MODE_PRIVATE);
                    boolean policy = sharedPreferences.getBoolean("policy",false);

                    if (policy) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), login.class);
                        startActivity(intent);
                        finish();
                    }

                }catch (Exception exception){
                    exception.printStackTrace();
                }

            }
        };

        splach.start();

    }
}