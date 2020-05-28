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
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            boolean vehicleIsPark = parkingLot.isPark(vehicle);
            Assert.assertTrue(vehicleIsPark);
        } catch (ParkingLotSystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenParkedVehicleIsUnParked_ThenReturnTrue() {
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            boolean vehicleIsPark = parkingLot.isUnPark(vehicle);
            Assert.assertTrue(vehicleIsPark);
        } catch (ParkingLotSystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotIsFull_WhenVehicleIsParked_ThenReturnTrue() {
        parkingLot.setParkingLotCapacity(2);
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            Vehicle secondVehicle = new Vehicle();
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, secondVehicle, "PQR");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle(), "ABC");
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("PARKING_IS_FULL", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenUnParkedVehicleWhichIsNotParked_ThenReturnFalse() {
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            boolean isVehicleParked = parkingLot.isUnPark(new Vehicle());
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not In Parking", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_CheckVehicleIsNotPresent_ThenShouldThrowException() {
        parkingLot.setParkingLotCapacity(2);
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, null, "ABC");
            boolean isVehicleParked = parkingLot.isPark(vehicle);
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("PARKING_IS_FULL", e.getMessage());
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
    public void givenParkingLot_WhenFull_ThenShouldThrowException() {
        parkingLot.setParkingLotCapacity(2);
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.registerHandler(parkingOwner);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle(), "ABC");
            Vehicle secondVehicle = new Vehicle();
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, secondVehicle, "PQR");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle(), "ABC");
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("PARKING_IS_FULL", e.getMessage());
        }
    }


    @Test
    public void givenParkingLot_WhenFull_ThenShouldInformOwner() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.initializeParkingSlot();
        Vehicle vehicle = new Vehicle();
        ParkingOwner parkingOwner = new ParkingOwner();
        try {
            parkingLot.registerHandler(parkingOwner);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle(), "ABC");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle(), "ABC");
        } catch (ParkingLotSystemException e) {
            Assert.assertTrue(parkingOwner.parkingFull());
        }
    }

    @Test
    public void givenParkingLotCapacity2_ShouldBeAbleToPark2Vehicles_ThenShouldInformOwner() {
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.initializeParkingSlot();
        parkingLot.setParkingLotCapacity(2);
        Vehicle vehicle2 = new Vehicle();
        try {
            parkingLot.registerHandler(parkingOwner);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "ABC");
            boolean isParked1 = parkingLot.isPark(vehicle);
            boolean isParked2 = parkingLot.isPark(vehicle2);
            Assert.assertTrue(isParked1 && isParked2);
        } catch (ParkingLotSystemException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ThenShouldInformAirportSecurity() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.registerHandler(airportSecurity);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle(), "PQR");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
        } catch (ParkingLotSystemException e) {
        }
        Assert.assertTrue(airportSecurity.parkingFull());
    }

    @Test
    public void givenParkingLot_WhenHavingSpace_ThenShouldInformAirportSecurity() {
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.initializeParkingSlot();
        try {
            parkingLot.registerHandler(airportSecurity);
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle(), "ABC");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle(), "PQR");
            parkingLot.isUnPark(vehicle);
        } catch (ParkingLotSystemException e) {
        }
        Assert.assertFalse(airportSecurity.parkingEmpty());
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
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, new Vehicle(), "PQR");
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "PQR");
        int slotNumber = parkingLot.findVehicle(this.vehicle);
        Assert.assertEquals(1, slotNumber);
    }

    @Test
    public void givenParkingLot_WhenTimeIsSet_ThenReturnTrue() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "PPQR");
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
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
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
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "PQR");

        boolean isParked1 = parkingLotSystem.isPark(vehicle);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1, "ABC");

        boolean isParked2 = parkingLotSystem.isPark(vehicle1);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "PQR");

        boolean isParked3 = parkingLotSystem.isPark(vehicle2);
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3, "ABC");

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
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            boolean isUnPark = parkingLotSystem.isUnPark(vehicle);
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "ABC");
            parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
            Assert.assertTrue(isUnPark);
        } catch (ParkingLotSystemException e) {
        }
    }

    @Test
    public void givenDriverTypeIsHandicap_ShouldReturnNearestLot() {
        ParkingStrategy parkingStrategy = ParkingFactory.getParkingStrategy(DriverType.HANDICAP_DRIVER);
        List<ParkingLot> lotList = new ArrayList<>();
        ParkingLot lot = new ParkingLot();
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
        Vehicle vehicle4 = new Vehicle();
        Vehicle vehicle5 = new Vehicle();

        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "PQR");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
        parkingLotSystem.parkVehicle(DriverType.HANDICAP_DRIVER, vehicle4, "XYZ");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5, "PQR");
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
        Vehicle vehicle4 = new Vehicle();
        Vehicle vehicle5 = new Vehicle();

        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "PQR");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
        parkingLotSystem.parkVehicle(VehicleType.LARGE_VEHICLE, vehicle4, "XYZ");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5, "PQR");
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
        Vehicle vehicle4 = new Vehicle("blue");
        Vehicle vehicle5 = new Vehicle("white");

        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "PQR");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4, "PQR");
        parkingLotSystem.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5, "ABC");
        List whiteCarList = parkingLotSystem.findVehicleByColour("white");
        Assert.assertEquals(3, whiteCarList.size());
    }

    @Test
    public void givenParkingLotSystem_WhenCarColourIsBle_ThenReturnLot() {
        try {
            parkingLot.setParkingLotCapacity(3);
            parkingLot.initializeParkingSlot();
            Vehicle vehicle1 = new Vehicle("black");
            Vehicle vehicle2 = new Vehicle("white");
            Vehicle vehicle3 = new Vehicle("blue");

            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "PQR");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3, "XYZ");
            List<Integer> blueCarList = parkingLot.findByColour("blue");
            List<Integer> result = new ArrayList<>();
            blueCarList.add(2);
            Assert.assertEquals(blueCarList, result);
        } catch (ParkingLotSystemException e) {
            e.printStackTrace();
        }

    }
        @Test
        public void givenParkingLotSystem_WhenVehicleParked_ThenShouldLocateBlueToyotoCar () {

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

            Vehicle vehicle1 = new Vehicle("black", "BMW", "MH-12-1176");
            Vehicle vehicle2 = new Vehicle("blue", "toyota", "MH-12-1276");
            Vehicle vehicle3 = new Vehicle("red", "BMW", "MH-12-1376");
            Vehicle vehicle4 = new Vehicle("white", "toyota", "MH-12-1476");
            Vehicle vehicle5 = new Vehicle("grey", "toyota", "MH-12-1576");

            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "XYZ");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4, "PQR");
            parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5, "ABC");
            List<List<String>> expectedCar = parkingLotSystem.findByModelAndColour("blue", "toyota");
            List result = new ArrayList();
            result.add("XYZ1MH-12-1276");
            Assert.assertEquals(result, expectedCar.get(0));
        }

    @Test
    public void givenParkingLotSystem_WhenVehicleParked_ThenShouldLocateBMWCar() {
<<<<<<< HEAD


        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot);


        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot);

=======
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot);
>>>>>>> Uc14-LocateBmwCar
        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setParkingLotCapacity(10);
        parkingLot1.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot1);
<<<<<<< HEAD

=======
>>>>>>> Uc14-LocateBmwCar
        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setParkingLotCapacity(10);
        parkingLot2.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot2);
        Vehicle vehicle1 = new Vehicle("black", "BMW", "MH-12-1176");
        Vehicle vehicle2 = new Vehicle("blue", "toyota", "MH-12-1276");
        Vehicle vehicle3 = new Vehicle("red", "BMW", "MH-12-1376");
        Vehicle vehicle4 = new Vehicle("white", "toyota", "MH-12-1476");
        Vehicle vehicle5 = new Vehicle("grey", "toyota", "MH-12-1576");
<<<<<<< HEAD

=======
>>>>>>> Uc14-LocateBmwCar
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle2, "XYZ");
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle4, "PQR");
        parkingLot.parkVehicle(DriverType.NORMAL_DRIVER, vehicle5, "ABC");
        List<List<Integer>> expectedList = parkingLotSystem.findVehicleByModelName("BMW");
        List result = new ArrayList();
        result.add(0);
        result.add(2);
        Assert.assertEquals(result, expectedList.get(0));
    }
}








