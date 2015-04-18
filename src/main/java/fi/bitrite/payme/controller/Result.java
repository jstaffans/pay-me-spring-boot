package fi.bitrite.payme.controller;

import fi.bitrite.payme.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Result {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    String pay(@RequestParam("number") final String ccNumber, Model model) {
        model.addAttribute("result", paymentService.doPayment(ccNumber).toBlocking().first());
        return "pay";
    }
}
