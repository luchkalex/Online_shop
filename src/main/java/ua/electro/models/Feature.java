package ua.electro.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Feature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @UniqueElements
    @Length(max = 45, message = "Title is too long (max - 45 symbols)")
    @NotBlank(message = "Title can't be empty!")
    private String title;

    @OneToMany(mappedBy = "feature", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeaturesOfCategory> featuresOfCategory;

    @OneToMany(mappedBy = "feature", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ValueOfFeature> valuesOfFeature;
}
