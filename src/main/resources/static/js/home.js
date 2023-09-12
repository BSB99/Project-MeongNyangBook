function logout() {

  $.ajax({
    type: "POST",
    url: "/mya/auth/logout",
    headers: {'Authorization': token}
  })
  .done(function (response, status, xhr) {
    Cookies.remove('Authorization', {path: '/'});
    // console.log(response.get("msg"));

    window.location.href = "/";
  })
  .fail(function (response) {
    alert('로그아웃 오류: ' + response);
    console.log(response);
  })
}

document.addEventListener("DOMContentLoaded", function () {
  start();
});

function start() {
  const auth = Cookies.get('Authorization');

  if (!auth) { // 쿠키가 없을 경우
    document.getElementById('login-text').style.display = 'block';
    document.getElementById('logout-text').style.display = 'none';
    document.getElementById('mypage-text').style.display = 'none';
  } else { // 쿠키가 있을 경우
    document.getElementById('login-text').style.display = 'none';
    document.getElementById('logout-text').style.display = 'block';
    document.getElementById('mypage-text').style.display = 'block';

    const postBoxes = document.getElementsByClassName('postbox');
    for (let i = 0; i < postBoxes.length; i++) {
      postBoxes[i].style.display = 'block';
    }
  }
}
