package fi.bitrite.payme.service;

import org.springframework.stereotype.Service;
import rx.Observable;

/**
 * Author: johannes.
 */
@Service
public class PaymentService {

    public Observable<String> doPayment(String ccNumber) {

        return Observable.just("ok: " + ccNumber);

    }
}
