package com.parkinglotsystem;

import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.observer.ParkingLotHandler;
import com.parkinglotsystem.observer.ParkingOwner;
import com.parkinglotsystem.observer.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {
    private int vehicleCount;
    private int parkingLotCapacity;
    private List<ParkingLotHandler> parkingLotHandlerList;
    private List<ParkingTimeSlot> vehicles;
    Map<Integer, Vehicle> vehicleSlotMap = new HashMap<>();

    public ParkingLot() {
        parkingLotHandlerList = new ArrayList();
        this.vehicles = new ArrayList();
    }

    public ParkingLot(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
        parkingLotHandlerList = new ArrayList();
        this.vehicles = new ArrayList();
    }

    public int setParkingLotCapacity(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
        return parkingLotCapacity;
    }

    public void registerHandler(ParkingLotHandler parkingOwner) {
        this.parkingLotHandlerList.add(parkingOwner);
    }

    public void parkVehicle(Enum driverType, Vehicle vehicle, String attendantName) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(driverType, vehicle, attendantName);
        if (isPark(vehicle))
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        int slot = getParkingSlot();
        parkingTimeSlot.setSlot(slot);
        this.vehicles.set(slot, parkingTimeSlot);
        vehicleCount++;
    }

    public boolean isPark(Vehicle vehicle) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        if (this.vehicles.contains(parkingTimeSlot))
            return true;
        return false;
    }

    public boolean isUnPark(Vehicle vehicle) {
        boolean isVehiclePrsent = this.vehicles.stream()
                .filter(parkingTimeSlot -> (vehicle) == parkingTimeSlot.getVehicle())
                .findFirst()
                .isPresent();
        for (ParkingLotHandler parkingOwner : parkingLotHandlerList)
            parkingOwner.parkingIsEmpty();
        if (isVehiclePrsent) return true;
        return false;
    }


    public void parkVehicle(int slot, Vehicle vehicle) {
        if (this.parkingLotCapacity == this.vehicleSlotMap.size()) {
            for (ParkingLotHandler parkingOwner : parkingLotHandlerList)
                parkingOwner.parkingIsFull();
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        }
        vehicleSlotMap.put(slot, vehicle);
    }

    public int initializeParkingSlot() {//
        IntStream.range(0, this.parkingLotCapacity).forEach(slots -> this.vehicles.add(new ParkingTimeSlot(slots)));
        return vehicles.size();
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public int getParkingSlot() {
        ArrayList<Integer> slotList = getSlot();
        for (int slot = 0; slot < slotList.size(); slot++) {
            if (slotList.get(0) == (slot))
                return slot;
        }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
    }

    public ParkingLotAttender getParkingLotAttendant(ParkingLotAttender attendant) {
        ParkingOwner parkingOwner = (ParkingOwner) parkingLotHandlerList.get(0);
        parkVehicle(parkingOwner.getParkingSlot(), (Vehicle) attendant.getVehicle());
        return attendant;
    }

    public ParkingLotAttender getMyVehicle(ParkingLotAttender attendant) {
        if (vehicleSlotMap.containsValue(attendant.getVehicle()))
            return attendant;
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.NO_SUCH_ATTENDANT, "No Attendant Found");
    }


    public int findVehicle(Vehicle vehicle) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        if (this.vehicles.contains(parkingTimeSlot))
            return this.vehicles.indexOf(parkingTimeSlot);
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
    }

    public ArrayList getSlot() {
        ArrayList<Integer> slots = new ArrayList();
        IntStream.range(0, parkingLotCapacity)
                .filter(slot -> this.vehicles.get(slot).getVehicle() == null)
                .forEach(slot -> slots.add(slot));
        if (slots.size() == 0) {
            for (ParkingLotHandler parkingOwner : parkingLotHandlerList)
                parkingOwner.parkingIsFull();
        }
        return slots;
    }

    public boolean setTime(Vehicle vehicle) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        for (int i = 0; i < this.vehicles.size(); i++) {
            if (this.vehicles.get(i).time != null && this.vehicles.contains(parkingTimeSlot))
                return true;
        }
        return false;
    }

    public List<Integer> findByColour(String colour) {
        List<Integer> colourList = new ArrayList<>();
        colourList = this.vehicles.stream()
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle() != null)
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle().getColour().equals(colour))
                .map(parkingTimeSlot -> parkingTimeSlot.getSlot())
                .collect(Collectors.toList());
        return colourList;
    }

    public List<String> findByModelAndColour(String colour, String modelName) {

        List<String>list=new ArrayList<>();
        list=this.vehicles.stream()
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle()!=null)
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle().getModelName().equals(modelName))
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle().getColour().equals(colour))
                .map(parkingTimeSlot -> (parkingTimeSlot.getAttendantName())+""+(parkingTimeSlot.getSlot())+""+(parkingTimeSlot.vehicle.getNumberPlate()))
                .collect(Collectors.toList());
        return list;

    }
    public List<Integer> findByModelName(String modelName) {
        List<Integer>list=new ArrayList<>();
        list=this.vehicles.stream()
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle()!=null)
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle().getModelName().equals(modelName))
                .map(parkingTimeSlot -> parkingTimeSlot.getSlot())
                .collect(Collectors.toList());
        return list;

    }
}
