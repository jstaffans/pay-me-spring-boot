package fi.bitrite.payme.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.Instant;

/**
 * Author: johannes.
 */
@Value
@AllArgsConstructor
public class PaymentResult {

    public enum Status {
        OK, FAILED
    }

    Payment payment;
    Status status;
    Instant paymentProcessedOn;

    public static PaymentResult FAILED(Payment payment) {
        return new PaymentResult(payment, Status.FAILED, Instant.now());
    }

    public static PaymentResult OK(Payment payment) {
        return new PaymentResult(payment, Status.OK, Instant.now());
    }
}
