package services;

import entity.Ticket;
import java.util.concurrent.atomic.AtomicInteger;

public class PaymentService {
    private static final AtomicInteger activeConnections = new AtomicInteger(0);
    private static final int MAX_CONNECTIONS = 1000;
    
    private final String connectionId;
    private boolean isPaymentDone;

    public PaymentService() {
        this.connectionId = "PAY-" + System.currentTimeMillis() + "-" + Thread.currentThread().getId();
        this.isPaymentDone = false;
    }

    public boolean payForTicket(Ticket ticket) {
        if (activeConnections.get() >= MAX_CONNECTIONS) {
            throw new RuntimeException("Payment service overloaded");
        }
        
        activeConnections.incrementAndGet();
        try {
            // Simulate payment processing
            Thread.sleep(100); // Simulate network delay
            isPaymentDone = true;
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            activeConnections.decrementAndGet();
        }
    }

    public boolean isPaymentDone() {
        return isPaymentDone;
    }
    
    public static int getActiveConnections() {
        return activeConnections.get();
    }
}
