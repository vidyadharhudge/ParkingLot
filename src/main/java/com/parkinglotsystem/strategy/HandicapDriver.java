/**************************************************************************************************************
 * @Purpose:Providing Handicap Driver Which Implementing ParkingStrategy Which Ocuppy Slot
 * @Author:vidyadhar
 * @Date-27/05/20
 * *************************************************************************************************************/
package com.parkinglotsystem.strategy;

import com.parkinglotsystem.ParkingLot;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import java.util.List;

/**
 * @Purpose:-Which Occupy Nearest Location In Slot And Occupy First
 * @return:-Returns Lot
 */
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
