package fares;

import ticket.Ticket;

import java.util.List;

public class FareCalculator {
    private final List<FareStrategy> fareStrategies;

    public FareCalculator(List<FareStrategy> fareStrategies) {
        this.fareStrategies = fareStrategies;
    }

    /**
     * Applies all fare strategies sequentially and increase the fare accordingly
     * @return final fare after applying all fare strategies provided
     */
    public double calculateFare(Ticket ticket) {
        double fare = 0.0;
        for (FareStrategy strategy : fareStrategies) {
            fare = strategy.calculateFare(ticket, fare);
        }
        return fare;
    }
}
