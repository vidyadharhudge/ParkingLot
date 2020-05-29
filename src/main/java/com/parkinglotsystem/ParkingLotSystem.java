package com.parkinglotsystem;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.observer.Vehicle;
import com.parkinglotsystem.strategy.ParkingFactory;
import com.parkinglotsystem.strategy.ParkingStrategy;

import java.util.*;
import java.util.stream.Collectors;

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

    public void parkVehicle(Enum driverType, Vehicle vehicle,String attendant) {
        ParkingStrategy parkingStrategy= ParkingFactory.getParkingStrategy(driverType);
      ParkingLot lot=parkingStrategy.getParkingLot(this.parkingLotList);
        lot.parkVehicle(driverType,vehicle,attendant);
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

    public List<List<Integer>> findVehicleByColour(String colour) {
        List<List<Integer>>vehicleList=this.parkingLotList.stream()
                .map(lot->lot.findByColour(colour))
                .collect(Collectors.toList());
        return vehicleList;
    }

    public List<List<String>> findByModelAndColour(String colour, String modelName) {
        List<List<String>>vehicleList=new ArrayList<>();
        for(ParkingLot list:this.parkingLotList){
            List<String>lot=list.findByModelAndColour(colour,modelName);
            vehicleList.add(lot);
        }
        return vehicleList;
    }

    public List<List<Integer>> findVehicleByModelName(String modelName) {
        List<List<Integer>>vehicleList=this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByModelName(modelName))
                .collect(Collectors.toList());
        return vehicleList;
    }

    public List<List<String>>findByLotNumber(ParkingLot parkingLot2,ParkingLot parkingLot4){
        List<List<String>>vehicleList=this.parkingLotList.stream()
                .map(parkingLot -> parkingLot2.getVehicleDetailByLotNumber())
                .map(parkingLot->parkingLot4.getVehicleDetailByLotNumber())
                .collect(Collectors.toList());
        return vehicleList;
    }
}
