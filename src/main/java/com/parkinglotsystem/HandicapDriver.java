package com.parkinglotsystem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum HandicapDriver implements ParkingStrategy {
   HANDICAP_DRIVER ;

    @Override
    public ParkingLot getParkingLot(List<ParkingLot> parkingLotList) {
        List<ParkingLot>lotList=parkingLotList;
        Collections.sort(lotList, Comparator.comparing(list->list.getSlot().size(),Comparator.naturalOrder()));
        return lotList.get(0);
    }
}
