package com.example.delaroy.recyclerswipe;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Route implements Serializable{
    String routeName;
    List<String> stops;
    int numberOfSeats;

    public Route(){

    }

    public Route(String routeName, List<String> stops, int numberOfSeats) {
        this.routeName = routeName;
        this.stops = stops;
        this.numberOfSeats = numberOfSeats;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString(){
        return "RouteName = "+routeName;
    }
}
