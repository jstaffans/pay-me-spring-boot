package fi.bitrite.payme.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static net.javacrumbs.futureconverter.java8rx.FutureConverter.toObservable;

/**
 * Author: johannes.
 */
@Service
@Slf4j
public class PaymentService {

    public Observable<String> doPayment(String ccNumber) {
        Observable<String> payment = getSum()
                .flatMap(this::processPayment)
                .timeout(3, TimeUnit.SECONDS)
                .onErrorReturn(t -> "timeout")
                .cache();

        // subscribe a reporting service
        payment.subscribe(result -> log.info("Service result: {} for cc number {}", result, ccNumber));

        return payment;
    }

    // Simulates an unreliable payment provider that takes up to five seconds to complete a payment.
    private Observable<String> processPayment(Double sum) {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(Math.round(Math.random() * 5000.0));
                log.info("Charging {}", sum);
                return "ok";
            } catch (InterruptedException e) {
                return "interrupted";
            }
        });

        return toObservable(f);
    }

    public Observable<Double> getSum() {
        return Observable.just(Math.round(Math.random() * 500000.0) / 100.0);
    }
}
