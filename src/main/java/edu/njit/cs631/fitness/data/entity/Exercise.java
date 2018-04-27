package edu.njit.cs631.fitness.data.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name=Exercise.TABLE_NAME)
public class Exercise {

	public static final String TABLE_NAME = "EXERCISE";
	
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
    
	@Override
	public String toString() {
		return "Exercise [id=" + id + "]";
	}
}
