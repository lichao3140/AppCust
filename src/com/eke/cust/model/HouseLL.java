package com.eke.cust.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-06-12.
 */

public class HouseLL implements Serializable{
    public String name;
    public double lon;
    public double lat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
