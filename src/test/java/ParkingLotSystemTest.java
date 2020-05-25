import com.parkinglotsystem.AirportSecurity;
import com.parkinglotsystem.ParkingLotSystem;
import com.parkinglotsystem.ParkingOwner;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

   /* @Test
    public void givenParkingLot_WhenVehicleIsNotParked_ThenReturnFalse() {
           parkingLotSystem.setParkingLotCapacity(2);
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.isPark(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Already Parked", e.getMessage());
        }
    }
*/
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
}





