package yaroslav.redickh_idf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yaroslav.redickh_idf.entity.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
}
