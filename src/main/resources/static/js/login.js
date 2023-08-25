document.addEventListener("DOMContentLoaded", function () {
  console.log("DOMContentLoaded event fired");

});
const host = "http://" + window.location.host;

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
    alert('로그인 성공');

    window.location.href = host;
  })
  .fail(function (response) {
    alert('로그인 오류: ' + response.responseJSON.msg);
  })
}