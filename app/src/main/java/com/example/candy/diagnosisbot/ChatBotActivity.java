package com.example.candy.diagnosisbot;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ChatBotActivity extends AppCompatActivity {
    private static final String TAG = ChatBotActivity.class.getName();
    private String appid = "76a0a013";
    private String appkey = "47d95003e43862338850d1f7b8cd62ec";
    JSONArray array = null;
    String choiceId = null;
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final EditText message_edit = (EditText) findViewById(R.id.editText_send);
        final Button send_button = (Button) findViewById(R.id.button_enter);
        send_button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                parsemessage(message_edit.getText().toString(),new symptomsinterface(){
                    @Override
                    public void onSuccess(String result){
                        Log.i(TAG,"hellostop"+result);
                    }
                });
                message_edit.setText("");

            }
        });

    }

    public void parsemessage(String message, final symptomsinterface callback) {
        RequestQueue mRequestQueuetheDiagnosis = Volley.newRequestQueue(this);
        String parse_url = "https://api.infermedica.com/v2/parse";
        String idd = "fake";
        JSONObject datatest = new JSONObject();
        try {
            datatest.put("text", message);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, parse_url, datatest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.i(TAG, "responsed" + response.toString());
                            array = response.getJSONArray("mentions");
                            //     String choiceId= array.getJSONObject(0).getString("choice_id");
                            //     String commonName= array.getJSONObject(0).getString("common_name");
                            //     Log.i(TAG,choiceId+commonName);
                            String id = array.getJSONObject(0).getString("id");
                            callback.onSuccess(id);

                            //        Log.i(TAG,id);
                            //      messageedit.setText(id);
               //             getText.givetext(id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //          Log.i(TAG,id);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        Log.i(TAG, "error" + error.toString());

                    }
                }) {
            /**
             * Passing some request headers*
             */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("App-Id", appid);
                headers.put("App-Key", appkey);
                //     headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        mRequestQueuetheDiagnosis.add(jsonObjReq);

    }







}





