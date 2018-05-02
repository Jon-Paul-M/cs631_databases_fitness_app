package edu.njit.cs631.fitness.web.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RoomModel {

    @NotNull
    @Size(min = 1, message = "Must provide a room number.")
    private String roomNumber;

    @NotNull
    @Size(min = 1, message = "Must provide a building name.")
    private String buildingName;

    @NotNull
    @Min(value=1, message = "Must provide a room capacity.")
    private Integer capacity;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
