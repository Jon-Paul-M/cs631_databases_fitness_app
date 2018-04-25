package edu.njit.cs631.fitness.data.entity;

import edu.njit.cs631.fitness.data.entity.security.User;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="CLASS")
//weird things happen in java when you name a class 'Class'
public class Clazz { 

	public static final String TABLE_NAME = "CLASS";
	
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="CLASS_ID", nullable=false)
    private Integer id;
    public Integer getId() {
        return id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="EXERCISE_ID")
    private Exercise exercise;
    public Exercise getExercise() {
        return exercise;
    }
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ROOM_ID")
    private Room room;
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INSTRUCTOR_ID")
    private User instructor;
    public User getInstructor() {
    	return instructor;
    }
    public void setInstructor(User instructor) {
    	this.instructor = instructor;
    }

    @Transient
    public User getUser() {
        return getInstructor();
    }
    public void setUser(User user) {
        setInstructor(user);
    }

    // Registered members for the class
    @OneToMany
    @JoinTable(
            name="REGISTER",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "CLASS_ID")
    )
    private Set<Member> members = new HashSet<>();
    public Set<Member> getMembers() {
    	return members;
    }
    void setMembers(Set<Member> members) {
    	this.members = members;
    }

    @Column(name="START_DATETIME")
    private Date start;
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
    
	@Column(name="DURATION")
	private Integer duration;
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
}
