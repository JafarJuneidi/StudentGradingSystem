<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.jafar.User" %>
<%@ page import="com.jafar.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jafar.StudentDTO" %>
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
    List<StudentDTO> students = (List<StudentDTO>) request.getAttribute("students");
    request.setAttribute("students", students);
%>

<body class="bg-gray-100 h-screen">
<div class="container mx-auto p-6">
    <h1 class="text-3xl font-bold mb-4">Welcome to your Dashboard!</h1>
    <div class="flex">
        <jsp:include page="../navbar.jsp" />

        <!-- Main Content Area -->
        <div class="ml-4 w-3/4">

            <c:choose>
                <c:when test="${empty students}">
                    <jsp:include page="../no_data.jsp" />
                </c:when>

                <c:otherwise>
                    <table class="min-w-full table-auto">
                        <thead>
                        <tr>
                            <th class="px-4 py-2">ID</th>
                            <th class="px-4 py-2">Name</th>
                            <th class="px-4 py-2">No. Courses</th>
                            <th class="px-4 py-2">Average</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="student" items="${students}">
                            <tr>
                                <td class="border px-4 py-2">${student.id()}</td>
                                <td class="border px-4 py-2">${student.name()}</td>
                                <td class="border px-4 py-2">${student.numberOfCourses()}</td>
                                <td class="border px-4 py-2">${student.average()}</td>
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
