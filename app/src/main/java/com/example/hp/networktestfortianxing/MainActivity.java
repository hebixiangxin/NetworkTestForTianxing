package com.example.hp.networktestfortianxing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.networktestfortianxing.View.I_View;
import com.example.hp.networktestfortianxing.present.I_Present;
import com.example.hp.networktestfortianxing.present.Present;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity implements I_View{


    public static final int SetText = 1;
    public static final int ClearText = 2;

    private TextView responseText;
    private Button sendRequest;
    private static Handler mHandler;

    private I_Present I_myPresent;


    private Button downLoadButton;
    private final String IMAGE_PATH = "http://www.baidu.com/img/bd_logo1.png";
    private ProgressDialog progressDialog;
    private ImageView downLoadImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initHandle();
        I_myPresent = new Present(this);

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.send_request) {
                    I_myPresent.buttonOnClick();
                }
            }
        });

        downLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownLoadTask().execute(IMAGE_PATH);
            }
        });
    }

    private void initView(){
        responseText=(TextView) findViewById(R.id.response_text);
        sendRequest=(Button) findViewById(R.id.send_request);
        downLoadButton=(Button) findViewById(R.id.download_button);
        downLoadImg=(ImageView) findViewById(R.id.downloadImg);

    }

    private void initHandle(){
        mHandler = new Handler(){
            @Override
            public void handleMessage( Message msg ){
                super.handleMessage(msg);
                if( msg.what == SetText ){
                    responseText.setText((String)msg.obj);
                }else if(msg.what == ClearText){
                    responseText.setText("");
                }
            }
        };
    }


    private class DownLoadTask extends AsyncTask<String,Integer,byte[]>{

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("提示");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("正在下载图片，请耐心等候！");
            progressDialog.show();
        }


        @Override
        protected byte[] doInBackground(String... strings) {
            byte[] result = null;
            try {
                URL url_downloadURL = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url_downloadURL.openConnection();
                conn.connect();
                int file_length = conn.getContentLength();
                InputStream inputStream = conn.getInputStream();

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                byte[] data = new byte[1024];

                int total_length = 0;
                int len = 0;
                while ((len = inputStream.read(data)) != -1) {
                    total_length += len;
                    int progress_value = (int) ((total_length / (float) file_length) * 100);
                    publishProgress(progress_value);
                    outputStream.write(data, 0, len);
                }

                result = outputStream.toByteArray();

            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        protected void onPostExecute(byte[] result) {
            super.onPostExecute(result);
            Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
            downLoadImg.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }

    }

    public void setTextViewString( final String str ){

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                responseText.setText(str);
//            }
//        });
        Message m = Message.obtain();
        m.what = SetText;
        m.obj = str;
        mHandler.sendMessage(m);
    }

    public void clearTextView(){
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                responseText.setText("");
//            }
//        });

        Message m = Message.obtain();
        m.what = ClearText;
        mHandler.sendMessage(m);

    }


}