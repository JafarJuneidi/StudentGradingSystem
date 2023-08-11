package com.jafar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/courses")
public class CoursesServlet extends HttpServlet {
    private DB db;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        db = new DB();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute("loggedInUser");

        List<CourseDTO> courses = null;
        switch (user.role()) {
            case "Instructor" -> {
                courses = createCoursesDTO(db.getCourses(user.id()));
            }
            case "Admin", "Student" -> {
                courses = createCoursesDTO(db.getCourses(null));
            }
        }
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("WEB-INF/courses.jsp").forward(request, response);
    }

    private List<CourseDTO> createCoursesDTO(List<Course> courses) {
        return courses.stream()
                .map(course -> {
                    List<StudentGrade> grades = db.getStudentGrades(null, Integer.toString(course.id()));
                    double total = grades.stream()
                            .mapToDouble(grade -> Double.parseDouble(grade.grade()))
                            .sum();
                    double average = grades.isEmpty() ? 0 : total / grades.size();
                    return new CourseDTO(Integer.toString(course.id()), course.name(), course.instructor(), Integer.toString(grades.size()), Double.toString(average));
                })
                .collect(Collectors.toList());
    }
}

