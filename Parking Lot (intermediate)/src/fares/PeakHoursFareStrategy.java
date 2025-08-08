package fares;

import ticket.Ticket;

import java.time.LocalTime;

public class PeakHoursFareStrategy implements FareStrategy {
    /**
     * Peak hours are [9am - 11am] & [6pm to 8pm]
     * @return true if vehicle entry time is in peak hours
     */
    public boolean isPeakHours(LocalTime time) {
        return (time.getHour() >= 9 && time.getHour() <= 11) || (time.getHour() >= 18 && time.getHour() <= 20);
    }
    @Override
    public double calculateFare(Ticket ticket, double inputFare) {
        return isPeakHours(ticket.getEntryTime()) ? inputFare * Rates.PEAK_HOURS_MULTIPLIER.getFare() : inputFare;
    }
}
