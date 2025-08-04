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

    protected void generateParkingSpots(Map<VehicleType, Integer> parkingLotDetails) {
        int spotId = 0;
        for (Map.Entry<VehicleType, Integer> e : parkingLotDetails.entrySet()) {
            VehicleType type = e.getKey();
            int quantity = e.getValue();
            while(quantity-- > 0)
                parkingSpots.add(new ParkingSpot(spotId++, type, false));
        }
    }

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
        this.parkingSpots.add(spot);
        this.map.remove(ticket);
    }

    protected int generateTicketNumber() {
        // Integer overflow
        if (ticketNum < 0)
            ticketNum = 0;
        return this.ticketNum++;
    }

    protected int getBillAmount(VehicleType vehicleType, LocalTime arrivalTime) {
        long duration = Duration.between(arrivalTime, LocalTime.now()).toHours();
        double finalAmount = vehicleType.getHourlyCharges() * duration * 1f;
        return (int)Math.ceil(finalAmount);
    }
}
