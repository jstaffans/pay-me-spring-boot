package fi.bitrite.payme.model;

import lombok.AllArgsConstructor;
import lombok.Value;

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

    public static PaymentResult FAILED(Payment payment) {
        return new PaymentResult(payment, Status.FAILED);
    }

    public static PaymentResult OK(Payment payment) {
        return new PaymentResult(payment, Status.OK);
    }
}
