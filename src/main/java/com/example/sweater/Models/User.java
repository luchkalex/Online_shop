package com.example.sweater.Models;

import javax.persistence.*;
import java.util.Set;

/*TODO: Add description for Annotations*/

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean active;

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
}
