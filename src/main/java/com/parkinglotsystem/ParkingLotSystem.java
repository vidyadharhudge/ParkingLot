package com.parkinglotsystem;
import com.parkinglotsystem.exception.ParkingLotSystemException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {

    private int parkingLotCapacity;
    private List<ParkingLotHandler> parkingLotHandlerList;
    private List<Object>vehicles;

    public ParkingLotSystem(int parkingLotCapacity) {
        this.parkingLotCapacity=parkingLotCapacity;
        parkingLotHandlerList=new ArrayList();
        this.vehicles=new ArrayList();
    }

    public void setParkingLotCapacity(int parkingLotCapacity)
    {
        this.parkingLotCapacity=parkingLotCapacity;
    }

    public void registerHandler(ParkingLotHandler parkingOwner) {
        this.parkingLotHandlerList.add(parkingOwner);
    }

    public void parkVehicle(Object vehicle) {
        if (this.parkingLotCapacity==this.vehicles.size()) {
            for(ParkingLotHandler parkingOwner:parkingLotHandlerList)
            parkingOwner.parkingIsFull();
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        }
        if (isPark(vehicle))
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
        this.vehicles.add(vehicle);
    }

    public boolean isPark(Object vehicle) {
        if (this.vehicles.contains(vehicle))
            return true;
            return false;
        }

    public boolean isUnPark(Object vehicle) {
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
            for(ParkingLotHandler parkingOwner:parkingLotHandlerList)
                parkingOwner.parkingIsEmpty();
            return true;
        }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
    }
}
