package yaroslav.redickh_idf.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yaroslav.redickh_idf.entity.Transaction;
import yaroslav.redickh_idf.repositories.TransactionRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testSaveTransaction() {
        Transaction transaction = new Transaction();
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        verify(transactionRepository).save(transaction);
        assert(savedTransaction).equals(transaction);
    }
}
