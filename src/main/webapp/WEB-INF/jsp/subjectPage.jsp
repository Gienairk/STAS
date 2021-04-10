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
        <form action="${pageContext.request.contextPath}/admin/SubjectPage/${currentPage}"  method="post">
            <input id="Name" name="name" type="text" placeholder="название предмета"/>
            <input type="hidden" name="action" value="find"/>
            <button type="submit">Найти</button>
        </form>
    </div>

  <table>
     <thead>
     <th>Название предмета</th>
     </thead>
      <c:if test="${fn:length(subject)!=0}">
      <c:forEach items="${subject}" var="subject">
              <tr>
                  <td>${subject.name}</td>
                  <td>
                      <form action="${pageContext.request.contextPath}/admin/SubjectPage/${currentPage}"  method="post">
                          <input type="hidden" name="subjectId" value="${subject.id}"/>
                          <input type="hidden" name="action" value="delete"/>
                          <button type="submit">Delete</button>
                      </form>
                  </td>
              </tr>
          </c:forEach>
      </c:if>
      <c:if test="${fn:length(subject)==0}">
      <c:forEach items="${subjectList}" var="subject">
       <tr>
         <td>${subject.name}</td>
            <td>
           <form action="${pageContext.request.contextPath}/admin/SubjectPage/${currentPage}"  method="post">
                <input type="hidden" name="subjectId" value="${subject.id}"/>
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
      <a href="/admin/SubjectPage/${currentPage-1} ">Prev</a>
    </c:if>
    <c:if test="${currentPage+1<=totalPages}">
        <a href="/admin/SubjectPage/${currentPage+1}">Next</a>
    </c:if>
  <a href="/">Главная</a>
</div>
</body>
</html>