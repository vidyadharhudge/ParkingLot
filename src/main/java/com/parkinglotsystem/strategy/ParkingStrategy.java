/**************************************************************************************************************
 * @Purpose:Methode For Implementing Parking Lot List
 * @Author:vidyadhar
 * @Date-28/05/20
 * *************************************************************************************************************/
package com.parkinglotsystem.strategy;

import com.parkinglotsystem.ParkingLot;
import java.util.List;

public interface ParkingStrategy {

    public ParkingLot getParkingLot(List<ParkingLot> parkingLotList);
}

