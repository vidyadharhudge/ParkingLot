package com.parkinglotsystem.exception;

public class ParkingLotSystemException extends RuntimeException {

    public enum ExceptionType {
        VEHICLE_NOT_FOUND,PARKING_IS_FULL,NO_SUCH_ATTENDANT;
    }
    public ExceptionType type;
    public ParkingLotSystemException(ExceptionType type,String message) {
        super((message));
        this.type=type;
    }

}
