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
  <form action="${pageContext.request.contextPath}/admin/ChooseDepartment" method="post">
    <select name="teather" path="teather">
      <c:forEach items="${allTeather}" var="teather">
        <option>${teather.firstName} ${teather.secondName} ${teather.lastName}</option>
      </c:forEach>
    </select>

    <select name="department" path="department">
      <c:forEach items="${allDepartment}" var="department">
        <option>${department.departmentName}</option>
      </c:forEach>
    </select>
    <button type="submit">Create</button>
  </form>



  <a href="/">Главная</a>
</div>
</body>
</html>