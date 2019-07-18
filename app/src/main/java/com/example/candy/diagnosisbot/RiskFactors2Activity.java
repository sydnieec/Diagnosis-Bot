package com.example.candy.diagnosisbot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RiskFactors2Activity extends AppCompatActivity {
    private static final String TAG = RiskFactors2Activity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riskfactors2);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final RadioGroup radiogroup_smoking= findViewById(R.id.radioGroup_smoking);
        final RadioGroup radiogroup_diabetes= findViewById(R.id.radioGroup_diabetes);


        Button buttonnext1=findViewById(R.id.buttonnext2);
        buttonnext1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                int radioID_smoking= radiogroup_smoking.getCheckedRadioButtonId();
                RadioButton radiobutton_smoking=findViewById(radioID_smoking);

                int radioID_diabetes= radiogroup_diabetes.getCheckedRadioButtonId();
                RadioButton radiobutton_diabetes=findViewById(radioID_diabetes);

                Intent intent= getIntent();
                String sex =intent.getStringExtra("sex");
                String age =intent.getStringExtra("age");
                String overweight =intent.getStringExtra("overweight");
                String hypertension =intent.getStringExtra("hypertension");
                String highc =intent.getStringExtra("highc");

                Intent i = new Intent(RiskFactors2Activity.this, ChatBotActivity.class);
                i.putExtra("sex", sex);
                i.putExtra("age", age);
                i.putExtra("overweight", overweight);
                i.putExtra("hypertension", hypertension);
                i.putExtra("highc", highc);
                i.putExtra("smoking", radiobutton_smoking.getText().toString());
                i.putExtra("diabetes", radiobutton_diabetes.getText().toString());

                startActivity(i);

            }

    });
    }
    public void checkButton(View v){

    }
}