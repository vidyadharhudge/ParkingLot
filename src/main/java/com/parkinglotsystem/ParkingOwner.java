package com.parkinglotsystem;

public class ParkingOwner implements ParkingLotHandler
{
    private boolean parkingFull;

    @Override
    public void parkingIsFull()
    {
        parkingFull=true;
    }
    public boolean parkingFull()
    {
        return parkingFull;
    }

}
