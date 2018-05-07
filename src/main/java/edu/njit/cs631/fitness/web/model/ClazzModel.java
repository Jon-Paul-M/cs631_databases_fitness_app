package edu.njit.cs631.fitness.web.model;

import edu.njit.cs631.fitness.data.entity.Clazz;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClazzModel {

	private static final String DURATION_MESSAGE = "Duration is in minutes. Please provide a value between 1 and 480.";

	public ClazzModel() {
		super();
	}

	private final static String makeSelection = "You must make a selection for this field.";

	private Integer id;

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

    @NotNull(message=DURATION_MESSAGE)
    @Min(value = 1, message = DURATION_MESSAGE)
    @Max(value = 480, message = DURATION_MESSAGE)
	private Integer duration;


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
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
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

	public void copyFromClazz(Clazz clazz) {
	    setInstructor(clazz.getInstructor().getId());
	    setExercise(clazz.getExercise().getId());
	    setRoom(clazz.getRoom().getId());
        LocalDateTime start = clazz.getStart().toLocalDateTime();
        setStartMM("" + start.getMonthValue());
        setStartDD("" + start.getDayOfMonth());
        setStartYYYY("" + start.getYear());
        setStartHH("" + (start.getHour() > 12 ? "" + (start.getHour() - 12) : "" + start.getHour()));
        setStartMI("" + start.getMinute());
        setStartMeridiem("" + (start.getHour() > 12 ? "PM" : "AM"));
        setDuration(clazz.getDuration());
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
