package com.example.betterttc.services;

public class Stop {

    private String StopId;
    private String Name;

    public String getBusStopId() {
        return StopId;
    }

    public String getName() {
        return Name;
    }



    public void setBusStopId(String busStopId) {
        this.StopId = busStopId;
    }

    public void setName(String name) {
        this.Name = name;
    }

}
