package edu.njit.cs631.medical.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Title {

	private Long id;
	private String title;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="title_id", nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="title", nullable=false)
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


}
