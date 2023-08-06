package com.jafar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/atypon";
    private static final String USER = "root";
    private static final String PASS = "root";

    private Connection conn = null;
    public DB() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> login(String username, String password) {
        String SQL = """
        SELECT 
            Users.id as user_id, 
            Users.name as user_name, 
            Users.password as user_password, 
            Roles.name as user_role 
        FROM 
            Users 
        JOIN 
            Roles 
        ON 
            Roles.id = Users.role_id
        WHERE 
            BINARY Users.name = ? AND Users.password = ?;""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password); // This should be a hashed password

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String userId = rs.getString("user_id");
                String userName = rs.getString("user_name");
                String userRole = rs.getString("user_role");
                return Optional.of(new User(userId, userName, userRole));
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<User> getUsers(String roleId) {
        List<User> users = new ArrayList<>();

        String SQL = """
        SELECT 
            Users.id as user_id, 
            Users.name as user_name,
            Roles.name as user_role
        FROM 
            Users 
        JOIN
            Roles
        ON 
            Users.role_id = Roles.id
        WHERE 
            1=1""";


        if (roleId != null) {
            SQL += " AND Users.role_id = ?";
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            if (roleId != null) {
                pstmt.setInt(1, Integer.parseInt(roleId));
            }
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("user_id");
                String userName = rs.getString("user_name");
                String userRole = rs.getString("user_role");
                users.add(new User(userId, userName, userRole));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public List<Course> getCourses(String instructorId) {
        List<Course> courses = new ArrayList<>();

        String SQL = """
        SELECT 
            Courses.id as course_id, 
            Courses.name as course_name, 
            Users.name as instructor_name 
        FROM Courses 
        JOIN Users ON Users.id = instructor_id
        WHERE 1=1""";

        if (instructorId != null) {
            SQL += " AND Courses.instructor_id = ?";
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            if (instructorId != null) {
                pstmt.setString(1, instructorId);
            }
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                String courseName = rs.getString("course_name");
                String instructorName = rs.getString("instructor_name");
                courses.add(new Course(courseId, courseName, instructorName));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return courses;
    }

    public List<Role> getRoles() {

        List<Role> roles = new ArrayList<>();

        String SQL = """
        SELECT 
        Roles.id as role_id, Roles.name as role_name
        FROM Roles""";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                roles.add(new Role(Integer.toString(roleId), roleName));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roles;
    }

    public List<StudentGrade> getStudentsGrades() {
        List<StudentGrade> studentsGrades = new ArrayList<>();

        String SQL = """
        SELECT
        U1.name AS student_name,
                C.name AS course_name,
        G.grade AS grade,
                U2.name AS instructor_name
        FROM
        Grades G
        JOIN
        Users U1 ON G.student_id = U1.id
        JOIN
        Courses C ON G.course_id = C.id
        JOIN
        Users U2 ON C.instructor_id = U2.id;""";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String studentName = rs.getString("student_name");
                String courseName = rs.getString("course_name");
                String grade = rs.getString("grade");
                String instructorName = rs.getString("instructor_name");
                studentsGrades.add(new StudentGrade(studentName, courseName, instructorName, grade));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return studentsGrades;
    }

    public List<StudentGrade> getStudentGrades(String studentId, String courseId) {
        List<StudentGrade> studentsGrades = new ArrayList<>();

        String baseSQL = """
        SELECT
            U1.name AS student_name,
            C.name AS course_name,
            G.grade AS grade,
            U2.name AS instructor_name
        FROM
            Grades G
            JOIN Users U1 ON G.student_id = U1.id
            JOIN Courses C ON G.course_id = C.id
            JOIN Users U2 ON C.instructor_id = U2.id
        WHERE
            1=1""";

        List<String> conditions = new ArrayList<>();
        List<String> parameters = new ArrayList<>();

        if (studentId != null) {
            conditions.add(" AND U1.id = ?");
            parameters.add(studentId);
        }

        if (courseId != null) {
            conditions.add(" AND C.id = ?");
            parameters.add(courseId);
        }

        String SQL = baseSQL + String.join("", conditions);

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setString(i + 1, parameters.get(i));
            }
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String studentName = rs.getString("student_name");
                String courseName = rs.getString("course_name");
                String grade = rs.getString("grade");
                String instructorName = rs.getString("instructor_name");
                studentsGrades.add(new StudentGrade(studentName, courseName, instructorName, grade));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return studentsGrades;
    }

    public boolean addUser(String username, String password, int roleId) {
        String SQL = """
        INSERT INTO Users (name, password, role_id) 
        VALUES (?, ?, ?)""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, roleId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean addCourse(String courseName, int instructorId) {
        String SQL = """
        INSERT INTO Courses (name, instructor_id) 
        VALUES (?, ?)""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, courseName);
            pstmt.setInt(2, instructorId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean addRole(String roleName) {
        String SQL = """
        INSERT INTO Roles (name) 
        VALUES (?)""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, roleName);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean addGrade(int studentId, int courseId, int grade) {
        String SQL = """
        INSERT INTO Grades (student_id, course_id, grade) 
        VALUES (?, ?, ?)""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, grade);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean updateUser(int userId, String name, String password, int roleId) {
        String SQL = """
        UPDATE Users 
        SET name = ?, password = ?, role_id = ? 
        WHERE id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setInt(3, roleId);
            pstmt.setInt(4, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateCourse(int courseId, String name, int instructorId) {
        String SQL = """
        UPDATE Courses 
        SET name = ?, instructor_id = ?
        WHERE id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setInt(2, instructorId);
            pstmt.setInt(3, courseId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateRole(int roleId, String name) {
        String SQL = """
        UPDATE Roles 
        SET name = ?
        WHERE id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setInt(2, roleId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateGrade(int studentId, int courseId, int grade) {
        String SQL = """
        UPDATE Grades 
        SET grade = ?
        WHERE student_id = ? AND course_id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, grade);
            pstmt.setInt(2, studentId);
            pstmt.setInt(3, courseId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String SQL = """
        DELETE FROM Users 
        WHERE id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteGrade(int studentId, int courseId) {
        String SQL = """
        DELETE FROM Grades 
        WHERE student_id = ? AND course_id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}