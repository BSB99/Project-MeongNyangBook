let urlParts = window.location.href.split("/");
userNo = urlParts[urlParts.length - 1].replace("#", "");

document.addEventListener("DOMContentLoaded", function () {
  const host = "http://" + window.location.host;
  start();
  // let userId = 1; // board 페이지에서 받아와야 하는 값
  const token = Cookies.get('Authorization');

  $.ajax({
    type: "GET",
    url: "/mya/auth/profile/" + userNo,
    headers: {'Authorization': token}
  })
  .done((response) => {
    document.getElementById("nickname").value = response.nickname;
    document.getElementById("introduce").value = response.introduce;

    document.getElementById("address").value = response.address;
    document.getElementById("phone-number").value = response.phoneNumber;
    document.getElementById("user-image").src = response.fileList;

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

function myCommunity() {
  const token = Cookies.get("Authorization");
  $.ajax({
    type: "GET",
    url: "/mya/communities/relative-post/" + userNo,
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('.gallery').empty();

    for (let res of response) {

      console.log(response);
      let temp_html =
          `<div class="gallery-item" tabIndex="0">
        <img
            src="${res.fileUrls.fileName.split(",")[0]}"
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
    url: "/mya/adoptions/relative-post/" + userNo,
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('.gallery').empty();

    for (let res of response) {

      console.log(response);
      let file = res.fileUrls.fileName.split(",")[0];
      console.log(file);
      let temp_html =
          `<div className="gallery-item" tabIndex="0">
        <img
            src="${file}"
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