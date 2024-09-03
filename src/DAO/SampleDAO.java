package DAO;

import Model.Sample;
import Utils.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SampleDAO {
    public void addSample(Sample sample) {
        String query = "INSERT INTO Sample (experiment_id, name, type, quantity) VALUES (?, ?, ?, ?)";
        if (Helper.isTableEmpty("Sample")){
            Helper.resetAutoIncrement("Sample",1);
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

    public Sample getSample(int sampleId) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateSample(Sample sample) {
        String query = "UPDATE Sample SET experiment_id = ?, name = ?, type = ?, quantity = ? WHERE sample_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, sample.getExperimentId());
            pst.setString(2, sample.getName());
            pst.setString(3, sample.getType());
            pst.setInt(4, sample.getQuantity());
            pst.setInt(5, sample.getSampleId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSample(int sampleId) {
        String query = "DELETE FROM Sample WHERE sample_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, sampleId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Sample> getAllSamples() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return samples;
    }
}

