package edu.njit.cs631.medical.data.entity.security;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="ROLE")
public class Role {
    private Integer id;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="ROLE_ID", nullable=false)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    private String name;
    @Column(name="NAME", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    @ManyToMany(mappedBy = "ROLES")
    private Collection<User> users;

    private Collection<Privilege> privileges;

    @ManyToMany
    @JoinTable(
            name = "ROLES_PRIVILEGES",
            joinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "PRIVILEGE_ID", referencedColumnName = "PRIVILEGE_ID"))
    public Collection<Privilege> getPrivileges() {
        return privileges;
    }
    public void setPrivileges(final Collection<Privilege> privileges) {
        this.privileges = privileges;
    }
}
