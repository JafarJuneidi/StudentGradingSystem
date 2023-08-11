<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.jafar.User" %>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if(user == null) {
        response.sendRedirect("login.jsp");
    } else {
        request.setAttribute("role", user.role());
    }
%>

<!-- Side Navbar -->
<nav class="bg-gray-200 w-1/4 p-4 rounded shadow-lg">
    <c:if test="${role == 'Student'}">
        <h2 class="text-xl font-semibold mb-3">Student Menu</h2>
        <ul class="space-y-2">
                <%-- I want to get more course info --%>
            <li><a href="courses" class="text-blue-500 hover:underline">Get All Courses</a></li>
            <%-- I want to get more grade info --%>
            <li><a href="grades" class="text-blue-500 hover:underline">Get My Grades</a></li>
            <li><a href="logout" class="text-blue-500 hover:underline">Log Out</a></li>
        </ul>
    </c:if>

    <c:if test="${role == 'Instructor'}">
        <h2 class="text-xl font-semibold mb-3">Admin Menu</h2>
        <ul class="space-y-2">
            <li><a href="students" class="text-blue-500 hover:underline">Get All Students</a></li>
            <li><a href="courses" class="text-blue-500 hover:underline">Get My Courses</a></li>
            <li><a href="grades" class="text-blue-500 hover:underline">Get All Grades</a></li>
            <li><a href="logout" class="text-blue-500 hover:underline">Log Out</a></li>
        </ul>
    </c:if>

    <c:if test="${role == 'Admin'}">
        <h2 class="text-xl font-semibold mb-3">Admin Menu</h2>
        <ul class="space-y-2">
            <li><a href="students" class="text-blue-500 hover:underline">Get All Students</a></li>
            <li><a href="courses" class="text-blue-500 hover:underline">Get All Courses</a></li>
            <li><a href="grades" class="text-blue-500 hover:underline">Get All Grades</a></li>
            <li><a href="logout" class="text-blue-500 hover:underline">Log Out</a></li>
        </ul>
    </c:if>
</nav>