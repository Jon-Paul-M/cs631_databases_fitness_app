package edu.njit.cs631.medical.data.entity.security;

import edu.njit.cs631.medical.data.entity.Person;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    public User() {
        super();
    }

    private Integer id;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="USER_ID", nullable=false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private boolean enabled;
    @Column(name="ENABLED", nullable=false)
    public boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private boolean tokenExpired;
    @Column(name="TOKEN_EXPIRED", nullable=false)
    public boolean getTokenExpired() {
        return tokenExpired;
    }
    public void setTokenExpired(boolean tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    private String email;
    @Column(name="EMAIL", nullable=false)
    public String getEmail() {
        return email;
    }
    public void setEmail(String value) {
        email = value;
    }

    private String passwordHash;
    @Column(name="PASSWORD_HASH", nullable=false)
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String hash) {
        passwordHash = hash;
    }

    private Person person;
    @JoinColumn(name = "PERSON_ID")
    @OneToOne(fetch = FetchType.LAZY)
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @ManyToMany
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(
                    name = "USER_ID", referencedColumnName = "USER_ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "ROLE_ID"))
    private Collection<Role> roles;
}
