package com.parkinglotsystem;

import java.util.List;

public class ParkingLotAttender
{
    private Object vehicle;
    private List<ParkingOwner>parkingSlots;
    public ParkingLotAttender(Object vehicle)
    {
        this.vehicle=vehicle;
    }
    public ParkingLotAttender(Object vehicle, List<ParkingOwner> parkingSlots) {
        this.vehicle = vehicle;
        this.parkingSlots = parkingSlots;
    }
    public Object getVehicle()
    {
        return vehicle;
    }
}
