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

function gptAsking() {
  const token = Cookies.get('Authorization');

  let question = document.getElementById("questionInput").value;
  let anwerbox = document.getElementById("answer");
  const message = document.getElementById("message");

  if (question === null || question === "") {
    alert("질문을 입력해주세요.");
  } else {
    message.style.display = "block"; // 메시지 보이기
    setTimeout(() => {
      message.style.display = "none"; // 일정 시간 후 메시지 숨기기
    }, 6000); // 2초 후에 숨김 (2000 밀리초)

    const questionJson = {
      question: question
    }
    $.ajax({
      type: "POST",
      url: "/chat-gpt/question",
      contentType: "application/json",
      headers: {'Authorization': token},
      data: JSON.stringify(questionJson)

    })
    .done(function (response, status, xhr) {
      anwerbox.innerText = response;
    })
    .fail(function (response) {
      console.log(response);
      alert('Error 다시 질문해주세요 ' + response);
      console.log(response);
    })
  }
}

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
