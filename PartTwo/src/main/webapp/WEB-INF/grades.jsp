<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.jafar.User" %>
<%@ page import="com.jafar.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jafar.StudentGrade" %>
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
    List<StudentGrade> grades = (List<StudentGrade>) request.getAttribute("grades");
    request.setAttribute("grades", grades);
%>

<body class="bg-gray-100 h-screen">
<div class="container mx-auto p-6">
    <h1 class="text-3xl font-bold mb-4">Welcome to your Dashboard!</h1>
    <div class="flex">
        <jsp:include page="../navbar.jsp" />

        <!-- Main Content Area -->
        <div class="ml-4 w-3/4">

            <c:choose>
                <c:when test="${empty grades}">
                    <jsp:include page="../no_data.jsp" />
                </c:when>

                <c:otherwise>
                    <table class="min-w-full table-auto">
                        <thead>
                        <tr>
                            <th class="px-4 py-2">StudentName</th>
                            <th class="px-4 py-2">CourseName</th>
                            <th class="px-4 py-2">InstructorName</th>
                            <th class="px-4 py-2">Grade</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="grade" items="${grades}">
                            <tr>
                                <td class="border px-4 py-2">${grade.studentName()}</td>
                                <td class="border px-4 py-2">${grade.courseName()}</td>
                                <td class="border px-4 py-2">${grade.instructorName()}</td>
                                <td class="border px-4 py-2">${grade.grade()}</td>
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
