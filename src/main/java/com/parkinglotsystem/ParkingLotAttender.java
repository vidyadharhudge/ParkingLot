package com.parkinglotsystem;
import com.parkinglotsystem.observer.ParkingOwner;
import com.parkinglotsystem.observer.Vehicle;

import java.util.List;

public class ParkingLotAttender
{
    private Vehicle vehicle;
    private List<ParkingOwner>parkingSlots;//


    public ParkingLotAttender(Vehicle vehicle) {
        this.vehicle=vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
