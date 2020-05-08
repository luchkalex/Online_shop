package ua.electro.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatuses implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String title;

    @OneToMany(mappedBy = "orderStatuses", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<OrderOfProduct> orders;

}
