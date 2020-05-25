import com.parkinglotsystem.AirportSecurity;
import com.parkinglotsystem.ParkingLotAttender;
import com.parkinglotsystem.ParkingLotSystem;
import com.parkinglotsystem.ParkingOwner;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ParkingLotSystemTest {
    ParkingLotSystem parkingLotSystem;
    Object vehicle;
    ParkingOwner parkingOwner;
    AirportSecurity airportSecurity;

    @Before
    public void setup() {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicle = new Object();
        parkingOwner = new ParkingOwner();
        airportSecurity = new AirportSecurity();
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ThenReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle);
        boolean vehicleIsPark = parkingLotSystem.isPark(vehicle);
        Assert.assertTrue(vehicleIsPark);
    }

    @Test
    public void givenParkingLot_WhenVehicleIstParked_ThenReturnTrue() {

        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.isPark(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not In Parking", e.getMessage());
        }
    }

    @Test
    public void givenVehicleUnPark_WhenVehicleIsParked_ThenReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle);
        boolean vehicleIsUnPark = parkingLotSystem.isUnPark(vehicle);
        Assert.assertTrue(vehicleIsUnPark);
    }

    @Test
    public void givenVehicleUnParked_WhenVehicleIsNotParked_ThenReturnException() {
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.isUnPark(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not In Parking", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ThenShouldInformOwner() {
        try {
            parkingLotSystem.registerHandler(parkingOwner);
            parkingLotSystem.parkVehicle(new Object());
            parkingLotSystem.parkVehicle(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertTrue(parkingOwner.parkingFull());
        }
    }

    @Test
    public void givenParkingLotCapacity2_ShouldBeAbleToPark2Vehicles_ThenShouldInformOwner() {
        Object vehicle2 = new Object();
        parkingLotSystem.setParkingLotCapacity(2);
        try {
            parkingLotSystem.registerHandler(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(vehicle2);
            boolean isParked1 = parkingLotSystem.isPark(vehicle);
            boolean isParked2 = parkingLotSystem.isPark(vehicle2);
            Assert.assertTrue(isParked1 && isParked2);
        } catch (ParkingLotSystemException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ThenShouldInformAirportSecurity() {
        try {
            parkingLotSystem.registerHandler(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
        }
        Assert.assertTrue(airportSecurity.parkingFull());
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailableAfterFull_ThenShouldReturnTrue() {
        Object vehicle2 = new Object();
        parkingLotSystem.registerHandler(parkingOwner);
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(vehicle2);
        } catch (ParkingLotSystemException e) {
            parkingLotSystem.isUnPark(vehicle);
            Assert.assertFalse(parkingOwner.parkingFull());
        }
    }

    @Test
    public void givenParkingSlotSystem_WhenParkingCapacitySet_ShouldReturnCapacity() {
        int parkingLotCapacity = parkingLotSystem.setParkingLotCapacity(100);
        Assert.assertEquals(100, parkingLotCapacity);
    }

    @Test
    public void givenParkingLotSlot_WhenCarCome_ThenShouldAttenderParkCar() {
        try {
            parkingLotSystem.registerHandler(parkingOwner);
            ParkingLotAttender parkingLotAttender = new ParkingLotAttender(vehicle);
            ParkingLotAttender attender = parkingLotSystem.getParkingLotAttendant(parkingLotAttender);
            Assert.assertEquals(attender, parkingLotAttender);
        } catch (ParkingLotSystemException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenParkingFull_ThenAttenderShouldThrowException() {
        try {
            parkingLotSystem.registerHandler(parkingOwner);
            ParkingLotAttender parkingLotAttender1 = new ParkingLotAttender(vehicle);
            ParkingLotAttender parkingLotAttender2 = new ParkingLotAttender(new Object());
            ParkingLotAttender parkingLotAttender3 = new ParkingLotAttender(vehicle);

            parkingLotSystem.getParkingLotAttendant(parkingLotAttender1);
            parkingLotSystem.getParkingLotAttendant(parkingLotAttender2);
            parkingLotSystem.getParkingLotAttendant(parkingLotAttender3);

        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("PARKING_IS_FULL", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_ShouldReturnAttendant() {
        try {
            parkingLotSystem.registerHandler(parkingOwner);
            ParkingLotAttender parkingLotAttender = new ParkingLotAttender(vehicle);
            parkingLotSystem.getParkingLotAttendant(parkingLotAttender);
            ParkingLotAttender myAttendant = parkingLotSystem.getMyVehicle(parkingLotAttender);
            Assert.assertEquals(parkingLotAttender, myAttendant);
        } catch (ParkingLotSystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenAttenderNotAvailable_ThenThrowException() {
        try {
            parkingLotSystem.registerHandler(parkingOwner);
            ParkingLotAttender parkingLotAttender = new ParkingLotAttender(vehicle);
            parkingLotSystem.getParkingLotAttendant(parkingLotAttender);
            ParkingLotAttender myAttendant = parkingLotSystem.getMyVehicle(new ParkingLotAttender(new Object()));
            Assert.assertEquals(parkingLotAttender, myAttendant);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("No Attendant Found", e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleFound_ShouldReturnVehicleSlot() {
        parkingLotSystem.setParkingLotCapacity(10);
        parkingLotSystem.initializeParkingSlot();
        ArrayList<Integer> slot = parkingLotSystem.getSlot();
        parkingLotSystem.isParkVehicles(slot.get(0), new Object());
        parkingLotSystem.isParkVehicles(slot.get(1), vehicle);
        int slotNumber = parkingLotSystem.findVehicle(this.vehicle);
        Assert.assertEquals(1, slotNumber);
    }

}



