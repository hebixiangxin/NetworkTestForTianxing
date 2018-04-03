package com.example.hp.networktestfortianxing.present;

import com.example.hp.networktestfortianxing.View.I_View;
import com.example.hp.networktestfortianxing.model.I_model;
import com.example.hp.networktestfortianxing.model.Model;
import com.example.hp.networktestfortianxing.model.NetMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hp on 2018/4/1.
 */
public class Present implements I_Present{

    private I_model I_myModel;
    private I_View I_myView;

    public Present( I_View view ){
        I_myView = view;
    }

    public void buttonOnClick(){
        netRequest();

    }


    private void netRequest(){
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
                    showMessage();
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




    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONObject allMess = new JSONObject(jsonData);

            String name = allMess.getString("name");

            JSONObject subObject = allMess.getJSONObject("address");
            String str_street = subObject.getString("street");
            String str_city = subObject.getString("city");
            String str_country = subObject.getString("country");

            JSONArray subArray= allMess.getJSONArray("links");

            List<NetMessage> linkMessages = new LinkedList<>();

            for(int i=0;i<subArray.length();i++){
                JSONObject temp = subArray.getJSONObject(i);
                String str_name = temp.getString("name");
                String str_url = temp.getString("url");


                NetMessage tempMessage = new NetMessage(str_name,str_url);
                linkMessages.add(tempMessage);
            }

            I_myModel = new Model(name,str_street,str_city,str_country,linkMessages);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void showMessage(){
        String res = "名字是 " + I_myModel.getName() + "\n" +
                "地址在 " + I_myModel.getCountry() + I_myModel.getCity() + I_myModel.getStreet() + "\n\n";

        String str_link = "";
        for( int i=0;i<I_myModel.getLinks().size();i++ ){
            str_link += "名字是：" + I_myModel.getLinks().get(i).getName() + " 网址是：" + I_myModel.getLinks().get(i).getUrl() +"\n";
        }

        I_myView.setTextViewString( res + str_link );

    }

}
