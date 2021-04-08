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
  <form action="${pageContext.request.contextPath}/admin/adminGroupSubject" method="post">
    <input type="hidden" name="action" value="create"/>
    <input list="group" name="group">
    <datalist id="group">
      <c:forEach items="${allGroup}" var="group">
        <option >${group.fullname}</option>
      </c:forEach>
    </datalist>
    <input list="subject" name="subject">
    <datalist id="subject">
      <c:forEach items="${allSubject}" var="subject">
        <option>${subject.name}-</option>
      </c:forEach>
    </datalist>
    <button type="submit">Create</button>
  </form>

  <table>
    <thead>
    <th>group</th>
    </thead>

    <c:forEach items="${allSG}" var="SG">
      <tr>
        <td ><a href="Groups/${SG.id}" >${SG.fullname}</a></td>
      </tr>
    </c:forEach>
  </table>
  <a href="/">Главная</a>
</div>
</body>
</html>