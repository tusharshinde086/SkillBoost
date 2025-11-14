<%-- WebContent/WEB-INF/views/dashboard.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skillboost.model.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SkillBoost Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<% 
    // Retrieve the Student object from the session
    Student student = (Student) session.getAttribute("currentStudent");
    // Ensure the student object exists before trying to access its methods
    if (student == null) {
        // Should be caught by DashboardServlet, but good practice to check
        response.sendRedirect("login");
        return;
    }
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">SkillBoost Tracker</a>
        <span class="navbar-text ms-auto text-light">
            Welcome, **<%= student.getName() %>**!
        </span>
        <a href="logout" class="btn btn-outline-light ms-3">Logout</a>
    </div>
</nav>

<div class="container mt-5">
    <h1 class="mb-4">Your Skill Progress Dashboard</h1>
    
    <div class="alert alert-info">
        Hello <%= student.getName() %>. This dashboard is under construction. Future logic will display your skills and progress data here, fetched from the database by the **DashboardServlet**.
    </div>

    </div>

</body>
</html>