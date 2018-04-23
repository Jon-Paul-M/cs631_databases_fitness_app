package edu.njit.cs631.fitness.data.entity;

import javax.persistence.*;

@Entity
@Table(name="ROOM")
public class Room {


    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="ROOM_ID", nullable=false)
    private Integer id;
    public Integer getId() {
        return id;
    }

    @Column(name="CAPACITY")
    private Integer capacity;
    public Integer getCapacity() {
        return capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Column(name="ROOM_NUMBER")
    private String roomNumber;
    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Column(name="BUILDING_NAME")
    private String buildingName;
    public String getBuildingName() {
        return buildingName;
    }
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
