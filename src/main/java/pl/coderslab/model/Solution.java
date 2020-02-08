package pl.coderslab.model;

import java.sql.Timestamp;

public class Solution {
    private int id;
    private Timestamp created;
    private Timestamp updated;
    private String description;
    private int users_id;
    private int exercise_id;

    public Solution(int id, Timestamp created, Timestamp updated, String description, int users_id, int exercise_id) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.users_id = users_id;
        this.exercise_id = exercise_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }
}
