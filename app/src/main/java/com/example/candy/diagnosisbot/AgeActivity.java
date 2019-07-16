package com.example.candy.diagnosisbot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AgeActivity extends AppCompatActivity {
    private static final String TAG = AgeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Button next = (Button) findViewById(R.id.button_next);
        next.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {

                EditText age= (EditText) findViewById(R.id.editText_age);
                String string_age= age.getText().toString();
         //       int number= Integer.parseInt(string_age);
                Intent intent= getIntent();
                String sex =intent.getStringExtra("sex");
                Intent i = new Intent(AgeActivity.this, SymptomsActivity.class);
                i.putExtra("sex", sex);
                i.putExtra("age", string_age);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


            }});
    }
}
