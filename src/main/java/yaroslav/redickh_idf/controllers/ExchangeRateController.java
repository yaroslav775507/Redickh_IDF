package yaroslav.redickh_idf.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yaroslav.redickh_idf.entity.ExchangeRate;
import yaroslav.redickh_idf.service.ExchangeRateService;

@RestController
@RequestMapping("/change")
public class ExchangeRateController {
    private final ExchangeRateService rateService;

    public ExchangeRateController(ExchangeRateService rateService) {
        this.rateService = rateService;
    }
    @PostMapping
    private ExchangeRate createExchangeRate(@RequestBody ExchangeRate rate){
        return  rateService.saveExchange(rate);
    }
}
