package com.jafar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/grades")
public class GradesServlet extends HttpServlet {
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

        switch (user.role()) {
            case "Student" -> {
                List<StudentGrade> grades = db.getStudentGrades(user.id(), null);
                request.setAttribute("grades", grades);
                request.getRequestDispatcher("WEB-INF/grades.jsp").forward(request, response);
            }
            case "Admin", "Instructor" -> {
                List<StudentGrade> grades = db.getStudentGrades(null, null);
                request.setAttribute("grades", grades);
                request.getRequestDispatcher("WEB-INF/grades.jsp").forward(request, response);
            }
        }

    }
}

