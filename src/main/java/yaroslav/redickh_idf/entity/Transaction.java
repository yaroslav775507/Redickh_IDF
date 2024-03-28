package yaroslav.redickh_idf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_from")
    private Integer accountFrom;

    @Column(name = "account_to")
    private Integer accountTo;

    @Column(name = "currency_shortname")
    private String currencyShortName;
    //сумма транзакции
    @Column(name = "sum")
    private Double sum;

    @Column(name = "expense_category")
    private String expenseCategory;

    @Column(name = "datetime")
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_limit_id")
    private MonthlyLimit monthlyLimit;
}
