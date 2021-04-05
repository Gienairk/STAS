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
  <table>
     <thead>
     <th>ID</th>
     <th>UserName</th>
     </thead>
     <c:forEach items="${userList}" var="user">
       <tr>
         <td>${user.id}</td>
         <td>${user.username}</td>
            <td>
          <%-- <form action="${pageContext.request.contextPath}/admin" method="post">
                <input type="hidden" name="userId" value="${user.id}"/>
                <input type="hidden" name="action" value="delete"/>
                <button type="submit">Delete</button>
              </form>

         </td>--%>

      </tr>
    </c:forEach>
  </table>

    <c:if test="${currentPage-1>0}">
      <a href="/admin/adminUserListPage/${currentPage-1} ">Prev</a>
    </c:if>
    <c:if test="${currentPage+1<=totalPages}">
        <a href="/admin/adminUserListPage/${currentPage+1}">Next</a>
    </c:if>
  <a href="/">Главная</a>
</div>
</body>
</html>