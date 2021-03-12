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
  <form action="${pageContext.request.contextPath}/admin/ChooseSubject" method="post">
    <select name="teather" path="teather">
      <c:forEach items="${allTeather}" var="teather">
        <option>${teather.firstName} ${teather.secondName} ${teather.lastName}</option>
      </c:forEach>
    </select>

    <select name="subject" path="subject">
      <c:forEach items="${allSubject}" var="subject">
        <option>${subject.name}</option>
      </c:forEach>
    </select>
    <button type="submit">Create</button>
  </form>



  <a href="/">Главная</a>
</div>
</body>
</html>