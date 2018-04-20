package edu.njit.cs631.fitness.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TITLES")
public class Title {

	private Integer id;
	private String title;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="TITLE_ID", nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="TITLE", nullable=false)
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


}
