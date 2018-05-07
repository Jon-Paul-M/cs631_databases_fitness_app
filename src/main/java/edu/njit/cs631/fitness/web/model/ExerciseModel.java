package edu.njit.cs631.fitness.web.model;

import edu.njit.cs631.fitness.data.entity.Exercise;

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

    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void copyFromExercise(Exercise exercise) {
        setExerciseName(exercise.getName());
        setId(exercise.getId());
        setDescription(exercise.getDescription());
    }
}
