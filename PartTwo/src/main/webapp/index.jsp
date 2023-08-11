<%@ page import="com.jafar.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <!-- Include Tailwind CSS via CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
</head>

<%
    User user = (User) session.getAttribute("loggedInUser");
    if(user == null) response.sendRedirect("login.jsp");
%>

<body class="bg-gray-100 h-screen">
<div class="container mx-auto p-6">
    <h1 class="text-3xl font-bold mb-4">Welcome to your Dashboard!</h1>
    <div class="flex">
        <jsp:include page="navbar.jsp" />

        <!-- Main Content Area -->
        <div class="ml-4 w-3/4">
            <!-- Place your main content here -->
        </div>
    </div>
</div>
</body>
</html>
