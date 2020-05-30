/**************************************************************************************************************
 * @Purpose:-To park and Unpark vehicle And Informing Parking Availability To Observer
 * @Author:vidyadhar
 * @Date-22/05/20
 * *************************************************************************************************************/
package com.parkinglotsystem;

import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.observer.Vehicle;
import com.parkinglotsystem.strategy.ParkingFactory;
import com.parkinglotsystem.strategy.ParkingStrategy;
import java.util.*;
import java.util.stream.Collectors;

public class ParkingLotSystem {
    private int capacity;
    private List<ParkingLot> parkingLotList;

    public ParkingLotSystem(int capacity) {
        this.capacity = capacity;
        this.parkingLotList = new ArrayList<>();
    }

    /**+
     * purpose;-add parking lot
     * @param parkingLot
     */
    public void addLots(ParkingLot parkingLot) {
        this.parkingLotList.add(parkingLot);
    }

    /**+
     * to check if lot added
     * @param parkingLot
     * @return false if not added
     */
    public boolean isLotAdd(ParkingLot parkingLot) {
        if (this.parkingLotList.contains(parkingLot)) return true;
        return false;
    }

    /**+
     * purpose;-vehicle park in parking lot
     * @param driverType
     * @param vehicle
     * @param attendant
     */
    public void parkVehicle(Enum driverType, Vehicle vehicle, String attendant) {
        ParkingStrategy parkingStrategy = ParkingFactory.getParkingStrategy(driverType);
        ParkingLot lot = parkingStrategy.getParkingLot(this.parkingLotList);
        lot.parkVehicle(driverType, vehicle, attendant);
    }

    /**+
     * purpose:-park vehicle in lot
     * @param vehicle park in parking lot
     * @return false if it is not park
     */
    public boolean isPark(Vehicle vehicle) {
        for (int i = 0; i < this.parkingLotList.size(); i++) {
            if (this.parkingLotList.get(i).isPark(vehicle)) return true;
        }
        return false;
    }

    /**+
     * purpose;-unpark vehicle from lot
     * @param vehicle park in parking lot
     */
    public boolean isUnPark(Vehicle vehicle) {
        for (int lot = 0; lot < this.parkingLotList.size(); lot++) {
            if (this.parkingLotList.get(lot).isUnPark(vehicle)) return true;
        }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
    }

    /**+
     * purpose;-find vehicle by colour
     * @param colour to find vehicle
     * @return vehicle list
     */
    public List<List<Integer>> findVehicleByColour(String colour) {
        List<List<Integer>> vehicleList = this.parkingLotList.stream()
                .map(lot -> lot.findByColour(colour))
                .collect(Collectors.toList());
        return vehicleList;
    }

    /**+
     * purpose:-find vehicle with help of model and colour
     * @param colour to find vehicle
     * @param modelName to find vehicle
     * @return vehicle with same model and colour
     */
    public List<List<String>> findByModelAndColour(String colour, String modelName) {
        List<List<String>> vehicleList = new ArrayList<>();
        for (ParkingLot list : this.parkingLotList) {
            List<String> lot = list.findByModelAndColour(colour, modelName);
            vehicleList.add(lot);
        }
        return vehicleList;
    }

    /**+
     * purpose;-finding vehicle by model name
     * @param modelName  to find vehicle
     * @return vehicle with same model name
     */
    public List<List<Integer>> findVehicleByModelName(String modelName) {
        List<List<Integer>> vehicleList = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByModelName(modelName))
                .collect(Collectors.toList());
        return vehicleList;
    }

    /**
     * purpose;-getting detail of vehicle
     * @return vehicle list
     */
    public List<List<String>>getDetailsOfParkedVehicle(){
        List<List<String>>ParkedVehicleList=this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.getVehicleList())
                .collect(Collectors.toList());
        return ParkedVehicleList;
    }

}
