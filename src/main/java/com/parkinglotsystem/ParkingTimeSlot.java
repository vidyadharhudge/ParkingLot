package com.parkinglotsystem;
import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingTimeSlot {

    protected   LocalDateTime time;
    protected  Object vehicle;

    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        if (obj==null || getClass()!=obj.getClass())return false;
        ParkingTimeSlot that=(ParkingTimeSlot)obj;
        return Objects.equals(vehicle,that.vehicle);
    }

    public ParkingTimeSlot(Object vehicle) {
    this.vehicle=vehicle;
    this.time= LocalDateTime.now();
}
}
