package dao;

import db.DBConnection;
import model.Employee;

import java.sql.*;

public class EmployeeDAO {

    public void addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (name, email, salary) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, emp.getName());
            pst.setString(2, emp.getEmail());
            pst.setDouble(3, emp.getSalary());
            pst.executeUpdate();
            System.out.println("Employee added successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void viewEmployees() {
        String sql = "SELECT * FROM employees";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email") +
                        ", Salary: " + rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET name=?, email=?, salary=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, emp.getName());
            pst.setString(2, emp.getEmail());
            pst.setDouble(3, emp.getSalary());
            pst.setInt(4, emp.getId());
            pst.executeUpdate();
            System.out.println("Employee updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Employee deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
}
