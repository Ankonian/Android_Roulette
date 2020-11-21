package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener{

    boolean rotateButton = true;
    int sections = 8;
    long degree = 0;
    SharedPreferences sharedPreferences;

    Button b_start;
    ImageView selected, roulette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(1024);
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_start = (Button)findViewById(R.id.buttonStart);

        selected = (ImageView)findViewById(R.id.itemSelected);
        roulette = (ImageView)findViewById(R.id.roulette);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sections = this.sharedPreferences.getInt("SECTIONS", 8);
        setImageRoulette(this.sections);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        this.rotateButton = false;
        b_start.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Toast toast = Toast.makeText(this, " " + String.valueOf((int)((double)this.sections) - Math.floor(((double)this.degree) / (360.0d / ((double)this.sections)))) + " ", 0);
        toast.setGravity(49, 0, 0);
        toast.show();
        this.rotateButton = true;
        b_start.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void onClickButtonRotation(View v)
    {
        if(this.rotateButton)
        {
            int rotateAmount = new Random().nextInt(360) + 3600;
            RotateAnimation rotateAnimation = new RotateAnimation((float)this.degree, (float)(this.degree) + ((long)rotateAmount), 1, 0.5f, 1, 0.5f);
            this.degree = (this.degree + ((long)rotateAmount)) % 360;
            rotateAnimation.setDuration((long)rotateAmount);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            rotateAnimation.setAnimationListener(this);
            roulette.setAnimation(rotateAnimation);
            roulette.startAnimation(rotateAnimation);
        }
    }

    private void setImageRoulette(int numSections)
    {
        switch(numSections)
        {
            case 8:
                roulette.setImageDrawable(getResources().getDrawable(R.drawable.complete_roulette));
        }
    }
}