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
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Product product;

    @Column(name = "sold", columnDefinition = "bigint default 0")
    private Long sold;

    @Column(name = "viewed", columnDefinition = "bigint default 0")
    private Long viewed;

}
