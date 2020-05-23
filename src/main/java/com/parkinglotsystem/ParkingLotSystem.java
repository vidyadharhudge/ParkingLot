package com.parkinglotsystem;
import com.parkinglotsystem.exception.ParkingLotSystemException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {
    private int parkingLotCapacity;
    private Object vehicle;
    private int parkingLotSize=0;
    private ParkingOwner parkingOwner;
    List<ParkingLotHandler> parkingLotHandlerList;

    public void parkVehicle(Object vehicle) {
        if (this.parkingLotCapacity==parkingLotSize) {
            for(ParkingLotHandler parkingOwner:parkingLotHandlerList)
            parkingOwner.parkingIsFull();
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        }
        this.vehicle = vehicle;
        parkingLotSize++;
    }

    public ParkingLotSystem(int parkingLotCapacity) {
        this.parkingLotCapacity=parkingLotCapacity;
        parkingLotHandlerList=new ArrayList();
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
            return true;
        }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
    }

    public void registerHandler(ParkingLotHandler parkingOwner) {
        parkingLotHandlerList.add(parkingOwner);
    }
}
