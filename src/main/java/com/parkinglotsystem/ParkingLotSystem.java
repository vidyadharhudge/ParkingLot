package com.parkinglotsystem;

import com.parkinglotsystem.exception.ParkingLotSystemException;

public class ParkingLotSystem {
    private Object vehicle;

    public void parkVehicle(Object vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isPark(Object vehicle) {
        if (this.vehicle == vehicle) {
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