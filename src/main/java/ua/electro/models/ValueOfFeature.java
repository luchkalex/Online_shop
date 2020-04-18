package ua.electro.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@Table(name = "value_of_feature")
public class ValueOfFeature {

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
}
