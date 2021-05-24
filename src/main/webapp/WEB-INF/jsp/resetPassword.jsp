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
    <form action="${pageContext.request.contextPath}/admin/resetPassword" method="post">


        <select list="students" name="students">
            <datalist id="students">
                <c:forEach items="${allUsers}" var="allUsers">
                    <option >${allUsers.firstName} ${allUsers.secondName}  ${allUsers.lastName}</option>
                </c:forEach>
            </datalist></select>
        <input name="newPass"  placeholder="новый пароль"/>
        <button type="submit">Обновить</button>
    </form>
    <c:if test="${ err == 1}">
        <div>Пароль должен быть длиннее 3-х символов</div>
    </c:if>



    <a href="/">Главная</a>
</div>
</body>
</html>