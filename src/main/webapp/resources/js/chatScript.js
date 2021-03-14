const url='http://localhost:8080';

var createRoomForm=document.querySelector('#createRoomForm')
var choseRoomName=document.querySelector('#chooseRoomName')
var openUserForm=document.querySelector('#openUserForm')
var openGroupForm=document.querySelector('#openGroupForm')
var refreshRoom=document.querySelector('#refreshRoom')
var chatWith=document.querySelector('#chatWith')
var messageToSend=document.querySelector('#message-to-send')
var messageForm=document.querySelector('#messageForm')
var listOfRoom = document.querySelector('#ChatList');
var roomName = document.querySelector('#roomName');
var stompClient = null;
var currentSubscription;

var userName = null;
var roomId = null;
var topic = null;

let $chatHistory;
let $chatHistoryList;


function init() {
    cacheDOM();

}


function cacheDOM(){
    $chatHistory = $('.chat-history');
    $chatHistoryList = $chatHistory.find('ul');
}

window.onload = function() {
    $.get(url + "/getCurrentUser",function (response){
        userName=response;
        $('#userName').append(userName)
        var socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({},onConnected,onError);
    })
}

function listRoom()
{
    stompClient.subscribe(`/app/chat/${userName}/getChats`, onListofRoom);
}

function onListofRoom(payload) {
    var listRoom = JSON.parse(payload.body);
    let usersTemplateHTML="";
    for (let i=0; i<listRoom.length;i++){//сделать переход в чат и контроль при создании , selectUser глянуть 
        usersTemplateHTML=usersTemplateHTML+'<a href="#" onclick="enterRoom(\''+listRoom[i]+'\')" <li class="clearfix">\n' +
            '                <div class="about">\n' +
            '                     <div id="userNameAppender_' + listRoom[i] + '" class="name">' + listRoom[i] + '</div>\n' +
            '                    <div class="status">\n' +
            '                        <i class="fa fa-circle online"></i>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </li></a>';
    }
    $('#ChatList').html(usersTemplateHTML);
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function onConnected() {
    console.log("start")
    listRoom();
}

/*function createRoom(event){
    var roomNameValue=roomName.value.trim();

    if (roomNameValue){
        var chatRoom={
            roomName:roomNameValue
        };
        stompClient.send('/app/chat/rooms',{},JSON.stringify(chatRoom));
        setTimeout(() => enterRoom(roomNameValue), 200);
    }
    createRoomForm.classList.add('hidden')
    event.preventDefault();
}*/
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
    if (message)
        enterRoom(roomName.value.trim())
    else
        alert("This chat exists ")
}

function enterRoom(newRoomId){
    var data={
        roomName:newRoomId,
    };
    stompClient.send(`/app/chat/rooms/${userName}`,{},JSON.stringify(data));
    chatCleaner()
    roomId=newRoomId;
    chatWith.textContent=newRoomId;
    topic = `/app/chat/${newRoomId}`;
    if (currentSubscription){
        currentSubscription.unsubscribe();
    }
    stompClient.subscribe(`/app/chat/${roomId}/getPrevious`, onPreviousMessage);
    currentSubscription=stompClient.subscribe(`/topic/${roomId}`,onMessageReceived);
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

function sendMessage(event){
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
    event.preventDefault();
}

function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
}
function addUserFormOpen(){//приделать окошко hiden и сделать появление и заполнение при нажатии
    console.log("user form")

}

function addGroupFormOpen(){
    console.log("group form")

}

$(document).ready(function (){
    refreshRoom.addEventListener('submit',listRoom,true)
    createRoomForm.addEventListener('submit',createRoom,true)
    choseRoomName.addEventListener('submit',chooseChatName,true)
    messageForm.addEventListener('submit',sendMessage,true)
    openUserForm.addEventListener('submit',addUserFormOpen,true)
    openGroupForm.addEventListener('submit',addGroupFormOpen,true)
})

$(document).on('click', '.btn.btn-primary.join', function(event){
    var roomNameValue = $(this).attr('value');
    if(roomNameValue)
    {
        enterRoom(roomNameValue);
    }
    event.preventDefault();
});

init();