<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 8/9/2023
  Time: 8:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="h-screen flex justify-center items-center bg-gray-200">
<div class="bg-white p-8 rounded-lg shadow-md w-96">
    <h1 class="text-2xl font-bold mb-4">Login</h1>

    <form action="login" method="post">
        <div class="mb-4">
            <label for="username" class="block text-sm font-medium text-gray-600">Username</label>
            <input type="text" id="username" name="username" class="mt-1 p-2 w-full border rounded-md">
        </div>

        <div class="mb-4">
            <label for="password" class="block text-sm font-medium text-gray-600">Password</label>
            <input type="password" id="password" name="password" class="mt-1 p-2 w-full border rounded-md">
        </div>

        <% if(request.getAttribute("errorMessage") != null) { %>
        <div class="bg-red-500 text-white p-4 rounded-lg mb-4">
            <%= request.getAttribute("errorMessage") %>
        </div>
        <% } %>

        <button type="submit" class="w-full bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600">Login</button>
    </form>
</div>
</body>
</html>
