/**************************************************************************************************************
 * @Purpose:Providing Small Vehicle Which Implementing ParkingStrategy Which Getting  Space In Lot
 * @Author:vidyadhar
 * @Date-27/05/20
 * *************************************************************************************************************/
package com.parkinglotsystem.strategy;
import com.parkinglotsystem.ParkingLot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Purpose:-Which Occupy Space In Lot
 * @return:-Returns Position Of Vehicle
 */
public class SmallVehicle implements ParkingStrategy {
    @Override
    public ParkingLot getParkingLot(List<ParkingLot> parkingLotList) {
        List<ParkingLot>lotList=new ArrayList<>(parkingLotList);
        Collections.sort(lotList, Comparator.comparing(list->list.getSlot().size(),Comparator.reverseOrder()));
        return lotList.get(0);
    }
}
