import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Handles parking spots, generate invoice & accept payments
 */
@AllArgsConstructor
public class ParkingLotService {
    private ParkingLotManager parkingManager;
    public Ticket issueTicket(Vehicle vehicle) {
        // find a parking spot
        ParkingSpot spot = parkingManager.getParkingSpot(vehicle.getType());
        // if there's no parking spot available throw exception
        if (spot == null) {
            throw new ParkingFullException();
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
        double billAmount = parkingManager.getBillAmount(ticket.getVehicle().getType(), ticket.getArrivalTime());
        return new Invoice(ticket, billAmount);
    }

    public boolean acceptPayment(Object cardDetails, PaymentMethod type) {
        // call payment aggregator service
        return false;
    }
}
