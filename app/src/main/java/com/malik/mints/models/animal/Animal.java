package com.malik.mints.models.animal;

import com.malik.mints.models.map.Location;

import java.util.Date;

/**
 * Created by Malik on 3/2/2018.
 */

public class Animal
{
    public Animal(int id, String name, Location lastLocation, Date date, String userid, String deviceid) {
        this.id = id;
        this.name = name;
        this.lastLocation = lastLocation;
        this.date = date;
        this.userid = userid;
        this.deviceid = deviceid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public int id;
    public String name;
    public Location lastLocation;
    public Date date;
    public String userid;
    public String deviceid;

}
