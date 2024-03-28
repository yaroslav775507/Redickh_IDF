package yaroslav.redickh_idf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yaroslav.redickh_idf.entity.Transaction;
import yaroslav.redickh_idf.service.TransactionService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        transaction.setDateTime(LocalDateTime.now());
        return transactionService.saveTransaction(transaction);
    }
}
