package fi.bitrite.payme.service;

import fi.bitrite.payme.model.Payment;
import fi.bitrite.payme.model.PaymentResult;

/**
 * Author: johannes.
 */
public interface PaymentProcessor {

    PaymentResult process(Payment payment) throws InterruptedException;

}
