package com.example.hp.networktestfortianxing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.networktestfortianxing.View.I_View;
import com.example.hp.networktestfortianxing.present.I_Present;
import com.example.hp.networktestfortianxing.present.Present;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements I_View{

    private TextView responseText;
    private Button sendRequest;

    private I_Present I_myPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        I_myPresent = new Present(this);

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.send_request) {
                    I_myPresent.buttonOnClick();
                }
            }
        });
    }

    private void initView(){
        responseText=(TextView) findViewById(R.id.response_text);
        sendRequest=(Button) findViewById(R.id.send_request);
    }




    public void setTextViewString( final String str ){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(str);
            }
        });
    }

    public void clearTextView(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText("");
            }
        });
    }



}