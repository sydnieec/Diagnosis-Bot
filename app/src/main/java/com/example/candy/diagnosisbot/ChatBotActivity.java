package com.example.candy.diagnosisbot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatBotActivity extends AppCompatActivity {
    private static final String TAG = ChatBotActivity.class.getName();
    private String appid = "76a0a013";
    private String appkey = "47d95003e43862338850d1f7b8cd62ec";
    JSONArray array = null;
    JSONArray arraydiagnosis = null;

    String choiceId = null;
    String id = null;
    private ListView lvMessages;
    private MessageAdapter adapter;
    private List<messages> mMessageList;
    int l= 0;
    String step="beginning";
    RequestQueue mRequestQueuetheDiagnosis;
    String response_overweight;
    String response_hypertension;
    String response_highc;
    String response_smoking;
    String response_diabetes;
    String repeat= "no";
    int numberofquestions=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final EditText message_edit = (EditText) findViewById(R.id.editText_send);
        final Button send_button = (Button) findViewById(R.id.button_enter);
        List<String> array_of_ids = new ArrayList<String>();
        List<String> array_of_choiceid = new ArrayList<String>();

        Intent intent= getIntent();
        String sex =intent.getStringExtra("sex");
        String age =intent.getStringExtra("age");
        int int_age = Integer.parseInt(age);
        String overweight =intent.getStringExtra("overweight");
        if (overweight.equals("Yes")){
             response_overweight="present";
        }else if (overweight.equals("No")){
             response_overweight="absent";
        }else {
             response_overweight="unknown";
        }
        array_of_ids.add("p_7");
        array_of_choiceid.add(response_overweight);

        String hypertension =intent.getStringExtra("hypertension");
        if (hypertension.equals("Yes")){
             response_hypertension="present";
        }else if (hypertension.equals("No")){
             response_hypertension="absent";
        }else {
             response_hypertension="unknown";
        }
        array_of_ids.add("p_9");
        array_of_choiceid.add(response_hypertension);

        String highc =intent.getStringExtra("highc");
        if (highc.equals("Yes")){
             response_highc="present";
        }else if (highc.equals("No")){
             response_highc="absent";
        }else {
             response_highc="unknown";
        }
        array_of_ids.add("p_10");
        array_of_choiceid.add(response_highc);

        String smoking =intent.getStringExtra("smoking");
        if (smoking.equals("Yes")){
             response_smoking="present";
        }else if (smoking.equals("No")){
             response_smoking="absent";
        }else {
             response_smoking="unknown";
        }
        array_of_ids.add("p_28");
        array_of_choiceid.add(response_smoking);

        String diabetes =intent.getStringExtra("diabetes");
        if (diabetes.equals("Yes")){
            String response_diabetes="present";
        }else if (diabetes.equals("No")){
            String response_diabetes="absent";
        }else {
            String response_diabetes="unknown";
        }


        lvMessages = (ListView) findViewById(R.id.MessageListView);

        mMessageList = new ArrayList<>();

        //Init Adapter
        adapter = new MessageAdapter(getApplicationContext(), mMessageList);
        lvMessages.setAdapter(adapter);

        l++;
        mMessageList.add(new messages(l, "Diagnosis Bot","Welcome to Diagnosis Bot! Please describe your symptoms, and enter 'I'm done' when you are done to begin interview" ));
        adapter = new MessageAdapter(getApplicationContext(), mMessageList);
        lvMessages.setAdapter(adapter);
        lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);

        send_button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (!message_edit.getText().toString().equals("I'm done") && step.equals("beginning") ) {
                    parsemessage(message_edit.getText().toString(), new symptomsinterface() {
                        @Override
                        public void onSuccess(String result) {

                            Log.i(TAG, "hellostop" + result);
                            array_of_ids.add(result);
                            array_of_choiceid.add("present");
                        }
                    });

                    l++;
                    mMessageList.add(new messages(l, "You",message_edit.getText().toString()));
                    adapter = new MessageAdapter(getApplicationContext(), mMessageList);
                    lvMessages.setAdapter(adapter);
                    lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);
                    message_edit.setText("");

                    l++;
                    mMessageList.add(new messages(l, "Diagnosis Bot","Noted, please describe more symptoms for more accuracy, enter 'stop' when done"));
                    adapter = new MessageAdapter(getApplicationContext(), mMessageList);
                    lvMessages.setAdapter(adapter);
                    lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);
                }else if (message_edit.getText().toString().equals("I'm done")){
                    step="step two";
                    repeat="no";

                    l++;
                    mMessageList.add(new messages(l, "You",message_edit.getText().toString()));
                    adapter = new MessageAdapter(getApplicationContext(), mMessageList);
                    lvMessages.setAdapter(adapter);
                    lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);
                    message_edit.setText("");

                    sendRequestandprinttheDiagnosis(sex, int_age,array_of_ids, array_of_choiceid , new diagnosisinterface() {
                        @Override
                        public void onSuccess(JSONObject result) {

                            try {
                                String question= result.getJSONObject("question").getString("text");
                                String new_id= result.getJSONObject("question").getJSONArray("items").getJSONObject(0).getString("id");

                                array_of_ids.add(new_id);


                                l++;
                                mMessageList.add(new messages(l, "Diagnosis Bot",question + new_id));
                                adapter = new MessageAdapter(getApplicationContext(), mMessageList);
                                lvMessages.setAdapter(adapter);
                                lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    });





                } else if (step.equals("step two")){
                    numberofquestions++;
                    if (numberofquestions>5){
                        step="step 3";
                    }
                    if (message_edit.getText().toString().equals(("Yes"))){
                        array_of_choiceid.add("present");
                    }
                    else if (message_edit.getText().toString().equals("No")){
                        array_of_choiceid.add("absent");
                    } else {
                        array_of_choiceid.add("unknown");

                    }
                    l++;
                    mMessageList.add(new messages(l, "You",message_edit.getText().toString()));
                    adapter = new MessageAdapter(getApplicationContext(), mMessageList);
                    lvMessages.setAdapter(adapter);
                    lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);
                    message_edit.setText("");

                    Log.i(TAG, "bitch lasagna" + array_of_ids.toString() + " "+array_of_choiceid);
                    sendRequestandprinttheDiagnosis(sex, int_age,array_of_ids, array_of_choiceid , new diagnosisinterface() {
                        @Override
                        public void onSuccess(JSONObject result) {

                            try {
                                String question= result.getJSONObject("question").getString("text");
                                String new_id= result.getJSONObject("question").getJSONArray("items").getJSONObject(0).getString("id");

                                array_of_ids.add(new_id);


                                l++;
                                mMessageList.add(new messages(l, "Diagnosis Bot",question + new_id));
                                adapter = new MessageAdapter(getApplicationContext(), mMessageList);
                                lvMessages.setAdapter(adapter);
                                lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    });


                }
                else if ((message_edit.getText().toString().equals("I'm finished")) || (step.equals("step 3"))) {

                    if (message_edit.getText().toString().equals(("Yes"))){
                        array_of_choiceid.add("present");
                    }
                    else if (message_edit.getText().toString().equals("No")){
                        array_of_choiceid.add("absent");
                    } else {
                        array_of_choiceid.add("unknown");

                    }

                    l++;
                    mMessageList.add(new messages(l, "You",message_edit.getText().toString()));
                    adapter = new MessageAdapter(getApplicationContext(), mMessageList);
                    lvMessages.setAdapter(adapter);
                    lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);
                    message_edit.setText("");


                    sendRequestandprinttheDiagnosis(sex, int_age,array_of_ids, array_of_choiceid , new diagnosisinterface() {
                        @Override
                        public void onSuccess(JSONObject result) {

                            try {
                                arraydiagnosis = result.getJSONArray("conditions");
                                String iddiagnosis = arraydiagnosis.getJSONObject(0).getString("name");
                                String probablity = arraydiagnosis.getJSONObject(0).getString("probability");

                                Log.i(TAG,"aSDASD" +iddiagnosis);
                                //         callback.onSuccess((iddiagnosis));

                                l++;
                                mMessageList.add(new messages(l, "Diagnosis Bot","You most likely have " +iddiagnosis +" with a  " + probablity +"%" + "probability."));
                                adapter = new MessageAdapter(getApplicationContext(), mMessageList);
                                lvMessages.setAdapter(adapter);
                                lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);

                                String iddiagnosis2 = arraydiagnosis.getJSONObject(1).getString("name");
                                String probablity2 = arraydiagnosis.getJSONObject(1).getString("probability");

                                l++;
                                mMessageList.add(new messages(l, "Diagnosis Bot","Another condition I calculated is: " +iddiagnosis2 +" with a  " + probablity2 +"%" + "probability."));
                                adapter = new MessageAdapter(getApplicationContext(), mMessageList);
                                lvMessages.setAdapter(adapter);
                                lvMessages.setSelection(lvMessages.getAdapter().getCount()-1);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    });
                  //  sendRequestandprinttheDiagnosis(sex,int_age,array_of_ids,array_of_choiceid);
                }




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
    private void sendRequestandprinttheDiagnosis(String sex, Integer age, List<String> listofids, List<String> listofchoiceids,   diagnosisinterface callback){
        mRequestQueuetheDiagnosis = Volley.newRequestQueue(this);
        String diagnosis_url ="https://api.infermedica.com/v2/diagnosis";
        JSONObject data = new JSONObject();
        try {
            data.put("sex", sex);
            data.put("age",  age);
            JSONArray evidence_array = new JSONArray();
            JSONObject id = new JSONObject();
            JSONObject id2 = new JSONObject();
            JSONObject id3 = new JSONObject();

            ///creating json for example evidence
            id.put("id", "s_47");
            id.put("choice_id","present");
            id.put("initial",true);
            id2.put("id", "s_22");
            id2.put("choice_id","present");
            id2.put("initial",true);
            id3.put("id", "p_81");
            id3.put("choice_id","absent");
    //        evidence_array.put(id);
     //       evidence_array.put(id2);
     //       evidence_array.put(id3);

            for (int j=0; j<listofids.size(); j++) {
                    JSONObject id4=new JSONObject();
                    id4.put("id",listofids.get(j));
                    id4.put("choice_id",listofchoiceids.get(j));
                    id4.put("initial",true);
                    evidence_array.put(id4);
            }
            data.put("evidence", evidence_array);

            JSONObject item = new JSONObject();
            item.put("disable_groups", true);
            data.put( "extras", item);
            Log.i(TAG,data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG,data.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, diagnosis_url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                        //Success Callback
                        Log.i(TAG,"response" + response.toString());
                        try {
                            arraydiagnosis = response.getJSONArray("conditions");
                            String iddiagnosis = arraydiagnosis.getJSONObject(0).getString("name");
                     //       Log.i(TAG,"aSDASD" +iddiagnosis);
                   //         callback.onSuccess((iddiagnosis));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

        mRequestQueuetheDiagnosis.add(jsonObjReq);
    }






}





