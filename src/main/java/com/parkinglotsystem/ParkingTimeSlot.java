/**************************************************************************************************************
 * @Purpose:-To Store Properties Of Parking Slot(vehicle,Time,Slot,Attendent Name)
 * @Author:vidyadhar
 * @Date-29/05/20
 * *************************************************************************************************************/
package com.parkinglotsystem;

import com.parkinglotsystem.observer.Vehicle;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingTimeSlot {
    private  int slot;
    protected   String attendantName;
    public Enum driverType;
    protected  LocalDateTime time;
    protected Vehicle vehicle;

    public ParkingTimeSlot(Enum driverType, Vehicle vehicle,String attendantName) {
        this.time= LocalDateTime.now();
        this.driverType=driverType;
        this.attendantName=attendantName;
        this.vehicle=vehicle;
    }

    /**
     * @Purpose:-To initialize Slot
     * @Param:-slot
     */
    public ParkingTimeSlot(int slot){
        this.slot=slot;
    }

    /**
     * @Purpose:-To initialize Vehicle
     * @Param:-vehicle
     */
    public ParkingTimeSlot(Vehicle vehicle) {
        this.vehicle=vehicle;
    }

    /**
     * @Purpose:-To Return Slot
     * @return :-slot
     */
    public int getSlot(){
        return slot;
    }

    /**
     * @Purpose:-To set Slot
     * @param ;-slot
     */
    public void setSlot(int slot){
        this.slot=slot;
    }

    /**
     * @Purpose:-To Return Time
     * @return :-Time
     */
    public LocalDateTime getTime(){
        return time;
    }

    /**
     * @Purpose:-To Return vehicle
     * @return :-vehicle
     */
    public Vehicle getVehicle(){
        return vehicle;
    }

    /**
     * @Purpose:-To Return Attendant Name
     * @return :-attendant Name
     */
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

    public Enum getDriverType() {
        return driverType;
    }
}
