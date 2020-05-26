import com.parkinglotsystem.*;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {
    ParkingLot parkingLot;
    ParkingLotSystem parkingLotSystem;
    Object vehicle;
    ParkingOwner parkingOwner;
    AirportSecurity airportSecurity;

    @Before
    public void setup() {
        parkingLot = new ParkingLot(1);
        parkingLotSystem = new ParkingLotSystem(1);
        vehicle = new Object();
        parkingOwner = new ParkingOwner();
        airportSecurity = new AirportSecurity();
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ThenReturnTrue() {
        try {
            parkingLot.parkVehicle(vehicle);
            boolean vehicleIsPark = parkingLot.isPark(vehicle);
            Assert.assertTrue(vehicleIsPark);
        } catch (ParkingLotSystemException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenParkingLot_WhenParkedVehicleIsUnParked_ThenReturnTrue() {
        try {
            parkingLot.parkVehicle(vehicle);
            boolean vehicleIsUnPark = parkingLot.isUnPark(vehicle);
            Assert.assertTrue(vehicleIsUnPark);
        } catch (ParkingLotSystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotIsFull_WhenVehicleIsParked_ThenReturnTrue() {

        try {
            parkingLot.parkVehicle(vehicle);
            Object secondVehicle = new Object();
            parkingLot.parkVehicle(secondVehicle);
            parkingLot.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("PARKING_IS_FULL", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenUnParkedVehicleWhichIsNotParked_ThenReturnFalse() {
        try {
            parkingLot.isPark(vehicle);
            boolean isVehicleParked = parkingLot.isUnPark(new Object());
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not In Parking", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_CheckVehicleIsNotPrsent_ThenShouldThrowException() {
        try {
            parkingLot.isPark(null);
            boolean isVehicleParked = parkingLot.isPark(vehicle);
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not In Parking", e.getMessage());
        }
    }


    @Test
    public void givenParkingLot_WhenVehicleIsNotParked_ThenReturnException() {
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
            parkingLot.parkVehicle(new Object());
            parkingLot.parkVehicle(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertTrue(parkingOwner.parkingFull());
        }
    }

    @Test
    public void givenParkingLotCapacity2_ShouldBeAbleToPark2Vehicles_ThenShouldInformOwner() {
        Object vehicle2 = new Object();
        parkingLot.setParkingLotCapacity(2);
        try {
            parkingLot.registerHandler(parkingOwner);
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(vehicle2);
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
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
        }
        Assert.assertTrue(airportSecurity.parkingFull());
    }

    @Test //
    public void givenParkingLot_WhenSpaceAvailableAfterFull_ThenShouldReturnTrue() {
        Object vehicle2 = new Object();
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
        try {
            parkingLot.registerHandler(parkingOwner);
            ParkingLotAttender parkingLotAttender1 = new ParkingLotAttender(vehicle);
            ParkingLotAttender parkingLotAttender2 = new ParkingLotAttender(new Object());
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
            ParkingLotAttender myAttendant = parkingLot.getMyVehicle(new ParkingLotAttender(new Object()));
            Assert.assertEquals(parkingLotAttender, myAttendant);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("No Attendant Found", e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleFound_ShouldReturnVehicleSlot() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLot.parkVehicle(new Object());
        parkingLot.parkVehicle(vehicle);
        int slotNumber = parkingLot.findVehicle(this.vehicle);
        Assert.assertEquals(1, slotNumber);
    }

    @Test
    public void givenParkingLot_WhenTimeIsSet_ThenReturnTrue() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLot.parkVehicle(vehicle);
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
            parkingLotSystem.parkVehicle(vehicle);
            boolean isVehiclePark = parkingLotSystem.isPark(vehicle);
            Assert.assertTrue(isVehiclePark);
        } catch (ParkingLotSystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehiclePark_ThenShouldParkVehicleEvenly() {

        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingSlot();
        parkingLotSystem.addLots(parkingLot);
        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLot1.setParkingLotCapacity(10);
        parkingLot1.initializeParkingSlot();
        Object vehicle1 = new Object();
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();

        parkingLotSystem.addLots(parkingLot1);
        parkingLotSystem.parkVehicle(vehicle);

        boolean isParked1 = parkingLotSystem.isPark(vehicle);
        parkingLotSystem.parkVehicle(vehicle1);

        boolean isParked2 = parkingLotSystem.isPark(vehicle1);
        parkingLotSystem.parkVehicle(vehicle2);

        boolean isParked3 = parkingLotSystem.isPark(vehicle2);
        parkingLotSystem.parkVehicle(vehicle3);

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
        Object vehicle1 = new Object();
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        parkingLotSystem.addLots(parkingLot1);
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(vehicle1);
            boolean isUnPark = parkingLotSystem.isUnPark(vehicle);
            parkingLotSystem.parkVehicle(vehicle2);
            parkingLotSystem.parkVehicle(vehicle3);
            Assert.assertTrue(isUnPark);
        } catch (ParkingLotSystemException e) {
        }
    }
}







