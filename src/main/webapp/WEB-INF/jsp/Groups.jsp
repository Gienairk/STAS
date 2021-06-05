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
        <form action="${pageContext.request.contextPath}/admin/Groups/${currentPage}"  method="post">
            <input id="groupNumber" name="groupNumber" type="text" placeholder="Номер группы"/>
            <input id="groupPostfix" name="groupPostfix" type="text" placeholder="Постфикс группы"/>
            <input type="hidden" name="action" value="findGroup"/>
            <button type="submit">Найти</button>
        </form>
    </div>
    <table>
        <thead>
        <th>группа</th>
        <th>курс</th>
        </thead>
    <c:if test="${fn:length(groupRez)!=0}">
        <c:forEach items="${groupRez}" var="SG">
            <tr>
                <td ><a href="Group/${SG.id}" >${SG.fullname}</a></td>
            </tr>
        </c:forEach>
    </c:if>
        <c:if test="${fn:length(groupRez)==0}">
        <c:forEach items="${groupList}" var="SG">
            <tr>
                <td ><a href="Group/${SG.id}" >${SG.fullname}</a></td>
            </tr>
        </c:forEach>
    </table>
    <div>Всего ${totalPages} страниц</div>
    <c:if test="${currentPage-1>0}">
        <a href="/admin/Groups/${currentPage-1} ">Prev</a>
    </c:if>
    <c:if test="${currentPage+1<=totalPages}">
        <a href="/admin/Groups/${currentPage+1}">Next</a>
    </c:if>
    <div>
        <form action="${pageContext.request.contextPath}/admin/Groups/${currentPage}"   method="post">
            <input type="hidden" name="action" value="upAllGroup"/>
            <button type="submit" onclick="return confirm('повысить курс всех групп, если она выпускаются её данные будут удалены ? ')">Повысить курс всех групп</button>
        </form>
    </div>
    </c:if>
    <a href="/">Главная</a></div>
</div>
</body>
</html>
