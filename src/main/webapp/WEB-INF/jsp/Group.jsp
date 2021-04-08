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
    <label>Студенты</label>
  <c:forEach items="${Group.users}" var="groupdata">
    <div >${groupdata.toStringForPage()}</div>
    <form action="${pageContext.request.contextPath}/admin/Groups/${Group.id}"  method="post">
      <input type="hidden" name="dataId" value="${groupdata.id}"/>
      <input type="hidden" name="action" value="deleteUser"/>
      <button type="submit">убрать из группы</button>
    </form>
  </c:forEach>
    <label>Предметы</label>
  <c:forEach items="${Group.subjects}" var="groupdata">
    <div >${groupdata.toStringForPage()}</div>
    <form action="${pageContext.request.contextPath}/admin/Groups/${Group.id}"  method="post">
      <input type="hidden" name="dataId" value="${groupdata.id}"/>
      <input type="hidden" name="action" value="deleteSubject"/>
      <button type="submit">убрать</button>
    </form>
  </c:forEach>
  <a href="/">Главная</a>
</div>
</body>
</html>