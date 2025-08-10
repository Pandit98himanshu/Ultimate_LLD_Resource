import fares.*;
import parkingspot.*;
import ticket.Ticket;
import vehicles.*;

import java.util.*;

public class Client {
    private static ParkingLotService parkingService;

    public Client() {
        int spotid = 1;
        List<ParkingSpot> smallSpots = new ArrayList<>();
        List<ParkingSpot> mediumSpots = new ArrayList<>();
        List<ParkingSpot> largeSpots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            smallSpots.add(new CompactParkingSpot(spotid++));
        }
        for (int i = 0; i < 5; i++) {
            mediumSpots.add(new RegularParkingSpot(spotid++));
        }
        for (int i = 0; i < 2; i++) {
            largeSpots.add(new OversizedParkingSpot(spotid++));
        }

        // parking spot map
        Map<VehicleSize, List<ParkingSpot>> availableParkingSpots = new HashMap<>();
        availableParkingSpots.put(VehicleSize.SMALL, smallSpots);
        availableParkingSpots.put(VehicleSize.MEDIUM, mediumSpots);
        availableParkingSpots.put(VehicleSize.LARGE, largeSpots);
        // define fare strategies list to be applied
        List<FareStrategy > fareStrategies = new ArrayList<>();
        fareStrategies.add(new BaseFareStrategy());
        fareStrategies.add(new PeakHoursFareStrategy());
        // initialize parkingService object
        parkingService = new ParkingLotService(availableParkingSpots, fareStrategies);
    }

    public static void main(String[] args) {
        new Client();   // anonymous instantiation
        Vehicle c1 = new Car("23 BH 1234");
        Vehicle c2 = new Car("23 BH 2345");
        Vehicle c3 = new Car("23 BH 3456");
        Vehicle c4 = new Car("23 BH 4567");
        Vehicle m1 = new Motorcycle("25 BH 1234");
        Vehicle m2 = new Motorcycle("25 BH 2345");
        Vehicle m3 = new Motorcycle("25 BH 3456");
        Vehicle t1 = new Truck("24 BH 1234");
        Vehicle t2 = new Truck("24 BH 2345");
        Vehicle t3 = new Truck("24 BH 3456");

        Ticket pt1 = parkingService.enterVehicle(c1);
        Ticket pt2 = parkingService.enterVehicle(c2);
        Ticket pt3 = parkingService.enterVehicle(c3);
        System.out.println(parkingService.leaveVehicle(pt1));
        Ticket pt4 = parkingService.enterVehicle(c4);
        Ticket pt5 = parkingService.enterVehicle(m1);
        try {
            System.out.println(parkingService.leaveVehicle(pt1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Ticket pt6 = parkingService.enterVehicle(m2);
        System.out.println(parkingService.leaveVehicle(pt5));
        System.out.println(parkingService.leaveVehicle(pt6));
        Ticket pt7 = parkingService.enterVehicle(m3);
        Ticket pt8 = parkingService.enterVehicle(t1);
        Ticket pt9 = parkingService.enterVehicle(t2);
        try {
            Ticket pt10 = parkingService.enterVehicle(t3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(parkingService.leaveVehicle(pt8));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
