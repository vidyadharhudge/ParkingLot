package com.parkinglotsystem;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import java.util.*;

public class ParkingLotSystem {
    private int capacity;
    private List<ParkingLot>parkingLotList;

    public ParkingLotSystem(int capacity) {
        this.capacity=capacity;
        this.parkingLotList=new ArrayList<>();
    }

    public void addLots(ParkingLot parkingLot) {
        this.parkingLotList.add(parkingLot);
    }

    public boolean isLotAdd(ParkingLot parkingLot) {
        if (this.parkingLotList.contains(parkingLot))return true;
        return false;
    }

    public void parkVehicle(ParkingStrategy driverType,Object vehicle) {
      ParkingLot lot=driverType.getParkingLot(this.parkingLotList);
        lot.parkVehicle(driverType,vehicle);
    }

    public boolean isPark(Object vehicle) {
        if (this.parkingLotList.get(0).isPark(vehicle))
            return true;
        return false;
    }

    public boolean isUnPark(Object vehicle) {
        for(int lot=0; lot<this.parkingLotList.size();lot++) {
            if(this.parkingLotList.get(lot).isUnPark(vehicle))return true;
        }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND,"Vehicle Is Not In Parking");
    }
}
