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
        <th>group</th>
        </thead>

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
</div>
</body>
</html>
