package com.example.deliverable1fixed;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;


public class LoadingScreen extends AppCompatActivity {

 /**   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        /*EasySplashScreen config = new EasySplashScreen(LoadingScreen.this)
                .withFullScreen()
                .withTargetActivity(FrontScreen.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("FFFFF"))
                .withHeaderText("Header")
                .withFooterText("Footer")
                .withBeforeLogoText("Before Logog Text")
                .withAfterLogoText("After")
                .withLogo(R.mipmap.ic_launcher_round);
        config.getHeaderTextView().setTextColor(Color.BLACK);
        config.getFooterTextView().setTextColor(Color.BLACK);
        config.getBeforeLogoTextView().setTextColor(Color.BLACK);
        config.getAfterLogoTextView().setTextColor(Color.BLACK);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);

        getSupportActionBar().hide();

        Intent intentView;
        Handler().postDeplayed({
                Intent intentView = new Intent(LoadingScreen.this, FrontScreen.class)
                startActivity(intentView)
                finish()

        })
    }*/

    // Create a new event for the activity.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the content view.
        setContentView(R.layout.activity_loading_screen);

    }
}