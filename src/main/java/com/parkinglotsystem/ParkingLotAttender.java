package com.parkinglotsystem;
import com.parkinglotsystem.observer.ParkingOwner;
import com.parkinglotsystem.observer.Vehicle;

public class ParkingLotAttender
{
    private Vehicle vehicle;

    public ParkingLotAttender(Vehicle vehicle) {
        this.vehicle=vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
