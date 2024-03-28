package yaroslav.redickh_idf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yaroslav.redickh_idf.service.CurrencyExchangeService;
import yaroslav.redickh_idf.entity.Currency;

@EnableScheduling
@RestController
public class CurrencyController {

    private final CurrencyExchangeService currencyService;

    @Autowired
    public CurrencyController(CurrencyExchangeService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/exchange-rate")
    public Currency createCurrency(@RequestParam String pairs) {
        currencyService.getExchangeRate(pairs);
        return currencyService.saveCurrency(pairs);
    }

    @GetMapping("/exchange-rat")
    public String getExchangeRate(@RequestParam String pairs) {
        return currencyService.getExchangeRate(pairs);
    }
    @GetMapping("/update-rates")
    public String updateExchangeRates() {
        currencyService.updateExchangeRate();
        return "Exchange rates updated successfully";
    }
}
