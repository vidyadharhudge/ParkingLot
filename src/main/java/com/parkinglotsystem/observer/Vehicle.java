package com.parkinglotsystem.observer;

public class Vehicle {
    private  String modelName;
    private  String numberPlate;
    private  String colour;


    public Vehicle(){

    }
    public Vehicle(String colour){
        this.colour=colour;
    }
    public Vehicle(String colour,String modelName,String numberPlate){
        this.colour=colour;
        this.modelName=modelName;
        this.numberPlate=numberPlate;
    }
    public String getColour() {
        return colour;
    }
    public String getModelName(){
        return modelName;
    }
    public String getNumberPlate(){
        return numberPlate;
    }
}
