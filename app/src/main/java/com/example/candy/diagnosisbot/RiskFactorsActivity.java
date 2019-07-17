package com.example.candy.diagnosisbot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RiskFactorsActivity extends AppCompatActivity {
    private static final String TAG = RiskFactorsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riskfactors);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final RadioGroup radiogroup_overweight= findViewById(R.id.radioGroup_overweight);
        final RadioGroup radiogroup_hypertension= findViewById(R.id.radioGroup_hypertension);
        final RadioGroup radiogroup_highc= findViewById(R.id.radioGroup_highc);
        Button buttonnext1=findViewById(R.id.buttonnext1);
        buttonnext1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int radioID_overweight= radiogroup_overweight.getCheckedRadioButtonId();
                RadioButton radiobutton_overweight=findViewById(radioID_overweight);

                int radioID_hypertension= radiogroup_hypertension.getCheckedRadioButtonId();
                RadioButton radiobutton_hypertension=findViewById(radioID_hypertension);

                int radioID_highc= radiogroup_highc.getCheckedRadioButtonId();
                RadioButton radiobutton_highc=findViewById(radioID_highc);

                Intent intent= getIntent();
                String sex =intent.getStringExtra("sex");
                String age =intent.getStringExtra("age");

                Intent i = new Intent(RiskFactorsActivity.this, RiskFactors2Activity.class);
                i.putExtra("sex", sex);
                i.putExtra("age", age);
                i.putExtra("overweight", radiobutton_overweight.getText().toString());
                i.putExtra("hypertension", radiobutton_hypertension.getText().toString());
                i.putExtra("highc", radiobutton_highc.getText().toString());

                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            //    Log.i(TAG,sex+ age+ radiobutton_overweight.getText().toString()+ radiobutton_hypertension.getText().toString()+ radiobutton_highc.getText().toString() );

            }
        });



    }
    public void checkButton(View v){

    }
}
