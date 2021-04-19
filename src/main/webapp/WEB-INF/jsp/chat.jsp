<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<head>
    <meta charset="UTF-8">
    <title>Custom messanger</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.0/handlebars.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js"></script>
    <!--    libs for stomp and sockjs-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <!--    end libs for stomp and sockjs-->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet"
          type="text/css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>

    <div id="addUserForm" class="hidden">
            <input list="student" name="studentName" id="studentName">
            <datalist id="student"></datalist>
            <button  id="addUserToChat" onclick="addUserToChatFunction()">Добавить</button>
    </div>
    <div id="addGroupForm" class="hidden">
        <input list="group" name="groupName" id="groupName">
        <datalist id="group"></datalist>
        <button  id="addGroupToChat" onclick="addGroupToChatFunction()">Добавить</button>
    </div>

    <div id="createChatForStudentForm" class="hidden">
        <input type="text" id="roomNameStudent" name="roomNameStudent" placeholder="Enter Chat Room name" autocomplete="off" class="form-control"/>
        <input list="teathers" name="teathersName" id="teathersName">
        <datalist id="teathers"></datalist>
        <button  id="createChatStudent" onclick="createRoomForStudent()">Добавить</button>
    </div>
    <div id="chatRoomUserForm" class="hidden">
        <div id="chatRoomUser"></div>
        <button  id="hideUserInRoom" onclick="hideUserInRoom()">Скрыть</button>
    </div>

<div class="container clearfix">
    <div class="people-list" id="people-list"  items="${userRight}" var="userRight">
        <div class="search">
            <c:if test="${ userRight == 'Admin'|| userRight == 'Teather'}">
                <form id="chooseRoomName" name="chooseRoomName">
                    <a id="userName"   type="text"></a>
                    <button onclick="">Create chat</button>
                </form>
            </c:if>
            <c:if test="${ userRight == 'Student'}">
                <form id="chooseRoomNameStudent" name="chooseRoomNameStudent">
                    <a id="userName"  type="text"></a>
                    <button onclick="createRoomStudent()">Create chat</button>
                </form>
            </c:if>
        </div>

        <ul id="ChatList"class="list">
        </ul>
    </div>

    <div class="chat">
        <div class="chat-header clearfix">

            <div  class="chat-about">
                <button onclick="leaveFromRoom()">Выйти</button>
                <button onclick="showUserInRoom()">Показать участников</button>
                <div id="chatWith" class="chat-with">Chat with ...</div>
                <div class="chat-num-messages"></div>
            </div>

        </div> <!-- end chat-header -->
        <form id="createRoomForm" name="createRoomForm" class="hidden" >
            <div class="form-group">
                <div class="input-group clearfix">
                    <input type="text" id="roomName" name="roomName" placeholder="Enter Chat Room name" autocomplete="off" class="form-control"/>
                    <button type="submit" class="btn btn-primary">Create Room</button>
                </div>
            </div>
        </form>
        <div class="chat-history">
            <ul id="messageArea">



            </ul>

        </div> <!-- end chat-history -->

        <div class="chat-message clearfix">
            <form id="messageForm" name="messageForm">
                <textarea id="message-to-send" name="message-to-send" placeholder="Type your message" rows="3"></textarea>
                <button id="sendBtn" type="submit">Send</button>
            </form>
        <c:if test="${ userRight == 'Admin'|| userRight == 'Teather'}">
            <button id="addUser" onclick="addUserFormOpen()" >add user</button>
            <button id="addGroup" onclick="addGroupFormOpen()">add group</button>
        </c:if>

        </div> <!-- end chat-message -->
    </div> <!-- end chat -->

</div> <!-- end container -->


<script id="message-response-template" type="text/x-handlebars-template">
    <li>
        <div class="message-data">
            <span class="message-data-name"><i class="fa fa-circle online"></i> {{userName}}</span>
            <span class="message-data-time">{{time}}</span>
        </div>
        <div class="message my-message">
            {{response}}
        </div>
    </li>
</script>

<script type="text/javascript" src="${contextPath}/resources/js/chatScript.js"></script>

</body>
</html>