
document.addEventListener("DOMContentLoaded", function () {
    import firebase from "firebase/compat/app";
    import "firebase/compat/messaging";
    import { getMessaging, getToken } from "firebase/messaging";

// TODO: Replace the following with your app's Firebase project configuration
// See: https://firebase.google.com/docs/web/learn-more#config-object
    const firebaseConfig = {
        apiKey: "AIzaSyCIoZVCfP5XG038Jald_Q9BxphVkEPiCZc",
        authDomain: "meongnyangbook.firebaseapp.com",
        projectId: "meongnyangbook",
        storageBucket: "meongnyangbook.appspot.com",
        messagingSenderId: "1044918801546",
        appId: "1:1044918801546:web:70dc543eaaac987c820973",
        measurementId: "G-JYXQFCBL5J"
    };

// Initialize Firebase
    firebase.initializeApp(firebaseConfig);


// Initialize Firebase Cloud Messaging and get a reference to the service
    const messaging = firebase.messaging();


// Get registration token. Initially this makes a network call, once retrieved
// subsequent calls to getToken will return from cache.
    const getMessaging = getMessaging();
    getToken(getMessaging, { vapidKey: '<BOg78AzxKfvkR97RRlaejNe1WrkrRi4lWJYkYQqIpihuEmFM4ld_w-vl9Z1ydiYBp_ywFs_jHYmjDu-yBZEt52M>' }).then((currentToken) => {
        if (currentToken) {
            console.log(currentToken);
        } else {
            // Show permission request UI
            console.log('No registration token available. Request permission to generate one.');
            // ...
        }
    }).catch((err) => {
        console.log('An error occurred while retrieving token. ', err);
        // ...
    });

    let token;  // 위의 코드를 통해 얻은 토큰
    const title = "알림 제목";
    const body = "알림 내용";

    sendNotification(token, title, body);

});

const sendNotification = async (token, title, body) => {
    const url = '/api/notifications/send';
    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            token: token,
            title: title,
            body: body
        })
    });

    const data = await response.json();
    console.log(data);
};