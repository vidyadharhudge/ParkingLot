package com.parkinglotsystem.strategy;

import com.parkinglotsystem.ParkingLot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SmallVehicle implements ParkingStrategy {
    @Override
    public ParkingLot getParkingLot(List<ParkingLot> parkingLotList) {
        List<ParkingLot>lotList=new ArrayList<>(parkingLotList);
        Collections.sort(lotList, Comparator.comparing(list->list.getSlot().size(),Comparator.reverseOrder()));
        return lotList.get(0);
    }
}
