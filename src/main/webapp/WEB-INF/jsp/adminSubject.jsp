<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Log in with your account</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>

<body>
<div>
  <form action="${pageContext.request.contextPath}/admin/adminSubject" method="post">
    <input name="subjectname" type="text" placeholder="Subjectname"
           autofocus="true"/>
    <input type="hidden" name="action" value="create"/>
    <button type="submit">Create</button>
  </form>


  <table>
    <thead>
    <th>ID</th>
    <th>Name</th>
    </thead>
    <c:forEach items="${allSubjects}" var="subject">
      <tr>
        <td>${subject.id}</td>
        <td>${subject.name}</td>
        <td>
          <form action="${pageContext.request.contextPath}/admin/adminSubject" method="post">
            <input type="hidden" name="subjectId" value="${subject.id}"/>
            <input type="hidden" name="action" value="delete"/>
            <button type="submit">Delete</button>
          </form>

        </td>

      </tr>
    </c:forEach>
  </table>
  <a href="/">Главная</a>
</div>
</body>
</html>