package yaroslav.redickh_idf.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import yaroslav.redickh_idf.entity.Currency;
import yaroslav.redickh_idf.repositories.CurrencyExchangeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CurrencyExchangeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CurrencyExchangeRepository exchangeRepository;

    private CurrencyExchangeService currencyExchangeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        currencyExchangeService = new CurrencyExchangeService(restTemplate, "dummyApiKey", exchangeRepository);
    }

    @Test
    void testGetExchangeRate() {
        String expectedResponse = "{\"status\":200,\"message\":\"rates\",\"data\":{\"USDRUB\":\"64.1824\"}}";
        String expectedPairs = "USDRUB";
        String apiUrl = "https://currate.ru/api/?get=rates&pairs=" + expectedPairs.toUpperCase() + "&key=dummyApiKey";

        when(restTemplate.getForObject(apiUrl, String.class)).thenReturn(expectedResponse);

        String actualResponse = currencyExchangeService.getExchangeRate(expectedPairs);

        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate, times(1)).getForObject(apiUrl, String.class);
    }

    @Test
    void testUpdateExchangeRate() {
        String expectedResponse = "{\"status\":200,\"message\":\"rates\",\"data\":{\"USDRUB\":\"64.1824\"}}";
        String expectedPairs = "USDRUB";

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(expectedResponse);

        currencyExchangeService.updateExchangeRate();

        verify(exchangeRepository, times(1)).save(any(Currency.class));
    }

    @Test
    void testSaveCurrency() {
        String expectedResponse = "{\"status\":200,\"message\":\"rates\",\"data\":{\"USDRUB\":\"64.1824\"}}";
        String expectedPair = "USDRUB";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(expectedResponse);
        Currency savedCurrency = currencyExchangeService.saveCurrency(expectedPair);
        assertEquals(expectedPair, savedCurrency.getCurrencyPair());
        verify(exchangeRepository, times(1)).save(any(Currency.class));
    }
}
