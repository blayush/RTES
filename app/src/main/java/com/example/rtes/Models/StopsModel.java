package com.example.rtes.Models;

public class StopsModel {
    String stopname;
    String time;
    int status;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public StopsModel() {
    }

    public StopsModel(String stopname, String time,int status) {
        this.stopname = stopname;
        this.time = time;
        this.status=status;
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
