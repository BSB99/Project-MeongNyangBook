let urlParts = window.location.href.split("/");
userNo = urlParts[urlParts.length - 1].replace("#", "");
const token = Cookies.get("Authorization");
document.addEventListener("DOMContentLoaded", function () {
  if (token === undefined) {
    alert("로그인 후 이용해주세요");
    location.href = "/mya/view/users/sign-in";
  }
  const host = "http://" + window.location.host;
  myCommunity();

  $.ajax({
    type: "GET",
    url: "/mya/auth/profile/" + userNo,
    headers: {'Authorization': token}
  })
  .done((response) => {
    document.getElementById("nickname").value = response.nickname;
    document.getElementById("introduce").value = response.introduce;

    document.getElementById("address").value = response.address;
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

      let postId = res.id;
      let temp_html =
          `<div onclick="moveCommunityPost(${postId})" class="gallery-item" tabIndex="0">
        <img
            src="${res.fileUrls.fileName.split(",")[0]}"
            class="gallery-image"
            alt=""/>
        <div class="gallery-item-info">
          <ul>
            <li class="gallery-item-likes">
                    <span class="visually-hidden">Like:</span
                    ><i class="fas fa-heart" aria-hidden="true"></i> ${res.likeCount}
            </li>
            <li class="gallery-item-comments">
                    <span class="visually-hidden">Comments:</span
                    ><i class="fas fa-comment" aria-hidden="true"></i> ${res.commentCount}
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

  $.ajax({
    type: "GET",
    url: "/mya/adoptions/relative-post/" + userNo,
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('.gallery').empty();

    for (let res of response) {

      let postId = res.id;
      let file = res.fileUrls.fileName.split(",")[0];
      let temp_html =
          `<div onclick="moveAdoptionPost(${postId})" class="gallery-item" tabIndex="0">
        <img
            src="${file}"
            class="gallery-image"
            alt=""/>
        <div class="gallery-item-info">
          <ul>
            <li class="gallery-item-likes">
                    <span class="visually-hidden">Like:</span
                    ><i class="fas fa-heart" aria-hidden="true"></i> ${res.likeCount}
            </li>
            <li class="gallery-item-comments">
                    <span class="visually-hidden">Comments:</span
                    ><i class="fas fa-comment" aria-hidden="true"></i> ${res.commentCount}
            </li>
          </ul>
        </div>
      </div>`

      $('.gallery').append(temp_html);

    }
  })
  .fail(function (response) {
    console.log(response.responseJSON.msg);
  })
}

function onChat() {
  $.ajax({
    type: "POST",
    url: "/room/user/" + userNo,
    headers: {'Authorization': token}
  })
  .done(function (response) {
        window.location.href = "/mya/view/chat";
      }
  )
  .fail(function (response) {
    console.log(response.responseJSON.msg);
  })
}

function openReportModal() {
  $('.modale').addClass('opened');
}

function clsReportModal() {
  $('.modale').removeClass('opened');
}

function report() {
  let reportContent = document.getElementById("report_content").value;
  let reportCategory = document.getElementById("report_category").value;

  let requestDto = {
    "msg": reportContent,
    "reportEnum": reportCategory
  }

  $.ajax({
    type: "POST",
    url: "/mya/reports/user/" + userNo,
    contentType: "application/json",
    data: JSON.stringify(requestDto),
    headers: {'Authorization': token}

  })
  .done(function (response) {
        alert("유저 신고 완료");
        window.location.reload();
      }
  )
  .fail(function (response) {
    console.log(response.responseJSON.msg);
  })
}

function moveCommunityPost(postId) {
  window.location.href = "/mya/view/communities/" + postId;
}

function moveAdoptionPost(postId) {
  window.location.href = "/mya/view/adoptions/" + postId;
}