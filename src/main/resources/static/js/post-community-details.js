let lastPart;
let userNickname;
let currentUserId;
let currentPageUserId;
const token = Cookies.get('Authorization');
document.addEventListener("DOMContentLoaded", function () {

  start();

  var aTagUsername = document.getElementById("username");

  aTagUsername.addEventListener("click", function (event) {
    event.preventDefault(); // Prevent the default behavior of the link
    usernameClick(); // Call your function
  });

  if (!token || token.length === 0) {
    alert("로그인 후 이용해주세요");
    location.href = "/mya/view/users/sign-in";
  }

  getUserNickname();

  let currentURL = window.location.href;
  const commentBox = document.querySelectorAll(".comments-area");

  let urlParts = currentURL.split("/");
  lastPart = urlParts[urlParts.length - 1].replace("#", "");

  confirmHeart()

  $.ajax({
    type: "GET",
    url: "/mya/communities/" + lastPart,
    headers: {
      "Authorization": token
    }
  })
  .done(function (response) {
    setCardData(response);

    commentBox.forEach(container => {
      container.innerHTML = '';
    });

    let firstCommentBoxHtml = `<h4>댓글</h4>`;
    let commentInfoHtml = ``;
    if (response.commentList.length > 0) {
      for (let commentInfo of response.commentList) {
        let resizeImg = fileImgNullCheck(commentInfo.imgUrl);
        let replyButtonHtml = ``;

        if (commentInfo.userNickname === userNickname) {
          replyButtonHtml = `
                        <div class="btn" style="width: 100px; height: 100px">
                            <div class="reply-btn">
                                <a class="btn-reply text-uppercase edit-button" onclick="editComment(this)">수정</a>
                                <a class="btn-reply text-uppercase confirm-button" style="display: none;" onclick="confirmEdit(this, ${commentInfo.commentId})">완료</a>
                            </div>
                            <div class="delete-btn">
                                <a class="btn-reply text-uppercase" onclick="deleteComment(${commentInfo.commentId})">삭제</a>
                            </div>
                        </div>`;
        }

        commentInfoHtml += `
                    <div class="comment-list">
                        <div class="single-comment justify-content-between d-flex">
                            <div class="user justify-content-between d-flex">
                                <a href="javascript:void(0);" onclick="commentClickMove(${commentInfo.userId})" class="thumb">
                                    <img src="${resizeImg}" alt="" style="width: 70px; height: 50px;">
                                </a>
                                <div class="desc">
                                    <h5>
                                        <a href="javascript:void(0);" onclick="commentClickMove(${commentInfo.userId})">${commentInfo.userNickname}</a>
                                    </h5>
                                    <p class="comment">
                                        ${commentInfo.content}
                                    </p>
                                    <!-- Comment input for editing, initially hidden -->
                                    <input type="text" class="comment-input" style="display: none;" value="${commentInfo.content}">
                                </div>
                            </div>
                            ${replyButtonHtml}
                        </div>
                    </div>`;
      }
    }

    let lastCommentBoxHtml = `</div>`;
    commentBox.forEach(container => {
      container.innerHTML = firstCommentBoxHtml + commentInfoHtml
          + lastCommentBoxHtml;
    });
  })
  .fail(function (response, status, xhr) {
    console.log(response);
  });
});

function commentClickMove(userId) {
  if (currentUserId === userId) {
    window.location.href = "/mya/view/users/my-profile";
  } else {
    window.location.href = "/mya/view/users/relative-profile/" + userId;
  }
}

function fileImgNullCheck(imgFileName) {
  let profilePicture;
  if (imgFileName == null) {
    profilePicture = "https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/1_copy.jpg";
  } else {
    profilePicture = imgFileName.replace(
        "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/",
        "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/resize/")
  }
  return profilePicture;
}

function setCardData(response) {

  currentPageUserId = response.userId;

  let communityTitle = document.getElementById("communityTitle");
  let communityDescription = document.getElementById("communityDescription");
  let viewCount = document.getElementById("viewCount");
  let nickname = document.getElementById("username");
  let createdAt = document.getElementById("createdAt");
  let likes = document.getElementById("likes");

  let fileNames = response.fileUrls.fileName.split(",");

  // if (response.username !== userNickname) {
  //   let deleteCommunityBtn = document.getElementById("deleteCommunityBtn");
  //   let updateCommunityBtn = document.getElementById("updateCommunityBtn");
  //   deleteCommunityBtn.style.display = "none";
  //   updateCommunityBtn.style.display = "none";
  // }

  $("#carousel-inners").empty();
  let i = 0;
  for (let file of fileNames) {
    let temp_html
    if (i == 0) {
      temp_html = `<div class="carousel-item active">
        <img
            src="${file}"
            class="img-fluid d-block w-100" alt="..." style="height: 550px">
      </div>`
    } else {
      temp_html = `<div class="carousel-item">
        <img
            src="${file}"
            class="img-fluid d-block w-100 " alt="..." style="height: 550px">
      </div>`
    }
    $("#carousel-inners").append(temp_html);
    i++;
  }

  //수정 삭제 버튼 활성,비활성
  let modifyBtn = document.getElementById("community_modify_btn");
  let deleteBtn = document.getElementById("community_delete_btn");
  getUserNickname();
  if (userNickname === response.username) {
    modifyBtn.style.display = "block";
    deleteBtn.style.display = "block";
  } else {
    modifyBtn.style.display = "none";
    deleteBtn.style.display = "none";
  }

  communityTitle.innerText = response.title;
  communityDescription.innerText = response.description;

  // 아래 부분은 응답 데이터 구조에 따라 약간 다를 수 있습니다.
  viewCount.innerText = response.viewCount + " Views";
  nickname.innerText = response.username;
  createdAt.innerText = response.createdAt;
  likes.innerText = response.likesCount + " likes";
}

function deleteCommunity() {

  $.ajax({
    url: "/mya/communities/" + lastPart, // 백엔드 endpoint로 수정
    type: "DELETE",
    headers: {"Authorization": token},
    success: function (response) {
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

function postComment() {
  const orderRecipientInput = document.getElementById("requestComment").value;

  if (orderRecipientInput.length === 0) {
    alert("댓글을 입력해주세요");
    return;
  }

  const requestDto = {
    "postId": lastPart,
    "content": orderRecipientInput
  }

  $.ajax({
    type: "POST",
    url: "/mya/comments",
    headers: {
      'Content-Type': 'application/json',
      "Authorization": token
    },
    data: JSON.stringify(requestDto),
    timeout: 1000
  })
  .done((res) => {
    if (res.statusCode === 201) {
      location.reload();
    }
  })
  .fail(function (response, status, xhr) {
    location.reload();
  })
}

function getUserNickname() {
  $.ajax({
    type: "GET",
    url: "/mya/users",
    headers: {"Authorization": token}
  })
  .done((res) => {
    userNickname = res.nickname;
    currentUserId = res.userId;
  })
  .fail(function (response, status, xhr) {
    console.log(response);
  })
}

function deleteComment(commentId) {
  $.ajax({
    type: "DELETE",
    url: "/mya/comments/" + commentId,
    headers: {"Authorization": token}
  })
  .done((res) => {
    location.reload();
  })
  .fail(function (response, status, xhr) {
    alert("댓글 삭제 실패");
    console.log(response);
  })
}

function editComment(button) {
  const commentContainer = button.closest(".single-comment");
  const commentContent = commentContainer.querySelector(".comment");
  const commentInput = commentContainer.querySelector(".comment-input");
  const editButton = commentContainer.querySelector(".edit-button");
  const confirmButton = commentContainer.querySelector(".confirm-button");

  commentContent.style.display = "none";
  commentInput.style.display = "inline";
  editButton.style.display = "none";
  confirmButton.style.display = "inline";
}

function confirmEdit(button, commentId) {
  const commentContainer = button.closest(".single-comment");
  const commentContent = commentContainer.querySelector(".comment");
  const commentInput = commentContainer.querySelector(".comment-input");
  const editButton = commentContainer.querySelector(".edit-button");
  const confirmButton = commentContainer.querySelector(".confirm-button");

  const newComment = commentInput.value;

  // TODO: Ajax 요청을 사용하여 댓글 수정 내용과 아이디를 서버로 전송
  // 아래는 예시로 console.log로 출력하는 코드입니다.
  const commentRequestDto = {
    content: newComment
  }
  $.ajax({
    type: "PATCH",
    url: "/mya/comments/" + commentId,
    headers: {
      'Content-Type': 'application/json',
      "Authorization": token
    },
    data: JSON.stringify(commentRequestDto)
  })
  .done((res) => {
  })
  .fail(function (response, status, xhr) {
    alert("댓글 수정 실패");
    console.log(response);
  })

  commentContent.innerText = newComment;
  commentContent.style.display = "inline";
  commentInput.style.display = "none";
  editButton.style.display = "inline";
  confirmButton.style.display = "none";
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

function commentLike() {
  const heart = document.querySelector(".bi-heart");
  const fillValue = heart.getAttribute("fill");
  if (fillValue === "black") {
    $.ajax({
      type: "POST",
      url: "/mya/likes/" + lastPart,
      headers: {
        "Authorization": token
      },
      timeout: 1000
    })
    .done((res) => {
      location.reload();
    })
    .fail(function (response, status, xhr) {
      location.reload();
    })
  } else {
    $.ajax({
      type: "DELETE",
      url: "/mya/likes/" + lastPart,
      headers: {
        "Authorization": token
      },
      timeout: 1000
    })
    .done((res) => {
      location.reload();
    })
    .fail(function (response, status, xhr) {
      alert("좋아요 취소 실패");
      console.log(response);
    })
  }
}

function confirmHeart() {
  const heart = document.querySelector(".bi-heart");

  $.ajax({
    type: "GET",
    url: "/mya/likes/" + lastPart,
    headers: {
      "Authorization": token
    }
  })
  .done((res) => {
    if (res) {
      heart.setAttribute("fill", "red");
    } else {
      heart.setAttribute("fill", "black");
    }
  })
  .fail(function (response, status, xhr) {
    console.log(response);
  })
}

function usernameClick() {

  window.location.href = "/mya/view/users/relative-profile/"
      + currentPageUserId;
}