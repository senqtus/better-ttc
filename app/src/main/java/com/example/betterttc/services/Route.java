package com.example.betterttc.services;

public class Route {

    private String routeNumber;
    private String firstStopName;
    private String secondStopName;


    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public void setFirstStopName(String firstStopName) {
        this.firstStopName = firstStopName;
    }

    public void setSecondStopName(String secondStopName) {
        this.secondStopName = secondStopName;
    }


    public String getRouteNumber() {
        return routeNumber;
    }

    public String getFirstStopName() {
        return firstStopName;
    }

    public String getSecondStopName() {
        return secondStopName;
    }

}
