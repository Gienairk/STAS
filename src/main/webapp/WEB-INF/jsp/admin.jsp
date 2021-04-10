<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Меню администратора</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<div>
    <h3>${pageContext.request.userPrincipal.name}</h3>
    <h4><a href="/admin/Postfix">Постфиксы</a></h4>
    <h4><a href="/admin/Subject">Создать предметы</a></h4>
    <h4><a href="/admin/SubjectPage/1">Предметы</a></h4>
    <h4><a href="/admin/Department">Отделений</a></h4>
    <h4><a href="/admin/Group">Группы</a></h4>
    <h4><a href="/admin/Groups">Список групп</a></h4>
    <h4><a href="/admin/GroupSubject">Предметы для групп</a></h4>
    <h4><a href="/admin/ChooseDepartment">Установка отделения(для преподавателей)</a></h4>
    <h4><a href="/admin/ChooseGroup">Выбор студентов и зачисление их в группы</a></h4>
    <h4><a href="/admin/ChooseSubject">Установка предметов(для преподавателей)</a></h4>
   <!-- <h4><a href="/admin/adminUserList">Пользователи</a></h4>-->
    <h4><a href="/admin/adminUserListPage/1">Пользователи</a></h4>
    <h4><a href="/admin/registration">Создание пользователя</a></h4>
</div>
</body>
</html>
