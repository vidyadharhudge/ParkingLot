package com.parkinglotsystem.strategy;

import com.parkinglotsystem.ParkingLot;
import com.parkinglotsystem.exception.ParkingLotSystemException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HandicapDriver implements ParkingStrategy{
    @Override
    public ParkingLot getParkingLot(List<ParkingLot> parkingLotList) {
        ParkingLot lot = parkingLotList.stream()
                .filter(parkingLot -> parkingLot.getSlot().size() > 0)
                .findFirst()
                .orElseThrow(() -> new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle not found."));
       return lot;
    }
}
