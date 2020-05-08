package ua.electro.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "username"})
public class User implements UserDetails, Serializable {

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
    @NotBlank(message = "Password can't be empty!")
    private String password;


    @NonNull
    @Email(message = "Email is not correct!")
    @NotBlank(message = "Email can't be empty!")
    private String email;


    private String address;

    private boolean active;

    private String activationCode;

    @Pattern(regexp = "\\+\\d*$", message = "Wrong format of phone number")
    @Length(max = 13, message = "Phone number is too long (max - 13 symbols)")
    private String phone;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<OrderOfProduct> orders = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User(long id) {
        this.id = id;
    }

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
