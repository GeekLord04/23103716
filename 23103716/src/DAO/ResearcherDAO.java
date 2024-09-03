package DAO;

import Model.Researcher;
import Utils.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResearcherDAO {
    public void addResearcher(Researcher researcher) {
        String query = "INSERT INTO Researcher (name, email, phone_number, specialization) VALUES (?, ?, ?, ?)";
        if (Helper.isTableEmpty("Researcher")){
            Helper.resetAutoIncrement("Researcher",1);
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

    public Researcher getResearcher(int researcherId) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateResearcher(Researcher researcher) {
        String query = "UPDATE Researcher SET name = ?, email = ?, phone_number = ?, specialization = ? WHERE researcher_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, researcher.getName());
            pst.setString(2, researcher.getEmail());
            pst.setString(3, researcher.getPhoneNumber());
            pst.setString(4, researcher.getSpecialization());
            pst.setInt(5, researcher.getResearcherId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteResearcher(int researcherId) {
        String query = "DELETE FROM Researcher WHERE researcher_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, researcherId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Researcher> getAllResearchers() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return researchers;
    }
}
