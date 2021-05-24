const url='http://localhost:8080';

var createRoomForm=document.querySelector('#createRoomForm')

var choseRoomName=document.querySelector('#chooseRoomName')
var chooseRoomNameStudent=document.querySelector('#chooseRoomNameStudent')

var chatWith=document.querySelector('#chatWith')

var messageToSend=document.querySelector('#message-to-send')
var messageForm=document.querySelector('#messageForm')

var userToAdd=document.querySelector('#studentName')
var groupToAdd=document.querySelector('#groupName')
var teatherToAdd=document.querySelector('#teathersName')
var roomToCreate=document.querySelector('#roomNameStudent')
var roomName = document.querySelector('#roomName');
var ChooseUserToAdd = document.querySelector('#addUserForm');
var ChooseGroupToAdd = document.querySelector('#addGroupForm');
var CreateRoomforStudent = document.querySelector('#createChatForStudentForm');
var chatRoomUserForm = document.querySelector('#chatRoomUserForm');
var stompClient = null;
var currentSubscription;


var roomClient=null;
var roomSubscription;

var messageClient=null;
var messageSubscription;

var userRight=null;
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
        $.get(url + "/getCurrentUserRight",function (response){
            userRight=response;
        })
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
    let usersTemplateHTML='<a id="ChatRoomContainer_' + room + '" href="#" onclick="enterRoom(\''+room+'\')" <li class="clearfix">\n' +
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
    var whatChat=payload.body;
    chatToTop(whatChat);
    if (roomId!=whatChat) {
        newMessageSound();
        let elem=document.getElementById('userNameAppender_'+whatChat)
        elem.classList.add('newMessage');
    }
}

function chatToTop(roomName){
    let elem=document.getElementById('ChatRoomContainer_'+roomName)
    elem.remove();
    let usersTemplateHTML='<a id="ChatRoomContainer_' + roomName + '"  href="#" onclick="enterRoom(\''+roomName+'\')" <li class="clearfix">\n' +
        '                <div class="about">\n' +
        '                     <div id="userNameAppender_' + roomName + '" class="name">' + roomName + '</div>\n' +
        '                    <div class="status">\n' +
        '                        <i class="fa fa-circle online"></i>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </li></a>'+$('#ChatList').html();
    $('#ChatList').html(usersTemplateHTML);
}

function roomClientConnected() {
    console.log("start roomClient")
    roomSubscription=roomClient.subscribe(`/topic/chat/`+userName, updateRoom);
}

function onListofRoom(payload) {
    var listRoom =JSON.parse(payload.body);
    let map = new Map()
    /*var listRoom = JSON.parse(payload.body);
    for (var value in listRoom) {
        map.set(value,listRoom[value])
    }*/
    let usersTemplateHTML="";
    for (let i = 0; i < listRoom.length; i++) {
        var key=listRoom[i].chatname;
        if (listRoom[i].haveNewMessage){
           usersTemplateHTML=usersTemplateHTML+'<a id="ChatRoomContainer_' + key + '" href="#" onclick="enterRoom(\''+key+'\')" <li class="clearfix">\n' +
               '                <div class="about">\n' +
               '                     <div id="userNameAppender_' + key + '" class="newMessage">' + key + '</div>\n' +
               '                    <div class="status">\n' +
               '                        <i class="fa fa-circle online"></i>\n' +
               '                    </div>\n' +
               '                </div>\n' +
               '            </li></a>';
       }else{
           usersTemplateHTML=usersTemplateHTML+'<a id="ChatRoomContainer_' + key + '" href="#" onclick="enterRoom(\''+key+'\')" <li class="clearfix">\n' +
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
        toastr.error("Такой чат уже есть ")
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
    if (elem.classList!=null){
        elem.classList.remove('newMessage')
        elem.classList.add('name')
    }
    if (currentSubscription){
        currentSubscription.unsubscribe();
    }
    stompClient.subscribe(`/app/chat/${roomId}/getPrevious`, onPreviousMessage);
    currentSubscription=stompClient.subscribe(`/topic/${roomId}`,onMessageReceived);
    elem=document.getElementById('leaveRoom')
    elem.classList.remove('hidden');
    elem=document.getElementById('showUser')
    elem.classList.remove('hidden');
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
function newMessageSound(){
    var audio = new Audio();
    audio.src="resources/audio/message.mp3";
    audio.volume=0.4;
    audio.autoplay = true;
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
    if (message.fromLogin==userName)
        var templateResponse = Handlebars.compile($("#message-response-template-from-my").html());
    else
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
    if (createRoomForm.classList.contains('hidden'))
        createRoomForm.classList.remove('hidden')
    else
        createRoomForm.classList.add('hidden')
    event.preventDefault();
}

function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
}

function addUserFormOpen(){
    if (ChooseUserToAdd.classList.contains('hidden')){
    ChooseUserToAdd.classList.remove('hidden');
    stompClient.subscribe(`/app/chat/getUserList`, getUserToAdd);}
    else
        ChooseUserToAdd.classList.add('hidden');
}
function getUserToAdd(payload){
    var user = JSON.parse(payload.body);
    let usersTemplateHTML="";
    for (let i = 0; i <user.length ; i++) {
        usersTemplateHTML=usersTemplateHTML+'<option >'+user[i]+'</option>'
    }
    $('#student').html(usersTemplateHTML);
}

function getTeatherToAdd(payload){
    var user = JSON.parse(payload.body);
    let usersTemplateHTML="";
    for (let i = 0; i <user.length ; i++) {
        usersTemplateHTML=usersTemplateHTML+'<option >'+user[i]+'</option>'
    }
    $('#teathers').html(usersTemplateHTML);
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

function goToMain(){
    document.location.href = "/";
}
function logOut(){
    document.location.href = "/logout";
}

function addGroupFormOpen(){
    if (ChooseGroupToAdd.classList.contains('hidden')){
    ChooseGroupToAdd.classList.remove('hidden');
    stompClient.subscribe(`/app/chat/getGroupList`, getGroupToAdd);}
    else
        ChooseGroupToAdd.classList.add('hidden');
}

function addGroupToChatFunction(){
    var groupToAddvalue=groupToAdd.value.trim();
    var array = $('#group option').map(function () {
        return this.value;
    }).get();
    if (array.indexOf(groupToAddvalue)!=-1){
    var data={
        roomName:roomId,
    };
    stompClient.send(`/app/chat/rooms/${groupToAddvalue}/addGroup`,{},JSON.stringify(data));
    ChooseGroupToAdd.classList.add('hidden');}
    else
        toastr.info('Такой группы нету, проверьте данные');
}
function addUserToChatFunction(){
    var userToAddValue=userToAdd.value.trim();
    var array = $('#student option').map(function () {
        return this.value;
    }).get();
    if (array.indexOf(userToAddValue)!=-1){
    var data={
        roomName:roomId,
    };
    updateRoomList(userToAddValue,roomId)
    stompClient.send(`/app/chat/rooms/${userToAddValue}`,{},JSON.stringify(data));
    ChooseUserToAdd.classList.add('hidden');}
    else
        toastr.info('Такого пользователя нету, выбирайте из списка');
}
function createRoomForStudent(){
    var teather=teatherToAdd.value.trim();
    var room=roomToCreate.value.trim();
    if (teather!='' & room!=''){
        stompClient.subscribe(`/app/chat/${room}/CreateRoom`, getAnswerRoomStudent);
    }
   /* var data={
        roomName:roomId,
    };
    updateRoomList(userToAddValue,roomId)
    stompClient.send(`/app/chat/rooms/${userToAddValue}`,{},JSON.stringify(data));

    CreateRoomforStudent.classList.add('hidden');*/
}
function getAnswerRoomStudent(payload){
    var message = JSON.parse(payload.body);
    console.log(message)
    if (message){
        var teather=teatherToAdd.value.trim();
        var room=roomToCreate.value.trim();
        updateRoomList(userName,room)
        updateRoomList(teather,room)

        var data={
            roomName:room,
        };
        stompClient.send(`/app/chat/rooms/${teather}`,{},JSON.stringify(data));
        stompClient.send(`/app/chat/rooms/${userName}`,{},JSON.stringify(data));
        setTimeout(() => enterRoom(room), 100);
    }
    else{
        toast.error("Такой чат уже есть ")
    }
    /*

     var data={
        roomName:roomId,
    };
    console.log("addUserToChatFunction "+roomId)
    updateRoomList(userToAddValue,roomId)
    stompClient.send(`/app/chat/rooms/${userToAddValue}`,{},JSON.stringify(data));
     */
}

function createRoomStudent(){
    if (CreateRoomforStudent.classList.contains('hidden')){
    CreateRoomforStudent.classList.remove('hidden')
    stompClient.subscribe(`/app/chat/getTeatherList`, getTeatherToAdd);}
    else
        CreateRoomforStudent.classList.add('hidden')
    event.preventDefault();
}

$(document).ready(function (){
    // refreshRoom.addEventListener('submit',listRoom,true)
    if (chooseRoomNameStudent!=null){
        chooseRoomNameStudent.addEventListener('submit',createRoomStudent,true)
    }else{
        createRoomForm.addEventListener('submit',createRoom,true)
        choseRoomName.addEventListener('submit',chooseChatName,true)
    }
    messageForm.addEventListener('submit',sendMessage,true)
})

function leaveFromRoom(){
    var data={
        roomName:roomId,
    };
    stompClient.send(`/app/chat/roomsLeave/${userName}`,{},JSON.stringify(data));
    chatCleaner()
    chatWith.textContent="Chat with ...";
    currentSubscription.unsubscribe();
    roomId=null;
    setTimeout(() => stompClient.subscribe(`/app/chat/${userName}/getChats`, onListofRoom), 100);
    let elem=document.getElementById('leaveRoom')
    elem.classList.add('hidden');
    elem=document.getElementById('showUser')
    elem.classList.add('hidden');
}
function showUserInRoom(){
    if (chatRoomUserForm.classList.contains('hidden')){
    stompClient.subscribe(`/app/chat/${roomId}/getUsers`, drowUserInChat)
    chatRoomUserForm.classList.remove('hidden')
    }
    else
        chatRoomUserForm.classList.add('hidden')
}
function drowUserInChat(payload){
    var answer =JSON.parse(payload.body);

    let html="";
    for (let i = 0; i <answer.length ; i++) {
        html+="<div>"+ answer[i] +"</div>"
    }
    $('#chatRoomUser').html(html);

}
function hideUserInRoom(){
    chatRoomUserForm.classList.add('hidden')
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