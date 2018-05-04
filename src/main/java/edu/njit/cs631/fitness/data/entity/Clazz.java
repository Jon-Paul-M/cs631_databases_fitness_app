package edu.njit.cs631.fitness.data.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import edu.njit.cs631.fitness.data.entity.security.User;

@Entity
@Table(name=Clazz.TABLE_NAME)
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


    @OneToOne(fetch = FetchType.EAGER)
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

    public boolean hasUserRegistered(User user) {
        return this.members.contains(user);
    }

    // Registered members for the class
    // TODO: See issue #34
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "REGISTER",
            joinColumns = @JoinColumn(
                    name = "CLASS_ID", referencedColumnName = "CLASS_ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "USER_ID", referencedColumnName = "USER_ID"))
    private Set<User> members = new HashSet<>();
    public Set<User> getMembers() {
    	return members;
    }
    public void setMembers(Set<User> users) {
    	this.members = users;
    }


    public int getTotalRegistered() {
        return this.getMembers().size();
    }

    public int getCapacity() {
        if (room == null) return 0;
        return room.getCapacity();
    }

    public boolean isInFuture() {
        return this.start.after(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Column(name="START_DATETIME")
    private Timestamp start;
	public Timestamp getStart() {
		return start;
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
    
	@Column(name="DURATION")
	private Integer duration; // in minutes
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@Basic(fetch=FetchType.LAZY)
	@Formula(value = "SPOTS_REMAINING(CLASS_ID)")
	private Integer spotsRemaining;
	public Integer getSpotsRemaining() {
		return spotsRemaining;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Clazz [id=");
		builder.append(id);
		builder.append(", exercise=");
		builder.append(exercise);
		builder.append(", room=");
		builder.append(room);
		builder.append(", instructor=");
		builder.append(instructor);
		builder.append(", members=");
		builder.append(members);
		builder.append(", start=");
		builder.append(start);
		builder.append(", duration=");
		builder.append(duration);
		builder.append(", spotsRemaining=");
		builder.append(spotsRemaining);
		builder.append("]");
		return builder.toString();
	}
	
}
