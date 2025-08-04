import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles parking spots, generate invoice & accept payments
 */
public class ParkingLotService {

    private final ParkingLotManager parkingManager;

    ParkingLotService(Map<VehicleType, Integer> parkingLotDetails) {
        parkingManager = new ParkingLotManager();
        parkingManager.generateParkingSpots(parkingLotDetails);
    }

    public Ticket issueTicket(Vehicle vehicle) {
        // find a parking spot
        ParkingSpot spot = parkingManager.getParkingSpot(vehicle.getType());
        // if there's no parking spot available throw exception
        if (spot == null) {
            throw new ParkingFullException(vehicle.getType().name());
        }
        LocalTime currentTime = LocalTime.now();
        // generate a ticket & mark the spot as occupied
        Ticket ticket = new Ticket(parkingManager.generateTicketNumber(), vehicle, spot, currentTime);
        parkingManager.getMap().put(ticket, vehicle);
        return ticket;
    }

    public Invoice getInvoice(Ticket ticket) {
        // release parking spot when vehicle leaves
        parkingManager.releaseParkingSpot(ticket);
        // generate invoice for the "ticket"
        int billAmount = parkingManager.getBillAmount(ticket.getVehicle().getType(), ticket.getArrivalTime());
        return new Invoice(ticket, billAmount);
    }

    public void displayParkingLot() {
        Map<VehicleType, Integer> status = new HashMap<>();
        for (ParkingSpot spot: parkingManager.getParkingSpots()) {
            if (spot.isOccupied())
                continue;
            status.put(spot.getType(), status.getOrDefault(spot.getType(), 0) + 1);
        }
        status.forEach((type, availableSpots) -> System.out.println(type.name() + "\t->\t" + availableSpots));
    }
/*
    // Payment service is completely different design
    public boolean acceptPayment(PaymentMethod type, Object cardDetails) {
        if (type == PaymentMethod.CASH)
            return true;
        // call payment aggregator services
        return false;
    }
*/
}
