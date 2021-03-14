<html lang="en">
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
<div class="container clearfix">
    <div class="people-list" id="people-list">
        <div class="search">
            <form id="chooseRoomName" name="chooseRoomName">
                <a id="userName"   type="text"></a>
                <button onclick="">Create chat</button>
            </form>
            <form id="refreshRoom" name="refreshRoom">
                <a id="refreshR"   type="text"></a>
                <button onclick="">Refresh room</button>
            </form>
        </div>
        <ul id="ChatList"class="list">
        </ul>
    </div>

    <div class="chat">
        <div class="chat-header clearfix">


            <div  class="chat-about">
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
            <form id="openUserForm" name="openUserForm">
            <button id="addUser" type="submit">add user</button>
                </form>
            <form id="openGroupForm" name="openGroupForm">
                <button id="addGroup" type="submit">add group</button>
            </form>
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