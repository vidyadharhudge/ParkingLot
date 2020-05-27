package com.parkinglotsystem;
import com.parkinglotsystem.observer.Vehicle;

import java.util.List;

public class ParkingLotAttender
{
    private Vehicle vehicle;


    public ParkingLotAttender(Vehicle vehicle) {
        this.vehicle=vehicle;
    }

    public Object getVehicle() {
        return vehicle;
    }
}
