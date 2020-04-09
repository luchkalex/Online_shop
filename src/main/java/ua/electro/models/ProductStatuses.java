package ua.electro.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatuses {
    //    NOT_AVAILABLE, IN_STOCK, PRE_ORDER, NEW, DELETED
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String title;

    @OneToMany(mappedBy = "productStatus", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Product> products;
}
