package com.example.sweater.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

/*TODO: Add description for Annotations*/

@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Username can't be empty!")
    private String username;

    @NotBlank(message = "Password can't be empty!")
    private String password;

    /*Transient says to hibernate not to add this field to db*/
    @Transient
    private String conf_password;

    private boolean active;

    @Email(message = "Email is not correct!")
    @NotBlank(message = "Email can't be empty!")
    private String email;

    private String activationCode;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getConf_password() {
        return conf_password;
    }

    public void setConf_password(String conf_password) {
        this.conf_password = conf_password;
    }
}
