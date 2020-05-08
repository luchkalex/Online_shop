package ua.electro.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/*Categories of products*/
@Entity
@Data
@NoArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
public class Category implements Serializable {

    public Category(Category category) {
        this.id = category.id;
        this.title = category.title;
        this.products = category.products;
        this.featuresOfCategory = category.featuresOfCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    private Long id;

    @NonNull
    @Length(max = 45, message = "Title is too long (max - 45 symbols)")
    @NotNull(message = "Title can't be empty (null)")
    @NotBlank(message = "Title can't be empty!")
    private String title;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Product> products;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeaturesOfCategory> featuresOfCategory;

    public Category(Long category_id) {
        this.id = category_id;
    }
}
