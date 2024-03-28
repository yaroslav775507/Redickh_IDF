package yaroslav.redickh_idf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yaroslav.redickh_idf.entity.Currency;
import yaroslav.redickh_idf.entity.ExchangeRate;

public interface CurrencyExchangeRepository extends JpaRepository<Currency, Long> {
}
