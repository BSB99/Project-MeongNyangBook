function uploadData() {
  var formData = new FormData($('#uploadForm')[0]); // 폼의 데이터를 FormData 객체로 가져옵니다.

  var requestDto = {
    title: $(".title").val(),
    description: $(".image_upload_textarea").val()
  };

  formData.append("requestDto", JSON.stringify(requestDto));

  const token = Cookies.get('Authorization');

  $.ajax({
    url: "/mya/communities", // 백엔드 endpoint로 수정
    type: "POST",
    data: formData,
    processData: false, // 데이터를 query string으로 변환하는 jQuery 형식을 방지합니다.
    contentType: false, // 서버에 데이터를 보낼 때 사용되는 content-type을 false로 지정하여 browser에게 multipart/form-data를 사용하도록 합니다.
    headers: {"Authorization": token},
    success: function (response) {
      window.location.href = "/mya/view/post/community";
    },
    error: function (error) {
      alert('이미지가 너무 큽니다.');
      console.error("Error:", error);
    }
  });
}

function uploadAdoptions() {
  var formData = new FormData($('#uploadForms')[0]); // 폼의 데이터를 FormData 객체로 가져옵니다.

  var requestDto = {
    title: $(".title").val(),
    description: $(".image_upload_textarea").val(),
    animalName: $(".animal-name").val(),
    animalGender: $(".animal-sex").val(),
    animalAge: $(".animal-age").val(),
    area: $(".animal-address").val(),
    category: $(".animal-category").val()
  };
  formData.append("requestDto", JSON.stringify(requestDto));
  const token = Cookies.get('Authorization');

  $.ajax({
    url: "/mya/adoptions", // 백엔드 endpoint로 수정
    type: "POST",
    data: formData,
    processData: false, // 데이터를 query string으로 변환하는 jQuery 형식을 방지합니다.
    contentType: false, // 서버에 데이터를 보낼 때 사용되는 content-type을 false로 지정하여 browser에게 multipart/form-data를 사용하도록 합니다.
    headers: {"Authorization": token},
    success: function (response) {
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

