import com.parkinglotsystem.*;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.observer.AirportSecurity;
import com.parkinglotsystem.observer.ParkingOwner;
import com.parkinglotsystem.observer.Vehicle;
import com.parkinglotsystem.strategy.DriverType;
import com.parkinglotsystem.strategy.ParkingFactory;
import com.parkinglotsystem.strategy.ParkingStrategy;
import com.parkinglotsystem.strategy.VehicleType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystemTest {
    ParkingLot parkingLot;
    ParkingLotSystem parkingLotSystem;
    Vehicle vehicle;
    ParkingOwner parkingOwner;
    AirportSecurity airportSecurity;

    @Before
    public void setup() {
        parkingLot = new ParkingLot(1);
        parkingLotSystem = new ParkingLotSystem(1);
        vehicle = new Vehicle();
        parkingOwner = new ParkingOwner();
        airportSecurity = new AirportSecurity();
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ThenReturnTrue() {
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            boolean vehicleIsPark = parkingLot.isPark(vehicle);
            Assert.assertTrue(vehicleIsPark);
        } catch (ParkingLotSystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotIsFull_WhenVehicleIsParked_ThenReturnTrue() {

        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            Vehicle secondVehicle = new Vehicle();
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, secondVehicle);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("PARKING_IS_FULL", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenUnParkedVehicleWhichIsNotParked_ThenReturnFalse() {
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            boolean isVehicleParked = parkingLot.isUnPark(vehicle);
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not In Parking", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_CheckVehicleIsNotPrsent_ThenShouldThrowException() {
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, null);
            boolean isVehicleParked = parkingLot.isPark(vehicle);
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not In Parking", e.getMessage());
        }
    }


    @Test
    public void givenParkingLot_WhenVehicleIsNotParked_ThenReturnException() {
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.isUnPark(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not In Parking", e.getMessage());
        }
    }


    @Test
    public void givenParkingLot_WhenFull_ThenShouldInformOwner() {
        try {
            parkingLot.registerHandler(parkingOwner);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle());
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertTrue(parkingOwner.parkingFull());
        }
    }

    @Test
    public void givenParkingLotCapacity2_ShouldBeAbleToPark2Vehicles_ThenShouldInformOwner() {
        Vehicle vehicle2 = new Vehicle();
        parkingLot.setParkingLotCapacity(2);
        try {
            parkingLot.registerHandler(parkingOwner);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
            boolean isParked1 = parkingLot.isPark(vehicle);
            boolean isParked2 = parkingLot.isPark(vehicle2);
            Assert.assertTrue(isParked1 && isParked2);
        } catch (ParkingLotSystemException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ThenShouldInformAirportSecurity() {
        try {
            parkingLot.registerHandler(airportSecurity);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle());
        } catch (ParkingLotSystemException e) {
        }
        Assert.assertTrue(airportSecurity.parkingFull());
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailableAfterFull_ThenShouldReturnTrue() {
        Vehicle vehicle2 = new Vehicle();
        parkingLot.registerHandler(parkingOwner);
        try {
            parkingLot.isPark(vehicle);
            parkingLot.isPark(vehicle2);
        } catch (ParkingLotSystemException e) {
            parkingLotSystem.isUnPark(vehicle);
            Assert.assertFalse(parkingOwner.parkingFull());
        }
    }

    @Test
    public void givenParkingSlotSystem_WhenParkingCapacitySet_ShouldReturnCapacity() {
        int parkingLotCapacity = parkingLot.setParkingLotCapacity(100);
        Assert.assertEquals(100, parkingLotCapacity);
    }

    @Test
    public void givenParkingLotSlot_WhenCarCome_ThenShouldAttenderParkCar() {
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.registerHandler(parkingOwner);
            ParkingLotAttender parkingLotAttender = new ParkingLotAttender(vehicle);
            ParkingLotAttender attender = parkingLot.getParkingLotAttendant(parkingLotAttender);
            Assert.assertEquals(attender, parkingLotAttender);
        } catch (ParkingLotSystemException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenParkingFull_ThenAttenderShouldThrowException() {
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.registerHandler(parkingOwner);
            ParkingLotAttender parkingLotAttender1 = new ParkingLotAttender(vehicle);
            ParkingLotAttender parkingLotAttender2 = new ParkingLotAttender(new Vehicle());
            ParkingLotAttender parkingLotAttender3 = new ParkingLotAttender(vehicle);

            parkingLot.getParkingLotAttendant(parkingLotAttender1);
            parkingLot.getParkingLotAttendant(parkingLotAttender2);
            parkingLot.getParkingLotAttendant(parkingLotAttender3);

        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("PARKING_IS_FULL", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_ShouldReturnAttendant() {
        try {
            parkingLot.registerHandler(parkingOwner);
            parkingLot.initializeParkingSlot();
            ParkingLotAttender parkingLotAttender = new ParkingLotAttender(vehicle);
            parkingLot.getParkingLotAttendant(parkingLotAttender);
            ParkingLotAttender myAttendant = parkingLot.getMyVehicle(parkingLotAttender);
            Assert.assertEquals(parkingLotAttender, myAttendant);
        } catch (ParkingLotSystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenAttenderNotAvailable_ThenThrowException() {
        try {
            parkingLot.registerHandler(parkingOwner);
            ParkingLotAttender parkingLotAttender = new ParkingLotAttender(vehicle);
            parkingLot.getParkingLotAttendant(parkingLotAttender);
            ParkingLotAttender myAttendant = parkingLot.getMyVehicle(new ParkingLotAttender(new Vehicle()));
            Assert.assertEquals(parkingLotAttender, myAttendant);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("No Attendant Found", e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleFound_ShouldReturnVehicleSlot() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle());
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        int slotNumber = parkingLot.findVehicle(this.vehicle);
        Assert.assertEquals(1, slotNumber);
    }

    @Test
    public void givenParkingLot_WhenTimeIsSet_ThenReturnTrue() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
        boolean isTimeSet = parkingLot.setTime(vehicle);
        Assert.assertTrue(isTimeSet);
    }

    @Test
    public void givenParkingLotSystem_WhenAddLot_ThenReturnTrue() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot);
        boolean isLotAdded = parkingLotSystem.isLotAdd(parkingLot);
        Assert.assertTrue(isLotAdded);
    }

    @Test
    public void givenParkingLotSystem_WhenLotsAdded_ThenReturnTrue() {
        parkingLot.setParkingLotCapacity(10);
        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLot.initializeParkingSlot();
        parkingLot1.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot);
        parkingLotSystem.addLots(parkingLot1);
        boolean isLotAdded = parkingLotSystem.isLotAdd(parkingLot1);
        Assert.assertTrue(isLotAdded);
    }

    @Test
    public void givenParkingLot_WhenVehicleParkOnLotPosition_ThenReturnTrue() {
        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLotSystem.addLots(parkingLot1);
        parkingLot1.setParkingLotCapacity(1);
        parkingLot1.initializeParkingSlot();
        try {
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            boolean isVehiclePark = parkingLotSystem.isPark(vehicle);
            Assert.assertTrue(isVehiclePark);
        } catch (ParkingLotSystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehiclePark_ThenShouldParkVehicleEvenly() {

        parkingLot.setParkingLotCapacity(3);
        parkingLot.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot);
        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLot1.setParkingLotCapacity(3);
        parkingLot1.initializeParkingSlot();
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();

        parkingLotSystem.addLots(parkingLot1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);

        boolean isParked1 = parkingLotSystem.isPark(vehicle);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);

        boolean isParked2 = parkingLotSystem.isPark(vehicle1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);

        boolean isParked3 = parkingLotSystem.isPark(vehicle2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3);

        boolean isParked4 = parkingLotSystem.isPark(vehicle3);
        Assert.assertTrue(isParked1 && isParked2 && isParked3 && isParked4);
    }

    @Test
    public void givenParkingLotSystem_WhenEvenlyDistrubuated_ThenUParkVehicle() {

        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot);
        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLot1.setParkingLotCapacity(10);
        parkingLot1.initializeParkingSlot();
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        parkingLotSystem.addLots(parkingLot1);
        try {
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle);
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
            boolean isUnPark = parkingLotSystem.isUnPark(vehicle);
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3);
            Assert.assertTrue(isUnPark);
        } catch (ParkingLotSystemException e) {
        }
    }

    @Test
    public void givenDriverTypeIsHandicap_ShouldReturnNearestLot() {
        ParkingStrategy parkingStrategy = ParkingFactory.getParkingStrategy(DriverType.HANDICAP_DRIVER);
        List<ParkingLot> lotList = new ArrayList<>();
        ParkingLot lot = new ParkingLot(1);
        lot.setParkingLotCapacity(10);
        lot.initializeParkingSlot();
        lotList.add(lot);
        ParkingLot parkingLot = null;
        parkingLot = parkingStrategy.getParkingLot(lotList);
        Assert.assertEquals(lot, parkingLot);
    }

    @Test
    public void givenDriver_WhenHandicap_ThenShouldParkVehicleAtNearestPlace() {

        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot);

        ParkingLot parkingLot1 = new ParkingLot(10);
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
        Vehicle vehicle4=new Vehicle();
        Vehicle vehicle5=new Vehicle();

        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3);
        parkingLotSystem.parkVehicle(DriverType.HANDICAP_DRIVER,vehicle4);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5);
        Assert.assertTrue(true);

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
        Vehicle vehicle4=new Vehicle();
        Vehicle vehicle5=new Vehicle();

        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3);
        parkingLotSystem.parkVehicle(VehicleType.LARGE_VEHICLE,vehicle4);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5);
        boolean isVehiclePark=parkingLotSystem.isPark(vehicle4);
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
        Vehicle vehicle4 = new Vehicle("blue");
        Vehicle vehicle5 = new Vehicle("white");

        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5);
        List whiteCarList = parkingLotSystem.findVehicleByColour("white");
        Assert.assertEquals(3, whiteCarList.size());

    }
}







