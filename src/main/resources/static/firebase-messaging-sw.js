importScripts('https://www.gstatic.com/firebasejs/8.6.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.6.1/firebase-messaging.js');

firebase.initializeApp({
    'messagingSenderId': '1044918801546',// 여기에 messagingSenderId를 넣어주세요.
    'apiKey': "AIzaSyCIoZVCfP5XG038Jald_Q9BxphVkEPiCZc",
    'authDomain': "meongnyangbook.firebaseapp.com",
    'projectId': "meongnyangbook",
    'storageBucket': "meongnyangbook.appspot.com",
    'appId': "1:1044918801546:web:70dc543eaaac987c820973",
    'measurementId': "G-JYXQFCBL5J"
});

const messaging = firebase.messaging();