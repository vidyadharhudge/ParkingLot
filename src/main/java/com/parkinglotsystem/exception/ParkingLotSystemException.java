package com.parkinglotsystem.exception;

public class ParkingLotSystemException extends RuntimeException {

    public enum ExceptionType {
        VEHICLE_NOT_FOUND;
    }
    public ExceptionType type;
    public ParkingLotSystemException(ExceptionType type,String message) {
        super((message));
        this.type=type;
    }

}
