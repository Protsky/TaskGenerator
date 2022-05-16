package com.example;

/**
* Classe che rappresenta un waypoint.
* @author Gionata Donati
*/

public class Waypoint {

    private String name;
    private String latitude;
    private String longitude;
    private String altitude;

    public Waypoint(String name, String latitude, String longitude, String altitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return this.altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public Waypoint name(String name) {
        setName(name);
        return this;
    }

    public Waypoint latitude(String latitude) {
        setLatitude(latitude);
        return this;
    }

    public Waypoint longitude(String longitude) {
        setLongitude(longitude);
        return this;
    }

    public Waypoint altitude(String altitude) {
        setAltitude(altitude);
        return this;
    }


    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", altitude='" + getAltitude() + "'" +
            "}";
    }

}