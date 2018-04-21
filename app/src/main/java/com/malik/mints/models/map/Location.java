package com.malik.mints.models.map;

import java.util.Date;

/**
 * Created by Malik on 3/2/2018.
 */

public class Location {
    public int id;
    public String animalName;
    public String latitude;
    public String longitude;
    public Date date;


    public Location(int id, String animalName, String latitude, String longitude, Date date)
    {
        this.id = id;
        this.animalName = animalName;
        this.latitude = latitude;

        this.longitude = longitude;
        this.date = date;
    }

    public int getId()
    {
        return id;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public Date getDate()
    {
        return date;
    }

    public String getAnimalName()
    {
        return animalName;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public void setAnimalName(String animalName)
    {
        this.animalName = animalName;
    }


}
