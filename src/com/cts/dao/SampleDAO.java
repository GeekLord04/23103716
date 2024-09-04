package com.cts.dao;

import com.cts.exception.EntityNotFoundException;
import com.cts.model.Sample;
import com.cts.utils.DatabaseConnection;
import com.cts.utils.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SampleDAO {

    public void addSample(Sample sample) {
        String query = "INSERT INTO Sample (experiment_id, name, type, quantity) VALUES (?, ?, ?, ?)";
        if (Helper.isTableEmpty("Sample")) {
            Helper.resetAutoIncrement("Sample", 1);
        }
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, sample.getExperimentId());
            pst.setString(2, sample.getName());
            pst.setString(3, sample.getType());
            pst.setInt(4, sample.getQuantity());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Sample getSample(int sampleId) throws EntityNotFoundException {
        String query = "SELECT * FROM Sample WHERE sample_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, sampleId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Sample sample = new Sample();
                sample.setSampleId(rs.getInt("sample_id"));
                sample.setExperimentId(rs.getInt("experiment_id"));
                sample.setName(rs.getString("name"));
                sample.setType(rs.getString("type"));
                sample.setQuantity(rs.getInt("quantity"));
                return sample;
            } else {
                throw new EntityNotFoundException("Sample with ID " + sampleId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while retrieving the sample", e);
        }
    }

    public void updateSample(Sample sample) throws EntityNotFoundException {
        String checkQuery = "SELECT COUNT(*) FROM Sample WHERE sample_id = ?";
        String updateQuery = "UPDATE Sample SET experiment_id = ?, name = ?, type = ?, quantity = ? WHERE sample_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement checkPst = con.prepareStatement(checkQuery);
             PreparedStatement updatePst = con.prepareStatement(updateQuery)) {
            checkPst.setInt(1, sample.getSampleId());
            ResultSet rs = checkPst.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new EntityNotFoundException("Sample with ID " + sample.getSampleId() + " not found.");
            }

            updatePst.setInt(1, sample.getExperimentId());
            updatePst.setString(2, sample.getName());
            updatePst.setString(3, sample.getType());
            updatePst.setInt(4, sample.getQuantity());
            updatePst.setInt(5, sample.getSampleId());
            updatePst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating the sample", e);
        }
    }

    public void deleteSample(int sampleId) throws EntityNotFoundException {
        String checkQuery = "SELECT COUNT(*) FROM Sample WHERE sample_id = ?";
        String deleteQuery = "DELETE FROM Sample WHERE sample_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement checkPst = con.prepareStatement(checkQuery);
             PreparedStatement deletePst = con.prepareStatement(deleteQuery)) {
            checkPst.setInt(1, sampleId);
            ResultSet rs = checkPst.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new EntityNotFoundException("Sample with ID " + sampleId + " not found.");
            }

            deletePst.setInt(1, sampleId);
            deletePst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while deleting the sample", e);
        }
    }

    public List<Sample> getAllSamples() throws EntityNotFoundException {
        List<Sample> samples = new ArrayList<>();
        String query = "SELECT * FROM Sample";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Sample sample = new Sample();
                sample.setSampleId(rs.getInt("sample_id"));
                sample.setExperimentId(rs.getInt("experiment_id"));
                sample.setName(rs.getString("name"));
                sample.setType(rs.getString("type"));
                sample.setQuantity(rs.getInt("quantity"));
                samples.add(sample);
            }
            if (samples.isEmpty()) {
                throw new EntityNotFoundException("No Samples found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while retrieving all samples", e);
        }
        return samples;
    }
}
