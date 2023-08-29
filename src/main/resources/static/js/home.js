function logout() {
  const token = Cookies.get('Authorization');

  $.ajax({
    type: "POST",
    url: "/mya/auth/logout",
    headers: {'Authorization': token}
  })
  .done(function (response, status, xhr) {
    alert('로그아웃 완료');
    Cookies.remove('Authorization', {path: '/'});
    // console.log(response.get("msg"));

    window.location.href = "/";
  })
  .fail(function (response) {
    alert('로그아웃 오류: ' + response);
    console.log(response);
  })
}

function parseJwt(token) {
  var base64Url = token.split('.')[1];
  var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  var jsonPayload = decodeURIComponent(
      window.atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));

  return JSON.parse(jsonPayload);
}

document.addEventListener("DOMContentLoaded", function () {
  start();
});

function start() {
  const auth = Cookies.get('Authorization');
  console.log("auth=", auth);

  if (!auth) { // 쿠키가 없을 경우
    console.log(1);
    document.getElementById('login-text').style.display = 'block';
    document.getElementById('logout-text').style.display = 'none';
    document.getElementById('mypage-text').style.display = 'none';
  } else { // 쿠키가 있을 경우
    console.log(2);
    document.getElementById('login-text').style.display = 'none';
    document.getElementById('logout-text').style.display = 'block';
    document.getElementById('mypage-text').style.display = 'block';

    const postBoxes = document.getElementsByClassName('postbox');
    for (let i = 0; i < postBoxes.length; i++) {
      postBoxes[i].style.display = 'block';
    }
  }
}
