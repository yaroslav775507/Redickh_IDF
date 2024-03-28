package yaroslav.redickh_idf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yaroslav.redickh_idf.entity.MonthlyLimit;
import yaroslav.redickh_idf.repositories.MonthlyLimitRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MonthlyLimitService {
    private final MonthlyLimitRepository monthlyLimitRepository;

    @Autowired
    public MonthlyLimitService(MonthlyLimitRepository monthlyLimitRepository) {
        this.monthlyLimitRepository = monthlyLimitRepository;
    }

    public MonthlyLimit saveMonthLimit(MonthlyLimit limit) {
        return monthlyLimitRepository.save(limit);
    }

    public MonthlyLimit changeLimit(Long userId, Double newLimit) {
        List<MonthlyLimit> limits = monthlyLimitRepository.findByUserId(userId);
        if (!limits.isEmpty()) {
            MonthlyLimit monthlyLimit = limits.get(0);
            monthlyLimit.setLimitUSD(newLimit);
            return monthlyLimitRepository.save(monthlyLimit);
        } else {
            MonthlyLimit monthlyLimit = new MonthlyLimit();
            monthlyLimit.setUserId(userId);
            monthlyLimit.setLimitUSD(newLimit);
            return monthlyLimitRepository.save(monthlyLimit);
        }
    }
}
