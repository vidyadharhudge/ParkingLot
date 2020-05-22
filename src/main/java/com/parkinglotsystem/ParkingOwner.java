package com.parkinglotsystem;

public class ParkingOwner
{
    private boolean parkingLotCapacity;

    public void parkingIsFull()
    {
        this.parkingLotCapacity=true;
    }
    public boolean parkingFull()
    {
        return this.parkingLotCapacity;
    }
}
