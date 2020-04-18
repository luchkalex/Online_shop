package ua.electro.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatuses {
    //    WAITING, CANCELED, ACCEPTED, DONE

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String title;

    @OneToMany(mappedBy = "orderStatuses", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderOfProduct> orders;

}
