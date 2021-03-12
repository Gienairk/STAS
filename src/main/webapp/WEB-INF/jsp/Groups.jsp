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

        <c:forEach items="${allGroup}" var="SG">
            <tr>
                <td ><a href="Groups/${SG.id}" >${SG.fullname}</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
