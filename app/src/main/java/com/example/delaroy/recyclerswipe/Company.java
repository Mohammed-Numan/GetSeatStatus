package com.example.delaroy.recyclerswipe;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Company implements Serializable{
    String name;
    List<Route> routes;

    public Company(){

    }

    public Company(String name, List<Route> routes) {
        this.name = name;
        this.routes = routes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
