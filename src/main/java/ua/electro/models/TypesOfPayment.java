package ua.electro.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class TypesOfPayment {
    //    PICKUP, COURIER
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @NotNull(message = "Title can't be empty")
    private String title;

    @OneToMany(mappedBy = "typeOfPayment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderOfProduct> orders;
}