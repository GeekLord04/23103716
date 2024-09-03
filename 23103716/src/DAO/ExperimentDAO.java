package DAO;

import Model.Experiment;
import Utils.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ExperimentDAO {
    public void addExperiment(Experiment experiment) {
        String query = "INSERT INTO Experiment (name, description, start_date, end_date) VALUES (?, ?, ?, ?)";
        if (Helper.isTableEmpty("Experiment")){
            Helper.resetAutoIncrement("Experiment",1);
        }
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, experiment.getName());
                pst.setString(2, experiment.getDescription());
                pst.setDate(3, experiment.getStartDate());
                pst.setDate(4, experiment.getEndDate());
                pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Experiment getExperiment(int experimentId) {
        String query = "SELECT * FROM Experiment WHERE experiment_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, experimentId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Experiment experiment = new Experiment();
                experiment.setExperimentId(rs.getInt("experiment_id"));
                experiment.setName(rs.getString("name"));
                experiment.setDescription(rs.getString("description"));
                experiment.setStartDate(rs.getDate("start_date"));
                experiment.setEndDate(rs.getDate("end_date"));
                return experiment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateExperiment(Experiment experiment) {
        String query = "UPDATE Experiment SET name = ?, description = ?, start_date = ?, end_date = ? WHERE experiment_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, experiment.getName());
            pst.setString(2, experiment.getDescription());
            pst.setDate(3, experiment.getStartDate());
            pst.setDate(4, experiment.getEndDate());
            pst.setInt(5, experiment.getExperimentId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExperiment(int experimentId) {
        String query = "DELETE FROM Experiment WHERE experiment_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, experimentId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Experiment> getAllExperiments() {
        List<Experiment> experiments = new ArrayList<>();
        String query = "SELECT * FROM Experiment";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Experiment experiment = new Experiment();
                experiment.setExperimentId(rs.getInt("experiment_id"));
                experiment.setName(rs.getString("name"));
                experiment.setDescription(rs.getString("description"));
                experiment.setStartDate(rs.getDate("start_date"));
                experiment.setEndDate(rs.getDate("end_date"));
                experiments.add(experiment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return experiments;
    }
}

