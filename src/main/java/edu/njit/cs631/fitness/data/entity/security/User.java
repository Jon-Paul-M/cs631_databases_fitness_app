package edu.njit.cs631.fitness.data.entity.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import edu.njit.cs631.fitness.data.manager.PayrollQueries;
import edu.njit.cs631.fitness.data.projection.InstructorPayroll;

@Entity
@Table(name=User.TABLE_NAME)
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	// "USER" is a reserved word in postgres
	public static final String TABLE_NAME = "PERSON";

    public User() {
        super();
    }

    @Id
    @Basic(optional=false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    /* TODO: related to #28, members can't be instructors and vice versa
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IdOrGenerated")
    @GenericGenerator(name="IdOrGenerated",
                      strategy="edu.njit.cs631.fitness.tools.UseIdOrGenerate")
                      */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }
}
