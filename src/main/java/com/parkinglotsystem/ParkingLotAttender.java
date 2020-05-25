package com.parkinglotsystem;
import java.util.List;

public class ParkingLotAttender
{
    private Object vehicle;
    private List<ParkingOwner>parkingSlots;

    public ParkingLotAttender(Object vehicle) {
        this.vehicle=vehicle;
    }

    public Object getVehicle() {
        return vehicle;
    }
}
