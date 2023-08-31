const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/mya-websocket'
});

let chatRoomId = null;

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/send/room/' + chatRoomId, (message) => {
        console.log(message.body);
        showMessage(JSON.parse(message.body));
    });
    $.ajax({
        url: "/mya/chats/room/" + chatRoomId,
        type: "GET"
    })
        .done(function (response) {
            console.log(response)
            response.forEach(function (message) {
                showMessage(message);
            })
        })
        .fail(function (response, status, xhr) {
            console.log(response);
            console.log(status);
            console.log(xhr);
        })
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#chat-messages").html("");
}

function connect() {
    chatRoomId = $("#room-number").val();
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.publish({
        destination: "/room/" + chatRoomId,
        body: JSON.stringify({
            'senderName': $("#name").val(),
            'content': $("#message").val()
        })
    });
}

function showMessage(message) {
    $("#chat-messages").append("<tr><td>"
        + message.senderName + ": " + message.content + "    " + message.time
        + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendMessage());
});