package com.example.candy.diagnosisbot;

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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getName();

    private String login_url = "https://sandbox-authservice.priaid.ch/login";
    private String symptom_url="https://sandbox-healthservice.priaid.ch/symptoms";
    private  String  api_key = "sydnie.chau@gmail.com";
    private String secret_key = "Tk9y7Y3GtSa4i5M6A";
    private String token_id="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InN5ZG5pZS5jaGF1QGdtYWlsLmNvbSIsInJvbGUiOiJVc2VyIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc2lkIjoiNTQwMSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvdmVyc2lvbiI6IjIwMCIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbGltaXQiOiI5OTk5OTk5OTkiLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL21lbWJlcnNoaXAiOiJQcmVtaXVtIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9sYW5ndWFnZSI6ImVuLWdiIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9leHBpcmF0aW9uIjoiMjA5OS0xMi0zMSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbWVtYmVyc2hpcHN0YXJ0IjoiMjAxOS0wNy0xNCIsImlzcyI6Imh0dHBzOi8vc2FuZGJveC1hdXRoc2VydmljZS5wcmlhaWQuY2giLCJhdWQiOiJodHRwczovL2hlYWx0aHNlcnZpY2UucHJpYWlkLmNoIiwiZXhwIjoxNTYzMTY2MzMwLCJuYmYiOjE1NjMxNTkxMzB9.I8zCl2C5MNYc0OBJ0EedQhnpc84xQdkPXvQln2zRRAE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

         String computedHash = HMAC.hmacDigest(login_url,secret_key,"HmacMD5");

        sendRequestAndPrintResponse(api_key,computedHash);
      ///  sendRequestandPrintSymptoms(token_id);
    }

    private void sendRequestAndPrintResponse(final String api_key, final String computedHash) {
         String request_url="https://sandbox-authservice.priaid.ch/login";

        StringRequest stringrequest = new StringRequest(Request.Method.POST, lo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Volley Result", "" + response); //the response contains the result from the server, a json string or any other object returned by your server

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.i("Volley Result", "" + error); //the response contains the result from the server, a json string or any other object returned by your server

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postMap = new HashMap<>();
                postMap.put("Authorization", "Bearer "+ api_key+ ":"+ computedHash);

                Log.i(TAG, postMap.toString());

                //..... Add as many key value pairs in the map as necessary for your request
                return postMap;

            }
        };
//make the request to your server as indicated in your request url
        Volley.newRequestQueue(getApplicationContext()).add(stringrequest);

    }
    private void sendRequestandPrintSymptoms(final String token_id) {

        StringRequest stringrequest = new StringRequest(Request.Method.POST, symptom_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Volley Result", "" + response); //the response contains the result from the server, a json string or any other object returned by your server

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging
                Log.i("Volley Result", "" + error); //the response contains the result from the server, a json string or any other object returned by your server

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postMap = new HashMap<>();
                postMap.put("token", token_id);
                postMap.put("language", "en-gb");
                Log.i(TAG, postMap.toString());

                //..... Add as many key value pairs in the map as necessary for your request
                return postMap;

            }
        };
//make the request to your server as indicated in your request url
        Volley.newRequestQueue(getApplicationContext()).add(stringrequest);

    }
}


