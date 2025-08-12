package services;

import entity.*;
import exceptions.SelectedSeatUnavailableException;
import exceptions.TicketBookingFailedException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class BookingManager {
    private static final int THREAD_POOL_SIZE = 100;
    private static final int MAX_QUEUE_SIZE = 10000;
    
    private final ExecutorService bookingExecutor;
    private final ExecutorService paymentExecutor;
    private final BlockingQueue<BookingRequest> bookingQueue;
    private final AtomicLong requestIdGenerator;
    
    public BookingManager() {
        this.bookingExecutor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.paymentExecutor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.bookingQueue = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);
        this.requestIdGenerator = new AtomicLong(0);
        
        // Start the booking processor
        startBookingProcessor();
    }
    
    public CompletableFuture<Ticket> bookMovieAsync(User user, City city, CinemaHall hall, Show show, List<String> seats) {
        long requestId = requestIdGenerator.incrementAndGet();
        BookingRequest request = new BookingRequest(requestId, user, city, hall, show, seats);
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                return processBooking(request);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }, bookingExecutor);
    }
    
    private Ticket processBooking(BookingRequest request) {
        // Step 1: Try to hold seats
        if (!request.hall.holdSeats(request.show, request.seats)) {
            throw new SelectedSeatUnavailableException();
        }
        
        // Step 2: Create ticket
        final Ticket ticket = new Ticket(request.user, request.city, request.hall, request.show, request.seats);
        
        // Step 3: Process payment asynchronously
        CompletableFuture<Boolean> paymentFuture = CompletableFuture.supplyAsync(() -> {
            PaymentService paymentService = new PaymentService();
            return paymentService.payForTicket(ticket);
        }, paymentExecutor);
        
        try {
            // Wait for payment with timeout
            Boolean paymentSuccess = paymentFuture.get(5, TimeUnit.MINUTES);
            
            if (paymentSuccess) {
                // Confirm booking
                if (!request.hall.confirmBooking(request.show, request.seats)) {
                    throw new TicketBookingFailedException();
                }
                ticket.setBookingTime(java.time.LocalTime.now());
                return ticket;
            } else {
                // Release held seats
                request.hall.releaseHeldSeats(request.show, request.seats);
                return null;
            }
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            // Timeout or payment failed
            request.hall.releaseHeldSeats(request.show, request.seats);
            return null;
        }
    }
    
    private void startBookingProcessor() {
        Thread processor = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    BookingRequest request = bookingQueue.poll(100, TimeUnit.MILLISECONDS);
                    if (request != null) {
                        processBooking(request);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        processor.setDaemon(true);
        processor.start();
    }
    
    public void shutdown() {
        bookingExecutor.shutdown();
        paymentExecutor.shutdown();
        try {
            if (!bookingExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                bookingExecutor.shutdownNow();
            }
            if (!paymentExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                paymentExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            bookingExecutor.shutdownNow();
            paymentExecutor.shutdownNow();
        }
    }
    
    private static class BookingRequest {
        final long requestId;
        final User user;
        final City city;
        final CinemaHall hall;
        final Show show;
        final List<String> seats;
        
        BookingRequest(long requestId, User user, City city, CinemaHall hall, Show show, List<String> seats) {
            this.requestId = requestId;
            this.user = user;
            this.city = city;
            this.hall = hall;
            this.show = show;
            this.seats = seats;
        }
    }
}
