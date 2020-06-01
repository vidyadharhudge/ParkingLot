/**************************************************************************************************************
 * @Purpose:-To Park And Unpark Vehicle On Different Slot
 * @Author:vidyadhar
 * @Date-22/05/20
 * *************************************************************************************************************/
package com.parkinglotsystem;
import com.parkinglotsystem.enums.DriverType;
import com.parkinglotsystem.enums.VehicleType;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.observer.ParkingLotHandler;
import com.parkinglotsystem.observer.ParkingOwner;
import com.parkinglotsystem.observer.Vehicle;
import java.time.LocalDateTime;
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
        parkingLotHandlerList = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    public ParkingLot(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
        parkingLotHandlerList = new ArrayList<>();
        this.vehicles = new ArrayList();
    }

    /**
     * @param parkingLotCapacity new Parking Lot Capacity
     * @return Size Of Parking Lot
     * @purpose:-To Set Parking Capacity Of Parking Lot
     */
    public int setParkingLotCapacity(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
        return parkingLotCapacity;
    }

    /**
     * @param parkingOwner for Informing
     * @purpose:-To inform Parking Full To Observer
     */
    public void registerHandler(ParkingLotHandler parkingOwner) {
        this.parkingLotHandlerList.add(parkingOwner);
    }

    /**
     * purpose:-parking full or not and set the slot
     * @param driverType  which type of driver we needed
     * @param vehicle  which type of vehicle
     * @param attendantName providing attendant name
     */
    public void parkVehicle(Enum driverType, Vehicle vehicle, String attendantName) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(driverType, vehicle, attendantName);
        if (isPark(vehicle))
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        int slot = getParkingSlot();//
        parkingTimeSlot.setSlot(slot);
        this.vehicles.set(slot, parkingTimeSlot);
        vehicleCount++;
    }

    /**
     * purpose;-getting vehicle count
     * @return no of vehicle
     */
    public int getVehicleCount() {
        return vehicleCount;
    }

    /**
     * purpose;-vehicle prsent or not
     * @param vehicle fo attendance
     * @return present or not
     */
    public boolean isPark(Vehicle vehicle) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        if (this.vehicles.contains(parkingTimeSlot))
            return true;
        return false;
    }

    /**
     * purpose;-Unparking Vehicle And Checking Parking Is Empty Or Not
     * @param vehicle checking for presenty
     * @return vehicle present or not
     */
    public boolean isUnPark(Vehicle vehicle) {
        boolean isVehiclePrsent = this.vehicles.stream()
                .filter(parkingTimeSlot -> (vehicle) == parkingTimeSlot.getVehicle())
                .findFirst()
                .isPresent();
        if (isVehiclePrsent) return true;
        return false;
    }


    /**
     * purpose:-vehicle park in parking lot
     * @param slot    passing slot in map
     * @param vehicle passing vehicle in map
     */
    public void parkVehicle(int slot, Vehicle vehicle) {
        if (this.vehicleSlotMap.size() == this.parkingLotCapacity) {
            for (ParkingLotHandler parkingOwner : parkingLotHandlerList)
                parkingOwner.parkingIsFull();
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        }
        vehicleSlotMap.put(slot, vehicle);
    }

    /**
     * purpose;-Intializing parking Lot Capacity
     * @return size
     */
    public int initializeParkingSlot() {
        IntStream.range(0, this.parkingLotCapacity).forEach(slots -> this.vehicles.add(new ParkingTimeSlot(slots)));
        return vehicles.size();
    }

    /**
     * purpose;-set the slot as per size
     * @return slot
     */
    public int getParkingSlot() {//
        ArrayList<Integer> slotList = getSlot();
        for (int slot = 0; slot < slotList.size(); slot++) {
            if (slotList.get(0) == (slot))
                return slot;
        }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
    }

    /**
     * purpose;-getting slot for vehicle And Return Attendant
     * @param attendant information of vehicle
     * @return attendant
     */
    public ParkingLotAttender getParkingLotAttendant(ParkingLotAttender attendant) {
        ParkingOwner parkingOwner = (ParkingOwner) parkingLotHandlerList.get(0);
        parkVehicle(parkingOwner.getParkingSlot(), attendant.getVehicle());
        return attendant;
    }

    /**
     * purpose;-attendant present or not
     * @param attendant checking for vehicle
     * @return attendant
     */
    public ParkingLotAttender getMyVehicle(ParkingLotAttender attendant) {
        if (vehicleSlotMap.containsValue(attendant.getVehicle()))
            return attendant;
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.NO_SUCH_ATTENDANT, "No Attendant Found");
    }

    /**
     * purpose;-To Find Slot In Which Vehicle Is Parked
     * @param vehicle
     * @return slot on which vehicle is parked
     */
    public int findVehicle(Vehicle vehicle) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        if (this.vehicles.contains(parkingTimeSlot))
            return this.vehicles.indexOf(parkingTimeSlot);
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
    }

    /**
     * purpose;-intialize slot to null
     * @return slot is full or not
     */
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

    /**
     * purpose;-on which time vehicle is parked
     * @param vehicle To Find On Which Time Is Parked
     * @return time
     */
    public boolean setTime(Vehicle vehicle) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        for (int i = 0; i < this.vehicles.size(); i++) {
            if (this.vehicles.get(i).time != null && this.vehicles.contains(parkingTimeSlot))
                return true;
        }
        return false;
    }

    /**
     * purpose;-To Find Vehicle Based On Colour
     * @param colour vehicle to find
     * @return list of vehicle with same colour
     */
    public List<Integer> findByColour(String colour) {
        List<Integer> colourList = new ArrayList<>();
        colourList = this.vehicles.stream()
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle() != null)
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle().getColour().equals(colour))
                .map(parkingTimeSlot -> parkingTimeSlot.getSlot())
                .collect(Collectors.toList());
        return colourList;
    }

    /**
     * purpose;-find vehicle by model and colour
     * @param colour vehicle to find
     * @param modelName vehicle to find
     * @return list of vehicle with same colour and model
     */
    public List<String> findByModelAndColour(String colour, String modelName) {

        List<String> list = new ArrayList<>();
        list = this.vehicles.stream()
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle() != null)
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle().getModelName().equals(modelName))
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle().getColour().equals(colour))
                .map(parkingTimeSlot -> (parkingTimeSlot.getAttendantName()) + " " + (parkingTimeSlot.getSlot()) + " " +
                        (parkingTimeSlot.vehicle.getNumberPlate()))
                .collect(Collectors.toList());
        return list;

    }

    /**
     * purpose;-find vehicle by model name
     * @param modelName model of vehicle to find
     * @return list oof vehicle with same model
     */
    public List<Integer> findByModelName(String modelName) {
        List<Integer> list = new ArrayList<>();
        list = this.vehicles.stream()
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle() != null)
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle().getModelName().equals(modelName))
                .map(parkingTimeSlot -> parkingTimeSlot.getSlot())
                .collect(Collectors.toList());
        return list;

    }

    /**
     * purpose;-finding vehicle which parked in last 30 min
     * @return list of vehicle
     */
    public List<String> findParkedVehicleLast30Min() {
        List<String> vehicleList = new ArrayList<>();
        vehicleList = this.vehicles.stream()
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle() != null)
                .filter(parkingTimeSlot -> parkingTimeSlot.getTime().getMinute() - LocalDateTime.now().getMinute() <= 30)
                .map(parkingTimeSlot -> (parkingTimeSlot.getSlot()) + " " + (parkingTimeSlot.getVehicle().getModelName()) + " " + (parkingTimeSlot.getVehicle().getNumberPlate()))
                .collect(Collectors.toList());
        return vehicleList;

    }


    /**+
     * purpose;-get list of vehicle As Per Lot Number
     * @return vehicle list with as per lot
     */
    public List<String> getVehicleDetailByLotNumber() {
        List<String> vehicleList = new ArrayList();
        vehicleList = this.vehicles.stream()
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle() != null)
                .filter((parkingTimeSlot -> (parkingTimeSlot.getDriverType() == DriverType.HANDICAP_DRIVER) ||
                        (parkingTimeSlot.getDriverType() == VehicleType.SMALL_VEHICLE)))
                .map (parkingTimeSlot -> (parkingTimeSlot.getVehicle().getModelName()) + " " +
                        (parkingTimeSlot.getVehicle().getNumberPlate()))
                .collect(Collectors.toList());
        return vehicleList;
    }

    /**
     * purpose;-getting list of vehicle
     * @return details of vehicle
     */
    public List<String> getVehicleList() {
        List<String> vehicleList = this.vehicles.stream()
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle() != null)
                .map(parkingTimeSlot -> (parkingTimeSlot.getSlot()) + " " + (parkingTimeSlot.getVehicle().getModelName())
                        + " " + (parkingTimeSlot.getVehicle().getNumberPlate()))
                .collect(Collectors.toList());
        return vehicleList;
    }
}
