package ua.electro.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/*Categories of products*/
@Entity
@Data
@NoArgsConstructor
public class Category {

    /*FIXME: Category.ID Integer -> Long (Controller get from form Integer can't parse into Long)*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    private Integer id;

    @NonNull
    @Length(max = 45, message = "Title is too long (max - 45 symbols)")
    @NotBlank(message = "Title can't be empty!")
    private String title;

    private boolean active;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Product> products;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeaturesOfCategory> featuresOfCategory;

}
