package com.parkinglotsystem;

import com.parkinglotsystem.exception.ParkingLotSystemException;

public class ParkingLotSystem<vehicle> {
    private int parkingLotCapacity;
    private Object vehicle;
    private int parkingLotSize=0;

    public void parkVehicle(Object vehicle) {
        if (this.parkingLotCapacity==parkingLotSize) {
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        }
        this.vehicle = vehicle;
        parkingLotSize++;
    }

    public ParkingLotSystem(int parkingLotCapacity)
    {
        this.parkingLotCapacity=parkingLotCapacity;
    }

    public boolean isPark(Object vehicle) {
        if (this.vehicle==vehicle) {
            return true;
        } else {
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
        }
    }

    public boolean isUnPark(Object vehicle) {
        if (this.vehicle != null && this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
    }
}