package models;

public class Enrollment {
    private int enrollId;
    private String studentId;
    private String courseId;
    private String enrollDate;

    public Enrollment(int enrollId, String studentId, String courseId, String enrollDate) {
        this.enrollId = enrollId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollDate = enrollDate;
    }

    // Getters and Setters
    public int getEnrollId() { return enrollId; }
    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public String getEnrollDate() { return enrollDate; }
}