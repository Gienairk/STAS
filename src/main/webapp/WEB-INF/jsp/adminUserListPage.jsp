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
        <form action="${pageContext.request.contextPath}/admin/adminUserListPage/${currentPage}"  method="post">
            <input id="Name" name="name" type="text" placeholder="Имя"/>
            <input id="Surname" name="surname" type="text" placeholder="Фамилия"/>
            <input id="Patronymic" name="patronymic" type="text" placeholder="Отчество"/>
            <input type="hidden" name="action" value="find"/>
            <button type="submit">Найти</button>
        </form>
    </div>

  <table>
     <thead>
     <th>ID</th>
     <th>Логин</th>
     <th>Имя</th>
     <th>Отчество</th>
     <th>Фамилия</th>
     </thead>
      <c:if test="${fn:length(users)!=0}">
      <c:forEach items="${users}" var="subject">
              <tr>
                  <td>${subject.id}</td>
                  <td>${subject.username}</td>
                  <td>${subject.firstName}</td>
                  <td>${subject.secondName}</td>
                  <td>${subject.lastName}</td>
                  <td>
                      <form action="${pageContext.request.contextPath}/admin/adminUserListPage/${currentPage}"  method="post">
                          <input type="hidden" name="userId" value="${subject.id}"/>
                          <input type="hidden" name="action" value="delete"/>
                          <button type="submit">Delete</button>
                      </form>
                  </td>
              </tr>
          </c:forEach>
      </c:if>
      <c:if test="${fn:length(users)==0}">
      <c:forEach items="${userList}" var="subject">
       <tr>
         <td>${subject.id}</td>
         <td>${subject.username}</td>
         <td>${subject.firstName}</td>
         <td>${subject.secondName}</td>
         <td>${subject.lastName}</td>
            <td>
           <form action="${pageContext.request.contextPath}/admin/adminUserListPage/${currentPage}"  method="post">
                <input type="hidden" name="userId" value="${subject.id}"/>
                <input type="hidden" name="action" value="delete"/>
                <button type="submit">Delete</button>
              </form>
         </td>
      </tr>
    </c:forEach>
      </c:if>
  </table>

    <div>Всего ${totalPages} страниц</div>
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