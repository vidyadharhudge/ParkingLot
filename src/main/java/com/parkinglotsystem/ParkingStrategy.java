package com.parkinglotsystem;

import java.util.List;

public interface ParkingStrategy {

    public ParkingLot getParkingLot(List<ParkingLot>parkingLotList);
}
