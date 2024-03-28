package yaroslav.redickh_idf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yaroslav.redickh_idf.entity.ExchangeRate;
import yaroslav.redickh_idf.repositories.ExchangeRateRepository;
@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public ExchangeRate saveExchange (ExchangeRate rate){
        return exchangeRateRepository.save(rate);
    }
}
