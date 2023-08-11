package com.jafar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
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
        List<StudentDTO> students = createStudentsDTO(db.getUsers("3"));
        request.setAttribute("students", students);
        request.getRequestDispatcher("WEB-INF/students.jsp").forward(request, response);
    }

    private List<StudentDTO> createStudentsDTO(List<User> students) {
        return students.stream()
                .map(student -> {
                    List<StudentGrade> grades = db.getStudentGrades(student.id(), null);
                    double total = grades.stream()
                            .mapToDouble(grade -> Double.parseDouble(grade.grade()))
                            .sum();
                    double average = grades.isEmpty() ? 0 : total / grades.size();
                    return new StudentDTO(student.id(), student.name(), Integer.toString(grades.size()), Double.toString(average));
                })
                .collect(Collectors.toList());
    }
}

