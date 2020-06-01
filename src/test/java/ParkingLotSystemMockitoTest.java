import com.parkinglotsystem.ParkingLot;
import com.parkinglotsystem.ParkingLotSystem;
import com.parkinglotsystem.enums.DriverType;
import com.parkinglotsystem.enums.VehicleType;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.observer.AirportSecurity;
import com.parkinglotsystem.observer.ParkingOwner;
import com.parkinglotsystem.observer.Vehicle;
import com.parkinglotsystem.strategy.ParkingFactory;
import com.parkinglotsystem.strategy.ParkingStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingLotSystemMockitoTest {
    private ParkingLot parkingLot;
    Vehicle vehicle;
    ParkingLotSystem parkingLotSystem;
    ParkingOwner parkingOwner;
    AirportSecurity airportSecurity;

    @Before
    public void setup() {
        parkingLot = mock(ParkingLot.class);
        vehicle = new Vehicle();
        parkingLotSystem = new ParkingLotSystem(1);
        parkingOwner = new ParkingOwner();
        airportSecurity = new AirportSecurity();
    }

    @Test
    public void givenParkingLotSystem_WhenAddLots_ShouldReturnTrue() {
        parkingLotSystem.addLots(parkingLot);
        boolean isLotAdded = parkingLotSystem.isLotAdd(parkingLot);
        Assert.assertTrue(isLotAdded);
    }

    @Test
    public void givenParkingLotSystem_WhenNotAddLots_ShouldReturnFalse() {
        ParkingLot parkingLot1 = mock(ParkingLot.class);
        boolean isLotAdded = parkingLotSystem.isLotAdd(parkingLot1);
        Assert.assertFalse(isLotAdded);
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailableAfterFull_ThenShouldReturnFalse() {
        ParkingOwner parkingOwner = mock(ParkingOwner.class);
        Vehicle vehicle2 = new Vehicle();
        parkingLot.registerHandler(parkingOwner);
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "PQR");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "PQR");
            parkingLot.isPark(vehicle);
            parkingLot.isPark(vehicle2);
        } catch (ParkingLotSystemException e) {
            parkingLotSystem.isUnPark(vehicle);
            when(parkingOwner.parkingFull()).thenReturn(false);
            Assert.assertFalse(parkingOwner.parkingFull());
        }
    }

    @Test
    public void givenParkingLot_WhenLargeVehicleParked_ThenShouldReturnTrue() {

        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot);

        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setParkingLotCapacity(10);
        parkingLot1.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot1);

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setParkingLotCapacity(10);
        parkingLot2.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot2);

        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();
        Vehicle vehicle5 = new Vehicle();
        Vehicle vehicle6 = new Vehicle();

        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "PQR");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
        parkingLotSystem.parkVehicle(VehicleType.LARGE_VEHICLE, vehicle4, "XYZ");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5, "PQR");
        parkingLotSystem.parkVehicle(VehicleType.SMALL_VEHICLE,vehicle6,"MNO");
        when(parkingLot.isPark(vehicle4)).thenReturn(true);
        boolean isVehiclePark = parkingLotSystem.isPark(vehicle4);
        Assert.assertTrue(isVehiclePark);
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleParked_ThenShouldReturnLocationOfWhiteCar() {

        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot);

        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setParkingLotCapacity(10);
        parkingLot1.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot1);

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setParkingLotCapacity(10);
        parkingLot2.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot2);

        Vehicle vehicle1 = new Vehicle("black");
        Vehicle vehicle2 = new Vehicle("white");
        Vehicle vehicle3 = new Vehicle("white");
        Vehicle vehicle4 = new Vehicle("white");
        Vehicle vehicle5 = new Vehicle("blue");

        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "PQR");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4, "PQR");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5, "ABC");
        List<Integer> colourList=new ArrayList<>(3);
        when(parkingLot.findByColour("white")).thenReturn(colourList);
        List whiteCarList = parkingLotSystem.findVehicleByColour("white");
        Assert.assertEquals(3, whiteCarList.size());
    }

    @Test
    public void givenParkingLot_WhenVehicleParkOnLotPosition_ThenReturnTrue() {
        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLotSystem.addLots(parkingLot1);
        parkingLot1.setParkingLotCapacity(1);
        parkingLot1.initializeParkingSlot();
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            when((parkingLot).isPark(vehicle)).thenReturn(true);
            boolean isVehiclePark = parkingLotSystem.isPark(vehicle);
            Assert.assertTrue(isVehiclePark);
    }

    @Test
    public void givenDriverTypeIsHandicap_ShouldReturnNearestLot() {
        ParkingStrategy parkingStrategy=mock(ParkingStrategy.class);
         ParkingFactory.getParkingStrategy(DriverType.HANDICAP_DRIVER);
        List<ParkingLot> lotList = new ArrayList<>();
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotCapacity(10);
        lot.initializeParkingSlot();
        lotList.add(lot);
        ParkingLot parkingLot = null;
        when((parkingStrategy).getParkingLot(lotList)).thenReturn(lot);
        parkingLot = parkingStrategy.getParkingLot(lotList);
        Assert.assertEquals(lot, parkingLot);
    }
}