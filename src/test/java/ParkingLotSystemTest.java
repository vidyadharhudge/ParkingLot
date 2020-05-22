import com.parkinglotsystem.ParkingLotSystem;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {
    ParkingLotSystem parkingLotSystem;
    Object vehicle;

    @Before
    public void setup() {
        parkingLotSystem = new ParkingLotSystem(2);
        vehicle=new Object();
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ThenReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle);
        boolean vehicleIsPark = parkingLotSystem.isPark(vehicle);
        Assert.assertTrue(vehicleIsPark);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParked_ThenReturnFalse() {
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.isPark(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not In Parking", e.getMessage());
        }
    }

    @Test
    public void givenVehicleUnPark_WhenVehicleIsParked_ThenReturnTrue()
    {
        parkingLotSystem.parkVehicle(vehicle);
        boolean vehicleIsUnPark=parkingLotSystem.isUnPark(vehicle);
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
    public void givenParkingLot_WhenFull_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.isPark(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("PARKING_IS_FULL", e.getMessage());
        }
    }

}