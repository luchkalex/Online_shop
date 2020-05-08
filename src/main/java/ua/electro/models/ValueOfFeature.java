package ua.electro.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@Table(name = "value_of_feature")
public class ValueOfFeature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Length(max = 45, message = "Title is too long (max - 45 symbols)")
    @NotBlank(message = "Title can't be empty!")
    private String title;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @ManyToMany
    @JoinTable(
            name = "feature_of_product",
            joinColumns = {@JoinColumn(name = "feature_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")
            }
    )
    private Set<Product> products = new HashSet<>();

    public ValueOfFeature(Long id) {
        this.id = id;
    }
}
