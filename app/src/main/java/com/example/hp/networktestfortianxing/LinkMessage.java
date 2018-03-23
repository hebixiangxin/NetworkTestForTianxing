package com.example.hp.networktestfortianxing;

/**
 * Created by hp on 2018/3/23.
 */
public class LinkMessage {

    private String name;
    private String url;

    public LinkMessage( String name,String url ){
        this.name = name;
        this.url = url;
    }

    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }

}
