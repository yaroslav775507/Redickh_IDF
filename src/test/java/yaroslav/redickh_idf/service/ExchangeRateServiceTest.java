package yaroslav.redickh_idf.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yaroslav.redickh_idf.entity.ExchangeRate;
import yaroslav.redickh_idf.repositories.ExchangeRateRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Test
    public void testSaveExchange() {
        ExchangeRate exchangeRate = new ExchangeRate();
        when(exchangeRateRepository.save(exchangeRate)).thenReturn(exchangeRate);
        ExchangeRate savedExchangeRate = exchangeRateService.saveExchange(exchangeRate);
        verify(exchangeRateRepository).save(exchangeRate);
        assert(savedExchangeRate).equals(exchangeRate);
    }
}
