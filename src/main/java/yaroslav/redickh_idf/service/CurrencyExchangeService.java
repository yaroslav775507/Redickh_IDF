package yaroslav.redickh_idf.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yaroslav.redickh_idf.entity.Currency;
import yaroslav.redickh_idf.repositories.CurrencyExchangeRepository;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class CurrencyExchangeService {

    private static final String BASE_URL = "https://currate.ru/api/?get=rates&pairs=";

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final CurrencyExchangeRepository exchangeRepository;


    @Autowired
    public CurrencyExchangeService(RestTemplate restTemplate, @Value("${currate.api.key}") String apiKey, CurrencyExchangeRepository exchangeRepository) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.exchangeRepository = exchangeRepository;
    }

    public String getExchangeRate(String pairs) {
        String url = BASE_URL + pairs.toUpperCase() + "&key=" + apiKey;
        return restTemplate.getForObject(url, String.class);
    }

    //@Scheduled(cron = "0 0 0 * * ?")
    //Для проверки установил 5000
    @Scheduled(fixedRate = 5000)
    public void updateExchangeRate() {
        String pairs = "USDRUB";
        String exchangeRateResponse = getExchangeRate(pairs);
        try {
            double rate = parseExchangeRateFromJson(exchangeRateResponse);
            Currency currency = new Currency();
            currency.setCurrencyPair(pairs);
            currency.setSum(rate);
            currency.setDate(LocalDateTime.now());
            exchangeRepository.save(currency);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Currency saveCurrency(String pair) {
        String exchangeRateResponse = getExchangeRate(pair);
        try {
            double rate = parseExchangeRateFromJson(exchangeRateResponse);
            Currency currency = new Currency();
            currency.setCurrencyPair(pair);
            currency.setSum(rate);
            currency.setDate(LocalDateTime.now());
            return exchangeRepository.save(currency);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private double parseExchangeRateFromJson(String exchangeRateResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(exchangeRateResponse);
            JsonNode dataNode = rootNode.get("data");
            JsonNode usdRubNode = dataNode.get("USDRUB");
            String rateAsString = usdRubNode.asText();
            return Double.parseDouble(rateAsString);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error parsing JSON response: " + e.getMessage());
        }
    }

}