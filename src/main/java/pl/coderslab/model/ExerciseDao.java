package pl.coderslab.model;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;
import pl.coderslab.util.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class ExerciseDao {
    private static final String CREATE_EXERCISE_QUERY =
            "INSERT INTO exercise(title, description) VALUES (?, ?)";
    private static final String READ_EXERCISE_QUERY =
            "SELECT * FROM exercise WHERE id= ?";
    private static final String UPDATE_EXERCISE_QUERY =
            "UPDATE exercise SET title = ?, description = ? WHERE id = ?";
    private static final String DELETE_EXERCISE_QUERY =
            "DELETE FROM exercise WHERE id = ?";
    private static final String FIND_ALL_EXERCISE_QUERY =
            "SELECT * FROM exercise";
    private static final String FIND_ALL_WITH_SOLUTION_BY_USER_ID = "SELECT DISTINCT exercise.id, exercise.title, exercise.description\n" +
            "FROM exercise\n" +
            "         LEFT JOIN solution s on exercise.id = s.exercise_id\n" +
            "WHERE users_id IS NULL\n" +
            "   OR users_id != ?\n" +
            "ORDER BY id";

    public Exercise create(Exercise exercise) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_EXERCISE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exercise read(int exerciseId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_EXERCISE_QUERY);
            statement.setInt(1, exerciseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Exercise exercise = new Exercise(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"));
                return exercise;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Exercise exercise) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_EXERCISE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int exerciseId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_EXERCISE_QUERY);
            statement.setInt(1, exerciseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Exercise[] addToArray(Exercise e, Exercise[] exercises) {
        Exercise[] tmpExercise = Arrays.copyOf(exercises, exercises.length + 1);
        tmpExercise[exercises.length] = e;
        return tmpExercise;
    }

    public Exercise[] findAll() {
        try (Connection conn = DbUtil.getConnection()) {
            Exercise[] exercises = new Exercise[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISE_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"));
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void printExercise(Exercise exercise) {
        int id = exercise.getId();
        String title = exercise.getTitle();
        String description = exercise.getDescription();
        System.out.println(id + " " + title + " " + description);
    }

    public Exercise[] findAllWithSolutionByUserId(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            Exercise[] exercises = new Exercise[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_WITH_SOLUTION_BY_USER_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"));
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}