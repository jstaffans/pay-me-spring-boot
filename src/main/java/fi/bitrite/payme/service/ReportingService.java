package fi.bitrite.payme.service;

import fi.bitrite.payme.model.PaymentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: johannes.
 */
@Service
public class ReportingService {

    @Autowired
    PaymentService paymentService;

    private List<PaymentResult> payments = new ArrayList<>();

    @PostConstruct
    private void registerPaymentResultSubscriber() {
        paymentService.getEventBus().subscribe(paymentResult -> {
            payments.add(paymentResult);
        });
    }

    public List<PaymentResult> getPayments() {
        return payments;
    }
}
