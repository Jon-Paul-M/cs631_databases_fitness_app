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
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private boolean tokenExpired;
    @Column(name="TOKEN_EXPIRED", nullable=false)
    public boolean isTokenExpired() {
        return tokenExpired;
    }
    public void setTokenExpired(boolean tokenExpired) {
        this.tokenExpired = tokenExpired;
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

    private Collection<Role> roles;

    // Note: Moving these annotations to the getter method seems to
    // remove a category of errors where the hibernate core can't
    // find a relevant type to match against the Collection<Role>
    // see
    // https://stackoverflow.com/questions/6164123/org-hibernate-mappingexception-could-not-determine-type-for-java-util-set

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(
                    name = "USER_ID", referencedColumnName = "USER_ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "ROLE_ID"))
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return String.format("<Person %s %s %s>", id, getPerson().getEmail(), getPerson().getFirstName());
    }
}
