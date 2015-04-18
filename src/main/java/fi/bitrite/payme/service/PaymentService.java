package fi.bitrite.payme.service;

import fi.bitrite.payme.model.Payment;
import fi.bitrite.payme.model.PaymentResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.concurrent.*;

@Service
@Slf4j
public class PaymentService {

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    PaymentProcessor paymentProcessor = (payment) -> {
        Thread.sleep(Math.round(Math.random() * 5000.0));
        log.info("Charging: {}", payment);
        return PaymentResult.OK(payment);
    };

    @Autowired
    private ReportingService reportingService;

    public Observable<PaymentResult> doPayment(String ccNumber) {
        Double sum = Math.round(Math.random() * 500000.0) / 100.0;

        Payment payment = new Payment(ccNumber, sum);
        Observable<PaymentResult> result = processPayment(payment)
                .cache();

        // subscribe a reporting service
        result.subscribe(res -> log.info("Payment processed: {}", res));

        return result;
    }

    private Observable<PaymentResult> processPayment(Payment payment) {
        Future<PaymentResult> task = executorService.submit(() -> paymentProcessor.process(payment));

        try {
            return Observable.just(task.get(3, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            task.cancel(true);
            return Observable.just(PaymentResult.FAILED(payment));
        }
    }

}
