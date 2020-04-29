package ua.electro.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatuses implements Serializable {
    //    NOT_AVAILABLE, IN_STOCK, PRE_ORDER, NEW, DELETED
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    private Long id;

    @NonNull
    @NotNull(message = "Title can't be empty")
    private String title;

    @OneToMany(mappedBy = "productStatus", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Product> products;
}
