package fi.bitrite.payme.controller;

import fi.bitrite.payme.model.PaymentResult;
import fi.bitrite.payme.service.PaymentService;
import fi.bitrite.payme.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class Result {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ReportingService reportingService;

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(@RequestParam("number") final String ccNumber, Model model) {
        model.addAttribute("result", paymentService.doPayment(ccNumber).toBlocking().first());
        return "pay";
    }

    @RequestMapping(value = "/report")
    public @ResponseBody List<PaymentResult> report() {
        return reportingService.getPayments();
    }
}
