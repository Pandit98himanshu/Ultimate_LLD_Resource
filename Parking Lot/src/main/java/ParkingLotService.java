import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

/**
 * Handles parking spots, generate invoice & accept payments
 */
@Getter
@Setter
public class ParkingLotService {
    private int ticketNum;
    private Set<ParkingSpot> parkingSpots;
    private Map<Ticket, Vehicle> map;

    public Ticket issueTicket(Vehicle vehicle) {
        // find a parking spot
        ParkingSpot spot = getParkingSpot(vehicle.getType());
        // if there's no parking spot available throw exception
        if (spot == null) {
            throw new ParkingFullException();
        }
        LocalTime currentTime = LocalTime.now();
        // generate a ticket & mark the spot as occupied
        Ticket ticket = new Ticket(generateTicketNumber(), vehicle, spot, currentTime);
        map.put(ticket, vehicle);
        return ticket;
    }

    public Invoice getInvoice(Ticket ticket) {
        // release parking spot when vehicle leaves
        releaseParkingSpot(ticket);
        double billAmount = getBillAmount(ticket.getVehicle().getType(), ticket.getArrivalTime());
        return new Invoice(ticket, billAmount);
    }

    public boolean acceptPayment(Object cardDetails, PaymentMethod type) {
        // call payment aggregator service
        return false;
    }

    private ParkingSpot getParkingSpot(VehicleType vehicleType) {
        for (ParkingSpot spot : parkingSpots) {
            if (!spot.isOccupied() && spot.getType() == vehicleType) {
                return spot;
            }
        }
        return null;
    }

    private void releaseParkingSpot(Ticket ticket) {
        ParkingSpot spot = ticket.getParkingSpot();
        spot.setOccupied(false);
        parkingSpots.add(spot);
        map.remove(ticket);
    }

    private double getBillAmount(VehicleType vehicleType, LocalTime arrivalTime) {
        long duration = Duration.between(arrivalTime, LocalTime.now()).toHours();
        double finalAmount = vehicleType.getHourlyCharges() * duration * 1f;
        return Math.ceil(finalAmount);
    }

    private int generateTicketNumber() {
        return ticketNum++;
    }
}
