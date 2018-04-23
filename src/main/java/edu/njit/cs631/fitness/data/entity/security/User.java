package edu.njit.cs631.fitness.data.entity.security;

import edu.njit.cs631.fitness.data.entity.Member;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    public User() {
        super();
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="USER_ID", nullable=false)
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String email;
    @Column(name="EMAIL", nullable=false)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    private String name;
    @Column(name="NAME", nullable=false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="ENABLED", nullable=false)
    private boolean enabled;
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name="TOKEN_EXPIRED", nullable=false)
    private boolean tokenExpired;
    public boolean isTokenExpired() {
        return tokenExpired;
    }
    public void setTokenExpired(boolean tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    @Column(name="PASSWORD_HASH", nullable=false)
    private String passwordHash;
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String hash) {
        passwordHash = hash;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(
                    name = "USER_ID", referencedColumnName = "USER_ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "ROLE_ID"))
    private Collection<Role> roles;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return String.format("<User %s %s>",
                id, getEmail(), getName());
    }
}
