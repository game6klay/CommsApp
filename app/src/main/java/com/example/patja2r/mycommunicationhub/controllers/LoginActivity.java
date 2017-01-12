package com.example.patja2r.mycommunicationhub.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.patja2r.mycommunicationhub.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by patja2r on 6/16/2016.
 */
public class LoginActivity extends AppCompatActivity {

    /*
    * This activity follows the similar details requirements for the JSON object as the Mobile Survey App
    *
    *
    * */
    private EditText enterEmail;
    private EditText enterPhoneNumber;
    private Button bLogin;
    private int clicks = 0;
    private static final String TAG = "MainActivity";

    String emailAddress = "jay@gmail.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //binding the text fields
        enterEmail = (EditText) findViewById(R.id.etEnterEmail);
        enterPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);

        emailAddress = enterEmail.getText().toString();

        //binding button
        bLogin = (Button) findViewById(R.id.bLogin);

        //set listeners
        enterEmail.addTextChangedListener(textWatcher);
        enterPhoneNumber.addTextChangedListener(textWatcher);

        //run once to disable it if empty
        checkFieldsForEmptyValues();



        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CalendarView.class);
                startActivity(intent);
                new PostAsync().execute();
            }
        });

    }



    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    public String getPhoneNumber(){

        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        return tMgr.getLine1Number();
    }

    private void checkFieldsForEmptyValues() {

        String sEmail = enterEmail.getText().toString().trim();
        String sPhoneNumber = enterPhoneNumber.getText().toString().trim();

        bLogin = (Button) findViewById(R.id.bLogin);

        if ((sEmail.length() > 0 && sPhoneNumber.length() > 0)) {
            bLogin.setEnabled(true);
        } else {
            bLogin.setEnabled(false);
        }

    }

}

class PostAsync extends AsyncTask<String, String, JSONObject> {

    JsonParser jsonParser = new JsonParser();

    private ProgressDialog pDialog;

    private static final String SIGN_IN_URL = "http://108.20.122.99/survey/v1/signin/";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    final String mdn = "754353453";
    final String deviceId = "45435435534";
    final String email = "jay@gmail.com";
    final String pushId = "5646";
    final String manufacturer = "Google";
    final String model = "65.5";
    final String osVersion = "8.8.8";
    final String product = "some";

    @Override
    protected void onPreExecute() {
        //pDialog = new ProgressDialog(MainActivity.this);
        //pDialog.setMessage("Attempting login...");
        //pDialog.setIndeterminate(false);
        //pDialog.setCancelable(true);
        //pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... args) {

        try {

            HashMap<String, String> postParam= new HashMap<String, String>();
            postParam.put("MDN",mdn);
            postParam.put("deviceId",deviceId);
            postParam.put("emailAddress",email);
            postParam.put("pushId",pushId);
            postParam.put("Manufacturer",manufacturer);
            postParam.put("Model",model);
            postParam.put("OSVersion",osVersion);
            postParam.put("Product",product);
            Log.d("request", "starting");

            JSONObject json = jsonParser.makeHttpRequest(
                    SIGN_IN_URL, "POST", postParam);

            if (json != null) {
                Log.d("JSON result", json.toString());

                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(JSONObject json) {

        int success = 0;
        String message = "";

        if (json != null) {

            try {
                success = json.getInt(TAG_SUCCESS);
                message = json.getString(TAG_MESSAGE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (success == 1) {
            Log.d("Success!", message);
        }else{
            Log.d("Failure", message);
        }
    }

}

