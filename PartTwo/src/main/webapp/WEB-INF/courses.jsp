<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.jafar.User" %>
<%@ page import="com.jafar.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jafar.CourseDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>

<%
    User user = (User) session.getAttribute("loggedInUser");
    if(user == null) response.sendRedirect("login.jsp");
    List<CourseDTO> courses = (List<CourseDTO>) request.getAttribute("courses");
    request.setAttribute("courses", courses);
%>

<body class="bg-gray-100 h-screen">
<div class="container mx-auto p-6">
    <h1 class="text-3xl font-bold mb-4">Welcome to your Dashboard!</h1>
    <div class="flex">
        <jsp:include page="../navbar.jsp" />

        <!-- Main Content Area -->
        <div class="ml-4 w-3/4">

            <c:choose>
                <c:when test="${empty courses}">
                    <jsp:include page="../no_data.jsp" />
                </c:when>

                <c:otherwise>
                    <table class="min-w-full table-auto">
                        <thead>
                        <tr>
                            <th class="px-4 py-2">ID</th>
                            <th class="px-4 py-2">Name</th>
                            <th class="px-4 py-2">Instructor</th>
                            <th class="px-4 py-2">No. Students</th>
                            <th class="px-4 py-2">Average</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="course" items="${courses}">
                            <tr>
                                <td class="border px-4 py-2">${course.courseId()}</td>
                                <td class="border px-4 py-2">${course.courseName()}</td>
                                <td class="border px-4 py-2">${course.courseInstructor()}</td>
                                <td class="border px-4 py-2">${course.numberOfStudents()}</td>
                                <td class="border px-4 py-2">${course.courseAverage()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>
</body>
</html>
