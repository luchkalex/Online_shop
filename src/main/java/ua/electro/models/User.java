package ua.electro.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/*TODO: Add description for Annotations*/


@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "username"})
public class User implements UserDetails {

    static final long serialVersionUID = -3249357386608006686L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    private Long id;

    @NonNull
    @Length(max = 45, message = "Username is too long (max - 45 symbols)")
    @NotBlank(message = "Username can't be empty!")
    private String username;


    @NonNull
    @Length(max = 45, message = "Password is too long (max - 45 symbols)")
    @NotBlank(message = "Password can't be empty!")
    private String password;


    @NonNull
    @Email(message = "Email is not correct!")
    @NotBlank(message = "Email can't be empty!")
    private String email;


    private String address;

    private boolean active;

    private String activationCode;

    /*TODO: Make validator of phone*/
    @Length(max = 13, message = "Password is too long (max - 13 symbols)")
    private String phone;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Message> messages;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderOfProduct> orders;

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = {@JoinColumn(name = "channel_id")},
            inverseJoinColumns = {@JoinColumn(name = "subscriber_id")
            }
    )
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = {@JoinColumn(name = "subscriber_id")},
            inverseJoinColumns = {@JoinColumn(name = "channel_id")}
    )
    private Set<User> subscriptions = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "wishlist_items",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")
            }
    )
    private Set<Product> wishlist_products = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "cart_items",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")
            }
    )
    private Set<Product> products_users = new HashSet<>();

    /*@ElementCollection - We use @ElementCollection annotation to declare an element-collection
        mapping. All the records of the collection are stored in a separate table.
        The configuration for this table is specified using the @CollectionTable annotation.

    * @CollectionTable - The @CollectionTable annotation is used to specify the name of the
        table that stores all the records of the collection, and the JoinColumn that refers
        to the primary table.

    * @Enumerated
    * @JoinColumn
    * FetchType -  it is mean which type of uploading we use
    *   EAGER - mean that at user request all data of user will be uploaded at the moment. Use when have little data
    *   LAZY - only when we touch this field. Use when there are a lot of data*/

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }
}
