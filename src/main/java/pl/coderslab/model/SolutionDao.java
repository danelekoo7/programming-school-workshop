package pl.coderslab.model;

import pl.coderslab.model.Solution;
import pl.coderslab.model.User;
import pl.coderslab.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolutionDao {
    private static final String CREATE_SOLUTION_QUERY =
            "INSERT INTO solution(created, updated, description, users_id, exercise_id) VALUES (?, ?, ?, ?, ?)";
    private static final String READ_SOLUTION_QUERY =
            "SELECT * FROM solution WHERE id = ?";
    private static final String UPDATE_SOLUTION_QUERY =
            "UPDATE solution\n" +
                    "SET created = ?,\n" +
                    "    updated = ?,\n" +
                    "    description = ?,\n" +
                    "    users_id = ?,\n" +
                    "    exercise_id = ? WHERE id = ?";
    private static final String DELETE_SOLUTION_QUERY =
            "DELETE FROM solution WHERE id = ?";
    private static final String FIND_ALL_SOLUTION_QUERY =
            "SELECT * FROM solution";
    private static final String FIND_SOLUTIONS_BY_USER_ID = "SELECT *\n" +
            "FROM solution WHERE users_id = ?";
    private static final String FIND_ALL_BY_EXERCISE_ID = "SELECT *\n" +
            "FROM solution\n" +
            "WHERE exercise_id= ?\n" +
            "ORDER BY IF(updated > created, updated, created) DESC";


    private static final String FIND_RECENT = "SELECT * FROM solution ORDER BY created LIMIT ?";


    public List<Solution> findRecent(int size) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_RECENT)
        ) {
            int idx = 0;
            statement.setInt(++idx, size);
            ResultSet rs = statement.executeQuery();
            List<Solution> solutions = new ArrayList<>();
            while (rs.next()){
                Solution solution = new Solution(
                        rs.getInt("id"),
                        rs.getTimestamp("created"),
                        rs.getTimestamp("updated"),
                        rs.getString("description"),
                        rs.getInt("users_id"),
                        rs.getInt("exercise_id"));
                solutions.add(solution);
            }
            return solutions;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot find recent due to: ", e);
        }
    }

    public Solution create(Solution solution) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setTimestamp(1, solution.getCreated());
            statement.setTimestamp(2, solution.getUpdated());
            statement.setString(3, solution.getDescription());
            statement.setInt(4, solution.getUsers_id());
            statement.setInt(5, solution.getExercise_id());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                solution.setId(resultSet.getInt(1));
            }
            return solution;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution read(int solutionId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_SOLUTION_QUERY);
            statement.setInt(1, solutionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Solution solution = new Solution(resultSet.getInt("id"),
                        resultSet.getTimestamp("created"),
                        resultSet.getTimestamp("updated"),
                        resultSet.getString("description"),
                        resultSet.getInt("users_id"),
                        resultSet.getInt("exercise_id"));
                return solution;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Solution solution) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_SOLUTION_QUERY);
            statement.setTimestamp(1, solution.getCreated());
            statement.setTimestamp(2, solution.getUpdated());
            statement.setString(3, solution.getDescription());
            statement.setInt(4, solution.getUsers_id());
            statement.setInt(5, solution.getExercise_id());
            statement.setInt(6, solution.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int solutionId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_SOLUTION_QUERY);
            statement.setInt(1, solutionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Solution[] addToArray(Solution s, Solution[] solutions) {
        Solution[] tmpSolutions = Arrays.copyOf(solutions, solutions.length + 1);
        tmpSolutions[solutions.length] = s;
        return tmpSolutions;
    }

    public Solution[] findAll() {
        try (Connection conn = DbUtil.getConnection()) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_SOLUTION_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution(resultSet.getInt("id"),
                        resultSet.getTimestamp("created"),
                        resultSet.getTimestamp("updated"),
                        resultSet.getString("description"),
                        resultSet.getInt("users_id"),
                        resultSet.getInt("exercise_id"));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution[] findAllByUserId(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_SOLUTIONS_BY_USER_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution(resultSet.getInt("id"),
                        resultSet.getTimestamp("created"),
                        resultSet.getTimestamp("updated"),
                        resultSet.getString("description"),
                        resultSet.getInt("users_id"),
                        resultSet.getInt("exercise_id"));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void printSolution(Solution solution) {
        int id = solution.getId();
        Timestamp created = solution.getCreated();
        Timestamp updated = solution.getUpdated();
        String description = solution.getDescription();
        int users_is = solution.getUsers_id();
        int exercise_id = solution.getExercise_id();
        System.out.println(id + " " + created + " " + updated + " " + description + " "
                + users_is + " " + exercise_id);
    }

    public Solution[] findAllByExerciseId(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_EXERCISE_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution(resultSet.getInt("id"),
                        resultSet.getTimestamp("created"),
                        resultSet.getTimestamp("updated"),
                        resultSet.getString("description"),
                        resultSet.getInt("users_id"),
                        resultSet.getInt("exercise_id"));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
