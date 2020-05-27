package com.parkinglotsystem;
import com.parkinglotsystem.observer.Vehicle;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingTimeSlot {

    private Enum driverType;
    protected  LocalDateTime time;
    protected Vehicle vehicle;

    public ParkingTimeSlot(Enum driverType, Vehicle vehicle) {
    this.vehicle=vehicle;
    this.time= LocalDateTime.now();
    this.driverType=driverType;
    }

    public ParkingTimeSlot(Vehicle vehicle) {
    this.vehicle=vehicle;
    }



    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        if (obj==null || getClass()!=obj.getClass())return false;
        ParkingTimeSlot that=(ParkingTimeSlot)obj;
        return Objects.equals(vehicle,that.vehicle);
    }
}
