/**************************************************************************************************************
 * @Purpose:Providing Type Of Driver And Vehicle
 * @Author:vidyadhar
 * @Date-28/05/20
 * *************************************************************************************************************/
package com.parkinglotsystem.strategy;

import com.parkinglotsystem.enums.DriverType;
import com.parkinglotsystem.enums.VehicleType;

public class ParkingFactory {

    /**
     * @Purpose:-Which Provide Type Of Driver And Vehicle As Per Recuirment
     * @return:-Returns Vehicle And Driver
     */
    public static ParkingStrategy getParkingStrategy(Enum type) {
        if(type.equals(DriverType.HANDICAP_DRIVER))
            return new HandicapDriver();
        else if(type.equals(VehicleType.LARGE_VEHICLE))
            return new LargeVehicle();
        else if(type.equals(VehicleType.SMALL_VEHICLE))
            return new SmallVehicle();
        return new NormalDriver();
    }
}
