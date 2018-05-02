package edu.njit.cs631.fitness.web.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ExerciseModel {

    @NotNull
    @Size(min = 1, message = "Must provide a name for this exercise.")
    private String exerciseName;

    @NotNull
    @Size(min = 1, message = "Must provide a description for this exercise.")
    private String description;

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String name) {
        this.exerciseName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "<name " + getExerciseName() + " desc " + getDescription() + ">";
    }
}
