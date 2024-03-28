package yaroslav.redickh_idf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "exchange_rate")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_pair")
    private String currencyPair;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "date")
    private Date date;

}
