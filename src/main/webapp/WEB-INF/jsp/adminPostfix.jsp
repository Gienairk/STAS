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
  <form action="${pageContext.request.contextPath}/admin/adminPostfix" method="post">
    <input name="postfixname" type="text" placeholder="Postfixname"
           autofocus="true"/>
    <input name="duration" type="number" placeholder="Duration"/>
    <input type="hidden" name="action" value="create"/>
    <button type="submit">Create</button>
  </form>


  <table>
    <thead>
    <th>ID</th>
    <th>Name</th>
    <th>Duration</th>
    </thead>
    <c:forEach items="${allPostfix}" var="postfix">
      <tr>
        <td>${postfix.id}</td>
        <td>${postfix.name}</td>
        <td>${postfix.duration}</td>
        <td>
          <form action="${pageContext.request.contextPath}/admin/adminPostfix" method="post">
            <input type="hidden" name="postfixId" value="${postfix.id}"/>
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