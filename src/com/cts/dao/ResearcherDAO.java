package com.cts.dao;

import com.cts.exception.EntityNotFoundException;
import com.cts.model.Researcher;
import com.cts.utils.DatabaseConnection;
import com.cts.utils.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResearcherDAO {

    public void addResearcher(Researcher researcher) {
        String query = "INSERT INTO Researcher (name, email, phone_number, specialization) VALUES (?, ?, ?, ?)";
        if (Helper.isTableEmpty("Researcher")) {
            Helper.resetAutoIncrement("Researcher", 1);
        }
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, researcher.getName());
            pst.setString(2, researcher.getEmail());
            pst.setString(3, researcher.getPhoneNumber());
            pst.setString(4, researcher.getSpecialization());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Researcher getResearcher(int researcherId) throws EntityNotFoundException {
        String query = "SELECT * FROM Researcher WHERE researcher_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, researcherId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Researcher researcher = new Researcher();
                researcher.setResearcherId(rs.getInt("researcher_id"));
                researcher.setName(rs.getString("name"));
                researcher.setEmail(rs.getString("email"));
                researcher.setPhoneNumber(rs.getString("phone_number"));
                researcher.setSpecialization(rs.getString("specialization"));
                return researcher;
            } else {
                throw new EntityNotFoundException("Researcher with ID " + researcherId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while retrieving the researcher", e);
        }
    }

    public void updateResearcher(Researcher researcher) throws EntityNotFoundException {
        String checkQuery = "SELECT COUNT(*) FROM Researcher WHERE researcher_id = ?";
        String updateQuery = "UPDATE Researcher SET name = ?, email = ?, phone_number = ?, specialization = ? WHERE researcher_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement checkPst = con.prepareStatement(checkQuery);
             PreparedStatement updatePst = con.prepareStatement(updateQuery)) {
            checkPst.setInt(1, researcher.getResearcherId());
            ResultSet rs = checkPst.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new EntityNotFoundException("Researcher with ID " + researcher.getResearcherId() + " not found.");
            }

            updatePst.setString(1, researcher.getName());
            updatePst.setString(2, researcher.getEmail());
            updatePst.setString(3, researcher.getPhoneNumber());
            updatePst.setString(4, researcher.getSpecialization());
            updatePst.setInt(5, researcher.getResearcherId());
            updatePst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating the researcher", e);
        }
    }

    public void deleteResearcher(int researcherId) throws EntityNotFoundException {
        String checkQuery = "SELECT COUNT(*) FROM Researcher WHERE researcher_id = ?";
        String deleteQuery = "DELETE FROM Researcher WHERE researcher_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement checkPst = con.prepareStatement(checkQuery);
             PreparedStatement deletePst = con.prepareStatement(deleteQuery)) {
            checkPst.setInt(1, researcherId);
            ResultSet rs = checkPst.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new EntityNotFoundException("Researcher with ID " + researcherId + " not found.");
            }

            deletePst.setInt(1, researcherId);
            deletePst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while deleting the researcher", e);
        }
    }

    public List<Researcher> getAllResearchers() throws EntityNotFoundException {
        List<Researcher> researchers = new ArrayList<>();
        String query = "SELECT * FROM Researcher";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Researcher researcher = new Researcher();
                researcher.setResearcherId(rs.getInt("researcher_id"));
                researcher.setName(rs.getString("name"));
                researcher.setEmail(rs.getString("email"));
                researcher.setPhoneNumber(rs.getString("phone_number"));
                researcher.setSpecialization(rs.getString("specialization"));
                researchers.add(researcher);
            }
            if (researchers.isEmpty()) {
                throw new EntityNotFoundException("No Researchers found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while retrieving all researchers", e);
        }
        return researchers;
    }
}
