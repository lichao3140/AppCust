package com.eke.cust.model;

import java.io.Serializable;

/**
 * Created by wujian on 2017/2/25.
 */

public class HouseLocation implements Serializable {

    public  String pointLon;
    public  String pointLat;

    public HouseLocation(String pointLon, String pointLat) {
        this.pointLon = pointLon;
        this.pointLat = pointLat;
    }

    public String getPointLon() {
        return pointLon;
    }

    public void setPointLon(String pointLon) {
        this.pointLon = pointLon;
    }

    public String getPointLat() {
        return pointLat;
    }

    public void setPointLat(String pointLat) {
        this.pointLat = pointLat;
    }
}
