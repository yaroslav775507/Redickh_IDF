package yaroslav.redickh_idf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "monthly_limits")
public class MonthlyLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "expense_category")
    private String expenseCategory;

    @Column(name = "limit_usd")
    private Double limitUSD;

    @Column(name = "data_limit")
    private LocalDateTime dateTime;

    public MonthlyLimit() {
        this.limitUSD = 1000.0;
    }

}
