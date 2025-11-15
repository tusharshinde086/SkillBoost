
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
    <title>Register - SkillBoost</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .register-container { margin-top: 100px; max-width: 400px; }
    </style>
</head>
<body>

<div class="container register-container">
    <div class="card shadow-sm">
        <div class="card-header text-center bg-primary text-white">
            <h3 class="mb-0">SkillBoost Registration</h3>
        </div>
        <div class="card-body">
            
            <%-- Display error message from Servlet --%>
            <c:if test="${requestScope.message != null}">
                <div class="alert alert-danger">${requestScope.message}</div>
            </c:if>

            <form action="register" method="post">
                
                <div class="mb-3">
                    <label for="name" class="form-label">Full Name</label>
                    <input type="text" class="form-control" id="name" name="name" required>
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Email address</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>
                
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                
                <button type="submit" class="btn btn-success w-100 mb-3">Register</button>
            </form>
        </div>
        <div class="card-footer text-center">
            Already have an account? <a href="login">Login here</a>
        </div>
    </div>
</div>

</body>
</html>