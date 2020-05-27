package com.parkinglotsystem;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.observer.Vehicle;
import com.parkinglotsystem.strategy.ParkingFactory;
import com.parkinglotsystem.strategy.ParkingStrategy;

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

    public void parkVehicle(Enum driverType, Vehicle vehicle) {
        ParkingStrategy parkingStrategy= ParkingFactory.getParkingStrategy(driverType);
      ParkingLot lot=parkingStrategy.getParkingLot(this.parkingLotList);
        lot.parkVehicle(driverType,vehicle);
    }

    public boolean isPark(Vehicle vehicle) {
        for(int i=0;i<this.parkingLotList.size();i++) {
            if(this.parkingLotList.get(i).isPark(vehicle))return true;
        }
        return false;
    }

    public boolean isUnPark(Vehicle vehicle) {
        for(int lot=0; lot<this.parkingLotList.size();lot++) {
            if(this.parkingLotList.get(lot).isUnPark(vehicle))return true;
        }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND,"Vehicle Is Not In Parking");
    }

    public List findVehicleByColour(String colour) {
        List<ArrayList>parkingLot=new ArrayList<>();
        for(ParkingLot lot:this.parkingLotList)
        {
            ArrayList<Integer>location=lot.findLocation(colour);
            parkingLot.add(location);
        }
        return parkingLot;
    }
}
