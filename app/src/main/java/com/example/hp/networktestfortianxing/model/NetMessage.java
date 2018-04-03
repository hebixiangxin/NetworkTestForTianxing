package com.example.hp.networktestfortianxing.model;

/**
 * Created by hp on 2018/4/1.  /
 */
public class NetMessage {
    private String name;
    private String url;

    public NetMessage(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
