package com.parkinglotsystem.strategy;

import com.parkinglotsystem.ParkingLot;

import java.util.List;

public interface ParkingStrategy {

    public ParkingLot getParkingLot(List<ParkingLot> parkingLotList);
}

