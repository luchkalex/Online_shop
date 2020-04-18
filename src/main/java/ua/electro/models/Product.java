package ua.electro.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "title"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Product(Long id) {
        this.id = id;
    }

    public Product(Product product, Long quantity) {
        this.id = product.id;
        this.title = product.title;
        this.description = product.description;
        this.price = product.price;
        this.discount = product.discount;
        this.rating = product.rating;
        this.popularity = product.popularity;
        this.photo = product.photo;
        this.quantity = quantity;
        this.release_date = product.release_date;
        this.category = product.category;
        this.productStatus = product.productStatus;
        this.outcomes = product.outcomes;
        this.incomes = product.incomes;
        this.priceHistories = product.priceHistories;
        this.wishlist_users = product.wishlist_users;
        this.cart_users = product.cart_users;
    }

    @NonNull
    @Length(max = 45, message = "Title is too long (max - 45 symbols)")
    @NotBlank(message = "Title can't be empty!")
    private String title;

    @NonNull
    @Length(max = 800, message = "Description is too long (max - 800 symbols)")
    @NotBlank(message = "Description can't be empty!")
    private String description;

    //    @Pattern(regexp = "\\d{1,6}", message = "Wrong price")
    @NotNull(message = "Price can't be empty")
    private Integer price;

    private int discount;

    private float rating;

    private float popularity;

    private String photo;

    @Transient
    private Long quantity;

    //    @NonNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.DATE)
    private Date release_date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_status_id")
    private ProductStatuses productStatus;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Outcome> outcomes;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Income> incomes;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PriceHistory> priceHistories;


    @ManyToMany
    @JoinTable(
            name = "wishlist_items",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")
            }
    )
    private Set<User> wishlist_users = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "cart_items",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")
            }
    )
    private Set<User> cart_users = new HashSet<>();

}
