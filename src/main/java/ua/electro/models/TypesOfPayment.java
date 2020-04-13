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
public class TypesOfPayment {
    //    PICKUP, COURIER
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String title;

    @OneToMany(mappedBy = "typeOfPayment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderOfProduct> orders;
}