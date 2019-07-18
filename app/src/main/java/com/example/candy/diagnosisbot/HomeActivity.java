package com.example.candy.diagnosisbot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getName();

    private String appid= "76a0a013";
    private String appkey="47d95003e43862338850d1f7b8cd62ec";
   // private String secret_key = "Tk9y7Y3GtSa4i5M6A";
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private RequestQueue mRequestQueueDiagnosis;
    private StringRequest stringRequestDiagnosis;
    private RequestQueue mRequestQueuesymptoms;
    private StringRequest stringRequestsymptoms;
    private RequestQueue mRequestQueuetheDiagnosis;
    private StringRequest stringRequesttheDiagnosis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // String computedHash = HMAC.hmacDigest(login_url,secret_key,"HmacMD5");
      //  sendRequestAndPrintResponse(api_key,computedHash);
      ///  sendRequestandPrintSymptoms(token_id);
    //    sendRequestAndPrintinfo();
       // sendRequestAndPrintsymptoms();
      //  sendRequestandprinttheDiagnosis();
        Button buttonfemale = (Button) findViewById(R.id.button_female);
        Button buttonmale = (Button) findViewById(R.id.button_male);
        buttonfemale.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {


               Intent i = new Intent(HomeActivity.this, AgeActivity.class);
                i.putExtra("sex", "female");
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }});
        buttonmale.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {


                    Intent i = new Intent(HomeActivity.this, AgeActivity.class);
                    i.putExtra("sex", "male");
                    startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }});



    }private void sendRequestAndPrintsymptoms() {
         String symptoms_url="https://api.infermedica.com/v2/symptoms";
        mRequestQueuesymptoms = Volley.newRequestQueue(this);
        stringRequestsymptoms = new StringRequest(Request.Method.GET, symptoms_url, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
           //         Log.i(TAG,"hi" + obj.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error: " + error.toString());
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("App-Id", appid);
                params.put("App-Key", appkey);

                return params;
            }
        };
        mRequestQueuesymptoms.add(stringRequestsymptoms);
    } private void sendRequestAndPrintinfo() {
        String info_url="https://api.infermedica.com/v2/info";
        mRequestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, info_url, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Log.i(TAG,obj.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error: " + error.toString());
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("App-Id", appid);
                params.put("App-Key", appkey);

                return params;
            }
        };
        mRequestQueue.add(stringRequest);
        Log.i(TAG,"hello" +stringRequest.toString());
    } private void sendRequestandprinttheDiagnosis(){
        mRequestQueuetheDiagnosis = Volley.newRequestQueue(this);



        String diagnosis_url ="https://api.infermedica.com/v2/diagnosis";
        String parse_url="https://api.infermedica.com/v2/parse";
        JSONObject data = new JSONObject();
        JSONObject datatest= new JSONObject();
        try {
            data.put("sex", "female");
            data.put("age",  25);
            JSONArray evidence_array = new JSONArray();
            JSONObject id = new JSONObject();
            JSONObject id2 = new JSONObject();
            JSONObject id3 = new JSONObject();
            datatest.put("text", "I have diabetes");

            ///creating json for example evidence
                id.put("id", "s_47");
                id.put("choice_id","present");
               id.put("initial",true);
                id2.put("id", "s_22");
               id2.put("choice_id","present");
                id2.put("initial",true);
                id3.put("id", "p_81");
                id3.put("choice_id","absent");
                evidence_array.put(id);
                evidence_array.put(id2);
                evidence_array.put(id3);


            data.put("evidence", evidence_array);

            JSONObject item = new JSONObject();

            item.put("disable_groups", true);

            data.put( "extras", item);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG,data.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, diagnosis_url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Success Callback
                        Log.i(TAG,"response" + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        Log.i(TAG,"error" + error.toString());

                    }
                })
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("App-Id", appid);
                headers.put("App-Key", appkey);
           //     headers.put("Content-Type", "application/json");
                return headers; }
        };
// Adding the request to the queue along with a unique string tag
      //  MyApplication.getInstance().addToRequestQueue(jsonObjectReq, "headerRequest");
// Adding the request to the queue along with a unique string tag
    //    HomeActivity.getInstance(this).addToRequestQueue(jsonObjReq, "postRequest");
        mRequestQueuetheDiagnosis.add(jsonObjReq);
        Log.i(TAG,datatest.toString());
    }private void sendRequestAndPrintDiagnosis() {
        String diagnosis_url ="https://api.infermedica.com/v2/diagnosis";
        mRequestQueueDiagnosis = Volley.newRequestQueue(this);
        stringRequestDiagnosis = new StringRequest(Request.Method.POST, diagnosis_url, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Log.i(TAG,obj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error: " + error.toString());
            }
        }
        ){          protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put("App-Id", appid);
                    MyData.put ("App-Key", appkey);
                    MyData.put("Content-Type", "application/json");
                    //Add the data you'd like to send to the server
                    return MyData;
            }
        };
        mRequestQueueDiagnosis.add(stringRequestDiagnosis);
    }

}


