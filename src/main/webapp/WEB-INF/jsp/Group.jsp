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
  <table>
  <c:forEach items="${Group.users}" var="groupdata">
    <tr>
    <td >${groupdata.toStringForPage()}</td>
      <td>
    <form action="${pageContext.request.contextPath}/admin/Groups/Group/${Group.id}"  method="post">
      <input type="hidden" name="dataId" value="${groupdata.id}"/>
      <input type="hidden" name="action" value="deleteUser"/>
      <button type="submit">убрать из группы</button>
    </form>
      </td>
    </tr>
  </c:forEach>
  </table>
    <label>Предметы</label>
  <table>
  <c:forEach items="${Group.subjects}" var="groupdata">
    <tr>
    <td >${groupdata.toStringForPage()}</td>
      <td>
        <form action="${pageContext.request.contextPath}/admin/Groups/Group/${Group.id}"  method="post">
          <input type="hidden" name="dataId" value="${groupdata.id}"/>
          <input type="hidden" name="action" value="deleteSubject"/>
          <button type="submit">убрать</button>
        </form>
      </td >
    </tr>
  </c:forEach>
  </table>
    <div>
      <form action="${pageContext.request.contextPath}/admin/Groups/Group/${Group.id}"  method="post">
        <input type="hidden" name="action" value="deleteGroup"/>
        <button type="submit">удалить группу</button>
      </form>
    </div>
  <div><a href="/">Главная</a></div>
</div>
</body>
</html>