const firebaseConfig = {
  apiKey: "AIzaSyCIoZVCfP5XG038Jald_Q9BxphVkEPiCZc",
  authDomain: "meongnyangbook.firebaseapp.com",
  projectId: "meongnyangbook",
  storageBucket: "meongnyangbook.appspot.com",
  messagingSenderId: "1044918801546",
  appId: "1:1044918801546:web:70dc543eaaac987c820973",
  measurementId: "G-JYXQFCBL5J"
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

document.addEventListener("DOMContentLoaded", function () {
  Notification.requestPermission().then(permission => {
    if (permission === 'granted') {
      console.log('Notification permission granted.');
      // 여기서 sendAlarm 함수 호출
    } else {
      console.log('Notification permission denied.');
    }
  });
  const token = Cookies.get('Authorization');
  getToken(1, 1, 1)
  $.ajax({
    type: "GET",
    url: `/mya/users`,
    headers: {
      "Authorization": token,
    }
  })
  .done((res) => {
    sendAlarm(res.userId);
  })
});

function getToken(titleContent, disc, user) {
  console.log(titleContent);
  messaging.getToken(
      {vapidKey: 'BOg78AzxKfvkR97RRlaejNe1WrkrRi4lWJYkYQqIpihuEmFM4ld_w-vl9Z1ydiYBp_ywFs_jHYmjDu-yBZEt52M'}).then(
      (currentToken) => {
        if (currentToken) {
          console.log(currentToken);
          sendNotification(currentToken, titleContent, disc, user);
        } else {
          console.log(
              'No registration token available. Request permission to generate one.');
        }
      }).catch((err) => {
    console.log('An error occurred while retrieving token. ', err);
  });
}

async function sendNotification(token, title, body, userNo) {
  const url = `/api/notifications/send?token=${token}&title=${title}&body=${body}&userNo=${userNo}`;
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
  });

  const data = await response.json();
  console.log(data);
}

function sendAlarm(userId) {
  messaging.onMessage((payload) => {
    console.log('Message received:', payload);
    console.log(payload.data.userNo && payload.data.userNo !== userId);
    console.log(payload.data.userNo)
    console.log(userId);
    console.log(payload.data.userNo !== userId)

    if (payload.data.userNo && parseInt(payload.data.userNo) !== userId) {
      // 현재 사용자에게 해당하지 않는 알림은 무시
      return;
    }

    // 페이로드를 사용하여 브라우저 알림 표시
    const notificationTitle = payload.notification.title;
    const notificationOptions = {
      body: payload.notification.body,
      icon: payload.notification.icon, // 필요하다면 아이콘도 설정
      // 추가 옵션들...
    };

    new Notification(notificationTitle, notificationOptions);
  });
}
