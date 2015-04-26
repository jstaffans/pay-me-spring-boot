package fi.bitrite.payme.service;

import fi.bitrite.payme.model.Payment;
import fi.bitrite.payme.model.PaymentResult;
import lombok.extern.slf4j.Slf4j;
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

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Subject<PaymentResult, PaymentResult> eventBus = new SerializedSubject<>(PublishSubject.create());

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
        Observable<PaymentResult> result = processPaymentCompletableFuture(payment)
                .timeout(3000, TimeUnit.MILLISECONDS, Observable.just(PaymentResult.FAILED(payment)))
                .cache();

        result.subscribe(eventBus::onNext);

        return result;
    }

    private Observable<PaymentResult> processPaymentCompletableFuture(Payment payment) {
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
