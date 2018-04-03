package com.example.hp.networktestfortianxing.model;

import java.util.List;

/**
 * Created by hp on 2018/4/1.
 ...

 */

public interface I_model {
    public String getName();

    public String getStreet();

    public String getCity();

    public String getCountry();

    public List<NetMessage> getLinks();
}
