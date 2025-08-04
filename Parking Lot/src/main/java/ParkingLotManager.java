import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class ParkingLotManager {
    private int ticketNum;
    private Set<ParkingSpot> parkingSpots;
    private Map<Ticket, Vehicle> map;

    protected ParkingSpot getParkingSpot(VehicleType vehicleType) {
        for (ParkingSpot spot : parkingSpots) {
            if (!spot.isOccupied() && spot.getType() == vehicleType) {
                return spot;
            }
        }
        return null;
    }

    protected void releaseParkingSpot(Ticket ticket) {
        ParkingSpot spot = ticket.getParkingSpot();
        spot.setOccupied(false);
        parkingSpots.add(spot);
        map.remove(ticket);
    }

    protected int generateTicketNumber() {
        return ticketNum++;
    }

    protected double getBillAmount(VehicleType vehicleType, LocalTime arrivalTime) {
        long duration = Duration.between(arrivalTime, LocalTime.now()).toHours();
        double finalAmount = vehicleType.getHourlyCharges() * duration * 1f;
        return Math.ceil(finalAmount);
    }
}
