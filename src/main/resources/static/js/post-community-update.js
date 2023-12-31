var sel_file = [];
$(document).ready(function () {
  $("#multipartFiles").on("change", handleImgFileSelect);
  start();
  confirmHeart();
});

let currentURL = window.location.href;

// URL을 "/"로 분할하여 배열로 저장합니다.
let urlParts = currentURL.split("/");

// 배열에서 마지막 요소를 가져옵니다.
let lastPart = urlParts[urlParts.length - 1];

let imgArr;

const token = Cookies.get('Authorization');

function handleInputClick(event) {
  // Prevent the default behavior of the click event
  event.preventDefault();

  // Trigger the hidden file input element programmatically
  document.getElementById('multipartFiles').click();
}

async function handleImgFileSelect(e) {
  var files = e.target.files;
  var filesArr = Array.prototype.slice.call(files);

  $(".img_wrap").empty();

  for (const f of filesArr) {
    if (!f.type.match("image.*")) {
      alert("확장자는 이미지 확장자만 가능합니다.");
      return;
    }

    sel_file.push(f);

    // Wrap FileReader in a Promise for async/await usage
    const imgData = await readFileAsync(f);

    var img_html = `<img class="img" style="transition: transform 0.5s;" src="${imgData}"/>`
    $(".img_wrap").append(img_html);
  }
  slideImage(); // Call slideImage() after all images have been processed
}

// Promisify FileReader.readAsDataURL
function readFileAsync(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = (event) => resolve(event.target.result);
    reader.onerror = (error) => reject(error);
    reader.readAsDataURL(file);
  });
}

function slideImage() {

  let pages = 0; //현재 인덱스 번호
  let positionValue = 0; //images 위치값
  const IMAGE_WIDTH = 500; //한번 이동 시 IMAGE_WIDTH만큼 이동한다.
  const images = document.querySelectorAll(".img");
  //DOM
  const backBtn = document.querySelector(".prevBtn");
  const nextBtn = document.querySelector(".nextBtn");

  function moveImages() {
    images.forEach(function (image) {
      image.style.transform = `translateX(${positionValue}px)`;
    });
  }

  function back() {
    if (pages > 0) {
      nextBtn.removeAttribute("disabled");
      positionValue += IMAGE_WIDTH;
      moveImages();
      pages -= 1; //이전 페이지로 이동해서 pages를 1감소 시킨다.
    }
    if (pages === 0) {
      backBtn.setAttribute("disabled", "true"); //마지막 장일 때 back버튼이 disabled된다.
    }
  }

  function next() {
    const myDiv = document.getElementById("my_div");
    const imgTags = myDiv.getElementsByTagName("img");
    const imagesSize = imgTags.length;
    if (pages < imagesSize - 1) {
      backBtn.removeAttribute("disabled"); //뒤로 이동해 더이상 disabled가 아니여서 속성을 삭제한다.
      positionValue -= IMAGE_WIDTH; //IMAGE_WIDTH의 증감을 positionValue에 저장한다.
      moveImages();
      //x축으로 positionValue만큼의 px을 이동한다.
      pages += 1; //다음 페이지로 이동해서 pages를 1증가 시킨다.
    }
    if (pages == imagesSize) {
      //
      nextBtn.setAttribute("disabled", "true"); //마지막 장일 때 next버튼이 disabled된다.
    }
  }

  function init() {

    //초기 화면 상태
    backBtn.setAttribute("disabled", "true"); //속성이 disabled가 된다.
    backBtn.addEventListener("click", back); //클릭시 다음으로 이동한다.
    nextBtn.addEventListener("click", next); //클릭시 이전으로 이동한다.
  }

  init();
}

function uploadData() {
  var formData = new FormData($('#uploadForm')[0]); // 폼의 데이터를 FormData 객체로 가져옵니다.

  var requestDto = {
    title: $(".title").val(),
    description: $(".image_upload_textarea").val()
  };

  // formData.append("fileName",);
  formData.append("requestDto", JSON.stringify(requestDto));
  // var deleteFileDto = {
  //   deleteFileName: deleteFileName
  // }
  // formData.append("deleteFileName", JSON.stringify(deleteFileDto));
  $.ajax({
    url: "/mya/communities/" + lastPart, // 백엔드 endpoint로 수정
    type: "PUT",
    data: formData,
    processData: false, // 데이터를 query string으로 변환하는 jQuery 형식을 방지합니다.
    contentType: false, // 서버에 데이터를 보낼 때 사용되는 content-type을 false로 지정하여 browser에게 multipart/form-data를 사용하도록 합니다.
    headers: {"Authorization": token},
    success: function (response) {
      // 다른 성공 동작 처리
      window.location.href = "/mya/view/post/community";

    },
    error: function (error) {
      alert('Upload failed. Status: ' + error.status + ', Text: '
          + error.statusText);
      console.error("Error:", error);
    }
  });
}

document.addEventListener("DOMContentLoaded", function () {

  $.ajax({
    type: "GET",
    url: "/mya/communities/" + lastPart,
    headers: {"Authorization": token}
  })
  .done(function (response) {
    // fetchWorkerList(response);
    setCardData(response);
    // categoryId = response.categoryId;
    // commentBtnUpdate();
    imgList(response);

  })
  .fail(function (response, status, xhr) {
    alert("카드 정보 불러오기 실패");
    console.log(response);
  })
})

function setCardData(response) {
  let communityTitle = document.getElementById("communityTitle");
  let communityDescription = document.getElementById("communityDescription");

  communityTitle.value = response.title;
  communityDescription.value = response.description;
}

// let deleteFileName = [];

function imgList(response) {
  // var files = e.target.files;
  var filesArr = response['fileUrls']['fileName'].split(",");
  imgArr = filesArr;
  $(".img_wrap").empty();

  for (let f of filesArr) {

    var img_html = `<img src="${f}" class="img" style="transition: transform 0.5s;"/>`;
    // deleteFileName = deleteFileName + "," + f;
    // deleteFileName.push(f);
    // console.log(deleteFileName);
    $(".img_wrap").append(img_html);
  }
  slideImage(); // Call slideImage() after all images have been processed
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