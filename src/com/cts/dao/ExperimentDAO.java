package com.cts.dao;

import com.cts.exception.EntityNotFoundException;
import com.cts.model.Experiment;
import com.cts.utils.DatabaseConnection;
import com.cts.utils.Helper;

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

    public Experiment getExperiment(int experimentId) throws EntityNotFoundException {
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
            } else {
                throw new EntityNotFoundException("Experiment with ID " + experimentId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // You can wrap and rethrow the SQLException if needed
            throw new RuntimeException("Error while retrieving the experiment", e);
        }
    }


    public void updateExperiment(Experiment experiment) throws EntityNotFoundException {
        String checkQuery = "SELECT COUNT(*) FROM Experiment WHERE experiment_id = ?";
        String updateQuery = "UPDATE Experiment SET name = ?, description = ?, start_date = ?, end_date = ? WHERE experiment_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement checkPst = con.prepareStatement(checkQuery);
             PreparedStatement updatePst = con.prepareStatement(updateQuery)) {
            checkPst.setInt(1, experiment.getExperimentId());
            ResultSet rs = checkPst.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new EntityNotFoundException("Experiment with ID " + experiment.getExperimentId() + " not found.");
            }

            updatePst.setString(1, experiment.getName());
            updatePst.setString(2, experiment.getDescription());
            updatePst.setDate(3, experiment.getStartDate());
            updatePst.setDate(4, experiment.getEndDate());
            updatePst.setInt(5, experiment.getExperimentId());
            updatePst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // You can wrap and rethrow the SQLException if needed
            throw new RuntimeException("Error while updating the experiment", e);
        }
    }


    public void deleteExperiment(int experimentId) throws EntityNotFoundException {
        String checkQuery = "SELECT COUNT(*) FROM Experiment WHERE experiment_id = ?";
        String deleteQuery = "DELETE FROM Experiment WHERE experiment_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement checkPst = con.prepareStatement(checkQuery);
             PreparedStatement deletePst = con.prepareStatement(deleteQuery)) {
            checkPst.setInt(1, experimentId);
            ResultSet rs = checkPst.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new EntityNotFoundException("Experiment with ID " + experimentId + " not found.");
            }

            deletePst.setInt(1, experimentId);
            deletePst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // You can wrap and rethrow the SQLException if needed
            throw new RuntimeException("Error while deleting the experiment", e);
        }
    }


    public List<Experiment> getAllExperiments() throws EntityNotFoundException {
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

            if (experiments.isEmpty()) {
                throw new EntityNotFoundException("No Experiments found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while retrieving all experiments", e);
        }

        return experiments;
    }

}

