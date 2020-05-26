package com.parkinglotsystem;

import com.parkinglotsystem.exception.ParkingLotSystemException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ParkingLot
{
    private int parkingLotCapacity;
    private List<ParkingLotHandler> parkingLotHandlerList;
    private List<ParkingTimeSlot>vehicles;
    Map<Integer,Object> vehicleSlotMap=new HashMap();

    public ParkingLot(int parkingLotCapacity) {
        this.parkingLotCapacity=parkingLotCapacity;
        parkingLotHandlerList=new ArrayList();
        this.vehicles=new ArrayList();
    }

    public int setParkingLotCapacity(int parkingLotCapacity) {
        this.parkingLotCapacity=parkingLotCapacity;
        return parkingLotCapacity;
    }

    public void registerHandler(ParkingLotHandler parkingOwner) {
        this.parkingLotHandlerList.add(parkingOwner);
    }

    public void parkVehicle(Object vehicle) {
        ParkingTimeSlot parkingTimeSlot=new ParkingTimeSlot(vehicle);
        if (!this.vehicles.contains(null)) {
            for(ParkingLotHandler parkingOwner:parkingLotHandlerList)
                parkingOwner.parkingIsFull();
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        }
        if (isPark(vehicle))
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_ALREADY_PARKED, "Vehicle Is Already Parked");
        int slot=getParkingSlot();
        this.vehicles.set(slot,parkingTimeSlot);
    }

    public boolean isPark(Object vehicle) {
        ParkingTimeSlot parkingTimeSlot=new ParkingTimeSlot(vehicle);
        if (this.vehicles.contains(parkingTimeSlot))
            return true;
        return false;
    }

    public boolean isUnPark(Object vehicle) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        for (int slotNumber = 0; slotNumber < this.vehicles.size(); slotNumber++)
            if (this.vehicles.contains(parkingTimeSlot)) {
                this.vehicles.set(slotNumber, null);
                for (ParkingLotHandler parkingOwner : parkingLotHandlerList) {
                    parkingOwner.parkingIsEmpty();
                    return true;
                }
            }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
    }


    public void parkVehicle(int slot,Object vehicle) {
        if (this.parkingLotCapacity==this.vehicleSlotMap.size()) {
            for(ParkingLotHandler parkingOwner:parkingLotHandlerList)
                parkingOwner.parkingIsFull();
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        }
        vehicleSlotMap.put(slot,vehicle);
    }

    public int initializeParkingSlot() {
        IntStream.range(0,this.parkingLotCapacity).forEach(slots->vehicles.add(null));
        return vehicles.size();
    }

    public int getParkingSlot() {
        ArrayList<Integer> slotList = getSlot();
        for (int slot = 0; slot < slotList.size(); slot++) {
            if (slotList.get(0) == (slot))
                return slot;
        }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
    }

    public ParkingLotAttender getParkingLotAttendant(ParkingLotAttender attendant) {
        ParkingOwner parkingOwner= (ParkingOwner)parkingLotHandlerList.get(0);
        parkVehicle(parkingOwner.getParkingSlot(),attendant.getVehicle());
        return attendant;
    }

    public ParkingLotAttender getMyVehicle(ParkingLotAttender attendant) {
        if(vehicleSlotMap.containsValue(attendant.getVehicle()))
            return attendant;
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.NO_SUCH_ATTENDANT, "No Attendant Found");
    }


    public int findVehicle(Object vehicle) {
        ParkingTimeSlot parkingTimeSlot=new ParkingTimeSlot(vehicle);
        if (this.vehicles.contains(parkingTimeSlot))
            return this.vehicles.indexOf(parkingTimeSlot);
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
    }

    public ArrayList getSlot()
    {
        ArrayList slots=new ArrayList();
        for(int slot=0;slot<this.parkingLotCapacity;slot++)
        {
            if(this.vehicles.get(slot)==null)
                slots.add(slot);
        }
        return slots;
    }

    public boolean setTime(Object vehicle) {
        ParkingTimeSlot parkingTimeSlot=new ParkingTimeSlot(vehicle);
        for(int i=0;i<this.vehicles.size();i++) {
            if(this.vehicles.get(i).time!=null && this.vehicles.contains(parkingTimeSlot))
                return true;
        }
        return false;
    }
}
