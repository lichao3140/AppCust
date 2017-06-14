package com.eke.cust.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wujian on 2016/7/31.
 */

public class HouseCover implements Serializable {
    public  String id;
    public  String estateid;
    public  String estatename;
    public  String areaname;
    public  String districtname;
    public  String name;
    public  int chuzunum;
    public  int chushounum;
    public  double lon;
    public  double lat;
    public  String main;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstateid() {
        return estateid;
    }

    public void setEstateid(String estateid) {
        this.estateid = estateid;
    }

    public String getEstatename() {
        return estatename;
    }

    public void setEstatename(String estatename) {
        this.estatename = estatename;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChuzunum() {
        return chuzunum;
    }

    public void setChuzunum(int chuzunum) {
        this.chuzunum = chuzunum;
    }

    public int getChushounum() {
        return chushounum;
    }

    public void setChushounum(int chushounum) {
        this.chushounum = chushounum;
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

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
