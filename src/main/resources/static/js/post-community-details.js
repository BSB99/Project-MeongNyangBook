document.addEventListener("DOMContentLoaded", function () {
  const token = Cookies.get('Authorization');

  let currentURL = window.location.href;

// URL을 "/"로 분할하여 배열로 저장합니다.
  let urlParts = currentURL.split("/");

// 배열에서 마지막 요소를 가져옵니다.
  let lastPart = urlParts[urlParts.length - 1];

  $.ajax({
    type: "GET",
    url: "/mya/communities/" + lastPart,
    headers: {"Authorization": token}
  })
  .done(function (response) {
    console.log("단건 조회 성공");
    console.log(response);
    // fetchWorkerList(response);
    setCardData(response);
    // categoryId = response.categoryId;
    // commentBtnUpdate();

  })
  .fail(function (response, status, xhr) {
    alert("카드 정보 불러오기 실패");
    console.log(response);
  })

})

function setCardData(response) {
  let communityTitle = document.getElementById("communityTitle");
  let communityDescription = document.getElementById("communityDescription");
  let viewCount = document.getElementById("viewCount");
  let nickname = document.getElementById("username");
  let createdAt = document.getElementById("createdAt");

  communityTitle.innerText = response.title;
  communityDescription.innerText = response.description;

  // 아래 부분은 응답 데이터 구조에 따라 약간 다를 수 있습니다.
  viewCount.innerText = response.viewCount + " Views";
  nickname.innerText = response.username;
  createdAt.innerText = response.createdAt;
}

let currentURL = window.location.href;

// URL을 "/"로 분할하여 배열로 저장합니다.
let urlParts = currentURL.split("/");

// 배열에서 마지막 요소를 가져옵니다.
let lastPart = urlParts[urlParts.length - 1];

const token = Cookies.get('Authorization');

function deleteCommunity() {

  $.ajax({
    url: "/mya/communities/" + lastPart, // 백엔드 endpoint로 수정
    type: "DELETE",
    headers: {"Authorization": token},
    success: function (response) {
      alert('삭제가 완료 되었습니다!', response);
      // 다른 성공 동작 처리
      window.location.href = "/mya/view/post/community";
    },
    error: function (error) {
      alert('Upload failed.');
      console.error("Error:", error);
      // 다른 에러 동작 처리
    }
  });
}

function updateCommunity() {
  window.location.href = "/mya/view/post/community/update/" + lastPart;
}