package services;

import entity.*;
import exceptions.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MovieBookingService {
    private Ticket ticket;
    private final PaymentService paymentService;

    public MovieBookingService() {
        this.ticket = null;
        this.paymentService = new PaymentService();
    }

    public Ticket bookMovie(User user, City city, CinemaHall hall, Show show, List<String> seats) {
        // First, try to hold the seats
        if (!hall.holdSeats(show, seats)) {
            throw new SelectedSeatUnavailableException();
        }
        
        // Create a ticket
        this.ticket = new Ticket(user, city, hall, show, seats);
        
        // Wait for payment with timeout using CompletableFuture
        try {
            CompletableFuture.supplyAsync(() -> {
                while (!paymentService.isPaymentDone()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                }
                return paymentService.isPaymentDone();
            }).get(5, TimeUnit.MINUTES);
            
            // If payment successful, confirm the booking
            if (paymentService.isPaymentDone()) {
                if (!hall.confirmBooking(show, seats)) {
                    throw new TicketBookingFailedException();
                }
            } else {
                hall.releaseHeldSeats(show, seats);
                this.ticket = null;
            }
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            // Timeout or interruption occurred
            hall.releaseHeldSeats(show, seats);
            this.ticket = null;
        }
        
        return this.ticket;
    }

    public void cancelBookedSeats(Ticket ticket) {
        if (ticket == null) {
            throw new InvalidTicketException();
        }
        ticket.cancel();
    }
}
