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
  <form action="${pageContext.request.contextPath}/admin/adminGroup" method="post">
    <input type="hidden" name="action" value="create"/>
    <input name="groupNumber" type="text" required pattern="^[ 0-9]+$" placeholder="Group number"/>
    <input name="coursenumber" type="number" placeholder="coursenumber"/>
    <input list="department" name="department">
    <datalist id="department">
      <c:forEach items="${allDepartment}" var="department">
      <option >${department.departmentName}</option>
      </c:forEach>
    </datalist>
    <input list="postfix" name="postfix">
    <datalist id="postfix">
      <c:forEach items="${allPostfix}" var="postfix">
        <option>${postfix.name}</option>
      </c:forEach>
    </datalist>
    <button type="submit">Create</button>
  </form>

  <table>
    <thead>
    <th>ID</th>
    <th>course</th>
    <th>group</th>
    </thead>
    <c:forEach items="${allGroup}" var="group">
      <tr>
        <td>${group.id}</td>
        <td>${group.courseNumber}</td>
        <td>${group.number}</td>
        <td>
          <form action="${pageContext.request.contextPath}/admin/adminGroup" method="post">
            <input type="hidden" name="groupId" value="${group.id}"/>
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