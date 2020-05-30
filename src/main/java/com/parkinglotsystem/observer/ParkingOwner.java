/**************************************************************************************************************
 * @Purpose:To Inform Parking Full To Parking Owner
 * @Author:vidyadhar
 * @Date-22/05/20
 * *************************************************************************************************************/
package com.parkinglotsystem.observer;

public class ParkingOwner implements ParkingLotHandler {

    private boolean parkingLotCapacity;
    private int count=0;

    /**
     * purpose;-To Change Parking Capacity To True If Parking Is Full
     */
    @Override
    public void parkingIsFull() {
        this.parkingLotCapacity=true;
    }

    /**
     * purpose;-To If Parking Capacity fullThen Return Capacity
     * @Return:-Parking Lot Capacity
     */
    public boolean parkingFull() {
        return this.parkingLotCapacity;
    }

    /**
     * purpose;-It Is Used For Counting Parking Slot
     * @Return:-Count For Slot
     */
    public  int getParkingSlot() {
        return count++;
    }
}
