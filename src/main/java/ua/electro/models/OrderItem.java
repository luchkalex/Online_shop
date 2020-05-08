package ua.electro.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@ToString(of = "id")
@EqualsAndHashCode(of = {"id", "product"})
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private OrderOfProduct order;

    @NotNull
    private Integer quantity;

    public OrderItem(Product product, Integer quantity, OrderOfProduct order) {
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }
}
