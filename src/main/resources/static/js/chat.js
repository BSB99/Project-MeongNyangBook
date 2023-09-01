const token = Cookies.get('Authorization');
let userId;
let roomId;
let userNickname;

document.addEventListener("DOMContentLoaded", function () {

    if (token === undefined) {
        alert("로그인 해주세요");
        location.href="/mya/view/users/sign-in";
    }


    $.ajax({
        type: "GET",
        url: `/mya/users`,
        headers: {
            "Authorization": token,
        }
    })
        .done((res) => {
            userNickname = res.nickname;
            userId = res.userId;
        })

    const friendList = document.querySelectorAll("#friends");

    let firstChatHtml = ``;

    $.ajax({
        type: "GET",
        url: `/mya/chats/rooms`,
        headers: {
            "Authorization": token,
        }
    })
        .done((res) => {
            for (let i of res) {
                if (i.participant.nickname === userNickname) {
                    firstChatHtml += `
                <div class="friend" onclick="createConnect(${i.id})">
                <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/1_copy.jpg"/>
                <p>
                <strong>${i.constructor.nickname}</strong>
                </p>
                <div class="status available"></div>
                </div>
                `
                } else {
                    firstChatHtml += `
                <div class="friend" onclick="createConnect(${i.id})">
                <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/1_copy.jpg" "/>
                <p>
                <strong>${i.participant.nickname}</strong>
                </p>
                <div class="status available"></div>
                </div>
                `
                }
            }

            friendList.forEach((container) => {
                container.innerHTML = firstChatHtml;
            })
        })
        .fail((error) => {
            console.error("Error:", error);
        })
});

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/mya-websocket'
});
let chatModal;
async function createConnect(chatRoomId) {
    roomId = chatRoomId;
    let chatContent = await getChatInfo(chatRoomId);

        chatContent += `</div>`;
            document.getElementById('chatModalBody').innerHTML = chatContent;

            // 모달 표시
    chatModal = new bootstrap.Modal(document.getElementById('chatModal'), {
        backdrop: 'static',
        keyboard: false
    });
            chatModal.show();

            stompClient.activate();

            stompClient.onConnect = (frame) => {
                //setConnected(true);
                stompClient.subscribe('/send/room/' + chatRoomId, (message) => {
                    const receivedMessage = JSON.parse(message.body);
                    console.log(receivedMessage);
                    if(receivedMessage.user.id !== userId) {
                        displayReceivedMessage(receivedMessage.message);
                    }
                });
                $.ajax({
                    url: "/mya/chats/room/" + chatRoomId,
                    type: "GET"
                })
                    .done(function (response) {
                        response.forEach(function (message) {
                            //showMessage(message);
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
        }

function displayReceivedMessage(message) {
    const chatModalBody = document.querySelectorAll('#chat-messages');
    const messageElement = `
        <div class="message">
        <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/1_copy.jpg" />
        <div class="bubble">
          ${message}
          <div class="corner"></div>
          <span>Just now</span>
        </div>
        </div>
    `;

    chatModalBody.forEach((container) => {
        container.innerHTML += messageElement;
    })
    chatModalBody.scrollTop = chatModalBody.scrollHeight; // 스크롤을 메시지의 마지막 부분으로 이동
}

    function sendMessageToChat() {
        const inputElement = document.querySelector(".input-msg");
        const message = inputElement.value;

        // 메시지가 비어있지 않으면 sendMsg 함수 호출
        if (message.trim() !== "") {
            sendMsg(message);
            inputElement.value = "";  // 입력 필드 초기화
        }
    }

    function sendMsg(message) {
        stompClient.publish({
            destination: "/room/" + roomId,
            body: JSON.stringify({
                'msg': message,
                'userId': userId
            })
        });

        displayMyMessage(message);
    }

function displayMyMessage(message) {
    const chatMessagesElement = document.querySelectorAll('#chat-messages');
    const messageElement = `
        <div class="message right">
        <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/1_copy.jpg" />
        <div class="bubble">
          ${message}
          <div class="corner"></div>
          <span>Just now</span>
        </div>
        </div>
    `;

    chatMessagesElement.forEach((container) => {
        container.innerHTML += messageElement;
    })
    chatMessagesElement.scrollTop = chatMessagesElement.scrollHeight;
}
async function getChatInfo(chatRoomId) {
    let chatContent = `<div id="chat-messages">`;

    try {
        const res = await $.ajax({
            type: "GET",
            url: `/mya/chats/room/` + chatRoomId,
            headers: {
                "Authorization": token,
            }
        });

        res.forEach(message => {
            if (message.responseDto.nickname === userNickname) {
                chatContent += `
                    <div class="message right">
                    <a href="/mya/view/users/my-profile">
                    <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/1_copy.jpg" />
                    </a>
                    <div class="bubble">
                      ${message.msg}
                      <div class="corner"></div>
                      <span>${message.localTime}</span>
                    </div>
                    </div>
                `;
            } else {
                chatContent += `
                    <div class="message">
                    <a href="/mya/view/users/relative-profile/${message.responseDto.id}">
                    <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/1_copy.jpg" />
                    </a>
                    <div class="bubble">
                      ${message.msg}
                      <div class="corner"></div>
                      <span>${message.localTime}</span>
                    </div>
                    </div>
                `;
            }
        });
        return chatContent;
    } catch (error) {
        console.error("Error fetching chat info:", error);
        return chatContent;
    }
}

function closeChatBtn() {
    try {
        stompClient.deactivate();
    } catch (error) {
        console.error("Error deactivating stompClient:", error);
    }
    chatModal.hide();
}