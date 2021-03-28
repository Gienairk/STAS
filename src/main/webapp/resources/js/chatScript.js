const url='http://localhost:8080';

var createRoomForm=document.querySelector('#createRoomForm')

var choseRoomName=document.querySelector('#chooseRoomName')

var chatWith=document.querySelector('#chatWith')

var messageToSend=document.querySelector('#message-to-send')
var messageForm=document.querySelector('#messageForm')

var userToAdd=document.querySelector('#studentName')
var groupToAdd=document.querySelector('#groupName')

var roomName = document.querySelector('#roomName');
var ChooseUserToAdd = document.querySelector('#addUserForm');
var ChooseGroupToAdd = document.querySelector('#addGroupForm');

var stompClient = null;
var currentSubscription;


var roomClient=null;
var roomSubscription;

var messageClient=null;
var messageSubscription;

var userName = null;
var roomId = null;
var topic = null;

let $chatHistory;
let $chatHistoryList;
let $textarea;

function init() {
    cacheDOM();
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function addMessageEnter(event) {

    if (event.keyCode === 13) {
        sendMessage()
    }
}

function cacheDOM(){
    $textarea = $('#message-to-send');
    $chatHistory = $('.chat-history');
    $chatHistoryList = $chatHistory.find('ul');
}

window.onbeforeunload = function() {
    if (roomId!=null)
        userOutFromChat(roomId)

}
function userOutFromChat (roomName){
    stompClient.send(`/app/changeChat/${userName}`,{},roomName);
}
window.onload = function() {
    $.get(url + "/getCurrentUser",function (response){
        userName=response;
        $('#userName').append(userName)
        var socket = new SockJS('/chat');
        var socketRoom = new SockJS('/room');
        var socketMessage = new SockJS('/message');
        stompClient = Stomp.over(socket);
        stompClient.connect({},stompClientConnected,onError);
        roomClient=Stomp.over(socketRoom);
        roomClient.connect({},roomClientConnected,onError);
        messageClient=Stomp.over(socketMessage)
        messageClient.connect({},messageClientConnected,onError);
    })
}

function listRoom() {
    stompClient.subscribe(`/app/chat/${userName}/getChats`, onListofRoom);
    //chatSubscription=stompClient.subscribe(`/app/${userName}/chatList`)
    //currentSubscription=stompClient.subscribe(`/topic/${roomId}`,onMessageReceived);
}

function stompClientConnected() {
    console.log("start stompClient")
    listRoom()
}

function updateRoom(payload){
    console.log(payload)
    var room = payload.body;
    console.log(room)
    let usersTemplateHTML='<a href="#" onclick="enterRoom(\''+room+'\')" <li class="clearfix">\n' +
        '                <div class="about">\n' +
        '                     <div id="userNameAppender_' + room + '" class="name">' + room + '</div>\n' +
        '                    <div class="status">\n' +
        '                        <i class="fa fa-circle online"></i>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </li></a>'+$('#ChatList').html();
    $('#ChatList').html(usersTemplateHTML);
}

function updateRoomList(username,roomname){
    console.log("updateRoomList "+roomname)
    stompClient.send(`/app/chat/updateRoom/${username}`,{},roomname);
}

function messageClientConnected(){
    messageSubscription=messageClient.subscribe(`/topic/newMessage/`+userName, messageRecover);
}
function messageRecover(payload){
    console.log(payload.body)
    var whatChat=payload.body;
    let elem=document.getElementById('userNameAppender_'+whatChat)
    console.log(roomId)
    console.log(whatChat)
    if (roomId!=whatChat) {
        elem.classList.add('newMessage');
    }
}

function roomClientConnected() {
    console.log("start roomClient")
    roomSubscription=roomClient.subscribe(`/topic/chat/`+userName, updateRoom);
}

function onListofRoom(payload) {
    let map = new Map()
    var listRoom = JSON.parse(payload.body);
    for (var value in listRoom) {
        map.set(value,listRoom[value])
    }
    let usersTemplateHTML="";
    for (let [key, value] of map) {
       if (value){
           usersTemplateHTML=usersTemplateHTML+'<a href="#" onclick="enterRoom(\''+key+'\')" <li class="clearfix">\n' +
               '                <div class="about">\n' +
               '                     <div id="userNameAppender_' + key + '" class="name newMessage">' + key + '</div>\n' +
               '                    <div class="status">\n' +
               '                        <i class="fa fa-circle online"></i>\n' +
               '                    </div>\n' +
               '                </div>\n' +
               '            </li></a>';
       }else{
           usersTemplateHTML=usersTemplateHTML+'<a href="#" onclick="enterRoom(\''+key+'\')" <li class="clearfix">\n' +
               '                <div class="about">\n' +
               '                     <div id="userNameAppender_' + key + '" class="name">' + key + '</div>\n' +
               '                    <div class="status">\n' +
               '                        <i class="fa fa-circle online"></i>\n' +
               '                    </div>\n' +
               '                </div>\n' +
               '            </li></a>';
       }
    }


    $('#ChatList').html(usersTemplateHTML);
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function createRoom(event){
    var roomNameValue=roomName.value.trim();

    if (roomNameValue){
         stompClient.subscribe(`/app/chat/${roomNameValue}/CreateRoom`, getAnswerRoom);
    }
    createRoomForm.classList.add('hidden')
    event.preventDefault();
}


function getAnswerRoom(payload){
    var message = JSON.parse(payload.body);
    console.log(message)
    if (message){
        console.log("getAnswerRoom "+roomId)
        updateRoomList(userName,roomName.value.trim())
        setTimeout(() => enterRoom(roomName.value.trim()), 100);
    }

    else{
        alert("This chat already exists ")
    }
}

function enterRoom(newRoomId){
    var data={
        roomName:newRoomId,
    };
    stompClient.send(`/app/chat/rooms/${userName}`,{},JSON.stringify(data));
    chatCleaner()
    if(roomId!=null)
        userOutFromChat(roomId)
    roomId=newRoomId;
    chatWith.textContent=newRoomId;
    topic = `/app/chat/${newRoomId}`;
    let elem=document.getElementById('userNameAppender_'+newRoomId)
    if (elem.classList!=null)
        elem.classList.remove('newMessage')
    if (currentSubscription){
        currentSubscription.unsubscribe();
    }
    stompClient.subscribe(`/app/chat/${roomId}/getPrevious`, onPreviousMessage);
    currentSubscription=stompClient.subscribe(`/topic/${roomId}`,onMessageReceived);
}

function sendMessage(event){
    console.log(event+ " event")
    var messageContent=messageToSend.value.trim();
    if (messageContent && stompClient){
        var chatMessage={
            fromLogin: userName,
            message:messageToSend.value,
            type: 'CHAT'
        };
        stompClient.send(`${topic}/sendMessage`, {}, JSON.stringify(chatMessage));
    }
    messageToSend.value='';
    if (event!=null)
        event.preventDefault();
}

function onPreviousMessage(payload) {
    var messages = JSON.parse(payload.body);

    for (var i=0, len = messages.length;i<len;i++ )
    {

        showMessage(messages[i]);
    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    showMessage(message);
}

function formatDate(date) {

    var dd = date.getDate();
    if (dd < 10) dd = '0' + dd;

    var mm = date.getMonth() + 1;
    if (mm < 10) mm = '0' + mm;

    var yy = date.getFullYear()

    var hh=date.getHours()
    var min=date.getMinutes()
    if (min=="0")
        min="00"
    return hh+":"+min+" "+dd + '.' + mm + '.' + yy ;
}


function getCurrentTime() {
    var d = new Date();
    return formatDate(d)
}

function showMessage(message){
    if (message.date==null){
        message.date=getCurrentTime()
    }
    var templateResponse = Handlebars.compile($("#message-response-template").html());
    var contextResponse = {
        response: message.message,
        time: message.date,
        userName: message.fromLogin
    };
    $chatHistoryList.append(templateResponse(contextResponse));
    scrollToBottom();

}

function chatCleaner(){
    var whatClean=document.getElementById("messageArea")
    while (whatClean.firstChild) {
        whatClean.removeChild(whatClean.firstChild);
    }
}

function chooseChatName(event){
    createRoomForm.classList.remove('hidden')
    event.preventDefault();
}

function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
}

function addUserFormOpen(){
    ChooseUserToAdd.classList.remove('hidden');
    stompClient.subscribe(`/app/chat/getUserList`, getUserToAdd);
}
function getUserToAdd(payload){
    var user = JSON.parse(payload.body);
    let usersTemplateHTML="";
    for (let i = 0; i <user.length ; i++) {
        usersTemplateHTML=usersTemplateHTML+'<option >'+user[i]+'</option>'
    }
    $('#student').html(usersTemplateHTML);
}

function getGroupToAdd(payload){
    var group = JSON.parse(payload.body);

    let usersTemplateHTML="";
    for (let i = 0; i <group.length ; i++) {
        usersTemplateHTML=usersTemplateHTML+'<option >'+group[i]+'</option>'
        console.log(group[i])
    }
    console.log(usersTemplateHTML);
    $('#group').html(usersTemplateHTML);
}

function addGroupFormOpen(){
    ChooseGroupToAdd.classList.remove('hidden');
    stompClient.subscribe(`/app/chat/getGroupList`, getGroupToAdd);
}

function addGroupToChatFunction(){
    var groupToAddvalue=groupToAdd.value.trim();
    console.log(groupToAddvalue);
    console.log(roomId);
    var data={
        roomName:roomId,
    };
    stompClient.send(`/app/chat/rooms/${groupToAddvalue}/addGroup`,{},JSON.stringify(data));
    ChooseGroupToAdd.classList.add('hidden');
}
function addUserToChatFunction(){
    var userToAddValue=userToAdd.value.trim();
    console.log(userToAddValue);
    console.log(roomId);
    var data={
        roomName:roomId,
    };
    console.log("addUserToChatFunction "+roomId)
    updateRoomList(userToAddValue,roomId)
    stompClient.send(`/app/chat/rooms/${userToAddValue}`,{},JSON.stringify(data));

    ChooseUserToAdd.classList.add('hidden');
}

$(document).ready(function (){
    // refreshRoom.addEventListener('submit',listRoom,true)
    createRoomForm.addEventListener('submit',createRoom,true)
    choseRoomName.addEventListener('submit',chooseChatName,true)
    messageForm.addEventListener('submit',sendMessage,true)
})

function leaveFromRoom(){
    console.log(roomId)
    var data={
        roomName:roomId,
    };
    stompClient.send(`/app/chat/roomsLeave/${userName}`,{},JSON.stringify(data));
    chatCleaner()
    chatWith.textContent="Chat with ...";
    currentSubscription.unsubscribe();
    roomId=null;
    setTimeout(() => stompClient.subscribe(`/app/chat/${userName}/getChats`, onListofRoom), 100);

}

$(document).on('click', '.btn.btn-primary.join', function(event){
    var roomNameValue = $(this).attr('value');
    if(roomNameValue)
    {
        enterRoom(roomNameValue);
    }
    event.preventDefault();
});

function handleEnter(e, func){
    if (e.keyCode == 13 || e.which == 13)
       console.log("enter")
        }

init();