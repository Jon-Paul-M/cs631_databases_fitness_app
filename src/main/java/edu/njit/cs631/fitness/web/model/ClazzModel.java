package edu.njit.cs631.fitness.web.model;

public class ClazzModel {

	public ClazzModel() {
		super();
	}

	private String instructor;
	private String exercise;
	private String room;
	private String startMM;
	private String startDD;
	private String startYYYY;
	private String startHH;
	private String startMI;
	private String startMeridiem;
	private String duration;
	
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	public String getExercise() {
		return exercise;
	}
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}
	
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
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
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
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
	
}
