import java.util.HashMap;
import java.util.Map;

public class Client {
    public static void main(String...args) {
        // define parking spot details
        Map<VehicleType, Integer> parkingSpots = new HashMap<>();
        parkingSpots.put(VehicleType.BIKE, 20);
        parkingSpots.put(VehicleType.CAR, 10);
        parkingSpots.put(VehicleType.TRUCK, 1);

        ParkingLotService service = new ParkingLotService(parkingSpots);
        try {
            Ticket c1 = service.issueTicket(new Vehicle("24 BH 1234", VehicleType.CAR));
            Ticket c2 = service.issueTicket(new Vehicle("25 BH 2345", VehicleType.CAR));
            service.displayParkingLot();
            Ticket b1 = service.issueTicket(new Vehicle("25 BH 9876", VehicleType.BIKE));
            Invoice i1 = service.getInvoice(c2);
            System.out.println(i1);
            service.displayParkingLot();
            Ticket t1 = service.issueTicket(new Vehicle("23 BH 158", VehicleType.TRUCK));
            Ticket t2 = service.issueTicket(new Vehicle("23 BH 269", VehicleType.TRUCK));
            service.displayParkingLot();
            Ticket t3 = service.issueTicket(new Vehicle("24 BH 7890", VehicleType.TRUCK));
            Invoice i2 = service.getInvoice(t2);
            System.out.println(i2);
            service.displayParkingLot();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
