<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Log in with your account</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>

<body>
<div>
    <div>
        <form action="${pageContext.request.contextPath}/admin/PostfixPage/${currentPage}"  method="post">
            <input id="Name" name="name" type="text" placeholder="Имя"/>
            <input type="hidden" name="action" value="find"/>
            <button type="submit">Найти</button>
        </form>
    </div>

  <table>
     <thead>
     <th>ID</th>
     <th>Название</th>
     <th>Длительность</th>
     </thead>
      <c:forEach items="${postfixList}" var="subject">
       <tr>
         <td>${subject.id}</td>
         <td>${subject.name}</td>
         <td>${subject.duration}</td>
            <td>
           <form action="${pageContext.request.contextPath}/admin/PostfixPage/${currentPage}"  method="post">
                <input type="hidden" name="userId" value="${subject.id}"/>
                <input type="hidden" name="action" value="delete"/>
                <button type="submit">Delete</button>
              </form>
         </td>
      </tr>
    </c:forEach>
  </table>
    <div>Всего ${totalPages} страниц</div>
    <c:if test="${currentPage-1>0}">
      <a href="/admin/adminUserListPage/${currentPage-1} ">Prev</a>
    </c:if>
    <c:if test="${currentPage+1<=totalPages}">
        <a href="/admin/adminUserListPage/${currentPage+1}">Next</a>
    </c:if>
    </c:if>
  <a href="/">Главная</a>
</div>
</body>
</html>