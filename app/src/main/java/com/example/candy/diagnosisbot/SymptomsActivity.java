package com.example.candy.diagnosisbot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class SymptomsActivity extends AppCompatActivity {
    private static final String TAG = SymptomsActivity.class.getName();
    private static final String[] countries= new String[]{"Abdominal guarding","Ascites ","Chest ","cat"};
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


        AutoCompleteTextView edittext= findViewById(R.id.actv);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
        edittext.setAdapter(adapter);

    }
    }
