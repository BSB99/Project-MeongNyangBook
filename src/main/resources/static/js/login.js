document.addEventListener("DOMContentLoaded", function () {
    console.log("DOMContentLoaded event fired");
});
const host = "http://" + window.location.host;

//휴대폰 인증번호 전송
//모달 열기
function sendPhoneMessage() {
    let phone = $('#signup-phone-number').val();

    $.ajax({
        type: "POST",
        url: "/mya/users/phone",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            phoneNumber: phone
        })
    })
        .done(function (response, status, xhr) {
            alert('인증번호 전송 성공');

        })
        .fail(function (response) {
            alert('인증번호 전송 오류: ' + response.responseJSON.msg);
        })

    document.getElementById("modal").style.display = "block";
    const signupBtn = document.getElementById("signup-btn");
    signupBtn.disabled = true;
    signupBtn.style.opacity = 0.6;
    signupBtn.style.cursor = "not-allowed";
    const accessBtn = document.getElementById('access-button');
    accessBtn.disabled = true;
    accessBtn.style.opacity = 0.6;
    accessBtn.style.cursor = "not-allowed";
}

//휴대폰 인증번호 인증
function accessPhone() {
    let accessNumber = $('#access-number').val();
    let phone = $('#signup-phone-number').val();

    if (accessNumber == "" || accessNumber == null) {
        alert("번호를 입력해주세요.");
    } else {
        $.ajax({
            type: "POST",
            url: "/mya/users/phone/auth",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                phoneNumber: phone,
                code: accessNumber
            })
        })

            .done(function (response, status, xhr) {
                alert('인증번호 확인');
                document.getElementById('access-button').disabled = true;
                closeModalBtn();
                const accessBtn = document.getElementById('access-button');
                accessBtn.disabled = true;
                accessBtn.style.opacity = 0.6;
                accessBtn.style.cursor = "not-allowed";

            })
            .fail(function (response) {
                alert('인증번호 오류: ' + response.responseJSON.msg);
            })
    }
}

//모달 닫기
function closeModalBtn() {
    document.getElementById("modal").style.display = "none";
    const accessBtn = document.getElementById('access-button');
    accessBtn.disabled = false;
    accessBtn.style.opacity = 1;
    accessBtn.style.cursor = "pointer";

    const signupBtn = document.getElementById("signup-btn");
    signupBtn.disabled = false;
    signupBtn.style.opacity = 1;
    signupBtn.style.cursor = "pointer";
}

//회원 가입
function onSignup() {
    let username = $('#signup-username').val();
    let password = $('#signup-password').val();
    let nickname = $('#signup-nickname').val();
    let introduce = $('#signup-introduce').val();
    let address = $('#signup-adress').val();
    let phone = $('#signup-phone-number').val();
    let adminKey = $('#signup-admin-key').val();

    if (document.getElementById('access-button').disabled) {
        $.ajax({
            type: "POST",
            url: "/mya/users/signup",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                username: username,
                password: password,
                nickname: nickname,
                introduce: introduce,
                address: address,
                phoneNumber: phone,
                adminToken: adminKey
            })
        })

            .done(function (response, status, xhr) {
                window.location.href = host;
            })
            .fail(function (response) {
                alert('회원가입 오류: ' + response.responseJSON.msg);
            })
    } else {
        console.log(address);
        alert("핸드폰 인증을 완료하세요.");
    }
}

//로그인
function onLogin() {
    let username = $('#login-email').val();
    let password = $('#login-password').val();

    $.ajax({
        type: "POST",
        url: "/mya/users/login",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            username: username,
            password: password
        })
    })
        .done(function (response, status, xhr) {
            const token = xhr.getResponseHeader('Authorization');

            // 쿠키 만료 시간(초 단위) 설정
            let maxAgeInSeconds = 60 * 60; // 1시간

            // 현재 시간에 만료 시간(초 단위)을 더한 값을 구함
            let expirationDate = new Date();
            expirationDate.setTime(
                expirationDate.getTime() + (maxAgeInSeconds * 1000));

            Cookies.set('Authorization', token, {path: '/', expires: expirationDate});

            window.location.href = host;
        })
        .fail(function (response) {
            alert('로그인 오류: ' + response.responseJSON.msg);
        })
}