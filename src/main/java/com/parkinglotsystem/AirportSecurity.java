package com.parkinglotsystem;

public class AirportSecurity implements ParkingLotHandler
{
    private boolean parkingLotCapacity;

    @Override
    public void parkingIsFull() {
        this.parkingLotCapacity=true;
    }

    @Override
    public void parkingIsEmpty() {

        this.parkingLotCapacity=false;
    }

    public boolean parkingFull() {
        return this.parkingLotCapacity;
    }
}
