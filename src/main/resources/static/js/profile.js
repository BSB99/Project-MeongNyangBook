const resizeS3FirstName = "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/resize/";
document.addEventListener("DOMContentLoaded", function () {

  const host = "http://" + window.location.host;
  myCommunity();
  // let userId = 1; // board 페이지에서 받아와야 하는 값
  const token = Cookies.get('Authorization');
  console.log("profile화면 출력")
  $.ajax({
    type: "GET",
    url: "/mya/auth/profile",
    headers: {'Authorization': token}
  })
  .done(function (response) {
    console.log(response.nickname);
    console.log(response.address);
    console.log(response.introduce);
    document.getElementById("nickname").value = response.nickname;
    document.getElementById("introduce").value = response.introduce;

    document.getElementById("address").value = response.address;
    document.getElementById("phone-number").value = response.phoneNumber;
    console.log("fileName : " + response.fileList)
    let fileNames = response.fileList.split("/");
    resizeFileName = resizeS3FirstName + fileNames[fileNames.length - 1];
    console.log(resizeFileName);
    document.getElementById("user-image").src = resizeFileName;

  })
  .fail(function (response) {
    alert(response.responseJSON.msg);
  })

  // 수정 버튼 클릭시 필드 값 변경 & 버튼 전환

  const doneButtons = document.querySelectorAll('.done-btn');
  doneButtons.forEach(function (button) {
    button.addEventListener('click', doneButtons);
  });
})

function EditButton(btn) {
  btn.style.display = 'none';
  const doneButton = document.getElementById("doneButton");
  doneButton.style.display = 'block';

  const nickname = document.getElementById("nickname");
  const address = document.getElementById("address");
  const phoneNumber = document.getElementById("phone-number");
  const introduce = document.getElementById("introduce");
  const imgUpload = document.getElementById("file");

  nickname.disabled = false;
  address.disabled = false;
  phoneNumber.disabled = false;
  introduce.disabled = false;
  imgUpload.style.display = "block";
  //사진 수정 추가하기

}

function updateBtn() {

  const doneButton = document.getElementById("doneButton");
  doneButton.style.display = "none";
  document.getElementById("edit-button").style.display = "block";

  const nickname = document.getElementById("nickname");
  const address = document.getElementById("address");
  const phoneNumber = document.getElementById("phone-number");
  const introduce = document.getElementById("introduce");
  const imgUpload = document.getElementById("file");

  nickname.disabled = true;
  address.disabled = true;
  phoneNumber.disabled = true;
  introduce.disabled = true;
  imgUpload.style.display = "none";

  console.log(nickname.value);

  const token = Cookies.get("Authorization");
  let formData = new FormData();
  const profileRequestDto = {
    nickname: nickname.value,
    address: address.value,
    phoneNumber: phoneNumber.value,
    introduce: introduce.value
  };

  console.log($('input[type="file"]')[0].files[0]);
  formData.append('fileName', $('input[type="file"]')[0].files[0]);

  formData.append("profileRequestDto", JSON.stringify(profileRequestDto));
  console.log(profileRequestDto);
  console.log(token);

  $.ajax({
    type: "PUT",
    url: "/mya/auth/profile",
    contentType: false,
    data: formData,
    headers: {'Authorization': token},
    processData: false
  })
  .done(function (xhr) {
    console.log(xhr);
    alert("프로필 수정 성공");
    location.href = "/";
    logout();
  })
  .fail(function (xhr) {
    alert('프로필 수정 오류!');
    alert("상태 코드 " + xhr.status + ": " + xhr.responseJSON.message);
  });
}

function myCommunity() {
  const token = Cookies.get("Authorization");
  $.ajax({
    type: "GET",
    url: "/mya/communities/my-post",
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('.gallery').empty();

    for (let res of response) {

      let splitName = res.fileUrls.fileName.split(",")[0];
      let resizefile = splitName.replace(
          "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/",
          resizeS3FirstName);
      let temp_html =
          `<div class="gallery-item" tabIndex="0">
        <img
            src="${resizefile}"
            class="gallery-image"
            alt=""/>
        <div class="gallery-item-info">
          <ul>
            <li class="gallery-item-likes">
                    <span class="visually-hidden">Like:</span
                    ><i class="fas fa-heart" aria-hidden="true"></i> 56
            </li>
            <li class="gallery-item-comments">
                    <span class="visually-hidden">Comments:</span
                    ><i class="fas fa-comment" aria-hidden="true"></i> 2
            </li>
          </ul>
        </div>
      </div>`

      $('.gallery').append(temp_html);

    }
  })
  .fail(function (response) {
    alert(response.responseJSON.msg);
  })
}

function myAdoption() {
  const token = Cookies.get("Authorization");
  $.ajax({
    type: "GET",
    url: "/mya/adoptions/my-post",
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('.gallery').empty();

    for (let res of response) {

      let splitName = res.fileUrls.fileName.split(",")[0];
      let resizefile = splitName.replace(
          "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/",
          resizeS3FirstName);

      let temp_html =
          `<div className="gallery-item" tabIndex="0">
        <img
            src="${resizefile}"
            className="gallery-image"
            alt=""
        />
        
        <div className="gallery-item-info">
          <ul>
            <li className="gallery-item-likes">
                    <span className="visually-hidden">Like:</span
                    ><i className="fas fa-heart" aria-hidden="true"></i> 56
            </li>
            <li className="gallery-item-comments">
                    <span className="visually-hidden">Comments:</span
                    ><i className="fas fa-comment" aria-hidden="true"></i> 2
            </li>
          </ul>
        </div>
      </div>`

      $('.gallery').append(temp_html);

    }
  })
  .fail(function (response) {
    alert(response.responseJSON.msg);
  })
}