package com.omdb.rohksin.transitionapi;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAnimation();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setAnimation();
        TextView textView = findViewById(R.id.text);

        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRevealAnimation();
            }
        });

        layout = findViewById(R.id.reveal);

        FloatingActionButton button = findViewById(R.id.fab);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reverseReveal();
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ListActivity.class);
                if(Build.VERSION.SDK_INT>20) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                    startActivity(i, options.toBundle());
                }
                else {
                    startActivity(i);
                }
            }
        });


    }

    public void setAnimation()
    {
        if(Build.VERSION.SDK_INT>20)
        {
            Slide slide = new Slide();
            slide.setDuration(4000);
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setExitTransition(fade);
            getWindow().setReturnTransition(slide);
        }
    }

    public void startRevealAnimation()
    {
        int centerX = layout.getRight();
        int centerY = layout.getBottom();
        int startRadius = 0;
        double endRadius = Math.hypot(layout.getWidth(), layout.getHeight());

        int radius = (int)endRadius;

        if(Build.VERSION.SDK_INT>20) {
            Animator animator = ViewAnimationUtils.createCircularReveal(layout, centerX, centerY, startRadius, radius);
            animator.setDuration(500);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.start();
            layout.setVisibility(View.VISIBLE);
        }


    }

    public void reverseReveal()
    {
        int centerX = layout.getRight();
        int centerY = layout.getBottom();
        int startRadius = 0;
        double endRadius = Math.hypot(layout.getWidth(), layout.getHeight());

        int radius = (int)endRadius;

        if(Build.VERSION.SDK_INT>20) {
            Animator animator = ViewAnimationUtils.createCircularReveal(layout, centerX, centerY, radius, startRadius);
            animator.setDuration(500);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    layout.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        }

    }



}
