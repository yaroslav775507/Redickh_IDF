package yaroslav.redickh_idf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yaroslav.redickh_idf.entity.MonthlyLimit;

import java.util.List;

@Repository
public interface MonthlyLimitRepository extends JpaRepository<MonthlyLimit, Long> {
    List<MonthlyLimit> findByUserId(Long userId);
}
