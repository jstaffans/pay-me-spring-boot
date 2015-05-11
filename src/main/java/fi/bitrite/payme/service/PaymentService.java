package fi.bitrite.payme.service;

import fi.bitrite.payme.model.Payment;
import fi.bitrite.payme.model.PaymentResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

import java.util.concurrent.*;

@Service
@Slf4j
public class PaymentService {

    // a cached executor service with max 5 threads
    ExecutorService executorService = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private final Subject<PaymentResult, PaymentResult> eventBus = new SerializedSubject<>(PublishSubject.create());

    PaymentProcessor paymentProcessor = (payment) -> {
        log.info("Charging: {}", payment);
        return PaymentResult.OK(payment);
    };

    @Autowired
    private ReportingService reportingService;

    public Observable<PaymentResult> doPayment(String ccNumber) {
        NormalDistribution priceDistribution = new NormalDistribution(1000.0, 200.0);
        Double sum = priceDistribution.sample();
        Payment payment = new Payment(ccNumber, sum);

        return processPayment(payment)
                .timeout(10000, TimeUnit.MILLISECONDS, Observable.just(PaymentResult.FAILED(payment)))
                .doOnNext(eventBus::onNext);
    }

    private Observable<PaymentResult> processPayment(Payment payment) {
        Future<PaymentResult> task = executorService.submit(() -> paymentProcessor.process(payment));

        return Observable.create(subscriber -> {
            subscriber.add(new Subscription() {
                private boolean unsubscribed = false;

                @Override
                public void unsubscribe() {
                    if (!task.isDone()) {
                        task.cancel(true);
                    }

                    unsubscribed = true;
                }

                @Override
                public boolean isUnsubscribed() {
                    return unsubscribed;
                }
            });

            try {
                PaymentResult value = task.get();
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(value);
                    subscriber.onCompleted();
                }
            } catch (InterruptedException | ExecutionException e) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<PaymentResult> getEventBus() {
        return eventBus;
    }
}
