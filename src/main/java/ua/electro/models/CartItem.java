package ua.electro.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem implements Serializable {

    static final long serialVersionUID = -5765298360908027461L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @Min(value = 0, message = "Quantity can't be negative")
    @Max(value = 1000, message = "Quantity is too large (max 1,000)")
    @NotNull(message = "Quantity can't be empty")
    private Integer quantity;

    public CartItem(Long id, User user, Product product, Integer quantity) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }
}
