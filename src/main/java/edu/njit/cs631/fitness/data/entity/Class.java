package edu.njit.cs631.fitness.data.entity;

import edu.njit.cs631.fitness.data.entity.security.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="CLASS")
public class Class {

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

    public User getInstructor() {
        return getUser();
    }
    public void setInstructor(User instructor) {
        setUser(instructor);
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INSTRUCTOR_ID")
    private User instructor;
    public User getUser() {
        return instructor;
    }
    public void setUser(User instructor) {
        this.instructor = instructor;
    }

    @OneToMany
    @JoinTable(
            name="REGISTER",
            joinColumns = @JoinColumn( name="CLASS_ID"),
            inverseJoinColumns = @JoinColumn( name="MEMBER_ID")
    )
    private Set<Member> members = new HashSet<>();
    public Set<Member> getMembers() { return members; }
    void setMembers(Set<Member> members) { this.members = members; }

}
