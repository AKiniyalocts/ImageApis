package com.example.anthony.wedpics.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.anthony.wedpics.R;
import com.example.anthony.wedpics.helpers.SharedPreferenceHelper;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
            Check if there is a logged in session.
            Start the LoginActivity if not.
            Otherwise, go straight to the MainActivity
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextScreen;

                if(SharedPreferenceHelper.getGoodSession(SplashActivity.this)){
                    nextScreen = new Intent(SplashActivity.this, MainActivity.class);
                }
                else{
                    nextScreen = new Intent(SplashActivity.this, LoginActivity.class);
                }
                nextScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(nextScreen);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
