/**************************************************************************************************************
 * @Purpose:To Inform Parking Full To Airport Security
 * @Author:vidyadhar
 * @Date-22/05/20
 * *************************************************************************************************************/
package com.parkinglotsystem.observer;

public class AirportSecurity implements ParkingLotHandler {
    private boolean parkingLotCapacity;

    /**
     * purpose;-To Change Parking Capacity To True If Parking Is Full
     */
    @Override
    public void parkingIsFull() {
        this.parkingLotCapacity=true;
    }

    /**
     * purpose;-To Change Parking Capacity To False If Parking Is Empty
     */

    /**
     * purpose;-To If Parking Capacity Full Then Return Capacity
     * @Return:-Parking Lot Capacity
     */
    public boolean parkingFull() {
        return this.parkingLotCapacity;
    }

    /**
     * purpose;-To If Parking Capacity Empty Then Return Capacity
     * @Return:-Parking Lot Capacity
     */
    public boolean parkingEmpty() {
        return this.parkingLotCapacity;
    }
}
