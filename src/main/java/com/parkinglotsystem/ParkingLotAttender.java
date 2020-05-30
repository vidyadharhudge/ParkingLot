/**************************************************************************************************************
 * @Purpose:-Use For Attending The Parked Vehicle
 * @Author:vidyadhar
 * @Date-29/05/20
 * *************************************************************************************************************/
package com.parkinglotsystem;

import com.parkinglotsystem.observer.Vehicle;

public class ParkingLotAttender
{
    private Vehicle vehicle;

    /**
     * @Purpose:-To initialize vehicle
     */
    public ParkingLotAttender(Vehicle vehicle) {
        this.vehicle=vehicle;
    }

    /**
     * @Purpose:-To Return Vehicle
     * @return :-vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }
}
