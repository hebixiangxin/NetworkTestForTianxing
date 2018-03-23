package com.example.hp.networktestfortianxing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainActivity extends Activity {

    TextView responseText;
    String name;
    String str_street;
    String str_city;
    String str_country;
    String url_1;
    String url_2;
    String url_3;

    private List<LinkMessage> linkMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest=(Button) findViewById(R.id.send_request);
        responseText=(TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.send_request) {
                    sendRequestWithHttpURLConnection();
                }
            }
        });
    }



    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    URL url=new URL("http://wanandroid.com/tools/mockapi/3875/android");
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream in=connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    parseJSONWithJSONObject( response.toString() );

                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    {
                        if(reader!=null){
                            try{
                                reader.close();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }

                        }
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONObject allMess = new JSONObject(jsonData);

            name = allMess.getString("name");

            JSONObject subObject = allMess.getJSONObject("address");
            str_street = subObject.getString("street");
            str_city = subObject.getString("city");
            str_country = subObject.getString("country");

            JSONArray subArray= allMess.getJSONArray("links");
            for(int i=0;i<subArray.length();i++){
                JSONObject temp = subArray.getJSONObject(i);
                String str_name = temp.getString("name");
                String str_url = temp.getString("url");


                LinkMessage tempMessage = new LinkMessage(str_name,str_url);
                linkMessages.add(tempMessage);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        showFormedString();
    }


    private void showFormedString(){
        String res = "名字是 " + name + "\n" +
                "地址在 " + str_country + str_city + str_street + "\n\n";

        String str_link = "";
        for( int i=0;i<linkMessages.size();i++ ){
            str_link += "名字是：" + linkMessages.get(i).getName() + " 网址是：" + linkMessages.get(i).getUrl() +"\n";
        }


        showResponse( res + str_link );
    }


    private void test(){

        String mess = "{\n" +
                "    \"name\": \"安卓测试\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"科技园路.\",\n" +
                "        \"city\": \"江苏苏州\",\n" +
                "        \"country\": \"中国\"\n" +
                "    },\n" +
                "    \"links\": [\n" +
                "        {\n" +
                "            \"name\": \"Google\",\n" +
                "            \"url\": \"http://www.google.com\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Baidu\",\n" +
                "            \"url\": \"http://www.baidu.com\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"SoSo\",\n" +
                "            \"url\": \"http://www.SoSo.com\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";








        try{
            JSONObject allMess = new JSONObject(mess);

            name = allMess.getString("name");

            JSONObject subObject = allMess.getJSONObject("address");
            str_street = subObject.getString("street");
            str_city = subObject.getString("city");
            str_country = subObject.getString("country");

            JSONArray subArray= allMess.getJSONArray("links");
            for(int i=0;i<subArray.length();i++){
                JSONObject temp = subArray.getJSONObject(i);
                String str_name = temp.getString("name");
                String str_url = temp.getString("url");


                LinkMessage tempMessage = new LinkMessage(str_name,str_url);
                linkMessages.add(tempMessage);
            }





        }catch (JSONException e){
            e.printStackTrace();
        }


    }


}
