package com.parkinglotsystem;

public class ParkingOwner implements ParkingLotHandler
{
    private boolean parkingLotCapacity;
    private int count=0;

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

    public  int getParkingSlot() {
        return count++;
    }
}
