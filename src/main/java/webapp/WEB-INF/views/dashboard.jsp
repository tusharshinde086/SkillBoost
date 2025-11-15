
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
    <title>SkillBoost Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="dashboard">SkillBoost Tracker</a>
        <span class="navbar-text ms-auto text-light">
            Welcome, **${sessionScope.currentStudent.name}**!
        </span>
        <a href="logout" class="btn btn-outline-light ms-3">Logout</a>
    </div>
</nav>

<div class="container mt-5">
    <h1 class="mb-4">Your Skill Progress Dashboard</h1>

    <%-- Display flash message --%>
    <c:if test="${requestScope.message != null}">
        <div class="alert alert-info">${requestScope.message}</div>
    </c:if>

    <c:set var="editSkill" value="${requestScope.skillToEdit}" />
    <c:set var="mode" value="${editSkill != null ? 'Edit' : 'Add'}" />

    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            ${mode} Skill Progress
        </div>
        <div class="card-body">
            <form action="skill" method="post" class="row g-3">
                <input type="hidden" name="progressId" value="${editSkill.progressId}" />
                
                <div class="col-md-5">
                    <label for="skillName" class="form-label">Skill Name</label>
                    <input type="text" class="form-control" id="skillName" name="skillName" 
                           value="${editSkill.skillName}" required 
                           ${mode eq 'Edit' ? 'readonly' : ''} />
                    <c:if test="${mode eq 'Edit'}">
                        <div class="form-text text-danger">Skill name cannot be changed during edit.</div>
                    </c:if>
                </div>
                
                <div class="col-md-5">
                    <label for="currentLevel" class="form-label">Current Level (1.0 - 10.0)</label>
                    <input type="number" step="0.1" min="1.0" max="10.0" class="form-control" 
                           id="currentLevel" name="currentLevel" 
                           value="${editSkill.currentLevel}" required>
                </div>
                
                <div class="col-md-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-success w-100">
                        ${mode eq 'Edit' ? 'Update Skill' : 'Save New Skill'}
                    </button>
                </div>
            </form>
        </div>
    </div>

    <h2>Tracked Skills</h2>
    <c:choose>
        <c:when test="${empty requestScope.skillList}">
            <div class="alert alert-warning">You haven't tracked any skills yet. Use the form above to start!</div>
        </c:when>
        <c:otherwise>
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Skill Name</th>
                        <th>Level</th>
                        <th>Progress Bar</th>
                        <th>Last Updated</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="skill" items="${requestScope.skillList}">
                        <c:set var="progressPercent" value="${(skill.currentLevel / 10) * 100}"/>
                        <tr>
                            <td>${skill.skillName}</td>
                            <td>**${skill.currentLevel}** / 10.0</td>
                            <td>
                                <div class="progress" style="height: 25px;">
                                    <div class="progress-bar progress-bar-striped bg-info" role="progressbar" 
                                         style="width: ${progressPercent}%;" 
                                         aria-valuenow="${skill.currentLevel}" aria-valuemin="0" aria-valuemax="10">
                                         ${(skill.currentLevel/10) * 100}%
                                    </div>
                                </div>
                            </td>
                            <td>${skill.formattedLastUpdated}</td>
                            <td>
                                <a href="dashboard?edit=${skill.progressId}" class="btn btn-sm btn-info me-2">Edit</a>
                                <a href="skill?delete=${skill.progressId}" 
                                   onclick="return confirm('Are you sure you want to delete ${skill.skillName}?')"
                                   class="btn btn-sm btn-danger">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>