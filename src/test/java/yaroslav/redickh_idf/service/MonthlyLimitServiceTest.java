package yaroslav.redickh_idf.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yaroslav.redickh_idf.entity.MonthlyLimit;
import yaroslav.redickh_idf.repositories.MonthlyLimitRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MonthlyLimitServiceTest {

    @Mock
    private MonthlyLimitRepository monthlyLimitRepository;

    @InjectMocks
    private MonthlyLimitService monthlyLimitService;

    @Test
    public void testSaveMonthLimit() {
        MonthlyLimit limit = new MonthlyLimit();
        when(monthlyLimitRepository.save(limit)).thenReturn(limit);
        MonthlyLimit savedLimit = monthlyLimitService.saveMonthLimit(limit);
        verify(monthlyLimitRepository).save(limit);
        assert(savedLimit).equals(limit);
    }

    @Test
    public void testChangeLimit_ExistingLimit() {
        Long userId = 1L;
        Double newLimit = 1500.0;
        MonthlyLimit existingLimit = new MonthlyLimit();
        existingLimit.setUserId(userId);
        List<MonthlyLimit> limits = new ArrayList<>();
        limits.add(existingLimit);
        when(monthlyLimitRepository.findByUserId(userId)).thenReturn(limits);
        when(monthlyLimitRepository.save(existingLimit)).thenReturn(existingLimit);
        MonthlyLimit updatedLimit = monthlyLimitService.changeLimit(userId, newLimit);
        verify(monthlyLimitRepository).findByUserId(userId);
        verify(monthlyLimitRepository).save(existingLimit);
        assert(updatedLimit.getLimitUSD()).equals(newLimit);
    }

    @Test
    public void testChangeLimit_NewLimit() {
        Long userId = 1L;
        Double newLimit = 1500.0;
        List<MonthlyLimit> emptyLimits = new ArrayList<>();
        when(monthlyLimitRepository.findByUserId(userId)).thenReturn(emptyLimits);
        when(monthlyLimitRepository.save(any(MonthlyLimit.class))).thenReturn(new MonthlyLimit());
        MonthlyLimit updatedLimit = monthlyLimitService.changeLimit(userId, newLimit);
        verify(monthlyLimitRepository).findByUserId(userId);
        verify(monthlyLimitRepository).save(any(MonthlyLimit.class));
        assert(updatedLimit.getUserId()).equals(userId);
        assert(updatedLimit.getLimitUSD()).equals(newLimit);
    }
}
