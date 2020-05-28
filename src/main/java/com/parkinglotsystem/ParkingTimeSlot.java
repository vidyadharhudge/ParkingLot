package com.parkinglotsystem;
import com.parkinglotsystem.observer.Vehicle;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingTimeSlot {

    private  int slot;
    protected   String attendantName;
    private Enum driverType;
    protected  LocalDateTime time;
    protected Vehicle vehicle;

    public ParkingTimeSlot(Enum driverType, Vehicle vehicle,String attendantName) {
    this.vehicle=vehicle;
    this.time= LocalDateTime.now();
    this.driverType=driverType;
    this.attendantName=attendantName;
    }

    public ParkingTimeSlot(int slot){
        this.slot=slot;
    }

    public ParkingTimeSlot(Vehicle vehicle) {
        this.vehicle=vehicle;
    }

    public int getSlot(){
        return slot;
    }

    public void setSlot(int slot){
        this.slot=slot;
    }

    public LocalDateTime getTime(){
        return time;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public String getAttendantName(){
        return attendantName;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        if (obj==null || getClass()!=obj.getClass())return false;
        ParkingTimeSlot that=(ParkingTimeSlot)obj;
        return Objects.equals(vehicle,that.vehicle);
    }
}
