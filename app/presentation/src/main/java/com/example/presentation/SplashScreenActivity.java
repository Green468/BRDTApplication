package com.example.presentation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.presentation.fragments.UserProfileFragment;

public class SplashScreenActivity extends AppCompatActivity {
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.greeting_screen);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                i.putExtra("fragment","4");
                startActivity(i);
                // close this activity
                finish();

            }
        }, 1000);
//        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
//        finish();
        
    }
}
