package edu.njit.cs631.medical.data.entity.security;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="PRIVILEGE")
public class Privilege {

    private Integer id;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="PRIVILEGE_ID", nullable=false)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    private String name;
    @Column(name="NAME", nullable=false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @ManyToMany(mappedBy = "PRIVILEGES")
    private Collection<Role> roles;

}
