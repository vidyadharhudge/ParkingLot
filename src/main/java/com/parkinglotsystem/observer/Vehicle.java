/**************************************************************************************************************
 * @Purpose:To Store Properties Of Vehicle
 * @Author:vidyadhar
 * @Date-27/05/20
 * *************************************************************************************************************/

package com.parkinglotsystem.observer;

public class Vehicle {
    private  String modelName;
    private  String numberPlate;
    private  String colour;

    /**
     * purpose;-Used As Default Constructor
     */
    public Vehicle(){
    }

    /**
     * purpose;-To Return Vehicle Colour
     */
    public Vehicle(String colour){
        this.colour=colour;
    }

    /**
     * purpose;-Used As Parameterized Constructor
     * @param :-initializing colour,modelName,Plate
     */
    public Vehicle(String colour,String modelName,String numberPlate){
        this.colour=colour;
        this.modelName=modelName;
        this.numberPlate=numberPlate;
    }

    /**
     * purpose;-Return Vehicle Colour
     * @return :-colour Of Vehicle
     */
    public String getColour() {
        return colour;
    }

    /**
     * purpose;-Return Model Of Vehicle
     * @return :-Model Of Vehicle
     */
    public String getModelName(){
        return modelName;
    }

    /**
     * purpose;-Return Number Plate
     * @return :-numberPlateOfVehicle
     */
    public String getNumberPlate(){
        return numberPlate;
    }
}
