package com.parkinglotsystem;

public class AirportSecurity implements ParkingLotHandler
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
