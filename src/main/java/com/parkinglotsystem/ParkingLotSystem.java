package com.parkinglotsystem;
import com.parkinglotsystem.exception.ParkingLotSystemException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    private int parkingLotCapacity;
    private List<ParkingLotHandler> parkingLotHandlerList;
    private List<Object>vehicles;
    Map<Integer,Object>vehicleSlotMap=new HashMap();

    public ParkingLotSystem(int parkingLotCapacity) {
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
        if (this.parkingLotCapacity==this.vehicles.size()) {
            for(ParkingLotHandler parkingOwner:parkingLotHandlerList)
            parkingOwner.parkingIsFull();
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        }
        if (isPark(vehicle))
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_ALREADY_PARKED, "Vehicle Is Already Parked");
        this.vehicles.add(vehicle);
    }

    public void parkVehicle(int slot,Object vehicle) {
        if (this.parkingLotCapacity==this.vehicles.size()) {
            for(ParkingLotHandler parkingOwner:parkingLotHandlerList)
                parkingOwner.parkingIsFull();
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        }
        vehicleSlotMap.put(slot,vehicle);
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

    public int initializeParkingSlot()
    {
        IntStream.range(0,this.parkingLotCapacity).forEach(slots->vehicles.add(null));
        return vehicles.size();
    }

    public void isParkVehicles(int slot,Object vehicle)
    {
        if(isPark(vehicle))
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_IS_FULL, "PARKING_IS_FULL");
        this.vehicles.set(slot,vehicle);
    }

    public int findVehicle(Object vehicle)
    {
      if(this.vehicles.contains(vehicle))
          return this.vehicles.indexOf(vehicle);
      throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle Is Not In Parking");
  }

  public ArrayList getSlot()
  {
      ArrayList<Integer>slots=new ArrayList<>();
      for(int slot=0;slot<this.parkingLotCapacity;slot++)
      {
          if(this.vehicles.get(slot)==null)
              slots.add(slot);
      }
      return slots;
  }

}
