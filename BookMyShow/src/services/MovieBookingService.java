package services;

import entity.*;

import java.time.LocalTime;
import java.util.List;

public class MovieBookingService {
    private Ticket ticket;
    private final PaymentService paymentService;

    public MovieBookingService() {
        this.ticket = null;
        this.paymentService = new PaymentService();
    }

    public Ticket bookMovie(User user, Movie movie, LocalTime startTime, City city, CinemaHall hall, List<String> seats) {
        // create a ticket
        this.ticket = new Ticket(user, movie, city, hall, startTime);
        // block seats for 5 mins
        hall.addBookedSeats(movie, startTime, seats);
        // blocking call for 5 mins
        LocalTime blockingTime = LocalTime.now().plusMinutes(5);
        while (!paymentService.isPaymentDone() && LocalTime.now().isBefore(blockingTime)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        // if payment is not done within 5 mins release blocked seats
        if (!paymentService.isPaymentDone()) {
            hall.releaseBlockedSeats(movie, startTime, seats);
            this.ticket = null;
        }
        return this.ticket;
    }
}
