document.addEventListener("DOMContentLoaded", function () {
  const token = Cookies.get('Authorization');

  let currentURL = window.location.href;

// URL을 "/"로 분할하여 배열로 저장합니다.
  let urlParts = currentURL.split("/");

// 배열에서 마지막 요소를 가져옵니다.
  let lastPart = urlParts[urlParts.length - 1];

  $.ajax({
    type: "GET",
    url: "/mya/adoptions/" + lastPart,
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
  let adoptionTitle = document.getElementById("adoptionTitle");
  let communityDescription = document.getElementById("adoptionDescription");
  let viewCount = document.getElementById("viewCount");
  let nickname = document.getElementById("username");
  let createdAt = document.getElementById("createdAt");

  let fileNames = response.fileUrls.fileName.split(",");
  let animalName = document.getElementById("animal-name");
  let animalGender = document.getElementById("animal-gender");
  let animalAge = document.getElementById("animal-age");
  let area = document.getElementById("area");
  let category = document.getElementById("category");

  adoptionTitle.innerText = response.title;
  communityDescription.innerText = response.description;
  // 아래 부분은 응답 데이터 구조에 따라 약간 다를 수 있습니다.
  viewCount.innerText = response.viewCount + " Views";
  nickname.innerText = response.username;
  createdAt.innerText = response.createAt;

  animalName.innerText = response.animalName;
  animalGender.innerText = response.animalGender;
  animalAge.innerText = response.animalAge;
  area.innerText = response.area;
  category.innerText = response.category;

}

let currentURL = window.location.href;

// URL을 "/"로 분할하여 배열로 저장합니다.
let urlParts = currentURL.split("/");

// 배열에서 마지막 요소를 가져옵니다.
let lastPart = urlParts[urlParts.length - 1];

const token = Cookies.get('Authorization');

function deleteAdoption() {

  $.ajax({
    url: "/mya/adoptions/" + lastPart, // 백엔드 endpoint로 수정
    type: "DELETE",
    headers: {"Authorization": token},
    success: function (response) {
      alert('삭제가 완료 되었습니다!', response);
      // 다른 성공 동작 처리
      window.location.href = "/mya/view/post/adoptions";
    },
    error: function (error) {
      alert('Upload failed.');
      console.error("Error:", error);
      // 다른 에러 동작 처리
    }
  });
}

function updateAdoption() {
  window.location.href = "/mya/view/post/adoptions/update/" + lastPart;
}