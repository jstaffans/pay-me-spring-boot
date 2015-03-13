package fi.bitrite.payme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FormController {

    @RequestMapping("/")
    String paymentForm() {
        return "form";
    }
}
