package fares;

import ticket.Ticket;

import java.time.LocalTime;

public interface FareStrategy {
    double calculateFare(Ticket ticket, double inputFare);
}
