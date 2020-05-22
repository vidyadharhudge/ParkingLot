import com.parkinglotsystem.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {

    ParkingLotSystem parkingLotSystem;

    @Before
    public void setup() {
        parkingLotSystem = new ParkingLotSystem();
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ThenReturnTrue() {
        boolean vehicleIsPark = parkingLotSystem.isPark(new Object());
        Assert.assertTrue(vehicleIsPark);
    }
}