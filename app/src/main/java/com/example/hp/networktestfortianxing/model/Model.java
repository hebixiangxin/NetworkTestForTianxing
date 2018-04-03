package com.example.hp.networktestfortianxing.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hp on 2018/4/1.
 */
public class Model implements I_model{
    private String name;
    private String street;
    private String city;
    private String country;

    private List<NetMessage> links = new LinkedList<>();


    public Model(String name, String street, String city, String country, List<NetMessage> links) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.country = country;
        this.links = links;
    }


    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public List<NetMessage> getLinks() {
        return links;
    }
}
