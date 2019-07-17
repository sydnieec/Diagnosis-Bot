package com.example.candy.diagnosisbot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SymptomsActivity extends AppCompatActivity {
    private static final String TAG = SymptomsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent= getIntent();
        String sex =intent.getStringExtra("sex");
        String age =intent.getStringExtra("age");
        String overweight =intent.getStringExtra("overweight");
        String hypertension =intent.getStringExtra("hypertension");
        String highc =intent.getStringExtra("highc");
        String smoking =intent.getStringExtra("smoking");
        String diabetes =intent.getStringExtra("diabetes");
        Log.i(TAG,"chicken+" + sex+ age+ overweight+hypertension+highc+smoking+diabetes);

    }
    }
