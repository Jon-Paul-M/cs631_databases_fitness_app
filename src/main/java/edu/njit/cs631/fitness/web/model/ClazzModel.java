package edu.njit.cs631.fitness.web.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ClazzModel {

	public ClazzModel() {
		super();
	}

	private final static String makeSelection = "You must make a selection for this field.";

	@NotNull
    @Min(value=1, message=makeSelection)
	private Integer instructor;

	@NotNull
    @Min(value=1, message=makeSelection)
	private Integer exercise;

	@NotNull
    @Min(value=1, message=makeSelection)
	private Integer room;

	@NotNull
    @Min(value=1, message=makeSelection)
	private String startMM;

	@NotNull
    @Min(value=1, message=makeSelection)
	private String startDD;

	@NotNull
    @Min(value=1, message=makeSelection)
	private String startYYYY;

	@NotNull
    @Min(value=1, message=makeSelection)
	private String startHH;

    @NotNull
    @Min(value=1, message=makeSelection)
	private String startMI;

	private String startMeridiem;

    @NotNull(message="Please provide a value between 0 and 9, inclusive.")
    @Min(value = 0)
    @Max(value=9)
	private Double duration;


    private LocalDateTime startTime;
	
	public Integer getInstructor() {
		return instructor;
	}
	public void setInstructor(Integer instructor) {
		this.instructor = instructor;
	}
	
	public Integer getExercise() {
		return exercise;
	}
	public void setExercise(Integer exercise) {
		this.exercise = exercise;
	}
	
	public Integer getRoom() {
		return room;
	}
	public void setRoom(Integer room) {
		this.room = room;
	}
	
	
	public String getStartMM() {
		return startMM;
	}
	public void setStartMM(String startMM) {
		this.startMM = startMM;
	}
	public String getStartDD() {
		return startDD;
	}
	public void setStartDD(String startDD) {
		this.startDD = startDD;
	}
	public String getStartYYYY() {
		return startYYYY;
	}
	public void setStartYYYY(String startYYYY) {
		this.startYYYY = startYYYY;
	}
	public String getStartHH() {
		return startHH;
	}
	public void setStartHH(String startHH) {
		this.startHH = startHH;
	}
	public String getStartMI() {
		return startMI;
	}
	public void setStartMI(String startMI) {
		this.startMI = startMI;
	}
	public String getStartMeridiem() {
		return startMeridiem;
	}
	public void setStartMeridiem(String startMeridiem) {
		this.startMeridiem = startMeridiem;
	}
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClazzModel [instructor=");
		builder.append(instructor);
		builder.append(", exercise=");
		builder.append(exercise);
		builder.append(", room=");
		builder.append(room);
		builder.append(", startMM=");
		builder.append(startMM);
		builder.append(", startDD=");
		builder.append(startDD);
		builder.append(", startYYYY=");
		builder.append(startYYYY);
		builder.append(", startHH=");
		builder.append(startHH);
		builder.append(", startMI=");
		builder.append(startMI);
		builder.append(", startMeridiem=");
		builder.append(startMeridiem);
		builder.append(", duration=");
		builder.append(duration);
		builder.append("]");
		return builder.toString();
	}

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
