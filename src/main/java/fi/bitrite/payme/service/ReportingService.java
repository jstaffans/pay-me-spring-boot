package fi.bitrite.payme.service;

import fi.bitrite.payme.model.Payment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Author: johannes.
 */
@Service
public class ReportingService {

    private List<Payment> payments = new CopyOnWriteArrayList<>();

}
