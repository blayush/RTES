package com.example.rtes.Models;

public class StopsModel {
    String stopname,time;

    public StopsModel() {
    }

    public StopsModel(String stopname, String time) {
        this.stopname = stopname;
        this.time = time;
    }

    public String getStopname() {
        return stopname;
    }

    public void setStopname(String stopname) {
        this.stopname = stopname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
