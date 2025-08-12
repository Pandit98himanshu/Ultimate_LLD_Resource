package services;

import entity.Ticket;

public class PaymentService {
    private boolean isPaymentDone;

    public PaymentService() {
        this.isPaymentDone = false;
    }

    public boolean payForTicket(Ticket ticket) {
        // make payment
        isPaymentDone = true;
        return true;
    }

    public boolean isPaymentDone() {
        return isPaymentDone;
    }
}
