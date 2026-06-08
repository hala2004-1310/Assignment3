package dao;

import config.DBConnection;
import models.Enrollment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EnrollmentDAO {

    // دالة إضافة تسجيل جديد
    public boolean addEnrollment(String studentId, String courseId, String enrollDate) {
        if (checkDuplicate(studentId, courseId)) return false;

        String sql = "INSERT INTO enrollment (student_id, course_id, enroll_date) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            ps.setString(3, enrollDate);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Add Error: " + e.getMessage());
            return false;
        }
    }

    // دالة تحديث سجل موجود
    public boolean updateEnrollment(int enrollId, String studentId, String courseId, String enrollDate) {
        String sql = "UPDATE enrollment SET student_id=?, course_id=?, enroll_date=? WHERE enrollment_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            ps.setString(3, enrollDate);
            ps.setInt(4, enrollId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Update Error: " + e.getMessage());
            return false;
        }
    }

    // دالة حذف سجل
    public boolean deleteEnrollment(int enrollId) {
        String sql = "DELETE FROM enrollment WHERE enrollment_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, enrollId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Delete Error: " + e.getMessage());
            return false;
        }
    }

    // دالة جلب كل السجلات لعرضها في الجدول
    public ArrayList<Enrollment> getAllEnrollments() {
        ArrayList<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM enrollment";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Enrollment(
                    rs.getInt("enrollment_id"),
                    rs.getString("student_id"),
                    rs.getString("course_id"),
                    rs.getString("enroll_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("View Error: " + e.getMessage());
        }
        return list;
    }

    // دالة التحقق من عدم التكرار
    public boolean checkDuplicate(String studentId, String courseId) {
        String sql = "SELECT * FROM enrollment WHERE student_id=? AND course_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Check Error: " + e.getMessage());
            return false;
        }
    }
}