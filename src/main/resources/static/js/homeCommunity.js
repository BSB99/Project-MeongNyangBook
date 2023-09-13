const token = Cookies.get('Authorization');
document.addEventListener("DOMContentLoaded", function () {

  let page = 0;
  let size = 3;
  $.ajax({
    type: "GET",
    url: `/mya/communities?page=${page}&size=${size}`,
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('#community_last').empty();
    for (let i = 0; i < 3; i++) {
      let communityTitle = response.responseList[i].title; //h5
      let createdAt = response.responseList[i].createdAt; //span - 날짜
      let communityId = response.responseList[i].id;
      let imgUrl = response.responseList[i].fileUrls.fileName.split(
          ",")[0].split("/");

      let resizeImg;
      if (imgUrl === null) {
        resizeImg = "https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/1_copy.jpg";
      } else {
        resizeImg = "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/resize/"
            + imgUrl[imgUrl.length - 1]
      }

      let html = `
      <div class="col-lg-4 col-md-6 col-sm-6">
        <div class="blog__item">
          <div class="blog__item__text">
            <span><img src="${resizeImg}"
                       alt="" style="height: 250px">"${createdAt}"></span>
            <h5>"${communityTitle}"</h5>
            <a href="/mya/view/communities/${communityId}">Read More</a>
          </div>
        </div>
      </div>
    `;
      $('#community_last').append(html);
    }
  })
  .fail(function (response) {
    alert(response.responseJSON.msg);
  })
});

function gptAsking() {
  document.getElementById("answer").classList.remove("d-none");

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