package com.example.buddycop;

public class Wanted {


    private String City;
    private String Crime;
    private String Name;

    public Wanted(String city, String crime, String name) {

        City = city;
        Crime = crime;
        Name = name;
    }

    public Wanted() {
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCrime() {
        return Crime;
    }

    public void setCrime(String crime) {
        Crime = crime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
