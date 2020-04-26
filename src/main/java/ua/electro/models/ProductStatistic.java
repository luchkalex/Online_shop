package ua.electro.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Immutable
@Entity
@NoArgsConstructor
@Table(name = "product_statistic")
@EqualsAndHashCode(of = {"product"})
@ToString(of = {"product"})
public class ProductStatistic implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Product product;

    private Long sold;

    private Long viewed;

    private Long added_to_cart;

}
