package fares;

import ticket.Ticket;

import java.time.LocalTime;

public class BaseFareStrategy implements FareStrategy {
    @Override
    public double calculateFare(Ticket ticket, double inputFare) {
        long totalHours = ticket.calculateParkingDuration();
        long days = totalHours / 24;
        long remainingHours = totalHours % 24;
        inputFare += switch (ticket.getVehicle().getSize()) {
            case SMALL ->
                    days * Rates.SMALL_VEHICLE_DAILY_RATE.getFare() + remainingHours * Rates.SMALL_VEHICLE_HOURLY_RATE.getFare();
            case MEDIUM ->
                    days * Rates.MEDIUM_VEHICLE_DAILY_RATE.getFare() + remainingHours * Rates.MEDIUM_VEHICLE_HOURLY_RATE.getFare();
            case LARGE ->
                    days * Rates.LARGE_VEHICLE_DAILY_RATE.getFare() + remainingHours * Rates.LARGE_VEHICLE_HOURLY_RATE.getFare();
        };
        return inputFare;
    }
}
