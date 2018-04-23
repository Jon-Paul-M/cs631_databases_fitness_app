package edu.njit.cs631.fitness.data.entity;


import javax.persistence.*;

@Entity
@Table(name="EXERCISE")
public class Exercise {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="EXERCISE_ID", nullable=false)
    private Integer id;
    public Integer getId() {
        return id;
    }

    @Column(name="NAME")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="DESCRIPTION")
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
