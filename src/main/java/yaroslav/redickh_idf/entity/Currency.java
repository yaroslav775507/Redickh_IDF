package yaroslav.redickh_idf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "currency")
public class Currency  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pair")
    private String currencyPair;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "date_currancy")
    private LocalDateTime date;
}
