package com.example.retrofitexam;

import java.util.ArrayList;

public class WeatherResult {
    private Loc coord;
    private ArrayList<Weather> weather;

    public Loc getCoord() {
        return coord;
    }

    public void setCoord(Loc coord) {
        this.coord = coord;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WeatherResult{");
        sb.append("coord=").append(coord);
        sb.append(", weather=").append(weather);
        sb.append('}');
        return sb.toString();
    }
}
