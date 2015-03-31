package fi.bitrite.payme.controller;

import fi.bitrite.payme.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class FormController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/")
    String paymentForm() {
        return "form";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    String pay(@RequestParam("number") final String ccNumber, Model model) {
        paymentService.doPayment(ccNumber).subscribe(result -> {
            log.info("Result: {}", result);
            model.addAttribute("result", result);
        });

        return "pay";
    }
}
