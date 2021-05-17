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
  <form action="${pageContext.request.contextPath}/admin/ChooseGroup" method="post">

    <select list="student" name="student">
    <datalist id="student">
      <c:forEach items="${allStudents}" var="student">
        <option >${student.firstName} ${student.secondName} ${student.lastName}</option>
      </c:forEach>
    </datalist></select>

    <select list="group" name="group">
    <datalist id="group">
      <c:forEach items="${allGroup}" var="group">
        <option >${group.fullname}</option>
      </c:forEach>
    </datalist></select>


    <button type="submit">Create</button>
  </form>



  <a href="/">Главная</a>
</div>
</body>
</html>