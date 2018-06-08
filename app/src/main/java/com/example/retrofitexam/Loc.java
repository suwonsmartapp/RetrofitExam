package com.example.retrofitexam;

class Loc {
    private String lon;
    private String lat;

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Loc{");
        sb.append("lon='").append(lon).append('\'');
        sb.append(", lat='").append(lat).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
