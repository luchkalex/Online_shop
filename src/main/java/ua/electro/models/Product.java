package ua.electro.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "title"})
public class Product implements Serializable {

    static final long serialVersionUID = 4214776066879963819L;

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
        this.photo = product.photo;
        this.quantity = quantity;
        this.creatingDate = product.creatingDate;
        this.category = product.category;
        this.productStatus = product.productStatus;
        this.outcomes = product.outcomes;
        this.incomes = product.incomes;
        this.priceHistories = product.priceHistories;
        this.cartItems = product.cartItems;
        this.valuesOfFeatures = product.valuesOfFeatures;
        this.statistic = product.statistic;
        this.rating = product.rating;
    }


    @NonNull
    @Length(max = 255, message = "Title is too long (max - 45 symbols)")
    @NotBlank(message = "Title can't be empty!")
    private String title;

    @NonNull
    @Length(max = 800, message = "Description is too long (max - 800 symbols)")
    @NotBlank(message = "Description can't be empty!")
    private String description;

    @Column(name = "price", columnDefinition = "float default 0")
    @NotNull(message = "Price can't be empty")
    private Float price;

    @Column(name = "discount", columnDefinition = "float default 0")
    private Float discount;

    private String photo;

    @Transient
    private Long quantity;

    @Column(name = "rating", columnDefinition = "float default 0")
    private Float rating;

    @OneToOne(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ProductStatistic statistic;

    //    @NonNull
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creatingDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_status", columnDefinition = "bigint default 3")
    private ProductStatuses productStatus;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Outcome> outcomes;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Income> incomes;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PriceHistory> priceHistories;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "feature_of_product",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "feature_id")
            }
    )
    private Set<ValueOfFeature> valuesOfFeatures = new HashSet<>();

}
